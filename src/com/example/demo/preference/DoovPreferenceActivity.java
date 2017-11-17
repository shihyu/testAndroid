package com.example.demo.preference;


import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.demo.R;

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
