package com.exclusive.recreation.musical.View.Adapter.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exclusive.recreation.musical.Models.Featured;
import com.exclusive.recreation.musical.MusicalActivity;
import com.exclusive.recreation.musical.R;
import com.exclusive.recreation.musical.View.Adapter.utils.Constant;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeaturdAdapter extends RecyclerView.Adapter<FeaturdAdapter.viewHolder> {
    Context context;
    public FeaturdAdapter(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_featured, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        Featured featured = Constant.list_featured.get(i);
        Picasso.get().load(featured.getImageUrl()).into(viewHolder.circleImageView);
        viewHolder.title.setText(featured.getTitle());
        viewHolder.description.setText(featured.getDescription());
    }

    @Override
    public int getItemCount() {
        return Constant.list_featured.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CircleImageView circleImageView;
        TextView title, description;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView4);
            circleImageView = itemView.findViewById(R.id.collectionsImage);
            description = itemView.findViewById(R.id.description);
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
