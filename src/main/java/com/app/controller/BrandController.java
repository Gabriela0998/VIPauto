package com.app.controller;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.app.entity.BrandEntity;
import com.app.manager.TableManager;
import com.app.view.Brand;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.manager.DatabaseManager.getConnection;

public class BrandController  {
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
    public BrandController(){
        String table = "Brand";
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

    public static BrandEntity vis(BrandEntity brand) {
        String sql = "SELECT * FROM brand WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, brand.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nameTF = resultSet.getString("name");
                String idTF = resultSet.getString("id");
                comboBox = resultSet.getString("id_cat");
                BrandController.getByIdCat(comboBox);
                Brand.visible(nameTF,idTF,comboBox);

                return brand;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static BrandEntity show() {
        BrandEntity brand = new BrandEntity();
        String sql = "SELECT * FROM brand LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, brand.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nameTF = resultSet.getString("name");
                String idTF = resultSet.getString("id");
                comboBox = resultSet.getString("id_cat");
                BrandController.getByIdCat(comboBox);
                Brand.visible(nameTF,idTF, comboBox);

                return brand;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static BrandEntity idNum() {
        BrandEntity brand = new BrandEntity();
        String sql = "SELECT (MAX(id)+1) as idnum FROM brand";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, brand.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String idTF = resultSet.getString("idnum");
                Brand.idNumber(idTF);

                return brand;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static BrandEntity getById(BrandEntity Brand) {
        String sql = "SELECT * FROM brand WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, Brand.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Внимание!");
                alert.setHeaderText("Вие ще редактирате запис!");
                alert.setContentText("Желаете ли да продължите?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    update(Brand);}
            }else {
                insert(Brand);
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
    public static BrandEntity insert(BrandEntity Brand) {
        String sql = "INSERT INTO `brand`(name, id, id_cat)" +
                "VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, Brand.getName());
            preparedStatement.setInt(2, Brand.getId());
            preparedStatement.setInt(3, Brand.getId_catAuto());
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Brand.setId(resultSet.getInt("id"));
                Brand.setName(resultSet.getString("name"));
                Brand.setId_catAuto(resultSet.getInt("id_cat"));

                return Brand;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public Integer getExistingBrandId(String brandName) {
        String sql = "SELECT id " +
                "FROM catauto " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);) {

            preparedStatement.setString(1, brandName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    BrandEntity brand = new BrandEntity();
                    brand.setId_catAuto(resultSet.getInt("id"));

                    return brand.getId_catAuto();
                }
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }

    public static BrandEntity update(BrandEntity Brand) {
        String sql = "UPDATE `brand`" +
                "SET name = ?, id_cat = ? " +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(1, Brand.getName());
            preparedStatement.setInt(2, Brand.getId_catAuto());
            preparedStatement.setInt(3, Brand.getId());
            //preparedStatement.setInt(13, Brand.getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Brand.setId(resultSet.getInt("id"));
                Brand.setName(resultSet.getString("name"));
                Brand.setId_catAuto(resultSet.getInt("id_cat"));

                return Brand;
            }

        } catch (Exception e) {

        }
        return null;
    }
    public static BrandEntity delete(BrandEntity Brand) {
        String sql = "DELETE FROM `brand`" +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, Brand.getId());
            preparedStatement.execute();

        } catch (Exception e) {

        }
        return null;
    }
    public List<BrandEntity> getByCode(String code) {
        List<BrandEntity> brandList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `brand` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    BrandEntity brandEntity = new BrandEntity();
                    brandEntity.setId(resultSet.getInt("id"));
                    brandEntity.setName(resultSet.getString("name"));
                    brandEntity.setId_catAuto(resultSet.getInt("id_cat"));

                    brandList.add(brandEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by code method: ", e.getStackTrace());
        }

        return brandList;
    }
    public List<BrandEntity> getByName(String name) {
        List<BrandEntity> brandList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `brand` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    BrandEntity brand = new BrandEntity();
                    brand.setId(resultSet.getInt("id"));
                    brand.setName(resultSet.getString("name"));
                    brand.setId_catAuto(resultSet.getInt("id_cat"));

                    brandList.add(brand);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by name method: ", e.getStackTrace());
        }

        return brandList;
    }
    public List<BrandEntity> getAll(int idCat) {
        List<BrandEntity> brandEntities = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `brand` WHERE id_cat=?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, idCat);
            resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    BrandEntity brandEntity = new BrandEntity();
                    brandEntity.setId(resultSet.getInt("id"));
                    brandEntity.setName(resultSet.getString("name"));
                    brandEntity.setId_catAuto(resultSet.getInt("id_cat"));

                    brandEntities.add(brandEntity);
                }
        } catch (Exception e) {
            //LOGGER.error("Exception in get all faculties method: ", e.getStackTrace());
        }

        return brandEntities;
    }
    public List<BrandEntity> getAll() {
        List<BrandEntity> brandEntities = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `brand`";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    BrandEntity brandEntity = new BrandEntity();
                    brandEntity.setId(resultSet.getInt("id"));
                    brandEntity.setName(resultSet.getString("name"));
                    brandEntity.setId_catAuto(resultSet.getInt("id_cat"));

                    brandEntities.add(brandEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get all faculties method: ", e.getStackTrace());
        }

        return brandEntities;
    }
}
