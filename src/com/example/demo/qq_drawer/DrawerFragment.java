package com.example.demo.qq_drawer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by zy1373 on 2017-11-27.
 */

public class DrawerFragment extends FrameLayout {
    private final String TAG = "DrawerFragment";

    private ViewDragHelper viewDragHelper;

    private View leftView, rightView;

    private float width;

    public DrawerFragment(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        viewDragHelper = ViewDragHelper.create(this,new DrawerCallBack());
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }



    public DrawerFragment(@NonNull Context context) {
        this(context,null);

    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return viewDragHelper.shouldInterceptTouchEvent(event);//super.onInterceptHoverEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);//super.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int child_count = getChildCount();
        if(child_count < 2){
            throw new IllegalStateException("至少需要2个child view");

        }

        if(!(getChildAt(0) instanceof ViewGroup && getChildAt(1) instanceof  ViewGroup)){
            throw new IllegalStateException("child view 要求是ViewGroup 的子类");
        }


        leftView = getChildAt(0);
        rightView = getChildAt(1);
        width = rightView.getWidth();

    }

    class DrawerCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == leftView;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            Log.d(TAG,"onViewPositionChanged leftView=" +left +" dx=" +dx);
            //leftView.layout(0,0,left +,0+getHeight());
            //rightView.layout(0,0,0+width,0+getHeight());
            invalidate();
        }


        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return super.clampViewPositionHorizontal(child, left, dx);
            //Log.d(TAG,"clampViewPositionHorizontal left=" +left +" dx=" +dx);
            //return 0;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return super.getViewHorizontalDragRange(child);
            //return child == leftView ?child.getWidth():0;
        }
    }
}
