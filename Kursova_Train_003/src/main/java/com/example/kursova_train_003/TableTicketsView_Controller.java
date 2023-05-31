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
import java.sql.*;
import java.util.ResourceBundle;

public class TableTicketsView_Controller implements Initializable {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    Tickets tickets = new Tickets();

    @FXML
    private TableColumn<Tickets, Integer> colid;

    @FXML
    private TableColumn<Tickets, Integer> coltrain_id;

    @FXML
    private TableColumn<Tickets, Integer> colnumber_train;

    @FXML
    private TableColumn<Tickets, Integer> colseat_id;

    @FXML
    private TableColumn<Tickets, Integer> colseat_number;

    @FXML
    private TableColumn<Tickets, String> coldepartureStationName;

    @FXML
    private TableColumn<Tickets, String> colarrivalStationName;

    @FXML
    private TableColumn<Tickets, Date> coldeparture_date;

    @FXML
    private TableColumn<Tickets, Time> coldeparture_time;

    @FXML
    private TableColumn<Tickets, Date> colarrival_date;

    @FXML
    private TableColumn<Tickets, Time> colarrival_time;

    @FXML
    private TableColumn<Tickets, String> collogin;

    @FXML
    private TableColumn<Tickets, String> colfirstname;

    @FXML
    private TableColumn<Tickets, String> collastname;

    @FXML
    private TableColumn<Tickets, String> colprice;

    @FXML
    private TableView<Tickets> table;
//----------------------------------------------------------------------------------------
    //tab Видалити  по id



    @FXML
    private Label warningdelete;

    @FXML
    private TextField tDelete;

    @FXML
    void btnDeleteA(ActionEvent event) {
        warningdelete.setText("");
        if (tDelete.getText().equals("")) {
            warningdelete.setText("*ви не ввели дані");
        } else {
                tickets.setId(Integer.parseInt(tDelete.getText()));
            String query = "DELETE FROM Tickets WHERE id = ?";

            con = DBConnextion.getCon();
            try {
                st = con.prepareStatement(query);
                st.setInt(1, tickets.getId());
                st.executeUpdate();
                warningdelete.setText("Запис успішно видалено.");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnections();
            }
        }


    }









    //-------------------------------------------------------------------------------------------


    private void setupCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltrain_id.setCellValueFactory(new PropertyValueFactory<>("trainId"));
        colnumber_train.setCellValueFactory(new PropertyValueFactory<>("numberTrain"));
        colseat_id.setCellValueFactory(new PropertyValueFactory<>("seatId"));
        colseat_number.setCellValueFactory(new PropertyValueFactory<>("seatNumber"));
        coldepartureStationName.setCellValueFactory(new PropertyValueFactory<>("departureStationName"));
        colarrivalStationName.setCellValueFactory(new PropertyValueFactory<>("arrivalStationName"));
        coldeparture_date.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        coldeparture_time.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        colarrival_date.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        colarrival_time.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        collogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colfirstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        collastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colprice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void showTickets() {
        ObservableList<Tickets> tickets = getTickets();
        table.setItems(tickets);
    }

    private ObservableList<Tickets> getTickets() {
        ObservableList<Tickets> tickets = FXCollections.observableArrayList();
        String query = "SELECT t.*, s1.name_staishon AS departure_station_name, s2.name_staishon AS arrival_station_name " +
                "FROM Tickets t " +
                "INNER JOIN Staishon s1 ON t.departure_station_id = s1.id " +
                "INNER JOIN Staishon s2 ON t.arrival_station_id = s2.id";

        try {
            con = DBConnextion.getCon();
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                Tickets ticket = new Tickets();
                ticket.setId(rs.getInt("id"));
                ticket.setTrainId(rs.getInt("train_id"));
                ticket.setNumberTrain(rs.getInt("number_train"));
                ticket.setSeatId(rs.getInt("seat_id"));
                ticket.setSeatNumber(rs.getInt("seat_number"));
                ticket.setDepartureStationName(rs.getString("departure_station_name"));
                ticket.setArrivalStationName(rs.getString("arrival_station_name"));
                ticket.setDepartureDate(rs.getDate("departure_date"));
                ticket.setDepartureTime(rs.getTime("departure_time"));
                ticket.setArrivalDate(rs.getDate("arrival_date"));
                ticket.setArrivalTime(rs.getTime("arrival_time"));
                ticket.setLogin(rs.getString("login"));
                ticket.setFirstName(rs.getString("firstname"));
                ticket.setLastName(rs.getString("lastname"));
                ticket.setPrice(rs.getString("price"));
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return tickets;
    }

    private void closeConnections() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupCellValueFactory();
        showTickets();
    }
}
