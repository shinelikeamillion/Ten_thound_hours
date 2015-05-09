package com.sxt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class RawActivity extends Activity {
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.raw);

		tv = (TextView) findViewById(R.id.textView_raw);
		findViewById(R.id.button_raw).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				readRaw();
			}
		});
	}

	/**
	 * [abcdefj1234567890]//17字节 byte[] b = new byte[16]; {a,b,cdefj1234567,8,9}
	 * {0,b,cdefj1234567,8,9}
	 */
	private void readRaw() {
		Resources r = getResources();
		InputStream ins = r.openRawResource(R.raw.tt);

		try {
			//字节字符转换流
			InputStreamReader reader = new InputStreamReader(ins, "gbk");
			//字符缓冲流
			BufferedReader br = new BufferedReader(reader);
			String line = null;
			StringBuilder sbf0 = new StringBuilder();

			while ((line = br.readLine()) != null) {
				sbf0.append(line);
			}
			tv.setText(sbf0.toString());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		// try {
		// byte[] b = new byte[128];
		// StringBuilder sbf = new StringBuilder();
		// int len = 0;//读取字节数
		// while((len=ins.read(b))!=-1){
		// String ss = new String(b,0,len,"gbk");
		// sbf.append(ss);
		// }
		//			
		// tv.setText(sbf.toString());
		//			
		// ins.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}
}
