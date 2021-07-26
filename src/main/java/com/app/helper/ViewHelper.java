package com.app.helper;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.app.entity.*;
import com.app.service.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ViewHelper {

    public static void setGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(15, 40, 40, 40));

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints columnOneConstraints = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
    }
    public static void setGridPaneSelectPeople(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(15, 20, 20, 20));

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints columnOneConstraints = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
    }

    public static void initSearchSection(GridPane gridPane, Button submitButton, Button exitTableButton,
                                         String promptText, int colIndex, int rowindex) {
        //GridPane.setHalignment(submitButton, HPos.CENTER);
        //GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        HBox searchBox = getHBox();

        exitTableButton.setPrefWidth(120);
        submitButton.setPrefWidth(120);

        searchBox.getChildren().addAll( submitButton,exitTableButton);
        gridPane.add(searchBox, colIndex, rowindex);
    }
    public static void initSearchSection(GridPane gridPane, TextField searchBoxTF, Button searchButton, Button resetTableButton,
                                         String promptText, int colIndex, int rowindex) {
        HBox searchBox = getHBox();

        //Search text field
        searchBoxTF.setPromptText(promptText);
        searchBoxTF.setPrefWidth(300);

        searchButton.setPrefWidth(80);

        resetTableButton.setPrefWidth(120);

        searchBox.getChildren().addAll(searchBoxTF, searchButton, resetTableButton);
        gridPane.add(searchBox, colIndex, rowindex,2,1);
    }
    public static void RadioButton(GridPane gridPane, RadioButton type1, RadioButton type2,
                                   String promptText, int colIndex, int rowindex) {
        //GridPane.setHalignment(submitButton, HPos.CENTER);
        //GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        HBox searchBox = getHBox();

        type1.setPrefWidth(120);
        type2.setPrefWidth(120);

        final ToggleGroup tg = new ToggleGroup();
        type1.setToggleGroup(tg);
        type2.setToggleGroup(tg);

        searchBox.getChildren().addAll( type1,type2);
        gridPane.add(searchBox, colIndex, rowindex);
    }
    public static void RadioButton(GridPane gridPane, RadioButton type1, RadioButton type2, RadioButton type3, RadioButton type4, RadioButton type5,
                                   String promptText, int colIndex, int rowindex) {
        //GridPane.setHalignment(submitButton, HPos.CENTER);
        //GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        Label label = new Label("Статус: ");
        gridPane.add(label, colIndex, rowindex);

        HBox searchBox = getHBox();

        type1.setPrefWidth(120);
        type2.setPrefWidth(120);

        final ToggleGroup tg = new ToggleGroup();
        type1.setToggleGroup(tg);
        type2.setToggleGroup(tg);
        type3.setToggleGroup(tg);
        type4.setToggleGroup(tg);
        type5.setToggleGroup(tg);

        searchBox.getChildren().addAll( type1,type2,type3,type4,type5);
        gridPane.add(searchBox, colIndex+1, rowindex, 6,1);
    }
    public static void RadioButton5(GridPane gridPane, RadioButton type1, RadioButton type2, RadioButton type3, RadioButton type4, RadioButton type5,
                                   String promptText, int colIndex, int rowindex) {
        //GridPane.setHalignment(submitButton, HPos.CENTER);
        //GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        Label label = new Label("Вид лице: ");
        gridPane.add(label, colIndex, rowindex);

        HBox searchBox = getHBox();

        type1.setPrefWidth(120);
        type2.setPrefWidth(120);

        final ToggleGroup tg = new ToggleGroup();
        type1.setToggleGroup(tg);
        type2.setToggleGroup(tg);
        type3.setToggleGroup(tg);
        type4.setToggleGroup(tg);
        type5.setToggleGroup(tg);

        searchBox.getChildren().addAll( type1,type2,type3,type4,type5);
        gridPane.add(searchBox, colIndex+1, rowindex, 6,1);
    }
    public static void RadioButton(GridPane gridPane, RadioButton type1, RadioButton type2, RadioButton type3, RadioButton type4, String labelName,
                                   String promptText, int colIndex, int rowindex) {
        //GridPane.setHalignment(submitButton, HPos.CENTER);
        //GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        Label label = new Label(labelName);
        gridPane.add(label, colIndex, rowindex);

        HBox searchBox = getHBox();

        type1.setPrefWidth(120);
        type2.setPrefWidth(120);

        final ToggleGroup tg = new ToggleGroup();
        type1.setToggleGroup(tg);
        type2.setToggleGroup(tg);
        type3.setToggleGroup(tg);
        type4.setToggleGroup(tg);

        searchBox.getChildren().addAll( type1,type2,type3,type4);
        gridPane.add(searchBox, colIndex+1, rowindex, 3,1);
    }
    public static void RadioButtonV(GridPane gridPane, RadioButton type1, RadioButton type2, RadioButton type3, RadioButton type4, String labelName,
                                    String promptText, int colIndex, int rowindex) {
        //GridPane.setHalignment(submitButton, HPos.CENTER);
        //GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        Label label = new Label(labelName);
        gridPane.add(label, colIndex, rowindex);

        VBox searchBox = getVBox();

        type1.setPrefWidth(120);
        type2.setPrefWidth(120);

        final ToggleGroup tg = new ToggleGroup();
        type1.setToggleGroup(tg);
        type2.setToggleGroup(tg);
        type3.setToggleGroup(tg);
        type4.setToggleGroup(tg);

        searchBox.getChildren().addAll( type1,type2,type3,type4);
        gridPane.add(searchBox, colIndex, rowindex+1, 1,4);
    }
    public static void RadioButton(GridPane gridPane, RadioButton type1, RadioButton type2, RadioButton type3,
                                   String promptText, int colIndex, int rowindex) {
        //GridPane.setHalignment(submitButton, HPos.CENTER);
        //GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        Label label = new Label("Статус на лицето: ");
        gridPane.add(label, colIndex, rowindex);

        HBox searchBox = getHBox();

        type1.setPrefWidth(120);
        type2.setPrefWidth(120);

        final ToggleGroup tg = new ToggleGroup();
        type1.setToggleGroup(tg);
        type2.setToggleGroup(tg);
        type3.setToggleGroup(tg);

        searchBox.getChildren().addAll( type1,type2,type3);
        gridPane.add(searchBox, colIndex+1, rowindex, 3,1);
    }

    public static Label addLabel(GridPane gridPane, String labelText, int colIndex, int rowIndex) {
        Label label = new Label(labelText);
        gridPane.add(label, colIndex, rowIndex);

        return label;
    }

    public static TextField addTextField(GridPane gridPane, String promptText, int colIndex, int rowIndex) {
        TextField textField = new TextField();
        textField.setPrefHeight(40);
        textField.setPromptText("" + promptText);
        gridPane.add(textField, colIndex, rowIndex);

        return textField;
    }

    public static PasswordField addPassField(GridPane gridPane, String promptText, int colIndex, int rowIndex) {
        PasswordField passField = new PasswordField();
        passField.setPrefHeight(40);
        passField.setPromptText("" + promptText);
        gridPane.add(passField, colIndex, rowIndex);

        return passField;
    }

    public static ComboBox<CatAutoEntity> getComboBox(GridPane gridPane, int colIndex, int rowIndex) {
        Label majorsLabel = new Label("Категория автомобил: ");
        gridPane.add(majorsLabel, colIndex, rowIndex);

        ComboBox<CatAutoEntity> catAuto = new ComboBox();
        catAuto.setPrefHeight(40);
        catAuto.setPrefWidth(200);
        gridPane.add(catAuto, colIndex+1, rowIndex);

        fetchCatAuto(catAuto);

        return catAuto;
    }

    private static void fetchCatAuto(ComboBox catAutoesBox) {
        CatAutoService catAutoService = new CatAutoService();
        int id = 1;
        List<CatAutoEntity> catAutoes = catAutoService.getAll();
        //catAutoesBox.setItems("cc");
        for (CatAutoEntity catAuto : catAutoes) {
            catAutoesBox.getItems().add(catAuto.getName());
        }

        //catAutoesBox.getSelectionModel().getSelectedItem().toString();
    }
    public static ComboBox<FirmsEntity> getComboBoxFirms(GridPane gridPane, int colIndex, int rowIndex) {
        /*Label majorsLabel = new Label("Към Фирма/Организация: ");
        gridPane.add(majorsLabel, colIndex, rowIndex);*/

        ComboBox<FirmsEntity> firms = new ComboBox();
        firms.setPrefHeight(40);
        firms.setPrefWidth(200);
        gridPane.add(firms, colIndex, rowIndex);

        fetchFirms(firms);

        return firms;
    }

    private static void fetchFirms(ComboBox firmsBox) {
        FirmsService firmsService = new FirmsService();
        int id = 1;
        List<FirmsEntity> firms = firmsService.getAll();
        //catAutoesBox.setItems("cc");
        for (FirmsEntity firm : firms) {
            firmsBox.getItems().add(firm.getName());
        }
    }

    public static ComboBox<ModelEntity> getComboBoxModel(GridPane gridPane, int colIndex, int rowIndex) {
        Label majorsLabel = new Label("Модел: ");
        gridPane.add(majorsLabel, colIndex, rowIndex);

        ComboBox<ModelEntity> model = new ComboBox();
        model.setPrefHeight(40);
        model.setPrefWidth(200);
        gridPane.add(model, colIndex+1, rowIndex);

        fetchModel(model);

        return model;
    }

    private static void fetchModel(ComboBox modelsBox) {
        ModelService modelService = new ModelService();
        int id = 1;
        List<ModelEntity> models = modelService.getAll();
        //catAutoesBox.setItems("cc");
        for (ModelEntity model : models) {
            modelsBox.getItems().add(model.getName());
        }

        //catAutoesBox.getSelectionModel().getSelectedItem().toString();
    }
    public static ComboBox<MotorVehicleEntity> getComboBoxCar(GridPane gridPane, int colIndex, int rowIndex, int idBrand, Integer idCat, Integer idModel) {
        Label majorsLabel = new Label("МПС рег. номер: ");
        gridPane.add(majorsLabel, colIndex, rowIndex);

        ComboBox<MotorVehicleEntity> car = new ComboBox();
        car.setPrefHeight(40);
        car.setPrefWidth(200);
        gridPane.add(car, colIndex+1, rowIndex);

        fetchCar(car, idBrand, idCat, idModel);

        return car;
    }

    private static void fetchCar(ComboBox carsBox, int idBrand, Integer idCat, Integer idModel) {
        MotorVehicleService carService = new MotorVehicleService();
        int id = 1;
        List<MotorVehicleEntity> cars = carService.getAll(idBrand, idCat, idModel);
        //catAutoesBox.setItems("cc");
        for (MotorVehicleEntity model : cars) {
            carsBox.getItems().add(model.getName());
        }

        //catAutoesBox.getSelectionModel().getSelectedItem().toString();
    }
    public static ComboBox<String> getComboBoxCount(GridPane gridPane, int colIndex, int rowIndex) {
        Label majorsLabel = new Label("Брой вноски: ");
        gridPane.add(majorsLabel, colIndex, rowIndex);

        ComboBox<String> car = new ComboBox();
        car.setPrefHeight(40);
        car.setPrefWidth(200);
        gridPane.add(car, colIndex+1, rowIndex);

        fetchCount(car);

        return car;
    }
    private static void fetchCount(ComboBox carsBox) {
        int id = 1;

        carsBox.getItems().add("1 вноска");
        carsBox.getItems().add("2 вноски");
        carsBox.getItems().add("3 вноски");
        carsBox.getItems().add("4 вноски");

        //catAutoesBox.getSelectionModel().getSelectedItem().toString();
    }


    public static ComboBox<ModelEntity> getComboBoxModel(GridPane gridPane, int colIndex, int rowIndex, int idBrand) {
        Label majorsLabel = new Label("Модел: ");
        gridPane.add(majorsLabel, colIndex, rowIndex);

        ComboBox<ModelEntity> model = new ComboBox();
        model.setPrefHeight(40);
        model.setPrefWidth(200);
        gridPane.add(model, colIndex+1, rowIndex);

        fetchModel(model, idBrand);

        return model;
    }

    private static void fetchModel(ComboBox modelsBox, int idBrand) {
        ModelService modelService = new ModelService();
       // int id = 1;
        List<ModelEntity> models = modelService.getAll(idBrand);
        //catAutoesBox.setItems("cc");
        for (ModelEntity model : models) {
            modelsBox.getItems().add(model.getName());
        }

        //catAutoesBox.getSelectionModel().getSelectedItem().toString();
    }
    public static ComboBox<FuelEntity> getComboBoxFuel(GridPane gridPane, int colIndex, int rowIndex) {
        Label majorsLabel = new Label("Гориво/Мотор: ");
        gridPane.add(majorsLabel, colIndex, rowIndex);

        ComboBox<FuelEntity> fuel = new ComboBox();
        fuel.setPrefHeight(40);
        fuel.setPrefWidth(200);
        gridPane.add(fuel, colIndex+1, rowIndex);

        fetchFuel(fuel);

        return fuel;
    }

    private static void fetchFuel(ComboBox fuelsBox) {
        FuelService fuelService = new FuelService();
        int id = 1;
        List<FuelEntity> fuels = fuelService.getAll();
        //catAutoesBox.setItems("cc");
        for (FuelEntity fuel : fuels) {
            fuelsBox.getItems().add(fuel.getName());
        }

        //catAutoesBox.getSelectionFuel().getSelectedItem().toString();
    }
    public static ComboBox<InsCompaniesEntity> getComboBoxInsCom(GridPane gridPane, int colIndex, int rowIndex) {
        Label majorsLabel = new Label("Застрахователна компания: ");
        gridPane.add(majorsLabel, colIndex, rowIndex);

        ComboBox<InsCompaniesEntity> companiesEntityComboBox = new ComboBox();
        companiesEntityComboBox.setPrefHeight(40);
        companiesEntityComboBox.setPrefWidth(200);
        gridPane.add(companiesEntityComboBox, colIndex+1, rowIndex);

        fetchInsCom(companiesEntityComboBox);

        return companiesEntityComboBox;
    }

    private static void fetchInsCom(ComboBox fuelsBox) {
        InsCompaniesService insCompaniesService = new InsCompaniesService();
        int id = 1;
        List<InsCompaniesEntity> insCompaniesEntities = insCompaniesService.getAll();
        //catAutoesBox.setItems("cc");
        for (InsCompaniesEntity insCompaniesEntity : insCompaniesEntities) {
            fuelsBox.getItems().add(insCompaniesEntity.getName());
        }

        //catAutoesBox.getSelectionFuel().getSelectedItem().toString();
    }
    public static ComboBox<YearEntity> getComboBoxYear(GridPane gridPane, int colIndex, int rowIndex) {
        Label majorsLabel = new Label("Година на производство: ");
        gridPane.add(majorsLabel, colIndex, rowIndex);

        ComboBox<YearEntity> year = new ComboBox();
        year.setPrefHeight(40);
        year.setPrefWidth(200);
        year.editorProperty();
        gridPane.add(year, colIndex+1, rowIndex);

        fetchYear(year);

        return year;
    }

    private static void fetchYear(ComboBox yearsBox) {
        //YearsService yearService = new YearsService();
        int id = 1;
        //List<String> years = new ArrayList<>();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int maxYear = calendar.get(Calendar.YEAR);
        int minYear = 1960;
        while (minYear<=maxYear) {
            yearsBox.getItems().add(maxYear);
            maxYear = maxYear-1;
        }
        //catAutoesBox.setItems("cc");
        /*for (String year : years) {
            yearsBox.getItems().add(year.getName());
        }*/

        //catAutoesBox.getSelectionFuel().getSelectedItem().toString();
    }
    public static ComboBox<TypeEntity> getComboBoxType(GridPane gridPane, int colIndex, int rowIndex, int id_cat) {
        Label majorsLabel = new Label("Тип/Купе: ");
        gridPane.add(majorsLabel, colIndex, rowIndex);

        ComboBox<TypeEntity> type = new ComboBox();
        type.setPrefHeight(40);
        type.setPrefWidth(200);
        gridPane.add(type, colIndex+1, rowIndex);

        fetchType(type, id_cat);

        return type;
    }

    private static void fetchType(ComboBox typesBox, int id) {
        TypeService typeService = new TypeService();
        //int id = 1;
        List<TypeEntity> types = typeService.getAll(id);
        //catAutoesBox.setItems("cc");
        for (TypeEntity type : types) {
            typesBox.getItems().add(type.getName());
        }

        //catAutoesBox.getSelectionModel().getSelectedItem().toString();
    }
    public static ComboBox<BrandEntity> getComboBoxBrand(GridPane gridPane, int colIndex, int rowIndex, int id_cat) {
        Label majorsLabel = new Label("Марка: ");
        gridPane.add(majorsLabel, colIndex, rowIndex);

        ComboBox<BrandEntity> brand = new ComboBox();
        brand.setPrefHeight(40);
        brand.setPrefWidth(200);
        gridPane.add(brand, colIndex+1, rowIndex);

        fetchBrand(brand, id_cat);

        return brand;
    }
    public static ComboBox<BrandEntity> getComboBoxBrand(GridPane gridPane, int colIndex, int rowIndex) {
        Label majorsLabel = new Label("Марка: ");
        gridPane.add(majorsLabel, colIndex, rowIndex);

        ComboBox<BrandEntity> brand = new ComboBox();
        brand.setPrefHeight(40);
        brand.setPrefWidth(200);
        gridPane.add(brand, colIndex+1, rowIndex);

        fetchBrand(brand);

        return brand;
    }
    public static ComboBox<BrandEntity> getComboBoxBrandGrid(GridPane gridPane, int colIndex, int rowIndex) {
        ComboBox<BrandEntity> brand = new ComboBox();
        fetchBrand(brand);
        return brand;
    }

    public static void fetchBrand(ComboBox brandBox) {
        BrandService brandService = new BrandService();
        //int id = 1;
        List<BrandEntity> brands = brandService.getAll();
        //catAutoesBox.setItems("cc");
        for (BrandEntity brand : brands) {
            brandBox.getItems().add(brand.getName());
        }

        //catAutoesBox.getSelectionModel().getSelectedItem().toString();
    }

    public static void fetchBrand(ComboBox brandBox, int id) {
        BrandService brandService = new BrandService();
        //int id = 1;
        List<BrandEntity> brands = brandService.getAll(id);
        //catAutoesBox.setItems("cc");
        for (BrandEntity brand : brands) {
            brandBox.getItems().add(brand.getName());
        }

        //catAutoesBox.getSelectionModel().getSelectedItem().toString();
    }
    public static void CheckBox(GridPane gridPane, CheckBox type0, CheckBox type1, CheckBox type2, CheckBox type3, CheckBox type4, CheckBox type5,
                                CheckBox type6, CheckBox type7, CheckBox type8, CheckBox type9, CheckBox type10,
                                CheckBox type11, CheckBox type12, CheckBox type13, CheckBox type14, CheckBox type15,
                                CheckBox type16, CheckBox type17, String promptText, int colIndex, int rowindex) {
        //GridPane.setHalignment(submitButton, HPos.CENTER);
        //GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        Label label = new Label("Категория: ");
        gridPane.add(label, colIndex, rowindex);

        HBox searchBox = getHBox();

        TilePane r = new TilePane();
        r.getChildren().add(type1);

        searchBox.getChildren().addAll(type0, type1,type2,type3,type4,type5, type6, type7, type8, type9, type10, type11, type12, type13, type14, type15, type16, type17);
        gridPane.add(searchBox, colIndex+1, rowindex, 6,1);
    }

    public static Button getSubmitButton(GridPane gridPane, int colIndex, int rowIndex) {
        Button submitButton = new Button("Submit");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, colIndex, rowIndex, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        return submitButton;
    }

    //SubjectView clear form
    public static void clearForm(TextField nameTF, TextField codeTF, ComboBox majors) {
        nameTF.clear();
        codeTF.clear();
        majors.setValue(null);
    }

    //StudentView clear form
    public static void clearForm(TextField fNameField, TextField lNameField, TextField dateField,
                                 TextField degreeField, ComboBox majors) {
        fNameField.clear();
        lNameField.clear();
        dateField.clear();
        degreeField.clear();
        majors.setValue(null);
    }

    //FacultyView clear form
    public static void clearForm(TextField nameTF, TextField codeTF) {
        nameTF.clear();
        codeTF.clear();
    }

    public static HBox getHBox() {
        HBox searchBox = new HBox();
        searchBox.setSpacing(10);

        return searchBox;
    }
    public static VBox getVBox() {
        VBox searchBox = new VBox();
        searchBox.setSpacing(10);

        return searchBox;
    }

    public static String capitalizeFirstLetter(String input) {
        return input.substring(0, 1).toUpperCase().concat(input.substring(1));
    }
}
