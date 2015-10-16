package com.haoxueren.mydos;

import java.util.Scanner;

/**
 * @author Haoxueren
 */
public class MainActivity
{
	public static void main(String[] args)
	{
		Commander commander = new Commander();
		Scanner scanner = new Scanner(System.in);
		while (true)
		{
			System.out.print(MyConstants.HEADER);
			String command = scanner.nextLine().trim();
			boolean taskEnd = commander.runTask(command);
			if (taskEnd)
			{
				scanner.close();
				scanner = null;
				System.out.println("程序已正常退出");
				break;
			}
		}
	}
}
