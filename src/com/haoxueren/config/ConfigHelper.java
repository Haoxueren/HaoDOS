package com.haoxueren.config;

import com.haoxueren.helper.TextHelper;

/**
 * 程序的配置文件管理类；<br>
 * 支持从本地配置文件中读取数据；<br>
 */
public class ConfigHelper
{
	/**
	 * 获取单词图片所在的目录；<br>
	 * 先从本地读取，如果本地没有，则使用默认值；<br>
	 */
	public static String getWordsPath()
	{
		String wordsPath = getLocalWordPath();
		if (TextHelper.isEmpty(wordsPath))
		{
			// HAO FileHelper.getCurrentDir();
			return "C:\\Android\\Developer\\HoasirDos\\英语单词图解";
		}
		return wordsPath;
	}

	/** HAO 读取本地的数据； */
	private static String getLocalWordPath()
	{
		return null;
	}
}
