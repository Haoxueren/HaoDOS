package com.haoxueren.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.haoxueren.main.OutputListener;

/**
 * 逐级查询文件的帮助类；<br>
 * 类似Linux下的ls命令；<br>
 */
public class FileList
{
	private File parent;
	private List<File> list;
	private OutputListener listener;

	public FileList(OutputListener listener)
	{
		this.listener = listener;
	}

	/** 入口：显示指定目录的文件列表； */
	public void enter(String path)
	{
		parent = new File(path);
		list = new ArrayList<>();
		File[] files = parent.listFiles();
		for (int i = 0; i < files.length; i++)
		{
			list.add(files[i]);
			listener.output(i + "、" + files[i].getName() + "\n");
		}
	}

	/** 显示指定子目录下的文件列表； */
	public void folder(String folder)
	{
		File dir = new File(parent, folder);
		if (dir.isDirectory())
		{
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				listener.output(i + "、" + files[i].getName() + "\n");
			}
			parent = dir;
		}
	}
}
