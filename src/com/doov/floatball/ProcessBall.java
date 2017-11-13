package com.doov.floatball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class ProcessBall extends View {

	private final String TAG = "ProcessBall";
	public int width=200,height=200;
	private Paint mCiclePaint;
	private Paint mProcessPaint;
	private Paint mTextPaint;
	private Path mProcessPath;
	private Canvas mBitMapCanvas;
	
	private int mCurrentProcess = 0;
	private final int MAX_PROCESS = 100;
	private int mTargetPorcess = 75;
	private Bitmap bitmap;
	
	private boolean isSingleTap;
	private final int StartAmplitude = 20;//振幅
	private final int VIBRATE_COUNT = 20;//震动的次数
	private int mCurrentCount;
	private SingleTapRunnable mSingleTapRunnable = new SingleTapRunnable();
	private DoubleTabRunnable mDoubleTabRunnable = new DoubleTabRunnable();
	
	public ProcessBall(Context context) {
		this(context,null);
	}

	public ProcessBall(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public ProcessBall(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr,0);
	}

	public ProcessBall(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	private void init() {
		//圆球画笔
		mCiclePaint = new Paint();
		mCiclePaint.setAntiAlias(true);
		mCiclePaint.setDither(true);
		mCiclePaint.setFilterBitmap(true);
		mCiclePaint.setColor(Color.argb(0xff, 0x3a, 0x8c, 0x6c));
		
		//波浪线的画笔
		mProcessPaint = new Paint();
		mProcessPaint.setAntiAlias(true);
		mProcessPaint.setDither(true);
		mProcessPaint.setFilterBitmap(true);
		mProcessPaint.setColor(Color.GREEN);
		mProcessPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		
		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(40);
		
		mProcessPath = new Path();
		bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		mBitMapCanvas = new Canvas(bitmap);
		mBitMapCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));
		
		final GestureDetector gestureDetector = new GestureDetector(getContext(),new SimpleOnGestureListener(){
			@Override
			public boolean onDoubleTap(MotionEvent e) {
				if(mCurrentCount == 0 || mCurrentProcess == mTargetPorcess){//单击效果完成/未单击  或者双击已经完成
					isSingleTap = false;
					mCurrentProcess = 0;
					startDoubleTabAnimation();
				}
				return super.onDoubleTap(e);
			}
			@Override
			public boolean onSingleTapConfirmed(MotionEvent e) {
				if(mCurrentCount == 0 && mCurrentProcess == mTargetPorcess){//双击已经完成
					isSingleTap = true;
					startSingleTapAnimation();
				}
				return super.onSingleTapConfirmed(e);
			}
		})	;
		setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		});
	}
	public void startDoubleTabAnimation(){
		postDelayed(mDoubleTabRunnable, 50);
	}
	
	class DoubleTabRunnable implements Runnable{

		@Override
		public void run() {
			if(mCurrentProcess < mTargetPorcess){
				mCurrentProcess++;
				invalidate();
				postDelayed(mDoubleTabRunnable, 50);
			}else{
				removeCallbacks(mDoubleTabRunnable);
				//mCurrentProcess = 0;
			}
		}
	}
	
	public void startSingleTapAnimation(){
		postDelayed(mSingleTapRunnable, 100);
	}
	
	class SingleTapRunnable implements Runnable{

		@Override
		public void run() {
			if(mCurrentCount < VIBRATE_COUNT){
				mCurrentCount++;
				invalidate();
				postDelayed(mSingleTapRunnable, 100);
			}else{
				removeCallbacks(mSingleTapRunnable);
				mCurrentCount = 0;
			}
		}
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(width, height);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mBitMapCanvas.drawCircle(width/2, height/2, width/2, mCiclePaint);
		
		//绘制波浪线:矩形的4个点和上边的波浪线一起组成一个矩形.
		//由于mProcessPaint 是使用的Mode.SRC_IN 所以只会显示和下层重叠的部分.
		mProcessPath.reset();
		//波浪线的高度由当前进度值决定
		float y = (1-(float)mCurrentProcess/MAX_PROCESS)*height;
		//右上角
		mProcessPath.moveTo(width, y);
		//右下角
		mProcessPath.lineTo(width, height);
		//左下角
		mProcessPath.lineTo(0, height);
		//左上角
		mProcessPath.lineTo(0, y);
		//上面的波浪线
		
		if(isSingleTap){//单击 波浪线上下抖动逐渐平静
			float tempAmplitude = (1-(float)mCurrentCount/VIBRATE_COUNT)*StartAmplitude;
			Log.d(TAG,"onDraw tempAmplitude=" +tempAmplitude + " mCurrentCount=" + mCurrentCount);
			for(int i=0; i<5; i++){
				if(mCurrentCount%2 == 0){//根据当前次数的奇偶来上下震动
					//先上后下
					mProcessPath.rQuadTo(StartAmplitude, -tempAmplitude, 2*StartAmplitude, 0);
					mProcessPath.rQuadTo(StartAmplitude, tempAmplitude, 2*StartAmplitude, 0);
				}else{
					//先下后上
					mProcessPath.rQuadTo(StartAmplitude, tempAmplitude, 2*StartAmplitude, 0);
					mProcessPath.rQuadTo(StartAmplitude, -tempAmplitude, 2*StartAmplitude, 0);
				}
			}
		}else{//双击实现逐渐注入效果:随注入的进度波浪逐渐变小
			float tempAmplitude = (1-(float)mCurrentProcess/mTargetPorcess)*StartAmplitude;
			for(int i=0; i<5; i++){
				//先上后下
				mProcessPath.rQuadTo(StartAmplitude, -tempAmplitude, 2*StartAmplitude, 0);
				mProcessPath.rQuadTo(StartAmplitude, tempAmplitude, 2*StartAmplitude, 0);
			}
		}  
	
		mProcessPath.close();
		mBitMapCanvas.drawPath(mProcessPath, mProcessPaint);
		
		//进度百分比
		String text = mCurrentProcess +"%";
		float text_width = mTextPaint.measureText(text);
		float x = (width-text_width)/2;
		FontMetrics fontMetrics = mTextPaint.getFontMetrics();
		float baseLine = height/2 - (fontMetrics.ascent + fontMetrics.descent);
		mBitMapCanvas.drawText(text, x, baseLine, mTextPaint);
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));
		canvas.drawBitmap(bitmap, 0, 0, null);
		
	}
	
}
