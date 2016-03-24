package com.cosmosource.app.attendance.vo;

import java.util.ArrayList;
import java.util.List;

public class Type{
	
	public static final List<Type> LEAVE_LIST = new ArrayList<Type>();  //请假分类信息
	public static final List<Type> STATUS_LIST = new ArrayList<Type>(); //状态分类信息
	
	
	static{
		//请假分类
		LEAVE_LIST.add(new Type("1", "病假"));
		LEAVE_LIST.add(new Type("2", "事假"));
		LEAVE_LIST.add(new Type("3", "公出"));
		LEAVE_LIST.add(new Type("4", "其它"));
		
		//状态分类
		STATUS_LIST.add(new Type("0", "待审核"));
		STATUS_LIST.add(new Type("1", "申请通过"));
		STATUS_LIST.add(new Type("2", "申请驳回"));
		
	}
	
	String id;
	String name;

	public Type(String id,String name){
		this.id = id ;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Type [id=" + id + ", name=" + name + "]";
	}
	
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}

}
