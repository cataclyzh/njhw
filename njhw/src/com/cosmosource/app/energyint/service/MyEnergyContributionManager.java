package com.cosmosource.app.energyint.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cosmosource.app.energyint.model.EiMyselfInfo;
import com.cosmosource.app.energyint.model.MyEnergyChartModel;
import com.cosmosource.app.energyint.model.MyEnergyContributionModel;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.StringUtil;

/**
 * 获取我的节能贡献数值显示用的bean
 * 
 */
public class MyEnergyContributionManager extends BaseManager {

	// 本月
	public static int MON_TYPE_CUR = 0;
	
	// 上月
	public static int  MON_TYPE_LAST = 1;
	
	// 当前年份
	private int year;

	// 当前月份
	private int month;

	// 当前天
	private int day;

	// 我所在的楼
	private String mySeat;

	// 我所在的楼层
	private String myFloor;

	// 我所在的房间
	private String myRoom;

	// 大厦总人数
	//private int totalPeopleCount;

	// 我所在楼层的人数
	//private int floorPeopleCount;

	// 我所在房间的人数
	//private int roomPeopleCount;

	// 获取我的相关信息
	private EiReportManager eiReportManager;
	
	//大厦总房间数
	private int buildingAllRooms;
	
	//获取当前楼层的总房间数
	private int floorRoomsCount;

	public EiReportManager getEiReportManager() {
		return eiReportManager;
	}

	public void setEiReportManager(EiReportManager eiReportManager) {
		this.eiReportManager = eiReportManager;
	}

	// 我的相关信息
	private EiMyselfInfo eiMyselfInfo;

	// 用户ID
	private String userid;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public MyEnergyContributionManager() {
		// 获取当天的年、月、日
		Calendar today = Calendar.getInstance();
		year = today.get(Calendar.YEAR);
		month = today.get(Calendar.MONTH) + 1;
		day = today.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取我的能耗相关数据
	 * 
	 * @return
	 */
	public MyEnergyContributionModel getMyEnergyContributionModel() {
		MyEnergyContributionModel myEnergyContributionModel = new MyEnergyContributionModel();

		if (eiMyselfInfo == null) {
			eiMyselfInfo = eiReportManager.getMyselfInfo(userid);
		}
		// 获取我的信息数据有问题，数据都设置为0
		if (eiMyselfInfo == null) {
			myEnergyContributionModel.setTodayConsume(0.0);
			myEnergyContributionModel.setTomonthConsume(0.0);
			myEnergyContributionModel.setTomonthConsumeContirbution(0.0);
			myEnergyContributionModel.setTomonthConsumePerson(0.0);
			return myEnergyContributionModel;
		}

		// 获取我的所在的哪栋楼、楼层、房间
		mySeat = eiMyselfInfo.getSeat();
		myFloor = eiMyselfInfo.getFloor();
		myRoom = eiMyselfInfo.getRoom();

		// 获取大厦总人数、楼层人数、房间人数,大厦总房间数，当前楼层总房间数
//		totalPeopleCount = eiMyselfInfo.getPeopleCount();
//		floorPeopleCount = eiMyselfInfo.getFloorPeopleCount();
//		roomPeopleCount = eiMyselfInfo.getRoomPeopleCount();
		buildingAllRooms = eiMyselfInfo.getBuildingAllRooms();
		floorRoomsCount = eiMyselfInfo.getFloorRoomsCount();

		// 启动3个线程，获取我的当日能耗、我的当月能耗、大厦的当月能耗
		ExecutorService pool = null;
		pool = Executors.newFixedThreadPool(3);
		MyTodayConsumeThread myTodayConsumeThread = new MyTodayConsumeThread(
				year, month, day, mySeat, myFloor, myRoom,buildingAllRooms,floorRoomsCount);
		MyTomonthConsumeThread myTomonthConsumeThread = new MyTomonthConsumeThread(
				year, month, mySeat, myFloor, myRoom,buildingAllRooms,floorRoomsCount);
		BuildingTomonthConsumeThread buildingTomonthConsumeThread = new BuildingTomonthConsumeThread(
				year, month);
		pool.execute(myTodayConsumeThread);
		pool.execute(myTomonthConsumeThread);
		pool.execute(buildingTomonthConsumeThread);

		pool.shutdown();
		while (!pool.isTerminated()) {
			// all tasks have completed following shut down
		}

		// 我的当日能耗
		double myTodayConsume = myTodayConsumeThread.getTodayConsume();

		// 我的当月能耗
		double myTomonthConsume = EIUtils.doubleFormat(myTomonthConsumeThread
				.getTomonthConsume());

		// 大厦的当月能耗
		double buildingTomonthConsume = EIUtils
				.doubleFormat(buildingTomonthConsumeThread.getTomonthConsume());

		// 计算大厦的平均均能耗(房间为单位)
		double buildingTomonthConsumeEveryPerson = EIUtils.div(
				buildingTomonthConsume, buildingAllRooms);

		// 计算我的节能贡献
		double myConsumeContribution = EIUtils.doubleFormat(EIUtils.sub(
				buildingTomonthConsumeEveryPerson, myTomonthConsume));

		myEnergyContributionModel.setTodayConsume(myTodayConsume);
		myEnergyContributionModel.setTomonthConsume(myTomonthConsume);
		myEnergyContributionModel
				.setTomonthConsumePerson(buildingTomonthConsumeEveryPerson);
		myEnergyContributionModel
				.setTomonthConsumeContirbution(myConsumeContribution);

		return myEnergyContributionModel;
	}
	
	/**
	 *	如果当天能耗为空 则置为0.0
	 * @param  map 要搞的map 
	 * @param i 当月第几天
	 * @param type 0 本月 1 上月
	 */
	public static TreeMap<Integer,Double> setDeHashMap(TreeMap<Integer,Double> map,int i,int type)
	{
		if (0 == type)
		{
			for (int j = 1; j <=i; j++)
			{   
				if (null == map.get(j))
				{
					map.put(j, 0.0);
				}
			}
		}
		else if (1 == type)
		{   
			// 上月一共多少天
			Calendar calendar = Calendar.getInstance();
			Calendar time = Calendar.getInstance();
			time.clear();
			time.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
			time.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);// Calendar对象默认一月为0
			int lastDay = time.getActualMaximum(Calendar.DAY_OF_MONTH);// 上月份的天数
			for (int j = 1; j <=lastDay; j++)
			{   
				if (null == map.get(j))
				{
					map.put(j, 0.0);
				}
			}
		}
		
		return map;
	}
	
	
	
