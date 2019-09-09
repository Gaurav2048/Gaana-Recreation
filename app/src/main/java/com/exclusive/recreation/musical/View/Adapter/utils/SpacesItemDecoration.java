package com.exclusive.recreation.musical.View.Adapter.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration  extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        boolean isLast = position == state.getItemCount()-1;
        if(isLast){
          //  outRect.bottom = space;
           // outRect.top = 0; //don't forget about recycling...
        }
        if(position == 0){
            outRect.left = space;
            // don't recycle bottom if first item is also last
            // should keep bottom padding set above
            if(!isLast)
                outRect.bottom = 0;
        }
    }
}