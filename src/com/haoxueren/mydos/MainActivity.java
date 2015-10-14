package com.haoxueren.mydos;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import net.sourceforge.pinyin4j.PinyinHelper;

import com.haoxueren.helper.CommandHelper;
import com.haoxueren.helper.FileHelper;
import com.haoxueren.helper.FileHelper.FileHelperListener;
import com.haoxueren.helper.ProcessHelper;
import com.haoxueren.helper.ProcessHelper.ProcessHelperListener;

/**
 * @author Haoxueren
 */
public class MainActivity
{
	public static void main(String[] args)
	{
		// 准备工作；
		Scanner scanner = new Scanner(System.in);
		ProcessHelper processHelper = listenerProcess();
		while (true)
		{
			try
			{
				System.out.print(MyConstants.HEADER);
				// 取出用户录入的数据；
				String nextLine = scanner.nextLine().trim();
				System.out.println();
				// 优先判断用户是否录入了退出指令；
				if (nextLine.equals("QUIT") || nextLine.equals("EXIT"))
				{
					scanner.close();
					scanner = null;
					System.out.println("您已安全退出");
					break;
				}
				// 处理用户录入空白字符的情况；
				if (nextLine.matches("\\s*"))
				{
					continue;
				}
				// 百度搜索功能；
				CommandHelper commandHelper = new CommandHelper(nextLine);
				if (commandHelper.matchSearchCommand())
				{
					String keyWords = commandHelper.getSearchWords();
					commandHelper.search(keyWords);
					continue;
				}
				// 爱词霸翻译功能；
				if (commandHelper.matchTranslateCommand())
				{
					String word = commandHelper.getTranslateWord();
					commandHelper.translate(word);
					continue;
				}

				// 打开网址功能；
				if (commandHelper.matchUrlCommand())
				{
					commandHelper.openUrl(nextLine);
					continue;
				}

				// 添加/打开单词功能；
				if (commandHelper.matchAddWordCommand() || commandHelper.matchEditWordCommand())
				{
					WordCheck wordCheck = new WordCheck(scanner);
					wordCheck.add(commandHelper.getEnglishWord().toLowerCase());
					continue;
				}

				// 执行用户录入的DOS指令；
				if (commandHelper.matchDosCommand())
				{
					Process process = commandHelper.executeDos();
					processHelper.readProcess(process);
					continue;
				}

				// 根据文件名打开对应的文件；
				findFileByName(nextLine);
			} catch (Exception e)
			{
				System.out.println(e.getMessage());
				System.out.println();
			}
		}
	}

	/** 初始化线程监听器； */
	private static ProcessHelper listenerProcess()
	{
		/* 监听DOS命令执行结果； */
		ProcessHelperListener processListener = new ProcessHelperListener()
		{
			@Override
			public void onReadLine(String line)
			{
				System.out.println(line);
			}
		};
		ProcessHelper processHelper = new ProcessHelper(processListener);
		return processHelper;
	}

	/** 根据文件名打开文件； */
	private static void findFileByName(final String filename)
	{
		FileHelper fileHelper = new FileHelper(new FileHelperListener()
		{
			@Override
			public void onFileFindOver(ArrayList<File> fileList)
			{
				try
				{
					// 如果集合为空，搜索该关键词；
					if (fileList.isEmpty())
					{
						// 替换用户指令中的空白字符；
						String keyWords = filename.replaceAll("\\s+", "%20");
						new CommandHelper().search(keyWords);
						return;
					}

					// 如果集合只有一个元素，直接打开该文件；
					if (fileList.size() == 1)
					{
						Desktop.getDesktop().open(fileList.get(0));
						return;
					}

					// 如果集合有多个元素，提示用户进一步选择；
					if (fileList.size() > 1)
					{
						for (File file : fileList)
						{
							System.out.println(file.getName());
						}
						return;
					}
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
		File directory = new File(MyConstants.PATH_SHORTCUTS);
		fileHelper.findFileByName(directory, filename);
	}

}
