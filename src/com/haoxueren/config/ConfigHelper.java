package com.haoxueren.config;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

import org.junit.Test;

import com.haoxueren.utils.FileHelper;

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
			// Properties extends Hashtable；
			Properties properties = new Properties();
			File file = new File(FileHelper.getCurrentDir(), "/config/configure.properties");
			// 如果目录不存在，就创建目录；
			File directory = file.getParentFile();
			if (!directory.exists())
			{
				directory.mkdirs();
			}
			// 文件不存在：创建文件并写入默认数据；
			if (!file.exists())
			{
				file.createNewFile();
				properties.put(key, defaultValue);
				Writer writer = new PrintWriter(file);
				properties.store(writer, null);
				writer.close();
				return defaultValue;
			} else
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
				return value;
			}
		} catch (Exception e)
		{
			System.err.println("异常信息：" + e.getMessage());
			return null;
		}
	}

	@Test
	public void method()
	{
		getConfig("words_path", Values.WORDS_PATH);
	}

}