	/**
	 * 获得最近三十天的能耗 
	 * @param map1 本月能耗
	 * @param map2 上月能耗
	 */
	public static TreeMap<Integer,Double> mergeMap(TreeMap<Integer,Double> map1,TreeMap<Integer,Double> map2)
	{   
		int size1 = map1.size();
		int size2 = map2.size();
		
		int removeCount = size1 + size2 - 30  ;
		for (int j=1;j<=removeCount;j++){
			map2.remove(j);
		}
		
		List<Double> list = new LinkedList<Double>();
		
		
		Iterator<Double> iter2 = map2.values().iterator();
		
		while(iter2.hasNext()){
			
			list.add(iter2.next());
		}
		
		Iterator<Double> iter1 = map1.values().iterator();
		
		while(iter1.hasNext()){
			list.add(iter1.next());
		}
		
		TreeMap<Integer,Double> finalMap = new TreeMap<Integer,Double>();
		
		if (removeCount < 0){
			finalMap.put(1,0.0);
			for (int k=0;k<list.size();k++){
				
				finalMap.put(k+2, list.get(k));
			}
		}else{
			for (int k=0;k<list.size();k++){
				finalMap.put(k+1, list.get(k));
			}
		}
		return finalMap;
	}

	
	public MyEnergyChartModel getMyEnergyChartModel() {
		MyEnergyChartModel myEnergyChartModel = new MyEnergyChartModel();

		if (eiMyselfInfo == null) {
			eiMyselfInfo = eiReportManager.getMyselfInfo(userid);
		}

		// 获取我的信息数据有问题，数据都设置为0
		if (eiMyselfInfo == null) {
			HashMap<Integer, Double> energy = new LinkedHashMap<Integer, Double>();
			for (int i = 1; i <= 30; i++) {
				energy.put(i, 0.0);
			}
			myEnergyChartModel.setBuildingEverydayEnergy(energy);
			myEnergyChartModel.setBuildingEverydayKwh(energy);
			myEnergyChartModel.setMyEverydayEnergy(energy);
			myEnergyChartModel.setMyEverydayKwh(energy);
			return myEnergyChartModel;
		}

		// 获取我的所在的哪栋楼、楼层、房间
		mySeat = eiMyselfInfo.getSeat();
		myFloor = eiMyselfInfo.getFloor();
		myRoom = eiMyselfInfo.getRoom();

		// 获取大厦总人数、楼层人数、房间人数
//		totalPeopleCount = eiMyselfInfo.getPeopleCount();
//		floorPeopleCount = eiMyselfInfo.getFloorPeopleCount();
//		roomPeopleCount = eiMyselfInfo.getRoomPeopleCount();
		buildingAllRooms = eiMyselfInfo.getBuildingAllRooms();
		floorRoomsCount = eiMyselfInfo.getFloorRoomsCount();

		// 大厦当月每天人均总能耗和电耗
		HashMap<Integer, Double> buildingEnergy = new LinkedHashMap<Integer, Double>();
		HashMap<Integer, Double> buildingKwh = new LinkedHashMap<Integer, Double>();

		// 我当月每天的总能耗和电耗
		HashMap<Integer, Double> myEnergy = new LinkedHashMap<Integer, Double>();
		HashMap<Integer, Double> myKwh = new LinkedHashMap<Integer, Double>();

		// 启动6个线程，获取大厦当月每天气耗、水耗、电耗；我的每天气耗、水耗、电耗
		ExecutorService pool = null;
		pool = Executors.newFixedThreadPool(6);
		BuildingEverydayFlowThread buildingEverydayFlowThread = new BuildingEverydayFlowThread(
				year, month);
		BuildingEverydayWaterThread buildingEverydayWaterThread = new BuildingEverydayWaterThread(
				year, month);
		BuildingEverydayKwhThread buildingEverydayKwhThread = new BuildingEverydayKwhThread(
				year, month);
		MyEverydayFlowThread myEverydayFlowThread = new MyEverydayFlowThread(
				mySeat, myFloor, year, month);
		MyEverydayWaterThread myEverydayWaterThread = new MyEverydayWaterThread(
				mySeat, myFloor, year, month);
		MyEverydayKwhThread myEverydayKwhThread = new MyEverydayKwhThread(
				mySeat, myFloor, myRoom, year, month);
		pool.execute(buildingEverydayFlowThread);
		pool.execute(buildingEverydayWaterThread);
		pool.execute(buildingEverydayKwhThread);
		pool.execute(myEverydayFlowThread);
		pool.execute(myEverydayWaterThread);
		pool.execute(myEverydayKwhThread);

		pool.shutdown();
		while (!pool.isTerminated()) {
			// all tasks have completed following shut down
		}

		// 获取取得的数值
		TreeMap<Integer, Double> buildingEverydayFlow = buildingEverydayFlowThread
				.getFlow();
		TreeMap<Integer, Double> buildingEverydayWater = buildingEverydayWaterThread
				.getWater();
		TreeMap<Integer, Double> buildingEverydayKwh = buildingEverydayKwhThread
				.getKwh();
		TreeMap<Integer, Double> myEverydayFlow = myEverydayFlowThread
				.getFlow();
		TreeMap<Integer, Double> myEverydayWater = myEverydayWaterThread
				.getWater();
		TreeMap<Integer, Double> myEverydayKwh = myEverydayKwhThread.getKwh();

		Double buildingFlowConsume = null;
		Double buildingWaterConsume = null;
		Double buildingKwhConsume = null;

		Double myFlowConsume = null;
		Double myWaterConsume = null;
		Double myKwhConsume = null;

		double buildingTotalConsume = 0.0;
		double buildingTotalKwhConsume = 0.0;

		double myTotalConsume = 0.0;
		double myTotalKwhConsume = 0.0;

		for (int i = 1; i <= 30; i++) {
			buildingFlowConsume = buildingEverydayFlow.get(i);
			if (buildingFlowConsume == null) {
				buildingFlowConsume = 0.0;
			}

			buildingWaterConsume = buildingEverydayWater.get(i);
			if (buildingWaterConsume == null) {
				buildingWaterConsume = 0.0;
			}

			buildingKwhConsume = buildingEverydayKwh.get(i);
			if (buildingKwhConsume == null) {
				buildingKwhConsume = 0.0;
			}

			myFlowConsume = myEverydayFlow.get(i);
			if (myFlowConsume == null) {
				myFlowConsume = 0.0;
			}

			myWaterConsume = myEverydayWater.get(i);
			if (myWaterConsume == null) {
				myWaterConsume = 0.0;
			}

			myKwhConsume = myEverydayKwh.get(i);
			if (myKwhConsume == null) {
				myKwhConsume = 0.0;
			}

			// 计算大厦的房间平均总能耗（Kwh）
			buildingTotalConsume = EIUtils.div(EIUtils.calcTotalConsume(
					buildingWaterConsume, buildingKwhConsume,
					buildingFlowConsume), buildingAllRooms);

			// 计算大厦的房间平均均电耗
			buildingTotalKwhConsume = EIUtils.div(buildingKwhConsume,
					buildingAllRooms);

			// 计算我的房间总能耗(Kwh)
			Double avgFlowConsume = EIUtils.div(EIUtils.mul(myFlowConsume,
					EIConstrants.HEATING_POWER_TO_STANDARD_COAL),
					floorRoomsCount);
			Double avgWaterConsume = EIUtils.div(EIUtils.mul(myWaterConsume,
					EIConstrants.WATER_TO_STANDARD_COAL), floorRoomsCount);
			Double avgKwhConsume = EIUtils.mul(myKwhConsume,
					EIConstrants.POWER_TO_STANDARD_COAL);
			myTotalConsume = EIUtils
					.div(EIUtils.add(avgFlowConsume, avgWaterConsume,
							avgKwhConsume), EIConstrants.POWER_TO_STANDARD_COAL);

			// 计算我的电耗
			myTotalKwhConsume = myKwhConsume;

			buildingEnergy.put(i, buildingTotalConsume);
			buildingKwh.put(i, buildingTotalKwhConsume);

			myEnergy.put(i, myTotalConsume);
			myKwh.put(i, myTotalKwhConsume);
		}
		
		myEnergyChartModel.setBuildingEverydayEnergy(buildingEnergy);
		myEnergyChartModel.setBuildingEverydayKwh(buildingKwh);
		myEnergyChartModel.setMyEverydayEnergy(myEnergy);
		myEnergyChartModel.setMyEverydayKwh(myKwh);

		return myEnergyChartModel;

	}
}

