package com.zhp.net;



import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
/**
 * �ύ����
 *
 */
public class NetuploadActivity extends Activity {
	private Button btn;
	private EditText unameEdit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up);
        
        unameEdit = (EditText) findViewById(R.id.editText1);
        btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				new MyNetThread(unameEdit.getText().toString()).start();
			}
		});
    }

    /**
     * URL�ϴ�����
     * 1.?uname=zhang URL��ʽ��GET����
     * 2.POST:��������ķ�ʽ�������Լ�ֵ�Է�ʽ���䣨��������ʹ��request.getParameter()���ɣ�
     * ���Ҫ�ϴ��ļ�Ҳ�ɣ���Ҫʹ��request.getInputStream���ɡ����ǲ��ܺ�request.getParameterͬʱʹ��
     * 
     */
    private void param(String uname){
    	try {
	
    		System.out.println("=========");
    		URL url = new URL("http://192.168.1.99:8081/myweb/login");
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setRequestMethod("POST");
    		
    		conn.setDoOutput(true);//�����������
    		conn.setDoInput(true);

    		OutputStream out = conn.getOutputStream();
    		String str = "uname="+uname+"&upass=234";
    		out.write(str.getBytes());
    		out.flush();
    		out.close();
    		
    		int code = conn.getResponseCode();//��Ӧ״̬��
    		System.out.println(code);
    		if(code==200){//�ɹ�
    			InputStream ins = conn.getInputStream();
    			byte[] b = new byte[1024];
    			ins.read(b);
    			
    			String resMess = new String(b);
    			
    			System.out.println(resMess.trim());
    		}
    		
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * �ϴ��򵥲���
     * �������룺3�ַ�ʽ
     * 1.URLEncoder.encode("ˮ���", "utf-8");Ȼ���������
     * 2.entity.setContentEncoding("utf-8");
     * 3.new StringEntity("",utf-8)
     */
    private void dopost(){
    	String url = "http://192.168.0.107:8080/myweb/index";
    	DefaultHttpClient client = new DefaultHttpClient();
    	HttpPost httpPost = new HttpPost(url);
    	   		 	
    	try {
//    		String str = URLEncoder.encode("ˮ���", "utf-8");
    		String str = "�ǶԷ������";
    		
    		// ������򵥵��ַ�������
    		StringEntity entity = new StringEntity("uname="+str+"&upass=123",HTTP.UTF_8);
    		//������������
    		entity.setContentType("application/x-www-form-urlencoded");
    		
    		//�������ݱ����ʽ
    		entity.setContentEncoding("utf-8");
    		//������������
    		httpPost.setEntity(entity);
    		//��������
			HttpResponse response = client.execute(httpPost);
			System.out.println(response.getStatusLine()+"============"+client);
		} catch (Exception e) {
			e.printStackTrace();
		}    	
    }
    class MyNetThread extends Thread{
    	String uname;
    	public MyNetThread(String uname){
    		this.uname = uname;
    	}
    	@Override
    	public void run() {
    		param(uname);
    	}
    }
}