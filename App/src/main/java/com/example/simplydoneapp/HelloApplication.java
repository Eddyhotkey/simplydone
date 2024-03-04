package com.example.simplydoneapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(HelloApplication.class.getResource("login-screen.fxml"));
        Parent loginRoot = loginLoader.load();
        Loginscreen loginscreen = loginLoader.getController();

        Scene scene = new Scene(loginRoot);
        scene.getStylesheets().add(getClass().getResource("styles/css/base.css").toExternalForm());
        stage.setTitle("simplyDone");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        System.setProperty("java.net.useSystemProxies", "false");
    }
}