package com.lab2.flickr;

import android.os.AsyncTask;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AsyncFlickrJSONData extends AsyncTask<String, Void, JSONObject> {
    protected AppCompatActivity mainActivity;
    public AsyncFlickrJSONData(AppCompatActivity mainActivity) {
        this.mainActivity = mainActivity;
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(JSONObject result) {
        // Log.i("tag", result.toString());

        try {
            JSONObject image1 = result.getJSONArray("items").getJSONObject(0);
            String image1_url = image1.getString("media");

            new AsyncBitmapDownloader(mainActivity).execute(image1_url);
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
}
