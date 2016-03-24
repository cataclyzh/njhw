package com.cosmosource.app.property.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrConferencePackage;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

/** 
* @description: 会务套餐
* @author chengyun
* @date 2013-07-24
*/ 
@SuppressWarnings("unchecked")
public class ConferencePackageManager extends BaseManager{

	public void updateConferencePackageStateById(Long conferencePackageId,String conferencePackageState){
		List<Map> list = new ArrayList();
		Map map=new HashMap();
		map.put("conferencePackageId", conferencePackageId);
		map.put("conferencePackageState", conferencePackageState);
		list.add(map);
		sqlDao.batchUpdate("PropertySQL.updateConferencePackageStateById", list);
	}
	public void updateConferencePackageState(Long conferencePackageId,String conferenceState){
		List<Map> list = new ArrayList();
		Map map=new HashMap();
		map.put("conferencePackageId", conferencePackageId);
		map.put("conferenceState", conferenceState);
		list.add(map);
		sqlDao.batchUpdate("PropertySQL.updateConferencePackageState", list);
	}
	public void addOneConferencePackage(GrConferencePackage conferencePackage){
		List<Map> list = new ArrayList();
		Map map=new HashMap();
		map.put("conferencePackageName", conferencePackage.getConferencePackageName());
		map.put("conferencePackageRoom", conferencePackage.getConferencePackageRoom());
		map.put("conferencePackageLocation", conferencePackage.getConferencePackageLocation());
		map.put("conferencePackageSeat", conferencePackage.getConferencePackageSeat());
		map.put("conferencePackageStyle", conferencePackage.getConferencePackageStyle());
		map.put("conferencePackageFacility", conferencePackage.getConferencePackageFacility());
		map.put("conferencePackagePrice", conferencePackage.getConferencePackagePrice());
		map.put("conferencePackageService", conferencePackage.getConferencePackageService());
		map.put("conferencePackageState", conferencePackage.getConferencePackageState());
		map.put("resBak1", conferencePackage.getResBak1());
		map.put("resBak2", conferencePackage.getResBak2());
		map.put("resBak3", conferencePackage.getResBak3());
		map.put("resBak4", conferencePackage.getResBak4());
		list.add(map);
		sqlDao.batchInsert("PropertySQL.insertConferencePackage", list);
	}
	
	public void updateOneConferencePackage(GrConferencePackage conferencePackage){
		List<Map> list = new ArrayList();
		Map map=new HashMap();
		map.put("conferencePackageId", conferencePackage.getConferencePackageId());
		map.put("conferencePackageName", conferencePackage.getConferencePackageName());
		map.put("conferencePackageRoom", conferencePackage.getConferencePackageRoom());
		map.put("conferencePackageLocation", conferencePackage.getConferencePackageLocation());
		map.put("conferencePackageSeat", conferencePackage.getConferencePackageSeat());
		map.put("conferencePackageStyle", conferencePackage.getConferencePackageStyle());
		map.put("conferencePackageFacility", conferencePackage.getConferencePackageFacility());
		map.put("conferencePackagePrice", conferencePackage.getConferencePackagePrice());
		map.put("conferencePackageService", conferencePackage.getConferencePackageService());
		map.put("conferencePackageState", conferencePackage.getConferencePackageState());
		map.put("resBak1", conferencePackage.getResBak1());
		map.put("resBak2", conferencePackage.getResBak2());
		map.put("resBak3", conferencePackage.getResBak3());
		map.put("resBak4", conferencePackage.getResBak4());
		list.add(map);
		sqlDao.batchInsert("PropertySQL.updateConferencePackage", list);
	}
	
	public void deleteOneConferencePackage(Long conferencePackageId){
		List<Map> list = new ArrayList();
		Map map=new HashMap();
		map.put("conferencePackageId", conferencePackageId);
		list.add(map);
		sqlDao.batchDelete("PropertySQL.deleteConferencePackage", list);
	}
	
