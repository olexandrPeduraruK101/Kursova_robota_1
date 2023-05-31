package com.example.kursova_train_003;

import java.sql.*;

public class Methods {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    private Users users;

    public void addAll_info(Users users) {
        this.users = users;
        con = DBConnextion.getCon();

        try {

            st = con.prepareStatement("select * from users where Login = '" + users.getLogin() + "' and Password = '"+ users.getPassword()+"'");
            rs = st.executeQuery();
            if(rs.next()){
                users.setFirstName(rs.getString("FirstName"));
                users.setLastName(rs.getString("LastName"));
                users.setFatherName(rs.getString("FatherName"));
                users.setNumberphone(rs.getString("numberphone"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getIdByName(String name) {
        int id = 0;
        String query = "SELECT id FROM train_database_001.staishon WHERE name_staishon = ?";
        con = DBConnextion.getCon();
        try {
            st = con.prepareStatement(query);
            st.setString(1, name);
            rs = st.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }




    public float getDistanceIfRouteExists(int departureStationId, int arrivalStationId) {
        float distance = 0.0F;
        String query = "SELECT distance FROM train_database_001.routes WHERE departure_station_id = ? AND arrival_station_id = ?";
        con = DBConnextion.getCon();
        try {
            st = con.prepareStatement(query);
            st.setInt(1, departureStationId);
            st.setInt(2, arrivalStationId);
            rs = st.executeQuery();
            if (rs.next()) {
                distance = rs.getFloat("distance"); // Отримати значення distance
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return distance;
    }


    public boolean checkTrainAvailability(Date departureDate, Time departureTime, Date arrivalDate, Time arrivalTime, int departureStationId, int arrivalStationId) {
        con = DBConnextion.getCon();
        try {
            // Створіть запит для перевірки наявності конфліктів
            String query = "SELECT * FROM train WHERE ((departure_date = ? AND departure_time <= ? AND arrival_time >= ?) OR (arrival_date = ? AND (departure_time <= ? AND arrival_time >= ?))) AND departure_station_id = ? AND arrival_station_id = ?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setDate(1, departureDate);
            statement.setTime(2, departureTime);
            statement.setTime(3, departureTime);
            statement.setDate(4, arrivalDate);
            statement.setTime(5, departureTime);
            statement.setTime(6, arrivalTime);
            statement.setInt(7, departureStationId);
            statement.setInt(8, arrivalStationId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Якщо отримано хоча б один запис, є конфлікт з існуючими маршрутами
                return false;
            }

            // Якщо результати не знайдені, немає конфлікту і поїзд доступний для додавання
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }



    public int getTrainIdByParameters(int trainNumber, int departureStationId, int arrivalStationId, Date departureDate, Time departureTime) {
        int trainId = -1;

        String query = "SELECT id FROM train_database_001.train WHERE train_number = ? AND departure_station_id = ? AND arrival_station_id = ? AND departure_date = ? AND departure_time = ?";

        try {
            con = DBConnextion.getCon();
            st = con.prepareStatement(query);
            st.setInt(1, trainNumber);
            st.setInt(2, departureStationId);
            st.setInt(3, arrivalStationId);
            st.setDate(4, departureDate);
            st.setTime(5, departureTime);
            rs = st.executeQuery();

            if (rs.next()) {
                trainId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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

        return trainId;
    }


    public void updateSeatStatus(int id, boolean booked) {
        boolean isBooked = booked;

        String selectQuery = "SELECT train_id, booked FROM seats_buy WHERE id = ?";
        String updateQuery = "UPDATE train SET booked_seats = booked_seats + ? WHERE id = ?";
        String updateSeatsBuyQuery = "UPDATE seats_buy SET booked = ? WHERE id = ?";

        con = DBConnextion.getCon();

        try {
            PreparedStatement selectStatement = con.prepareStatement(selectQuery);
            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int trainId = resultSet.getInt("train_id");
                int bookedSeats = resultSet.getInt("booked");

                boolean currentBookedStatus = (bookedSeats > 0);

                if (currentBookedStatus == isBooked) {
                    // Статус місця відповідає заданому булевому значенню
                    // Нічого не робимо, місце залишається без змін
                } else {
                    // Статус місця відрізняється від заданого булевого значення
                    // Оновлюємо кількість зайнятих місць відповідно
                    int updateValue = (isBooked) ? 1 : -1;

                    PreparedStatement updateStatement = con.prepareStatement(updateQuery);
                    updateStatement.setInt(1, updateValue);
                    updateStatement.setInt(2, trainId);
                    updateStatement.executeUpdate();

                    PreparedStatement updateSeatsBuyStatement = con.prepareStatement(updateSeatsBuyQuery);
                    updateSeatsBuyStatement.setBoolean(1, isBooked);
                    updateSeatsBuyStatement.setInt(2, id);
                    updateSeatsBuyStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }








}

