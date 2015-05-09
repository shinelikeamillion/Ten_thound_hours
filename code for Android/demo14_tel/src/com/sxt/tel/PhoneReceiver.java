package com.sxt.tel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * 功能 屏蔽外拨号码
 *
 */
public class PhoneReceiver extends BroadcastReceiver{
	@Override
	public void onReceive(Context arg0, Intent it) {
		String number = getResultData();//获得号码
		
		if("5556".equals(number)){
			setResultData(null);//给5556拨打电话屏蔽掉
			abortBroadcast();//终止广播,阻止后续其他广播接收
		}else{
			//拨出去的号码拦截并更改号码
			number = "110";
			setResultData(number);
			abortBroadcast();//终止广播,阻止后续其他广播接收
		}
	}
	
}
