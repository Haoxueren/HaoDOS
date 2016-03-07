package com.haoxueren.word;

import java.util.Random;

/**
 * 随机数的帮助类；
 */
public class RandomHelper
{
	/** 获取一个start~end间的随机整数； */
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
