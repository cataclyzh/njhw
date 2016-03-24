package com.cosmosource.app.property.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrLostFound;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.DateUtil;

/** 
* @description: 失物招领
* @author chengyun
* @date 2013-07-03
*/ 
@SuppressWarnings("unchecked")
public class LostFoundManager extends BaseManager {
	
	public String showNotClaimCountNumber(){
		List<Map> list = new ArrayList();
		Map map=new HashMap();
		map.put("lostFoundState", "0");
		list.add(map);
		List<Map> resultList=sqlDao.findList("PropertySQL.selectNotClaimCountNumber", map);
		String notClaimCountNumber ="0";
		if(resultList.isEmpty())
			return "0";
		else {
			for(Map resultMap :resultList){
				notClaimCountNumber=(resultMap.get("NOTCLAIMCOUNTNUMBER")).toString();
			}
		}
		
		return notClaimCountNumber;
	}
	
	public int updateLostFoundStateById(Long lostFoundId){
		List<Map> list = new ArrayList();
		Map map = new HashMap();
		map.put("lostFoundId", lostFoundId);
		list.add(map);
		sqlDao.batchUpdate("PropertySQL.updateLostFoundStateById", list);
		return 0;
	}
	
	public void addOneLostFound(GrLostFound lostfound){
		List<Map> list = new ArrayList();
		Map map = new HashMap();
		map.put("lostFoundTitle", lostfound.getLostFoundTitle());
		map.put("lostFoundThingName", lostfound.getLostFoundThingName());
		map.put("lostFoundIntime", lostfound.getLostFoundIntime());
		map.put("lostFoundPickUser", lostfound.getLostFoundPickUser());
		map.put("lostFoundPickUserOrg", lostfound.getLostFoundPickUserOrg());
		map.put("lostFoundLocation", lostfound.getLostFoundLocation());
		map.put("lostFoundDetail", lostfound.getLostFoundDetail());
		map.put("lostFoundKeeper", lostfound.getLostFoundKeeper());
		map.put("lostFoundState", lostfound.getLostFoundState());
		map.put("lostFoundLostUser", lostfound.getLostFoundLostUser());
		map.put("lostFoundLostUserOrg", lostfound.getLostFoundLostUserOrg());
		map.put("lostFoundOverTime", lostfound.getLostFoundOverTime());
		map.put("lostFoundPhone", lostfound.getLostFoundPhone());
		map.put("resBak1", lostfound.getResBak1());
		map.put("resBak2", lostfound.getResBak2());
		map.put("resBak3", lostfound.getResBak3());
		map.put("resBak4", lostfound.getResBak4());
		list.add(map);
		sqlDao.batchInsert("PropertySQL.insertLostFound", list);
	}
	
	public void addLostFounds(List<GrLostFound> lostfounds){
		List<Map> list = new ArrayList();
		Map map =null;
		for(GrLostFound lostfound : lostfounds){
			map= new HashMap();
			map.put("lostFoundTitle", lostfound.getLostFoundTitle());
			map.put("lostFoundThingName", lostfound.getLostFoundThingName());
			map.put("lostFoundIntime", lostfound.getLostFoundIntime());
			map.put("lostFoundPickUser", lostfound.getLostFoundPickUser());
			map.put("lostFoundPickUserOrg", lostfound.getLostFoundPickUserOrg());
			map.put("lostFoundLocation", lostfound.getLostFoundLocation());
			map.put("lostFoundDetail", lostfound.getLostFoundDetail());
			map.put("lostFoundKeeper", lostfound.getLostFoundKeeper());
			map.put("lostFoundState", lostfound.getLostFoundState());
			map.put("lostFoundLostUser", lostfound.getLostFoundLostUser());
			map.put("lostFoundLostUserOrg", lostfound.getLostFoundLostUserOrg());
			map.put("lostFoundOverTime", lostfound.getLostFoundOverTime());
			map.put("lostFoundPhone", lostfound.getLostFoundPhone());
			map.put("resBak1", lostfound.getResBak1());
			map.put("resBak2", lostfound.getResBak2());
			map.put("resBak3", lostfound.getResBak3());
			map.put("resBak4", lostfound.getResBak4());
			list.add(map);
		}
		sqlDao.batchInsert("PropertySQL.insertLostFound", list);
	}
	
	public void deleteOneLostFound(Long lostFoundId){
		List<Map> list = new ArrayList();
		Map map = new HashMap();
		map.put("lostFoundId", lostFoundId);
		
		list.add(map);
		
		sqlDao.batchDelete("PropertySQL.deleteLostFound", list);
	}
	
