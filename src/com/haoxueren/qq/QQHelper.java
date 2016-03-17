package com.haoxueren.qq;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * Java与QQ交互的帮助类；<br>
 * 支持QQ、QQLite不支持TM；<br>
 * QQ轻聊版：http://im.qq.com/lightqq/<br>
 * 技术支持：http://download.csdn.net/detail/fanston/8062811
 */
public class QQHelper
{
	/** 通过QQ昵称获取QQ号码； */
	public static String getQQNo(String nickname) throws Exception
	{
		Properties properties = new Properties();
		Reader reader = new FileReader(System.getProperty("user.dir") + "/config/qqmap.properties");
		properties.load(reader);
		Object QQNo = properties.get(nickname);
		reader.close();
		return QQNo.toString();
	}

	/** 打开QQ聊天界面； */
	public static void openQQ(String qq) throws IOException
	{
		String url = "tencent://message/?uin=" + qq;
		Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
	}
}
