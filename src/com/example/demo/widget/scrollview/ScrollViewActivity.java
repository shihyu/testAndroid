package com.example.demo.widget.scrollview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.example.demo.R;


public class ScrollViewActivity extends Activity implements ScrollViewListener {  
  
    private ObservableScrollView scrollView1 = null;  
    private ObservableScrollView scrollView2 = null;  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.test_scrollview);
  
        scrollView1 = (ObservableScrollView) findViewById(R.id.view1);  
        scrollView1.setScrollViewListener(this);  
        scrollView2 = (ObservableScrollView) findViewById(R.id.view2);  
        scrollView2.setScrollViewListener(this);  
  
    }  
  
    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        // Inflate the menu; this adds items to the action bar if it is present.  
        //getMenuInflater().inflate(R.menu.main, menu);  
        return true;  
    }  
  
    @Override  
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y,  
            int oldx, int oldy) {  
        if (scrollView == scrollView1) {  
            scrollView2.scrollTo(x, y);  
        } else if (scrollView == scrollView2) {  
            scrollView1.scrollTo(x, y);  
        }  
    }  
  
}  