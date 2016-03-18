package com.haoxueren.file;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
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
	public void enterPath(String path)
	{
		parent = new File(path);
		list = new ArrayList<>();
		File[] files = parent.listFiles();
		for (File file : files)
		{
			list.add(file);
			if (file.isFile())
			{
				listener.output(list.size() + "、[F]" + file.getName() + "\n");
			} else
			{
				listener.output(list.size() + "、[D]" + file.getName() + "\n");
			}
		}
	}

	/** 根据文件编号打开文件； */
	public void openFile(int id) throws IOException
	{
		Desktop.getDesktop().open(list.get(id - 1));
	}

	/** 显示指定子目录下的文件列表； */
	public void listDir(int id)
	{
		File dir = list.get(id - 1);
		if (dir.isDirectory())
		{
			File[] files = dir.listFiles();
			for (File file : files)
			{
				list.add(file);
				if (file.isFile())
				{
					listener.output(list.size() + "、[F]" + file.getName() + "\n");
				} else
				{
					listener.output(list.size() + "、[D]" + file.getName() + "\n");
				}
			}
			parent = dir;
		}
	}
}
