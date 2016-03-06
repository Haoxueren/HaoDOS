package com.haoxueren.test;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class MsDosTest
{
	public static void main(String[] args) throws IOException
	{
		Desktop.getDesktop().open(new File("C:/Windows/system32/cmd.exe"));
	}



	@SuppressWarnings("unused")
	private void test01() throws MalformedURLException, IOException, ProtocolException, UnsupportedEncodingException
	{
		String path = "http://www.iciba.com/word";
		URL url = new URL(path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(5 * 1000);
		connection.setRequestMethod("GET");
		InputStream inputStream = connection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
		BufferedReader reader = new BufferedReader(inputStreamReader);
		String readLine = reader.readLine();
		while (readLine != null)
		{
			System.out.println(readLine);
			readLine = reader.readLine();
		}
	}
}
