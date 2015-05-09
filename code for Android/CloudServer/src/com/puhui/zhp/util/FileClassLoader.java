package com.puhui.zhp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * 自定义类的加载器，从磁盘指定类文件加载到虚拟机
 * 
 * 1.加载类文件.class到byte[]
 * 2.使用ClassLoader的defineClass转换为类的实例对象
 * @author zhang
 *
 */
public class FileClassLoader extends ClassLoader{
    private String rootDir;   //存放类文件的目录
    
    public FileClassLoader(String rootDir) {   
        this.rootDir = rootDir;   
    }
    /**
     * 1.根据类名 找到类所在的目录 并加载此类文件 到内存字节数组
     * 2.再使用类加载器 转换为Class对象
     */
    public Class<?> findClass(String classname) throws ClassNotFoundException {   
        byte[] classData = getClassData(classname);   //1.根据类名 找到类所在的目录 并加载此类文件 到内存字节数组
        if (classData == null) {   
            throw new ClassNotFoundException();   
        }   
        else {   
        	//将一个 byte 数组转换为 Class 类的实例
            return defineClass(classname, classData, 0, classData.length);   
        }   
    }   
    //根据类名，从根目录中加载类的字节码 com.sxt.zhp.Test.class---com/sxt/zhp/Test.class
    private byte[] getClassData(String className) {   
        String path = classNameToPath(className); 
        InputStream ins = null;
        try {   
            ins = new FileInputStream(path);     
            int bufferSize = ins.available();  
            byte[] buffer = new byte[bufferSize];   
            ins.read(buffer);
            
            return buffer;   
        } catch (IOException e) {   
            e.printStackTrace();   
        }finally{
        	try {
        		if(ins!=null){
        			ins.close();
        		}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return null;   
    }   
    /**
     * 完整类名转为目录
     * 如：com.puhui.Test转为com\puhui\Test.class   d:/cloud/com/puhui/Test.class
     * @param className
     * @return
     */
    private String classNameToPath(String className) {   
    	String cls = rootDir + File.separatorChar + className.replace('.', File.separatorChar) + ".class"; 
        return cls;   
    }
}
