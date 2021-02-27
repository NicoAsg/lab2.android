package com.lab2.flickr;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class AsyncFlickrJSONDataForList extends AsyncTask<String, Void, JSONObject> {
    protected AppCompatActivity mainActivity;
    protected ListActivity.MyAdapter adapter;
    public AsyncFlickrJSONDataForList(AppCompatActivity mainActivity, ListActivity.MyAdapter adapter) {
        this.mainActivity = mainActivity;
        this.adapter = adapter;
    }
    @Override
    protected JSONObject doInBackground(String... url) {
        try{
            URL _url = new URL(url[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) _url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String s = readStream(in);
            s = s.substring(s.indexOf("jsonFlickrFeed") + 15, s.length() - 1); //Clean the JSONObject
            return new JSONObject(s);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(JSONObject result) {
        // Log.i("tag", result.toString());
        try {
            JSONArray urls = result.getJSONArray("items");
            String c_url;

            for (int i = 0; i < urls.length(); i++){
                c_url = urls.getJSONObject(i).getString("media");
                if(c_url != "") {
                    c_url = cleanLink(c_url);
                    adapter.dd(c_url);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is),5000);
        for (String line = buffer.readLine(); line != null; line =buffer.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }

    private String cleanLink(String url) {
        String r = new String();
        for (int i = url.indexOf("https"); i < url.indexOf("\"}"); i++) {
            if (url.charAt(i) == '\\' && url.charAt(i + 1) == '/');
            else
                r += url.charAt(i);
        }
        return r;
    }
}
