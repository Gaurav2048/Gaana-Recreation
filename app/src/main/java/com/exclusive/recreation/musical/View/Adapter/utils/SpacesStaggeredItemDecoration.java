package com.exclusive.recreation.musical.View.Adapter.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class SpacesStaggeredItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int rightSpace;

    public SpacesStaggeredItemDecoration(int space, int rightSpace) {
        this.space = space;
        this.rightSpace = rightSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        outRect.left = space;
            outRect.right = rightSpace;

//        if(state.getItemCount() %2 ==0 ){
//            if(position == 0 ){
//                outRect.left = space;
//                outRect.right = rightSpace;
//            }else if (position == (state.getItemCount()/2)){
//                outRect.left = space;
//                outRect.right = rightSpace;
//            }else {
//                outRect.right = rightSpace;
//            }
//        }else {
//
//        }

        //        if(isLast){
//          //  outRect.bottom = space;
//           // outRect.top = 0; //don't forget about recycling...
//        }
//        if(position == 0){
//            outRect.left = space;
//            // don't recycle bottom if first item is also last
//            // should keep bottom padding set above
//            if(!isLast)
//                outRect.bottom = 0;
//        }
    }
}