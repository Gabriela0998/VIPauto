package com.app.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.app.entity.MotorVehicleEntity;
import com.app.manager.TableManager;
import com.app.view.MotorVehicle;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.manager.DatabaseManager.getConnection;

public class MotorVehicleController {
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    static Connection conn = null; // connection object
    Statement stmt = null; // statement object
    static ResultSet rs = null; // result set object
    public String name = "";
    public int id = 0;
    public String text = "";
    static String comboBoxCat = "";
    static String comboBoxType = "";
    static String comboBoxBrand = "";
    static String comboBoxModel = "";
    static String comboBoxFuel = "";
    static String comboBoxYear = "";
    //Connection conn = getConnection();
    public MotorVehicleController(){
        String table = "MotorVehicle";
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

    public static MotorVehicleEntity vis(MotorVehicleEntity motorVehicle) {
        String sql = "SELECT reg_plate as name, reg_plate, id, id_cat, id_type, id_brand, id_model, " +
                " id_fuel, YEAR(year) as year, mileage, euro, frame, status " +
                " FROM motorvehicle WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, motorVehicle.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nameTF = resultSet.getString("reg_plate");
                String idTF = resultSet.getString("id");
                comboBoxCat = resultSet.getString("id_cat");
                comboBoxType = resultSet.getString("id_type");
                comboBoxBrand = resultSet.getString("id_brand");
                comboBoxModel = resultSet.getString("id_model");
                comboBoxFuel = resultSet.getString("id_fuel");
                comboBoxYear = resultSet.getString("year");
                String frame = resultSet.getString("frame");
                String euro = resultSet.getString("euro");
                String status = resultSet.getString("status");
                String mileage = resultSet.getString("mileage");
                MotorVehicleController.getByIdCat(comboBoxCat);
                MotorVehicleController.getByIdType(comboBoxType);
                MotorVehicleController.getByIdBrand(comboBoxBrand);
                MotorVehicleController.getByIdModel(comboBoxModel);
                MotorVehicleController.getByIdFuel(comboBoxFuel);
                MotorVehicle.visible(nameTF,idTF, comboBoxCat, comboBoxType, comboBoxBrand, comboBoxModel, comboBoxFuel, comboBoxYear, frame, euro, status, mileage);


                return motorVehicle;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static MotorVehicleEntity show() {
        MotorVehicleEntity motorVehicle = new MotorVehicleEntity();
        String sql = "SELECT reg_plate as name, reg_plate, id, id_cat, id_type, id_brand, id_model, " +
                " id_fuel, YEAR(year) as year, mileage, euro, frame, status  FROM motorvehicle LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, motorVehicle.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nameTF = resultSet.getString("reg_plate");
                String idTF = resultSet.getString("id");
                comboBoxCat = resultSet.getString("id_cat");
                comboBoxType = resultSet.getString("id_type");
                comboBoxBrand = resultSet.getString("id_brand");
                comboBoxModel = resultSet.getString("id_model");
                comboBoxFuel = resultSet.getString("id_fuel");
                comboBoxYear = resultSet.getString("year");
                String frame = resultSet.getString("frame");
                String euro = resultSet.getString("euro");
                String status = resultSet.getString("status");
                String mileage = resultSet.getString("mileage");
                MotorVehicleController.getByIdCat(comboBoxCat);
                MotorVehicleController.getByIdType(comboBoxType);
                MotorVehicleController.getByIdBrand(comboBoxBrand);
                MotorVehicleController.getByIdModel(comboBoxModel);
                MotorVehicleController.getByIdFuel(comboBoxFuel);
                MotorVehicle.visible(nameTF,idTF, comboBoxCat, comboBoxType, comboBoxBrand, comboBoxModel, comboBoxFuel, comboBoxYear, frame, euro, status, mileage);

                return motorVehicle;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static MotorVehicleEntity idNum() {
        MotorVehicleEntity motorVehicle = new MotorVehicleEntity();
        String sql = "SELECT (MAX(id)+1) as idnum FROM motorvehicle";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, motorVehicle.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String idTF = resultSet.getString("idnum");
                MotorVehicle.idNumber(idTF);

                return motorVehicle;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static MotorVehicleEntity getById(MotorVehicleEntity MotorVehicle) {
        String sql = "SELECT * FROM motorvehicle WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, MotorVehicle.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Внимание!");
                alert.setHeaderText("Вие ще редактирате запис!");
                alert.setContentText("Желаете ли да продължите?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    update(MotorVehicle);}
            }else {
                insert(MotorVehicle);
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
                comboBoxCat = resultSet.getString("name");
            }else {
                comboBoxCat = "";
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static String getByIdType(String idType) {
        String sql = "SELECT * FROM type WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, Integer.parseInt(idType));
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                comboBoxType = resultSet.getString("name");
            }else {
                comboBoxType = "";
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static String getByIdBrand(String idBrand) {
        String sql = "SELECT * FROM brand WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, Integer.parseInt(idBrand));
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                comboBoxBrand = resultSet.getString("name");
            }else {
                comboBoxBrand = "";
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static String getByIdModel(String idModel) {
        String sql = "SELECT * FROM model WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, Integer.parseInt(idModel));
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                comboBoxModel = resultSet.getString("name");
            }else {
                comboBoxModel = "";
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static String getByIdFuel(String idFuel) {
        String sql = "SELECT * FROM fuel WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, Integer.parseInt(idFuel));
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                comboBoxFuel = resultSet.getString("name");
            }else {
                comboBoxFuel = "";
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static MotorVehicleEntity insert(MotorVehicleEntity MotorVehicle) {
        String sql = "INSERT INTO `motorvehicle`(`id`, `reg_plate`, `id_cat`, `id_type`, `id_brand`, `id_model`, `id_fuel`, `status`," +
                "`mileage`, `year`, `euro`, `frame`)" +
                "VALUES (?, ?, ?,?, ?, ?,?, ?, ?,?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setString(2, MotorVehicle.getRegPlate());
            preparedStatement.setInt(1, MotorVehicle.getId());
            preparedStatement.setInt(3, MotorVehicle.getIdCat());
            preparedStatement.setInt(4, MotorVehicle.getIdType());
            preparedStatement.setInt(5, MotorVehicle.getIdBrand());
            preparedStatement.setInt(6, MotorVehicle.getIdModel());
            preparedStatement.setInt(7, MotorVehicle.getIdFuel());
            preparedStatement.setInt(8, MotorVehicle.getStatus());
            preparedStatement.setDouble(9, MotorVehicle.getMileage());
            preparedStatement.setInt(10, MotorVehicle.getYear());
            preparedStatement.setInt(11, MotorVehicle.getEURO());
            preparedStatement.setString(12, MotorVehicle.getFrame());
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                MotorVehicle.setId(resultSet.getInt("id"));
                MotorVehicle.setRegPlate(resultSet.getString("name"));
                MotorVehicle.setIdCat(resultSet.getInt("id_cat"));

                return MotorVehicle;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public Integer getExistingMotorVehicleIdCat(String comboBoxName) {
        String sql = "SELECT id " +
                "FROM catauto " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setString(1, nameTable);
            preparedStatement.setString(1, comboBoxName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                MotorVehicleEntity motorVehicle = new MotorVehicleEntity();
                motorVehicle.setIdCat(resultSet.getInt("id"));
                int idCat = resultSet.getInt("id");
                MotorVehicle.idNumberCat(String.valueOf(idCat));
                return motorVehicle.getIdCat();
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public Integer getExistingMotorVehicleIdType(String comboBoxName) {
        String sql = "SELECT id " +
                "FROM type " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setString(1, nameTable);
            preparedStatement.setString(1, comboBoxName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                MotorVehicleEntity motorVehicle = new MotorVehicleEntity();
                motorVehicle.setIdType(resultSet.getInt("id"));
                return motorVehicle.getIdType();
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public Integer getExistingMotorVehicleIdBrand(String comboBoxName) {
        String sql = "SELECT id " +
                "FROM brand " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setString(1, nameTable);
            preparedStatement.setString(1, comboBoxName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                MotorVehicleEntity motorVehicle = new MotorVehicleEntity();
                motorVehicle.setIdBrand(resultSet.getInt("id"));
                int idBrand=resultSet.getInt("id");
                MotorVehicle.idNumberBrand(String.valueOf(idBrand));
                return motorVehicle.getIdBrand();
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public Integer getExistingMotorVehicleIdBrand(String comboBoxName, String nameBrand) {
        String sql = "SELECT id " +
                "FROM brand " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setString(1, nameTable);
            preparedStatement.setString(1, comboBoxName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                MotorVehicleEntity motorVehicle = new MotorVehicleEntity();
                motorVehicle.setIdBrand(resultSet.getInt("id"));
                return motorVehicle.getIdBrand();
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public Integer getExistingMotorVehicleIdModel(String comboBoxName) {
        String sql = "SELECT id " +
                "FROM model " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setString(1, nameTable);
            preparedStatement.setString(1, comboBoxName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                MotorVehicleEntity motorVehicle = new MotorVehicleEntity();
                            motorVehicle.setIdModel(resultSet.getInt("id"));
                            return motorVehicle.getIdModel();
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public Integer getExistingMotorVehicleIdFuel(String comboBoxName) {
        String sql = "SELECT id " +
                "FROM fuel " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setString(1, nameTable);
            preparedStatement.setString(1, comboBoxName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                MotorVehicleEntity motorVehicle = new MotorVehicleEntity();
                            motorVehicle.setIdFuel(resultSet.getInt("id"));
                            return motorVehicle.getIdFuel();
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public static MotorVehicleEntity update(MotorVehicleEntity MotorVehicle) {
        String sql = "UPDATE `motorvehicle`" +
                "SET reg_plate = ?, id_cat = ?, id_type = ?, id_brand = ?, id_model = ?, id_fuel = ?, year = ?," +
                " status = ?, euro = ?, frame = ?, mileage  = ? " +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){

            preparedStatement.setString(1, MotorVehicle.getName());
            preparedStatement.setInt(2, MotorVehicle.getIdCat());
            preparedStatement.setInt(3, MotorVehicle.getIdType());
            preparedStatement.setInt(4, MotorVehicle.getIdBrand());
            preparedStatement.setInt(5, MotorVehicle.getIdModel());
            preparedStatement.setInt(6, MotorVehicle.getIdFuel());
            preparedStatement.setInt(7, MotorVehicle.getYear());
            preparedStatement.setInt(8, MotorVehicle.getStatus());
            preparedStatement.setInt(9, MotorVehicle.getEURO());
            preparedStatement.setString(10, MotorVehicle.getFrame());
            preparedStatement.setDouble(11, MotorVehicle.getMileage());
            preparedStatement.setInt(12, MotorVehicle.getId());
            //preparedStatement.setInt(13, MotorVehicle.getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                MotorVehicle.setId(resultSet.getInt("id"));
                MotorVehicle.setRegPlate(resultSet.getString("name"));
                MotorVehicle.setIdCat(resultSet.getInt("id_cat"));

                return MotorVehicle;
            }

        } catch (Exception e) {

        }
        return null;
    }
    public static MotorVehicleEntity delete(MotorVehicleEntity MotorVehicle) {
        String sql = "DELETE FROM `motorvehicle`" +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, MotorVehicle.getId());
            preparedStatement.execute();

        } catch (Exception e) {

        }
        return null;
    }
    public List<MotorVehicleEntity> getByCode(String code) {
        List<MotorVehicleEntity> motorVehicleList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `motorvehicle` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    MotorVehicleEntity motorVehicleEntity = new MotorVehicleEntity();
                    motorVehicleEntity.setId(resultSet.getInt("id"));
                    motorVehicleEntity.setRegPlate(resultSet.getString("name"));
                    motorVehicleEntity.setIdCat(resultSet.getInt("id_cat"));

                    motorVehicleList.add(motorVehicleEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by code method: ", e.getStackTrace());
        }

        return motorVehicleList;
    }
    public List<MotorVehicleEntity> getByName(String name) {
        List<MotorVehicleEntity> motorVehicleList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `motorvehicle` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    MotorVehicleEntity motorVehicle = new MotorVehicleEntity();
                    motorVehicle.setId(resultSet.getInt("id"));
                    motorVehicle.setRegPlate(resultSet.getString("name"));
                    motorVehicle.setIdCat(resultSet.getInt("id_cat"));

                    motorVehicleList.add(motorVehicle);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by name method: ", e.getStackTrace());
        }

        return motorVehicleList;
    }
    public List<MotorVehicleEntity> getAll() {
        List<MotorVehicleEntity> motorVehicleEntities = new ArrayList<>();
        String sql = "SELECT reg_plate as name, reg_plate, id, id_cat, id_type, id_brand, id_model, id_fuel, year, mileage, euro, frame, status " +
                "FROM `motorvehicle`";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    MotorVehicleEntity motorVehicleEntity = new MotorVehicleEntity();
                    motorVehicleEntity.setId(resultSet.getInt("id"));
                    motorVehicleEntity.setRegPlate(resultSet.getString("reg_plate"));
                    motorVehicleEntity.setName(resultSet.getString("name"));
                    motorVehicleEntity.setIdCat(resultSet.getInt("id_cat"));
                    motorVehicleEntity.setIdType(resultSet.getInt("id_type"));
                    motorVehicleEntity.setIdBrand(resultSet.getInt("id_brand"));
                    motorVehicleEntity.setIdModel(resultSet.getInt("id_model"));
                    motorVehicleEntity.setIdFuel(resultSet.getInt("id_fuel"));
                    motorVehicleEntity.setYear(resultSet.getInt("year"));
                    motorVehicleEntity.setMileage(resultSet.getInt("mileage"));
                    motorVehicleEntity.setEURO(resultSet.getInt("euro"));
                    motorVehicleEntity.setFrame(resultSet.getString("frame"));
                    motorVehicleEntity.setStatus(resultSet.getInt("status"));

                    motorVehicleEntities.add(motorVehicleEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get all faculties method: ", e.getStackTrace());
        }

        return motorVehicleEntities;
    }
    public List<MotorVehicleEntity> getAll(Integer idBrand, Integer idCat, Integer idModel) {
        List<MotorVehicleEntity> motorVehicleEntities = new ArrayList<>();
        String sql = "SELECT reg_plate as name, reg_plate, id, id_cat, id_type, id_brand, id_model, id_fuel, year, mileage, euro, frame, status " +
                "FROM `motorvehicle` WHERE id_cat=? and id_brand=? and id_model=?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, idCat);
            preparedStatement.setInt(2, idBrand);
            preparedStatement.setInt(3, idModel);
            resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    MotorVehicleEntity motorVehicleEntity = new MotorVehicleEntity();
                    motorVehicleEntity.setId(resultSet.getInt("id"));
                    motorVehicleEntity.setRegPlate(resultSet.getString("reg_plate"));
                    motorVehicleEntity.setName(resultSet.getString("name"));
                    motorVehicleEntity.setIdCat(resultSet.getInt("id_cat"));
                    motorVehicleEntity.setIdType(resultSet.getInt("id_type"));
                    motorVehicleEntity.setIdBrand(resultSet.getInt("id_brand"));
                    motorVehicleEntity.setIdModel(resultSet.getInt("id_model"));
                    motorVehicleEntity.setIdFuel(resultSet.getInt("id_fuel"));
                    motorVehicleEntity.setYear(resultSet.getInt("year"));
                    motorVehicleEntity.setMileage(resultSet.getInt("mileage"));
                    motorVehicleEntity.setEURO(resultSet.getInt("euro"));
                    motorVehicleEntity.setFrame(resultSet.getString("frame"));
                    motorVehicleEntity.setStatus(resultSet.getInt("status"));

                    motorVehicleEntities.add(motorVehicleEntity);
                }

        } catch (Exception e) {
            //LOGGER.error("Exception in get all faculties method: ", e.getStackTrace());
        }

        return motorVehicleEntities;
    }
}
