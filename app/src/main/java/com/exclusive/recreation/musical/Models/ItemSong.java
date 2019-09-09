package com.exclusive.recreation.musical.Models;

public class ItemSong {

    String SongName;
    String SongDescription;
    String SongPlayed;
    String artistName;
    String ImageUrl;
    String musicUrl;

    public ItemSong(String songName, String songDescription, String songPlayed, String artistName, String imageUrl, String musicUrl) {
        SongName = songName;
        SongDescription = songDescription;
        SongPlayed = songPlayed;
        this.artistName = artistName;
        ImageUrl = imageUrl;
        this.musicUrl = musicUrl;
    }

    public String getSongName() {
        return SongName;
    }

    public void setSongName(String songName) {
        SongName = songName;
    }

    public String getSongDescription() {
        return SongDescription;
    }

    public void setSongDescription(String songDescription) {
        SongDescription = songDescription;
    }

    public String getSongPlayed() {
        return SongPlayed;
    }

    public void setSongPlayed(String songPlayed) {
        SongPlayed = songPlayed;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }
}
