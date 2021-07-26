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
import com.app.controller.MotorVehicleController;
import com.app.entity.*;
import com.app.helper.EditingCell;
import com.app.helper.Helpers;
import com.app.helper.MotorVehicleHelper;
import com.app.helper.ViewHelper;
import com.app.service.MotorVehicleService;

import java.text.ParseException;
import java.util.Optional;

public class MotorVehicle {
    private MotorVehicleService motorVehicleService = new MotorVehicleService();
    private MotorVehicleEntity motorVehicle = new MotorVehicleEntity();
    private TableView<MotorVehicleEntity> motorVehicleTableView;
    private ObservableList<MotorVehicleEntity> motorVehicleObservableList;
    Stage subStage;

    static public TextField nameTF;
    static public TextField idTF;
    static public ComboBox yearTF;
    static public TextField euro;
    static public TextField mileage;
    static public TextField frameTF;
    static public ComboBox catAuto;
    static public ComboBox type;
    static public ComboBox brand;
    static public ComboBox model;
    static public ComboBox fuel;
    static public String nameCat;
    static public Integer idCat;
    static public String nameBrand;
    static public Integer idBrand;
    static public RadioButton t1,t2,t3,t4,t5;
    static public Integer status;
    static public String catST, typeST, brandST, modelST, fuelST, yearST;
    static public GridPane gridPane;
    public MotorVehicle() {
        subStage = new Stage();
        subStage.setTitle("МПС");
        Scene scene = new Scene(createmotorVehicleCreateFormPane(), 1500, 700);
        subStage.setScene(scene);
        subStage.show();
    }

