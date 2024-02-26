package com.example.simplydoneapp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

public  class Database {
    static String apiBaseUrl = "http:localhost:1337/";
    static OkHttpClient client = new OkHttpClient();

    private static JsonObject apiCallSingleObject(String url) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();

                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
                return jsonObject;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private static List<Task> apiCallMultipleObjects(String url) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();

                // Definieren Sie den Typ für die Deserialisierung
                Type listType = new TypeToken<List<Task>>() {}.getType();

                Gson gson = new Gson();
                return gson.fromJson(responseBody, listType);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static int setNewToDo(int userid, String category, String title, String description, LocalDate dueday, String priority) {
        String apiUrl = apiBaseUrl + "todo/add_todo?"
                + "userid="         + userid
                + "&title="         + title
                + "&description="   + description
                + "&dueday="        + dueday
                + "&category="      + category
                + "&priority="      + priority;

        JsonObject apiResponse = apiCallSingleObject(apiUrl);

        if(apiResponse != null) {
            return apiResponse.get("ToDoID").getAsInt();
        }
        return -1;
    }
    public static List<Task> getAllOpenToDos(int userid) {
        String apiUrl = apiBaseUrl + "todo/get_all_open_todos?"
                + "userid=" + userid;

        return apiCallMultipleObjects(apiUrl);
    }
}
