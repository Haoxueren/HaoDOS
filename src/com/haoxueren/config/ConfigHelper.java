package com.haoxueren.config;

import com.haoxueren.helper.TextHelper;

/**
 * ����������ļ������ࣻ<br>
 * ֧�ִӱ��������ļ��ж�ȡ���ݣ�<br>
 */
public class ConfigHelper
{
	/**
	 * ��ȡ����ͼƬ���ڵ�Ŀ¼��<br>
	 * �ȴӱ��ض�ȡ���������û�У���ʹ��Ĭ��ֵ��<br>
	 */
	public static String getWordsPath()
	{
		String wordsPath = getLocalWordPath();
		if (TextHelper.isEmpty(wordsPath))
		{
			// HAO FileHelper.getCurrentDir();
			return "C:\\Android\\Developer\\HoasirDos\\Ӣ�ﵥ��ͼ��";
		}
		return wordsPath;
	}

	/** HAO ��ȡ���ص����ݣ� */
	private static String getLocalWordPath()
	{
		return null;
	}
}
