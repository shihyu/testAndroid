package com.example.demo.handler;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.demo.R;

public class HandlerActivity extends Activity {
	private final static String TAG = "HandlerActivity";
	private Handler mHandler;
	
	private MyUI mMyUI = new MyUI();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_handler);
		mHandler = new MyHandler();
		Log.d(TAG,"HandleActivity,thread:" + Thread.currentThread().getName());
		new Thread(new Runnable() {
			@Override
			public void run() {
				Log.d(TAG,"Thread run,thread:" + Thread.currentThread().getName());
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						Log.d(TAG,"Thread run mHandler post,thread:" + Thread.currentThread().getName());
						
					}
				});
			}
		}).run();
		
		sleep(200);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				Log.d(TAG,"Thread start,thread:" + Thread.currentThread().getName());
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						Log.d(TAG,"Thread start mHandler post 1,thread:" + Thread.currentThread().getName());
					}
				});
				
				//Looper.prepare(); 
				mHandler = new MyHandler(Looper.getMainLooper());
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						Log.d(TAG,"Thread start mHandler post 2,thread:" + Thread.currentThread().getName());
					}
				});
				//Looper.loop();  
			}
		}).start();
		
		sleep(200);
		
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				Log.d(TAG,"mHandler post,thread:" + Thread.currentThread().getName());
				
			}
		});
		
		mHandler.sendMessage(mHandler.obtainMessage(1101));
		
	}
	public void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	class MyHandler extends Handler{
		public MyHandler(Looper mainLooper) {
			super(mainLooper);
		}

		public MyHandler() {
			super();
		}
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1101:
				Log.d(TAG,"handleMessage,thread:" + Thread.currentThread().getName());
				break;
			default:
				break;
			}
		}
	}
	
	class MyUI {
		public void getCurrentThreadName(){
			Log.d(TAG,"getCurrentThreadName,thread:" + Thread.currentThread().getName());
		}
	}
}
