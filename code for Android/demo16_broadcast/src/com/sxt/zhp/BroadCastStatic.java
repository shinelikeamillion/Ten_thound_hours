package com.sxt.zhp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
/**
 * ͨ����̬ע��Ĺ㲥
 * ��xml�嵥�ļ�������<receiver>
 */
public class BroadCastStatic extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent it) {
		System.out.println("----BroadCastStatic--"+context);
		Toast.makeText(context, "��̬ע��Ĺ㲥", Toast.LENGTH_LONG).show();
		
	}
	
}
