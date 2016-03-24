package com.cosmosource.app.property.model;

/**
 * @description: 物业常量类
 * @author tangtq
 * @date 2013-7-19 10:06:12
 */
public class Constant {
	/**
	 * 已确认
	 */
	public final static String GR_REPAIR_STATE_CONFIRMED = "0";
	/**
	 * 已派发
	 */
	public final static String GR_REPAIR_STATE_DISTRIBUTED = "1";
	/**
	 * 已完成
	 */
	public final static String GR_REPAIR_STATE_COMPLETED = "2";
	/**
	 * 已评价
	 */
	public final static String GR_REPAIR_STATE_EVALUATED = "3";
	/**
	 * 已挂起
	 */
	public final static String GR_REPAIR_STATE_SUSPENDED = "4";
	
	/**
	 * 初始库存
	 */
	public final static String GR_STORAGE_INVENTORY_INIT = "0";
	
	/**
	 * 入库标识
	 */
	public final static Integer GR_IN_STORAGE_FLAG = Integer.parseInt("0");
	
	/**
	 * 出库标识
	 */
	public final static Integer GR_OUT_STORAGE_FLAG = Integer.parseInt("1");
	
	/**
	 * 逗号
	 */
	public final static String SPLIT_COMMA = ",";
	
	/**
	 * 美元号
	 */
	public final static String SPLIT_DOLLAR = "$";
	
	/**
	 * 竖线
	 */
	public final static String SPLIT_LINE = "|";
	
	/**
	 * 使用中的巡查班次
	 */
	public final static String GR_PATROL_SCHEDULE_STATE_RUNNING = "0";
	/**
	 * 停止中的巡查班次
	 */
	public final static String GR_PATROL_SCHEDULE_STATE_STOPPED = "1";
	
	/**
	 * 使用中的巡查路线
	 */
	public final static String GR_PATROL_LINE_STATE_RUNNING = "0";
	/**
	 * 停止中的巡查路线
	 */
	public final static String GR_PATROL_LINE_STATE_STOPPED = "1";
	
	/**
	 * 使用中的员工定位卡
	 */
	public final static String GR_PATROL_POSITION_CARD_STATE_RUNNING = "0";
	/**
	 * 停止中的员工定位卡
	 */
	public final static String GR_PATROL_POSITION_CARD_STATE_STOPPED = "1";
	
	/**
	 * 巡查记录未处理
	 */
	public final static String GR_PATROL_RECORD_NOT_DEAL = "0";
	/**
	 * 巡查记录已处理
	 */
	public final static String GR_PATROL_RECORD_IS_DEAL = "1";
}
