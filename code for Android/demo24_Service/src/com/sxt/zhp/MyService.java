package com.sxt.zhp;

import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
/**
 * 服务
 * @author Administrator
 *
 */
public class MyService extends Service{
	private static final String MYTAG = "MyTag";
	private Random rand = new Random();
	
	
	
	@Override
	public IBinder onBind(Intent intent) {
		
		Log.i(MYTAG, "======onBind======="+intent.getStringExtra("age")	);
		return new MyBinder(); //实例我们自己的binder 定义自己的方法
	}
	
	private int weather(){
		int r = rand.nextInt(5);
		return r;
	}
	
	/**
	 * 定义内部类
	 */
	class MyBinder extends Binder{
		public int getWeather(){
			return weather();
		}
	}
	
	
	
	
	

	@Override
	public void onCreate() {
		Log.i(MYTAG, "======onCreate======="	);
	}

	@Override
	public void onDestroy() {
		Log.i(MYTAG, "======onDestroy======="	);
	}

	@Override
	public void onRebind(Intent intent) {
		Log.i(MYTAG, "======onRebind======="	);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.i(MYTAG, "======onStart======="	);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(MYTAG, "======onStartCommand======="+intent.getExtras().getString("age")	);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.i(MYTAG, "======onUnbind======="	);
		return super.onUnbind(intent);
	}

}
