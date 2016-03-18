package com.haoxueren.file;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

import com.haoxueren.main.OutputListener;

/** 分类整理桌面上的文件； */
public class FileGroup
{
	private OutputListener listener;

	public FileGroup(OutputListener listener)
	{
		this.listener = listener;
	}

	/** 分类整理桌面上的文件； */
	public void group(String... suffixs) throws IOException
	{
		// 获取桌面文件对象；
		FileSystemView fileView = FileSystemView.getFileSystemView();
		File desktop = fileView.getHomeDirectory();
		// 获取桌面上所有文件；
		File[] files = desktop.listFiles();
		for (File file : files)
		{
			// 如果不是个人桌面上的文件，返回重新开始；
			if (!file.getAbsolutePath().startsWith(desktop.getAbsolutePath()))
			{
				continue;
			}
			// 如果当前文件是目录，返回重新开始；
			if (file.isDirectory())
			{
				continue;
			}
			// 分类整理桌面上的文件；
			for (String suffix : suffixs)
			{
				// 将文件移动到对应的目录；
				String filename = file.getName();
				if (filename.endsWith(suffix))
				{
					File dest = new File("D:/FileGroup/group" + suffix, filename);
					File destDir = dest.getParentFile();
					if (!destDir.exists())
					{
						destDir.mkdirs();
					}
					boolean success = file.renameTo(dest);
					listener.output("移动结果[" + success + "]" + filename + "\n");
				}
			}

		}
	}
}
