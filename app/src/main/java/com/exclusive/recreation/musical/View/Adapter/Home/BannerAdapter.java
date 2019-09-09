package com.exclusive.recreation.musical.View.Adapter.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.exclusive.recreation.musical.Models.Banners;
import com.exclusive.recreation.musical.MusicalActivity;
import com.exclusive.recreation.musical.R;
import com.exclusive.recreation.musical.View.Adapter.utils.Constant;
import com.squareup.picasso.Picasso;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.viewHolder> {
    Context context;
    public BannerAdapter(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_banners, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

        Banners banners = Constant.list_banner.get(i);
        Picasso.get().load(banners.getImageUrl()).into(viewHolder.bannerImage);

    }

    @Override
    public int getItemCount() {
        return Constant.list_banner.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView bannerImage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            bannerImage = itemView.findViewById(R.id.bannerImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Constant.arrayList_play = Constant.list_banner.get(getAdapterPosition()).getArrayList();
            ((MusicalActivity)context).showPlayerFragment();
            ((MusicalActivity)context).startPlayer();
        }
    }
}
