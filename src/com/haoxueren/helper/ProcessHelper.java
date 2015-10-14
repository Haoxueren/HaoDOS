package com.haoxueren.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/** 执行DOS命令返回的Process帮助类； */
public class ProcessHelper
{
	private ProcessHelperListener listener;

	public ProcessHelper(ProcessHelperListener listener)
	{
		this.listener = listener;
	}

	/** 读取线程中输入流中的信息； */
	public void readProcess(Process process) throws IOException, InterruptedException
	{
		InputStream inputStream = process.getInputStream();
		InputStreamReader in = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(in);
		String line = null;
		while ((line = reader.readLine()) != null)
		{
			listener.onReadLine(line);
		}
		process.waitFor();
		inputStream.close();
		reader.close();
		process.destroy();
	}

	public interface ProcessHelperListener
	{
		void onReadLine(String line);
	}

}
