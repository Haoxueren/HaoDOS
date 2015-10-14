package com.haoxueren.utils;

import java.util.List;

import org.junit.Test;

/**
 * @author Haoxueren 返回指定汉字字符串的首字母缩写形式；<br>
 *         示例：好学人 --> HXR<br>
 */
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
