package com.app.helper;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class Helpers{
    private static Helpers ourInstance = new Helpers();

    public static Helpers getInstance() {
        return ourInstance;
    }

    private Helpers() {
    }


    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.showAndWait();
    }
    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String headerText, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.showAndWait();
    }
}