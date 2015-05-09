package com.sxt.zhp.aidl;

import java.util.Random;

import com.sxt.zhp.aidl.IWeather;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
/**
 * 1.定义AIDL文件 （接口）
 * 2.在Service中实现接口及引用
 * 3.配置Service,注意需要action
 * 4.远程调用者，需要拷贝aidl文件到工程
 * 5.绑定服务和调用方式基本一致，需要在ServcieConnectin转换Binder
 */
public class WeatherAIDLService extends Service{
	private Random random = new Random();
	//实现自定义的接口
	private IWeather.Stub weather = new IWeather.Stub() {		
		@Override
		public int getWeather() throws RemoteException {
			Log.i("MyTag", "===*AIDL*===");
			return random.nextInt(5);//产生5以内的随机数 表示天气
		}
	};
	@Override
	public IBinder onBind(Intent arg0) {
		Log.i("MyTag","=aidl onBind==");
		return weather;
	}

}
