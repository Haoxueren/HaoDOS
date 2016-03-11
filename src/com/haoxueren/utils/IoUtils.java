package com.haoxueren.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * InputStream/OutputStream的帮助类；
 */
public class IoUtils
{
    /**
     * 这是用来关闭输入流的方法；
     */
    public static void close(InputStream inputStream)
    {
        if (inputStream != null)
        {
            try
            {
                inputStream.close();
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 此方法用于把输入流中的数据转化为指定编码的字符串；
     *
     * @param inputStream 要转化的输入流；
     *
     * @return 该输入流内容的字符串对象；
     */
    public static String toString(InputStream inputStream, String charset)
    {
        BufferedInputStream bufferedInputStream = null;
        try
        {
            bufferedInputStream = new BufferedInputStream(inputStream);
            // 创建一个字节数组输出流，用来接收输入流中的数据；
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bys = new byte[1024];
            int len = 0;
            while ((len = bufferedInputStream.read(bys)) != -1)
            {
                // 将输入流的的数据写入字节数组输出流；
                byteArrayOutputStream.write(bys, 0, len);
            }
            // 将字节数组输出流中的数据转化为指定编码的字符串；
            return byteArrayOutputStream.toString(charset);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        } finally
        {
            close(bufferedInputStream);
        }
    }
}
