package com.haoxueren.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

/**
 * @author Haoxueren
 */
public class CopyUtils
{
	/**
	 * �������ϵĿ�ݷ�ʽ���Ƶ�ָ����Ŀ¼�£�
	 */
	public static void copyDesktop() throws IOException
	{
		// ��ȡ�������·����
		FileSystemView fileView = FileSystemView.getFileSystemView();
		File desktop = fileView.getHomeDirectory();
		File[] files = desktop.listFiles();
		for (File file : files)
		{
			if (file.getAbsolutePath().startsWith(desktop.getAbsolutePath()))
			{
				System.out.println(file.getAbsolutePath());
			}
		}
	}

	/**
	 * @method ����һ����ָ���ļ����Ƶ�ָ��Ŀ¼�ķ�����
	 * @param file
	 *            Ҫ���Ƶ�Դ�ļ�����֧�ָ���Ŀ¼��
	 * @param dir
	 *            Ҫ���Ƶ���Ŀ¼��
	 * @return ͨ��������ļ��Ƿ�����ж��Ƿ��Ƴɹ���
	 */
	public static boolean copyFile(File file, String dir) throws IOException
	{
		// ���������ļ���һ��Ŀ¼���׳��쳣��
		if (file.isDirectory())
		{
			throw new IllegalArgumentException("��֧�ָ���Ŀ¼��" + file.getName());
		}
		// ���ָ��Ŀ¼�����ڣ��ʹ�����
		File targetDir = new File(dir);
		if (!targetDir.exists())
		{
			targetDir.mkdirs();
		}
		// ��װ���ļ���·����
		File path = new File(dir, file.getName());
		// ��װ��ȡ�ļ��Ķ���
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		// ��װд���ļ��Ķ���
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
		// ���ݴ���ǰ׼����
		byte[] bys = new byte[1024];
		int len = 0;
		// ��ʼ�������ݣ�
		while ((len = bis.read(bys)) != -1)
		{
			bos.write(bys, 0, len);
			bos.flush();
		}
		// �ͷ�ϵͳ��Դ��
		bis.close();
		bos.close();
		// �����Ƿ��Ƴɹ���
		return path.exists();
	}
}
