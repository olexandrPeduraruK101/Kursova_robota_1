package com.example.kursova_train_003;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TableSeatView_Controller implements Initializable {

    private Connection con;
    private PreparedStatement st;
    private ResultSet rs;
    SeatsBuy seatsBuy = new SeatsBuy();
    Methods methods = new Methods();



    @FXML
    void btnupdatae_tableA(ActionEvent event) {
        showSeatsBuy();
    }
    @FXML
    private Button updatae_table;
//-------------------------------------------------------------------------------------
   //table
    @FXML
    private TableColumn<SeatsBuy, Integer> colid;

    @FXML
    private TableColumn<SeatsBuy, Integer> coltrain_id;

    @FXML
    private TableColumn<SeatsBuy, Integer> colseat_number;

    @FXML
    private TableColumn<SeatsBuy, Boolean> colbooked;

    @FXML
    private TableView<SeatsBuy> table;
//-------------------------------------------------------------------------------------------------
    //Tab "Додавання"
@FXML
private TextField tUpdate0;

    @FXML
    private ComboBox<Boolean> cmbUpdate0;

    @FXML
    private Label warning_update0;



    @FXML
    void btnUpdate0A(ActionEvent event) {
        warning_update0.setText("");
        if(tUpdate0.getText().equals("")||cmbUpdate0.getValue() == null){
            warning_update0.setText("*Ви не вказали значення");
        }
        else{
            seatsBuy.setId(Integer.parseInt(tUpdate0.getText()));
           seatsBuy.setBooked(cmbUpdate0.getValue());
            methods.updateSeatStatus(seatsBuy.getId(), seatsBuy.isBooked());
            showSeatsBuy();
        }
    }

//--------------------------------------------------------------------------------------------------

//Tab "Видаляння"

    @FXML
    void getData(MouseEvent event) {
        SeatsBuy seatsBuys = table.getSelectionModel().getSelectedItem();
        tDelete0.setText(String.valueOf(seatsBuys.getId()));
        tDelete1.setText(String.valueOf(seatsBuys.getTrainId()));
        tUpdate0.setText(String.valueOf(seatsBuys.getId()));
    }



    @FXML
    private TextField tDelete1;

    @FXML
    private TextField tDelete0;




    @FXML
    void btnDelete0A(ActionEvent event) {
        int id = Integer.parseInt(tDelete0.getText());
        String delete = "DELETE FROM seats_buy WHERE id = ?";
        con = DBConnextion.getCon();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
            showSeatsBuy();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnDelete1A(ActionEvent event) {
        int trainId = Integer.parseInt(tDelete1.getText());
        String delete = "DELETE FROM seats_buy WHERE train_id = ?";
        con = DBConnextion.getCon();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, trainId);
            st.executeUpdate();
            showSeatsBuy();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//--------------------------------------------------------------------------------
    //TAB (пошук)
    @FXML
    private TextField tPoshuk0;

    @FXML
    private TextField tPoshuk1;

    @FXML
    private TextField tPoshuk2;




    @FXML
    void btnPoshuk2A(ActionEvent event) {
        String searchSeatNumber = tPoshuk2.getText();

        try {
            // Виконання SQL-запиту для пошуку за заданим seat_number
            String sql = "SELECT * FROM train_database_001.seats_buy WHERE seat_number = ?";
            st = con.prepareStatement(sql);
            st.setString(1, searchSeatNumber);
            rs = st.executeQuery();

            ObservableList<SeatsBuy> searchResults = FXCollections.observableArrayList();
            while (rs.next()) {
                // Створення об'єктів SeatsBuy на основі результатів пошуку
                SeatsBuy seatsBuy = new SeatsBuy();
                seatsBuy.setId(rs.getInt("id"));
                seatsBuy.setTrainId(rs.getInt("train_id"));
                seatsBuy.setSeatNumber(rs.getInt("seat_number"));
                seatsBuy.setBooked(rs.getBoolean("booked"));

                searchResults.add(seatsBuy);
            }

            // Відображення результатів на TableView
            table.setItems(searchResults);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //пошук по train_id
    @FXML
    void btnPoshuk1A(ActionEvent event) {
        String searchTrainId = tPoshuk1.getText();

        try {
            // Виконання SQL-запиту для пошуку за заданим train_id
            String sql = "SELECT * FROM train_database_001.seats_buy WHERE train_id = ?";
            st = con.prepareStatement(sql);
            st.setString(1, searchTrainId);
            rs = st.executeQuery();

            ObservableList<SeatsBuy> searchResults = FXCollections.observableArrayList();
            while (rs.next()) {
                // Створення об'єктів SeatsBuy на основі результатів пошуку
                SeatsBuy seatsBuy = new SeatsBuy();
                seatsBuy.setId(rs.getInt("id"));
                seatsBuy.setTrainId(rs.getInt("train_id"));
                seatsBuy.setSeatNumber(rs.getInt("seat_number"));
                seatsBuy.setBooked(rs.getBoolean("booked"));

                searchResults.add(seatsBuy);
            }

            // Відображення результатів на TableView
            table.setItems(searchResults);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //пошук по id
    @FXML
    void btnPoshuk0A(ActionEvent event) {
        String searchId = tPoshuk0.getText();

        try {
            // Виконання SQL-запиту для пошуку за заданим ідентифікатором
            String sql = "SELECT * FROM train_database_001.seats_buy WHERE id = ?";
            st = con.prepareStatement(sql);
            st.setString(1, searchId);
            rs = st.executeQuery();

            ObservableList<SeatsBuy> searchResults = FXCollections.observableArrayList();
            while (rs.next()) {
                // Створення об'єктів SeatsBuy на основі результатів пошуку
                SeatsBuy seatsBuy = new SeatsBuy();
                seatsBuy.setId(rs.getInt("id"));
                seatsBuy.setTrainId(rs.getInt("train_id"));
                seatsBuy.setSeatNumber(rs.getInt("seat_number"));
                seatsBuy.setBooked(rs.getBoolean("booked"));

                searchResults.add(seatsBuy);
            }

            // Відображення результатів на TableView
            table.setItems(searchResults);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//--------------------------------------------------------------------------------
        //закидує дані в ObservableList а потім їх показує завдяки методу showSeatsBuy()
    public ObservableList<SeatsBuy> getSeatsBuy() {
    ObservableList<SeatsBuy> seats = FXCollections.observableArrayList();
    String query = "SELECT * FROM train_database_001.seats_buy";

    con = DBConnextion.getCon();
    try {
        st = con.prepareStatement(query);
        rs = st.executeQuery();
        while (rs.next()) {
            SeatsBuy seatsBuy = new SeatsBuy();
            seatsBuy.setId(rs.getInt("id"));
            seatsBuy.setTrainId(rs.getInt("train_id"));
            seatsBuy.setSeatNumber(rs.getInt("seat_number"));
            seatsBuy.setBooked(rs.getBoolean("booked"));
            seats.add(seatsBuy);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return seats;
}
    //показує дані у TableView
    private void setupCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltrain_id.setCellValueFactory(new PropertyValueFactory<>("trainId"));
        colseat_number.setCellValueFactory(new PropertyValueFactory<>("seatNumber"));
        colbooked.setCellValueFactory(new PropertyValueFactory<>("booked"));
    }

    public void showSeatsBuy() {
        ObservableList<SeatsBuy> list = getSeatsBuy();
        table.setItems(list);
        setupCellValueFactory();
    }

    //-------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showSeatsBuy();
        //додавання варіантів в combobox
        ObservableList<Boolean> options = FXCollections.observableArrayList(true, false);
        cmbUpdate0.setItems(options);
        //----------------------------------------------------------
        //обмеження вводу з клавіатури
        tUpdate0.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]{0,10}")) {
                tUpdate0.setText(oldValue);
            }});

        tDelete0.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]{0,10}")) {
                tDelete0.setText(oldValue);
            }});

        tDelete1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]{0,10}")) {
                tDelete1.setText(oldValue);
            }});


    }
}

