package com.haoxueren.utils;

import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * 这个类用来控制拼音的格式；
 */
public abstract class PinyinBuilder
{
	private HanyuPinyinOutputFormat format;

	public HanyuPinyinOutputFormat build()
	{
		return format;
	}

	/**
	 * 初始化汉语拼音格式；
	 */
	public PinyinBuilder()
	{
		format = new HanyuPinyinOutputFormat();
		format.setCaseType(getCaseType());
		format.setToneType(getToneType());
		format.setVCharType(getVCharType());
	}

	/**
	 * Define the output format of character 'ü' <br>
	 * WITH_U_AND_COLON u:<br>
	 * WITH_V v<br>
	 * WITH_U_UNICODE ü<br>
	 */
	public abstract HanyuPinyinVCharType getVCharType();

	/**
	 * 请返回音调的表现形式：<br>
	 * HanyuPinyinToneType.WITH_TONE_NUMBER da3<br>
	 * HanyuPinyinToneType.WITHOUT_TONE da<br>
	 * HanyuPinyinToneType.WITH_TONE_MARK dǎ<br>
	 */
	public abstract HanyuPinyinToneType getToneType();

	/**
	 * 请返回拼字字符的大小写； <br>
	 * 参数：<br>
	 * 小写：HanyuPinyinCaseType.LOWERCASE;<br>
	 * 大写：HanyuPinyinCaseType.UPPERCASE;<br>
	 */
	public abstract HanyuPinyinCaseType getCaseType();
}