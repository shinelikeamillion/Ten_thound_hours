package com.sxt.tel;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * �˽���������ͬʱ�������Ⲧ�绰�������¼� ������� �������������Ⲧ�ͽ��ն��ķ��񣬼���ͬʱ�������¼�
 * 
 * 1.<uses-permission
 * android:name="android.permission.READ_PHONE_STATE"></uses-permission> 2.
 * <intent-filter> <action android:name="android.intent.action.PHONE_STATE"/>
 * </intent-filter>
 * 
 * ���ܣ�ͳ��ͨ��ʱ��
 */
public class PhoneComeInReceiver extends BroadcastReceiver {
	private static String TAG = "MyTag";
	private static long startTime;//��¼��ʼʱ��  �����Ǿ�̬��  ��Ϊÿ�ν��չ㲥��������ʵ���µĶ���

	@Override
	public void onReceive(Context context, Intent intent) {
//		System.out.println("======" + intent.getAction());
		// ����ǲ���绰Intent.ACTION_NEW_OUTGOING_CALL
		// ��������� android.intent.action.PHONE_STATE		
		if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {

			String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
			System.out.println(this+"**������룺" + phoneNumber);
//			System.out.println(getResultData() + "==������==" + intent.getAction());

			// ��������� ��õ绰������
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Service.TELEPHONY_SERVICE);

//			System.out.println("====tm.getCallState()====" + tm.getCallState());

			switch (tm.getCallState()) {
				case TelephonyManager.CALL_STATE_RINGING:// ����״̬
					String incoming_number = intent.getStringExtra("incoming_number");
					Log.i(TAG, "RINGING :" + incoming_number);
				break;
				case TelephonyManager.CALL_STATE_OFFHOOK: // ��ͨ�绰
					startTime = System.currentTimeMillis();//��¼��ʼͨ��ʱ��
					Log.i(TAG, "��ʼͨ�� :" + System.currentTimeMillis());
				break;

				case TelephonyManager.CALL_STATE_IDLE: // �Ҷϵ绰
					Log.i(TAG, "ͨ��ʱ�� :" + (System.currentTimeMillis()-startTime));
				break;
			}
		}
	}

}
