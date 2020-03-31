package com.developersbreach.xyzreader.repository.network;

import android.net.Uri;
import android.util.Log;


import com.developersbreach.xyzreader.repository.ArticleRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


/**
 * This class builds standard response by building a URI for starting a response. And the final
 * Built URI is returned by {@link #uriBuilder()}
 */
public class ResponseBuilder {

    // Strings which hold path to create a standard URI object.
    //https://raw.githubusercontent.com/SuperAwesomeness/XYZReader/master/data.json
//    private static final String SCHEME_AUTHORITY = "https://d17h27t6h515a5.cloudfront.net";
//    private static final String APPEND_PATH_TOPHER = "topher";
//    private static final String APPEND_PATH_YEAR = "2017";
//    private static final String APPEND_PATH_MONTH = "March";
//    private static final String APPEND_PATH_ID = "58c5d68f_xyz-reader";
//    private static final String APPEND_PATH_TYPE = "xyz-reader.json";

    private static final String SCHEME_AUTHORITY = "https://raw.githubusercontent.com";
    private static final String APPEND_PATH_TOPHER = "SuperAwesomeness";
    private static final String APPEND_PATH_YEAR = "XYZReader";
    private static final String APPEND_PATH_MONTH = "master";
    private static final String APPEND_PATH_ID = "data.json";

    /**
     * @return returns a string URL created to make JSON request in background see class
     * implemented this method inside {@link ArticleRepository#refreshData()}.
     * @throws IOException if an error is thrown when executing any of the statements.
     */
    public static String startResponse() throws IOException {
        String uriString = uriBuilder();
        URL requestUrl = createUrl(uriString);
        return getResponseFromHttpUrl(requestUrl);
    }

    /**
     * Builds Uri used to fetch articles data from the server.
     *
     * @return The String to use to query the articles data from the server.
     * <p>
     * https://d17h27t6h515a5.cloudfront.net/topher/2017/March/58c5d68f_xyz-reader/xyz-reader.json
     */
    private static String uriBuilder() {
        Uri baseUri = Uri.parse("https://d17h27t6h515a5.cloudfront.net");
        // Constructs a new Builder.
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder
                .appendPath("topher")
                .appendPath("2017")
                .appendPath("March")
                .appendPath("58c5d68f_xyz-reader")
                .appendPath("xyz-reader.json");
        // Returns a string representation of the object.
        return uriBuilder.build().toString();
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response, null if no response
     * @throws IOException Related to network and stream reading
     */
    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("createUrl", "Problem building the URL ", e);
        }
        return url;
    }
}
