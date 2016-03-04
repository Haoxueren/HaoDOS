package com.haoxueren.gtd;

import java.util.HashMap;
import java.util.Map;

/** GTDָ��������� */
public class GtdPaser
{
	private String[] inputs;

	public GtdPaser(String input)
	{
		this.inputs = input.split("(��|:)");
	}

	/** ��ȡ����ͷ��Ϣ�� */
	public String getAction()
	{
		return inputs[0].trim();
	}

	/** ��ȡָ���еļ�ֵ����Ϣ�� */
	public Map<String, String> getPairs()
	{
		Map<String, String> map = new HashMap<>();
		String[] pairs = inputs[1].split("(��|,)");
		for (String pairText : pairs)
		{
			String[] pair = pairText.split("=");
			map.put(pair[0].trim(), pair[1].trim());
		}
		System.out.println(map);
		return map;
	}
}
