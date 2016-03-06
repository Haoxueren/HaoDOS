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

	/** 使用爱词霸翻译指定的单词； */
	public static void translate(String word) throws Exception
	{
		if (TextHelper.isEmpty(word))
		{
			System.out.println("要查询的单词不可为空！");
			return;
		}
		FileOutputStream outputStream = new FileOutputStream("D:/dict.txt");
		OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
		// 要请求的URL路径；
		String lowerCaseWord = word.toLowerCase();
		String url = "http://www.iciba.com/" + lowerCaseWord;
		System.out.println(url);
		// 获取见面的Document对象；
		Document document = Jsoup.connect(url).get();
		// 解析单词的(英/美)发音；
		Elements voice = document.select("div.word-voice");
		for (Element element : voice)
		{
			System.out.println(lowerCaseWord + "：" + element.text());
			writer.write(lowerCaseWord + "：" + element.text());
		}
		// 如果word-voice没有数据，从base-speak标签获取；
		Elements speak = document.select("div.base-speak");
		for (Element element : speak)
		{
			System.out.println(lowerCaseWord + "：" + element.text());
			writer.write(lowerCaseWord + "：" + element.text());
		}
		// 解析单词的释义；
		Element table = document.select("table").first();
		Elements select = table.select("tr");
		for (Element element : select)
		{
			System.out.println(element.text());
		}
		// 解析单词的各个状态词；
		Elements state = document.select("ul.word-state");
		for (Element element : state)
		{
			System.out.println(element.text());
		}
		writer.close();
	}
}
