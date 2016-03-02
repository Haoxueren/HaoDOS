package com.haoxueren.word;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.haoxueren.config.ConfigHelper;
import com.haoxueren.config.Keys;
import com.haoxueren.config.Values;
import com.haoxueren.helper.FileHelper;
import com.haoxueren.helper.FileUtils;

public class WordHelper
{
	private static String wrodsPath;
	/** �ѳ�ȡ���ʵĸ����� */
	private static int index, loop = 29;

	static
	{
		wrodsPath = ConfigHelper.getConfig(Keys.WORDS_PATH, Values.WORDS_PATH);
	}

	/** ��ʼ���洢�ļ���Ϣ�ļ��ϣ� */
	private static Map<Long, File> initFileMap()
	{
		Map<Long, File> fileInfoMap = new TreeMap<>();
		File directory = new File(wrodsPath);
		if (!directory.exists())
		{
			directory.mkdirs();
		}
		File[] files = directory.listFiles();
		for (File file : files)
		{
			// ��ȡ�ļ�������޸�ʱ�䣻
			long lastModified = file.lastModified();
			// ���ļ���Ϣ������fileInfoMap�У�
			fileInfoMap.put(lastModified, file);
		}
		return fileInfoMap;
	}

	/**
	 * �㷨��Ϊ֪�ʼ�2016.02.25�� Ҫ��index<=loop<=sum��<br>
	 * ���ͽ�ԭ��(lastModifyTime)�����ȡ���ʣ�<br>
	 */
	public static File getRandomWordFile()
	{
		Map<Long, File> map = initFileMap();
		Object[] objects = map.keySet().toArray();
		int sum = map.size();
		if (sum == 0)
		{
			return null;
		}
		int start = sum - (sum * (++index % loop) / loop);
		int random = RandomHelper.getRandomInt(start, sum);
		Object time = objects[random];
		File file = map.get(time);
		System.out.print("[" + start + "~" + sum + "]��" + index + "��" + getWordName(file));
		return file;
	}

	/** �����ļ���ȡ�������� */
	public static String getWordName(File word)
	{
		String name = word.getName();
		return name.substring(0, name.length() - 4);
	}

	/**
	 * @method �����ļ�������ָ��Ŀ¼�µİ������ļ����������ļ���
	 * @param wordsPath
	 *            Ҫ������Ŀ¼·����������֧�ֶ༶Ŀ¼��
	 */
	public static void addWord(String word) throws IOException
	{
		String wordTrim = word.trim();
		if (wordTrim.length() <= 1)
		{
			throw new IllegalArgumentException("�ף�����̫���˰ɣ�");
		}
		// ��װҪ��ѯ��Ŀ¼��
		String word_dir = ConfigHelper.getConfig(Keys.WORDS_PATH, Values.WORDS_PATH);
		File dir = new File(word_dir);
		FileHelper.mkdirs(dir);
		// ����Desktop����
		Desktop desktop = Desktop.getDesktop();
		// ��ȡ��Ŀ¼�������ļ��ļ��ϣ�֧�ֶ༶Ŀ¼����
		ArrayList<File> files = FileUtils.getDirsFiles(new ArrayList<File>(), dir);

		// �������ϣ���ѯ�������������ݣ�
		for (File file : files)
		{
			// �ж��ļ����Ƿ������������ݣ������ִ�Сд��
			if (file.getName().toLowerCase().contains(word.toLowerCase()))
			{
				// ����������򿪶�Ӧ���ļ���
				desktop.edit(file);
				System.out.println("�ļ� " + file.getName().split("\\.")[0] + " �Ѵ򿪣�");
				return;
			}
		}
		// �ڵ���ͼ��Ŀ¼�´������ļ���
		File file = new File(word_dir, word + ".png");
		ImageHelper.createImage(file);
		System.out.println(wordTrim + "����ӳɹ� ��");
		// ���ļ���
		desktop.open(file);
		desktop.edit(file);
	}

}