	public void deleteLostFounds(Long[] lostFoundIds){
		List<Map> list = new ArrayList();
		Map map = null;
		for(Long lostFoundId : lostFoundIds){
			map = new HashMap();
			map.put("lostFoundId", lostFoundId);
			list.add(map);
		}
		sqlDao.batchDelete("PropertySQL.deleteLostFound", list);
	}
	
	public void updateOneLostFound(GrLostFound lostfound){
		List<Map> list = new ArrayList();
		Map map = new HashMap();
		map.put("lostFoundId", lostfound.getLostFoundId());
		map.put("lostFoundTitle", lostfound.getLostFoundTitle());
		map.put("lostFoundThingName", lostfound.getLostFoundThingName());
		map.put("lostFoundIntime", lostfound.getLostFoundIntime());
		map.put("lostFoundPickUser", lostfound.getLostFoundPickUser());
		map.put("lostFoundPickUserOrg", lostfound.getLostFoundPickUserOrg());
		map.put("lostFoundLocation", lostfound.getLostFoundLocation());
		map.put("lostFoundDetail", lostfound.getLostFoundDetail());
		map.put("lostFoundKeeper", lostfound.getLostFoundKeeper());
		map.put("lostFoundState", lostfound.getLostFoundState());
		map.put("lostFoundLostUser", lostfound.getLostFoundLostUser());
		map.put("lostFoundLostUserOrg", lostfound.getLostFoundLostUserOrg());
		map.put("lostFoundOverTime", lostfound.getLostFoundOverTime());
		map.put("lostFoundPhone", lostfound.getLostFoundPhone());
		map.put("resBak1", lostfound.getResBak1());
		map.put("resBak2", lostfound.getResBak2());
		map.put("resBak3", lostfound.getResBak3());
		map.put("resBak4", lostfound.getResBak4());
		list.add(map);
		sqlDao.batchUpdate("PropertySQL.updateLostFound", list);
	}
	
	public void updateLostFounds(List<GrLostFound> lostfounds){
		List<Map> list = new ArrayList();
		Map map =null;
		for(GrLostFound lostfound : lostfounds){
			map = new HashMap();
			map.put("lostFoundId", lostfound.getLostFoundId());
			map.put("lostFoundTitle", lostfound.getLostFoundTitle());
			map.put("lostFoundThingName", lostfound.getLostFoundThingName());
			map.put("lostFoundIntime", lostfound.getLostFoundIntime());
			map.put("lostFoundPickUser", lostfound.getLostFoundPickUser());
			map.put("lostFoundPickUserOrg", lostfound.getLostFoundPickUserOrg());
			map.put("lostFoundLocation", lostfound.getLostFoundLocation());
			map.put("lostFoundDetail", lostfound.getLostFoundDetail());
			map.put("lostFoundKeeper", lostfound.getLostFoundKeeper());
			map.put("lostFoundState", lostfound.getLostFoundState());
			map.put("lostFoundLostUser", lostfound.getLostFoundLostUser());
			map.put("lostFoundLostUserOrg", lostfound.getLostFoundLostUserOrg());
			map.put("lostFoundOverTime", lostfound.getLostFoundOverTime());
			map.put("lostFoundPhone", lostfound.getLostFoundPhone());
			map.put("resBak1", lostfound.getResBak1());
			map.put("resBak2", lostfound.getResBak2());
			map.put("resBak3", lostfound.getResBak3());
			map.put("resBak4", lostfound.getResBak4());
			list.add(map);
		}
		sqlDao.batchUpdate("PropertySQL.updateLostFound", list);
	}
	
	public List<GrLostFound> queryLostFounds(String lostFoundTitle,String lostFoundThingName,Date lostFoundIntime,Date lostFoundOverTime,String lostFoundState){
		Map map = new HashMap();
		map.put("lostFoundTitle", lostFoundTitle);
		map.put("lostFoundThingName", lostFoundThingName);
		map.put("lostFoundIntime", lostFoundIntime);
		map.put("lostFoundOverTime", lostFoundOverTime);
		map.put("lostFoundState", lostFoundState);
		List<Map> list = new ArrayList<Map>();
		list = sqlDao.findList("PropertySQL.selectAllLostFounds", map);
		return mapToLostFound(list);
	}
	
