package com.haoxueren.config;

public interface Values
{
	String TEMP_DIR = System.getProperty("user.dir") + "\\temp_dir";
	String TITLE_MOTTO = System.getProperty("user.name", "Haoxueren");
	String SHORTCUTS = System.getProperty("user.dir") + "\\shortcuts";
	String WORDS_PATH = System.getProperty("user.dir") + "\\words_set";
}
