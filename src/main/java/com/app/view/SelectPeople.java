package com.app.view;

import com.app.controller.CatAutoController;
import com.app.controller.PeopleController;
import com.app.entity.CatAutoEntity;
import com.app.entity.PeopleEntity;
import com.app.helper.*;
import com.app.service.CatAutoService;
import com.app.service.PeopleService;
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

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class SelectPeople {


    private PeopleService peopleService = new PeopleService();
    private TableView<PeopleEntity> peopleTableView;
    private ObservableList<PeopleEntity> peopleObservableList;
    Stage subStage;

    public SelectPeople() {
        subStage = new Stage();
        subStage.setTitle("Избор на лице");
        Scene scene = new Scene(createPeopleCreateFormPane(), 1000, 900);
        subStage.setScene(scene);
        subStage.show();
    }

    public GridPane createPeopleCreateFormPane() {
        GridPane gridPane = new GridPane();

        ViewHelper.setGridPaneSelectPeople(gridPane);
        addUIControls(gridPane);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        //Search
        TextField searchBoxTF = new TextField();
        //Button submitButton = ViewHelper.getSubmitButton(gridPane, 0, 6);
        Button searchButton = new Button("Търсене");
        Button resetTableButton = new Button("Обновявяне");

        gridPane.add(getTableView(getPeoplesInfo(false, null), gridPane), 0, 1, 2, 50);
        ViewHelper.initSearchSection(gridPane, searchBoxTF, searchButton, resetTableButton,
                "Търсене на лице по ЕГН и Име", 0, 0);
        searchButton.setOnAction(e -> {
            gridPane.add(getTableView(getPeoplesInfo(true, searchBoxTF.getText()), gridPane), 0, 1, 2,50 );
        });
        subStage.setOnCloseRequest(event ->{
            subStage.close();
            new People();
            PeopleController.show();
        });
                resetTableButton.setOnAction(e -> {
            searchBoxTF.clear();
            gridPane.add(getTableView(getPeoplesInfo(false, null), gridPane), 0, 1, 2,50);
        });
    }

    private TableView getTableView(ObservableList<PeopleEntity> observableList, GridPane gridPane) {
        Callback<TableColumn<PeopleEntity, String>, TableCell<PeopleEntity, String>> cellFactory
                = (TableColumn<PeopleEntity, String> param) -> new EditingCell();

        Callback<TableColumn<PeopleEntity, Date>, TableCell<PeopleEntity, Date>> dateCellFactory
                = (TableColumn<PeopleEntity, Date> param) -> new DateEditingCell();


        //Column for first name
        TableColumn<PeopleEntity, String> firstNameColumn = new TableColumn<>("Име");
        firstNameColumn.setMinWidth(120);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        firstNameColumn.setCellFactory(cellFactory);
        firstNameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<PeopleEntity, String> t) -> {
                    ((PeopleEntity) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setName(t.getNewValue());

                });

        //Column for last name
        TableColumn<PeopleEntity, String> lastNameColumn = new TableColumn<>("Презиме");
        lastNameColumn.setMinWidth(120);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lname"));
        lastNameColumn.setCellFactory(cellFactory);
        lastNameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<PeopleEntity, String> t) -> {
                    ((PeopleEntity) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setLname(t.getNewValue());

                });

        //Column for f name
        TableColumn<PeopleEntity, String> fNameColumn = new TableColumn<>("Фамилия");
        fNameColumn.setMinWidth(120);
        fNameColumn.setCellValueFactory(new PropertyValueFactory<>("fname"));
        fNameColumn.setCellFactory(cellFactory);
        fNameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<PeopleEntity, String> t) -> {
                    ((PeopleEntity) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setFname(t.getNewValue());

                });

        //Column for date of birth
        TableColumn<PeopleEntity, Date> dateColumn = new TableColumn<>("Дата на раждане");
        dateColumn.setMinWidth(120);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("born"));
        /*dateColumn.setCellFactory(dateCellFactory);
        dateColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<PeopleEntity, Date> t) -> {
                    ((PeopleEntity) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setDateBorn(t.getNewValue());

                });*/


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
                    subStage.close();
                    new People();
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
        peopleTableView.getColumns().addAll(firstNameColumn, lastNameColumn, fNameColumn, dateColumn, actions);

        return peopleTableView;
    }

    private Date getDateToSqlTimestamp(Date date) {
        Date sd = new java.sql.Date(date.getTime());

        return sd;
    }

    private ObservableList<PeopleEntity> getPeoplesInfo(boolean isPeopleSearched, String criteria) {
        peopleObservableList = FXCollections.observableArrayList();

        if (isPeopleSearched) {
            for (PeopleEntity people : peopleService.getSearched(ViewHelper.capitalizeFirstLetter(criteria))) {
                peopleObservableList.add(people);
            }
        } else {
            for (PeopleEntity people : peopleService.getAll()) {
                peopleObservableList.add(people);
            }
        }

        return peopleObservableList;
    }
}

