package com.exclusive.recreation.musical.View.Adapter.Home;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exclusive.recreation.musical.Models.self;
import com.exclusive.recreation.musical.MusicalActivity;
import com.exclusive.recreation.musical.R;
import com.exclusive.recreation.musical.View.Adapter.utils.Constant;
import com.squareup.picasso.Picasso;


public class SelfAdapter  extends RecyclerView.Adapter<SelfAdapter.viewHolder>{

    Context context;
    public SelfAdapter(Context context) {
        this.context = context;
    }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            return new viewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_small, viewGroup, false));

        }

        @Override
        public void onBindViewHolder(@NonNull final viewHolder viewHolder, int i) {

            self selfData = Constant.list__self.get(i);
            Picasso.get().load(selfData.getImageUrl()).into(viewHolder.imageView);
            viewHolder.title.setText(selfData.getTitle());
            viewHolder.textViewDescription.setText(selfData.getDescription());

        }

        @Override
        public int getItemCount() {
            return Constant.list__self.size();
        }


        public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView title, textViewDescription;
            ImageView imageView;

            ConstraintLayout smallBlogHolder;
            public viewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.textView);

                textViewDescription = itemView.findViewById(R.id.textViewDescription);
                imageView = itemView.findViewById(R.id.imageView);
                smallBlogHolder = itemView.findViewById(R.id.smallBlogHolder);
                imageView.setOnClickListener(this);
                itemView.setOnClickListener(this);
            }

            boolean siSHown = false;

            public void showAnimation() {
                if(siSHown==false)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.clone(context, R.layout.unit_large);

                        ChangeBounds transition = new ChangeBounds();

                        // transition.setInterpolator(new AnticipateInterpolator(1.0f));

                        transition.setDuration(200);

                        TransitionManager.beginDelayedTransition(smallBlogHolder, transition);
                        constraintSet.applyTo(smallBlogHolder);
                    }
                    siSHown=true;
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.clone(context, R.layout.unit_small);

                        ChangeBounds transition = new ChangeBounds();

                        // transition.setInterpolator(new AnticipateInterpolator(1.0f));

                        transition.setDuration(200);

                        TransitionManager.beginDelayedTransition(smallBlogHolder, transition);
                        constraintSet.applyTo(smallBlogHolder);
                    }
                    siSHown=false;
                }
            }


            @Override
            public void onClick(View v) {
                if(v==imageView)
                {
                    showAnimation();
                }else if(v==itemView){
                    Constant.arrayList_play = Constant.list__self.get(getAdapterPosition()).getSongs();
                    ((MusicalActivity)context).startPlayer();
                }
            }
        }


    }



