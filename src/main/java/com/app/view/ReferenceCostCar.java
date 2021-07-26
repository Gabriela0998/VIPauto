package com.app.view;

import com.app.controller.CostController;
import com.app.entity.CatAutoEntity;
import com.app.entity.ReferenceEntity;
import com.app.entity.ReferenceEntity;
import com.app.export.ExportDataToExcel;
import com.app.helper.*;
import com.app.service.CostService;
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

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Optional;

public class ReferenceCostCar {

    //private static final Logger LOGGER = LoggerFactory.getLogger(Cost.class);

    private CostService costService = new CostService();
    private ReferenceEntity cost = new ReferenceEntity();
    private TableView<ReferenceEntity> costTableView;
    private ObservableList<ReferenceEntity> costObservableList;
    Stage subStage;

    static public DatePicker date_from;
    static public DatePicker date_to;
    static public ComboBox<CatAutoEntity> catAuto;

    GridPane gridPane = new GridPane();

    public ReferenceCostCar() {
        subStage = new Stage();
        subStage.setTitle("Справка разход по автомобили");
        Scene scene = new Scene(createcostCreateFormPane(), 800, 700);
        subStage.setScene(scene);
        subStage.show();
    }


    public GridPane createcostCreateFormPane() {
        CostHelper.setGridPane2(gridPane);
        addUIControls(gridPane);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Справка разход по автомобили");
        headerLabel.setFont(new Font("Arial", 26));

        gridPane.add(headerLabel, 0, 0, 4, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        //cost name label and text
        CostHelper.addLabel(gridPane, "От дата: ", 0, 1);
        date_from = CostHelper.addDatePicker(gridPane, "", 1, 1);
        date_from.setValue(LocalDate.now());
        //ValidationHelper.lettersOnly(nameTF, 200);
        //cost name label and text
        CostHelper.addLabel(gridPane, "До дата: ", 2, 1);
        date_to = CostHelper.addDatePicker(gridPane, "", 3, 1);
        date_to.setValue(LocalDate.now());
        //ValidationHelper.lettersOnly(nameTF, 200);
        catAuto = ViewHelper.getComboBox(gridPane, 0, 3);

        //TextField searchBoxTF = new TextField();
        Button searchButton = new Button("Търсене");
        Button printButton = new Button("Excel");
        Button exitButton = new Button("Изход");

        CostHelper.initSearchSection(gridPane, searchButton, printButton, exitButton,
                "", 1, 4);

        //
        checkDate();
        searchButton.setOnAction(event -> {
                    //inserForFilter();
            checkDate();
        });
        printButton.setOnAction(event -> {
            checkDate();

            Date date1 = Date.valueOf(date_from.getValue().toString());
            Date date2 = Date.valueOf(date_to.getValue().toString());

            new ExportDataToExcel().export(date1, date2);
        });
        exitButton.setOnAction(e -> {
            subStage.close();
        });
    }
    
    private void checkDate(){
        if (date_from.getValue()==null) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Внимание!","Вие не сте попълнили \"Дата от!\"", "Трябва да е попълнен периода!");
            return;
        }
        if (date_to.getValue()==null) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Внимание!","Вие не сте попълнили \"Дата до!\"", "Трябва да е попълнен периода!");
            return;
        }
        gridPane.add(getTableView(getcostInfo(false, null), gridPane),0,6, 4, 1);

    }
    
    private ObservableList<ReferenceEntity> getcostInfo(boolean iscostSearched, String criteria) {
        costObservableList = FXCollections.observableArrayList();
        ReferenceEntity referenceEntity = new ReferenceEntity();

        if (iscostSearched) {
            /*for (ReferenceEntity cost : costService.getSearched(CostHelper.capitalizeFirstLetter(criteria))) {
                costObservableList.add(cost);
            }*/
        } else {

            Date date1 = Date.valueOf(date_from.getValue().toString());
            Date date2 = Date.valueOf(date_to.getValue().toString());

            for (ReferenceEntity cost : costService.referencePrint(date1, date2)) {
                costObservableList.add(cost);
            }
        }

        return costObservableList;
    }
    private TableView getTableView(ObservableList<ReferenceEntity> observableList, GridPane gridPane) {
        Callback<TableColumn<ReferenceEntity, String>, TableCell<ReferenceEntity, String>> cellFactory
                = (TableColumn<ReferenceEntity, String> param) -> new EditingCell<ReferenceEntity>();

        Callback<TableColumn<ReferenceEntity, java.util.Date>, TableCell<ReferenceEntity, java.util.Date>> dateCellFactory
                = (TableColumn<ReferenceEntity, java.util.Date> param) -> new DateEditingCell();
        //Column for cost name
        TableColumn<ReferenceEntity, String> nameColumn = new TableColumn<>("Разход");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(cellFactory);

        TableColumn<ReferenceEntity, String> carColumn = new TableColumn<>("Автомобил (рег.номер)");
        carColumn.setMinWidth(200);
        carColumn.setCellValueFactory(new PropertyValueFactory<>("car"));
        carColumn.setCellFactory(cellFactory);

        TableColumn<ReferenceEntity, String> catColumn = new TableColumn<>("Категория автомобил");
        catColumn.setMinWidth(200);
        catColumn.setCellValueFactory(new PropertyValueFactory<>("cat"));
        catColumn.setCellFactory(cellFactory);

        TableColumn<ReferenceEntity, java.util.Date> dateFromColumn = new TableColumn<>("Дата разход");
        dateFromColumn.setMinWidth(100);
        dateFromColumn.setCellValueFactory(new PropertyValueFactory<>("date_from"));
        //dateFromColumn.setCellFactory(dateCellFactory);

        TableColumn<ReferenceEntity, java.util.Date> dateToColumn = new TableColumn<>("Дата на плащане");
        dateToColumn.setMinWidth(100);
        dateToColumn.setCellValueFactory(new PropertyValueFactory<>("date_to"));
        //dateToColumn.setCellFactory(dateCellFactory);


        costTableView = new TableView<>();
        costTableView.setEditable(true);
        costTableView.setItems(observableList);
        costTableView.getColumns().addAll(nameColumn, carColumn, catColumn, dateFromColumn, dateToColumn);

        return costTableView;
    }
}

