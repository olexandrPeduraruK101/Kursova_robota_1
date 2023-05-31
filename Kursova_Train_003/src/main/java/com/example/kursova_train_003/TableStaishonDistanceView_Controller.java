package com.example.kursova_train_003;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

public class TableStaishonDistanceView_Controller implements Initializable {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    Methods methods =new Methods();
    Routes routes = new Routes();





    //оновлення данних
    @FXML
    void btnUpdatetableA(ActionEvent event) {
        showRoutes();
    }

//---------------------------------------------------------------------------------
    //table
    @FXML
    private TableColumn<Routes, Integer> colid;

    @FXML
    private TableColumn<Routes, Float> coldistance;

    @FXML
    private TableColumn<Routes, String> coldeparture_station_name;

    @FXML
    private TableColumn<Routes, String> colarrival_station_name;

    @FXML
    private TableView<Routes> table;
    //---------------------------------------------------------------------------------
    @FXML
    void getData(MouseEvent event) {
        Routes routess = table.getSelectionModel().getSelectedItem();
        tRedag0.setText(String.valueOf(routess.getId()));
        tDelete0.setText(String.valueOf(routess.getId()));
    }

    //-----------------------------------------------------------------------------------
    //Tab (Видаляння)


    @FXML
    private Label warningDelete;


    @FXML
    private TextField tDelete0;





