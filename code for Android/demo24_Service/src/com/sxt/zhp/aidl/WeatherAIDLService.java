package com.sxt.zhp.aidl;

import java.util.Random;

import com.sxt.zhp.aidl.IWeather;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
/**
 * 1.����AIDL�ļ� ���ӿڣ�
 * 2.��Service��ʵ�ֽӿڼ�����
 * 3.����Service,ע����Ҫaction
 * 4.Զ�̵����ߣ���Ҫ����aidl�ļ�������
 * 5.�󶨷���͵��÷�ʽ����һ�£���Ҫ��ServcieConnectinת��Binder
 */
public class WeatherAIDLService extends Service{
	private Random random = new Random();
	//ʵ���Զ���Ľӿ�
	private IWeather.Stub weather = new IWeather.Stub() {		
		@Override
		public int getWeather() throws RemoteException {
			Log.i("MyTag", "===*AIDL*===");
			return random.nextInt(5);//����5���ڵ������ ��ʾ����
		}
	};
	@Override
	public IBinder onBind(Intent arg0) {
		Log.i("MyTag","=aidl onBind==");
		return weather;
	}

}
