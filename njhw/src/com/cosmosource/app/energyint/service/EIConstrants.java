package com.cosmosource.app.energyint.service;

public class EIConstrants {

	/**
	 * 1立方米自来水转化为0.086千克标准煤
	 */
	public static double WATER_TO_STANDARD_COAL = 0.086;

	/**
	 * 1千瓦时电转化为0.404千克标准煤
	 */
	public static double POWER_TO_STANDARD_COAL = 0.404;

	/**
	 * 1Gj热力转化为34.12千克标准煤
	 */
	public static double HEATING_POWER_TO_STANDARD_COAL = 34.12;
	
	/**
	 * 1立方米天然气转化为1.2143千克标准煤
	 */
	public static double FLOW_POWER_TO_STANDARD_COAL = 1.2143;
	
	/**
	 * 1L汽油转化为1.0668千克标准煤
	 */
	public static double GASOLINE_POWER_TO_STANDARD_COAL = 1.0668;
	
	/**
	 * 1L柴油转化为1.2385千克标准煤
	 */
	public static double DIESOL_OIL_POWER_TO_STANDARD_COAL = 1.2385;

	/**
	 * 电耗
	 */
	public static final String KWH_MEASURE = "KWH_MEASURE";

	/**
	 * 电标准煤结果
	 */
	public static final String KEY_POWER_TO_STANDARD_COAL = "POWER_TO_STANDARD_COAL";

	/**
	 * 流量计 单位时间气耗（单位：Gj）
	 */
	public static final String FLOW_MEASURE_HEAT = "FLOW_MEASURE_HEAT";

	/**
	 * 流量计 单位时间气耗（单位：m³） int类型
	 */
	public static final String FLOW_MEASURE_FLOW = "FLOW_MEASURE_FLOW";

	/**
	 * 气 标准煤
	 */
	public static final String KEY_HEATING_POWER_TO_STANDARD_COAL = "HEATING_POWER_TO_STANDARD_COAL";

	/**
	 * 水耗
	 */
	public static final String WATER_MEASURE = "WATER_MEASURE";

	/**
	 * 水标注煤结果
	 */
	public static final String KEY_WATER_TO_STANDARD_COAL = "WATER_TO_STANDARD_COAL";
	
	/**
	 * 油耗
	 */
	public static final String OIL_MEASURE = "OIL_MEASURE";

	/**
	 * 油标注煤结果
	 */
	public static final String KEY_OIL_TO_STANDARD_COAL = "OIL_TO_STANDARD_COAL";

	/**
	 * 总能耗
	 */
	public static final String TOTAL_MEASURE = "TOTAL_MEASURE";

	/**
	 * 气标准煤百分比
	 */
	public static final String FLOW_PERCENTAGE = "FLOW_PERCENTAGE";

	/**
	 * 水标准煤百分比
	 */
	public static final String WATER_PERCENTAGE = "WATER_PERCENTAGE";

	/**
	 * 电标准煤百分比
	 */
	public static final String KWH_PERCENTAGE = "KWH_PERCENTAGE";
	
	/**
	 * 油标准煤百分比
	 */
	public static final String OIL_PERCENTAGE = "OIL_PERCENTAGE";

	/**
	 * 1:前一小时 2:前一日 3:前一月 4:当年累积 5:前一年
	 */
	public static final String TIME_TYPE_PRE_HOUR = "1";
	public static final String TIME_TYPE_PRE_DAY = "2";
	public static final String TIME_TYPE_PRE_MONTH = "3";
	public static final String TIME_TYPE_CURRENT_YEAR = "4";
	public static final String TIME_TYPE_PRE_YEAR = "5";

	/**
	 * 1：总能耗 2：总电耗 3：总水耗 4：总气耗
	 */
	public static final String ENERGY_ALL = "1";
	public static final String ENERGY_KWH = "2";
	public static final String ENERGY_WATER = "3";
	public static final String ENERGY_FLOW = "4";
	public static final String ENERGY_OIL = "5";
}