	public List<GrLostFound> mapToLostFound(List<Map> list){
		List<GrLostFound> result = new ArrayList<GrLostFound>();
		GrLostFound lostfound;
		for(Map map:list){
			lostfound = new GrLostFound();
			if (map.get("LOST_FOUND_ID") != null) {
				lostfound.setLostFoundId(Long.parseLong(String.valueOf(map
						.get("LOST_FOUND_ID"))));
			}

			if (map.get("LOST_FOUND_TITLE") != null) {
			
					lostfound.setLostFoundTitle(String.valueOf(map.get("LOST_FOUND_TITLE")));
				
			}

			if (map.get("LOST_FOUND_THINGNAME") != null) {
				lostfound.setLostFoundThingName(String.valueOf(map
						.get("LOST_FOUND_THINGNAME")));
			}

			if (map.get("LOST_FOUND_INTIME") != null) {
				lostfound.setLostFoundIntime(DateUtil.str2Date(String.valueOf(map.get("LOST_FOUND_INTIME")), "yyyy-MM-dd HH:mm:ss"));
			}else {
				lostfound.setLostFoundIntime(null);
			}
			
			if (map.get("LOST_FOUND_INTIME_STRING") != null) {
				lostfound.setLostFoundIntimeString((String.valueOf(map.get("LOST_FOUND_INTIME_STRING"))));
			}else {
				lostfound.setLostFoundIntimeString(null);
			}

			if (map.get("LOST_FOUND_PICKUSER") != null) {
				lostfound.setLostFoundPickUser(String.valueOf(map
						.get("LOST_FOUND_PICKUSER")));
			}

			if (map.get("LOST_FOUND_PICKUSER_ORG") != null) {
				lostfound.setLostFoundPickUserOrg(String.valueOf(map
						.get("LOST_FOUND_PICKUSER_ORG")));
			}

			if (map.get("LOST_FOUND_LOCATION") != null) {
				lostfound.setLostFoundLocation(String.valueOf(map
						.get("LOST_FOUND_LOCATION")));
			}

			if (map.get("LOST_FOUND_DETAIL") != null) {
				lostfound.setLostFoundDetail(String.valueOf(map
						.get("LOST_FOUND_DETAIL")));
			}

			if (map.get("LOST_FOUND_KEEPER") != null) {
				lostfound.setLostFoundKeeper(String.valueOf(map
						.get("LOST_FOUND_KEEPER")));
			}

			if (map.get("LOST_FOUND_STATE") != null) {
				lostfound.setLostFoundState(String.valueOf(map
						.get("LOST_FOUND_STATE")));
			}

			if (map.get("LOST_FOUND_LOSTUSER") != null) {
				lostfound.setLostFoundLostUser(String.valueOf(map
						.get("LOST_FOUND_LOSTUSER")));
			}

			if (map.get("LOST_FOUND_LOSTUSER_ORG") != null) {
				lostfound.setLostFoundLostUserOrg(String.valueOf(map
						.get("LOST_FOUND_LOSTUSER_ORG")));
			}
						
			if (map.get("LOST_FOUND_OVERTIME")!= null) {
				lostfound.setLostFoundOverTime(DateUtil.str2Date(String.valueOf(map.get("LOST_FOUND_OVERTIME")), "yyyy-MM-dd HH:mm:ss"));
			}else {
				lostfound.setLostFoundOverTime(null);
			}
			
			if (map.get("LOST_FOUND_PHONE")!= null) {
				lostfound.setLostFoundPhone((String)map.get("LOST_FOUND_PHONE"));
			}
			
			if (map.get("RES_BAK1")!= null) {
				lostfound.setResBak1((String)map.get("RES_BAK1"));
			}
			
			if (map.get("RES_BAK2")!= null) {
				lostfound.setResBak2((String)map.get("RES_BAK2"));
			}
			
			if (map.get("RES_BAK3")!= null) {
				lostfound.setResBak3((String)map.get("RES_BAK3"));
			}
			
			if (map.get("RES_BAK4")!= null) {
				lostfound.setResBak4((String)map.get("RES_BAK4"));
			}
			result.add(lostfound);
		}
		
		return result;
	}
	
	public List<GrLostFound> queryLostFoundsForIndex(String lostFoundTitle,String lostFoundThingName,Date lostFoundIntime,Date lostFoundOverTime,String lostFoundState){
		Map map = new HashMap();
		map.put("lostFoundTitle", lostFoundTitle);
		map.put("lostFoundThingName", lostFoundThingName);
		map.put("lostFoundIntime", lostFoundIntime);
		map.put("lostFoundOverTime", lostFoundOverTime);
		map.put("lostFoundState", lostFoundState);
		List<Map> list = new ArrayList<Map>();
		list = sqlDao.findList("PropertySQL.selectAllLostFounds", map);
		return mapToLostFoundForIndex(list);
	}
	
