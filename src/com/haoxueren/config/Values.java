package com.haoxueren.config;

public interface Values
{
	// ���ݿ�·����
	String DATABASE = System.getProperty("user.dir") + "\\xml";
	// ������ʱ�ļ���Ŀ¼��
	String TEMP_DIR = System.getProperty("user.dir") + "\\temp_dir";
	// ������Ĭ��չʾ�����Ծ��䣻
	String TITLE_MOTTO = System.getProperty("user.name", "Haoxueren");
	// �����ݷ�ʽ��Ŀ¼��
	String SHORTCUTS = System.getProperty("user.dir") + "\\shortcuts";
	// ���浥��ͼ���Ŀ¼��
	String WORDS_PATH = System.getProperty("user.dir") + "\\words_set";
}
