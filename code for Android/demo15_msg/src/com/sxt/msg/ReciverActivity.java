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
 * ��ҪȨ�ޣ�<uses-permission android:name="android.permission.RECEIVE_SMS" />
 * ���Ž��� ����ȡ��֤���Զ����������
 * ͬ����ҪBroadcastReceiver���嵥����intentFilter�����ǲ��ǵ�������<receiver>
 * �������ڲ��ඨ��㲥����������Ҫ��Activity�д���ʵ��ע��㲥����
 * action��nameҪ���嵥����ͬ
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
				Intent data = new Intent();//��Ҫ������������  
				data.putExtra("phone", edtPhone.getText().toString());
				setResult(RESULT_OK, data);//����ͼ���������
				finish();
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// ������
		IntentFilter mFilter01 = new IntentFilter(SMS_RECIVER);
		sReciver = new SmsReciver();
		// ע��㲥����
		registerReceiver(sReciver, mFilter01);

	}
	@Override
	protected void onPause() {
		super.onPause();
		// ע�������¼�
		unregisterReceiver(sReciver);
	}
	
	/**
	 * �㲥������
	 */
	class SmsReciver extends BroadcastReceiver{
		
		@Override
		public void onReceive(Context arg0, Intent it) {
			
			String action = it.getAction();
			int state = getResultCode();//����״̬
			
			if(state==Activity.RESULT_OK){
				if(SMS_RECIVER.equals(action)){
					Bundle bundle = it.getExtras(); 
					
					Object[] pdus = (Object[]) bundle.get("pdus");//ÿ��Ԫ��Ϊbyte[]���� ����˶��ŵ����ݡ������˵���Ϣ
					SmsMessage[] msg = new SmsMessage[pdus.length];
					for (int i = 0; i < pdus.length; i++) {//��Object[]�����е�byte[]Ԫ�� ת��ΪSmsMessage����
						msg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					}

					//��ȡ��Ϣ���ݺͷ����˵���Ϣ
					StringBuilder sbf = new StringBuilder();//����Լ���ʽ��Ҫ��ʾ������
					StringBuilder sbfContent = new StringBuilder();//ֻ������
					for (SmsMessage smsMessage : msg) {
						sbf.append("\n���ݣ�"+smsMessage.getDisplayMessageBody());
						sbf.append("\n�����ˣ�"+smsMessage.getDisplayOriginatingAddress());
						
						Date date = new Date(smsMessage.getTimestampMillis());
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sbf.append("\n���ڣ�"+format.format(date));
						
						sbfContent.append(smsMessage.getDisplayMessageBody());
						
//						System.out.println(smsMessage.getMessageBody());//Ҳ�ɻ�ȡ����
					}
					
					tvMsg.setText(sbf.toString());
					
					//��ȡ��֤��
					int index = sbfContent.indexOf("code:");
					if(index>=0){
						String code = sbfContent.substring(index+5,index+9);
						edtCode.setText(code);
						btnReg.setEnabled(true);//��Ϊ����
					}
				}
			}

		}		
	}

}
