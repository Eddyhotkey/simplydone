package com.example.simplydoneapp;

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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.util.Objects;

public class HelloController {
    public Button formLageSubmit;
    public Button formLageRegister;
    public TextField formLageEmail;
    public TextField formLagePassword;
    public Label dummy;


    public void actLageSubmit(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start-screen.fxml"));
        Parent secondSceneRoot = loader.load();
        Scene secondScene = new Scene(secondSceneRoot);

        Scene currentScene = formLageSubmit.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();

        String user = formLageEmail.getText();
        String password = formLagePassword.getText();

        String apiUrl = "http:localhost:1337/users/check-credential";
        String apiString = "?username=" + user;

        apiUrl += apiString;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setDoOutput(true);



            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = apiString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                dummy.setText(response.toString());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.fillInStackTrace();
        }

        if(Objects.equals(user, "test")) {
            if(Objects.equals(password, "123")) {
                currentStage.setScene(secondScene);
            }
        }
    }

    public void actLageRegister(ActionEvent actionEvent) {
        String url = "https://simplydone.bairamov.de/?registering=true";

        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}