package com.sxt.sensor;





import org.openintents.sensorsimulator.hardware.Sensor;
import org.openintents.sensorsimulator.hardware.SensorEvent;
import org.openintents.sensorsimulator.hardware.SensorEventListener;
import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.hardware.SensorManager;

import android.os.Bundle;
import android.widget.ImageView;

public class Demo38_sensorActivity extends Activity implements SensorEventListener{
   private ImageView iv;
   private Paint paint;
   private float rBall = 15;
   private Bitmap bitmap;
   private Canvas canvas;
   private int width;
   private int height;
   
   private float ballX;//小球坐标
   private float ballY;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        iv = new ImageView(this);
        setContentView(iv);
        
		// 屏幕宽度和高度
		width = this.getWindowManager().getDefaultDisplay().getWidth();
		height = this.getWindowManager().getDefaultDisplay().getHeight();//
		
		ballX = width/2;
		ballY = height/2;
        
        paint = new Paint();
        paint.setColor(Color.RED);
        
        bitmap = Bitmap.createBitmap(width, height,Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        init();
    }
    /**
     * 初始化组件
     */
    private void init(){
//        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);//传感器管理器
        //1.获取管理器
        SensorManagerSimulator sm = SensorManagerSimulator.getSystemService(this,SENSOR_SERVICE);
        //2.连接模拟器
        sm.connectSimulator();//需要网络 
        //3.给加速度传感器注册监听
        //先获取加速度传感器
        Sensor sensor =  sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //注册监听器
        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);  
    }

    
    /**
     * 绘图
     */
    private void draw(){
    	System.out.println(ballX+"========="+ballY);
    	canvas.drawRGB(0, 0, 0);
    	paint.setColor(Color.WHITE);
    	canvas.drawCircle(ballX, ballY, rBall, paint);
    	
    	iv.setImageBitmap(bitmap);
    }
    
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
	
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float mGX = event.values[SensorManager.DATA_X];
		float mGY = event.values[SensorManager.DATA_Y];
		float mGZ = event.values[SensorManager.DATA_Z];
//		System.out.println(mGX+"==="+mGY+"==="+mGZ);
		
		ballX += mGX;
		ballY += mGY;
		
		// 检测小球是否超出边界
		if (ballX < rBall) {
			ballX = rBall;
		} else if (ballX+rBall > width) {
			ballX = width-rBall;
		}
		if (ballY < rBall) {
			ballY = rBall;
		} else if (ballY+rBall > height) {
			ballY = height-rBall;
		}
		
		draw();
	}
    
}