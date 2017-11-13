package com.doov.floatball;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.demo1.R;


public class FloatBallActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_floatball);
		
		startService(new Intent(this, StartFloatBallService.class));
	}

	
}
