package com.example.simplydoneapp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class Categoryscreen {
    int userid;

    protected void openCategory(Stage startscreen, int userid) {
        this.userid = userid;

        AnchorPane body = new AnchorPane();
        VBox container = new VBox();
        VBox categoryList = new VBox();


        container.getStyleClass().add("category--container");

        container.setPrefWidth(700);
        container.setPrefHeight(700);
        container.setAlignment(Pos.CENTER);
        body.setPrefWidth(Double.MAX_VALUE);
        body.setPrefHeight(Double.MAX_VALUE);

        HBox newCategoryBar = new HBox();
        TextField newCategory = new TextField();
        Button newCategorySubmit = new Button("add");

        newCategory.setPromptText("Neue Kategorie");
        newCategorySubmit.setOnAction(e -> actNewCategory(categoryList, newCategory.getText()));

        newCategoryBar.getChildren().addAll(newCategorySubmit, newCategory);
        container.getChildren().add(newCategoryBar);


        ScrollPane scrollCategory = new ScrollPane(categoryList);
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
            addCategory(categoryVBox, newCategory);
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
