package com.example.palette;
import java.util.List;

import com.example.demo1.R;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.graphics.Palette.Swatch;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PaletteActivity extends Activity {
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.palette_layout);
		final ViewGroup sv_root_ll = (ViewGroup)this.findViewById(R.id.sv_root_ll);
		final ImageView iv_wallpapaer = (ImageView)this.findViewById(R.id.iv_wallpapaer);
		WallpaperManager wallpaperManager = (WallpaperManager) this.getSystemService(Context.WALLPAPER_SERVICE);
		Drawable wallpapaer = wallpaperManager.getDrawable();
		
		Bitmap wallpaperBitmap = ((BitmapDrawable)wallpapaer).getBitmap();
		Bitmap bitmap = ImageCrop(wallpaperBitmap.copy(Bitmap.Config.ARGB_4444, true),0,600);
		Log.d("zhangle","bitmap=" + bitmap );
		iv_wallpapaer.setLayoutParams(new  LinearLayout.LayoutParams(bitmap.getWidth(),bitmap.getHeight()));
		iv_wallpapaer.setBackground(new BitmapDrawable(bitmap));
		
		
		Palette.generateAsync(bitmap, 32, new Palette.PaletteAsyncListener() {
		    @Override
		    public void onGenerated(Palette palette) {
                List<Swatch> swatchs = palette.getSwatches();
                for (int i = 0; i < swatchs.size(); i++) {
                	Swatch swatch = swatchs.get(i);
                	if(null != swatch){
                		TextView main_color = new TextView(getApplicationContext());
                    	main_color.setText("swatch" + i +"主要颜色=" + Integer.toHexString(swatch.getRgb()) + 
                    			"合适的字体=" +Integer.toHexString(swatch.getTitleTextColor()) + " Population=" + swatch.getPopulation());
                        main_color.setTextColor(swatch.getTitleTextColor());
                        main_color.setBackgroundColor(swatch.getRgb());
                        sv_root_ll.addView(main_color);
                    }else{
                    	Toast.makeText(getApplicationContext(), "获取调色板失败", Toast.LENGTH_SHORT).show();
                    }
				}
                
                
		    }
		});
		/*int maxColor = 16;
		int colors[] = new int[maxColor];
		int colorsNumber[] = new int[maxColor];
		HashMap<Integer, Integer> colorCountMap = new HashMap<Integer, Integer>();
		Bitmap bitmap = scaleBitmapDown(wallpaperBitmap);
		final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();

        final int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        if(pixels.length > 0){
             int currentColor = pixels[0];
             int currentColorCount = 1;
             colorCountMap.put(currentColor, 1);
             
             for (int i = 1; i < pixels.length; i++) {
            	 if (pixels[i] == currentColor) {//
                     // We've hit the same color as before, increase population
            		 colorCountMap.put(currentColor, currentColorCount++);
                 }else{
                	 currentColor = pixels[i];
                	 
                	 if(colorCountMap.get(currentColor) != null){//颜色已经出现过
                		 currentColorCount = colorCountMap.get(currentColor) + 1;
                	 }else{//新颜色
                		 currentColorCount = 1;
                	 }
                	 colorCountMap.put(currentColor, currentColorCount++);
                 }
			 }
        }
        if(colorCountMap.size() >= maxColor){
        	
        }else{
        	colors = new int[colorCountMap.size()];
        	colorsNumber = new int[colorCountMap.size()];
        }*/
		
	}
	private boolean mGeneratedTextColors;
	
	
	private static final int CALCULATE_BITMAP_MIN_DIMENSION = 100;
	private static Bitmap scaleBitmapDown(Bitmap bitmap) {
        final int minDimension = Math.min(bitmap.getWidth(), bitmap.getHeight());

        if (minDimension <= CALCULATE_BITMAP_MIN_DIMENSION) {
            // If the bitmap is small enough already, just return it
            return bitmap;
        }

        final float scaleRatio = CALCULATE_BITMAP_MIN_DIMENSION / (float) minDimension;
        return Bitmap.createScaledBitmap(bitmap,
                Math.round(bitmap.getWidth() * scaleRatio),
                Math.round(bitmap.getHeight() * scaleRatio),
                false);
    }
	
	/**
	 * 裁剪图片,根据图片的高度
	 * @param bitmap
	 * @param start
	 * @param end
	 * @return
	 */
    public static Bitmap ImageCrop(Bitmap bitmap,int Y_start,int Y_end) {
    	int x = 0;
    	int y = 0;
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

    	if(h >= Y_end){
        	y = Y_start;
        	h = Y_end -Y_start;
        }else{
        	
        }
    	x = w/4;
    	w = w-w/4;
    	Log.d("zhangle","ImageCrop x=" + x + " y=" + y + " w=" + w + " h=" + h );
        return Bitmap.createBitmap(bitmap, x, y, w, h, null, false);
    }
}
