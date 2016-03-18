package com.haoxueren.helper;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

/**
 * GUI的帮助类；<br>
 * 参考资料：http://blog.csdn.net/ycb1689/article/details/7514339<br>
 */
public class DesktopHelper
{
	/** 获取屏幕的高度(不含任务栏高度) */
	public static int getDesktopHeight(Component frame)
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		// 获取整个显示屏幕的大小，包含了任务栏的高度。
		Dimension screenSize = toolkit.getScreenSize();
		// 获取任务栏对象；
		Insets insets = toolkit.getScreenInsets(frame.getGraphicsConfiguration());
		// 屏幕的高度减去上下任务栏的高度；
		return screenSize.height - insets.top - insets.bottom;
	}

	/** 根据文件名打开文件； */
	public static void openFile(String path) throws IOException
	{
		Desktop desktop = Desktop.getDesktop();
		File file = new File(path);
		desktop.open(file);
	}

	/** 获取桌面文件对象； */
	public static File getDesktopFile()
	{
		FileSystemView fileView = FileSystemView.getFileSystemView();
		System.out.println(fileView.getHomeDirectory());
		return fileView.getHomeDirectory();
	}
}
