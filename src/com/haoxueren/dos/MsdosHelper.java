package com.haoxueren.dos;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

/** Window Dos命令的帮助类； */
public class MsdosHelper
{
	private Process process;

	public MsdosHelper(Process process)
	{
		this.process = process;
	}

	/** 执行DOS命令并输出信息； */
	public void output() throws Exception
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					// 读取DOS命令返回的信息；
					InputStream inputStream = process.getInputStream();
					InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
					// 把流中的信息输出到控制台；
					char[] buffer = new char[1024];
					int len = reader.read(buffer);
					while (len != -1)
					{
						System.out.println(new String(buffer, 0, len));
						len = reader.read(buffer);
					}
					reader.close();
					process.destroy();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}

	/** 输入信息到DOS命令； */
	public void input(String input)
	{
		PrintWriter writer = new PrintWriter(process.getOutputStream());
		writer.write(input);
		writer.close();
	}
}
