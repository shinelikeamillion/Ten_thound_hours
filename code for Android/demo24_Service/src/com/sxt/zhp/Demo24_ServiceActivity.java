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
 * Android四大组件:Activity,Broadcast,ContentProvider,Service
 * 
 * 服务分为：本地服务和远程服务 启动方式：startService()和bindService()
 * 
 * 步骤：1.自定义Service (继承Service) 2.配置Service在项目清单文件中 3.启动
 * 
 * 
 * 绑定服务：ServiceConnection和IBinder  
 * 注意：服务中的onBinder方法必须有返回值，在自定义Service类中，必须声明一个Binder的内部类
 * 在Activity中获取服务中的方法：在ServiceConnection的onServiceConnected中获取IBinder对象即可
 * 
 * 
 * startService和bindService区别：
 * 1.都启动服务
 * 2.startService，服务运行于后台，应用程序即使退出，服务仍可以运行
 *   bindService，服务是和应用绑定的，如果应用退出，则绑定的服务也必须解除绑定和销毁
 * 
 */
public class Demo24_ServiceActivity extends Activity {
	private String[] weatherStr = { "晴天", "阴天", "多云", "小雨", "暴雨" };
	private MyBinder myBinder;
	private boolean isConn;
	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			Log.i("MyTag", arg0 + "==onServiceDisconnected==");

		}

		// 只有在绑定的服务中onBind方法有返回值时，此方法才会调用
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

		// 点击获取天气预报的
		findViewById(R.id.btn_weather).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						if (myBinder != null) {
							int w = myBinder.getWeather();//调用服务
							((TextView)findViewById(R.id.tv_weather)).setText(weatherStr[w]);
						}else{
							Toast.makeText(Demo24_ServiceActivity.this, "请先绑定服务！", 1).show();
						}
					}
				});
	}

	/**
	 * 启动服务 第一启动：onCreate-OnStartCommand-onStart 再次启动：不再调用onCreate方法
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
	 * 停止服务：onDestroy 还可以通过应用程序中 找正在运行的我们的服务 点击停止 也会调用onDestroy
	 */
	private void stopMyServie() {
		Intent it = new Intent(this, MyService.class);
		stopService(it);
	}

	/**
	 * 绑定服务
	 */
	private void bindMyService() {
		Intent it = new Intent(this, MyService.class);
		Bundle b = new Bundle();
		b.putString("age", "33");
		it.putExtras(b);
		
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

	@Override
	protected void onDestroy() {
		unbindMyServie();
		super.onDestroy();
	}
}