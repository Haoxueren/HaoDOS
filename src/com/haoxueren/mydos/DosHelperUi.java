package com.haoxueren.mydos;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.haoxueren.word.WordHelper;

/**
 * ������ڣ�
 */
public class DosHelperUi
{
	/** �������ͣ� */
	private static String TASK_TYPE;

	public static void main(String[] args)
	{
		Commander commander = new Commander();
		Scanner scanner = new Scanner(System.in);
		while (true)
		{
			System.out.print("\n" + MyConstants.HEADER + ">\n");
			// �����û�¼���ָ�
			String command = scanner.nextLine().trim();
			// ִ���˳�ָ�
			if (command.equalsIgnoreCase("$EXIT"))
			{
				scanner.close();
				scanner = null;
				System.out.println("�����������˳���");
				break;
			}
			// ��¼��ǰ���������ͣ�
			if (command.equalsIgnoreCase("$WORD"))
			{
				TASK_TYPE = "$WORD";
				System.out.println("��ӭ�����������ϵͳ��");
				continue;
			}
			if (command.equalsIgnoreCase("$COMMON"))
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
					File wordFile = WordHelper.getRandomWordPath();
					Desktop.getDesktop().open(wordFile);
					WordHelper.getWordName(wordFile);
				} catch (IOException e)
				{
					System.out.println(e.getMessage());
				}
				continue;
			}
			// ִ��ͨ��ָ�
			commander.runTask(command);
		}
	}

}
