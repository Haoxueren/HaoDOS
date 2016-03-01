package com.haoxueren.word;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ClipBoardHelper
{

	/**
	 * 获取系统剪切板的文本内容；
	 */
	public static String getClipboardText() throws Exception
	{
		// 获取剪切析对象；
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		// 获取剪切析内容；
		Transferable transferable = clipboard.getContents(null);
		// 判断剪切析内容的类型；
		if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor))
		{
			return (String) transferable.getTransferData(DataFlavor.stringFlavor);
		}
		return null;
	}
}
