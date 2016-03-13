package com.haoxueren.main;

import java.awt.TextArea;

import com.haoxueren.config.Values;
import com.haoxueren.dict.DictHelper;
import com.haoxueren.dict.FanyiHelper;
import com.haoxueren.helper.DateHelper;
import com.haoxueren.test.LetouLuckDraw;
import com.haoxueren.utils.TextHelper;

public class MyOrder implements OutputListener
{
	private TextArea textArea;
	private static MyOrder order;

	private MyOrder(TextArea textArea)
	{
		this.textArea = textArea;
	}

	public static MyOrder getInstance(TextArea textArea)
	{
		if (order == null)
		{
			return new MyOrder(textArea);
		}
		return order;
	}

	/** 初始化一些信息； */
	public void init()
	{
		// 添加我的格言；
		textArea.setText("对生活充满热情，对未来充满信心。\n");
		textArea.append(Values.DIVIDER);
		// 获取并输出当前的日期；
		DateHelper.printDate(this);
		textArea.append(Values.DIVIDER + "$");
		textArea.setCaretPosition(textArea.getText().length());
	}

	/** 执行用户的指令； */
	public void execute(String input)
	{
		try
		{
			// 乐投天下抽奖程序；
			if (input.matches("\\$(LETOU|letou)\\s+(lucky draw|LUCKY DRAW)"))
			{
				LetouLuckDraw.luckyDraw(this);
				return;
			}
			// 执行查询单词指令；
			if (input.matches("\\$(DICT|dict)\\s+.+"))
			{
				String word = input.replaceFirst("\\$(DICT|dict)\\s+", "");
				new DictHelper(this).translate(word);
				return;
			}

			// 在线翻译[英译汉]
			if (input.matches("\\$(FANYI|fanyi)\\s+[\\s\\S]+"))
			{
				String content = input.replaceFirst("\\$(FANYI|fanyi)\\s+", "");
				FanyiHelper.getInstance(this).icibaFanyi(content);
				return;
			}
			textArea.append("未找到对应的指令！\n");
		} catch (Exception e)
		{
			if (TextHelper.notEmpty(e.getMessage()))
			{
				textArea.append("异常：" + e.getMessage() + "\n");
			} else
			{
				textArea.append("异常：NullPointException");
			}
			e.printStackTrace();

		}
	}

	@Override
	public void output(String text)
	{
		textArea.append(text + "\n");
	}

}
