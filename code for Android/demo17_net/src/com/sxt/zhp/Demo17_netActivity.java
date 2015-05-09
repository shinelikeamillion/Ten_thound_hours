package com.sxt.zhp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class Demo17_netActivity extends Activity {
	private TextView tv;
	private WebView webView;
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tv = (TextView) findViewById(R.id.showContent);
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
//						page();
//						accessBrowser();
						webView();
					};
				}.start();			
			}
		});
		
		webView = (WebView) findViewById(R.id.webView1);
	}

	/**
	 * ������ҳ
	 */
	private void page() {
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
			//tv.setText(sbf.toString());//�߳����ǲ�����������߳��е�View���
			System.out.println("========"+sbf);
			ins.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    /**
     * ���������ACTION_VIEW
     */
    private void accessBrowser(){
    	
    	Intent it = new Intent(Intent.ACTION_VIEW,Uri.parse("http://192.168.1.99:808"));
    	startActivity(it);
    }
    /**
     * webView
     */
    private void webView(){
    	webView.loadUrl("http://192.168.1.99:808");
    	
    	webView.getSettings().setJavaScriptEnabled(false);
    	//Ĭ�ϵ����ҳ�����ӻᵯ����������� ���¿��Ա�֤��ҳ�е�����Ҳ��webview�� 

		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				System.out.println("------shouldOverrideUrlLoading------");
				view.loadUrl(url);
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
    }
    
	//Handler+Message
	class MyHandler extends Handler{
		
	}
}