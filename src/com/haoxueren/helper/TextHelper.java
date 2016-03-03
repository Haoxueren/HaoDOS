package com.haoxueren.helper;

public class TextHelper
{
	/** 判断文本内容是否为空； */
	public static boolean isEmpty(String text)
	{
		if (text == null)
		{
			return true;
		}
		if (text.trim().length() == 0)
		{
			return true;
		}
		return false;
	}

	/** 判断文本内容是否不为空； */
	public static boolean notEmpty(String text)
	{
		return !isEmpty(text);
	}
}
