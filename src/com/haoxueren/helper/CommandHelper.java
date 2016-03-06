package com.haoxueren.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.haoxueren.dos.MsdosHelper;

/**
 * 2015年10月9日20:40:21<br>
 * 处理并执行指令的帮助类；<br>
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
	 * 检测用户输入的指令是否为搜索指令；
	 * 
	 * @param order
	 *            格式：{S/s 1次或多次}{空白字符 1次或多次}{任意字符1次或多次}
	 */
	public boolean matchSearchCommand()
	{
		String regexSearchCommand = "[S|s]+\\s+.+";
		return order.matches(regexSearchCommand);
	}

	/** 检测用户录入的指令是否为新增单词指令； */
	public boolean matchAddWordCommand()
	{
		String regexAddWord = "(ADD|add)\\s+.+";
		return order.matches(regexAddWord);
	}

	/** 检测用户录入的指令是否为打开单词指令； */
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
	 * 提取用户录入的关键字；
	 * 
	 * @return 用户录入的关键字；
	 */
	public String getSearchWords()
	{
		// 去除内容的搜索命令标识前缀；
		String keyWords = order.replaceAll("[S|s]+\\s+", "");
		// 处理用户要搜索内容中的空格(%20)；
		keyWords = keyWords.replaceAll("\\s+", "%20");
		return keyWords;
	}

	/**
	 * 检测用户输入的指令是否为翻译指令；
	 * 
	 * @param order
	 *            格式：{FY/fy 1次或多次}{空白字符 1次或多次}{任意字符1次或多次}
	 */
	public boolean matchTranslateCommand()
	{
		String regexTranslate = "(FY|fy)\\s+.+";
		return order.matches(regexTranslate);
	}

	/**
	 * 提取用户录入的关键字；
	 * 
	 * @return 用户录入的关键字；
	 */
	public String getTranslateWord()
	{
		return order.replaceAll("(FY|fy)\\s+", "");
	}

	/**
	 * 百度搜索；
	 * 
	 * @param keyWords
	 *            搜索关键字；
	 * @throws IOException
	 */
	public void search(String keyWords) throws IOException
	{
		Runtime.getRuntime().exec("cmd /c start http://www.baidu.com/s?wd=" + keyWords);
	}

	/**
	 * 用爱词霸翻译单词；
	 * 
	 * @param word
	 *            要翻译单词；
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

	/** 打开指定的网址； */
	public void openUrl(String url) throws IOException
	{
		Runtime.getRuntime().exec("cmd /c start " + url);
	}

	/** 判断用户请求是否为DOS指令； */
	public boolean matchDosCommand()
	{
		String regex_dos = "(dos|DOS)\\s+.+";
		return order.matches(regex_dos);
	}

	/** 执行用户录入的DOS请求； */
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
