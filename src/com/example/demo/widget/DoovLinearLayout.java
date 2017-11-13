package com.example.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class DoovLinearLayout extends LinearLayout {
	private static final String TAG = "DoovLinearLayout";

	public DoovLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		boolean result = super.dispatchTouchEvent(ev);
		Log.d(TAG,"dispatchTouchEvent()" + " action=" + ev.getAction() + " result=" + result);
		return result;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean result = super.onInterceptTouchEvent(ev);
		Log.d(TAG,"onInterceptTouchEvent()"+ " action=" + ev.getAction()  + " result=" + result);
		return result;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean result = super.onTouchEvent(event);
		Log.d(TAG,"onTouchEvent()"+ " action=" + event.getAction()  + " result=" + result);
		return result;
	}

}
