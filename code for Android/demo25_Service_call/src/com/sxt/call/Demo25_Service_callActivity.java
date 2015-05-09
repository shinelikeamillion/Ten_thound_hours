package com.sxt.call;
import com.sxt.zhp.aidl.IWeather;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * ��ΪԶ�̵����ߣ����÷�ʽIWeather.Stub.asInterface(binder)��ȡ��ͬ���󶨵���ͼ����action���ṩ�����һ�¼���
 *
 */
public class Demo25_Service_callActivity extends Activity {
	private String[] weatherStr = { "����", "����", "����", "С��", "����" };
	private IWeather myBinder;
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
			myBinder = IWeather.Stub.asInterface(binder);//ʹ�ýӿ����ڲ��෽��ת�� ����ֱ��ǿת

			isConn = true;
		}
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
							try {
								int w = myBinder.getWeather();//���÷���
								((TextView)findViewById(R.id.tv_weather)).setText(weatherStr[w]);
							} catch (RemoteException e) {
								e.printStackTrace();
							}							
						}else{
							Toast.makeText(Demo25_Service_callActivity.this, "���Ȱ󶨷���", 1).show();
						}
					}
				});
    }
	/**
	 * �󶨷���
	 * ע�������Action ��Ҫ���ṩ���������actionһ��
	 */
	private void bindMyService() {
		Intent it = new Intent("com.sxt.zhp.aidl.Weather");
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