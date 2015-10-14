package com.haoxueren.utils;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author ��ѧ�� ���Ǻ���תƴ���Ŀ�ܣ�<br>
 *         �����ṩ��һ����ں�һ�����ڣ�<br>
 *         ��ڣ�ͨ��setHanzis()����Ҫת���ĺ����ַ�����<br>
 *         ���ڣ�ͨ��getResult()������ȡ���ڲ������ݣ�<br>
 */
public class Pinyin
{
	private String hanzis;

	public Pinyin(String hanzis)
	{
		this.hanzis = hanzis;
	}

	/**
	 * ��ڣ�ͨ��setter����Ҫת���ĺ��֣�
	 */
	public void setHanzis(String hanzis)
	{
		this.hanzis = hanzis;
	}

	/**
	 * ���ڣ�����ת��������ݣ�
	 */
	public List<String[]> getResult()
	{
		return transform(hanzis);
	}

	/**
	 * �뷵��ƴ���ĸ�ʽ���󣬴�nullʹ��Ĭ�ϸ�ʽ��
	 */
	public PinyinBuilder setPinyinBuilder()
	{
		return null;
	}

	/**
	 * �������������ַ���������ʽ��<br>
	 * �����������ַ�������ӵ�����<br>
	 * ��null��ʾ��ɸѡ���������зǺ����ַ���
	 */
	public String extraRegex()
	{
		return null;
	}

	/**
	 * ������ת��Ϊƴ����
	 */
	private List<String[]> transform(String string)
	{
		// ����һ�����ϣ������洢ԭ�ַ�����Ӧ��ƴ�����飻
		List<String[]> pinyinList = new ArrayList<String[]>();
		// ��ԭ�ַ���ת��Ϊ�ַ����飻
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
					// ���ַ�ת��Ϊ��Ӧ��ƴ�����飻
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
					System.out.println("�쳣�ַ���" + ch);
					e.printStackTrace();
				}
			}
			// ��ƴ��������ӵ������У�
			pinyinList.add(array);
		}
		return pinyinList;
	}

	/**
	 * �봫���ֵ���쳣�ַ��������<br>
	 * ����null��ʾʹ���ַ�����
	 */
	public String nullMark()
	{
		return null;
	}

	/**
	 * �ж�һ���ַ��Ƿ��Ǻ��֣�<br>
	 * ����true��ʾ�Ǻ��֣�
	 */
	private boolean isHanzi(char ch)
	{
		String regex = "[\\u4e00-\\u9fa5]";
		boolean isHanzi = ("" + ch).matches(regex);
		return isHanzi;
	}

	/**
	 * ��������ʽ����ȷ����Щ�Ǻ����ַ�������ӵ�����У�<br>
	 * ����null��ʾ������ɸѡ���������еķǺ����ַ���
	 */
	private boolean matchRegex(String regex, char ch)
	{
		return regex != null ? ("" + ch).matches(regex) : true;
	}

}
