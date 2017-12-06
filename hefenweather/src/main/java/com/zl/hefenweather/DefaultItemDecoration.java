package com.zl.hefenweather;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.security.InvalidParameterException;

/**
 * Created by zhangle on 2017-12-6.
 */

public class DefaultItemDecoration extends RecyclerView.ItemDecoration {

    private final static int VERTICAL = LinearLayoutManager.VERTICAL;
    private final static int HORIZONTAL = LinearLayoutManager.HORIZONTAL;

    private  Drawable dividerDrawable = null;

    //我们通过获取系统属性中的listDivider来添加，在系统中的AppTheme中设置
    public static final int[] ATRRS  = new int[]{
            android.R.attr.listDivider
    };

    private int orientation ;

    public DefaultItemDecoration(Context context,int orientation) {

        TypedArray typedArray = context.obtainStyledAttributes(ATRRS);
        this.dividerDrawable = typedArray.getDrawable(0);
        if(orientation != VERTICAL && orientation != HORIZONTAL){
            throw new InvalidParameterException("invalid orientation param");
        }
        this.orientation = orientation;
    }



    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //super.onDraw(c, parent, state);
        if(orientation == VERTICAL){
            drawVertialDivider(c,parent);
        }else if(orientation == HORIZONTAL){
            drawHorizontalDivider(c,parent);
        }

    }


    /**
     * 画横线
     * @param c
     * @param parent
     */
    public void drawHorizontalDivider(Canvas c,RecyclerView parent){

        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) v.getLayoutParams();
            int top = v.getBottom() + params.bottomMargin;
            int bottom = top + dividerDrawable.getIntrinsicHeight();
            dividerDrawable.setBounds(left,top,right,bottom);
            dividerDrawable.draw(c);
        }


    }

    /**
     * 画竖线
     * @param c
     * @param parent
     */
    private void drawVertialDivider(Canvas c,RecyclerView parent){

        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) v.getLayoutParams();
            int left = v.getRight() + params.rightMargin;
            int right = left +dividerDrawable.getIntrinsicWidth();

            dividerDrawable.setBounds(left,top,right,bottom);
            dividerDrawable.draw(c);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //super.getItemOffsets(outRect, view, parent, state);
        if(orientation == VERTICAL){
            outRect.set(0,0,dividerDrawable.getIntrinsicWidth(),0);
        }else if(orientation == HORIZONTAL){
            outRect.set(0,0,0,dividerDrawable.getIntrinsicHeight());
        }
    }
}
