package com.example.kursova_train_003;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.*;

public class Headmenu_Controller {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private Label tRWarning;

    @FXML
    private Button btnALog_into;

    @FXML
    private Button btnHAutorization;

    @FXML
    private Button btnHRegister;

    @FXML
    private Button btnRCreate_acc;

    @FXML
    private Pane pane_create;

    @FXML
    private Pane pane_register;

    @FXML
    private TextField tALogin;

    @FXML
    private TextField tApassword;

    @FXML
    private Label tLAWarning;

    @FXML
    private TextField tRFatherName;

    @FXML
    private TextField tRFirstName;

    @FXML
    private TextField tRLastName;

    @FXML
    private TextField tRLogin;



    @FXML
    private TextField tRNuberPhone;

    @FXML
    private TextField tRPassword;




    @FXML
    public void initialize() {
        tRLogin.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9_]{0,20}")) {
                tRLogin.setText(oldValue);
            }
        });

        tRPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9_]{0,20}")) {
                tRPassword.setText(oldValue);
            }
        });

        tRFirstName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[а-яА-ЯїЇІіЄєA-Za-z]{0,30}")) {
                tRFirstName.setText(oldValue);
            }

        });
        tRLastName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[а-яА-ЯїЇІіЄєA-Za-z]{0,30}")) {
                tRLastName.setText(oldValue);
                }
        });
        tRFatherName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[а-яА-ЯїЇІіЄєA-Za-z]{0,30}")) {
                tRFatherName.setText(oldValue);
            }});

            tRNuberPhone.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("[0-9]{0,10}")) {
                    tRNuberPhone.setText(oldValue);
                }



        });




    }














    @FXML
    void btnALog_intoA(ActionEvent event)  throws Exception {
        if (tApassword.getText().equals("") || tALogin.getText().equals("")) {

            tLAWarning.setText("*Якийсь рядок порожній");
            tLAWarning.setVisible(true);
        } else {


            Users users = new Users(tALogin.getText(),tApassword.getText());
            con = DBConnextion.getCon();
            try {
                st = con.prepareStatement("select * from users where Login = '"+ tALogin.getText()+ "' and Password = '"+ tApassword.getText()+ "' ");
                rs = st.executeQuery();
                if(rs.next()){
                    String status = rs.getString("status");
                    if(status.equals("admin")){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("HeadAdminMenu.fxml"));
                        Parent root = loader.load();
                      //  HeadAdminMenu_Controller HeadAdminMenu_Controler = loader.getController();

                        Stage stage = (Stage) btnALog_into.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Iнтерфейс Адміна");
                        stage.show();
                    }
                    else if(status.equals("passenger")){
                        System.out.println("1");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("HeadUserMenu.fxml"));
                        Parent root = loader.load();
                        HeadUserMenu_Controler headUserMenuControler = loader.getController();

                        Methods methods = new Methods();
                        methods.addAll_info(users);
                        headUserMenuControler.setWelcomeMessage(users);

                        Stage stage = (Stage) btnALog_into.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Iнтерфейс користувача");
                        stage.show();
                    }
                }
                else{

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }
    @FXML
    void btnHAutorizationA(ActionEvent event) {
        pane_create.setVisible(false);
        pane_register.setVisible(true);
        btnHAutorization.setDisable(true);
        btnHRegister.setDisable(false);
    }

    @FXML
    void btnHRegisterA(ActionEvent event) {
        pane_create.setVisible(true);
        pane_register.setVisible(false);
        btnHRegister.setDisable(true);
        btnHAutorization.setDisable(false);
    }

    @FXML
    void btnRCreate_accA(ActionEvent event) {
        /* tRLogin.getText().equals("")||
         tRPassword.getText().equals("")||
         tRFirstName.getText().equals("")||
         tRLastName.getText().equals("")||
         tRFatherName.getText().equals("")||
         tRNuberPhone.getText().equals("")

        */
        if( tRLogin.getText().equals("")||
                tRPassword.getText().equals("")||
                tRFirstName.getText().equals("")||
                tRLastName.getText().equals("")||
                tRFatherName.getText().equals("")||
                tRNuberPhone.getText().equals("")){
            //tRWarning.setVisible(true);
            tRWarning.setVisible(true);
            tRWarning.setText("*Заповніть рядок");
            }
        else {
            con = DBConnextion.getCon();
            try {
                st = con.prepareStatement("INSERT INTO users (Login, Password, FirstName, LastName, FatherName, numberphone, status)\n" +
                        "VALUES (?, ?, ?, ?, ?, ?, 'passenger')");
                st.setString(1, tRLogin.getText());
                st.setString(2, tRPassword.getText());
                st.setString(3, tRFirstName.getText());
                st.setString(4, tRLastName.getText());
                st.setString(5, tRFatherName.getText());
                st.setString(6, tRNuberPhone.getText());
                int rowsAffected = st.executeUpdate();
                if (rowsAffected != Statement.EXECUTE_FAILED && rowsAffected > 0) {
                    tRWarning.setVisible(true);
                    tRWarning.setText("Ви успішно зареєструвалися");
                    tRWarning.setTextFill(Color.GREEN);
                }
            } catch (SQLException e) {
                if (e.getErrorCode() == 1062) {
                    System.out.println("ok");
                    // Помилка унікального обмеження
                    if (e.getMessage().toLowerCase().contains("login")) {
                        System.out.println("jack");
                        // Повторення логіну
                        tRWarning.setVisible(true);
                        tRWarning.setText("*Даний логін вже зайнятий. Будь ласка, виберіть інший логін.");
                    } else if (e.getMessage().toLowerCase().contains("numberphone")) {
                        // Повторення номера телефону
                        tRWarning.setVisible(true);
                        tRWarning.setText("*Даний номер телефону вже зареєстрований. Будь ласка, використовуйте інший номер.");
                    }
            }
                else if(e.getErrorCode() == 1146){
                    tRWarning.setVisible(true); tRWarning.setText("*Ви не виконали умови вводу");
                }
                else {
                    e.printStackTrace();
                }
            }


        }

    }

}
