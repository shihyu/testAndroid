package com.example.demo.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/** 
 * @ClassName: BootBroadcastReceiver 
 * @author 
 * @date 2015年8月24日 上午9:43:21 
 *  
 */
public class BootBroadcastReceiver extends BroadcastReceiver {

	private static final String TAG = "BootBroadcastReceiver";
	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG,"onReceive---");

	}

}
