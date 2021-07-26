package com.app.controller;

import com.app.entity.PeopleEntity;
import com.app.entity.PeopleLicenceEntity;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

import static com.app.manager.DatabaseManager.getConnection;

public class PeopleLicenceController {
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    static Connection conn = null; // connection object
    Statement stmt = null; // statement object
    static ResultSet rs = null; // result set object
    public String name = "";
    public int id = 0;
    public String text = "";
    static String comboBox = "";
    //Connection conn = getConnection();int idPeople,
    public PeopleLicenceController(){
    }
    public static PeopleLicenceEntity delete(Integer idPeople) {
        String sql = "DELETE FROM `peoplelicence`" +
                "WHERE id_people = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, idPeople);
            preparedStatement.execute();

        } catch (Exception e) {

        }
        return null;
    }
    public static PeopleLicenceEntity getAll(Integer idLicence, Integer idPeople) {
        String sql = "SELECT * FROM peoplelicence WHERE id_licence = ?, id_people = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, idLicence);
            preparedStatement.setInt(2, idPeople);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return null;
            }else {
                insert(idLicence, idPeople);
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static PeopleLicenceEntity insert(Integer idLicence, Integer idPeople) {
        PeopleLicenceEntity peopleLicenceEntities = new PeopleLicenceEntity();
        String sql = "INSERT INTO `peoplelicence`(`id_licence`, `id_people`)" +
                "VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, idLicence);
            preparedStatement.setInt(2, idPeople);

            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                peopleLicenceEntities.setIdPeople(resultSet.getInt("id_people"));
                peopleLicenceEntities.setIdLicence(resultSet.getInt("id_licence"));
                return peopleLicenceEntities;
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get all faculties method: ", e.getStackTrace());
        }

        return null;
    }
}

