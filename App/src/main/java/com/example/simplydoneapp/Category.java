package com.example.simplydoneapp;

import com.google.gson.annotations.Expose;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class Category {
    @Expose
    public int CategoryID;
    @Expose
    public String Kategoriename;

    public Category(int categoryID, String kategoriename) {
        CategoryID = categoryID;
        Kategoriename = kategoriename;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public String getKategoriename() {
        return Kategoriename;
    }

    public void setKategoriename(String kategoriename) {
        Kategoriename = kategoriename;
    }
}
