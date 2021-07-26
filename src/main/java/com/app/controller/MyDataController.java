package com.app.controller;

import com.app.entity.MyDataEntity;
import com.app.entity.UserEntity;
import com.app.manager.TableManager;
import com.app.view.*;

import java.io.IOException;
import java.sql.*;

import static com.app.manager.DatabaseManager.getConnection;


public class MyDataController {
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    static Connection conn = null; // connection object
    Statement stmt = null; // statement object
    static ResultSet rs = null; // result set object
    public String name = "";
    public String password = "";
    //Connection conn = getConnection();
    public MyDataController(){
        String table = "MyData";
        try {
            TableManager newTable = new TableManager(table);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserEntity read() {
        String sql = "SELECT * FROM mydata WHERE id = 1";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
           // preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                /*UserEntity user = new UserEntity();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setA0(resultSet.getInt("A0"));*/

                String nameTF = resultSet.getString("name");
                String country = resultSet.getString("country");
                String city = resultSet.getString("city");
                String address = resultSet.getString("address");
                String eik = resultSet.getString("EIK");
                String dds = resultSet.getString("DDS");
                String bank = resultSet.getString("bank");
                String iban = resultSet.getString("iban");
                String bic = resultSet.getString("bic");
                String manager = resultSet.getString("manager");
                String place = resultSet.getString("place");
                String phone = resultSet.getString("phone");
                MyData.visible(nameTF,country,city, address, eik, dds, bank, iban, bic, manager, place, phone);

                //return user;
            }

        } catch (Exception e) {

        }
        return null;
    }
    public static MyDataEntity insert(MyDataEntity myData) {
        String sql = "UPDATE `mydata`" +
                "SET name = ?, city = ?, country = ?, address = ?, eik = ?, dds = ?, bank = ?, iban = ?, bic = ?, manager = ?, place = ?, phone = ? " +
                "WHERE id = 1";
        try (Connection conn = getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ){
            preparedStatement.setString(1, myData.getName());
            preparedStatement.setString(2, myData.getCity());
            preparedStatement.setString(3, myData.getCountry());
            preparedStatement.setString(4, myData.getAddress());
            preparedStatement.setString(5, myData.getEIK());
            preparedStatement.setString(6, myData.getDDS());
            preparedStatement.setString(7, myData.getBank());
            preparedStatement.setString(8, myData.getIBAN());
            preparedStatement.setString(9, myData.getBIC());
            preparedStatement.setString(10, myData.getManager());
            preparedStatement.setString(11, myData.getPlace());
            preparedStatement.setString(12, myData.getPhone());
            //preparedStatement.setInt(13, myData.getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                myData.setId(resultSet.getInt("id"));
                myData.setName(resultSet.getString("name"));
                myData.setEIK(resultSet.getString("EIK"));
                myData.setAddress(resultSet.getString("address"));

                //ao = resultSet.getInt("A0");
                //nameUser = resultSet.getString("name");

                return myData;
            }

        } catch (Exception e) {

        }
        return null;
    }

    public static MyDataEntity idNum() {
        MyDataEntity brand = new MyDataEntity();
        String sql = "SELECT (MAX(id)+1) as idnum FROM mydata";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, brand.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer idTF = resultSet.getInt("idnum");
                NextMyData.idNumber(idTF);

                return brand;
            }

        } catch (Exception e) {
        }
        return null;
    }
}
