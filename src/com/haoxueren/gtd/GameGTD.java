package com.haoxueren.gtd;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import com.haoxueren.config.Values;
import com.haoxueren.helper.TextHelper;

/** 基于游戏原理设计的GTD系统； */
@SuppressWarnings("unchecked")
public class GameGTD
{
	@Test
	public void test() throws Exception
	{
		// String input = "$TODO 读书 #今天#学习#思想";
		listTask("TODO", "今天", "思想", "学习");
	}

	/**
	 * 添加一条待办事项；<br>
	 * 指令格式：$TODO TASK#TAG1#TAG2#TAG3...<br>
	 */
	public void addTask(String input) throws Exception
	{
		File xmlFile = new File(Values.DATABASE, "gtd.xml");
		// 获取XML文档根节点；
		Document document = getDocument(xmlFile);
		Element root = document.getRootElement();
		// 处理用户录入的信息；
		String[] array = input.split("[#|\\s]+");
		// 添加任务节点；
		Element task = root.addElement("task");
		// 添加任务创建时间；
		Element createTime = task.addElement("create");
		createTime.addText(new Date().toLocaleString());
		// 添加任务当前状态；
		Element status = task.addElement("status");
		status.setText(array[0].trim().substring(1));
		// 添加任务内容；
		Element event = task.addElement("event");
		event.setText(array[1].trim());
		// 添加任务标签；
		Element tags = task.addElement("tags");
		for (int i = 2; i < array.length; i++)
		{
			Element tag = tags.addElement("tag");
			tag.setText(array[i]);
		}
		// 将Document保存到本地XML；
		storeXml(document, xmlFile);
	}

	/**
	 * 根据任务状态和标签查询任务；<br>
	 * 任务状态：ALL,TODO,DOING DONE<br>
	 * 指令格式：$LIST TODO #TAG1#TAG2#TAG3...
	 */
	public void listTask(String status, String... tags) throws Exception
	{
		File xmlFile = new File(Values.DATABASE, "gtd.xml");
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
			}
		}
	}

	/*********************** 【以下是封装方法区】 ***********************/

	/** 判断任务的状态是否满足条件； */
	private boolean checkStatus(Element task, String status)
	{
		if (TextHelper.isEmpty(status))
		{
			return true;
		}

		if (TextHelper.notEmpty(status))
		{
			String taskStatus = task.element("status").getText();
			if (status.equals(taskStatus))
			{
				return true;
			}
		}
		return false;
	}

	/** 检查任务的标签是否满足条件； */
	private boolean checkTags(Element task, String... tags)
	{
		// 如果没有筛选标签，返回true；
		if (tags == null || tags.length == 0)
		{
			return true;
		}
		// 如果有筛选标签，仅当任务标签集包括筛选标签集时，返回true；
		List<Element> tagList = task.element("tags").elements("tag");
		String[] taskTags = new String[tagList.size()];
		for (int i = 0; i < tagList.size(); i++)
		{
			taskTags[i] = tagList.get(i).getText();
		}
		// 如果任务标签小于筛选标签，肯定不满足条件；
		if (taskTags.length < tags.length)
		{
			return false;
		}
		// 判断tagArray是否包含tags；
		for (String tag : tags)
		{
			int index = Arrays.binarySearch(taskTags, tag);
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
	private Document getDocument(File xmlFile) throws DocumentException
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
			return saxReader.read(xmlFile);
		}
	}

	/**
	 * 将Document序列化到本地XML文件中；<br>
	 * XML文件默认为GBK编码；<br>
	 */
	private void storeXml(Document document, File xmlFile) throws IOException
	{
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GBK");
		FileWriter fileWriter = new FileWriter(xmlFile);
		XMLWriter writer = new XMLWriter(fileWriter, format);
		writer.write(document);
		writer.close();
	}

}
