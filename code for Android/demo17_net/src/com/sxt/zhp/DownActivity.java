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
 * �ļ����� AsyncTask�첽����
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
//			tv.setText("׼����ʼ....");
			dialog.setTitle("������ʾ");
			dialog.setMessage("��������...");
			dialog.show();
			
			super.onPreExecute();
		}

		/**
		 * ���½���
		 */
		protected void onProgressUpdate(Integer... values) {
			System.out.println("=====onPreExecute=====");
//			tv.setText("��ǰ����Ϊ��"+values[0]);
			dialog.setProgress(values[0]);
			super.onProgressUpdate(values);
			
		}

		/**
		 * ִ�к�ʱ����
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
//				this.publishProgress(i);///��������  �Զ����ݵ���onProgressUpdate
//			}
			
			
			try {

				URL url = new URL("http://192.168.1.99:808/jQuery.ppt");

//				InputStream is = url.openStream();
				URLConnection conn = url.openConnection();
				conn.setConnectTimeout(5000);
				int fileSize = conn.getContentLength();//��ȡ�ļ���С
				//1.֪ͨ׼��
//				sendMsg(1, fileSize);
				dialog.setMax(fileSize);
				
				InputStream is = conn.getInputStream();
				

				File downDir = Environment.getExternalStorageDirectory();
				File appDir = new File(downDir, getPackageName());// ������Ӧ�ó�������ļ���Ŀ¼
				if (appDir.exists() == false) {
					appDir.mkdirs();
				}
				File saveFilePath = new File(appDir, "jQuery.ppt");
				System.out.println(">>>===savePath:"
						+ saveFilePath.getAbsolutePath());
				OutputStream out = new FileOutputStream(saveFilePath);

				int downSize = 0;// �Ѿ����صĴ�С

				byte[] b = new byte[1024];
				int len = 0;
				while ((len = is.read(b)) != -1) {
					out.write(b, 0, len);
					out.flush();
					downSize += len;// �ۼ��Ѿ�������
					//2.֪ͨ��������
					this.publishProgress(downSize);
				}
				out.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
				//4.֪ͨ�쳣
				sendMsg(4, 0);
			}
			return "sucess";
		}
		
		
	}
}
