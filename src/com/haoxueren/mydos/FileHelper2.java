package com.haoxueren.mydos;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.haoxueren.helper.CommandHelper;

/** 操作文件的帮助类； */
public class FileHelper2
{
	/** 要操作的目录； */
	private String directory;

	public FileHelper2(String directory)
	{
		this.directory = directory;
	}

	/**
	 * 输入文件名，打开指定目录内对应的文件； 如果对应多个文件，会提示用户进行二次选择；
	 * 
	 * @param fileName
	 *            这是要打开的文件的名称；
	 */
	public void openFileByName(String fileName, Scanner scanner) throws IOException
	{
		// 获取符合条件的文件集合；
		List<File> fileList = Files.getFileByName(directory, fileName);

		// 如果集合为空，说明文件不存在；
		if (fileList.isEmpty())
		{
			CommandHelper commandHelper = new CommandHelper(fileName);
			String keyWords = fileName.replaceAll("\\s+", "%20");
			commandHelper.search(keyWords);
			return;
		}
		// 如果集合中只有一个元素，直接打开该文件；
		if (fileList.size() == 1)
		{
			// update at 2015-6-29 10:54:42
			String filePath = fileList.get(0).getAbsolutePath();
			Runtime.getRuntime().exec("cmd /C start " + filePath);
			System.out.println("\n文件打开成功！\n");
			return;
		}

		// 如果集合中有多个元素，提示用户选择；
		// 定义一个变量用来记录文件编号；
		int i = 0;
		// 遍历获取到的文件集合；
		for (File file : fileList)
		{
			System.out.println(++i + "、" + file.getName());
		}
		while (true)
		{
			System.out.println("[0]请选择文件编号！");
			int index = Integer.parseInt(scanner.nextLine());
			if (index > 0 && index <= fileList.size())
			{
				// update at 2015-6-29 17:17:48
				String path = fileList.get(index - 1).getAbsolutePath();
				Runtime.getRuntime().exec("cmd /C start " + path);
				System.out.println("文件 " + fileList.get(index - 1).getName() + " 打开成功！\n");
				break;
			} else
			{
				System.out.println("找不到编号为 " + index + " 的文件！\n");
			}
		}
	}
}
