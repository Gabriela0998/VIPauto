package com.app.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.app.entity.TypeDocumentEntity;
import com.app.manager.TableManager;
import com.app.view.TypeDocument;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.manager.DatabaseManager.getConnection;

public class TypeDocumentController {
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    static Connection conn = null; // connection object
    Statement stmt = null; // statement object
    static ResultSet rs = null; // result set object
    public String name = "";
    public int id = 0;
    public String text = "";
    //Connection conn = getConnection();
    public TypeDocumentController(){
        String table = "TypeDocument";
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

    public static TypeDocumentEntity vis(TypeDocumentEntity typeDocument) {
        String sql = "SELECT * FROM typedocument WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, typeDocument.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nameTF = resultSet.getString("name");
                String idTF = resultSet.getString("id");
                TypeDocument.visible(nameTF,idTF);

                return typeDocument;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static TypeDocumentEntity show() {
        TypeDocumentEntity typeDocument = new TypeDocumentEntity();
        String sql = "SELECT * FROM typedocument LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
           // preparedStatement.setInt(1, typeDocument.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nameTF = resultSet.getString("name");
                String idTF = resultSet.getString("id");
                TypeDocument.visible(nameTF,idTF);

                return typeDocument;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static TypeDocumentEntity getById(TypeDocumentEntity TypeDocument) {
        String sql = "SELECT * FROM typedocument WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, TypeDocument.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Внимание!");
                alert.setHeaderText("Вие ще редактирате запис!");
                alert.setContentText("Желаете ли да продължите?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                update(TypeDocument);}
            }else {
                insert(TypeDocument);
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static TypeDocumentEntity insert(TypeDocumentEntity TypeDocument) {
        String sql = "INSERT INTO `typedocument`(name, id)" +
                "VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, TypeDocument.getName());
            preparedStatement.setInt(2, TypeDocument.getId());
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                TypeDocument.setId(resultSet.getInt("id"));
                TypeDocument.setName(resultSet.getString("name"));

                return TypeDocument;
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static TypeDocumentEntity update(TypeDocumentEntity TypeDocument) {
        String sql = "UPDATE `typedocument`" +
                "SET name = ? " +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, TypeDocument.getName());
            preparedStatement.setInt(2, TypeDocument.getId());
            //preparedStatement.setInt(13, TypeDocument.getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                TypeDocument.setId(resultSet.getInt("id"));
                TypeDocument.setName(resultSet.getString("name"));

                //ao = resultSet.getInt("A0");
                //nameUser = resultSet.getString("name");

                return TypeDocument;
            }

        } catch (Exception e) {

        }
        return null;
    }
    public List<TypeDocumentEntity> getByCode(String code) {
        List<TypeDocumentEntity> typeDocumentList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `typedocument` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    TypeDocumentEntity typeDocumentEntity = new TypeDocumentEntity();
                    typeDocumentEntity.setId(resultSet.getInt("id"));
                    typeDocumentEntity.setName(resultSet.getString("name"));

                    typeDocumentList.add(typeDocumentEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by code method: ", e.getStackTrace());
        }

        return typeDocumentList;
    }
    public List<TypeDocumentEntity> getByName(String name) {
        List<TypeDocumentEntity> typeDocumentList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `typedocument` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    TypeDocumentEntity typeDocument = new TypeDocumentEntity();
                    typeDocument.setId(resultSet.getInt("id"));
                    typeDocument.setName(resultSet.getString("name"));

                    typeDocumentList.add(typeDocument);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by name method: ", e.getStackTrace());
        }

        return typeDocumentList;
    }
    public static TypeDocumentController idNum() {
        TypeDocumentController typeDocument = new TypeDocumentController();
        String sql = "SELECT (MAX(id)+1) as idnum FROM typedocument";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, brand.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String idTF = resultSet.getString("idnum");
                TypeDocument.idNumber(idTF);

                return typeDocument;
            }

        } catch (Exception e) {
        }
        return null;
    }

    public static TypeDocumentEntity delete(TypeDocumentEntity typeDocument) {
        String sql = "DELETE FROM `typedocument`" +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, typeDocument.getId());
            preparedStatement.execute();

        } catch (Exception e) {

        }
        return null;
    }
    public List<TypeDocumentEntity> getAll() {
        List<TypeDocumentEntity> typeDocumentEntities = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `typedocument`";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    TypeDocumentEntity typeDocumentEntity = new TypeDocumentEntity();
                    typeDocumentEntity.setId(resultSet.getInt("id"));
                    typeDocumentEntity.setName(resultSet.getString("name"));

                    typeDocumentEntities.add(typeDocumentEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get all faculties method: ", e.getStackTrace());
        }

        return typeDocumentEntities;
    }
}
