package com.sxt;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * dom4j jdom dom sax DOM 和 SAX
 */
public class XmlActivity extends Activity {
	private Button btn;
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.raw);

		tv = (TextView) findViewById(R.id.textView_raw);
		btn = (Button) findViewById(R.id.button_raw);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// readXml();
				// saxRead();
				xmlPuller();
			}
		});
	}
	private void xmlPuller() {
		StringBuilder sbf = new StringBuilder();
		XmlResourceParser parser = getResources().getXml(R.xml.my);
		try {
			int action = 0;
			while ((action = parser.next()) != XmlPullParser.END_DOCUMENT) {
				switch (action) {
				case XmlPullParser.START_TAG:
					String qname = parser.getName();///保存开始标签				
					String txt = "";
					if("name".equals(qname)){
						txt = parser.nextText();
						sbf.append("姓名："+txt);
					}else if("age".equals(qname)){
						txt = parser.nextText();
						sbf.append("年龄："+txt);
					}
					break;

				default:
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		tv.setText(sbf);
	}
	/**
	 * XMLPuller
	 */
	private void xmlPuller2() {
//		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//		XmlResourceParser parser = factory.newPullParser();
		
		// XmlPullParser parser = Xml.newPullParser();
		// parser.setInput(arg0, arg1);
		StringBuilder sbf = new StringBuilder();
		String qname = "";
		XmlResourceParser parser = getResources().getXml(R.xml.my);
		try {
			int action = 0;
			while ((action = parser.next()) != XmlPullParser.END_DOCUMENT) {
				switch (action) {
				case XmlPullParser.START_DOCUMENT:
					
					break;
				case XmlPullParser.START_TAG:
					qname = parser.getName();///保存开始标签
//					parser.get
//					System.out.println("<"+qname+">");
					break;
				case XmlPullParser.TEXT:
					String txt = parser.getText();
					if("name".equals(qname)){
						sbf.append("姓名："+txt);
					}else if("age".equals(qname)){
						sbf.append("年龄："+txt);
					}
//					System.out.println(txt+"===="+parser.getName());
					break;
				case XmlPullParser.END_TAG:
//					qname = parser.getName();
//					System.out.println("</"+tag+">");
					break;
				default:
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		tv.setText(sbf);
	}

	/**
	 * DOM解析
	 */
	private void readXml() {
		// 读取xml输入流

		InputStream ins = getResources().openRawResource(R.raw.my);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(ins);

			NodeList personsList = doc.getElementsByTagName("person");
			int len = personsList.getLength();
			StringBuilder sbf = new StringBuilder();
			for (int i = 0; i < len; i++) {
				Element person = (Element) personsList.item(i);
				String id = person.getAttribute("id");
				String name = person.getElementsByTagName("name").item(0)
						.getTextContent();
				String age = person.getElementsByTagName("age").item(0)
						.getTextContent();
				sbf.append(id + "\t");
				sbf.append(name + "\t");
				sbf.append(age + "\n");
			}

			tv.setText(sbf.toString());
			System.out.println(personsList.getLength());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 自带SAX解析方式
	 */
	private void saxRead() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		InputStream ins = getResources().openRawResource(R.raw.my);
		try {
			SAXParser parser = factory.newSAXParser();
			MyHandler my = new MyHandler();
			parser.parse(ins, my);

			tv.setText(my.sbf.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 自定义Handler
	 * 
	 */
	private class MyHandler extends DefaultHandler {
		private String qName;
		public StringBuilder sbf = new StringBuilder();

		/**
		 * 读取文本时
		 */
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			String content = new String(ch, start, length);
			if ("name".equals(qName)) {
				sbf.append("姓名：" + content);
				// System.out.println("姓名："+content);
			} else if ("age".equals(qName)) {
				// System.out.println("年龄："+content);
				sbf.append("年龄：" + content);
			}

		}

		/**
		 * 开始标记
		 */
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			// System.out.println("<"+qName+">");
			this.qName = qName;
		}

		/**
		 * 结束标记
		 */
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			// System.out.println("</"+qName+">");
			this.qName = null;
		}

		@Override
		public void endDocument() throws SAXException {

			super.endDocument();
		}

		@Override
		public void startDocument() throws SAXException {

		}
	}
}
