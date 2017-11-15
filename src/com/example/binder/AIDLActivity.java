package com.example.binder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.aidl.Book;
import com.example.aidl.IBookManager;
import com.example.aidl.IOnNewBookListener;
import com.example.demo1.R;

public class AIDLActivity extends Activity implements OnClickListener{
	private static final String TAG = "AIDLActivity";
	private IBookManager mIBookManager = null;
	private int i = 0;
	private TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_binder);
		Button send = (Button)findViewById(R.id.bt_send);
		send.setOnClickListener(this);
		tv = (TextView)findViewById(R.id.tv_msg_response);
		
		Intent intent = new Intent(this, ServerService.class);
		bindService(intent, sc, Context.BIND_AUTO_CREATE);
	}

	ServiceConnection sc = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mIBookManager = null;
			
			try {
				mIBookManager.unregistenerListener(mIOnNewBookListener);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mIBookManager = IBookManager.Stub.asInterface(service);
			try {
				mIBookManager.registerListener(mIOnNewBookListener);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	};
	
	private IOnNewBookListener mIOnNewBookListener = new IOnNewBookListener.Stub() {
		@Override
		public void onNewBook(Book newBook) throws RemoteException {
			Log.d(TAG," " + Thread.currentThread().getName());
			tv.setText("第" + i + "本书添加成功,名称是:"+ newBook.bookName + " " + Thread.currentThread().getName());
		}
	};

	@Override
	public void onClick(View v) {
		try {
			i++;
			mIBookManager.addBook(new Book(1, "第" + i + "本书"));
		} catch (RemoteException e) {
			e.printStackTrace();
			i--;
		}
	}
	
	@Override
	protected void onDestroy() {
		try {
			if(mIBookManager != null && mIBookManager.asBinder().isBinderAlive()){
				mIBookManager.unregistenerListener(mIOnNewBookListener);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		unbindService(sc);
		super.onDestroy();
	}
}
