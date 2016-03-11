package com.haoxueren.gtd;

import java.util.HashMap;
import java.util.Map;

import com.haoxueren.utils.TextHelper;

/** GTD指令解析器； */
public class GtdParser
{
	private String[] array;

	public GtdParser(String input)
	{
		array = new String[2];
		input = input.toLowerCase();
		array[0] = input.substring(0, getHeaderDividerIndex(input));
		array[1] = input.substring(getHeaderDividerIndex(input) + 1);
	}

	/** 获取命令头信息； */
	public String getAction()
	{
		return array[0].trim();
	}

	/** 获取指令中的键值对信息； */
	private Map<String, String> getPairs()
	{
		Map<String, String> map = new HashMap<>();
		if (TextHelper.notEmpty(array[1]))
		{
			String[] pairs = array[1].split("(；|;)");
			for (String pairText : pairs)
			{
				String[] pair = pairText.split("=");
				map.put(pair[0].trim(), pair[1].trim());
			}
		}
		return map;
	}

	/** 获取事件； */
	public String getEvent()
	{
		return getPairs().get(XmlField.TEXT);
	}

	/** 获取任务的状态； */
	public String getStatus(String defaultValue)
	{
		String status = getPairs().get(XmlField.STATUS);
		return status == null ? defaultValue : status;
	}

	/** 获取任务标签； */
	public String[] getTags()
	{
		String tagPair = getPairs().get(XmlField.TAG);
		if (tagPair == null)
		{
			return new String[] {};
		}
		tagPair = tagPair.toUpperCase();
		return tagPair.split("\\s+");
	}

	/** 获取任务的id； */
	public String getId()
	{
		return getPairs().get(XmlField.ID);
	}

	/** 获取信息头分隔符的索引； */
	public int getHeaderDividerIndex(String input)
	{
		int index1 = input.indexOf(':');
		int index2 = input.indexOf('：');
		if (index1 != -1)
		{
			return index1;
		}
		if (index2 != -1)
		{
			return index2;
		}
		throw new IllegalArgumentException("找不到信息头分隔符(：或 :)");
	}
}