	public List<GrConferencePackage> queryConferencePackages(String conferencePackageName,String conferencePackageRoom,
									String conferencePackageLocation,String conferencePackageStyle,String conferencePackageState){
		Map map = new HashMap();
		map.put("conferencePackageName", conferencePackageName);
		map.put("conferencePackageRoom", conferencePackageRoom);
		map.put("conferencePackageLocation", conferencePackageLocation);
		map.put("conferencePackageStyle", conferencePackageStyle);
		map.put("conferencePackageState", conferencePackageState);
		
		List<Map> list = sqlDao.findList("PropertySQL.selectAllConferencePackages1", map);
		if(list.isEmpty())
			return null;
		else {
			return mapToConferencePackage(list);
		}
	}
	
	public List<GrConferencePackage> mapToConferencePackage(List<Map> list){
		List<GrConferencePackage> result = new ArrayList<GrConferencePackage>();
		GrConferencePackage conferencePackage;
		
		for(Map map:list){
			conferencePackage = new GrConferencePackage();
			conferencePackage.setConferencePackageId(Long.parseLong(String.valueOf(map.get("CONFERENCE_PACKAGE_ID"))));
			conferencePackage.setConferencePackageName((String)map.get("CONFERENCE_PACKAGE_NAME"));
			conferencePackage.setConferencePackageRoom((String)map.get("CONFERENCE_PACKAGE_ROOM"));
			conferencePackage.setConferencePackageLocation((String)map.get("CONFERENCE_PACKAGE_LOCATION"));
			conferencePackage.setConferencePackageSeat((String)map.get("CONFERENCE_PACKAGE_SEAT"));
			conferencePackage.setConferencePackageStyle((String)map.get("CONFERENCE_PACKAGE_STYLE"));
			conferencePackage.setConferencePackageFacility((String)map.get("CONFERENCE_PACKAGE_FACILITY"));
			conferencePackage.setConferencePackagePrice((String)map.get("CONFERENCE_PACKAGE_PRICE"));
			conferencePackage.setConferencePackageService((String)map.get("CONFERENCE_PACKAGE_SERVICE"));
			conferencePackage.setConferencePackageState((String)map.get("CONFERENCE_PACKAGE_STATE"));
			conferencePackage.setResBak1((String)map.get("RES_BAK1"));
			conferencePackage.setResBak2((String)map.get("RES_BAK2"));
			conferencePackage.setResBak3((String)map.get("RES_BAK3"));
			conferencePackage.setResBak4((String)map.get("RES_BAK4"));
			
			result.add(conferencePackage);
		}
		
		return result;		
	}
	
	public GrConferencePackage findGrConferencePackageById(Long conferencePackageId){
		Map map = new HashMap();
		map.put("conferencePackageId", conferencePackageId);
		List<Map> list = sqlDao.findList("PropertySQL.findGrConferencePackageById", map);
		List<GrConferencePackage> result;
		GrConferencePackage conferencePackage =null;
		
		if(list.isEmpty()){
			return null;
		}else {
			result = mapToConferencePackage(list);
			conferencePackage = result.get(0);
		}
		
		return conferencePackage;
	}
	
	
	public Page<HashMap> queryAllConferencePackages(final Page<HashMap> page, HashMap<String, String> parMap){
		return sqlDao.findPage(page, "PropertySQL.selectAllConferencePackages", parMap);
	}
	
	public Page<HashMap> queryOneConferences(final Page<HashMap> page, HashMap parMap){
		return sqlDao.findPage(page, "PropertySQL.selectOneConferences", parMap);
	}
	
	public Page<HashMap> checkConference(final Page<HashMap> page, HashMap parMap){
		return sqlDao.findPage(page, "PropertySQL.checkConference", parMap);
	}
	
	public Page<HashMap> checkAddConference(final Page<HashMap> page, HashMap parMap){
		return sqlDao.findPage(page, "PropertySQL.checkAddConference", parMap);
	}
	
	public Boolean checkConferencePackageName(String conferencePackageName){
		Map map = new HashMap();
		map.put("conferencePackageName", conferencePackageName);
		List list = sqlDao.findList("PropertySQL.checkConferencePackageName", map);
		if(list==null||list.size()==0){
			return true;
		}else{
			return false;
		}
	}

	public Boolean checkConferencePackageRoom(String conferencePackageRoom){
		Map map = new HashMap();
		map.put("conferencePackageRoom", conferencePackageRoom);
		List list = sqlDao.findList("PropertySQL.checkConferencePackageRoom", map);
		if(list==null||list.size()==0){
			return true;
		}else{
			return false;
		}
	}
	
}
