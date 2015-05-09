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
 * 发送短信示例 
 * 注意要开启发短消息的权限 <uses-permission android:name="android.permission.SEND_SMS"/>
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
		
		tel_text = (EditText) findViewById(R.id.editText_tel);// 电话号码输入框
		content = (EditText) findViewById(R.id.editText_content);// 发送的内容框
		// 发送短信的按钮
		sendButton = (Button) findViewById(R.id.button_send);
		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String tel = tel_text.getText() + "";// 获得电话号码
				String mess = content.getText() + "";// 发送的内容
				sendMess(tel, mess);
			}
		});
		
		//发送大量消息
		moreButton = (Button) findViewById(R.id.button_more);
		moreButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				String tel = tel_text.getText() + "";// 获得电话号码
				String mess = content.getText() + "";// 发送的内容
				sendMoreMsg(tel, mess);
			}
		});
    }
	/**
	 * 简单的 发送短信的方法 此方法发送字符数过大，会失败，160左右
	 */
	public void sendMess(String tel, String content) {

		/* 创建SmsManager对象 */
		SmsManager smsManager = SmsManager.getDefault();
		

		// 参数：号码，服务中心地址，内容，发送状态，接收状态广播
		smsManager.sendTextMessage(tel, null, content, null, null);

		Toast.makeText(this, "向" + tel + "发送短信完成", Toast.LENGTH_LONG).show();
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
		
		Toast.makeText(this, "向" + tel + "发送短信完成", Toast.LENGTH_LONG).show();
	}
}