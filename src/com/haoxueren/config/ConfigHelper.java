package com.haoxueren.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

import org.junit.Test;

import com.haoxueren.file.FileHelper;

/**
 * 程序的配置文件管理类；<br>
 * 支持从本地配置文件中读取数据；<br>
 */
public class ConfigHelper
{
	/**
	 * 获取单词图片所在的目录；<br>
	 * 先从本地读取，如果本地没有，则使用默认值；<br>
	 * 从本地属性文件中读取配置数据；
	 */
	public static String getConfig(String key, String defaultValue) throws Exception
	{
		// 读取本地的配置文件；
		// Properties extends Hashtable；
		Properties properties = new Properties();
		File file = new File(FileHelper.getCurrentDir(), "/config/config.properties");
		// 文件存在：读取文件中的数据；
		Reader reader = new FileReader(file);
		properties.load(reader);
		reader.close();
		String value = (String) properties.get(key);
		return value == null ? defaultValue : value;
	}

	/**
	 * 通过配置文件中的映射转换内容；
	 */
	public static String convert(String key) throws Exception
	{
		// 读取本地的配置文件；
		Properties properties = new Properties();
		File file = new File(FileHelper.getCurrentDir(), "/config/config.properties");
		// 读取文件中的数据；
		Reader reader = new FileReader(file);
		properties.load(reader);
		reader.close();
		String value = (String) properties.get(key);
		return value == null ? key : value;
	}

}
