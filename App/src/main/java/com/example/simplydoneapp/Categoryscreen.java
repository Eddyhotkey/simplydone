package com.example.simplydoneapp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Categoryscreen {

    protected void openCategory(Stage startscreen) {
        AnchorPane test = new AnchorPane();
        VBox container = new VBox();
        container.getStyleClass().add("category--container");
        container.setPrefWidth(700);
        container.setPrefHeight(700);
        container.setAlignment(Pos.CENTER);

        Label labeldas = new Label();
        labeldas.setText("Foo");
        container.getChildren().add(labeldas);

        test.getChildren().add(container);

        Scene scene = new Scene(test, 1314, 799);
        startscreen.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("styles/css/base.css").toExternalForm());
        startscreen.show();
    }


}
