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
 * �����������ؽ��ն���
 *  1.<uses-permission android:name="android.permission.RECEIVE_SMS" />
 *  2.���������㲥��ע��
 */
public class SmsReciver extends Activity {
	private TextView messView;
	// ��������ǹ̶���
	public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	private EditText telInput;//�绰����
	private Button gocallButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recive);
		// ��ʾ���յ�����Ϣ
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
		// ������
		IntentFilter mFilter01 = new IntentFilter(SMS_RECEIVED_ACTION);
		recevier = new SmsReceiver();
		// ע��㲥����
		registerReceiver(recevier, mFilter01);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// ע�������¼�
		unregisterReceiver(recevier);
	}

	private SmsReceiver recevier;

	// ״̬�㲥
	class SmsReceiver extends BroadcastReceiver {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println(intent.getAction() + "======3======="
					+ getResultCode());
			switch (getResultCode()) {
			
			case Activity.RESULT_OK:// �������ͻ����-1
				if (SMS_RECEIVED_ACTION.equals(intent.getAction())) {
					Bundle bundle = intent.getExtras();
					Object[] pdus = (Object[]) bundle.get("pdus");
					SmsMessage[] msg = new SmsMessage[pdus.length];
					for (int i = 0; i < pdus.length; i++) {
						msg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					}
					StringBuilder sb = new StringBuilder();
					for (SmsMessage currMsg : msg) {
						sb.append("����:");
						sb.append(currMsg.getDisplayOriginatingAddress());
						sb.append("\n��Ϣ����:");
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
