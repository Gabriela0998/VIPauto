package com.app.view;

import com.app.export.ExportDataToExcel;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.app.controller.*;
//import com.app.export.ExportDataToExcel;

public class StartMenu {


    Stage subStage = new Stage();

    public StartMenu(int a0, String nameUser)
    {
        String title = "";
        if (a0==0){
            title = "VIPauto" + "     " + "Потребител:" + nameUser + "   ДЕМО ВЕРСИЯ";
        }else{
            title = "VIPauto" + "     " + "Потребител:" + nameUser;
        }

        subStage.setTitle(title);
        //subStage.getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,700,200 );

        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("Сервизни функции");
        MenuItem firmMenuItem = new MenuItem("Моята фирма");
        firmMenuItem.setOnAction(eve-> {
            MyDataController table = new MyDataController();
            new MyData();});
        MenuItem userMenuItem = new MenuItem("Потребители");
        userMenuItem.setOnAction(eve-> new User());
        MenuItem exitMenuItem = new MenuItem("Изход");
        exitMenuItem.setOnAction(eve-> System.exit(0) );

        Menu nMenu = new Menu("Номенклатури за автомобила");
        MenuItem catMenuItem = new MenuItem("Категория МПС");
        catMenuItem.setOnAction(eve-> {
                CatAutoController table = new CatAutoController();
                new CatAuto();});
        MenuItem typeMenuItem = new MenuItem("Тип/Купе");
        typeMenuItem.setOnAction(eve-> {
            TypeController table = new TypeController();
            new Type();});
        MenuItem brandMenuItem = new MenuItem("Марка МПС");
        brandMenuItem.setOnAction(eve-> {
            BrandController table = new BrandController();
            new Brand();});
        MenuItem modelMenuItem = new MenuItem("Модел МПС");
        modelMenuItem.setOnAction(eve-> {
            ModelController table = new ModelController();
            new Model();});
        MenuItem fuelMenuItem = new MenuItem("Мотор (гориво)");
        fuelMenuItem.setOnAction(eve-> {
            FuelController table = new FuelController();
            new Fuel();});
        MenuItem vehicleMenuItem = new MenuItem("МПС");
        vehicleMenuItem.setOnAction(eve-> {
            MotorVehicleController table = new MotorVehicleController();
            new MotorVehicle();});


        Menu n2Menu = new Menu("Номенклатури за лица");
        MenuItem firmsMenuItem = new MenuItem("Фирми");
        firmsMenuItem.setOnAction(eve-> {
            FirmsController table = new FirmsController();
            new Firm();});
        MenuItem extraDocMenuItem = new MenuItem("Видове допълнителни документи");
        extraDocMenuItem.setOnAction(eve-> {
            TypeDocumentController table = new TypeDocumentController();
            new TypeDocument();});
        MenuItem peopleMenuItem = new MenuItem("Лица");
        peopleMenuItem.setOnAction(eve-> {
            PeopleController table = new PeopleController();
            new People();});


        Menu n3Menu = new Menu("Номенклатури за разходи");
        MenuItem costsMenuItem = new MenuItem("Вид разходи");
        costsMenuItem.setOnAction(eve-> {
            TypeCostController table = new TypeCostController();
            new TypeCost();});
        MenuItem otherMenuItem = new MenuItem("Застр. дружества");
        otherMenuItem.setOnAction(eve-> {
            InsCompaniesController table = new InsCompaniesController();
            new InsCompanies();});

        Menu iMenu = new Menu("Застраховки и такси");
        MenuItem iMenuItem = new MenuItem("Застраховки,данъци и други такси");
        iMenuItem.setOnAction(event -> {
            CostController table = new CostController();
            new Cost();
        });
        MenuItem sub2MenuItem = new MenuItem("Sub2");

        Menu sMenu = new Menu("Справки");
        MenuItem sMenuItem = new MenuItem("Справка разходи по автомобили");
        sMenuItem.setOnAction(event -> {new ReferenceCostCar();});
        MenuItem pMenuItem = new MenuItem("Справка лица");
        pMenuItem.setOnAction(event -> { new ReferencePeople();
             });
        MenuItem sub3MenuItem = new MenuItem("Sub2");


        fileMenu.getItems().addAll(firmMenuItem,userMenuItem,exitMenuItem);
        nMenu.getItems().addAll(catMenuItem,typeMenuItem,brandMenuItem,modelMenuItem,fuelMenuItem,vehicleMenuItem);
        n2Menu.getItems().addAll(firmsMenuItem, extraDocMenuItem, peopleMenuItem);
        n3Menu.getItems().addAll(costsMenuItem,otherMenuItem);
        iMenu.getItems().addAll(iMenuItem);
        sMenu.getItems().addAll(sMenuItem, pMenuItem);
        menuBar.getMenus().addAll(fileMenu, nMenu, n2Menu, n3Menu, iMenu, sMenu);

        root.setTop(menuBar);
        subStage.setScene(scene);
        subStage.show();
        subStage.setMaximized(true);/*Screen(true);*/
        subStage.setOnCloseRequest(event -> System.exit(0)); //При изход от формата цялото приложение се затваря
    }


    /*public static void main(String[] args) {
        String driverName = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String serverName = "localhost:3306";
        String mydatabase = "v-auto";
        String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

        String username = "root";
        String password = "";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        launch(args);
    }*/
}
