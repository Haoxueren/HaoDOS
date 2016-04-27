package com.haoxueren.helper;

import java.util.Calendar;

import com.haoxueren.config.ConfigHelper;

/** 监听日期变动，本类用来实现程序第一次运行时执行抽奖功能； */
public class DateWatcher
{
	/**
	 * 判断现在是否为另一天了；
	 */
	public static boolean isAnotherDay() throws Exception
	{
		// 今日日期；
		Calendar calendar = Calendar.getInstance();
		int today = calendar.get(Calendar.DATE);
		// 本地储存的日期；
		String localdayString = ConfigHelper.getConfig("date", "0");
		// 比较是否为同一天；
		int localday = Integer.parseInt(localdayString);
		if (today != localday)
		{
			// 更新本地储存的日期；
			ConfigHelper.setConfig("date", String.valueOf(today));
			return true;
		}
		return false;
	}

}
