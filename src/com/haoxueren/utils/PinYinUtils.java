package com.haoxueren.utils;

import java.util.List;

public class PinYinUtils
{
	public static String getFirstLatter(String hanzis)
	{
		Pinyin pinyin = new Pinyin(hanzis);
		List<String[]> result = pinyin.getResult();
		StringBuilder builder = new StringBuilder();
		for (String[] pinyins : result)
		{
			char firstChar = pinyins[0].charAt(0);
			builder.append(firstChar);
		}
		return builder.toString().toUpperCase();
	}

}
