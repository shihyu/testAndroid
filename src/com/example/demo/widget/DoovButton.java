/**   
* @Title: DoovButton.java 
* @Package com.example.demo.widget 
* @author
* @date 2015年11月21日 上午10:28:17 
* @version V1.0   
*/
package com.example.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

public class DoovButton extends Button {
	private static final String TAG = "DoovButton";
	
	public DoovButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	 
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		boolean result = super.dispatchTouchEvent(event);
		Log.d(TAG,"dispatchTouchEvent()" + " action=" + event.getAction() + " result=" + result);
		return result;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean result = super.onTouchEvent(event);
		Log.d(TAG,"onTouchEvent()"+ " action=" + event.getAction()  + " result=" + result);
		return result;
	}
}
