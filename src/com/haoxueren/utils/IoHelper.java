package com.haoxueren.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 这是IO流 字节流的工具类；
 */
public class IoHelper
{
	/**
	 * 这是用来关闭输入流的方法；
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
	 * 这是用来关闭输出流的方法；
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
	 *         把输入流复制到输出流中；
	 * @param inStream
	 *            输入流；
	 * @param outStream
	 *            输出流；
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
	 *         每天阅读1小时专业书籍
	 * @method toString
	 *         此方法用于把输入流中的数据转化为UTF-8编码的字符串；
	 * @param inStream
	 *            要转化的输入流；
	 * @return
	 *         该输入流内容的字符串对象；
	 *         该方法未测试；
	 */
	public static String toString(InputStream inStream)
	{
		BufferedInputStream bis = null;
		try
		{
			bis = new BufferedInputStream(inStream);
			// 创建一个字节数组输出流，用来接收输入流中的数据；
			ByteArrayOutputStream aos = new ByteArrayOutputStream();
			byte[] bys = new byte[1024];
			int len = 0;
			while ((len = bis.read(bys)) != -1)
			{
				// 将输入流的的数据写入字节数组输出流；
				aos.write(bys, 0, len);
			}
			// 将字节数组输出流中的数据转化为UTF-8编码的字符串；
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
	 *         此方法用于把输入流中的数据转化为指定编码的字符串；
	 * @param inStream
	 *            要转化的输入流；
	 * @return
	 *         该输入流内容的字符串对象；
	 *         该方法未测试；
	 */
	public static String toString(InputStream inStream, String charset)
	{
		BufferedInputStream bis = null;
		try
		{
			bis = new BufferedInputStream(inStream);
			// 创建一个字节数组输出流，用来接收输入流中的数据；
			ByteArrayOutputStream aos = new ByteArrayOutputStream();
			byte[] bys = new byte[1024];
			int len = 0;
			while ((len = bis.read(bys)) != -1)
			{
				// 将输入流的的数据写入字节数组输出流；
				aos.write(bys, 0, len);
			}
			// 将字节数组输出流中的数据转化为UTF-8编码的字符串；
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
