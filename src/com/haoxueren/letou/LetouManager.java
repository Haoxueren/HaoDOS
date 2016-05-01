package com.haoxueren.letou;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/** 乐投天下管理后台； */
public class LetouManager
{
	public static void main(String[] args) throws Exception
	{
		for (int i = 0; i < 1; i++)
		{
			String millis = System.currentTimeMillis() + "";
			String title = "标题：" + millis.substring(10);
			// type：activity transaction
			pushMessage(title, "transaction");
		}
	}

	/** 登录乐投天下后台并自动上传APK； */
	public static void pushMessage(String title, String type) throws IOException
	{
		// 创建OkHttpClient对象(禁止跟随重定向)；
		OkHttpClient okHttpClient = new OkHttpClient.Builder().followRedirects(false).build();
		// 构建POST请求表单；
		FormBody formBody1 = new FormBody.Builder().add("j_username", "manager").add("j_password", "test998").build();
		// 构建POST请求体；
		Request request1 = new Request.Builder().url("http://123.57.3.3:88/letou360/login").post(formBody1).build();
		// 请求服务器；
		Response response1 = okHttpClient.newCall(request1).execute();
		// 获取服务器返回的Cookie；
		String cookie = response1.headers().get("Set-Cookie");
		System.out.println(cookie);
		// 登陆成功，从前一页获取ViewState；
		Request request2 = new Request.Builder().url("http://123.57.3.3:88/letou360/admin/user/pushMessageList.htm")
				.addHeader("Cookie", cookie).build();
		Response response2 = okHttpClient.newCall(request2).execute();
		Pattern compile = Pattern.compile("value=\"[+-]?\\d+:[+-]?\\d+");
		Matcher matcher = compile.matcher(response2.body().string());
		String viewState = null;
		while (matcher.find())
		{
			String target = matcher.group();
			viewState = target.replaceFirst("value=\"", "");
		}
		System.out.println(viewState);
		// 发布一条消息；transaction ios
		FormBody formBody3 = new FormBody.Builder().add("form", "form").add("form:j_idt186", title)
				.add("form:j_idt188", "简介：" + System.currentTimeMillis())
				.add("form:details", "<p>对生活充满热情，对未来充满信心。\nPublish by Haoxueren</p>").add("form:j_idt192", "android")
				.add("form:minInvestAmount", "").add("form:accountBalance", "").add("form:j_idt199", type)
				.add("form:j_idt204", "").add("form:saveBtn", "").add("javax.faces.ViewState", viewState).build();
		Request request3 = new Request.Builder().url("http://123.57.3.3:88/letou360/admin/user/pushMessageEdit.htm")
				.addHeader("Cookie", cookie).post(formBody3).build();
		okHttpClient.newCall(request3).execute();
		// 跳转到消息列表界面；
		Request request4 = new Request.Builder().url("http://123.57.3.3:88/letou360/admin/user/pushMessageList.htm")
				.addHeader("Cookie", cookie).build();
		Response response4 = okHttpClient.newCall(request4).execute();
		String html4 = response4.body().string();
		// 获取页面的ViewState；
		Matcher matcher4 = compile.matcher(html4);
		String viewState4 = null;
		while (matcher4.find())
		{
			String target = matcher4.group();
			viewState4 = target.replaceFirst("value=\"", "");
		}
		System.out.println(viewState4);
		// 使用Jsoup提取要发布消息的ID；
		Document document = Jsoup.parse(html4);
		Element table = document.getElementById("form:dataTable_data");
		String itemId = table.select("tr").first().select("td").last().select("a").last().attr("id");
		System.out.println(itemId);
		/** 请求服务器发布消息； */
		FormBody formBody5 = new FormBody.Builder().add("javax.faces.partial.ajax", "true")
				.add("javax.faces.source", itemId).add("javax.faces.partial.execute", itemId)
				.add("javax.faces.partial.render", "form:dataTable").add(itemId, itemId).add("form", "form")
				.add("form:searchTitle", "").add("form:searchIntroduction", "")
				.add("form:searchCreateTimeStart_input", "").add("form:searchCreateTimeEnd_input", "")
				.add("form:j_idt187", "").add("javax.faces.ViewState", viewState4).build();
		Request request5 = new Request.Builder().url("http://123.57.3.3:88/letou360/admin/user/pushMessageList.htm")
				.addHeader("Cookie", cookie).post(formBody5).build();
		Response response5 = okHttpClient.newCall(request5).execute();
		System.out.println("消息发布完毕！" + response5);
	}
}
