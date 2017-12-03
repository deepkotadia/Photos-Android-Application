package model;

import java.io.*;
import java.util.*;

/**
 * Created by deepkotadia on 12/2/17.
 */

public class Album implements Serializable {

    /* Serialization stuff */
    public static final long serialVersionUID = 42L;

    private String albumName;
    private List<Photo> photos;


    /**
     * constructor for Album
     * @param albumName   name of the album
     * @return
     */
    public Album(String albumName) {
        this.albumName = albumName;
        photos = new ArrayList<Photo>();
    }


    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public List<Photo> getPhotos() {
        return photos;
    }


}
