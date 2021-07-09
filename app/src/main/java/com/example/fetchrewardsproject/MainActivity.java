package com.example.fetchrewardsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RecyclerView listView;
    private ImageView logo;
    private String id, listId, name;
    public ArrayList<ItemData> itemDataArrayList;
    public ListViewAdapter listViewAdapter;
    public String url = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
    private int logoImage = R.drawable.fetchrewards_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView= (RecyclerView) findViewById(R.id.listView);
        logo = (ImageView) findViewById(R.id.logo);
        logo.setImageResource(logoImage);
        itemDataArrayList = new ArrayList<>();

        Thread dataThread = new Thread(new Runnable() {
            @Override
            public void run() {
                fetchData();
            }
        });
        dataThread.start();

    }

    private void displayListIdGroups(){
        listViewAdapter = new ListViewAdapter( MainActivity.this,itemDataArrayList);
        listView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        listView.setAdapter(listViewAdapter);
    }

    public void fetchData() {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i< response.length();i++){
                    try {
                        JSONObject responseObject = response.getJSONObject(i);
                        id = responseObject.getString("id");
                        listId = responseObject.getString("listId");
                        name = responseObject.getString("name");
                        itemDataArrayList.add(new ItemData(listId,id,name));
                        System.out.println("Item Added to List :"+ itemDataArrayList.get(i).getName());

                        displayListIdGroups();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }}, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "ERROR OCCURRED WHILE FETCHING DATA", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonArrayRequest);
    }
}