/**
 * 获取大厦当月每天的气耗的线程类
 * 
 */
class BuildingEverydayFlowThread extends Thread {

	private TreeMap<Integer, Double> flow;

	private int year;
	private int month;

	public BuildingEverydayFlowThread(int year, int month) {
		this.year = year;
		this.month = month;
	}

	@Override
	public void run() {
		getBuildingEverydayFlow();
	}

	private void getBuildingEverydayFlow() {
		String mdxStr = String.format(
				MdxStrings.BUILDING_EVERYDAY_FLOW_CONSUME, year, month);

		MdxExecutor mdxExecutor = new MdxExecutor();
		flow = mdxExecutor.getResultHashMap(mdxStr);
		flow = MyEnergyContributionManager.setDeHashMap(flow, Calendar.getInstance().get(Calendar.DAY_OF_MONTH),MyEnergyContributionManager.MON_TYPE_CUR);
		if (null != flow  && flow.size()<30)
		{   
			// 上月气耗
			TreeMap<Integer, Double> flow2 = new TreeMap<Integer, Double>();
			if (month ==1)
			{
				flow2 = mdxExecutor.getResultHashMap(String.format(MdxStrings.BUILDING_EVERYDAY_FLOW_CONSUME, year-1,12));
			}
			else
			{
				flow2 = mdxExecutor.getResultHashMap(String.format(MdxStrings.BUILDING_EVERYDAY_FLOW_CONSUME, year, month-1));
			}
			flow2 = MyEnergyContributionManager.setDeHashMap(flow2, Calendar.getInstance().get(Calendar.DAY_OF_MONTH),MyEnergyContributionManager.MON_TYPE_LAST);
			flow = MyEnergyContributionManager.mergeMap(flow, flow2);
		}
	}

