package com.example.simplydoneapp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.Objects;

public class Loginscreen {
    public Button formLageSubmit;
    public Button formLageRegister;
    public TextField formLageEmail;
    public TextField formLagePassword;
    public Label errorLabel;

    private Startscreen startscreen;

    OkHttpClient client = new OkHttpClient();


    public void actLageSubmit(ActionEvent actionEvent) throws IOException {
        errorLabel.setText("");

        String user = formLageEmail.getText();
        String password = formLagePassword.getText();

        if(formLageEmail.getText().isEmpty() || formLagePassword.getText().isEmpty()) {
            errorLabel.setText("Fehlerhafte Eingaben!");
            return;
        }

        String apiUrl = "http:localhost:1337/users/check-credential?username=" + user;
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

                    if(Objects.equals(password, jsonObject.get("Passwort").getAsString())) {
                        callStartscreen();
                        startscreen.setUserId(jsonObject.get("UserID").getAsInt());
                    } else {
                        errorLabel.setText("Passwort ist nicht korrekt!");
                    }
                } else {
                    errorLabel.setText("Benutzername ist nicht korrekt!");
                }
            } else {
                int statusCode = response.code();
                String responseBody = response.body().string();
                errorLabel.setText("Anmeldung derzeit nicht möglich!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actLageRegister(ActionEvent actionEvent) {
        String url = "https://simplydone.bairamov.de/?registering=true";

        String os = System.getProperty("os.name").toLowerCase();

        if(os.contains("windows")){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStartscreen(Startscreen startscreen) {
        this.startscreen = startscreen;
    }

    private void callStartscreen() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start-screen.fxml"));
        try {
            Stage activStage = (Stage) formLageSubmit.getScene().getWindow();

            loader.setLocation(getClass().getResource("start-screen.fxml"));
            Parent root = loader.load();
            Startscreen startscreen = loader.getController();

            this.setStartscreen(startscreen);
            startscreen.setLoginscreen(this);

            Scene scene = new Scene(root);
            activStage.setScene(scene);
            activStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}