    @FXML
    void btnDeleteA(ActionEvent event) {

        warningDelete.setText("");

        if(tDelete0.getText().equals("")){
            warningDelete.setText("*Ви не вказали дані");
        }
        else {
            try {
                routes.setId(Integer.parseInt(tDelete0.getText()));

                con = DBConnextion.getCon();
                String query = "DELETE FROM Routes WHERE id = ?";
                st = con.prepareStatement(query);
                st.setInt(1, routes.getId());

                int rowsDeleted = st.executeUpdate();
                if (rowsDeleted > 0) {
                    warningDelete.setText("Запис успішно видалено");
                    showRoutes();
                } else {
                    warningDelete.setText("Не вдалося видалити запис");
                }

                st.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




//---------------------------------------------------------------------------------
    //Tab (Редагування)



    @FXML
    private Label warningRedag;

    @FXML
    private TextField tRedag0;

    @FXML
    private TextField tRedag1;







    @FXML
    void btnRedagA(ActionEvent event) {
        warningRedag.setText("");

        if(tRedag0.getText().equals("")||tRedag1.getText().equals("")){
            warningRedag.setText("*Ви не вказали дані");
        }
        else {
            String input = tRedag1.getText();
            if (input.matches("^\\d+(\\.\\d+)?$")) {

                try {
                    routes.setId(Integer.parseInt(tRedag0.getText()));
                    routes.setDistance(Float.parseFloat(tRedag1.getText()));

                    con = DBConnextion.getCon();
                    String query = "UPDATE routes SET distance = ? WHERE id = ?";
                    st = con.prepareStatement(query);
                    st.setFloat(1, routes.getDistance());
                    st.setInt(2, routes.getId());

                    int rowsUpdated = st.executeUpdate();
                    if (rowsUpdated > 0) {
                        warningRedag.setText("Значення distance успішно оновлено");
                        showRoutes();
                    } else {
                        warningRedag.setText("Не вдалося оновити значення distance");
                    }

                    st.close();
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                warningRedag.setText("Ви не коректно ввелт данні в distance");
            }
        }
    }



//---------------------------------------------------------------------------------
    //Tab (Додавання)
    @FXML
    private Button btnPlus0;

    @FXML
    private Button btnPlusMarshuts;

    @FXML
    private TextField tPlus0;

    @FXML
    private Label warningPlus0;

    @FXML
    private Label warningPlus1;

    @FXML
    private ComboBox<String> cmbPlus1;

    @FXML
    private ComboBox<String> сmbPlus0;








    //додавання маршут
    @FXML
    void btnPlusMarshutsA(ActionEvent event) {
        warningPlus1.setText("");
        if(tPlus0.getText().equals("")){
            warningPlus1.setText("*Ви не вказали дані");
        }
        else {
            String input = tPlus0.getText();
            if (input.matches("^\\d+(\\.\\d+)?$")) {
                // Введене значення має коректний формат
                routes.setDistance(Float.parseFloat(input));
                addRoute();
                showRoutes();

                tPlus0.setDisable(true);
                tPlus0.setText("");
                btnPlusMarshuts.setDisable(true);
                btnResetA(event);


            } else {
                // Введене значення має некоректний формат
                warningPlus1.setText("Некоректне значення");
            }
        }

    }


    //скидання налаштувань
    @FXML
    void btnResetA(ActionEvent event) {

        //це частина з перевіркою
        сmbPlus0.setValue("");
        cmbPlus1.setValue("");
        сmbPlus0.setDisable(false);
        cmbPlus1.setDisable(false);
        btnPlus0.setDisable(false);

        //це частина з додавання маршуту

        btnPlusMarshuts.setDisable(true);
        tPlus0.setDisable(true);
        tPlus0.setText("");

    }
//перевірка коректності маршуту
    @FXML
    void btnPlus0A(ActionEvent event) {warningPlus0.setText("");
        if(сmbPlus0.getValue()!=null&&cmbPlus1.getValue()!=null) {
            if (сmbPlus0.getValue().equals(cmbPlus1.getValue())){
                warningPlus0.setText("*Ви  вказали дві однакові станції.");
            }
            else{
                routes.setDeparture_station_id(methods.getIdByName(сmbPlus0.getValue()));
                routes.setArrival_station_id(methods.getIdByName(cmbPlus1.getValue()));
                routes.setDistance(methods.getDistanceIfRouteExists(routes.getDeparture_station_id(),routes.getArrival_station_id()));
                if (routes.getDistance() > 0.0) {
                    warningPlus0.setText("такий маршрут вже існує");
                }
                else{

                   //
                    сmbPlus0.setDisable(true);
                    cmbPlus1.setDisable(true);
                    btnPlus0.setDisable(true);

                    //
                    tPlus0.setDisable(false);
                    btnPlusMarshuts.setDisable(false);

                }
        }}
        else{
            warningPlus0.setText("*Ви не вказали якусь станцію.");
        }

    }
//---------------------------------------------------------------------------------
    private void setupCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coldistance.setCellValueFactory(new PropertyValueFactory<>("distance"));
        coldeparture_station_name.setCellValueFactory(new PropertyValueFactory<>("departureStationName"));
        colarrival_station_name.setCellValueFactory(new PropertyValueFactory<>("arrivalStationName"));
    }

    private void showRoutes() {
        ObservableList<Routes> routes = getRoutes();
        table.setItems(routes);
        setupCellValueFactory();
    }

    private ObservableList<Routes> getRoutes() {
        ObservableList<Routes> routes = FXCollections.observableArrayList();
        String query = "SELECT r.*, s1.name_staishon AS departure_station_name, s2.name_staishon AS arrival_station_name " +
                "FROM train_database_001.routes r " +
                "INNER JOIN train_database_001.staishon s1 ON r.departure_station_id = s1.id " +
                "INNER JOIN train_database_001.staishon s2 ON r.arrival_station_id = s2.id";

        con = DBConnextion.getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                Routes route = new Routes();
                route.setId(rs.getInt("id"));
                route.setDistance(rs.getFloat("distance"));
                route.setDepartureStationName(rs.getString("departure_station_name"));
                route.setArrivalStationName(rs.getString("arrival_station_name"));
                routes.add(route);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }


    //----------------------------------------------------------------------------------
    //сombobox+sql та вибір між Станціями
    public void fillComboBoxWithStations() {
        String query = "SELECT name_staishon FROM train_database_001.staishon";
        con = DBConnextion.getCon();

        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                String stationName = rs.getString("name_staishon");

                cmbPlus1.getItems().add(stationName);
                сmbPlus0.getItems().add(stationName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//-----------------------------------------------------------------------------------

    public void addRoute() {
        try {
            con = DBConnextion.getCon();
            String query = "INSERT INTO Routes (id, departure_station_id, arrival_station_id, distance) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, routes.getId());
            statement.setInt(2, routes.getDeparture_station_id());
            statement.setInt(3, routes.getArrival_station_id());
            statement.setFloat(4, routes.getDistance());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Дані успішно додано до таблиці Routes.");
            }

            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



//-------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showRoutes();
        fillComboBoxWithStations();
    }

}