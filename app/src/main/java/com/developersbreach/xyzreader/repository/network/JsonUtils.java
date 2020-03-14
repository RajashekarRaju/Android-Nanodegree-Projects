package com.developersbreach.xyzreader.repository.network;

import android.util.Log;

import com.developersbreach.xyzreader.repository.ArticleRepository;
import com.developersbreach.xyzreader.repository.database.entity.ArticleEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * This class returns data for articles which parses JSON data to show.
 * This code implements the parsing from URL below
 * https://d17h27t6h515a5.cloudfront.net/topher/2017/March/58c5d68f_xyz-reader/xyz-reader.json
 * <p>
 * We perform this parsing operations in background thread using executors.
 */
public class JsonUtils {

    // These values are parsed and declared here to re-use without changes.
    private static final String ARTICLE_ID = "id";
    private static final String ARTICLE_TITLE = "title";
    private static final String ARTICLE_AUTHOR = "author";
    private static final String ARTICLE_BODY = "body";
    private static final String ARTICLE_THUMBNAIL = "thumb";
    private static final String ARTICLE_PUBLISHED_DATE = "published_date";

    /**
     * @param json takes as a String parameter.
     * @return list of articles {@link ArticleEntity} that has been built up from parsing a JSON
     * response in background database thread see {@link ArticleRepository#refreshData()}
     */
    public static List<ArticleEntity> fetchArticleJsonData(String json) {

        // Create a new ArrayList for adding articles into list.
        List<ArticleEntity> articleList = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONArray from the json response string.
            JSONArray baseJsonArray = new JSONArray(json);
            // Loop inside each objects of array
            for (int i = 0; i < baseJsonArray.length(); i++) {
                JSONObject baseJsonObject = baseJsonArray.getJSONObject(i);

                // Extract the value for the key called "id"
                int id = 0;
                if (baseJsonObject.has(ARTICLE_ID)) {
                    id = baseJsonObject.getInt(ARTICLE_ID);
                }

                // Extract the value for the key called "title"
                String title = "";
                if (baseJsonObject.has(ARTICLE_TITLE)) {
                    title = baseJsonObject.getString(ARTICLE_TITLE);
                }

                // Extract the value for the key called "author"
                String authorName = "";
                if (baseJsonObject.has(ARTICLE_AUTHOR)) {
                    authorName = baseJsonObject.getString(ARTICLE_AUTHOR);
                }

                // Extract the value for the key called "body"
                String body = "";
                if (baseJsonObject.has(ARTICLE_BODY)) {
                    body = baseJsonObject.getString(ARTICLE_BODY);
                }

                // Extract the value for the key called "thumb"
                String thumbnail = "";
                if (baseJsonObject.has(ARTICLE_THUMBNAIL)) {
                    thumbnail = baseJsonObject.getString(ARTICLE_THUMBNAIL);
                }

                // Extract the value for the key called "published_date"
                String publishedDate = "";
                if (baseJsonObject.has(ARTICLE_PUBLISHED_DATE)) {
                    publishedDate = baseJsonObject.getString(ARTICLE_PUBLISHED_DATE);
                }

                // Create new ArticleEntity object with their properties from response.
                ArticleEntity articleEntity = new ArticleEntity(id, title, authorName, body, thumbnail,
                        publishedDate);
                // Add each object to created ArrayList of ArticleEntity.
                articleList.add(articleEntity);
            }

        } catch (Exception e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(JsonUtils.class.getSimpleName(), "Problem parsing fetchArticleJsonData results", e);
        }
        // Return the list of articles.
        return articleList;
    }
}
