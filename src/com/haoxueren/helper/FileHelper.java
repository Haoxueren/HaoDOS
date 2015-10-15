package com.haoxueren.helper;

import java.io.File;
import java.util.ArrayList;

import com.haoxueren.helper.FileFinder.FileFinderListener;

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
	public void onFileFindOut(File file)
	{
		list.add(file);
	}

	public interface FileHelperListener
	{
		void onFileFindOver(ArrayList<File> list);
	}

}
