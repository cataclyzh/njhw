package com.cosmosource.app.property.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.cosmosource.app.entity.GrConference;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.DateUtil;


/** 
* @description: 会务服务
* @author chengyun
* @date 2013-07-10
*/ 
@SuppressWarnings("unchecked")
public class ConferenceManager extends BaseManager {
	
	public int updateConferenceStateById(Long conferenceId,String confrenceState){
		List<Map> list = new ArrayList();
		Map map=new HashMap();
		map.put("conferenceId", conferenceId);
		map.put("conferenceState",confrenceState);
		list.add(map);
		sqlDao.batchUpdate("PropertySQL.updateConferenceStateById", list);
		return 0;
	}
	
	public int updatePassConferenceState(String nowDate){
		List<Map> list = new ArrayList();
		Map map=new HashMap();
		map.put("nowDate", nowDate);
		list.add(map);
		sqlDao.batchUpdate("PropertySQL.updatePassConferenceState", list);
		return 0;
	}
	
	
	
	public void addOneConference(GrConference conference){
		List<Map> list = new ArrayList();
		Map map=new HashMap();
//		map.put("conferenceId", conference.getConferenceId());
		map.put("conferenceName", conference.getConferenceName());
		map.put("conferenceStartTime", conference.getConferenceStartTime());
		map.put("conferenceEndTime", conference.getConferenceEndTime());
		map.put("conferenceUserId", conference.getConferenceUserId());
		map.put("conferenceUserName", conference.getOutName());
		map.put("conferenceUserORG", conference.getOrgName());
		map.put("conferenceUserPhone", conference.getPhonespan());
		map.put("conferenceRoom", conference.getConferenceRoom());
		map.put("conferenceManCount", conference.getConferenceManCount());
		map.put("conferenceState", conference.getConferenceState());
		map.put("conferenceHasService", conference.getConferenceHasService());
		map.put("conferenceDetailService", conference.getConferenceDetailService());
		map.put("conferenceSatisfy",conference.getConferenceSatisfy());
		map.put("conferenceClientValue",conference.getConferenceClientValue());
		map.put("resBak1",conference.getResBak1());
		map.put("resBak2",conference.getResBak2());
		map.put("resBak3",conference.getResBak3());
		map.put("resBak4",conference.getResBak4());
		map.put("conferencePackageId", conference.getConferencePackageId());
		list.add(map);
		sqlDao.batchInsert("PropertySQL.insertConference", list);
	}
	
	public void addConferences(List<GrConference> conferences){
		List<Map> list = new ArrayList();
		Map map=null;
		for(GrConference conference:conferences){
			map = new HashMap();
			map.put("conferenceName", conference.getConferenceName());
			map.put("conferenceStartTime", conference.getConferenceStartTime());
			map.put("conferenceEndTime", conference.getConferenceEndTime());
			map.put("conferenceUserId", conference.getConferenceUserId());
			map.put("conferenceUserName", conference.getOutName());
			map.put("conferenceUserORG", conference.getOrgName());
			map.put("conferenceUserPhone", conference.getPhonespan());
			map.put("conferenceRoom", conference.getConferenceRoom());
			map.put("conferenceManCount", conference.getConferenceManCount());
			map.put("conferenceState", conference.getConferenceState());
			map.put("conferenceHasService", conference.getConferenceHasService());
			map.put("conferenceDetailService", conference.getConferenceDetailService());
			map.put("conferenceSatisfy",conference.getConferenceSatisfy());
			map.put("conferenceClientValue",conference.getConferenceClientValue());
			map.put("resBak1",conference.getResBak1());
			map.put("resBak2",conference.getResBak2());
			map.put("resBak3",conference.getResBak3());
			map.put("resBak4",conference.getResBak4());
			map.put("conferencePackageId", conference.getConferencePackageId());
			list.add(map);
		}
		sqlDao.batchInsert("PropertySQL.insertConference", list);
	}

	public void deleteOneConference(Long conferenceId){
		List<Map> list = new ArrayList();
		Map map=new HashMap();
		map.put("conferenceId", conferenceId);
		list.add(map);
		
		sqlDao.batchDelete("PropertySQL.deleteConference", list);
	}
	
