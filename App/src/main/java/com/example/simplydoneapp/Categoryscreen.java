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

    protected void openCategory(Stage startscreen, Scene startscene, int userid) {
        this.userid = userid;

        AnchorPane body = new AnchorPane();
        body.getStyleClass().add("body");
        VBox container = new VBox();
        VBox categoryList = new VBox();

        container.setPrefWidth(700);
        container.setPrefHeight(700);

        categoryList.setMaxSize(700, 700);
        container.setMaxSize(700, 700);

        AnchorPane.setTopAnchor(container, (body.getHeight() - container.getHeight()) / 2);
        AnchorPane.setBottomAnchor(container, (body.getHeight() - container.getHeight()) / 2);
        AnchorPane.setLeftAnchor(container, (body.getWidth() - container.getWidth()) / 2);
        AnchorPane.setRightAnchor(container, (body.getWidth() - container.getWidth()) / 2);

        Button logoButton = new Button();
        logoButton.setOnAction(e -> backToStartscreen(startscreen, startscene));
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
        scrollCategory.setFitToWidth(true);

        loadCategories(categoryList);
        container.getChildren().add(scrollCategory);

        body.getChildren().add(container);

        container.prefWidthProperty().bind(body.widthProperty());
        container.prefHeightProperty().bind(body.heightProperty());

        body.widthProperty().addListener((obs, oldVal, newVal) -> updateContainerPosition(container));
        body.heightProperty().addListener((obs, oldVal, newVal) -> updateContainerPosition(container));


        Scene scene = new Scene(body, 1314, 799);
        startscreen.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("styles/css/base.css").toExternalForm());
        startscreen.show();
    }

    protected void backToStartscreen(Stage startscreen,Scene startscene) {
        startscreen.setScene(startscene);
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

    protected void shareCategory(Button button, int catgoryid, HBox sharedUserContainer) {
        Stage sharePopup = new Stage();
        Stage currentStage = (Stage) button.getScene().getWindow();
        sharePopup.initOwner(currentStage);

        VBox sharePopupContainer = new VBox(15);
        sharePopupContainer.getStyleClass().add("task--create--container");

        Label newLabel = new Label("Kategorie teilen");
        newLabel.getStyleClass().add("h1Title");
        sharePopupContainer.getChildren().add(newLabel);

        TextField shareUser = new TextField();
        shareUser.getStyleClass().add("form--widget");
        shareUser.setPromptText("Benutzername");
        sharePopupContainer.getChildren().add(shareUser);

        Button shareSubmit = new Button("Kategorie teilen");
        shareSubmit.setMaxWidth(Double.MAX_VALUE);
        shareSubmit.getStyleClass().add("form--submit");
        shareSubmit.setOnAction(event -> {
            shareDatabase(shareUser.getText(), catgoryid, sharedUserContainer);
            closeStage(sharePopup);
        });
        sharePopupContainer.getChildren().add(shareSubmit);

        Scene sharePopupScene = new Scene(sharePopupContainer, 300, 220);
        sharePopupScene.getStylesheets().clear();
        sharePopupScene.getStylesheets().add(getClass().getResource("styles/css/base.css").toExternalForm());
        sharePopup.setScene(sharePopupScene);
        sharePopup.show();
    }

    protected void shareDatabase(String user, int catgoryid, HBox sharedUserContainer) {
        int sharingID = Database.createSharing(userid, catgoryid, Database.getUserId(user));
        Sharing newSharing = new Sharing(sharingID, catgoryid, userid, Database.getUserId(user));

        addToSharedList(sharedUserContainer, newSharing);
    }

    protected void addToSharedList(HBox sharedUserContainer, Sharing sharingObject) {
        HBox singleShare = new HBox();

        Button deleteShare = new Button("x");
        deleteShare.getStyleClass().add("button--image");
        deleteShare.setOnAction(e -> {
                deleteShareFromDatabase(sharingObject.getSharingID());
                deleteShareFromView(sharedUserContainer, singleShare);
        });
        singleShare.getChildren().add(deleteShare);

        Label name = new Label(Database.getUsername(sharingObject.getEmpfängerUserID()));
        singleShare.getChildren().add(name);

        sharedUserContainer.getChildren().add(singleShare);
    }

    protected void deleteShareFromDatabase(int sharedId) {
        Database.deleteSharing(sharedId);
    }
    protected void deleteShareFromView(HBox container, HBox child) {
        container.getChildren().remove(child);
    }

    protected void deleteCategory(int categoryid, VBox categoryVBox, HBox viewElement, HBox sharedUser) {
        if(sharedUser.getChildren().isEmpty()) {
            Database.deleteCategory(categoryid);
            categoryVBox.getChildren().remove(viewElement);
        }
    }

    private void addCategory(VBox categoryVBox, Category category) {
        HBox container = new HBox();

        Label categoryID = new Label(String.valueOf(category.getCategoryID()));
        Label categoryName = new Label(category.getKategoriename());

        HBox sharedUser = new HBox();

        List<Sharing> sharedWith = Database.getSharedUsersForCategory(userid, category.getCategoryID());

        for(Sharing share : sharedWith) {
            addToSharedList(sharedUser, share);
        }

        Button shareCategory = new Button("share");
        shareCategory.setOnAction(e -> shareCategory(shareCategory, category.getCategoryID(), sharedUser));

        Button deleteCategory = new Button("del");
        deleteCategory.setOnAction(e -> deleteCategory(category.getCategoryID(), categoryVBox, container, sharedUser));

        container.getChildren().addAll(categoryID, categoryName, sharedUser, shareCategory, deleteCategory);
        categoryVBox.getChildren().add(container);
    }

    private void updateContainerPosition(VBox container) {

        double top = 100;
        double right = 200;
        double left = 200;
        double bottom = 100;

        AnchorPane.setTopAnchor(container, top);
        AnchorPane.setRightAnchor(container, right);
        AnchorPane.setLeftAnchor(container, left);
        AnchorPane.setBottomAnchor(container, bottom);
    }

    public void closeStage(Stage currentStage) {
        currentStage.close();
    }

}
