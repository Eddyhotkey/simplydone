package com.example.simplydoneapp;

import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;

public class Startscreen {
    public Label dummy;
    public Canvas cvnProfilePicture;
    public Label lblProfileForename;
    public Label lblProfileLastname;
    public Label lblProfileMail;
    public Label lblGreeting;
    int userID;

    public Startscreen(int userid) {
        this.userID = userid;
    }

    public void actBtnExit(ActionEvent actionEvent) {
        dummy.setText(" " + userID);
    }
}


