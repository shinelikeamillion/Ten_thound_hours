package com.sxt.zhp;

import com.sxt.zhp.MyService.MyBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * Android�Ĵ����:Activity,Broadcast,ContentProvider,Service
 * 
 * �����Ϊ�����ط����Զ�̷��� ������ʽ��startService()��bindService()
 * 
 * ���裺1.�Զ���Service (�̳�Service) 2.����Service����Ŀ�嵥�ļ��� 3.����
 * 
 * 
 * �󶨷���ServiceConnection��IBinder  
 * ע�⣺�����е�onBinder���������з���ֵ�����Զ���Service���У���������һ��Binder���ڲ���
 * ��Activity�л�ȡ�����еķ�������ServiceConnection��onServiceConnected�л�ȡIBinder���󼴿�
 * 
 * 
 * startService��bindService����
 * 1.����������
 * 2.startService�����������ں�̨��Ӧ�ó���ʹ�˳��������Կ�������
 *   bindService�������Ǻ�Ӧ�ð󶨵ģ����Ӧ���˳�����󶨵ķ���Ҳ�������󶨺�����
 * 
 */
public class Demo24_ServiceActivity extends Activity {
	private String[] weatherStr = { "����", "����", "����", "С��", "����" };
	private MyBinder myBinder;
	private boolean isConn;
	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			Log.i("MyTag", arg0 + "==onServiceDisconnected==");

		}

		// ֻ���ڰ󶨵ķ�����onBind�����з���ֵʱ���˷����Ż����
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder binder) {
			Log.i("MyTag", arg0 + "==onServiceConnected==" + binder);
			myBinder = (MyBinder) binder;

			isConn = true;
		}
	};

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
						if (myBinder != null) {
							int w = myBinder.getWeather();//���÷���
							((TextView)findViewById(R.id.tv_weather)).setText(weatherStr[w]);
						}else{
							Toast.makeText(Demo24_ServiceActivity.this, "���Ȱ󶨷���", 1).show();
						}
					}
				});
	}

	/**
	 * �������� ��һ������onCreate-OnStartCommand-onStart �ٴ����������ٵ���onCreate����
	 */
	private void startMyServie() {
		// Intent it = new Intent(this,MyService.class);
		Intent it = new Intent("com.sxt.zhp.MyService");
		Bundle b = new Bundle();
		b.putString("age", "13");
		it.putExtras(b);
		startService(it);
	}

	/**
	 * ֹͣ����onDestroy ������ͨ��Ӧ�ó����� ���������е����ǵķ��� ���ֹͣ Ҳ�����onDestroy
	 */
	private void stopMyServie() {
		Intent it = new Intent(this, MyService.class);
		stopService(it);
	}

	/**
	 * �󶨷���
	 */
	private void bindMyService() {
		Intent it = new Intent(this, MyService.class);
		Bundle b = new Bundle();
		b.putString("age", "33");
		it.putExtras(b);
		
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

	@Override
	protected void onDestroy() {
		unbindMyServie();
		super.onDestroy();
	}
}