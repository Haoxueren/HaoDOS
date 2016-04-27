package com.haoxueren.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import com.haoxueren.config.Values;
import com.haoxueren.helper.DateWatcher;
import com.haoxueren.helper.DesktopHelper;
import com.haoxueren.helper.FrameHelper;

public class HosFrame extends Frame implements KeyListener, FocusListener, MouseListener
{
	private TextArea textArea;
	private static final long serialVersionUID = 1L;

	public HosFrame()
	{
		super("稳扎稳打，步步为营");
	}

	public void init() throws Exception
	{
		// 创建窗体并设置窗体属性；
		FrameHelper frameHelper = new FrameHelper(this);
		frameHelper.enableExitButton();
		int frameSize = 500;
		this.setSize(frameSize, frameSize);
		this.setLocation(0, DesktopHelper.getDesktopHeight(this) - frameSize);
		this.setBackground(Color.BLACK);
		textArea = new TextArea();
		// 设置背景色和前景色；
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		// 设置字体大小；
		textArea.setFont(new Font(null, Font.BOLD, 18));
		// 监听键盘事件；
		textArea.addKeyListener(this);
		// 监听获取焦点事件；
		textArea.addFocusListener(this);
		// 监听鼠标点击事件；
		textArea.addMouseListener(this);
		// 添加部件并显示窗体；
		this.add(textArea);
		this.setVisible(true);
		MyOrder order = MyOrder.getInstance(textArea);
		order.init();
		// 每天第一次运行启动抽奖；
		if (DateWatcher.isAnotherDay())
		{
			textArea.append("letou lucky draw\n");
			order.execute("$letou lucky draw");
			textArea.append(Values.DIVIDER + "~$" + MyOrder.prefix);
		}
	}

	/*********************** 【接口监听区】 ***********************/

	@Override
	public void focusGained(FocusEvent e)
	{
		// 获取焦点时，将光标移到最后；
		int position = textArea.getText().length();
		textArea.setCaretPosition(position);
	}

	@Override
	public void focusLost(FocusEvent e)
	{
	}

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
		// 更新文字颜色；
		textArea.setForeground(Color.WHITE);
		// 键释放时，将光标移动到最后；
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
			// 开始执行命令；
			MyOrder.getInstance(textArea).execute(input);
			textArea.append(Values.DIVIDER + "~$" + MyOrder.prefix);
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent event)
	{
		// 鼠标(左键)点击，将光标移到最后；
		if (event.getButton() == MouseEvent.BUTTON1)
		{
			int position = textArea.getText().length();
			textArea.setCaretPosition(position);
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

}
