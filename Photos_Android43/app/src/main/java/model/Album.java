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
    private Photo currentPhoto;


    /**
     * constructor for Album
     * @param albumName   name of the album
     * @return
     */
    public Album(String albumName) {
        this.albumName = albumName;
        photos = new ArrayList<Photo>();
    }

    /**
     * to add a photo to an album
     * @param photoPath  file path of the photo
     */
    public void addPhoto(String photoPath) {
        Photo newPhoto = new Photo(photoPath);
        photos.add(newPhoto);
    }

    /**
     * to remove a photo from a list of photos at a given index
     * @param photoIndex        index of the photo to remove
     */
    public void removePhoto(int photoIndex) {
        photos.remove(photoIndex);
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

    public Photo getcurrentPhoto() {
        return currentPhoto;
    }

    public void setcurrentPhoto(Photo currentPhoto) {
        this.currentPhoto = currentPhoto;
    }
}
