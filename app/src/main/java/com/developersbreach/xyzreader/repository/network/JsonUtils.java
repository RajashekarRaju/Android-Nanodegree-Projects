package com.developersbreach.xyzreader.repository.network;

import android.util.Log;

import com.developersbreach.xyzreader.repository.database.ArticleEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<ArticleEntity> fetchArticleJsonData(String json) {

        List<ArticleEntity> articleList = new ArrayList<>();

        try {

            JSONArray baseJsonArray = new JSONArray(json);
            for (int i = 0; i < baseJsonArray.length(); i++) {

                JSONObject baseJsonObject = baseJsonArray.getJSONObject(i);

                int id = 0;
                if (baseJsonObject.has("id")) {
                    id = baseJsonObject.getInt("id");
                }

                String authorName = "";
                if (baseJsonObject.has("author")) {
                    authorName = baseJsonObject.getString("author");
                }

                ArticleEntity articleEntity = new ArticleEntity(id, authorName);
                articleList.add(articleEntity);
            }

        } catch (Exception e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("JsonUtils", "Problem parsing fetchArticleJsonData results", e);
        }

        return articleList;
    }
}
