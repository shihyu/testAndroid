package com.doov.luckypanel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.doov.luckypanel.LuckyPanelActivity.EndCallBack;

import com.example.demo1.R;

import java.util.Random;

public class LuckyPanel extends SurfaceView implements Callback ,Runnable{

	private final String TAG = "LuckyPanel";
	
	private SurfaceHolder mSurfaceHolder;
	private Canvas mCanvas;
	private Thread mThread;
	private boolean isRunning ;
	private int mPadding;
	/**
	 * 转盘的中心点(正方形)
	 */
	private float center;
	/**
	 * 转盘半径
	 */
	private float mRadius ;
	private int mItemCount = 6;
	
	/**
	 * 各盘块的颜色
	 */
	private int mItemColors [] = new int [] {0xffffc300, 0xfff17e01, 0xffffc300,
			0xfff17e01, 0xffffc300, 0xfff17e01 };
	
	/**
	 * 各盘块的奖项图片资源ID
	 */
	private int [] mImagsResID = new int[]{R.drawable.danfan,R.drawable.ipad,R.drawable.xialian,R.drawable.iphone,
			R.drawable.meizi,R.drawable.xialian};
	
	/**
	 * 各个奖项出现的概率段
	 * 单反:0.2%
	 * IPad: 0.7
	 * 恭喜发财:59%
	 * Iphone: 5%
	 * 服装一套:15%
	 * 恭喜发财:20%
	 */
	private int [] mPercents = new int[]{3,10,600,650,800,1000};
	
