package com.example.simplydoneapp;

import java.time.LocalDate;
import java.util.Date;

public class Task {
    int userID;
    String title;
    String description;
    LocalDate dueDay;
    String category;
    String priority;

    public Task(int userID, String title, String description, LocalDate dueDay, String category, String priority) {
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.dueDay = dueDay;
        this.category = category;
        this.priority = priority;
    }

    public int getUserID() {
        return userID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDay() {
        return dueDay;
    }

    public String getCategory() {
        return category;
    }

    public String getPriority() {
        return priority;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDay(LocalDate dueDay) {
        this.dueDay = dueDay;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
