package com.haoxueren.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.haoxueren.helper.StreamHelper;

/** 乐投天下自动抽奖功能； */
public class LuckDraw
{
	public static void main(String[] args) throws Exception
	{
		/*
		 * 登陆网站并获取Cookies；
		 */
		URL loginUrl = new URL("http://123.57.3.3:88/letou360/memberLogin");
		HttpURLConnection connection = (HttpURLConnection) loginUrl.openConnection();
		connection.setRequestProperty("Connection", "Keep-Alive");
		// 重要：HttpURLConnection如果跟随重定向，会导致HttpURLConnection无法获取服务器返回的Cookies；
		connection.setInstanceFollowRedirects(false);
		// 允许连接提交信息；
		connection.setDoOutput(true);
		// 网页提交方式；
		connection.setRequestMethod("POST");
		String params = "j_username=18268977847&j_password=test88&button=立即登录";
		OutputStream outputStream = connection.getOutputStream();
		outputStream.write(params.toString().getBytes());
		// 取到所用的Cookie；
		String cookie = connection.getHeaderField("Set-Cookie");
		String sessionId = cookie.substring(0, cookie.indexOf(";"));
		// 打印出获取到的数据；
		System.out.println("cookie：" + cookie);
		System.out.println("sessionId：" + sessionId);
		outputStream.close();
		/*
		 * GET请求抽奖界面获取ViewState；
		 */
		URL luckDrawUrl = new URL("http://123.57.3.3:88/letou360/lotteryDraw");
		HttpURLConnection connection1 = (HttpURLConnection) luckDrawUrl.openConnection();
		connection1.setRequestProperty("Cookie", cookie);
		InputStream inputStream1 = connection1.getInputStream();
		String content1 = StreamHelper.readInputStream(inputStream1);
		Pattern compile = Pattern.compile("value=\"[+-]?\\d+:[+-]?\\d+");
		Matcher matcher = compile.matcher(content1);
		String replaceFirst = "";
		while (matcher.find())
		{
			String group = matcher.group();
			replaceFirst = group.replaceFirst("value=\"", "");
			System.out.println(replaceFirst);
		}

		/*
		 * 点击抽奖按钮进行抽奖；
		 */
		HttpURLConnection connection2 = (HttpURLConnection) luckDrawUrl.openConnection();
		connection2.setRequestProperty("Connection", "Keep-Alive");
		connection2.setRequestProperty("Cookie", cookie);
		connection2.setDoOutput(true);
		connection2.setRequestMethod("POST");
		OutputStream outputStream2 = connection2.getOutputStream();
		String params2 = "form=form&javax.faces.ViewState="
				+ replaceFirst
				+ "&javax.faces.source=form:j_idt32&javax.faces.partial.event=click&javax.faces.partial.execute=form:j_idt32 form&javax.faces.partial.render=form:drawMessage&javax.faces.behavior.event=action&javax.faces.partial.ajax=true";
		outputStream2.write(params2.getBytes());
		InputStream inputStream2 = connection2.getInputStream();
		String content2 = StreamHelper.readInputStream(inputStream2);
		System.out.println(content2);
	}

}
