package com.sxt.zhp;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 线程通信的几种方法 1.View中post()方法 2.View中postDelayed() 3.Activity中的runOnUiThread()
 * 以上三种，不能及时更新视图。只有等线程结束后才能更新UI,应用于不看过程只看结果的情况下 4.Handler机制
 * 
 */
public class DemoPostActivity extends Activity {
	private Handler handler;
	private TextView tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tv = (TextView) findViewById(R.id.textView1);
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {				
				runTime();
			}
		});
		

	}

	private void runTime() {
		MyThread mt = new MyThread();
		 tv.post(mt);//View中的方法
//		 tv.postDelayed(mt, 3000);

//		 runOnUiThread(mt);//Activity方法
//
//		 mt.start();
	}

	private class MyThread extends Thread {
		private int i;

		public void run() {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy年MM月dd日 HH:mm:ss");

			while (true) {
				System.out.println("---------------" + i);
				String str = format.format(new Date());
				tv.setText(str);// 更新view视图 使用post只能在线程结束后看到UI更新
				// 休眠1秒
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				i++;
				if (i >= 10) {
					break;
				}
			}
		};
	}
}