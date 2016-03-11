package com.haoxueren.helper;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.haoxueren.config.ConsoleHelper;

public class DateHelper
{

	/** 打印今天的公历及农历日期； */
	public static void printDate() throws Exception
	{
		// 获取当前时间的日历对象；
		Calendar calendar = Calendar.getInstance();
		// 获取今年的年份；
		int year = calendar.get(Calendar.YEAR);
		// 获取当前的月份；
		int month = calendar.get(Calendar.MONTH) + 1;
		// 获取今天是几号；
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		// 格式化月日；
		DecimalFormat format = new DecimalFormat("00");
		String monthText = format.format(month);
		String dayText = format.format(day);
		// 请求今天对应的公历和农历日期；
		Document document = Jsoup.connect("http://gonglinongli.51240.com/").data("gongli_nian", year + "")
				.data("gongli_yue", monthText).data("gongli_ri", dayText).timeout(3000).post();
		Element table = document.select("table").last();
		Elements tds = table.select("td");
		System.out.println(tds.get(1).text());
		System.out.println(tds.get(3).text().replaceAll("（.+）", ""));
		ConsoleHelper.printDivider();
	}
}
