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
import com.app.controller.TypeCostController;
import com.app.entity.TypeCostEntity;
import com.app.helper.EditingCell;
import com.app.helper.Helpers;
import com.app.helper.TypeCostHelper;
import com.app.service.TypeCostService;

import java.text.ParseException;
import java.util.Optional;

public class TypeCost {

    //private static final Logger LOGGER = LoggerFactory.getLogger(TypeCost.class);

    private TypeCostService typeCostService = new TypeCostService();
    private TypeCostEntity typeCost = new TypeCostEntity();
    private TableView<TypeCostEntity> typeCostTableView;
    private ObservableList<TypeCostEntity> typeCostObservableList;
    Stage subStage;

    static public TextField nameTF;
    static public TextField idTF;
    public TypeCost() {
        subStage = new Stage();
        subStage.setTitle("Вид разходи");
        Scene scene = new Scene(createtypeCostCreateFormPane(), 1200, 700);
        subStage.setScene(scene);
        subStage.show();
    }


    public GridPane createtypeCostCreateFormPane() {
        GridPane gridPane = new GridPane();

        TypeCostHelper.setGridPane(gridPane);
        addUIControls(gridPane);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Други видове разходи");
        headerLabel.setFont(new Font("Arial", 26));

        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        //typeCost name label and text
        TypeCostHelper.addLabel(gridPane, "№: ", 0, 1);
        idTF = TypeCostHelper.addTextField(gridPane, "", 1, 1);
        //ValidationHelper.lettersOnly(nameTF, 200);
        //typeCost name label and text
        TypeCostHelper.addLabel(gridPane, "Наименование на разхода: ", 0, 2);
        nameTF = TypeCostHelper.addTextField(gridPane, "", 1, 2);
        //ValidationHelper.lettersOnly(nameTF, 200);

        //TextField searchBoxTF = new TextField();
        Button editButton = new Button("Нов");
        Button addButton = new Button("Запис");
        Button exitButton = new Button("Изход");

        TypeCostHelper.initSearchSection(gridPane, editButton, addButton, exitButton,
                "", 1, 4);

        gridPane.add(getTableView(gettypeCostInfo(false, null), gridPane),0,6, 2, 1);
        TypeCostController.show();

        addButton.setOnAction(e -> {
            try {
                onSubmit(gridPane, typeCost, idTF, nameTF);
                gridPane.add(getTableView(gettypeCostInfo(false, null), gridPane),0,6, 2, 1);
            } catch (ParseException ex) {
                // LOGGER.error("Exception in submit button set on action: ", ex.getStackTrace());
            }
        });
        editButton.setOnAction(e -> {
            TypeCostHelper.clearForm(gridPane, typeCost, idTF, nameTF);
            TypeCostController.idNum();
        });
        exitButton.setOnAction(e -> {
            subStage.close();
        });
    }
    private void onSubmit(GridPane gridPane, TypeCostEntity TypeCost, TextField idTF, TextField nameTF) throws ParseException {
        if (nameTF.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Не е попълнено име на разхода!");
            return;
        }
        if (idTF.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Няма индификатор!");
            return;
        }

        createTypeCost(nameTF.getText(), Integer.parseInt(idTF.getText()));
        Helpers.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(),
                "Записа успешен!", "Успешно записахте данните на допълнителния разход!");
    }


    private void createTypeCost(String name, int id) {
        TypeCostService TypeCostService = new TypeCostService();

        TypeCostEntity TypeCost = new TypeCostEntity();
        TypeCost.setId(id);
        TypeCost.setName(name);

        TypeCostService.getById(TypeCost);
    }

    public static void visible(String name, String id) {
        nameTF.setText(name);
        idTF.setText(id);
    }

    public static void idNumber(String id) {
        idTF.setText(id);
    }
    private ObservableList<TypeCostEntity> gettypeCostInfo(boolean istypeCostSearched, String criteria) {
        typeCostObservableList = FXCollections.observableArrayList();

        if (istypeCostSearched) {
            for (TypeCostEntity typeCost : typeCostService.getSearched(TypeCostHelper.capitalizeFirstLetter(criteria))) {
                typeCostObservableList.add(typeCost);
            }
        } else {
            for (TypeCostEntity typeCost : typeCostService.getAll()) {
                typeCostObservableList.add(typeCost);
            }
        }

        return typeCostObservableList;
    }
    private TableView getTableView(ObservableList<TypeCostEntity> observableList, GridPane gridPane) {
        Callback<TableColumn<TypeCostEntity, String>, TableCell<TypeCostEntity, String>> cellFactory
                = (TableColumn<TypeCostEntity, String> param) -> new EditingCell<TypeCostEntity>();

        //Column for typeCost name
        TableColumn<TypeCostEntity, String> nameColumn = new TableColumn<>("Наименование");
        nameColumn.setMinWidth(500);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(cellFactory);
        nameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<TypeCostEntity, String> t) -> {
                    ((TypeCostEntity) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setName(t.getNewValue());

                });

        TableColumn<TypeCostEntity, TypeCostEntity> actions = new TableColumn<>("Действия");
        actions.setMinWidth(400);
        actions.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actions.setCellFactory(param -> new TableCell<TypeCostEntity, TypeCostEntity>() {
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
                        TypeCostEntity typeCost = getTableView().getItems().get(getIndex());
                        TypeCostService typeCostService = new TypeCostService();
                        typeCostService.delete(typeCost);
                        getTableView().getItems().remove(typeCost);
                    }
                });

                editButton.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Вие ще редактирате запис!");
                    alert.setContentText("Желаете ли да продължите?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        TypeCostEntity typeCost = getTableView().getItems().get(getIndex());
                        TypeCostService typeCostService = new TypeCostService();
                        typeCostService.update(typeCost);
                    }
                });

                selectButton.setOnAction(event -> {
                        TypeCostEntity typeCost = getTableView().getItems().get(getIndex());
                        TypeCostService typeCostService = new TypeCostService();
                        typeCostService.vis(typeCost);

                });
            }

            @Override
            protected void updateItem(TypeCostEntity item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });


        typeCostTableView = new TableView<>();
        typeCostTableView.setEditable(true);
        typeCostTableView.setItems(observableList);
        typeCostTableView.getColumns().addAll(nameColumn, actions);

        return typeCostTableView;
    }
}

