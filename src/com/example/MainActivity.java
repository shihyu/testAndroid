package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.doov.floatball.FloatBallActivity;
import com.doov.guaguaka.GuaGuaKaActivity;
import com.doov.luckypanel.LuckyPanelActivity;
import com.doov.wuzhiqi.WuZhiQiActivity;
import com.example.animation.AnimationActivity;
import com.example.binder.BinderActivity;
import com.example.demo.widget.activity.TouchActivity;
import com.example.demo1.R;
import com.example.demo1.slidingdrawer.SlidingDrawerDemo;
import com.example.handler.HandlerActivity;
import com.example.horizontalscrollview.HorizontalscrollActivity;
import com.example.matrix.MatrixActivity;
import com.example.messenger.MessengerActivity;
import com.example.notification.NotificationGroupSummaryActivity;
import com.example.palette.PaletteActivity;
import com.example.preference.DoovPreferenceActivity;
import com.example.progressbar.ProgressActivity;
import com.example.shake.ShakeActivity;

public class MainActivity extends Activity implements OnClickListener{

	private final String TAG = "MainActivity";
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		Button notifications = (Button)findViewById(R.id.notifications);
		notifications.setOnClickListener(this);
		
		Button notification_group = (Button)findViewById(R.id.notification_group);
		notification_group.setOnClickListener(this);
		
		Button binder = (Button)findViewById(R.id.binder);
		binder.setOnClickListener(this);
		
		Button messenger = (Button)findViewById(R.id.messenger);
		messenger.setOnClickListener(this);
		
		Button animation = (Button)findViewById(R.id.animation);
		animation.setOnClickListener(this);
		
		Button palette = (Button)findViewById(R.id.palette);
		palette.setOnClickListener(this);
		
		Button shake = (Button)findViewById(R.id.shake);
		shake.setOnClickListener(this);
		
		Button touch = (Button)findViewById(R.id.touch);
		touch.setOnClickListener(this);
		
		Button handler = (Button)findViewById(R.id.handler);
		handler.setOnClickListener(this);
		
		Button preference = (Button)findViewById(R.id.preference);
		preference.setOnClickListener(this);
		
		Button horizontalscrollview_gridview = (Button)findViewById(R.id.horizontalscrollview_gridview);
		horizontalscrollview_gridview.setOnClickListener(this);
		
		Button progress_bar = (Button)findViewById(R.id.progress_bar);
		progress_bar.setOnClickListener(this);
		
		Button matrix = (Button)findViewById(R.id.matrix);
		matrix.setOnClickListener(this);

		Button floatball = (Button)findViewById(R.id.floatball);
		floatball.setOnClickListener(this);

		Button guaguaka = (Button)findViewById(R.id.guaguaka);
		guaguaka.setOnClickListener(this);

		Button wuzhiqi = (Button)findViewById(R.id.wuzhiqi);
		 wuzhiqi.setOnClickListener(this);

		Button lucky_panel = (Button)findViewById(R.id.lucky_panel);
		 lucky_panel.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		int id = v.getId();
		switch (id) {
		case R.id.notifications:
			intent.setClass(this, SlidingDrawerDemo.class);
			break;
		case R.id.notification_group:
			intent.setClass(this, NotificationGroupSummaryActivity.class);
			break;
		case R.id.binder:
			intent.setClass(this, BinderActivity.class);
			break;
		case R.id.messenger:
			intent.setClass(this, MessengerActivity.class);
			break;
		case R.id.animation:
			intent.setClass(this, AnimationActivity.class);
			break;
		case R.id.palette:
			intent.setClass(this, PaletteActivity.class);
			break;
		case R.id.shake:
			intent.setClass(this, ShakeActivity.class);
			break;
		case R.id.touch:
			intent.setClass(this, TouchActivity.class);
			break;
		case R.id.handler:
			intent.setClass(this, HandlerActivity.class);
			break;
		case R.id.preference:
			intent.setClass(this, DoovPreferenceActivity.class);
			break;
		case R.id.horizontalscrollview_gridview:
			intent.setClass(this, HorizontalscrollActivity.class);
			break;
		case R.id.progress_bar:
			intent.setClass(this, ProgressActivity.class);
			break;
		case R.id.matrix:
			intent.setClass(this, MatrixActivity.class);
			break;
		case R.id.floatball:
			intent.setClass(this, FloatBallActivity.class);
			break;
		case R.id.guaguaka:
			intent.setClass(this, GuaGuaKaActivity.class);
			break;
		case R.id.wuzhiqi:
			intent.setClass(this, WuZhiQiActivity.class);
			break;
		case R.id.lucky_panel:
			intent.setClass(this, LuckyPanelActivity.class);
			break;

		default:
			break;
		}
		startActivity(intent);		
		
	}
}
