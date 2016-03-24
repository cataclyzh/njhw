package com.cosmosource.app.personnel.model;
/**
 * 
* @description: 客服人员首页统计信息
* @author herb
* @date May 10, 2013 6:27:51 PM
 */
public class CusServiceCountInfo {
	//入住单位总数
	private int innerUnit;
	//入住人员总数
	private int innerUser;
	//累计访客数
	private int sumVisitor;
	//日闸机流量
	private int dailyZjCount;
	//停车场车位数
	private int partyCount;
	//固定（免费）车位数
	private int freePartyCount;
	//日均车流量
	private int dailyAvgCarCount;
	//今日车流量
	private int dailyCarCount;
	
	
	public int getInnerUnit() {
		return innerUnit;
	}
	public void setInnerUnit(int innerUnit) {
		this.innerUnit = innerUnit;
	}
	public int getInnerUser() {
		return innerUser;
	}
	public void setInnerUser(int innerUser) {
		this.innerUser = innerUser;
	}
	public int getSumVisitor() {
		return sumVisitor;
	}
	public void setSumVisitor(int sumVisitor) {
		this.sumVisitor = sumVisitor;
	}
	public int getDailyZjCount() {
		return dailyZjCount;
	}
	public void setDailyZjCount(int dailyZjCount) {
		this.dailyZjCount = dailyZjCount;
	}
	public int getPartyCount() {
		return partyCount;
	}
	public void setPartyCount(int partyCount) {
		this.partyCount = partyCount;
	}
	public int getFreePartyCount() {
		return freePartyCount;
	}
	public void setFreePartyCount(int freePartyCount) {
		this.freePartyCount = freePartyCount;
	}
	public int getDailyAvgCarCount() {
		return dailyAvgCarCount;
	}
	public void setDailyAvgCarCount(int dailyAvgCarCount) {
		this.dailyAvgCarCount = dailyAvgCarCount;
	}
	public int getDailyCarCount() {
		return dailyCarCount;
	}
	public void setDailyCarCount(int dailyCarCount) {
		this.dailyCarCount = dailyCarCount;
	}
	
	
}
