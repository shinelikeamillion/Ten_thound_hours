package com.sxt.client.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import android.os.Handler;

public class ClientThread extends Thread{
	private String sendText;
	private Handler handler;
	public ClientThread(String sendText,Handler handler){
		this.handler = handler;
		this.sendText = sendText+"\n";
	}
	@Override
	public void run() {
		try {
			System.out.println("--------"+SysConfig.defaultip+"------"+SysConfig.serverPort+"-----");
			Socket socket = new Socket(InetAddress.getByName(SysConfig.defaultip), SysConfig.serverPort);
			OutputStream out = socket.getOutputStream();
			out.write(sendText.getBytes());
			
			out.flush();
			socket.shutdownOutput();  
			System.out.println("----发送完成！--");
			
			InputStream ins = socket.getInputStream();//接收服务器端的响应结果
			String responseText = convertStreamToString(ins);
			
			System.out.println("--------"+responseText+"-----");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
}
