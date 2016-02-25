package com.haoxueren.helper;

import java.util.Random;

/**
 * 随机数的帮助类；
 */
public class RandomHelper
{
    /** 获取一个start~end间的随机整数； */
    public static int getRandomInt(int start, int end)
    {
        Random random = new Random();
        return start + random.nextInt(end - start);
    }
}
