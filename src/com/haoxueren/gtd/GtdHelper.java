package com.haoxueren.gtd;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GtdHelper
{
	/** 执行用户录入的GTD指令； */
	public static void execute(String input) throws Exception
	{
		GtdParser parser = new GtdParser(input);
		String action = parser.getAction();
		switch (action)
		{
		case "gtd add task":
			GameGtd.addTask(parser.getStatus("TODO"), parser.getEvent(), parser.getTags());
			break;
		case "gtd update task":
			GameGtd.updateTask(parser.getId(), parser.getStatus(null), parser.getEvent());
			break;
		case "gtd list task":
			GameGtd.listTask(parser.getStatus(null), parser.getTags());
			break;
		case "gtd delete task":
			break;
		default:
			System.out.println("无法识别的指令！");
			break;
		}

	}

	/** 解析指令头信息； */
	public static void getHeader(String input)
	{
		input.split("(：|:)");
	}

	/** 获取内容中匹配正则的第一个片段； */
	public static String getFirstMatchedText(String input, String regex)
	{
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		while (matcher.find())
		{
			String text = matcher.group();
			return text;
		}
		return null;
	}

	/** 获取内容中匹配正则的所有片段； */
	public static List<String> getAllMatchedText(String input, String regex)
	{
		List<String> list = new ArrayList<>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		while (matcher.find())
		{
			String text = matcher.group();
			list.add(text);
		}
		return list;
	}
}
