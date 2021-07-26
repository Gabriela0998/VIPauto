package com.app.view;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import com.app.controller.CostController;
import com.app.entity.CatAutoEntity;
import com.app.entity.CostEntity;
import com.app.helper.CostHelper;
import com.app.helper.Helpers;
import com.app.helper.ViewHelper;
import com.app.service.CostService;

import java.text.ParseException;
import java.util.List;

public class Cost {
    private CostService costService = new CostService();
    private CostEntity cost = new CostEntity();
    private TableView<CostEntity> costTableView;
    private ObservableList<CostEntity> costObservableList;
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

    //tpl
    static public TextField idTpl, sumTpl, numberPolTpl;
    static public DatePicker dateFromTpl, dateToTpl, date1Tpl, date1Tp2, date1Tp3, date1Tp4;
    static public ComboBox countTpl, insCompTpl;

    //tp2
    static public TextField idTpl2, sumTpl2, numberPolTpl2;
    static public DatePicker dateFromTpl2, dateToTpl2, date1Tpl2, date1Tp22, date1Tp32, date1Tp42;
    static public ComboBox countTpl2, insCompTpl2;

    //tp3
    static public TextField idTp3, sumTp3, nameTp3;
    static public DatePicker dateFromTp3, dateToTp3;

    static public ComboBox cat;
    static public ComboBox typeCB;
    static public ComboBox brand;
    static public ComboBox model;
    static public ComboBox car;
    static public Integer idCat, idBrand, idModel;

    static public String catST, typeST, brandST, modelST, fuelST, yearST;
    static public GridPane gridPane;
    public Cost() {
        subStage = new Stage();
        subStage.setTitle("Застраховки, такси и други разходи");
        Scene scene = new Scene(createcostCreateFormPane(), 1500, 700);
        subStage.setScene(scene);
        subStage.show();
    }

