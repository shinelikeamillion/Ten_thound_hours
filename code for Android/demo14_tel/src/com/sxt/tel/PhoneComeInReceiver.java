package com.sxt.tel;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 此接收器可以同时监听到外拨电话和来电事件 来电监听 此类配置两种外拨和接收订阅服务，即可同时做两种事件
 * 
 * 1.<uses-permission
 * android:name="android.permission.READ_PHONE_STATE"></uses-permission> 2.
 * <intent-filter> <action android:name="android.intent.action.PHONE_STATE"/>
 * </intent-filter>
 * 
 * 功能：统计通话时长
 */
public class PhoneComeInReceiver extends BroadcastReceiver {
	private static String TAG = "MyTag";
	private static long startTime;//记录开始时间  必须是静态的  因为每次接收广播都会重新实例新的对象

	@Override
	public void onReceive(Context context, Intent intent) {
//		System.out.println("======" + intent.getAction());
		// 如果是拨打电话Intent.ACTION_NEW_OUTGOING_CALL
		// 如果是来电 android.intent.action.PHONE_STATE		
		if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {

			String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
			System.out.println(this+"**来电号码：" + phoneNumber);
//			System.out.println(getResultData() + "==有来电==" + intent.getAction());

			// 如果是来电 获得电话管理器
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Service.TELEPHONY_SERVICE);

//			System.out.println("====tm.getCallState()====" + tm.getCallState());

			switch (tm.getCallState()) {
				case TelephonyManager.CALL_STATE_RINGING:// 来电状态
					String incoming_number = intent.getStringExtra("incoming_number");
					Log.i(TAG, "RINGING :" + incoming_number);
				break;
				case TelephonyManager.CALL_STATE_OFFHOOK: // 接通电话
					startTime = System.currentTimeMillis();//记录开始通话时间
					Log.i(TAG, "开始通话 :" + System.currentTimeMillis());
				break;

				case TelephonyManager.CALL_STATE_IDLE: // 挂断电话
					Log.i(TAG, "通话时长 :" + (System.currentTimeMillis()-startTime));
				break;
			}
		}
	}

}
