package com.sxt.call;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.sxt.aidl.IWeather;

public class Demo27_callActivity extends Activity {
	private String[] weatherStr = { "����", "����", "����", "С��", "����" };
	private IWeather weather;
	private boolean isConn;
	private ServiceConnection conn = new ServiceConnection(){

		/***============================================***/
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			isConn = true;
			weather = IWeather.Stub.asInterface(arg1);//ת���ӿ� ����ǿ��ת��
			
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
	
			
		}};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViewById(R.id.btn_startService).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						startMyServie();
					}
				});

		findViewById(R.id.btn_stopService).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						stopMyServie();
					}
				});
		findViewById(R.id.btn_bind).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				bindMyService();
			}
		});

		findViewById(R.id.btn_unbind).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				unbindMyServie();
			}
		});

		// �����ȡ����Ԥ����
		findViewById(R.id.btn_weather).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						if (weather != null) {
							int w;
							try {
								w = weather.getWeather();
								((TextView)findViewById(R.id.tv_weather)).setText(weatherStr[w]);
							} catch (RemoteException e) {
								e.printStackTrace();
							}//���÷���
							
						}else{
							Toast.makeText(Demo27_callActivity.this, "���Ȱ󶨷���", 1).show();
						}
					}
				});
	}
	/**
	 * Զ����������
	 */
	private void startMyServie() {
		Intent it = new Intent("com.sxt.aidl.MyService");
		startService(it);
	}

	/**
	 * ֹͣ����onDestroy ������ͨ��Ӧ�ó����� ���������е����ǵķ��� ���ֹͣ Ҳ�����onDestroy
	 */
	private void stopMyServie() {
		Intent it = new Intent("com.sxt.aidl.MyService");
		stopService(it);
	}
	
	/**
	 * Զ�̰󶨷���
	 */
	private void bindMyService() {
		Intent it = new Intent("com.sxt.aidl.MyService");
		
		bindService(it, conn, Context.BIND_AUTO_CREATE);
	}

	/**
	 * �����:���˳�Ӧ��ʱ�����û�е��ô˷��� ����� ����׳��쳣
	 */
	private void unbindMyServie() {
		if(isConn){
			unbindService(conn);
		}		
	}
}