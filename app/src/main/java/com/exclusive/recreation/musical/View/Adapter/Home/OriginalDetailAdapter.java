package com.exclusive.recreation.musical.View.Adapter.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exclusive.recreation.musical.Models.DownLoadMajor;
import com.exclusive.recreation.musical.MusicalActivity;
import com.exclusive.recreation.musical.R;
import com.exclusive.recreation.musical.View.Adapter.utils.Constant;
import com.squareup.picasso.Picasso;

public class OriginalDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    public OriginalDetailAdapter(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i==0){
            return new viewHolderOne(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_view_one, viewGroup, false));
        }else {
            return new viewHolderRest(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_view_two, viewGroup, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(i==0){
            viewHolderOne viewHolderOne = (OriginalDetailAdapter.viewHolderOne) viewHolder;
            viewHolderOne.bindView();
        }else {
            viewHolderRest vRest = (viewHolderRest) viewHolder;
            vRest.bindView();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return  position;
    }

    @Override
    public int getItemCount() {
        return Constant.list_dlm.size()+1;
    }

    public class viewHolderRest extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageOne;
        TextView title;
        TextView description;
        public viewHolderRest(@NonNull View itemView) {
            super(itemView);
            imageOne = itemView.findViewById(R.id.imageOne);
            title = itemView.findViewById(R.id.textView6);
            description = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }

        public void bindView(){
            DownLoadMajor download = Constant.list_dlm.get(getAdapterPosition()-1);
            Picasso.get().load(download.getImageUrl()).into(imageOne);
            title.setText(download.getTitle());
            description.setText(download.getDescription());
        }

        @Override
        public void onClick(View v) {

                Constant.arrayList_play = Constant.list_banner.get(getAdapterPosition()).getArrayList();
                ((MusicalActivity)context).showPlayerFragment();
                ((MusicalActivity)context).startPlayer();

        }
    }

    public class viewHolderOne extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView background;
        public viewHolderOne(@NonNull View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.imageView2);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Constant.arrayList_play = Constant.list_banner.get(getAdapterPosition()).getArrayList();
            ((MusicalActivity)context).showPlayerFragment();
            ((MusicalActivity)context).startPlayer();
        }

        public void bindView(){
            Picasso.get().load("https://images.pexels.com/photos/1266130/pexels-photo-1266130.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(background);

        }

    }
}