	public TreeMap<Integer, Double> getFlow() {
		return flow;
	}

	public void setFlow(TreeMap<Integer, Double> flow) {
		this.flow = flow;
	}
}

/**
 * 获取大厦当月每天的水耗的线程类
 * 
 */
class BuildingEverydayWaterThread extends Thread {

	private TreeMap<Integer, Double> water;

	private int year;
	private int month;

	public BuildingEverydayWaterThread(int year, int month) {
		this.year = year;
		this.month = month;
	}

	@Override
	public void run() {
		getBuildingEverydayWater();
	}

	private void getBuildingEverydayWater() {
		String mdxStr = String.format(
				MdxStrings.BUILDING_EVERYDAY_WATER_CONSUME, year, month);

		MdxExecutor mdxExecutor = new MdxExecutor();

		water = mdxExecutor.getResultHashMap(mdxStr);
		
		water = MyEnergyContributionManager.setDeHashMap(water, Calendar.getInstance().get(Calendar.DAY_OF_MONTH),MyEnergyContributionManager.MON_TYPE_CUR);
		if (null != water  && water.size()<30)
		{
			// 上个月的水耗
			TreeMap<Integer, Double> water2 = new TreeMap<Integer, Double>();
			// 如果是当前月份为1 则获得去年12月的数据
			if (month==1)
			{
				water2 = mdxExecutor.getResultHashMap(String.format(MdxStrings.BUILDING_EVERYDAY_WATER_CONSUME, year-1, 12));
			}
			else
			{
				water2 = mdxExecutor.getResultHashMap(String.format(MdxStrings.BUILDING_EVERYDAY_WATER_CONSUME, year, month-1));
			}
			 water2 = MyEnergyContributionManager.setDeHashMap(water2, Calendar.getInstance().get(Calendar.DAY_OF_MONTH),MyEnergyContributionManager.MON_TYPE_LAST);
			 water = MyEnergyContributionManager.mergeMap(water, water2);
		}
	}
  
	
	public TreeMap<Integer, Double> getWater() {
		return water;
	}

