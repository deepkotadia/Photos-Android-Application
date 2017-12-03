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

    /**
     * serialize
     * Serialize the userdata and write it in albums.dat file
     *
     * @param userdata - all all albums, photos, and tags info to be serialized
     */
    public static void serialize(PhotoAlbumManager userdata) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("albums.dat"));
        oos.writeObject(userdata);
        oos.close();
    }


    /**
     * deserialize
     * Read the userdata from albums.dat file and deserialize it
     *
     * @return userdata - albums of user
     */
    public static PhotoAlbumManager deserialize() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("albums.dat"));
        PhotoAlbumManager userdata = (PhotoAlbumManager) ois.readObject();
        ois.close();
        return userdata;
    }

}
