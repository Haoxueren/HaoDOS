package com.haoxueren.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @class
 *        ���ǲ���File��Ĺ����ࣻ
 * @author ��ѧ��
 */
public class FileUtils
{
	/**
	 * ����������getDirsFiles ����һ����ȡָ��Ŀ¼�������ļ��ļ��ϣ�
	 * 
	 * @param dir
	 *                ����ָ����Ŀ¼��֧�ֶ༶Ŀ¼��
	 * @return
	 *         ������Ŀ¼�������ļ���ArrayList<File>���ϣ�
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
					// ���ڣ����ָ����׺�����ļ������ϣ�
					fileList.add(file);
				}
			}
		}
		// ���صõ����ļ����ϣ�
		return fileList;
	}

	/**
	 * @method getFilesBySuffixs
	 *         ����һ����ȡָ��Ŀ¼����ָ����׺���ļ����ϵķ�����֧�ֶ༶Ŀ¼��
	 * @param dir
	 *                ��Ҫ��ȡ��Ŀ¼��
	 * @param suffixs
	 *                Ҫָ�����ļ��ĺ�׺�����ɱ��������
	 *                �������һ�����ַ��������������׺���ɣ�
	 * @return
	 *         ����ָ����׺�����ļ���ArrayList���ϣ�
	 */
	// ������������ָ��Ŀ¼�������ļ��ļ��ϣ�
	// private static ArrayList<File> dirFileList = new ArrayList<>();

	public static ArrayList<File> getFilesBySuffixs(ArrayList<File> fileList, File dir, String... suffixs)
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
					getFilesBySuffixs(fileList, file, suffixs);
				} else
				{
					for (String suffix : suffixs)
					{
						if (file.getName().endsWith(suffix))
						{
							// ���ڣ����ָ����׺�����ļ������ϣ�
							fileList.add(file);
						}
					}
				}
			}
		}
		// ���صõ����ļ����ϣ�
		return fileList;
	}

	/**
	 * @method getFileByName
	 *         �����ļ�������ָ��Ŀ¼�µİ������ļ������ļ���
	 *         �����Ӧ����ļ������ṩһ�������б��û�ѡ��򿪣�
	 *         ���齫����Ŀ�ݷ�ʽ���е�ָ����Ŀ¼�У�����߳��������Ч�ʣ�
	 * @param dirPath
	 *                Ҫ������Ŀ¼·����������֧�ֵݹ�༶Ŀ¼��
	 * @param fileName
	 *                ��Ҫ��ȡ���ļ����ƣ�����ͨ������¼�룻
	 * @return
	 *         Ҫ���ҵ��ļ��ļ��ϣ�
	 *         �������Ϊ�գ�˵��û�з����������ļ���
	 */
	public static List<File> getFileByName(ArrayList<File> fileList, String dirPath, String fileName) throws IOException
	{
		// ��װҪ�����ķ�Χ��
		File dir = new File(dirPath);

		// ��ȡ��Ŀ¼�������ļ��ļ��ϣ�֧�ֶ༶Ŀ¼����
		ArrayList<File> files = FileUtils.getDirsFiles(fileList, dir);

		// �������ϣ���ѯ�������������ݣ�
		for (File file : files)
		{
			// �ж��ļ����Ƿ������������ݣ������ִ�Сд��
			if (file.getName().toLowerCase().contains(fileName.toLowerCase()))
			{
				// �������������ļ���ӵ����ϣ�
				fileList.add(file);
			}
		}
		return fileList;
	}

	/**
	 * @method getDisksFiles
	 *         ����һ����ȡ���������д������ļ��ķ�����
	 * @return
	 *         һ�������˸õ����������ļ�(�������ļ�)�ļ��ϣ�
	 */
	public static ArrayList<File> getDisksFiles(ArrayList<File> list)
	{
		// ��ȡ��Ŀ¼���飻
		File[] roots = File.listRoots();
		// ������Ŀ¼���飬��ȡ��������Ŀ¼��
		for (File root : roots)
		{
			// ��ȡÿ����Ŀ¼�µ������ļ�������ӵ�diskFileList��
			list.addAll(getDirsFiles(list, root));
		}
		return list;
	}

	/**
	 * @method getFilesBySuffixs
	 *         ����һ����ȡ����������ָ����׺���ļ��ķ�����
	 * @param suffixs
	 *                Ҫ��ȡ��ָ���ļ��ĺ�׺�����ɱ��������
	 * @return
	 *         ����ָ���ĺ�׺�����������ļ���ArrayList���ϣ�
	 */
	public static ArrayList<File> getFilesBySuffixs(ArrayList<File> list, String... suffixs)
	{
		// ��ȡ��Ŀ¼���飻
		File[] roots = File.listRoots();
		// ������Ŀ¼���飬��ȡ��������Ŀ¼��
		for (File root : roots)
		{
			// ��ȡÿ����Ŀ¼�µ������ļ�������ӵ�diskFileList��
			list.addAll(getFilesBySuffixs(list, root, suffixs));
		}
		return list;
	}
}
