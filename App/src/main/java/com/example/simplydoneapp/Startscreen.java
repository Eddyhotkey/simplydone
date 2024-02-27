package com.example.simplydoneapp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    public VBox vboxDueToday;
    public VBox vboxOtherTasks;
    int userID = 1;
    private Loginscreen loginscreen;

    OkHttpClient client = new OkHttpClient();

    public void setLoginscreen(Loginscreen loginscreen) {
        this.loginscreen = loginscreen;
    }

    public void setUserId(int userid) {
        this.userID = userid;
    }

    public int getUserID() {
        return this.userID;
    }

    protected void getUserData() {

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
        newSubmit.setOnAction(event -> createToDo(userID, newTitle.getText(), newDescripton.getText(), newDate.getValue(), newKategory.getText(), newPriority.getText()));
        todoPopupContainer.getChildren().add(newSubmit);

        Scene todoPopupScene = new Scene(todoPopupContainer, 300, 450);
        todoPopupScene.getStylesheets().clear();
        todoPopupScene.getStylesheets().add(getClass().getResource("styles/css/base.css").toExternalForm());
        todoPopup.setScene(todoPopupScene);
        todoPopup.show();
    }

    private void createToDo(int userID, String title, String description, LocalDate dueday, String category, String priority) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = dueday.format(formatter);
        Task task = new Task(userID, title, description, formattedDate, category, priority);

        task.setTodoID(Database.setNewToDo(task.getUserID(), task.getCategory(), task.getTitle(), task.getDescription(), task.getDueDay(), task.getPriority()));
        task.setDateFaelligkeitsdatum(task.getDueDay());


        LocalDate currentLocalDate = LocalDate.now();
        String currentDate = currentLocalDate.format(formatter);
        String selectedDate = task.getDateFaelligkeitsdatum().toString();

        if(Objects.equals(selectedDate, currentDate)) {
            vboxDueToday.getChildren().add(addToDo(task));
        } else {
            vboxOtherTasks.getChildren().add(addToDo(task));
        }

        //ToDo Fenster schlie0en nach Button click
    }

    public void fillTodayTasks() {
        List<Task> data = Database.getAllOpenToDosToday(this.userID);
        if (data != null) {
            for (Task task : data) {
                vboxDueToday.getChildren().add(addToDo(task));
            }
        }
        vboxDueToday.setSpacing(10);
        vboxDueToday.getStylesheets().add(getClass().getResource("styles/css/base.css").toExternalForm());
    }

    public void fillOtherTasks() {
        List<Task> data = Database.getAllOpenToDosOther(this.userID);
        if (data != null) {
            for (Task task : data) {
                vboxOtherTasks.getChildren().add(addToDo(task));
            }
        }
        vboxOtherTasks.setSpacing(10);
        vboxOtherTasks.getStylesheets().add(getClass().getResource("styles/css/base.css").toExternalForm());
    }

    public VBox addToDo(Task task) {
        task.setDateFaelligkeitsdatum(task.getDueDay());
        VBox container = new VBox(10);
        container.getStyleClass().add("todo--container");
        setBackgroundColor(container, task);

        HBox firstRow = new HBox(10);

        Label labelFirstRow = new Label(task.getTodoID() + task.getTitle());
        labelFirstRow.getStyleClass().add("todo--title");
        Button editButton = new Button("edit");
        editButton.getStyleClass().add("todo--edit");
        Button doneButton = new Button("done");
        doneButton.getStyleClass().add("todo--done");
        firstRow.getChildren().add(labelFirstRow);
        firstRow.getChildren().add(editButton);
        firstRow.getChildren().add(doneButton);

        container.getChildren().add(firstRow);
        Label labelSecondRow = new Label(task.getDescription());
        labelSecondRow.getStyleClass().add("todo--description");
        container.getChildren().add(labelSecondRow);

        HBox lastRow = new HBox();

        Label labelThirdRow = new Label("Fällig am: " + task.getDateFaelligkeitsdatum());
        labelThirdRow.getStyleClass().add("todo--dueday");
        lastRow.getChildren().add(labelThirdRow);

        Label labelForthRow = new Label("Priorität:" + task.getPriority());
        labelForthRow.getStyleClass().add("todo--priority");
        labelForthRow.setAlignment(Pos.CENTER_RIGHT);
        lastRow.getChildren().add(labelForthRow);

        //ToDo: Spacing variabel gestalten
        lastRow.setSpacing(80);


        container.getChildren().add(lastRow);
        return container;
    }

    public void setBackgroundColor(VBox container, Task task) {
        if(Objects.equals(task.getPriority(), "hoch")) {
            container.getStyleClass().add("todo--high");
        } else if (Objects.equals(task.getPriority(), "mittel")) {
            container.getStyleClass().add("todo--middle");
        } else {
            container.getStyleClass().add("todo--low");
        }
    }
}