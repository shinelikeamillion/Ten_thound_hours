package com.sxt.zhp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
/**
 * 旋转动画
 *
 */
public class RetateActivity extends Activity{
	private ImageView iv;
	private Animation animLeft;
	private Animation animRight;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.retate);
		
		iv = (ImageView) findViewById(R.id.imageView_retate);
		
		//加载动画
		animLeft = AnimationUtils.loadAnimation(this, R.anim.retateleft);
		animRight = AnimationUtils.loadAnimation(this, R.anim.retateright);
		
		findViewById(R.id.btn_left).setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
					left();
			}
		});
		findViewById(R.id.btn_right).setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
					right();
			}
		});
	}
	/**
	 * 旋转
	 */
	private void right(){
//		animRight.setRepeatCount(10);
		//设置匀速旋转
		LinearInterpolator lir = new LinearInterpolator();
		animRight.setInterpolator(lir);
		iv.startAnimation(animRight);
		
	}
	/**
	 * 旋转
	 */
	private void left(){
		//设置匀速旋转
		LinearInterpolator lir = new LinearInterpolator();
		animLeft.setInterpolator(lir);
		iv.startAnimation(animLeft);
	}
}
