package com.zhp.net;

import java.util.HashMap;
import java.util.Map;

import com.zhp.net.util.NetUtil;
import com.zhp.net.util.SimpleNet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ��ʾ����ʾ
 * ������ĵ���
 * ������Դ�����ݷ���
 * ����ͼƬ��ʾ
 * @author zhangpeng
 *
 */
public class NetActivity extends Activity {
  	TextView myview = null;
  	ImageView img ;
  	ProgressBar progressBar;//����
  	private final String url = "http://192.168.1.5:8080/myweb/";
  	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 1:
				progressBar.setVisibility(View.GONE);//����������
				break;
			}
			super.handleMessage(msg);
		}
  		
  	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        img = (ImageView)findViewById(R.id.image_net);
        progressBar = (ProgressBar)findViewById(R.id.progressBar1);
        
        myview = (TextView)findViewById(R.id.mess);
        Button button = (Button)findViewById(R.id.button_net);
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				netPage();//����ҳ������
				
//				if(NetUtil.checkNet(NetActivity.this)){
//					Toast.makeText(NetActivity.this, "������ã�", Toast.LENGTH_LONG).show();
//				}else{
//					Toast.makeText(NetActivity.this, "û�з��ֿ������磡", Toast.LENGTH_LONG).show();
//				}
//				accessNet();//���������
				
//				downImg();//����ͼƬ����ʾ
				
				post();
			}
		});
    }

    /**
     * �ύ���ݵ�������
     */
    public void post(){
    	Map<String, String>  data = new HashMap<String, String>();
    	data.put("uname", "zhans��");
    	data.put("uid", "12");
    	String str =  SimpleNet.dopost(url, data);
    	
    	myview.setText(str);
    }
    /**
     * ��ȡҳ������
     */
    public void netPage(){
		//ע�����ﲻ��ʹ��127.0.0.1 Ҫʹ��IP��ַ
//		String str = SimpleNet.dopost("http://192.168.1.110:8080/myweb/index.jsp");
		String str = SimpleNet.getHttpString(url+"index.jsp");
		
		myview.setText(str);
    }
    /**
     * ����������Դ 
     * ����ͼƬ����ʾ
     */
    public void downImg(){
		Drawable imgdraw  = NetUtil.getImageFromUrl(url+"/a.jpg");
		
		if(imgdraw!=null){
			img.setImageDrawable(imgdraw);
			img.setVisibility(View.VISIBLE);
			Toast.makeText(NetActivity.this, "���سɹ���", Toast.LENGTH_LONG).show();
		}
    }
    
    /**
     * ���������
     */
    public void accessNet(){
    	Intent intent= new Intent();        
        intent.setAction("android.intent.action.VIEW");    
        Uri content_url = Uri.parse(url+"/myweb/a.jpg");   
        intent.setData(content_url);  
        startActivity(intent);
    }
}