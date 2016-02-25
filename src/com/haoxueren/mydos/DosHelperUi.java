package com.haoxueren.mydos;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.haoxueren.word.WordHelper;

/**
 * 程序入口；
 */
public class DosHelperUi
{
	/** 任务类型； */
	private static String TASK_TYPE;

	public static void main(String[] args)
	{
		Commander commander = new Commander();
		Scanner scanner = new Scanner(System.in);
		while (true)
		{
			System.out.print("\n" + MyConstants.HEADER + ">\n");
			// 接收用户录入的指令；
			String command = scanner.nextLine().trim();
			// 执行退出指令；
			if (command.equalsIgnoreCase("$EXIT"))
			{
				scanner.close();
				scanner = null;
				System.out.println("程序已正常退出！");
				break;
			}
			// 记录当前的任务类型；
			if (command.equalsIgnoreCase("$WORD"))
			{
				TASK_TYPE = "$WORD";
				System.out.println("欢迎进入随机单词系统！");
				continue;
			}
			if (command.equalsIgnoreCase("$COMMON"))
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
					File wordFile = WordHelper.getRandomWordPath();
					Desktop.getDesktop().open(wordFile);
					WordHelper.getWordName(wordFile);
				} catch (IOException e)
				{
					System.out.println(e.getMessage());
				}
				continue;
			}
			// 执行通用指令；
			commander.runTask(command);
		}
	}

}
