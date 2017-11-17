package com.doov.wuzhiqi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.demo.R;


public class WuZhiQiActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wuzhiqi);
		
		final WuZhiQiPanel wuZhiQiPanel = (WuZhiQiPanel) this.findViewById(R.id.wuzhiqipanel);
		
		wuZhiQiPanel.setGameChangeStatusListener(new GameChangeStatusListener() {
			
			@Override
			public void onGameOver(int result) {
				AlertDialog.Builder builder = new Builder(WuZhiQiActivity.this);
				builder.setTitle("游戏结束");
				if(result == wuZhiQiPanel.WHITE_WIN){
					builder.setMessage("白棋胜利哟!");
				}else{
					builder.setMessage("黑棋胜利哟!");
				}
				builder.setPositiveButton("重来一盘",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						wuZhiQiPanel.reStartGame();
					}

				});
				builder.setNegativeButton("休息一下", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						WuZhiQiActivity.this.finish();
					}
				});
				builder.setCancelable(false);
				builder.create().show();
			}
		});
	}
}
