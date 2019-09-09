package com.exclusive.recreation.musical.View.Adapter.utils;

import com.exclusive.recreation.musical.Models.Banners;
import com.exclusive.recreation.musical.Models.Collections;
import com.exclusive.recreation.musical.Models.DownLoadMajor;
import com.exclusive.recreation.musical.Models.Featured;
import com.exclusive.recreation.musical.Models.ItemSong;
import com.exclusive.recreation.musical.Models.Original;
import com.exclusive.recreation.musical.Models.Regional;
import com.exclusive.recreation.musical.Models.self;
import com.exclusive.recreation.musical.Models.suggestions;

import java.util.ArrayList;

public class Constant {

    public static Integer playPos = 0;
    public static   ArrayList<ItemSong> arrayList_play = new ArrayList();
    public static   ArrayList<Banners> list_banner = new ArrayList();
    public static   ArrayList<Collections> list_collections = new ArrayList();
    public static   ArrayList<Regional> list_regional = new ArrayList<>();
    public static   ArrayList<Featured> list_featured = new ArrayList<>();
    public static   ArrayList<Original> list_original = new ArrayList<>();
    public static   ArrayList<DownLoadMajor> list_dlm = new ArrayList<>();
    public static   ArrayList<self> list__self = new ArrayList<>();
    public static   ArrayList<ItemSong> list__suggestion = new ArrayList<>();
    public static Boolean isPlaying = false;
    public static Boolean isSuffle = false;
    public static Boolean isPlayed = false,  isRepeat = false;


}
