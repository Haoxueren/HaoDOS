package com.haoxueren.gtd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.haoxueren.helper.TextHelper;

public class GtdHelper
{
	/** ִ���û�¼���GTDָ� */
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
			System.out.println("�޷�ʶ���ָ�");
			break;
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
