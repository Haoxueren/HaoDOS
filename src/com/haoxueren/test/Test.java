package com.haoxueren.test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sun.misc.BASE64Encoder;

import com.haoxueren.config.ConfigHelper;
import com.haoxueren.tinypng.TinyPng;

public class Test
{
	@org.junit.Test
	public void name() throws IOException
	{
		Request request = new Request.Builder().url("http://123.57.3.3:88/letou360/app/authImage").get().build();
		OkHttpClient client = new OkHttpClient();
		Response response = client.newCall(request).execute();
		System.out.println(response.body().contentType());
	}

	@org.junit.Test
	public void readMp3Info() throws Exception
	{
		String mp3 = "C:/Users/Haosir/Desktop/123.mp3";
		byte[] bytes = new byte[128];// 初始化标签信息的byte数组
		RandomAccessFile randomAccessFile = new RandomAccessFile(mp3, "r");// 随机读写方式打开MP3文件

		randomAccessFile.seek(randomAccessFile.length() - 128);// 移动到文件MP3末尾

		randomAccessFile.read(bytes);// 读取标签信息

		randomAccessFile.close();// 关闭文件

		// 获得TAG_V1中的各个内容
		String SongName = new String(bytes, 3, 30, "utf-8").trim();// 歌曲名称

		String Artist = new String(bytes, 33, 30, "utf-8").trim();// 歌手名字

		String Album = new String(bytes, 63, 30, "utf-8").trim();// 专辑名称

		String Year = new String(bytes, 93, 4, "utf-8").trim();// 出品年份

		String Comment = new String(bytes, 97, 28, "utf-8").trim();// 备注信息
		System.out.println(SongName);
		System.out.println(Artist);
		System.out.println(Album);
		System.out.println(Year);
		System.out.println(Comment);
	}

	@org.junit.Test
	public void login() throws Exception
	{
		JSONArray jsonArray = new JSONArray("[46d14ee1f7cf42ccabe17bec7d53bbec, c9d955952eda4afdb942cde29d1144f4]");
		System.out.println(jsonArray);
	}

	@org.junit.Test
	public void apiKeys() throws Exception
	{
		String keys = ConfigHelper.getConfig("tinypngapikeys", null);
		JSONArray jsonArray = new JSONArray(keys);
		System.out.println(jsonArray.get(1));
	}

	@org.junit.Test
	public void base64()
	{
		BASE64Encoder encoder = new BASE64Encoder();
		String b = encoder.encode("api:7v-DIkXh5k9n5RdWhrVCWZMOH192J4u3".getBytes());
		System.out.println(b);
	}

	@org.junit.Test
	public void tinyPng() throws Exception
	{
		TinyPng tinyPng = new TinyPng(null);
		File file = new File("d:/panda.png");
		tinyPng.shrinkImage(file);
	}

	/**
	 * 客户需求：切割以下文件路径："F:\乐投天下\课件与视频"；<br>
	 * 按以下格式输出："F:/乐投天下/课件与视频"；<br>
	 */
	public static void main(String[] args)
	{
		String pathname = "F:\\乐投天下\\课件与视频";
		// 注意：此处为4条“\”;
		String regex = "\\\\";
		String newPath = pathname.replaceAll(regex, "/");
		System.out.println(pathname.split(regex).length);
		System.out.println(newPath);
	}

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

}
