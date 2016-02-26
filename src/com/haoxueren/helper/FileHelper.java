package com.haoxueren.helper;

import java.io.File;
import java.io.IOException;

/**
 * File帮助类；
 */
public class FileHelper
{
	/** 获取当前程序所在的目录； */
	public static String getCurrentDir()
	{
		return System.getProperty("user.dir");
	}

	/**
	 * 创建文件；
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

	/** 创建目录； */
	public static boolean MakeDirectory(File directory)
	{
		return directory.mkdirs();
	}

}
