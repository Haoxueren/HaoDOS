package com.haoxueren.word;

import java.util.Random;

/**
 * ������İ����ࣻ
 */
public class RandomHelper
{
	/** ��ȡһ��start~end������������ */
	public static int getRandomInt(int start, int end)
	{
		int num = end - start;
		if (num <= 0)
		{
			return 0;
		} else
		{
			Random random = new Random();
			return start + random.nextInt(num);
		}
	}
}
