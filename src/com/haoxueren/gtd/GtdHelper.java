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
		GtdPaser paser = new GtdPaser("gtd add task��status=done��event=text");
		System.out.println(paser.getPairs().get("status"));
	}

	// gtd add done=text tag=tag1,tag2,tag3
	public static void execute(String input) throws Exception
	{
		input = input.toLowerCase();
		// ����[gtd add todo]ָ�
		if (input.matches("^gtd\\s+add\\s+(todo|doing|done){1}\\s+=.+"))
		{
			String[] infos = input.split("=");
			// ��ȡ��������״̬��
			String[] heads = infos[0].trim().split("\\s");
			String statusText = heads[heads.length - 1];
			System.out.println(statusText);
			// ��ȡ�����������ݣ�
			// String matchEventText = getFirstMatchedText(input, "(todo|doing|done){1}=\"[^\"]+\"");
			// String eventText = matchEventText.replaceFirst("(todo|doing|done){1}=", "");
			// eventText = eventText.replaceAll("\"", "");
			// // ��ȡ�û�����ı�ǩ��
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

	/** ����ָ��ͷ��Ϣ�� */
	public static void getHeader(String input)
	{
		input.split("(��|:)");
	}

	/** ��ȡ������ƥ������ĵ�һ��Ƭ�Σ� */
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

	/** ��ȡ������ƥ�����������Ƭ�Σ� */
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
