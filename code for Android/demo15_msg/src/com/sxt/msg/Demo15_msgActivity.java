package com.sxt.msg;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * ���Ͷ���ʾ�� 
 * ע��Ҫ����������Ϣ��Ȩ�� <uses-permission android:name="android.permission.SEND_SMS"/>
 */
public class Demo15_msgActivity extends Activity {
	private Button sendButton;
	private EditText tel_text;
	private EditText content;
	private Button moreButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		tel_text = (EditText) findViewById(R.id.editText_tel);// �绰���������
		content = (EditText) findViewById(R.id.editText_content);// ���͵����ݿ�
		// ���Ͷ��ŵİ�ť
		sendButton = (Button) findViewById(R.id.button_send);
		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String tel = tel_text.getText() + "";// ��õ绰����
				String mess = content.getText() + "";// ���͵�����
				sendMess(tel, mess);
			}
		});
		
		//���ʹ�����Ϣ
		moreButton = (Button) findViewById(R.id.button_more);
		moreButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				String tel = tel_text.getText() + "";// ��õ绰����
				String mess = content.getText() + "";// ���͵�����
				sendMoreMsg(tel, mess);
			}
		});
    }
	/**
	 * �򵥵� ���Ͷ��ŵķ��� �˷��������ַ������󣬻�ʧ�ܣ�160����
	 */
	public void sendMess(String tel, String content) {

		/* ����SmsManager���� */
		SmsManager smsManager = SmsManager.getDefault();
		

		// ���������룬�������ĵ�ַ�����ݣ�����״̬������״̬�㲥
		smsManager.sendTextMessage(tel, null, content, null, null);

		Toast.makeText(this, "��" + tel + "���Ͷ������", Toast.LENGTH_LONG).show();
	}
	
	private void sendMoreMsg(String tel,String content){
		SmsManager smanager = SmsManager.getDefault();
		
		if(content.length()>70){
			List<String> list = smanager.divideMessage(content);
			for (String string : list) {
				System.out.println("length:"+string.length());
				smanager.sendTextMessage(tel, null, string, null, null);
			}
			System.out.println(">>>>>>===="+list.size());
		}
		
		Toast.makeText(this, "��" + tel + "���Ͷ������", Toast.LENGTH_LONG).show();
	}
}