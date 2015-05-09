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
 * 提交参数
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
     * URL上传数据
     * 1.?uname=zhang URL方式的GET方法
     * 2.POST:以输出流的方式，参数以键值对方式传输（服务器端使用request.getParameter()即可）
     * 如果要上传文件也可，需要使用request.getInputStream即可。但是不能和request.getParameter同时使用
     * 
     */
    private void param(String uname){
    	try {
	
    		System.out.println("=========");
    		URL url = new URL("http://192.168.1.99:8081/myweb/login");
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setRequestMethod("POST");
    		
    		conn.setDoOutput(true);//允许输出参数
    		conn.setDoInput(true);

    		OutputStream out = conn.getOutputStream();
    		String str = "uname="+uname+"&upass=234";
    		out.write(str.getBytes());
    		out.flush();
    		out.close();
    		
    		int code = conn.getResponseCode();//响应状态码
    		System.out.println(code);
    		if(code==200){//成功
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
     * 上传简单参数
     * 中文乱码：3种方式
     * 1.URLEncoder.encode("水电费", "utf-8");然后服务器端
     * 2.entity.setContentEncoding("utf-8");
     * 3.new StringEntity("",utf-8)
     */
    private void dopost(){
    	String url = "http://192.168.0.107:8080/myweb/index";
    	DefaultHttpClient client = new DefaultHttpClient();
    	HttpPost httpPost = new HttpPost(url);
    	   		 	
    	try {
//    		String str = URLEncoder.encode("水电费", "utf-8");
    		String str = "是对方的身份";
    		
    		// 构造最简单的字符串数据
    		StringEntity entity = new StringEntity("uname="+str+"&upass=123",HTTP.UTF_8);
    		//设置内容类型
    		entity.setContentType("application/x-www-form-urlencoded");
    		
    		//设置内容编码格式
    		entity.setContentEncoding("utf-8");
    		//设置请求数据
    		httpPost.setEntity(entity);
    		//发送请求
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