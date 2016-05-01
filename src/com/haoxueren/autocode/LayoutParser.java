package com.haoxueren.autocode;

import java.io.FileInputStream;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


/** 布局解析器； */
public class LayoutParser
{
	private LayoutParserListener listener;

	public LayoutParser(LayoutParserListener listener)
	{
		this.listener = listener;
	}

	/** 从xml文件中解析出所有控件的全类名及控件id； */
	public void parseLayout(String xmlFile) throws Exception
	{
		// 创建xml文件的输入流；
		InputStream inputStream = new FileInputStream(xmlFile);
		// 创建XmlPullParser对象；
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser pullParser = factory.newPullParser();
		// 设置输入流并开始解析xml；
		pullParser.setInput(inputStream, "UTF-8");
		int eventType = pullParser.getEventType();
		// 解析到文档末尾自动退出；
		String namespace = pullParser.getNamespace();
		while (eventType != XmlPullParser.END_DOCUMENT)
		{
			// 解析到开始标签时，进行操作；
			if (eventType == XmlPullParser.START_TAG)
			{
				// 获取控件的id；
				String viewId = pullParser.getAttributeValue(namespace, "android:id");
				if (viewId != null && viewId.length() > 0)
				{
					// 获取并处理控件的类名；
					String fullName = pullParser.getName();
					/** 根据类名和id生成代码； */
					listener.onParseXml(fullName, viewId);
				}
			}
			// 开始解析下一个事件；
			eventType = pullParser.next();
		}
	}

}
