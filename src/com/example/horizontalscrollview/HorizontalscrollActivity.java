package com.example.horizontalscrollview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.demo1.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

public class HorizontalscrollActivity extends Activity {
	private List<HashMap<String, Object>> list ;
	private final int DATA_SIZE = 8;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_horizontal_scroll);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		float density = dm.density;
		int ll_width = (int) ((100+5) * density * DATA_SIZE);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ll_width, RelativeLayout.LayoutParams.WRAP_CONTENT);
		GridView gv = (GridView)this.findViewById(R.id.gv);
		gv.setLayoutParams(params);
		gv.setNumColumns(DATA_SIZE);
		getData();
		ListAdapter adapter = new SimpleAdapter(this, list, R.layout.gridview_item, 
				new String[]{"itemImage","itemName"}, 
				new int[]{R.id.itemImage,R.id.itemName});
		gv.setAdapter(adapter);
	}

	private void getData() {
		list = new ArrayList<HashMap<String,Object>>();
		for (int i = 0; i < DATA_SIZE; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", R.drawable.ic_launcher);
			map.put("itemName", "图片-" + i);
			list.add(map);
		}
		
	}
	
	
}
