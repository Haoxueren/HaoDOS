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

/** ������Ϸԭ����Ƶ�GTDϵͳ�� */
@SuppressWarnings("unchecked")
public class GameGtd
{
	/**
	 * ���һ���������<br>
	 */
	public static void addTask(String statusText, String eventText, String... tagArray) throws Exception
	{
		// ��ȡXML�ĵ����ڵ㣻
		File xmlFile = new File(Values.DATABASE, "gtd.xml");
		Document document = getDocument(xmlFile);
		Element root = document.getRootElement();
		// �������ڵ㣻
		Element task = root.addElement("task");
		task.addAttribute("id", root.elements().size() + "");
		// �������ǰ״̬��
		Element status = task.addElement("status");
		status.setText(statusText.toUpperCase());
		// ����������ݣ�
		Element event = task.addElement("event");
		event.setText(eventText);
		// ��������ǩ��
		Element tags = task.addElement("tags");
		for (String tagText : tagArray)
		{
			if (TextHelper.notEmpty(tagText))
			{
				Element tag = tags.addElement("tag");
				tag.setText(tagText);
			}
		}
		// ������񴴽�ʱ�䣻
		Element createTime = task.addElement("TodoTime");
		createTime.addText(new Date().toLocaleString());
		// ��Document���浽����XML��
		storeXml(document, xmlFile);
		System.out.println("ADD SUCCESS��" + statusText + "��" + eventText + " TAGS��" + Arrays.toString(tagArray));
	}

	/** �޸��������ݻ�״̬�� */
	public static void updateTask(String id, String status, String event) throws Exception
	{
		File xmlFile = new File(Values.DATABASE, "gtd.xml");
		Document document = getDocument(xmlFile);
		Element rootElement = document.getRootElement();
		List<Element> tasks = rootElement.elements("task");
		for (Element task : tasks)
		{
			if (id.equals(task.attributeValue("id")))
			{
				// �����������ݣ�
				if (TextHelper.notEmpty(event))
				{
					task.element("event").setText(event);
				}
				// ��������״̬��
				String localeTime = new Date().toLocaleString();
				if ("TODO".equalsIgnoreCase(status))
				{
					task.element("status").setText("TODO");
					getChildElement(task, "TodoTime").setText(localeTime);
				} else if ("DOING".equalsIgnoreCase(status))
				{
					task.element("status").setText("DOING");
					task.addElement("DoingTime").setText(localeTime);
					getChildElement(task, "DoingTime").setText(localeTime);
				} else if ("DONE".equalsIgnoreCase(status))
				{
					task.element("status").setText("DONE");
					getChildElement(task, "DoneTime").setText(localeTime);
				}
			}
		}
		// ��Document���浽����XML��
		storeXml(document, xmlFile);
		System.out.println("UPDATE SUCCESS��ID=" + id + " STATUS=" + status + " EVENT=" + event);
	}

	/** ��ȡ��Ӧ���Ƶ��ӽڵ㣬����ӽڵ㲻���ڣ��ʹ����� */
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
	 * ��������״̬�ͱ�ǩ��ѯ����<br>
	 */
	public static void listTask(String status, String... tags) throws Exception
	{
		File xmlFile = new File(Values.DATABASE, "gtd.xml");
		Document document = getDocument(xmlFile);
		Element rootElement = document.getRootElement();
		List<Element> newTasks = new ArrayList<>();
		// ��ȡ��������ڵ㣻
		List<Element> tasks = rootElement.elements("task");
		for (Element task : tasks)
		{
			// �ж������״̬�Ƿ���ϲ�ѯ������
			boolean statusFlag = checkStatus(task, status);
			boolean tagsFlag = checkTags(task, tags);
			if (statusFlag && tagsFlag)
			{
				newTasks.add(task);
				System.out.println(task.attributeValue("id") + "��" + task.elementText("event"));
			}
		}
	}

	/*********************** �������Ƿ�װ�������� ***********************/

	/** �ж������״̬�Ƿ����������� */
	private static boolean checkStatus(Element task, String status)
	{
		if (TextHelper.isEmpty(status))
		{
			return true;
		}

		if (TextHelper.notEmpty(status))
		{
			String taskStatus = task.element("status").getText();
			if (status.equalsIgnoreCase(taskStatus))
			{
				return true;
			}
		}
		return false;
	}

	/** �������ı�ǩ�Ƿ����������� */
	private static boolean checkTags(Element task, String... tags)
	{
		// ���û��ɸѡ��ǩ������true��
		if (tags == null || tags.length == 0)
		{
			return true;
		}
		// �����ɸѡ��ǩ�����������ǩ������ɸѡ��ǩ��ʱ������true��
		List<Element> tagList = task.element("tags").elements("tag");
		String[] taskTags = new String[tagList.size()];
		for (int i = 0; i < tagList.size(); i++)
		{
			taskTags[i] = tagList.get(i).getText().toUpperCase();
		}
		// ��������ǩС��ɸѡ��ǩ���϶�������������
		if (taskTags.length < tags.length)
		{
			return false;
		}
		// �ж�tagArray�Ƿ����tags��
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
	 * ��ȡ����XML�ļ���ȡDocument����<br>
	 * �������XML�����ڣ��������ڴ���һ��Document����<br>
	 * Document����Ĭ�ϵĸ��ڵ�Ϊ"GTD"��<br>
	 */
	private static Document getDocument(File xmlFile) throws DocumentException
	{
		if (!xmlFile.exists())
		{
			// ��ȡ����XML�ļ�����������ڣ��ʹ�����
			File parentFile = xmlFile.getParentFile();
			if (!parentFile.exists())
			{
				parentFile.mkdirs();
			}
			// ����һ���ĵ����󣬲���Ӹ��ڵ�GTD��
			Document document = DocumentHelper.createDocument();
			document.addElement("GTD");
			return document;
		} else
		{
			// �������XML�ļ��Ѵ��ڣ�ֱ�ӽ�����
			SAXReader saxReader = new SAXReader();
			return saxReader.read(xmlFile);
		}
	}

	/**
	 * ��Document���л�������XML�ļ��У�<br>
	 * XML�ļ�Ĭ��ΪGBK���룻<br>
	 */
	private static void storeXml(Document document, File xmlFile) throws IOException
	{
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GBK");
		FileWriter fileWriter = new FileWriter(xmlFile);
		XMLWriter writer = new XMLWriter(fileWriter, format);
		writer.write(document);
		writer.close();
	}

}
