package com.nickwelna.booklisting;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public final class QueryUtils {

    private QueryUtils() {}

    public static ArrayList<Book> extractBooks(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {

            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {

            Log.e("QueryUtils", "Problem making http request", e);

        }

        ArrayList<Book> books = new ArrayList<>();

        try {

            JSONObject response = new JSONObject(jsonResponse);
            JSONArray items = response.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {

                JSONObject bookJson = items.getJSONObject(i);
                JSONObject volumeInfo = bookJson.getJSONObject("volumeInfo");

                String title = volumeInfo.getString("title");
                StringBuilder authorNames = new StringBuilder();
                JSONArray authors = volumeInfo.getJSONArray("authors");

                for(int j = 0; j < authors.length(); j++) {

                    String author = authors.getString(j);
                    authorNames.append(author);
                    if (j != authors.length() - 1) {

                        authorNames.append(", ");

                    }

                }

                String bookUrl = volumeInfo.getString("infoLink");

                Book book = new Book(title, authorNames.toString(), bookUrl);
                books.add(book);

            }

        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);

        }

        return books;

    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {

        URL url = null;
        try {

            url = new URL(stringUrl);

        } catch (MalformedURLException e) {

            Log.e("QueryUtils", "Error with creating URL ", e);

        }
        return url;

    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {

            return jsonResponse;

        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {

                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {

                Log.e("QueryUtils", "Error response code: " + urlConnection.getResponseCode());

            }
        } catch (IOException e) {

            Log.e("QueryUtils", "Problem retrieving the earthquake JSON results.", e);

        } finally {

            if (urlConnection != null) {

                urlConnection.disconnect();

            }
            if (inputStream != null) {

                inputStream.close();

            }

        }
        return jsonResponse;

    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {

                output.append(line);
                line = reader.readLine();

            }

        }
        return output.toString();

    }

}