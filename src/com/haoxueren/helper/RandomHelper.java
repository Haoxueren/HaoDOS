package com.haoxueren.helper;

import java.util.Random;

/**
 * ������İ����ࣻ
 */
public class RandomHelper
{
    /** ��ȡһ��start~end������������ */
    public static int getRandomInt(int start, int end)
    {
        Random random = new Random();
        return start + random.nextInt(end - start);
    }
}
