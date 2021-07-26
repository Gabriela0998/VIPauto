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
import com.app.controller.TypeDocumentController;
import com.app.entity.TypeDocumentEntity;
import com.app.helper.EditingCell;
import com.app.helper.Helpers;
import com.app.helper.TypeDocumentHelper;
import com.app.service.TypeDocumentService;

import java.text.ParseException;
import java.util.Optional;

public class TypeDocument {

    //private static final Logger LOGGER = LoggerFactory.getLogger(TypeDocument.class);

    private TypeDocumentService typeDocumentService = new TypeDocumentService();
    private TypeDocumentEntity typeDocument = new TypeDocumentEntity();
    private TableView<TypeDocumentEntity> typeDocumentTableView;
    private ObservableList<TypeDocumentEntity> typeDocumentObservableList;
    Stage subStage;

    static public TextField nameTF;
    static public TextField idTF;
    public TypeDocument() {
        subStage = new Stage();
        subStage.setTitle("Типове документи");
        Scene scene = new Scene(createtypeDocumentCreateFormPane(), 1200, 700);
        subStage.setScene(scene);
        subStage.show();
    }


    public GridPane createtypeDocumentCreateFormPane() {
        GridPane gridPane = new GridPane();

        TypeDocumentHelper.setGridPane(gridPane);
        addUIControls(gridPane);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Допълнителни видове документи");
        headerLabel.setFont(new Font("Arial", 26));

        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        //typeDocument name label and text
        TypeDocumentHelper.addLabel(gridPane, "№: ", 0, 1);
        idTF = TypeDocumentHelper.addTextField(gridPane, "", 1, 1);
        //ValidationHelper.lettersOnly(nameTF, 200);
        //typeDocument name label and text
        TypeDocumentHelper.addLabel(gridPane, "Тип документ: ", 0, 2);
        nameTF = TypeDocumentHelper.addTextField(gridPane, "", 1, 2);
        //ValidationHelper.lettersOnly(nameTF, 200);

        //TextField searchBoxTF = new TextField();
        Button editButton = new Button("Нов");
        Button addButton = new Button("Запис");
        Button exitButton = new Button("Изход");

        TypeDocumentHelper.initSearchSection(gridPane, editButton, addButton, exitButton,
                "", 1, 4);

        gridPane.add(getTableView(gettypeDocumentInfo(false, null), gridPane),0,6, 2, 1);
        TypeDocumentController.show();

        addButton.setOnAction(e -> {
            try {
                onSubmit(gridPane, typeDocument, idTF, nameTF);
                gridPane.add(getTableView(gettypeDocumentInfo(false, null), gridPane),0,6, 2, 1);
            } catch (ParseException ex) {
                // LOGGER.error("Exception in submit button set on action: ", ex.getStackTrace());
            }
        });
        editButton.setOnAction(e -> {
            TypeDocumentHelper.clearForm(gridPane, typeDocument, idTF, nameTF);
            TypeDocumentController.idNum();
        });
        exitButton.setOnAction(e -> {
            subStage.close();
        });
    }
    private void onSubmit(GridPane gridPane, TypeDocumentEntity TypeDocument, TextField idTF, TextField nameTF) throws ParseException {
        if (nameTF.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Не е попълнен типа документ!");
            return;
        }
        if (idTF.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Няма индификатор!");
            return;
        }

        createTypeDocument(nameTF.getText(), Integer.parseInt(idTF.getText()));
        Helpers.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(),
                "Записа успешен!", "Успешно записахте данните на типа документ!");
    }


    private void createTypeDocument(String name, int id) {
        TypeDocumentService TypeDocumentService = new TypeDocumentService();

        TypeDocumentEntity TypeDocument = new TypeDocumentEntity();
        TypeDocument.setId(id);
        TypeDocument.setName(name);

        TypeDocumentService.getById(TypeDocument);
    }

    public static void visible(String name, String id) {
        nameTF.setText(name);
        idTF.setText(id);
    }

    public static void idNumber(String id) {
        idTF.setText(id);
    }
    private ObservableList<TypeDocumentEntity> gettypeDocumentInfo(boolean istypeDocumentSearched, String criteria) {
        typeDocumentObservableList = FXCollections.observableArrayList();

        if (istypeDocumentSearched) {
            for (TypeDocumentEntity typeDocument : typeDocumentService.getSearched(TypeDocumentHelper.capitalizeFirstLetter(criteria))) {
                typeDocumentObservableList.add(typeDocument);
            }
        } else {
            for (TypeDocumentEntity typeDocument : typeDocumentService.getAll()) {
                typeDocumentObservableList.add(typeDocument);
            }
        }

        return typeDocumentObservableList;
    }
    private TableView getTableView(ObservableList<TypeDocumentEntity> observableList, GridPane gridPane) {
        Callback<TableColumn<TypeDocumentEntity, String>, TableCell<TypeDocumentEntity, String>> cellFactory
                = (TableColumn<TypeDocumentEntity, String> param) -> new EditingCell<TypeDocumentEntity>();

        //Column for typeDocument name
        TableColumn<TypeDocumentEntity, String> nameColumn = new TableColumn<>("Наименование");
        nameColumn.setMinWidth(500);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(cellFactory);
        nameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<TypeDocumentEntity, String> t) -> {
                    ((TypeDocumentEntity) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setName(t.getNewValue());

                });

        TableColumn<TypeDocumentEntity, TypeDocumentEntity> actions = new TableColumn<>("Действия");
        actions.setMinWidth(400);
        actions.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actions.setCellFactory(param -> new TableCell<TypeDocumentEntity, TypeDocumentEntity>() {
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
                        TypeDocumentEntity typeDocument = getTableView().getItems().get(getIndex());
                        TypeDocumentService typeDocumentService = new TypeDocumentService();
                        typeDocumentService.delete(typeDocument);
                        getTableView().getItems().remove(typeDocument);
                    }
                });

                editButton.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Вие ще редактирате запис!");
                    alert.setContentText("Желаете ли да продължите?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        TypeDocumentEntity typeDocument = getTableView().getItems().get(getIndex());
                        TypeDocumentService typeDocumentService = new TypeDocumentService();
                        typeDocumentService.update(typeDocument);
                    }
                });

                selectButton.setOnAction(event -> {
                        TypeDocumentEntity typeDocument = getTableView().getItems().get(getIndex());
                        TypeDocumentService typeDocumentService = new TypeDocumentService();
                        typeDocumentService.vis(typeDocument);

                });
            }

            @Override
            protected void updateItem(TypeDocumentEntity item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });


        typeDocumentTableView = new TableView<>();
        typeDocumentTableView.setEditable(true);
        typeDocumentTableView.setItems(observableList);
        typeDocumentTableView.getColumns().addAll(nameColumn, actions);

        return typeDocumentTableView;
    }
}

