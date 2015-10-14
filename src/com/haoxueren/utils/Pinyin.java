package com.haoxueren.utils;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author 好学人 这是汉字转拼音的框架；<br>
 *         本类提供了一个入口和一个出口；<br>
 *         入口：通过setHanzis()传入要转换的汉字字符串；<br>
 *         出口：通过getResult()方法获取类内部的数据；<br>
 */
public class Pinyin
{
	private String hanzis;

	public Pinyin(String hanzis)
	{
		this.hanzis = hanzis;
	}

	/**
	 * 入口：通过setter传入要转换的汉字；
	 */
	public void setHanzis(String hanzis)
	{
		this.hanzis = hanzis;
	}

	/**
	 * 出口：返回转换后的数据；
	 */
	public List<String[]> getResult()
	{
		return transform(hanzis);
	}

	/**
	 * 请返回拼音的格式对象，传null使用默认格式；
	 */
	public PinyinBuilder setPinyinBuilder()
	{
		return null;
	}

	/**
	 * 除汉字外其它字符的正则表达式；<br>
	 * 符合条件的字符将被添加到结果里；<br>
	 * 传null表示不筛选，包含所有非汉字字符；
	 */
	public String extraRegex()
	{
		return null;
	}

	/**
	 * 将汉字转换为拼音；
	 */
	private List<String[]> transform(String string)
	{
		// 创建一个集合，用来存储原字符串对应的拼音数组；
		List<String[]> pinyinList = new ArrayList<String[]>();
		// 将原字符串转换为字符数组；
		char[] charArray = string.toCharArray();
		for (char ch : charArray)
		{
			String[] array;
			if (matchRegex(extraRegex(), ch) && !isHanzi(ch))
			{
				array = new String[] { ch + "" };
			} else
			{
				try
				{
					// 将字符转换为对应的拼音数组；
					if (setPinyinBuilder() == null)
					{
						array = PinyinHelper.toHanyuPinyinStringArray(ch);
					} else
					{
						array = PinyinHelper.toHanyuPinyinStringArray(ch, setPinyinBuilder().build());
					}
					if (array == null)
					{
						array = new String[] { nullMark() == null ? ch + "" : nullMark() };
					}
				} catch (BadHanyuPinyinOutputFormatCombination e)
				{
					array = new String[] { nullMark() == null ? ch + "" : nullMark() };
					System.out.println("异常字符：" + ch);
					e.printStackTrace();
				}
			}
			// 将拼音数组添加到集合中；
			pinyinList.add(array);
		}
		return pinyinList;
	}

	/**
	 * 请传入空值或异常字符替代符；<br>
	 * 返回null表示使用字符本身；
	 */
	public String nullMark()
	{
		return null;
	}

	/**
	 * 判断一个字符是否是汉字；<br>
	 * 返回true表示是汉字；
	 */
	private boolean isHanzi(char ch)
	{
		String regex = "[\\u4e00-\\u9fa5]";
		boolean isHanzi = ("" + ch).matches(regex);
		return isHanzi;
	}

	/**
	 * 此正则表达式用来确定哪些非汉字字符将被添加到结果中；<br>
	 * 返回null表示不进行筛选，包含所有的非汉字字符；
	 */
	private boolean matchRegex(String regex, char ch)
	{
		return regex != null ? ("" + ch).matches(regex) : true;
	}

}
