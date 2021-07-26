package com.app.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.app.entity.TypeEntity;
import com.app.manager.TableManager;
import com.app.view.Type;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.manager.DatabaseManager.getConnection;

public class TypeController  {
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    static Connection conn = null; // connection object
    Statement stmt = null; // statement object
    static ResultSet rs = null; // result set object
    public String name = "";
    public int id = 0;
    public String text = "";
    static String comboBox = "";
    //Connection conn = getConnection();
    public TypeController(){
        String table = "Type";
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

    public static TypeEntity vis(TypeEntity type) {
        String sql = "SELECT * FROM type WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, type.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nameTF = resultSet.getString("name");
                String idTF = resultSet.getString("id");
                comboBox = resultSet.getString("id_cat");
                TypeController.getByIdCat(comboBox);
                Type.visible(nameTF,idTF,comboBox);

                return type;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static TypeEntity show() {
        TypeEntity type = new TypeEntity();
        String sql = "SELECT * FROM type LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, type.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nameTF = resultSet.getString("name");
                String idTF = resultSet.getString("id");
                comboBox = resultSet.getString("id_cat");
                TypeController.getByIdCat(comboBox);
                Type.visible(nameTF,idTF, comboBox);

                return type;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static TypeEntity getById(TypeEntity Type) {
        String sql = "SELECT * FROM type WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, Type.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Внимание!");
                alert.setHeaderText("Вие ще редактирате запис!");
                alert.setContentText("Желаете ли да продължите?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    update(Type);}
            }else {
                insert(Type);
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static String getByIdCat(String idCat) {
        String sql = "SELECT * FROM catauto WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, Integer.parseInt(idCat));
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                comboBox = resultSet.getString("name");
            }else {
                comboBox = "";
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static TypeEntity insert(TypeEntity Type) {
        String sql = "INSERT INTO `type`(name, id, id_cat)" +
                "VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, Type.getName());
            preparedStatement.setInt(2, Type.getId());
            preparedStatement.setInt(3, Type.getId_catAuto());
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Type.setId(resultSet.getInt("id"));
                Type.setName(resultSet.getString("name"));
                Type.setId_catAuto(resultSet.getInt("id_cat"));

                return Type;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public Integer getExistingTypeId(String typeName) {
        String sql = "SELECT id " +
                "FROM catauto " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);) {

            preparedStatement.setString(1, typeName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    TypeEntity type = new TypeEntity();
                    type.setId_catAuto(resultSet.getInt("id"));

                    return type.getId_catAuto();
                }
            }
        } catch (Exception e) {
           // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }

    public static TypeEntity update(TypeEntity Type) {
        String sql = "UPDATE `type`" +
                "SET name = ?, id_cat = ? " +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, Type.getName());
            preparedStatement.setInt(2, Type.getId_catAuto());
            preparedStatement.setInt(3, Type.getId());
            //preparedStatement.setInt(13, Type.getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Type.setId(resultSet.getInt("id"));
                Type.setName(resultSet.getString("name"));
                Type.setId_catAuto(resultSet.getInt("id_cat"));

                return Type;
            }

        } catch (Exception e) {

        }
        return null;
    }
    public static TypeEntity delete(TypeEntity Type) {
        String sql = "DELETE FROM `type`" +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, Type.getId());
            preparedStatement.execute();

        } catch (Exception e) {

        }
        return null;
    }
    public List<TypeEntity> getByCode(String code) {
        List<TypeEntity> typeList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `type` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    TypeEntity typeEntity = new TypeEntity();
                    typeEntity.setId(resultSet.getInt("id"));
                    typeEntity.setName(resultSet.getString("name"));
                    typeEntity.setId_catAuto(resultSet.getInt("id_cat"));

                    typeList.add(typeEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by code method: ", e.getStackTrace());
        }

        return typeList;
    }
    public List<TypeEntity> getByName(String name) {
        List<TypeEntity> typeList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `type` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    TypeEntity type = new TypeEntity();
                    type.setId(resultSet.getInt("id"));
                    type.setName(resultSet.getString("name"));
                    type.setId_catAuto(resultSet.getInt("id_cat"));

                    typeList.add(type);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by name method: ", e.getStackTrace());
        }

        return typeList;
    }
    public List<TypeEntity> getAll() {
        List<TypeEntity> typeEntities = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `type`";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    TypeEntity typeEntity = new TypeEntity();
                    typeEntity.setId(resultSet.getInt("id"));
                    typeEntity.setName(resultSet.getString("name"));
                    typeEntity.setId_catAuto(resultSet.getInt("id_cat"));

                    typeEntities.add(typeEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get all faculties method: ", e.getStackTrace());
        }

        return typeEntities;
    }
    public List<TypeEntity> getAll(int idCat) {
        List<TypeEntity> typeEntities = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `type` where id_cat=?";


        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, idCat);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                    TypeEntity typeEntity = new TypeEntity();
                    typeEntity.setId(resultSet.getInt("id"));
                    typeEntity.setName(resultSet.getString("name"));
                    typeEntity.setId_catAuto(resultSet.getInt("id_cat"));

                    typeEntities.add(typeEntity);
                }
        } catch (Exception e) {
            //LOGGER.error("Exception in get all faculties method: ", e.getStackTrace());
        }

        return typeEntities;
    }

    public static TypeEntity idNum() {
        TypeEntity type = new TypeEntity();
        String sql = "SELECT (MAX(id)+1) as idnum FROM type";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, brand.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String idTF = resultSet.getString("idnum");
                Type.idNumber(idTF);

                return type;
            }

        } catch (Exception e) {
        }
        return null;
    }
}

