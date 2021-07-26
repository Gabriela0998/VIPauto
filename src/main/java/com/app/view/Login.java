package com.app.view;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.app.controller.LoginController;

import java.io.IOException;
import java.sql.SQLException;

import static com.app.helper.ValidationHelper.getHBox;

public class Login {
    public static Stage subStage;
    public Login()
    {
        subStage = new Stage();
        Scene scene = new Scene(createVBoxLayout(), 250, 200);
        subStage.setTitle("Вход");
        subStage.setScene(scene);
        subStage.show();
    }

    public VBox createVBoxLayout() {
        VBox vbox = new VBox();

        vbox.setSpacing(10);
        vbox.setPadding(new Insets(5));
        vbox.setAlignment(Pos.CENTER_LEFT);

        HBox buttonBox = getHBox();


        Label userLabel=new Label("Потребителско име ");
        Label passLabel=new Label("Парола ");
        TextField userTextField=new TextField();
        PasswordField passwordField=new PasswordField();
        Button loginButton=new Button("Вход");
        Button regButton=new Button("Регистрация");
        regButton.setOnAction(event -> {subStage.close(); new Register();});
        loginButton.setOnAction(eve-> {
                String lcUsername = String.valueOf(userTextField.getText());
                String lcPassword = String.valueOf(passwordField.getText());
            try {
                new LoginController(lcUsername, lcPassword, subStage);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        buttonBox.getChildren().addAll(loginButton, regButton);
        vbox.getChildren().addAll(userLabel,userTextField,
                passLabel,passwordField, buttonBox);

        return vbox;
    }

    public static Login startmenu(int a0, String nameUser){
        StartMenu menu = new StartMenu(a0, nameUser);
        subStage.close();
        return null;
    }
}
