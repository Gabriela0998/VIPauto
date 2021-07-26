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
import com.app.controller.BrandController;
import com.app.entity.BrandEntity;
import com.app.entity.CatAutoEntity;
import com.app.helper.BrandHelper;
import com.app.helper.EditingCell;
import com.app.helper.Helpers;
import com.app.helper.ViewHelper;
import com.app.service.BrandService;

import java.text.ParseException;
import java.util.Optional;

public class Brand {
    private BrandService brandService = new BrandService();
    private BrandEntity brand = new BrandEntity();
    private TableView<BrandEntity> brandTableView;
    private ObservableList<BrandEntity> brandObservableList;
    Stage subStage;

    static public TextField nameTF;
    static public TextField idTF;
    static public ComboBox<CatAutoEntity> catAuto;
    public Brand() {
        subStage = new Stage();
        subStage.setTitle("Марка");
        Scene scene = new Scene(createbrandCreateFormPane(), 1200, 700);
        subStage.setScene(scene);
        subStage.show();
    }

    public GridPane createbrandCreateFormPane() {
        GridPane gridPane = new GridPane();

        BrandHelper.setGridPane(gridPane);
        addUIControls(gridPane);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Марка");
        headerLabel.setFont(new Font("Arial", 26));

        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        //brand name label and text
        BrandHelper.addLabel(gridPane, "№: ", 0, 1);
        idTF = BrandHelper.addTextField(gridPane, "", 1, 1);
        //ValidationHelper.lettersOnly(nameTF, 200);
        //brand name label and text
        BrandHelper.addLabel(gridPane, "Наименование на марката: ", 0, 2);
        nameTF = BrandHelper.addTextField(gridPane, "", 1, 2);
        //ValidationHelper.lettersOnly(nameTF, 200);

        catAuto = ViewHelper.getComboBox(gridPane, 0, 3);


        //TextField searchBoxTF = new TextField();
        Button editButton = new Button("Нов");
        Button addButton = new Button("Запис");
        Button exitButton = new Button("Изход");

        BrandHelper.initSearchSection(gridPane, editButton, addButton, exitButton,
                "", 1, 4);

        gridPane.add(getTableView(getbrandInfo(false, null), gridPane),0,6, 2, 1);
        BrandController.show();

        addButton.setOnAction(e -> {
            try {
                onSubmit(gridPane, brand, idTF, nameTF, catAuto);
                gridPane.add(getTableView(getbrandInfo(false, null), gridPane),0,6, 2, 1);
            } catch (ParseException ex) {
                // LOGGER.error("Exception in submit button set on action: ", ex.getStackTrace());
            }
        });
        editButton.setOnAction(e -> {
            BrandHelper.clearForm(gridPane, brand, idTF, nameTF);
            BrandController.idNum();
            catAuto = ViewHelper.getComboBox(gridPane, 0, 3);
            catAuto.setPromptText("");
        });
        exitButton.setOnAction(e -> {
            subStage.close();
        });
    }
    private void onSubmit(GridPane gridPane, BrandEntity Brand, TextField idTF, TextField nameTF, ComboBox catAuto) throws ParseException {
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

        createBrand(nameTF.getText(), Integer.parseInt(idTF.getText()),catAuto.getValue().toString());
        Helpers.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(),
                "Записа успешен!", "Успешно записахте данните на тип/купе на МПС-то!");
    }


    private void createBrand(String name, int id, String id_cat) {
        BrandService BrandService = new BrandService();

        BrandEntity Brand = new BrandEntity();
        Brand.setId(id);
        Brand.setName(name);
        Brand.setId_catAuto(BrandService.getExistingBrandId(id_cat));

        BrandService.getById(Brand);
    }

    public static void visible(String name, String id, String id_combo) {
        nameTF.setText(name);
        idTF.setText(id);
        catAuto.setPromptText(id_combo);
    }
    public static void idNumber(String id) {
        idTF.setText(id);

        if(id=="0" || id==null){
            idTF.setText("1");
        }

    }
    private ObservableList<BrandEntity> getbrandInfo(boolean isbrandSearched, String criteria) {
        brandObservableList = FXCollections.observableArrayList();

        if (isbrandSearched) {
            for (BrandEntity brand : brandService.getSearched(BrandHelper.capitalizeFirstLetter(criteria))) {
                brandObservableList.add(brand);
            }
        } else {
            for (BrandEntity brand : brandService.getAll()) {
                brandObservableList.add(brand);
            }
        }

        return brandObservableList;
    }
    private TableView getTableView(ObservableList<BrandEntity> observableList, GridPane gridPane) {
        Callback<TableColumn<BrandEntity, String>, TableCell<BrandEntity, String>> cellFactory
                = (TableColumn<BrandEntity, String> param) -> new EditingCell<BrandEntity>();

        //Column for brand name
        TableColumn<BrandEntity, String> nameColumn = new TableColumn<>("Наименование");
        nameColumn.setMinWidth(500);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(cellFactory);
        nameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<BrandEntity, String> t) -> {
                    ((BrandEntity) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setName(t.getNewValue());

                });

        TableColumn<BrandEntity, BrandEntity> actions = new TableColumn<>("Действия");
        actions.setMinWidth(400);
        actions.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actions.setCellFactory(param -> new TableCell<BrandEntity, BrandEntity>() {
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
                        BrandEntity brand = getTableView().getItems().get(getIndex());
                        BrandService brandService = new BrandService();
                        brandService.delete(brand);
                        getTableView().getItems().remove(brand);
                    }
                });

                editButton.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Вие ще редактирате запис!");
                    alert.setContentText("Желаете ли да продължите?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        BrandEntity brand = getTableView().getItems().get(getIndex());
                        BrandService brandService = new BrandService();
                        brandService.update(brand);
                    }
                });

                selectButton.setOnAction(event -> {
                    BrandEntity brand = getTableView().getItems().get(getIndex());
                    BrandService brandService = new BrandService();
                    brandService.vis(brand);

                });
            }

            @Override
            protected void updateItem(BrandEntity item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });


        brandTableView = new TableView<>();
        brandTableView.setEditable(true);
        brandTableView.setItems(observableList);
        brandTableView.getColumns().addAll(nameColumn, actions);

        return brandTableView;
    }
}
