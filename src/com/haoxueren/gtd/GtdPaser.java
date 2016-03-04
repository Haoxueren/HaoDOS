package com.haoxueren.gtd;

import java.util.HashMap;
import java.util.Map;

/** GTD指令解析器； */
public class GtdPaser
{
	private String[] inputs;

	public GtdPaser(String input)
	{
		this.inputs = input.split("(：|:)");
	}

	/** 获取命令头信息； */
	public String getAction()
	{
		return inputs[0].trim();
	}

	/** 获取指令中的键值对信息； */
	public Map<String, String> getPairs()
	{
		Map<String, String> map = new HashMap<>();
		String[] pairs = inputs[1].split("(，|,)");
		for (String pairText : pairs)
		{
			String[] pair = pairText.split("=");
			map.put(pair[0].trim(), pair[1].trim());
		}
		System.out.println(map);
		return map;
	}
}
