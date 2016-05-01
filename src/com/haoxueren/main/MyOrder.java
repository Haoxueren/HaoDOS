package com.haoxueren.main;

import java.awt.Desktop;
import java.awt.TextArea;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import com.haoxueren.autocode.CodeFactoryService;
import com.haoxueren.config.ConfigHelper;
import com.haoxueren.config.Keys;
import com.haoxueren.config.Values;
import com.haoxueren.dict.DictHelper;
import com.haoxueren.dict.FanyiHelper;
import com.haoxueren.file.FileList;
import com.haoxueren.file.FileConfig;
import com.haoxueren.file.FileManager;
import com.haoxueren.file.FileGroup;
import com.haoxueren.file.FileTree;
import com.haoxueren.gtd.GtdHelper;
import com.haoxueren.helper.DateHelper;
import com.haoxueren.helper.DateWatcher;
import com.haoxueren.helper.DesktopHelper;
import com.haoxueren.helper.Md5Helper;
import com.haoxueren.helper.MsdosHelper;
import com.haoxueren.letou.Letou360;
import com.haoxueren.letou.LetouLuckDraw;
import com.haoxueren.qq.QQHelper;
import com.haoxueren.tinypng.TinyPng;
import com.haoxueren.utils.TextHelper;
import com.haoxueren.word.WordHelper;

public class MyOrder implements OutputListener
{
	/** 目录结构树对象； */
	private FileTree fileTree;
	private FileList fileList;
	private TextArea textArea;
	private static MyOrder order;
	/** 默认的命令前缀； */
	public static String prefix = "";

	private MyOrder(TextArea textArea)
	{
		this.textArea = textArea;
	}

	/** 使用单例模式； */
	public static MyOrder getInstance(TextArea textArea)
	{
		if (order == null)
		{
			order = new MyOrder(textArea);
		}
		return order;
	}

	/** 初始化一些信息； */
	public void init() throws Exception
	{
		// 添加我的格言；
		textArea.setText("对生活充满热情，对未来充满信心。\n");
		textArea.append(Values.DIVIDER);
		// 获取并输出当前的日期；
		DateHelper.printDate(this);
		textArea.append(Values.DIVIDER + "~$");
		textArea.setCaretPosition(textArea.getText().length());
		fileList = new FileList(this);
	}

