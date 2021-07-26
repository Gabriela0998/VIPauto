package com.app.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    public static Boolean start;
    public static Connection getConnection() throws Exception{

        String driverName = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String serverName = "localhost:3306";
        //String serverName = "127.0.0.1";
        String mydatabase = "vauto";
        String url = "jdbc:mysql://" + serverName;

        String username = "root";
        String password = "";

        Connection connection = DriverManager.getConnection(url, username, password);
        url = "";
        Statement st = connection.createStatement();
        try {
            st.executeUpdate("CREATE DATABASE vauto");
            url = "jdbc:mysql://" + serverName + "/" + mydatabase;
        } catch (SQLException e) {
            url = "jdbc:mysql://" + serverName + "/" + mydatabase;
        }

        return DriverManager.getConnection(url, username, password);

    }
}
