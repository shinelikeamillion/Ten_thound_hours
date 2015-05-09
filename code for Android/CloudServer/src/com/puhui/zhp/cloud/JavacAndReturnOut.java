package com.puhui.zhp.cloud;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Method;

import com.puhui.zhp.util.FileClassLoader;
import com.puhui.zhp.util.RuntimeUtil;

public class JavacAndReturnOut {
	/**
	 * 获得指定方法中输出的日志信息
	 * 309342333
	 * 
	 */
	private static String getSystemOut(Object obj,Method method,Object[] param){
		PrintStream out1 = System.out; //先保存原有的输出消息和错误对象的引用，以便后面恢复控制台的输出
		PrintStream out2 = System.err;
		
		//把原来的System.out输出的日志直接对接输出到字节流中
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(baos));
		System.setErr(new PrintStream(baos));
		
		try {
			method.invoke(obj, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		byte[] b = baos.toByteArray();
		System.out.close();
		System.err.close();
		
		//重新恢复到原有的输出对象，否则后面也无法看到输出的日志信息
		System.setOut(out1);
		System.setErr(out2);
		return new String(b);
	}
	/**
	 * 
	 * @param filePath java源文件路径
	 * @param claName  完整带包名的类名
	 * @return
	 */
	public static String getSystemout(String filePath,String claName){
		try {
			//1.编译java文件
			RuntimeUtil.javac(filePath);
			Thread.sleep(3000);//给一定的编译时间
			
			//2.加载类到虚拟机（）		走到获取了类的实例对象
			FileClassLoader classLoader = new FileClassLoader(new File(filePath).getParent());		
			Class<?> cla = classLoader.findClass(claName);
			
			//3.获得类的main方法
			Method main = cla.getMethod("main", String[].class);
			
			//4.执行方法并获取输出的日志
			String outStr = getSystemOut(cla, main,new String[]{null});
			return outStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