	/** 执行用户的指令； */
	public void execute(String input)
	{
		try
		{
			// 对用户录入的命令进行转换；
			int space = input.indexOf(' ');
			if (space > 1)
			{
				String key = input.substring(1, space);
				String value = ConfigHelper.convert(key.toLowerCase());
				input = input.replaceFirst(key, value);
			} else
			{
				String key = input.substring(1);
				String value = ConfigHelper.convert(key.toLowerCase());
				input = input.replaceFirst(key, value);
			}
			System.out.println(input);

			/** 启动AutoCode窗体； */
			if (input.matches("\\$\\s*(auto code|AUTO CODE)\\s*"))
			{
				new CodeFactoryService().service();
				return;
			}

			// 按文件名正则从指定的目录中递归搜索文件；
			if (input.matches("\\$\\s*(find|FIND)\\s+\\S+\\s+(from|FROM)\\s+\\S+\\s*"))
			{
				FileFinder.getInstance(this).reset();
				String fileInfo = input.replaceFirst("\\$\\s*(find|FIND)", "").trim();
				String[] infoArray = fileInfo.split("\\s+(from|FROM)\\s+");
				String fileRegex = infoArray[0];
				if (!fileRegex.equals("*"))
				{
					String fullPath = FileConfig.getPath(infoArray[1]);
					File directory = new File(fullPath);
					fileRegex = fileRegex.replaceAll("\\*", "\\\\S*");
					FileFinder.getInstance(this).searchFile(directory, fileRegex);
				} else
				{
					textArea.append("请准确描述您的检索条件\n");
				}
				return;
			}
			if (input.matches("\\$\\s*(find|FIND)\\s+(open|OPEN)\\s+\\d+\\s*"))
			{
				String id = input.replaceFirst("\\$\\s*(find|FIND)\\s+(open|OPEN)", "").trim();
				int idInt = Integer.parseInt(id);
				FileFinder.getInstance(this).openFile(idInt);
				return;
			}

			// 分类整理桌面上的文件；
			if (input.matches("\\$\\s*(group|GROUP)\\s+.+"))
			{
				String path = input.replaceFirst("\\$\\s*(group|GROUP)", "").trim();
				FileGroup fileGroup = new FileGroup(this);
				String[] suffixs = FileConfig.getSuffix();
				File dir = new File(FileConfig.getPath(path));
				fileGroup.group(dir, suffixs);
				return;
			}

			// 获取md5加密后的密码；
			if (input.matches("\\$\\s*(password|PASSWORD)\\s+.+"))
			{
				String text = input.replaceFirst("\\$\\s*(password|PASSWORD)", "");
				String password = Md5Helper.encode(text.trim().getBytes());
				textArea.append("您的密码为：" + password + "\n");
				return;
			}

			// 显示当前目录下所有文件(单级)；
			if (input.matches("\\$\\s*(list|LIST)\\s+(dir|DIR)\\s+.+"))
			{
				String path = input.replaceFirst("\\$\\s*(list|LIST)\\s+(dir|DIR)", "").trim();
				fileList.enterPath(FileConfig.getPath(path));
				return;
			}

			// 显示文件夹下的所有文件；
			if (input.matches("\\$\\s*(list|LIST)\\s+(id|ID)\\s+\\d+"))
			{
				String dir = input.replaceFirst("\\$\\s*(list|LIST)\\s+(id|ID)", "").trim();
				int id = Integer.parseInt(dir);
				fileList.listDir(id);
				return;
			}

			// 根据编码打开文件(list open ..)；
			if (input.matches("\\$\\s*(list|LIST)\\s+(open|OPEN)\\s+\\d+"))
			{
				String index = input.replaceFirst("\\$\\s*(list|LIST)\\s+(open|OPEN)", "").trim();
				int id = Integer.parseInt(index);
				fileList.openFile(id);
				textArea.append("已为您打开编号" + id + "文件\n");
				return;
			}

			// 显示当前文件夹的目录结构图；
			if (input.matches("\\$\\s*(tree|TREE)\\s+(dir|DIR)\\s+.+"))
			{
				String path = input.replaceFirst("\\$\\s*(tree|TREE)\\s+(dir|DIR)", "").trim();
				String fullPath = FileConfig.getPath(path.toUpperCase());
				path = fullPath == null ? path : fullPath;
				File dir = new File(path);
				fileTree = new FileTree(dir, this);
				fileTree.tree(dir, false);
				return;
			}

			// 显示当前文件夹的目录及文件结构图；
			if (input.matches("\\$\\s*(tree|TREE)\\s+(file|FILE)\\s+.+"))
			{
				String path = input.replaceFirst("\\$\\s*(tree|TREE)\\s+(file|FILE)", "").trim();
				String fullPath = FileConfig.getPath(path.toUpperCase());
				path = fullPath == null ? path : fullPath;
				File dir = new File(path);
				fileTree = new FileTree(dir, this);
				fileTree.tree(dir, true);
				return;
			}
			// 遍历目录结构树中的子节点，结果只显示目录；
			if (input.matches("\\$\\s*(tree dirid|TREE DIRID)\\s+.+"))
			{
				String index = input.replaceFirst("\\$\\s*(tree dirid|TREE DIRID)", "").trim();
				int id = Integer.parseInt(index);
				fileTree.treeDirId(id);
				return;
			}
			// 遍历目录结构树中的子节点，结果显示目录及文件；
			if (input.matches("\\$\\s*(tree fileid|TREE FILEID)\\s+.+"))
			{
				String index = input.replaceFirst("\\$\\s*(tree fileid|TREE FILEID)", "").trim();
				int id = Integer.parseInt(index);
				fileTree.treeFileId(id);
				return;
			}

			// 按编号打开目录结构树中的文件；
			if (input.matches("\\$\\s*(tree|TREE)\\s+(open|OPEN)\\s+\\d+"))
			{
				String index = input.replaceFirst("\\$\\s*(tree|TREE)\\s+(open|OPEN)", "").trim();
				int indexInt = Integer.parseInt(index);
				fileTree.openFile(indexInt);
				return;
			}
			// 为命令行添加前缀功能；
			if (input.matches("\\$\\s*(set|SET)\\s+(prefix|PREFIX)\\s+.+"))
			{
				String prefix = input.replaceFirst("\\$\\s*(set|SET)\\s+(prefix|PREFIX)", "").trim();
				if (prefix.equalsIgnoreCase("null"))
				{
					// 清除命令前缀；
					MyOrder.prefix = "";
					textArea.append("已清除命令前缀\n");
					return;
				}
				// 设置命令前缀；
				MyOrder.prefix = prefix + " ";
				textArea.append("已设置命令前缀：" + prefix + "\n");
				return;
			}
			// 执行随机单词命令；
			if (input.matches("\\$\\s*(random|RANDOM)\\s+(word|WORD)\\s?"))
			{
				File wordFile = WordHelper.getInstance(this).getRandomWordFile();
				if (wordFile == null)
				{
					textArea.append("单词目录为空！\n");
					return;
				}
				// 打开单词图解；
				Desktop.getDesktop().open(wordFile);
				WordHelper.getWordName(wordFile);
				return;
			}

			// 打开对应的文件；
			if (input.matches("\\$\\s*(open|OPEN)\\s+.+"))
			{
				String fileName = input.replaceFirst("\\$\\s*(open|OPEN)", "").trim();
				// 打开程序所在的目录；
				if (fileName.equalsIgnoreCase("userdir"))
				{
					DesktopHelper.openFile(System.getProperty("user.dir"));
					return;
				}
				// 打开配置文件；
				if (fileName.equalsIgnoreCase("config"))
				{
					DesktopHelper.openFile(System.getProperty("user.dir") + "/config");
					return;
				}
				// 打开SHORTCUTS中的文件；
				openShortcuts(fileName);
				return;
			}

			// 使用TinyPng压缩图片；
			if (input.matches("\\$\\s*(tinypng|TINYPNG)\\s+.+"))
			{
				String pathname = input.replaceFirst("\\$\\s*(tinypng|TINYPNG)", "").trim();
				/** 移除LEFT-TO-RIGHT EMBEDDING(Win8.1)； */
				pathname = pathname.replaceAll("\\u202A", "");
				File sourceFile = new File(pathname);
				TinyPng tinyPng = new TinyPng(this);
				if (sourceFile.isFile())
				{
					tinyPng.shrinkImage(sourceFile);
				} else if (sourceFile.isDirectory())
				{
					tinyPng.sharkImages(sourceFile);
				} else
				{
					textArea.append("无法识别的文件类型：" + sourceFile.getAbsolutePath() + "\n");
				}
				return;
			}

			// 打开QQ聊天窗口；
			if (input.matches("\\$\\s*(qq|QQ)\\s+.+"))
			{
				String nickname = input.replaceFirst("\\$\\s*(qq|QQ)", "").trim().toUpperCase();
				String qq = nickname.matches("\\d+") ? nickname : QQHelper.getQQNo(nickname);
				textArea.append("正在打开QQ(" + qq + ")聊天窗口\n");
				QQHelper.openQQ(qq);
				return;
			}

			// 处理GameGTD系统指令；
			if (input.matches("^\\$(gtd|GTD)\\s+.+"))
			{
				GtdHelper.getInstance(this).execute(input);
				return;
			}

			// 乐投天下抽奖程序；
			if (input.matches("\\$\\s*(LETOU|letou)\\s+(lucky draw|LUCKY DRAW)"))
			{
				String username = ConfigHelper.getConfig("letou_username", null);
				String password = ConfigHelper.getConfig("letou_password", null);
				new Letou360(this).luckyDraw(username, password);
				return;
			}
			// 添加单词到词库；
			if (input.matches("\\$\\s*(ADD|add)\\s+(word|WORD)\\s+\\w+"))
			{
				String word = input.replaceFirst("\\$\\s*(ADD|add)\\s+(word|WORD)", "");
				WordHelper.getInstance(this).addWord(word.trim().toLowerCase());
				return;
			}
			// 执行查询单词指令；
			if (input.matches("\\$\\s*(DICT|dict)\\s+.+"))
			{
				String word = input.replaceFirst("\\$\\s*(DICT|dict)\\s+", "");
				new DictHelper(this).translate(word);
				return;
			}

			// 在线翻译[英译汉]
			if (input.matches("\\$\\s*(FANYI|fanyi)\\s+[\\s\\S]+"))
			{
				String content = input.replaceFirst("\\$\\s*(FANYI|fanyi)\\s+", "");
				FanyiHelper.getInstance(this).icibaFanyi(content);
				return;
			}
			// 打开网址；
			if (input.matches("\\$\\s*(http://|HTTP://|www\\.|WWW\\.).+"))
			{
				String url = input.replaceFirst("\\$\\s?", "");
				Runtime.getRuntime().exec("cmd /c start " + url);
				textArea.append("正在打开网址：" + url + "\n");
				return;
			}
			// 使用百度搜索关键词；
			if (input.matches("\\$\\s*(search|SEARCH)\\s+.+"))
			{
				String keyWords = input.replaceFirst("\\$\\s*(search|SEARCH)\\s+", "").trim();
				String encode = URLEncoder.encode(keyWords, "UTF-8");
				Runtime.getRuntime().exec("cmd /c start http://www.baidu.com/s?wd=" + encode);
				textArea.append("正在搜索：" + keyWords + "\n");
				return;
			}

			// 打开MS-DOS窗口；
			if (input.matches("\\$\\s*(run|RUN)\\s+(cmd|CMD)\\s?"))
			{
				Desktop.getDesktop().open(new File("C:/Windows/system32/cmd.exe"));
				textArea.append("MS-DOS已打开！\n");
				return;
			}
			// 执行DOS命令；
			if (input.matches("\\$\\s*(dos|DOS)\\s+.+"))
			{
				String dos = input.replaceAll("\\$\\s*(dos|DOS)", "").trim();
				Process process = Runtime.getRuntime().exec("cmd.exe /c " + dos);
				textArea.append("正在执行：" + dos + "\n");
				MsdosHelper msdosHelper = new MsdosHelper(process, this);
				msdosHelper.output();
				msdosHelper.input("\n");
				return;
			}

			// 打开程序的配置文件；
			if ("$CONFIG".equalsIgnoreCase(input))
			{
				File file = new File(System.getProperty("user.dir"), "configure.properties");
				Desktop.getDesktop().open(file);
				return;
			}

			// 空命令，输出一句格言；
			if (input.matches("\\$\\s*"))
			{
				textArea.append("对生活充满热情，对未来充满信心！\n");
				return;
			}

			// 清除屏幕；
			if (input.matches("\\$\\s*(CLEAR|clear)\\s?"))
			{
				textArea.setText("");
				return;
			}
			// 保存数据并退出；
			if (input.equalsIgnoreCase("$EXIT"))
			{
				System.exit(0);
				return;
			}
			textArea.append("找不到{" + input + "}指令！\n");
		}
		// 输出异常信息；
		catch (Exception e)
		{
			if (TextHelper.notEmpty(e.getMessage()))
			{
				textArea.append("异常：" + e.getMessage() + "\n");
			} else
			{
				textArea.append("异常：NullPointException\n");
			}
			e.printStackTrace();

		}
	}

	@Override
	public void output(String text)
	{
		textArea.append(text + "\n");
	}

	/*********************** 【以下是封装方法区】 ***********************/

	/** 打开快捷方式中的文件或快捷方式； */
	private void openShortcuts(String fileName) throws Exception
	{
		String directory = ConfigHelper.getConfig(Keys.SHORTCUTS, Values.SHORTCUTS);
		FileManager fileManager = new FileManager();
		fileManager.fillFileList(directory, fileName);
		List<File> fileList = fileManager.getFileList();
		switch (fileList.size())
		{
		// 未找到文件；
		case 0:
			textArea.append("未找到文件：" + fileName + "\n");
			break;
		// 直接打开文件；
		case 1:
			Desktop.getDesktop().open(fileList.get(0));
			textArea.append("打开文件：" + fileList.get(0).getName() + "\n");
			break;
		// 多个文件，提示用户重新输入；
		default:
			for (int i = 0; i < fileList.size(); i++)
			{
				// 对文件进行重命名后输出；
				File file = fileList.get(i);
				textArea.append((i + 1) + "、" + file.getName() + "\n");
			}
			break;
		}
	}

}
