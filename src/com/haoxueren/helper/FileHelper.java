package com.haoxueren.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * File帮助类；
 */
public class FileHelper
{
    /** 获取当前程序所在的目录； */
    public static String getCurrentDir()
    {
        return System.getProperty("user.dir");
    }

    /**
     * 这是获取文件创建时间(yyyyMMddHHmm)的方法；
     *
     * @param filepath 文件的绝对路径；
     */
    public static String getCreateTime(String filepath)
    {
        try
        {
            // 获取系统的运行时对象；
            Runtime runtime = Runtime.getRuntime();
            // 执行指定的dos命令；
            String command = String.format("cmd /c dir %s /tc", filepath);
            Process process;
            process = runtime.exec(command);
            // 获取DOS命令返回内容的输入流；
            InputStream inputStream = process.getInputStream();
            String inputString = IoHelper.toString(inputStream, "GBK");
            // 用正则表达式提取返回内容中的日期；
            String regex = "[0-9]{4}/[0-9]{2}/[0-9]{2}  [0-9]{2}:[0-9]{2}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(inputString);
            // 返回提取出来的文件创建日期；
            return matcher.find() ? matcher.group().replaceAll("\\D", "") : "";
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
