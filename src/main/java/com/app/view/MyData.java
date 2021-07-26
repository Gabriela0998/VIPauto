package com.app.view;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import com.app.controller.MyDataController;
import com.app.entity.MyDataEntity;
import com.app.helper.Helpers;
import com.app.helper.MyDataHelper;
import com.app.service.MyDataService;

import java.text.ParseException;

//import static com.app.controller.MyDataController.visible;

public class MyData {

    //private static final Logger LOGGER = LoggerFactory.getLogger(MyData.class);

    private MyDataService facultyService = new MyDataService();
    private MyDataEntity myData = new MyDataEntity();
    private TableView<MyDataEntity> facultyTableView;
    private ObservableList<MyDataEntity> facultyObservableList;
    Stage subStage;

    static public TextField nameTF;
    static public TextField city;
    static public TextField country;
    static public TextField address;
    static public TextField EIK;
    static public TextField DDS;
    static public TextField bank;
    static public TextField Iban;
    static public TextField bic;
    static public TextField manager;
    static public TextField place;
    static public TextField phone;
    public MyData() {
        subStage = new Stage();
        subStage.setTitle("Данни за фирмата");

        //FlowPane root = new FlowPane();
        //root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(createFacultyCreateFormPane(), 700, 700);

        //root.getChildren().add(new Button("New Stage"));
        subStage.setScene(scene);
        subStage.show();
    }


