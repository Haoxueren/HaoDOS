package com.haoxueren.word;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import com.haoxueren.config.ConfigHelper;
import com.haoxueren.helper.RandomHelper;

public class WordHelper
{
	private static String wrodsPath;
	/** �ѳ�ȡ���ʵĸ����� */
	private static int index, loop = 29;

	/** ��ʼ���洢�ļ���Ϣ�ļ��ϣ� */
	private static Map<Long, File> initFileMap()
	{
		wrodsPath = ConfigHelper.getWordsPath();
		Map<Long, File> fileInfoMap = new TreeMap<>();
		File directory = new File(wrodsPath);
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
	public static File getRandomWordPath()
	{
		Map<Long, File> map = initFileMap();
		Object[] objects = map.keySet().toArray();
		int sum = map.size();
		int start = sum - (sum * (++index) / loop);
		int random = RandomHelper.getRandomInt(start, sum);
		Object time = objects[random];
		File file = map.get(time);
		System.out.println("[" + random + "��(" + +start + "~" + sum + ")]");
		System.out.println(index + "��" + getWordName(file));
		return file;
	}

	/** �����ļ���ȡ�������� */
	public static String getWordName(File word)
	{
		String name = word.getName();
		return name.substring(0, name.length() - 4);
	}

}
