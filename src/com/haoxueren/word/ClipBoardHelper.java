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
	 * ��ȡϵͳ���а���ı����ݣ�
	 */
	public static String getClipboardText() throws Exception
	{
		// ��ȡ����������
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		// ��ȡ���������ݣ�
		Transferable transferable = clipboard.getContents(null);
		// �жϼ��������ݵ����ͣ�
		if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor))
		{
			return (String) transferable.getTransferData(DataFlavor.stringFlavor);
		}
		return null;
	}
}
