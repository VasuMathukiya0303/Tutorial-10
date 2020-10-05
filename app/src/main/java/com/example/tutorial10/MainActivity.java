package com.example.tutorial10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    CustomAdapter adapter;
    ListView lstData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstData = findViewById(R.id.lstData);
        new MyAsyncTask().execute();

        lstData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,UserDetail.class);
                intent.putExtra("userPosition",i);
                startActivity(intent);
            }
        });
    }

    class MyAsyncTask extends AsyncTask{
        ProgressDialog dialog;
        StringBuilder builder;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                URL url = new URL(MyUtil.URL_USERS);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(reader);

                builder = new StringBuilder();
                String temp = "";
                while ((temp = br.readLine()) != null){
                    builder.append(temp);
                }

                Log.i("jsonString",builder.toString());
                MyUtil.jsonArray = new JSONArray(builder.toString());

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            adapter = new CustomAdapter(MainActivity.this,MyUtil.jsonArray);
            lstData.setAdapter(adapter);
            if (dialog.isShowing()) dialog.dismiss();
        }
    }
}