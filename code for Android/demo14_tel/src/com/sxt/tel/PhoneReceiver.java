package com.sxt.tel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * ���� �����Ⲧ����
 *
 */
public class PhoneReceiver extends BroadcastReceiver{
	@Override
	public void onReceive(Context arg0, Intent it) {
		String number = getResultData();//��ú���
		
		if("5556".equals(number)){
			setResultData(null);//��5556����绰���ε�
			abortBroadcast();//��ֹ�㲥,��ֹ���������㲥����
		}else{
			//����ȥ�ĺ������ز����ĺ���
			number = "110";
			setResultData(number);
			abortBroadcast();//��ֹ�㲥,��ֹ���������㲥����
		}
	}
	
}
