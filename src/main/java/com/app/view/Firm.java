package com.app.view;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.app.controller.FirmsController;
import com.app.controller.MyDataController;
import com.app.entity.FirmsEntity;
import com.app.helper.EditingCell;
import com.app.helper.FirmsHelper;
import com.app.helper.Helpers;
import com.app.service.FirmsService;

import java.text.ParseException;
import java.util.Optional;

//import static com.app.controller.MyDataController.visible;

public class Firm {

    //private static final Logger LOGGER = LoggerFactory.getLogger(Firms.class);

    private FirmsService facultyService = new FirmsService();

    private TableView<FirmsEntity> firmsTableView;
    private FirmsEntity firm = new FirmsEntity();
    private TableView<FirmsEntity> facultyTableView;
    private ObservableList<FirmsEntity> facultyObservableList;
    Stage subStage;

    static public TextField idTF;
    static public TextField nameTF;
    static public TextField city;
    static public TextField country;
    static public TextField address;
    static public TextField EIK;
    static public TextField DDS;
    static public TextField bank;
    static public TextField Iban;
    static public TextField bic;
    static public TextField manager;
    static public TextField place;
    static public TextField phone;
    public Firm() {
        subStage = new Stage();
        subStage.setTitle("Данни за фирмата");

        //FlowPane root = new FlowPane();
        //root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(createFacultyCreateFormPane(), 1500, 700);

        //root.getChildren().add(new Button("New Stage"));
        subStage.setScene(scene);
        subStage.show();
    }


