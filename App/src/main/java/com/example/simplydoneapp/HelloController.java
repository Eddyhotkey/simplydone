package com.example.simplydoneapp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.Objects;

public class HelloController {
    public Button formLageSubmit;
    public Button formLageRegister;
    public TextField formLageEmail;
    public TextField formLagePassword;
    public Label errorLabel;

    OkHttpClient client = new OkHttpClient();

    public void actLageSubmit(ActionEvent actionEvent) throws IOException {
        errorLabel.setText("");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start-screen.fxml"));
        Parent secondSceneRoot = loader.load();
        Scene secondScene = new Scene(secondSceneRoot);

        Scene currentScene = formLageSubmit.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();

        String user = formLageEmail.getText();
        String password = formLagePassword.getText();

        if(formLageEmail.getText().isEmpty() || formLagePassword.getText().isEmpty()) {
            errorLabel.setText("Fehlerhafte Eingaben!");
            return;
        }

        String apiUrl = "http:localhost:8080/users/check-credential?username=" + user;
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
                        currentStage.setScene(secondScene);
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

        WebDriver driver = new ChromeDriver();
        driver.get(url);
        driver.quit();
    }
}