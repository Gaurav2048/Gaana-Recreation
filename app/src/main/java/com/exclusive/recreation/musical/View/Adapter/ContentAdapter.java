package com.exclusive.recreation.musical.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exclusive.recreation.musical.Bus.clickInterface;
import com.exclusive.recreation.musical.Models.ItemSong;
import com.exclusive.recreation.musical.MusicalActivity;
import com.exclusive.recreation.musical.R;
import com.exclusive.recreation.musical.View.Adapter.utils.Constant;
import com.squareup.picasso.Picasso;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.viewHolder> {
    Context context;
    clickInterface clickInterface;
    public ContentAdapter(@NonNull Context context, clickInterface clickInterface) {
        this.context = context;
        this.clickInterface = clickInterface;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_content_small, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        ItemSong itemSong = Constant.arrayList_play.get(i);
        Picasso.get().load(itemSong.getImageUrl()).into(viewHolder.imageView4);
        viewHolder.title.setText(itemSong.getSongName());
        viewHolder.description.setText(itemSong.getSongDescription());

    }

    @Override
    public int getItemCount() {
        return Constant.arrayList_play.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView4, download;
        TextView title, description;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView4 = itemView.findViewById(R.id.imageView4);
            download = itemView.findViewById(R.id.imageView5);
            title = itemView.findViewById(R.id.textView8);
            description = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickInterface.click(getAdapterPosition());
            Constant.playPos = getAdapterPosition();
            Constant.arrayList_play = Constant.list__suggestion;
            ((MusicalActivity)context).startPlayer();
        }
    }


}