	/**
	 * 各盘块奖项的描述
	 */
	private String[] mDescribe = new String[] {"单反相机","IPad","恭喜发财","IPhone 6","服装一套","恭喜发财"};
	private float mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());
	
	private Bitmap[] mImagsBitmap  ;
	
	private Bitmap mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);
	
	private Paint mTextPaint ;
	private Paint mArcPaint ;

	/**
	 * 整个转盘的矩形边框(不包括背景)
	 */
	private RectF mArcRectF;

	private float startAngle;
	
	/**
	 * 旋转速度
	 */
	private double speed = 0;
	
	
	/**
	 * 是否准备停止
	 */
	private boolean mShouldStop;

	private LuckyPanelActivity.EndCallBack mEndCallBack;

	private int index;
	
	
	public LuckyPanel(Context context, AttributeSet attrs) {
		super(context,attrs);
		
		mSurfaceHolder = this.getHolder();
		mSurfaceHolder.addCallback(this);
		
		setFocusable(true);
		setFocusableInTouchMode(true);
		
		//设置常亮
		setKeepScreenOn(true);
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		mImagsBitmap = new Bitmap[mItemCount];
		for (int i = 0; i < mImagsResID.length; i++) {
			mImagsBitmap[i] = BitmapFactory.decodeResource(getResources(), mImagsResID[i]);
		}
		
		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);//防锯齿
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(mTextSize);
		
		mArcPaint = new Paint();
		mArcPaint.setAntiAlias(true);
		mArcPaint.setDither(true);//防抖动
		
		mArcRectF = new RectF(mPadding,mPadding,mRadius*2 + mPadding ,mRadius*2+ mPadding);
		
		isRunning = true;
		mThread = new Thread(this);
		mThread.start();
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isRunning = false; 
		
	}

	@Override
	public void run() {
		while(isRunning){//不断进行绘制
			int startTime = (int) System.currentTimeMillis();
			drawPanel();
			int endTime = (int) System.currentTimeMillis();
			if(endTime-startTime <50){//最少要50ms 才能绘制一次
				try {
					Thread.sleep(50-(endTime-startTime));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		//取宽高的最小值,并设置为相同的宽高的矩形
		int width = Math.min(getMeasuredHeight(), getMeasuredWidth());
		setMeasuredDimension(width, width);
		
		mPadding = getPaddingLeft();
		center = width/2;
		mRadius = (width-mPadding*2)/2;
		
	}

	private void drawPanel() {
		mCanvas = mSurfaceHolder.lockCanvas();
		if(mCanvas != null){
			try {
				drawBg();//绘制转盘背景

				//绘制各盘块
				float tmepstartAngle = startAngle;
				int sweepAngle = 360/mItemCount;//单个盘块的弧度
				
				for (int i = 0; i < mItemCount; i++) {
					mArcPaint.setColor(mItemColors[i]);
					//绘制盘块
					mCanvas.drawArc(mArcRectF,tmepstartAngle , sweepAngle, true, mArcPaint);
					
					//绘制奖项的文字描述
					drawText(tmepstartAngle,sweepAngle,mDescribe[i]);
					
					//绘制奖项的图片
					drawIcon(tmepstartAngle,sweepAngle,mImagsBitmap[i]);
					
					tmepstartAngle += sweepAngle;
					
				}
				startAngle += speed;
				if(mShouldStop){
					speed -=1;
				}
				if(speed<=0){
					speed = 0;
					if(mShouldStop && mEndCallBack != null){
						//mEndCallBack.onEnd(mDescribe[index]);
						post(new Runnable() {
							@Override
							public void run() {
								mEndCallBack.onEnd(mDescribe[index]);
								
							}
						});
					}
					mShouldStop = false;
				}
				//Log.d(TAG,"drawPanel startAngle="+ startAngle +" speed=" + speed + " mShouldStop=" + mShouldStop);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(mCanvas != null){                          
					mSurfaceHolder.unlockCanvasAndPost(mCanvas);
				}
			}
		}
	}


	private void drawIcon(float tmepstartAngle, int sweepAngle,
			Bitmap  mImag) {
		
		//图片的宽高固定为半径的1/4
		int iconWidth = (int) (mRadius/4);
		
		float angle = (float) ((tmepstartAngle + sweepAngle/2)*Math.PI/180);//多少个π
		
		//图片的中心位置
		int x = (int) (center + mRadius/2*Math.cos(angle));
		int y = (int) (center + mRadius/2*Math.sin(angle));
		
		Rect rect = new Rect(x-iconWidth/2, y-iconWidth/2, x+iconWidth/2, y+iconWidth/2);
		mCanvas.drawBitmap(mImag, null, rect, null);
	}


	private void drawText(float tmepstartAngle, int sweepAngle,String text) {
		Path path = new Path();
		path.addArc(mArcRectF, tmepstartAngle, sweepAngle);
		
		float textWidth = mTextPaint.measureText(text);
		float hOffset = (float) ((sweepAngle*Math.PI * mRadius/180)- textWidth)/2; //弧形长度:nπr/180
		mCanvas.drawTextOnPath(text, path, hOffset, mRadius/4, mTextPaint);
	}


	/**
	 * 绘制转盘背景
	 */
	private void drawBg() {
		mCanvas.drawColor(Color.WHITE);//先设置一个白色的背景
		mCanvas.drawBitmap(mBgBitmap, null, new Rect(mPadding/2, mPadding/2, getMeasuredWidth()-mPadding/2, getMeasuredHeight()-mPadding/2), null);
	}

	
	public void startSpin(){
		mShouldStop = false;
		
		Random random = new Random();
		int r = random.nextInt(1000);
		float itemAngle = 360/mItemCount;
		Log.d(TAG,"startSpin r=" + r + " speed=" + speed); 
		index = getCurrentIndex(r);
		/**
		 * 计算每项奖项中奖需要旋转的角度范围
		 * 1:210~270
		 * 2:150~210
		 */
		float fromAngle = 210- index*itemAngle;
		float endAngle = 270 - index*itemAngle;
		
		/**
		 * 转盘停止时需要继续转动的距离
		 */
		float tagrgetFrom = 2*360 + fromAngle;
		float targetEnd = 2*360 + endAngle;
		
		/**
		 * 转盘点击停止时会逐渐减小旋转的速度
		 * 速度范围
		 * (0+V1)(V1+1)/2=tagrgetFrom   -> V1^2 + V1 -2t = 0
		 * (0+V2)(V2+1)/2=targetEnd
		 * 
		 * 一元二次方程的标准形式是ax^2+bx+c=0（a，b，c为常数，x为未知数，且a≠0）。
		 * 求根公式：x=[-b±√(b^2-4ac)]/2a。
		 */
		 
		float v1 = (float) ((-1+Math.sqrt(1+4*2*tagrgetFrom))/2);
		float v2 = (float) ((-1+Math.sqrt(1+4*2*targetEnd))/2);
		
		speed = v1 + Math.random()*(v2-v1);//实际速度取最大值和最小值之间的一个随机值
		Log.d(TAG,"startSpin index=" + index + " fromAngle=" + fromAngle + " endAngle=" + endAngle + 
				" v1=" + v1 + " v2="  + v2 + " speed=" +speed);
	}
	
	public void stopSpin(){
		startAngle = 0;
		mShouldStop = true;
	}
	
	private int getCurrentIndex(int r) {
		for (int i = 0; i < mPercents.length; i++) {
			if(r <= mPercents[i]) return i;
		}
		return 0;
	}

	public boolean isRunning() {
		return speed > 0;
	}

	public boolean isShoudStop(){
		return mShouldStop;
	}
	
	public void setEndCallBack(EndCallBack endCallBack){
		mEndCallBack = endCallBack;
	}
}
