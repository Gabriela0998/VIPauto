package com.app.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.app.entity.TypeCostEntity;
import com.app.manager.TableManager;
import com.app.view.TypeCost;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.manager.DatabaseManager.getConnection;

public class TypeCostController {
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    static Connection conn = null; // connection object
    Statement stmt = null; // statement object
    static ResultSet rs = null; // result set object
    public String name = "";
    public int id = 0;
    public String text = "";
    //Connection conn = getConnection();
    public TypeCostController(){
        String table = "TypeCost";
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

    public static TypeCostEntity vis(TypeCostEntity typeCost) {
        String sql = "SELECT * FROM typecost WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, typeCost.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nameTF = resultSet.getString("name");
                String idTF = resultSet.getString("id");
                TypeCost.visible(nameTF,idTF);

                return typeCost;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static TypeCostEntity show() {
        TypeCostEntity typeCost = new TypeCostEntity();
        String sql = "SELECT * FROM typecost LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
           // preparedStatement.setInt(1, typeCost.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nameTF = resultSet.getString("name");
                String idTF = resultSet.getString("id");
                TypeCost.visible(nameTF,idTF);

                return typeCost;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static TypeCostEntity getById(TypeCostEntity TypeCost) {
        String sql = "SELECT * FROM typecost WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, TypeCost.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Внимание!");
                alert.setHeaderText("Вие ще редактирате запис!");
                alert.setContentText("Желаете ли да продължите?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                update(TypeCost);}
            }else {
                insert(TypeCost);
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static TypeCostEntity insert(TypeCostEntity TypeCost) {
        String sql = "INSERT INTO `typecost`(name, id)" +
                "VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, TypeCost.getName());
            preparedStatement.setInt(2, TypeCost.getId());
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                TypeCost.setId(resultSet.getInt("id"));
                TypeCost.setName(resultSet.getString("name"));

                return TypeCost;
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static TypeCostEntity update(TypeCostEntity TypeCost) {
        String sql = "UPDATE `typecost`" +
                "SET name = ? " +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, TypeCost.getName());
            preparedStatement.setInt(2, TypeCost.getId());
            //preparedStatement.setInt(13, TypeCost.getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                TypeCost.setId(resultSet.getInt("id"));
                TypeCost.setName(resultSet.getString("name"));

                //ao = resultSet.getInt("A0");
                //nameUser = resultSet.getString("name");

                return TypeCost;
            }

        } catch (Exception e) {

        }
        return null;
    }
    public List<TypeCostEntity> getByCode(String code) {
        List<TypeCostEntity> typeCostList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `typecost` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    TypeCostEntity typeCostEntity = new TypeCostEntity();
                    typeCostEntity.setId(resultSet.getInt("id"));
                    typeCostEntity.setName(resultSet.getString("name"));

                    typeCostList.add(typeCostEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by code method: ", e.getStackTrace());
        }

        return typeCostList;
    }
    public List<TypeCostEntity> getByName(String name) {
        List<TypeCostEntity> typeCostList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `typecost` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    TypeCostEntity typeCost = new TypeCostEntity();
                    typeCost.setId(resultSet.getInt("id"));
                    typeCost.setName(resultSet.getString("name"));

                    typeCostList.add(typeCost);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by name method: ", e.getStackTrace());
        }

        return typeCostList;
    }
    public static TypeCostController idNum() {
        TypeCostController typeCost = new TypeCostController();
        String sql = "SELECT (MAX(id)+1) as idnum FROM typecost";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, brand.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String idTF = resultSet.getString("idnum");
                TypeCost.idNumber(idTF);

                return typeCost;
            }

        } catch (Exception e) {
        }
        return null;
    }

    public static TypeCostEntity delete(TypeCostEntity typeCost) {
        String sql = "DELETE FROM `typecost`" +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, typeCost.getId());
            preparedStatement.execute();

        } catch (Exception e) {

        }
        return null;
    }
    public List<TypeCostEntity> getAll() {
        List<TypeCostEntity> typeCostEntities = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `typecost`";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    TypeCostEntity typeCostEntity = new TypeCostEntity();
                    typeCostEntity.setId(resultSet.getInt("id"));
                    typeCostEntity.setName(resultSet.getString("name"));

                    typeCostEntities.add(typeCostEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get all faculties method: ", e.getStackTrace());
        }

        return typeCostEntities;
    }
}
