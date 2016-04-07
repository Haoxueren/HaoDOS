package com.haoxueren.tinypng;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.haoxueren.config.ConsoleHelper;
import com.haoxueren.main.OutputListener;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/** 调用TinyPng接口压缩图片； */
public class TinyPng
{
	private MediaType mediaType;
	private OkHttpClient okHttpClient;
	private OutputListener listener;

	/** 初始化数据； */
	public TinyPng(OutputListener listener)
	{
		this.listener = listener;
		okHttpClient = new OkHttpClient();
		mediaType = MediaType.parse("image/png,image/jpeg;");
	}

	/** 遍历(不递归)压缩文件夹下的所有png和jpg图片； */
	public void sharkImages(File directory) throws Exception
	{
		File[] files = directory.listFiles();
		for (File file : files)
		{
			if (file.isFile())
			{
				shrinkImage(file);
			}
		}
		listener.output("恭喜，所有图片压缩完毕！");
		Desktop.getDesktop().open(new File(directory, "TinyPng"));
	}

	/** 压缩单个图片，支持png和jpg格式； */
	public void shrinkImage(File sourceFile) throws Exception
	{
		if (!(sourceFile.getName().endsWith(".png") || sourceFile.getName().endsWith(".jpg")))
		{
			listener.output("不支持的文件类型：" + sourceFile.getName());
			return;
		}
		// 上传图片到服务器；
		listener.output("正在压缩图片：" + sourceFile.getName());
		RequestBody body = RequestBody.create(mediaType, sourceFile);
		Request sharkRequest = new Request.Builder().url("https://api.tinify.com/shrink ")
				.addHeader("Authorization", "Basic YXBpOjd2LURJa1hoNWs5bjVSZFdoclZDV1pNT0gxOTJKNHUz").post(body)
				.build();
		Response sharkResponse = okHttpClient.newCall(sharkRequest).execute();

		// 获取压缩后图片的下载地址；
		JSONObject resultJson = new JSONObject(sharkResponse.body().string());
		String url = resultJson.getJSONObject("output").getString("url");

		// 从指定的URL下载图片；
		Request downloadRequest = new Request.Builder().url(url).build();
		Response downloadResponse = okHttpClient.newCall(downloadRequest).execute();
		InputStream byteStream = downloadResponse.body().byteStream();

		// 将下载的图片保存到本地；
		File sharkedFile = new File(sourceFile.getParent() + "/TinyPng", sourceFile.getName());
		if (sharkedFile.exists())
		{
			StringBuilder builder = new StringBuilder(sourceFile.getName());
			builder.insert(builder.lastIndexOf("."), "_" + System.currentTimeMillis());
			sharkedFile = new File(sourceFile.getParent() + "/TinyPng", builder.toString());
		}

		File sharkedDirectory = sharkedFile.getParentFile();
		if (!sharkedDirectory.exists())
		{
			// 如果目录不存在，创建目录；
			sharkedDirectory.mkdirs();
		}
		FileOutputStream fileOutputStream = new FileOutputStream(sharkedFile);
		byte[] bytes = new byte[1024];
		int len = byteStream.read(bytes);
		while (len != -1)
		{
			fileOutputStream.write(bytes, 0, len);
			len = byteStream.read(bytes);
		}
		fileOutputStream.close();
		byteStream.close();
		listener.output("图片压缩成功：" + sharkedFile.getName());
		listener.output("————————————————————————");
	}

}
