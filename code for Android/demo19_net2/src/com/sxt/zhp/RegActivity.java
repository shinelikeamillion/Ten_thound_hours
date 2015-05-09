package com.sxt.zhp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class RegActivity extends Activity {
	private EditText edtUname;
	private EditText edtUpass;
	private EditText edtUage;
	private EditText edtUemail;
	private ProgressDialog dialog;
	private String urlStr = "http://192.168.1.99:808/reg";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg);
		
		
		edtUname = (EditText) findViewById(R.id.edt_uname);
		edtUpass = (EditText) findViewById(R.id.edt_pass);
		edtUage = (EditText) findViewById(R.id.edt_age);
		edtUemail = (EditText) findViewById(R.id.edt_email);
		
		
		findViewById(R.id.btn_reg).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				new Thread(){public void run() {
					reg();
				}}.start();
			}
		});
		
	}
	private void reg(){
		try {
			
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			putDataToList(paramList);//Ìî³äÊý¾Ýµ½List
			
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(urlStr);
			
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
			
			post.setEntity(entity);
			
			client.execute(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void putDataToList(List<NameValuePair> paramList){
		String uname = edtUname.getText().toString();
		String age = edtUage.getText().toString();
		String email = edtUemail.getText().toString();
		String upass = edtUpass.getText().toString();
		
		BasicNameValuePair nvUname = new BasicNameValuePair("uname", uname);
		BasicNameValuePair nvAge = new BasicNameValuePair("age", age);
		BasicNameValuePair nvEmail = new BasicNameValuePair("email", email);
		BasicNameValuePair nvUpass = new BasicNameValuePair("upass", upass);
		
		paramList.add(nvAge);
		paramList.add(nvUname);
		paramList.add(nvEmail);
		paramList.add(nvUpass);
	}
	
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				dialog.dismiss();
				Toast.makeText(RegActivity.this, "×¢²á³É¹¦£¡", Toast.LENGTH_LONG)
						.show();
				break;
			case 2:
				dialog.dismiss();
				Toast.makeText(RegActivity.this, "×¢²áÊ§°Ü£¡", Toast.LENGTH_LONG)
						.show();
				break;
			default:
				break;
			}
		};
	};
}
