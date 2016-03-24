package com.cosmosource.app.caller.model;
/**
* @description: 访客统计模型
* @author herb
* @date Apr 22, 2013 2:24:32 PM
 */
public class VMCountModel {
	//内部人员访客总数
	private int vmCount;
	//用户总数
	private int userCount;
	//超时人数
	private int expiredCount;
	//访客来访人数
	private int vmCome;
	//访客在线人数
	private int vmVisiting;
	//访客离开人数
	private int vmLeave;
	//用户来访人数
	private int userCome;
	//用户在线人数
	private int userVisiting;
	//用户离开人数
	private int userLeave;
	
	
	public int getVmCount() {
		return vmCount;
	}
	public void setVmCount(int vmCount) {
		this.vmCount = vmCount;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public int getExpiredCount() {
		return expiredCount;
	}
	public void setExpiredCount(int expiredCount) {
		this.expiredCount = expiredCount;
	}
	public int getVmCome() {
		return vmCome;
	}
	public void setVmCome(int vmCome) {
		this.vmCome = vmCome;
	}
	public int getVmVisiting() {
		return vmVisiting;
	}
	public void setVmVisiting(int vmVisiting) {
		this.vmVisiting = vmVisiting;
	}
	public int getVmLeave() {
		return vmLeave;
	}
	public void setVmLeave(int vmLeave) {
		this.vmLeave = vmLeave;
	}
	public int getUserCome() {
		return userCome;
	}
	public void setUserCome(int userCome) {
		this.userCome = userCome;
	}
	public int getUserVisiting() {
		return userVisiting;
	}
	public void setUserVisiting(int userVisiting) {
		this.userVisiting = userVisiting;
	}
	public int getUserLeave() {
		return userLeave;
	}
	public void setUserLeave(int userLeave) {
		this.userLeave = userLeave;
	} 
	
	
}
