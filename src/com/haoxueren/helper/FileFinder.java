package com.haoxueren.helper;

import java.io.File;

/** �����ݹ�����ļ��İ����ࣻ12222 */   
public class FileFinder
{
	private FileFinderListener listener;

	public FileFinder(FileFinderListener listener)
	{
		this.listener = listener;
	}

	/** �����ļ����ݹ������ļ��� */
	public void findFile(File directory)
	{
		File[] files = directory.listFiles();
		for (File file : files)
		{
			if (file.isDirectory())
			{
				findFile(file);
				listener.onFileFindOut(file);
			} else
			{
				listener.onFileFindOut(file);
			}
		}
	}

	/** �����ļ�ʱ�ļ������� */
	public interface FileFinderListener
	{
		/** �������ļ�/�ļ���ʱ���ã� */
		void onFileFindOut(File file);
	}
}
