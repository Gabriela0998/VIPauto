package com.app.manager;

import com.app.service.StartService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.app.manager.DatabaseManager.getConnection;

public class TableManager {
    Connection conn = null; // connection object
    Statement stmt = null; // statement object
    Statement stmt2 = null; // statement object

    public TableManager(String table) throws IOException, ClassNotFoundException, SQLException {

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            stmt2 = conn.createStatement();
            conn.setAutoCommit(false);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch(table) {
            case "login":
                login();
                break;
            case "firms":
                firms();
                break;
            case "CatAuto":
                catAuto();
                break;
            case "TypeCost":
                typeCost();
                break;
            case "TypeDocument":
                typeDocument();
                break;
            case "InsCompanies":
                insCompanies();
                break;
            case "Type":
                type();
                break;
            case "Brand":
                brand();
                break;
            case "Model":
                model();
                break;
            case "Fuel":
                fuel();
                break;
            case "MyData":
                mydata();
                break;
            case "MotorVehicle":
                motor();
                break;
            case "Cost":
                cost();
                thirdPartyLiability();
                technicalReview();
                otherInsurance();
                break;
            case "People":
                people();
                drivingLicence();
                otherDoc();
                peopleDrivingLicence();
                break;
            default:
                // code block
        }
        conn.close();
    }
    public TableManager insurance()throws IOException, ClassNotFoundException, SQLException {
            try {
                String firm =  "CREATE TABLE insurance(id int PRIMARY KEY, "
                        + "name          VARCHAR(50), country         VARCHAR(50), "
                        + "city         VARCHAR(50), address          VARCHAR(150), "
                        + "EIK         VARCHAR(13), DDS         VARCHAR(15), bank   VARCHAR(50), IBAN   VARCHAR(30), bic   VARCHAR(10)"
                        + "manager VARCHAR(50), place VARCHAR(50), phone VARCHAR(20), email     VARCHAR(150), pass_email  VARCHAR(50))";
                stmt.executeUpdate(firm);
                //stmt2.executeUpdate("INSERT INTO user(id, name, password, AO) VALUES(1, 'DEMO', 'DEMO', 0)");
                conn.commit();
                conn.setAutoCommit(true);
            } catch (Exception e){

            }

        return null;
    }
    public TableManager login()throws IOException, ClassNotFoundException, SQLException {
        try {
                String user = "CREATE TABLE user(id int PRIMARY KEY, "
                        + "name          VARCHAR(50), fname         VARCHAR(50), "
                        + "lname         VARCHAR(50), password          VARCHAR(50), "
                        + "admin         int,   AO        int, "
                        + "A1    BOOL, A2    BOOL, A3    BOOL, A4    BOOL, A5    BOOL, "
                        + "A6    BOOL, A7    BOOL, A8    BOOL, A9    BOOL, A10    BOOL, extra VARCHAR(200), type int, id_type int)";
                stmt.executeUpdate(user);
                //stmt2.executeUpdate("INSERT INTO user(id, name, password, AO) VALUES(1, 'DEMO', 'DEMO', 0)");
                conn.commit();
                conn.setAutoCommit(true);
                StartService.getAll();
            } catch (Exception e){
                StartService.getAll();
            }
        return null;
    }

