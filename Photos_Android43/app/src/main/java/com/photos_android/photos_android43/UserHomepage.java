package com.photos_android.photos_android43;

import android.content.Context;
import android.support.annotation.XmlRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.content.DialogInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import model.Album;
import model.PhotoAlbumManager;


public class UserHomepage extends AppCompatActivity {

    private FloatingActionButton addAlbumBtn;
    final Context c = this;
    //final EditText albumNameFromDialogBox = new EditText(this);
    String albumName;

    public static PhotoAlbumManager manager = new PhotoAlbumManager();
    File albumsfile = new File("/data/data/com.photos_android.photos_android43/files/albums.dat");
    ListView testList;
    private static List<String> albums = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;
    //private static List<String> albums = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //albums.add("Deep");
        //albums.add("Chinmoyi");

        try {
            manager = PhotoAlbumManager.deserialize();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);


        /*
        When app starts for first time, albums.dat will not exist
        so need to create it
         */
        if(!albumsfile.exists()) {
            Context context = this;
            File file = new File(context.getFilesDir(), "albums.dat");
            try {
                file.createNewFile();
            } catch (IOException e) {

            }
        }

        populateAlbumList();
        testList = (ListView) findViewById(R.id.albumsList);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_albums_list_view, R.id.textView, albums);
        testList.setAdapter(arrayAdapter);


        addAlbumBtn = (FloatingActionButton) findViewById(R.id.addAlbumBtn);
        addAlbumBtn.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.activity_create_album_dialogbx, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                final EditText albumNamefromDialog = (EditText) mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                //create an album and add it to the list of albums
                                //albumNameFromDialogBox = (EditText) findViewById(R.id.userInputDialog);
                                albumName = albumNamefromDialog.getText().toString();
                                Album newAlbum = new Album(albumName);
                                manager.addAlbum(newAlbum);

                                //serialize and refresh list
                                try {
                                    PhotoAlbumManager.serialize(manager);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                testList = (ListView) findViewById(R.id.albumsList);
                                populateAlbumList();
                                arrayAdapter.notifyDataSetChanged();
                                testList.setAdapter(arrayAdapter);
                                //refreshing ends here

                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });

    }

    /**
     * Populates the list of Albums for the user
     */
    private static void populateAlbumList() {
        albums.clear();

        for(int i = 0; i < manager.getAlbums().size(); i++) {
            albums.add(manager.getAlbums().get(i).getAlbumName());
        }
    }

}
