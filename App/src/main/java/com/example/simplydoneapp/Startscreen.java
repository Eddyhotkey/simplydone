package com.example.simplydoneapp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Startscreen {
    public Label dummy;
    public Canvas cvnProfilePicture;
    public Label lblProfileForename;
    public Label lblProfileLastname;
    public Label lblProfileMail;
    public Label lblGreeting;
    public Button btnNewTodo;
    public Label taskTitle;
    public Label taskPriority;
    public Label taskDueDay;
    public Accordion taskDescription;
    int userID = 1;
    private Loginscreen loginscreen;

    ListView<Task> tasksToday = new ListView<Task>();

    OkHttpClient client = new OkHttpClient();

    public void setLoginscreen(Loginscreen loginscreen) {
        this.loginscreen = loginscreen;
    }

    public void setUserId(int userid) {
        this.userID = userid;
        System.out.println("Empfangener Wert: " + userID);
        getUserData();
    }

    private void getUserData() {

        String apiUrl = "http:localhost:1337/users/get-user-data?userid=" + this.userID;
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

                    if(!jsonObject.get("Vorname").isJsonNull()) {
                        lblGreeting.setText("Hallo " + jsonObject.get("Vorname").getAsString());
                        lblProfileForename.setText(jsonObject.get("Vorname").getAsString());
                    }
                    if(!jsonObject.get("Nachname").isJsonNull()) {
                        lblProfileLastname.setText(jsonObject.get("Nachname").getAsString());
                    }
                    if(!jsonObject.get("Email").isJsonNull()) {
                        lblProfileMail.setText(jsonObject.get("Email").getAsString());
                    }
                    if(!jsonObject.get("Profilbild").isJsonNull()) {
                        //lblProfileMail.setText(jsonObject.get("Profilbild").getAsString());
                    }
                }
            } else {
                int statusCode = response.code();
                String responseBody = response.body().string();
                dummy.setText("Anmeldung derzeit nicht möglich!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actBtnExit(ActionEvent actionEvent) {
        ((Stage) lblGreeting.getScene().getWindow()).close();
    }


    public void actNewTodo(ActionEvent actionEvent) {
        Stage todoPopup = new Stage();
        Stage currentStage = (Stage) btnNewTodo.getScene().getWindow();
        todoPopup.initOwner(currentStage);

        VBox todoPopupContainer = new VBox(15);
        todoPopupContainer.getStyleClass().add("task--create--container");

        Label newLabel = new Label("Neues Todo");
        newLabel.getStyleClass().add("h1Title");
        todoPopupContainer.getChildren().add(newLabel);

        TextField newTitle = new TextField();
        newTitle.getStyleClass().add("form--widget");
        newTitle.setPromptText("Titel");
        todoPopupContainer.getChildren().add(newTitle);

        TextArea newDescripton = new TextArea();
        newDescripton.getStyleClass().add("form--widget");
        newDescripton.getStyleClass().add("form--widget--special-input");
        newDescripton.setPromptText("Beschreibung");
        todoPopupContainer.getChildren().add(newDescripton);

        DatePicker newDate = new DatePicker();
        newDate.getStyleClass().add("form--widget");
        newDate.getStyleClass().add("form--widget--special-input");
        newDate.setPromptText(new Date().toString());
        newDate.setMaxWidth(Double.MAX_VALUE);
        todoPopupContainer.getChildren().add(newDate);

        TextField newKategory = new TextField();
        newKategory.getStyleClass().add("form--widget");
        newKategory.setPromptText("Kategorie");
        todoPopupContainer.getChildren().add(newKategory);

        TextField newPriority = new TextField();
        newPriority.getStyleClass().add("form--widget");
        newPriority.setPromptText("Priorität");
        todoPopupContainer.getChildren().add(newPriority);

        Button newSubmit = new Button("Todo hinzufügen");
        newSubmit.setMaxWidth(Double.MAX_VALUE);
        newSubmit.getStyleClass().add("form--submit");
        newSubmit.setOnAction(event -> createToDo(userID, newTitle.getText(), newDescripton.getText(), String.valueOf(newDate.getValue()), newKategory.getText(), newPriority.getText()));
        todoPopupContainer.getChildren().add(newSubmit);

        Scene todoPopupScene = new Scene(todoPopupContainer, 300, 450);
        todoPopupScene.getStylesheets().clear();
        todoPopupScene.getStylesheets().add(getClass().getResource("styles/css/base.css").toExternalForm());
        todoPopup.setScene(todoPopupScene);
        todoPopup.show();
    }

    private void createToDo(int userID, String title, String description, String dueday, String category, String priority) {
        Task task = new Task(userID, title, description, dueday, category, priority);

        task.setTodoID(Database.setNewToDo(task.getUserID(), task.getCategory(), task.getTitle(), task.getDescription(), task.getDueDay(), task.getPriority()));

        taskTitle.setText(task.getTitle());
        taskDescription.getPanes().getFirst().setText(task.getDescription());
        taskDueDay.setText(String.valueOf(task.getDueDay()));
        taskPriority.setText(String.valueOf(task.getPriority()));
    }

    public void fillTodayTasks() {
        List<Task> data = Database.getAllOpenToDos(this.userID);
    }
}