package com.haoxueren.gtd;

import java.util.HashMap;
import java.util.Map;

/** GTD指令解析器； */
public class GtdParser
{
	private String[] array;

	public GtdParser(String input)
	{
		array = input.toLowerCase().split("(：|:)");
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
		if (array.length == 2)
		{
			String[] pairs = array[1].split("(，|,)");
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
		return getPairs().get("event");
	}

	/** 获取任务的状态； */
	public String getStatus(String defaultValue)
	{
		String status = getPairs().get("status");
		return status == null ? defaultValue : status;
	}

	/** 获取任务标签； */
	public String[] getTags()
	{
		String tagPair = getPairs().get("tag");
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
		return getPairs().get("id");
	}
}
