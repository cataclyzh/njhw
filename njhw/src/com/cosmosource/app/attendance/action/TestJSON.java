package com.cosmosource.app.attendance.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestJSON {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonArray.add("hello");
		jsonArray.add("world");
		jsonArray.add("java");
		jsonArray.add("linux");
		jsonObject.put("events", "123");
		System.out.println(jsonArray.toString());
	}

}
