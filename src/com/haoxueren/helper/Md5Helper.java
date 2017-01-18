package com.haoxueren.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** Md5加密帮助类； */
public class Md5Helper
{
	/**
	 * 动态生成密码的算法；
	 */
	public static String encode(byte[] bytes) throws NoSuchAlgorithmException
	{
		char[] hexs =
		{ 'Q', '1', 'I', '9', 'H', '8', 'A', '6', 'O', '0', 'X', '9', 'U', '0', 'E', '8' };
		MessageDigest md5 = MessageDigest.getInstance("md5");
		byte[] digest = md5.digest(bytes);
		char[] chars = new char[16];
		for (int i = 0; i < digest.length; i++)
		{
			chars[i] = hexs[digest[i] & 0xf];
		}
		return new String(chars).substring(0, 9);
	}
}
