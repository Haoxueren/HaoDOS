package com.haoxueren.autocode;

/** 生成代码的工厂； */
public class CodeFactory implements LayoutParserListener
{
	private CodeFactoryListener listener;

	public CodeFactory(CodeFactoryListener listener)
	{
		this.listener = listener;
	}

	/**
	 * 生成代码；
	 * 
	 * @param xmlFile
	 */
	public void process(String xmlFile)
	{
		try
		{
			LayoutParser parser = new LayoutParser(this);
			parser.parseLayout(xmlFile);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 根据类名和id生成代码的方法；
	 * 
	 * @param className
	 *            控件的类名；
	 * @param id
	 *            控件的id；
	 */
	@Override
	public void onParseXml(String fullName, String id)
	{
		// 获取控件的SimpleClassName；
		String className = getSimpleName(fullName);
		// 根据id生成控件的变量名；
		String fieldName = getFieldName(id);
		fieldName = fieldName + className;
		// 生成控件的id；
		String intId = id.replace("@+id/", "R.id.");
		listener.onParseLayout(className, fieldName, intId);
	}

	/*********************** 【以下是封装方法区】 ***********************/

	/** 从全类名中提取类名； */
	private String getSimpleName(String fullName)
	{
		int lastDotIndex = fullName.lastIndexOf('.');
		lastDotIndex = lastDotIndex == -1 ? 0 : lastDotIndex + 1;
		String className = fullName.substring(lastDotIndex);
		return className;
	}

	/** 从控件id中提取变量名； */
	public String getFieldName(String id)
	{
		String fieldName = id.replace("@+id/", "");
		String[] names = fieldName.split("_");
		if (names.length == 1)
		{
			fieldName = names[0];
		} else
		{
			fieldName = names[1];
		}
		return fieldName;
	}

}
