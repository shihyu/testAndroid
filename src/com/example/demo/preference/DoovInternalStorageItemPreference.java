/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.demo.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demo.R;


public class DoovInternalStorageItemPreference extends Preference {

	private final String TAG = DoovInternalStorageItemPreference.class.getSimpleName();
	private TextView mTotalSpace ;
	private TextView mOthersSpace ;
	private TextView mUsedSpace ;
	private TextView mAvaliableSpace ;
	
	private float total;
    private float others;
    private float used;
    private float avaliable;
	

	public DoovInternalStorageItemPreference(Context context,
			AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr,0);
	}

	public DoovInternalStorageItemPreference(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public DoovInternalStorageItemPreference(Context context) {
		this(context,null);
	}
	
	public DoovInternalStorageItemPreference(Context context,
			AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		setLayoutResource(R.layout.doov_preference_internal_stroge);
		Log.d(TAG,"DoovInternalStorageItemPreference mTotalSpace=" + mTotalSpace);
	}

	@Override
    protected View onCreateView(ViewGroup parent) {
		View view = super.onCreateView(parent);
		Log.d(TAG,"onCreateView=" + mTotalSpace);
    	return view;
    }
    
    @Override
    protected void onBindView(View view) {
    	super.onBindView(view);
    	mTotalSpace = (TextView)view.findViewById(R.id.total_storage);
    	mOthersSpace = (TextView)view.findViewById(R.id.others_storage);
    	mUsedSpace = (TextView)view.findViewById(R.id.used_storage);
    	mAvaliableSpace = (TextView)view.findViewById(R.id.available_storage);
    	
    	mTotalSpace.setText(getContext().getString(R.string.memoryusage_item_total_space, this.total));
    	mOthersSpace.setText(getContext().getString(R.string.memoryusage_item_available_space, this.others)); 
    	mUsedSpace.setText(getContext().getString(R.string.memoryusage_item_used_space, this.used));
    	mAvaliableSpace.setText(getContext().getString(R.string.memoryusage_item_other_space, this.avaliable));
    	
    	Log.d(TAG,"onBindView=" + mTotalSpace);
    }
    
    public void setData(float total,float other,float used,float avaliable){
    	this.total = (float)(Math.round(total*100))/100;//保留2个小数点
    	this.others = (float)(Math.round(other*100))/100;//保留2个小数点
    	this.used = (float)(Math.round(used*100))/100;//保留2个小数点
    	this.avaliable = (float)(Math.round(avaliable*100))/100;//保留2个小数点
    	Log.d(TAG,"setData");
    	//error
    	/*mTotalSpace.setText(getContext().getString(R.string.memoryusage_item_total_space, this.total));
    	mOthersSpace.setText(getContext().getString(R.string.memoryusage_item_available_space, this.others)); 
    	mUsedSpace.setText(getContext().getString(R.string.memoryusage_item_used_space, this.used));
    	mAvaliableSpace.setText(getContext().getString(R.string.memoryusage_item_other_space, this.avaliable));*/
    	//notifyChanged(); 
    	
    	/*
    	//ok 
    	mTotalSpace.setText(getContext().getString(R.string.memoryusage_item_total_space, total));
    	mOthersSpace.setText(getContext().getString(R.string.memoryusage_item_available_space, other)); 
    	mUsedSpace.setText(getContext().getString(R.string.memoryusage_item_used_space, used));
    	mAvaliableSpace.setText(getContext().getString(R.string.memoryusage_item_other_space, avaliable));*/
    }
}
