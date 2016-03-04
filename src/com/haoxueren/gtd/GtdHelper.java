package com.haoxueren.gtd;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.haoxueren.helper.TextHelper;

public class GtdHelper
{
	@Test
	public void test() throws Exception
	{
		GtdPaser paser = new GtdPaser("gtd add task：status=done，event=text");
		System.out.println(paser.getPairs().get("status"));
	}

	// gtd add done=text tag=tag1,tag2,tag3
	public static void execute(String input) throws Exception
	{
		input = input.toLowerCase();
		// 解析[gtd add todo]指令；
		if (input.matches("^gtd\\s+add\\s+(todo|doing|done){1}\\s+=.+"))
		{
			String[] infos = input.split("=");
			// 提取待办事项状态；
			String[] heads = infos[0].trim().split("\\s");
			String statusText = heads[heads.length - 1];
			System.out.println(statusText);
			// 提取待办事项内容；
			// String matchEventText = getFirstMatchedText(input, "(todo|doing|done){1}=\"[^\"]+\"");
			// String eventText = matchEventText.replaceFirst("(todo|doing|done){1}=", "");
			// eventText = eventText.replaceAll("\"", "");
			// // 提取用户输入的标签；
			// String[] tags = null;
			// String matchTagText = getFirstMatchedText(input, "tag=\\S+");
			// if (TextHelper.notEmpty(matchTagText))
			// {
			// String tagsText = matchTagText.replaceFirst("tag=\"", "");
			// System.out.println(tagsText);
			// tags = tagsText.split("\\s");
			// } else
			// {
			// tags = new String[] {};
			// }
			// GameGtd.addTask(statusText, eventText, tags);
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
