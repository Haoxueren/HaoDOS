package com.haoxueren.dos;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

/** Window Dos����İ����ࣻ */
public class MsdosHelper
{
	private Process process;

	public MsdosHelper(Process process)
	{
		this.process = process;
	}

	/** ִ��DOS��������Ϣ�� */
	public void output() throws Exception
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					// ��ȡDOS����ص���Ϣ��
					InputStream inputStream = process.getInputStream();
					InputStreamReader reader = new InputStreamReader(inputStream);
					// �����е���Ϣ���������̨��
					char[] buffer = new char[1024];
					int len = reader.read(buffer);
					while (len != -1)
					{
						System.out.println(new String(buffer, 0, len));
						len = reader.read(buffer);
					}
					reader.close();
					process.destroy();
					System.out.println("DOS FINISHED��");
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}

	/** ������Ϣ��DOS��� */
	public void input(String input)
	{
		PrintWriter writer = new PrintWriter(process.getOutputStream());
		writer.write(input);
		writer.close();
	}
}
