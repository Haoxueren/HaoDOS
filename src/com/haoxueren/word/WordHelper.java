package com.haoxueren.word;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.haoxueren.config.ConfigHelper;
import com.haoxueren.config.Keys;
import com.haoxueren.config.Values;
import com.haoxueren.file.FileHelper;
import com.haoxueren.main.OutputListener;
import com.haoxueren.utils.FileUtils;

public class WordHelper
{
	private static String wordsPath;
	private static WordHelper wordHelper;
	/** 已抽取单词的个数； */
	private static int index, loop = 29;
	private static Map<Long, File> map;
	private OutputListener listener;

	private WordHelper(OutputListener listener) throws Exception
	{
		this.listener = listener;
		wordsPath = ConfigHelper.getConfig(Keys.WORDS_PATH, Values.WORDS_PATH);
		map = initFileMap();
		listener.output("恭喜，单词库中已有" + map.size() + "个单词啦！");
		listener.output("—————————————————————————");
	}

	public static WordHelper getInstance(OutputListener listener) throws Exception
	{
		System.out.println(wordHelper);
		if (wordHelper == null)
		{
			wordHelper = new WordHelper(listener);
		}
		return wordHelper;
	}

	/** 初始化存储文件信息的集合； */
	private static Map<Long, File> initFileMap()
	{
		Map<Long, File> fileInfoMap = new TreeMap<>();
		File directory = new File(wordsPath);
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
	public File getRandomWordFile()
	{
		// Map<Long, File> map = initFileMap();
		Object[] objects = map.keySet().toArray();
		int sum = map.size();
		if (sum == 0)
		{
			return null;
		}
		int start = (int) (sum * (1 - (++index * 1.0f / loop)));
		int random = RandomHelper.getRandomInt(start, sum);
		Object time = objects[random];
		File file = map.get(time);
		listener.output(index + "、" + getWordName(file));
		// 循环一圈后，初始化index；
		index = index == loop ? 0 : index;
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
	public void addWord(String word) throws Exception
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
				listener.output("文件 " + file.getName().split("\\.")[0] + " 已打开！");
				return;
			}
		}
		// 在单词图解目录下创建该文件；
		File file = new File(word_dir, word + ".png");
		ImageHelper.createImage(file);
		listener.output(wordTrim + "已添加成功 ！");
		// 打开文件；
		desktop.open(file);
		desktop.edit(file);
	}

}
