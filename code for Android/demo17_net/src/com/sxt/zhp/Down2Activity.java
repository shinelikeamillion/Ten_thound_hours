package com.sxt.zhp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 文件下载 AsyncTask异步任务
 */
public class Down2Activity extends Activity {
	private TextView tv;
	private ProgressDialog dialog;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0://初始
				dialog.setTitle("下载提示");
				dialog.setMessage("正在连接...");
				dialog.show();
				break;
			case 1:// 准备下载
//				tv.setText("准备下载，文件总大小："+msg.arg1);
				dialog.setMessage("准备下载，文件总大小："+msg.arg1);
				dialog.setMax(msg.arg1);
				break;
			case 2:// 正在下载
//				tv.setText("正在下载:"+msg.arg1);
				dialog.setMessage("正在下载:"+msg.arg1);
				dialog.setProgress(msg.arg1);
				break;
			case 3://完成下载
//				tv.setText("完成下载："+msg.arg1);
				dialog.dismiss();
				Toast.makeText(Down2Activity.this, "下载完成！", Toast.LENGTH_LONG).show();
				
				break;
			case 4://异常
//				tv.setText("异常了稍后再试");
				dialog.dismiss();
				Toast.makeText(Down2Activity.this, "异常了稍后再试！", Toast.LENGTH_LONG).show();
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
		tv = (TextView) findViewById(R.id.textView1);
		
		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

		findViewById(R.id.button_net).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				down();
			}
		});
	}

	/**
	 * InputStream OutputStream
	 */
	private void down() {
		
		new Thread() {
			public void run() {
				try {
					//0通知初始
					sendMsg(0, 0);
					URL url = new URL("http://192.168.1.99:808/jQuery.ppt");

//					InputStream is = url.openStream();
					URLConnection conn = url.openConnection();
					conn.setConnectTimeout(5000);
					int fileSize = conn.getContentLength();//获取文件大小
					//1.通知准备
					sendMsg(1, fileSize);
					
					InputStream is = conn.getInputStream();
					

					File downDir = Environment.getExternalStorageDirectory();
					File appDir = new File(downDir, getPackageName());// 创建本应用程序包的文件夹目录
					if (appDir.exists() == false) {
						appDir.mkdirs();
					}
					File saveFilePath = new File(appDir, "jQuery.ppt");
					System.out.println(">>>===savePath:"
							+ saveFilePath.getAbsolutePath());
					OutputStream out = new FileOutputStream(saveFilePath);

					int downSize = 0;// 已经下载的大小

					byte[] b = new byte[1024];
					int len = 0;
					while ((len = is.read(b)) != -1) {
						out.write(b, 0, len);
						out.flush();
						downSize += len;// 累加已经下载量
						//2.通知正在下载
						sendMsg(2, downSize);
					}
					out.close();
					is.close();
					//3.通知下载完成
					sendMsg(3, downSize);
				} catch (Exception e) {
					e.printStackTrace();
					//4.通知异常
					sendMsg(4, 0);
				}
			};
		}.start();
	}
	
	private void sendMsg(int flag,int size){
		Message msg = new Message();
		msg.what = flag;
		msg.arg1 = size;
		handler.sendMessage(msg);
	}
}
