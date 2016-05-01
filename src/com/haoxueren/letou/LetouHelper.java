package com.haoxueren.letou;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/** 使用程序操作乐投天下网站的帮助类； */
public class LetouHelper
{
	private static OkHttpClient okHttpClient;

	/**
	 * 获取单例的OkHttpClient对象；
	 * 
	 * @param followRedirects
	 *            是否跟随重定向(登陆时不要跟随重定向，否则获取不到Cookie)；
	 */
	public static OkHttpClient getOkHttpClient(boolean followRedirects)
	{
		if (okHttpClient == null)
		{
			okHttpClient = new OkHttpClient.Builder().followRedirects(followRedirects).build();
		}
		return okHttpClient;
	}

	/**
	 * 使用OkHttp执行GET请求；
	 */
	public static Response okHttpGet(String url, String cookie) throws IOException
	{
		Request request = new Request.Builder().url(url).addHeader("Cookie", cookie).get().build();
		return getOkHttpClient(false).newCall(request).execute();
	}

	/**
	 * 使用OkHttp执行POST请求；
	 * 
	 * @throws IOException
	 */
	public static Response okHttpPost(String url, FormBody formBody, String cookie) throws IOException
	{
		Request request = new Request.Builder().url(url).addHeader("Cookie", cookie).post(formBody).build();
		return getOkHttpClient(false).newCall(request).execute();
	}

	/** 获取Html代码中的ViewState的值； */
	public static String getViewState(String responseHtml)
	{
		Document document = Jsoup.parse(responseHtml);
		Element viewStateElement = document.getElementById("javax.faces.ViewState");
		String viewStateValue = viewStateElement.attr("value");
		return viewStateValue;
	}

}
