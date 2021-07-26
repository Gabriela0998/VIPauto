package com.app.controller;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.app.entity.PeopleEntity;
import com.app.manager.TableManager;
import com.app.view.People;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.manager.DatabaseManager.getConnection;

public class PeopleController {
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
    public PeopleController(){
        String table = "People";
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

    public static Integer getExistingFirmsId(String firmsName) {
        String sql = "SELECT id " +
                "FROM firms " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);) {

            preparedStatement.setString(1, firmsName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    PeopleEntity type = new PeopleEntity();
                    type.setIdFirms(resultSet.getInt("id"));

                    return type.getIdFirms();
                }
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public static PeopleEntity vis(PeopleEntity people) {
        String sql = "SELECT * " +
                " FROM people WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, people.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                PeopleEntity peopleEntity = new PeopleEntity();
                peopleEntity.setId(resultSet.getInt("id"));
                peopleEntity.setName(resultSet.getString("name"));
                peopleEntity.setLname(resultSet.getString("lname"));
                peopleEntity.setFname(resultSet.getString("fname"));
                peopleEntity.setEgn(resultSet.getString("egn"));
                peopleEntity.setDateBorn(resultSet.getDate("dateborn"));


                //People.visible(nameTF,idTF, comboBoxCat, comboBoxType, comboBoxBrand, comboBoxModel, comboBoxFuel, comboBoxYear, frame, euro, status, mileage);
                People.visible(people);


                return people;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static PeopleEntity show() {
        PeopleEntity people = new PeopleEntity();
        String sql = "SELECT * FROM people LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, people.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                /*String nameTF = resultSet.getString("reg_plate");
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
                PeopleController.getByIdCat(comboBoxCat);
                PeopleController.getByIdType(comboBoxType);
                PeopleController.getByIdBrand(comboBoxBrand);
                PeopleController.getByIdModel(comboBoxModel);
                PeopleController.getByIdFuel(comboBoxFuel);
                People.visible(nameTF,idTF, comboBoxCat, comboBoxType, comboBoxBrand, comboBoxModel, comboBoxFuel, comboBoxYear, frame, euro, status, mileage);
*/
                return people;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static PeopleEntity idNum() {
        PeopleEntity people = new PeopleEntity();
        String sql = "SELECT (MAX(id)+1) as idnum FROM people";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            // preparedStatement.setInt(1, people.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String idTF = resultSet.getString("idnum");
                People.idNumber(idTF);

                return people;
            }

        } catch (Exception e) {
        }
        return null;
    }
    public static PeopleEntity getById(PeopleEntity People) {
        String sql = "SELECT * FROM people WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, People.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Внимание!");
                alert.setHeaderText("Вие ще редактирате запис!");
                alert.setContentText("Желаете ли да продължите?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    update(People);}
            }else {
                insert(People);
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
    public static PeopleEntity insert(PeopleEntity People) {
        String sql = "INSERT INTO `people`(`id`, `name`, `lname`, `fname`, `egn`, `phone`, `mail`, `dateborn`, `address`, `address2`, `citizenship`," +
                " `typep`, `statusp`, `id_firm`, `text1`, `datecreate`, `dateval`, `place`, `numberdoc`)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, People.getId());
            preparedStatement.setString(2, People.getName());
            preparedStatement.setString(3, People.getLname());
            preparedStatement.setString(4, People.getFname());
            preparedStatement.setString(5, People.getEgn());
            preparedStatement.setString(6, People.getPhone());
            preparedStatement.setString(7, People.getMail());
            preparedStatement.setString(8, String.valueOf(People.getDateBorn()));
            preparedStatement.setString(9, People.getAddress());
            preparedStatement.setString(10, People.getAddressSecond());
            preparedStatement.setString(11, People.getBornCountry());
            preparedStatement.setInt(12, People.getTypePerson());
            preparedStatement.setInt(13, People.getStatusPerson());
            preparedStatement.setInt(14, People.getIdFirms());
            preparedStatement.setString(15, People.getText1());
            preparedStatement.setString(16, String.valueOf(People.getDateC()));
            preparedStatement.setString(17, String.valueOf(People.getDateV()));
            preparedStatement.setString(18, People.getPlace());
            preparedStatement.setString(19, People.getNumberDoc());
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                People.setId(resultSet.getInt("id"));
                //People.setRegPlate(resultSet.getString("name"));
                //People.setIdCat(resultSet.getInt("id_cat"));

                return People;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public Integer getExistingPeopleIdCat(String comboBoxName) {
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
                PeopleEntity people = new PeopleEntity();
                /*people.setIdCat(resultSet.getInt("id"));
                int idCat = resultSet.getInt("id");
                People.idNumberCat(String.valueOf(idCat));
                return people.getIdCat();*/
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public Integer getExistingPeopleIdType(String comboBoxName) {
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
                PeopleEntity people = new PeopleEntity();
                /*people.setIdType(resultSet.getInt("id"));
                return people.getIdType();*/
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public Integer getExistingPeopleIdBrand(String comboBoxName) {
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
                PeopleEntity people = new PeopleEntity();
                /*people.setIdBrand(resultSet.getInt("id"));
                int idBrand=resultSet.getInt("id");
                People.idNumberBrand(String.valueOf(idBrand));
                return people.getIdBrand();*/
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public Integer getExistingPeopleIdBrand(String comboBoxName, String nameBrand) {
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
                PeopleEntity people = new PeopleEntity();
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public Integer getExistingPeopleIdModel(String comboBoxName) {
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
                PeopleEntity people = new PeopleEntity();
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public Integer getExistingPeopleIdFuel(String comboBoxName) {
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
                PeopleEntity people = new PeopleEntity();
            }
        } catch (Exception e) {
            // LOGGER.error("Exception in get existing major id method: ", e.getStackTrace());
        }

        return null;
    }
    public static PeopleEntity update(PeopleEntity People) {
        String sql = "UPDATE `people`" +
                "SET name = ?, lname = ?, fname = ?, egn = ?, phone = ?, mail = ?, dateborn = ?," +
                " address = ?, address2 = ?, citizenship = ?, typep  = ?,  statusp = ?, id_firm = ?, " +
                " text1 = ?, id_driving = ?,  datecreate = ?, dateval = ?, place = ?, numberdoc = ? " +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){

            preparedStatement.setString(1, People.getName());
            preparedStatement.setInt(12, People.getId());
            //preparedStatement.setInt(13, People.getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                People.setId(resultSet.getInt("id"));

                return People;
            }

        } catch (Exception e) {

        }
        return null;
    }
    public static PeopleEntity delete(PeopleEntity People) {
        String sql = "DELETE FROM `people`" +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, People.getId());
            preparedStatement.execute();

        } catch (Exception e) {

        }
        return null;
    }
    public List<PeopleEntity> getByCode(String code) {
        List<PeopleEntity> peopleList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `people` " +
                "WHERE egn = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    PeopleEntity peopleEntity = new PeopleEntity();
                    peopleEntity.setId(resultSet.getInt("id"));
                    peopleEntity.setName(resultSet.getString("name"));
                    peopleEntity.setLname(resultSet.getString("lname"));
                    peopleEntity.setFname(resultSet.getString("fname"));
                    peopleEntity.setEgn(resultSet.getString("egn"));
                    peopleEntity.setDateBorn(resultSet.getDate("dateborn"));

                    peopleList.add(peopleEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by code method: ", e.getStackTrace());
        }

        return peopleList;
    }
    public List<PeopleEntity> getByName(String name) {
        List<PeopleEntity> peopleList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM `people` " +
                "WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    PeopleEntity peopleEntity = new PeopleEntity();
                    peopleEntity.setId(resultSet.getInt("id"));
                    peopleEntity.setName(resultSet.getString("name"));
                    peopleEntity.setLname(resultSet.getString("lname"));
                    peopleEntity.setFname(resultSet.getString("fname"));
                    peopleEntity.setDateBorn(resultSet.getDate("dateborn"));

                    peopleList.add(peopleEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get faculties by name method: ", e.getStackTrace());
        }

        return peopleList;
    }
    public List<PeopleEntity> getAll() {
        List<PeopleEntity> peopleEntities = new ArrayList<>();
        String sql = "SELECT id, name, lname, fname, dateborn as born " +
                "FROM `people`";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    PeopleEntity peopleEntity = new PeopleEntity();
                    peopleEntity.setId(resultSet.getInt("id"));
                    peopleEntity.setName(resultSet.getString("name"));
                    peopleEntity.setLname(resultSet.getString("lname"));
                    peopleEntity.setFname(resultSet.getString("fname"));
                    peopleEntity.setDateBorn(resultSet.getDate("born"));

                    peopleEntities.add(peopleEntity);
                }
            }
        } catch (Exception e) {
            //LOGGER.error("Exception in get all faculties method: ", e.getStackTrace());
        }

        return peopleEntities;
    }
}
