package com.expensetracker.Model;

import org.json.JSONObject;

import java.net.URL;

/**
 * Created by user on 09-07-2017.
 */

public class AsyncData {

    private URL url;
    private JSONObject data;

    public AsyncData(URL url, JSONObject data) {
        this.url = url;
        this.data = data;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
