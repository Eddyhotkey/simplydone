package com.example.simplydoneapp;

import com.google.gson.annotations.Expose;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
    public String DateFaelligkeitsdatum;
    @Expose
    public int CategoryID;
    @Expose
    public String Priorität;
    @Expose
    public String Status;


    public Task(int userID, String title, String description, String dueDay, int categoryID, String priority) {
        this.UserID = userID;
        this.Titel = title;
        this.Beschreibung = description;
        this.Fälligkeitsdatum = dueDay;
        this.CategoryID = categoryID;
        this.Priorität = priority;
        this.Status = "open";
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

    public int getCategory() {
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

    public void setFälligkeitsdatum(String dueDay) {
        this.Fälligkeitsdatum = dueDay;
    }

    public void setCategory(int category) {
        this.CategoryID = category;
    }

    public void setPriority(String priority) {
        this.Priorität = priority;
    }

    public String getDateFaelligkeitsdatum() {
        return DateFaelligkeitsdatum;
    }

    public void setDateFaelligkeitsdatum(String Fälligkeitsdatum) {
        if(Fälligkeitsdatum == null) { return; }
        if(Fälligkeitsdatum.length() > 10) {
            Instant instant = Instant.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(Fälligkeitsdatum));
            LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = date.format(outputFormatter);
            DateFaelligkeitsdatum = formattedDate;
        } else {
            DateFaelligkeitsdatum = Fälligkeitsdatum;
        }
    }
}
