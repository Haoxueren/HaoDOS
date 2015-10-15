package com.haoxueren.helper;

/** 处理字符串的帮助类 */
public class TextHelper
{
	public boolean isEmpty(String text)
	{
		return text == null || text.length() == 0;
	}

	/** 把字符串中的字符用星号替换； */
	public String replaceCharByStar(String text, int startIndex, int endIndex)
	{
		StringBuilder hiddenText = new StringBuilder();
		for (int i = 0; i < endIndex - startIndex; i++)
		{
			hiddenText.append('*');
		}
		return text.substring(0, startIndex) + hiddenText + text.substring(endIndex, text.length());
	}

	/** 替换字符串中指定的字符； */
	public String replaceChars(String text, int startIndex, int endIndex, char mark)
	{
		StringBuilder hiddenText = new StringBuilder();
		for (int i = 0; i < endIndex - startIndex; i++)
		{
			hiddenText.append(mark);
		}
		return text.substring(0, startIndex) + hiddenText + text.substring(endIndex, text.length());
	}

}
