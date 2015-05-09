package com.zhp.net.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import android.os.Environment;
/**
 * 访问网页内容的简单示例
 * 两种不同方法访问网络
 * @author zhangpeng
 *
 */

public class SimpleNet {
	/**
	 * 只读取数据
	 * @param parmas
	 * @return
	 */
	public static String dopost(String url ) {

		DefaultHttpClient client = new DefaultHttpClient();// http客户端
		HttpPost httpPost = new HttpPost(url);
//		HttpGet httpget = new HttpGet();

		try {
			/*
			 * 发出实际的HTTP POST请求
			 */
			HttpResponse response = client.execute(httpPost);
			
			HttpEntity entity = response.getEntity();//获得封装了HTTP信息的实体对象
			
			InputStream content = entity.getContent();//
			
			System.out.println(content.available()+"====length==="+entity.getContentLength());
			
			return convertStreamToString(content);
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	/**
	 * 使用HttpURLConnection对象获得网页数据
	 * @param httpUrl
	 * @return
	 */
	public static String getHttpString(String httpUrl) {
		if (httpUrl == null || httpUrl.equals("")) {
			return null;
		}
//		System.out.println(">>>URL:"+httpUrl); 

		URL url;
		InputStream in = null;
		
		try {
			url = new URL(httpUrl);
			
			//获得http连接对象
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(1000*5);// 如果指定时间内没响应 则抛出异常
			

			// conn.setRequestMethod("GET");
			int code = conn.getResponseCode() ;
			// 如果响应成功
			if (code == 200) {
				in = conn.getInputStream();// 获取web服务器响应内容

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertStreamToString(in);
	}
	/**
	 * 从流中解析提取出字符串
	 * @param is
	 * @return
	 */
	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	/**
	 * 读取网络资源
	 * 保存到sdcard  所以注意要打开读写sdcard权限
	 * @param url
	 */
	public static boolean  readLoad(String url,String saveFileName){
		try{
			//先根据url获得输入流
			URL myURL = new URL(url);
			URLConnection conn = myURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			long fileSize = conn.getContentLength();// 根据响应获取文件大小
			System.out.println("=============文件大小========"+fileSize);
			
			//把流文件写入sdcard文件
			File file = new File(Environment.getExternalStorageDirectory(),saveFileName);
			FileOutputStream fos = new FileOutputStream(file);
			byte b[] = new byte[1024];
			while(is.read(b)!=-1){
				fos.write(b);//读取流并写入文件流
				b = new byte[1024];
			}
			
			fos.close();
			is.close();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 发送请求并返回响应
	 * @param parmas
	 * @return
	 */
	public static String dopost(String url ,Map<String, String> parmas) {
		// 封装数据
//		Map<String, String> parmas = new HashMap<String, String>();
//		parmas.put("name", val);

		DefaultHttpClient client = new DefaultHttpClient();// http客户端
		HttpPost httpPost = new HttpPost(url);

		ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		if (parmas != null) {
			Set<String> keys = parmas.keySet();
			for (Iterator<String> i = keys.iterator(); i.hasNext();) {
				String key = (String) i.next();
				pairs.add(new BasicNameValuePair(key, parmas.get(key)));
			}
		}

		try {
			UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(pairs,"utf-8");
			/*
			 * 将POST数据放入HTTP请求
			 */
			httpPost.setEntity(p_entity);
			/*
			 * 发出实际的HTTP POST请求
			 */
			HttpResponse response = client.execute(httpPost);
			
			HttpEntity entity = response.getEntity();
			
			InputStream content = entity.getContent();
			return convertStreamToString(content);
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
