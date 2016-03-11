package com.haoxueren.dict;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.haoxueren.config.ConsoleHelper;
import com.haoxueren.utils.TextHelper;

public class DictHelper
{
	/** 使用爱词霸翻译指定的单词； */
	public static void translate(String word) throws Exception
	{
		if (TextHelper.isEmpty(word))
		{
			System.out.println("要查询的单词不可为空！");
			return;
		}
		File file = new File(System.getProperty("user.dir"), "dict.txt");
		if (!file.exists())
		{
			file.createNewFile();
		}
		OutputStream out = new FileOutputStream(file);
		OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
		// 要请求的URL路径；
		String lowerCaseWord = word.toLowerCase().trim();
		String url = "http://www.iciba.com/" + lowerCaseWord;
		// 获取见面的Document对象；
		Document document = Jsoup.connect(url).get();
		// 解析单词的(英/美)发音；
		Elements voice = document.select("div.word-voice");
		for (Element element : voice)
		{
			writer.write(lowerCaseWord + "：" + element.text() + "\r\n");
			System.out.println(lowerCaseWord + "：" + element.text() + "\r\n");
		}
		// 如果word-voice没有数据，从base-speak标签获取；
		Elements speak = document.select("div.base-speak");
		for (Element element : speak)
		{
			writer.write(lowerCaseWord + "：" + element.text() + "\r\n");
			System.out.println(lowerCaseWord + "：" + element.text() + "\r\n");
		}
		// 解析单词的释义；
		Element table = document.select("table").first();
		Elements select = table.select("tr");
		for (Element element : select)
		{
			writer.write(element.text() + "\r\n");
			System.out.println(element.text() + "\r\n");
		}
		// 解析单词的各个状态词；
		Elements state = document.select("ul.word-state");
		for (Element element : state)
		{
			writer.write(element.text() + "\r\n");
			System.out.println(element.text() + "\r\n");
		}
		Desktop.getDesktop().open(file);
		ConsoleHelper.printDivider();
		writer.close();
	}

	/** 转换爱词霸不可识别的单词音标； */
	public String convert(String wordVoice) throws IOException
	{
		// 从Properties文件中加载音标映射；
		String path = System.getProperty("user.dir") + "/config/phonetic.properties";
		File propertiesFile = new File(path);
		if (!propertiesFile.exists())
		{
			propertiesFile.createNewFile();
			PrintWriter writer = new PrintWriter(propertiesFile);
			writer.write("# 首先注释，防UTF-8 BOM头影响；");
			writer.close();
		}
		InputStream inputStream = new FileInputStream(propertiesFile);
		Properties properties = new Properties();
		// 保持编码一致；
		properties.load(new InputStreamReader(inputStream, "UTF-8"));
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		// 替换音标中的特殊字符；
		for (Entry<Object, Object> entry : entrySet)
		{
			String key = ((String) entry.getKey()).trim();
			String value = ((String) entry.getValue()).trim();
			wordVoice = wordVoice.replaceAll(key, value);
		}
		// 返回处理后的音标；
		return wordVoice;
	}
}
