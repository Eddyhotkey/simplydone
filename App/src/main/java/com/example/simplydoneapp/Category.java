package com.example.simplydoneapp;

import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class Category {

    protected void openCategory(Stage startscreen) {
        AnchorPane test = new AnchorPane();
        VBox box = new VBox();
        DatePicker labeldas = new DatePicker();
        box.getChildren().add(labeldas);

        test.getChildren().add(box);

        Scene scene = new Scene(test, 1314, 799);
        startscreen.setScene(scene);
        startscreen.show();
    }
}
