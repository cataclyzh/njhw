package com.cosmosource.app.threedimensional.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cosmosource.app.energyint.service.EIConstrants;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.Constants;

public class EnergyManager extends BaseManager 
{
   /*
    * 获取4-19层的水能能耗数据及每层单位面积的数据，并根据单位面积值进行排序
    */
   	public List<Map<String,Object>> getWaterValueOfFloors()
   	{
   		Calendar cal = Calendar.getInstance();
   		int year = cal.get(Calendar.YEAR);
   		int month = cal.get(Calendar.MONTH) + 1;
   		int day = cal.get(Calendar.DATE);
   		//前一小时
   		int hour = cal.get(Calendar.HOUR_OF_DAY)-1;
   		
   		Map<String,Object> map = new HashMap<String,Object>();
   		map.put("year", 2013);
   		map.put("month", 8);
   		map.put("day", 1);
   		map.put("hour", 11);
   		List<Map<String,Object>> floorWatereList = sqlDao.getSqlMapClientTemplate().queryForList("ThreeDimensionalSQL.SELECT_WATER_VALUE_OF_FLOOR", map);
   		if (floorWatereList==null) {
   			return null;
   		}else
   		{
   			for (int i = 0; i < floorWatereList.size(); i++) {
				if (Integer.parseInt(floorWatereList.get(i)
						.get("floorId").toString().replace("层", "")) > 14) {
					floorWatereList.get(i).put(
							"monitorValue_avg",
							Double.parseDouble(floorWatereList.get(i)
									.get("measure").toString())
									/ Constants.EDIFICE_FLAT_AREA);
				} else {
					floorWatereList.get(i).put(
							"monitorValue_avg",
							Double.parseDouble(floorWatereList.get(i)
									.get("measure").toString())
									/ Constants.EDIFICE_HORN_AREA);
				}
			}
   			floorWatereList = this.sort(floorWatereList, "monitorValue_avg");
			int size = floorWatereList.size();
			for (int i = 0; i < size; i++) {
				if (i < size / 5) {
					floorWatereList.get(i).put("color",Constants.ENERGY_LEVEL_FIVE_COLOR);
				} else if (i>size / 5 && i <= size * 2 / 5) {
                    floorWatereList.get(i).put("color",Constants.ENERGY_LEVEL_FOUR_COLOR);
				} else if (i>size *2/ 5 &&i <= size * 3 / 5) {
                    floorWatereList.get(i).put("color",Constants.ENERGY_LEVEL_THREE_COLOR);
				} else if (i>size *3/ 5 && i <= size * 4 / 5) {
                    floorWatereList.get(i).put("color",Constants.ENERGY_LEVEL_TWO_COLOR);
				} else {
                    floorWatereList.get(i).put("color",Constants.ENERGY_LEVEL_ONE_COLOR);
				}
				floorWatereList.get(i).put("monitorUnit" , Constants.ENERGY_WATER_UNIT);
				floorWatereList.get(i).put("monitorTypeName" , Constants.ENERGY_TYPE_WATER);
		}
		return floorWatereList;
   		}
   	}
   	