    public TableManager catAuto()throws IOException, ClassNotFoundException, SQLException {
        try {
            String cat_auto =  "CREATE TABLE catauto(id int PRIMARY KEY, "
                    + "name          VARCHAR(150), id_mydata int, CONSTRAINT fk_idmydata6 FOREIGN KEY (id_mydata) REFERENCES mydata(id))";
            stmt.executeUpdate(cat_auto);
            //stmt.executeUpdate(firm);
            //stmt2.executeUpdate("INSERT INTO user(id, name, password, AO) VALUES(1, 'DEMO', 'DEMO', 0)");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }

    public TableManager insCompanies()throws IOException, ClassNotFoundException, SQLException {
        try {
            String cat_auto =  "CREATE TABLE inscompanies(id int PRIMARY KEY, "
                    + "name          VARCHAR(150), phone VARCHAR(20), extra VARCHAR(300), id_mydata int, CONSTRAINT fk_idmydata7 FOREIGN KEY (id_mydata) REFERENCES mydata(id))";
            stmt.executeUpdate(cat_auto);
            //stmt.executeUpdate(firm);
            //stmt2.executeUpdate("INSERT INTO user(id, name, password, AO) VALUES(1, 'DEMO', 'DEMO', 0)");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }

    public TableManager typeCost()throws IOException, ClassNotFoundException, SQLException {
        try {
            String typeCost =  "CREATE TABLE typecost(id int PRIMARY KEY, "
                    + "name          VARCHAR(150), id_mydata int, extra VARCHAR(150), CONSTRAINT fk_idmydata8 FOREIGN KEY (id_mydata) REFERENCES mydata(id))";
            stmt.executeUpdate(typeCost);
            //stmt.executeUpdate(firm);
            //stmt2.executeUpdate("INSERT INTO user(id, name, password, AO) VALUES(1, 'DEMO', 'DEMO', 0)");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }
    public TableManager typeDocument()throws IOException, ClassNotFoundException, SQLException {
        try {
            String typeCost =  "CREATE TABLE typedocument(id int PRIMARY KEY, "
                    + "name          VARCHAR(150), id_mydata int, extra VARCHAR(150), CONSTRAINT fk_idmydata9 FOREIGN KEY (id_mydata) REFERENCES mydata(id))";
            stmt.executeUpdate(typeCost);
            //stmt.executeUpdate(firm);
            //stmt2.executeUpdate("INSERT INTO user(id, name, password, AO) VALUES(1, 'DEMO', 'DEMO', 0)");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }

    public TableManager fuel()throws IOException, ClassNotFoundException, SQLException {
        try {
            String fuel =  "CREATE TABLE fuel(id int PRIMARY KEY, "
                    + "name          VARCHAR(150), id_mydata int, CONSTRAINT fk_idmydata10 FOREIGN KEY (id_mydata) REFERENCES mydata(id))";
            stmt.executeUpdate(fuel);
            //stmt.executeUpdate(firm);
            //stmt2.executeUpdate("INSERT INTO user(id, name, password, AO) VALUES(1, 'DEMO', 'DEMO', 0)");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }
    public TableManager drivingLicence()throws IOException, ClassNotFoundException, SQLException {
        try {
            String fuel =  "CREATE TABLE licence(id int PRIMARY KEY, "
                    + "name          VARCHAR(15))";
            stmt.executeUpdate(fuel);
            //stmt.executeUpdate(firm);
            stmt2.executeUpdate("INSERT INTO `licence`(`id`, `name`) VALUES (1,'AM'), " +
                    " (2,'A1'),(3,'A2'),(4,'A'),(5,'B1'),(6,'B'),(7,'C1'),(8,'C'),(9,'D1'),(10,'D')," +
                    "(11,'BE'),(12,'C1E'),(13,'CE'),(14,'D1E'),(15,'DE'),(16,'Ttm'),(17,'Tkm');");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }
    public TableManager peopleDrivingLicence()throws IOException, ClassNotFoundException, SQLException {
        try {
            String fuel =  "CREATE TABLE peoplelicence(id_licence int, "
                    + "id_people  int, CONSTRAINT fk_idlicence FOREIGN KEY (id_licence) REFERENCES licence(id)" +
                    ", CONSTRAINT fk_idpeople1 FOREIGN KEY (id_people) REFERENCES people(id))";
            stmt.executeUpdate(fuel);
            //stmt.executeUpdate(firm);
            //stmt2.executeUpdate("INSERT INTO user(id, name, password, AO) VALUES(1, 'DEMO', 'DEMO', 0)");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }
    public TableManager otherDoc()throws IOException, ClassNotFoundException, SQLException {
        try {
            String fuel =  "CREATE TABLE otherdoc(id int PRIMARY KEY, "
                    + "extra          VARCHAR(150), datecreate DATE, dateval DATE, numberdoc VARCHAR(20), id_typedoc int, id_people int" +
                    ", CONSTRAINT fk_idpeople2 FOREIGN KEY (id_people) REFERENCES people(id))" +
                    ", CONSTRAINT fk_idtdoc FOREIGN KEY (id_typedoc) REFERENCES typedocument(id)))";
            stmt.executeUpdate(fuel);
            //stmt.executeUpdate(firm);
            //stmt2.executeUpdate("INSERT INTO user(id, name, password, AO) VALUES(1, 'DEMO', 'DEMO', 0)");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }
    public TableManager people()throws IOException, ClassNotFoundException, SQLException {
        try {
            String people =  "CREATE TABLE people(id int PRIMARY KEY, "
                    + "name VARCHAR(150),lname VARCHAR(150),fname VARCHAR(150), egn VARCHAR(13), phone VARCHAR(15), mail VARCHAR(150)," +
                    "dateborn DATE ,address VARCHAR(250),address2 VARCHAR(150), citizenship VARCHAR(100), typep int, statusp int, id_firm int," +
                    "text1 VARCHAR(150), text2 VARCHAR(150), id_driving int, id_mydata int," +
                    " datecreate DATE, dateval DATE, place VARCHAR(100),  numberdoc VARCHAR(20)" +
                    ", CONSTRAINT fk_idmydata11 FOREIGN KEY (id_mydata) REFERENCES mydata(id)" +
                    ", CONSTRAINT fk_idfirms FOREIGN KEY (id_firm) REFERENCES firms(id))";
            stmt.executeUpdate(people);
            //stmt.executeUpdate(firm);
            //stmt2.executeUpdate("INSERT INTO user(id, name, password, AO) VALUES(1, 'DEMO', 'DEMO', 0)");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }
    public TableManager type()throws IOException, ClassNotFoundException, SQLException {
        try {
            String type =  "CREATE TABLE type(id int PRIMARY KEY, "
                    + "name          VARCHAR(150), id_cat int, id_mydata int, CONSTRAINT fk_idmydata1 FOREIGN KEY (id_mydata) REFERENCES mydata(id), " +
                    "CONSTRAINT fk_idcat1 FOREIGN KEY (id_cat) REFERENCES catauto(id))";
            stmt.executeUpdate(type);
            //stmt.executeUpdate(firm);
            //stmt2.executeUpdate("INSERT INTO user(id, name, password, AO) VALUES(1, 'DEMO', 'DEMO', 0)");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }
    public TableManager motor()throws IOException, ClassNotFoundException, SQLException {
        try {
            String type =  "CREATE TABLE motorvehicle(id int PRIMARY KEY, "
                    + "reg_plate          VARCHAR(150), id_cat int, id_type int, id_brand int, id_model int, id_fuel int, status int, "
                    + "mileage double, year YEAR, euro int, frame varchar(50), id_mydata int," +
                    " CONSTRAINT fk_idmydata2 FOREIGN KEY (id_mydata) REFERENCES mydata(id)" +
                    ", CONSTRAINT fk_idcat2 FOREIGN KEY (id_cat) REFERENCES catauto(id)" +
                    ", CONSTRAINT fk_idtype FOREIGN KEY (id_type) REFERENCES type(id)" +
                    ", CONSTRAINT fk_idbrand1 FOREIGN KEY (id_brand) REFERENCES brand(id)" +
                    ", CONSTRAINT fk_idmodel FOREIGN KEY (id_model) REFERENCES model(id)" +
                    ", CONSTRAINT fk_idfuel FOREIGN KEY (id_fuel) REFERENCES fuel(id))";
            stmt.executeUpdate(type);
            //stmt.executeUpdate(firm);
            //stmt2.executeUpdate("INSERT INTO user(id, name, password, AO) VALUES(1, 'DEMO', 'DEMO', 0)");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }
    public TableManager cost()throws IOException, ClassNotFoundException, SQLException {
        try {
            String type =  "CREATE TABLE cost(id int PRIMARY KEY, "
                    + "status int, id_type int, id_car int, id_mydata int," +
                    " CONSTRAINT fk_idmydata3 FOREIGN KEY (id_mydata) REFERENCES mydata(id)" +
                    ", CONSTRAINT fk_idcar FOREIGN KEY (id_car) REFERENCES motorvehicle(id))";
            stmt.executeUpdate(type);
            //stmt.executeUpdate(firm);
            //stmt2.executeUpdate("INSERT INTO user(id, name, password, AO) VALUES(1, 'DEMO', 'DEMO', 0)");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }
    public TableManager thirdPartyLiability()throws IOException, ClassNotFoundException, SQLException {
        try {
            String type =  "CREATE TABLE tpl(id int PRIMARY KEY, "
                    + "id_cost int, id_ins int, numberDeposits int, amount double, date_from date, date_to date, policyNumber varchar(30)," +
                    "amount1 double, date1 date, amount2 double, date2 date, amount3 double, date3 date, amount4 double, date4 date, extra VARCHAR(250)" +
                    ", CONSTRAINT fk_idcost1 FOREIGN KEY (id_cost) REFERENCES cost(id), CONSTRAINT fk_idins FOREIGN KEY (id_ins) REFERENCES inscompanies(id))";

            stmt.executeUpdate(type);
            //stmt.executeUpdate(firm);
            //stmt2.executeUpdate("INSERT INTO user(id, name, password, AO) VALUES(1, 'DEMO', 'DEMO', 0)");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }
    public TableManager otherInsurance()throws IOException, ClassNotFoundException, SQLException {
        try {
            String type =  "CREATE TABLE otherins(id int PRIMARY KEY, "
                    + "id_cost int, numberDeposits int, amount double, date_from date, date_to date, policyNumber varchar(30)," +
                    "amount1 double, date1 date, amount2 double, date2 date, amount3 double, date3 date, amount4 double, date4 date, extra VARCHAR(250)" +
                    ", CONSTRAINT fk_idcost2 FOREIGN KEY (id_cost) REFERENCES cost(id))";

            stmt.executeUpdate(type);
            //stmt.executeUpdate(firm);
            //stmt2.executeUpdate("INSERT INTO user(id, name, password, AO) VALUES(1, 'DEMO', 'DEMO', 0)");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }
    public TableManager technicalReview()throws IOException, ClassNotFoundException, SQLException {
        try {
            String type =  "CREATE TABLE techreview(id int PRIMARY KEY, "
                    + "id_cost int, amount double, date_from date, date_to date, " +
                    "service VARCHAR(100), extra VARCHAR(250)" +
                    ", CONSTRAINT fk_idcost3 FOREIGN KEY (id_cost) REFERENCES cost(id))";

            stmt.executeUpdate(type);
            //stmt.executeUpdate(firm);
            //stmt2.executeUpdate("INSERT INTO user(id, name, password, AO) VALUES(1, 'DEMO', 'DEMO', 0)");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }
    public TableManager model()throws IOException, ClassNotFoundException, SQLException {
        try {
            String model =  "CREATE TABLE model(id int PRIMARY KEY, "
                    + "name          VARCHAR(150), id_brand int, id_mydata int, CONSTRAINT fk_idmydata4 FOREIGN KEY (id_mydata) REFERENCES mydata(id)" +
                    ", CONSTRAINT fk_idbrand2 FOREIGN KEY (id_brand) REFERENCES brand(id))";
            stmt.executeUpdate(model);
            //stmt.executeUpdate(firm);
            //stmt2.executeUpdate("INSERT INTO user(id, name, password, AO) VALUES(1, 'DEMO', 'DEMO', 0)");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }
    public TableManager brand()throws IOException, ClassNotFoundException, SQLException {
        try {
            String brand =  "CREATE TABLE brand(id int PRIMARY KEY, "
                    + "name          VARCHAR(150), id_cat int, id_mydata int, CONSTRAINT fk_idmydata5 FOREIGN KEY (id_mydata) REFERENCES mydata(id)" +
                    ", CONSTRAINT fk_idcat3 FOREIGN KEY (id_cat) REFERENCES catauto(id))";
            stmt.executeUpdate(brand);
            //stmt.executeUpdate(firm);
            //stmt2.executeUpdate("INSERT INTO user(id, name, password, AO) VALUES(1, 'DEMO', 'DEMO', 0)");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }
    public TableManager firms()throws IOException, ClassNotFoundException, SQLException {
        try {
            String firm =  "CREATE TABLE firms(id int PRIMARY KEY, "
                    + "name          VARCHAR(50), country         VARCHAR(50), "
                    + "city         VARCHAR(50), address          VARCHAR(150), "
                    + "EIK         VARCHAR(13), DDS         VARCHAR(15), bank   VARCHAR(50), IBAN   VARCHAR(30), bic   VARCHAR(10),"
                    + "manager VARCHAR(50), place VARCHAR(50), phone VARCHAR(20), email     VARCHAR(150), pass_email  VARCHAR(50))";
            stmt.executeUpdate(firm);
            //stmt2.executeUpdate("INSERT INTO firm(id, name) VALUES(1, 'ИМЕ НА ФИРМАТА')");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }
    public TableManager mydata()throws IOException, ClassNotFoundException, SQLException {
        try {
            String firm =  "CREATE TABLE mydata(id int PRIMARY KEY, "
                    + "name          VARCHAR(50), country         VARCHAR(50), "
                    + "city         VARCHAR(50), address          VARCHAR(150), "
                    + "EIK         VARCHAR(13), DDS         VARCHAR(15), bank   VARCHAR(50), IBAN   VARCHAR(30), bic   VARCHAR(10),"
                    + "manager VARCHAR(50), place VARCHAR(50), phone VARCHAR(20), email     VARCHAR(150), pass_email  VARCHAR(50))";
            stmt.executeUpdate(firm);
            stmt2.executeUpdate("INSERT INTO mydata(id, name) VALUES(1, 'ИМЕ НА ФИРМАТА')");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e){

        }
        return null;
    }
}
