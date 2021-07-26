package com.app.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.app.entity.FirmsEntity;
import com.app.manager.TableManager;
import com.app.view.Firm;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.manager.DatabaseManager.getConnection;
import static com.app.manager.DatabaseManager.getConnection;


public class FirmsController {
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    static Connection conn = null; // connection object
    Statement stmt = null; // statement object
    static ResultSet rs = null; // result set object
    public String name = "";
    public String password = "";
    //Connection conn = getConnection();
    public FirmsController(){
        String table = "firms";
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

    public static FirmsEntity vis(FirmsEntity firms) {
        String sql = "SELECT * FROM firms WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, firms.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                /*UserEntity user = new UserEntity();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setA0(resultSet.getInt("A0"));*/

                String idTF = resultSet.getString("id");
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
                Firm.visible(idTF,nameTF,country,city, address, eik, dds, bank, iban, bic, manager, place, phone);

                //return user;
            }

        } catch (Exception e) {

        }
        return null;
    }
    public static FirmsEntity vis() {
        String sql = "SELECT * FROM firms LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement = conn.prepareStatement(sql);

            //preparedStatement.setInt(1, firms.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                /*UserEntity user = new UserEntity();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setA0(resultSet.getInt("A0"));*/

                String idTF = resultSet.getString("id");
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
                Firm.visible(idTF,nameTF,country,city, address, eik, dds, bank, iban, bic, manager, place, phone);

                //return user;
            }

        } catch (Exception e) {

        }
        return null;
    }

    public static FirmsEntity getById(FirmsEntity firms) {
        String sql = "SELECT * FROM firms WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, firms.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Внимание!");
                alert.setHeaderText("Вие ще редактирате запис!");
                alert.setContentText("Желаете ли да продължите?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    update(firms);}
            }else {
                insert(firms);
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static FirmsEntity idNum() {
        FirmsEntity firms = new FirmsEntity();
        String sql = "SELECT (MAX(id)+1) as idnum FROM firms";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, firms.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String idTF = resultSet.getString("idnum");
                Firm.idNumber(idTF);

                return firms;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static FirmsEntity insert(FirmsEntity firms) {
        String sql = "INSERT INTO `firms`(name, city, country, address, eik, dds, bank, iban, bic, manager, place, phone, id)" +
                "VALUES (?, ?, ?, ?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, firms.getName());
            preparedStatement.setString(2, firms.getCity());
            preparedStatement.setString(3, firms.getCountry());
            preparedStatement.setString(4, firms.getAddress());
            preparedStatement.setString(5, firms.getEIK());
            preparedStatement.setString(6, firms.getDDS());
            preparedStatement.setString(7, firms.getBank());
            preparedStatement.setString(8, firms.getIBAN());
            preparedStatement.setString(9, firms.getBIC());
            preparedStatement.setString(10, firms.getManager());
            preparedStatement.setString(11, firms.getPlace());
            preparedStatement.setString(12, firms.getPhone());
            preparedStatement.setInt(13, firms.getId());
            //preparedStatement.setInt(13, firms.getId());
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                firms.setId(resultSet.getInt("id"));
                firms.setName(resultSet.getString("name"));
                firms.setEIK(resultSet.getString("EIK"));
                firms.setAddress(resultSet.getString("address"));

                //ao = resultSet.getInt("A0");
                //nameUser = resultSet.getString("name");

                return firms;
            }

        } catch (Exception e) {

        }
        return null;
    }
    public static FirmsEntity update(FirmsEntity firms) {
        String sql = "UPDATE `firms`" +
                "SET name = ?, city = ?, country = ?, address = ?, eik = ?, dds = ?, bank = ?, iban = ?, bic = ?, manager = ?, place = ?, phone = ? " +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, firms.getName());
            preparedStatement.setString(2, firms.getCity());
            preparedStatement.setString(3, firms.getCountry());
            preparedStatement.setString(4, firms.getAddress());
            preparedStatement.setString(5, firms.getEIK());
            preparedStatement.setString(6, firms.getDDS());
            preparedStatement.setString(7, firms.getBank());
            preparedStatement.setString(8, firms.getIBAN());
            preparedStatement.setString(9, firms.getBIC());
            preparedStatement.setString(10, firms.getManager());
            preparedStatement.setString(11, firms.getPlace());
            preparedStatement.setString(12, firms.getPhone());
            preparedStatement.setInt(13, firms.getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                firms.setId(resultSet.getInt("id"));
                firms.setName(resultSet.getString("name"));
                firms.setEIK(resultSet.getString("EIK"));
                firms.setAddress(resultSet.getString("address"));

                //ao = resultSet.getInt("A0");
                //nameUser = resultSet.getString("name");

                return firms;
            }

        } catch (Exception e) {

        }
        return null;
    }
    public static FirmsEntity delete(FirmsEntity firms) {
        String sql = "DELETE FROM `firms`" +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, firms.getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                firms.setId(resultSet.getInt("id"));
                firms.setName(resultSet.getString("name"));
                firms.setEIK(resultSet.getString("EIK"));
                firms.setAddress(resultSet.getString("address"));

                //ao = resultSet.getInt("A0");
                //nameUser = resultSet.getString("name");

                return firms;
            }

        } catch (Exception e) {

        }
        return null;
    }
    public static List<FirmsEntity> getAll() {
        List<FirmsEntity> firmsEntities = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `firms`";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    FirmsEntity firmsEntity = new FirmsEntity();
                    firmsEntity.setId(resultSet.getInt("id"));
                    firmsEntity.setName(resultSet.getString("name"));
                    //firmsEntity.setId_catAuto(resultSet.getInt("id_cat"));

                    firmsEntities.add(firmsEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get all faculties method: ", e.getStackTrace());
        }

        return firmsEntities;
    }

    public static List<FirmsEntity> getByCode(String code) {
        List<FirmsEntity> firmsList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `firms` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    FirmsEntity firmsEntity = new FirmsEntity();
                    firmsEntity.setId(resultSet.getInt("id"));
                    firmsEntity.setName(resultSet.getString("name"));
                    //firmsEntity.setId_catAuto(resultSet.getInt("id_cat"));

                    firmsList.add(firmsEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by code method: ", e.getStackTrace());
        }

        return firmsList;
    }
    public static List<FirmsEntity> getByName(String name) {
        List<FirmsEntity> firmsList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `firms` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    FirmsEntity firms = new FirmsEntity();
                    firms.setId(resultSet.getInt("id"));
                    firms.setName(resultSet.getString("name"));
                    //firms.setId_catAuto(resultSet.getInt("id_cat"));

                    firmsList.add(firms);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by name method: ", e.getStackTrace());
        }

        return firmsList;
    }
}
