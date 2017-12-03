package com.photos_android.photos_android43;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.*;
//import java.util.ArrayList;

public class UserHomepage extends AppCompatActivity {
    ListView testList;
    ArrayList<String> albums = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        albums.add("Deep");
        albums.add("Chinmoyi");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);
        testList = (ListView) findViewById(R.id.albumsList);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.activity_albums_list_view,
                R.id.textView,albums);
        testList.setAdapter(arrayAdapter);
    }
}
