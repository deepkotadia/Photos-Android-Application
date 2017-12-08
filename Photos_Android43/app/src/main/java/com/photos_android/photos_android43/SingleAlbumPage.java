package com.photos_android.photos_android43;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;

import model.Photo;

public class SingleAlbumPage extends AppCompatActivity {

    private static List<Photo> photosInAlbum = new ArrayList<Photo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_album_page);

        populatePhotosList();

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
