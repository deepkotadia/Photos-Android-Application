package model;

import java.io.*;
import java.util.*;

/**
 * Created by deepkotadia on 12/2/17.
 */

public class PhotoAlbumManager implements Serializable {

    /* Serialization stuff */
    public static final long serialVersionUID = 42L;

    public List<Album> albums;

    /**
     * PhotoAlbumManager
     *
     * Constructor that creates arraylist of photos that user has
     *
     */
    public PhotoAlbumManager() {
        albums = new ArrayList<Album>();
    }


    public List<Album> getAlbums() {
        return albums;
    }

}
