package com.doov.floatball;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class StartFloatBallService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		ViewManager viewManager = ViewManager.getInstance(this);
		viewManager.showFloatBall();
		
	}
}

