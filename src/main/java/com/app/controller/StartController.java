package com.app.controller;

import com.app.entity.UserEntity;
import com.app.view.Login;
import com.app.view.Register;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.app.manager.DatabaseManager.getConnection;

public class StartController {
    static Boolean result = false;

    private String lcUserName;
    private String lcPassword;
    int ao;
    String nameUser;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    static Connection conn = null; // connection object
    Statement stmt = null; // statement object
    ResultSet rs = null; // result set object
    public String name = "";
    public String password = "";
    //Connection conn = getConnection();

    public static List<UserEntity> getAll() {
        List<UserEntity> userEntities = new ArrayList<>();
        String sql = "SELECT COUNT(*) as count FROM `user`";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");

                if (count==0) {
                    Register register = new Register();
                } else {
                    Login log = new Login();
                }
                return null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return userEntities;
    }
}
