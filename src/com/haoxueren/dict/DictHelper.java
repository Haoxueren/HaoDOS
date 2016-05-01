package com.haoxueren.dict;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.haoxueren.main.OutputListener;
import com.haoxueren.utils.TextHelper;

public class DictHelper
{
	private OutputListener listener;

	public DictHelper(OutputListener listener)
	{
		this.listener = listener;
	}

	/** 使用爱词霸翻译指定的单词； */
	public void translate(String word) throws Exception
	{
		if (TextHelper.isEmpty(word))
		{
			listener.output("要查询的单词不可为空！");
			return;
		}
		// 要请求的URL路径；
		String lowerCaseWord = word.toLowerCase().trim();
		String url = "http://www.iciba.com/" + URLEncoder.encode(lowerCaseWord, "UTF-8");
		// 获取见面的Document对象；
		Document document = Jsoup.connect(url).get();
		// 解析单词的(英/美)发音；
		Elements voice = document.select("div.word-voice");
		for (Element element : voice)
		{
			listener.output(lowerCaseWord + " " + element.text());
		}
		// 如果word-voice没有数据，从base-speak标签获取；
		Elements speak = document.select("div.base-speak");
		for (Element element : speak)
		{
			listener.output(lowerCaseWord + "：" + element.text());
		}
		// 解析单词的释义；
		Element table = document.select("table").first();
		Elements select = table.select("tr");
		for (Element element : select)
		{
			listener.output(element.text());
		}
		// 解析单词的各个状态词；
		Elements state = document.select("ul.word-state");
		for (Element element : state)
		{
			listener.output(element.text() + "\r\n");
		}
	}

	/** 备用方法：调用金山词霸API查询单词； */
	public void jscbApi(String word) throws IOException
	{
		OkHttpClient okHttpClient = new OkHttpClient();
		Request request = new Request.Builder().url("http://www.iciba.com/" + URLEncoder.encode(word, "UTF-8")).get()
				.build();
		Response response = okHttpClient.newCall(request).execute();
		Document document = Jsoup.parse(response.body().string());
		Element inBaseDiv = document.select("div.in-base").first();
		// 单词发音；
		System.out.println(inBaseDiv.select("div.base-speak").first().text());
		// 单词释义；
		Elements means = inBaseDiv.getElementsByTag("ul").first().select("li");
		for (Element element : means)
		{
			System.out.println(element.text());
		}

		// 单词变形；
		System.out.println(inBaseDiv.select("li.change").first().text());
	}
}
