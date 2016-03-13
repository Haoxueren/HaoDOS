package com.haoxueren.utils;

import java.io.File;
import java.io.IOException;

public class FileHelper
{
	public static String getCurrentDir()
	{
		return System.getProperty("user.dir");
	}

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

	public static boolean mkdirs(File directory)
	{
		if (!directory.exists())
		{
			return directory.mkdirs();
		}
		return false;
	}

}
