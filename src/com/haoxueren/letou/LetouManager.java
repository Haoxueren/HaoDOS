package com.haoxueren.letou;

import java.io.IOException;

import okhttp3.FormBody;
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
			// type： activity transaction
			pushMessage(title, "transaction");
		}
	}

	/** 登录乐投天下后台并自动上传APK； */
	public static void pushMessage(String title, String type) throws IOException
	{
		// 构建POST请求表单；
		FormBody formBody1 = new FormBody.Builder().add("j_username", "manager").add("j_password", "test998").build();
		Response response1 = LetouHelper.okHttpPost("http://123.57.3.3:88/letou360/login", formBody1, "");
		// 获取服务器返回的Cookie；
		String cookie = response1.headers().get("Set-Cookie");
		System.out.println(cookie);
		// 登录成功，获取ViewState；
		Response response2 = LetouHelper.okHttpGet("http://123.57.3.3:88/letou360/admin/user/pushMessageList.htm",
				cookie);
		String viewState2 = LetouHelper.getViewState(response2.body().string());
		// 发布一条消息；transaction ios
		FormBody formBody3 = new FormBody.Builder()
				.add("form", "form")
				.add("form:j_idt186", title)
				.add("form:j_idt188", "\u003c\u003e简介：")
				.add("form:details",
						"<script>alert(document.cookie)</script><p>对生活充满热情，对未来充满信心。\nPublish by Haoxueren</p>")
				.add("form:j_idt192", "android,ios").add("form:j_idt192", "ios").add("form:minInvestAmount", "")
				.add("form:accountBalance", "").add("form:j_idt199", type).add("form:j_idt204", "")
				.add("form:saveBtn", "").add("javax.faces.ViewState", viewState2).build();
		LetouHelper.okHttpPost("http://123.57.3.3:88/letou360/admin/user/pushMessageEdit.htm", formBody3, cookie);
		// 跳转到消息列表界面；
		Response response4 = LetouHelper.okHttpGet("http://123.57.3.3:88/letou360/admin/user/pushMessageList.htm",
				cookie);
		String responseHtml4 = response4.body().string();
		// 获取页面的ViewState；
		String viewState4 = LetouHelper.getViewState(responseHtml4);
		// 使用Jsoup提取要发布消息的ID；
		Document document = Jsoup.parse(responseHtml4);
		Element table = document.getElementById("form:dataTable_data");
		String itemId = table.select("tr").first().select("td").last().select("a").last().attr("id");
		System.out.println(itemId);
		/** 请求服务器发布消息； */
		FormBody formBody5 = new FormBody.Builder().add("javax.faces.partial.ajax", "true")
				.add("javax.faces.source", itemId).add("javax.faces.partial.execute", itemId)
				.add("javax.faces.partial.render", "form:dataTable").add(itemId, itemId).add("form", "form")
				.add("javax.faces.ViewState", viewState4).build();
		LetouHelper.okHttpPost("http://123.57.3.3:88/letou360/admin/user/pushMessageList.htm", formBody5, cookie);
		System.out.println("消息发布完毕！\n");
	}
}
