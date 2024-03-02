package com.example.simplydoneapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    private static Response apiCallBase(String url) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            return client.newCall(request).execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private static List<Task> apiCallMultipleTasksObjects(String url) {
        try {
            Response response = apiCallBase(url);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();

                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                Type listType = new TypeToken<List<Task>>() {}.getType();

                List<Task> taskList = gson.fromJson(responseBody, listType);

                return taskList;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    private static List<Category> apiCallMultipleCategoriesObjects(String url) {
        try {
            Response response = apiCallBase(url);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();

                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                Type listType = new TypeToken<List<Category>>() {}.getType();

                List<Category> CategoryList = gson.fromJson(responseBody, listType);

                return CategoryList;
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

        return apiCallMultipleTasksObjects(apiUrl);
    }

    public static List<Task> getAllOpenToDosToday(int userid) {
        String apiUrl = apiBaseUrl + "todo/get_all_open_todos_today?"
                + "userid=" + userid;

        return apiCallMultipleTasksObjects(apiUrl);
    }

    public static List<Task> getAllOpenToDosOther(int userid) {
        String apiUrl = apiBaseUrl + "todo/get_all_open_other_todos?"
                + "userid=" + userid;

        return apiCallMultipleTasksObjects(apiUrl);
    }
    public static int updateCurrentTodo(String category, String title, String description, LocalDate dueday, String priority, int todoid) {
        //ToDo GetCategory Methode für CategoryID
        int categoryid = 1;

        String apiUrl = apiBaseUrl + "todo/update_todo?"
                + "category=" + categoryid
                + "&title=" + title
                + "&description=" + description
                + "&dueday=" + dueday
                + "&priority=" + priority
                + "&todoid=" + todoid;

        JsonObject apiResponse = apiCallSingleObject(apiUrl);

        if(apiResponse != null) {
            return apiResponse.get("affectedRows").getAsInt();
        }
        return -1;
    }
    public static int closeTodo(int todoid) {
        String apiUrl = apiBaseUrl + "todo/close_todo?"
                + "todoid=" + todoid;

        JsonObject apiResponse = apiCallSingleObject(apiUrl);

        if(apiResponse != null) {
            return apiResponse.get("affectedRows").getAsInt();
        }
        return -1;
    }

    public static int deleteTodo(int todoid) {
        String apiUrl = apiBaseUrl + "todo/delete_todo?"
                + "todoid=" + todoid;

        JsonObject apiResponse = apiCallSingleObject(apiUrl);

        if(apiResponse != null) {
            return apiResponse.get("affectedRows").getAsInt();
        }
        return -1;
    }

    public static List<Task> getCalenderToDos(int userid, LocalDate date) {
        String apiUrl = apiBaseUrl + "todo/get_calender_todos?"
                + "userid=" + userid
                + "&date=" + date;

        return apiCallMultipleTasksObjects(apiUrl);
    }

    public static int addNewCategory(int userid, String title) {
        String apiUrl = apiBaseUrl + "category/add_category?"
                + "userid="         + userid
                + "&title="         + title;

        JsonObject apiResponse = apiCallSingleObject(apiUrl);

        if(apiResponse != null) {
            return apiResponse.get("CategoryID").getAsInt();
        }
        return -1;
    }

    public static List<Category> getAllCategories(int userid) {
        String apiUrl = apiBaseUrl + "category/get_all_categories?"
                + "userid=" + userid;

        return apiCallMultipleCategoriesObjects(apiUrl);
    }
}