    public GridPane createmotorVehicleCreateFormPane() {
        gridPane = new GridPane();

        MotorVehicleHelper.setGridPane(gridPane);
        addUIControls(gridPane);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Моторно превозно средство (МПС)");
        headerLabel.setFont(new Font("Arial", 26));

        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        //motorVehicle name label and text
        MotorVehicleHelper.addLabel(gridPane, "№: ", 0, 1);
        idTF = MotorVehicleHelper.addTextField(gridPane, "", 1, 1);
        //ValidationHelper.lettersOnly(nameTF, 200);
        //motorVehicle name label and text
        MotorVehicleHelper.addLabel(gridPane, "Регистрационен номер: ", 0, 2);
        nameTF = MotorVehicleHelper.addTextField(gridPane, "", 1, 2);
        //ValidationHelper.lettersOnly(nameTF, 200);

        catAuto = ViewHelper.getComboBox(gridPane, 0, 3);

        catAuto.setOnAction(e ->{
            nameCat = String.valueOf(catAuto.getValue());
            MotorVehicleService.getExistingMotorVehicleIdCat(nameCat);

            type = ViewHelper.getComboBoxType(gridPane, 0, 4, idCat);
            brand = ViewHelper.getComboBoxBrand(gridPane, 0, 5, idCat);
            type.setDisable(false);
            brand.setDisable(false);

            brand.setOnAction(event -> {
                nameBrand = String.valueOf(brand.getValue());
                MotorVehicleService.getExistingMotorVehicleIdBrand(nameBrand);

                model = ViewHelper.getComboBoxModel(gridPane, 0, 6, idBrand);
                model.setDisable(false);

            });
        });

        idCat = 1;
        type = ViewHelper.getComboBoxType(gridPane, 0, 4, idCat);
        type.setDisable(true);

        brand = ViewHelper.getComboBoxBrand(gridPane, 0, 5, idCat);
        brand.setDisable(true);
        /*brand.setOnAction(event -> {
            //MotorVehicleHelper.comboView(gridPane, brand.getValue());
            nameBrand = String.valueOf(brand.getValue());
            MotorVehicleService.getExistingMotorVehicleIdBrand(nameBrand);

            model = ViewHelper.getComboBoxModel(gridPane, 0, 6, idBrand);
            model.setDisable(false);

        });*/

        model = ViewHelper.getComboBoxModel(gridPane, 0, 6);
        model.setDisable(true);

        fuel = ViewHelper.getComboBoxFuel(gridPane, 0, 7);


        MotorVehicleHelper.addLabel(gridPane, "Шаси: ", 0, 8);
        frameTF = MotorVehicleHelper.addTextField(gridPane, "", 1, 8);


        //MotorVehicleHelper.addLabel(gridPane, "Година производство: ", 0, 2);
        //yearTF = MotorVehicleHelper.addTextField(gridPane, "", 1, 2);
        yearTF = ViewHelper.getComboBoxYear(gridPane, 0, 9);
        //gridPane.add(new YearChooser(), 1 , 1);

        MotorVehicleHelper.addLabel(gridPane, "EURO: ", 0, 10);
        euro = MotorVehicleHelper.addTextField(gridPane, "", 1, 10);


        MotorVehicleHelper.addLabel(gridPane, "Навъртяни километри: ", 0, 11);
        mileage = MotorVehicleHelper.addTextField(gridPane, "", 1, 11);

        t1=new RadioButton("Без статус");
        //t1.setBounds(100,50,100,30);
        t2=new RadioButton("Продаден");
        t3=new RadioButton("Даден под наем");
        t4=new RadioButton("Собственост на фирмата");
        t5=new RadioButton("Частен");
        //t2.setBounds(100,100,100,30);
        t1.setSelected(true);
        ViewHelper.RadioButton(gridPane, t1, t2, t3, t4, t5,"",0,12);

        //TextField searchBoxTF = new TextField();
        Button editButton = new Button("Нов");
        Button addButton = new Button("Запис");
        Button exitButton = new Button("Изход");

        MotorVehicleHelper.initSearchSection(gridPane, editButton, addButton, exitButton,
                "", 1, 13);

        gridPane.add(getTableView(getmotorVehicleInfo(false, null), gridPane),2,1, 1, 11);
        MotorVehicleController.show();

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
                        status = 5;
                    }
                  comboBoxValue(catAuto, brand, model, fuel, type, yearTF);
                onSubmit(gridPane, motorVehicle, idTF, nameTF, catST, typeST, brandST,
                        modelST, fuelST, yearST, euro, mileage, frameTF, status);
                gridPane.add(getTableView(getmotorVehicleInfo(false, null), gridPane),2,1, 1, 11);
            } catch (ParseException ex) {
                // LOGGER.error("Exception in submit button set on action: ", ex.getStackTrace());
            }
        });
        editButton.setOnAction(e -> {
            MotorVehicleHelper.clearForm(gridPane, motorVehicle, idTF, nameTF);
            MotorVehicleController.idNum();
            catAuto = ViewHelper.getComboBox(gridPane, 0, 3);
            catAuto.setValue("");
            type.setValue("");
            brand.setValue("");
            model.setValue("");
            fuel.setValue("");
            yearTF.setValue("");
            t1.setSelected(true);
            frameTF.clear();
            euro.clear();
            mileage.clear();

            catAuto.setOnAction(ee ->{
                nameCat = String.valueOf(catAuto.getValue());
                MotorVehicleService.getExistingMotorVehicleIdCat(nameCat);

                type = ViewHelper.getComboBoxType(gridPane, 0, 4, idCat);
                brand = ViewHelper.getComboBoxBrand(gridPane, 0, 5, idCat);
                type.setDisable(false);
                brand.setDisable(false);

                brand.setOnAction(event -> {
                    nameBrand = String.valueOf(brand.getValue());
                    MotorVehicleService.getExistingMotorVehicleIdBrand(nameBrand);

                    model = ViewHelper.getComboBoxModel(gridPane, 0, 6, idBrand);
                    model.setDisable(false);

                });
            });
        });
        exitButton.setOnAction(e -> {
            subStage.close();
        });
    }
    private void comboBoxValue(ComboBox cat, ComboBox brandC, ComboBox modelC, ComboBox fuelC, ComboBox typeC, ComboBox yearC){
        //catST = cat.getValue().toString();
        //brandST = brandC.getValue().toString();
        //modelST = modelC.getValue().toString();
        //fuelST = fuelC.getValue().toString();
        //typeST = typeC.getValue().toString();
        //yearST = yearC.getValue().toString();
        if(cat.getValue()==null || cat.getValue().toString()==""){
            catST = "";
        }else{catST = cat.getValue().toString();}
        if(brandC.getValue()==null || brandC.getValue().toString()==""){
            brandST = "";
        }else{brandST = brandC.getValue().toString();}
        if(modelC.getValue()==null || modelC.getValue().toString()==""){
            modelST = "";
        }else{modelST = modelC.getValue().toString();}
        if(fuelC.getValue()==null || fuelC.getValue().toString()==""){
            fuelST = "";
        }else{fuelST = fuelC.getValue().toString();}
        if(typeC.getValue()==null || typeC.getValue().toString()==""){
            typeST = "";
        }else{typeST = typeC.getValue().toString();}
        if(yearC.getValue()==null || yearC.getValue().toString()==""){
            yearST = "";
        }else{yearST = yearC.getValue().toString();}
    }
    private void onSubmit(GridPane gridPane, MotorVehicleEntity MotorVehicle, TextField idTF, TextField nameTF, String catAuto,
                          String type, String brand, String model, String fuel, String yearTF,
                          TextField euro,TextField mileage,TextField frame, int statusText) throws ParseException {
        if (nameTF.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Не е попълнен регистрационния номер!");
            return;
        }
        if (idTF.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Няма индификатор!");
            return;
        }

        createMotorVehicle(nameTF.getText(), Integer.parseInt(idTF.getText()), catAuto, type,
                brand, model, fuel, Integer.parseInt(yearTF),
                Integer.parseInt(euro.getText()), Double.parseDouble(mileage.getText()), frame.getText(), statusText);
        Helpers.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(),
                "Записа успешен!", "Успешно записахте данните на тип/купе на МПС-то!");
    }


    private void createMotorVehicle(String name, int id, String id_cat, String id_type,String id_brand,String id_model,String id_fuel,
                                    int year, int euro, double mileage, String frame, Integer statusTX) {
        MotorVehicleService MotorVehicleService = new MotorVehicleService();

        MotorVehicleEntity MotorVehicle = new MotorVehicleEntity();
        MotorVehicle.setId(id);
        MotorVehicle.setRegPlate(name);
        MotorVehicle.setName(name);
        MotorVehicle.setIdCat(MotorVehicleService.getExistingMotorVehicleIdCat(id_cat));
        MotorVehicle.setIdType(MotorVehicleService.getExistingMotorVehicleIdType(id_type));
        MotorVehicle.setIdBrand(MotorVehicleService.getExistingMotorVehicleIdBrand(id_brand));
        MotorVehicle.setIdModel(MotorVehicleService.getExistingMotorVehicleIdModel(id_model));
        MotorVehicle.setIdFuel(MotorVehicleService.getExistingMotorVehicleIdFuel(id_fuel));
        MotorVehicle.setYear(year);
        MotorVehicle.setEURO(euro);
        MotorVehicle.setMileage(mileage);
        MotorVehicle.setFrame(frame);
        MotorVehicle.setStatus(statusTX);

        MotorVehicleService.getById(MotorVehicle);
    }

    public static void visible(String name, String id, String id_combo, String id_type, String id_brand, String id_model,
                               String id_fuel, String year, String frame, String euro1, String statusST, String mileageSt) {

        CatAutoEntity motorVehicle = new CatAutoEntity();
        nameTF.setText(name);
        idTF.setText(id);
        //catAuto.setPromptText(id_combo);
        catAuto.setValue(id_combo);
        type.setValue(id_type);
        model.setValue(id_model);
        brand.setValue(id_brand);
        brand.setPromptText(id_brand);
        fuel.setValue(id_fuel);
        yearTF.setValue(year);
        frameTF.setText(frame);
        euro.setText(euro1);
        mileage.setText(mileageSt);
        int statusRB = Integer.parseInt(statusST);

        if(statusRB == 1){
            t1.setSelected(true);
        }
        if(statusRB == 2){
            t2.setSelected(true);
        }
        if(statusRB == 3){
            t3.setSelected(true);
        }
        if(statusRB == 4){
            t4.setSelected(true);
        }
        if(statusRB == 5){
            t5.setSelected(true);
        }

    }
    public static void idNumber(String id) {
        idTF.setText(id);

        if(id=="0" || id==null){
            idTF.setText("1");
        }

    }
    public static void idNumberCat(String id) {
        //idTF.setText(id);
        idCat = Integer.valueOf(id);
        if(id=="0" || id==null){
            //idTF.setText("1");
            idCat = 0;
        }

    }
    public static void idNumberBrand(String id) {
        //idTF.setText(id);
        idBrand = Integer.valueOf(id);
        if(id=="0" || id==null){
            //idTF.setText("1");
            idBrand = 0;
        }

    }
    private ObservableList<MotorVehicleEntity> getmotorVehicleInfo(boolean ismotorVehicleSearched, String criteria) {
        motorVehicleObservableList = FXCollections.observableArrayList();

        if (ismotorVehicleSearched) {
            for (MotorVehicleEntity motorVehicle : motorVehicleService.getSearched(MotorVehicleHelper.capitalizeFirstLetter(criteria))) {
                motorVehicleObservableList.add(motorVehicle);
            }
        } else {
            for (MotorVehicleEntity motorVehicle : motorVehicleService.getAll()) {
                motorVehicleObservableList.add(motorVehicle);
            }
        }

        return motorVehicleObservableList;
    }
    private TableView getTableView(ObservableList<MotorVehicleEntity> observableList, GridPane gridPane) {
        Callback<TableColumn<MotorVehicleEntity, String>, TableCell<MotorVehicleEntity, String>> cellFactory
                = (TableColumn<MotorVehicleEntity, String> param) -> new EditingCell<MotorVehicleEntity>();

        //Column for motorVehicle name
        TableColumn<MotorVehicleEntity, String> nameColumn = new TableColumn<>("Наименование");
        nameColumn.setMinWidth(500);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(cellFactory);
        nameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<MotorVehicleEntity, String> t) -> {
                    ((MotorVehicleEntity) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setName(t.getNewValue());

                });

        TableColumn<MotorVehicleEntity, MotorVehicleEntity> actions = new TableColumn<>("Действия");
        actions.setMinWidth(400);
        actions.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actions.setCellFactory(param -> new TableCell<MotorVehicleEntity, MotorVehicleEntity>() {
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
                        MotorVehicleEntity motorVehicle = getTableView().getItems().get(getIndex());
                        MotorVehicleService motorVehicleService = new MotorVehicleService();
                        motorVehicleService.delete(motorVehicle);
                        getTableView().getItems().remove(motorVehicle);
                    }
                });

                editButton.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Вие ще редактирате запис!");
                    alert.setContentText("Желаете ли да продължите?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        MotorVehicleEntity motorVehicle = getTableView().getItems().get(getIndex());
                        MotorVehicleService motorVehicleService = new MotorVehicleService();
                        motorVehicleService.update(motorVehicle);
                    }
                });

                selectButton.setOnAction(event -> {
                    MotorVehicleEntity motorVehicle = getTableView().getItems().get(getIndex());
                    MotorVehicleService motorVehicleService = new MotorVehicleService();
                    motorVehicleService.vis(motorVehicle);

                });
            }

            @Override
            protected void updateItem(MotorVehicleEntity item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });


        motorVehicleTableView = new TableView<>();
        motorVehicleTableView.setEditable(true);
        motorVehicleTableView.setItems(observableList);
        motorVehicleTableView.getColumns().addAll(nameColumn, actions);

        return motorVehicleTableView;
    }
}