	public void deleteConferences(Long[] conferenceIds){
		List<Map> list = new ArrayList();
		Map map=null;
		for(Long conferenceId:conferenceIds){
			map = new HashMap();
			map.put("conferenceId", conferenceId);
			list.add(map);
		}		
		sqlDao.batchDelete("PropertySQL.deleteConference", list);
	}
	
	public void updateOneConference(GrConference conference){
		List<Map> list = new ArrayList();
		Map map=new HashMap();
		map.put("conferenceId", conference.getConferenceId());
		map.put("conferenceName", conference.getConferenceName());
		map.put("conferenceStartTime", conference.getConferenceStartTime());
		map.put("conferenceEndTime", conference.getConferenceEndTime());
		map.put("conferenceUserId", conference.getConferenceUserId());
		map.put("conferenceUserName", conference.getOutName());
		map.put("conferenceUserORG", conference.getOrgName());
		map.put("conferenceUserPhone", conference.getPhonespan());
		map.put("conferenceRoom", conference.getConferenceRoom());
		map.put("conferenceManCount", conference.getConferenceManCount());
		map.put("conferenceState", conference.getConferenceState());
		map.put("conferenceHasService", conference.getConferenceHasService());
		map.put("conferenceDetailService", conference.getConferenceDetailService());
		map.put("conferenceSatisfy",conference.getConferenceSatisfy());
		map.put("conferenceClientValue",conference.getConferenceClientValue());
		map.put("resBak1",conference.getResBak1());
		map.put("resBak2",conference.getResBak2());
		map.put("resBak3",conference.getResBak3());
		map.put("resBak4",conference.getResBak4());
		map.put("conferencePackageId", conference.getConferencePackageId());
		list.add(map);
		sqlDao.batchUpdate("PropertySQL.updateConference", list);
	}
	public void valeOneConference(long conferenceId,String conferenceState,String conferenceClientValue,String conferenceSatisfy){
		List<Map> list = new ArrayList();
		Map map=new HashMap();
		map.put("conferenceId", conferenceId);
		map.put("conferenceState",conferenceState);
		map.put("conferenceClientValue",conferenceClientValue);
		map.put("conferenceSatisfy",conferenceSatisfy);
		
		list.add(map);
		sqlDao.batchUpdate("PropertySQL.updateConferenceValueById", list);
	}
	
	public void updateConferences(List<GrConference> conferences){
		List<Map> list = new ArrayList();
		Map map=null;
		for(GrConference conference:conferences){
			map = new HashMap();
			map.put("conferenceId", conference.getConferenceId());
			map.put("conferenceName", conference.getConferenceName());
			map.put("conferenceStartTime", conference.getConferenceStartTime());
			map.put("conferenceEndTime", conference.getConferenceEndTime());
			map.put("conferenceUserId", conference.getConferenceUserId());
			map.put("conferenceUserName", conference.getOutName());
			map.put("conferenceUserORG", conference.getOrgName());
			map.put("conferenceUserPhone", conference.getPhonespan());
			map.put("conferenceRoom", conference.getConferenceRoom());
			map.put("conferenceManCount", conference.getConferenceManCount());
			map.put("conferenceState", conference.getConferenceState());
			map.put("conferenceHasService", conference.getConferenceHasService());
			map.put("conferenceDetailService", conference.getConferenceDetailService());
			map.put("conferenceSatisfy",conference.getConferenceSatisfy());
			map.put("conferenceClientValue",conference.getConferenceClientValue());
			map.put("resBak1",conference.getResBak1());
			map.put("resBak2",conference.getResBak2());
			map.put("resBak3",conference.getResBak3());
			map.put("resBak4",conference.getResBak4());
			map.put("conferencePackageId", conference.getConferencePackageId());
			list.add(map);
		}
		sqlDao.batchUpdate("PropertySQL.updateConference", list);
	}
	
	
	public List<GrConference> queryConferences(String conferenceName,Date conferenceStartTime,
			Date conferenceEndTime,String conferenceUserName,String conferenceHasService,
			String conferenceState,Long conferencePackageId){
		
		Map map = new HashMap();
		List<Map> list = new ArrayList<Map>();
		map.put("conferenceName", conferenceName);
		map.put("conferenceStartTime", conferenceStartTime);
		map.put("conferenceEndTime", conferenceEndTime);
//		map.put("conferenceUserId", conferenceUserId);
		map.put("conferenceUserName", conferenceUserName);
		map.put("conferenceHasService", conferenceHasService);
		map.put("conferenceState", conferenceState);
			map.put("conferencePackageId", conferencePackageId);
		list = sqlDao.findList("PropertySQL.selectAllConferences", map);
		return mapToConference(list);
	}
	
