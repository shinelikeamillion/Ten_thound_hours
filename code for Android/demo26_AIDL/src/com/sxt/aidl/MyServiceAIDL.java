package com.sxt.aidl;

import java.util.Random;

import com.sxt.aidl.IWeather.Stub;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyServiceAIDL extends Service{

	@Override
	public IBinder onBind(Intent arg0) {
		return stub;
	}

	/**
	 * 实现了自己的接口
	 */
	IWeather.Stub stub = new Stub() {
		private Random rand = new Random();
		@Override
		public int getWeather() throws RemoteException {			
			return rand.nextInt(5);
		}		
	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("MyTag", "===onStartCommand====");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.i("MyTag", "===onUnbind====");
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		Log.i("MyTag", "===onDestroy====");
	}
	
	
	
//	class MyBinder extends Binder{
//		
//	}
}
