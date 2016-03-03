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
public class GameGTD
{
	@Test
	public void test() throws Exception
	{
		// String input = "$TODO ���� #����#ѧϰ#˼��";
		listTask("TODO", "����", "˼��", "ѧϰ");
	}

	/**
	 * ���һ���������<br>
	 * ָ���ʽ��$TODO TASK#TAG1#TAG2#TAG3...<br>
	 */
	public void addTask(String input) throws Exception
	{
		File xmlFile = new File(Values.DATABASE, "gtd.xml");
		// ��ȡXML�ĵ����ڵ㣻
		Document document = getDocument(xmlFile);
		Element root = document.getRootElement();
		// �����û�¼�����Ϣ��
		String[] array = input.split("[#|\\s]+");
		// �������ڵ㣻
		Element task = root.addElement("task");
		// ������񴴽�ʱ�䣻
		Element createTime = task.addElement("create");
		createTime.addText(new Date().toLocaleString());
		// �������ǰ״̬��
		Element status = task.addElement("status");
		status.setText(array[0].trim().substring(1));
		// ����������ݣ�
		Element event = task.addElement("event");
		event.setText(array[1].trim());
		// ��������ǩ��
		Element tags = task.addElement("tags");
		for (int i = 2; i < array.length; i++)
		{
			Element tag = tags.addElement("tag");
			tag.setText(array[i]);
		}
		// ��Document���浽����XML��
		storeXml(document, xmlFile);
	}

	/**
	 * ��������״̬�ͱ�ǩ��ѯ����<br>
	 * ����״̬��ALL,TODO,DOING DONE<br>
	 * ָ���ʽ��$LIST TODO #TAG1#TAG2#TAG3...
	 */
	public void listTask(String status, String... tags) throws Exception
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
			}
		}
	}

	/*********************** �������Ƿ�װ�������� ***********************/

	/** �ж������״̬�Ƿ����������� */
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

	/** �������ı�ǩ�Ƿ����������� */
	private boolean checkTags(Element task, String... tags)
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
			taskTags[i] = tagList.get(i).getText();
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
	private Document getDocument(File xmlFile) throws DocumentException
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