	public List<GrConference> mapToConference(List<Map> list){
		List<GrConference> result = new ArrayList<GrConference>();
		GrConference conference;
		for(Map map : list){
			conference = new GrConference();
			conference.setConferenceId(Long.parseLong(String.valueOf(map.get("CONFERENCE_ID"))));
			conference.setConferenceName((String)map.get("CONFERENCE_NAME"));
			if(map.get("CONFERENCE_STARTTIME")!=null){
			conference.setConferenceStartTime(DateUtil.StringToDate(map.get("CONFERENCE_STARTTIME").toString()));
			conference.setStartTime(map.get("CONFERENCE_STARTTIME").toString());
			
			}
			if(map.get("CONFERENCE_ENDTIME")!=null){
			conference.setConferenceEndTime(DateUtil.StringToDate(map.get("CONFERENCE_ENDTIME").toString()));
			conference.setEndTime(map.get("CONFERENCE_ENDTIME").toString());
			}
			if(map.get("CONFERENCE_USERID")!=null)
				conference.setConferenceUserId(Long.parseLong(String.valueOf(map.get("CONFERENCE_USERID"))));
			conference.setOutName((String)map.get("CONFERENCE_USERNAME"));
			conference.setOrgName((String)map.get("CONFERENCE_USERORG"));
			conference.setPhonespan((String)map.get("CONFERENCE_USERPHONE"));
			conference.setConferenceRoom((String)map.get("CONFERENCE_ROOM"));
			conference.setConferenceManCount((String)map.get("CONFERENCE_MANCOUNT"));
			conference.setConferenceState((String)map.get("CONFERENCE_STATE"));
			conference.setConferenceHasService((String)map.get("CONFERENCE_HASSERVICE"));
			conference.setConferenceDetailService((String)map.get("CONFERENCE_DETAIL_SERVICE"));
			conference.setConferenceSatisfy((String)map.get("CONFERENCE_SATISFY"));
			conference.setConferenceClientValue((String)map.get("CONFERENCE_CLIENTVALUE"));
			conference.setResBak1((String)map.get("RES_BAK1"));
			conference.setResBak2((String)map.get("RES_BAK2"));
			conference.setResBak3((String)map.get("RES_BAK3"));
			conference.setResBak4((String)map.get("RES_BAK4"));
			if(map.get("CONFERENCE_PACKAGE_ID")!=null)
				conference.setConferencePackageId(Long.parseLong((String.valueOf(map.get("CONFERENCE_PACKAGE_ID")))));
			else
				conference.setConferencePackageId(null);
			result.add(conference);
		}
		
		return result;
	}
	public List<GrConference> queryConferencesForIndex(String conferenceName,Date conferenceStartTime,
			Date conferenceEndTime,String conferenceUserName,String conferenceHasService,
			String conferenceState,Long conferencePackageId){
		
		Map map = new HashMap();
		List<Map> list = new ArrayList<Map>();
		map.put("conferenceName", conferenceName);
		map.put("conferenceStartTime", conferenceStartTime);
		map.put("conferenceEndTime", conferenceEndTime);
//		map.put("conferenceUserId", conferenceUserId);
		map.put("conferenceUserName", conferenceUserName);
		map.put("conferenceHasService", conferenceHasService);
		map.put("conferenceState", conferenceState);
			map.put("conferencePackageId", conferencePackageId);
		list = sqlDao.findList("PropertySQL.selectAllConferences", map);
		return mapToConferenceForIndex(list);
	}
	
