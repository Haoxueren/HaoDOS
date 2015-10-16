package com.haoxueren.helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;

import com.haoxueren.helper.FileFinder.FileFinderListener;
import com.haoxueren.mydos.MyConstants;

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
	public void onFileFindOut(File directory, File file)
	{
		list.add(file);
	}

	public interface FileHelperListener
	{
		void onFileFindOver(ArrayList<File> list);
	}

	// ��ȡ�������·����
	public void moveLnkFile(String entrance) throws IOException
	{
		FileSystemView fileView = FileSystemView.getFileSystemView();
		File desktop = fileView.getHomeDirectory();
		File[] files = desktop.listFiles();
		for (File file : files)
		{
			// ������������ϵ��ļ������أ�
			if (!file.getAbsolutePath().startsWith(desktop.getAbsolutePath())
					&& !file.getAbsolutePath().startsWith("C:\\Users\\Public\\Desktop"))
			{
				continue;
			}
			// ������������ļ���
			if (file.getName().equals(entrance))
			{
				continue;
			}
			// ����ǿ�ݷ�ʽ���ƶ�����ݷ�ʽ�ļ��У�
			if (endWith(file.getName(), ".lnk", ".url"))
			{
				System.out.println("�����ƶ���ݷ�ʽ��" + file.getName());
				File dest = new File(MyConstants.PATH_SHORTCUTS, file.getName());
				file.renameTo(dest);
			} else
			{
				// ����������ƶ��������ļ�Ŀ¼��
				System.out.println("�����ƶ������ļ���" + file.getName());
				File directory = new File("D:\\���������ļ�");
				if (!directory.exists())
				{
					// ���Ŀ¼�����ڣ�������Ŀ¼��
					directory.createNewFile();
				}
				File destFile = new File(directory, file.getName());
				file.renameTo(destFile);
			}
		}
	}

	/** �ж��ļ�����ָ���ĺ�׺�� */
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
