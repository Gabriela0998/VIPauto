package com.app.view;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.app.controller.ModelController;
import com.app.entity.BrandEntity;
import com.app.entity.ModelEntity;
import com.app.helper.EditingCell;
import com.app.helper.Helpers;
import com.app.helper.ModelHelper;
import com.app.helper.ViewHelper;
import com.app.service.ModelService;

import java.text.ParseException;
import java.util.Optional;

public class Model {
    private ModelService modelService = new ModelService();
    private ModelEntity model = new ModelEntity();
    private TableView<ModelEntity> modelTableView;
    private ObservableList<ModelEntity> modelObservableList;
    Stage subStage;

    static public TextField nameTF;
    static public TextField idTF;
    static public ComboBox<BrandEntity> brandBox;
    static public ComboBox<BrandEntity> brandBox2;
    public Model() {
        subStage = new Stage();
        subStage.setTitle("Модел");
        Scene scene = new Scene(createmodelCreateFormPane(), 1200, 700);
        subStage.setScene(scene);
        subStage.show();
    }

    public GridPane createmodelCreateFormPane() {
        GridPane gridPane = new GridPane();

        ModelHelper.setGridPane(gridPane);
        addUIControls(gridPane);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Модел");
        headerLabel.setFont(new Font("Arial", 26));

        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        //model name label and text
        ModelHelper.addLabel(gridPane, "№: ", 0, 1);
        idTF = ModelHelper.addTextField(gridPane, "", 1, 1);
        //ValidationHelper.lettersOnly(nameTF, 200);
        //model name label and text
        ModelHelper.addLabel(gridPane, "Наименование на модел: ", 0, 2);
        nameTF = ModelHelper.addTextField(gridPane, "", 1, 2);
        //ValidationHelper.lettersOnly(nameTF, 200);

        brandBox = ViewHelper.getComboBoxBrand(gridPane, 0, 3);


        //TextField searchBoxTF = new TextField();
        Button editButton = new Button("Нов");
        Button addButton = new Button("Запис");
        Button exitButton = new Button("Изход");

        ModelHelper.initSearchSection(gridPane, editButton, addButton, exitButton,
                "", 1, 4);

        gridPane.add(getTableView(getmodelInfo(false, null), gridPane),0,6, 2, 1);
        ModelController.show();

        addButton.setOnAction(e -> {
            try {
                onSubmit(gridPane, model, idTF, nameTF, brandBox);
                gridPane.add(getTableView(getmodelInfo(false, null), gridPane),0,6, 2, 1);
            } catch (ParseException ex) {
                // LOGGER.error("Exception in submit button set on action: ", ex.getStackTrace());
            }
        });
        editButton.setOnAction(e -> {
            ModelHelper.clearForm(gridPane, model, idTF, nameTF);
            ModelController.idNum();
            brandBox = ViewHelper.getComboBoxBrand(gridPane, 0, 3);
            brandBox.setPromptText("");
        });
        exitButton.setOnAction(e -> {
            subStage.close();
        });
    }
    private void onSubmit(GridPane gridPane, ModelEntity Model, TextField idTF, TextField nameTF, ComboBox brandBox) throws ParseException {
        if (nameTF.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Не е попълнено име на тип/купе!");
            return;
        }
        if (idTF.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Няма индификатор!");
            return;
        }

        createModel(nameTF.getText(), Integer.parseInt(idTF.getText()),brandBox.getValue().toString());
        Helpers.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(),
                "Записа успешен!", "Успешно записахте данните на тип/купе на МПС-то!");
    }


    private void createModel(String name, int id, String id_brand) {
        ModelService ModelService = new ModelService();

        ModelEntity Model = new ModelEntity();
        Model.setId(id);
        Model.setName(name);
        Model.setId_brand(ModelService.getExistingModelId(id_brand));

        ModelService.getById(Model);
    }

    public static void visible(String name, String id, String id_combo) {
        nameTF.setText(name);
        idTF.setText(id);
        brandBox.setPromptText(id_combo);
    }
    public static void idNumber(String id) {
        idTF.setText(id);

        if(id=="0" || id==null){
            idTF.setText("1");
        }
    }
    private ObservableList<ModelEntity> getmodelInfo(boolean ismodelSearched, String criteria) {
        modelObservableList = FXCollections.observableArrayList();

        if (ismodelSearched) {
            for (ModelEntity model : modelService.getSearched(ModelHelper.capitalizeFirstLetter(criteria))) {
                modelObservableList.add(model);
            }
        } else {
            for (ModelEntity model : modelService.getAll()) {
                modelObservableList.add(model);
            }
        }

        return modelObservableList;
    }
    private TableView getTableView(ObservableList<ModelEntity> observableList, GridPane gridPane) {
        Callback<TableColumn<ModelEntity, String>, TableCell<ModelEntity, String>> cellFactory
                = (TableColumn<ModelEntity, String> param) -> new EditingCell<ModelEntity>();

        //Column for model name
        TableColumn<ModelEntity, String> nameColumn = new TableColumn<>("Наименование");
        nameColumn.setMinWidth(400);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(cellFactory);
        nameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<ModelEntity, String> t) -> {
                    ((ModelEntity) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setName(t.getNewValue());

                });

        //Column for model name

        brandBox2 = ViewHelper.getComboBoxBrandGrid(gridPane, 0, 3);

        TableColumn<ModelEntity, String> brandColumn = new TableColumn<>("Марка");
        brandColumn.setMinWidth(400);
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        brandColumn.setCellFactory(ComboBoxTableCell.<ModelEntity, String>forTableColumn(String.valueOf("")));
        brandColumn.setOnEditCommit(
                (t) -> {
                    ((ModelEntity) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setBrand(String.valueOf(t.getNewValue()));

                });

        TableColumn<ModelEntity, ModelEntity> actions = new TableColumn<>("Действия");
        actions.setMinWidth(300);
        actions.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actions.setCellFactory(param -> new TableCell<ModelEntity, ModelEntity>() {
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
                        ModelEntity model = getTableView().getItems().get(getIndex());
                        ModelService modelService = new ModelService();
                        modelService.delete(model);
                        getTableView().getItems().remove(model);
                    }
                });

                editButton.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Вие ще редактирате запис!");
                    alert.setContentText("Желаете ли да продължите?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        ModelEntity model = getTableView().getItems().get(getIndex());
                        ModelService modelService = new ModelService();
                        modelService.update(model);
                    }
                });

                selectButton.setOnAction(event -> {
                    ModelEntity model = getTableView().getItems().get(getIndex());
                    ModelService modelService = new ModelService();
                    modelService.vis(model);

                });
            }

            @Override
            protected void updateItem(ModelEntity item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });


        modelTableView = new TableView<>();
        modelTableView.setEditable(true);
        modelTableView.setItems(observableList);
        modelTableView.getColumns().addAll(nameColumn, brandColumn, actions);

        return modelTableView;
    }
}
