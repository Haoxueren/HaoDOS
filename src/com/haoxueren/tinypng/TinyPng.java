package com.haoxueren.tinypng;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.haoxueren.config.ConfigHelper;
import com.haoxueren.config.ConsoleHelper;
import com.haoxueren.config.Values;
import com.haoxueren.main.OutputListener;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sun.misc.BASE64Encoder;

/** 调用TinyPng接口压缩图片； */
public class TinyPng
{
	private MediaType mediaType;
	private OutputListener listener;
	private OkHttpClient okHttpClient;
	private BASE64Encoder encoder;
	private String apiKey;
	private JSONArray keyArray;
	private int keyIndex, keyNumber;
	private JSONObject apiKeysJson;

	/** 初始化数据； */
	public TinyPng(OutputListener listener) throws Exception
	{
		this.listener = listener;
		okHttpClient = new OkHttpClient();
		mediaType = MediaType.parse("image/png,image/jpeg;");
		encoder = new BASE64Encoder();
		String tinyPngInfo = ConfigHelper.getConfig("TinyPngInfo", null);
		apiKeysJson = new JSONObject(tinyPngInfo);
		keyIndex = apiKeysJson.getInt("keyIndex");
		keyArray = apiKeysJson.getJSONArray("keys");
		keyNumber = keyArray.length();
		apiKey = encoder.encode(keyArray.getString(keyIndex).getBytes());
	}

	/** 遍历(不递归)压缩文件夹下的所有png和jpg图片； */
	public void sharkImages(File directory) throws Exception
	{
		File[] files = directory.listFiles();
		for (int i = 0; i < files.length; i++)
		{
			if (files[i].isFile())
			{
				listener.output("正在压缩第" + (i + 1) + "张图片：" + files[i].getName());
				shrinkImage(files[i]);
			}
		}
		listener.output("恭喜，所有图片压缩完毕！");
		Desktop.getDesktop().open(new File(directory, "TinyPng"));
	}

	/** 压缩单个图片，支持png和jpg格式； */
	public void shrinkImage(File sourceFile) throws Exception
	{
		if (sourceFile.getName().endsWith(".9.png"))
		{
			listener.output("本图为点9图，跳过：" + sourceFile.getName());
			listener.output(Values.DIVIDER);
			return;
		}

		if (!(sourceFile.getName().endsWith(".png") || sourceFile.getName().endsWith(".jpg")))
		{
			listener.output("不支持的文件类型：" + sourceFile.getName());
			listener.output(Values.DIVIDER);
			return;
		}
		// 上传图片到服务器；
		RequestBody body = RequestBody.create(mediaType, sourceFile);
		Request sharkRequest = new Request.Builder().url("https://api.tinify.com/shrink ")
				.addHeader("Authorization", "Basic " + apiKey).post(body).build();
		Response sharkResponse = okHttpClient.newCall(sharkRequest).execute();
		// 已使用的次数，每月免费500次；
		String compressionCount = sharkResponse.header("Compression-Count");
		int compressionCountInt = Integer.parseInt(compressionCount);
		listener.output("Compression-Count：" + compressionCount);
		if (compressionCountInt > 498)
		{
			// 切换TINYPNG_API_KEY；
			int nextKeyIndex = (keyIndex + 1) % keyNumber;
			apiKey = encoder.encode(keyArray.getString(nextKeyIndex).getBytes());
			// 将nextKeyIndex更新到配置文件中；
			apiKeysJson.put("keyIndex", nextKeyIndex);
			ConfigHelper.updateConfig("TinyPngInfo", apiKeysJson.toString());
		}
		// 获取压缩后图片的下载地址；
		JSONObject resultJson = new JSONObject(sharkResponse.body().string());
		String url = resultJson.getJSONObject("output").getString("url");

		// 从指定的URL下载图片；
		Request downloadRequest = new Request.Builder().url(url).build();
		Response downloadResponse = okHttpClient.newCall(downloadRequest).execute();
		InputStream inputStream = downloadResponse.body().byteStream();

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
		FileOutputStream outputStream = new FileOutputStream(sharkedFile);
		byte[] bytes = new byte[1024];
		int len = inputStream.read(bytes);
		while (len != -1)
		{
			outputStream.write(bytes, 0, len);
			len = inputStream.read(bytes);
		}
		outputStream.close();
		inputStream.close();
		listener.output("图片压缩成功：" + sharkedFile.getName());
		listener.output(Values.DIVIDER);
	}

}
