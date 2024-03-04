package com.example.simplydoneapp;

import com.google.gson.annotations.Expose;

public class Sharing {
    @Expose
    public int SharingID;
    @Expose
    public int CategoryID;
    @Expose
    public int UserID;
    @Expose
    public int EmpfängerUserID;

    public Sharing(int sharingID, int categoryID, int userID, int empfängerUserID) {
        SharingID = sharingID;
        CategoryID = categoryID;
        UserID = userID;
        EmpfängerUserID = empfängerUserID;
    }

    public int getSharingID() {
        return SharingID;
    }

    public void setSharingID(int sharingID) {
        SharingID = sharingID;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getEmpfängerUserID() {
        return EmpfängerUserID;
    }

    public void setEmpfängerUserID(int empfängerUserID) {
        EmpfängerUserID = empfängerUserID;
    }
}
