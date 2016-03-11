package com.haoxueren.helper;

import java.io.InputStream;

/** 处理IO流数据的帮助类； */
public class StreamHelper
{
	/** 读取文本输入流内的内容； */
	public static String readInputStream(InputStream inputStream) throws Exception
	{
		StringBuffer buffer = new StringBuffer();
		byte[] bytes = new byte[1024];
		int len = inputStream.read(bytes);
		while (len != -1)
		{
			buffer.append(new String(bytes, 0, len));
			len = inputStream.read(bytes);
		}
		return buffer.toString();
	}
}
