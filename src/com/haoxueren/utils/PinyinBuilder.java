package com.haoxueren.utils;

import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * �������������ƴ���ĸ�ʽ��
 */
public abstract class PinyinBuilder
{
	private HanyuPinyinOutputFormat format;

	public HanyuPinyinOutputFormat build()
	{
		return format;
	}

	/**
	 * ��ʼ������ƴ����ʽ��
	 */
	public PinyinBuilder()
	{
		format = new HanyuPinyinOutputFormat();
		format.setCaseType(getCaseType());
		format.setToneType(getToneType());
		format.setVCharType(getVCharType());
	}

	/**
	 * Define the output format of character '��' <br>
	 * WITH_U_AND_COLON u:<br>
	 * WITH_V v<br>
	 * WITH_U_UNICODE ��<br>
	 */
	public abstract HanyuPinyinVCharType getVCharType();

	/**
	 * �뷵�������ı�����ʽ��<br>
	 * HanyuPinyinToneType.WITH_TONE_NUMBER da3<br>
	 * HanyuPinyinToneType.WITHOUT_TONE da<br>
	 * HanyuPinyinToneType.WITH_TONE_MARK d��<br>
	 */
	public abstract HanyuPinyinToneType getToneType();

	/**
	 * �뷵��ƴ���ַ��Ĵ�Сд�� <br>
	 * ������<br>
	 * Сд��HanyuPinyinCaseType.LOWERCASE;<br>
	 * ��д��HanyuPinyinCaseType.UPPERCASE;<br>
	 */
	public abstract HanyuPinyinCaseType getCaseType();
}