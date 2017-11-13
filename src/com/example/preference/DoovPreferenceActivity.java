package com.example.preference;

import com.example.demo1.R;

import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity;

public class DoovPreferenceActivity extends PreferenceActivity {
	
	private DoovInternalStorageItemPreference mpreference ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.test_preferencescreen);
		mpreference = new DoovInternalStorageItemPreference(this.getApplicationContext());
		//mpreference.setData(7, 8, 9, 10);
		getPreferenceScreen().addPreference(mpreference);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		/*new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mpreference.setData(17, 18, 19, 20);
			}
		}, 200);*/
		
		
		mpreference.setData(17, 18, 19, 20);
	}
}
