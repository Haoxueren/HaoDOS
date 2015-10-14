package com.haoxueren.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @class
 *        这是操作File类的工具类；
 * @author 好学人
 */
public class FileUtils
{
	/**
	 * 方法描述：getDirsFiles 这是一个获取指定目录下所有文件的集合；
	 * 
	 * @param dir
	 *                这是指定的目录，支持多级目录；
	 * @return
	 *         包含该目录下所有文件的ArrayList<File>集合；
	 */
	public static ArrayList<File> getDirsFiles(ArrayList<File> fileList, File dir)
	{
		File[] files = dir.listFiles();
		// 如果数组不为空，就对数组进行操作；
		if (files != null)
		{
			for (File file : files)
			{
				// 规律：如果文件是目录，就递归；
				if (file.isDirectory())
				{
					getDirsFiles(fileList, file);
				} else
				{
					// 出口：添加指定后缀名的文件到集合；
					fileList.add(file);
				}
			}
		}
		// 返回得到的文件集合；
		return fileList;
	}

	/**
	 * @method getFilesBySuffixs
	 *         这是一个获取指定目录下以指定后缀名文件集合的方法，支持多级目录；
	 * @param dir
	 *                想要获取的目录；
	 * @param suffixs
	 *                要指定的文件的后缀名（可变参数）；
	 *                如果传入一个空字符，即代表任意后缀均可；
	 * @return
	 *         包含指定后缀名的文件的ArrayList集合；
	 */
	// 这是用来储存指定目录下所有文件的集合；
	// private static ArrayList<File> dirFileList = new ArrayList<>();

	public static ArrayList<File> getFilesBySuffixs(ArrayList<File> fileList, File dir, String... suffixs)
	{
		File[] files = dir.listFiles();
		// 如果数组不为空，就对数组进行操作；
		if (files != null)
		{
			for (File file : files)
			{
				// 规律：如果文件是目录，就递归；
				if (file.isDirectory())
				{
					getFilesBySuffixs(fileList, file, suffixs);
				} else
				{
					for (String suffix : suffixs)
					{
						if (file.getName().endsWith(suffix))
						{
							// 出口：添加指定后缀名的文件到集合；
							fileList.add(file);
						}
					}
				}
			}
		}
		// 返回得到的文件集合；
		return fileList;
	}

	/**
	 * @method getFileByName
	 *         输入文件名，打开指定目录下的包含该文件名的文件；
	 *         如果对应多个文件，则提供一个有序列表供用户选择打开；
	 *         建议将程序的快捷方式集中到指定的目录中，以提高程序的运行效率；
	 * @param dirPath
	 *                要操作的目录路径，本程序支持递归多级目录；
	 * @param fileName
	 *                想要获取的文件名称，可以通过键盘录入；
	 * @return
	 *         要查找的文件的集合；
	 *         如果集合为空，说明没有符合条件的文件；
	 */
	public static List<File> getFileByName(ArrayList<File> fileList, String dirPath, String fileName) throws IOException
	{
		// 封装要搜索的范围；
		File dir = new File(dirPath);

		// 获取该目录下所有文件的集合（支持多级目录）；
		ArrayList<File> files = FileUtils.getDirsFiles(fileList, dir);

		// 遍历集合，查询符合条件的数据；
		for (File file : files)
		{
			// 判断文件名是否包含输入的内容，不区分大小写；
			if (file.getName().toLowerCase().contains(fileName.toLowerCase()))
			{
				// 将符合条件的文件添加到集合；
				fileList.add(file);
			}
		}
		return fileList;
	}

	/**
	 * @method getDisksFiles
	 *         这是一个获取电脑上所有磁盘内文件的方法；
	 * @return
	 *         一个包含了该电脑上所有文件(含隐藏文件)的集合；
	 */
	public static ArrayList<File> getDisksFiles(ArrayList<File> list)
	{
		// 获取根目录数组；
		File[] roots = File.listRoots();
		// 遍历根目录数组，获取到单个根目录；
		for (File root : roots)
		{
			// 获取每个根目录下的所有文件，并添加到diskFileList；
			list.addAll(getDirsFiles(list, root));
		}
		return list;
	}

	/**
	 * @method getFilesBySuffixs
	 *         这是一个获取电脑中所有指定后缀名文件的方法；
	 * @param suffixs
	 *                要获取的指定文件的后缀名（可变参数）；
	 * @return
	 *         符合指定的后缀名的条件的文件的ArrayList集合；
	 */
	public static ArrayList<File> getFilesBySuffixs(ArrayList<File> list, String... suffixs)
	{
		// 获取根目录数组；
		File[] roots = File.listRoots();
		// 遍历根目录数组，获取到单个根目录；
		for (File root : roots)
		{
			// 获取每个根目录下的所有文件，并添加到diskFileList；
			list.addAll(getFilesBySuffixs(list, root, suffixs));
		}
		return list;
	}
}