    public GridPane createcostCreateFormPane() {
        gridPane = new GridPane();
        GridPane gridPaneTab = new GridPane();
        GridPane gridPaneTab2 = new GridPane();
        GridPane gridPaneTab3 = new GridPane();

        CostHelper.setGridPane(gridPane);
        CostHelper.setGridPane(gridPaneTab);
        CostHelper.setGridPane(gridPaneTab2);
        CostHelper.setGridPane(gridPaneTab3);
        addUIControls(gridPane, gridPaneTab, gridPaneTab2, gridPaneTab3);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane, GridPane gridPaneTab, GridPane gridPaneTab2, GridPane gridPaneTab3) {
        Label headerLabel = new Label("Застраховки, такси и други разходи");
        headerLabel.setFont(new Font("Arial", 26));

        gridPane.add(headerLabel, 0, 0, 2, 1);
        Button viewButton = new Button("Избор");
        gridPane.add(viewButton, 5, 0);
        viewButton.setOnAction(event -> {subStage.close(); new SelectPeople();});
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        t1=new RadioButton("Фирмен");
        t2=new RadioButton("Под наем");
        t3=new RadioButton("Продаден");
        t4=new RadioButton("Бракуван");
        t1.setSelected(true);
        ViewHelper.RadioButtonV(gridPane, t1, t2, t3, t4,"Статус","",4,1);


        cat = ViewHelper.getComboBox(gridPane, 0, 1);

        idCat = 0;
        idBrand = 0;
        idModel = 0;
        typeCB = ViewHelper.getComboBoxType(gridPane, 2, 1, idCat);
        typeCB.setDisable(true);

        brand = ViewHelper.getComboBoxBrand(gridPane, 0, 2, idCat);
        brand.setDisable(true);

        model = ViewHelper.getComboBoxModel(gridPane, 2, 2, idBrand);
        model.setDisable(true);

        car = ViewHelper.getComboBoxCar(gridPane, 0, 3, idBrand, idCat, idModel);
        car.setDisable(true);

        BorderPane root = new BorderPane();
        TabPane tabpane = new TabPane();
        tabpane.setBackground(new Background(new BackgroundFill(Color.rgb(201, 230, 203), CornerRadii.EMPTY, Insets.EMPTY)));

        Tab tab = new Tab("Гражданска");
        //Ред Tab - Дата на издаване и др.
        CostHelper.addLabel(gridPaneTab, "Дата на плащане: ", 0, 1);
        dateFromTpl = CostHelper.addDatePicker(gridPaneTab, "", 1, 1);

        CostHelper.addLabel(gridPaneTab, "Платено до: ", 2, 1);
        dateToTpl = CostHelper.addDatePicker(gridPaneTab, "", 3, 1);

        //
        CostHelper.addLabel(gridPaneTab, "Номер на полицата: ", 0, 2);
        numberPolTpl = CostHelper.addTextField(gridPaneTab, "", 1, 2);

        countTpl = ViewHelper.getComboBoxCount(gridPaneTab, 0, 3);

        insCompTpl = ViewHelper.getComboBoxInsCom(gridPaneTab, 2, 2);

        CostHelper.addLabel(gridPaneTab, "Сума: ", 2, 3);
        numberPolTpl = CostHelper.addTextField(gridPaneTab, "", 3, 3);

        tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        gridPaneTab.setBackground(new Background(new BackgroundFill(Color.rgb(213, 216, 230), CornerRadii.EMPTY, Insets.EMPTY)));
        tab.setContent(gridPaneTab);

        Tab tab2 = new Tab("Автокаско");
        tab2.setDisable(false);

        CostHelper.addLabel(gridPaneTab2, "Дата на плащане: ", 0, 1);
        dateFromTpl2 = CostHelper.addDatePicker(gridPaneTab2, "", 1, 1);

        CostHelper.addLabel(gridPaneTab2, "Платено до: ", 2, 1);
        dateToTpl2 = CostHelper.addDatePicker(gridPaneTab2, "", 3, 1);

        //
        CostHelper.addLabel(gridPaneTab2, "Номер на полицата: ", 0, 2);
        numberPolTpl2 = CostHelper.addTextField(gridPaneTab2, "", 1, 2);

        countTpl2 = ViewHelper.getComboBoxCount(gridPaneTab2, 0, 3);

        insCompTpl2 = ViewHelper.getComboBoxInsCom(gridPaneTab2, 2, 2);

        CostHelper.addLabel(gridPaneTab2, "Сума: ", 2, 3);
        numberPolTpl2 = CostHelper.addTextField(gridPaneTab2, "", 3, 3);

        gridPaneTab2.setBackground(new Background(new BackgroundFill(Color.rgb(201, 230, 203), CornerRadii.EMPTY, Insets.EMPTY)));
        tab2.setContent(gridPaneTab2);

        Tab tab3 = new Tab("Технически преглед");
        tab3.setDisable(false);

        CostHelper.addLabel(gridPaneTab3, "Дата на тех. преглед: ", 0, 1);
        dateFromTp3 = CostHelper.addDatePicker(gridPaneTab3, "", 1, 1);

        CostHelper.addLabel(gridPaneTab3, "До дата: ", 2, 1);
        dateToTp3 = CostHelper.addDatePicker(gridPaneTab3, "", 3, 1);

        //
        CostHelper.addLabel(gridPaneTab3, "Име на сервиз: ", 0, 2);
        nameTp3 = CostHelper.addTextField(gridPaneTab3, "", 1, 2);

        CostHelper.addLabel(gridPaneTab3, "Сума: ", 0, 3);
        nameTp3 = CostHelper.addTextField(gridPaneTab3, "", 1, 3);

        gridPaneTab3.setBackground(new Background(new BackgroundFill(Color.rgb(227, 219, 236), CornerRadii.EMPTY, Insets.EMPTY)));
        tab3.setContent(gridPaneTab3);

        Tab tab4 = new Tab("Други такси и разходи");
        tab4.setDisable(true);
        tabpane.getTabs().add(tab);
        tabpane.getTabs().add(tab2);
        tabpane.getTabs().add(tab3);
        tabpane.getTabs().add(tab4);
        //root.setCenter();
        gridPane.add(tabpane,0,7, 6,5);

        //TextField searchBoxTF = new TextField();
        Button editButton = new Button("Нов");
        Button addButton = new Button("Запис");
        Button exitButton = new Button("Изход");

        CostHelper.initSearchSection(gridPane, editButton, addButton, exitButton,
                "", 3, 13);

        //gridPane.add(getTableView(getcostInfo(false, null), gridPane),2,1, 1, 14);
        CostController.show();
        cat.setOnAction(event -> {
            CatComboClick();
        });
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
                  //comboBoxValue(catAuto, brand, model, fuel, type, yearTF);idTF,
                CostController.idNum();
                onSubmit(gridPane, car, status, typeCB);
                //gridPane.add(getTableView(getcostInfo(false, null), gridPane),2,1, 1, 14);
            } catch (ParseException ex) {
                // LOGGER.error("Exception in submit button set on action: ", ex.getStackTrace());
            }
        });
        editButton.setOnAction(e -> {
            /*CostHelper.clearForm(gridPane, cost, idTF, nameTF);
            CostController.idNum();
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
                CostService.getExistingCostIdCat(nameCat);

                type = ViewHelper.getComboBoxType(gridPane, 0, 4, idCat);
                brand = ViewHelper.getComboBoxBrand(gridPane, 0, 5, idCat);
                type.setDisable(false);
                brand.setDisable(false);

                brand.setOnAction(event -> {
                    nameBrand = String.valueOf(brand.getValue());
                    CostService.getExistingCostIdBrand(nameBrand);

                    model = ViewHelper.getComboBoxModel(gridPane, 0, 6, idBrand);
                    model.setDisable(false);

                });
            });*/
        });
        exitButton.setOnAction(e -> {
            subStage.close();
        });
    }

    private void onSubmit(GridPane gridPane, ComboBox carN,int status, ComboBox typeCB2) throws ParseException {
        if (carN.getValue().toString().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Не е попълнен регистрационния номер!");
            return;
        }
        /*if (id==null) {
            id=1;
        }*/

        createCost(carN.getValue().toString(), status, typeCB2.getValue().toString());
        Helpers.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(),
                "Записа успешен!", "Успешно записахте данните!");
    }




    private void createCost(String id_car2, Integer status, String id_type) {
        CostService CostService = new CostService();

        CostEntity Cost = new CostEntity();
        Cost.setId(idTF);
        Cost.setIdCar(CostService.getExistingCostIdCar(id_car2));
        Cost.setIdType(CostService.getExistingCostIdType(id_type));
        Cost.setStatus(status);
        CostService.getById(Cost);
    }

    public static void visible( String id, String id_combo, String id_type, String id_brand, String id_model,
                               String id_car, String statusST) {

        CatAutoEntity cost = new CatAutoEntity();
        //nameTF.setText(name);
        idTF = Integer.valueOf(id);
        //catAuto.setPromptText(id_combo);
        cat.setValue(id_combo);
        CatComboClick();

        typeCB.setValue(id_type);
        brand.setValue(id_brand);
        BrandComboClick();
        model.setValue(id_model);
        car.setValue(id_car);
        brand.setPromptText(id_brand);
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

        if (idTF==null) {
            if (id == null) {
                idTF = 1;
            } else {
                idTF = Integer.valueOf(id);
            }
        }
    }
    static public void CatComboClick(){

        String nameCat = String.valueOf(cat.getValue());
        CostService.getExistingCostIdCat(nameCat);

        typeCB = ViewHelper.getComboBoxType(gridPane, 2, 1, idCat);

        brand = ViewHelper.getComboBoxBrand(gridPane, 0, 2, idCat);

        typeCB.setDisable(false);
        brand.setDisable(false);
        brand.setOnAction(event -> {BrandComboClick();});
    }

    static public void BrandComboClick(){
        String nameBrand = String.valueOf(brand.getValue());
        CostService.getExistingCostIdBrand(nameBrand);

        model = ViewHelper.getComboBoxModel(gridPane, 2, 2, idBrand);
        model.setDisable(false);
        model.setOnAction(event -> {CarComboClick();});
    }
    static public void CarComboClick(){
        String nameCat = String.valueOf(cat.getValue());
        CostService.getExistingCostIdCat(nameCat);

        String nameBrand = String.valueOf(brand.getValue());
        CostService.getExistingCostIdBrand(nameBrand);

        String nameModel = String.valueOf(model.getValue());
        CostService.getExistingCostIdModel(nameModel);

        car = ViewHelper.getComboBoxCar(gridPane, 0, 3, idBrand, idCat, idModel);
        car.setDisable(false);

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
    public static void idNumberModel(String id) {
        //idTF.setText(id);
        idModel = Integer.valueOf(id);
        if(id=="0" || id==null){
            //idTF.setText("1");
            idModel = 0;
        }
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
}
