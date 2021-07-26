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
import com.app.controller.InsCompaniesController;
import com.app.entity.InsCompaniesEntity;
import com.app.helper.EditingCell;
import com.app.helper.Helpers;
import com.app.helper.InsCompaniesHelper;
import com.app.service.InsCompaniesService;

import java.text.ParseException;
import java.util.Optional;

public class InsCompanies {

    //private static final Logger LOGGER = LoggerFactory.getLogger(InsCompanies.class);

    private InsCompaniesService insCompaniesService = new InsCompaniesService();
    private InsCompaniesEntity insCompanies = new InsCompaniesEntity();
    private TableView<InsCompaniesEntity> insCompaniesTableView;
    private ObservableList<InsCompaniesEntity> insCompaniesObservableList;
    Stage subStage;

    static public TextField nameTF;
    static public TextField idTF;
    static public TextField phoneTF;
    static public TextField extraTF;
    public InsCompanies() {
        subStage = new Stage();
        subStage.setTitle("Застрахователно дружество");
        Scene scene = new Scene(createinsCompaniesCreateFormPane(), 1200, 700);
        subStage.setScene(scene);
        subStage.show();
    }


    public GridPane createinsCompaniesCreateFormPane() {
        GridPane gridPane = new GridPane();

        InsCompaniesHelper.setGridPane(gridPane);
        addUIControls(gridPane);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Застрахователно дружество");
        headerLabel.setFont(new Font("Arial", 26));

        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        //insCompanies name label and text
        InsCompaniesHelper.addLabel(gridPane, "№: ", 0, 1);
        idTF = InsCompaniesHelper.addTextField(gridPane, "", 1, 1);
        //ValidationHelper.lettersOnly(nameTF, 200);
        //insCompanies name label and text
        InsCompaniesHelper.addLabel(gridPane, "Име на дружеството: ", 0, 2);
        nameTF = InsCompaniesHelper.addTextField(gridPane, "", 1, 2);

        InsCompaniesHelper.addLabel(gridPane, "Телефон: ", 0, 3);
        phoneTF = InsCompaniesHelper.addTextField(gridPane, "", 1, 3);

        InsCompaniesHelper.addLabel(gridPane, "Допълнителна информация: ", 0, 4);
        extraTF = InsCompaniesHelper.addTextField(gridPane, "", 1, 4);
        //ValidationHelper.lettersOnly(nameTF, 200);

        //TextField searchBoxTF = new TextField();
        Button editButton = new Button("Нов");
        Button addButton = new Button("Запис");
        Button exitButton = new Button("Изход");

        InsCompaniesHelper.initSearchSection(gridPane, editButton, addButton, exitButton,
                "", 1, 5);

        gridPane.add(getTableView(getinsCompaniesInfo(false, null), gridPane),0,6, 2, 1);
        InsCompaniesController.show();

        addButton.setOnAction(e -> {
            try {
                onSubmit(gridPane, insCompanies, idTF, nameTF, phoneTF, extraTF);
                gridPane.add(getTableView(getinsCompaniesInfo(false, null), gridPane),0,6, 2, 1);
            } catch (ParseException ex) {
                // LOGGER.error("Exception in submit button set on action: ", ex.getStackTrace());
            }
        });
        editButton.setOnAction(e -> {
            InsCompaniesHelper.clearForm(gridPane, insCompanies, idTF, nameTF, phoneTF, extraTF);
            InsCompaniesController.idNum();
        });
        exitButton.setOnAction(e -> {
            subStage.close();
        });
    }
    private void onSubmit(GridPane gridPane, InsCompaniesEntity InsCompanies, TextField idTF, TextField nameTF, TextField phoneTF, TextField extraTF) throws ParseException {
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

        createInsCompanies(nameTF.getText(), Integer.parseInt(idTF.getText()), phoneTF.getText(), extraTF.getText());
        Helpers.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(),
                "Записа успешен!", "Успешно записахте данните на категория автомобил!");
    }


    private void createInsCompanies(String name, int id, String phone, String extra) {
        InsCompaniesService InsCompaniesService = new InsCompaniesService();

        InsCompaniesEntity InsCompanies = new InsCompaniesEntity();
        InsCompanies.setId(id);
        InsCompanies.setName(name);
        InsCompanies.setPhone(phone);
        InsCompanies.setExtra(extra);

        InsCompaniesService.getById(InsCompanies);
    }

    public static void visible(String name, String id, String phone, String extra) {
        nameTF.setText(name);
        idTF.setText(id);
        phoneTF.setText(phone);
        extraTF.setText(extra);
    }

    public static void idNumber(String id) {
        idTF.setText(id);
        if(id=="0" || id==null){idTF.setText("1");}
    }
    private ObservableList<InsCompaniesEntity> getinsCompaniesInfo(boolean isinsCompaniesSearched, String criteria) {
        insCompaniesObservableList = FXCollections.observableArrayList();

        if (isinsCompaniesSearched) {
            for (InsCompaniesEntity insCompanies : insCompaniesService.getSearched(InsCompaniesHelper.capitalizeFirstLetter(criteria))) {
                insCompaniesObservableList.add(insCompanies);
            }
        } else {
            for (InsCompaniesEntity insCompanies : insCompaniesService.getAll()) {
                insCompaniesObservableList.add(insCompanies);
            }
        }

        return insCompaniesObservableList;
    }
    private TableView getTableView(ObservableList<InsCompaniesEntity> observableList, GridPane gridPane) {
        Callback<TableColumn<InsCompaniesEntity, String>, TableCell<InsCompaniesEntity, String>> cellFactory
                = (TableColumn<InsCompaniesEntity, String> param) -> new EditingCell<InsCompaniesEntity>();

        //Column for insCompanies name
        TableColumn<InsCompaniesEntity, String> nameColumn = new TableColumn<>("Наименование");
        nameColumn.setMinWidth(500);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(cellFactory);
        nameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<InsCompaniesEntity, String> t) -> {
                    ((InsCompaniesEntity) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setName(t.getNewValue());

                });

        TableColumn<InsCompaniesEntity, InsCompaniesEntity> actions = new TableColumn<>("Действия");
        actions.setMinWidth(400);
        actions.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actions.setCellFactory(param -> new TableCell<InsCompaniesEntity, InsCompaniesEntity>() {
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
                        InsCompaniesEntity insCompanies = getTableView().getItems().get(getIndex());
                        InsCompaniesService insCompaniesService = new InsCompaniesService();
                        insCompaniesService.delete(insCompanies);
                        getTableView().getItems().remove(insCompanies);
                    }
                });

                editButton.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Вие ще редактирате запис!");
                    alert.setContentText("Желаете ли да продължите?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        InsCompaniesEntity insCompanies = getTableView().getItems().get(getIndex());
                        InsCompaniesService insCompaniesService = new InsCompaniesService();
                        insCompaniesService.update(insCompanies);
                    }
                });

                selectButton.setOnAction(event -> {
                        InsCompaniesEntity insCompanies = getTableView().getItems().get(getIndex());
                        InsCompaniesService insCompaniesService = new InsCompaniesService();
                        insCompaniesService.vis(insCompanies);

                });
            }

            @Override
            protected void updateItem(InsCompaniesEntity item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });


        insCompaniesTableView = new TableView<>();
        insCompaniesTableView.setEditable(true);
        insCompaniesTableView.setItems(observableList);
        insCompaniesTableView.getColumns().addAll(nameColumn, actions);

        return insCompaniesTableView;
    }
}

