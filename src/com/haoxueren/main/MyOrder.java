package com.haoxueren.main;

import java.awt.Desktop;
import java.awt.TextArea;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import com.haoxueren.config.ConfigHelper;
import com.haoxueren.config.Keys;
import com.haoxueren.config.Values;
import com.haoxueren.dict.DictHelper;
import com.haoxueren.dict.FanyiHelper;
import com.haoxueren.gtd.GtdHelper;
import com.haoxueren.helper.DateHelper;
import com.haoxueren.helper.FileManager;
import com.haoxueren.helper.MsdosHelper;
import com.haoxueren.test.LetouLuckDraw;
import com.haoxueren.utils.TextHelper;
import com.haoxueren.word.WordHelper;

public class MyOrder implements OutputListener
{
	private TextArea textArea;
	private static MyOrder order;

	private MyOrder(TextArea textArea)
	{
		this.textArea = textArea;
	}

	public static MyOrder getInstance(TextArea textArea)
	{
		if (order == null)
		{
			return new MyOrder(textArea);
		}
		return order;
	}

	/** 初始化一些信息； */
	public void init()
	{
		// 添加我的格言；
		textArea.setText("对生活充满热情，对未来充满信心。\n");
		textArea.append(Values.DIVIDER);
		// 获取并输出当前的日期；
		DateHelper.printDate(this);
		textArea.append(Values.DIVIDER + "$");
		textArea.setCaretPosition(textArea.getText().length());
	}

	/** 执行用户的指令； */
	public void execute(String input)
	{
		try
		{
			// 打开对应的文件；
			if (input.matches("\\$(open|OPEN)\\s+.+"))
			{
				String fileName = input.replaceFirst("\\$(open|OPEN)", "").trim();
				textArea.append("打开文件：" + fileName + "\n");
				String directory = ConfigHelper.getConfig(Keys.SHORTCUTS, Values.SHORTCUTS);
				new FileManager().openFile(directory, "JSCB");
				return;
			}

			// 处理GameGTD系统指令；
			if (input.matches("^\\$(gtd|GTD)\\s+.+"))
			{
				GtdHelper.getInstance(this).execute(input);
				return;
			}

			// 乐投天下抽奖程序；
			if (input.matches("\\$(LETOU|letou)\\s+(lucky draw|LUCKY DRAW)"))
			{
				LetouLuckDraw.luckyDraw(this);
				return;
			}
			// 添加单词到词库；
			if (input.matches("\\$(ADD|add)\\s+.+"))
			{
				String word = input.replaceFirst("\\$(ADD|add)\\s+", "");
				WordHelper.getInstance(this).addWord(word.toLowerCase());
				return;
			}
			// 执行查询单词指令；
			if (input.matches("\\$(DICT|dict)\\s+.+"))
			{
				String word = input.replaceFirst("\\$(DICT|dict)\\s+", "");
				new DictHelper(this).translate(word);
				return;
			}

			// 在线翻译[英译汉]
			if (input.matches("\\$(FANYI|fanyi)\\s+[\\s\\S]+"))
			{
				String content = input.replaceFirst("\\$(FANYI|fanyi)\\s+", "");
				FanyiHelper.getInstance(this).icibaFanyi(content);
				return;
			}
			// 打开网址；
			if (input.matches("\\$(http://|HTTP://|www\\.|WWW\\.).+"))
			{
				Runtime.getRuntime().exec("cmd /c start " + input.replaceFirst("\\$\\s?", ""));
				return;
			}
			// 使用百度搜索关键词；
			if (input.matches("\\$(search|SEARCH)\\s+.+"))
			{
				String keyWords = input.replaceFirst("\\$(search|SEARCH)\\s+", "").trim();
				String encode = URLEncoder.encode(keyWords, "UTF-8");
				Runtime.getRuntime().exec("cmd /c start http://www.baidu.com/s?wd=" + encode);
				textArea.append("正在搜索" + keyWords + "\n");
				return;
			}

			// 打开MS-DOS窗口；
			if (input.matches("\\$(run|RUN)\\s+(cmd|CMD)\\s?"))
			{
				Desktop.getDesktop().open(new File("C:/Windows/system32/cmd.exe"));
				textArea.append("MS-DOS已打开！\n");
				return;
			}
			// 执行DOS命令；
			if (input.matches("\\$(dos|DOS)\\s+.+"))
			{
				String dos = input.replaceAll("\\$(dos|DOS)", "").trim();
				Process process = Runtime.getRuntime().exec("cmd.exe /c " + dos);
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
			if (input.matches("\\$(CLS|cls)\\s?"))
			{
				textArea.setText("$\n");
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
				textArea.append("异常：NullPointException");
			}
			e.printStackTrace();

		}
	}

	@Override
	public void output(String text)
	{
		textArea.append(text + "\n");
	}

}
