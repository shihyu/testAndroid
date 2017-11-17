package com.doov.floatball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.View;

import com.example.demo.R;


public class FloatBallView extends View {
	private final String TAG = "FloatBallView";
	private Bitmap bitmap;
	public int width;
	public int height;
	
	private boolean isDrag = false;
	private Paint mPercentPaint ;
	private Paint mCirclePaint;
	private String mText = "50%";
	
	public FloatBallView(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public FloatBallView(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context,attrs,defStyleAttr,0);
	}

	public FloatBallView(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}

	public FloatBallView(Context context) {
		this(context,null);
		init();
	}

	private void init() {
		width = height= 150;
		
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.float_speed_up);
		bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
		
		mCirclePaint = new Paint();
		mCirclePaint.setColor(Color.GRAY);
		mCirclePaint.setAlpha(200);
		mCirclePaint.setAntiAlias(true);
		
		mPercentPaint = new Paint();
		mPercentPaint.setColor(Color.WHITE);
		mPercentPaint.setAntiAlias(true);
		mPercentPaint.setFakeBoldText(true);
		mPercentPaint.setTextSize(40);
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(width, height);
	} 
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if(isDrag){//拖动时显示小火箭
			canvas.drawBitmap(bitmap, 0, 0, null);
		}else{
			canvas.drawCircle(width/2, height/2, width/2, mCirclePaint);
			float textWidth = mPercentPaint.measureText(mText);
			FontMetrics fontMetrics = mPercentPaint.getFontMetrics();
			float dy = -(fontMetrics.ascent + fontMetrics.descent);
			canvas.drawText(mText, (width-textWidth)/2, height/2+dy, mPercentPaint);
		}
	}
	
	
	public void setDrag(boolean drag){
		isDrag = drag;
		invalidate();
	}
}