    public GridPane createFacultyCreateFormPane() {
        GridPane gridPane = new GridPane();

        FirmsHelper.setGridPane(gridPane);
        addUIControls(gridPane);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Данни за фирмата");
        headerLabel.setFont(new Font("Arial", 26));

        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));


        FirmsHelper.addLabel(gridPane, "№: ", 0, 1);
        idTF = FirmsHelper.addTextField(gridPane, "", 1, 1);

        //Faculty name label and text
        FirmsHelper.addLabel(gridPane, "Име на фирмата: ", 0, 2);
        nameTF = FirmsHelper.addTextField(gridPane, "", 1, 2);
        //ValidationHelper.lettersOnly(nameTF, 200);

        //country label and text
        FirmsHelper.addLabel(gridPane, "Държава: ", 0, 3);
        country = FirmsHelper.addTextField(gridPane, "", 1, 3);
        //ValidationHelper.lettersOnly(country, 20);

        //city label and text
        FirmsHelper.addLabel(gridPane, "Град: ", 0, 4);
        city = FirmsHelper.addTextField(gridPane, "", 1, 4);
        //ValidationHelper.lettersOnly(city, 50);

        //address label and text
        FirmsHelper.addLabel(gridPane, "Адрес: ", 0, 5);
        address = FirmsHelper.addTextField(gridPane, "", 1, 5);
        //ValidationHelper.lettersOnly(address, 100);

        //EIK label and text
        FirmsHelper.addLabel(gridPane, "ЕИК: ", 0, 6);
        EIK = FirmsHelper.addTextField(gridPane, "", 1, 6);
        //ValidationHelper.lettersAndNumberOnly(EIK, 13);

        //DDS label and text
        FirmsHelper.addLabel(gridPane, "Регистрация по ДДС: ", 0, 7);
        DDS = FirmsHelper.addTextField(gridPane, "", 1, 7);
        //ValidationHelper.lettersAndNumberOnly(DDS, 15);

        //bank label and text
        FirmsHelper.addLabel(gridPane, "Банка: ", 0, 8);
        bank = FirmsHelper.addTextField(gridPane, "", 1, 8);
        //ValidationHelper.lettersOnly(bank, 50);

        //Iban label and text
        FirmsHelper.addLabel(gridPane, "IBAN: ", 0, 9);
        Iban = FirmsHelper.addTextField(gridPane, "", 1, 9);
        //ValidationHelper.lettersAndNumberOnly(Iban, 30);

        //bic label and text
        FirmsHelper.addLabel(gridPane, "BIC: ", 0, 10);
        bic = FirmsHelper.addTextField(gridPane, "", 1, 10);
        //bic.setText("Not Glowing");
        //ValidationHelper.lettersAndNumberOnly(bic, 10);

        //manager label and text
        FirmsHelper.addLabel(gridPane, "МОЛ: ", 0, 11);
        manager = FirmsHelper.addTextField(gridPane, "", 1, 11);
        //ValidationHelper.lettersOnly(manager, 50);

        //place label and text
        FirmsHelper.addLabel(gridPane, "Място на сделката: ", 0, 12);
        place = FirmsHelper.addTextField(gridPane, "", 1, 12);
        //ValidationHelper.lettersOnly(place, 50);

        //phone label and text
        FirmsHelper.addLabel(gridPane, "Телефон: ", 0, 13);
        phone = FirmsHelper.addTextField(gridPane, "", 1, 13);
        //phone.setVisible(false);
        //ValidationHelper.lettersAndNumberOnly(phone, 20);

        //TextField searchBoxTF = new TextField();
        Button editButton = new Button("Нов");
        Button addButton = new Button("Запис");
        Button exitButton = new Button("Изход");

       FirmsHelper.initSearchSection(gridPane, editButton, addButton, exitButton,
                "", 1, 14);

        gridPane.add(getTableView(getfirmsInfo(false, null), gridPane),2,1, 1, 13);

        //gridPane.add(getTableView(getFacultyInfo(false, null), gridPane), 1, 9);

        //visible(firm, nameTF, country, city, address, EIK, DDS, bank,
         //       Iban, bic, manager, phone, place);
        FirmsController.vis();

        addButton.setOnAction(e -> {
            try {
                onSubmit(gridPane, firm, nameTF, country, city, address, EIK, DDS, bank,
                        Iban, bic, manager, phone, place);
                //gridPane.add(getTableView(getFacultyInfo(false, null), gridPane), 1, 9);
            } catch (ParseException ex) {
                // LOGGER.error("Exception in submit button set on action: ", ex.getStackTrace());
            }
        });
        editButton.setOnAction(e -> {
                FirmsHelper.clearForm(gridPane, firm, nameTF, country, city, address, EIK, DDS, bank,
                        Iban, bic, manager, phone, place);
                FirmsController.idNum();
        });

        /*searchButton.setOnAction(e -> {
            gridPane.add(getTableView(getFacultyInfo(true, searchBoxTF.getText()), gridPane), 1, 9);
        });*/

        exitButton.setOnAction(e -> {
            subStage.close();
        });
    }


    public static void idNumber(String id) {
        idTF.setText(id);

        if(id=="0" || id==null){
            idTF.setText("1");
        }
    }
    private ObservableList<FirmsEntity> firmsObservableList;
    private ObservableList<FirmsEntity> getfirmsInfo(boolean isfirmsSearched, String criteria) {
        firmsObservableList = FXCollections.observableArrayList();

        if (isfirmsSearched) {
            for (FirmsEntity firms : FirmsService.getSearched(FirmsHelper.capitalizeFirstLetter(criteria))) {
                firmsObservableList.add(firms);
            }
        } else {
            for (FirmsEntity firms : FirmsService.getAll()) {
                firmsObservableList.add(firms);
            }
        }

        return firmsObservableList;
    }
    private void onSubmit(GridPane gridPane, FirmsEntity firm, TextField nameTF, TextField country, TextField city,
                          TextField address, TextField EIK, TextField DDS, TextField bank,
                          TextField Iban, TextField bic, TextField manager, TextField phone, TextField place) throws ParseException {
        if (nameTF.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Не е попълнено име на фирмата!");
            return;
        }
        if (EIK.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Не е попълнен ЕИК/Булстат!");
            return;
        }

        createFaculty(nameTF.getText(), country.getText(), EIK.getText(), address.getText(), city.getText(),
                bank.getText(), Iban.getText(), bic.getText(), DDS.getText(), manager.getText(), place.getText(), phone.getText(), Integer.valueOf(idTF.getText()));
        Helpers.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(),
                "Записа успешен!", "Успешно записахте данните на Вашата фирма!");
        //ViewHelper.clearForm(nameTF, EIK);
        MyDataController.read();
    }


    private void createFaculty(String name, String country, String EIK, String address, String city,
                               String bank, String iban, String bic, String dds, String manager, String place, String phone, Integer id) {
        FirmsService firmService = new FirmsService();

        FirmsEntity firm = new FirmsEntity();
        firm.setName(name);
        firm.setEIK(EIK);
        firm.setCountry(country);
        firm.setAddress(address);
        firm.setBank(bank);
        firm.setIBAN(iban);
        firm.setBIC(bic);
        firm.setDDS(dds);
        firm.setManager(manager);
        firm.setPhone(phone);
        firm.setCity(city);
        firm.setId(id);
        firm.setPlace(place);

        firmService.getById(firm);
    }

    public static void visible(String id,String name, String countryS, String cityS, String addressS,
                               String eikS, String ddsS, String bankS, String ibanS, String bicS, String managerS, String placeS, String phoneS) {
        idTF.setText(id);
        nameTF.setText(name);
        if(countryS!=null || countryS!=""){
            country.setText(countryS);}
        if(cityS!=null || cityS!=""){
            city.setText(cityS);}
        if(addressS!=null || addressS!=""){
            address.setText(addressS);}
        if(eikS!=null || eikS!=""){
            EIK.setText(eikS);}
        if(ddsS!=null || ddsS!=""){
            DDS.setText(ddsS);}
        if(bankS!=null || bankS!=""){
            bank.setText(bankS);}
        if(ibanS!=null || ibanS!=""){
            Iban.setText(ibanS);}
        if(bicS!=null || bicS!=""){
            bic.setText(bicS);}
        if(managerS!=null || managerS!=""){
            manager.setText(managerS);}
        if(placeS!=null || placeS!=""){
            place.setText(placeS);}
        if(phoneS!=null || phoneS!=""){
            phone.setText(phoneS);}
    }
    private TableView getTableView(ObservableList<FirmsEntity> observableList, GridPane gridPane) {
        Callback<TableColumn<FirmsEntity, String>, TableCell<FirmsEntity, String>> cellFactory
                = (TableColumn<FirmsEntity, String> param) -> new EditingCell<FirmsEntity>();

        //Column for firms name
        TableColumn<FirmsEntity, String> nameColumn = new TableColumn<>("Наименование");
        nameColumn.setMinWidth(500);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(cellFactory);
        nameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<FirmsEntity, String> t) -> {
                    ((FirmsEntity) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setName(t.getNewValue());

                });

        TableColumn<FirmsEntity, FirmsEntity> actions = new TableColumn<>("Действия");
        actions.setMinWidth(400);
        actions.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actions.setCellFactory(param -> new TableCell<FirmsEntity, FirmsEntity>() {
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
                        FirmsEntity firms = getTableView().getItems().get(getIndex());
                        FirmsService firmsService = new FirmsService();
                        firmsService.delete(firms);
                        getTableView().getItems().remove(firms);
                    }
                });

                editButton.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Вие ще редактирате запис!");
                    alert.setContentText("Желаете ли да продължите?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        FirmsEntity firms = getTableView().getItems().get(getIndex());
                        FirmsService firmsService = new FirmsService();
                        firmsService.update(firms);
                    }
                });

                selectButton.setOnAction(event -> {
                    FirmsEntity firms = getTableView().getItems().get(getIndex());
                    FirmsService firmsService = new FirmsService();
                    firmsService.vis(firms);

                });
            }

            @Override
            protected void updateItem(FirmsEntity item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });


        firmsTableView = new TableView<>();
        firmsTableView.setEditable(true);
        firmsTableView.setItems(observableList);
        firmsTableView.getColumns().addAll(nameColumn, actions);

        return firmsTableView;
    }
}
