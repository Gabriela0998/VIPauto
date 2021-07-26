package com.app.helper;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import com.app.entity.CostEntity;

import static com.app.helper.ValidationHelper.getHBox;

public class CostHelper {
    public static Integer idBrand;
    public static void setGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(15, 40, 40, 40));

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints columnOneConstraints = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        ColumnConstraints columnTwoConstrains = new ColumnConstraints(250, 250, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnThreeConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnFourConstrains = new ColumnConstraints(250, 250, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnFiveConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnSixConstrains = new ColumnConstraints(250, 250, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);


        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains, columnThreeConstrains,
                columnFourConstrains, columnFiveConstrains, columnSixConstrains);
    }
    public static void setGridPane2(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(15, 40, 40, 40));

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints columnOneConstraints = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnThreeConstraints = new ColumnConstraints(80, 80, Double.MAX_VALUE);
        columnThreeConstraints.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnFourConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnFourConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains, columnThreeConstraints, columnFourConstrains);
    }
    public static Label addLabel(GridPane gridPane, String labelText, int colIndex, int rowIndex) {
        Label label = new Label(labelText);
        gridPane.add(label, colIndex, rowIndex);

        return label;
    }

    public static TextField addTextField(GridPane gridPane, String promptText, int colIndex, int rowIndex) {
        TextField textField = new TextField();
        textField.setPrefHeight(40);
        textField.setPromptText("" + promptText);
        gridPane.add(textField, colIndex, rowIndex);

        return textField;
    }

    public static DatePicker addDatePicker(GridPane gridPane, String promptText, int colIndex, int rowIndex) {
        DatePicker textField = new DatePicker();
        textField.setPrefHeight(40);
        textField.setPromptText("" + promptText);
        gridPane.add(textField, colIndex, rowIndex);

        return textField;
    }

    public static String capitalizeFirstLetter(String input) {
        return input.substring(0, 1).toUpperCase().concat(input.substring(1));
    }

    public static void initSearchSection(GridPane gridPane, Button removeButton, Button addButton, Button exitButton,
                                         String promptText, int colIndex, int rowindex) {

        HBox searchBox = getHBox();

        GridPane.setHalignment(searchBox, HPos.CENTER);
        removeButton.setPrefWidth(120);
        addButton.setPrefWidth(120);
        exitButton.setPrefWidth(120);

        searchBox.getChildren().addAll(removeButton, addButton, exitButton);
        gridPane.add(searchBox, colIndex, rowindex, 6, 1);
    }

    public static void clearForm(GridPane gridPane, CostEntity cost, TextField idTF, TextField nameTF) {
        nameTF.clear();
        idTF.clear();
    }
    public static void comboView(GridPane gridPane, String nameComboBox, ComboBox combo){
        //CostService.getExistingCostIdBrand(nameComboBox);

        combo = ViewHelper.getComboBoxModel(gridPane, 0, 6, idBrand);
        combo.setDisable(false);
    }

    public static void idNumberBrand(String id) {
        //idTF.setText(id);
        idBrand = Integer.valueOf(id);
        if(id=="0" || id==null){
            //idTF.setText("1");
            idBrand = 0;
        }

    }
}

