package com.haoxueren.mydos;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.haoxueren.config.Keys;
import com.haoxueren.config.Values;
import com.haoxueren.config.ConfigHelper;
import com.haoxueren.word.WordHelper;

/**
 * 程序入口；
 */
public class DosWindow
{
	/** 任务类型； */
	private static String TASK_TYPE;

	public static void main(String[] args)
	{
		Commander commander = new Commander();
		Scanner scanner = new Scanner(System.in);
		String titleMotto = ConfigHelper.getConfig(Keys.TITLE_MOTTO, Values.TITLE_MOTTO);
		while (true)
		{
			System.out.print("\n" + titleMotto + ">\n");
			// 接收用户录入的指令；
			String order = scanner.nextLine().trim();
			if (order.equalsIgnoreCase("$HELP"))
			{
				commander.printHelpInfo();
				continue;
			}
			// 执行退出指令；
			if (order.equalsIgnoreCase("$EXIT"))
			{
				scanner.close();
				scanner = null;
				System.out.println("程序已安全退出！");
				break;
			}
			// 记录当前的任务类型；
			if (order.equalsIgnoreCase("$WORD"))
			{
				TASK_TYPE = "$WORD";
				System.out.println("欢迎进入随机单词系统！");
				continue;
			}
			if (order.equalsIgnoreCase("$COMMON"))
			{
				TASK_TYPE = "$COMMON";
				System.out.println("欢迎进入通用指令系统！");
				continue;
			}
			// 执行随机单词指令；
			if ("$WORD".equalsIgnoreCase(TASK_TYPE))
			{
				try
				{
					File wordFile = WordHelper.getRandomWordFile();
					if (wordFile == null)
					{
						System.err.println("单词目录为空！");
						continue;
					}
					Desktop.getDesktop().open(wordFile);
					WordHelper.getWordName(wordFile);
				} catch (IOException e)
				{
					System.err.println(e.getMessage());
				}
				continue;
			}
			// 执行通用指令；
			commander.runTask(order);
		}
	}

}
