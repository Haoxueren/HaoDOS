package com.haoxueren.dict;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.haoxueren.helper.TextHelper;

public class DictHelper
{
	public static void main(String[] args) throws Exception
	{
		translate("hello");
	}

	/** ʹ�ð��ʰԷ���ָ���ĵ��ʣ� */
	public static void translate(String word) throws Exception
	{
		if (TextHelper.isEmpty(word))
		{
			System.out.println("Ҫ��ѯ�ĵ��ʲ���Ϊ�գ�");
			return;
		}
		// Ҫ�����URL·����
		String lowerCaseWord = word.toLowerCase().trim();
		String encode = URLEncoder.encode(lowerCaseWord, "GBK");
		String url = "http://www.iciba.com/" + encode;
		// ��ȡ�����Document����
		Document document = Jsoup.connect(url).get();
		// �������ʵ�(Ӣ/��)������
		Elements voice = document.select("div.word-voice");
		for (Element element : voice)
		{
			System.out.println(lowerCaseWord + "��" + convert(element.text()));
		}
		// ���word-voiceû�����ݣ���base-speak��ǩ��ȡ��
		Elements speak = document.select("div.base-speak");
		for (Element element : speak)
		{
			System.out.println(lowerCaseWord + "��" + convert(element.text()));
		}
		// �������ʵ����壻
		Element table = document.select("table").first();
		Elements select = table.select("tr");
		for (Element element : select)
		{
			System.out.println(element.text());
		}
		// �������ʵĸ���״̬�ʣ�
		Elements state = document.select("ul.word-state");
		for (Element element : state)
		{
			System.out.println(element.text());
		}
		System.out.println("-------------------------------");
	}

	/** ת�����ʰԲ���ʶ��ĵ������ꣻ */
	public static String convert(String wordVoice) throws IOException
	{
		// ��Properties�ļ��м�������ӳ�䣻
		String path = System.getProperty("user.dir") + "/phonetic.properties";
		File propertiesFile = new File(path);
		if (!propertiesFile.exists())
		{
			propertiesFile.createNewFile();
			PrintWriter writer = new PrintWriter(propertiesFile);
			writer.write("# ����ע�ͣ���UTF-8 BOMͷӰ�죻");
		}
		InputStream inputStream = new FileInputStream(propertiesFile);
		Properties properties = new Properties();
		// ���ֱ���һ�£�
		properties.load(new InputStreamReader(inputStream, "UTF-8"));
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		// �滻�����е������ַ���
		for (Entry<Object, Object> entry : entrySet)
		{
			String key = ((String) entry.getKey()).trim();
			String value = ((String) entry.getValue()).trim();
			wordVoice = wordVoice.replaceAll(key, value);
		}
		// ���ش��������ꣻ
		return wordVoice;
	}
}
