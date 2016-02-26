package com.haoxueren.mydos;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import com.haoxueren.config.ConfigHelper;
import com.haoxueren.config.Keys;
import com.haoxueren.config.Values;
import com.haoxueren.helper.CommandHelper;
import com.haoxueren.helper.FileHelper;
import com.haoxueren.helper.FileUtils;
import com.haoxueren.helper.FileUtils.FileHelperListener;
import com.haoxueren.helper.ProcessHelper;
import com.haoxueren.helper.ProcessHelper.ProcessHelperListener;
import com.haoxueren.utils.PinYinUtils;

/** 指挥官：负责执行具体的命令； */
public class Commander implements FileHelperListener, ProcessHelperListener
{
	private File directory;
	private String command;
	private List<File> fileList;
	private FileUtils fileHelper;
	private ProcessHelper processHelper;
	private CommandHelper commandHelper;

	public Commander()
	{
		processHelper = new ProcessHelper(this);
		commandHelper = new CommandHelper();
		fileHelper = new FileUtils(this);
		directory = new File(ConfigHelper.getConfig(Keys.SHORTCUTS, Values.SHORTCUTS));
		FileHelper.MakeDirectory(directory);
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
			if (command.equalsIgnoreCase("$INIT"))
			{
				// 初始化数据；
				fileHelper.getFiles(directory);
			} else if (command.equalsIgnoreCase("$EXIT"))
			{
				// 退出程序；
				return true;
			} else if (command.equalsIgnoreCase("$MOVE"))
			{
				// 整理桌面文件；
				moveLnkFile("Haoxueren.lnk");
				fileHelper.getFiles(directory);
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

	/** 根据文件名打开文件； */
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
		System.out.println("数据初始化成功...");
		fileList = list;
	}

	/** 读取执行DOS命令所返回的信息； */
	@Override
	public void onReadLine(String line)
	{
		System.out.println(line);
	}

	// 获取到桌面的路径；
	public void moveLnkFile(String entrance) throws IOException
	{
		FileSystemView fileView = FileSystemView.getFileSystemView();
		File desktop = fileView.getHomeDirectory();
		File[] files = desktop.listFiles();
		File tempDir = new File(Values.TEMP_DIR);
		FileHelper.MakeDirectory(tempDir);
		String shortcuts = ConfigHelper.getConfig(Keys.SHORTCUTS, Values.SHORTCUTS);
		File shortcutsDir = new File(shortcuts);
		shortcutsDir.mkdirs();
		for (File file : files)
		{
			// 如果不是桌面上的文件，返回；
			if (!file.getAbsolutePath().startsWith(desktop.getAbsolutePath())
					&& !file.getAbsolutePath().startsWith("C:\\Users\\Public\\Desktop"))
			{
				continue;
			}
			// 保留程序入口文件；
			if (file.getName().equalsIgnoreCase(entrance))
			{
				continue;
			}
			// 如果是快捷方式，移动到快捷方式文件夹；
			if (fileHelper.endWith(file.getName(), ".lnk", ".url"))
			{
				System.out.println("正在移动快捷方式：" + file.getName());
				File dest = new File(tempDir, file.getName());
				file.renameTo(dest);
			} else
			{
				// 其它情况，移动到桌面文件目录；
				System.out.println("正在移动桌面文件：" + file.getName());
				File destFile = new File(tempDir, file.getName());
				file.renameTo(destFile);
			}
		}
	}
}
