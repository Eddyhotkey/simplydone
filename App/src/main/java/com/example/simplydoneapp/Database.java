package com.example.simplydoneapp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.time.LocalDate;
import java.util.Objects;

public  class Database {
    static String apiBaseUrl = "http:localhost:1337/";
    static OkHttpClient client = new OkHttpClient();

    public static void setNewToDo(int userid, String title, String description, LocalDate dueday, String category, String priority) {
        String apiUrl = apiBaseUrl + "todo/add_todo?"
                + "userid="         + userid
                + "&title="         + title
                + "&description="   + description
                + "&dueday="        + dueday
                + "&category="      + category
                + "&priority="      + priority;
        try {
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
