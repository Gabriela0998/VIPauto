package com.app.helper;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import com.app.entity.BrandEntity;

import static com.app.helper.ValidationHelper.getHBox;

public class BrandHelper {
    public static void setGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(15, 40, 40, 40));

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints columnOneConstraints = new ColumnConstraints(300, 300, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        ColumnConstraints columnTwoConstrains = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
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
        gridPane.add(searchBox, colIndex, rowindex, 2, 1);
    }

    public static void clearForm(GridPane gridPane, BrandEntity brand, TextField idTF, TextField nameTF) {
        nameTF.clear();
        idTF.clear();
    }
}

