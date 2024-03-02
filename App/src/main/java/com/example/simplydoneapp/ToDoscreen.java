package com.example.simplydoneapp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ToDoscreen {

    int userid;

    protected void openTodoscreen(Stage startscreen, int userid) {
        this.userid = userid;

        AnchorPane body = new AnchorPane();
        VBox container = new VBox();
        VBox todoList = new VBox();


        container.getStyleClass().add("category--container");

        container.setPrefWidth(700);
        container.setPrefHeight(700);
        container.setAlignment(Pos.CENTER);
        body.setPrefWidth(Double.MAX_VALUE);
        body.setPrefHeight(Double.MAX_VALUE);


        ScrollPane scrollTodo = new ScrollPane(todoList);
        loadTodos(todoList);
        container.getChildren().add(scrollTodo);

        body.getChildren().add(container);

        Scene scene = new Scene(body, 1314, 799);
        startscreen.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("styles/css/base.css").toExternalForm());
        startscreen.show();
    }


    protected void loadTodos(VBox categoryVBox) {
        List<Task> todoList = Database.getAllOpenToDos(this.userid);

        if (todoList != null) {
            for (Task task : todoList) {
                addTodo(categoryVBox, task);
            }
        }
    }

    protected void actEditTodo() {
        //ToDO
    }

    protected void actDeleteTodo() {
        //ToDO
    }

    private void addTodo(VBox categoryVBox, Task task) {
        task.setDateFaelligkeitsdatum(task.getDueDay());
        VBox container = new VBox();

        Label todoID = new Label(String.valueOf(task.getTodoID()));
        Label todoTitle = new Label(task.getTitle());
        Label todoDescription = new Label(task.getDescription());
        Label todoDueDay = new Label("Fällig am: " + task.getDateFaelligkeitsdatum());
        Label todoPriority = new Label("Priorität: " + task.getPriority());

        Button editTodo = new Button("edit");
        editTodo.setOnAction(e -> actEditTodo());

        Button deleteTodo = new Button("del");
        deleteTodo.setOnAction(e -> actDeleteTodo());

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(editTodo, deleteTodo);

        container.getChildren().addAll(todoID, todoTitle, todoDescription, todoDueDay, todoPriority, buttonBar);
        categoryVBox.getChildren().add(container);
    }

}