	public void setWater(TreeMap<Integer, Double> water) {
		this.water = water;
	}
}



/**
 * 获取大厦当月每天的电耗的线程类
 * 
 */
class BuildingEverydayKwhThread extends Thread {

	private TreeMap<Integer, Double> kwh;

	private int year;
	private int month;

	public BuildingEverydayKwhThread(int year, int month) {
		this.year = year;
		this.month = month;
	}

	@Override
	public void run() {
		getBuildingEverydayKwh();
	}

	private void getBuildingEverydayKwh() {
		String mdxStr = String.format(MdxStrings.BUILDING_EVERYDAY_KWH_CONSUME,
				year, month);

		MdxExecutor mdxExecutor = new MdxExecutor();
		kwh = mdxExecutor.getResultHashMap(mdxStr);
		kwh = MyEnergyContributionManager.setDeHashMap(kwh, Calendar.getInstance().get(Calendar.DAY_OF_MONTH),MyEnergyContributionManager.MON_TYPE_CUR);
		if (null != kwh  && kwh.size()<30)
		{
			TreeMap<Integer, Double> kwh2 = new TreeMap<Integer, Double>();
			if (month ==1)
			{
				kwh2 =  mdxExecutor.getResultHashMap(String.format(MdxStrings.BUILDING_EVERYDAY_KWH_CONSUME, year-1, 12));
			}
			else
			{
				kwh2 =  mdxExecutor.getResultHashMap(String.format(MdxStrings.BUILDING_EVERYDAY_KWH_CONSUME, year, month-1));
			} 
		
			 kwh2 = MyEnergyContributionManager.setDeHashMap(kwh2, Calendar.getInstance().get(Calendar.DAY_OF_MONTH),MyEnergyContributionManager.MON_TYPE_LAST);
			 kwh = MyEnergyContributionManager.mergeMap(kwh, kwh2);
		}
	}

	public TreeMap<Integer, Double> getKwh() {
		return kwh;
	}

	public void setKwh(TreeMap<Integer, Double> kwh) {
		this.kwh = kwh;
	}

}

/**
 * 获取我当月每天的气耗的线程类
 * 
 */
class MyEverydayFlowThread extends Thread {

	private TreeMap<Integer, Double> flow;

	private String mySeat;
	private String myFloor;
	private int year;
	private int month;

	public MyEverydayFlowThread(String mySeat, String myFloor, int year,
			int month) {
		this.mySeat = mySeat;
		this.myFloor = myFloor;
		this.year = year;
		this.month = month;
	}

	@Override
	public void run() {
		getMyEverydayFlow();
	}

	private void getMyEverydayFlow() {
		String mdxStr = String.format(MdxStrings.MY_EVERYDAY_FLOW_CONSUME,
				mySeat, myFloor, year, month);

		MdxExecutor mdxExecutor = new MdxExecutor();
		flow = mdxExecutor.getResultHashMap(mdxStr);
		flow = MyEnergyContributionManager.setDeHashMap(flow, Calendar.getInstance().get(Calendar.DAY_OF_MONTH),MyEnergyContributionManager.MON_TYPE_CUR);
		if (null != flow  && flow.size()<30)
		{
			TreeMap<Integer, Double> flow2 = new TreeMap<Integer, Double>();
			if (month==1)
			{
				flow2 = mdxExecutor.getResultHashMap(String.format(MdxStrings.MY_EVERYDAY_FLOW_CONSUME,
						mySeat, myFloor, year-1, 12));
			}
			else
			{
				flow2 = mdxExecutor.getResultHashMap(String.format(MdxStrings.MY_EVERYDAY_FLOW_CONSUME,
						mySeat, myFloor, year, month-1));
			}
			flow2 = MyEnergyContributionManager.setDeHashMap(flow2, Calendar.getInstance().get(Calendar.DAY_OF_MONTH),MyEnergyContributionManager.MON_TYPE_LAST);
			flow = MyEnergyContributionManager.mergeMap(flow, flow2);
		}
	}

