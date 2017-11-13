package com.doov.wuzhiqi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.demo1.R;

import java.util.ArrayList;
import java.util.List;


public class WuZhiQiPanel extends View {
	
	private final String TAG = "WuZhiQiPanel";
	
	private int mPanelHeight; //棋盘的宽高
	private float mLineHeight; //棋盘单行的宽高
	private int MAX_LINE; //棋盘行列数
	private Paint mLinePaint;  //棋盘画笔
	private Paint mSuccessLinePaint;  //棋盘画笔

	private ArrayList<Point> mBlackArray;
	private ArrayList<Point> mWhiteArray;
	private boolean mIsWhite = true;
	
	private Bitmap mWhitePiece;
	private Bitmap mBlackPiece;
	private float RADIO_PIECE_OF_LINEH_EIGHT = 0.8f;
	
	private final int MAX_COUNT_PIECE_ONE_LINE = 5;
	
	private boolean isGameOver = false;
	/**
	 * 0:未分胜负
	 * 1:白棋子胜
	 * 2:黑其中胜
	 */
	private int mGmaeWinStatus ;
	public static final int WHITE_WIN = 1;
	public static  final int BLACK_WIN = 2;
	private GameChangeStatusListener mGameChangeStatusListener ;
	
	private ArrayList<Point> mSuccessPoints = new ArrayList<Point>();
	
	public WuZhiQiPanel(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		init();
	}

