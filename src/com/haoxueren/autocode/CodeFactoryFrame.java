package com.haoxueren.autocode;

import java.awt.Button;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/** CodeFactory界面； */
public class CodeFactoryFrame extends Frame
{
	private static final long serialVersionUID = 460863617205502569L;

	public CodeFactoryFrame() throws HeadlessException
	{
		super();
	}

	public CodeFactoryFrame(String title) throws HeadlessException
	{
		super(title);
	}

	/* 控件成员变量区； */
	private String letouLayout = "C:/Android/Development/letou_android_v2.0/App_Letou360/res/layout";
	public TextField fileNameTextField, pathTextField;
	public Button createCodeButton, copyCodeButton;
	public TextArea textArea;

	/** 启动AutoCodeFrame； */
	public void showFrame(ActionListener actionListener)
	{
		// 初始化窗体属性；
		this.setTitle("自动化与批处理");
		this.setBounds(500, 100, 600, 600);
		this.setLayout(new FlowLayout());
		// 添加关闭监听；
		initWindow(this);
		// XML文件路径；
		Label pathLabel = new Label("文件路径");
		pathTextField = new TextField(60);
		pathTextField.setText(letouLayout);
		this.add(pathLabel);
		this.add(pathTextField);
		// 生成代码显示区域；
		textArea = new TextArea(21, 52);
		Font font = new Font(null, 0, 18);
		textArea.setFont(font);
		this.add(textArea);
		// 说明文字标签；
		Label label = new Label("文件名称");
		this.add(label);
		// 文件名输入框；
		fileNameTextField = new TextField(35);
		this.add(fileNameTextField);
		// 下拉选择框；
		final Choice choice = new Choice();
		choice.add("注解模式");
		this.add(choice);
		// 生成代码的按钮；
		createCodeButton = new Button("生成代码");
		createCodeButton.setActionCommand(createCodeButton.getLabel());
		createCodeButton.addActionListener(actionListener);
		this.add(createCodeButton);
		// 复制代码按钮 ；
		copyCodeButton = new Button("复制代码");
		copyCodeButton.setActionCommand(copyCodeButton.getLabel());
		copyCodeButton.addActionListener(actionListener);
		this.add(copyCodeButton);
		// 显示窗体；
		this.setVisible(true);
	}

	/** 初始化窗口属性； */
	private void initWindow(final Frame frame)
	{
		// 禁止改变窗口大小；
		this.setResizable(false);
		// 添加窗口关闭监听器；
		frame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				// 仅关闭当前窗体；
				frame.dispose();
			}
		});
	}

}
