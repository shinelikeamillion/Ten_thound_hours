package com.sxt.zhp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service{
	private static final String MYTAG = "MyTag";
	@Override
	public IBinder onBind(Intent it) {
		Log.i(MYTAG, "==onBind=="+this);
		return null;
	}

	@Override
	public void onCreate() {
		Log.i(MYTAG, "==onCreate=="+this);
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		Log.i(MYTAG, "==onDestroy=="+this);
		super.onDestroy();
	}

	@Override
	public void onRebind(Intent intent) {
		Log.i(MYTAG, "==onRebind=="+this);
		super.onRebind(intent);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.i(MYTAG, "==onStart=="+this+startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(MYTAG, "==onStartCommand=="+this+startId);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.i(MYTAG, "==onUnbind=="+this);
		return super.onUnbind(intent);
	}

	/**
	 * 要实现Activity与服务间的通信，需要创建一个Binder的子类，然后在onBind方法中返回MyBinder的实例
	 *
	 */
	class MyBind extends Binder{		
		public int weather(){
			int rand = (int)(Math.random()*10);
			return rand;
		}
	}
}
