package com.haoxueren.helper;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/** HttpURLConnection的帮助类； */
public class HttpHelper
{

	/** POST方式请求网络； */
	public static String post(String urlSpec, String params, String cookie) throws Exception
	{
		URL url = new URL(urlSpec);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setRequestProperty("Cookie", cookie);
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		OutputStream outputStream = connection.getOutputStream();
		outputStream.write(params.getBytes());
		InputStream inputStream = connection.getInputStream();
		String response = StreamHelper.readInputStream(inputStream);
		inputStream.close();
		outputStream.close();
		connection.disconnect();
		return response;
	}

	/** GET方式请求网络； */
	public static String get(String urlSpec, String cookie) throws Exception
	{
		URL url = new URL(urlSpec);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Cookie", cookie);
		InputStream inputStream = connection.getInputStream();
		String response = StreamHelper.readInputStream(inputStream);
		inputStream.close();
		connection.disconnect();
		return response;
	}

}
