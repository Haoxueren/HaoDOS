package com.haoxueren.layout;

import java.awt.Dialog;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/** CodeFactory的业务入口类； */
public class CodeFactoryService implements ActionListener, CodeFactoryListener
{
	private CodeFactoryFrame frame;

	public void service()
	{
		frame = new CodeFactoryFrame();
		frame.showFrame(this);
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		String command = event.getActionCommand();
		// 处理生成代码按钮的点击事件；
		if (command.equals(frame.createCodeButton.getLabel()))
		{
			frame.textArea.setText("");
			// 获取用户输入的文件名；
			String fileName = frame.fileNameTextField.getText().trim();
			String layoutPath = frame.pathTextField.getText().trim();
			// 解析用户输入的布局；
			CodeFactory codeFactory = new CodeFactory(this);
			File file = new File(layoutPath, fileName + ".xml");
			codeFactory.process(file.getAbsolutePath());
			frame.textArea.append("// 初始化布局控件；\n");
			frame.textArea.append("ViewAssistor.inject(this,layout);");
			return;
		}
		// 处理复制代码按钮的点击事件；
		if (command.equals(frame.copyCodeButton.getLabel()))
		{
			// 获取生成的代码文本；
			String code = frame.textArea.getText();
			if (code == null || code.length() == 0)
			{
				return;
			}
			// 创建一个可复制到剪切板的文本对象；
			StringSelection selection = new StringSelection(code);
			// 获取系统剪切板；
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			// 设置内容到剪切板；
			clipboard.setContents(selection, null);
			// 提示用户复制成功；
			frame.copyCodeButton.setLabel("复制成功");
			frame.copyCodeButton.setEnabled(false);
			Timer timer = new Timer();
			TimerTask task = new TimerTask()
			{
				@Override
				public void run()
				{
					frame.copyCodeButton.setLabel("复制代码");
					frame.copyCodeButton.setEnabled(true);
				}
			};
			timer.schedule(task, 2000);
			return;
		}

	}

	/** 生成代码的方法； */
	@Override
	public void onParseLayout(String className, String fieldName, String intId)
	{
		String annotation = "@InitView(id = " + intId + ")\n";
		String statement = "private " + className + " " + fieldName + ";\n\n";
		frame.textArea.append(annotation);
		frame.textArea.append(statement);
	}
}
