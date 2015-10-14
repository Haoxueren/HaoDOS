package com.haoxueren.helper;

import java.io.File;

/** 用来递归查找文件的帮助类；12222 */   
public class FileFinder
{
	private FileFinderListener listener;

	public FileFinder(FileFinderListener listener)
	{
		this.listener = listener;
	}

	/** 根据文件名递归所有文件； */
	public void findFile(File directory)
	{
		File[] files = directory.listFiles();
		for (File file : files)
		{
			if (file.isDirectory())
			{
				findFile(file);
				listener.onFileFindOut(file);
			} else
			{
				listener.onFileFindOut(file);
			}
		}
	}

	/** 查找文件时的监听器； */
	public interface FileFinderListener
	{
		/** 遍历到文件/文件夹时调用； */
		void onFileFindOut(File file);
	}
}
