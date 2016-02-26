package com.haoxueren.helper;

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
	public static boolean MakeDirectory(File directory)
	{
		return directory.mkdirs();
	}

}
