package com.example.progressbar;

import com.example.demo1.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ProgressActivity extends Activity {
	
	private HorizontalProgressBar mHorizontalProgressBar;
	private CircleProgressBar mcCircleProgressBar;
	private final  int MSG_UPDATE = 1001;
	
	private MyHandler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress_bar);
		mHorizontalProgressBar = (HorizontalProgressBar) this.findViewById(R.id.horizontal_progress_bar);
		mcCircleProgressBar = (CircleProgressBar) this.findViewById(R.id.circleprogressbar);
		handler = new MyHandler();
		handler.sendEmptyMessage(MSG_UPDATE);
	}
	
	class MyHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case MSG_UPDATE:
				int progress = mHorizontalProgressBar.getProgress();
				mHorizontalProgressBar.setProgress(++progress);
				int cirProgress = mcCircleProgressBar.getProgress();
				mcCircleProgressBar.setProgress(++cirProgress);
				handler.sendEmptyMessageDelayed(MSG_UPDATE,100);
				break;

			default:
				break;
			}
		}
		
	}

}
