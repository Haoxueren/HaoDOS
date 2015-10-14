package com.haoxueren.mydos;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.haoxueren.helper.CommandHelper;

/** �����ļ��İ����ࣻ */
public class FileHelper2
{
	/** Ҫ������Ŀ¼�� */
	private String directory;

	public FileHelper2(String directory)
	{
		this.directory = directory;
	}

	/**
	 * �����ļ�������ָ��Ŀ¼�ڶ�Ӧ���ļ��� �����Ӧ����ļ�������ʾ�û����ж���ѡ��
	 * 
	 * @param fileName
	 *            ����Ҫ�򿪵��ļ������ƣ�
	 */
	public void openFileByName(String fileName, Scanner scanner) throws IOException
	{
		// ��ȡ�����������ļ����ϣ�
		List<File> fileList = Files.getFileByName(directory, fileName);

		// �������Ϊ�գ�˵���ļ������ڣ�
		if (fileList.isEmpty())
		{
			CommandHelper commandHelper = new CommandHelper(fileName);
			String keyWords = fileName.replaceAll("\\s+", "%20");
			commandHelper.search(keyWords);
			return;
		}
		// ���������ֻ��һ��Ԫ�أ�ֱ�Ӵ򿪸��ļ���
		if (fileList.size() == 1)
		{
			// update at 2015-6-29 10:54:42
			String filePath = fileList.get(0).getAbsolutePath();
			Runtime.getRuntime().exec("cmd /C start " + filePath);
			System.out.println("\n�ļ��򿪳ɹ���\n");
			return;
		}

		// ����������ж��Ԫ�أ���ʾ�û�ѡ��
		// ����һ������������¼�ļ���ţ�
		int i = 0;
		// ������ȡ�����ļ����ϣ�
		for (File file : fileList)
		{
			System.out.println(++i + "��" + file.getName());
		}
		while (true)
		{
			System.out.println("[0]��ѡ���ļ���ţ�");
			int index = Integer.parseInt(scanner.nextLine());
			if (index > 0 && index <= fileList.size())
			{
				// update at 2015-6-29 17:17:48
				String path = fileList.get(index - 1).getAbsolutePath();
				Runtime.getRuntime().exec("cmd /C start " + path);
				System.out.println("�ļ� " + fileList.get(index - 1).getName() + " �򿪳ɹ���\n");
				break;
			} else
			{
				System.out.println("�Ҳ������Ϊ " + index + " ���ļ���\n");
			}
		}
	}
}
