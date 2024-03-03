package com.example.simplydoneapp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Categoryscreen {
    int userid;

    protected void openCategory(Stage startscreen, int userid) {
        this.userid = userid;

        AnchorPane body = new AnchorPane();
        body.getStyleClass().add("body");
        VBox container = new VBox();
        VBox categoryList = new VBox();

        body.setPrefWidth(Double.MAX_VALUE);
        body.setPrefHeight(Double.MAX_VALUE);

        container.setAlignment(Pos.CENTER);
        container.setPrefWidth(700);
        container.setPrefHeight(500);
        categoryList.setPrefWidth(650);
        categoryList.setPrefHeight(450);

        categoryList.setMaxSize(700, 700);
        container.setMaxSize(700, 700);

        AnchorPane.setTopAnchor(container, (body.getHeight() - container.getHeight()) / 2);
        AnchorPane.setBottomAnchor(container, (body.getHeight() - container.getHeight()) / 2);
        AnchorPane.setLeftAnchor(container, (body.getWidth() - container.getWidth()) / 2);
        AnchorPane.setRightAnchor(container, (body.getWidth() - container.getWidth()) / 2);

        Button logoButton = new Button();
        logoButton.getStyleClass().add("button--image");
        Image imageLogo = new Image(getClass().getResource("images/Logo_simplyDone.png").toExternalForm());
        ImageView imageView = new ImageView(imageLogo);
        logoButton.setGraphic(imageView);

        body.getChildren().add(logoButton);
        AnchorPane.setTopAnchor(logoButton, 0.0);
        AnchorPane.setLeftAnchor(logoButton, 0.0);

        ButtonBar topButtonBar = new ButtonBar();

        Button newTask = new Button();
        newTask.getStyleClass().addAll("button--image", "button--image--round");
        Image imageNewTask = new Image(getClass().getResource("images/plus-circle.png").toExternalForm());
        ImageView imageViewNewTask = new ImageView(imageNewTask);
        newTask.setGraphic(imageViewNewTask);

        Button exit = new Button();
        exit.getStyleClass().add("button--image");
        Image imageExit = new Image(getClass().getResource("images/log-out.png").toExternalForm());
        ImageView imageViewExit = new ImageView(imageExit);
        exit.setGraphic(imageViewExit);

        topButtonBar.getButtons().addAll(newTask, exit);

        body.getChildren().add(topButtonBar);
        AnchorPane.setTopAnchor(topButtonBar, 0.0);
        AnchorPane.setRightAnchor(topButtonBar, 0.0);

        HBox newCategoryBar = new HBox();
        TextField newCategory = new TextField();
        Button newCategorySubmit = new Button("add");

        newCategory.setPromptText("Neue Kategorie");
        newCategorySubmit.setOnAction(e -> actNewCategory(categoryList, newCategory.getText()));

        newCategoryBar.getChildren().addAll(newCategorySubmit, newCategory);
        container.getChildren().add(newCategoryBar);


        ScrollPane scrollCategory = new ScrollPane(categoryList);

        scrollCategory.setPrefWidth(700);
        scrollCategory.setPrefHeight(500);
        scrollCategory.setMaxSize(700, 500);

        loadCategories(categoryList);
        container.getChildren().add(scrollCategory);

        body.getChildren().add(container);

        Scene scene = new Scene(body, 1314, 799);
        startscreen.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("styles/css/base.css").toExternalForm());
        startscreen.show();
    }

    protected void actNewCategory(VBox categoryVBox, String name) {
        if(!Objects.equals(name, "")) {
            Category newCategory = new Category(Database.addNewCategory(this.userid, name), name);
            if(newCategory.getCategoryID() > -1) {
                addCategory(categoryVBox, newCategory);
            }
        }
    }

    protected void loadCategories(VBox categoryVBox) {
        List<Category> categoryList = Database.getAllCategories(this.userid);

        if (categoryList != null) {
            for (Category category : categoryList) {
                addCategory(categoryVBox, category);
            }
        }
    }

    protected void shareCategory() {
        //ToDO
    }

    protected void deleteCategory() {
        //ToDO
    }

    private void addCategory(VBox categoryVBox, Category category) {
        HBox container = new HBox();

        Label categoryID = new Label(String.valueOf(category.getCategoryID()));
        Label categoryName = new Label(category.getKategoriename());

        Button shareCategory = new Button("share");
        shareCategory.setOnAction(e -> shareCategory());

        Button deleteCategory = new Button("del");
        deleteCategory.setOnAction(e -> deleteCategory());

        container.getChildren().addAll(categoryID, categoryName, shareCategory, deleteCategory);
        categoryVBox.getChildren().add(container);
    }


}
