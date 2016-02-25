package com.haoxueren.mydos;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.haoxueren.utils.FileUtils;

public class WordCheck
{
	/**
	 * @method �����ļ�������ָ��Ŀ¼�µİ������ļ����������ļ���
	 * @param wordsPath
	 *            Ҫ������Ŀ¼·����������֧�ֶ༶Ŀ¼��
	 */
	public void add(String word) throws IOException, MyException
	{
		String wordTrim = word.trim();
		if (wordTrim.length() <= 1)
		{
			throw new MyException("�ף�����̫���˰ɣ�");
		}
		// ��װҪ��ѯ��Ŀ¼��
		File dir = new File(MyConstants.WORDS_PATH);
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
		File file = new File(MyConstants.WORDS_PATH, word + ".png");
		file.createNewFile();
		System.out.println(wordTrim + "����ӳɹ� ��");
		// ���ļ���
		desktop.open(file);
		desktop.edit(file);
	}
}
