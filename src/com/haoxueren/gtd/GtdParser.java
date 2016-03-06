package com.haoxueren.gtd;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/** GTDָ��������� */
public class GtdParser
{
	private String[] array;

	public GtdParser(String input)
	{
		array = input.toLowerCase().split("(��|:)");
	}

	/** ��ȡ����ͷ��Ϣ�� */
	public String getAction()
	{
		return array[0].trim();
	}

	/** ��ȡָ���еļ�ֵ����Ϣ�� */
	private Map<String, String> getPairs()
	{
		Map<String, String> map = new HashMap<>();
		if (array.length == 2)
		{
			String[] pairs = array[1].split("(��|,)");
			for (String pairText : pairs)
			{
				String[] pair = pairText.split("=");
				map.put(pair[0].trim(), pair[1].trim());
			}
		}
		return map;
	}

	/** ��ȡ�¼��� */
	public String getEvent()
	{
		return getPairs().get("event");
	}

	/** ��ȡ�����״̬�� */
	public String getStatus(String defaultValue)
	{
		String status = getPairs().get("status");
		return status == null ? defaultValue : status;
	}

	/** ��ȡ�����ǩ�� */
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

	/** ��ȡ�����id�� */
	public String getId()
	{
		return getPairs().get("id");
	}
}
