package com.cosmosource.app.threedimensional.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import com.cosmosource.app.threedimensional.service.EnergyManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Struts2Util;

@SuppressWarnings("unchecked")
public class EnergyAction extends BaseAction {

	private static final long serialVersionUID = 1533830008983298926L;
	private EnergyManager energyManager;
   /*
    * 获取水耗
    */
	public String getWaterEnergy() {
		List<Map<String,Object>> waterOfFloorsList = null;
		try {
			waterOfFloorsList = energyManager.getWaterValueOfFloors();
            if(waterOfFloorsList!=null)
            {	
            	for(int i =0; i<waterOfFloorsList.size(); i++){
            		Map map = waterOfFloorsList.get(i);
            		map.put("floor", waterOfFloorsList.get(i).get("floorId"));
            		map.remove("floorId");
            		map.put("monitorValue_sum", String.format("%.2f", waterOfFloorsList.get(i).get("measure")));
            		map.remove("measure");
            		map.put("monitorValue_avg", String.format("%.3f", waterOfFloorsList.get(i).get("monitorValue_avg")));
            	}
            	JSONArray  jsonArray = JSONArray.fromObject(waterOfFloorsList);
            	Struts2Util.renderJson(jsonArray.toString(), "encoding:UTF-8", "no-cache:true");
            }
            else
            {
             return null;	
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	 /*
	    * 获取气耗
	    */
	public String getFlowEnergy() {
		List<Map<String,Object>> flowOfFloorsList = null;
		try {
			flowOfFloorsList = energyManager.getFlowValueOfFloors();
            if(flowOfFloorsList!=null)
            {	
            	for(int i =0; i<flowOfFloorsList.size(); i++){
            		Map map = flowOfFloorsList.get(i);
            		map.put("floor", flowOfFloorsList.get(i).get("floorId"));
            		map.remove("floorId");
            		map.put("monitorValue_sum", String.format("%.2f", flowOfFloorsList.get(i).get("measure")));
            		map.remove("measure");
            		map.put("monitorValue_avg", String.format("%.3f", flowOfFloorsList.get(i).get("monitorValue_avg")));
            	}
            	JSONArray  jsonArray = JSONArray.fromObject(flowOfFloorsList);
            	Struts2Util.renderJson(jsonArray.toString(), "encoding:UTF-8", "no-cache:true");
            }
            else
            {
             return null;	
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 /*
	    * 获取电耗
	    */
	public String getElectricityEnergy() {
		List<Map<String,Object>> electricityOfFloorsList = null;
		try {
			electricityOfFloorsList = energyManager.getElectricityOfFloors();
            if(electricityOfFloorsList!=null) {
            	for(int i =0; i<electricityOfFloorsList.size(); i++){
            		Map map = electricityOfFloorsList.get(i);
            		map.put("floor", electricityOfFloorsList.get(i).get("FLOOR"));
            		map.remove("FLOOR");
            		map.put("monitorValue_sum", String.format("%.2f", electricityOfFloorsList.get(i).get("MEASURE")));
            		map.remove("MEASURE");
            		map.put("monitorValue_avg", String.format("%.3f", electricityOfFloorsList.get(i).get("monitorValue_avg")));
            	}
            	JSONArray jsonArray = JSONArray.fromObject(electricityOfFloorsList);
            	Struts2Util.renderJson(jsonArray.toString(), "encoding:UTF-8", "no-cache:true");
            } else {
                return null;	
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	 /*
	    * 获取水、电、气耗
	    */
	public String getEnergyOfFloors() {
		List<Map<String,Object>> energyOfFloorsList = null;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		//Map map = new HashMap<String, Object>();
		try {
			energyOfFloorsList = energyManager.getEnergyValueOfFloors();
            if(energyOfFloorsList!=null) {
            	for(int i = 0; i < energyOfFloorsList.size(); i++){
            		Map map = new HashMap<String, Object>();
            		map.put("floor", energyOfFloorsList.get(i).get("floorId"));
            		map.put("color", energyOfFloorsList.get(i).get("color"));
            		map.put("monitorValue_sum", String.format("%.2f", energyOfFloorsList.get(i).get("sum_measure")));
            		map.put("monitorValue_avg", String.format("%.3f", energyOfFloorsList.get(i).get("avg_measure")));
            		map.put("monitorUnit", energyOfFloorsList.get(i).get("monitorUnit"));
            		map.put("monitorTypeName", energyOfFloorsList.get(i).get("monitorTypeName"));
            		list.add(map);
            	}
            	JSONArray jsonArray = JSONArray.fromObject(list);
            	Struts2Util.renderJson(jsonArray.toString(), "encoding:UTF-8", "no-cache:true");
            } else {
                return null;	
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public EnergyManager getEnergyManager() {
		return energyManager;
	}

	public void setEnergyManager(EnergyManager energyManager) {
		this.energyManager = energyManager;
	}

}
