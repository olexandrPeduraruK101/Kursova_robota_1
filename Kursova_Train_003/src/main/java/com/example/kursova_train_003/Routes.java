package com.example.kursova_train_003;

public class Routes {
    private int id;
    private int departure_station_id;
    private int arrival_station_id;
    private float distance;
    private String departureStationName;
    private String arrivalStationName;

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getDepartureStationName() {
        return departureStationName;
    }

    public void setDepartureStationName(String departureStationName) {
        this.departureStationName = departureStationName;
    }

    public String getArrivalStationName() {
        return arrivalStationName;
    }

    public void setArrivalStationName(String arrivalStationName) {
        this.arrivalStationName = arrivalStationName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeparture_station_id() {
        return departure_station_id;
    }

    public void setDeparture_station_id(int departure_station_id) {
        this.departure_station_id = departure_station_id;
    }

    public int getArrival_station_id() {
        return arrival_station_id;
    }

    public void setArrival_station_id(int arrival_station_id) {
        this.arrival_station_id = arrival_station_id;
    }
}
