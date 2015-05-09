package com.puhui.zhp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * �Զ�����ļ��������Ӵ���ָ�����ļ����ص������
 * 
 * 1.�������ļ�.class��byte[]
 * 2.ʹ��ClassLoader��defineClassת��Ϊ���ʵ������
 * @author zhang
 *
 */
public class FileClassLoader extends ClassLoader{
    private String rootDir;   //������ļ���Ŀ¼
    
    public FileClassLoader(String rootDir) {   
        this.rootDir = rootDir;   
    }
    /**
     * 1.�������� �ҵ������ڵ�Ŀ¼ �����ش����ļ� ���ڴ��ֽ�����
     * 2.��ʹ��������� ת��ΪClass����
     */
    public Class<?> findClass(String classname) throws ClassNotFoundException {   
        byte[] classData = getClassData(classname);   //1.�������� �ҵ������ڵ�Ŀ¼ �����ش����ļ� ���ڴ��ֽ�����
        if (classData == null) {   
            throw new ClassNotFoundException();   
        }   
        else {   
        	//��һ�� byte ����ת��Ϊ Class ���ʵ��
            return defineClass(classname, classData, 0, classData.length);   
        }   
    }   
    //�����������Ӹ�Ŀ¼�м�������ֽ��� com.sxt.zhp.Test.class---com/sxt/zhp/Test.class
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
     * ��������תΪĿ¼
     * �磺com.puhui.TestתΪcom\puhui\Test.class   d:/cloud/com/puhui/Test.class
     * @param className
     * @return
     */
    private String classNameToPath(String className) {   
    	String cls = rootDir + File.separatorChar + className.replace('.', File.separatorChar) + ".class"; 
        return cls;   
    }
}
