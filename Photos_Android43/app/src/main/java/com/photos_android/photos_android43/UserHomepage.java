package com.photos_android.photos_android43;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.XmlRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.content.DialogInterface;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import model.Album;
import model.MySpinner;
import model.PhotoAlbumManager;


public class UserHomepage extends AppCompatActivity {

    private FloatingActionButton addAlbumBtn;
    private MySpinner spinner;
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
                                String albumName = albumNamefromDialog.getText().toString();
                                //create an album and add it to the list of albums
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

        spinner = (MySpinner) findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> itemAdapter = ArrayAdapter.createFromResource(this, R.array.album_options,android.R.layout.simple_spinner_item);
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(itemAdapter);
        spinner.setVisibility(View.INVISIBLE); //initially, the spinner does not appear
        testList.setLongClickable(true);

        /*
         * Long Click on Album Name to Rename or Delete Album
         */
        testList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                spinner.setVisibility(View.VISIBLE);

                final int indexOfAlbum = i;

                spinner.setOnItemSelectedEvenIfUnchangedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        if(i == 1) { //rename selected

                            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                            View mView = layoutInflaterAndroid.inflate(R.layout.activity_create_album_dialogbx, null);
                            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                            alertDialogBuilderUserInput.setView(mView);
                            TextView title = (TextView) mView.findViewById(R.id.title);
                            title.setText("Rename Album");
                            final EditText albumNameinDialog = (EditText) mView.findViewById(R.id.userInputDialog);
                            albumNameinDialog.setText(manager.getAlbums().get(indexOfAlbum).getAlbumName());
                            alertDialogBuilderUserInput
                                    .setCancelable(false)
                                    .setPositiveButton("Rename", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogBox, int id) {
                                            String newname = albumNameinDialog.getText().toString();
                                            manager.getAlbums().get(indexOfAlbum).setAlbumName(newname);

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

                                            Toast.makeText(UserHomepage.this, "Album Successfully Renamed", Toast.LENGTH_SHORT).show();

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

                        else if(i == 2) { //delete selected
                            manager.removeAlbum(indexOfAlbum);
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
                            Toast.makeText(UserHomepage.this, "Album Successfully Deleted", Toast.LENGTH_SHORT).show();
                        }

                        spinner.setVisibility(View.INVISIBLE); //hide the spinner
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                return true;
            }
        });


        /*
         * Single Click on Album Name to Open an Album
         */
        testList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //set onclicked album as current album
                Album album = manager.getAlbums().get(i);
                manager.setcurrentAlbum(album);

                //launch the SingleAlbumPage with photos for current album
                Intent singlealbum = new Intent(UserHomepage.this, SingleAlbumPage.class);
                startActivity(singlealbum);

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
