package com.haoxueren.helper;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.haoxueren.config.ConfigHelper;
import com.haoxueren.config.Keys;
import com.haoxueren.config.Values;

/** 操作文件的帮助类； */
public class FileManager
{
	public FileManager()
	{
	}

	/** 根据文件名打开文件； */
	public void openFile(String dirPath, String filename) throws IOException
	{
		// 构建要操作的目录对象；
		File directory = new File(dirPath);
		// 遍历目录，打开符合条件的文件；
		File[] fileList = directory.listFiles();
		for (File file : fileList)
		{
			// 如果是文件夹，继续遍历；
			if (file.isDirectory())
			{
				openFile(file.getAbsolutePath(), filename);
			}
			// 如果是文件，判断是否符合条件；
			else
			{
				System.out.println(file.getName());
				if (file.getName().equalsIgnoreCase(filename))
				{
					Desktop.getDesktop().open(file);
				}
			}
		}
	}

}
