package com.app.view;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.app.controller.PeopleController;
import com.app.entity.CatAutoEntity;
import com.app.entity.PeopleEntity;
import com.app.helper.EditingCell;
import com.app.helper.Helpers;
import com.app.helper.PeopleHelper;
import com.app.helper.ViewHelper;
import com.app.service.PeopleLicenceService;
import com.app.service.PeopleService;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public class People {
    private PeopleService peopleService = new PeopleService();
    private PeopleEntity people = new PeopleEntity();
    private TableView<PeopleEntity> peopleTableView;
    private ObservableList<PeopleEntity> peopleObservableList;
    private List<Integer> licenceObservableList;
    Stage subStage;

    static public TextField nameTF, lnameTF, fnameTF;
    static public TextField egn, phone, mail;
    static public ComboBox firmsTF;
    static public DatePicker dateBorn;
    static public TextField address, addressSecond;
    static public TextField countryBorn, citizenship;
    static public TextField text1, text2;
    static public Label firmsLabel, otherLabel;
    static public RadioButton t1,t2,t3,t4,t5,t6,t7;
    static public CheckBox c0, c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17;
    static public Integer status, type, idTF;
    static public DatePicker dateCreate, dateValidation;
    static public TextField place, numberDoc;
    static public String catST, typeST, brandST, modelST, fuelST, yearST;
    static public GridPane gridPane;
    public People() {
        subStage = new Stage();
        subStage.setTitle("Лица");
        Scene scene = new Scene(createpeopleCreateFormPane(), 1500, 700);
        subStage.setScene(scene);
        subStage.show();
    }

    public GridPane createpeopleCreateFormPane() {
        gridPane = new GridPane();
        GridPane gridPaneTab = new GridPane();

        PeopleHelper.setGridPane(gridPane);
        PeopleHelper.setGridPane(gridPaneTab);
        addUIControls(gridPane, gridPaneTab);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane, GridPane gridPaneTab) {
        Label headerLabel = new Label("Лица");
        headerLabel.setFont(new Font("Arial", 26));

        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        t1=new RadioButton("Шофьор");
        t2=new RadioButton("Механник");
        t3=new RadioButton("Ползвател");
        t4=new RadioButton("Друго");
        t1.setSelected(true);
        ViewHelper.RadioButton(gridPane, t1, t2, t3, t4,"Вид лице: ","",0,1);

        t5=new RadioButton("Частно лице");
        t6=new RadioButton("Фирма");
        t7=new RadioButton("Друго");
        t7.setSelected(true);
        ViewHelper.RadioButton(gridPane, t5, t6, t7,"",0,2);

        firmsLabel = new Label("Към Фирма/Организация: ");
        gridPane.add(firmsLabel, 4, 2);
        firmsTF = ViewHelper.getComboBoxFirms(gridPane, 5, 2);
        firmsTF.setVisible(false);
        firmsLabel.setVisible(false);

        otherLabel = new Label("Друго: ");
        gridPane.add(otherLabel, 4, 2);
        text1 = PeopleHelper.addTextField(gridPane, "", 5, 2);

        Button viewButton = new Button("Избор");
        gridPane.add(viewButton, 5, 1);
        viewButton.setOnAction(event -> {subStage.close(); new SelectPeople();});
        //Ред 4 - Имер Презимер, Фамилия
        /*PeopleHelper.addLabel(gridPane, "№: ", 0, 3);
        idTF = PeopleHelper.addTextField(gridPane, "", 1, 3);*/

        PeopleHelper.addLabel(gridPane, "Име: ", 0, 3);
        nameTF = PeopleHelper.addTextField(gridPane, "", 1, 3);

        PeopleHelper.addLabel(gridPane, "Презиме: ", 2, 3);
        lnameTF = PeopleHelper.addTextField(gridPane, "", 3, 3);

        PeopleHelper.addLabel(gridPane, "Фамилия: ", 4, 3);
        fnameTF = PeopleHelper.addTextField(gridPane, "", 5, 3);

        //Ред 5 - ЕГН, Телефон, Имейл
        PeopleHelper.addLabel(gridPane, "ЕГН: ", 0, 4);
        egn = PeopleHelper.addTextField(gridPane, "", 1, 4);

        PeopleHelper.addLabel(gridPane, "Телефон: ", 2, 4);
        phone = PeopleHelper.addTextField(gridPane, "", 3, 4);

        PeopleHelper.addLabel(gridPane, "Email: ", 4, 4);
        mail = PeopleHelper.addTextField(gridPane, "", 5, 4);

        //Ред 6 - Дата на раждане, Място на раждане, Гражданство
        PeopleHelper.addLabel(gridPane, "Дата на раждане: ", 0, 5);
        dateBorn = PeopleHelper.addDatePicker(gridPane, "", 1, 5);

        PeopleHelper.addLabel(gridPane, "Място на раждане: ", 2, 5);
        countryBorn = PeopleHelper.addTextField(gridPane, "", 3, 5);

        PeopleHelper.addLabel(gridPane, "Гражданство: ", 4, 5);
        citizenship = PeopleHelper.addTextField(gridPane, "", 5, 5);

        //Ред 7 - Адрес по лична карта
        PeopleHelper.addLabel(gridPane, "Адрес по лична карта: ", 0, 6);
        address = PeopleHelper.addTextField(gridPane, "", 1, 6);

        PeopleHelper.addLabel(gridPane, "Втори адрес: ", 2, 6);
        addressSecond = PeopleHelper.addTextField(gridPane, "", 3, 6);

        BorderPane root = new BorderPane();
        TabPane tabpane = new TabPane();
        tabpane.setBackground(new Background(new BackgroundFill(Color.rgb(201, 230, 203), CornerRadii.EMPTY, Insets.EMPTY)));

        Tab tab = new Tab("Данни за шофьорската книжка");
        //Ред Tab - Дата на издаване и др.
        PeopleHelper.addLabel(gridPaneTab, "Дата на издаване: ", 0, 1);
        dateCreate = PeopleHelper.addDatePicker(gridPaneTab, "", 1, 1);

        PeopleHelper.addLabel(gridPaneTab, "Дата на валидност: ", 2, 1);
        dateValidation = PeopleHelper.addDatePicker(gridPaneTab, "", 3, 1);

        //
        PeopleHelper.addLabel(gridPaneTab, "Издаващ орган място: ", 0, 2);
        place = PeopleHelper.addTextField(gridPaneTab, "", 1, 2);

        PeopleHelper.addLabel(gridPaneTab, "Номер на документа: ", 2, 2);
        numberDoc = PeopleHelper.addTextField(gridPaneTab, "", 3, 2);
        //tab.setContent(dateCreate);
        c0=new CheckBox("Всички");
        c1=new CheckBox("AM");
        c2=new CheckBox("A1");
        c3=new CheckBox("A2");
        c4=new CheckBox("A");
        c5=new CheckBox("B1");
        c6=new CheckBox("B");
        c7=new CheckBox("C1");
        c8=new CheckBox("C");
        c9=new CheckBox("D1");
        c10=new CheckBox("D");
        c11=new CheckBox("BE");
        c12=new CheckBox("C1E");
        c13=new CheckBox("CE");
        c14=new CheckBox("D1E");
        c15=new CheckBox("DE");
        c16=new CheckBox("Ttm");
        c17=new CheckBox("Tkm");
        //t1.setSelected(true);
        ViewHelper.CheckBox(gridPaneTab, c0, c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17,"",0,3);
        tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        gridPaneTab.setBackground(new Background(new BackgroundFill(Color.rgb(213, 216, 230), CornerRadii.EMPTY, Insets.EMPTY)));
        tab.setContent(gridPaneTab);

        Tab tab2 = new Tab("Други документи");
        tab2.setDisable(true);
        tabpane.getTabs().add(tab);
        tabpane.getTabs().add(tab2);
        //root.setCenter();
        gridPane.add(tabpane,0,7, 6,5);

        //TextField searchBoxTF = new TextField();
        Button editButton = new Button("Нов");
        Button addButton = new Button("Запис");
        Button exitButton = new Button("Изход");

        addButton.setDisable(true);

        PeopleHelper.initSearchSection(gridPane, editButton, addButton, exitButton,
                "", 3, 13);

        //gridPane.add(getTableView(getpeopleInfo(false, null), gridPane),2,1, 1, 14);
        PeopleController.show();

        addButton.setOnAction(e -> {
            try {
                    if(t1.isSelected()){
                        status = 1;
                    }
                    if(t2.isSelected()){
                        status = 2;
                    }
                    if(t3.isSelected()){
                        status = 3;
                    }
                    if(t4.isSelected()){
                        status = 4;
                    }
                if(t5.isSelected()){
                    type = 1;
                }
                if(t6.isSelected()){
                    type = 2;
                }
                if(t7.isSelected()){
                    type = 3;
                }
                  //comboBoxValue(catAuto, brand, model, fuel, type, yearTF);idTF,
                getLicenceInfo();

                onSubmit(gridPane, people,  nameTF, fnameTF, lnameTF, egn,
                        phone, mail, dateBorn, address, addressSecond, citizenship, text1, firmsTF, status, type, dateCreate, dateValidation, place, numberDoc);
                PeopleLicenceService.delete(idTF);
                for (Integer id : licenceObservableList) {
                    PeopleLicenceService.getAll(id,idTF);
                }

                addButton.setDisable(true);
                //gridPane.add(getTableView(getpeopleInfo(false, null), gridPane),2,1, 1, 14);
            } catch (ParseException ex) {
                // LOGGER.error("Exception in submit button set on action: ", ex.getStackTrace());
            }
        });
        editButton.setOnAction(e -> {
            PeopleController.idNum();

            addButton.setDisable(false);
        });
        exitButton.setOnAction(e -> {
            subStage.close();
        });

        c0.setOnAction(e -> {
            if(c0.isSelected()){
                CheckAll(true);
            }else{CheckAll(false);}
        });
        t6.setOnAction(e -> {
            VisibleRadioButton();
        });
        t7.setOnAction(e -> {
            VisibleRadioButton();
        });
        t5.setOnAction(e -> {
            VisibleRadioButton();
        });
    }

    private void onSubmit(GridPane gridPane, PeopleEntity People, TextField name, TextField fname, TextField lname,
                          TextField egnTF, TextField phoneTF, TextField mailTF, DatePicker dateB, TextField addressTF, TextField addressSecTF,
                          TextField citizenshipTF, TextField tex1, ComboBox firms,int status, int type,
                          DatePicker dateC, DatePicker dateV, TextField placeTF, TextField numberD) throws ParseException {
        if (name.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Не е попълнен регистрационния номер!");
            return;
        }
        /*if (id==null) {
            id=1;
        }*/

        createPeople(nameTF.getText(), fname.getText(), lname.getText(),
                egnTF.getText(), phoneTF.getText(), mailTF.getText(), Date.valueOf(dateB.getValue()), addressTF.getText(), addressSecTF.getText(),
                citizenshipTF.getText(), tex1.getText(), firms.getValue().toString(), status, type,
                Date.valueOf(dateC.getValue()), Date.valueOf(dateV.getValue()), placeTF.getText(), numberD.getText());
        Helpers.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(),
                "Записа успешен!", "Успешно записахте данните за лице!");
    }


    private void createPeople(String name, String fname, String lname,
                              String egnTF, String phoneTF, String mailTF, Date dateB, String addressTF, String addressSecTF,
                              String citizenshipTF, String tex1, String firms, int status, int type,
                              Date dateC, Date dateV, String placeTF, String numderD) {
        PeopleService PeopleService = new PeopleService();

        PeopleEntity People = new PeopleEntity();
        People.setId(idTF);
        People.setName(name);
        People.setLname(lname);
        People.setFname(fname);
        People.setEgn(egnTF);
        People.setPhone(phoneTF);
        People.setMail(mailTF);
        People.setDateBorn(dateB);
        People.setAddress(addressTF);
        People.setAddressSecond(addressSecTF);
        People.setBornCountry(citizenshipTF);
        People.setText1(tex1);
        People.setIdFirms(PeopleService.getExistingFirmsId(firms));
        People.setTypePerson(type);
        People.setStatusPerson(status);
        People.setDateC(dateC);
        People.setDateV(dateV);
        People.setPlace(placeTF);
        People.setNumberDoc(numderD);

        PeopleService.getById(People);
    }

    public static void visible(PeopleEntity people) {

        //PeopleEntity people = new PeopleEntity();
        nameTF.setText(people.getName());
        lnameTF.setText(people.getLname());
        fnameTF.setText(people.getFname());
    }
    public static void idNumber(String id) {
        if(id==null){
            idTF = 1;
        }else{idTF= Integer.valueOf(id);}

    }
    public void CheckAll(Boolean check){
        c1.setSelected(check);
        c2.setSelected(check);
        c3.setSelected(check);
        c4.setSelected(check);
        c5.setSelected(check);
        c6.setSelected(check);
        c7.setSelected(check);
        c8.setSelected(check);
        c9.setSelected(check);
        c10.setSelected(check);
        c11.setSelected(check);
        c12.setSelected(check);
        c13.setSelected(check);
        c14.setSelected(check);
        c15.setSelected(check);
        c16.setSelected(check);
        c17.setSelected(check);
    }
    public void VisibleRadioButton(){

        if(t6.isSelected()){
            firmsTF.setVisible(true);
            firmsLabel.setVisible(true);
        }else{firmsTF.setVisible(false);
            firmsLabel.setVisible(false);}


        if(t7.isSelected()){
            text1.setVisible(true);
            otherLabel.setVisible(true);
        }else{text1.setVisible(false);
            otherLabel.setVisible(false);}
    }
    private ObservableList<PeopleEntity> getpeopleInfo(boolean ispeopleSearched, String criteria) {
        peopleObservableList = FXCollections.observableArrayList();

        if (ispeopleSearched) {
            for (PeopleEntity people : peopleService.getSearched(PeopleHelper.capitalizeFirstLetter(criteria))) {
                peopleObservableList.add(people);
            }
        } else {
            for (PeopleEntity people : peopleService.getAll()) {
                peopleObservableList.add(people);
            }
        }

        return peopleObservableList;
    }
    private List<PeopleEntity> getLicenceInfo() {
        licenceObservableList = FXCollections.observableArrayList();
        if(c1.isSelected()){licenceObservableList.add(1);}
        if(c2.isSelected()){licenceObservableList.add(2);}
        if(c3.isSelected()){licenceObservableList.add(3);}
        if(c4.isSelected()){licenceObservableList.add(4);}
        if(c5.isSelected()){licenceObservableList.add(5);}
        if(c6.isSelected()){licenceObservableList.add(6);}
        if(c7.isSelected()){licenceObservableList.add(7);}
        if(c8.isSelected()){licenceObservableList.add(8);}
        if(c9.isSelected()){licenceObservableList.add(9);}
        if(c10.isSelected()){licenceObservableList.add(10);}
        if(c11.isSelected()){licenceObservableList.add(11);}
        if(c12.isSelected()){licenceObservableList.add(12);}
        if(c13.isSelected()){licenceObservableList.add(13);}
        if(c14.isSelected()){licenceObservableList.add(14);}
        if(c15.isSelected()){licenceObservableList.add(15);}
        if(c16.isSelected()){licenceObservableList.add(16);}
        if(c17.isSelected()){licenceObservableList.add(17);}

        return peopleObservableList;
    }
    private TableView getTableView(ObservableList<PeopleEntity> observableList, GridPane gridPane) {
        Callback<TableColumn<PeopleEntity, String>, TableCell<PeopleEntity, String>> cellFactory
                = (TableColumn<PeopleEntity, String> param) -> new EditingCell<PeopleEntity>();

        //Column for people name
        TableColumn<PeopleEntity, String> nameColumn = new TableColumn<>("Наименование");
        nameColumn.setMinWidth(500);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(cellFactory);
        nameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<PeopleEntity, String> t) -> {
                    ((PeopleEntity) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setName(t.getNewValue());

                });

        TableColumn<PeopleEntity, PeopleEntity> actions = new TableColumn<>("Действия");
        actions.setMinWidth(400);
        actions.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actions.setCellFactory(param -> new TableCell<PeopleEntity, PeopleEntity>() {
            private final Button editButton = new Button("Редакция");
            private final Button deleteButton = new Button("Изтриване");
            private final Button selectButton = new Button("Избор");
            private final HBox pane = new HBox(deleteButton, editButton,selectButton);

            {
                deleteButton.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Вие ще изтриете запис!");
                    alert.setContentText("Желаете ли да продължите?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        PeopleEntity people = getTableView().getItems().get(getIndex());
                        PeopleService peopleService = new PeopleService();
                        peopleService.delete(people);
                        getTableView().getItems().remove(people);
                    }
                });

                editButton.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Вие ще редактирате запис!");
                    alert.setContentText("Желаете ли да продължите?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        PeopleEntity people = getTableView().getItems().get(getIndex());
                        PeopleService peopleService = new PeopleService();
                        peopleService.update(people);
                    }
                });

                selectButton.setOnAction(event -> {
                    PeopleEntity people = getTableView().getItems().get(getIndex());
                    PeopleService peopleService = new PeopleService();
                    peopleService.vis(people);

                });
            }

            @Override
            protected void updateItem(PeopleEntity item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });


        peopleTableView = new TableView<>();
        peopleTableView.setEditable(true);
        peopleTableView.setItems(observableList);
        peopleTableView.getColumns().addAll(nameColumn, actions);

        return peopleTableView;
    }
}
