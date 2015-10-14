package com.haoxueren.mydos;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import net.sourceforge.pinyin4j.PinyinHelper;

import com.haoxueren.helper.CommandHelper;
import com.haoxueren.helper.FileHelper;
import com.haoxueren.helper.FileHelper.FileHelperListener;
import com.haoxueren.helper.ProcessHelper;
import com.haoxueren.helper.ProcessHelper.ProcessHelperListener;

/**
 * @author Haoxueren
 */
public class MainActivity
{
	public static void main(String[] args)
	{
		// ׼��������
		Scanner scanner = new Scanner(System.in);
		ProcessHelper processHelper = listenerProcess();
		while (true)
		{
			try
			{
				System.out.print(MyConstants.HEADER);
				// ȡ���û�¼������ݣ�
				String nextLine = scanner.nextLine().trim();
				System.out.println();
				// �����ж��û��Ƿ�¼�����˳�ָ�
				if (nextLine.equals("QUIT") || nextLine.equals("EXIT"))
				{
					scanner.close();
					scanner = null;
					System.out.println("���Ѱ�ȫ�˳�");
					break;
				}
				// �����û�¼��հ��ַ��������
				if (nextLine.matches("\\s*"))
				{
					continue;
				}
				// �ٶ��������ܣ�
				CommandHelper commandHelper = new CommandHelper(nextLine);
				if (commandHelper.matchSearchCommand())
				{
					String keyWords = commandHelper.getSearchWords();
					commandHelper.search(keyWords);
					continue;
				}
				// ���ʰԷ��빦�ܣ�
				if (commandHelper.matchTranslateCommand())
				{
					String word = commandHelper.getTranslateWord();
					commandHelper.translate(word);
					continue;
				}

				// ����ַ���ܣ�
				if (commandHelper.matchUrlCommand())
				{
					commandHelper.openUrl(nextLine);
					continue;
				}

				// ���/�򿪵��ʹ��ܣ�
				if (commandHelper.matchAddWordCommand() || commandHelper.matchEditWordCommand())
				{
					WordCheck wordCheck = new WordCheck(scanner);
					wordCheck.add(commandHelper.getEnglishWord().toLowerCase());
					continue;
				}

				// ִ���û�¼���DOSָ�
				if (commandHelper.matchDosCommand())
				{
					Process process = commandHelper.executeDos();
					processHelper.readProcess(process);
					continue;
				}

				// �����ļ����򿪶�Ӧ���ļ���
				findFileByName(nextLine);
			} catch (Exception e)
			{
				System.out.println(e.getMessage());
				System.out.println();
			}
		}
	}

	/** ��ʼ���̼߳������� */
	private static ProcessHelper listenerProcess()
	{
		/* ����DOS����ִ�н���� */
		ProcessHelperListener processListener = new ProcessHelperListener()
		{
			@Override
			public void onReadLine(String line)
			{
				System.out.println(line);
			}
		};
		ProcessHelper processHelper = new ProcessHelper(processListener);
		return processHelper;
	}

	/** �����ļ������ļ��� */
	private static void findFileByName(final String filename)
	{
		FileHelper fileHelper = new FileHelper(new FileHelperListener()
		{
			@Override
			public void onFileFindOver(ArrayList<File> fileList)
			{
				try
				{
					// �������Ϊ�գ������ùؼ��ʣ�
					if (fileList.isEmpty())
					{
						// �滻�û�ָ���еĿհ��ַ���
						String keyWords = filename.replaceAll("\\s+", "%20");
						new CommandHelper().search(keyWords);
						return;
					}

					// �������ֻ��һ��Ԫ�أ�ֱ�Ӵ򿪸��ļ���
					if (fileList.size() == 1)
					{
						Desktop.getDesktop().open(fileList.get(0));
						return;
					}

					// ��������ж��Ԫ�أ���ʾ�û���һ��ѡ��
					if (fileList.size() > 1)
					{
						for (File file : fileList)
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
		});
		File directory = new File(MyConstants.PATH_SHORTCUTS);
		fileHelper.findFileByName(directory, filename);
	}

}
