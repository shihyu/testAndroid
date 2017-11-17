package com.doov.luckypanel;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.R;


public class LuckyPanelActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lucky_panel);
		
		final TextView tv_award = (TextView)this.findViewById(R.id.tv_award);
		
		final LuckyPanel luckyPanel = (LuckyPanel)this.findViewById(R.id.lucyk_panel);
		luckyPanel.setEndCallBack(new EndCallBack() {
			@Override
			public void onEnd(String award) {
				tv_award.setText("奖品:" + award);
				Log.d("MainActivity","onEnd tv_award=" + tv_award.getText());
			}
		});
		
		final ImageView iv_start = (ImageView) this.findViewById(R.id.iv_start);
		iv_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!luckyPanel.isRunning()){//未旋转时
					luckyPanel.startSpin();
					iv_start.setBackgroundResource(R.drawable.stop);
				}else{//旋转时
					if(!luckyPanel.isShoudStop()){//没有准备停止旋转
						iv_start.setBackgroundResource(R.drawable.start);
						luckyPanel.stopSpin();
					}
				}
			}
		});
	}
	
	interface EndCallBack{
		public void onEnd(String award);
	}
}
