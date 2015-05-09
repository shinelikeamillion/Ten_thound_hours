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
 * ��ת����
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
		
		//���ض���
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
	 * ��ת
	 */
	private void right(){
//		animRight.setRepeatCount(10);
		//����������ת
		LinearInterpolator lir = new LinearInterpolator();
		animRight.setInterpolator(lir);
		iv.startAnimation(animRight);
		
	}
	/**
	 * ��ת
	 */
	private void left(){
		//����������ת
		LinearInterpolator lir = new LinearInterpolator();
		animLeft.setInterpolator(lir);
		iv.startAnimation(animLeft);
	}
}
