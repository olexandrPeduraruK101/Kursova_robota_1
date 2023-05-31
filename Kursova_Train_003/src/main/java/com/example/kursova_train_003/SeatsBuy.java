package com.example.kursova_train_003;

public class SeatsBuy {
    private int id;
    private int trainId;
    private int seatNumber;
    private boolean booked;


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }
}