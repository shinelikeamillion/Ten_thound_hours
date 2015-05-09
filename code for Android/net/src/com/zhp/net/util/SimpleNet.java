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
 * ������ҳ���ݵļ�ʾ��
 * ���ֲ�ͬ������������
 * @author zhangpeng
 *
 */

public class SimpleNet {
	/**
	 * ֻ��ȡ����
	 * @param parmas
	 * @return
	 */
	public static String dopost(String url ) {

		DefaultHttpClient client = new DefaultHttpClient();// http�ͻ���
		HttpPost httpPost = new HttpPost(url);
//		HttpGet httpget = new HttpGet();

		try {
			/*
			 * ����ʵ�ʵ�HTTP POST����
			 */
			HttpResponse response = client.execute(httpPost);
			
			HttpEntity entity = response.getEntity();//��÷�װ��HTTP��Ϣ��ʵ�����
			
			InputStream content = entity.getContent();//
			
			System.out.println(content.available()+"====length==="+entity.getContentLength());
			
			return convertStreamToString(content);
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	/**
	 * ʹ��HttpURLConnection��������ҳ����
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
			
			//���http���Ӷ���
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(1000*5);// ���ָ��ʱ����û��Ӧ ���׳��쳣
			

			// conn.setRequestMethod("GET");
			int code = conn.getResponseCode() ;
			// �����Ӧ�ɹ�
			if (code == 200) {
				in = conn.getInputStream();// ��ȡweb��������Ӧ����

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertStreamToString(in);
	}
	/**
	 * �����н�����ȡ���ַ���
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
	 * ��ȡ������Դ
	 * ���浽sdcard  ����ע��Ҫ�򿪶�дsdcardȨ��
	 * @param url
	 */
	public static boolean  readLoad(String url,String saveFileName){
		try{
			//�ȸ���url���������
			URL myURL = new URL(url);
			URLConnection conn = myURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			long fileSize = conn.getContentLength();// ������Ӧ��ȡ�ļ���С
			System.out.println("=============�ļ���С========"+fileSize);
			
			//�����ļ�д��sdcard�ļ�
			File file = new File(Environment.getExternalStorageDirectory(),saveFileName);
			FileOutputStream fos = new FileOutputStream(file);
			byte b[] = new byte[1024];
			while(is.read(b)!=-1){
				fos.write(b);//��ȡ����д���ļ���
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
	 * �������󲢷�����Ӧ
	 * @param parmas
	 * @return
	 */
	public static String dopost(String url ,Map<String, String> parmas) {
		// ��װ����
//		Map<String, String> parmas = new HashMap<String, String>();
//		parmas.put("name", val);

		DefaultHttpClient client = new DefaultHttpClient();// http�ͻ���
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
			 * ��POST���ݷ���HTTP����
			 */
			httpPost.setEntity(p_entity);
			/*
			 * ����ʵ�ʵ�HTTP POST����
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
