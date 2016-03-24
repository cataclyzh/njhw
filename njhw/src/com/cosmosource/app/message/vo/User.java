package com.cosmosource.app.message.vo;

import java.util.HashMap;
import java.util.Map;

public class User {
	private long id;
	private String name;
	private long activityFlag;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getActivityFlag() {
		return activityFlag;
	}
	public void setActivityFlag(long activityFlag) {
		this.activityFlag = activityFlag;
	}
	
	public Map getMapVo(){
		Map map = new HashMap();
		map.put("id", id);
		map.put("name", name);
		map.put("activityFlag", activityFlag);
		return map;
	}
	
	public User setFromMap(Map map){
		this.id = Long.parseLong(map.get("ID").toString());
		this.name = (String) map.get("NAME");
		this.activityFlag = Long.parseLong(map.get("ACTIVITYFLAG").toString());
		return this;
	}
	
	@Override
	public String toString() {
		return "["+id+"]["+name+"]["+activityFlag+"]";
	}
}
