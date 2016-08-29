package com.haoxueren.letou;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.haoxueren.main.OutputListener;

/** 正式环境，乐投天下抽奖功能； */
public class Letou360
{
	/** 保存用户登录成功后返回的Cookie； */
	private String loginCookie;

	/** 将信息输入到控制台； */
	private OutputListener listener;

	public Letou360(OutputListener listener)
	{
		this.listener = listener;
	}

	/**
	 * 乐投天下自动抽奖：正式环境；
	 * 
	 * @param username
	 *            乐投天下用户名；
	 * @param password
	 *            用户名对应的密码；
	 */
	public void luckyDraw(String username, String password) throws Exception
	{
		OkHttpClient okHttpClient = new OkHttpClient.Builder().cookieJar(new CookieJar()
		{
			private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

			@Override
			public void saveFromResponse(HttpUrl url, List<Cookie> cookies)
			{
				cookieStore.put(url, cookies);
				loginCookie = cookies.get(0).toString();
			}

			@Override
			public List<Cookie> loadForRequest(HttpUrl url)
			{
				List<Cookie> cookies = cookieStore.get(url);
				return cookies != null ? cookies : new ArrayList<Cookie>();
			}
		}).build();
		// 使用用户名和密码登录网站；
		listener.output("正在登录...");
		String loginUrl = "https://www.letou360.com/memberLogin";
		FormBody body = new FormBody.Builder().add("j_username", username).add("j_password", password)
				.add("button", "立即登录").build();
		Request request = new Request.Builder().url(loginUrl).post(body).build();
		Response response = okHttpClient.newCall(request).execute();
		if (response.toString().contains("jsessionid"))
		{
			listener.output("登录成功...");
		} else
		{
			listener.output("登录失败...");
			return;
		}
		// 进入抽奖页面获取ViewState；
		listener.output("正在抽奖...");
		String luckDrawUrlSpec = "https://www.letou360.com/lotteryDraw";
		Request request2 = new Request.Builder().url(luckDrawUrlSpec).addHeader("Cookie", loginCookie).build();
		Response execute = okHttpClient.newCall(request2).execute();
		Pattern compile = Pattern.compile("value=\"[+-]?\\d+:[+-]?\\d+");
		Matcher matcher = compile.matcher(execute.body().string());
		String viewState = null;
		while (matcher.find())
		{
			String target = matcher.group();
			viewState = target.replaceFirst("value=\"", "");
		}
		System.out.println("viewState：" + viewState);
		// 进入投资界面开始抽奖；
		FormBody formBody = new FormBody.Builder().add("form", "form").add("javax.faces.ViewState", viewState)
				.add("javax.faces.source", "form:j_idt36").add("javax.faces.partial.event", "click")
				.add("javax.faces.partial.execute", "form:j_idt36 form")
				.add("javax.faces.partial.render", "form:drawMessage").add("javax.faces.behavior.event", "action")
				.add("javax.faces.partial.ajax", "true").build();
		Request request3 = new Request.Builder().url(luckDrawUrlSpec).post(formBody).addHeader("Cookie", loginCookie)
				.build();
		Response response3 = okHttpClient.newCall(request3).execute();
		String text = response3.body().string();
		String tag = "<span id=\"form:message\" style=\"display:none;\">";
		int index = text.lastIndexOf(tag);
		text = text.substring(index + tag.length());
		text = text.substring(0, text.indexOf("</span"));
		listener.output(text);
	}

}
