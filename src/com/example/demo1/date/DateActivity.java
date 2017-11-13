package com.example.demo1.date;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.demo1.R;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.widget.TextClock;
import android.widget.TextView;
 
public class DateActivity extends Activity {
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date);
		TextClock tc = (TextClock)this.findViewById(R.id.time_view);
		TextView tv_date = (TextView)this.findViewById(R.id.tv_date);
		tc.setText(new Date().toString());
		//tv_date.setText(new SimpleDateFormat().format(new Date()));
		String pattern = Settings.System.getString(getContentResolver(), Settings.System.DATE_FORMAT);
		String date = new SimpleDateFormat(pattern).format(new Date());
		tv_date.setText(date);
	}
}
