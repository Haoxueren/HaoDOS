package com.haoxueren.word;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import com.haoxueren.config.Values;
import com.haoxueren.config.ConfigHelper;
import com.haoxueren.config.Keys;
import com.haoxueren.helper.FileHelper;
import com.haoxueren.helper.RandomHelper;

public class WordHelper
{
	private static String wrodsPath;
	/** �ѳ�ȡ���ʵĸ����� */
	private static int index, loop = 29;

	static
	{
		wrodsPath = ConfigHelper.getConfig(Keys.WORDS_PATH, Values.WORDS_PATH);
	}

	/** ��ʼ���洢�ļ���Ϣ�ļ��ϣ� */
	private static Map<Long, File> initFileMap()
	{
		Map<Long, File> fileInfoMap = new TreeMap<>();
		File directory = new File(wrodsPath);
		if (!directory.exists())
		{
			directory.mkdirs();
		}
		File[] files = directory.listFiles();
		for (File file : files)
		{
			// ��ȡ�ļ�������޸�ʱ�䣻
			long lastModified = file.lastModified();
			// ���ļ���Ϣ������fileInfoMap�У�
			fileInfoMap.put(lastModified, file);
		}
		return fileInfoMap;
	}

	/**
	 * �㷨��Ϊ֪�ʼ�2016.02.25�� Ҫ��index<=loop<=sum��<br>
	 * ���ͽ�ԭ��(lastModifyTime)�����ȡ���ʣ�<br>
	 */
	public static File getRandomWordFile()
	{
		Map<Long, File> map = initFileMap();
		Object[] objects = map.keySet().toArray();
		int sum = map.size();
		if (sum == 0)
		{
			return null;
		}
		int start = sum - (sum * (++index) / loop);
		int random = RandomHelper.getRandomInt(start, sum);
		Object time = objects[random];
		File file = map.get(time);
		System.out.println("[" + start + "~" + sum + "]��" + index + "��" + getWordName(file));
		return file;
	}

	/** �����ļ���ȡ�������� */
	public static String getWordName(File word)
	{
		String name = word.getName();
		return name.substring(0, name.length() - 4);
	}

}
