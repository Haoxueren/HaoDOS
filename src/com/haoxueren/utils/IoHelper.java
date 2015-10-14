package com.haoxueren.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ����IO�� �ֽ����Ĺ����ࣻ
 */
public class IoHelper
{
	/**
	 * ���������ر��������ķ�����
	 */
	public static void close(InputStream in)
	{
		if (in != null)
		{
			try
			{
				in.close();
			} catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * ���������ر�������ķ�����
	 */
	public static void close(OutputStream out)
	{
		if (out != null)
		{
			try
			{
				out.close();
			} catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * @method copy
	 *         �����������Ƶ�������У�
	 * @param inStream
	 *            ��������
	 * @param outStream
	 *            �������
	 */
	public static void copy(InputStream inStream, OutputStream outStream)
	{
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try
		{
			bis = new BufferedInputStream(inStream);
			bos = new BufferedOutputStream(outStream);
			byte[] bys = new byte[1024];
			int len = 0;
			while ((len = bis.read(bys)) != -1)
			{
				bos.write(bys, 0, len);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			close(bos);
			close(bis);
		}
	}

	/**
	 * @author
	 *         ÿ���Ķ�1Сʱרҵ�鼮
	 * @method toString
	 *         �˷������ڰ��������е�����ת��ΪUTF-8������ַ�����
	 * @param inStream
	 *            Ҫת������������
	 * @return
	 *         �����������ݵ��ַ�������
	 *         �÷���δ���ԣ�
	 */
	public static String toString(InputStream inStream)
	{
		BufferedInputStream bis = null;
		try
		{
			bis = new BufferedInputStream(inStream);
			// ����һ���ֽ���������������������������е����ݣ�
			ByteArrayOutputStream aos = new ByteArrayOutputStream();
			byte[] bys = new byte[1024];
			int len = 0;
			while ((len = bis.read(bys)) != -1)
			{
				// ���������ĵ�����д���ֽ������������
				aos.write(bys, 0, len);
			}
			// ���ֽ�����������е�����ת��ΪUTF-8������ַ�����
			String string = aos.toString("UTF-8");
			return string;
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		} finally
		{
			close(bis);
		}
	}

	/**
	 * @method toString
	 *         �˷������ڰ��������е�����ת��Ϊָ��������ַ�����
	 * @param inStream
	 *            Ҫת������������
	 * @return
	 *         �����������ݵ��ַ�������
	 *         �÷���δ���ԣ�
	 */
	public static String toString(InputStream inStream, String charset)
	{
		BufferedInputStream bis = null;
		try
		{
			bis = new BufferedInputStream(inStream);
			// ����һ���ֽ���������������������������е����ݣ�
			ByteArrayOutputStream aos = new ByteArrayOutputStream();
			byte[] bys = new byte[1024];
			int len = 0;
			while ((len = bis.read(bys)) != -1)
			{
				// ���������ĵ�����д���ֽ������������
				aos.write(bys, 0, len);
			}
			// ���ֽ�����������е�����ת��ΪUTF-8������ַ�����
			String string = aos.toString(charset);
			return string;
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		} finally
		{
			close(bis);
		}
	}
}
