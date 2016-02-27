package com.haoxueren.mydos;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.haoxueren.config.Keys;
import com.haoxueren.config.Values;
import com.haoxueren.config.ConfigHelper;
import com.haoxueren.word.WordHelper;

/**
 * ������ڣ�
 */
public class DosWindow
{
	/** �������ͣ� */
	private static String TASK_TYPE;

	public static void main(String[] args)
	{
		Commander commander = new Commander();
		Scanner scanner = new Scanner(System.in);
		String titleMotto = ConfigHelper.getConfig(Keys.TITLE_MOTTO, Values.TITLE_MOTTO);
		while (true)
		{
			System.out.print("\n" + titleMotto + ">\n");
			// �����û�¼���ָ�
			String order = scanner.nextLine().trim();
			if (order.equalsIgnoreCase("$HELP"))
			{
				commander.printHelpInfo();
				continue;
			}
			// ִ���˳�ָ�
			if (order.equalsIgnoreCase("$EXIT"))
			{
				scanner.close();
				scanner = null;
				System.out.println("�����Ѱ�ȫ�˳���");
				break;
			}
			// ��¼��ǰ���������ͣ�
			if (order.equalsIgnoreCase("$WORD"))
			{
				TASK_TYPE = "$WORD";
				System.out.println("��ӭ�����������ϵͳ��");
				continue;
			}
			if (order.equalsIgnoreCase("$COMMON"))
			{
				TASK_TYPE = "$COMMON";
				System.out.println("��ӭ����ͨ��ָ��ϵͳ��");
				continue;
			}
			// ִ���������ָ�
			if ("$WORD".equalsIgnoreCase(TASK_TYPE))
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
			// ִ��ͨ��ָ�
			commander.runTask(order);
		}
	}

}
