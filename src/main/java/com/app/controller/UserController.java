package com.app.controller;

import javafx.scene.control.Alert;
import com.app.entity.UserEntity;
import com.app.view.Register;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.app.manager.DatabaseManager.getConnection;

public class UserController {
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    static Connection conn = null; // connection object
    Statement stmt = null; // statement object
    static ResultSet rs = null; // result set object
    public String name = "";
    public int id = 0;
    public String text = "";
    static String comboBox = "";

    public static UserEntity getById(UserEntity User) {
        String sql = "SELECT * FROM user WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, User.getName());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Внимание!");
                alert.setHeaderText("Вече съществува такъв потребител!");
                return null;
            }else {
                insert(User);
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static UserEntity next(int idTF) {
        String sql = "SELECT * FROM user WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, idTF);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
              int itype =   resultSet.getInt("type");
                Register.next(itype);
            }else {

            }
        } catch (Exception e) {
        }
        return null;
    }

    public static UserEntity insert(UserEntity User) {
        String sql = "INSERT INTO `user`(name, id, password, type)" +
                "VALUES (?, ?, MD5(?), ?)";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, User.getName());
            preparedStatement.setInt(2, User.getId());
            preparedStatement.setString(3, User.getPassword());
            preparedStatement.setInt(4, User.getType());
            //preparedStatement.setInt(4, 0);
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User.setId(resultSet.getInt("id"));
                User.setName(resultSet.getString("name"));
                User.setPassword(resultSet.getString("password"));
                User.setType(resultSet.getInt("type"));

                return User;
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static UserEntity update(UserEntity user) {
        String sql = "UPDATE `user` SET `AO`=0, `id_type`=? WHERE id=?" ;
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, user.getId_type());
            //preparedStatement.setInt(2, user.getA0());
            preparedStatement.setInt(2, user.getId());
            //preparedStatement.setInt(13, Brand.getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));

                return user;
            }

        } catch (Exception e) {

        }
        return null;
    }
    public static UserEntity idNum() {
        UserEntity brand = new UserEntity();
        String sql = "SELECT (MAX(id)+1) as idnum FROM user";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, brand.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer idTF = resultSet.getInt("idnum");
                Register.idNumber(idTF);

                return brand;
            }

        } catch (Exception e) {
        }
        return null;
    }
}
