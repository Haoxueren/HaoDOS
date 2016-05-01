package com.haoxueren.layout;

/** 有新代码生成时调用此方法； */
public interface CodeFactoryListener
{
	/** 在本方法中拼装代码； */
	void onParseLayout(String className, String fieldName, String intId);
}
