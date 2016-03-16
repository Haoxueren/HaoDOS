package com.haoxueren.helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.haoxueren.utils.PinYinUtils;

/**
 * 操作文件的帮助类； <br>
 * 从指定目录中查找符合条件的文件；<br>
 */
public class FileManager
{
	/** 用来保存符合条件的文件的集合； */
	private List<File> fileList;

	public FileManager()
	{
		fileList = new ArrayList<>();
	}

	/** 根据文件名打开文件； */
	public void fillFileList(String dirPath, String inputFilename) throws IOException
	{
		// 构建要操作的目录对象；
		File directory = new File(dirPath);
		// 遍历目录，打开符合条件的文件；
		File[] files = directory.listFiles();
		for (File file : files)
		{
			// 如果是文件夹，继续遍历；
			if (file.isDirectory())
			{
				fillFileList(file.getAbsolutePath(), inputFilename);
			}
			// 如果是文件，判断是否符合条件；
			else
			{
				String filename = file.getName();
				filename = filename.substring(0, filename.lastIndexOf('.'));
				// 将两方内容都转化成首字母缩写；
				String shortname = PinYinUtils.getFirstLatter(filename);
				String inputShortname = PinYinUtils.getFirstLatter(inputFilename);
				// 如果文件名的缩写包含输入文件名的缩写，将文件加入集合；
				if (shortname.contains(inputShortname))
				{
					fileList.add(file);
				}
			}
		}
	}

	public List<File> getFileList()
	{
		return fileList;
	}

}
