package com.sxt.zhp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
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
public class DownActivity extends Activity {
	private TextView tv;
	private ProgressDialog dialog;
	
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
//				down();
				new MyTask().execute("http://192.168.1.99:808/jQuery.ppt");
			}
		});
	}

	
	private void sendMsg(int flag,int size){
		Message msg = new Message();
		msg.what = flag;
		msg.arg1 = size;
//		handler.sendMessage(msg);
	}
	
	class MyTask extends AsyncTask<String, Integer, String>{
		
		@Override
		protected void onCancelled() {
			System.out.println("=========onCancelled=========");
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(String result) {
			System.out.println("=========result=========");
			dialog.dismiss();
			Toast.makeText(DownActivity.this, result, Toast.LENGTH_LONG).show();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			System.out.println("=====onPreExecute=====");
//			tv.setText("准备开始....");
			dialog.setTitle("下载提示");
			dialog.setMessage("正在连接...");
			dialog.show();
			
			super.onPreExecute();
		}

		/**
		 * 更新进度
		 */
		protected void onProgressUpdate(Integer... values) {
			System.out.println("=====onPreExecute=====");
//			tv.setText("当前进度为："+values[0]);
			dialog.setProgress(values[0]);
			super.onProgressUpdate(values);
			
		}

		/**
		 * 执行耗时操作
		 */
		protected String doInBackground(String... arg0) {
			System.out.println("=====onPreExecute====="+Arrays.toString(arg0));
//			for (int i = 0; i < 10; i++) {
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				this.publishProgress(i);///发布进度  自动传递调用onProgressUpdate
//			}
			
			
			try {

				URL url = new URL("http://192.168.1.99:808/jQuery.ppt");

//				InputStream is = url.openStream();
				URLConnection conn = url.openConnection();
				conn.setConnectTimeout(5000);
				int fileSize = conn.getContentLength();//获取文件大小
				//1.通知准备
//				sendMsg(1, fileSize);
				dialog.setMax(fileSize);
				
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
					this.publishProgress(downSize);
				}
				out.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
				//4.通知异常
				sendMsg(4, 0);
			}
			return "sucess";
		}
		
		
	}
}
