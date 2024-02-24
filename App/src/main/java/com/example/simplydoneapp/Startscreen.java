package com.example.simplydoneapp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Objects;

public class Startscreen {
    public Label dummy;
    public Canvas cvnProfilePicture;
    public Label lblProfileForename;
    public Label lblProfileLastname;
    public Label lblProfileMail;
    public Label lblGreeting;
    int userID = 1;
    private Loginscreen loginscreen;

    OkHttpClient client = new OkHttpClient();

    public void setLoginscreen(Loginscreen loginscreen) {
        this.loginscreen = loginscreen;
    }

    public void setUserId(int userid) {
        this.userID = userid;
        System.out.println("Empfangener Wert: " + userID);
        getUserData();
    }

    private void getUserData() {

        String apiUrl = "http:localhost:1337/users/get-user-data?userid=" + this.userID;
        Request request = new Request.Builder()
                .url(apiUrl)
                .build();
        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();

                if(!responseBody.isEmpty()) {
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);

                    if(!jsonObject.get("Vorname").isJsonNull()) {
                        lblGreeting.setText("Hallo " + jsonObject.get("Vorname").getAsString());
                        lblProfileForename.setText(jsonObject.get("Vorname").getAsString());
                    }
                    if(!jsonObject.get("Nachname").isJsonNull()) {
                        lblProfileLastname.setText(jsonObject.get("Nachname").getAsString());
                    }
                    if(!jsonObject.get("Email").isJsonNull()) {
                        lblProfileMail.setText(jsonObject.get("Email").getAsString());
                    }
                    if(!jsonObject.get("Profilbild").isJsonNull()) {
                        lblProfileMail.setText(jsonObject.get("Profilbild").getAsString());
                    }
                }
            } else {
                int statusCode = response.code();
                String responseBody = response.body().string();
                dummy.setText("Anmeldung derzeit nicht möglich!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actBtnExit(ActionEvent actionEvent) {
        ((Stage) lblGreeting.getScene().getWindow()).close();
    }


    public void actNewTodo(ActionEvent actionEvent) {
        dummy.setText(String.valueOf(userID));
    }
}


