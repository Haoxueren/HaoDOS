package com.haoxueren.main;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.Scanner;

import com.haoxueren.config.ConfigHelper;
import com.haoxueren.config.Keys;
import com.haoxueren.config.Values;
import com.haoxueren.dict.DictHelper;
import com.haoxueren.gtd.GameGtd;
import com.haoxueren.gtd.GtdHelper;
import com.haoxueren.helper.FileHelper;
import com.haoxueren.helper.FileUtils;
import com.haoxueren.helper.TextHelper;
import com.haoxueren.mail.MailHelper;
import com.haoxueren.word.WordHelper;

/**
 * 程序入口；
 */
public class DosWindow
{
	// 进入发送邮件械的指令；
	private static final String SEND_MAIL = "$mail";
	/** 任务类型； */
	private static String TASK_TYPE;

	public static void main(String[] args) throws Exception
	{
		// 用来存储邮件信息的集合；
		Properties emailInfo = new Properties();

		Commander commander = new Commander();
		Scanner scanner = new Scanner(System.in);
		String titleMotto = ConfigHelper.getConfig(Keys.TITLE_MOTTO, Values.TITLE_MOTTO);
		while (true)
		{
			if ("$WORD".equalsIgnoreCase(TASK_TYPE))
			{
				System.out.print("\n" + titleMotto + ">Words>\n");
			} else
			{
				System.out.print("\n" + titleMotto + ">\n");
			}
			// 接收用户录入的指令；
			String input = scanner.nextLine().trim();
			input = input.toUpperCase();
			if (input.equalsIgnoreCase("$HELP"))
			{
				commander.printHelpInfo();
				continue;
			}
			// 执行退出指令；
			if (input.equalsIgnoreCase("$EXIT"))
			{
				scanner.close();
				scanner = null;
				System.out.println("程序已安全退出！");
				break;
			}
			// 进入通用指令系统；
			if (input.equalsIgnoreCase("$COMMON"))
			{
				TASK_TYPE = "$COMMON";
				System.out.println("欢迎进入通用指令系统！");
				continue;
			}
			// 进入随机单词系统；
			if (input.equalsIgnoreCase("$WORD"))
			{
				// 记录当前的任务类型；
				TASK_TYPE = "$WORD";
				System.out.println("欢迎进入随机单词系统！");
				continue;
			}

			// 处理GameGTD系统指令；
			if (input.matches("^(gtd|GTD){1}\\s+.+"))
			{
				GtdHelper.execute(input);
				continue;
			}

			// 开始：随机单词；
			if ("$WORD".equalsIgnoreCase(TASK_TYPE) && TextHelper.isEmpty(input))
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
			// 结束：随机单词；

			// 开始：单词翻译功能；
			if (input.startsWith("DICT "))
			{
				try
				{
					String word = input.replaceFirst("DICT ", "");
					DictHelper.translate(word.trim());
				} catch (Exception e)
				{
					System.err.println(e.getMessage());
				}
				continue;
			}
			// 结束：单词翻译功能；

			// 开始：发送邮件；

			if (input.equalsIgnoreCase(SEND_MAIL))
			{
				TASK_TYPE = SEND_MAIL;
				System.out.println("请输入收件人：");
				continue;
			}

			if (SEND_MAIL.equalsIgnoreCase(TASK_TYPE))
			{
				if (TextHelper.isEmpty(input))
				{
					System.out.println("您没有输入任何内容。");
					continue;
				}
				if (emailInfo.size() == 0)
				{
					emailInfo.put("receivers", input);
					System.out.println("请输入邮件标题：");
					continue;
				}
				if (emailInfo.size() == 1)
				{
					emailInfo.put("subject", input);
					System.out.println("请输入邮件正文：");
					continue;
				}
				if (emailInfo.size() == 2)
				{
					try
					{
						emailInfo.put("content", input);
						File file = new File(System.getProperty("user.dir"), emailInfo.getProperty("subject") + "_"
								+ System.currentTimeMillis() + ".txt");
						Writer writer = new PrintWriter(file);
						emailInfo.store(writer, "Email Draft...");
						writer.close();
						System.out.println("收件人：" + emailInfo.getProperty("receivers"));
						System.out.println("标题：" + emailInfo.getProperty("subject"));
						System.out.println("正文：" + emailInfo.getProperty("content"));
						System.out.println("\n已保存草稿，是否发送邮件(Y/N)?");
					} catch (IOException e)
					{
						System.err.println(e.getMessage());
					}
					continue;
				}
				if (input.equalsIgnoreCase("Y"))
				{
					System.out.println("正在发送邮件...");
					try
					{
						MailHelper.getInstance().from("751850011@qq.com", "ynoqhtpvxmlibbbh")
								.to(emailInfo.getProperty("receivers")).subject(emailInfo.getProperty("subject"))
								.content(emailInfo.getProperty("content")).send(true);
						System.out.println("邮件发送成功...");
					} catch (Exception e)
					{
						e.printStackTrace();
					}
					continue;
				} else if (input.equalsIgnoreCase("N"))
				{
					System.out.println("邮件未发送");
				} else
				{
					System.out.println("指令不正确，请重新输入：");
					continue;
				}
			}

			// 结束：发送邮件；

			// 打开程序的配置文件；
			if ("$CONFIG".equalsIgnoreCase(input))
			{
				try
				{
					File file = new File(System.getProperty("user.dir"), "configure.properties");
					Desktop.getDesktop().open(file);
				} catch (IOException e)
				{
					System.err.println(e.getMessage());
				}
				continue;
			}
			// 打开MS-DOS窗口；
			if (input.startsWith("RUN CMD"))
			{
				Desktop.getDesktop().open(new File("C:/Windows/system32/cmd.exe"));
				continue;
			}

			// 执行通用指令；
			commander.runTask(input);
		}
	}
}