	public TreeMap<Integer, Double> getFlow() {
		return flow;
	}

	public void setFlow(TreeMap<Integer, Double> flow) {
		this.flow = flow;
	}

}

/**
 * 获取我当月每天的水耗的线程类
 * 
 */
class MyEverydayWaterThread extends Thread {

	private TreeMap<Integer, Double> water;

	private String mySeat;
	private String myFloor;
	private int year;
	private int month;

	public MyEverydayWaterThread(String mySeat, String myFloor, int year,
			int month) {
		this.mySeat = mySeat;
		this.myFloor = myFloor;
		this.year = year;
		this.month = month;
	}

	@Override
	public void run() {
		getMyEverydayWater();
	}

	private void getMyEverydayWater() {
		String mdxStr = String.format(MdxStrings.MY_EVERYDAY_WATER_CONSUME,
				mySeat, myFloor, year, month);

		MdxExecutor mdxExecutor = new MdxExecutor();

		water = mdxExecutor.getResultHashMap(mdxStr);
		water = MyEnergyContributionManager.setDeHashMap(water, Calendar.getInstance().get(Calendar.DAY_OF_MONTH),MyEnergyContributionManager.MON_TYPE_CUR);
		if (null != water  && water.size()<30)
		{
			TreeMap<Integer, Double>  water2 = new TreeMap<Integer, Double>();
			if(month ==1)
			{
				water2 = mdxExecutor.getResultHashMap(String.format(MdxStrings.MY_EVERYDAY_WATER_CONSUME,
						mySeat, myFloor, year-1, 12));
			}
			else
			{
				water2 = mdxExecutor.getResultHashMap(String.format(MdxStrings.MY_EVERYDAY_WATER_CONSUME,
						mySeat, myFloor, year, month-1));
			}
		
			water2 = MyEnergyContributionManager.setDeHashMap(water2, Calendar.getInstance().get(Calendar.DAY_OF_MONTH),MyEnergyContributionManager.MON_TYPE_LAST);
			water = MyEnergyContributionManager.mergeMap(water, water2);
		}

	}

	public TreeMap<Integer, Double> getWater() {
		return water;
	}

	public void setWater(TreeMap<Integer, Double> water) {
		this.water = water;
	}

}

/**
 * 获取我当月每天的电耗的线程类
 * 
 */
class MyEverydayKwhThread extends Thread {

	private TreeMap<Integer, Double> kwh;

	private String mySeat;
	private String myFloor;
	private String myRoom;
	private int year;
	private int month;

	public MyEverydayKwhThread(String mySeat, String myFloor, String myRoom,
			int year, int month) {
		this.mySeat = mySeat;
		this.myFloor = myFloor;
		this.myRoom = myRoom;
		this.year = year;
		this.month = month;
	}

	@Override
	public void run() {
		getMyEverydayKwh();
	}

	private void getMyEverydayKwh() {
		String mdxStr = String.format(MdxStrings.MY_EVERYDAY_KWH_CONSUME,
				mySeat, myFloor, myRoom, year, month);

		MdxExecutor mdxExecutor = new MdxExecutor();
		kwh = mdxExecutor.getResultHashMap(mdxStr);
		kwh = MyEnergyContributionManager.setDeHashMap(kwh, Calendar.getInstance().get(Calendar.DAY_OF_MONTH),MyEnergyContributionManager.MON_TYPE_CUR);
		if (null != kwh  && kwh.size()<30)
		{   
			TreeMap<Integer, Double>  kwh2 = new TreeMap<Integer, Double>();
			if (month ==1)
			{
				kwh2 = mdxExecutor.getResultHashMap(String.format(MdxStrings.MY_EVERYDAY_KWH_CONSUME,
						mySeat, myFloor, myRoom, year-1, 12));
			}
			else
			{
				kwh2 = mdxExecutor.getResultHashMap(String.format(MdxStrings.MY_EVERYDAY_KWH_CONSUME,
						mySeat, myFloor, myRoom, year, month-1));
			}
			kwh2 = MyEnergyContributionManager.setDeHashMap(kwh2, Calendar.getInstance().get(Calendar.DAY_OF_MONTH),MyEnergyContributionManager.MON_TYPE_LAST);
			kwh = MyEnergyContributionManager.mergeMap(kwh, kwh2);
		}
	}

