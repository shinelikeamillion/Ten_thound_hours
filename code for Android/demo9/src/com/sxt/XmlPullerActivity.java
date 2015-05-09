package com.sxt;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.content.res.Resources;

public class XmlPullerActivity extends Activity{
	/**
	 * XmlPullParser是Android平台标准的XML解析器，
	 * 这项技术来自一个开源的XML解析API项目XMLPULL
	 * 
	 * parser.getAttributeValue("http://www.sxt.com", "id");//命名空间的id属性,没有命名空间可以赋null
	 * parser.getAttributeIntValue(index, defaultValue);//
	 * 
	 * 解析输入流
	 *  XmlPullParserFactory parser = XmlPullParserFactory.newInstance();
//		XmlPullParser aa = parser.newPullParser();
//		aa.setInput(InputStream, encoding)
 * 
 * 		另一种方法：
 * 		XmlPullParser parser = Xml.newPullParser();
	 */

	public void readXml2() {

		Resources resources = this.getResources();
		// 通过资源对象的getXml()函数获取到XML解析器		
		XmlPullParser parser = resources.getXml(R.xml.my);
		StringBuilder msg = new StringBuilder();	
		
		try {
			int action = 0;			
			while((action=parser.next())!=XmlPullParser.END_DOCUMENT){	//结束文档						
				switch(action){
					case XmlPullParser.START_DOCUMENT://开始文档
						System.out.println("<?xml version=\"1.0\"?>");
						msg.append("<?xml version=\"1.0\"?>"+"\n");
					break;
					case XmlPullParser.START_TAG :  //开始标记
						String attri = "";
						//解析属性 属性数量
						for (int i = 0; i < parser.getAttributeCount(); i++) {
							attri+=" "+parser.getAttributeName(i);//属性的名称
							attri+="=\""+parser.getAttributeValue(i)+"\"";//属性的值
						}
						System.out.println("<"+parser.getName()+attri+">");
						msg.append("<"+parser.getName()+attri+">"+"\n");
						break;
					case XmlPullParser.TEXT :		//文本
						System.out.println(parser.getText());
						msg.append(parser.getText()+"\n");
						break;	
					case XmlPullParser.END_TAG :	//结束标记
						System.out.println("</"+parser.getName()+">");
						msg.append("</"+parser.getName()+">"+"\n");
						break;					
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} 

//		System.out.println(msg);
//		xmlText.setText(msg);
	}
}
