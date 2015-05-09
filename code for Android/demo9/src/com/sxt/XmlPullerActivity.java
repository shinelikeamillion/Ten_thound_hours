package com.sxt;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.content.res.Resources;

public class XmlPullerActivity extends Activity{
	/**
	 * XmlPullParser��Androidƽ̨��׼��XML��������
	 * ���������һ����Դ��XML����API��ĿXMLPULL
	 * 
	 * parser.getAttributeValue("http://www.sxt.com", "id");//�����ռ��id����,û�������ռ���Ը�null
	 * parser.getAttributeIntValue(index, defaultValue);//
	 * 
	 * ����������
	 *  XmlPullParserFactory parser = XmlPullParserFactory.newInstance();
//		XmlPullParser aa = parser.newPullParser();
//		aa.setInput(InputStream, encoding)
 * 
 * 		��һ�ַ�����
 * 		XmlPullParser parser = Xml.newPullParser();
	 */

	public void readXml2() {

		Resources resources = this.getResources();
		// ͨ����Դ�����getXml()������ȡ��XML������		
		XmlPullParser parser = resources.getXml(R.xml.my);
		StringBuilder msg = new StringBuilder();	
		
		try {
			int action = 0;			
			while((action=parser.next())!=XmlPullParser.END_DOCUMENT){	//�����ĵ�						
				switch(action){
					case XmlPullParser.START_DOCUMENT://��ʼ�ĵ�
						System.out.println("<?xml version=\"1.0\"?>");
						msg.append("<?xml version=\"1.0\"?>"+"\n");
					break;
					case XmlPullParser.START_TAG :  //��ʼ���
						String attri = "";
						//�������� ��������
						for (int i = 0; i < parser.getAttributeCount(); i++) {
							attri+=" "+parser.getAttributeName(i);//���Ե�����
							attri+="=\""+parser.getAttributeValue(i)+"\"";//���Ե�ֵ
						}
						System.out.println("<"+parser.getName()+attri+">");
						msg.append("<"+parser.getName()+attri+">"+"\n");
						break;
					case XmlPullParser.TEXT :		//�ı�
						System.out.println(parser.getText());
						msg.append(parser.getText()+"\n");
						break;	
					case XmlPullParser.END_TAG :	//�������
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
