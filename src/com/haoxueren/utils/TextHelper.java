package com.haoxueren.utils;

public class TextHelper
{
	/** �ж��ı������Ƿ�Ϊ�գ� */
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

	/** �ж��ı������Ƿ�Ϊ�գ� */
	public static boolean notEmpty(String text)
	{
		return !isEmpty(text);
	}
}
