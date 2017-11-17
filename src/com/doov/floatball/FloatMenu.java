package com.doov.floatball;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.demo.R;

public class FloatMenu extends LinearLayout {
	private final String TAG = "FloatMenu";
	private ViewManager mViewManager;
	public FloatMenu(Context context) {
		this(context,null);
	}

	public FloatMenu(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public FloatMenu(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr,0);
	}

	public FloatMenu(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	private void init() {
		Log.d(TAG,"init");
		View root = this.inflate(getContext(), R.layout.processball, null);
		//点击进度球的以外区域
		root.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mViewManager = ViewManager.getInstance(getContext());
				mViewManager.hideFloatMenu();
				mViewManager.showFloatBall();
				return false;
			}
		});
		
		addView(root);

	}
}
