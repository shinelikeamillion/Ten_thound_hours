package com.sxt.zhp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
/**
 * 通过静态注册的广播
 * 在xml清单文件中配置<receiver>
 */
public class BroadCastStatic extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent it) {
		System.out.println("----BroadCastStatic--"+context);
		Toast.makeText(context, "静态注册的广播", Toast.LENGTH_LONG).show();
		
	}
	
}
