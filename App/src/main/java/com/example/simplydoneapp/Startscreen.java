package com.example.simplydoneapp;

import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class Startscreen {
    public Label dummy;
    public Canvas cvnProfilePicture;
    public Label lblProfileForename;
    public Label lblProfileLastname;
    public Label lblProfileMail;
    public Label lblGreeting;
    int userID;
    private Loginscreen loginscreen;

    public void setLoginscreen(Loginscreen loginscreen) {
        this.loginscreen = loginscreen;
    }

    public void setUserId(int wert) {
        this.userID = wert;
        System.out.println("Empfangener Wert: " + userID);
    }

    public void actBtnExit(ActionEvent actionEvent) {
        ((Stage) lblGreeting.getScene().getWindow()).close();
    }


}


