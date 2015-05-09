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
 * �������ŵ��շ�״̬
 * ���ﲻ��Ҫ���嵥����
 * ͨ������ע������ͷ��Ͷ���Ϣʱ��ӹ�������ͼ��ʵ��
 */
public class StateActivity extends Activity{
	private Button sendButton;
	private EditText tel_text;
	private EditText content;
	// �Զ���ACTION��������Ϊ�㲥��Intent Filterʶ������ͨ���ַ������������ֲ�ͬ�Ĺ㲥��Ϣ���� */
	private String SMS_SEND_ACTIOIN = "SMS_SEND_ACTIOIN";
	private String SMS_DELIVERED_ACTION = "SMS_DELIVERED_ACTION";
	
	//��������ServiceReceiver������Ϊ���Ա����	
	private ServiceReceiver receiverstate, sendstate;// ��������״̬�ͷ���״̬�Ķ���
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tel_text = (EditText) findViewById(R.id.editText_tel);// �绰���������
		content = (EditText) findViewById(R.id.editText_content);// ���͵����ݿ�
		// ���Ͷ��ŵİ�ť
		sendButton = (Button) findViewById(R.id.button_send);
		sendButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String tel = tel_text.getText() + "";// ��õ绰����
				String mess = content.getText() + "";// ���͵�����
				sendMess(tel, mess);
			}
		});
	}
	/**
	 * �򵥵� ���Ͷ��ŵķ��� �˷��������ַ������󣬻�ʧ�ܣ�160����
	 */
	public void sendMess(String tel, String content) {

		/* ����SmsManager���� */
		SmsManager smsManager = SmsManager.getDefault();
		
		/* ����ĳ������ã��͹㲥�������˶��󱣳�һ�£��Ա������Ӧ����Ϣ*/
        Intent itSend = new Intent(SMS_SEND_ACTIOIN);
        Intent itDeliver = new Intent(SMS_DELIVERED_ACTION);
        
        /* sentIntent����Ϊ���ͺ���ܵĹ㲥��ϢPendingIntent */
        PendingIntent mSendPI = PendingIntent.getBroadcast(this, 0, itSend, 0);
        
        /* deliveryIntent����Ϊ�ʹ����ܵĹ㲥��ϢPendingIntent */
        PendingIntent mDeliverPI = PendingIntent.getBroadcast(this, 0, itDeliver, 0);

		// ���������룬�������ĵ�ַ�����ݣ�����״̬������״̬�㲥
		smsManager.sendTextMessage(tel, null, content, mSendPI, mDeliverPI);

		Toast.makeText(this, "��" + tel + "���Ͷ������", Toast.LENGTH_LONG).show();
	}
	

	@Override
	protected void onResume()
	{
	    /* �Զ���IntentFilterΪSENT_SMS_ACTIOIN Receiver */
	    IntentFilter mFilter01 = new IntentFilter(SMS_DELIVERED_ACTION);
	    receiverstate = new ServiceReceiver();
	    //ע�������������������״̬�㲥
	    registerReceiver(receiverstate, mFilter01);
	    
	    //Ϊ����״̬������
	    IntentFilter mFilter02 = new IntentFilter(SMS_SEND_ACTIOIN);
	    sendstate = new ServiceReceiver();
	    registerReceiver(sendstate, mFilter02);    
	    super.onResume();
	}
	/**
	 * ����ͣ���˳�ʱ��ע������
	 */
	protected void onPause()
	{
	    /* ȡ��ע���Զ���Receiver */
	    unregisterReceiver(receiverstate);
	    unregisterReceiver(sendstate);
	    
	    super.onPause();
	}
	
	/**
	 * �Զ����ڲ���㲥������
	 * BroadcastReceiver �ǶԷ��ͳ����Ĺ㲥���й��˽��ղ���Ӧ��һ�������
	 * �Զ���ServiceReceiver����BroadcastReceiver��������״̬��Ϣ
	 */
	public class ServiceReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println(intent.getAction() + "=====�յ��㲥==="+ getResultCode());
			switch (getResultCode()) {
				case Activity.RESULT_OK://�������ͻ����-1
					if (SMS_SEND_ACTIOIN.equals(intent.getAction())) {
						Toast.makeText(getApplicationContext(), "���ŷ��ͳɹ���", Toast.LENGTH_LONG).show();
					}else if(SMS_DELIVERED_ACTION.equals(intent.getAction())){
						Toast.makeText(getApplicationContext(), "�Է��Ѿ��յ����ţ�", Toast.LENGTH_LONG).show();
					}
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE://��Ϣ����ʧ��	1
					if (SMS_SEND_ACTIOIN.equals(intent.getAction())) {
						Toast.makeText(getApplicationContext(), "���ŷ���ʧ�ܣ�", Toast.LENGTH_LONG).show();
					}
					break;					
			}
		}
	}
}
