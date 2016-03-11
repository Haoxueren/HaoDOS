package com.haoxueren.utils;

import java.io.File;

/** �����ݹ�����ļ��İ����ࣻ */
public class FileFinder
{
	private FileFinderListener listener;

	public FileFinder(FileFinderListener listener)
	{
		this.listener = listener;
	}

	/** ����ļ���ݹ������ļ��� */
	public void findFile(File directory)
	{
		File[] files = directory.listFiles();
		for (File file : files)
		{
			if (file.isDirectory())
			{
				findFile(file);
				listener.onFileFindOut(directory, file);
			} else
			{
				listener.onFileFindOut(directory, file);
			}
		}
	}

	/** �����ļ�ʱ�ļ������� */
	public interface FileFinderListener
	{
		/** �����ļ�/�ļ���ʱ���ã� */
		void onFileFindOut(File directory, File file);
	}
}
