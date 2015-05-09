package com.sxt.zhp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 通过动态注册的广播接收器
 *
 */
public class BroadCastDy extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent arg1) {
		System.out.println("------"+context);
		Toast.makeText(context, "动态注册的广播", Toast.LENGTH_LONG).show();
	}

	
}
