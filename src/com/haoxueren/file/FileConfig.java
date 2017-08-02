package com.haoxueren.file;

import java.io.FileReader;
import java.io.Reader;
import java.util.Properties;

/** 浏览文件的帮助类； */
public class FileConfig
{
	/** 根据文件路径的简称获取文件路径的全称； */
	public static String getPath(String path) throws Exception
	{
		Properties properties = new Properties();
		Reader reader = new FileReader(System.getProperty("user.dir") + "/config/filemap.properties");
		properties.load(reader);
		Object fullPath = properties.get(path.toUpperCase());
		reader.close();
		System.out.println("path " + fullPath);
		return fullPath == null ? path : fullPath.toString();
	}

	/** 根据文件路径的简称获取文件路径的全称； */
	public static String skipRegex() throws Exception
	{
		Properties properties = new Properties();
		Reader reader = new FileReader(System.getProperty("user.dir") + "/config/filemap.properties");
		properties.load(reader);
		Object path = properties.get("ignoreDirRegex");
		reader.close();
		return path.toString();
	}

	/** 获取本地定义的文件后缀名； */
	public static String[] getSuffix() throws Exception
	{
		Properties properties = new Properties();
		Reader reader = new FileReader(System.getProperty("user.dir") + "/config/filemap.properties");
		properties.load(reader);
		String suffix = properties.get("suffix").toString();
		reader.close();
		String[] array = suffix.split(",");
		return array;
	}

}
