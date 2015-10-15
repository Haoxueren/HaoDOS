package com.haoxueren.helper;

import java.io.IOException;

import org.junit.Test;

/**
 * 2015年10月9日20:40:21<br>
 * 处理并执行指令的帮助类；<br>
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
	 * 检测用户输入的指令是否为搜索指令；
	 * 
	 * @param command
	 *            格式：{S/s 1次或多次}{空白字符 1次或多次}{任意字符1次或多次}
	 */
	public boolean matchSearchCommand()
	{
		String regexSearchCommand = "[S|s]+\\s+.+";
		return command.matches(regexSearchCommand);
	}

	/** 检测用户录入的指令是否为新增单词指令； */
	public boolean matchAddWordCommand()
	{
		String regexAddWord = "(ADD|add)\\s+.+";
		return command.matches(regexAddWord);
	}

	/** 检测用户录入的指令是否为打开单词指令； */
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
	 * 提取用户录入的关键字；
	 * 
	 * @return 用户录入的关键字；
	 */
	public String getSearchWords()
	{
		// 去除内容的搜索命令标识前缀；
		String keyWords = command.replaceAll("[S|s]+\\s+", "");
		// 处理用户要搜索内容中的空格(%20)；
		keyWords = keyWords.replaceAll("\\s+", "%20");
		return keyWords;
	}

	/**
	 * 检测用户输入的指令是否为翻译指令；
	 * 
	 * @param command
	 *            格式：{FY/fy 1次或多次}{空白字符 1次或多次}{任意字符1次或多次}
	 */
	public boolean matchTranslateCommand()
	{
		String regexTranslate = "(FY|fy)\\s+.+";
		return command.matches(regexTranslate);
	}

	/**
	 * 提取用户录入的关键字；
	 * 
	 * @return 用户录入的关键字；
	 */
	public String getTranslateWord()
	{
		return command.replaceAll("(FY|fy)\\s+", "");
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
		return command.matches(regexUrl);
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
		return command.matches(regex_dos);
	}

	/** 执行用户录入的DOS请求； */
	public Process executeDos() throws IOException
	{
		String dos = command.replaceAll("(dos|DOS)\\s+", "");
		Process process = Runtime.getRuntime().exec(dos);
		return process;
	}
}
