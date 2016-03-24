package com.cosmosource.app.utils;

import java.util.Map;

public class DataUtils {

	/*
	 * 取Map中对应名称的值
	 * 
	 * @param 
	 * 		m : Map
	 * 		name: map中的key
	 * @return Map中对应name的字符串,没有返回null
	 */
	public static String getMapValue(Map m, String name){
		String result = null;
		Object o = m.get(name);
		if(o != null){
			result = o.toString();
		}
		return result;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
