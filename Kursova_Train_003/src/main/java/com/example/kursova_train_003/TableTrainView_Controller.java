package com.example.kursova_train_003;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.FormatterClosedException;
import java.util.ResourceBundle;

public class TableTrainView_Controller implements Initializable {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    Train train = new Train();
    Methods methods = new Methods();


    @FXML
    void btnUpdate_tableA(ActionEvent event) {
        showTrains();
    }

    //----------------------------------------------------------------------------
    // fxml view table id:fx
    @FXML
    private TableColumn<Train, Date> colArrivalDate;

    @FXML
    private TableColumn<Train, String> colArrivalStationId;

    @FXML
    private TableColumn<Train, Time> colArrivalTime;

    @FXML
    private TableColumn<Train, Integer> colBookedSeats;

    @FXML
    private TableColumn<Train, Date> colDepartureDate;

    @FXML
    private TableColumn<Train, String> colDepartureStationId;

    @FXML
    private TableColumn<Train, Time> colDepartureTime;

    @FXML
    private TableColumn<Train, Float> colDistance;

    @FXML
    private TableColumn<Train, Integer> colId;

    @FXML
    private TableColumn<Train, Integer> colTotalSeats;

    @FXML
    private TableColumn<Train, Integer> colTrainNumber;

    @FXML
    private TableColumn<Train, String> colTrainStatus;

    @FXML
    private TableView<Train> table;
    //-------------------------------------------------------------------------------------
    //fx:id Tab(Видалення)

    @FXML
    private TextField tDelete0;


