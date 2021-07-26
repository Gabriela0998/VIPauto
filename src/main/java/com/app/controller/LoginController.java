package com.app.controller;

import javafx.stage.Stage;
import com.app.entity.UserEntity;
import com.app.view.Login;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.*;

import static com.app.manager.DatabaseManager.getConnection;

public class LoginController {
    private String lcUserName;
    private String lcPassword;
    int ao;
    String nameUser;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    Connection conn = null; // connection object
    Statement stmt = null; // statement object
    ResultSet rs = null; // result set object
    public String name = "";
    public String password = "";
    //Connection conn = getConnection();

    public LoginController(String lcUsername, String lcPassword, Stage subStage) throws IOException, ClassNotFoundException, SQLException {
        lcUserName = lcUsername;
        lcPassword = lcPassword;

        //String table = "login";
        //TableManager newTable = new TableManager(table);
        handleButtonAction(lcUserName, lcPassword, subStage);

    }


    private void handleButtonAction(String lcUserName, String lcPassword, Stage subStage) {
        ActionEvent event;
        name = lcUserName;
        password = lcPassword;
        try {
            conn = getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "SELECT * FROM user WHERE name = ? and password = MD5(?)";

        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);

            /*UserEntity user = new UserEntity();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setA0(resultSet.getInt("A0"));*/
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {

            } else {
                ao = resultSet.getInt("AO");
                nameUser = resultSet.getString("name");
                Login.startmenu(ao, nameUser);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserEntity getById(String name, String password) {
           String sql = "SELECT * FROM user WHERE name = ? and password = MD5(?)";
            try {
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        UserEntity user = new UserEntity();
                        user.setId(resultSet.getInt("id"));
                        user.setName(resultSet.getString("name"));
                        user.setA0(resultSet.getInt("AO"));

                        ao = resultSet.getInt("AO");
                        nameUser = resultSet.getString("name");

                        return user;
                    }

            } catch (Exception e) {

            }
        return null;
    }

}