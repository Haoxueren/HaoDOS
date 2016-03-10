package com.haoxueren.main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import com.haoxueren.config.ConfigHelper;
import com.haoxueren.config.Keys;
import com.haoxueren.config.Values;
import com.haoxueren.dos.MsdosHelper;
import com.haoxueren.helper.CommandHelper;
import com.haoxueren.helper.FileHelper;
import com.haoxueren.helper.FileUtils;
import com.haoxueren.helper.FileUtils.FileHelperListener;
import com.haoxueren.helper.TextHelper;
import com.haoxueren.utils.PinYinUtils;
import com.haoxueren.word.ClipBoardHelper;
import com.haoxueren.word.WordHelper;

/** 指挥官：负责执行具体的命令； */
public class Commander implements FileHelperListener
{
	private File directory;
	private String order;
	private List<File> fileList;
	private FileUtils fileHelper;
	private CommandHelper commandHelper;

	public Commander()
	{
		commandHelper = new CommandHelper();
		fileHelper = new FileUtils(this);
		directory = new File(ConfigHelper.getConfig(Keys.SHORTCUTS, Values.SHORTCUTS));
		FileHelper.mkdirs(directory);
		fileHelper.getFiles(directory);
	}

	public void printHelpInfo()
	{
		System.out.println("$exit：退出系统");
		System.out.println("$help：查看帮助信息");
		System.out.println("$word：进入随机抽取单词系统");
		System.out.println("$common：进入通用指令系统");
	}

	public void runTask(String order)
	{
		this.order = order.trim();
		startWorking(fileList);
	}

	public void startWorking(List<File> fileList)
	{
		try
		{
			commandHelper.setCommand(order);
			// 将剪贴版的单词添加到单词图解；
			if (order.equalsIgnoreCase("add copy"))
			{
				String word = ClipBoardHelper.getClipboardText();
				if (TextHelper.isEmpty(word))
				{
					System.err.println("剪切板为空！");
				} else
				{
					WordHelper.addWord(word.toLowerCase());
				}
				return;
			}

			if (order.equalsIgnoreCase("$INIT"))
			{
				fileHelper.getFiles(directory);
				return;
			}
			if (order.equalsIgnoreCase("$MOVE"))
			{
				moveLnkFileExcept("Haoxueren.lnk");
				fileHelper.getFiles(directory);
			} else if (order.matches("\\s*"))
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
				commandHelper.openUrl(order);
			} else if (commandHelper.matchAddWordCommand() || commandHelper.matchEditWordCommand())
			{
				WordHelper.addWord(commandHelper.getEnglishWord().toLowerCase());
			} else if (commandHelper.matchDosCommand())
			{
				String dos = order.replaceAll("(dos|DOS)", "").trim();
				Process process = Runtime.getRuntime().exec("cmd.exe /c " + dos);
				MsdosHelper msdosHelper = new MsdosHelper(process);
				msdosHelper.output();
				msdosHelper.input("\n");
			} else
			{
				String filename = order;
				openFileByName(fileList, filename);
			}
		} catch (Exception e)
		{
			System.out.println("异常：" + e.getMessage());
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
		System.out.println("对生活充满热情，对未来充满信心。");
	}

	// 获取到桌面的路径；
	public void moveLnkFileExcept(String entrance) throws IOException
	{
		FileSystemView fileView = FileSystemView.getFileSystemView();
		File desktop = fileView.getHomeDirectory();
		File[] files = desktop.listFiles();
		File tempDir = new File(ConfigHelper.getConfig("temp_dir", Values.TEMP_DIR));
		FileHelper.mkdirs(tempDir);
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
			// 清理桌面文件；
			File destFile = null;
			// 如果是快捷方式，移动到快捷方式文件夹；
			if (fileHelper.endWith(file.getName(), ".lnk", ".url"))
			{
				System.out.println("正在移动快捷方式：" + file.getName());
				destFile = new File(ConfigHelper.getConfig(Keys.SHORTCUTS, Values.SHORTCUTS), file.getName());
			} else
			{
				System.out.println("正在移动桌面文件：" + file.getName());
				destFile = new File(tempDir, file.getName());
			}
			if (!destFile.exists())
			{
				file.renameTo(destFile);
			} else
			{
				System.err.println("文件" + destFile.getName() + "已存在！");
			}
		}
	}
}
