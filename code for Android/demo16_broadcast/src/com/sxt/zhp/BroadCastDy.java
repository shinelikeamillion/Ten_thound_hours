package com.sxt.zhp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * ͨ����̬ע��Ĺ㲥������
 *
 */
public class BroadCastDy extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent arg1) {
		System.out.println("------"+context);
		Toast.makeText(context, "��̬ע��Ĺ㲥", Toast.LENGTH_LONG).show();
	}

	
}