	public TreeMap<Integer, Double> getKwh() {
		return kwh;
	}

	public void setKwh(TreeMap<Integer, Double> kwh) {
		this.kwh = kwh;
	}

}

/**
 * 获取我当天的能耗的线程类
 * 
 */
class MyTodayConsumeThread extends Thread {

	private double todayConsume;

	public double getTodayConsume() {
		return todayConsume;
	}

	public void setTodayConsume(double todayConsume) {
		this.todayConsume = todayConsume;
	}

	private int year;
	private int month;
	private int day;
	private String mySeat;
	private String myFloor;
	private String myRoom;
	//private int floorPeopleCount;
	//private int roomPeopleCount;
	private int buildingAllRooms;
	private int floorRoomsCount;

	public MyTodayConsumeThread(int year, int month, int day, String mySeat,
			String myFloor, String myRoom,int buildingAllRooms,int floorRoomsCount) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.mySeat = mySeat;
		this.myFloor = myFloor;
		this.myRoom = myRoom;
		this.buildingAllRooms = buildingAllRooms;
		this.floorRoomsCount = floorRoomsCount;
	}

	@Override
	public void run() {
		getMyTodayConsume();
	}

	private void getMyTodayConsume() {
		// 转换为电耗
		todayConsume = EIUtils.calcTotalConsume(getMyFloorTodayWaterConsume(),
				getMyRoomTodayKwhConsume(), getMyFloorTodayFlowConsume());
	}

	/**
	 * 获取我所在楼层当天的气耗
	 * 
	 * @return
	 */
	private double getMyFloorTodayFlowConsume() {
		double myFloorFlowTodayConsume = 0.0;

		String mdxStr = String.format(MdxStrings.MY_FLOOR_TODAY_FLOW_CONSUME,
				year, month, day, mySeat, myFloor);
		MdxExecutor mdxExecutor = new MdxExecutor();

		myFloorFlowTodayConsume = mdxExecutor.getResultDouble(mdxStr);

		return EIUtils.div(myFloorFlowTodayConsume, floorRoomsCount);
	}

	/**
	 * 获取我所在楼层当天的水耗
	 * 
	 * @return
	 */
	private double getMyFloorTodayWaterConsume() {
		double myFloorTodayWaterConsume = 0.0;

		String mdxStr = String.format(MdxStrings.MY_FLOOR_TODAY_WATER_CONSUME,
				year, month, day, mySeat, myFloor);

		MdxExecutor mdxExecutor = new MdxExecutor();
		myFloorTodayWaterConsume = mdxExecutor.getResultDouble(mdxStr);
		return EIUtils.div(myFloorTodayWaterConsume, floorRoomsCount);
	}

	/**
	 * 获取我所在房间当天的电耗
	 * 
	 * @return
	 */
	private double getMyRoomTodayKwhConsume() {
		double myRoomTodayKwhConsume = 0.0;

		String mdxStr = String.format(MdxStrings.MY_ROOM_TODAY_KWH_CONSUME,
				year, month, day, mySeat, myFloor, myRoom);

		MdxExecutor mdxExecutor = new MdxExecutor();
		myRoomTodayKwhConsume = mdxExecutor.getResultDouble(mdxStr);

		return myRoomTodayKwhConsume;

	}

}

/**
 * 获取我当月的能耗的线程类
 * 
 */
class MyTomonthConsumeThread extends Thread {

	private double tomonthConsume;

	public double getTomonthConsume() {
		return tomonthConsume;
	}

	public void setTomonthConsume(double tomonthConsume) {
		this.tomonthConsume = tomonthConsume;
	}

	private int year;
	private int month;
	private String mySeat;
	private String myFloor;
	private String myRoom;
	//private int floorPeopleCount;
	//private int roomPeopleCount;
	private int buildingAllRooms;
	private int floorRoomsCount;

	public MyTomonthConsumeThread(int year, int month, String mySeat,
			String myFloor, String myRoom,int buildingAllRooms,int floorRoomsCount) {
		this.year = year;
		this.month = month;
		this.mySeat = mySeat;
		this.myFloor = myFloor;
		this.myRoom = myRoom;
		this.buildingAllRooms = buildingAllRooms;
		this.floorRoomsCount = floorRoomsCount;
	}

	@Override
	public void run() {
		getMyTomonthConsume();
	}

	/**
	 * 获取我当月的能耗
	 * 
	 * @return
	 */
	private void getMyTomonthConsume() {
		// 转换为电耗
		tomonthConsume = EIUtils.calcTotalConsume(
				getMyFloorTomonthWaterConsume(), getMyRoomTomonthKwhConsume(),
				getMyFloorTomonthFlowConsume());
	}

