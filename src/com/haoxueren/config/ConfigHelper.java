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
 * ����������ļ������ࣻ<br>
 * ֧�ִӱ��������ļ��ж�ȡ���ݣ�<br>
 */
public class ConfigHelper
{
	/**
	 * ��ȡ����ͼƬ���ڵ�Ŀ¼��<br>
	 * �ȴӱ��ض�ȡ���������û�У���ʹ��Ĭ��ֵ��<br>
	 * �ӱ��������ļ��ж�ȡ�������ݣ�
	 */
	public static String getConfig(String key, String defaultValue)
	{
		try
		{
			// ��ȡ���ص������ļ���
			File file = new File(FileHelper.getCurrentDir(), "configure.properties");
			// Properties extends Hashtable��
			Properties properties = new Properties();
			if (file.exists())
			{
				// �ļ����ڣ���ȡ�ļ��е����ݣ�
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
				// �ļ������ڣ������ļ���д��Ĭ�����ݣ�
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
