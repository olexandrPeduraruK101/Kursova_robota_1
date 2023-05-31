package com.example.kursova_train_003;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TableUsersView_Controller implements Initializable {

    private Connection con;
    private PreparedStatement st;
    private ResultSet rs;

    UsersAll users =new UsersAll();

    @FXML
    void btnUpdate(ActionEvent event) {
        showUsers();
    }


    @FXML
    void getData(MouseEvent event) {
        UsersAll usersAlls = table.getSelectionModel().getSelectedItem();
        tRedag0.setText(String.valueOf(usersAlls.getId()));
        tRedag1.setText(String.valueOf(usersAlls.getFirstName()));
        tRedag2.setText(String.valueOf(usersAlls.getLastName()));
        tRedag3.setText(String.valueOf(usersAlls.getFatherName()));
        tDelete.setText(String.valueOf(usersAlls.getId()));
    }
    //----------------------------------------------------

    @FXML
    private TableColumn<UsersAll, Integer> colid;

    @FXML
    private TableColumn<UsersAll, String> colLogin;

    @FXML
    private TableColumn<UsersAll, String> colPassword;

    @FXML
    private TableColumn<UsersAll, String> colFirstName;

    @FXML
    private TableColumn<UsersAll, String> colLastName;

    @FXML
    private TableColumn<UsersAll, String> colFatherName;

    @FXML
    private TableColumn<UsersAll, String> colnumberphone;

    @FXML
    private TableColumn<UsersAll, String> colstatus;

    @FXML
    private TableView<UsersAll> table;
    //--------------------------------------------------------------------------
    //tab пошук
    @FXML
    private Label warningPoshuk;


    @FXML
    private TextField tPoshuk0;



    @FXML
    void btnPoshuk0A(ActionEvent event) {


        users.setLogin(tPoshuk0.getText());


            warningPoshuk.setText("");
            if (tPoshuk0.getText().equals("")) {
                warningPoshuk.setText("*Ви не заповнили данні");
            } else {
                try {
                    // Виконання SQL-запиту для пошуку за заданим логіном
                    String sql = "SELECT * FROM users WHERE login = ?";
                    st = con.prepareStatement(sql);
                    st.setString(1, users.getLogin());
                    rs = st.executeQuery();

                    ObservableList<UsersAll> searchResults = FXCollections.observableArrayList();
                    while (rs.next()) {
                        // Створення об'єкту UsersAll на основі результатів пошуку
                        UsersAll user = new UsersAll();
                        user.setId(rs.getInt("id"));
                        user.setLogin(rs.getString("login"));
                        user.setPassword(rs.getString("password"));
                        user.setFirstName(rs.getString("firstName"));
                        user.setLastName(rs.getString("lastName"));
                        user.setFatherName(rs.getString("fatherName"));
                        user.setNumberphone(rs.getString("numberphone"));
                        user.setStatus(rs.getString("status"));

                        searchResults.add(user);
                    }

                    // Відображення результатів пошуку, наприклад, у TableView
                    table.setItems(searchResults);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    //--------------------------------------------------------------------------
    //tab видаляння
    @FXML
    private Label warningDelete;


    @FXML
    private TextField tDelete;




    @FXML
    void btnDeleteA(ActionEvent event) {
        warningDelete.setText("");
        if(tDelete.getText().equals("")){
            warningDelete.setText("*Ви не заповнили всі данні");
        }
        else {
            users.setId(Integer.parseInt(tRedag0.getText()));
            con = DBConnextion.getCon();
            try {
                String sql = "DELETE FROM users WHERE id = ?";
                st = con.prepareStatement(sql);
                st.setInt(1, users.getId());

                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


//--------------------------------------------------------------------------
    // tab редагування


    @FXML
    private TextField tRedag0;//id

    @FXML
    private TextField tRedag1;//FirstName

    @FXML
    private TextField tRedag2;//LastName

    @FXML
    private TextField tRedag3;//FatherName

    @FXML
    private Label warningRedag;


    @FXML
    void btnRedug0(ActionEvent event) {
        warningRedag.setText("");
        if(tRedag0.getText().equals("")||tRedag1.getText().equals("")||tRedag2.getText().equals("")||tRedag3.getText().equals("")){
            warningRedag.setText("*Ви не заповнили всі данні");
        }
        else
        {
            users.setId(Integer.parseInt(tRedag0.getText()));
            users.setFirstName(tRedag1.getText());
            users.setLastName(tRedag2.getText());
            users.setFatherName(tRedag3.getText());

            con = DBConnextion.getCon();
            try {
                String sql = "UPDATE users SET firstName = ?, lastName = ?, fatherName = ? WHERE id = ?";
                st = con.prepareStatement(sql);
                st.setString(1, users.getFirstName());
                st.setString(2, users.getLastName());
                st.setString(3, users.getFatherName());
                st.setInt(4, users.getId());

                st.executeUpdate();
                showUsers();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
//-----------------------------------------------------------------------------



    private void setupCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colFatherName.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
        colnumberphone.setCellValueFactory(new PropertyValueFactory<>("numberphone"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void showUsers() {
        ObservableList<UsersAll> users = getUsers();
        table.setItems(users);
    }

    private ObservableList<UsersAll> getUsers() {

        ObservableList<UsersAll> users = FXCollections.observableArrayList();

        con =DBConnextion.getCon();
        try {
            // Виконання SQL-запиту для отримання даних користувачів
            String sql = "SELECT * FROM users";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                // Створення об'єкту UsersAll на основі результатів запиту
                UsersAll user = new UsersAll();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setFatherName(rs.getString("fatherName"));
                user.setNumberphone(rs.getString("numberphone"));
                user.setStatus(rs.getString("status"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showUsers();
        setupCellValueFactory();
    }
}


