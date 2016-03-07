package com.haoxueren.gtd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.haoxueren.config.Values;
import com.haoxueren.helper.TextHelper;

/** 基于游戏原理设计的GTD系统； */
@SuppressWarnings("unchecked")
public class GameGtd
{
	/**
	 * 添加一条待办事项；<br>
	 */
	public static void addTask(String statusText, String eventText, String... tagArray) throws Exception
	{
		// 获取XML文档根节点；
		File xmlFile = new File(Values.DATABASE, XmlField.GameGTD);
		Document document = getDocument(xmlFile);
		Element root = document.getRootElement();
		// 添加任务节点；
		Element task = root.addElement("task");
		task.addAttribute("id", root.elements().size() + "");
		// 添加任务当前状态；
		Element status = task.addElement(XmlField.STATUS);
		status.setText(statusText.toUpperCase());
		// 添加任务内容；
		Element event = task.addElement(XmlField.TEXT);
		event.setText(eventText);
		// 添加任务标签；
		Element tags = task.addElement("tags");
		for (String tagText : tagArray)
		{
			if (TextHelper.notEmpty(tagText))
			{
				Element tag = tags.addElement(XmlField.TAG);
				tag.setText(tagText);
			}
		}
		// 添加任务创建时间；
		Element createTime = task.addElement("TodoTime");
		createTime.addText(new Date().toLocaleString());
		// 将Document保存到本地XML；
		storeXml(document, xmlFile);
		System.out.println("add success：" + statusText + " " + eventText + " TAGS=" + Arrays.toString(tagArray));
	}

	/** 修改任务内容或状态； */
	public static void updateTask(String id, String status, String taskText) throws Exception
	{
		File xmlFile = new File(Values.DATABASE, XmlField.GameGTD);
		Document document = getDocument(xmlFile);
		Element rootElement = document.getRootElement();
		List<Element> tasks = rootElement.elements("task");
		for (Element task : tasks)
		{
			if (id.equals(task.attributeValue("id")))
			{
				// 更新任务内容；
				if (TextHelper.notEmpty(taskText))
				{
					task.element(XmlField.TEXT).setText(taskText);
				}
				// 更新任务状态；
				String localeTime = new Date().toLocaleString();
				if ("TODO".equalsIgnoreCase(status))
				{
					task.element(XmlField.STATUS).setText("TODO");
					getChildElement(task, "TodoTime").setText(localeTime);
				} else if ("DOING".equalsIgnoreCase(status))
				{
					task.element(XmlField.STATUS).setText("DOING");
					task.addElement("DoingTime").setText(localeTime);
					getChildElement(task, "DoingTime").setText(localeTime);
				} else if ("DONE".equalsIgnoreCase(status))
				{
					task.element(XmlField.STATUS).setText("DONE");
					getChildElement(task, "DoneTime").setText(localeTime);
				}
				System.out.println("update success：ID=" + task.attributeValue("id") + " STATUS="
						+ task.elementText(XmlField.STATUS) + " TEXT=" + task.elementText(XmlField.TEXT));
			}
		}
		// 将Document保存到本地XML；
		storeXml(document, xmlFile);
	}

	/** 获取对应名称的子节点，如果子节点不存在，就创建； */
	private static Element getChildElement(Element task, String child)
	{
		Element element = task.element(child);
		if (element == null)
		{
			return task.addElement(child);
		} else
		{
			return element;
		}
	}

	/**
	 * 根据任务状态和标签查询任务；<br>
	 */
	public static void listTask(String status, String... tags) throws Exception
	{
		File xmlFile = new File(Values.DATABASE, XmlField.GameGTD);
		Document document = getDocument(xmlFile);
		Element rootElement = document.getRootElement();
		List<Element> newTasks = new ArrayList<>();
		// 获取所有任务节点；
		List<Element> tasks = rootElement.elements("task");
		for (Element task : tasks)
		{
			// 判断任务的状态是否符合查询条件；
			boolean statusFlag = checkStatus(task, status);
			boolean tagsFlag = checkTags(task, tags);
			if (statusFlag && tagsFlag)
			{
				newTasks.add(task);
				System.out.println("[" + task.elementText(XmlField.STATUS) + "]" + task.attributeValue("id") + "、"
						+ task.elementText(XmlField.TEXT));
			}
		}
		System.out.println("success: " + newTasks.size() + " matched tasks!");
	}

	/*********************** 【以下是封装方法区】 ***********************/

	/** 判断任务的状态是否满足条件； */
	private static boolean checkStatus(Element task, String status)
	{
		if (TextHelper.isEmpty(status))
		{
			return true;
		}

		if (TextHelper.notEmpty(status))
		{
			String taskStatus = task.element(XmlField.STATUS).getText();
			if (status.equalsIgnoreCase(taskStatus))
			{
				return true;
			}
		}
		return false;
	}

	/** 检查任务的标签是否满足条件； */
	private static boolean checkTags(Element task, String... tags)
	{
		// 如果没有筛选标签，返回true；
		if (tags == null || tags.length == 0)
		{
			return true;
		}
		// 如果有筛选标签，仅当任务标签集包括筛选标签集时，返回true；
		List<Element> tagList = task.element("tags").elements("tag");
		String[] xmlTags = new String[tagList.size()];
		for (int i = 0; i < tagList.size(); i++)
		{
			xmlTags[i] = tagList.get(i).getText().toUpperCase();
		}
		// 如果任务标签小于筛选标签，肯定不满足条件；
		if (xmlTags.length < tags.length)
		{
			return false;
		}
		// 判断xmlTags是否全部包含tags；
		for (String tag : tags)
		{
			Arrays.sort(xmlTags);// 使用二分查找必须先对数组进行排序；
			int index = Arrays.binarySearch(xmlTags, tag.toUpperCase());
			if (index < 0)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 读取本地XML文件获取Document对象；<br>
	 * 如果本地XML不存在，就在内在创建一个Document对象；<br>
	 * Document对象默认的根节点为"GTD"。<br>
	 */
	private static Document getDocument(File xmlFile) throws Exception
	{
		if (!xmlFile.exists())
		{
			// 获取本地XML文件，如果不存在，就创建；
			File parentFile = xmlFile.getParentFile();
			if (!parentFile.exists())
			{
				parentFile.mkdirs();
			}
			// 创建一个文档对象，并添加根节点GTD；
			Document document = DocumentHelper.createDocument();
			document.addElement("GTD");
			return document;
		} else
		{
			// 如果本地XML文件已存在，直接解析；
			SAXReader saxReader = new SAXReader();
			// 以GBK编码读取XML文件；
			InputStreamReader reader = new InputStreamReader(new FileInputStream(xmlFile), "UTF-8");
			return saxReader.read(reader);
		}
	}

	/**
	 * 将Document序列化到本地XML文件中；<br>
	 * 默认为GBK编码；<br>
	 */
	private static void storeXml(Document document, File xmlFile) throws IOException
	{
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 内容编码设置为UTF-8；
		format.setEncoding("UTF-8");
		OutputStream outputStream = new FileOutputStream(xmlFile);
		// 文件保存的编码也设置为UTF-8编码；
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
		XMLWriter writer = new XMLWriter(outputStreamWriter, format);
		writer.write(document);
		writer.close();
	}

}
