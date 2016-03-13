package com.haoxueren.helper;

import java.awt.Frame;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/** Frame窗体的帮助类； */
public class FrameHelper
{
	private Window frame;

	public FrameHelper(Frame frame)
	{
		this.frame = frame;
	}

	/** 设置窗体关闭的监听器； */
	public void enableExitButton()
	{
		frame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
	}
}
