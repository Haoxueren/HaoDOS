package com.haoxueren.mydos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.haoxueren.utils.PinYinUtils;

/**
 * @author ��ѧ��
 * 
 * @description
 *              ���ǲ���File��Ĺ����ࣻ
 */
public class Files
{
	/**
	 * @method getDirsFiles
	 *         ����һ����ȡָ��Ŀ¼�������ļ��ļ��ϣ�
	 * @param dir
	 *                ����ָ����Ŀ¼��֧�ֶ༶Ŀ¼��
	 * @return
	 *         ������Ŀ¼�������ļ���ArrayList<File>���ϣ�
	 */
	public static ArrayList<File> getDirsFiles(File dir)
	{
		// ������������ָ��Ŀ¼�������ļ��ļ��ϣ�
		ArrayList<File> dirFileList = new ArrayList<>();

		File[] files = dir.listFiles();
		// ������鲻Ϊ�գ��Ͷ�������в�����
		if (files != null)
		{
			for (File file : files)
			{
				// ���ɣ�����ļ���Ŀ¼���͵ݹ飻
				if (file.isDirectory())
				{
					getDirsFiles(file);
				} else
				{
					// ���ڣ����ָ����׺�����ļ������ϣ�
					dirFileList.add(file);
				}
			}
		}
		// ���صõ����ļ����ϣ�
		return dirFileList;
	}

	/**
	 * @method getFilesBySuffixs
	 *         ����һ����ȡָ��Ŀ¼����ָ����׺���ļ����ϵķ�����
	 * @param dir
	 *                ��Ҫ��ȡ��Ŀ¼��
	 * @param suffixs
	 *                Ҫָ�����ļ��ĺ�׺�����ɱ��������
	 *                �������һ�����ַ��������������׺���ɣ�
	 * @return
	 *         ����ָ����׺�����ļ��ļ��ϣ�
	 */
	public static ArrayList<File> getFilesBySuffixs(File dir, String... suffixs)
	{
		// ������������ָ��Ŀ¼�������ļ��ļ��ϣ�
		ArrayList<File> dirFileList = new ArrayList<>();

		File[] files = dir.listFiles();
		// ������鲻Ϊ�գ��Ͷ�������в�����
		if (files != null)
		{
			for (File file : files)
			{
				// ���ɣ�����ļ���Ŀ¼���͵ݹ飻
				if (file.isDirectory())
				{
					getFilesBySuffixs(file, suffixs);
				} else
				{
					for (String suffix : suffixs)
					{
						if (file.getName().endsWith(suffix))
						{
							// ���ڣ����ָ����׺�����ļ������ϣ�
							dirFileList.add(file);
						}
					}
				}
			}
		}
		// ���صõ����ļ����ϣ�
		return dirFileList;
	}

	/**
	 * @method getFileByName
	 *         �����ļ�������ָ��Ŀ¼�µİ������ļ������ļ���
	 *         �����Ӧ����ļ������ṩһ�������б��û�ѡ��򿪣�
	 *         ���齫����Ŀ�ݷ�ʽ���е�ָ����Ŀ¼�У�����߳��������Ч�ʣ�
	 * @param dirPath
	 *                Ҫ������Ŀ¼·����������֧�ֵݹ�༶Ŀ¼��
	 * @param input
	 *                ��Ҫ��ȡ���ļ����ƣ�����ͨ������¼�룻
	 * @return
	 *         Ҫ���ҵ��ļ��ļ��ϣ�
	 *         �������Ϊ�գ�˵��û�з����������ļ���
	 */
	public static List<File> getFileByName(String dirPath, String input) throws IOException
	{
		// ����һ��List���ϣ������洢�����ķ��ؽ����
		List<File> fileList = new ArrayList<>();

		// ��װҪ�����ķ�Χ��
		File dir = new File(dirPath);

		// ��ȡ��Ŀ¼�������ļ��ļ��ϣ�֧�ֶ༶Ŀ¼����
		ArrayList<File> files = Files.getDirsFiles(dir);

		// �������ϣ���ѯ�������������ݣ�
		for (File file : files)
		{
			// �ж��ļ����Ƿ������������ݣ������ִ�Сд��
			if (file.getName().toLowerCase().contains(input.toLowerCase()))
			{
				// �������������ļ���ӵ����ϣ�
				fileList.add(file);
			} else if (PinYinUtils.getFirstLatter(file.getName()).toUpperCase().contains(input.toUpperCase()))
			{
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
	public static ArrayList<File> getDisksFiles()
	{
		// ���������������д����������ļ��ļ��ϣ�
		ArrayList<File> diskFileList = new ArrayList<>();

		// ��ȡ��Ŀ¼���飻
		File[] roots = File.listRoots();
		// ������Ŀ¼���飬��ȡ��������Ŀ¼��
		for (File root : roots)
		{
			// ��ȡÿ����Ŀ¼�µ������ļ�������ӵ�diskFileList��
			diskFileList.addAll(getDirsFiles(root));
		}
		return diskFileList;
	}

	/**
	 * @method getFilesBySuffixs
	 *         ����һ����ȡ����������ָ����׺���ļ��ķ�����
	 * @param suffixs
	 *                Ҫ��ȡ��ָ���ļ��ĺ�׺�����ɱ��������
	 * @return
	 *         ����ָ���ĺ�׺�����������ļ���ArrayList���ϣ�
	 */
	public static ArrayList<File> getFilesBySuffixs(String... suffixs)
	{
		// ���������������д����������ļ��ļ��ϣ�
		ArrayList<File> diskFileList = new ArrayList<>();

		// ��ȡ��Ŀ¼���飻
		File[] roots = File.listRoots();
		// ������Ŀ¼���飬��ȡ��������Ŀ¼��
		for (File root : roots)
		{
			// ��ȡÿ����Ŀ¼�µ������ļ�������ӵ�diskFileList��
			diskFileList.addAll(getFilesBySuffixs(root, suffixs));
		}
		return diskFileList;
	}
}
