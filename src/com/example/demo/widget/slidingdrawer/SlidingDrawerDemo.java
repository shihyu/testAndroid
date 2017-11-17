package com.example.demo.widget.slidingdrawer;

import android.app.Activity;
import android.app.AlarmManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.example.demo.R;


public class SlidingDrawerDemo extends Activity {
	private String TAG= "SlidingDrawerDemo";
	private SlidingDrawer mDrawer;
	private Button imbg;
	private Boolean flag = false;
	private TextView tv;

	private AlarmManager am;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sildingdrawer);

		
		imbg = (Button) findViewById(R.id.handle);
		mDrawer = (SlidingDrawer) findViewById(R.id.slidingdrawer);
		tv = (TextView) findViewById(R.id.tv);


		mDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				flag = true;
			}

		});

		mDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {

			@Override
			public void onDrawerClosed() {
				flag = false;
				// imbg.setImageResource(R.drawable.up);
			}

		});

		mDrawer.setOnDrawerScrollListener(new SlidingDrawer.OnDrawerScrollListener() {

			@Override
			public void onScrollEnded() {
				tv.setText("结束拖动");

			}

			@Override
			public void onScrollStarted() {
				tv.setText("开始拖动");

			}

		});

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}

}