package com.haoxueren.helper;

import java.io.File;
import java.util.ArrayList;

import com.haoxueren.helper.FileFinder.FileFinderListener;

/** ���ҳ�ָ��Ŀ¼�������ļ��� */
public class FileHelper implements FileFinderListener
{
	/** �������������ļ����ϣ� */
	private ArrayList<File> list;
	/** ���ط����������ļ��еļ��ϣ� */
	private FileHelperListener listener;

	public FileHelper(FileHelperListener listener)
	{
		this.listener = listener;
	}

	/** �����ļ��������ļ��� */
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
