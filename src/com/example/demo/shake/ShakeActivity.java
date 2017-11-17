package com.example.demo.shake;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.demo.R;


public class ShakeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.shake_layout);
		TextView tv_shake = (TextView)this.findViewById(R.id.tv_shake);
		Button bt_shake = (Button)this.findViewById(R.id.bt_shake);
		bt_shake.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 TextView tv_shake = (TextView) ShakeActivity.this.findViewById(R.id.tv_shake);
				 Animation cycleAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_x);
				 tv_shake.startAnimation(cycleAnim);
			}
		});
	}
}
