package com.haoxueren.helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;

import com.haoxueren.helper.FileFinder.FileFinderListener;
import com.haoxueren.mydos.MyConstants;

/** 查找出指定目录下所有文件； */
public class FileHelper_ implements FileFinderListener
{
	/** 用来保存所有文件集合； */
	private ArrayList<File> list;
	/** 返回符合条件的文件夹的集合； */
	private FileHelperListener listener;

	public FileHelper_(FileHelperListener listener)
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

	/** 如果文件不存在，就创建文件； */
	public void createFile(File file) throws IOException
	{
		if (!file.exists())
		{
			file.createNewFile();
		}
	}

}
