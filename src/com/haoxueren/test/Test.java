package com.haoxueren.test;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test
{
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

	@org.junit.Test
	public void test01() throws IOException
	{
		File dir = new File("d:\\workspace");
		tree(dir);
	}

	/** 列出指定目录的文件树； */
	public void tree(File dir)
	{
		File[] files = dir.listFiles();
		for (File file : files)
		{
			String path = file.getAbsolutePath();
			int num = 0;
			if (file.isDirectory())
			{
				System.out.println(path);
				System.out.println(path.contains("\\"));
				path=path.replaceAll("\\", "/");
				num = path.split("/").length - 1;
				System.out.println(num + "目录：" + file.getName());
				tree(file);
			} else
			{
				// num = path.split("\\").length - 1;
				System.out.println(num + "文件：" + file.getName());
			}
		}
	}

}
