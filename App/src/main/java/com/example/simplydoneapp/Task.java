package com.example.simplydoneapp;

import com.google.gson.annotations.Expose;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Task {
    @Expose
    public int ToDoID;
    @Expose
    public int UserID;
    @Expose
    public String Titel;
    @Expose
    public String Beschreibung;
    @Expose
    public String Fälligkeitsdatum;
    @Expose
    public String CategoryID;
    @Expose
    public String Priorität;
    @Expose
    public String Status;


    public Task(int userID, String title, String description, String dueDay, String category, String priority) {
        this.UserID = userID;
        this.Titel = title;
        this.Beschreibung = description;
        this.Fälligkeitsdatum = dueDay;
        this.CategoryID = category;
        this.Priorität = priority;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public int getTodoID() {
        return ToDoID;
    }

    public void setTodoID(int todoID) {
        this.ToDoID = todoID;
    }

    public int getUserID() {
        return UserID;
    }

    public String getTitle() {
        return Titel;
    }

    public String getDescription() {
        return Beschreibung;
    }

    public String getDueDay() {
        return Fälligkeitsdatum;
    }

    public String getCategory() {
        return CategoryID;
    }

    public String getPriority() {
        return Priorität;
    }

    public void setTitle(String title) {
        this.Titel = title;
    }

    public void setDescription(String description) {
        this.Beschreibung = description;
    }

    public void String(String dueDay) {
        this.Fälligkeitsdatum = dueDay;
    }

    public void setCategory(String category) {
        this.CategoryID = category;
    }

    public void setPriority(String priority) {
        this.Priorität = priority;
    }
}
