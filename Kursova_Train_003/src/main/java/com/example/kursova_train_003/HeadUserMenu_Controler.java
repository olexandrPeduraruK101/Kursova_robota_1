package com.example.kursova_train_003;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HeadUserMenu_Controler {
    private Users users;

    @FXML
    private Label welcom;

    public void setWelcomeMessage(Users users) {
        this.users = users;
        welcom.setText("Добро пожаловать, " + users.getFirstName() + "!");
    }



}
