package com.sxt.msg;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * 监听短信的收发状态
 * 这里不需要在清单配置
 * 通过代码注册监听和发送短消息时添加过滤器意图来实现
 */
public class StateActivity extends Activity{
	private Button sendButton;
	private EditText tel_text;
	private EditText content;
	// 自定义ACTION常数，作为广播的Intent Filter识别常量，通过字符串常量来区分不同的广播消息类型 */
	private String SMS_SEND_ACTIOIN = "SMS_SEND_ACTIOIN";
	private String SMS_DELIVERED_ACTION = "SMS_DELIVERED_ACTION";
	
	//创建两个ServiceReceiver对象，作为类成员变量	
	private ServiceReceiver receiverstate, sendstate;// 监听接收状态和发送状态的对象
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tel_text = (EditText) findViewById(R.id.editText_tel);// 电话号码输入框
		content = (EditText) findViewById(R.id.editText_content);// 发送的内容框
		// 发送短信的按钮
		sendButton = (Button) findViewById(R.id.button_send);
		sendButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String tel = tel_text.getText() + "";// 获得电话号码
				String mess = content.getText() + "";// 发送的内容
				sendMess(tel, mess);
			}
		});
	}
	/**
	 * 简单的 发送短信的方法 此方法发送字符数过大，会失败，160左右
	 */
	public void sendMess(String tel, String content) {

		/* 创建SmsManager对象 */
		SmsManager smsManager = SmsManager.getDefault();
		
		/* 这里的常量引用，和广播监听过滤对象保持一致，以便监听对应的消息*/
        Intent itSend = new Intent(SMS_SEND_ACTIOIN);
        Intent itDeliver = new Intent(SMS_DELIVERED_ACTION);
        
        /* sentIntent参数为传送后接受的广播信息PendingIntent */
        PendingIntent mSendPI = PendingIntent.getBroadcast(this, 0, itSend, 0);
        
        /* deliveryIntent参数为送达后接受的广播信息PendingIntent */
        PendingIntent mDeliverPI = PendingIntent.getBroadcast(this, 0, itDeliver, 0);

		// 参数：号码，服务中心地址，内容，发送状态，接收状态广播
		smsManager.sendTextMessage(tel, null, content, mSendPI, mDeliverPI);

		Toast.makeText(this, "向" + tel + "发送短信完成", Toast.LENGTH_LONG).show();
	}
	

	@Override
	protected void onResume()
	{
	    /* 自定义IntentFilter为SENT_SMS_ACTIOIN Receiver */
	    IntentFilter mFilter01 = new IntentFilter(SMS_DELIVERED_ACTION);
	    receiverstate = new ServiceReceiver();
	    //注册监听器，来监听接收状态广播
	    registerReceiver(receiverstate, mFilter01);
	    
	    //为发送状态过滤器
	    IntentFilter mFilter02 = new IntentFilter(SMS_SEND_ACTIOIN);
	    sendstate = new ServiceReceiver();
	    registerReceiver(sendstate, mFilter02);    
	    super.onResume();
	}
	/**
	 * 在暂停或退出时，注销监听
	 */
	protected void onPause()
	{
	    /* 取消注册自定义Receiver */
	    unregisterReceiver(receiverstate);
	    unregisterReceiver(sendstate);
	    
	    super.onPause();
	}
	
	/**
	 * 自定义内部类广播接收器
	 * BroadcastReceiver 是对发送出来的广播进行过滤接收并响应的一类组件；
	 * 自定义ServiceReceiver覆盖BroadcastReceiver监听短信状态信息
	 */
	public class ServiceReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println(intent.getAction() + "=====收到广播==="+ getResultCode());
			switch (getResultCode()) {
				case Activity.RESULT_OK://正常发送或接收-1
					if (SMS_SEND_ACTIOIN.equals(intent.getAction())) {
						Toast.makeText(getApplicationContext(), "短信发送成功！", Toast.LENGTH_LONG).show();
					}else if(SMS_DELIVERED_ACTION.equals(intent.getAction())){
						Toast.makeText(getApplicationContext(), "对方已经收到短信！", Toast.LENGTH_LONG).show();
					}
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE://短息发送失败	1
					if (SMS_SEND_ACTIOIN.equals(intent.getAction())) {
						Toast.makeText(getApplicationContext(), "短信发送失败！", Toast.LENGTH_LONG).show();
					}
					break;					
			}
		}
	}
}
