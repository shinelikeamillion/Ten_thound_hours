package com.puhui.zhp.util;

import java.io.File;
import java.io.IOException;
/**
 * ����javaԴ�ļ�Ϊclass�ļ�
 */
public class RuntimeUtil {
	/**
	 * javac -d
	 * ����������ļ���λ�á����ĳ������һ��������ɲ��֣��� javac ���Ѹ����ļ����뷴ӳ��������Ŀ¼�У���Ҫʱ����Ŀ¼
	 * @param filePath����Ҫ����������ļ�·��
	 */
	public static void javac(String filePath){
		
		try {
			Runtime.getRuntime().exec(
					"javac -d " + new File(filePath).getParent() + " " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
