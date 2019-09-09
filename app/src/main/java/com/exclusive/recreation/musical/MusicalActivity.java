package com.exclusive.recreation.musical;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exclusive.recreation.musical.Models.Banners;
import com.exclusive.recreation.musical.Models.Collections;
import com.exclusive.recreation.musical.Models.DownLoadMajor;
import com.exclusive.recreation.musical.Models.Featured;
import com.exclusive.recreation.musical.Models.ItemSong;
import com.exclusive.recreation.musical.Models.Original;
import com.exclusive.recreation.musical.Models.Regional;
import com.exclusive.recreation.musical.Models.self;
import com.exclusive.recreation.musical.Services.OnlineService;
import com.exclusive.recreation.musical.View.Adapter.SuggestionsAdapter;
import com.exclusive.recreation.musical.View.Adapter.utils.Constant;
import com.exclusive.recreation.musical.View.Fragment.MainFragment;
import com.exclusive.recreation.musical.View.Fragment.PlayerFragment;
import com.exclusive.recreation.musical.View.Fragment.ProfileFragment;
import com.exclusive.recreation.musical.View.Fragment.SearchFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MusicalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CoordinatorLayout mainHolderLayout;
    RecyclerView Suggestions_recyclerview;
    BottomNavigationView bottomNavigation;
    ImageView imageThumb,play_pause;
    RelativeLayout PlayerView;
    TextView descriptionText, songTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_musical);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        PlayerView = findViewById(R.id.PlayerView);
        descriptionText = findViewById(R.id.descriptionText);
        imageThumb = findViewById(R.id.imageThumb);
        songTitle = findViewById(R.id.songTitle);
        play_pause = findViewById(R.id.play_pause);
        descriptionText.setHorizontallyScrolling(true);
        descriptionText.setSelected(true);
        mainHolderLayout = findViewById(R.id.mainHolderLayout);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        Suggestions_recyclerview = findViewById(R.id.Suggestions_recyclerview);


        if(Constant.arrayList_play.size()==0)
        {
            PlayerView.setVisibility(View.GONE);
        }else {
            PlayerView.setVisibility(View.VISIBLE);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        CreateData();


        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MainFragment()).commit();


        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.action_home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new MainFragment()).commit();
                }else if(menuItem.getItemId() == R.id.action_profile){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
                }else if(menuItem.getItemId() == R.id.action_search){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new SearchFragment()).commit();
                }
                return true;
            }
        });


        navigationView.setNavigationItemSelectedListener(this);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float floatOffset) {
                Log.e( "onDrawerSlide: ",floatOffset+" " );
                mainHolderLayout.setLeft((int) (floatOffset*mainHolderLayout.getWidth()));
            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });


        PlayerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlayer_shown=true;
                PlayerView.setVisibility(View.GONE);
                bottomNavigation.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new PlayerFragment()).addToBackStack("player_fragment").commit();
            }
        });


        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constant.isPlaying.equals(false))
                {
                    play_pause.setImageResource(R.mipmap.ic_pause_red);
                    Constant.isPlaying= true;
                }else {
                    Constant.isPlaying = false;
                    play_pause.setImageResource(R.mipmap.ic_play_red);
                }
                PausePlayer();
            }
        });


        initSideRecyclerView();

    }


    public void PausePlayer(){
        Intent intent = new Intent(getApplicationContext(), OnlineService.class);
        intent.setAction(OnlineService.ACTION_TOGGLE);
        startService(intent);
    }


    public void NextPlayer(){
        Intent intent = new Intent(getApplicationContext(), OnlineService.class);
        intent.setAction(OnlineService.ACTION_NEXT);
        startService(intent);
    }


    public void PreviousPlayer(){
        Intent intent = new Intent(getApplicationContext(), OnlineService.class);
        intent.setAction(OnlineService.ACTION_PREVIOUS);
        startService(intent);
    }

    boolean isPlayer_shown = false;

    public void showPlayerFragment(){
        Constant.isPlaying = true;
        if(PlayerView.getVisibility()==View.GONE){
            PlayerView.setVisibility(View.VISIBLE);
            bottomNavigation.setVisibility(View.VISIBLE);
        }
        ItemSong itemSong = Constant.arrayList_play.get(Constant.playPos);
        Picasso.get().load(itemSong.getImageUrl()).into(imageThumb);
        songTitle.setText(itemSong.getSongName());
        descriptionText.setText(itemSong.getSongDescription());
    }


    public void startPlayer(){
        Intent intent = new Intent(getApplicationContext(), OnlineService.class);
        intent.setAction(OnlineService.ACTION_PLAY);
         startService(intent);

    }


    private void CreateData() {
        ArrayList<ItemSong> arrayList_play = new ArrayList<>();
        arrayList_play.add(new ItemSong("Sun ben stokes","Lorem 2 ip fhdbnc kjnds","Name one please listnen","Name One","https://images.pexels.com/photos/210854/pexels-photo-210854.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","http://eklavyaonline.in/songs/wiz_khalifa_see_you.mp3"));
        arrayList_play.add(new ItemSong("Sun ben stokes","Lorem 2 ip fhdbnc kjnds","Name one please listnen","Name One","https://images.pexels.com/photos/34221/violin-musical-instrument-music-sound.jpg?auto=compress&cs=tinysrgb&dpr=1&w=500","http://eklavyaonline.in/songs/the_chainsmokers_closer.mp3"));
        arrayList_play.add(new ItemSong("Sun ben stokes","Lorem 2 ip fhdbnc kjnds","Name one please listnen","Name One","https://images.pexels.com/photos/164829/pexels-photo-164829.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","http://eklavyaonline.in/songs/owl_city_fireflies.mp3"));
        arrayList_play.add(new ItemSong("Sun ben stokes","Lorem 2 ip fhdbnc kjnds","Name one please listnen","Name One","https://images.pexels.com/photos/352505/pexels-photo-352505.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","http://eklavyaonline.in/songs/coldplay_paradise.mp3"));
        arrayList_play.add(new ItemSong("Sun ben stokes","Lorem 2 ip fhdbnc kjnds","Name one please listnen","Name One","https://images.pexels.com/photos/1762578/pexels-photo-1762578.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","http://eklavyaonline.in/songs/ed_sheeran_shape_of_you.mp3"));
        arrayList_play.add(new ItemSong("Sun ben stokes","Lorem 2 ip fhdbnc kjnds","Name one please listnen","Name One","https://images.pexels.com/photos/1001850/pexels-photo-1001850.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","http://eklavyaonline.in/songs/alan_walker_faded.mp3"));
        arrayList_play.add(new ItemSong("Sun ben stokes","Lorem 2 ip fhdbnc kjnds","Name one please listnen","Name One","https://images.pexels.com/photos/1481309/pexels-photo-1481309.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","http://eklavyaonline.in/songs/afreen_afreen_rahat_fateh_ali_khan.mp3"));
        arrayList_play.add(new ItemSong("Sun ben stokes","Lorem 2 ip fhdbnc kjnds","Name one please listnen","Name One","https://images.pexels.com/photos/33597/guitar-classical-guitar-acoustic-guitar-electric-guitar.jpg?auto=compress&cs=tinysrgb&dpr=1&w=500","http://eklavyaonline.in/songs/Unstoppable.mp3"));

        arrayList_play.add(new ItemSong("Sun ben stokes","Lorem 2 ip fhdbnc kjnds","Name one please listnen","Name One","https://images.pexels.com/photos/373945/pexels-photo-373945.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","http://eklavyaonline.in/songs/YMOC_Tone.mp3"));
        arrayList_play.add(new ItemSong("Sun ben stokes","Lorem 2 ip fhdbnc kjnds","Name one please listnen","Name One","https://images.pexels.com/photos/1763075/pexels-photo-1763075.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","http://eklavyaonline.in/songs/notify_old.mp3"));

        Constant.list__suggestion = arrayList_play;

        Constant.list_banner.add(new Banners("Banner 1", "https://images.pexels.com/photos/675960/mic-music-sound-singer-675960.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_banner.add(new Banners("Banner 2", "https://images.pexels.com/photos/63703/pexels-photo-63703.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_banner.add(new Banners("Banner 3", "https://images.pexels.com/photos/1626481/pexels-photo-1626481.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_banner.add(new Banners("Banner 4", "https://images.pexels.com/photos/1436141/pexels-photo-1436141.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_banner.add(new Banners("Banner 5", "https://images.pexels.com/photos/358666/pexels-photo-358666.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_banner.add(new Banners("Banner 6", "https://images.pexels.com/photos/265672/pexels-photo-265672.png?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_banner.add(new Banners("Banner 7", "https://images.pexels.com/photos/1190298/pexels-photo-1190298.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_banner.add(new Banners("Banner 8", "https://images.pexels.com/photos/1537689/pexels-photo-1537689.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));



        Constant.list_collections.add(new Collections("Romantic", "https://images.pexels.com/photos/165971/pexels-photo-165971.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_collections.add(new Collections("Indian", "https://images.pexels.com/photos/1958841/pexels-photo-1958841.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_collections.add(new Collections("Rock", "https://images.pexels.com/photos/2466341/pexels-photo-2466341.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_collections.add(new Collections("Night out", "https://images.pexels.com/photos/1706018/pexels-photo-1706018.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_collections.add(new Collections("Electronic", "https://images.pexels.com/photos/159376/turntable-top-view-audio-equipment-159376.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_collections.add(new Collections("Classic", "https://images.pexels.com/photos/1761362/pexels-photo-1761362.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_collections.add(new Collections("Instrument", "https://images.pexels.com/photos/248510/pexels-photo-248510.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_collections.add(new Collections("Hand made", "https://images.pexels.com/photos/1213918/pexels-photo-1213918.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));


        Constant.list_regional.add(new Regional("Hindi","https://images.pexels.com/photos/814499/pexels-photo-814499.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",  "1960s with the release of Letraset sheets containing", arrayList_play));
        Constant.list_regional.add(new Regional("English","https://images.pexels.com/photos/70365/forest-sunbeams-trees-sunlight-70365.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", "1960s with the release of Letraset sheets containing", arrayList_play));
        Constant.list_regional.add(new Regional("Assamese","https://images.pexels.com/photos/561463/pexels-photo-561463.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", "1960s with the release of Letraset sheets containing", arrayList_play));
        Constant.list_regional.add(new Regional("Marathi", "https://images.pexels.com/photos/462118/pexels-photo-462118.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","1960s with the release of Letraset sheets containing", arrayList_play));
        Constant.list_regional.add(new Regional("Vhojpui", "https://images.pexels.com/photos/2832041/pexels-photo-2832041.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","1960s with the release of Letraset sheets containing", arrayList_play));
        Constant.list_regional.add(new Regional("Newone", "https://images.pexels.com/photos/2832034/pexels-photo-2832034.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","1960s with the release of Letraset sheets containing", arrayList_play));
        Constant.list_regional.add(new Regional("Segment", "https://images.pexels.com/photos/1450082/pexels-photo-1450082.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","1960s with the release of Letraset sheets containing", arrayList_play));
        Constant.list_regional.add(new Regional("Instrument", "https://images.pexels.com/photos/66997/pexels-photo-66997.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","1960s with the release of Letraset sheets containing", arrayList_play));
        Constant.list_regional.add(new Regional("Classic", "https://images.pexels.com/photos/2832046/pexels-photo-2832046.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","1960s with the release of Letraset sheets containing", arrayList_play));
        Constant.list_regional.add(new Regional("Regional", "https://images.pexels.com/photos/917494/pexels-photo-917494.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","1960s with the release of Letraset sheets containing", arrayList_play));
        Constant.list_regional.add(new Regional("Popular", "https://images.pexels.com/photos/1020016/pexels-photo-1020016.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","1960s with the release of Letraset sheets containing", arrayList_play));
        Constant.list_regional.add(new Regional("Head Start", "https://images.pexels.com/photos/2831299/pexels-photo-2831299.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","1960s with the release of Letraset sheets containing", arrayList_play));
        Constant.list_regional.add(new Regional("Party", "https://images.pexels.com/photos/572861/pexels-photo-572861.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","1960s with the release of Letraset sheets containing", arrayList_play));
        Constant.list_regional.add(new Regional("Meanwhile", "https://images.pexels.com/photos/36762/scarlet-honeyeater-bird-red-feathers.jpg?auto=compress&cs=tinysrgb&dpr=1&w=500","1960s with the release of Letraset sheets containing", arrayList_play));

        Constant.list_featured.add(new Featured("Artist One", "210k followers\nPlayed 4m times","https://images.pexels.com/photos/2412691/pexels-photo-2412691.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",  arrayList_play));
        Constant.list_featured.add(new Featured("Artist Two", "200k followers\nPlayed 4.1m times","https://images.pexels.com/photos/25756/pexels-photo.jpg?auto=compress&cs=tinysrgb&dpr=1&w=500",  arrayList_play));
        Constant.list_featured.add(new Featured("Artist Three", "220k followers\nPlayed 4.2m times","https://images.pexels.com/photos/2674773/pexels-photo-2674773.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",  arrayList_play));
        Constant.list_featured.add(new Featured("Artist Four", "250k followers\nPlayed 4.3m times","https://images.pexels.com/photos/957882/pexels-photo-957882.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",  arrayList_play));
        Constant.list_featured.add(new Featured("Artist Five", "260k followers\nPlayed 4.4m times","https://images.pexels.com/photos/2317116/pexels-photo-2317116.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",  arrayList_play));
        Constant.list_featured.add(new Featured("Artist Six", "270k followers\nPlayed 4.5m times","https://images.pexels.com/photos/2120114/pexels-photo-2120114.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",  arrayList_play));
        Constant.list_featured.add(new Featured("Artist Seven", "280k followers\nPlayed 4.6m times","https://images.pexels.com/photos/2314939/pexels-photo-2314939.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",  arrayList_play));
        Constant.list_featured.add(new Featured("Artist Eight", "290k followers\nPlayed 4.7m times","https://images.pexels.com/photos/2787351/pexels-photo-2787351.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",  arrayList_play));
        Constant.list_featured.add(new Featured("Artist Nine", "300k followers\nPlayed 4.8m times","https://images.pexels.com/photos/2869076/pexels-photo-2869076.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",  arrayList_play));


        Constant.list_original.add(new Original("Original One", "Jhon Doe, Jane Doe ", "https://images.pexels.com/photos/1652296/pexels-photo-1652296.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_original.add(new Original("Original One", "Jhon Doe, Jane Doe ", "https://images.pexels.com/photos/1916821/pexels-photo-1916821.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_original.add(new Original("Original One", "Jhon Doe, Jane Doe ", "https://images.pexels.com/photos/1047442/pexels-photo-1047442.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_original.add(new Original("Original One", "Jhon Doe, Jane Doe ", "https://images.pexels.com/photos/2479328/pexels-photo-2479328.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_original.add(new Original("Original One", "Jhon Doe, Jane Doe ", "https://images.pexels.com/photos/2549941/pexels-photo-2549941.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_original.add(new Original("Original One", "Jhon Doe, Jane Doe ", "https://images.pexels.com/photos/2317519/pexels-photo-2317519.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_original.add(new Original("Original One", "Jhon Doe, Jane Doe ", "https://images.pexels.com/photos/40904/bmw-k-1300-metallic-motorcycle-40904.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_original.add(new Original("Original One", "Jhon Doe, Jane Doe ", "https://images.pexels.com/photos/2516874/pexels-photo-2516874.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_original.add(new Original("Original One", "Jhon Doe, Jane Doe ", "https://images.pexels.com/photos/2658994/pexels-photo-2658994.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));

        Constant.list_dlm.add(new DownLoadMajor("Download One","Songs awesome, loving","https://images.pexels.com/photos/164634/pexels-photo-164634.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_dlm.add(new DownLoadMajor("Download Three","Songs awesome, loving","https://images.pexels.com/photos/919073/pexels-photo-919073.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_dlm.add(new DownLoadMajor("Download Two","Songs awesome, loving","https://images.pexels.com/photos/241316/pexels-photo-241316.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_dlm.add(new DownLoadMajor("Download Four","Songs awesome, loving","https://images.pexels.com/photos/315938/pexels-photo-315938.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_dlm.add(new DownLoadMajor("Download Five","Songs awesome, loving","https://images.pexels.com/photos/1876851/pexels-photo-1876851.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_dlm.add(new DownLoadMajor("Download Six","Songs awesome, loving","https://images.pexels.com/photos/305070/pexels-photo-305070.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_dlm.add(new DownLoadMajor("Download One","Songs awesome, loving","https://images.pexels.com/photos/1164778/pexels-photo-1164778.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_dlm.add(new DownLoadMajor("Download One","Songs awesome, loving","https://images.pexels.com/photos/1454253/pexels-photo-1454253.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list_dlm.add(new DownLoadMajor("Download One","Songs awesome, loving","https://images.pexels.com/photos/1253049/pexels-photo-1253049.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));

        Constant.list__self.add(new self("Musical favourite one","text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Le","https://images.pexels.com/photos/1253049/pexels-photo-1253049.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list__self.add(new self("Musical favourite Two","text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Le","https://images.pexels.com/photos/2897914/pexels-photo-2897914.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list__self.add(new self("Musical favourite Three","text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Le","https://images.pexels.com/photos/1275680/pexels-photo-1275680.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list__self.add(new self("Musical favourite Four","text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Le","https://images.pexels.com/photos/2715442/pexels-photo-2715442.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list__self.add(new self("Musical favourite Five","text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Le","https://images.pexels.com/photos/2531734/pexels-photo-2531734.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list__self.add(new self("Musical favourite Six","text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Le","https://images.pexels.com/photos/2047905/pexels-photo-2047905.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list__self.add(new self("Musical favourite Seven","text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Le","https://images.pexels.com/photos/2709986/pexels-photo-2709986.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list__self.add(new self("Musical favourite Eight","text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Le","https://images.pexels.com/photos/2873510/pexels-photo-2873510.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list__self.add(new self("Musical favourite Nine","text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Le","https://images.pexels.com/photos/1683724/pexels-photo-1683724.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));
        Constant.list__self.add(new self("Musical favourite Ten","text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Le","https://images.pexels.com/photos/1808331/pexels-photo-1808331.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", arrayList_play));


        



    }

    private void initSideRecyclerView() {

    Suggestions_recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    Suggestions_recyclerview.setAdapter(new SuggestionsAdapter(MusicalActivity.this));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(isPlayer_shown==true){
                bottomNavigation.setVisibility(View.VISIBLE);
                PlayerView.setVisibility(View.VISIBLE);
                getSupportFragmentManager().popBackStack();
                isPlayer_shown = false;
            }else
            {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.musical, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
