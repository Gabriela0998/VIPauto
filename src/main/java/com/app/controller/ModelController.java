package com.app.controller;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.app.entity.ModelEntity;
import com.app.manager.TableManager;
import com.app.view.Model;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.manager.DatabaseManager.getConnection;

public class ModelController {
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
    public ModelController(){
        String table = "Model";
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

    public static ModelEntity vis(ModelEntity model) {
        String sql = "SELECT * FROM model WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, model.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nameTF = resultSet.getString("name");
                String idTF = resultSet.getString("id");
                comboBox = resultSet.getString("id_brand");
                ModelController.getByIdCat(comboBox);
                Model.visible(nameTF,idTF,comboBox);

                return model;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static ModelEntity show() {
        ModelEntity model = new ModelEntity();
        String sql = "SELECT * FROM model LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, model.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nameTF = resultSet.getString("name");
                String idTF = resultSet.getString("id");
                comboBox = resultSet.getString("id_brand");
                ModelController.getByIdCat(comboBox);
                Model.visible(nameTF,idTF, comboBox);

                return model;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static ModelEntity idNum() {
        ModelEntity model = new ModelEntity();
        String sql = "SELECT (MAX(id)+1) as idnum FROM model";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, model.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String idTF = resultSet.getString("idnum");
                Model.idNumber(idTF);

                return model;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static ModelEntity getById(ModelEntity Model) {
        String sql = "SELECT * FROM model WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, Model.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Внимание!");
                alert.setHeaderText("Вие ще редактирате запис!");
                alert.setContentText("Желаете ли да продължите?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    update(Model);}
            }else {
                insert(Model);
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static String getByIdCat(String idBrand) {
        String sql = "SELECT * FROM brand WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, Integer.parseInt(idBrand));
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
    public static ModelEntity insert(ModelEntity Model) {
        String sql = "INSERT INTO `model`(name, id, id_brand)" +
                "VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, Model.getName());
            preparedStatement.setInt(2, Model.getId());
            preparedStatement.setInt(3, Model.getId_brand());
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Model.setId(resultSet.getInt("id"));
                Model.setName(resultSet.getString("name"));
                Model.setId_brand(resultSet.getInt("id_brand"));

                return Model;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public Integer getExistingModelId(String modelName) {
        String sql = "SELECT id " +
                "FROM brand " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);) {

            preparedStatement.setString(1, modelName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    ModelEntity model = new ModelEntity();
                    model.setId_brand(resultSet.getInt("id"));

                    return model.getId_brand();
                }
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }

    public static ModelEntity update(ModelEntity Model) {
        String sql = "UPDATE `model`" +
                "SET name = ?, id_brand = ? " +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, Model.getName());
            preparedStatement.setInt(2, Model.getId_brand());
            preparedStatement.setInt(3, Model.getId());
            //preparedStatement.setInt(13, Model.getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Model.setId(resultSet.getInt("id"));
                Model.setName(resultSet.getString("name"));
                Model.setId_brand(resultSet.getInt("id_brand"));

                return Model;
            }

        } catch (Exception e) {

        }
        return null;
    }
    public static ModelEntity delete(ModelEntity Model) {
        String sql = "DELETE FROM `model`" +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, Model.getId());
            preparedStatement.execute();

        } catch (Exception e) {

        }
        return null;
    }
    public List<ModelEntity> getByCode(String code) {
        List<ModelEntity> modelList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `model` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ModelEntity modelEntity = new ModelEntity();
                    modelEntity.setId(resultSet.getInt("id"));
                    modelEntity.setName(resultSet.getString("name"));
                    modelEntity.setId_brand(resultSet.getInt("id_brand"));

                    modelList.add(modelEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by code method: ", e.getStackTrace());
        }

        return modelList;
    }
    public List<ModelEntity> getByName(String name) {
        List<ModelEntity> modelList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `model` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ModelEntity model = new ModelEntity();
                    model.setId(resultSet.getInt("id"));
                    model.setName(resultSet.getString("name"));
                    model.setId_brand(resultSet.getInt("id_brand"));

                    modelList.add(model);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by name method: ", e.getStackTrace());
        }

        return modelList;
    }
    public List<ModelEntity> getAll() {
        List<ModelEntity> modelEntities = new ArrayList<>();
        String sql = "SELECT m.id, m.name, b.name as brand " +
                "FROM `model` m join `brand` b ON m.id_brand=b.id";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ModelEntity modelEntity = new ModelEntity();
                    modelEntity.setId(resultSet.getInt("id"));
                    modelEntity.setName(resultSet.getString("name"));
                    modelEntity.setBrand(resultSet.getString("brand"));

                    modelEntities.add(modelEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get all faculties method: ", e.getStackTrace());
        }

        return modelEntities;
    }
    public List<ModelEntity> getAll(int idBrand) {
        List<ModelEntity> modelEntities = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `model`  WHERE id_brand=?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, idBrand);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                    ModelEntity modelEntity = new ModelEntity();
                    modelEntity.setId(resultSet.getInt("id"));
                    modelEntity.setName(resultSet.getString("name"));
                    modelEntity.setBrand(resultSet.getString("id_brand"));

                    modelEntities.add(modelEntity);
                }
        } catch (Exception e) {
            //LOGGER.error("Exception in get all faculties method: ", e.getStackTrace());
        }

        return modelEntities;
    }
}
