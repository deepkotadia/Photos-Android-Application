package com.photos_android.photos_android43;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.ImageAdapter;
import model.ImageAdapter1;
import model.Photo;
import model.PhotoAlbumManager;

public class SingleAlbumPage extends AppCompatActivity {

    private static List<Photo> photosInAlbum = new ArrayList<Photo>();
    private FloatingActionButton addPhotoBtn;
    private static final int READ_REQUEST_CODE = 42;
    private ImageAdapter1 imageAdapter;
    private Button deletePhotoBtn, movePhotoBtn;
    GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_album_page);

        TextView AlbumNameText = (TextView) findViewById(R.id.albumName);
        AlbumNameText.setText(UserHomepage.manager.getcurrentAlbum().getAlbumName());

        populatePhotosList();

        gridview = (GridView) findViewById(R.id.gridView1);
        imageAdapter = new ImageAdapter1(this, photosInAlbum);
        gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(SingleAlbumPage.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });


        addPhotoBtn = (FloatingActionButton) findViewById(R.id.addPhotoBtn);
        addPhotoBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                performFileSearch();

            }

            /**
             * Fires an intent to spin up the "file chooser" UI and select an image.
             */
            public void performFileSearch() {

                // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
                // browser.
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

                // Filter to only show results that can be "opened", such as a
                // file (as opposed to a list of contacts or timezones)
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                // Filter to show only images, using the image MIME data type.
                // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
                // To search for all documents available via installed storage providers,
                // it would be "*/*".
                intent.setType("image/*");

                startActivityForResult(intent, READ_REQUEST_CODE);
            }

        });

        deletePhotoBtn = (Button) findViewById(R.id.deletePhotoBtn);
        movePhotoBtn = (Button) findViewById(R.id.movePhotoBtn);
        deletePhotoBtn.setVisibility(View.INVISIBLE);
        movePhotoBtn.setVisibility(View.INVISIBLE);
        gridview.setLongClickable(true);

        /*
         * Long Click on Photo to Delete; Move Photo to different Album
         */
        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                deletePhotoBtn.setVisibility(View.VISIBLE);
                movePhotoBtn.setVisibility(View.VISIBLE);
                final int photoindex = i;

                deletePhotoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //remove photo at photoindex
                        UserHomepage.manager.getcurrentAlbum().removePhoto(photoindex);

                        Toast.makeText(SingleAlbumPage.this, "Photo Successfully Deleted", Toast.LENGTH_SHORT).show();

                        //serialize and refresh list
                        try {
                            PhotoAlbumManager.serialize(UserHomepage.manager);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        gridview = (GridView) findViewById(R.id.gridView1);
                        populatePhotosList();
                        imageAdapter.notifyDataSetChanged();
                        gridview.setAdapter(imageAdapter);
                        //refreshing ends here

                        deletePhotoBtn.setVisibility(View.INVISIBLE); //hide the delete button
                        movePhotoBtn.setVisibility(View.INVISIBLE); //hide the move button

                    }
                });

                movePhotoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //remove photo at photoindex from current album

                        //TODO
                        /*
                        UserHomepage.manager.getcurrentAlbum().removePhoto(photoindex);



                        Toast.makeText(SingleAlbumPage.this, "Photo Successfully Deleted", Toast.LENGTH_SHORT).show();

                        //serialize and refresh list
                        try {
                            PhotoAlbumManager.serialize(UserHomepage.manager);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        gridview = (GridView) findViewById(R.id.gridView1);
                        populatePhotosList();
                        imageAdapter.notifyDataSetChanged();
                        gridview.setAdapter(imageAdapter);
                        //refreshing ends here
                        */

                        deletePhotoBtn.setVisibility(View.INVISIBLE); //hide the delete button
                        movePhotoBtn.setVisibility(View.INVISIBLE); //hide the move button

                    }
                });

                return true;
            }
        });

        /*
         * Single Click on Photo to Open Display/Slideshow
         */

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                String image_uristr = uri.toString();

                for(int i = 0; i < photosInAlbum.size(); i++){
                    if(image_uristr.equals(photosInAlbum.get(i).getphotoPath())){ //duplicate photos in same album not allowed
                        Toast.makeText(getApplicationContext(), "This Photo Already Exists In Album", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                //At this point, we have the photopath, so add it in the current album
                UserHomepage.manager.getcurrentAlbum().addPhoto(image_uristr);

                //serialize and refresh list
                try {
                    PhotoAlbumManager.serialize(UserHomepage.manager);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                gridview = (GridView) findViewById(R.id.gridView1);
                populatePhotosList();
                imageAdapter.notifyDataSetChanged();
                gridview.setAdapter(imageAdapter);
                //refreshing ends here
            }
        }
    }


    /**
     * Populates the list of Photos inside the Album
     */
    private static void populatePhotosList() {
        photosInAlbum.clear();

        for(int i = 0; i < UserHomepage.manager.getcurrentAlbum().getPhotos().size(); i++) {
            photosInAlbum.add(UserHomepage.manager.getcurrentAlbum().getPhotos().get(i));
        }
    }

}
