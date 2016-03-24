package com.cosmosource.base.util;

import java.util.ArrayList;
import java.util.List;

public class Split {

	
	public static List split(String str, String separator) {
		List list = new ArrayList();
		if (str == null || str.equals("")) {
			return null;
		}
		int beginIndex = 0;
		int endIndex = 0;
		while (str.indexOf(separator, beginIndex) != -1) {
			endIndex = str.indexOf(separator, beginIndex);
			if (endIndex == 0) {
				beginIndex = separator.length();
				continue;
			}
			String st = str.substring(beginIndex, endIndex);
			list.add(st);
			beginIndex = endIndex + separator.length();
		}
		if (beginIndex != str.length()) {
			list.add(str.substring(beginIndex));
		}
		return list;
	}
	
	
	
	public static String split(String str, String separator,String ss) {
		String src="";
		if (str == null || str.equals("")) {
			return null;
		}
		int beginIndex = 0;
		int endIndex = 0;
		while (str.indexOf(separator, beginIndex) != -1) {
			endIndex = str.indexOf(separator, beginIndex);
			if (endIndex == 0) {
				beginIndex = separator.length();
				continue;
			}
			String st = str.substring(beginIndex, endIndex);
			st=ss+st+ss;
			src=src+st+separator;
			
			beginIndex = endIndex + separator.length();
		}
		if (beginIndex != str.length()) {
			src=src+ss+str.substring(beginIndex)+ss+separator;
		}
		return src.substring(0,src.length()-1);
	}
}
