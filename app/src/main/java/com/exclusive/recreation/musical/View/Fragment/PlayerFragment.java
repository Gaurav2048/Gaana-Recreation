package com.exclusive.recreation.musical.View.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.exclusive.recreation.musical.Bus.clickInterface;
import com.exclusive.recreation.musical.MusicalActivity;
import com.exclusive.recreation.musical.R;
import com.exclusive.recreation.musical.View.Adapter.ContentAdapter;
import com.exclusive.recreation.musical.View.Adapter.Home.PlayerAdapter;
import com.exclusive.recreation.musical.View.Adapter.utils.Constant;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import es.claucookie.miniequalizerlibrary.EqualizerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment implements clickInterface {

    EqualizerView equaliser;
    DiscreteScrollView scrollView;
    RelativeLayout llBottomSheet;
    RelativeLayout playerView, ContentView;
    ImageView play_pause, item_next, item_previous;
    RecyclerView contentRecyclerVIew;
    BottomSheetBehavior bottomSheetBehavior;

    public PlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_player, container, false);
        equaliser = view.findViewById(R.id.equaliser);
        contentRecyclerVIew = view.findViewById(R.id.contentRecyclerVIew);
          scrollView = view.findViewById(R.id.scrollView);
        item_next = view.findViewById(R.id.item_next);
        play_pause = view.findViewById(R.id.play_pause);
        item_previous = view.findViewById(R.id.item_previous);
        playerView = view.findViewById(R.id.peekView);
        ContentView = view.findViewById(R.id.contentView);
        llBottomSheet =  view.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        scrollView.setAdapter(new PlayerAdapter(getContext(), this));
        scrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1.05f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build());
        equaliser.animateBars();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                Log.e( "onSlide: ",(1-slideOffset )+" " );


                ContentView.setAlpha(slideOffset);
                playerView.setAlpha(1f-slideOffset);



            }
        });

        if(Constant.isPlaying.equals(false))
        {
            play_pause.setImageResource(R.mipmap.ic_pause_light);
            Constant.isPlaying= true;
        }else {
            Constant.isPlaying = false;
            play_pause.setImageResource(R.mipmap.ic_play_white);
        }

        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Constant.isPlaying.equals(false))
                {
                    play_pause.setImageResource(R.mipmap.ic_pause_light);
                    equaliser.animateBars();
                    Constant.isPlaying= true;
                }else {
                    Constant.isPlaying = false;
                    equaliser.stopBars();
                    play_pause.setImageResource(R.mipmap.ic_play_light);
                }
                ((MusicalActivity)getContext()).PausePlayer();
            }
        });

        item_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MusicalActivity)getContext()).NextPlayer();
            }
        });

        item_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MusicalActivity)getContext()).PreviousPlayer();
            }
        });

        contentRecyclerVIew.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        contentRecyclerVIew.setAdapter(new ContentAdapter(getContext(), this));

    }

    @Override
    public void click(int position) {
        scrollView.smoothScrollToPosition(position);
    }
}
