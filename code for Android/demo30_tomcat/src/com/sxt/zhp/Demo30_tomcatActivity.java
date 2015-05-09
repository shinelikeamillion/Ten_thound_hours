package com.sxt.zhp;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

public class Demo30_tomcatActivity extends Activity {
	private float[] rect = { 100, 100, 300, 200 };// 触碰点区域
	private ImageView iv;
	private AnimationDrawable ad;
	private long totalTime;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		iv = (ImageView) findViewById(R.id.imageView1);
		ad = (AnimationDrawable) iv.getDrawable();// 获得动画图像
		
		int count = ad.getNumberOfFrames();
		for (int i = 0; i < count; i++) {
			totalTime = ad.getDuration(i);
		}
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	System.out.println(ad.getNumberOfFrames()+"======"+ad.getCurrent()+"====="+ad.isRunning());
    	
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			float x = event.getX();
			float y = event.getY();
			if (x > rect[0] && x <= rect[2] && y > rect[1] && y < rect[3]) {
				// AnimationDrawable ad = (AnimationDrawable) iv.getDrawable();
				if (ad.isRunning()) {// 如果还在运行状态 则先stop，否则只能start一次，停留在最后一帧
					ad.stop();
				}
				ad.start();
			}
		}
    	return super.onTouchEvent(event);
    }
}