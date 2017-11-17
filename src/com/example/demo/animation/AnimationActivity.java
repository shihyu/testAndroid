package com.example.demo.animation;


import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.example.demo.R;

public class AnimationActivity extends Activity implements OnClickListener {
	private ImageView icon_view;
	private ImageView icon_view_2;
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.animation_test);
		icon_view = (ImageView) this.findViewById(R.id.icon_view);
		icon_view_2 = (ImageView) this.findViewById(R.id.icon_view_2);
		button1 = (Button) this.findViewById(R.id.button1);
		button2 = (Button) this.findViewById(R.id.button2);
		button3 = (Button) this.findViewById(R.id.button3);
		button4 = (Button) this.findViewById(R.id.button4);
		button5 = (Button) this.findViewById(R.id.button5);

		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		button5.setOnClickListener(this);
	}

	/**
	 * 用一个 ValueAnimator 控制多个属性动画
	 */
	public void changMultiplePropertyByValueAnimator(final View v) { 
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v
				.getLayoutParams();

		PropertyValuesHolder pvh_left = PropertyValuesHolder.ofFloat(
				"margin_left", params.leftMargin, 500);
		PropertyValuesHolder pvh_top = PropertyValuesHolder.ofFloat(
				"margin_top", params.topMargin, 500);

		ValueAnimator ani = ValueAnimator.ofPropertyValuesHolder(pvh_left,
				pvh_top).setDuration(1000);

		ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				float margin_left = (Float) valueAnimator
						.getAnimatedValue("margin_left");
				float margin_top = (Float) valueAnimator
						.getAnimatedValue("margin_top");

				RelativeLayout.LayoutParams p = (LayoutParams) v.getLayoutParams();

				p.leftMargin = (int) margin_left;
				p.topMargin = (int) margin_top;
				v.setLayoutParams(p);
			}
		});
		ani.start();
	}

	/**
	 * 使用ObjectAnimator直接设置属性和值
	 */
	public void changOneProperty(View v) {
		ValueAnimator colorAnimator = ObjectAnimator.ofInt(v,
				"backgroundColor", 0xffff8080 /*red*/ , 0xff8080ff /*Blue*/ );
		colorAnimator.setDuration(1000);
		colorAnimator.setEvaluator(new ArgbEvaluator());
		colorAnimator.setRepeatCount(ValueAnimator.INFINITE);
		colorAnimator.setRepeatMode(ValueAnimator.REVERSE);// ValueAnimator.RESTART
		colorAnimator.start();
	}

	
	public void changMultiplePropertyByValueAnimator2(View v) {

		/**
		 * 支持属性: translationX and translationY: These properties control where
		 * the View is located as a delta from its left and top coordinates
		 * which are set by its layout container.
		 * 
		 * rotation, rotationX, and rotationY: These properties control the
		 * rotation in 2D (rotation property) and 3D around the pivot point.
		 * 
		 * scaleX and scaleY: These properties control the 2D scaling of a View
		 * around its pivot point.
		 * 
		 * pivotX and pivotY: These properties control the location of the pivot
		 * point, around which the rotation and scaling transforms occur. By
		 * default, the pivot point is located at the center of the object.
		 * 
		 * x and y: These are simple utility properties to describe the final
		 * location of the View in its container, as a sum of the left and top
		 * values and translationX and translationY values.
		 * 
		 * alpha: Represents the alpha transparency on the View. This value is 1
		 * (opaque) by default, with a value of 0 representing full transparency
		 * (not visible).
		 */
		AnimatorSet set = new AnimatorSet();
		set.playTogether(
				ObjectAnimator.ofFloat(v, "rotation", 0, -90f),
				ObjectAnimator.ofFloat(v, "rotationX", 0, 360f),
				ObjectAnimator.ofFloat(v, "rotationY", 0, 360f),
				ObjectAnimator.ofFloat(v, "translationX", 0, 200f),
				ObjectAnimator.ofFloat(v, "translationY", 0, 200f),
				ObjectAnimator.ofFloat(v, "scalY", 1, 1.5f),
				ObjectAnimator.ofFloat(v, "scalX", 1, 2.0f),
				ObjectAnimator.ofFloat(v, "alpan", 1, 0.25f, 1),
				ObjectAnimator.ofFloat(v, "translationY", 0, 200f));
		//set.playSequentially(animator1,animator2,animator3);
		//set.play(ObjectAnimator.ofFloat(v, "rotation", 0, -90f)).with(anim)
		set.setDuration(1000);
		set.start();

	}

	/**
	 * 使用PropertyValuesHolder: scal alpha
	 * @param view
	 */
	public void propertyValuesHolder(View view) {
		PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,0f, 1f);
		PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,0, 1f);
		PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,0, 1f);
		PropertyValuesHolder pvhColor = PropertyValuesHolder.ofInt("backgroundColor", 0xffff8080,0xff8080ff, 0xffff8080);
		ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ,pvhColor)
				.setDuration(1000).start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			changMultiplePropertyByValueAnimator(icon_view_2);
			break;
		case R.id.button2:
			changMultiplePropertyByValueAnimator2(icon_view);
			break;
		case R.id.button3:
			propertyValuesHolder(icon_view_2);
			break;
		case R.id.button4:
			changOneProperty(icon_view_2);
			break;
		case R.id.button5:
			addProperty(icon_view_2);
			break;

		default:
			break;
		}

	}
	
	
	private static class ViewWrapper {
		private View mTarget;

		public ViewWrapper(View target) {
			mTarget = target;
		}

		public int getWidth() {
			Log.d("ViewWrapper","getWidth");
			return mTarget.getLayoutParams().width;
		}

		public void setWidth(int width) {
			Log.d("ViewWrapper","setWidth");
			mTarget.getLayoutParams().width = width;
			mTarget.requestLayout();
		}
	}
	
	public void addProperty(View v){
		ViewWrapper wrapper = new ViewWrapper(v);
		ObjectAnimator.ofInt(wrapper, "width", 500).setDuration(5000).start();
	}
}
