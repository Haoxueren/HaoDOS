package com.haoxueren.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * File�����ࣻ
 */
public class FileHelper
{
    /** ��ȡ��ǰ�������ڵ�Ŀ¼�� */
    public static String getCurrentDir()
    {
        return System.getProperty("user.dir");
    }

    /**
     * ���ǻ�ȡ�ļ�����ʱ��(yyyyMMddHHmm)�ķ�����
     *
     * @param filepath �ļ��ľ���·����
     */
    public static String getCreateTime(String filepath)
    {
        try
        {
            // ��ȡϵͳ������ʱ����
            Runtime runtime = Runtime.getRuntime();
            // ִ��ָ����dos���
            String command = String.format("cmd /c dir %s /tc", filepath);
            Process process;
            process = runtime.exec(command);
            // ��ȡDOS��������ݵ���������
            InputStream inputStream = process.getInputStream();
            String inputString = IoHelper.toString(inputStream, "GBK");
            // ��������ʽ��ȡ���������е����ڣ�
            String regex = "[0-9]{4}/[0-9]{2}/[0-9]{2}  [0-9]{2}:[0-9]{2}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(inputString);
            // ������ȡ�������ļ��������ڣ�
            return matcher.find() ? matcher.group().replaceAll("\\D", "") : "";
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
