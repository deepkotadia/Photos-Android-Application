package model;

import android.content.Context;

import com.photos_android.photos_android43.UserHomepage;

import java.io.*;
import java.util.*;

/**
 * Created by deepkotadia on 12/2/17.
 */

public class PhotoAlbumManager implements Serializable {

    /* Serialization stuff */
    public static final long serialVersionUID = 42L;

    private List<Album> albums;
    private Album currentAlbum;

    /**
     * PhotoAlbumManager
     *
     * Constructor that creates arraylist of photos that user has
     *
     */
    public PhotoAlbumManager() {
        albums = new ArrayList<Album>();
    }

    /**
     * function to add an album by passing an album object
     * @param album   Album object
     */
    public void addAlbum(Album album) {
        albums.add(album);
    }

    /**
     * remove an album from the user's list
     * @param index    index of album to be removed
     */
    public void removeAlbum(int index) {
        albums.remove(index);
    }

    public List<Photo> getPhotosWithTags(List<String> locationTags, List<String> personTags){
        Set<Photo> resultSet = new HashSet<Photo>();
        List<Photo> resultList = new ArrayList<Photo>();

        for(Album album : this.albums){
            for(Photo photo : album.getPhotos()){
                boolean alreadyAdded = false;

                for(String searchLocationTag : locationTags) {
                    for(String photoLocationTag : photo.getlocationTags()) {
                        if (photoLocationTag.toLowerCase().contains(searchLocationTag.toLowerCase())){
                            resultSet.add(photo);
                            alreadyAdded = true;
                            break;
                        }
                    }
                    if(alreadyAdded){
                        break;
                    }
                }
                if(!alreadyAdded) {
                    for (String searchPersonTag : personTags) {
                        for(String photoPersonTag : photo.getpersonTags()) {
                            if (photoPersonTag.toLowerCase().contains(searchPersonTag.toLowerCase())) {
                                resultSet.add(photo);
                                break;
                            }
                        }
                        if(alreadyAdded){
                            break;
                        }
                    }
                }
            }
        }

        resultList.addAll(resultSet);
        return resultList;
    }

    /**
     * serialize
     * Serialize the userdata and write it in albums.dat file
     *
     * @param userdata - all all albums, photos, and tags info to be serialized
     */
    public static void serialize(PhotoAlbumManager userdata) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/data/data/com.photos_android.photos_android43/files/albums.dat"));
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
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/data/data/com.photos_android.photos_android43/files/albums.dat"));
        PhotoAlbumManager userdata = (PhotoAlbumManager) ois.readObject();
        ois.close();
        return userdata;
    }

    /**
     * getter for the list of albums in app
     * @return List<Album>
     */
    public List<Album> getAlbums() {
        return albums;
    }

    /**
     * getter for the currently open album in app
     * @return Album
     */
    public Album getcurrentAlbum() {
        return currentAlbum;
    }

    /**
     * setter for the currently open album in app
     * @param currentAlbum       setting current album
     */
    public void setcurrentAlbum(Album currentAlbum) {
        this.currentAlbum = currentAlbum;
    }
}
