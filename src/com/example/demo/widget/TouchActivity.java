/**   
* @Title: TouchActivity.java
* @Package com.example.demo.widget.activity 
* @author
* @date 2015年11月21日 上午10:38:57 
* @version V1.0   
*/
package com.example.demo.widget;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

import com.example.demo.R;


public class TouchActivity extends Activity implements OnClickListener,OnTouchListener{

	private DoovButton mButton;
	private DoovLinearLayout mLinearLayout;
	private static final String TAG = "DoovActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.test_doov_view);
		mLinearLayout = (DoovLinearLayout)this.findViewById(R.id.doov_ll);
		mButton = (DoovButton)this.findViewById(R.id.doov_button);
		mLinearLayout.setOnClickListener(this);
		mLinearLayout.setOnTouchListener(this);
		//mButton.setOnClickListener(this);
		//mButton.setOnTouchListener(this);
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.d(TAG,"dispatchTouchEvent" + " action=" + ev.getAction());
		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG,"onTouchEvent" + " action=" + event.getAction());
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.d(TAG,"onTouch" + " action=" + event.getAction() + " v=" + v);
		return false;
	}

	@Override
	public void onClick(View v) {
		//Log.d(TAG,"onClick " +"v=" + v);
		if(v.getId() == R.id.doov_id3){
			v.scrollBy(0, 100);
			Log.d(TAG,"onClick " +"v.y" + v.getY());
			
		}
		
	}

	@Override
	public void onUserInteraction() {
		Log.d(TAG,"onUserInteraction");
		super.onUserInteraction();
	}
	
	
}
