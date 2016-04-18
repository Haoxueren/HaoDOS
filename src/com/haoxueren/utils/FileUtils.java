package com.haoxueren.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.haoxueren.utils.FileSeek.FileFinderListener;

/** ���ҳ�ָ��Ŀ¼�������ļ��� */
public class FileUtils implements FileFinderListener
{
	/** �������������ļ����ϣ� */
	private ArrayList<File> list;
	/** ���ط���������ļ��еļ��ϣ� */
	private FileHelperListener listener;

	public FileUtils(FileHelperListener listener)
	{
		this.listener = listener;
	}

	/** ����ļ�������ļ��� */
	public void getFiles(File directory)
	{
		list = new ArrayList<File>();
		FileSeek finder = new FileSeek(this);
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
	 * ����������getDirsFiles ����һ����ȡָ��Ŀ¼�������ļ��ļ��ϣ�
	 * 
	 * @param dir
	 *            ����ָ����Ŀ¼��֧�ֶ༶Ŀ¼��
	 * @return ���Ŀ¼�������ļ���ArrayList<File>���ϣ�
	 */
	public static ArrayList<File> getDirsFiles(ArrayList<File> fileList, File dir)
	{
		File[] files = dir.listFiles();
		// ������鲻Ϊ�գ��Ͷ�������в�����
		if (files != null)
		{
			for (File file : files)
			{
				// ���ɣ�����ļ���Ŀ¼���͵ݹ飻
				if (file.isDirectory())
				{
					getDirsFiles(fileList, file);
				} else
				{
					// ���ڣ����ָ����׺����ļ������ϣ�
					fileList.add(file);
				}
			}
		}
		// ���صõ����ļ����ϣ�
		return fileList;
	}

}
