package com.haoxueren.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test
{

	/**
	 * 客户需求：切割以下文件路径："F:\乐投天下\课件与视频"；<br>
	 * 按以下格式输出："F:/乐投天下/课件与视频"；<br>
	 */
	public static void main(String[] args)
	{
		String pathname = "F:\\乐投天下\\课件与视频";
		// 注意：此处为4条“\”;
		String regex = "\\\\";
		String newPath = pathname.replaceAll(regex, "/");
		System.out.println(pathname.split(regex).length);
		System.out.println(newPath);
	}

	@org.junit.Test
	public void method() throws Exception
	{
		Document document = Jsoup.connect("http://gonglinongli.51240.com/").data("gongli_nian", "2016")
				.data("gongli_yue", "08").data("gongli_ri", "15").post();
		Element table = document.select("table").last();
		Elements tdList = table.select("td");
		System.out.println(tdList.get(1).text());
		System.out.println(tdList.get(3).text().replaceAll("（.+）", ""));
	}





}
