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

    public void addPersonTag(String personTag){
        this.personTags.add(personTag.toLowerCase());
    }

    public void removePersonTag(String personTag){
        this.personTags.remove(personTag.toLowerCase());
    }

    public void addLocationTag(String locationTag){
        this.locationTags.add(locationTag.toLowerCase());
    }

    public void removeLocationTag(String locationTag){
        this.locationTags.remove(locationTag.toLowerCase());
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }

        if(!(obj instanceof Photo)){
            return false;
        }

        Photo photo = (Photo) obj;
        return photo.getphotoPath().equals(this.getphotoPath());
    }

    @Override
    public int hashCode(){
        return 17 * 11 + this.getphotoPath().hashCode();
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
