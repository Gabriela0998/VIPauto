package com.app;

import javafx.application.Application;
import javafx.stage.Stage;
import com.app.manager.DatabaseManager;
import com.app.manager.TableManager;

import java.sql.Connection;

import static com.app.manager.DatabaseManager.getConnection;

public class Main  extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Connection conn = getConnection();

        String table = "login";
        TableManager newTable = new TableManager(table);

    }
    public static void main(String[] args) {
        DatabaseManager db = new DatabaseManager();
        launch(args);
    }
}
