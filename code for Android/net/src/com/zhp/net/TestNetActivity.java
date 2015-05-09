package com.zhp.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * @author zhangchangpeng
 * 本例演示 
 * 网页源码访问，下载，提交数据，网络图片，浏览器的访问
 */
public class TestNetActivity extends Activity {
	private final String urlStr = "http://192.168.1.5:8080/myweb/";
	private TextView text;
	private ImageView img;
	private WebView webView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button button = (Button)findViewById(R.id.button_net);
        button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				accessBrowser();
//				page();
				down();
//				showImg();
//				web();
//				uploadData();
			}
        	
        });
        //
        text = (TextView) findViewById(R.id.mess);
        //显示图片
        img = (ImageView) findViewById(R.id.image_net);
        
        webView = (WebView) findViewById(R.id.webView1);
       
    }

    /**
     * 上传数据
     */
    private void uploadData(){
    	try {
    		DefaultHttpClient client = new DefaultHttpClient();// http客户端
    		HttpPost httpPost = new HttpPost(urlStr);
    		   		
    		List<NameValuePair> list = new ArrayList<NameValuePair>();
    		NameValuePair nvp = new BasicNameValuePair("uname", "张三");
    		NameValuePair nvp2 = new BasicNameValuePair("age", "12");
    		
    		list.add(nvp);
    		list.add(nvp2);
    		
    		HttpEntity entity = new UrlEncodedFormEntity(list,"utf-8");
    		httpPost.setEntity(entity);
    		
    		HttpResponse response = client.execute(httpPost);
    		HttpEntity resEntity = response.getEntity();
    		InputStream ins = resEntity.getContent();

			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * 使用webview
     */
    private void web(){
    	webView.loadUrl(urlStr);
//    	webView.getSettings().setJavaScriptEnabled(true);
    	webView.setVisibility(View.VISIBLE);
    	
    	//默认点击网页中链接会弹出浏览器访问 以下可以保证网页中的链接也在webview中 
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				System.out.println("------shouldOverrideUrlLoading------");
				view.loadUrl(url);
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
    	
    }
    /**
     * 读取网络图片并显示
     */
    private int index;
    private String[] imgs= {"1.jpg","2.jpg","3.jpg"};
  private void showImg(){
	try {
		URL url = new URL(urlStr+imgs[index]);
		InputStream ins = url.openStream();
		//第一种方式
//		Bitmap bitMap = BitmapFactory.decodeStream(ins);			
//		img.setImageBitmap(bitMap);
		//第二种方式
		Drawable drawable = new BitmapDrawable(ins);
		img.setImageDrawable(drawable);
		
		img.setVisibility(View.VISIBLE);
		Toast.makeText(this, "读取完成！", Toast.LENGTH_LONG).show();
		ins.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	index++;
	if(index>imgs.length-1){
		index = 0;
	}
}
    /**
     * 文件下载到SDcard
     */
    private void down(){
    	try {
			URL url = new URL(urlStr+"apache-cxf-2.6.1.zip");//文件过大，会异常
//    		URL url = new URL(urlStr+"1.jpg");
			URLConnection conn = url.openConnection();
			
			conn.setReadTimeout(5000);//超时时间
			//获得文件大小
			int fileSize = conn.getContentLength();
			
			InputStream ins = conn.getInputStream();
			
			//SDcard目录
			File sdCard = Environment.getExternalStorageDirectory();
			System.out.println(sdCard.getAbsolutePath());
			//根据应用的安装包名创建目录
			File savePath = new File(sdCard.getAbsolutePath()+"/"+getPackageName());
			if(!savePath.exists()){
				savePath.mkdirs();
			}
			//保存的文件
			File saveFile = new File(savePath,"test2.jpg");
			FileOutputStream out = new FileOutputStream(saveFile);

			byte b[] = new byte[1024];
			while (ins.read(b)!=-1) {
				out.write(b);	
				b = new byte[1024];
			}
			
			out.close();
			ins.close();
			
			Toast.makeText(this, "下载完成，大小"+fileSize, Toast.LENGTH_LONG).show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * 访问页面内容
     */
    private void page(){
    	try {
			URL url = new URL(urlStr+"?uname=张");			
			InputStream ins = url.openStream();
			
			byte[] b = new byte[1024];
			StringBuilder sb = new StringBuilder();
			while(ins.read(b)!=-1){
				sb.append(new String(b));
			}
			ins.close();
			text.setText(sb);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 访问浏览器
     */
    private void accessBrowser(){
    	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlStr));
		startActivity(intent);
    }
    

}