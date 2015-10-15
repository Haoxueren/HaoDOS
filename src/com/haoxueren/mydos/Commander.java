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

public class Commander implements FileHelperListener, ProcessHelperListener
{
	private String command;
	private List<File> fileList;
	private ProcessHelper processHelper;
	private CommandHelper commandHelper;

	public Commander()
	{
		processHelper = new ProcessHelper(this);
		commandHelper = new CommandHelper();
		FileHelper fileHelper = new FileHelper(this);
		File directory = new File(MyConstants.PATH_SHORTCUTS);
		fileHelper.getFiles(directory);
	}

	public boolean runTask(String command)
	{
		this.command = command;
		return startWorking(fileList);
	}

	public boolean startWorking(List<File> fileList)
	{
		try
		{
			commandHelper.setCommand(command);

			if (command.equals("QUIT") || command.equals("EXIT"))
			{
				return true;
			} else if (command.matches("\\s*"))
			{
			} else if (commandHelper.matchSearchCommand())
			{
				String keyWords = commandHelper.getSearchWords();
				commandHelper.search(keyWords);
			} else if (commandHelper.matchTranslateCommand())
			{
				String word = commandHelper.getTranslateWord();
				commandHelper.translate(word);
			} else if (commandHelper.matchUrlCommand())
			{
				commandHelper.openUrl(command);
			} else if (commandHelper.matchAddWordCommand() || commandHelper.matchEditWordCommand())
			{
				WordCheck wordCheck = new WordCheck();
				wordCheck.add(commandHelper.getEnglishWord().toLowerCase());
			} else if (commandHelper.matchDosCommand())
			{
				Process process = commandHelper.executeDos();
				processHelper.readProcess(process);
			} else
			{
				String filename = command;
				openFileByName(fileList, filename);
			}
			return false;
		} catch (Exception e)
		{
			return true;
		}
	}

	public List<File> filterFile(List<File> fileList, String filename)
	{
		List<File> filterLlist = new ArrayList<>();
		for (File file : fileList)
		{
			String name = file.getName().toUpperCase();
			filename = filename.toUpperCase();
			String regex_command = "[A-Z]+";

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

	private void openFileByName(List<File> fileList, String filename)
	{
		try
		{
			List<File> files = filterFile(fileList, filename);
			if (files.isEmpty())
			{

				String keyWords = filename.replaceAll("\\s+", "%20");
				new CommandHelper().search(keyWords);
				return;
			}

			if (files.size() == 1)
			{
				Desktop.getDesktop().open(files.get(0));
				return;
			}

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

	@Override
	public void onFileFindOver(ArrayList<File> list)
	{
		fileList = list;
	}

	@Override
	public void onReadLine(String line)
	{
		System.out.println(line);
	}
}
