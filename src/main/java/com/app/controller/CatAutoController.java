package com.app.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.app.entity.CatAutoEntity;
import com.app.manager.TableManager;
import com.app.view.CatAuto;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.manager.DatabaseManager.getConnection;

public class CatAutoController  {
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    static Connection conn = null; // connection object
    Statement stmt = null; // statement object
    static ResultSet rs = null; // result set object
    public String name = "";
    public int id = 0;
    public String text = "";
    //Connection conn = getConnection();
    public CatAutoController(){
        String table = "CatAuto";
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

    public static CatAutoEntity vis(CatAutoEntity catAuto) {
        String sql = "SELECT * FROM catauto WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, catAuto.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nameTF = resultSet.getString("name");
                String idTF = resultSet.getString("id");
                CatAuto.visible(nameTF,idTF);

                return catAuto;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static CatAutoEntity show() {
        CatAutoEntity catAuto = new CatAutoEntity();
        String sql = "SELECT * FROM catauto LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
           // preparedStatement.setInt(1, catAuto.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nameTF = resultSet.getString("name");
                String idTF = resultSet.getString("id");
                CatAuto.visible(nameTF,idTF);

                return catAuto;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static CatAutoEntity getById(CatAutoEntity CatAuto) {
        String sql = "SELECT * FROM catauto WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, CatAuto.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Внимание!");
                alert.setHeaderText("Вие ще редактирате запис!");
                alert.setContentText("Желаете ли да продължите?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                update(CatAuto);}
            }else {
                insert(CatAuto);
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static CatAutoEntity insert(CatAutoEntity CatAuto) {
        String sql = "INSERT INTO `catauto`(name, id)" +
                "VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, CatAuto.getName());
            preparedStatement.setInt(2, CatAuto.getId());
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                CatAuto.setId(resultSet.getInt("id"));
                CatAuto.setName(resultSet.getString("name"));

                return CatAuto;
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static CatAutoEntity update(CatAutoEntity CatAuto) {
        String sql = "UPDATE `catauto`" +
                "SET name = ? " +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, CatAuto.getName());
            preparedStatement.setInt(2, CatAuto.getId());
            //preparedStatement.setInt(13, CatAuto.getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                CatAuto.setId(resultSet.getInt("id"));
                CatAuto.setName(resultSet.getString("name"));

                //ao = resultSet.getInt("A0");
                //nameUser = resultSet.getString("name");

                return CatAuto;
            }

        } catch (Exception e) {

        }
        return null;
    }
    public List<CatAutoEntity> getByCode(String code) {
        List<CatAutoEntity> catAutoList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `catauto` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    CatAutoEntity catAutoEntity = new CatAutoEntity();
                    catAutoEntity.setId(resultSet.getInt("id"));
                    catAutoEntity.setName(resultSet.getString("name"));

                    catAutoList.add(catAutoEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by code method: ", e.getStackTrace());
        }

        return catAutoList;
    }
    public List<CatAutoEntity> getByName(String name) {
        List<CatAutoEntity> catAutoList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `catauto` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    CatAutoEntity catAuto = new CatAutoEntity();
                    catAuto.setId(resultSet.getInt("id"));
                    catAuto.setName(resultSet.getString("name"));

                    catAutoList.add(catAuto);
                }
            }
        } catch (Exception e) {

        }

        return catAutoList;
    }
    public static CatAutoController idNum() {
        CatAutoController catAuto = new CatAutoController();
        String sql = "SELECT (MAX(id)+1) as idnum FROM catauto";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, brand.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String idTF = resultSet.getString("idnum");
                CatAuto.idNumber(idTF);

                return catAuto;
            }

        } catch (Exception e) {
        }
        return null;
    }

    public static CatAutoEntity delete(CatAutoEntity catAuto) {
        String sql = "DELETE FROM `catauto`" +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, catAuto.getId());
            preparedStatement.execute();

        } catch (Exception e) {

        }
        return null;
    }
    public List<CatAutoEntity> getAll() {
        List<CatAutoEntity> catAutoEntities = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `catauto`";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    CatAutoEntity catAutoEntity = new CatAutoEntity();
                    catAutoEntity.setId(resultSet.getInt("id"));
                    catAutoEntity.setName(resultSet.getString("name"));

                    catAutoEntities.add(catAutoEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get all faculties method: ", e.getStackTrace());
        }

        return catAutoEntities;
    }
}
