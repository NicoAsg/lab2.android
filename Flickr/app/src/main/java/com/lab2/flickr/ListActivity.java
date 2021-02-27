package com.lab2.flickr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;
import java.util.Vector;

public class ListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listImages = (ListView) findViewById(R.id.list);
        MyAdapter adapter = new MyAdapter();
        listImages.setAdapter(adapter);
        new AsyncFlickrJSONDataForList(ListActivity.this, adapter).execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=cats&format=json");
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
    protected void onStop() {
        super.onStop();
    }

    public class MyAdapter extends BaseAdapter {
        protected Vector<String> urls;
        public MyAdapter() {
            this.urls = new Vector<String>();
        }
        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public Object getItem(int position) {
            return urls.elementAt(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i("JFL", "TODO");
            return null;
        }

        public void dd(String url) {
            urls.addElement(url);

            Log.i("JFL", "Adding to adapter url : " + url);
        }
    }
}