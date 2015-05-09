package com.sxt.zhp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private EditText edtUname;
	private EditText edtUpass;
	private Button btnLogin;

	private ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		dialog = new ProgressDialog(this);
		dialog.setTitle("正在登录...");
		edtUname = (EditText) findViewById(R.id.edt_name);
		edtUpass = (EditText) findViewById(R.id.edtPass);

		btnLogin = (Button) findViewById(R.id.button1);
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.show();
				new Thread() {
					public void run() {
						login();
					};
				}.start();
			}
		});
	}

	private void login() {

		System.out.println("---------login----------");
		try {
			String urlStr = "http://192.168.1.99:808/login";

			DefaultHttpClient client = new DefaultHttpClient();
			
			HttpPost httpPost = new HttpPost(urlStr);
			

			String params = "uname=" + edtUname.getText() + "&upass="
					+ edtUpass.getText();
			StringEntity entity = new StringEntity(params, HTTP.UTF_8);//请求实体对象
			// 设置内容类型
			entity.setContentType("application/x-www-form-urlencoded");

			httpPost.setEntity(entity);
			
			HttpResponse response = client.execute(httpPost);
			StatusLine status = response.getStatusLine();
			int code = status.getStatusCode();
			
			if (code == 200) {
				HttpEntity resEntity = response.getEntity();//响应实体对象
				int  len = (int) resEntity.getContentLength();
				InputStream is = resEntity.getContent();
				
				System.out.println(resEntity.getContentLength()+"*******===="+is.available());
//				int len = is.available();
				byte[] b = new byte[len];
				is.read(b);
				String result = new String(b);
//				String result = convertStreamToString(is);
				System.out.println(is+">>>>==="+len);
				if (result.equals("true")) {
					handler.sendEmptyMessage(1);
				} else {
					handler.sendEmptyMessage(2);
				}
				is.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			handler.sendEmptyMessage(2);
		}
	}
	/**
	 * 从流中解析提取出字符串
	 * @param is 
	 * @return
	 */
	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	Handler handler = new Handler() { 
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				dialog.dismiss();
				Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_LONG)
						.show();
				Intent it = new Intent(LoginActivity.this,JsonActivity.class);
				startActivity(it);
				break;
			case 2:
				dialog.dismiss();
				Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_LONG)
						.show();
				break;
			default:
				break;
			}
		};
	};
}