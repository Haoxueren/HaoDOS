package com.haoxueren.config;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

import org.junit.Test;

import com.haoxueren.helper.FileHelper;

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
	public static String getConfig(String key, String defaultValue)
	{
		try
		{
			// 读取本地的配置文件；
			File file = new File(FileHelper.getCurrentDir(), "configure.properties");
			// Properties extends Hashtable；
			Properties properties = new Properties();
			if (file.exists())
			{
				// 文件存在：读取文件中的数据；
				Reader reader = new FileReader(file);
				properties.load(reader);
				String value = (String) properties.get(key);
				if (value == null || value.trim().length() == 0)
				{
					value = defaultValue;
					properties.put(key, defaultValue);
					Writer writer = new PrintWriter(file);
					properties.store(writer, null);
					writer.close();
				}
				reader.close();
				System.out.println(key + " = " + value);
				return value;
			} else
			{
				// 文件不存在：创建文件并写入默认数据；
				file.createNewFile();
				properties.put(key, defaultValue);
				Writer writer = new PrintWriter(file);
				properties.store(writer, null);
				writer.close();
				return defaultValue;
			}
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Test
	public void method()
	{
		getConfig("words_path", Values.WORDS_PATH);
	}

}
