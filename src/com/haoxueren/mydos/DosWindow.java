package com.haoxueren.mydos;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.Scanner;

import com.haoxueren.config.ConfigHelper;
import com.haoxueren.config.Keys;
import com.haoxueren.config.Values;
import com.haoxueren.gtd.GameGtd;
import com.haoxueren.gtd.GtdHelper;
import com.haoxueren.helper.TextHelper;
import com.haoxueren.mail.MailHelper;
import com.haoxueren.word.WordHelper;

/**
 * ������ڣ�
 */
public class DosWindow
{
	// ���뷢���ʼ�е��ָ�
	private static final String SEND_MAIL = "$mail";
	/** �������ͣ� */
	private static String TASK_TYPE;

	public static void main(String[] args) throws Exception
	{
		// �����洢�ʼ���Ϣ�ļ��ϣ�
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
			// �����û�¼���ָ�
			String input = scanner.nextLine().trim();
			if (input.equalsIgnoreCase("$HELP"))
			{
				commander.printHelpInfo();
				continue;
			}
			// ִ���˳�ָ�
			if (input.equalsIgnoreCase("$EXIT"))
			{
				scanner.close();
				scanner = null;
				System.out.println("�����Ѱ�ȫ�˳���");
				break;
			}
			// ����ͨ��ָ��ϵͳ��
			if (input.equalsIgnoreCase("$COMMON"))
			{
				TASK_TYPE = "$COMMON";
				System.out.println("��ӭ����ͨ��ָ��ϵͳ��");
				continue;
			}
			// �����������ϵͳ��
			if (input.equalsIgnoreCase("$WORD"))
			{
				// ��¼��ǰ���������ͣ�
				TASK_TYPE = "$WORD";
				System.out.println("��ӭ�����������ϵͳ��");
				continue;
			}

			// ����GameGTDϵͳָ�
			if (input.matches("^(gtd|GTD){1}\\s+.+"))
			{
				GtdHelper.execute(input);
				continue;
			}

			// ��ʼ��������ʣ�
			if ("$WORD".equalsIgnoreCase(TASK_TYPE) && TextHelper.isEmpty(input))
			{
				try
				{
					File wordFile = WordHelper.getRandomWordFile();
					if (wordFile == null)
					{
						System.err.println("����Ŀ¼Ϊ�գ�");
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
			// ������������ʣ�

			// ��ʼ�������ʼ���

			if (input.equalsIgnoreCase(SEND_MAIL))
			{
				TASK_TYPE = SEND_MAIL;
				System.out.println("�������ռ��ˣ�");
				continue;
			}

			if (SEND_MAIL.equalsIgnoreCase(TASK_TYPE))
			{
				if (TextHelper.isEmpty(input))
				{
					System.out.println("��û�������κ����ݡ�");
					continue;
				}
				if (emailInfo.size() == 0)
				{
					emailInfo.put("receivers", input);
					System.out.println("�������ʼ����⣺");
					continue;
				}
				if (emailInfo.size() == 1)
				{
					emailInfo.put("subject", input);
					System.out.println("�������ʼ����ģ�");
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
						System.out.println("�ռ��ˣ�" + emailInfo.getProperty("receivers"));
						System.out.println("���⣺" + emailInfo.getProperty("subject"));
						System.out.println("���ģ�" + emailInfo.getProperty("content"));
						System.out.println("\n�ѱ���ݸ壬�Ƿ����ʼ�(Y/N)?");
					} catch (IOException e)
					{
						System.err.println(e.getMessage());
					}
					continue;
				}
				if (input.equalsIgnoreCase("Y"))
				{
					System.out.println("���ڷ����ʼ�...");
					try
					{
						MailHelper.getInstance().from("751850011@qq.com", "ynoqhtpvxmlibbbh")
								.to(emailInfo.getProperty("receivers")).subject(emailInfo.getProperty("subject"))
								.content(emailInfo.getProperty("content")).send(true);
						System.out.println("�ʼ����ͳɹ�...");
					} catch (Exception e)
					{
						e.printStackTrace();
					}
					continue;
				} else if (input.equalsIgnoreCase("N"))
				{
					System.out.println("�ʼ�δ����");
				} else
				{
					System.out.println("ָ���ȷ�����������룺");
					continue;
				}
			}

			// �����������ʼ���

			// �򿪳���������ļ���
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
			// ִ��ͨ��ָ�
			commander.runTask(input);
		}
	}
}
