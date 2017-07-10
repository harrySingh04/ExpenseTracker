package com.expensetracker;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by user on 05-07-2017.
 */

public class RegisterToken {


    public static final String TAG = "";
    public static final String GITHUB_BASE_URL = "";
    public static final String REGISTRATION_KEY = "registration_token";


    public static URL makeURL(String searchQuery, String sortBy, String api_key) {
        Uri uri = Uri.parse(GITHUB_BASE_URL).buildUpon().appendQueryParameter(REGISTRATION_KEY, searchQuery).build();

        URL url = null;

        try {
            String urlString = uri.toString();
            Log.d(TAG, "URL:" + urlString);
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getReponseFromHttpUrl(URL url) {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {



            InputStream in = urlConnection.getInputStream();
            Scanner input = new Scanner(in);

            input.useDelimiter("\\A");
            return (input.hasNext()) ? input.next() : null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return null;
    }




}
