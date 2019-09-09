package com.exclusive.recreation.musical.Models;

import java.util.ArrayList;

public class Regional {

    String title;
    String imageUrl;
    String description;

    ArrayList<ItemSong> songs;

    public Regional(String title, String imageUrl, String description, ArrayList<ItemSong> songs) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.songs = songs;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<ItemSong> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<ItemSong> songs) {
        this.songs = songs;
    }
}
