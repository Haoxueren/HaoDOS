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
	 * 将桌面上的快捷方式复制到指定的目录下；
	 */
	public static void copyDesktop() throws IOException
	{
		// 获取到桌面的路径；
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
	 * @method 这是一个将指定文件复制到指定目录的方法；
	 * @param file
	 *            要复制的源文件，不支持复制目录；
	 * @param dir
	 *            要复制到的目录；
	 * @return 通过检测新文件是否存在判断是否复制成功；
	 */
	public static boolean copyFile(File file, String dir) throws IOException
	{
		// 如果传入的文件是一个目录，抛出异常；
		if (file.isDirectory())
		{
			throw new IllegalArgumentException("不支持复制目录：" + file.getName());
		}
		// 如果指定目录不存在，就创建；
		File targetDir = new File(dir);
		if (!targetDir.exists())
		{
			targetDir.mkdirs();
		}
		// 封装新文件的路径；
		File path = new File(dir, file.getName());
		// 封装读取文件的对象；
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		// 封装写入文件的对象；
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
		// 数据传输前准备；
		byte[] bys = new byte[1024];
		int len = 0;
		// 开始传输数据；
		while ((len = bis.read(bys)) != -1)
		{
			bos.write(bys, 0, len);
			bos.flush();
		}
		// 释放系统资源；
		bis.close();
		bos.close();
		// 返回是否复制成功；
		return path.exists();
	}
}
