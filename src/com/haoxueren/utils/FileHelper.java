package com.haoxueren.utils;

import java.io.File;
import java.io.IOException;

/**
 * File�����ࣻ
 */
public class FileHelper
{
	/** ��ȡ��ǰ�������ڵ�Ŀ¼�� */
	public static String getCurrentDir()
	{
		return System.getProperty("user.dir");
	}

	/**
	 * �����ļ���
	 */
	public static boolean createFile(File file)
	{
		try
		{
			if (!file.exists())
			{
				return file.createNewFile();
			}
			return false;
		} catch (IOException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}

	/** ����Ŀ¼�� */
	public static boolean mkdirs(File directory)
	{
		if (!directory.exists())
		{
			return directory.mkdirs();
		}
		return false;
	}

}
