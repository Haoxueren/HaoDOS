package com.haoxueren.mydos;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.haoxueren.helper.CommandHelper;
import com.haoxueren.helper.FileHelper;
import com.haoxueren.helper.FileHelper.FileHelperListener;
import com.haoxueren.helper.ProcessHelper;
import com.haoxueren.helper.ProcessHelper.ProcessHelperListener;
import com.haoxueren.utils.PinYinUtils;

/** 程序运行时的主逻辑类； */
public class Commander implements FileHelperListener, ProcessHelperListener
{
	private String command;
	private List<File> fileList;
	private ProcessHelper processHelper;
	private CommandHelper commandHelper;

	// 初始化数据；
	public Commander()
	{
		processHelper = new ProcessHelper(this);
		commandHelper = new CommandHelper();
		FileHelper fileHelper = new FileHelper(this);
		File directory = new File(MyConstants.PATH_SHORTCUTS);
		fileHelper.getFiles(directory);
	}

	/**
	 * 调用此方法启动程序；
	 * 
	 * @return 任务是否结束；
	 */
	public boolean runTask(String command)
	{
		this.command = command;
		return startWorking(fileList);
	}

	/**
	 * 程序运行中：执行用户指令；
	 * 
	 * @return 是否退出程序；
	 */
	public boolean startWorking(List<File> fileList)
	{
		try
		{
			commandHelper.setCommand(command);
			// 优先判断用户是否录入了退出指令；
			if (command.equals("QUIT") || command.equals("EXIT"))
			{
				return true;
			}
			// 处理用户录入空白字符的情况；
			else if (command.matches("\\s*"))
			{
			}
			// 百度搜索功能；
			else if (commandHelper.matchSearchCommand())
			{
				String keyWords = commandHelper.getSearchWords();
				commandHelper.search(keyWords);
			}
			// 爱词霸翻译功能；
			else if (commandHelper.matchTranslateCommand())
			{
				String word = commandHelper.getTranslateWord();
				commandHelper.translate(word);
			}
			// 打开网址功能；
			else if (commandHelper.matchUrlCommand())
			{
				commandHelper.openUrl(command);
			}
			// 添加/打开单词功能；
			else if (commandHelper.matchAddWordCommand() || commandHelper.matchEditWordCommand())
			{
				WordCheck wordCheck = new WordCheck();
				wordCheck.add(commandHelper.getEnglishWord().toLowerCase());
			}
			// 执行用户录入的DOS指令；
			else if (commandHelper.matchDosCommand())
			{
				Process process = commandHelper.executeDos();
				processHelper.readProcess(process);
			} else
			{
				// 根据文件名打开对应的文件；
				String filename = command;
				openFileByName(fileList, filename);
			}
			return false;
		} catch (Exception e)
		{
			return true;
		}
	}

	/** 筛选出符合条件的文件； */
	public List<File> filterFile(List<File> fileList, String filename)
	{
		// 用来保存符合条件的文件的集合；
		List<File> filterLlist = new ArrayList<>();
		for (File file : fileList)
		{
			String name = file.getName().toUpperCase();
			filename = filename.toUpperCase();
			String regex_command = "[A-Z]+";
			// 如果用户录入的是文件名缩写；
			if (filename.matches(regex_command))
			{
				name = PinYinUtils.getFirstLatter(name).toUpperCase();
			}
			if (name.contains(filename))
			{
				filterLlist.add(file);
			}
		}
		return filterLlist;
	}

	/** 根据文件名打开文件； */
	private void openFileByName(List<File> fileList, String filename)
	{
		try
		{
			// 筛选出符合条件的文件；
			List<File> files = filterFile(fileList, filename);
			// 如果集合为空，搜索该关键词；
			if (files.isEmpty())
			{
				// 替换用户指令中的空白字符；
				String keyWords = filename.replaceAll("\\s+", "%20");
				new CommandHelper().search(keyWords);
				return;
			}

			// 如果集合只有一个元素，直接打开该文件；
			if (files.size() == 1)
			{
				Desktop.getDesktop().open(files.get(0));
				return;
			}

			// 如果集合有多个元素，提示用户进一步选择；
			if (files.size() > 1)
			{
				for (File file : files)
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

	/** 文件加载完毕； */
	@Override
	public void onFileFindOver(ArrayList<File> list)
	{
		fileList = list;
	}

	/** 输出执行DOS命令返回的结果； */
	@Override
	public void onReadLine(String line)
	{
		System.out.println(line);
	}
}
