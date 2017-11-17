package com.example.demo.progressbar;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.example.demo.R;

public class HorizontalProgressBar extends ProgressBar {
	
	private  final String TAG = "HorizontalProgressBar";
	
	private int DEFAULT_TEXT_COLOR = 0xFFFF0000;
	private int DEFAULT_TEXT_SIZE = 20;//sp
	private int DEFAULT_TEXT_OFFSET = 10;//dp
	private int DEFAULT_REACH_COLOR = DEFAULT_TEXT_COLOR;
	private int DEFAULT_UNREACH_COLOR = 0x44FF0000;
	private int DEFAULT_REACH_HEIGHT = 2;//dp
	private int DEFAULT_UNREACH_HEIGHT = 4;//dp

	protected int text_color;

	protected int text_size;

	//文字的间距(左右的间距的和)
	protected int text_offset;

	protected int reach_color;

	protected int reach_height;

	protected int unreach_color;

	protected int unreach_height;
	
	protected Paint mPaint = new Paint();

	private int realWidth;
	
	public HorizontalProgressBar(Context context) {
		this(context,null);
	}

	public HorizontalProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}

	public HorizontalProgressBar(Context context, AttributeSet attrs,
			int defStyleAttr) {
		this(context, attrs, defStyleAttr,0);
		// TODO Auto-generated constructor stub
	}

	public HorizontalProgressBar(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		
		obtainStyleAttributes(context, attrs);
		
		mPaint.setTextSize(sp2px(text_size));
	}

	public void obtainStyleAttributes(Context context, AttributeSet attrs) {
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgressBar);
		text_color = ta.getColor(R.styleable.HorizontalProgressBar_text_color, DEFAULT_TEXT_COLOR);
		text_size = (int) ta.getDimension(R.styleable.HorizontalProgressBar_text_size, sp2px(DEFAULT_TEXT_SIZE));
		text_offset = (int) ta.getDimension(R.styleable.HorizontalProgressBar_text_offset, dp2px(DEFAULT_TEXT_OFFSET));
		reach_color = ta.getColor(R.styleable.HorizontalProgressBar_reach_color, DEFAULT_REACH_COLOR);
		reach_height = (int) ta.getDimension(R.styleable.HorizontalProgressBar_reach_height, dp2px(DEFAULT_REACH_HEIGHT));
		unreach_color = ta.getColor(R.styleable.HorizontalProgressBar_unreach_color, DEFAULT_UNREACH_COLOR);
		unreach_height = (int) ta.getDimension(R.styleable.HorizontalProgressBar_unreach_height, dp2px(DEFAULT_UNREACH_HEIGHT));
		ta.recycle();
	}

	
	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		
		heightSize = measureHeight(heightSize,heightMode);
		 
		setMeasuredDimension(widthSize, heightSize);
		
		realWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
	}
	
	@Override
	protected synchronized void onDraw(Canvas canvas) {
		 
		canvas.save();
		canvas.translate(getPaddingLeft(), getHeight()/2);
		
		//是否需要绘制未完成的进度条部分
		boolean noNeedUnReach = false;
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		int progress = getProgress();
		String text = progress + "%";
		float textWidth = mPaint.measureText(text);
		float progressX = realWidth * progress/getMax() ;
		if(progressX + textWidth + text_offset/2 > realWidth){//默认情况下已完成的结束点坐标只减文字的偏移量,不减文字的宽度
			progressX = realWidth - (textWidth + text_offset/2);
			noNeedUnReach = true;
		}
		float stopX = noNeedUnReach?progressX:(progressX - textWidth/2 - text_offset/2);//默认情况下只减文字间距的一般
		
		//绘制已完成进度
		if(stopX > 0){
			mPaint.setColor(reach_color);
			mPaint.setStrokeWidth(reach_height);
			canvas.drawLine(0, 0, stopX, 0, mPaint);
		}
		
		mPaint.setColor(text_color);
		mPaint.setTextSize(text_size);
		//绘制进度文字
		float text_start = noNeedUnReach?(progressX + text_offset/2):(progressX - textWidth/2 - text_offset/2);
		canvas.drawText(text, text_start>0?text_start:0, -(mPaint.descent()+mPaint.ascent())/2, mPaint);
		
		//绘制未完成进度
		if(!noNeedUnReach){
			mPaint.setStrokeWidth(unreach_height);
			mPaint.setColor(unreach_color);
			canvas.drawLine(progressX + textWidth/2 + text_offset/2  , 0, realWidth, 0, mPaint);
		}
		
		canvas.restore();
	}
	
	private int measureHeight(int heightSize, int heightMode) {
		int result = 0;
		int textHeight = (int) Math.ceil(mPaint.descent() - mPaint.ascent());
		
		if(heightMode == MeasureSpec.EXACTLY){
			return heightSize;
		}else{
			result = getPaddingTop() + getPaddingBottom() + 
					Math.max(Math.max(unreach_height, reach_height),textHeight);
			if(heightMode == MeasureSpec.AT_MOST){
				result = Math.min(result, heightSize);
			}
		}
		
		return result;
	}

	
	
	public int dp2px(int value){
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
		
	}
	public int sp2px(int value){
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, getResources().getDisplayMetrics());
		
	}
}
