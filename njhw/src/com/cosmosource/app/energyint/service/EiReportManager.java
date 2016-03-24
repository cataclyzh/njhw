package com.cosmosource.app.energyint.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.cosmosource.app.energyint.model.EiHistoryConsume;
import com.cosmosource.app.energyint.model.EiMyselfInfo;
import com.cosmosource.app.energyint.model.EiReport;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.StringUtil;

/**
 * 获取自己相应的某些信息 <一句话功能简述> <功能详细描述>
 * 
 * @author Administrator
 * @version [版本号, 2013-7-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class EiReportManager extends BaseManager {
    /**
	 * 获取自己相应的某些信息
	 * 
	 * <功能详细描述>
	 * 
	 * @param userid
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public EiMyselfInfo getMyselfInfo(String userid) {

		if (!StringUtil.isNotBlank(userid)) {
			return null;
		}

		EiMyselfInfo eiMyselfInfo = new EiMyselfInfo();

		Integer temp = null;
		// 获取总人数
		temp = (Integer) sqlDao.getSqlMapClientTemplate().queryForObject(
				"EnergyintSQL.SELECT_PEOPLE_COUNT");
		if (temp == null || temp == 0) {
			return null;
		}
		eiMyselfInfo.setPeopleCount(temp);

		// 获取我所在的房间的总人数
		// temp = (Integer) sqlDao.getSqlMapClientTemplate().queryForObject(
		// "EnergyintSQL.SELECT_ROOM_PEOPLE_COUNT", userid);
		// if (temp == null || temp == 0) {
		// return null;
		// }
		// eiMyselfInfo.setRoomPeopleCount(temp);
		
		//获取大厦的总房间数
		temp = (Integer) sqlDao.getSqlMapClientTemplate().queryForObject(
				"EnergyintSQL.SELECT_BUILDING_ROOMS_COUNT");
		if (temp == null || temp == 0) {
			return null;
		}
		eiMyselfInfo.setBuildingAllRooms(temp);
		
		//获取我所在楼层的总房间数
		temp = (Integer) sqlDao.getSqlMapClientTemplate().queryForObject(
				"EnergyintSQL.SELECT_MY_FLOOR_ROOMS_COUNT",userid);
		if (temp == null || temp == 0) {
			return null;
		}
		eiMyselfInfo.setFloorRoomsCount(temp);

//		// 获取我所在的楼层人数
//		temp = (Integer) sqlDao.getSqlMapClientTemplate().queryForObject(
//				"EnergyintSQL.SELECT_FLOOR_PEOPLE_COUNT", userid);
//		if (temp == null || temp == 0) {
//			return null;
//		}
//		eiMyselfInfo.setFloorPeopleCount(temp);

		// 获取我所在的房间
		String myRoom = (String) sqlDao.getSqlMapClientTemplate()
				.queryForObject("EnergyintSQL.SELECT_MY_ROOM", userid);
		if (myRoom == null) {
			return null;
		}
		eiMyselfInfo.setRoom(myRoom);

		// 获取我所在的楼和层
		String seatAndFloor = (String) sqlDao.getSqlMapClientTemplate()
				.queryForObject("EnergyintSQL.SELECT_MY_FLOOR_AND_LAYER",
						userid);
		if (seatAndFloor == null) {
			return null;
		}
		// 15层 or A座4层 or A座14层
		if (seatAndFloor.length() == 3) {
			eiMyselfInfo.setFloor(seatAndFloor);
			String seat = (String) sqlDao.getSqlMapClientTemplate()
					.queryForObject("EnergyintSQL.SELECT_MY_SEAT", myRoom);
			if (seat == null) {
				return null;
			}
			eiMyselfInfo.setSeat(seat);
		} else {
			eiMyselfInfo.setFloor(seatAndFloor.substring(2,
					seatAndFloor.length()));
			eiMyselfInfo.setSeat(seatAndFloor.substring(0, 2));
		}

		return eiMyselfInfo;
	}
	
	/**
	 * 
	 * @param parameterEiReport
	 * @param actionType 查过去月份的话需要做特殊处理
	 * @return
	 */
    @SuppressWarnings({"unchecked"})
    public Map getGeneralReport(EiReport parameterEiReport,boolean queryMonthHistory)
    {
        Map resultMap = new HashMap();
        
        // 1.查询电耗
        EiReport resultKwhEiReport = null;
        if(queryMonthHistory)
        {
            parameterEiReport.setQueryEnergyType(3);
            resultKwhEiReport = queryEnerygyPreMonthInfo(parameterEiReport);
        }
        else
        {
            parameterEiReport.setQueryEnergyType(0);
            resultKwhEiReport = queryEnerygyInfo(parameterEiReport);
        }
        
        // 电耗
        resultMap.put(EIConstrants.KWH_MEASURE, resultKwhEiReport.getMeasure());
        // 电耗的标准煤（千克标准煤）
        resultMap.put(EIConstrants.KEY_POWER_TO_STANDARD_COAL,
            countStandardCoal(resultKwhEiReport.getMeasure(), EIConstrants.POWER_TO_STANDARD_COAL));

        // 2.查询气耗
        EiReport resultFlowEiReport = null;
        
        if(queryMonthHistory)
        {
            parameterEiReport.setQueryEnergyType(2);
            resultFlowEiReport = queryEnerygyPreMonthInfo(parameterEiReport);
            resultFlowEiReport.setMeasure_flow((int) resultFlowEiReport.getMeasure().doubleValue());
        }
        else
        {
            parameterEiReport.setQueryEnergyType(1);
            resultFlowEiReport = queryEnerygyInfo(parameterEiReport);
        }
        // 气耗
        resultMap.put(EIConstrants.FLOW_MEASURE_HEAT, resultFlowEiReport.getMeasure_heat());
        resultMap.put(EIConstrants.FLOW_MEASURE_FLOW, (double)resultFlowEiReport.getMeasure_flow());
        // 气耗的标准煤（千克标准煤）
        resultMap.put(EIConstrants.KEY_HEATING_POWER_TO_STANDARD_COAL,
            countStandardCoal(resultFlowEiReport.getMeasure_flow(), EIConstrants.FLOW_POWER_TO_STANDARD_COAL));
        
        // 3.查询水耗
        EiReport resultWaterEiReport = null;
        
        if(queryMonthHistory)
        {
            parameterEiReport.setQueryEnergyType(1);
            resultWaterEiReport = queryEnerygyPreMonthInfo(parameterEiReport);
        }
        else
        {
            parameterEiReport.setQueryEnergyType(2);
            resultWaterEiReport = queryEnerygyInfo(parameterEiReport);
        }
        // 水耗
        resultMap.put(EIConstrants.WATER_MEASURE, resultWaterEiReport.getMeasure());
        // 水耗的标准煤（千克标准煤）
        resultMap.put(EIConstrants.KEY_WATER_TO_STANDARD_COAL,
            countStandardCoal(resultWaterEiReport.getMeasure(), EIConstrants.WATER_TO_STANDARD_COAL));
        
        // 4.查询油耗
        EiReport resultOilEiReport = null;
        
        if(queryMonthHistory)
        {
            parameterEiReport.setQueryEnergyType(4);
            resultOilEiReport = queryEnerygyPreMonthInfo(parameterEiReport);
        }
        else
        {
            resultOilEiReport = new EiReport();
            resultOilEiReport.setMeasure(0d);
            resultOilEiReport.setMeasure_heat(0d);
        }
        // 油耗
        resultMap.put(EIConstrants.OIL_MEASURE, sum(resultOilEiReport.getMeasure(), resultOilEiReport.getMeasure_heat()));
        // 油耗的标准煤（千克标准煤）
        resultMap.put(EIConstrants.KEY_OIL_TO_STANDARD_COAL,
            sum(countStandardCoal(resultOilEiReport.getMeasure(), EIConstrants.GASOLINE_POWER_TO_STANDARD_COAL),
            		countStandardCoal(resultOilEiReport.getMeasure_heat(), EIConstrants.DIESOL_OIL_POWER_TO_STANDARD_COAL)));
        
        //总标准煤(千克标准煤)
        Double count = sum((Double)resultMap.get(EIConstrants.KEY_POWER_TO_STANDARD_COAL),
            (Double)resultMap.get(EIConstrants.KEY_HEATING_POWER_TO_STANDARD_COAL),
            (Double)resultMap.get(EIConstrants.KEY_WATER_TO_STANDARD_COAL),
            (Double)resultMap.get(EIConstrants.KEY_OIL_TO_STANDARD_COAL));

        // 总能耗（kWh）
        resultMap.put(EIConstrants.TOTAL_MEASURE, div(count,EIConstrants.POWER_TO_STANDARD_COAL));

        //防止分母为0的情况
        if(count == 0)
        {
            count = 1d;
        }
                
        // 标准煤百分比
        resultMap.put(EIConstrants.FLOW_PERCENTAGE, div((Double)resultMap.get("HEATING_POWER_TO_STANDARD_COAL"), count));
        resultMap.put(EIConstrants.WATER_PERCENTAGE, div((Double)resultMap.get("WATER_TO_STANDARD_COAL"), count));
        resultMap.put(EIConstrants.KWH_PERCENTAGE, div((Double)resultMap.get("POWER_TO_STANDARD_COAL"), count));
        resultMap.put(EIConstrants.OIL_PERCENTAGE, div((Double)resultMap.get("OIL_TO_STANDARD_COAL"), count));
        return resultMap;
    }
    
    /**
     * 每个机关的水耗处理， <一句话功能简述> <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    public LinkedHashMap<String,Double> waterProcess(EiReport eiReport)
    {
        //设置查询类型，查询水耗
        eiReport.setQueryEnergyType(0);
        
        List<EiReport> waterReportList = queryWaterConsumption(eiReport);
        if(waterReportList == null || waterReportList.isEmpty())
        {
            return null;
        }
        
        List<EiReport> organRoomsList = queryOrganRooms();
        if(organRoomsList == null || organRoomsList.isEmpty())
        {
            return null;
        }
        
        //取得共在一层的机关
        Map<String, String> sameFloorOrgan = new HashMap<String, String>();
        Map<String, String> temp = new HashMap<String, String>();
        for (EiReport organRooms : organRoomsList)
        {
            if (!temp.containsKey(organRooms.getName()))
            {
                
                temp.put(organRooms.getName(), organRooms.getOrg_name().trim() + "::" + organRooms.getTotalRoom());
            }
            else
            {
                sameFloorOrgan.put(organRooms.getName(), organRooms.getOrg_name().trim() + "::" + organRooms.getTotalRoom() + "_"
                    + temp.get(organRooms.getName()));
            }
        }
        
        //取得机关所在的层
        Map<String, String> OrganFloor = new HashMap<String, String>();
        for (EiReport organRooms : organRoomsList)
        {
            if (!OrganFloor.containsKey(organRooms.getOrg_name().trim()))
            {
                OrganFloor.put(organRooms.getOrg_name().trim(), organRooms.getName());
            }
            else
            {
                OrganFloor.put(organRooms.getOrg_name().trim(),
                    OrganFloor.get(organRooms.getOrg_name().trim()) + "_" + organRooms.getName());
            }
        }
        
        //每个楼层的水耗
        Map<String,Double> waterReportMap = new HashMap<String, Double>();
        for (EiReport waterReport : waterReportList)
        {
            int floor = Integer.parseInt(waterReport.getWater_floor().split("层")[0]);
            //如果大于14层就不带座了，直接存入"*层"，否则存入"*座*层"
            if (floor > 14)
            {
                waterReportMap.put(waterReport.getWater_floor(), waterReport.getMeasure());
            }
            else
            {
                waterReportMap.put(waterReport.getWater_seat()+waterReport.getWater_floor(), waterReport.getMeasure());
            }
        }
        
        //取得楼层总房间数
        Map<String, Integer> floorRoomTotal = new HashMap<String, Integer>();
        for (EiReport organRooms : organRoomsList)
        {
            if (!floorRoomTotal.containsKey(organRooms.getName()))
            {
                floorRoomTotal.put(organRooms.getName(), organRooms.getTotalRoom());
            }
            else
            {
                floorRoomTotal.put(organRooms.getName(),
                    floorRoomTotal.get(organRooms.getName()) + organRooms.getTotalRoom());
                
            }
        }
        
        //最终显示结果
        LinkedHashMap<String,Double> viewReport = new LinkedHashMap<String, Double>();
        
        Iterator<Entry<String, String>> iterator = OrganFloor.entrySet().iterator();
        while(iterator.hasNext())
        {
            Entry<String, String> entry = iterator.next();
            
            //该机是否关存在多个楼层中
            String [] tempStrArr = entry.getValue().split("_");
            
            //最终结果
            double resultConsumption = 0;
            
            for (String floor : tempStrArr)
            {
                // 如果某个机关存在多个楼层，则取冲突的楼层。没取到，则说明当前楼层只有一个机关.
                String tempOrgan = sameFloorOrgan.get(floor);
                if (null == tempOrgan)
                {
                    // 楼层总消耗
                    resultConsumption = sum(resultConsumption,
                        waterReportMap.get(floor) == null ? 0 : waterReportMap.get(floor));
                }
                else
                {
                    String[] tempOrganArr = tempOrgan.split("_");
                    for (String string : tempOrganArr)
                    {
                        if (string.indexOf(entry.getKey()) != -1)
                        {
                            // 该委办局所有房间数
                            double roomCount = Double.parseDouble(string.split("::")[1]);
                            // 总房间数
                            double allRoomCount = floorRoomTotal.get(floor);
                            // 楼层总消耗
                            double floorEnergyConsumption = waterReportMap.get(floor) == null ? 0 : waterReportMap.get(floor);
                            // 消耗
                            resultConsumption = sum(resultConsumption,
                                multiply(div(floorEnergyConsumption, allRoomCount), roomCount));
                        }
                    }
                }
            }
            viewReport.put(entry.getKey(), resultConsumption);
        }
        return viewReport;
    }
    
    /**
     * 每个机关的气耗处理， <一句话功能简述> <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    public List<LinkedHashMap<String, ?>> flowProcess(EiReport eiReport)
    {
        
        // 设置查询类型，查询气耗
        eiReport.setQueryEnergyType(1);
        
        List<EiReport> flowReportList = queryFlowConsumption(eiReport);
        if(flowReportList == null || flowReportList.isEmpty())
        {
            return null;
        }
        
        List<EiReport> organRoomsList = queryOrganRooms();
        if(organRoomsList == null || organRoomsList.isEmpty())
        {
            return null;
        }
        
        // 取得共在一层的机关
        Map<String, String> sameFloorOrgan = new HashMap<String, String>();
        Map<String, String> temp = new HashMap<String, String>();
        for (EiReport organRooms : organRoomsList)
        {
            if (!temp.containsKey(organRooms.getName()))
            {
                
                temp.put(organRooms.getName(), organRooms.getOrg_name().trim() + "::" + organRooms.getTotalRoom());
            }
            else
            {
                sameFloorOrgan.put(organRooms.getName(),
                    organRooms.getOrg_name().trim() + "::" + organRooms.getTotalRoom() + "_"
                        + temp.get(organRooms.getName()));
            }
        }
        
        // 取得机关所在的层
        Map<String, String> OrganFloor = new HashMap<String, String>();
        for (EiReport organRooms : organRoomsList)
        {
            if (!OrganFloor.containsKey(organRooms.getOrg_name().trim()))
            {
                OrganFloor.put(organRooms.getOrg_name().trim(), organRooms.getName());
            }
            else
            {
                OrganFloor.put(organRooms.getOrg_name().trim(), OrganFloor.get(organRooms.getOrg_name().trim()) + "_"
                    + organRooms.getName());
            }
        }
        
        // 每个楼层的气耗(单位：Gj)
        Map<String, Double> flowGjReportMap = new HashMap<String, Double>();
        for (EiReport flowReport : flowReportList)
        {
            int floor = Integer.parseInt(flowReport.getFlow_floor().split("层")[0]);
            // 如果大于14层就不带座，直接存入"*层"，否则存入"*座*层"
            if (floor > 14)
            {
                flowGjReportMap.put(flowReport.getFlow_floor(), flowReport.getMeasure_heat());
            }
            else
            {
                flowGjReportMap.put(flowReport.getFlow_seat() + flowReport.getFlow_floor(),
                    flowReport.getMeasure_heat());
            }
        }
        // 每个楼层的气耗(单位：Gj)
        Map<String, Integer> flowMReportMap = new HashMap<String, Integer>();
        for (EiReport flowReport : flowReportList)
        {
            int floor = Integer.parseInt(flowReport.getFlow_floor().split("层")[0]);
            // 如果大于14层就不带座，直接存入"*层"，否则存入"*座*层"
            if (floor > 14)
            {
                flowMReportMap.put(flowReport.getFlow_floor(), flowReport.getMeasure_flow());
            }
            else
            {
                flowMReportMap.put(flowReport.getFlow_seat() + flowReport.getFlow_floor(), flowReport.getMeasure_flow());
            }
        }
        
        // 取得楼层总房间数
        Map<String, Integer> floorRoomTotal = new HashMap<String, Integer>();
        for (EiReport organRooms : organRoomsList)
        {
            if (!floorRoomTotal.containsKey(organRooms.getName()))
            {
                floorRoomTotal.put(organRooms.getName(), organRooms.getTotalRoom());
            }
            else
            {
                floorRoomTotal.put(organRooms.getName(),
                    floorRoomTotal.get(organRooms.getName()) + organRooms.getTotalRoom());
                
            }
        }
        
        // 最终显示结果
        LinkedHashMap<String, Double> viewGjReport = new LinkedHashMap<String, Double>();
        LinkedHashMap<String, Integer> viewMReport = new LinkedHashMap<String, Integer>();
        
        Iterator<Entry<String, String>> iterator = OrganFloor.entrySet().iterator();
        while (iterator.hasNext())
        {
            Entry<String, String> entry = iterator.next();
            
            // 该机是否关存在多个楼层中
            String[] tempStrArr = entry.getValue().split("_");
            
            // 气耗-单位时间气耗（单位：Gj）
            double resultGjConsumption = 0;
            
            // 气耗-单位时间气耗（单位：m³）
            int resultMConsumption = 0;
            
            for (String floor : tempStrArr)
            {
                // 如果某个机关存在多个楼层，则取冲突的楼层。没取到，则说明当前楼层只有一个机关.
                String tempOrgan = sameFloorOrgan.get(floor);
                if (null == tempOrgan)
                {
                    // 楼层总消耗
                    resultGjConsumption = sum(resultGjConsumption, flowGjReportMap.get(floor) == null ? 0
                        : flowGjReportMap.get(floor));
                    resultMConsumption = resultMConsumption
                        + (flowMReportMap.get(floor) == null ? 0 : flowMReportMap.get(floor));
                }
                else
                {
                    String[] tempOrganArr = tempOrgan.split("_");
                    for (String string : tempOrganArr)
                    {
                        if (string.indexOf(entry.getKey()) != -1)
                        {
                            // 该委办局所有房间数
                            double roomCount = Double.parseDouble(string.split("::")[1]);
                            // 总房间数
                            double allRoomCount = floorRoomTotal.get(floor);
                            // 楼层总消耗
                            double floorGjEnergyConsumption = flowGjReportMap.get(floor) == null ? 0
                                : flowGjReportMap.get(floor);
                            int floorMEnergyConsumption = flowMReportMap.get(floor) == null ? 0
                                : flowMReportMap.get(floor);
                            
                            // 气耗-单位时间气耗（单位：Gj）
                            resultGjConsumption = sum(resultGjConsumption,
                                multiply(div(floorGjEnergyConsumption, allRoomCount), roomCount));
                            
                            // 气耗-单位时间气耗（单位：m³）
                            resultMConsumption = (int)(resultMConsumption + (floorMEnergyConsumption / allRoomCount)
                                * roomCount);
                        }
                    }
                }
            }
            viewGjReport.put(entry.getKey(), resultGjConsumption);
            viewMReport.put(entry.getKey(), resultMConsumption);
        }
        List<LinkedHashMap<String, ?>> resultList = new ArrayList<LinkedHashMap<String, ?>>();
        resultList.add(viewGjReport);
        resultList.add(viewMReport);
        return resultList;
        
    }
    
    /**
     * 每个机关平分3项电表耗处理， <一句话功能简述> <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    public LinkedHashMap<String,Double> kwhProcess(EiReport eiReport)
    {
        
        List<EiReport> kwhReportList = queryKwhConsumption(eiReport);
        if(kwhReportList == null || kwhReportList.isEmpty())
        {
            return null;
        }
        
        List<EiReport> organRoomsList = queryOrganRooms();
        if(organRoomsList == null || organRoomsList.isEmpty())
        {
            return null;
        }
        
        // 取得机关所在的层
        Map<String, String> OrganFloor = new HashMap<String, String>();
        for (EiReport organRooms : organRoomsList)
        {
            if (!OrganFloor.containsKey(organRooms.getOrg_name().trim()))
            {
                OrganFloor.put(organRooms.getOrg_name().trim(), organRooms.getName());
            }
            else
            {
                OrganFloor.put(organRooms.getOrg_name().trim(), OrganFloor.get(organRooms.getOrg_name().trim()) + "_"
                    + organRooms.getName());
            }
        }
        
        //每个楼层的水耗
        Map<String,Double> kwhReportMap = new HashMap<String, Double>();
        for (EiReport kwhReport : kwhReportList)
        {
            int floor = Integer.parseInt(kwhReport.getKwh_floor().split("层")[0]);
            //如果大于14层就不带座了，直接存入"*层"，否则存入"*座*层"
            if (floor > 14)
            {
                kwhReportMap.put(kwhReport.getKwh_floor(), kwhReport.getMeasure());
            }
            else
            {
                kwhReportMap.put(kwhReport.getKwh_seat()+kwhReport.getKwh_floor(), kwhReport.getMeasure());
            }
        }
        
        // 取得楼层总房间数
        Map<String, Integer> floorRoomTotal = new HashMap<String, Integer>();
        for (EiReport organRooms : organRoomsList)
        {
            if (!floorRoomTotal.containsKey(organRooms.getName()))
            {
                floorRoomTotal.put(organRooms.getName(), organRooms.getTotalRoom());
            }
            else
            {
                floorRoomTotal.put(organRooms.getName(),
                    floorRoomTotal.get(organRooms.getName()) + organRooms.getTotalRoom());
                
            }
        }
        
        // 取得共在一层的机关
        Map<String, String> sameFloorOrgan = new HashMap<String, String>();
        Map<String, String> temp = new HashMap<String, String>();
        for (EiReport organRooms : organRoomsList)
        {
            if (!temp.containsKey(organRooms.getName()))
            {
                
                temp.put(organRooms.getName(), organRooms.getOrg_name().trim() + "::" + organRooms.getTotalRoom());
            }
            else
            {
                sameFloorOrgan.put(organRooms.getName(),
                    organRooms.getOrg_name().trim() + "::" + organRooms.getTotalRoom() + "_"
                        + temp.get(organRooms.getName()));
            }
        }
        
        //最终显示结果
        LinkedHashMap<String,Double> viewReport = new LinkedHashMap<String, Double>();
        
        Iterator<Entry<String, String>> iterator = OrganFloor.entrySet().iterator();
        while(iterator.hasNext())
        {
            Entry<String, String> entry = iterator.next();
            
            //该机是否关存在多个楼层中
            String [] tempStrArr = entry.getValue().split("_");
            
            //最终结果
            double resultConsumption = 0;
            
            for (String floor : tempStrArr)
            {
                // 如果某个机关存在多个楼层，则取冲突的楼层。没取到，则说明当前楼层只有一个机关.
                String tempOrgan = sameFloorOrgan.get(floor);
                if (null == tempOrgan)
                {
                    // 楼层总消耗
                    resultConsumption = sum(resultConsumption,
                        kwhReportMap.get(floor) == null ? 0 : kwhReportMap.get(floor));
                }
                else
                {
                    String[] tempOrganArr = tempOrgan.split("_");
                    for (String string : tempOrganArr)
                    {
                        if (string.indexOf(entry.getKey()) != -1)
                        {
                            // 该委办局所有房间数
                            double roomCount = Double.parseDouble(string.split("::")[1]);
                            // 总房间数
                            double allRoomCount = floorRoomTotal.get(floor);
                            // 楼层总消耗
                            double floorEnergyConsumption = kwhReportMap.get(floor) == null ? 0 : kwhReportMap.get(floor);
                            // 消耗
                            resultConsumption = sum(resultConsumption,
                                multiply(div(floorEnergyConsumption, allRoomCount), roomCount));
                        }
                    }
                }
            }
            viewReport.put(entry.getKey(), resultConsumption);
        }
        
        return viewReport;
    }
    
    /**
     * 查询report方法
     * <一句话功能简述>
     * <功能详细描述>
     * @param eiReport
     * @return
     * @see [类、类#方法、类#成员]
     */
    private EiReport queryEnerygyInfo(EiReport eiReport)
    {
        EiReport resultEiReport = (EiReport)sqlDao.getSqlMapClientTemplate()
            .queryForObject("EnergyintSQL.SELECT_BUILDING_ENERGY_CONSUMPTION", eiReport);
        return resultEiReport;
    }
    
    /**
     * 查询report方法
     * <一句话功能简述>
     * <功能详细描述>
     * @param eiReport
     * @return
     * @see [类、类#方法、类#成员]
     */
    private EiReport queryEnerygyPreMonthInfo(EiReport eiReport)
    {
        EiReport resultEiReport = (EiReport)sqlDao.getSqlMapClientTemplate()
            .queryForObject("EnergyintSQL.SELECT_EI_HISTORY_CONSUME_PRE_MONTH", eiReport);
        return resultEiReport;
    }
    
    /**
     * 查询各个各个机关电耗report方法 <一句话功能简述> <功能详细描述>
     * 
     * @param eiReport
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public LinkedHashMap<String, Double> queryEnerygyInfoByOrgans(EiReport eiReport)
    {
        Map<String, Double> resultEiReport = null;
        
        //查询私用电表总消耗
        resultEiReport =  sqlDao.getSqlMapClientTemplate()
            .queryForMap("EnergyintSQL.SELECT_ENERGY_CONSUMPTION_GROUP_BY_ORGANS", eiReport, "org_name", "all_kwh");
        
        //查询3项公用电表，然后在平摊到每个机关
        Map<String,Double> kwhMap = kwhProcess(eiReport);
        
        //排序用
        Map<String, Double> resultNewEiReport = new TreeMap<String, Double>();
        Iterator<Entry<String, Double>> iterator = resultEiReport.entrySet().iterator();
        while (iterator.hasNext())
        {
            Entry<String, Double> entry = iterator.next();
            if (kwhMap.get(entry.getKey()) != null)
            {
                resultNewEiReport.put(entry.getKey(),
                    sum(new BigDecimal(String.valueOf(entry.getValue())).doubleValue(), kwhMap.get(entry.getKey())));
            }
            else
            {
                resultNewEiReport.put(entry.getKey(), new BigDecimal(String.valueOf(entry.getValue())).doubleValue());
            }
        }
        return new LinkedHashMap<String, Double>(resultEiReport);
    }
    
    /**
     * 根据时间查询 水耗
     * 
     * @param eiReport
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    private List<EiReport> queryWaterConsumption(EiReport eiReport)
    {
        List<EiReport> resultEiReport = null;
        
        resultEiReport = sqlDao.getSqlMapClientTemplate()
            .queryForList("EnergyintSQL.SELECT_WATER_ENERGY_CONSUMPTION", eiReport);
        
        return resultEiReport;
    }
    
    /**
     * 根据时间查询 三项电表的数据
     * 
     * @param eiReport
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    private List<EiReport> queryKwhConsumption(EiReport eiReport)
    {
        List<EiReport> resultEiReport = null;
        
        resultEiReport = sqlDao.getSqlMapClientTemplate()
            .queryForList("EnergyintSQL.SELECT_3KWH_ENERGY_CONSUMPTION", eiReport);
        
        return resultEiReport;
    }
    
    /**
     * 根据时间查询 气耗
     * 
     * @param eiReport
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    private List<EiReport> queryFlowConsumption(EiReport eiReport)
    {
        List<EiReport> resultEiReport = null;
        
        resultEiReport = sqlDao.getSqlMapClientTemplate()
            .queryForList("EnergyintSQL.SELECT_FLOW_ENERGY_CONSUMPTION", eiReport);
        
        return resultEiReport;
    }
    
    /**
     * 查询每个楼层，对应的机关的房间数 <一句话功能简述> <功能详细描述>
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    private List<EiReport> queryOrganRooms()
    {
        List<EiReport> resultEiReport = null;
        
        resultEiReport = sqlDao.getSqlMapClientTemplate().queryForList("EnergyintSQL.SELECT_ORGAN_ROOMS");
        
        return resultEiReport;
    }
    
    /**
     * 查询机关历史年能耗消耗数据
     * 
     * @param eiReport
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({"unchecked"})
    public List<EiHistoryConsume> queryHistoryOrganInfo(List<?> year)
    {
        List<EiHistoryConsume> resultList = null;
        
        resultList = sqlDao.getSqlMapClientTemplate().queryForList("EnergyintSQL.SELECT_EI_HISTORY_CONSUME_ORGAN", year);
        
        return resultList;
    }
    
    
    /**
     * 查询机关历史年能耗消耗数据
     * 
     * @param eiReport
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({"unchecked"})
    public List<EiHistoryConsume> queryHistoryOrganInfoNull()
    {
        List<EiHistoryConsume> resultList = null;
        
        resultList = sqlDao.getSqlMapClientTemplate().queryForList("EnergyintSQL.SELECT_EI_HISTORY_CONSUME_ORGAN_NULL");
        
        return resultList;
    }
    
    /**
     * 得到历史年的能源消耗，根据机关名字来存放到Map
     * 
     * @param eiReport
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Map<String, LinkedHashMap<String, EiHistoryConsume>> queryHistoryOrganInfoToMap(List<?> year)
    {
        List<EiHistoryConsume> resultList = queryHistoryOrganInfo(year);
        
        Map<String, LinkedHashMap<String, EiHistoryConsume>> viewReport = new HashMap<String, LinkedHashMap<String, EiHistoryConsume>>();
        LinkedHashMap<String, EiHistoryConsume> eiHistoryConsumeMap = null;
        String orgNameKey = null;
        for (EiHistoryConsume eiHistoryConsume : resultList)
        {
            orgNameKey = eiHistoryConsume.getOrg_name().trim();
            if (viewReport.containsKey(orgNameKey))
            {
                viewReport.get(orgNameKey).put(eiHistoryConsume.getHistory_year() + "_"
                    + eiHistoryConsume.getConsume_type(),
                    eiHistoryConsume);
            }
            else
            {
                eiHistoryConsumeMap = new LinkedHashMap<String, EiHistoryConsume>();
                eiHistoryConsumeMap.put(eiHistoryConsume.getHistory_year() + "_" + eiHistoryConsume.getConsume_type(),
                    eiHistoryConsume);
                viewReport.put(orgNameKey, eiHistoryConsumeMap);
            }
        }
        
        return viewReport;
    }
    
    /**
     * 查询大厦历史年能耗消耗数据
     * 
     * @param eiReport
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({"unchecked"})
    public List<EiHistoryConsume> queryHistoryBuildingInfo(List<?> year)
    {
        List<EiHistoryConsume> resultList = null;
        
        resultList = sqlDao.getSqlMapClientTemplate().queryForList("EnergyintSQL.SELECT_EI_HISTORY_CONSUME_BUILDING", year);
        
        return resultList;
    }
    
    /**
     * 查询某过去月份的能源消耗
     * 
     * @param eiReport
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings( {"unchecked"})
    public Map<String, Double> queryHistoryMonthInfo(Map<String, Integer> parametar)
    {
        List<EiHistoryConsume> resultList = null;
        resultList = sqlDao.getSqlMapClientTemplate().queryForList("EnergyintSQL.SELECT_EI_HISTORY_CONSUME_MONTH",parametar);
        
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        if (resultList != null && !resultList.isEmpty())
        {
            for (EiHistoryConsume eiHistoryConsume : resultList)
            {
                if(parametar.get("consume_type") == EiHistoryConsume.consume_type_22)
                {
                    map.put(eiHistoryConsume.getOrg_name(), eiHistoryConsume.getMeasure_heat());
                }
                else if (parametar.get("consume_type") == EiHistoryConsume.consume_type_4) {
                	map.put(eiHistoryConsume.getOrg_name(), sum(eiHistoryConsume.getMeasure(), eiHistoryConsume.getMeasure_heat()));
                } else
                {
                    map.put(eiHistoryConsume.getOrg_name(), eiHistoryConsume.getMeasure());
                }
            }
        }
        return map;
    }

    /**
     * 查询某过去月份的能源消耗
     * 
     * @param eiReport
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings( {"unchecked"})
    public Map<String, Double> queryOilHistoryMonthInfo(Map<String, Integer> parametar)
    {
        List<EiHistoryConsume> resultList = null;
        resultList = sqlDao.getSqlMapClientTemplate().queryForList("EnergyintSQL.SELECT_EI_HISTORY_CONSUME_MONTH",parametar);
        
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        if (resultList != null && !resultList.isEmpty())
        {
            for (EiHistoryConsume eiHistoryConsume : resultList)
            {

                map.put(eiHistoryConsume.getOrg_name()+"G", eiHistoryConsume.getMeasure());
                map.put(eiHistoryConsume.getOrg_name()+"D", eiHistoryConsume.getMeasure_heat());
            }
        }
        return map;
    }
    
    /**
     * 查询当年累积的能源消耗
     * 
     * 如果查询当年的能耗要特殊处理，月历史记录表+当前月的数据。 因为定时器每个月的月初才会清理！所以需要加上，不然本月的数据就会遗漏
     * 
     * @param eiReport
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings( {"unchecked"})
    public Map<String, Double> queryHistoryYearInfo(Map<String, Integer> parametar)
    {
        int consume_type = parametar.get("consume_type");
        int year = parametar.get("year");
        int month = parametar.get("month");
        List<EiHistoryConsume> resultList = null;
        
        //能源特殊处理,类型字段 逻辑用，所以需要还原
        if(EiHistoryConsume.consume_type_22 == consume_type)
        {
            parametar.put("consume_type", EiHistoryConsume.consume_type_2);
        }
        resultList = sqlDao.getSqlMapClientTemplate().queryForList("EnergyintSQL.SELECT_EI_HISTORY_CONSUME_MONTH",parametar);
        
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        if (resultList != null && !resultList.isEmpty())
        {
            for (EiHistoryConsume eiHistoryConsume : resultList)
            {
                if (consume_type == EiHistoryConsume.consume_type_22)
                {
                    map.put(eiHistoryConsume.getOrg_name(), eiHistoryConsume.getMeasure_heat());
                }
                else
                {
                    map.put(eiHistoryConsume.getOrg_name(), eiHistoryConsume.getMeasure());
                }
            }
        }
        
        EiReport eiReport = new EiReport();
        eiReport.setQueryTimeType(1);
        eiReport.setYear(year);
        eiReport.setMonth(month);
        
        if (EiHistoryConsume.consume_type_1 == consume_type)
        {
            LinkedHashMap<String, Double> temp = waterProcess(eiReport);
            mapToMap(map,temp);
        }
        else if (EiHistoryConsume.consume_type_2 == consume_type)
        {
            List<LinkedHashMap<String, ?>> flow = flowProcess(eiReport);
            LinkedHashMap<String, Double> temp = (LinkedHashMap<String, Double>)flow.get(0);
            mapToMap(map,temp);
        }
        else if (EiHistoryConsume.consume_type_22 == consume_type)
        {
            List<LinkedHashMap<String, ?>> flow = flowProcess(eiReport);
            LinkedHashMap<String, Integer> temp = (LinkedHashMap<String, Integer>)flow.get(1);
            mapToMap(map,temp);
        }
        else if (EiHistoryConsume.consume_type_3 == consume_type)
        {
            LinkedHashMap<String, Double> kwh = kwhProcess(eiReport);
            mapToMap(map,kwh);
        }
        else
        {
            return null;
        }
        
        return map;
    }
    
    /**
     * Map赋值
     * @param map1
     * @param map2
     */
    public void mapToMap(Map<String,Double> map1,Map<String,?> map2)
    {
        if(map1==null || map2 ==null)
        {
            return;
        }
        
        if(!map1.isEmpty() && map2.isEmpty())
        {
            return;
        }
        
        if(map1.isEmpty() && !map2.isEmpty())
        {
            Iterator<?> iterator = map2.entrySet().iterator();
            while(iterator.hasNext())
            {
                Entry<?,?> entry = (Entry<?,?>)iterator.next();
                map1.put(entry.getKey().toString(),Double.valueOf(entry.getValue().toString()));
            }
                
        }
        
        Iterator<Entry<String, Double>> iterator = map1.entrySet().iterator();
        while(iterator.hasNext())
        {
            Entry<String, Double> entry  = iterator.next();
            if(map2.containsKey(entry.getKey()))
            {
                entry.setValue(sum(entry.getValue(),(Double.valueOf(map2.get(entry.getKey()).toString()))));
            }
        }
    }
    
    /**
     * 算标准煤（千克标准煤）
     * <一句话功能简述>
     * <功能详细描述>
     * @param d1
     * @param d2
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static double countStandardCoal(double d1, double d2)
    {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }
    
    /**
     * double相加
     * <一句话功能简述>
     * <功能详细描述>
     * @param ds
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static double sum(double... ds)
    {
    	if (ds.length == 5)
        {
            BigDecimal bd1 = new BigDecimal(Double.toString(ds[0]));
            
            return bd1.add(new BigDecimal(Double.toString(ds[1])))
                .add(new BigDecimal(Double.toString(ds[2])))
                .add(new BigDecimal(Double.toString(ds[3])))
                .add(new BigDecimal(Double.toString(ds[4])))
                .doubleValue();
        }
    	else if (ds.length == 4)
        {
            BigDecimal bd1 = new BigDecimal(Double.toString(ds[0]));
            
            return bd1.add(new BigDecimal(Double.toString(ds[1])))
                .add(new BigDecimal(Double.toString(ds[2])))
                .add(new BigDecimal(Double.toString(ds[3])))
                .doubleValue();
        }
    	else if (ds.length == 3)
        {
            BigDecimal bd1 = new BigDecimal(Double.toString(ds[0]));
            
            return bd1.add(new BigDecimal(Double.toString(ds[1])))
                .add(new BigDecimal(Double.toString(ds[2])))
                .doubleValue();
        }
        else if (ds.length == 2)
        {
            BigDecimal bd1 = new BigDecimal(Double.toString(ds[0]));
            return bd1.add(new BigDecimal(Double.toString(ds[1]))).doubleValue();
        }
        return 0;
    }
    
    /**
     * double相加 <一句话功能简述> <功能详细描述>
     * 
     * @param ds
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static double sum(int b, double a)
    {
        
        BigDecimal bd1 = new BigDecimal(Double.toString(a));
        
        return bd1.add(new BigDecimal(b)).intValue();
    }
    
    /**
     * 相除 <一句话功能简述> <功能详细描述>
     * 
     * @param a
     * @param b
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static double div(double a, double b)
    {
        BigDecimal bd1 = new BigDecimal(Double.toString(a));
        return bd1.divide(new BigDecimal(Double.toString(b)),15,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 相除 <一句话功能简述> <功能详细描述>
     * 
     * @param a
     * @param b
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static double multiply(double a, double b)
    {
        BigDecimal bd1 = new BigDecimal(Double.toString(a));
        return bd1.multiply(new BigDecimal(Double.toString(b))).doubleValue();
    }
}
