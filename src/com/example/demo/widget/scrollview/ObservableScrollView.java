package com.example.demo.widget.scrollview;
  
import android.content.Context;  
import android.util.AttributeSet;  
import android.util.Log;
import android.widget.ScrollView;  
  
public class ObservableScrollView extends ScrollView {  
  
    private ScrollViewListener scrollViewListener = null;  
  
    public ObservableScrollView(Context context) {  
        super(context);  
    }  
  
    public ObservableScrollView(Context context, AttributeSet attrs,  
            int defStyle) {  
        super(context, attrs, defStyle);  
    }  
  
    public ObservableScrollView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public void setScrollViewListener(ScrollViewListener scrollViewListener) {  
        this.scrollViewListener = scrollViewListener;  
    }  
   
    @Override  
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {  
        super.onScrollChanged(x, y, oldx, oldy);  
        Log.d("zhangle","onScrollChanged x=" + x + " y="+ y + " oldx=" +oldx+ " oldy=" + oldy);
        if (scrollViewListener != null) {  
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);  
        }  
    }

	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
			boolean clampedY) {
		// TODO Auto-generated method stub
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
		Log.d("zhangle","onScrollChanged scrollX=" + scrollX + " scrollY="+ scrollY + " clampedX=" +clampedX+ " clampedY=" + clampedY);
	}  
  
}  