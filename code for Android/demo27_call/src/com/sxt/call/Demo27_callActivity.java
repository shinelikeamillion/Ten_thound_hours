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
	private String[] weatherStr = { "晴天", "阴天", "多云", "小雨", "暴雨" };
	private IWeather weather;
	private boolean isConn;
	private ServiceConnection conn = new ServiceConnection(){

		/***============================================***/
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			isConn = true;
			weather = IWeather.Stub.asInterface(arg1);//转换接口 不能强制转换
			
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

		// 点击获取天气预报的
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
							}//调用服务
							
						}else{
							Toast.makeText(Demo27_callActivity.this, "请先绑定服务！", 1).show();
						}
					}
				});
	}
	/**
	 * 远程启动服务
	 */
	private void startMyServie() {
		Intent it = new Intent("com.sxt.aidl.MyService");
		startService(it);
	}

	/**
	 * 停止服务：onDestroy 还可以通过应用程序中 找正在运行的我们的服务 点击停止 也会调用onDestroy
	 */
	private void stopMyServie() {
		Intent it = new Intent("com.sxt.aidl.MyService");
		stopService(it);
	}
	
	/**
	 * 远程绑定服务
	 */
	private void bindMyService() {
		Intent it = new Intent("com.sxt.aidl.MyService");
		
		bindService(it, conn, Context.BIND_AUTO_CREATE);
	}

	/**
	 * 解除绑定:在退出应用时，如果没有调用此方法 解除绑定 则会抛出异常
	 */
	private void unbindMyServie() {
		if(isConn){
			unbindService(conn);
		}		
	}
}