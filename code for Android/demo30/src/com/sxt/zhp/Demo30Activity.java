package com.sxt.zhp;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
/**
 * ֡����
 *
 */
public class Demo30Activity extends Activity {
    private ImageView iv ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        iv = (ImageView) findViewById(R.id.imageView1);
        
        //��ʼ
        findViewById(R.id.btnStart).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				AnimationDrawable anim =  (AnimationDrawable) iv.getDrawable();
				
				anim.start();
				
				
			}
		});
      //ֹͣ����
        findViewById(R.id.btnStop).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				AnimationDrawable anim =  (AnimationDrawable) iv.getDrawable();
				anim.stop();
			}
		});
    }
    
    
}