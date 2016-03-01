package com.haoxueren.helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.haoxueren.helper.FileFinder.FileFinderListener;

/** 查找出指定目录下所有文件； */
public class FileUtils implements FileFinderListener
{
	/** 用来保存所有文件集合； */
	private ArrayList<File> list;
	/** 返回符合条件的文件夹的集合； */
	private FileHelperListener listener;

	public FileUtils(FileHelperListener listener)
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

	/**
	 * 方法描述：getDirsFiles 这是一个获取指定目录下所有文件的集合；
	 * 
	 * @param dir
	 *            这是指定的目录，支持多级目录；
	 * @return 包含该目录下所有文件的ArrayList<File>集合；
	 */
	public static ArrayList<File> getDirsFiles(ArrayList<File> fileList, File dir)
	{
		File[] files = dir.listFiles();
		// 如果数组不为空，就对数组进行操作；
		if (files != null)
		{
			for (File file : files)
			{
				// 规律：如果文件是目录，就递归；
				if (file.isDirectory())
				{
					getDirsFiles(fileList, file);
				} else
				{
					// 出口：添加指定后缀名的文件到集合；
					fileList.add(file);
				}
			}
		}
		// 返回得到的文件集合；
		return fileList;
	}

}
