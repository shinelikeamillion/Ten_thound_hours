package com.sxt.zhp;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Android四大组件：Activity,Servcie,BroadCast,ContentProvider 
 * 广播：
 * 1.静态注册(xml清单注册)
 * 2.动态注册(程序中注册)
 */
public class Demo16Activity extends Activity {
	private static final String BROADCAST_FLAG_STATIC = "com.sxt.zhp.MyBroadCast";// 静态的action标记
	private static final String BROADCAST_FLAG_DY = "com.sxt.zhp.MyBroadCastdy";// 动态的action标记
	private BroadCastDy broadReceiver ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 测试静态广播
		findViewById(R.id.btn_static).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(BROADCAST_FLAG_STATIC);
				sendBroadcast(it);//发送广播
			}
		});
		//测试动态广播
		findViewById(R.id.btn_dy).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(BROADCAST_FLAG_DY);
				sendBroadcast(it);//发送广播				
			}
		});
	}

	@Override
    protected void onResume() {
    	super.onResume();
    	//接收器
    	broadReceiver = new BroadCastDy();  
    	//意图过滤器
    	IntentFilter filter = new IntentFilter(BROADCAST_FLAG_DY);
    	//注册广播接收器  绑定两者关系
    	registerReceiver(broadReceiver, filter);
    }

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(broadReceiver);
	}
}