    @FXML
    void btnDelete0A(ActionEvent event) {

        int id = Integer.parseInt(tDelete0.getText());
        String delete = "delete from train where id =?";
        con = DBConnextion.getCon();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
            showTrains();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------------------
    //fx:id Tab(Пошук)
    @FXML
    private Button btnPoshuk0;

    @FXML
    private TextField tPoshuk0;

    @FXML
    private Button btnPoshuk1;

    @FXML
    private TextField tPoshuk1;

    @FXML
    private Button btnPoshuk2;

    @FXML
    private ComboBox<String> tPoshuk2;

    @FXML
    private Button btnPoshuk3;

    @FXML
    private DatePicker tPoshuk3;

    @FXML
    private Button btnPoshuk4;

    @FXML
    private DatePicker tPoshuk4;

    @FXML
    void getData(MouseEvent event) {
        try {
            Train train = table.getSelectionModel().getSelectedItem();
            tDelete0.setText(String.valueOf(train.getId()));

        } catch (FormatterClosedException ignored) {
        }
    }


    @FXML
    void btnPoshuk4A(ActionEvent event) {

        java.sql.Date selectedDate = Date.valueOf(tPoshuk4.getValue());


        try {
            String sql = "SELECT t.*, s1.name_staishon AS departure_station_name, s2.name_staishon AS arrival_station_name " +
                    "FROM train_database_001.train t " +
                    "INNER JOIN train_database_001.staishon s1 ON t.departure_station_id = s1.id " +
                    "INNER JOIN train_database_001.staishon s2 ON t.arrival_station_id = s2.id " +
                    "WHERE t.arrival_date = ? ";
            st = con.prepareStatement(sql);
            st.setDate(1, selectedDate);
            rs = st.executeQuery();

            ObservableList<Train> searchResults = FXCollections.observableArrayList();
            while (rs.next()) {
                Train train = new Train();
                train.setId(rs.getInt("id"));
                train.setTrainNumber(rs.getInt("train_number"));
                train.setDepartureStationId(rs.getInt("departure_station_id"));
                train.setArrivalStationId(rs.getInt("arrival_station_id"));
                train.setDistance(rs.getFloat("distance"));
                train.setTotalSeats(rs.getInt("total_seats"));
                train.setBookedSeats(rs.getInt("booked_seats"));
                train.setDepartureDate(rs.getDate("departure_date"));
                train.setDepartureTime(rs.getTime("departure_time"));
                train.setArrivalDate(rs.getDate("arrival_date"));
                train.setArrivalTime(rs.getTime("arrival_time"));
                train.setTrainStatus(rs.getString("train_status"));
                train.setDepartureStationName(rs.getString("departure_station_name"));
                train.setArrivalStationName(rs.getString("arrival_station_name"));

                searchResults.add(train);
            }

            table.setItems(searchResults);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void btnPoshuk3A(ActionEvent event) {
        java.sql.Date selectedDate = Date.valueOf(tPoshuk3.getValue());


        try {
            String sql = "SELECT t.*, s1.name_staishon AS departure_station_name, s2.name_staishon AS arrival_station_name " +
                    "FROM train_database_001.train t " +
                    "INNER JOIN train_database_001.staishon s1 ON t.departure_station_id = s1.id " +
                    "INNER JOIN train_database_001.staishon s2 ON t.arrival_station_id = s2.id " +
                    "WHERE t.departure_date = ? ";
            st = con.prepareStatement(sql);
            st.setDate(1, selectedDate);
            rs = st.executeQuery();

            ObservableList<Train> searchResults = FXCollections.observableArrayList();
            while (rs.next()) {
                Train train = new Train();
                train.setId(rs.getInt("id"));
                train.setTrainNumber(rs.getInt("train_number"));
                train.setDepartureStationId(rs.getInt("departure_station_id"));
                train.setArrivalStationId(rs.getInt("arrival_station_id"));
                train.setDistance(rs.getFloat("distance"));
                train.setTotalSeats(rs.getInt("total_seats"));
                train.setBookedSeats(rs.getInt("booked_seats"));
                train.setDepartureDate(rs.getDate("departure_date"));
                train.setDepartureTime(rs.getTime("departure_time"));
                train.setArrivalDate(rs.getDate("arrival_date"));
                train.setArrivalTime(rs.getTime("arrival_time"));
                train.setTrainStatus(rs.getString("train_status"));
                train.setDepartureStationName(rs.getString("departure_station_name"));
                train.setArrivalStationName(rs.getString("arrival_station_name"));

                searchResults.add(train);
            }

            table.setItems(searchResults);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnPoshuk2A(ActionEvent event) {
        int searchStationId = methods.getIdByName(tPoshuk2.getValue());

        try {
            String sql = "SELECT t.*, s1.name_staishon AS departure_station_name, s2.name_staishon AS arrival_station_name " +
                    "FROM train_database_001.train t " +
                    "INNER JOIN train_database_001.staishon s1 ON t.departure_station_id = s1.id " +
                    "INNER JOIN train_database_001.staishon s2 ON t.arrival_station_id = s2.id " +
                    "WHERE t.departure_station_id = ? OR t.arrival_station_id = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, searchStationId);
            st.setInt(2, searchStationId);
            rs = st.executeQuery();

            ObservableList<Train> searchResults = FXCollections.observableArrayList();
            while (rs.next()) {
                Train train = new Train();
                train.setId(rs.getInt("id"));
                train.setTrainNumber(rs.getInt("train_number"));
                train.setDepartureStationId(rs.getInt("departure_station_id"));
                train.setArrivalStationId(rs.getInt("arrival_station_id"));
                train.setDistance(rs.getFloat("distance"));
                train.setTotalSeats(rs.getInt("total_seats"));
                train.setBookedSeats(rs.getInt("booked_seats"));
                train.setDepartureDate(rs.getDate("departure_date"));
                train.setDepartureTime(rs.getTime("departure_time"));
                train.setArrivalDate(rs.getDate("arrival_date"));
                train.setArrivalTime(rs.getTime("arrival_time"));
                train.setTrainStatus(rs.getString("train_status"));
                train.setDepartureStationName(rs.getString("departure_station_name"));
                train.setArrivalStationName(rs.getString("arrival_station_name"));

                searchResults.add(train);
            }

            table.setItems(searchResults);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnPoshuk1A(ActionEvent event) {
        String searchTrainNumber = tPoshuk1.getText();

        try {
            String sql = "SELECT t.*, s1.name_staishon AS departure_station_name, s2.name_staishon AS arrival_station_name " +
                    "FROM train_database_001.train t " +
                    "INNER JOIN train_database_001.staishon s1 ON t.departure_station_id = s1.id " +
                    "INNER JOIN train_database_001.staishon s2 ON t.arrival_station_id = s2.id " +
                    "WHERE t.train_number = ?";
            st = con.prepareStatement(sql);
            st.setString(1, searchTrainNumber);
            rs = st.executeQuery();

            ObservableList<Train> rss = FXCollections.observableArrayList();
            while (rs.next()) {
                Train train = new Train();
                train.setId(rs.getInt("id"));
                train.setTrainNumber(rs.getInt("train_number"));
                train.setDepartureStationId(rs.getInt("departure_station_id"));
                train.setArrivalStationId(rs.getInt("arrival_station_id"));
                train.setDistance(rs.getFloat("distance"));
                train.setTotalSeats(rs.getInt("total_seats"));
                train.setBookedSeats(rs.getInt("booked_seats"));
                train.setDepartureDate(rs.getDate("departure_date"));
                train.setDepartureTime(rs.getTime("departure_time"));
                train.setArrivalDate(rs.getDate("arrival_date"));
                train.setArrivalTime(rs.getTime("arrival_time"));
                train.setTrainStatus(rs.getString("train_status"));
                train.setDepartureStationName(rs.getString("departure_station_name"));
                train.setArrivalStationName(rs.getString("arrival_station_name"));

                rss.add(train);
            }

            table.setItems(rss);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnPoshuk0A(ActionEvent event) {
        String searchId = tPoshuk0.getText();

        try {
            // Виконання SQL-запиту для пошуку за заданим ідентифікатором
            String sql = "SELECT t.*, s1.name_staishon AS departure_station_name, s2.name_staishon AS arrival_station_name " +
                    "FROM train_database_001.train t " +
                    "INNER JOIN train_database_001.staishon s1 ON t.departure_station_id = s1.id " +
                    "INNER JOIN train_database_001.staishon s2 ON t.arrival_station_id = s2.id " +
                    "WHERE t.id = ?";
            st = con.prepareStatement(sql);
            st.setString(1, searchId);
            rs = st.executeQuery();

            ObservableList<Train> rss = FXCollections.observableArrayList();
            while (rs.next()) {
                // Створення об'єктів Train на основі результатів пошуку
                Train train = new Train();
                train.setId(rs.getInt("id"));
                train.setTrainNumber(rs.getInt("train_number"));
                train.setDepartureStationId(rs.getInt("departure_station_id"));
                train.setArrivalStationId(rs.getInt("arrival_station_id"));
                train.setDistance(rs.getFloat("distance"));
                train.setTotalSeats(rs.getInt("total_seats"));
                train.setBookedSeats(rs.getInt("booked_seats"));
                train.setDepartureDate(rs.getDate("departure_date"));
                train.setDepartureTime(rs.getTime("departure_time"));
                train.setArrivalDate(rs.getDate("arrival_date"));
                train.setArrivalTime(rs.getTime("arrival_time"));
                train.setTrainStatus(rs.getString("train_status"));
                train.setDepartureStationName(rs.getString("departure_station_name"));
                train.setArrivalStationName(rs.getString("arrival_station_name"));

                rss.add(train);
            }

            // Відображення результатів на TableView
            table.setItems(rss);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //------------------------------------------------------------------------------------
    // fx:id Tab(Додати потяг)


    @FXML
    private Label warning_marshuts0;

    @FXML
    private Label warning_datetime0;

    @FXML
    private Label warning_number_train0;

    @FXML
    private Button btnCheckDataTime;

    @FXML
    private Button btnPTCheckMarsurts;

    @FXML
    private Button btnPlusPotiag;

    @FXML
    private Button btnResetPotiag;

    @FXML
    private ChoiceBox<String> chbPTMarshutsA;

    @FXML
    private ChoiceBox<String> chbPTMarshutsB;

    @FXML
    private DatePicker dataB;

    @FXML
    private DatePicker dataA;

    @FXML
    private ComboBox<Integer> hoursA;

    @FXML
    private ComboBox<Integer> hoursB;

    @FXML
    private ComboBox<Integer> minutesA;

    @FXML
    private ComboBox<Integer> minutesB;

    @FXML
    private ComboBox<Integer> seats_total_choose;


    @FXML
    private ComboBox<Integer> number_train_choose;

    //кнопка що перевіряє на коректність даних Data Time
    @FXML
    void btnCheckDataTimeA(ActionEvent event) {
        warning_datetime0.setText("");
        if (dataA.getValue() != null && dataB.getValue() != null
                && hoursA.getValue() != null && hoursB.getValue() != null
                && minutesA.getValue() != null && minutesB.getValue() != null) {
            train.setDepartureDate(Date.valueOf(dataA.getValue()));
            train.setArrivalDate(Date.valueOf(dataB.getValue()));

            int hours1A = hoursA.getValue();
            int minutes1A = minutesA.getValue();
            Time departureTimeA = Time.valueOf(LocalTime.of(hours1A, minutes1A)); // Створення об'єкту Time для departureTimeA

            int hours1B = hoursB.getValue();
            int minutes1B = minutesB.getValue();
            Time arrivalTimeB = Time.valueOf(LocalTime.of(hours1B, minutes1B)); // Створення об'єкту Time для arrivalTimeB
            train.setDepartureTime(departureTimeA); // Встановлення часу в об'єкт train для departureTimeA
            train.setArrivalTime(arrivalTimeB); // Встановлення часу в об'єкт train для departureTimeB
            //System.out.println(train.getArrivalTime());

            if (train.getDepartureDate().before(train.getArrivalDate()) || (train.getDepartureDate().equals(train.getArrivalDate()) && train.getDepartureTime().before(train.getArrivalTime()))) {
                // Дата і час коректні, dateTimeA передує dateTimeB
                //System.out.println("Дата і час коректні");

                btnCheckDataTime.setDisable(true);

                dataA.setDisable(true);
                hoursA.setDisable(true);
                minutesA.setDisable(true);

                dataB.setDisable(true);
                hoursB.setDisable(true);
                minutesB.setDisable(true);


                seats_total_choose.setDisable(false);
                number_train_choose.setDisable(false);
                btnPlusPotiag.setDisable(false);


            } else {
                // Дата і час некоректні, dateTimeA не передує dateTimeB
                warning_datetime0.setText("Дата і час некоректні");
                //System.out.println("Дата і час некоректні");
            }


            //Time departureTimeA = new Time(hoursA.getValue().intValue(),minutesA.getValue().intValue()); // Створення об'єкту Time для departureTimeA
            //train.setDepartureTime(hoursA.getValue(),minutesA.getValue());

        } else {
            warning_datetime0.setText("*Ви не вказали дату або час.");
        }
    }


    //кнопка що перевіряє на коректність даних про маршути
    @FXML
    void btnPTCheckMarsurtsA(ActionEvent event) {
        warning_marshuts0.setText("");
        if (chbPTMarshutsA.getValue() != null && chbPTMarshutsB.getValue() != null) {
            if (chbPTMarshutsA.getValue().equals(chbPTMarshutsB.getValue())) {
                warning_marshuts0.setText("*Ви  вказали дві однакові станції.");
            } else {
                train.setDepartureStationId(methods.getIdByName(chbPTMarshutsA.getValue()));
                train.setArrivalStationId(methods.getIdByName(chbPTMarshutsB.getValue()));
                train.setDistance(methods.getDistanceIfRouteExists(train.getDepartureStationId(), train.getArrivalStationId()));
                //System.out.println(train.getDistance());
                if (train.getDistance() > 0.0) {
                    System.out.println("Маршрут існує. Відстань: " + train.getDistance());

                    btnPTCheckMarsurts.setDisable(true);
                    chbPTMarshutsA.setDisable(true);
                    chbPTMarshutsB.setDisable(true);


                    btnCheckDataTime.setDisable(false);
                    dataA.setDisable(false);
                    hoursA.setDisable(false);
                    minutesA.setDisable(false);
                    dataB.setDisable(false);
                    hoursB.setDisable(false);
                    minutesB.setDisable(false);


                } else {
                    warning_marshuts0.setText("*Маршрут не існує.");
                }
            }
        } else {
            warning_marshuts0.setText("*Ви не вказали якусь станцію.");
        }
    }


    // кнопка перевірки та додавання потяга
    @FXML
    void btnPlusPotiagA(ActionEvent event) {
        warning_number_train0.setText("");
        if (number_train_choose.getValue() != null && seats_total_choose.getValue() != null) {
            boolean marshuts;
            train.setTrainNumber(number_train_choose.getValue());
            train.setTotalSeats(seats_total_choose.getValue());
            marshuts = ((methods.checkTrainAvailability(train.getDepartureDate(), train.getDepartureTime(), train.getArrivalDate(), train.getArrivalTime(), train.getDepartureStationId(), train.getArrivalStationId())));
            if (marshuts == true) {
                //додається потяг
                addTrainData();
                showTrains();
                btnResetPotiagA(event);
            } else if (marshuts == false) {
                warning_number_train0.setText("*Даний потяг не може записатись.");
            } else {
                System.out.println("Bad");
            }

        } else {
            warning_number_train0.setText("*Ви не вказали дані");
        }
    }


    //Reset кнопка скидання налаштуваннь
    @FXML
    void btnResetPotiagA(ActionEvent event) {
        //скидання заданих даних про маршут
        btnPTCheckMarsurts.setDisable(false);
        chbPTMarshutsA.setValue(null);
        chbPTMarshutsA.setDisable(false);
        chbPTMarshutsB.setValue(null);
        chbPTMarshutsB.setDisable(false);
        warning_marshuts0.setText("");
        //скидання заданих даних про дату та час
        warning_datetime0.setText("");

        btnCheckDataTime.setDisable(true);
        dataA.setDisable(true);
        hoursA.setDisable(true);
        minutesA.setDisable(true);
        dataB.setDisable(true);
        hoursB.setDisable(true);
        minutesB.setDisable(true);

        dataA.setValue(null);
        hoursA.setValue(null);
        minutesA.setValue(null);
        dataB.setValue(null);
        hoursB.setValue(null);
        minutesB.setValue(null);

        //скидання даних про номер та місця потяга
        warning_number_train0.setText("");

        seats_total_choose.setDisable(true);
        number_train_choose.setDisable(true);
        btnPlusPotiag.setDisable(true);

        number_train_choose.setValue(null);
        seats_total_choose.setValue(null);


    }

    //-------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------
    //вставляє дані ObservableList а потім їх передає на таблицю завдяки методу showTrains()
    public ObservableList<Train> getTrains() {
        ObservableList<Train> trains = FXCollections.observableArrayList();
        String query = "SELECT t.*, s1.name_staishon AS departure_station_name, s2.name_staishon AS arrival_station_name " +
                "FROM train_database_001.train t " +
                "INNER JOIN train_database_001.staishon s1 ON t.departure_station_id = s1.id " +
                "INNER JOIN train_database_001.staishon s2 ON t.arrival_station_id = s2.id";


        con = DBConnextion.getCon();
        try {
            st = con.prepareStatement(query);


            rs = st.executeQuery();
            while (rs.next()) {
                Train train = new Train();
                train.setId(rs.getInt("id"));
                train.setTrainNumber(rs.getInt("train_number"));
                train.setDepartureStationId(rs.getInt("departure_station_id"));
                train.setArrivalStationId(rs.getInt("arrival_station_id"));
                train.setDistance(rs.getFloat("distance"));
                train.setTotalSeats(rs.getInt("total_seats"));
                train.setBookedSeats(rs.getInt("booked_seats"));
                train.setDepartureDate(rs.getDate("departure_date"));
                train.setDepartureTime(rs.getTime("departure_time"));
                train.setArrivalDate(rs.getDate("arrival_date"));
                train.setArrivalTime(rs.getTime("arrival_time"));
                train.setTrainStatus(rs.getString("train_status"));
                train.setDepartureStationName(rs.getString("departure_station_name"));
                train.setArrivalStationName(rs.getString("arrival_station_name"));
                trains.add(train);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trains;
    }


    private void setupCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTrainNumber.setCellValueFactory(new PropertyValueFactory<>("trainNumber"));
        colDepartureStationId.setCellValueFactory(new PropertyValueFactory<>("departureStationName"));
        colArrivalStationId.setCellValueFactory(new PropertyValueFactory<>("arrivalStationName"));
        colDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));
        colTotalSeats.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));
        colBookedSeats.setCellValueFactory(new PropertyValueFactory<>("bookedSeats"));
        colDepartureDate.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        colDepartureTime.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        colArrivalDate.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        colArrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        colTrainStatus.setCellValueFactory(new PropertyValueFactory<>("trainStatus"));
    }


    //метод котрий заповнює таблицю даними
    public void showTrains() {
        ObservableList<Train> list = getTrains();
        table.setItems(list);
        setupCellValueFactory();
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
                tPoshuk2.getItems().add(stationName);
                chbPTMarshutsA.getItems().add(stationName);
                chbPTMarshutsB.getItems().add(stationName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------
//метод який додає потяги та місця
    public void addTrainData() {

        try {
            con = DBConnextion.getCon();

            // Додавання інформації до таблиці "train"
            String trainInsertQuery = "INSERT INTO train_database_001.train (train_number, departure_station_id, arrival_station_id, distance, total_seats, booked_seats, departure_date, departure_time, arrival_date, arrival_time, train_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            st = con.prepareStatement(trainInsertQuery);

            // Заповнення параметрів для додавання інформації
            st.setInt(1, train.getTrainNumber());
            st.setInt(2, train.getDepartureStationId());
            st.setInt(3, train.getArrivalStationId());
            st.setFloat(4, train.getDistance());
            st.setInt(5, train.getTotalSeats());
            st.setInt(6, train.getBookedSeats());
            st.setDate(7, train.getDepartureDate());
            st.setTime(8, train.getDepartureTime());
            st.setDate(9, train.getArrivalDate());
            st.setTime(10, train.getArrivalTime());
            st.setString(11, "done");

            // Виконання запиту на додавання інформації до таблиці "train"
            st.executeUpdate();


            //додавання id
            train.setId(methods.getTrainIdByParameters(train.getTrainNumber(), train.getDepartureStationId(), train.getArrivalStationId(), train.getDepartureDate(), train.getDepartureTime()));


            // Додавання інформації до таблиці "seats_buy" за допомогою циклу
            String seatsInsertQuery = "INSERT INTO train_database_001.seats_buy (train_id, seat_number, booked) VALUES (?, ?, ?)";
            st = con.prepareStatement(seatsInsertQuery);

            int trainId = train.getId(); // ID потяга, для якого додаємо інформацію про місця
            int totalSeats = train.getTotalSeats(); // Загальна кількість місць

            // Цикл для додавання інформації про кожне місце
            for (int seatNumber = 1; seatNumber <= totalSeats; seatNumber++) {
                st.setInt(1, trainId);
                st.setInt(2, seatNumber);
                st.setBoolean(3, false); // За замовчуванням місце не заброньоване

                // Виконання запиту на додавання інформації до таблиці "seats_buy"
                st.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Закриття ресурсів
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


//-----------------------------------------------------------------------------------


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillComboBoxWithStations();
        showTrains();
        //створюємо варіанти для combobox (година)
        for (int i = 0; i < 24; i++) {
            hoursA.getItems().add(i);
            hoursB.getItems().add(i);
        }

        //створюємо варіанти для combobox (хвилина)
        for (int i = 0; i < 60; i++) {
            minutesA.getItems().add(i);
            minutesB.getItems().add(i);
        }
        //створюємо варіанти для combobox (загальна к-сть місць)
        for (int i = 1; i <= 520; i++) {
            seats_total_choose.getItems().add(i);
        }

        //створюємо варіанти для combobox (номер_поїзда)
        for (int i = 1; i <= 99999; i++) {
            number_train_choose.getItems().add(i);
        }
    }
}