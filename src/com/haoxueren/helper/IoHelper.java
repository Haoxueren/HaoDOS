package com.haoxueren.helper;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * InputStream/OutputStream�İ����ࣻ
 */
public class IoHelper
{
    /**
     * ���������ر��������ķ�����
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
     * �˷������ڰ��������е�����ת��Ϊָ��������ַ�����
     *
     * @param inputStream Ҫת������������
     *
     * @return �����������ݵ��ַ�������
     */
    public static String toString(InputStream inputStream, String charset)
    {
        BufferedInputStream bufferedInputStream = null;
        try
        {
            bufferedInputStream = new BufferedInputStream(inputStream);
            // ����һ���ֽ���������������������������е����ݣ�
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bys = new byte[1024];
            int len = 0;
            while ((len = bufferedInputStream.read(bys)) != -1)
            {
                // ���������ĵ�����д���ֽ������������
                byteArrayOutputStream.write(bys, 0, len);
            }
            // ���ֽ�����������е�����ת��Ϊָ��������ַ�����
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