	public void init() {
		MAX_LINE = 11;
		mLinePaint = new Paint();
		mLinePaint.setColor(Color.GRAY);
		mLinePaint.setAntiAlias(true);
		
		mSuccessLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mSuccessLinePaint.setColor(Color.RED);
		mSuccessLinePaint.setStrokeWidth(3);
		 
		mBlackArray = new ArrayList<Point>();
		mWhiteArray = new ArrayList<Point>();
		
		mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
		mBlackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);
		
	}

	public WuZhiQiPanel(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr, 0);
	}

	public WuZhiQiPanel(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WuZhiQiPanel(Context context) {
		this(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.d(TAG, "onMeasure");
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		widthSize = Math.min(widthSize, heightSize);
		
		setMeasuredDimension(widthSize, widthSize);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.d(TAG, "onSizeChanged w=" +w+ " h=" + h + " oldw=" + oldw + " oldh=" + oldh);
		
		mPanelHeight = w;
		mLineHeight = w/MAX_LINE;
		
		mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, (int)(mLineHeight*RADIO_PIECE_OF_LINEH_EIGHT), 
				(int)(mLineHeight*RADIO_PIECE_OF_LINEH_EIGHT), true);
		
		mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece, (int)(mLineHeight*RADIO_PIECE_OF_LINEH_EIGHT), 
				(int)(mLineHeight*RADIO_PIECE_OF_LINEH_EIGHT), true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.d(TAG, "onDraw");
		
		
		drawBoard(canvas);
		drawPieces(canvas);
		
		checkGameOver();
		
		if(isGameOver){
			if(mSuccessPoints != null)drawSuccessLine(canvas);
			Toast.makeText(getContext(), "游戏结束", Toast.LENGTH_LONG).show();
			/*postDelayed(new Runnable() {
				
				@Override
				public void run() {
					mGameChangeStatusListener.onGameOver(mGmaeWinStatus);
					
				}
			},2000);*/
			
		}
	}

	public void reStartGame() {
		isGameOver = false;
		mWhiteArray.clear();
		mBlackArray.clear();
		invalidate();
	}
	
	/**
	 * 检查游戏是否结束:是否有5个同色棋子连接
	 */
	private void checkGameOver() {
		boolean isWhiteWin = checkPiecesInLine(mWhiteArray);
		mGmaeWinStatus = isWhiteWin?0:WHITE_WIN;
		isGameOver = isWhiteWin;
		if(!isWhiteWin){//白棋未胜利,再检查黑棋
			boolean isBlackWin = checkPiecesInLine(mBlackArray);
			mGmaeWinStatus = isBlackWin?0:BLACK_WIN;
			isGameOver = isWhiteWin || isBlackWin;
		}
		
	}

	/**
	 * 是否有5个同色棋子连接(4个方向有一个方向有5个就返回true)
	 * @param list
	 */
	private boolean checkPiecesInLine(List<Point> list) {
		if(list.size() <=0){
			return false;
		}
		boolean vertiaclInMax = checkVerticalLine(list);
		if(vertiaclInMax) return vertiaclInMax;
		
		boolean horizontalInMax = checkHorizontalLine(list);
		if(horizontalInMax) return horizontalInMax;
		
		boolean leftDiagonalInMax = checkLeftDiagonalLine(list);
		if(leftDiagonalInMax) return leftDiagonalInMax;
		
		boolean rightDiagonalInMax = checkRightDiagonalLine(list);
		return vertiaclInMax || horizontalInMax || leftDiagonalInMax || rightDiagonalInMax;
	}

	/**
	 * 检查右斜方向:这里只检查列表中最后一个其中的周围5*5 范围内
	 * @param list
	 * @return
	 */
	private boolean checkRightDiagonalLine(List<Point> list) {
		Point lastPoint = list.get(list.size()-1);
		mSuccessPoints.add(lastPoint);
		int countSameColor = 1;
		for (int i = 1; i < MAX_COUNT_PIECE_ONE_LINE; i++) {//右斜上
			Point p = new Point(lastPoint.x+i,lastPoint.y-i);
			if(list.contains(p)){
				countSameColor ++;
				mSuccessPoints.add(p);
			}else{
				break;
			}
		}
		if(countSameColor >= MAX_COUNT_PIECE_ONE_LINE){
			return true;
		}
		for (int i = 1; i < MAX_COUNT_PIECE_ONE_LINE; i++) {//右斜下
			Point p = new Point(lastPoint.x-i,lastPoint.y+i);
			if(list.contains(p)){
				countSameColor ++;
				mSuccessPoints.add(p);
			}else{
				break;
			}
		}
		if(countSameColor >= MAX_COUNT_PIECE_ONE_LINE){
			return true;
		}
		mSuccessPoints.clear();
		return false;
		
	}

	/**
	 * 检查左斜方向:这里只检查列表中最后一个其中的周围5*5 范围内
	 * @param list
	 * @return
	 */
	private boolean checkLeftDiagonalLine(List<Point> list) {
		Point lastPoint = list.get(list.size()-1);
		mSuccessPoints.add(lastPoint);
		int countSameColor = 1;
		for (int i = 1; i < MAX_COUNT_PIECE_ONE_LINE; i++) {//左斜上
			Point p = new Point(lastPoint.x-i,lastPoint.y-i);
			if(list.contains(p)){
				countSameColor ++;
				mSuccessPoints.add(p);
			}else{
				break;
			}
		}
		if(countSameColor >= MAX_COUNT_PIECE_ONE_LINE){
			return true;
		}
		for (int i = 1; i < MAX_COUNT_PIECE_ONE_LINE; i++) {//左斜下
			Point p = new Point(lastPoint.x+i,lastPoint.y+i);
			if(list.contains(p)){
				countSameColor ++;
				mSuccessPoints.add(p);
			}else{
				break;
			}
		}
		if(countSameColor >= MAX_COUNT_PIECE_ONE_LINE){
			return true;
		}
		mSuccessPoints.clear();
		return false;
	}

	/**
	 * 检查水平方向:这里只检查列表中最后一个其中的周围5*5 范围内
	 * @param list
	 * @return
	 */
	private boolean checkHorizontalLine(List<Point> list) {
		Point lastPoint = list.get(list.size()-1);
		mSuccessPoints.add(lastPoint);
		int countSameColor = 1;
		for (int i = 1; i < MAX_COUNT_PIECE_ONE_LINE; i++) {//右
			Point p = new Point(lastPoint.x+i,lastPoint.y);
			if(list.contains(p)){
				countSameColor ++;
				mSuccessPoints.add(p);
			}else{
				break;
			}
		}
		if(countSameColor >= MAX_COUNT_PIECE_ONE_LINE){
			return true;
		}
		for (int i = 1; i < MAX_COUNT_PIECE_ONE_LINE; i++) {//左
			Point p = new Point(lastPoint.x-i,lastPoint.y);
			if(list.contains(p)){
				countSameColor ++;
				mSuccessPoints.add(p);
			}else{
				break;
			}
		}
		if(countSameColor >= MAX_COUNT_PIECE_ONE_LINE){
			return true;
		}
		mSuccessPoints.clear();
		return false;
	}

	/**
	 * 检查垂直方向:这里只检查列表中最后一个其中的周围5*5 范围内
	 * @param list
	 * @return
	 */
	private boolean checkVerticalLine(List<Point> list) {
		Point lastPoint = list.get(list.size()-1);
		mSuccessPoints.add(lastPoint);
		int countSameColor = 1;
		for (int i = 1; i < MAX_COUNT_PIECE_ONE_LINE; i++) {//下
			Point p = new Point(lastPoint.x,lastPoint.y+i);
			if(list.contains(p)){
				countSameColor ++;
				mSuccessPoints.add(p);
			}else{
				break;
			}
		}
		if(countSameColor >= MAX_COUNT_PIECE_ONE_LINE){
			return true;
		}
		for (int i = 1; i < MAX_COUNT_PIECE_ONE_LINE; i++) {//上
			Point p = new Point(lastPoint.x,lastPoint.y-i);
			if(list.contains(p)){
				countSameColor ++;
				mSuccessPoints.add(p);
			}else{
				break;
			}
		}
		if(countSameColor >= MAX_COUNT_PIECE_ONE_LINE){
			return true;
		}
		mSuccessPoints.clear();
		return false;
	}
	
	/**
	 * 黑白任意一方胜利,绘制五子连线
	 * @param canvas
	 */
	public void drawSuccessLine(Canvas canvas){
		Log.d(TAG,"drawSuccessLine mSuccessPoints.size=" + mSuccessPoints.size());
		 if(mSuccessPoints != null && mSuccessPoints.size() >= MAX_COUNT_PIECE_ONE_LINE){
			 Path path = new Path();
			 path.reset();
			 float lastX=0,lastY=0;
			 for (int i=0;i<mSuccessPoints.size();i++) {
				 Point p = mSuccessPoints.get(i);
				 float realX = getRealCoordinate(p, true);
				 float realY = getRealCoordinate(p, false);
				 if(i>0)canvas.drawLine(lastX, lastY, realX, realY, mSuccessLinePaint);
				 lastX = realX;
				 lastY = realY;
				 Log.d(TAG,"drawSuccessLine p=" + p.x + " p.y=" + p.y + " realX=" +realX + " realY=" +realY);
			}
		 }
	}

	/**
	 * 绘制黑白棋子
	 * @param canvas
	 */
	private void drawPieces(Canvas canvas) {
		for(Point blackPiece :mBlackArray){
			canvas.drawBitmap(mBlackPiece,(blackPiece.x+(1-RADIO_PIECE_OF_LINEH_EIGHT)/2)*mLineHeight, 
					(blackPiece.y+(1-RADIO_PIECE_OF_LINEH_EIGHT)/2)*mLineHeight, null);
		}
		
		for(Point whitePiece :mWhiteArray){
			canvas.drawBitmap(mWhitePiece,(whitePiece.x+(1-RADIO_PIECE_OF_LINEH_EIGHT)/2)*mLineHeight, 
					(whitePiece.y+(1-RADIO_PIECE_OF_LINEH_EIGHT)/2)*mLineHeight, null);
		}
	}

	/**
	 * 绘制棋盘
	 * @param canvas
	 */
	public void drawBoard(Canvas canvas) {
		float startX = mLineHeight/2;
		float stopX = mPanelHeight - mLineHeight/2;
		for (int i = 0; i < MAX_LINE; i++) {
			float y = (float) ((i+0.5)*mLineHeight);
			canvas.drawLine(startX, y, stopX, y, mLinePaint);//画棋盘的水平线
			canvas.drawLine(y, startX, y, stopX, mLinePaint);//画棋盘的垂直线
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if(isGameOver){
			return false;
		}
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			int x = (int) event.getX();
			int y = (int) event.getY();
			Point p = getValidatePoint(x, y);
			if(mWhiteArray.contains(p) || mBlackArray.contains(p))return false;
			
			if(mIsWhite){
				mWhiteArray.add(p);
			}else{
				mBlackArray.add(p);
			}
			invalidate();
			
			mIsWhite = !mIsWhite;
			break;

		default:
			break;
		}
		
		return true;
	}
	
	private Point getValidatePoint(int x,int y){
		return new Point((int)(x/mLineHeight), (int)(y/mLineHeight));
	}
	
	private float getRealCoordinate(Point p,boolean x_y){
		if(x_y){//x
			return (float) ((p.x + 0.5) * mLineHeight);
		}else{//y
			return (float) ((p.y+ 0.5) * mLineHeight);
		}
	} 
	
	private final String INSTANCE_GAME_OVER = "Instance_Game_Over";
	private final String INSTANCE_BLACK_PIECE = "Instance_Black_Piece";
	private final String INSTANCE_WHITE_PIECE = "Instance_White_Piece";
	private final String INSTANCE = "instance";
	
	/**
	 * 保存游戏数据
	 */
	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
		bundle.putBoolean(INSTANCE_GAME_OVER, isGameOver);
		bundle.putParcelableArrayList(INSTANCE_BLACK_PIECE, mBlackArray);
		bundle.putParcelableArrayList(INSTANCE_WHITE_PIECE, mWhiteArray);
		return bundle;
	}
	
	
	/**
	 * 恢复游戏数据
	 */
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if(state instanceof Bundle){
			Bundle bundle = (Bundle)state;
			mWhiteArray = bundle.getParcelableArrayList(INSTANCE_WHITE_PIECE);
			mBlackArray = bundle.getParcelableArrayList(INSTANCE_BLACK_PIECE);
			isGameOver = bundle.getBoolean(INSTANCE_GAME_OVER);
			super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
			return ;
		}
		super.onRestoreInstanceState(state);
	}
	
	public void setGameChangeStatusListener(GameChangeStatusListener listener){
		mGameChangeStatusListener = listener;
	}
}
