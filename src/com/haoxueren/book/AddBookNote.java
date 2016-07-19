package com.haoxueren.book;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddBookNote
{

	public static void main(String[] args) throws IOException
	{
		OkHttpClient client = new OkHttpClient();
		String url = "https://api.bmob.cn/1/classes/GameScore";
		RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{\"name\":\"haosir\"}");
		Request request = new Request.Builder().url(url)
				.header("X-Bmob-Application-Id", "c93860ef9cb5203772b4041848061c04")
				.header("X-Bmob-REST-API-Key", "9da7b2e0bf839df445596bc04571da05")
				.header("Content-Type", "application/json").post(body).build();
		Response response = client.newCall(request).execute();
		System.out.println(response.body().string());
	}

}