    public GridPane createFacultyCreateFormPane() {
        GridPane gridPane = new GridPane();

        MyDataHelper.setGridPane(gridPane);
        addUIControls(gridPane);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Данни за фирмата");
        headerLabel.setFont(new Font("Arial", 26));

        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        //Faculty name label and text
        MyDataHelper.addLabel(gridPane, "Име на фирмата: ", 0, 1);
        nameTF = MyDataHelper.addTextField(gridPane, "", 1, 1);
        //ValidationHelper.lettersOnly(nameTF, 200);

        //country label and text
        MyDataHelper.addLabel(gridPane, "Държава: ", 0, 2);
        country = MyDataHelper.addTextField(gridPane, "", 1, 2);
        //ValidationHelper.lettersOnly(country, 20);

        //city label and text
        MyDataHelper.addLabel(gridPane, "Град: ", 0, 3);
        city = MyDataHelper.addTextField(gridPane, "", 1, 3);
        //ValidationHelper.lettersOnly(city, 50);

        //address label and text
        MyDataHelper.addLabel(gridPane, "Адрес: ", 0, 4);
        address = MyDataHelper.addTextField(gridPane, "", 1, 4);
        //ValidationHelper.lettersOnly(address, 100);

        //EIK label and text
        MyDataHelper.addLabel(gridPane, "ЕИК: ", 0, 5);
        EIK = MyDataHelper.addTextField(gridPane, "", 1, 5);
        //ValidationHelper.lettersAndNumberOnly(EIK, 13);

        //DDS label and text
        MyDataHelper.addLabel(gridPane, "Регистрация по ДДС: ", 0, 6);
        DDS = MyDataHelper.addTextField(gridPane, "", 1, 6);
        //ValidationHelper.lettersAndNumberOnly(DDS, 15);

        //bank label and text
        MyDataHelper.addLabel(gridPane, "Банка: ", 0, 7);
        bank = MyDataHelper.addTextField(gridPane, "", 1, 7);
        //ValidationHelper.lettersOnly(bank, 50);

        //Iban label and text
        MyDataHelper.addLabel(gridPane, "IBAN: ", 0, 8);
        Iban = MyDataHelper.addTextField(gridPane, "", 1, 8);
        //ValidationHelper.lettersAndNumberOnly(Iban, 30);

        //bic label and text
        MyDataHelper.addLabel(gridPane, "BIC: ", 0, 9);
        bic = MyDataHelper.addTextField(gridPane, "", 1, 9);
        //bic.setText("Not Glowing");
        //ValidationHelper.lettersAndNumberOnly(bic, 10);

        //manager label and text
        MyDataHelper.addLabel(gridPane, "МОЛ: ", 0, 10);
        manager = MyDataHelper.addTextField(gridPane, "", 1, 10);
        //ValidationHelper.lettersOnly(manager, 50);

        //place label and text
        MyDataHelper.addLabel(gridPane, "Място на сделката: ", 0, 11);
        place = MyDataHelper.addTextField(gridPane, "", 1, 11);
        //ValidationHelper.lettersOnly(place, 50);

        //phone label and text
        MyDataHelper.addLabel(gridPane, "Телефон: ", 0, 12);
        phone = MyDataHelper.addTextField(gridPane, "", 1, 12);
        //ValidationHelper.lettersAndNumberOnly(phone, 20);

        //TextField searchBoxTF = new TextField();
        Button editButton = new Button("Редакция");
        Button addButton = new Button("Запис");
        Button exitButton = new Button("Изход");

       MyDataHelper.initSearchSection(gridPane, editButton, addButton, exitButton,
                "", 1, 14);

        //gridPane.add(getTableView(getFacultyInfo(false, null), gridPane), 1, 9);

        //visible(myData, nameTF, country, city, address, EIK, DDS, bank,
         //       Iban, bic, manager, phone, place);
        MyDataController.read();

        addButton.setOnAction(e -> {
            try {
                onSubmit(gridPane, myData, nameTF, country, city, address, EIK, DDS, bank,
                        Iban, bic, manager, phone, place);
                //gridPane.add(getTableView(getFacultyInfo(false, null), gridPane), 1, 9);
            } catch (ParseException ex) {
                // LOGGER.error("Exception in submit button set on action: ", ex.getStackTrace());
            }
        });
        editButton.setOnAction(e -> {
                MyDataHelper.clearForm(gridPane, myData, nameTF, country, city, address, EIK, DDS, bank,
                        Iban, bic, manager, phone, place);
        });

        /*searchButton.setOnAction(e -> {
            gridPane.add(getTableView(getFacultyInfo(true, searchBoxTF.getText()), gridPane), 1, 9);
        });*/

        exitButton.setOnAction(e -> {
            subStage.close();
        });
    }
    private void onSubmit(GridPane gridPane, MyDataEntity myData, TextField nameTF, TextField country, TextField city,
                          TextField address, TextField EIK, TextField DDS, TextField bank,
                          TextField Iban, TextField bic, TextField manager, TextField phone, TextField place) throws ParseException {
        if (nameTF.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Не е попълнено име на фирмата!");
            return;
        }
        if (EIK.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Не е попълнен ЕИК/Булстат!");
            return;
        }

        createFaculty(nameTF.getText(), country.getText(), EIK.getText(), address.getText(), city.getText(),
                bank.getText(), Iban.getText(), bic.getText(), DDS.getText(), manager.getText(), place.getText(), phone.getText());
        Helpers.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(),
                "Записа успешен!", "Успешно записахте данните на Вашата фирма!");
        //ViewHelper.clearForm(nameTF, EIK);
        MyDataController.read();
    }


    private void createFaculty(String name, String country, String EIK, String address, String city,
                               String bank, String iban, String bic, String dds, String manager, String place, String phone) {
        MyDataService myDataService = new MyDataService();

        MyDataEntity myData = new MyDataEntity();
        myData.setName(name);
        myData.setEIK(EIK);
        myData.setCountry(country);
        myData.setAddress(address);
        myData.setBank(bank);
        myData.setIBAN(iban);
        myData.setBIC(bic);
        myData.setDDS(dds);
        myData.setManager(manager);
        myData.setPhone(phone);
        myData.setCity(city);
        myData.setPlace(place);

        myDataService.insert(myData);
    }

    public static void visible(String name, String countryS, String cityS, String addressS,
                               String eikS, String ddsS, String bankS, String ibanS, String bicS, String managerS, String placeS, String phoneS) {
        nameTF.setText(name);
        if(countryS!=null || countryS!=""){
            country.setText(countryS);}
        if(cityS!=null || cityS!=""){
            city.setText(cityS);}
        if(addressS!=null || addressS!=""){
            address.setText(addressS);}
        if(eikS!=null || eikS!=""){
            EIK.setText(eikS);}
        if(ddsS!=null || ddsS!=""){
            DDS.setText(ddsS);}
        if(bankS!=null || bankS!=""){
            bank.setText(bankS);}
        if(ibanS!=null || ibanS!=""){
            Iban.setText(ibanS);}
        if(bicS!=null || bicS!=""){
            bic.setText(bicS);}
        if(managerS!=null || managerS!=""){
            manager.setText(managerS);}
        if(placeS!=null || placeS!=""){
            place.setText(placeS);}
        if(phoneS!=null || phoneS!=""){
            phone.setText(phoneS);}
    }
}
