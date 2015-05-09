package com.zhp;

import com.zhp.MessageActivity.ServiceReceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author zhangchangpeng
 * 
 * 本案例：拦截接收短信
 *  1.<uses-permission android:name="android.permission.RECEIVE_SMS" />
 *  2.过滤器，广播，注册
 */
public class SmsReciver extends Activity {
	private TextView messView;
	// 这个常量是固定的
	public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	private EditText telInput;//电话号码
	private Button gocallButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recive);
		// 显示接收到的消息
		messView = (TextView) findViewById(R.id.text_recevie);
		telInput = (EditText) findViewById(R.id.edit_tel);
		gocallButton = (Button) findViewById(R.id.button_call_go);
		gocallButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), TelActivity.class);
				startActivity(intent);
				
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 过滤器
		IntentFilter mFilter01 = new IntentFilter(SMS_RECEIVED_ACTION);
		recevier = new SmsReceiver();
		// 注册广播监听
		registerReceiver(recevier, mFilter01);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 注销监听事件
		unregisterReceiver(recevier);
	}

	private SmsReceiver recevier;

	// 状态广播
	class SmsReceiver extends BroadcastReceiver {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println(intent.getAction() + "======3======="
					+ getResultCode());
			switch (getResultCode()) {
			
			case Activity.RESULT_OK:// 正常发送或接收-1
				if (SMS_RECEIVED_ACTION.equals(intent.getAction())) {
					Bundle bundle = intent.getExtras();
					Object[] pdus = (Object[]) bundle.get("pdus");
					SmsMessage[] msg = new SmsMessage[pdus.length];
					for (int i = 0; i < pdus.length; i++) {
						msg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					}
					StringBuilder sb = new StringBuilder();
					for (SmsMessage currMsg : msg) {
						sb.append("来自:");
						sb.append(currMsg.getDisplayOriginatingAddress());
						sb.append("\n消息内容:");
						sb.append(currMsg.getDisplayMessageBody());
					}

					System.out.println(sb + "====" + intent.getExtras());
					messView.setText(sb);
				}
				break;

			}

		}
	}
}
