package com.haoxueren.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.haoxueren.helper.MsdosHelper;

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
