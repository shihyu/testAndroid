package com.doov.floatball;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import java.lang.reflect.Field;

public class ViewManager {
	
	private final String TAG = "DoovViewManager";
	private Context mContext;
	
	private WindowManager mWindowManager;
	private static ViewManager mViewManager;
	private FloatBallView mFloatBallView;
	private FloatMenu mFloatMenu;
	private LayoutParams mFloatBallParams;
	private LayoutParams mFloatMenuParams;
	
	private float mScreenWidth,mScreenHeight;
	private int mTouchSlop;
	
	private ViewManager(Context context) {
		this.mContext = context;
		init();
	}

	public static ViewManager getInstance(Context context){
		if(null == mViewManager){
			mViewManager = new ViewManager(context);
		}
		return mViewManager;
	}
	
	private void init() {
		Log.d(TAG,"init");
		mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		mScreenWidth = mWindowManager.getDefaultDisplay().getWidth();
		mScreenHeight = mWindowManager.getDefaultDisplay().getHeight();
		mTouchSlop = getTouchSlop();
		mFloatBallView = new FloatBallView(mContext);
		mFloatMenu = new FloatMenu(mContext);
		mFloatBallView.setOnTouchListener(new OnTouchListener() {
			float startX ,startY ,firstX ,firstY;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.d(TAG,"mFloatBallView-onTouch");
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = firstX =event.getRawX();
					startY = firstY = event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:
					float x = event.getRawX()-startX;
					float y = event.getRawY()-startY;
					mFloatBallParams.x += x;
					mFloatBallParams.y += y;
					mFloatBallView.setDrag(true);
					mWindowManager.updateViewLayout(v, mFloatBallParams);
					
					startX = event.getRawX();
					startY = event.getRawY(); 
					break;
				case MotionEvent.ACTION_UP:
					if(event.getRawX() <=mScreenWidth/2){
						mFloatBallParams.x = 0;
					}else{
						mFloatBallParams.x = (int) (mScreenWidth - mFloatBallView.width);
					}
					mFloatBallView.setDrag(false);
					mWindowManager.updateViewLayout(v, mFloatBallParams);
					
					//滑动的距离很大的时候消耗事件,否则由OnClickListener处理
					if(Math.abs(firstX-event.getRawX()) > mTouchSlop  && 
							Math.abs(firstY-event.getRawY()) > mTouchSlop){
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		
		mFloatBallView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(TAG,"mFloatBallView-onClick");
				mWindowManager.removeView(mFloatBallView);
				showFloatMenu();
			}
		});
	}

	public int getTouchSlop() {
		final ViewConfiguration configuration = ViewConfiguration.get(mContext);
	    return configuration.getScaledTouchSlop();
	}
	
	public void showFloatBall(){
		Log.d(TAG,"showFloatBall");
		if(null == mFloatBallParams){
			mFloatBallParams = new LayoutParams();
			mFloatBallParams.height = mFloatBallView.height;
			mFloatBallParams.width = mFloatBallView.width;
			mFloatBallParams.x = 0;
			mFloatBallParams.y = 700;
			mFloatBallParams.gravity = Gravity.LEFT|Gravity.TOP;
			mFloatBallParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
			mFloatBallParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE|LayoutParams.FLAG_NOT_TOUCH_MODAL;//防止桌面不能滑动
			mFloatBallParams.format = PixelFormat.RGBA_8888; // 不设置这个弹出框的透明遮罩显示为黑色
		}

		mWindowManager.addView(mFloatBallView, mFloatBallParams);
	}
	
	public void showFloatMenu(){
		Log.d(TAG,"showFloatMenu height=" +getScreenHeight() + " width=" + getScreenWidth());
		if(null == mFloatMenuParams){
			mFloatMenuParams = new LayoutParams();
			mFloatMenuParams.height = getScreenHeight()-getStatusHeight();
			mFloatMenuParams.width = getScreenWidth();
			mFloatMenuParams.gravity = Gravity.BOTTOM;
			mFloatMenuParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
			mFloatMenuParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE|LayoutParams.FLAG_NOT_TOUCH_MODAL;//防止桌面不能滑动
			mFloatMenuParams.format = PixelFormat.RGBA_8888; // 不设置这个弹出框的透明遮罩显示为黑色
		}
		
		mWindowManager.addView(mFloatMenu, mFloatMenuParams);
	}
	
	public void hideFloatMenu(){
		mWindowManager.removeView(mFloatMenu);
	}
	
	public int getScreenHeight(){
		return mWindowManager.getDefaultDisplay().getHeight();
	}
	public int getScreenWidth(){
		return mWindowManager.getDefaultDisplay().getWidth();
	}
	
	 //获取状态栏高度
    public int getStatusHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object object = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(object);
            return mContext.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            return 0;
        }
    }
}
