package com.sxt.zhp;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class NethandlerActivity extends Activity{
	private TextView tv;
	private ProgressBar bar;
	private ProgressDialog dialog;
	/**
	 * 
	 */
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1://成功
				//Toast.makeText(NethandlerActivity.this, "加载完成！", Toast.LENGTH_LONG).show();
				tv.setText(msg.obj.toString());
//				bar.setVisibility(View.GONE);
				dialog.dismiss();
				break;
			case 2://失败
				//Toast.makeText(NethandlerActivity.this, "加载失败！", Toast.LENGTH_LONG).show();
//				bar.setVisibility(View.GONE);
				dialog.dismiss();
				break;
			default:
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.net);
		
		dialog = new ProgressDialog(this);
		dialog.setTitle("正在拼命加载..");
//		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		
		tv = (TextView) findViewById(R.id.textView1);
		bar = (ProgressBar) findViewById(R.id.progressBar1);
		findViewById(R.id.button_net).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
//				bar.setVisibility(View.VISIBLE);//进度条显示
				dialog.show();//显示进度框
				new Thread(){
					public void run() {
						net();
					};
				}.start();
			}
		});
	}
	/**
	 * 加载网络数据
	 */
	private void net(){
		try {
			URL url = new URL("http://192.168.1.99:808"); 
			InputStream ins = url.openStream();
			byte[] b = new byte[1024];
			int len = 0;
			StringBuilder sbf = new StringBuilder();
			while ((len = ins.read(b)) != -1) {
				String str = new String(b,0,len);
				sbf.append(str);
			}						
			ins.close();

			//消息通知
			Message msg = new Message();
			msg.what = 1;
			msg.obj = sbf;
			handler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
			
			Message msg = new Message();
			msg.what = 2;
			handler.sendMessage(msg);
		}
	}
}
