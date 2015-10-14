package com.haoxueren.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.haoxueren.helper.FileFinder.FileFinderListener;
import com.haoxueren.utils.PinYinUtils;
import com.haoxueren.utils.Pinyin;

public class FileHelper implements FileFinderListener
{
	/** Ҫ�����ļ�ȫ·����������ʽ�� */
	private String filename;
	/** ������������������ļ����ϣ� */
	private ArrayList<File> list;
	/** ���ط����������ļ��еļ��ϣ� */
	private FileHelperListener listener;

	public FileHelper(FileHelperListener listener)
	{
		this.listener = listener;
	}

	/** �����ļ��������ļ��� */
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
		// ����û�¼������ļ�����д��
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
