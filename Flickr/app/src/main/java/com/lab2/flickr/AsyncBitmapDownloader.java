package com.lab2.flickr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {
    private AppCompatActivity mainActivity;
    public AsyncBitmapDownloader(AppCompatActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    @Override
    protected Bitmap doInBackground(String... url) {
        try {
            System.out.println(url[0]);
            URL _url = new URL(cleanLink(url[0]));
            HttpsURLConnection urlConnection = (HttpsURLConnection) _url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Bitmap bm = BitmapFactory.decodeStream(in);

            return bm;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bm) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageView image = mainActivity.findViewById(R.id.image);
                image.setImageBitmap(bm);
            }
        });
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
