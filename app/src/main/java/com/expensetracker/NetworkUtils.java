package com.expensetracker;

import android.os.AsyncTask;
import android.util.Log;

import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.Model.AsyncData;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.Scanner;

/**
 * Created by user on 09-07-2017.
 */

public class NetworkUtils extends AsyncTask<AsyncData, String, String> {

    AsyncResponse asyncResponse;

    public NetworkUtils(AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected String doInBackground(AsyncData... params) {


        try {

            HttpURLConnection con = (HttpURLConnection) params[0].getUrl().openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(params[0].getData().toString());
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
            Log.e("error","error",e);
        }

        return null;


    }

    @Override
    protected void onPostExecute(String data) {
        asyncResponse.sendData(data);
    }


}
