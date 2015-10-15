package com.haoxueren.helper;

import java.io.IOException;

import org.junit.Test;

/**
 * 2015��10��9��20:40:21<br>
 * ����ִ��ָ��İ����ࣻ<br>
 */
public class CommandHelper
{
	private String command;

	public CommandHelper()
	{
	}

	public CommandHelper(String command)
	{
		this.command = command;
	}

	public void setCommand(String command)
	{
		this.command = command;
	}

	/**
	 * ����û������ָ���Ƿ�Ϊ����ָ�
	 * 
	 * @param command
	 *            ��ʽ��{S/s 1�λ���}{�հ��ַ� 1�λ���}{�����ַ�1�λ���}
	 */
	public boolean matchSearchCommand()
	{
		String regexSearchCommand = "[S|s]+\\s+.+";
		return command.matches(regexSearchCommand);
	}

	/** ����û�¼���ָ���Ƿ�Ϊ��������ָ� */
	public boolean matchAddWordCommand()
	{
		String regexAddWord = "(ADD|add)\\s+.+";
		return command.matches(regexAddWord);
	}

	/** ����û�¼���ָ���Ƿ�Ϊ�򿪵���ָ� */
	public boolean matchEditWordCommand()
	{
		String regexOpenWord = "(OPEN|open|EDIT|edit)\\s+.+";
		return command.matches(regexOpenWord);
	}

	public String getEnglishWord()
	{
		return command.replaceAll("(ADD|add|OPEN|open|EDIT|edit)\\s+", "");
	}

	/**
	 * ��ȡ�û�¼��Ĺؼ��֣�
	 * 
	 * @return �û�¼��Ĺؼ��֣�
	 */
	public String getSearchWords()
	{
		// ȥ�����ݵ����������ʶǰ׺��
		String keyWords = command.replaceAll("[S|s]+\\s+", "");
		// �����û�Ҫ���������еĿո�(%20)��
		keyWords = keyWords.replaceAll("\\s+", "%20");
		return keyWords;
	}

	/**
	 * ����û������ָ���Ƿ�Ϊ����ָ�
	 * 
	 * @param command
	 *            ��ʽ��{FY/fy 1�λ���}{�հ��ַ� 1�λ���}{�����ַ�1�λ���}
	 */
	public boolean matchTranslateCommand()
	{
		String regexTranslate = "(FY|fy)\\s+.+";
		return command.matches(regexTranslate);
	}

	/**
	 * ��ȡ�û�¼��Ĺؼ��֣�
	 * 
	 * @return �û�¼��Ĺؼ��֣�
	 */
	public String getTranslateWord()
	{
		return command.replaceAll("(FY|fy)\\s+", "");
	}

	/**
	 * �ٶ�������
	 * 
	 * @param keyWords
	 *            �����ؼ��֣�
	 * @throws IOException
	 */
	public void search(String keyWords) throws IOException
	{
		Runtime.getRuntime().exec("cmd /c start http://www.baidu.com/s?wd=" + keyWords);
	}

	/**
	 * �ð��ʰԷ��뵥�ʣ�
	 * 
	 * @param word
	 *            Ҫ���뵥�ʣ�
	 * @throws IOException
	 */
	public void translate(String word) throws IOException
	{
		Runtime.getRuntime().exec("cmd /c start http://www.iciba.com/" + word);
	}

	public boolean matchUrlCommand()
	{
		String regexUrl = "(http://|HTTP://|www\\.|WWW\\.).+";
		return command.matches(regexUrl);
	}

	/** ��ָ������ַ�� */
	public void openUrl(String url) throws IOException
	{
		Runtime.getRuntime().exec("cmd /c start " + url);
	}

	/** �ж��û������Ƿ�ΪDOSָ� */
	public boolean matchDosCommand()
	{
		String regex_dos = "(dos|DOS)\\s+.+";
		return command.matches(regex_dos);
	}

	/** ִ���û�¼���DOS���� */
	public Process executeDos() throws IOException
	{
		String dos = command.replaceAll("(dos|DOS)\\s+", "");
		Process process = Runtime.getRuntime().exec(dos);
		return process;
	}
}
