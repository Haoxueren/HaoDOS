package com.haoxueren.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.haoxueren.config.Values;
import com.haoxueren.helper.FrameHelper;

public class DosFrame extends Frame implements KeyListener
{
	private TextArea textArea;
	private static final long serialVersionUID = 1L;

	public DosFrame()
	{
		super("稳扎稳打，步步为营");
	}

	public void init()
	{
		// 创建窗体并设置窗体属性；
		FrameHelper frameHelper = new FrameHelper(this);
		frameHelper.enableExitButton();
		this.setSize(400, 400);
		this.setLocation(0, 460);
		this.setBackground(Color.BLACK);
		textArea = new TextArea();
		// 设置背景色和前景色；
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		// 设置字体大小；
		textArea.setFont(new Font(null, 0, 18));
		// 监听回车键；
		textArea.addKeyListener(this);
		// 添加部件并显示窗体；
		this.add(textArea);
		this.setVisible(true);
		MyOrder.getInstance(textArea).init();
	}

	/*********************** 【接口监听区】 ***********************/

	@Override
	public void keyTyped(KeyEvent event)
	{
	}

	@Override
	public void keyPressed(KeyEvent event)
	{
	}

	@Override
	public void keyReleased(KeyEvent event)
	{
		int keyCode = event.getKeyCode();
		switch (keyCode)
		{
		// 回车键释放时执行特定的操作；
		case KeyEvent.VK_ENTER:
			// 获取用户输入的命令；
			String text = textArea.getText();
			int index = text.lastIndexOf("$");
			String input = text.substring(index).trim();
			textArea.append(Values.DIVIDER);
			// 执行命令；
			MyOrder.getInstance(textArea).execute(input);
			// 把光标位置放到文本末尾；
			textArea.setCaretPosition(text.length());
			textArea.append(Values.DIVIDER + "$");
			break;
		}
	}
}
