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
import com.app.controller.TypeController;
import com.app.entity.CatAutoEntity;
import com.app.entity.TypeEntity;
import com.app.helper.EditingCell;
import com.app.helper.Helpers;
import com.app.helper.TypeHelper;
import com.app.helper.ViewHelper;
import com.app.service.TypeService;

import java.text.ParseException;
import java.util.Optional;

public class Type {
    private TypeService typeService = new TypeService();
    private TypeEntity type = new TypeEntity();
    private TableView<TypeEntity> typeTableView;
    private ObservableList<TypeEntity> typeObservableList;
    Stage subStage;

    static public TextField nameTF;
    static public TextField idTF;
    static public ComboBox<CatAutoEntity> catAuto;
    public Type() {
        subStage = new Stage();
        subStage.setTitle("Тип/Купе на МПС");
        Scene scene = new Scene(createtypeCreateFormPane(), 1200, 700);
        subStage.setScene(scene);
        subStage.show();
    }

    public GridPane createtypeCreateFormPane() {
        GridPane gridPane = new GridPane();

        TypeHelper.setGridPane(gridPane);
        addUIControls(gridPane);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Тип/Купе");
        headerLabel.setFont(new Font("Arial", 26));

        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        //type name label and text
        TypeHelper.addLabel(gridPane, "№: ", 0, 1);
        idTF = TypeHelper.addTextField(gridPane, "", 1, 1);
        //ValidationHelper.lettersOnly(nameTF, 200);
        //type name label and text
        TypeHelper.addLabel(gridPane, "Наименование на тип/купе: ", 0, 2);
        nameTF = TypeHelper.addTextField(gridPane, "", 1, 2);
        //ValidationHelper.lettersOnly(nameTF, 200);

        catAuto = ViewHelper.getComboBox(gridPane, 0, 3);


        //TextField searchBoxTF = new TextField();
        Button editButton = new Button("Нов");
        Button addButton = new Button("Запис");
        Button exitButton = new Button("Изход");

        TypeHelper.initSearchSection(gridPane, editButton, addButton, exitButton,
                "", 1, 4);

        gridPane.add(getTableView(gettypeInfo(false, null), gridPane),0,6, 2, 1);
        TypeController.show();

        addButton.setOnAction(e -> {
            try {
                //if(catAuto==null){}
                onSubmit(gridPane, type, idTF, nameTF, catAuto);
                gridPane.add(getTableView(gettypeInfo(false, null), gridPane),0,6, 2, 1);
            } catch (ParseException ex) {
                // LOGGER.error("Exception in submit button set on action: ", ex.getStackTrace());
            }
        });
        editButton.setOnAction(e -> {
            TypeHelper.clearForm(gridPane, type, idTF, nameTF);
            TypeController.idNum();
            catAuto = ViewHelper.getComboBox(gridPane, 0, 3);
            catAuto.setPromptText("");
        });
        exitButton.setOnAction(e -> {
            subStage.close();
        });
    }
    private void onSubmit(GridPane gridPane, TypeEntity Type, TextField idTF, TextField nameTF, ComboBox catAuto) throws ParseException {
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

        createType(nameTF.getText(), Integer.parseInt(idTF.getText()),catAuto.getValue().toString());
        Helpers.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(),
                "Записа успешен!", "Успешно записахте данните на тип/купе на МПС-то!");
    }


    private void createType(String name, int id, String id_cat) {
        TypeService TypeService = new TypeService();

        TypeEntity Type = new TypeEntity();
        Type.setId(id);
        Type.setName(name);
        Type.setId_catAuto(TypeService.getExistingTypeId(id_cat));

        TypeService.getById(Type);
    }

    public static void visible(String name, String id, String id_combo) {
        nameTF.setText(name);
        idTF.setText(id);
        catAuto.setPromptText(id_combo);
    }

    public static void idNumber(String id) {
        idTF.setText(id);
    }
    private ObservableList<TypeEntity> gettypeInfo(boolean istypeSearched, String criteria) {
        typeObservableList = FXCollections.observableArrayList();

        if (istypeSearched) {
            for (TypeEntity type : typeService.getSearched(TypeHelper.capitalizeFirstLetter(criteria))) {
                typeObservableList.add(type);
            }
        } else {
            for (TypeEntity type : typeService.getAll()) {
                typeObservableList.add(type);
            }
        }

        return typeObservableList;
    }
    private TableView getTableView(ObservableList<TypeEntity> observableList, GridPane gridPane) {
        Callback<TableColumn<TypeEntity, String>, TableCell<TypeEntity, String>> cellFactory
                = (TableColumn<TypeEntity, String> param) -> new EditingCell<TypeEntity>();

        //Column for type name
        TableColumn<TypeEntity, String> nameColumn = new TableColumn<>("Наименование");
        nameColumn.setMinWidth(500);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(cellFactory);
        nameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<TypeEntity, String> t) -> {
                    ((TypeEntity) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setName(t.getNewValue());

                });

        TableColumn<TypeEntity, TypeEntity> actions = new TableColumn<>("Действия");
        actions.setMinWidth(400);
        actions.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actions.setCellFactory(param -> new TableCell<TypeEntity, TypeEntity>() {
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
                        TypeEntity type = getTableView().getItems().get(getIndex());
                        TypeService typeService = new TypeService();
                        typeService.delete(type);
                        getTableView().getItems().remove(type);
                    }
                });

                editButton.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Вие ще редактирате запис!");
                    alert.setContentText("Желаете ли да продължите?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        TypeEntity type = getTableView().getItems().get(getIndex());
                        TypeService typeService = new TypeService();
                        typeService.update(type);
                    }
                });

                selectButton.setOnAction(event -> {
                    TypeEntity type = getTableView().getItems().get(getIndex());
                    TypeService typeService = new TypeService();
                    typeService.vis(type);

                });
            }

            @Override
            protected void updateItem(TypeEntity item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });


        typeTableView = new TableView<>();
        typeTableView.setEditable(true);
        typeTableView.setItems(observableList);
        typeTableView.getColumns().addAll(nameColumn, actions);

        return typeTableView;
    }
}


