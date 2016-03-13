package com.haoxueren.test;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.haoxueren.helper.HttpHelper;
import com.haoxueren.main.OutputListener;

/** 乐投天下自动抽奖功能； */
public class LetouLuckDraw
{
	public static void luckyDraw(OutputListener listener) throws Exception
	{
		/*
		 * 登陆网站并获取Cookies；
		 */
		// POST请求参数；
		URL loginUrl = new URL("http://123.57.3.3:88/letou360/memberLogin");
		String loginParams = "j_username=18268977847&j_password=test88&button=立即登录";
		HttpURLConnection connection = (HttpURLConnection) loginUrl.openConnection();
		connection.setRequestProperty("Connection", "Keep-Alive");
		// 重要：禁止HttpURLConnection跟随重定向；
		connection.setInstanceFollowRedirects(false);
		// 允许连接对象提交信息；
		connection.setDoOutput(true);
		// 连接的请求方式；
		connection.setRequestMethod("POST");
		// 使用输出流把请求参数写出到服务器；
		OutputStream outputStream = connection.getOutputStream();
		outputStream.write(loginParams.getBytes());
		// 取到所用的Cookie；
		String cookie = connection.getHeaderField("Set-Cookie");
		// 获取当前会话的sessionId；
		String sessionId = cookie.substring(0, cookie.indexOf(";"));
		listener.output(sessionId);

		/*
		 * GET请求抽奖界面获取ViewState；
		 */
		// 抽奖页面地址；
		String luckDrawUrlSpec = "http://123.57.3.3:88/letou360/lotteryDraw";
		String content1 = HttpHelper.get(luckDrawUrlSpec, cookie);
		Pattern compile = Pattern.compile("value=\"[+-]?\\d+:[+-]?\\d+");
		Matcher matcher = compile.matcher(content1);
		String viewState = null;
		while (matcher.find())
		{
			String target = matcher.group();
			viewState = target.replaceFirst("value=\"", "");
		}

		/*
		 * 点击抽奖按钮进行抽奖；
		 */
		String luckDrawParams = "form=form&javax.faces.ViewState="
				+ viewState
				+ "&javax.faces.source=form:j_idt32&javax.faces.partial.event=click&javax.faces.partial.execute=form:j_idt32 form&javax.faces.partial.render=form:drawMessage&javax.faces.behavior.event=action&javax.faces.partial.ajax=true";
		String response = HttpHelper.post(luckDrawUrlSpec, luckDrawParams, cookie);
		String tag = "<span id=\"form:message\" style=\"display:none;\">";
		int index = response.lastIndexOf(tag);
		response = response.substring(index + tag.length());
		response = response.substring(0, response.indexOf("</span"));
		listener.output(response);
	}
}
