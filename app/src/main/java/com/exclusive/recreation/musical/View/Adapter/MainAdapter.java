package com.exclusive.recreation.musical.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.exclusive.recreation.musical.R;
import com.exclusive.recreation.musical.View.Adapter.Home.BannerAdapter;
import com.exclusive.recreation.musical.View.Adapter.Home.CollectionsAdapter;
import com.exclusive.recreation.musical.View.Adapter.Home.FeaturdAdapter;
import com.exclusive.recreation.musical.View.Adapter.Home.HindiAdapter;
import com.exclusive.recreation.musical.View.Adapter.Home.OriginalDetailAdapter;
import com.exclusive.recreation.musical.View.Adapter.Home.SelfAdapter;
import com.exclusive.recreation.musical.View.Adapter.Home.originalAdapter;
import com.exclusive.recreation.musical.View.Adapter.utils.SpacesItemDecoration;
import com.exclusive.recreation.musical.View.Adapter.utils.SpacesStaggeredItemDecoration;
import com.squareup.picasso.Picasso;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;

    public MainAdapter(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i==0){
            return new BannerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.banners, viewGroup, false));
        }if(i==1){
            return new CollectionsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.collections, viewGroup, false));
        }if(i==2){
            return new HindiViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hindi, viewGroup, false));
        }if(i==3){
            return new MoodViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mood, viewGroup, false));
        }else if(i==4){
            return new FeaturedViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.featured, viewGroup, false));
        }else if(i==5){
            return new OriginalViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.original, viewGroup, false));
        }else if (i ==6 ){
            return new AdvanceHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detailed_original, viewGroup, false));
        }else if (i ==7 ){
            return new SelfHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.self, viewGroup, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder.getItemViewType()==0){
            BannerViewHolder bannerViewHolder = (BannerViewHolder) viewHolder;
            bannerViewHolder.bindView();
        }else if(viewHolder.getItemViewType()==1){
           CollectionsViewHolder collectionsViewHolder = (CollectionsViewHolder) viewHolder;
            collectionsViewHolder.bindView();
        }else if(viewHolder.getItemViewType()==2){
            HindiViewHolder hindiViewHolder = (HindiViewHolder) viewHolder;
            hindiViewHolder.bindView();
        }else if(viewHolder.getItemViewType()==3){
            MoodViewHolder moodViewHolder = (MoodViewHolder) viewHolder;
            moodViewHolder.bindView();
        }else if(viewHolder.getItemViewType() == 4){
            FeaturedViewHolder featuredViewHolder = (FeaturedViewHolder) viewHolder;
            featuredViewHolder.bindView();
        }else if(viewHolder.getItemViewType()==5){
            OriginalViewHolder originalViewHolder = (OriginalViewHolder) viewHolder;
            originalViewHolder.bindView();
        }else if(viewHolder.getItemViewType()==6){
            AdvanceHolder advanceHolder = (AdvanceHolder) viewHolder;
            advanceHolder.bindView();
        }else if(viewHolder.getItemViewType()==7){
            SelfHolder selfAdapter = (SelfHolder)viewHolder;
            selfAdapter.bindView();
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder{
        RecyclerView banner_recyclerview;
        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            banner_recyclerview = itemView.findViewById(R.id.banner_recyclerview);
        }
        public void bindView(){
            int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25,
                    context.getResources().getDisplayMetrics());
            if(banner_recyclerview.getItemDecorationCount()==0)
            {
                banner_recyclerview.addItemDecoration(new SpacesItemDecoration(space));
                (new LinearSnapHelper()).attachToRecyclerView(banner_recyclerview);
            }
            banner_recyclerview.setAdapter(new BannerAdapter(context));
        }
    }

    public class CollectionsViewHolder extends RecyclerView.ViewHolder{
       RecyclerView collections_recycler;
        public CollectionsViewHolder(@NonNull View itemView) {
            super(itemView);
            collections_recycler = itemView.findViewById(R.id.collections_recycler);

        }
        public void bindView(){
            int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25,
                    context.getResources().getDisplayMetrics());
            if(collections_recycler.getItemDecorationCount()==0)
            {
                collections_recycler.addItemDecoration(new SpacesItemDecoration(space));
            }
            collections_recycler.setAdapter(new CollectionsAdapter(context));
        }
    }

    public class HindiViewHolder extends RecyclerView.ViewHolder{
        RecyclerView hindiRecyclerView;
        public HindiViewHolder(@NonNull View itemView) {
            super(itemView);
            hindiRecyclerView = itemView.findViewById(R.id.hindiRecyclerView);
        }

        public void bindView(){
//            int rightSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
//                    context.getResources().getDisplayMetrics());
//            int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25,
//                    context.getResources().getDisplayMetrics());
          //  hindiRecyclerView.addItemDecoration(new SpacesStaggeredItemDecoration(space, rightSpace));
            hindiRecyclerView.setAdapter(new HindiAdapter(context));
        }
    }

    public class MoodViewHolder extends RecyclerView.ViewHolder{
        ImageView c_1,c_3, c_2, c_4, c_5;
        ImageView bannerImage;
        public MoodViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerImage = itemView.findViewById(R.id.bannerImage);
            c_1 = itemView.findViewById(R.id.c_1);
            c_3 = itemView.findViewById(R.id.c_3);
            c_2 = itemView.findViewById(R.id.c_2);
            c_4 = itemView.findViewById(R.id.c_4);
            c_5 = itemView.findViewById(R.id.c_5);
        }

        public void bindView(){
            Picasso.get().load("https://images.pexels.com/photos/2156416/pexels-photo-2156416.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(c_1);
            Picasso.get().load("https://images.pexels.com/photos/1306791/pexels-photo-1306791.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(c_2);
            Picasso.get().load("https://images.pexels.com/photos/270288/pexels-photo-270288.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(c_3);
            Picasso.get().load("https://images.pexels.com/photos/459225/pexels-photo-459225.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(c_4);
            Picasso.get().load("https://images.pexels.com/photos/1407305/pexels-photo-1407305.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(c_5);
        }
    }

    public class FeaturedViewHolder extends RecyclerView.ViewHolder{
        RecyclerView featuredRecyclerview;
        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);
            featuredRecyclerview = itemView.findViewById(R.id.featuredRecyclerview);
        }

        public void bindView(){
            int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25,
                    context.getResources().getDisplayMetrics());
            featuredRecyclerview.addItemDecoration(new SpacesItemDecoration(space));
            featuredRecyclerview.setAdapter(new FeaturdAdapter(context));
        }


    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{
        RecyclerView originalRecyclerview;
        public OriginalViewHolder(@NonNull View itemView) {
            super(itemView);
            originalRecyclerview = itemView.findViewById(R.id.originalRecyclerview);
        }

        public void bindView(){
            int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25,
                    context.getResources().getDisplayMetrics());
          if(originalRecyclerview.getItemDecorationCount() == 0 )
          {
              originalRecyclerview.addItemDecoration(new SpacesItemDecoration(space));
          }
            originalRecyclerview.setAdapter(new originalAdapter(context));
        }


    }

    public class AdvanceHolder extends RecyclerView.ViewHolder{
        RecyclerView detailedOriginRecyclerView;
        ImageView imageBack;
        public AdvanceHolder(@NonNull View itemView) {
            super(itemView);
            imageBack = itemView.findViewById(R.id.imageBack);
            detailedOriginRecyclerView = itemView.findViewById(R.id.detailedOriginRecyclerView);
        }

        public void bindView(){
            Picasso.get().load("https://images.pexels.com/photos/2088203/pexels-photo-2088203.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(imageBack);
            int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25,
                    context.getResources().getDisplayMetrics());
            if(detailedOriginRecyclerView.getItemDecorationCount() == 0 )
            {
                detailedOriginRecyclerView.addItemDecoration(new SpacesItemDecoration(space));
            }
            detailedOriginRecyclerView.setAdapter(new OriginalDetailAdapter(context));
        }


    }


    public class SelfHolder extends RecyclerView.ViewHolder{
        RecyclerView detailedOriginRecyclerView;

        public SelfHolder(@NonNull View itemView) {
            super(itemView);

            detailedOriginRecyclerView = itemView.findViewById(R.id.selfRecyclerview);
        }

        public void bindView(){


            detailedOriginRecyclerView.setAdapter(new SelfAdapter(context));
        }


    }

}
