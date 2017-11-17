package com.example.demo.progressbar;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import com.example.demo.R;

public class CircleProgressBar extends HorizontalProgressBar {
	
	private  final String TAG = "CircleProgressBar";
	
	private int DEFAULT_RADIUS = 36;//dp
	private int mRadius;
	private int maxPaintHeight;
	
	public CircleProgressBar(Context context) {
		this(context,null);
	}

	public CircleProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}

	public CircleProgressBar(Context context, AttributeSet attrs,
			int defStyleAttr) {
		this(context, attrs, defStyleAttr,0);
		// TODO Auto-generated constructor stub
	}

	public CircleProgressBar(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
		mRadius = (int) ta.getDimension(R.styleable.CircleProgressBar_radius, dp2px(DEFAULT_RADIUS));
		ta.recycle();
		
		maxPaintHeight = Math.max(reach_height, unreach_height);
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		
		int except = mRadius*2 + maxPaintHeight + getPaddingLeft() + getPaddingRight();//期望的宽高大小
		
		//这里对参数进行处理,避免出现except 太大等异常情况
		int width = resolveSize(except, widthMeasureSpec);
		int height = resolveSize(except, heightMeasureSpec);
		
		int realWidth = Math.min(width, height);
		
		//重新调整以后的半径
		mRadius = (realWidth - getPaddingLeft() - getPaddingRight() - maxPaintHeight)/2;
		
		setMeasuredDimension(realWidth, realWidth);
	}
	
	@Override
	protected synchronized void onDraw(Canvas canvas) {
		Log.d(TAG,"onDraw");
		canvas.save();
		canvas.translate(maxPaintHeight/2+ getPaddingLeft(), maxPaintHeight/2+getPaddingTop());
		
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setStrokeCap(Cap.ROUND);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(unreach_height);
		mPaint.setColor(unreach_color);
		//绘制圆
		canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
		
		float sweepAngle = getProgress()*1.0f/getMax() *360;
		RectF oval = new RectF(0,0,2*mRadius ,2*mRadius);
		mPaint.setColor(reach_color);
		mPaint.setStrokeWidth(reach_height);
		//绘制弧形
		canvas.drawArc(oval, 0, sweepAngle, false, mPaint);
		
		String text = getProgress() + "%";
		mPaint.setTextSize(text_size);
		mPaint.setColor(text_color);
		mPaint.setStyle(Style.FILL);
		float textWidth = mPaint.measureText(text);
		float x = mRadius - textWidth/2;
		float y = mRadius - (mPaint.descent()+ mPaint.ascent()/2);
		//绘制文字百分比
		canvas.drawText(text, x, y, mPaint);
		
		canvas.restore();
	}
	
}
