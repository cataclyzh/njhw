package com.cosmosource.app.energyint.model;

/**
 * 显示大厦本月单位面积平均能耗、我的今日能耗、本月能耗、本月节能贡献的Bean
 * 
 */
public class MyEnergyContributionModel {

	// 大厦本月人均能耗
	private double tomonthConsumePerson;

	// 我的本月能耗
	private double tomonthConsume;

	// 我的今日能耗
	private double todayConsume;

	// 我的本月节能贡献
	private double tomonthConsumeContirbution;

	public double getTomonthConsume() {
		return tomonthConsume;
	}

	public void setTomonthConsume(double tomonthConsume) {
		this.tomonthConsume = tomonthConsume;
	}

	public double getTodayConsume() {
		return todayConsume;
	}

	public void setTodayConsume(double todayConsume) {
		this.todayConsume = todayConsume;
	}

	public double getTomonthConsumeContirbution() {
		return tomonthConsumeContirbution;
	}

	public void setTomonthConsumeContirbution(double tomonthConsumeContirbution) {
		this.tomonthConsumeContirbution = tomonthConsumeContirbution;
	}

	public double getTomonthConsumePerson() {
		return tomonthConsumePerson;
	}

	public void setTomonthConsumePerson(double tomonthConsumePerson) {
		this.tomonthConsumePerson = tomonthConsumePerson;
	}

}
