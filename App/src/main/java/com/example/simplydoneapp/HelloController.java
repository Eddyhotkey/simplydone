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

import java.io.IOException;
import java.util.Objects;

public class HelloController {
    public Button formLageSubmit;
    public Button formLageRegister;
    public TextField formLageEmail;
    public TextField formLagePassword;
    @FXML
    private Label welcomeText;

    public void actLageSubmit(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start-screen.fxml"));
        Parent secondSceneRoot = loader.load();
        Scene secondScene = new Scene(secondSceneRoot);

        Scene currentScene = formLageSubmit.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();

        if(Objects.equals(formLageEmail.getText(), "test")) {
            if(Objects.equals(formLagePassword.getText(), "123")) {
                currentStage.setScene(secondScene);
            }
        }
    }

    public void actLageRegister(ActionEvent actionEvent) {

    }
}