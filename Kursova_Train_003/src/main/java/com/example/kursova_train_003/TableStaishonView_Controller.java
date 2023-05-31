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

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TableStaishonView_Controller implements Initializable {

    private Connection con;
    private PreparedStatement st;
    private ResultSet rs;
    Staishon staishon = new Staishon();




    @FXML
    void btnUpdate0A(ActionEvent event) {
        showStaishons();
    }
//-------------------------------------------------
    //table
    @FXML
    private TableColumn<Staishon, Integer> colid;

    @FXML
    private TableColumn<Staishon, String> colname_staishon;

    @FXML
    private TableView<Staishon> table;


//-----------------------------------------------------
    //Tab (пошук)
    @FXML
    private Label warningPoshuk0;

    @FXML
    private TextField tPosuk0;


    @FXML
    void btnPoshuk0A(ActionEvent event) {
        warningPoshuk0.setText("");
        if (tPosuk0.getText().equals("")) {
            warningPoshuk0.setText("*ви не ввели дані");}
        else{
            String searchId = tPosuk0.getText();

            try {
                // Виконання SQL-запиту для пошуку за заданим ідентифікатором
                String sql = "SELECT * FROM train_database_001.staishon WHERE id = ?";
                st = con.prepareStatement(sql);
                st.setString(1, searchId);
                rs = st.executeQuery();

                ObservableList<Staishon> searchResults = FXCollections.observableArrayList();
                while (rs.next()) {
                    // Створення об'єктів Staishon на основі результатів пошуку
                    Staishon staishon = new Staishon();
                    staishon.setId(rs.getInt("id"));
                    staishon.setName_Staishon(rs.getString("name_staishon"));

                    searchResults.add(staishon);
                }

                // Відображення результатів на TableView
                table.setItems(searchResults);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


//-----------------------------------------------------
    //Tab (видаляння)


    @FXML
    private Label warningDelete0;

    @FXML
    private TextField tDelete0;






    @FXML
    void btnDelete0A(ActionEvent event) {
        warningDelete0.setText("");
        if (tDelete0.getText().equals("")) {
            warningDelete0.setText("*ви не ввели дані");
        } else {
            staishon.setId(Integer.parseInt(tDelete0.getText()));
            try {
                String sql = "DELETE FROM staishon WHERE id = ?";
                st = con.prepareStatement(sql);
                st.setInt(1, staishon.getId());

                int rowsAffected = st.executeUpdate();

                // Очищення поля вводу після успішного видалення
                tDelete0.clear();

                // Оновлення відображення станцій у TableView
                showStaishons();
            } catch (SQLException e) {
                if (e.getErrorCode() == 1451) {
                    System.out.println("Помилка видалення. Існують залежності у інших таблицях.");
                } else {
                    e.printStackTrace();
                }
            }
        }
    }


//-----------------------------------------------------
    //Tab (додавання)
    @FXML
    private TextField tPlus0;

    @FXML
    private Label warningPlus0;





    @FXML
    void btnPlus0A(ActionEvent event) {
        warningPlus0.setText("");
        if(tPlus0.getText().equals("")){
            warningPlus0.setText("*ви не ввели дані");
        }
        else {

            staishon.setName_Staishon(tPlus0.getText());

            try {
            // Підготовка SQL-запиту для вставки нової станції
            String sql = "INSERT INTO staishon (name_staishon) VALUES (?)";
            st = con.prepareStatement(sql);
            st.setString(1, staishon.getName_Staishon());
            st.executeUpdate();

            // Очищення поля вводу після успішного додавання
            tPlus0.clear();

            // Оновлення відображення станцій у TableView
            showStaishons();
            } catch (SQLException e) {
            e.printStackTrace();
            }
        }
    }
//-----------------------------------------------------
public ObservableList<Staishon> getStaishons() {
    ObservableList<Staishon> staishons = FXCollections.observableArrayList();
    String query = "SELECT * FROM staishon";

    con = DBConnextion.getCon();
    try {
        st = con.prepareStatement(query);
        rs = st.executeQuery();
        while (rs.next()) {
            Staishon staishon = new Staishon();
            staishon.setId(rs.getInt("id"));
            staishon.setName_Staishon(rs.getString("name_staishon"));
            staishons.add(staishon);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return staishons;
}




    private void setupCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname_staishon.setCellValueFactory(new PropertyValueFactory<>("name_Staishon"));
    }

    public void showStaishons() {
        ObservableList<Staishon> list = getStaishons();
        table.setItems(list);
        setupCellValueFactory();
    }

//-------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showStaishons();
    }



}
