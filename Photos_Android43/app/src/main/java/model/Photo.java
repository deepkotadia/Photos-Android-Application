package model;

import java.io.*;
import java.util.*;

/**
 * Created by deepkotadia on 12/2/17.
 */

public class Photo implements Serializable {

    /* Serialization stuff */
    public static final long serialVersionUID = 42L;

    private String photoPath;
    private String fileName;
    private List<String> personTags;
    private List<String> locationTags;


    /**
     * constructor for Photo
     * @param photoPath   path of photo on device
     * @return
     */
    public Photo(String photoPath) {
        this.photoPath = photoPath;
        this.fileName = photoPath.substring(photoPath.lastIndexOf('/')+1);
        personTags = new ArrayList<String>();
        locationTags = new ArrayList<String>();
    }


    public String getphotoPath() {
        return photoPath;
    }

    public String getfileName() {
        return fileName;
    }

    public List<String> getpersonTags() {
        return personTags;
    }

    public List<String> getlocationTags() {
        return locationTags;
    }

}
