package com.sxt.msg;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * 需要权限：<uses-permission android:name="android.permission.RECEIVE_SMS" />
 * 短信接收 并提取验证码自动填入输入框
 * 同样需要BroadcastReceiver和清单配置intentFilter，但是不是单独配置<receiver>
 * 这里是内部类定义广播接收器，需要在Activity中代码实现注册广播监听
 * action的name要和清单的相同
 *
 */
public class ReciverActivity extends Activity{
	private SmsReciver sReciver;
	private static final String SMS_RECIVER = "android.provider.Telephony.SMS_RECEIVED";
	private EditText edtCode;
	private EditText edtPhone;
	private TextView tvMsg;
	private Button btnReg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reciver);
		edtCode = (EditText) findViewById(R.id.edt_code);
		edtPhone = (EditText) findViewById(R.id.edt_phone);
		tvMsg = (TextView) findViewById(R.id.tv_showMsg);
		btnReg = (Button) findViewById(R.id.btn_complete);
		btnReg.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {	
				Intent data = new Intent();//主要用来带参数的  
				data.putExtra("phone", edtPhone.getText().toString());
				setResult(RESULT_OK, data);//把意图对象放入结果
				finish();
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// 过滤器
		IntentFilter mFilter01 = new IntentFilter(SMS_RECIVER);
		sReciver = new SmsReciver();
		// 注册广播监听
		registerReceiver(sReciver, mFilter01);

	}
	@Override
	protected void onPause() {
		super.onPause();
		// 注销监听事件
		unregisterReceiver(sReciver);
	}
	
	/**
	 * 广播接收器
	 */
	class SmsReciver extends BroadcastReceiver{
		
		@Override
		public void onReceive(Context arg0, Intent it) {
			
			String action = it.getAction();
			int state = getResultCode();//接收状态
			
			if(state==Activity.RESULT_OK){
				if(SMS_RECIVER.equals(action)){
					Bundle bundle = it.getExtras(); 
					
					Object[] pdus = (Object[]) bundle.get("pdus");//每个元素为byte[]类型 存放了短信的内容、发件人等信息
					SmsMessage[] msg = new SmsMessage[pdus.length];
					for (int i = 0; i < pdus.length; i++) {//把Object[]数组中的byte[]元素 转换为SmsMessage对象
						msg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					}

					//提取消息内容和发信人等信息
					StringBuilder sbf = new StringBuilder();//存放自己格式化要显示的内容
					StringBuilder sbfContent = new StringBuilder();//只存内容
					for (SmsMessage smsMessage : msg) {
						sbf.append("\n内容："+smsMessage.getDisplayMessageBody());
						sbf.append("\n发信人："+smsMessage.getDisplayOriginatingAddress());
						
						Date date = new Date(smsMessage.getTimestampMillis());
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sbf.append("\n日期："+format.format(date));
						
						sbfContent.append(smsMessage.getDisplayMessageBody());
						
//						System.out.println(smsMessage.getMessageBody());//也可获取内容
					}
					
					tvMsg.setText(sbf.toString());
					
					//提取验证码
					int index = sbfContent.indexOf("code:");
					if(index>=0){
						String code = sbfContent.substring(index+5,index+9);
						edtCode.setText(code);
						btnReg.setEnabled(true);//变为可用
					}
				}
			}

		}		
	}

}
