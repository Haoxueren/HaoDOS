package com.haoxueren.mydos;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.haoxueren.utils.FileUtils;

public class WordCheck
{
	private Scanner scanner;

	/** 通过构造方法注入scanner对象； */
	public WordCheck(Scanner scanner)
	{
		this.scanner = scanner;
	}

	/**
	 * @method 输入文件名，打开指定目录下的包含该文件名的所有文件；
	 * @param wordsPath
	 *            要操作的目录路径，本程序支持多级目录；
	 */
	public void add(String word) throws IOException, MyException
	{
		if (word.trim().length() <= 1)
		{
			throw new MyException("亲，内容太少了吧！");
		}
		// 封装要查询的目录；
		File dir = new File(MyConstants.WORDS_PATH);
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
		// 如果从来没有进去过，就提示文件不存在！
		System.out.println("该单词不存在！是否添加(Y/N)?");
		String command = scanner.nextLine();
		if ("Y".equalsIgnoreCase(command))
		{
			// 在单词图解目录下创建该文件；
			File file = new File(MyConstants.WORDS_PATH, word + ".png");
			file.createNewFile();
			System.out.println("单词 " + word + "已添加成功 ！");
			// 以编辑方式打开文件；
			desktop.edit(file);
			return;
		}
		System.out.println("单词" + word + "操作已取消！");
	}
}
