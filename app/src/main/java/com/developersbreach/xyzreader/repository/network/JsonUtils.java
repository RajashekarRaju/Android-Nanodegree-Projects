package com.developersbreach.xyzreader.repository.network;

import android.util.Log;

import com.developersbreach.xyzreader.repository.database.BookEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<BookEntity> fetchBookJsonData(String json) {

        List<BookEntity> bookList = new ArrayList<>();

        try {

            JSONArray baseJsonArray = new JSONArray(json);
            for (int i = 0; i < baseJsonArray.length(); i++) {

                JSONObject baseJsonObject = baseJsonArray.getJSONObject(i);

                int id = 0;
                if (baseJsonObject.has("id")) {
                    id = baseJsonObject.getInt("id");
                }

                String name = "";
                if (baseJsonObject.has("author")) {
                    name = baseJsonObject.getString("author");
                }

                BookEntity book = new BookEntity(id, name);
                bookList.add(book);
            }

        } catch (Exception e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("JsonUtils", "Problem parsing fetchBookJsonData results", e);
        }

        return bookList;
    }
}
