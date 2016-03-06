package com.haoxueren.word;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.haoxueren.helper.TextHelper;

public class DictHelper
{
	public static void main(String[] args) throws Exception
	{
		translate("dict");
	}

	/** ʹ�ð��ʰԷ���ָ���ĵ��ʣ� */
	public static void translate(String word) throws Exception
	{
		if (TextHelper.isEmpty(word))
		{
			System.out.println("Ҫ��ѯ�ĵ��ʲ���Ϊ�գ�");
			return;
		}
		FileOutputStream outputStream = new FileOutputStream("D:/dict.txt");
		OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
		// Ҫ�����URL·����
		String lowerCaseWord = word.toLowerCase();
		String url = "http://www.iciba.com/" + lowerCaseWord;
		System.out.println(url);
		// ��ȡ�����Document����
		Document document = Jsoup.connect(url).get();
		// �������ʵ�(Ӣ/��)������
		Elements voice = document.select("div.word-voice");
		for (Element element : voice)
		{
			System.out.println(lowerCaseWord + "��" + element.text());
			writer.write(lowerCaseWord + "��" + element.text());
		}
		// ���word-voiceû�����ݣ���base-speak��ǩ��ȡ��
		Elements speak = document.select("div.base-speak");
		for (Element element : speak)
		{
			System.out.println(lowerCaseWord + "��" + element.text());
			writer.write(lowerCaseWord + "��" + element.text());
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
		writer.close();
	}
}
