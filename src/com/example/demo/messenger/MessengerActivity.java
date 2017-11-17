package com.example.demo.messenger;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.demo.R;

public class MessengerActivity extends Activity implements OnClickListener{
	private static final String TAG = "MessengerActivity";
	private static final int ADDBOOK = 1001;
	private static final int ADDBOOK_SUCCESSED = 1002;
	private TextView tv;
	
	private Messenger mGetReplyMessenger = new Messenger(new MessengerReplyHandler());
	private Messenger mService; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_binder);
		Button send = (Button)findViewById(R.id.bt_send);
		send.setOnClickListener(this);
		tv = (TextView)findViewById(R.id.tv_msg_response);
		
		bindService(new Intent(this, MessengerService.class), mConn, Context.BIND_AUTO_CREATE);
	}
	
	private ServiceConnection mConn = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = new Messenger(service);
			
		}
	};

	@Override
	public void onClick(View v) {
		Message m = Message.obtain(null, ADDBOOK);
		Bundle b = new Bundle();
		b.putString("name", "书名:Android xxx");
		m.setData(b);
		m.replyTo = mGetReplyMessenger;
		try {
			mService.send(m);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	class MessengerReplyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			 switch (msg.what) {
			case ADDBOOK_SUCCESSED:
				String response = msg.getData().getString("reply_msg");
				Log.d(TAG, "response=" + response);
				tv.setText(response);
				break;

			default:
				super.handleMessage(msg);
				break;
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		unbindService(mConn);
		super.onDestroy();
	}
	
}
