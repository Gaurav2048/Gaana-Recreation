package com.exclusive.recreation.musical.Models;

import java.util.ArrayList;

public class suggestions {
    String title;
    String description;
    String imageUrl;

    ArrayList<ItemSong> songs;

    public suggestions(String title, String description, String imageUrl, ArrayList<ItemSong> songs) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.songs = songs;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<ItemSong> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<ItemSong> songs) {
        this.songs = songs;
    }
}
