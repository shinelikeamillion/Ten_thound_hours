package com.puhui.zhp.cloud;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Method;

import com.puhui.zhp.util.FileClassLoader;
import com.puhui.zhp.util.RuntimeUtil;

public class JavacAndReturnOut {
	/**
	 * ���ָ���������������־��Ϣ
	 * 309342333
	 * 
	 */
	private static String getSystemOut(Object obj,Method method,Object[] param){
		PrintStream out1 = System.out; //�ȱ���ԭ�е������Ϣ�ʹ����������ã��Ա����ָ�����̨�����
		PrintStream out2 = System.err;
		
		//��ԭ����System.out�������־ֱ�ӶԽ�������ֽ�����
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
		
		//���»ָ���ԭ�е�������󣬷������Ҳ�޷������������־��Ϣ
		System.setOut(out1);
		System.setErr(out2);
		return new String(b);
	}
	/**
	 * 
	 * @param filePath javaԴ�ļ�·��
	 * @param claName  ����������������
	 * @return
	 */
	public static String getSystemout(String filePath,String claName){
		try {
			//1.����java�ļ�
			RuntimeUtil.javac(filePath);
			Thread.sleep(3000);//��һ���ı���ʱ��
			
			//2.�����ൽ���������		�ߵ���ȡ�����ʵ������
			FileClassLoader classLoader = new FileClassLoader(new File(filePath).getParent());		
			Class<?> cla = classLoader.findClass(claName);
			
			//3.������main����
			Method main = cla.getMethod("main", String[].class);
			
			//4.ִ�з�������ȡ�������־
			String outStr = getSystemOut(cla, main,new String[]{null});
			return outStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
