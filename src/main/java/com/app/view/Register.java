package com.app.view;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import com.app.controller.UserController;
import com.app.entity.UserEntity;
import com.app.helper.Helpers;
import com.app.helper.ViewHelper;
import com.app.service.UserService;

import java.text.ParseException;

public class Register {

    //private static final Logger LOGGER = LoggerFactory.getLogger(User.class);

    private UserService userService = new UserService();
    private UserEntity user = new UserEntity();
    private TableView<UserEntity> userTableView;
    private ObservableList<UserEntity> userObservableList;
    Stage subStage;

    static public TextField nameTF;
    static public PasswordField passwordTF;
    static public PasswordField passTF;
    static public RadioButton t1,t2;
    private static int type;
    private static int itype;

    public Register() {
        subStage = new Stage();
        subStage.setTitle("Регистрация");
        Scene scene = new Scene(createuserCreateFormPane(), 1200, 700);
        subStage.setScene(scene);
        subStage.show();
    }


    public GridPane createuserCreateFormPane() {
        GridPane gridPane = new GridPane();

        ViewHelper.setGridPane(gridPane);
        addUIControls(gridPane);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        RadioButton firm = new RadioButton();

        Label headerLabel = new Label("Регистрация");
        headerLabel.setFont(new Font("Arial", 26));

        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        //user name label and text
        ViewHelper.addLabel(gridPane, "Потребителско име: ", 0, 1);
        nameTF = ViewHelper.addTextField(gridPane, "", 1, 1);
        //ValidationHelper.lettersOnly(nameTF, 200);
        //user name label and text
        ViewHelper.addLabel(gridPane, "Парола: ", 0, 2);
        passwordTF = ViewHelper.addPassField(gridPane, "", 1, 2);

        ViewHelper.addLabel(gridPane, "Парола: ", 0, 3);
        passTF = ViewHelper.addPassField(gridPane, "", 1, 3);
        //ValidationHelper.lettersOnly(nameTF, 200);

        ViewHelper.addLabel(gridPane, "Ползва се за: ", 0, 4);
        t1=new RadioButton("Частно лице");
        //t1.setBounds(100,50,100,30);
        t2=new RadioButton("Фирма");
        //t2.setBounds(100,100,100,30);
        ViewHelper.RadioButton(gridPane, t1, t2,"",1,4);


        Button addButton = new Button("Продължи");
        Button exitButton = new Button("Изход");

        ViewHelper.initSearchSection(gridPane, addButton, exitButton,
                "", 1, 5);


        addButton.setOnAction(e -> {
            try {
                if(t1.isSelected()){
                    type = 1;
                }
                if(t2.isSelected()){
                    type = 2;
                }
                onSubmit(gridPane, user, type, nameTF, passwordTF, passTF);
                //gridPane.add(getTableView(getuserInfo(false, null), gridPane),0,6, 2, 1);
            } catch (ParseException ex) {
                // LOGGER.error("Exception in submit button set on action: ", ex.getStackTrace());
            }
        });
       /* editButton.setOnAction(e -> {
            ViewHelper.clearForm(gridPane, user, idTF, nameTF);
            UserController.idNum();
        });*/
        exitButton.setOnAction(e -> {
            subStage.close();
        });
    }
    static Integer idTF;
    public static void idNumber(Integer id) {
        idTF = id;
        if(idTF==null || idTF==0){
            idTF = 1;
        }
    }
    private void onSubmit(GridPane gridPane, UserEntity User, int type,TextField nameTF, PasswordField passwordTF, PasswordField passTF) throws ParseException {

        String myPassword = passwordTF.getText();
        String myPass = passTF.getText();

        if (nameTF.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Не е попълнено потребителското име!");
            return;
        }
        if (passwordTF.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Не е попълнена паролата!");
            return;
        }
        if (passTF.getText().isEmpty()) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Моля повторете паролата!");
            return;
        }
        if (!myPass.equals(myPassword)) {
            Helpers.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Паролите не съвпадат!");
            return;
        }

        UserController.idNum();
        createUser(idTF, nameTF.getText(),myPassword, type) ;
        UserService.next(idTF);
        subStage.close();
        NextMyData next = new NextMyData(itype, idTF);
        /*Helpers.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(),
                "Записа успешен!", "Успешно записахте данните на вида на мотора!");*/
    }


    private void createUser(Integer id, String name, String password, Integer type) {
        UserService UserService = new UserService();

        UserEntity User = new UserEntity();
        User.setId(id);
        User.setName(name);
        User.setPassword(password);
        User.setType(type);

        UserService.getById(User);
    }

    public static void visible(String name, String id) {
        nameTF.setText(name);
        //idTF.setText(id);
    }
    public static void next(int id) {
        itype = id;
    }
}
