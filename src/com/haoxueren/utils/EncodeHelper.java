package com.haoxueren.utils;

public class EncodeHelper
{
	/** 将UTF-8编码转换为GBK编码； */
	public static String gbk(String utf) throws Exception
	{
		return new String(utf.getBytes("UTF-8"), "GBK");
	}

}