	public List<GrLostFound> mapToLostFoundForIndex(List<Map> list){
		List<GrLostFound> result = new ArrayList<GrLostFound>();
		GrLostFound lostfound;
		for(Map map:list){
			lostfound = new GrLostFound();
			if (map.get("LOST_FOUND_ID") != null) {
				lostfound.setLostFoundId(Long.parseLong(String.valueOf(map
						.get("LOST_FOUND_ID"))));
			}

			if (map.get("LOST_FOUND_TITLE") != null) {
				
				String lostTitle=String.valueOf(map.get("LOST_FOUND_TITLE"));
				
				if(lostTitle.length()>4){
					lostfound.setLostFoundTitle(lostTitle.substring(0,4)+"..");
				}else{
					lostfound.setLostFoundTitle(lostTitle);
				}
				
			}

			if (map.get("LOST_FOUND_THINGNAME") != null) {
				lostfound.setLostFoundThingName(String.valueOf(map
						.get("LOST_FOUND_THINGNAME")));
			}

			if (map.get("LOST_FOUND_INTIME") != null) {
				lostfound.setLostFoundIntime(DateUtil.str2Date(String.valueOf(map.get("LOST_FOUND_INTIME")), "yyyy-MM-dd HH:mm:ss"));
			}else {
				lostfound.setLostFoundIntime(null);
			}

			if (map.get("LOST_FOUND_PICKUSER") != null) {
				lostfound.setLostFoundPickUser(String.valueOf(map
						.get("LOST_FOUND_PICKUSER")));
			}

			if (map.get("LOST_FOUND_PICKUSER_ORG") != null) {
				lostfound.setLostFoundPickUserOrg(String.valueOf(map
						.get("LOST_FOUND_PICKUSER_ORG")));
			}

			if (map.get("LOST_FOUND_LOCATION") != null) {
				lostfound.setLostFoundLocation(String.valueOf(map
						.get("LOST_FOUND_LOCATION")));
			}

			if (map.get("LOST_FOUND_DETAIL") != null) {
				lostfound.setLostFoundDetail(String.valueOf(map
						.get("LOST_FOUND_DETAIL")));
			}

			if (map.get("LOST_FOUND_KEEPER") != null) {
				lostfound.setLostFoundKeeper(String.valueOf(map
						.get("LOST_FOUND_KEEPER")));
			}

			if (map.get("LOST_FOUND_STATE") != null) {
				lostfound.setLostFoundState(String.valueOf(map
						.get("LOST_FOUND_STATE")));
			}

			if (map.get("LOST_FOUND_LOSTUSER") != null) {
				lostfound.setLostFoundLostUser(String.valueOf(map
						.get("LOST_FOUND_LOSTUSER")));
			}

			if (map.get("LOST_FOUND_LOSTUSER_ORG") != null) {
				lostfound.setLostFoundLostUserOrg(String.valueOf(map
						.get("LOST_FOUND_LOSTUSER_ORG")));
			}
						
			if (map.get("LOST_FOUND_OVERTIME")!= null) {
				lostfound.setLostFoundOverTime(DateUtil.str2Date(String.valueOf(map.get("LOST_FOUND_OVERTIME")), "yyyy-MM-dd HH:mm:ss"));
			}else {
				lostfound.setLostFoundOverTime(null);
			}
			
			if (map.get("LOST_FOUND_PHONE")!= null) {
				lostfound.setLostFoundPhone((String)map.get("LOST_FOUND_PHONE"));
			}
			
			if (map.get("RES_BAK1")!= null) {
				lostfound.setResBak1((String)map.get("RES_BAK1"));
			}
			
			if (map.get("RES_BAK2")!= null) {
				lostfound.setResBak2((String)map.get("RES_BAK2"));
			}
			
			if (map.get("RES_BAK3")!= null) {
				lostfound.setResBak3((String)map.get("RES_BAK3"));
			}
			
			if (map.get("RES_BAK4")!= null) {
				lostfound.setResBak4((String)map.get("RES_BAK4"));
			}
			result.add(lostfound);
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	public GrLostFound findLostFoundById(Long lostFoundId){
		Map map = new HashMap();
		map.put("lostFoundId", lostFoundId);
		List<Map> list = sqlDao.findList("PropertySQL.findLostFoundById", map);
		List<GrLostFound> result;
		GrLostFound lostfound = null;
		if(list.isEmpty())
			return null;
		else {
			result = mapToLostFound(list);
			lostfound = (GrLostFound)result.get(0);
		}
		return lostfound;
	}
	
	public Page<HashMap> queryAllLostFounds(final Page<HashMap> page, HashMap<String, String> parMap){
		return sqlDao.findPage(page, "PropertySQL.selectAllLostFounds", parMap);
	}
}
