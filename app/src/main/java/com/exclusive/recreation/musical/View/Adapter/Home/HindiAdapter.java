package com.exclusive.recreation.musical.View.Adapter.Home;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exclusive.recreation.musical.Models.Regional;
import com.exclusive.recreation.musical.MusicalActivity;
import com.exclusive.recreation.musical.R;
import com.exclusive.recreation.musical.View.Adapter.utils.Constant;
import com.squareup.picasso.Picasso;

public class HindiAdapter extends RecyclerView.Adapter<HindiAdapter.viewHolder> {
    Context context;
    public HindiAdapter(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_hindi, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        Regional region = Constant.list_regional.get(i);
        Picasso.get().load(region.getImageUrl()).into(viewHolder.collectionsImage);
        viewHolder.title.setText(region.getTitle());
        viewHolder.description.setText(region.getDescription());
    }

    @Override
    public int getItemCount() {
        return Constant.list_regional.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView collectionsImage;
        TextView title;
        TextView description;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView4);
            description = itemView.findViewById(R.id.description);
            collectionsImage = itemView.findViewById(R.id.collView);
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
