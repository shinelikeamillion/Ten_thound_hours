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
 * 作为远程调用者，调用方式IWeather.Stub.asInterface(binder)获取不同，绑定的意图类中action和提供服务的一致即可
 *
 */
public class Demo25_Service_callActivity extends Activity {
	private String[] weatherStr = { "晴天", "阴天", "多云", "小雨", "暴雨" };
	private IWeather myBinder;
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
			myBinder = IWeather.Stub.asInterface(binder);//使用接口中内部类方法转换 不能直接强转

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

		// 点击获取天气预报的
		findViewById(R.id.btn_weather).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						if (myBinder != null) {
							try {
								int w = myBinder.getWeather();//调用服务
								((TextView)findViewById(R.id.tv_weather)).setText(weatherStr[w]);
							} catch (RemoteException e) {
								e.printStackTrace();
							}							
						}else{
							Toast.makeText(Demo25_Service_callActivity.this, "请先绑定服务！", 1).show();
						}
					}
				});
    }
	/**
	 * 绑定服务
	 * 注意这里的Action 需要和提供服务的配置action一致
	 */
	private void bindMyService() {
		Intent it = new Intent("com.sxt.zhp.aidl.Weather");
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