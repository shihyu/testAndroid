package com.doov.guaguaka;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.demo1.R;

public class GuaView extends View {
	private final static String TAg = "GuaView";
	private String mText ;
	private int mTextColor;
	private int mTextSize;
	
	private Paint mTextPaint;
	private Rect mTextBounds;
	
	
	private Canvas mCanvas;
	private Bitmap mBitmap;
	private Paint mOutPaint;
	
	private Path mPath;
	private int mLastX;
	private int mLastY;
	
	private boolean mComplete ;
	
	private GuaViewCompleteListener mListener;
	
	public GuaView(Context context) {
		this(context,null);
	}

	public GuaView(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}

	public GuaView(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context,attrs,defStyleAttr,0);
	}

	public GuaView(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		
		init();
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GuaView, defStyleAttr, defStyleRes);
		int number = a.getIndexCount();
		for (int i = 0; i < number; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.GuaView_text:
				mText = (String) a.getText(i);
				break;
			case R.styleable.GuaView_textColor:
				mTextColor = a.getColor(i, 0x000000);
				break;
			case R.styleable.GuaView_textSize:
				mTextSize = (int) a.getDimension(i, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 24, getResources().getDisplayMetrics()));
				break;

			default:
				break;
			}
		}
		
	}

	private void init() {
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mOutPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		mTextBounds = new Rect();
		
		mPath = new Path();
	}

	private void setTextPaint() {
		mTextPaint.setColor(mTextColor);
		mTextPaint.setStyle(Style.STROKE);
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);
	}
	
	private void setOutPaint(){
		mOutPaint.setColor(Color.DKGRAY);
		mOutPaint.setStyle(Style.FILL);
		mOutPaint.setDither(true);
		mOutPaint.setStrokeJoin(Paint.Join.ROUND);
		mOutPaint.setStrokeCap(Cap.ROUND);
		mOutPaint.setStrokeWidth(20);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//根据中间文字的来设置文字上层的涂层的尺寸
		setTextPaint();
		setMeasuredDimension((int)(mTextBounds.width()*1.5), (int)(mTextBounds.height()*1.5));
		
		int height = getMeasuredHeight();
		int width = getMeasuredWidth();
		
		mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		setOutPaint();
		mCanvas.drawRect(new Rect(0, 0, width, height), mOutPaint);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//绘制灰色背景
		canvas.drawColor(Color.LTGRAY);
		//绘制奖品说明:这里固定为谢谢惠顾
		canvas.drawText(mText, getWidth()/2 - mTextBounds.width()/2, getHeight()/2 + mTextBounds.height()/2, mTextPaint);

		if(!mComplete){
			drawPath();
			//绘制奖品上层的涂层
			canvas.drawBitmap(mBitmap,0,0,null);
		}
		if(mComplete){
			if(mListener != null){
				mListener.onComplete();
			}
		}
		
	}
	
	private void drawPath() {
		mOutPaint.setStyle(Style.STROKE);
		mOutPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
		mCanvas.drawPath(mPath, mOutPaint);
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastX = x;
			mLastY = y;
			mPath.moveTo(x, y);
			break;
		case MotionEvent.ACTION_MOVE:
			int dx = Math.abs(mLastX-x);
			int dy = Math.abs(mLastY-y);
			if(dx >5 || dy >5){
				mPath.lineTo(x, y);
			}
			mLastX = x;
			mLastY = y;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			if(!mComplete){
				new Thread(mRunnable).start();
			}
			break;

		default:
			break;
		}
		
		if(!mComplete){
			invalidate();
		}
		return true;
	}
	
	protected void setText(String mText) {
		this.mText = mText;
	}
	
	private Runnable mRunnable = new Runnable() {
		
		@Override
		public void run() {
			int w = getWidth();
			int h = getHeight();
			int wipe = 0;
			
			int[] pixels = new int[w*h];
			mBitmap.getPixels(pixels, 0, w, 0, 0, w, h);
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					int piex = pixels[i*h + j];
					if(piex == 0){
						wipe++;
					}
				}
			}
			
			if(wipe >0){
				if(wipe > w*h*0.6){
					mComplete = true;//已经刮的区域足够大,自动清除未刮的区域
					postInvalidate();
				}
			}
			
			
		}
	};

	
	public void setGuaViewCompleteListener(GuaViewCompleteListener listener){
		mListener =listener;
	}
	public interface GuaViewCompleteListener{
		public void onComplete();
	}
}
