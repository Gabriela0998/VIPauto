package com.app.helper;

import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class ValidationHelper {
    public static void numbersOnly(TextField textField, Integer maxLength) {
        textField.addEventFilter(KeyEvent.KEY_TYPED, getNumbersOnlyHandler(maxLength));
        textField.focusedProperty().addListener((Observable observable) -> {
            textField.setText(textField.getText().trim());
        });
    }

    private static EventHandler<KeyEvent> getNumbersOnlyHandler(Integer maxLength) {
        return (KeyEvent event) -> {
            if (event.getSource() instanceof TextField) {
                TextField textField = (TextField) event.getSource();
                if (textField.getText().length() >= maxLength) {
                    event.consume();
                    showToolTip(textField, "This field contains only " + maxLength + " characters!");
                }
                if (!event.getCharacter().matches("[0-9.]")) {
                    event.consume();
                    showToolTip(textField, "This field should contains only numbers!");
                }
            }
        };
    }

    public static void lettersOnly(TextField textField, Integer maxLength) {
        textField.addEventFilter(KeyEvent.KEY_TYPED, getLettersOnlyHandler(maxLength));
        textField.focusedProperty().addListener((Observable observable) -> {
            textField.setText(textField.getText().trim());
        });
    }

    private static EventHandler<KeyEvent> getLettersOnlyHandler(Integer maxLength) {
        return (KeyEvent event) -> {
            if (event.getSource() instanceof TextField) {
                TextField textField = (TextField) event.getSource();
                if (textField.getText().length() >= maxLength) {
                    event.consume();
                    showToolTip(textField, "This field should contains only " + maxLength + " characters!");
                }
                if (!event.getCharacter().matches("[A-Za-zА-яа-я\\u0020\\u002D]")) {
                    event.consume();
                    showToolTip(textField, "This field should contains only letters!");
                }
            }
        };
    }


    private static void showToolTip(TextField textField, String mesage) {
        Point2D p = textField.localToScene(5.0, 24.0);
        final Tooltip tooltip = new Tooltip();
        tooltip.setText(mesage);
        textField.setTooltip(tooltip);
        tooltip.setAutoHide(true);

        tooltip.show(textField,
                p.getX() + textField.getScene().getX() + textField.getScene().getWindow().getX(),
                p.getY() + textField.getScene().getY() + textField.getScene().getWindow().getY());
    }


    public static HBox getHBox() {
        HBox searchBox = new HBox();
        searchBox.setSpacing(10);

        return searchBox;
    }
}

