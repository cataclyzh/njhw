package com.cosmosource.app.energyint.service;

public class MdxStrings {

	public static final String INIT_MDX = "SELECT {} ON COLUMNS, {} ON ROWS FROM [Water Meter]";

	public static final String WATER_METER = "SELECT "
			+ "NON EMPTY CrossJoin([Organization].[Bureau].Members, {[Measures].[Water Meter Measures]}) ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Time].[Year].[Month].[Day]})} ON ROWS "
			+ "FROM [Water Meter]";

	public static final String KWH_METER = "SELECT "
			+ "NON EMPTY CrossJoin([Organization].[Bureau].Members, {[Measures].[KWH Meter Measures]}) ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Time].[Year].[Month].[Day].[Hour].Members})} ON ROWS "
			+ "FROM [Kwh Meter]";

	public static final String FLOW_METER = "SELECT "
			+ "NON EMPTY CrossJoin([Organization].[Bureau].Members, {[Measures].[Flow Meter Measures]}) ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Time].[Year].[Month].[Day].[Hour].Members})} ON ROWS "
			+ "FROM [Flow Meter]";

	public static final String KWH_CONSUME_MODEL = "SELECT "
			+ "NON EMPTY {Hierarchize({{[Time].[%s], [Time].[%s]}})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Organization].[Bureau].Members})} ON ROWS "
			+ "FROM [Kwh Meter]";

	public static final String WATER_CONSUME_MODEL = "SELECT "
			+ "NON EMPTY {Hierarchize({{[Time].[%s], [Time].[%s]}})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Organization].[Bureau].Members})} ON ROWS "
			+ "FROM [Water Meter]";

	public static final String FLOW_CONSUME_MODEL = "SELECT "
			+ "NON EMPTY {Hierarchize({{[Time].[%s], [Time].[%s]}})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Organization].[Bureau].Members})} ON ROWS "
			+ "FROM [Flow Meter]";

	public static final String ALL_KWH_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Time].[%s]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Location].[All Locations]})} ON ROWS "
			+ "FROM [Kwh Meter]";

	public static final String ALL_FLOW_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Time].[%s]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Location].[All Locations]})} ON ROWS "
			+ "FROM [Flow Meter]";

	public static final String ALL_WATER_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Time].[%s]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Location].[All Locations]})} ON ROWS "
			+ "FROM [Water Meter]";

	public static final String TOMONTH_WATER_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Time].[%d].[%d]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Location].[%s].[%s]})} ON ROWS "
			+ "FROM [Water Meter]";

	public static final String TOMONTH_FLOW_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Time].[%d].[%d]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Location].[%s].[%s]})} ON ROWS "
			+ "FROM [Flow Meter]";

	public static final String TEST_MDX = "SELECT "
			+ "NON EMPTY {Hierarchize({[Location].[All Locations]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Time].[All Times]})} ON ROWS "
			+ "FROM [Water Meter]";

	// 获取我所在楼层当日平均气耗
	public static final String MY_FLOOR_TODAY_FLOW_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Time].[%d].[%d].[%d]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Location].[%s].[%s]})} ON ROWS "
			+ "FROM [Flow Meter]";

	// 获取我所在楼层当日平均水耗
	public static final String MY_FLOOR_TODAY_WATER_CONSUME = "SELECT "
			+ "NON EMPTY Hierarchize({[Time].[%d].[%d].[%d]}) ON COLUMNS, "
			+ "NON EMPTY Hierarchize({[Location].[%s].[%s]}) ON ROWS "
			+ "FROM [Water Meter]";

	// 获取我所在房间当日平均电耗
	public static final String MY_ROOM_TODAY_KWH_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Time].[%d].[%d].[%d]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Location].[%s].[%s].[%s]})} ON ROWS "
			+ "FROM [Kwh Meter]";

	// 获取我所在楼层当月平均气耗
	public static final String MY_FLOOR_TOMONTH_FLOW_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Time].[%d].[%d]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Location].[%s].[%s]})} ON ROWS "
			+ "FROM [Flow Meter]";

	// 获取我所在楼层当月平均水耗
	public static final String MY_FLOOR_TOMONTH_WATER_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Time].[%d].[%d]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Location].[%s].[%s]})} ON ROWS "
			+ "FROM [Water Meter]";

	// 获取我所在房间当月平均电耗
	public static final String MY_ROOM_TOMONTH_KWH_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Time].[%d].[%d]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Location].[%s].[%s].[%s]})} ON ROWS "
			+ "FROM [Kwh Meter]";

	// 获取大厦当月总气耗
	public static final String BUILDING_TOMONTH_FLOW_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Time].[%d].[%d]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Location].[All Locations]})} ON ROWS "
			+ "FROM [Flow Meter]";

	// 获取大厦当月总水耗
	public static final String BUILDING_TOMONTH_WATER_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Time].[%d].[%d]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Location].[All Locations]})} ON ROWS "
			+ "FROM [Water Meter]";

	// 获取大厦当月总电耗
	public static final String BUILDING_TOMONTH_KWH_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Time].[%d].[%d]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Location].[All Locations]})} ON ROWS "
			+ "FROM [Kwh Meter]";

	// 获取大厦当月每天的总气耗
	public static final String BUILDING_EVERYDAY_FLOW_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Location].[All Locations]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Time].[%d].[%d].Children})} ON ROWS "
			+ "FROM [Flow Meter]";

	// 获取大厦当月每天的总水耗
	public static final String BUILDING_EVERYDAY_WATER_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Location].[All Locations]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Time].[%d].[%d].Children})} ON ROWS "
			+ "FROM [Water Meter]";

	// 获取大厦当月每天的总电耗
	public static final String BUILDING_EVERYDAY_KWH_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Location].[All Locations]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Time].[%d].[%d].Children})} ON ROWS "
			+ "FROM [Kwh Meter]";

	// 获取我所在楼层当月每天的总气耗
	public static final String MY_EVERYDAY_FLOW_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Location].[%s].[%s]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Time].[%d].[%d].Children})} ON ROWS "
			+ "FROM [Flow Meter]";

	// 获取我所在楼层当月每天的总水耗
	public static final String MY_EVERYDAY_WATER_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Location].[%s].[%s]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Time].[%d].[%d].Children})} ON ROWS "
			+ "FROM [Water Meter]";

	// 获取我所在房间当月每天的总电耗
	public static final String MY_EVERYDAY_KWH_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Location].[%s].[%s].[%s]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Time].[%d].[%d].Children})} ON ROWS "
			+ "FROM [Kwh Meter]";

	// 获取房间当月每天的能耗
	public static final String ROOM_CONSUME = "SELECT "
			+ "NON EMPTY {Hierarchize({[Location].[%s].[%s].[%s]})} ON COLUMNS, "
			+ "NON EMPTY {Hierarchize({[Time].[%d].[%d].Children})} ON ROWS "
			+ "FROM [Kwh Meter]";

}
