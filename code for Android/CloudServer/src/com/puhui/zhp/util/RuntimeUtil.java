package com.puhui.zhp.util;

import java.io.File;
import java.io.IOException;
/**
 * 编译java源文件为class文件
 */
public class RuntimeUtil {
	/**
	 * javac -d
	 * 设置输出类文件的位置。如果某个类是一个包的组成部分，则 javac 将把该类文件放入反映包名的子目录中，必要时创建目录
	 * @param filePath：需要编译的完整文件路径
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
