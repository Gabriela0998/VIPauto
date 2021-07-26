package com.app.controller;
import com.app.entity.ReferenceEntity;
import com.app.view.ReferenceCostCar;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.app.entity.CostEntity;
import com.app.manager.TableManager;
import com.app.view.Cost;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.manager.DatabaseManager.getConnection;
import static com.app.manager.DatabaseManager.getConnection;

public class CostController {
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    static Connection conn = null; // connection object
    Statement stmt = null; // statement object
    static ResultSet rs = null; // result set object
    public String name = "";
    public int id = 0;
    public String text = "";
    static String comboBoxCar = "";
    static String comboBoxCat = "";
    static String comboBoxType = "";
    static String comboBoxBrand = "";
    static String comboBoxModel = "";
    static String comboBoxFuel = "";
    static String comboBoxYear = "";
    //Connection conn = getConnection();
    public CostController(){
        String table = "Cost";
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
    public static Integer getExistingCostIdCat(String comboBoxName) {
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
                CostEntity cost = new CostEntity();
                cost.setId_catAuto(resultSet.getInt("id"));
                int idCat = resultSet.getInt("id");
                Cost.idNumberCat(String.valueOf(idCat));
                return cost.getId_catAuto();
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public static Integer getExistingCostIdType(String comboBoxName) {
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
                CostEntity cost = new CostEntity();
                cost.setIdType(resultSet.getInt("id"));
                return cost.getIdType();
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public static Integer getExistingCostIdBrand(String comboBoxName) {
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
                CostEntity cost = new CostEntity();
                cost.setIdBrand(resultSet.getInt("id"));
                int idBrand=resultSet.getInt("id");
                Cost.idNumberBrand(String.valueOf(idBrand));
                return cost.getIdBrand();
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public Integer getExistingCostIdBrand(String comboBoxName, String nameBrand) {
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
                CostEntity cost = new CostEntity();
                cost.setIdBrand(resultSet.getInt("id"));
                int idBrand=resultSet.getInt("id");
                Cost.idNumberBrand(String.valueOf(idBrand));
                return cost.getIdBrand();
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public static Integer getExistingCostIdModel(String comboBoxName) {
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
                CostEntity cost = new CostEntity();
                cost.setIdModel(resultSet.getInt("id"));
                int idBrand=resultSet.getInt("id");
                Cost.idNumberModel(String.valueOf(idBrand));
                return cost.getIdModel();
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public static Integer getExistingCostIdCar(String comboBoxName) {
        CostEntity cost = new CostEntity();
        String sql = "SELECT id " +
                "FROM motorvehicle " +
                "WHERE reg_plate = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setString(1, nameTable);
            preparedStatement.setString(1, comboBoxName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cost = new CostEntity();
                int idCar=resultSet.getInt("id");
                cost.setIdModel(resultSet.getInt("id"));
                return cost.getIdModel();
            }
            else{
                cost = new CostEntity();
                cost.setIdModel(0);
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public static CostEntity vis(CostEntity cost) {
        String sql = "SELECT c.id_car, b.name as brand_name, b.id as idbrand, " +
                "mm.name as model_name, mm.id as idmodel, ca.id as idcat, ca.name, c.id_type, m.id_fuel  " +
                "FROM cost as c LEFT JOIN motorvehicle as m ON c.id_car=m.id " +
                "LEFT JOIN brand as b ON m.id_brand=b.id LEFT JOIN model as mm ON m.id_model=mm.id " +
                "LEFT JOIN catauto as ca ON m.id_cat = ca.id WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, cost.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String idTF = resultSet.getString("id");
                String status = resultSet.getString("status");
                comboBoxCat = resultSet.getString("idcat");
                comboBoxType = resultSet.getString("id_type");
                comboBoxBrand = resultSet.getString("idbrand");
                comboBoxModel = resultSet.getString("idmodel");
                comboBoxFuel = resultSet.getString("id_fuel");
                CostController.getByIdCat(comboBoxCat);
                CostController.getByIdType(comboBoxType);
                CostController.getByIdBrand(comboBoxBrand);
                CostController.getByIdModel(comboBoxModel);
                CostController.getByIdFuel(comboBoxFuel);
                Cost.visible(idTF, comboBoxCat, comboBoxType, comboBoxBrand, comboBoxModel, comboBoxFuel, status);


                return cost;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static CostEntity show() {
        CostEntity cost = new CostEntity();
        String sql = "SELECT c.id, c.id_car, b.name as brand_name, b.id as idbrand, " +
                "mm.name as model_name, mm.id as idmodel, ca.id as idcat, ca.name, c.id_type, m.id_fuel, c.status  " +
                "FROM cost as c LEFT JOIN motorvehicle as m ON c.id_car=m.id " +
                "LEFT JOIN brand as b ON m.id_brand=b.id LEFT JOIN model as mm ON m.id_model=mm.id " +
                "LEFT JOIN catauto as ca ON m.id_cat = ca.id LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String idTF = resultSet.getString("id");
                String status = resultSet.getString("status");
                comboBoxCat = resultSet.getString("idcat");
                comboBoxCar = resultSet.getString("id_car");
                comboBoxType = resultSet.getString("id_type");
                comboBoxBrand = resultSet.getString("idbrand");
                comboBoxModel = resultSet.getString("idmodel");
                comboBoxFuel = resultSet.getString("id_fuel");
                CostController.getByIdCat(comboBoxCat);
                CostController.getByIdType(comboBoxType);
                CostController.getByIdBrand(comboBoxBrand);
                CostController.getByIdModel(comboBoxModel);
                CostController.getByIdFuel(comboBoxFuel);
                CostController.getByIdCar(comboBoxCar);
                Cost.visible(idTF, comboBoxCat, comboBoxType, comboBoxBrand, comboBoxModel, comboBoxCar, status);


                return cost;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static CostEntity idNum() {
        CostEntity cost = new CostEntity();
        String sql = "SELECT (MAX(id)+1) as idnum FROM cost";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, cost.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String idTF = resultSet.getString("idnum");
                Cost.idNumber(idTF);

                return cost;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static CostEntity getById(CostEntity Cost) {
        String sql = "SELECT * FROM cost WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, Cost.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Внимание!");
                alert.setHeaderText("Вие ще редактирате запис!");
                alert.setContentText("Желаете ли да продължите?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    update(Cost);}
            }else {
                insert(Cost);
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
    public static String getByIdCar(String idCar) {
        String sql = "SELECT * FROM motorvehicle WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, Integer.parseInt(idCar));
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                comboBoxCar = resultSet.getString("reg_plate");
            }else {
                comboBoxCar = "";
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static CostEntity insert(CostEntity Cost) {
        String sql = "INSERT INTO `cost`(`id`, `id_type`, `id_car`, `status`)" +
                "VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, Cost.getId());
            preparedStatement.setInt(2, Cost.getIdType());
            preparedStatement.setInt(3, Cost.getIdCar());
            preparedStatement.setInt(4, Cost.getStatus());
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Cost.setId(resultSet.getInt("id"));
                //Cost.setRegPlate(resultSet.getString("name"));
                //Cost.setIdCat(resultSet.getInt("id_cat"));

                return Cost;
            }
        } catch (Exception e) {
        }
        return null;
    }


    public Integer getExistingCostIdFuel(String comboBoxName) {
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
                CostEntity cost = new CostEntity();
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public static CostEntity update(CostEntity Cost) {
        String sql = "UPDATE `cost`" +
                " SET status = ?, id_type = ?, id_car = ?" +
                " WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){

            preparedStatement.setInt(1, Cost.getStatus());
            preparedStatement.setInt(2, Cost.getIdType());
            preparedStatement.setInt(3, Cost.getIdCar());
            preparedStatement.setInt(4, Cost.getId());
            //preparedStatement.setInt(13, Cost.getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Cost.setId(resultSet.getInt("id"));

                return Cost;
            }

        } catch (Exception e) {

        }
        return null;
    }
    public static CostEntity delete(CostEntity Cost) {
        String sql = "DELETE FROM `cost`" +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, Cost.getId());
            preparedStatement.execute();

        } catch (Exception e) {

        }
        return null;
    }
    public List<CostEntity> getByCode(String code) {
        List<CostEntity> costList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `cost` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    CostEntity costEntity = new CostEntity();
                    costEntity.setId(resultSet.getInt("id"));

                    costList.add(costEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by code method: ", e.getStackTrace());
        }

        return costList;
    }
    public List<CostEntity> getByName(String name) {
        List<CostEntity> costList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `cost` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    CostEntity cost = new CostEntity();
                    cost.setId(resultSet.getInt("id"));

                    costList.add(cost);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by name method: ", e.getStackTrace());
        }

        return costList;
    }
    public List<CostEntity> getAll() {
        List<CostEntity> costEntities = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `cost`";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    CostEntity costEntity = new CostEntity();
                    costEntity.setId(resultSet.getInt("id"));
                    costEntity.setId_catAuto(resultSet.getInt("idCat"));

                    costEntities.add(costEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get all faculties method: ", e.getStackTrace());
        }

        return costEntities;
    }
    public List<ReferenceEntity> referencePrint(Date dateFrom, Date dateTo) {
        List<ReferenceEntity> costEntities = new ArrayList<>();
        String sql = " SELECT \"Гражданска отговорност\" as name ,m.reg_plate as car, ca.name as cat, t.date_from, t.date_to " +
                " FROM tpl t JOIN cost c ON c.id=t.id_cost JOIN motorvehicle m ON c.id_car=m.id JOIN catauto ca ON m.id_cat=ca.id" +
                " WHERE t.date_to BETWEEN ? and ?" +
                " UNION" +
                " SELECT \"Автокаско\" as name ,m.reg_plate as car, ca.name as cat, t.date_from, t.date_to " +
                " FROM otherins t JOIN cost c ON c.id=t.id_cost JOIN motorvehicle m ON c.id_car=m.id JOIN catauto ca ON m.id_cat=ca.id" +
                " WHERE t.date_to BETWEEN ? and ?" +
                " UNION" +
                " SELECT \"Технически преглед\" as name ,m.reg_plate as car, ca.name as cat, t.date_from, t.date_to " +
                " FROM techreview t JOIN cost c ON c.id=t.id_cost JOIN motorvehicle m ON c.id_car=m.id JOIN catauto ca ON m.id_cat=ca.id" +
                " WHERE t.date_to BETWEEN ? and ?";// + filter;


        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            //preparedStatement.setString(1, nameST);
            preparedStatement.setDate(1, dateFrom);
            preparedStatement.setDate(2, dateTo);
            preparedStatement.setDate(3, dateFrom);
            preparedStatement.setDate(4, dateTo);
            preparedStatement.setDate(5, dateFrom);
            preparedStatement.setDate(6, dateTo);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ReferenceEntity costEntity = new ReferenceEntity();
                    costEntity.setName(resultSet.getString("name"));
                    costEntity.setCar(resultSet.getString("car"));
                    costEntity.setCat(resultSet.getString("cat"));
                    costEntity.setDate_from(resultSet.getDate("date_from"));
                    costEntity.setDate_to(resultSet.getDate("date_to"));

                    costEntities.add(costEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get all faculties method: ", e.getStackTrace());
        }

        return costEntities;
    }
}
