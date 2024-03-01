package com.example.simplydoneapp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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
    public Button btnExit;
    public Button btnCategoryScene;
    public Button btnToDoScene;
    public Button btnProfileScene;
    public VBox vboxLeft;
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
            createToDo(userID, newTitle.getText(), newDescripton.getText(), newDate.getValue(), newCategory.getText(), String.valueOf(newPriority.getValue()));
            closeStage(todoPopup);
        });
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

        task.setTodoID(Database.setNewToDo(task.getUserID(), task.getCategory(), task.getTitle(), task.getDescription(), dueday, task.getPriority()));
        task.setDateFaelligkeitsdatum(task.getDueDay());

        updateVboxes(vboxDueToday, vboxOtherTasks, addToDo(task), task);
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
        container.setOnMouseClicked((event -> editTodo(task, container)));

        HBox firstRow = new HBox(10);

        Label labelFirstRow = new Label(task.getTodoID() + task.getTitle());
        labelFirstRow.getStyleClass().add("todo--title");
        Button doneButton = new Button("done");
        doneButton.setOnAction(event -> actCloseToDo(task, container));
        doneButton.getStyleClass().add("todo--done");
        firstRow.getChildren().add(labelFirstRow);
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

    public void editTodo(Task task, VBox container) {
        Stage todoPopup = new Stage();
        Stage currentStage = (Stage) btnExit.getScene().getWindow();
        todoPopup.initOwner(currentStage);

        VBox todoPopupContainer = new VBox(15);
        todoPopupContainer.getStyleClass().add("task--create--container");

        Label newLabel = new Label("ToDo bearbeiten");
        newLabel.getStyleClass().add("h1Title");
        todoPopupContainer.getChildren().add(newLabel);

        TextField newTitle = new TextField();
        newTitle.getStyleClass().add("form--widget");
        newTitle.setPromptText("Titel");
        newTitle.setText(task.getTitle());
        todoPopupContainer.getChildren().add(newTitle);

        TextArea newDescripton = new TextArea();
        newDescripton.getStyleClass().add("form--widget");
        newDescripton.getStyleClass().add("form--widget--special-input");
        newDescripton.setPromptText("Beschreibung");
        newDescripton.setText(task.getDescription());
        todoPopupContainer.getChildren().add(newDescripton);

        DatePicker newDate = new DatePicker();
        newDate.getStyleClass().add("form--widget");
        newDate.getStyleClass().add("form--widget--special-input");
        newDate.setPromptText(getCurrentDate());
        newDate.setMaxWidth(Double.MAX_VALUE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        newDate.setValue(LocalDate.parse(task.getDateFaelligkeitsdatum(), formatter));
        todoPopupContainer.getChildren().add(newDate);

        TextField newCategory = new TextField();
        newCategory.getStyleClass().add("form--widget");
        newCategory.setPromptText("Kategorie");
        newCategory.setText(task.getCategory());
        todoPopupContainer.getChildren().add(newCategory);

        ComboBox newPriority = new ComboBox();
        newPriority.getStyleClass().add("form--widget");
        newPriority.getStyleClass().add("form--widget--select");
        newPriority.setPromptText("Priorität");
        newPriority.getItems().addAll("niedrig", "mittel", "hoch");
        newPriority.setValue(task.getPriority());
        newPriority.setMaxWidth(Double.MAX_VALUE);
        todoPopupContainer.getChildren().add(newPriority);

        Button editChange = new Button("Änderungen speichern");
        editChange.setMaxWidth(Double.MAX_VALUE);
        editChange.getStyleClass().add("form--submit");
        editChange.setOnAction(event -> {
            actUpdateTodo(task, newCategory.getText(), newTitle.getText(), newDescripton.getText(), newDate.getValue(), String.valueOf(newPriority.getValue()), container);
            closeStage(todoPopup);
        });
        todoPopupContainer.getChildren().add(editChange);

        Button editDone = new Button("ToDo erledigt");
        editDone.setMaxWidth(Double.MAX_VALUE);
        editDone.getStyleClass().add("form--submit");
        editDone.setOnAction(event -> {
            actCloseToDo(task, container);
            closeStage(todoPopup);
        });
        todoPopupContainer.getChildren().add(editDone);

        Button editDelete = new Button("ToDo löschen");
        editDelete.setMaxWidth(Double.MAX_VALUE);
        editDelete.getStyleClass().add("form--submit form--submit--red");
        editDelete.setOnAction(event -> {
            actDeleteToDo(task, container);
            closeStage(todoPopup);
        });
        todoPopupContainer.getChildren().add(editDelete);

        Scene todoPopupScene = new Scene(todoPopupContainer, 300, 450);
        todoPopupScene.getStylesheets().clear();
        todoPopupScene.getStylesheets().add(getClass().getResource("styles/css/base.css").toExternalForm());
        todoPopup.setScene(todoPopupScene);
        todoPopup.show();
    }

    public void actUpdateTodo(Task task, String category, String title, String description, LocalDate dueday, String priority, VBox container) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = dueday.format(formatter);

        task.setTitle(title);
        task.setDescription(description);
        task.setCategory(category);
        task.setDateFaelligkeitsdatum(formattedDate);
        task.setFälligkeitsdatum(formattedDate);
        task.setPriority(priority);

        int dbResponse = Database.updateCurrentTodo(task.getCategory(), task.getTitle(), task.getDescription(), dueday, task.getPriority(), task.getTodoID());

        //ToDO: besseres Handling
        if (dbResponse < 0) {
            return;
        }
        updateVboxes(vboxDueToday, vboxOtherTasks, container, task);
    }

    public void actCloseToDo(Task task, VBox container) {
        int dbResponse = Database.closeTodo(task.getTodoID());

        //ToDO: besseres Handling
        if (dbResponse < 0) {
            return;
        }

        task.setStatus("closed");

        updateVboxes(vboxDueToday, vboxOtherTasks, container, task);
    }
    public void actDeleteToDo(Task task, VBox container) {
        int dbResponse = Database.deleteTodo(task.getTodoID());
        //ToDO: besseres Handling
        if (dbResponse < 0) {
            return;
        }

        task.setStatus("closed");

        updateVboxes(vboxDueToday, vboxOtherTasks, container, task);
    }

    public String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate currentLocalDate = LocalDate.now();
        return currentLocalDate.format(formatter);
    }

    public void closeStage(Stage currentStage) {
        currentStage.close();
    }

    public void updateVboxes(VBox today, VBox other, VBox container, Task task) {
        String selectedDate = task.getDateFaelligkeitsdatum();

        if (today.getChildren().contains(container)) {
            today.getChildren().remove(container);
        } else other.getChildren().remove(container);

        if(Objects.equals(task.getStatus(), "open")) {
            if(Objects.equals(selectedDate, getCurrentDate())) {
                today.getChildren().add(addToDo(task));
            } else {
                other.getChildren().add(addToDo(task));
            }
        }
    }

    private LocalDate currentDate;
    public void initCalender() {
        GridPane gridCalender = createCalendarGrid();

        Label currentMonth = new Label();

        Button previousMonthButton = new Button("<<");
        previousMonthButton.setOnAction(e -> updateCalendar(-1, currentMonth));

        Button resetMonthButton = new Button("**");
        resetMonthButton.setOnAction(e -> updateCalendar(0, currentMonth));

        Button nextMonthButton = new Button(">>");
        nextMonthButton.setOnAction(e -> updateCalendar(1, currentMonth));

        VBox root = new VBox();
        HBox headRow = new HBox();

        headRow.getChildren().addAll(currentMonth, previousMonthButton, resetMonthButton, nextMonthButton);
        root.getChildren().addAll(headRow, gridCalender);

        vboxLeft.getChildren().add(root);
    }

    private GridPane createCalendarGrid() {
        currentDate = LocalDate.now();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if(i == 0) {
                    Label label = new Label();
                    label.setText(initWeekdays(j));
                    label.setMinSize(50, 20);
                    label.setAlignment(Pos.CENTER);
                    grid.add(label, j, i);
                } else {
                    Button cell = new Button();
                    cell.setMinSize(50, 38);
                    cell.setOnAction(e -> getCalenderToDos(Integer.parseInt(cell.getText()), currentDate.getMonthValue(), currentDate.getYear()));
                    grid.add(cell, j, i);
                }
            }
        }
        return grid;
    }

    private String initWeekdays(int i) {
        return switch (i) {
            case 0 -> "Mo";
            case 1 -> "Tu";
            case 2 -> "We";
            case 3 -> "Th";
            case 4 -> "Fr";
            case 5 -> "Sa";
            case 6 -> "So";
            default -> "error";
        };
    }

    private String initMonth(String month) {
        return switch (month) {
            case "JANUARY" -> "Januar";
            case "FEBRUARY" -> "Februar";
            case "MARCH" -> "März";
            case "APRIL" -> "April";
            case "MAY" -> "Mai";
            case "JUNE" -> "Juni";
            case "JULY" -> "Juli";
            case "AUGUST" -> "August";
            case "SEPTEMBER" -> "September";
            case "OCTOBER" -> "Oktober";
            case "NOVEMBER" -> "November";
            case "DECEMBER" -> "Dezember";
            default -> "error";
        };
    }

    private void updateCalendar(int monthOffset, Label currentMonth) {
        if(monthOffset == 0) {
            currentDate = LocalDate.now();
        } else {
            currentDate = currentDate.plusMonths(monthOffset);
        }
        currentMonth.setText(initMonth(String.valueOf(currentDate.getMonth())) + " " + currentDate.getYear());

        VBox parent = (VBox) vboxLeft.getChildrenUnmodifiable().getFirst();
        GridPane calendarGrid = (GridPane) parent.getChildren().get(1);

        calendarGrid.getChildren().forEach(node -> {
            if(node instanceof Button) {
                Button cell = (Button) node;
                cell.setText("");
            }
        });

        int daysInMonth = currentDate.lengthOfMonth();
        int dayOfMonth = 1;
        int startingDayOfWeek = currentDate.withDayOfMonth(1).getDayOfWeek().getValue();

        for (int i = 1; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 1 && j < startingDayOfWeek) {
                    continue;
                }
                if (dayOfMonth > daysInMonth) {
                    break;
                }

                Button cell = (Button) calendarGrid.getChildren().get(i * 7 + j - 1);
                cell.setText(String.valueOf(dayOfMonth));
                dayOfMonth++;
            }
        }
    }

    public void getCalenderToDos(Integer day, Integer month, Integer year) {
        LocalDate date = new LocalDate(year, month, day);
        Database.getCalenderToDos(userID, date);
    }

    public void actCategoryScene(ActionEvent actionEvent) {
        Categoryscreen categoryScene = new Categoryscreen();
        categoryScene.openCategory((Stage) btnCategoryScene.getScene().getWindow());
    }

    public void actToDoScene(ActionEvent actionEvent) {
    }

    public void actProfileScene(ActionEvent actionEvent) {
    }
}