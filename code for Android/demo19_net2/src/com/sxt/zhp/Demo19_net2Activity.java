package com.sxt.zhp;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Demo19_net2Activity extends Activity {
	private WebView  webView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        webView = (WebView) findViewById(R.id.webView1);
        new Thread(){
        	public void run() {
        		init();
        	};
        }.start();
        
    }
    private void init(){
    	webView.loadUrl("http://192.168.1.99:808/login.jsp");
    	
    	webView.getSettings().setJavaScriptEnabled(false);
    	//Ĭ�ϵ����ҳ�����ӻᵯ����������� ���¿��Ա�֤��ҳ�е�����Ҳ��webview�� 

//		webView.setWebViewClient(new WebViewClient(){
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				System.out.println("------shouldOverrideUrlLoading------");
//				view.loadUrl(url);
//				return super.shouldOverrideUrlLoading(view, url);
//			}
//		});
    }
}