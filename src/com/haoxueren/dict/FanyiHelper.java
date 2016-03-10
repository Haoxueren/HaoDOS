package com.haoxueren.dict;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

/** 翻译帮助类； */
public class FanyiHelper
{
	/**
	 * 爱词霸翻译；<br>
	 * 返回翻译结果(JSON)<br>
	 */
	public static void icibaFanyi(String words) throws Exception
	{
		System.out.println("\t" + words.toLowerCase());
		// 要请求的地址；
		URL url = new URL("http://fy.iciba.com/ajax.php?a=fy");
		// 打开与服务器的连接；
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// 允许向服务器输出数据；
		connection.setDoOutput(true);
		// 允许从服务器接收数据；
		connection.setDoInput(true);
		// 设置请求方式为POST；
		connection.setRequestMethod("POST");
		// 构造POST请求参数；
		String params = "f=auto&t=auto&w=" + words;
		// 把请求参数发送给服务器；
		connection.getOutputStream().write(params.getBytes());
		// 获取服务器返回的数据；
		InputStream inputStream = connection.getInputStream();
		// 读取服务器返回的数据；
		StringBuilder builder = new StringBuilder();
		byte[] bytes = new byte[1024];
		int len = inputStream.read(bytes);
		while (len != -1)
		{
			builder.append(new String(bytes, 0, len));
			len = inputStream.read(bytes);
		}
		// 解析服务器返回的数据；
		String result = builder.toString();
		JSONObject jsonObject = new JSONObject(result);
		JSONObject content = jsonObject.getJSONObject("content");
		JSONArray wordMeans = content.optJSONArray("word_mean");
		// 如果word_mean字段为空，取out字段；
		if (wordMeans == null)
		{
			String out = content.getString("out");
			System.out.println(out);
			return;
		}
		// 如果word_mean不为空，遍历内容；
		for (Object wordMean : wordMeans)
		{
			System.out.println(wordMean);
		}
		return;
	}

}