   	/*
     * 获取4-19层的气能能耗数据及每层单位面积的数据，并根据单位面积值进行排序
     */
    	public List<Map<String,Object>> getFlowValueOfFloors()
    	{
    		Calendar cal = Calendar.getInstance();
    		int year = cal.get(Calendar.YEAR);
    		int month = cal.get(Calendar.MONTH) + 1;
    		int day = cal.get(Calendar.DATE);
    		//前一小时
    		int hour = cal.get(Calendar.HOUR_OF_DAY)-1;
    		
    		Map<String,Object> map = new HashMap<String,Object>();
    		map.put("year", 2013);
    		map.put("month", 8);
    		map.put("day", 1);
    		map.put("hour", 11);
    		List<Map<String,Object>> floorFlowList = sqlDao.getSqlMapClientTemplate().queryForList("ThreeDimensionalSQL.SELECT_FLOW_VALUE_OF_FLOOR", map);
    		if (floorFlowList==null) {
    			return null;
    		}else
    		{
    			for (int i = 0; i < floorFlowList.size(); i++) {
    				if (Integer.parseInt(floorFlowList.get(i)
    						.get("floorId").toString().replace("层", "")) > 14) {
    					floorFlowList.get(i).put(
    							"monitorValue_avg",
    							Double.parseDouble(floorFlowList.get(i)
    									.get("measure").toString())
    									/ Constants.EDIFICE_FLAT_AREA);
    				} else {
    					floorFlowList.get(i).put(
    							"monitorValue_avg",
    							Double.parseDouble(floorFlowList.get(i)
    									.get("measure").toString())
    									/ Constants.EDIFICE_HORN_AREA);
    				}
    			}
    			floorFlowList = this.sort(floorFlowList, "monitorValue_avg");
    			int size = floorFlowList.size();
    			for (int i = 0; i < size; i++) {
    				if (i < size / 5) {
                        floorFlowList.get(i).put("color",Constants.ENERGY_LEVEL_FIVE_COLOR);
    				} else if (i>size / 5 && i <= size * 2 / 5) {
                        floorFlowList.get(i).put("color",Constants.ENERGY_LEVEL_FOUR_COLOR);
    				} else if (i>size *2/ 5 &&i <= size * 3 / 5) {
                        floorFlowList.get(i).put("color",Constants.ENERGY_LEVEL_THREE_COLOR);
    				} else if (i>size *3/ 5 && i <= size * 4 / 5) {
                        floorFlowList.get(i).put("color",Constants.ENERGY_LEVEL_TWO_COLOR);
    				} else {
                        floorFlowList.get(i).put("color",Constants.ENERGY_LEVEL_ONE_COLOR);
    				}
    				floorFlowList.get(i).put("monitorUnit" , Constants.ENERGY_GAS_UNIT);
    				floorFlowList.get(i).put("monitorTypeName" , Constants.ENERGY_TYPE_FLOW);
    		}
    		return floorFlowList;
    		}
    	}
   	/*
     * 获取当前时间的前一小时每一层的电总能耗数
     */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getElectricityOfFloors() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		int hour = cal.get(Calendar.HOUR_OF_DAY) - 1;  //前一小时

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("year", 2013);
		map.put("month", 8);
		map.put("day", 11);
		map.put("hour", 13);
		List<Map<String, Object>> electricityOfFloorsList = sqlDao
				.getSqlMapClientTemplate().queryForList(
						"ThreeDimensionalSQL.SEARCH_ELEC_ENERGY_BY_FLOORID",
						map);
		if (electricityOfFloorsList == null) {
			return null;
		} else {
			for (int i = 0; i < electricityOfFloorsList.size(); i++) {
				if (Integer.parseInt(electricityOfFloorsList.get(i)
						.get("FLOOR").toString().replace("层", "")) > 14) {
					electricityOfFloorsList.get(i).put(
							"monitorValue_avg",
							Double.parseDouble(electricityOfFloorsList.get(i)
									.get("MEASURE").toString())
									/ Constants.EDIFICE_FLAT_AREA);
				} else {
					electricityOfFloorsList.get(i).put(
							"monitorValue_avg",
							Double.parseDouble(electricityOfFloorsList.get(i)
									.get("MEASURE").toString())
									/ Constants.EDIFICE_HORN_AREA);
				}
			}
			electricityOfFloorsList = this.sort(electricityOfFloorsList, "monitorValue_avg");
			int size = electricityOfFloorsList.size();
			for (int i = 0; i < size; i++) {
				if (i < size / 5) {
                    electricityOfFloorsList.get(i).put("color",Constants.ENERGY_LEVEL_FIVE_COLOR);
				} else if (i < size * 2 / 5) {
                    electricityOfFloorsList.get(i).put("color",Constants.ENERGY_LEVEL_FOUR_COLOR);
				} else if (i < size * 3 / 5) {
                    electricityOfFloorsList.get(i).put("color",Constants.ENERGY_LEVEL_THREE_COLOR);
				} else if (i < size * 4 / 5) {
                    electricityOfFloorsList.get(i).put("color",Constants.ENERGY_LEVEL_TWO_COLOR);
				} else {
                    electricityOfFloorsList.get(i).put("color",Constants.ENERGY_LEVEL_ONE_COLOR);
				}
				electricityOfFloorsList.get(i).put("monitorUnit",
						Constants.ENERGY_ELECTRIC_UNIT);
				electricityOfFloorsList.get(i).put("monitorTypeName",
						Constants.ENERGY_TYPE_ELECTRIC);
			}
		}
		return electricityOfFloorsList;
	}
	
	/*
	    * 获取4-19层的水、电、气能耗数值
	    */
	   	public List<Map<String,Object>> getEnergyValueOfFloors()
	   	{
	   		Calendar cal = Calendar.getInstance();
	   		int year = cal.get(Calendar.YEAR);
	   		int month = cal.get(Calendar.MONTH) + 1;
	   		int day = cal.get(Calendar.DATE);
	   		//前一小时
	   		int hour = cal.get(Calendar.HOUR_OF_DAY)-1;
	   		
	   		Map<String,Object> map = new HashMap<String,Object>();
	   		map.put("year", 2013);
	   		map.put("month", 8);
	   		map.put("day", 1);
	   		map.put("hour", 11);
	   		List<Map<String,Object>> energyOfFloorsList = sqlDao.getSqlMapClientTemplate().queryForList("ThreeDimensionalSQL.SELECT_ENERGY_VALUE_OF_FLOOR", map);
	   		if (energyOfFloorsList==null) {
	   			return null;
	   		}else
	   		{
	   			for(int j= 0;j<energyOfFloorsList.size();j++)
	   			{
	   				energyOfFloorsList.get(j).put("measure_flow", Double.parseDouble(energyOfFloorsList.get(j).get("measure_flow").toString())*EIConstrants.HEATING_POWER_TO_STANDARD_COAL);
	   				energyOfFloorsList.get(j).put("measure_kwh",  Double.parseDouble(energyOfFloorsList.get(j).get("measure_kwh").toString())*EIConstrants.POWER_TO_STANDARD_COAL);
	   				energyOfFloorsList.get(j).put("measure_water", Double.parseDouble(energyOfFloorsList.get(j).get("measure_water").toString())*EIConstrants.WATER_TO_STANDARD_COAL);
	   				energyOfFloorsList.get(j).put("sum_measure", Double.parseDouble(energyOfFloorsList.get(j).get("measure_flow").toString())+ Double.parseDouble(energyOfFloorsList.get(j).get("measure_kwh").toString())+ Double.parseDouble(energyOfFloorsList.get(j).get("measure_water").toString()));
	   			}
	   			for (int i = 0; i < energyOfFloorsList.size(); i++) {
					if (Integer.parseInt(energyOfFloorsList.get(i)
							.get("floorId").toString().replace("层", "")) > 14) {
						energyOfFloorsList.get(i).put(
								"avg_measure",
								Double.parseDouble(energyOfFloorsList.get(i)
										.get("sum_measure").toString())
										/ Constants.EDIFICE_FLAT_AREA);
					} else {
						energyOfFloorsList.get(i).put(
								"avg_measure",
								Double.parseDouble(energyOfFloorsList.get(i)
										.get("sum_measure").toString())
										/ Constants.EDIFICE_HORN_AREA);
					}
				}
	   			energyOfFloorsList = this.sort(energyOfFloorsList, "avg_measure");
				int size = energyOfFloorsList.size();
				for (int i = 0; i < size; i++) {
					if (i < size / 5) {
						energyOfFloorsList.get(i).put("color",Constants.ENERGY_LEVEL_FIVE_COLOR);
					} else if (i>size / 5 && i <= size * 2 / 5) {
						energyOfFloorsList.get(i).put("color",Constants.ENERGY_LEVEL_FOUR_COLOR);
					} else if (i>size *2/ 5 &&i <= size * 3 / 5) {
						energyOfFloorsList.get(i).put("color",Constants.ENERGY_LEVEL_THREE_COLOR);
					} else if (i>size *3/ 5 && i <= size * 4 / 5) {
						energyOfFloorsList.get(i).put("color",Constants.ENERGY_LEVEL_TWO_COLOR);
					} else {
						energyOfFloorsList.get(i).put("color",Constants.ENERGY_LEVEL_ONE_COLOR);
					}
					energyOfFloorsList.get(i).put("monitorUnit" , "标准煤");
					energyOfFloorsList.get(i).put("monitorTypeName" , "水电气");
			}
			return energyOfFloorsList;
	   		}
	   	}
	
	
	// 根据能耗从大到小的排序
	public List<Map<String, Object>> sort(List<Map<String, Object>> targetList,
			String keyName) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		resultList.addAll(targetList);
		for (int i = 0; i < resultList.size(); i++) {
			for (int j = i; j < resultList.size(); j++) {
				if (Double.parseDouble(resultList.get(i).get(keyName)
						.toString()) < Double.parseDouble(resultList.get(j)
						.get(keyName).toString())) {
					resultList.set(i, targetList.get(j));
					resultList.set(j, targetList.get(i));
					targetList.set(i, resultList.get(i));
					targetList.set(j, resultList.get(j));
				}
			}
		}
		return resultList;
	}
}
