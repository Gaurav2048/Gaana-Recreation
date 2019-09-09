package com.exclusive.recreation.musical.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exclusive.recreation.musical.Models.ItemSong;
import com.exclusive.recreation.musical.Models.suggestions;
import com.exclusive.recreation.musical.MusicalActivity;
import com.exclusive.recreation.musical.R;
import com.exclusive.recreation.musical.View.Adapter.utils.Constant;
import com.squareup.picasso.Picasso;

public class SuggestionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    public SuggestionsAdapter(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i==0){
            return new viewHolderOne(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_suggestions_one, viewGroup, false));
        }else if(i==1){
            return new viewHolderTwo(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_suggestions_two, viewGroup, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder.getItemViewType()==0)
        {
            viewHolderOne vOne = (viewHolderOne) viewHolder;
            ItemSong suggest = Constant.list__suggestion.get(i);
            Picasso.get().load(suggest.getImageUrl()).into(vOne.main_image);
             vOne.title_list.setText(suggest.getSongName());
            vOne.description_list.setText(suggest.getSongDescription());
        }
    }

    @Override
    public int getItemCount() {
        return Constant.list__suggestion.size();
    }

    public class viewHolderOne extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title_list;
        ImageView main_image;
        TextView description_list;
        public viewHolderOne(@NonNull View itemView) {
            super(itemView);
            main_image = itemView.findViewById(R.id.main_image);
            title_list = itemView.findViewById(R.id.title_list);
            description_list = itemView.findViewById(R.id.description_list);
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

    public class viewHolderTwo extends RecyclerView.ViewHolder{

        public viewHolderTwo(@NonNull View itemView) {
            super(itemView);
        }
    }



}
