package com.haoxueren.file;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.haoxueren.main.OutputListener;

/** 显示指定目录下的文件树； */
public class FileTree
{
	private int back;
	private List<File> list;
	private String skipDirRegex;
	private StringBuilder builder;
	private OutputListener listener;

	/** 创建FileTree对象； */
	public FileTree(File dir, OutputListener listener) throws Exception
	{
		this.listener = listener;
		list = new ArrayList<>();
		builder = new StringBuilder();
		back = dir.getAbsolutePath().split("\\\\").length + 1;
		skipDirRegex = FileLooker.skipRegex();
	}

	/** 列出指定目录的文件树； */
	public void tree(File dir, boolean showFile)
	{
		// 结构树要跳过的目录；
		if (dir.getName().matches(skipDirRegex))
		{
			return;
		}

		File[] files = dir.listFiles();
		if (files == null)
		{
			listener.output(dir.getName() + "拒绝访问");
			return;
		}
		for (File file : files)
		{
			// 将文件添加到集合中；
			list.add(file);
			// 获取文件的绝对路径；
			String path = file.getAbsolutePath();
			// 计算当前文件是几级目录；
			int level = path.split("\\\\").length - back;
			// 清空缩进符容器；
			builder.delete(0, builder.length());
			// 根据缩进量填充缩进容器；
			for (int i = 0; i < level; i++)
			{
				builder.append("　　");
			}
			// 遍历所有文件及目录；
			if (file.isDirectory())
			{
				listener.output(builder.toString() + "D[" + list.size() + "]" + file.getName());
				tree(file, showFile);
			} else
			{
				if (showFile)
				{
					listener.output(builder.toString() + "F[" + list.size() + "]" + file.getName());
				}
			}
		}
	}

	/** 根据文件编号打开文件； */
	public void openFile(int index) throws IOException
	{
		System.out.println(list.size());
		Desktop.getDesktop().open(list.get(index - 1));
	}

	public List<File> getList()
	{
		return list;
	}

	/** 进一步遍历，只显示目录； */
	public void treeDirId(int id)
	{
		File dir = list.get(id - 1);
		back = dir.getAbsolutePath().split("\\\\").length + 1;
		list.clear();
		tree(dir, true);
	}

	/** 进一步遍历，显示目录及文件 */
	public void treeFileId(int id)
	{
		File dir = list.get(id - 1);
		back = dir.getAbsolutePath().split("\\\\").length + 1;
		list.clear();
		tree(dir, true);
	}

}
