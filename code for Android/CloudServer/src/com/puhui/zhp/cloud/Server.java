package com.puhui.zhp.cloud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static String saveDir = "d:/cloud/";

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(9999);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		System.out.println("服务器启动....");
		while(true){
			try {
				Socket socket = serverSocket.accept();
				System.out.println("*************************1********");
				InputStream is = socket.getInputStream();//接收数据
				OutputStream out = socket.getOutputStream();//响应
				
				String content = convertStreamToString(is);
				String clsName = getClassName(content);
				System.out.println("=="+content);
				File saveFile = saveToJava(clsName, content);//根据类名保存为.java文件
				System.out.println("----------------2-------");
				//进行编译运行并取出结果
				String returnStr = JavacAndReturnOut.getSystemout(saveFile.getAbsolutePath(), clsName)+"\n";
				
				System.out.println(returnStr);
				
				out.write(returnStr.getBytes());
				out.flush();
				System.out.println("server: Over");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 保存为.java文件
	 */
	public static File saveToJava(String clsName,String content){
		
		File dir = new File(saveDir);
		if(dir.exists()==false){
			dir.mkdirs();
		}
		File file = new File(dir,clsName+".java");
		
		try {
			FileOutputStream out = new FileOutputStream(file);
			out.write(content.getBytes());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
	
	/**
	 * 提取类名
	 */
	public static String getClassName(String content){
		int start = content.indexOf(" class ");
		if(start==-1){
			throw new RuntimeException("没有发现类名！");
		}
		String clsName = content.substring(start+7,content.indexOf("{"));
		return clsName.trim();
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
