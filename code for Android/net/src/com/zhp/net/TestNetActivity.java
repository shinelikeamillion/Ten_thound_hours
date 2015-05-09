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
 * ������ʾ 
 * ��ҳԴ����ʣ����أ��ύ���ݣ�����ͼƬ��������ķ���
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
        //��ʾͼƬ
        img = (ImageView) findViewById(R.id.image_net);
        
        webView = (WebView) findViewById(R.id.webView1);
       
    }

    /**
     * �ϴ�����
     */
    private void uploadData(){
    	try {
    		DefaultHttpClient client = new DefaultHttpClient();// http�ͻ���
    		HttpPost httpPost = new HttpPost(urlStr);
    		   		
    		List<NameValuePair> list = new ArrayList<NameValuePair>();
    		NameValuePair nvp = new BasicNameValuePair("uname", "����");
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
     * ʹ��webview
     */
    private void web(){
    	webView.loadUrl(urlStr);
//    	webView.getSettings().setJavaScriptEnabled(true);
    	webView.setVisibility(View.VISIBLE);
    	
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
    /**
     * ��ȡ����ͼƬ����ʾ
     */
    private int index;
    private String[] imgs= {"1.jpg","2.jpg","3.jpg"};
  private void showImg(){
	try {
		URL url = new URL(urlStr+imgs[index]);
		InputStream ins = url.openStream();
		//��һ�ַ�ʽ
//		Bitmap bitMap = BitmapFactory.decodeStream(ins);			
//		img.setImageBitmap(bitMap);
		//�ڶ��ַ�ʽ
		Drawable drawable = new BitmapDrawable(ins);
		img.setImageDrawable(drawable);
		
		img.setVisibility(View.VISIBLE);
		Toast.makeText(this, "��ȡ��ɣ�", Toast.LENGTH_LONG).show();
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
     * �ļ����ص�SDcard
     */
    private void down(){
    	try {
			URL url = new URL(urlStr+"apache-cxf-2.6.1.zip");//�ļ����󣬻��쳣
//    		URL url = new URL(urlStr+"1.jpg");
			URLConnection conn = url.openConnection();
			
			conn.setReadTimeout(5000);//��ʱʱ��
			//����ļ���С
			int fileSize = conn.getContentLength();
			
			InputStream ins = conn.getInputStream();
			
			//SDcardĿ¼
			File sdCard = Environment.getExternalStorageDirectory();
			System.out.println(sdCard.getAbsolutePath());
			//����Ӧ�õİ�װ��������Ŀ¼
			File savePath = new File(sdCard.getAbsolutePath()+"/"+getPackageName());
			if(!savePath.exists()){
				savePath.mkdirs();
			}
			//������ļ�
			File saveFile = new File(savePath,"test2.jpg");
			FileOutputStream out = new FileOutputStream(saveFile);

			byte b[] = new byte[1024];
			while (ins.read(b)!=-1) {
				out.write(b);	
				b = new byte[1024];
			}
			
			out.close();
			ins.close();
			
			Toast.makeText(this, "������ɣ���С"+fileSize, Toast.LENGTH_LONG).show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * ����ҳ������
     */
    private void page(){
    	try {
			URL url = new URL(urlStr+"?uname=��");			
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
     * ���������
     */
    private void accessBrowser(){
    	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlStr));
		startActivity(intent);
    }
    

}