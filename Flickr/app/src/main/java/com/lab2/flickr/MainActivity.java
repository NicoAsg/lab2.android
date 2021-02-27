package com.lab2.flickr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getImage = (Button) findViewById(R.id.get_image);
        getImage.setOnClickListener(new GetImageOnClickListener());

        Button go_listActivity = (Button) findViewById(R.id.change_activity);
        go_listActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        });
    }
    @Override
    protected void onDestroy() {
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
    protected void onStop(){
        super.onStop();
    }

    class GetImageOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new AsyncFlickrJSONData(MainActivity.this).execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");
        }
    }
}