package com.example.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {

	private static final String TAG = "MessengerService";
	private final int ADDBOOK = 1001;
	private final int ADDBOOK_SUCCESSED = 1002;
	
	private Messenger mMessenger = new Messenger(new MessengerHandler());
	
	@Override
	public IBinder onBind(Intent intent) {
		return mMessenger.getBinder();
	}

	private class MessengerHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ADDBOOK:
				Bundle bundle = msg.getData();
				String name = bundle.getString("name");
				Log.d(TAG,"add book name=" + name);
				
				Messenger client =  msg.replyTo;
				Message relpyMessage = Message.obtain(null, ADDBOOK_SUCCESSED);
				Bundle b = new Bundle();
				b.putString("reply_msg", "已經接到你的要求并处理!");
				relpyMessage.setData(b);
				try {
					client.send(relpyMessage);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				break;

			default:
				super.handleMessage(msg);
				break;
			}
		}
	}
	
	 
}
