package com.app.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.app.entity.InsCompaniesEntity;
import com.app.manager.TableManager;
import com.app.view.InsCompanies;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.manager.DatabaseManager.getConnection;

public class InsCompaniesController {
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    static Connection conn = null; // connection object
    Statement stmt = null; // statement object
    static ResultSet rs = null; // result set object
    public String name = "";
    public int id = 0;
    public String text = "";
    //Connection conn = getConnection();
    public InsCompaniesController(){
        String table = "InsCompanies";
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

    public static InsCompaniesEntity vis(InsCompaniesEntity insCompanies) {
        String sql = "SELECT * FROM inscompanies WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, insCompanies.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nameTF = resultSet.getString("name");
                String idTF = resultSet.getString("id");
                String phoneTF = resultSet.getString("phone");
                String extraTF = resultSet.getString("extra");
                InsCompanies.visible(nameTF,idTF,phoneTF,extraTF);

                return insCompanies;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static InsCompaniesEntity show() {
        InsCompaniesEntity insCompanies = new InsCompaniesEntity();
        String sql = "SELECT * FROM inscompanies LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
           // preparedStatement.setInt(1, insCompanies.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nameTF = resultSet.getString("name");
                String idTF = resultSet.getString("id");
                String phoneTF = resultSet.getString("phone");
                String extraTF = resultSet.getString("extra");
                InsCompanies.visible(nameTF,idTF, phoneTF, extraTF);

                return insCompanies;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static InsCompaniesEntity getById(InsCompaniesEntity InsCompanies) {
        String sql = "SELECT * FROM inscompanies WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, InsCompanies.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Внимание!");
                alert.setHeaderText("Вие ще редактирате запис!");
                alert.setContentText("Желаете ли да продължите?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                update(InsCompanies);}
            }else {
                insert(InsCompanies);
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static InsCompaniesEntity insert(InsCompaniesEntity InsCompanies) {
        String sql = "INSERT INTO `inscompanies`(name, id, phone, extra)" +
                "VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, InsCompanies.getName());
            preparedStatement.setInt(2, InsCompanies.getId());
            preparedStatement.setString(3, InsCompanies.getPhone());
            preparedStatement.setString(4, InsCompanies.getExtra());
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                InsCompanies.setId(resultSet.getInt("id"));
                InsCompanies.setName(resultSet.getString("name"));
                InsCompanies.setPhone(resultSet.getString("phone"));
                InsCompanies.setExtra(resultSet.getString("extra"));

                return InsCompanies;
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static InsCompaniesEntity update(InsCompaniesEntity InsCompanies) {
        String sql = "UPDATE `inscompanies`" +
                "SET name = ?, phone=?, extra=? " +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, InsCompanies.getName());
            preparedStatement.setString(2, InsCompanies.getPhone());
            preparedStatement.setString(3, InsCompanies.getExtra());
            preparedStatement.setInt(4, InsCompanies.getId());
            //preparedStatement.setInt(13, InsCompanies.getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                InsCompanies.setId(resultSet.getInt("id"));
                InsCompanies.setName(resultSet.getString("name"));
                InsCompanies.setPhone(resultSet.getString("phone"));
                InsCompanies.setExtra(resultSet.getString("extra"));

                //ao = resultSet.getInt("A0");
                //nameUser = resultSet.getString("name");

                return InsCompanies;
            }

        } catch (Exception e) {

        }
        return null;
    }
    public List<InsCompaniesEntity> getByCode(String code) {
        List<InsCompaniesEntity> insCompaniesList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `inscompanies` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    InsCompaniesEntity insCompaniesEntity = new InsCompaniesEntity();
                    insCompaniesEntity.setId(resultSet.getInt("id"));
                    insCompaniesEntity.setName(resultSet.getString("name"));
                    insCompaniesEntity.setPhone(resultSet.getString("phone"));
                    insCompaniesEntity.setExtra(resultSet.getString("extra"));

                    insCompaniesList.add(insCompaniesEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by code method: ", e.getStackTrace());
        }

        return insCompaniesList;
    }
    public List<InsCompaniesEntity> getByName(String name) {
        List<InsCompaniesEntity> insCompaniesList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `inscompanies` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    InsCompaniesEntity insCompanies = new InsCompaniesEntity();
                    insCompanies.setId(resultSet.getInt("id"));
                    insCompanies.setName(resultSet.getString("name"));

                    insCompaniesList.add(insCompanies);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by name method: ", e.getStackTrace());
        }

        return insCompaniesList;
    }
    public static InsCompaniesController idNum() {
        InsCompaniesController insCompanies = new InsCompaniesController();
        String sql = "SELECT (MAX(id)+1) as idnum FROM inscompanies";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, brand.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String idTF = resultSet.getString("idnum");
                InsCompanies.idNumber(idTF);

                return insCompanies;
            }

        } catch (Exception e) {
        }
        return null;
    }

    public static InsCompaniesEntity delete(InsCompaniesEntity insCompanies) {
        String sql = "DELETE FROM `inscompanies`" +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, insCompanies.getId());
            preparedStatement.execute();

        } catch (Exception e) {

        }
        return null;
    }
    public List<InsCompaniesEntity> getAll() {
        List<InsCompaniesEntity> insCompaniesEntities = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `inscompanies`";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    InsCompaniesEntity insCompaniesEntity = new InsCompaniesEntity();
                    insCompaniesEntity.setId(resultSet.getInt("id"));
                    insCompaniesEntity.setName(resultSet.getString("name"));

                    insCompaniesEntities.add(insCompaniesEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get all faculties method: ", e.getStackTrace());
        }

        return insCompaniesEntities;
    }
}
