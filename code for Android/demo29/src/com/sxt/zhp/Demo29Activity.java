package com.sxt.zhp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class Demo29Activity extends Activity {
	private ImageView iv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
        
//        MyButton btn = new MyButton(this);
//        btn.setText("��ť");
//        setContentView(btn);
        
        
    }
	

    class MyButton extends Button{
    	//����
    	Paint paint = new Paint();
		public MyButton(Context context) {
			super(context);
		}
    	
		/**
		 * canvas����
		 */
    	protected void onDraw(Canvas canvas) {
    		paint.setColor(Color.RED);
    		
    		canvas.drawText("�ҵİ�ť", 5, 55, paint);
    		super.onDraw(canvas);
    		System.out.println("=====onDraw===");
    	}
    }
}