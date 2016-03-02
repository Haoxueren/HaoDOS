package com.haoxueren.word;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.haoxueren.config.ConfigHelper;
import com.haoxueren.config.Keys;
import com.haoxueren.config.Values;
import com.haoxueren.helper.FileHelper;
import com.haoxueren.helper.FileUtils;

public class WordHelper
{
	private static String wrodsPath;
	/** 已抽取单词的个数； */
	private static int index, loop = 29;

	static
	{
		wrodsPath = ConfigHelper.getConfig(Keys.WORDS_PATH, Values.WORDS_PATH);
	}

	/** 初始化存储文件信息的集合； */
	private static Map<Long, File> initFileMap()
	{
		Map<Long, File> fileInfoMap = new TreeMap<>();
		File directory = new File(wrodsPath);
		if (!directory.exists())
		{
			directory.mkdirs();
		}
		File[] files = directory.listFiles();
		for (File file : files)
		{
			// 获取文件的最后修改时间；
			long lastModified = file.lastModified();
			// 将文件信息保存在fileInfoMap中；
			fileInfoMap.put(lastModified, file);
		}
		return fileInfoMap;
	}

	/**
	 * 算法：为知笔记2016.02.25； 要求：index<=loop<=sum；<br>
	 * 按就近原则(lastModifyTime)随机获取单词；<br>
	 */
	public static File getRandomWordFile()
	{
		Map<Long, File> map = initFileMap();
		Object[] objects = map.keySet().toArray();
		int sum = map.size();
		if (sum == 0)
		{
			return null;
		}
		int start = sum - (sum * (++index % loop) / loop);
		int random = RandomHelper.getRandomInt(start, sum);
		Object time = objects[random];
		File file = map.get(time);
		System.out.print("[" + start + "~" + sum + "]→" + index + "、" + getWordName(file));
		return file;
	}

	/** 根据文件获取单词名； */
	public static String getWordName(File word)
	{
		String name = word.getName();
		return name.substring(0, name.length() - 4);
	}

	/**
	 * @method 输入文件名，打开指定目录下的包含该文件名的所有文件；
	 * @param wordsPath
	 *            要操作的目录路径，本程序支持多级目录；
	 */
	public static void addWord(String word) throws IOException
	{
		String wordTrim = word.trim();
		if (wordTrim.length() <= 1)
		{
			throw new IllegalArgumentException("亲，内容太少了吧！");
		}
		// 封装要查询的目录；
		String word_dir = ConfigHelper.getConfig(Keys.WORDS_PATH, Values.WORDS_PATH);
		File dir = new File(word_dir);
		FileHelper.mkdirs(dir);
		// 创建Desktop对象；
		Desktop desktop = Desktop.getDesktop();
		// 获取该目录下所有文件的集合（支持多级目录）；
		ArrayList<File> files = FileUtils.getDirsFiles(new ArrayList<File>(), dir);

		// 遍历集合，查询符合条件的数据；
		for (File file : files)
		{
			// 判断文件名是否包含输入的内容，不区分大小写；
			if (file.getName().toLowerCase().contains(word.toLowerCase()))
			{
				// 如果包含，打开对应的文件；
				desktop.edit(file);
				System.out.println("文件 " + file.getName().split("\\.")[0] + " 已打开！");
				return;
			}
		}
		// 在单词图解目录下创建该文件；
		File file = new File(word_dir, word + ".png");
		ImageHelper.createImage(file);
		System.out.println(wordTrim + "已添加成功 ！");
		// 打开文件；
		desktop.open(file);
		desktop.edit(file);
	}

}
