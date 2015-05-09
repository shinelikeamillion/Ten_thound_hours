package com.zhp.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * 
 * @author zhangchangpeng
 *	本例演示下载，线程，进度条
 *	ProgressDialog
 *  ProgressBar
 */
public class DownBaActivity extends Activity {
//	private ProgressBar pb;
	private ProgressDialog pb;
	private String urlStr = "http://192.168.1.9:8081/web/";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downbar);
//		pb = (ProgressBar) findViewById(R.id.progressBar_downbar);
		pb = new ProgressDialog(this);
		//设置样式为带百分比的水平进度条
		pb.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		 /**设置透明度*/
		  Window window = pb.getWindow();
		  WindowManager.LayoutParams lp = window.getAttributes();
		  lp.alpha = 0.7f;// 透明度

		  lp.dimAmount = 0.8f;// 黑暗度

		  window.setAttributes(lp);

		Button button = (Button) findViewById(R.id.button_downbar);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				down();
			}
		});
		
		
	}

	/**
	 * 下载
	 */
	private void down() {
		new Thread(netThread).start();
	}
	
	/**
	 * 下载线程
	 */
	private Runnable netThread = new Runnable() {

		@Override
		public void run() {
			try {
				URL url = new URL(urlStr+"tototoo.apk");
				URLConnection conn = url.openConnection();
				fileSize = conn.getContentLength();//文件大小
				
				sendMessage(1);//第一步 初始化
				
				InputStream ins = conn.getInputStream();
				
				File sdCard = Environment.getExternalStorageDirectory();
				
				File appPath = new File(sdCard.getAbsolutePath()+"/"+getPackageName());
				
				if(!appPath.exists()){
					appPath.mkdirs();
				}
				
				File file = new File(appPath,"test.zip");
				FileOutputStream out = new FileOutputStream(file);
				
				byte[] b =new byte[1024];
				int len = 0;
				while((len=ins.read(b))!=-1){
					out.write(b, 0, len);										
					System.out.println("======"+len);

					downFile+=len;
					sendMessage(2);//通知主线程 更新进度
				}
				
				
				sendMessage(3);
				
				out.close();
				ins.close();
			} catch (Exception e) {
				e.printStackTrace();
				
				sendMessage(4);
			}
		}
	};
	private int fileSize;
	private int downFile;//已经下载的大小
	private void sendMessage(int flag){
		Message msg = new Message();
		switch(flag){
			case 1://初始化
				msg.what = 0;
				handler.sendMessage(msg);
				break;
			case 2:
				msg.what = 1;
				handler.sendMessage(msg);
				break;
			case 3:
				msg.what = 2;
				handler.sendMessage(msg);
				break;
			case 4:
				msg.what = 3;
				handler.sendMessage(msg);
				break;
		}
	}

	// Handler对象
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0://初始化
//				pb.setVisibility(View.VISIBLE);
				pb.setProgress(0);
				pb.setMax(fileSize);
				pb.setTitle("正在下载");
				pb.show();
				
				break;
			case 1://正在下载
				pb.setMessage("请稍候...");
				pb.setProgress(downFile);
				break;
			case 2://下载完成
//				pb.setVisibility(View.VISIBLE);
				pb.dismiss();
				show("下载成功！");
				break;
			case 3://失败
//				pb.setVisibility(View.VISIBLE);
				pb.dismiss();
				show("下载失败！");
				break;

			default:
				break;
			}
		}
	};
	
	private void show(String mess){
		Toast.makeText(this, mess, Toast.LENGTH_LONG).show();
	}
}
