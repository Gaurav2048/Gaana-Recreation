package com.exclusive.recreation.musical.Models;

import java.util.ArrayList;

public class Banners {

    String bannerName;
    String imageUrl;
    ArrayList<ItemSong> arrayList ;


    public Banners(String bannerName, String imageUrl, ArrayList<ItemSong> arrayList) {
        this.bannerName = bannerName;
        this.imageUrl = imageUrl;
        this.arrayList = arrayList;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<ItemSong> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ItemSong> arrayList) {
        this.arrayList = arrayList;
    }
}
