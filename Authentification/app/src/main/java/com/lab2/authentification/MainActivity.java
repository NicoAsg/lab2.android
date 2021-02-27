package com.lab2.authentification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button auth = (Button)findViewById(R.id.buttonAuthenticate);
        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL url;
                HttpURLConnection urlConnection;
                try {
                    url = new URL("https://httpbin.org/basic-auth/bob/sympa");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                TextView login = findViewById(R.id.Login);
                                TextView pswd = findViewById(R.id.Password);
                                String basicAuth = "Basic " + Base64.encodeToString((login.getText() + ":" + pswd.getText()).getBytes(), Base64.NO_WRAP);
                                urlConnection.setRequestProperty ("Authorization", basicAuth);

                                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                                String s = readStream(in);
                                Log.i("JFL", s.toString());
                                JSONObject result = new JSONObject(s);
                                String res = result.getString("authenticated");
                                runOnUiThread(new ResultRunnable(res));
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            } finally {
                                urlConnection.disconnect();
                            }
                        }
                    }.start();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public static String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = buffer.readLine(); line != null; line =buffer.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
    @Override
    protected  void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }

    private class ResultRunnable implements Runnable {
        private final String res;

        public ResultRunnable(String res) {
            this.res = res;
        }

        @Override
        public void run() {
            TextView resultText = findViewById(R.id.result);

            if(res.equals("true"))
                resultText.setText("Hello bob");
            else
                resultText.setText("Invalid log");

        }
    }
}