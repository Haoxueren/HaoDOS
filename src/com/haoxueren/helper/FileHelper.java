package com.haoxueren.helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;

import com.haoxueren.helper.FileFinder.FileFinderListener;
import com.haoxueren.mydos.MyConstants;

/** 查找出指定目录下所有文件； */
public class FileHelper implements FileFinderListener
{
	/** 用来保存所有文件集合； */
	private ArrayList<File> list;
	/** 返回符合条件的文件夹的集合； */
	private FileHelperListener listener;

	public FileHelper(FileHelperListener listener)
	{
		this.listener = listener;
	}

	/** 根据文件名查找文件； */
	public void getFiles(File directory)
	{
		list = new ArrayList<File>();
		FileFinder finder = new FileFinder(this);
		finder.findFile(directory);
		listener.onFileFindOver(list);
	}

	@Override
	public void onFileFindOut(File directory, File file)
	{
		list.add(file);
	}

	public interface FileHelperListener
	{
		void onFileFindOver(ArrayList<File> list);
	}

	// 获取到桌面的路径；
	public void moveLnkFile(String entrance) throws IOException
	{
		FileSystemView fileView = FileSystemView.getFileSystemView();
		File desktop = fileView.getHomeDirectory();
		File[] files = desktop.listFiles();
		for (File file : files)
		{
			// 如果不是桌面上的文件，返回；
			if (!file.getAbsolutePath().startsWith(desktop.getAbsolutePath())
					&& !file.getAbsolutePath().startsWith("C:\\Users\\Public\\Desktop"))
			{
				continue;
			}
			// 保留程序入口文件；
			if (file.getName().equals(entrance))
			{
				continue;
			}
			// 如果是快捷方式，移动到快捷方式文件夹；
			if (endWith(file.getName(), ".lnk", ".url"))
			{
				System.out.println("正在移动快捷方式：" + file.getName());
				File dest = new File(MyConstants.PATH_SHORTCUTS, file.getName());
				file.renameTo(dest);
			} else
			{
				// 其它情况，移动到桌面文件目录；
				System.out.println("正在移动桌面文件：" + file.getName());
				File directory = new File("D:\\来自桌面文件");
				if (!directory.exists())
				{
					// 如果目录不存在，创建该目录；
					directory.createNewFile();
				}
				File destFile = new File(directory, file.getName());
				file.renameTo(destFile);
			}
		}
	}

	/** 判断文件名是指定的后缀名 */
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
