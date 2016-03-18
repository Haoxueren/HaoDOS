package com.haoxueren.file;

import java.io.File;
import java.io.IOException;

public class FileHelper
{
	/** 获取程序所在目录； */
	public static String getCurrentDir()
	{
		return System.getProperty("user.dir");
	}

	/** 创建文件； */
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
	public static boolean mkdirs(File directory)
	{
		if (!directory.exists())
		{
			return directory.mkdirs();
		}
		return false;
	}

	/** 以后缀名判断文件类型； */
	public boolean endWith(String filename, String... suffixs)
	{
		for (String suffix : suffixs)
		{
			if (filename.endsWith(suffix))
			{
				return true;
			}
		}
		return false;
	}
}
