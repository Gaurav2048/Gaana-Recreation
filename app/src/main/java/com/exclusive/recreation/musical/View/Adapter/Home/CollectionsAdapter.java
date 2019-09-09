package com.exclusive.recreation.musical.View.Adapter.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exclusive.recreation.musical.Models.Collections;
import com.exclusive.recreation.musical.MusicalActivity;
import com.exclusive.recreation.musical.R;
import com.exclusive.recreation.musical.View.Adapter.utils.Constant;
import com.squareup.picasso.Picasso;

public class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.viewHolder> {
    Context context ;
    public CollectionsAdapter(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_collections, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        Collections collections = Constant.list_collections.get(i);
        Picasso.get().load(collections.getCollectionImage()).into(viewHolder.collectionsImage);
        viewHolder.text_holder.setText(collections.getCollectionName());
    }

    @Override
    public int getItemCount() {
        return Constant.list_collections.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView collectionsImage;
        TextView text_holder;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            collectionsImage = itemView.findViewById(R.id.collectionsImage);
            text_holder = itemView.findViewById(R.id.text_holder);
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
