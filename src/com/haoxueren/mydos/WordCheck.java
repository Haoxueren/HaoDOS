package com.haoxueren.mydos;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.haoxueren.utils.FileUtils;

public class WordCheck
{
	private Scanner scanner;

	/** ͨ�����췽��ע��scanner���� */
	public WordCheck(Scanner scanner)
	{
		this.scanner = scanner;
	}

	/**
	 * @method �����ļ�������ָ��Ŀ¼�µİ������ļ����������ļ���
	 * @param wordsPath
	 *            Ҫ������Ŀ¼·����������֧�ֶ༶Ŀ¼��
	 */
	public void add(String word) throws IOException, MyException
	{
		if (word.trim().length() <= 1)
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
		// �������û�н�ȥ��������ʾ�ļ������ڣ�
		System.out.println("�õ��ʲ����ڣ��Ƿ����(Y/N)?");
		String command = scanner.nextLine();
		if ("Y".equalsIgnoreCase(command))
		{
			// �ڵ���ͼ��Ŀ¼�´������ļ���
			File file = new File(MyConstants.WORDS_PATH, word + ".png");
			file.createNewFile();
			System.out.println("���� " + word + "����ӳɹ� ��");
			// �Ա༭��ʽ���ļ���
			desktop.edit(file);
			return;
		}
		System.out.println("����" + word + "������ȡ����");
	}
}
