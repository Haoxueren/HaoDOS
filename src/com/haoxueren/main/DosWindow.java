package com.haoxueren.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.Scanner;

import com.haoxueren.mail.MailHelper;
import com.haoxueren.utils.TextHelper;

/**
 * 程序入口；
 */
public class DosWindow
{
	/** 任务类型； */
	private static String TASK_TYPE;
	// 进入发送邮件械的指令；
	private static final String SEND_MAIL = "$mail";

	public static void main(String[] args) throws Exception
	{
		// 用来存储邮件信息的集合；
		Properties emailInfo = new Properties();
		Commander commander = new Commander();
		Scanner scanner = new Scanner(System.in);
		while (true)
		{
			// 接收用户录入的指令；
			String input = scanner.nextLine().trim();
			input = input.toUpperCase();
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
			// 执行通用指令；
			commander.runTask(input);
		}
	}
}
