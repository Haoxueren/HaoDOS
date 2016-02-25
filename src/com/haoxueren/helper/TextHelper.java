package com.haoxueren.helper;

public class TextHelper
{
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
}
