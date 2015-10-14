package com.haoxueren.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.haoxueren.helper.FileFinder.FileFinderListener;
import com.haoxueren.utils.PinYinUtils;
import com.haoxueren.utils.Pinyin;

public class FileHelper implements FileFinderListener
{
	/** 要查找文件全路径的正则表达式； */
	private String filename;
	/** 用来保存符合条件的文件集合； */
	private ArrayList<File> list;
	/** 返回符合条件的文件夹的集合； */
	private FileHelperListener listener;

	public FileHelper(FileHelperListener listener)
	{
		this.listener = listener;
	}

	/** 根据文件名查找文件； */
	public void findFileByName(File directory, String filename)
	{
		list = new ArrayList<File>();
		this.filename = filename;
		FileFinder finder = new FileFinder(this);
		finder.findFile(directory);
		listener.onFileFindOver(list);
	}

	@Override
	public void onFileFindOut(File file)
	{
		String name = file.getName().toUpperCase();
		filename = filename.toUpperCase();
		String regex_command = "[A-Z]+";
		// 如果用户录入的是文件名缩写；
		if (filename.matches(regex_command))
		{
			name = PinYinUtils.getFirstLatter(name).toUpperCase();
		}
		if (name.contains(filename))
		{
			list.add(file);
		}
	}

	public interface FileHelperListener
	{
		void onFileFindOver(ArrayList<File> list);
	}

}
