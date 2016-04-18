package com.haoxueren.main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** 从指定的目录中查找符合条件的文件； */
public class FileFinder
{
	/** 保存查找到的文件的集合； */
	private List<File> list;
	private OutputListener listener;
	private static FileFinder fileFinder;

	private FileFinder(OutputListener listener)
	{
		list = new ArrayList<>();
		this.listener = listener;
	}

	public static FileFinder getInstance(OutputListener listener)
	{
		if (fileFinder == null)
		{
			fileFinder = new FileFinder(listener);
		}
		return fileFinder;
	}

	/** 从目录中搜索文件； */
	public void searchFile(File directory, String fileRegex)
	{
		File[] fileList = directory.listFiles();
		if (fileList == null)
		{
			listener.output(directory.getAbsolutePath() + "拒绝访问");
			return;
		}
		for (File file : fileList)
		{
			if (file.getName().matches(fileRegex))
			{
				// 添加文件所在目录；
				File parentFile = file.getParentFile();
				list.add(parentFile);
				listener.output("F[" + list.size() + "] " + parentFile.getName());
				// 添加文件自身；
				list.add(file);
				String tag = file.isFile() ? "F" : "D";
				listener.output("　　" + tag + "[" + list.size() + "] " + file.getName());
			}

			if (file.isDirectory())
			{
				searchFile(file, fileRegex);
			}
		}
	}

	/** 打开指定编号的文件； */
	public void openFile(int id) throws IOException
	{
		File file = list.get(id - 1);
		listener.output("正在打开文件：" + file.getName());
		Desktop.getDesktop().open(file);
	}

	public void reset()
	{
		list.clear();

	}

}
