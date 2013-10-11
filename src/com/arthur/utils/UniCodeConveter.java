package com.arthur.utils;

public class UniCodeConveter {

	
	/**
	 * unicode转字符串
	 * @param str
	 * @return
	 */
	public static String unicode2Str(String str) {
		StringBuffer sb = new StringBuffer();
		String[] arr = str.split("\\\\u");
		int len = arr.length;
		sb.append(arr[0]);
		for(int i=1; i<len; i++){
			String tmp = arr[i];
			char c = (char)Integer.parseInt(tmp.substring(0, 4), 16);
			sb.append(c);
			sb.append(tmp.substring(4));
		}
		return sb.toString();
	}

	/**
	 * 字符串转unicode
	 * @param str
	 * @return
	 */
	public static String str2Unicode(String str) {
		StringBuffer sb = new StringBuffer();
		char[] charArr = str.toCharArray();
		for (char ch : charArr) {
			if (ch > 128) {
				sb.append("\\u" + Integer.toHexString(ch));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}
/*
	public static void main(String[] args) {
		String str = "abc\\u811adef\\u672cghi";
		System.out.println(unicode2Str(str));
		System.out.println(str2Unicode("222ds测1试aa"));
	}*/
}