	public List<GrConference> mapToConferenceForIndex(List<Map> list){
		List<GrConference> result = new ArrayList<GrConference>();
		GrConference conference;
		for(Map map : list){
			conference = new GrConference();
			conference.setConferenceId(Long.parseLong(String.valueOf(map.get("CONFERENCE_ID"))));
			
			if(map.get("CONFERENCE_NAME")!=null){
				
                String conferenceName=String.valueOf(map.get("CONFERENCE_NAME"));
				
				if(conferenceName.length()>4){
					conference.setConferenceName(conferenceName.substring(0,4)+"..");
				}else{
					conference.setConferenceName(conferenceName);
				}
			}
			
			if(map.get("CONFERENCE_STARTTIME")!=null){
			conference.setConferenceStartTime(DateUtil.StringToDate(map.get("CONFERENCE_STARTTIME").toString()));
			
			
			String startTimeString=String.valueOf(map.get("CONFERENCE_STARTTIME"));
			
			if(startTimeString.length()>11){
				conference.setStartTime(startTimeString.substring(0, 10));
			}else{
				conference.setStartTime(startTimeString);
			}
			
			
			}
			if(map.get("CONFERENCE_ENDTIME")!=null){
			conference.setConferenceEndTime(DateUtil.StringToDate(map.get("CONFERENCE_ENDTIME").toString()));
			conference.setEndTime(map.get("CONFERENCE_ENDTIME").toString());
			}
			if(map.get("CONFERENCE_USERID")!=null)
				conference.setConferenceUserId(Long.parseLong(String.valueOf(map.get("CONFERENCE_USERID"))));
			
	        if(map.get("CONFERENCE_USERNAME")!=null){
				
                String conferenceUserName=String.valueOf(map.get("CONFERENCE_USERNAME"));
				
				if(conferenceUserName.length()>4){
					conference.setOutName(conferenceUserName.substring(0,4)+"..");
				}else{
					conference.setOutName(conferenceUserName);
				}
			}
			
			
			
			
			
			conference.setOrgName((String)map.get("CONFERENCE_USERORG"));
			conference.setPhonespan((String)map.get("CONFERENCE_USERPHONE"));
			conference.setConferenceRoom((String)map.get("CONFERENCE_ROOM"));
			conference.setConferenceManCount((String)map.get("CONFERENCE_MANCOUNT"));
			conference.setConferenceState((String)map.get("CONFERENCE_STATE"));
			conference.setConferenceHasService((String)map.get("CONFERENCE_HASSERVICE"));
			conference.setConferenceDetailService((String)map.get("CONFERENCE_DETAIL_SERVICE"));
			conference.setConferenceSatisfy((String)map.get("CONFERENCE_SATISFY"));
			conference.setConferenceClientValue((String)map.get("CONFERENCE_CLIENTVALUE"));
			conference.setResBak1((String)map.get("RES_BAK1"));
			conference.setResBak2((String)map.get("RES_BAK2"));
			conference.setResBak3((String)map.get("RES_BAK3"));
			conference.setResBak4((String)map.get("RES_BAK4"));
			if(map.get("CONFERENCE_PACKAGE_ID")!=null)
				conference.setConferencePackageId(Long.parseLong((String.valueOf(map.get("CONFERENCE_PACKAGE_ID")))));
			else
				conference.setConferencePackageId(null);
			result.add(conference);
		}
		
		return result;
	}
	
	public GrConference findConferenceById(Long conferenceId){
		Map map = new HashMap();
		map.put("conferenceId", conferenceId);
		List<Map> list = sqlDao.findList("PropertySQL.findConferenceById", map);
		List<GrConference> result;
		GrConference conference = null;
		if(list.isEmpty())
			return null;
		else {
			result = mapToConference(list);
			conference= (GrConference)result.get(0);
		}			
		return conference;
	}
	
	
	public Page<HashMap> queryAllConferences(final Page<HashMap> page, HashMap<String, String> parMap,String type){
		if ("export".equals(type)) {
			page.setResult(sqlDao.findList("PropertySQL.selectAllConferences", parMap));
			return page;
		}else {
			return sqlDao.findPage(page, "PropertySQL.selectAllConferences", parMap);
		}
	}
	
	public Map getConferenceInfoById(String id) throws Exception{
		List<Map> list = sqlDao.getSqlMapClientTemplate().queryForList("PropertySQL.getConferenceInfoById", id);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public void saveEvaluateConference(Map m) throws Exception{
		sqlDao.getSqlMapClientTemplate().update("PropertySQL.saveEvaluateConference", m);
	}
	
	public Page<HashMap> queryMyConferences(final Page<HashMap> page, HashMap<String, String> parMap){
		return sqlDao.findPage(page, "PropertySQL.selectMyConferences", parMap);
	}
	
	
	public Page<HashMap> queryOneConferencesEx(final Page<HashMap> page, HashMap parMap){
		return sqlDao.findPage(page, "PropertySQL.selectOneConferencesEx", parMap);
	}
	
	
	
}
