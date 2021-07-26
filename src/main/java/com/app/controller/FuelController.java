package com.app.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.app.entity.FuelEntity;
import com.app.manager.TableManager;
import com.app.view.Fuel;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.manager.DatabaseManager.getConnection;

public class FuelController {
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    static Connection conn = null; // connection object
    Statement stmt = null; // statement object
    static ResultSet rs = null; // result set object
    public String name = "";
    public int id = 0;
    public String text = "";
    //Connection conn = getConnection();
    public FuelController(){
        String table = "Fuel";
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

    public static FuelEntity vis(FuelEntity fuel) {
        String sql = "SELECT * FROM fuel WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, fuel.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nameTF = resultSet.getString("name");
                String idTF = resultSet.getString("id");
                Fuel.visible(nameTF,idTF);

                return fuel;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static FuelEntity show() {
        FuelEntity fuel = new FuelEntity();
        String sql = "SELECT * FROM fuel LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
           // preparedStatement.setInt(1, fuel.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nameTF = resultSet.getString("name");
                String idTF = resultSet.getString("id");
                Fuel.visible(nameTF,idTF);

                return fuel;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static FuelEntity getById(FuelEntity Fuel) {
        String sql = "SELECT * FROM fuel WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, Fuel.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Внимание!");
                alert.setHeaderText("Вие ще редактирате запис!");
                alert.setContentText("Желаете ли да продължите?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                update(Fuel);}
            }else {
                insert(Fuel);
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static FuelEntity insert(FuelEntity Fuel) {
        String sql = "INSERT INTO `fuel`(name, id)" +
                "VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, Fuel.getName());
            preparedStatement.setInt(2, Fuel.getId());
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Fuel.setId(resultSet.getInt("id"));
                Fuel.setName(resultSet.getString("name"));

                return Fuel;
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static FuelEntity update(FuelEntity Fuel) {
        String sql = "UPDATE `fuel`" +
                "SET name = ? " +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, Fuel.getName());
            preparedStatement.setInt(2, Fuel.getId());
            //preparedStatement.setInt(13, Fuel.getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Fuel.setId(resultSet.getInt("id"));
                Fuel.setName(resultSet.getString("name"));

                //ao = resultSet.getInt("A0");
                //nameUser = resultSet.getString("name");

                return Fuel;
            }

        } catch (Exception e) {

        }
        return null;
    }
    public List<FuelEntity> getByCode(String code) {
        List<FuelEntity> fuelList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `fuel` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    FuelEntity fuelEntity = new FuelEntity();
                    fuelEntity.setId(resultSet.getInt("id"));
                    fuelEntity.setName(resultSet.getString("name"));

                    fuelList.add(fuelEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by code method: ", e.getStackTrace());
        }

        return fuelList;
    }
    public List<FuelEntity> getByName(String name) {
        List<FuelEntity> fuelList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `fuel` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    FuelEntity fuel = new FuelEntity();
                    fuel.setId(resultSet.getInt("id"));
                    fuel.setName(resultSet.getString("name"));

                    fuelList.add(fuel);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by name method: ", e.getStackTrace());
        }

        return fuelList;
    }
    public static FuelController idNum() {
        FuelController fuel = new FuelController();
        String sql = "SELECT (MAX(id)+1) as idnum FROM fuel";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, brand.getId());
            resultSet = preparedStatement.executeQuery();
            String idTF="0";
            if (resultSet.next()) {
                idTF = resultSet.getString("idnum");
                if (idTF==null) {
                    Fuel.idNumber("1");
                }else{
                    Fuel.idNumber(idTF);
                }
            }


        } catch (Exception e) {
        }
        return null;
    }

    public static FuelEntity delete(FuelEntity fuel) {
        String sql = "DELETE FROM `fuel`" +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, fuel.getId());
            preparedStatement.execute();

        } catch (Exception e) {

        }
        return null;
    }
    public List<FuelEntity> getAll() {
        List<FuelEntity> fuelEntities = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `fuel`";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    FuelEntity fuelEntity = new FuelEntity();
                    fuelEntity.setId(resultSet.getInt("id"));
                    fuelEntity.setName(resultSet.getString("name"));

                    fuelEntities.add(fuelEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get all faculties method: ", e.getStackTrace());
        }

        return fuelEntities;
    }
}
