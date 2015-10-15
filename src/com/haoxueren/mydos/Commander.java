package com.haoxueren.mydos;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.haoxueren.helper.CommandHelper;
import com.haoxueren.helper.FileHelper;
import com.haoxueren.helper.FileHelper.FileHelperListener;
import com.haoxueren.helper.ProcessHelper;
import com.haoxueren.helper.ProcessHelper.ProcessHelperListener;
import com.haoxueren.utils.PinYinUtils;

/** ��������ʱ�����߼��ࣻ */
public class Commander implements FileHelperListener, ProcessHelperListener
{
	private String command;
	private List<File> fileList;
	private ProcessHelper processHelper;
	private CommandHelper commandHelper;

	// ��ʼ�����ݣ�
	public Commander()
	{
		processHelper = new ProcessHelper(this);
		commandHelper = new CommandHelper();
		FileHelper fileHelper = new FileHelper(this);
		File directory = new File(MyConstants.PATH_SHORTCUTS);
		fileHelper.getFiles(directory);
	}

	/**
	 * ���ô˷�����������
	 * 
	 * @return �����Ƿ������
	 */
	public boolean runTask(String command)
	{
		this.command = command;
		return startWorking(fileList);
	}

	/**
	 * ���������У�ִ���û�ָ�
	 * 
	 * @return �Ƿ��˳�����
	 */
	public boolean startWorking(List<File> fileList)
	{
		try
		{
			commandHelper.setCommand(command);
			// �����ж��û��Ƿ�¼�����˳�ָ�
			if (command.equals("QUIT") || command.equals("EXIT"))
			{
				return true;
			}
			// �����û�¼��հ��ַ��������
			else if (command.matches("\\s*"))
			{
			}
			// �ٶ��������ܣ�
			else if (commandHelper.matchSearchCommand())
			{
				String keyWords = commandHelper.getSearchWords();
				commandHelper.search(keyWords);
			}
			// ���ʰԷ��빦�ܣ�
			else if (commandHelper.matchTranslateCommand())
			{
				String word = commandHelper.getTranslateWord();
				commandHelper.translate(word);
			}
			// ����ַ���ܣ�
			else if (commandHelper.matchUrlCommand())
			{
				commandHelper.openUrl(command);
			}
			// ���/�򿪵��ʹ��ܣ�
			else if (commandHelper.matchAddWordCommand() || commandHelper.matchEditWordCommand())
			{
				WordCheck wordCheck = new WordCheck();
				wordCheck.add(commandHelper.getEnglishWord().toLowerCase());
			}
			// ִ���û�¼���DOSָ�
			else if (commandHelper.matchDosCommand())
			{
				Process process = commandHelper.executeDos();
				processHelper.readProcess(process);
			} else
			{
				// �����ļ����򿪶�Ӧ���ļ���
				String filename = command;
				openFileByName(fileList, filename);
			}
			return false;
		} catch (Exception e)
		{
			return true;
		}
	}

	/** ɸѡ�������������ļ��� */
	public List<File> filterFile(List<File> fileList, String filename)
	{
		// ������������������ļ��ļ��ϣ�
		List<File> filterLlist = new ArrayList<>();
		for (File file : fileList)
		{
			String name = file.getName().toUpperCase();
			filename = filename.toUpperCase();
			String regex_command = "[A-Z]+";
			// ����û�¼������ļ�����д��
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

	/** �����ļ������ļ��� */
	private void openFileByName(List<File> fileList, String filename)
	{
		try
		{
			// ɸѡ�������������ļ���
			List<File> files = filterFile(fileList, filename);
			// �������Ϊ�գ������ùؼ��ʣ�
			if (files.isEmpty())
			{
				// �滻�û�ָ���еĿհ��ַ���
				String keyWords = filename.replaceAll("\\s+", "%20");
				new CommandHelper().search(keyWords);
				return;
			}

			// �������ֻ��һ��Ԫ�أ�ֱ�Ӵ򿪸��ļ���
			if (files.size() == 1)
			{
				Desktop.getDesktop().open(files.get(0));
				return;
			}

			// ��������ж��Ԫ�أ���ʾ�û���һ��ѡ��
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

	/** �ļ�������ϣ� */
	@Override
	public void onFileFindOver(ArrayList<File> list)
	{
		fileList = list;
	}

	/** ���ִ��DOS����صĽ���� */
	@Override
	public void onReadLine(String line)
	{
		System.out.println(line);
	}
}
