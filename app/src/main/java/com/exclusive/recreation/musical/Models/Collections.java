package com.exclusive.recreation.musical.Models;

import java.util.ArrayList;

public class Collections {

    String collectionName;
    String collectionImage;
    ArrayList<ItemSong> songs;

    public Collections(String collectionName, String collectionImage, ArrayList<ItemSong> songs) {
        this.collectionName = collectionName;
        this.collectionImage = collectionImage;
        this.songs = songs;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionImage() {
        return collectionImage;
    }

    public void setCollectionImage(String collectionImage) {
        this.collectionImage = collectionImage;
    }

    public ArrayList<ItemSong> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<ItemSong> songs) {
        this.songs = songs;
    }
}
