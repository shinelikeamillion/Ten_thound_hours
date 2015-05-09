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
 * �߳�ͨ�ŵļ��ַ��� 1.View��post()���� 2.View��postDelayed() 3.Activity�е�runOnUiThread()
 * �������֣����ܼ�ʱ������ͼ��ֻ�е��߳̽�������ܸ���UI,Ӧ���ڲ�������ֻ������������ 4.Handler����
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
		 tv.post(mt);//View�еķ���
//		 tv.postDelayed(mt, 3000);

//		 runOnUiThread(mt);//Activity����
//
//		 mt.start();
	}

	private class MyThread extends Thread {
		private int i;

		public void run() {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy��MM��dd�� HH:mm:ss");

			while (true) {
				System.out.println("---------------" + i);
				String str = format.format(new Date());
				tv.setText(str);// ����view��ͼ ʹ��postֻ�����߳̽����󿴵�UI����
				// ����1��
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