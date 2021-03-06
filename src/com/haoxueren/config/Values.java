﻿package com.haoxueren.config;

public interface Values
{
	String DIVIDER = "—————————————————————————\n";
	// 数据库路径；
	String DATABASE = System.getProperty("user.dir") + "\\xml";
	// 保存临时文件的目录；
	String TEMP_DIR = System.getProperty("user.dir") + "\\FileGroup";
	// 命令行默认展示的名言警句；
	String TITLE_MOTTO = System.getProperty("user.name", "Haoxueren");
	// 保存快捷方式的目录；
	String SHORTCUTS = System.getProperty("user.dir");
	// 保存单词图解的目录；
	String WORDS_PATH = System.getProperty("user.dir") + "\\words_set";
}
