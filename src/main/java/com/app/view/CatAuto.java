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
import com.app.controller.CatAutoController;
import com.app.entity.CatAutoEntity;
import com.app.helper.CatAutoHelper;
import com.app.helper.EditingCell;
import com.app.helper.Helpers;
import com.app.service.CatAutoService;

import java.text.ParseException;
import java.util.Optional;

public class CatAuto {

    //private static final Logger LOGGER = LoggerFactory.getLogger(CatAuto.class);

    private CatAutoService catAutoService = new CatAutoService();
    private CatAutoEntity catAuto = new CatAutoEntity();
    private TableView<CatAutoEntity> catAutoTableView;
    private ObservableList<CatAutoEntity> catAutoObservableList;
    Stage subStage;

    static public TextField nameTF;
    static public TextField idTF;
    public CatAuto() {
        subStage = new Stage();
        subStage.setTitle("Категория автомобил");
        Scene scene = new Scene(createcatAutoCreateFormPane(), 1200, 700);
        subStage.setScene(scene);
        subStage.show();
    }


    public GridPane createcatAutoCreateFormPane() {
        GridPane gridPane = new GridPane();

        CatAutoHelper.setGridPane(gridPane);
        addUIControls(gridPane);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Категория автомобил");
        headerLabel.setFont(new Font("Arial", 26));

        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        //catAuto name label and text
        CatAutoHelper.addLabel(gridPane, "№: ", 0, 1);
        idTF = CatAutoHelper.addTextField(gridPane, "", 1, 1);
        //ValidationHelper.lettersOnly(nameTF, 200);
        //catAuto name label and text
        CatAutoHelper.addLabel(gridPane, "Наименование на категорията: ", 0, 2);
        nameTF = CatAutoHelper.addTextField(gridPane, "", 1, 2);
        //ValidationHelper.lettersOnly(nameTF, 200);

        //TextField searchBoxTF = new TextField();
        Button editButton = new Button("Нов");
        Button addButton = new Button("Запис");
        Button exitButton = new Button("Изход");

        CatAutoHelper.initSearchSection(gridPane, editButton, addButton, exitButton,
                "", 1, 4);

        gridPane.add(getTableView(getcatAutoInfo(false, null), gridPane),0,6, 2, 1);
        CatAutoController.show();

        addButton.setOnAction(e -> {
            try {
                onSubmit(gridPane, catAuto, idTF, nameTF);
                gridPane.add(getTableView(getcatAutoInfo(false, null), gridPane),0,6, 2, 1);
            } catch (ParseException ex) {
                // LOGGER.error("Exception in submit button set on action: ", ex.getStackTrace());
            }
        });
        editButton.setOnAction(e -> {
            CatAutoHelper.clearForm(gridPane, catAuto, idTF, nameTF);
            CatAutoController.idNum();
        });
        exitButton.setOnAction(e -> {
            subStage.close();
        });
    }
    private void onSubmit(GridPane gridPane, CatAutoEntity CatAuto, TextField idTF, TextField nameTF) throws ParseException {
        if (nameTF.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Не е попълнено име на категорията!");
            return;
        }
        if (idTF.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Няма индификатор!");
            return;
        }

        createCatAuto(nameTF.getText(), Integer.parseInt(idTF.getText()));
        Helpers.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(),
                "Записа успешен!", "Успешно записахте данните на категория автомобил!");
    }


    private void createCatAuto(String name, int id) {
        CatAutoService CatAutoService = new CatAutoService();

        CatAutoEntity CatAuto = new CatAutoEntity();
        CatAuto.setId(id);
        CatAuto.setName(name);

        CatAutoService.getById(CatAuto);
    }

    public static void visible(String name, String id) {
        nameTF.setText(name);
        idTF.setText(id);
    }

    public static void idNumber(String id) {
        idTF.setText(id);
    }

    private ObservableList<CatAutoEntity> getcatAutoInfo(boolean iscatAutoSearched, String criteria) {
        catAutoObservableList = FXCollections.observableArrayList();

        if (iscatAutoSearched) {
            for (CatAutoEntity catAuto : catAutoService.getSearched(CatAutoHelper.capitalizeFirstLetter(criteria))) {
                catAutoObservableList.add(catAuto);
            }
        } else {
            for (CatAutoEntity catAuto : catAutoService.getAll()) {
                catAutoObservableList.add(catAuto);
            }
        }

        return catAutoObservableList;
    }
    private TableView getTableView(ObservableList<CatAutoEntity> observableList, GridPane gridPane) {
        Callback<TableColumn<CatAutoEntity, String>, TableCell<CatAutoEntity, String>> cellFactory
                = (TableColumn<CatAutoEntity, String> param) -> new EditingCell<CatAutoEntity>();

        //Column for catAuto name
        TableColumn<CatAutoEntity, String> nameColumn = new TableColumn<>("Наименование");
        nameColumn.setMinWidth(500);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(cellFactory);
        nameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<CatAutoEntity, String> t) -> {
                    ((CatAutoEntity) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setName(t.getNewValue());

                });

        TableColumn<CatAutoEntity, CatAutoEntity> actions = new TableColumn<>("Действия");
        actions.setMinWidth(400);
        actions.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actions.setCellFactory(param -> new TableCell<CatAutoEntity, CatAutoEntity>() {
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
                        CatAutoEntity catAuto = getTableView().getItems().get(getIndex());
                        CatAutoService catAutoService = new CatAutoService();
                        catAutoService.delete(catAuto);
                        getTableView().getItems().remove(catAuto);
                    }
                });

                editButton.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Вие ще редактирате запис!");
                    alert.setContentText("Желаете ли да продължите?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        CatAutoEntity catAuto = getTableView().getItems().get(getIndex());
                        CatAutoService catAutoService = new CatAutoService();
                        catAutoService.update(catAuto);
                    }
                });

                selectButton.setOnAction(event -> {
                        CatAutoEntity catAuto = getTableView().getItems().get(getIndex());
                        CatAutoService catAutoService = new CatAutoService();
                        catAutoService.vis(catAuto);

                });
            }

            @Override
            protected void updateItem(CatAutoEntity item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });


        catAutoTableView = new TableView<>();
        catAutoTableView.setEditable(true);
        catAutoTableView.setItems(observableList);
        catAutoTableView.getColumns().addAll(nameColumn, actions);

        return catAutoTableView;
    }
}