	/**
	 * 获取我所在楼层当月的气耗
	 * 
	 * @return
	 */
	private double getMyFloorTomonthFlowConsume() {
		double myFloorFlowTomonthConsume = 0.0;

		String mdxStr = String.format(MdxStrings.MY_FLOOR_TOMONTH_FLOW_CONSUME,
				year, month, mySeat, myFloor);
		MdxExecutor mdxExecutor = new MdxExecutor();
		myFloorFlowTomonthConsume = mdxExecutor.getResultDouble(mdxStr);

		return EIUtils.div(myFloorFlowTomonthConsume, floorRoomsCount);
	}

	/**
	 * 获取我所在楼层当月的水耗
	 * 
	 * @return
	 */
	private double getMyFloorTomonthWaterConsume() {
		double myFloorTomonthWaterConsume = 0.0;

		String mdxStr = String.format(
				MdxStrings.MY_FLOOR_TOMONTH_WATER_CONSUME, year, month, mySeat,
				myFloor);

		MdxExecutor mdxExecutor = new MdxExecutor();
		myFloorTomonthWaterConsume = mdxExecutor.getResultDouble(mdxStr);
		return EIUtils.div(myFloorTomonthWaterConsume, floorRoomsCount);
	}

	/**
	 * 获取我所在房间当月的电耗
	 * 
	 * @return
	 */
	private double getMyRoomTomonthKwhConsume() {
		double myRoomTomonthKwhConsume = 0.0;

		String mdxStr = String.format(MdxStrings.MY_ROOM_TOMONTH_KWH_CONSUME,
				year, month, mySeat, myFloor, myRoom);

		MdxExecutor mdxExecutor = new MdxExecutor();
		myRoomTomonthKwhConsume = mdxExecutor.getResultDouble(mdxStr);

		return myRoomTomonthKwhConsume;

	}

}

/**
 * 获取大厦当月的能耗的线程类
 * 
 */
class BuildingTomonthConsumeThread extends Thread {
	private double tomonthConsume;

	public double getTomonthConsume() {
		return tomonthConsume;
	}

	public void setTomonthConsume(double tomonthConsume) {
		this.tomonthConsume = tomonthConsume;
	}

	private int year;
	private int month;

	public BuildingTomonthConsumeThread(int year, int month) {
		this.year = year;
		this.month = month;
	}

	@Override
	public void run() {
		getBuildingTomonthConsume();
	}

	/**
	 * 获取大厦当月的能耗
	 * 
	 * @return
	 */
	private void getBuildingTomonthConsume() {
		// 转换为电耗
		tomonthConsume = EIUtils
				.calcTotalConsume(getBuildingTomonthWaterConsume(),
						getBuildingTomonthKwhConsume(),
						getBuildingTomonthFlowConsume());
	}

	/**
	 * 获取大厦当月的气耗
	 * 
	 * @return
	 */
	private double getBuildingTomonthFlowConsume() {
		double buildingTomonthFlowConsume = 0.0;

		String mdxStr = String.format(MdxStrings.BUILDING_TOMONTH_FLOW_CONSUME,
				year, month);

		MdxExecutor mdxExecutor = new MdxExecutor();
		buildingTomonthFlowConsume = mdxExecutor.getResultDouble(mdxStr);

		return buildingTomonthFlowConsume;

	}

	/**
	 * 获取大厦当月的水耗
	 * 
	 * @return
	 */
	private double getBuildingTomonthWaterConsume() {
		double buildingTomonthWaterConsume = 0.0;

		String mdxStr = String.format(
				MdxStrings.BUILDING_TOMONTH_WATER_CONSUME, year, month);

		MdxExecutor mdxExecutor = new MdxExecutor();
		buildingTomonthWaterConsume = mdxExecutor.getResultDouble(mdxStr);

		return buildingTomonthWaterConsume;

	}

	/**
	 * 获取大厦当月的电耗
	 * 
	 * @return
	 */
	private double getBuildingTomonthKwhConsume() {
		double buildingTomonthKwhConsume = 0.0;

		String mdxStr = String.format(MdxStrings.BUILDING_TOMONTH_KWH_CONSUME,
				year, month);

		MdxExecutor mdxExecutor = new MdxExecutor();
		buildingTomonthKwhConsume = mdxExecutor.getResultDouble(mdxStr);

		return buildingTomonthKwhConsume;

	}

}