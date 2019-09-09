package com.exclusive.recreation.musical.View.Adapter.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.exclusive.recreation.musical.Bus.clickInterface;
import com.exclusive.recreation.musical.Models.ItemSong;
import com.exclusive.recreation.musical.MusicalActivity;
import com.exclusive.recreation.musical.R;
import com.exclusive.recreation.musical.View.Adapter.utils.Constant;
import com.google.android.exoplayer2.C;
import com.squareup.picasso.Picasso;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.viewHolder> {
    Context context;
    clickInterface clickInterface;
    public PlayerAdapter(@NonNull Context context, clickInterface clickInterface) {
        this.context = context;
        this.clickInterface = clickInterface;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_player, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        ItemSong itemSong = Constant.arrayList_play.get(i);
        Picasso.get().load(itemSong.getImageUrl()).into(viewHolder.song_cover);

    }

    @Override
    public int getItemCount() {
        return Constant.arrayList_play.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView song_cover;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            song_cover = itemView.findViewById(R.id.song_cover);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Constant.playPos = getAdapterPosition();
            Constant.arrayList_play = Constant.list__suggestion;
            ((MusicalActivity)context).showPlayerFragment();
            ((MusicalActivity)context).startPlayer();
        }
    }
}
