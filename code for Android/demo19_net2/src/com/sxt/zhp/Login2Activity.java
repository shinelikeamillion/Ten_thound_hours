package com.sxt.zhp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login2Activity extends Activity {
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
		try {
			String urlStr = "http://192.168.1.99:808/login";
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			System.out.println("=====" + conn);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			
			conn.setReadTimeout(10000);
			conn.setRequestMethod("POST");
			
			//以输出流的方式提交数据到服务器
			OutputStream out = conn.getOutputStream();
			String params = "uname="+edtUname.getText()+"&upass="+edtUpass.getText();
			out.write(params.getBytes());
			
			String responseText = conn.getResponseMessage();
			System.out.println(">>>>>=="+responseText);

			//获取响应的状态码
			int code = conn.getResponseCode();
			if (code == 200) {//正常响应
				InputStream is = conn.getInputStream();
				int len = is.available();
				byte[] b = new byte[len];

				is.read(b);
				String result = new String(b);
				if (result.equals("true")) {
					handler.sendEmptyMessage(1);
				} else {
					handler.sendEmptyMessage(2);
				}
				is.close();
			}

			out.close();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get请求方式
	 */
	private void loginGet() {
		try {
			String urlStr = "http://192.168.1.99:808/login";
			String uname = edtUname.getText().toString();
			String upass = edtUpass.getText().toString();
			urlStr += "?uname=" + uname + "&upass=" + upass;

			URL url = new URL(urlStr);
			InputStream is = url.openStream();
			int len = is.available();
			byte[] b = new byte[len];

			is.read(b);
			String responseText = new String(b);
			System.out.println("=====" + responseText + "====");
			if (responseText.equals("true")) {

				handler.sendEmptyMessage(1);
			} else {
				handler.sendEmptyMessage(2);
			}

			is.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				dialog.dismiss();
				Toast.makeText(Login2Activity.this, "登录成功！", Toast.LENGTH_LONG)
						.show();
				break;
			case 2:
				dialog.dismiss();
				Toast.makeText(Login2Activity.this, "登录失败！", Toast.LENGTH_LONG)
						.show();
				break;
			default:
				break;
			}
		};
	};
}