package com.example.simplydoneapp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ToDoscreen {

    int userid;

    protected void openTodoscreen(Stage startscreen, Scene startscene, int userid) {
        this.userid = userid;

        AnchorPane body = new AnchorPane();
        body.getStyleClass().add("body");
        VBox container = new VBox();
        VBox todoList = new VBox();

        container.setPrefSize(700, 700);
        todoList.setPrefSize(700, 700);
        body.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

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
        newTask.setOnAction(e -> addTodoTopbar(todoList, newTask));
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


        ScrollPane scrollTodo = new ScrollPane(todoList);
        scrollTodo.setPrefSize(600, 700);
        scrollTodo.setFitToWidth(true);
        loadTodos(todoList);
        container.getChildren().add(scrollTodo);

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

    protected void loadTodos(VBox todoVBox) {
        List<Task> todoList = Database.getAllOpenToDos(this.userid);

        if (todoList != null) {
            for (Task task : todoList) {
                addTodo(todoVBox, task);
            }
        }
    }

    protected void addTodoTopbar(VBox todoVBox, Button button) {
        Stage todoPopup = new Stage();
        Stage currentStage = (Stage) button.getScene().getWindow();
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
        newDate.setPromptText(getCurrentDate());
        newDate.setMaxWidth(Double.MAX_VALUE);
        todoPopupContainer.getChildren().add(newDate);

        TextField newCategory = new TextField();
        newCategory.getStyleClass().add("form--widget");
        newCategory.setPromptText("Kategorie");
        todoPopupContainer.getChildren().add(newCategory);

        ComboBox newPriority = new ComboBox();
        newPriority.getStyleClass().add("form--widget");
        newPriority.getStyleClass().add("form--widget--select");
        newPriority.setPromptText("Priorität");
        newPriority.getItems().addAll("niedrig", "mittel", "hoch");
        newPriority.setValue("mittel");
        newPriority.setMaxWidth(Double.MAX_VALUE);
        todoPopupContainer.getChildren().add(newPriority);

        Button newSubmit = new Button("Todo hinzufügen");
        newSubmit.setMaxWidth(Double.MAX_VALUE);
        newSubmit.getStyleClass().add("form--submit");
        newSubmit.setOnAction(event -> {
            createToDo(todoVBox, this.userid, newTitle.getText(), newDescripton.getText(), newDate.getValue(), newCategory.getText(), String.valueOf(newPriority.getValue()));
            closeStage(todoPopup);
        });
        todoPopupContainer.getChildren().add(newSubmit);

        Scene todoPopupScene = new Scene(todoPopupContainer, 300, 450);
        todoPopupScene.getStylesheets().clear();
        todoPopupScene.getStylesheets().add(getClass().getResource("styles/css/base.css").toExternalForm());
        todoPopup.setScene(todoPopupScene);
        todoPopup.show();
    }

    protected void actEditTodo() {
        //ToDO
    }

    protected void actDeleteTodo() {
        //ToDO
    }

    private void createToDo(VBox todoVBox, int userID, String title, String description, LocalDate dueday, String category, String priority) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = dueday.format(formatter);
        Task task = new Task(userID, title, description, formattedDate, category, priority);

        task.setTodoID(Database.setNewToDo(task.getUserID(), task.getCategory(), task.getTitle(), task.getDescription(), dueday, task.getPriority()));
        task.setDateFaelligkeitsdatum(task.getDueDay());

        addTodo(todoVBox, task);
    }

    private void addTodo(VBox todoVBox, Task task) {
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
        todoVBox.getChildren().add(container);
    }

    public String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate currentLocalDate = LocalDate.now();
        return currentLocalDate.format(formatter);
    }

    public void closeStage(Stage currentStage) {
        currentStage.close();
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
}
