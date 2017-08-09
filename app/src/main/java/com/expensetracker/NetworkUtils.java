package com.expensetracker;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.Model.AsyncData;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by user on 09-07-2017.
 */

public class NetworkUtils extends AsyncTaskLoader<String> {

    AsyncResponse asyncResponse;
   AsyncData asyncData;

    public NetworkUtils(Context context, AsyncResponse asyncResponse,AsyncData asyncData) {
        super(context);
        this.asyncResponse = asyncResponse;
        this.asyncData = asyncData;
    }


    @Override
    protected void onStartLoading() {

    }

    @Override
    public String loadInBackground() {

        try {

            HttpURLConnection con = (HttpURLConnection) asyncData.getUrl().openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(asyncData.getData().toString());
            wr.flush();

//display what returns the POST request

            StringBuilder sb = new StringBuilder();
            int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {

                InputStream in = con.getInputStream();
                Scanner input = new Scanner(in);
                String data = "";
                while (input.hasNextLine()) {
                    data += input.nextLine();
                }
                return data;
            } else {
            }

        } catch (Exception e) {
            Log.e("error", "error", e);
        }

        return null;


    }


    @Override
    public void deliverResult(String data) {
        asyncResponse.sendData(data);

    }


}
