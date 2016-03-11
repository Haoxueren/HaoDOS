package com.haoxueren.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.haoxueren.dos.MsdosHelper;

/**
 * 2015��10��9��20:40:21<br>
 * ���?ִ��ָ��İ����ࣻ<br>
 */
public class CommandHelper
{
	private String order;

	public CommandHelper()
	{
	}

	public CommandHelper(String command)
	{
		this.order = command;
	}

	public void setCommand(String command)
	{
		this.order = command;
	}

	/**
	 * ����û������ָ���Ƿ�Ϊ����ָ�
	 * 
	 * @param order
	 *            ��ʽ��{S/s 1�λ���}{�հ��ַ� 1�λ���}{�����ַ�1�λ���}
	 */
	public boolean matchSearchCommand()
	{
		String regexSearchCommand = "[S|s]+\\s+.+";
		return order.matches(regexSearchCommand);
	}

	/** ����û�¼���ָ���Ƿ�Ϊ��������ָ� */
	public boolean matchAddWordCommand()
	{
		String regexAddWord = "(ADD|add)\\s+.+";
		return order.matches(regexAddWord);
	}

	/** ����û�¼���ָ���Ƿ�Ϊ�򿪵���ָ� */
	public boolean matchEditWordCommand()
	{
		String regexOpenWord = "(OPEN|open|EDIT|edit)\\s+.+";
		return order.matches(regexOpenWord);
	}

	public String getEnglishWord()
	{
		return order.replaceAll("(ADD|add|OPEN|open|EDIT|edit)\\s+", "");
	}

	/**
	 * ��ȡ�û�¼��Ĺؼ��֣�
	 * 
	 * @return �û�¼��Ĺؼ��֣�
	 */
	public String getSearchWords()
	{
		// ȥ�����ݵ����������ʶǰ׺��
		String keyWords = order.replaceAll("[S|s]+\\s+", "");
		// �����û�Ҫ���������еĿո�(%20)��
		keyWords = keyWords.replaceAll("\\s+", "%20");
		return keyWords;
	}

	/**
	 * ����û������ָ���Ƿ�Ϊ����ָ�
	 * 
	 * @param order
	 *            ��ʽ��{FY/fy 1�λ���}{�հ��ַ� 1�λ���}{�����ַ�1�λ���}
	 */
	public boolean matchTranslateCommand()
	{
		String regexTranslate = "(FY|fy)\\s+.+";
		return order.matches(regexTranslate);
	}

	/**
	 * ��ȡ�û�¼��Ĺؼ��֣�
	 * 
	 * @return �û�¼��Ĺؼ��֣�
	 */
	public String getTranslateWord()
	{
		return order.replaceAll("(FY|fy)\\s+", "");
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
		return order.matches(regexUrl);
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
		return order.matches(regex_dos);
	}

	/** ִ���û�¼���DOS���� */
	public void executeDos() throws Exception
	{
		String dos = order.replaceAll("(dos|DOS)\\s+", "");
		Process process = Runtime.getRuntime().exec("cmd.exe /c " + dos);
		InputStream inputStream = process.getInputStream();
		InputStreamReader in = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(in);
		String line = null;
		while ((line = reader.readLine()) != null)
		{
			System.out.println(line);
		}
		process.waitFor();
		inputStream.close();
		reader.close();
		process.destroy();
	}
}
