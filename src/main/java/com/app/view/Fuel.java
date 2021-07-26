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
import com.app.controller.FuelController;
import com.app.entity.FuelEntity;
import com.app.helper.EditingCell;
import com.app.helper.FuelHelper;
import com.app.helper.Helpers;
import com.app.service.FuelService;

import java.text.ParseException;
import java.util.Optional;

public class Fuel {

    //private static final Logger LOGGER = LoggerFactory.getLogger(Fuel.class);

    private FuelService fuelService = new FuelService();
    private FuelEntity fuel = new FuelEntity();
    private TableView<FuelEntity> fuelTableView;
    private ObservableList<FuelEntity> fuelObservableList;
    Stage subStage;

    static public TextField nameTF;
    static public TextField idTF;
    public Fuel() {
        subStage = new Stage();
        subStage.setTitle("Мотор (гориво)");
        Scene scene = new Scene(createfuelCreateFormPane(), 1200, 700);
        subStage.setScene(scene);
        subStage.show();
    }


    public GridPane createfuelCreateFormPane() {
        GridPane gridPane = new GridPane();

        FuelHelper.setGridPane(gridPane);
        addUIControls(gridPane);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Мотор (гориво)");
        headerLabel.setFont(new Font("Arial", 26));

        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        //fuel name label and text
        FuelHelper.addLabel(gridPane, "№: ", 0, 1);
        idTF = FuelHelper.addTextField(gridPane, "", 1, 1);
        //ValidationHelper.lettersOnly(nameTF, 200);
        //fuel name label and text
        FuelHelper.addLabel(gridPane, "Вид гориво: ", 0, 2);
        nameTF = FuelHelper.addTextField(gridPane, "", 1, 2);
        //ValidationHelper.lettersOnly(nameTF, 200);

        //TextField searchBoxTF = new TextField();
        Button editButton = new Button("Нов");
        Button addButton = new Button("Запис");
        Button exitButton = new Button("Изход");

        FuelHelper.initSearchSection(gridPane, editButton, addButton, exitButton,
                "", 1, 4);

        gridPane.add(getTableView(getfuelInfo(false, null), gridPane),0,6, 2, 1);
        FuelController.show();

        addButton.setOnAction(e -> {
            try {
                onSubmit(gridPane, fuel, idTF, nameTF);
                gridPane.add(getTableView(getfuelInfo(false, null), gridPane),0,6, 2, 1);
            } catch (ParseException ex) {
                // LOGGER.error("Exception in submit button set on action: ", ex.getStackTrace());
            }
        });
        editButton.setOnAction(e -> {
            FuelHelper.clearForm(gridPane, fuel, idTF, nameTF);
            FuelController.idNum();
        });
        exitButton.setOnAction(e -> {
            subStage.close();
        });
    }
    private void onSubmit(GridPane gridPane, FuelEntity Fuel, TextField idTF, TextField nameTF) throws ParseException {
        if (nameTF.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Не е попълнено вида на горивото!");
            return;
        }
        if (idTF.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Няма индификатор!");
            return;
        }

        createFuel(nameTF.getText(), Integer.parseInt(idTF.getText()));
        Helpers.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(),
                "Записа успешен!", "Успешно записахте данните на вида на мотора!");
    }


    private void createFuel(String name, int id) {
        FuelService FuelService = new FuelService();

        FuelEntity Fuel = new FuelEntity();
        Fuel.setId(id);
        Fuel.setName(name);

        FuelService.getById(Fuel);
    }

    public static void visible(String name, String id) {
        nameTF.setText(name);
        idTF.setText(id);
    }

    public static void idNumber(String id) {
        idTF.setText(id);
    }
    private ObservableList<FuelEntity> getfuelInfo(boolean isfuelSearched, String criteria) {
        fuelObservableList = FXCollections.observableArrayList();

        if (isfuelSearched) {
            for (FuelEntity fuel : fuelService.getSearched(FuelHelper.capitalizeFirstLetter(criteria))) {
                fuelObservableList.add(fuel);
            }
        } else {
            for (FuelEntity fuel : fuelService.getAll()) {
                fuelObservableList.add(fuel);
            }
        }

        return fuelObservableList;
    }
    private TableView getTableView(ObservableList<FuelEntity> observableList, GridPane gridPane) {
        Callback<TableColumn<FuelEntity, String>, TableCell<FuelEntity, String>> cellFactory
                = (TableColumn<FuelEntity, String> param) -> new EditingCell<FuelEntity>();

        //Column for fuel name
        TableColumn<FuelEntity, String> nameColumn = new TableColumn<>("Мотор(гориво)");
        nameColumn.setMinWidth(500);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(cellFactory);
        nameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<FuelEntity, String> t) -> {
                    ((FuelEntity) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setName(t.getNewValue());

                });

        TableColumn<FuelEntity, FuelEntity> actions = new TableColumn<>("Действия");
        actions.setMinWidth(400);
        actions.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actions.setCellFactory(param -> new TableCell<FuelEntity, FuelEntity>() {
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
                        FuelEntity fuel = getTableView().getItems().get(getIndex());
                        FuelService fuelService = new FuelService();
                        fuelService.delete(fuel);
                        getTableView().getItems().remove(fuel);
                    }
                });

                editButton.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Вие ще редактирате запис!");
                    alert.setContentText("Желаете ли да продължите?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        FuelEntity fuel = getTableView().getItems().get(getIndex());
                        FuelService fuelService = new FuelService();
                        fuelService.update(fuel);
                    }
                });

                selectButton.setOnAction(event -> {
                        FuelEntity fuel = getTableView().getItems().get(getIndex());
                        FuelService fuelService = new FuelService();
                        fuelService.vis(fuel);

                });
            }

            @Override
            protected void updateItem(FuelEntity item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });


        fuelTableView = new TableView<>();
        fuelTableView.setEditable(true);
        fuelTableView.setItems(observableList);
        fuelTableView.getColumns().addAll(nameColumn, actions);

        return fuelTableView;
    }
}

