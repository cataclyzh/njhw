package com.cosmosource.app.property.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import com.cosmosource.app.entity.GrEmOrgResCar;
import com.cosmosource.app.entity.GrParkingInfo;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

/** 
* @description: 车位分配
* @author chengyun
* @date 2013-07-03
*/ 
@SuppressWarnings("unchecked")
public class ParkingInfoManager extends BaseManager {
	
	public ParkingTotalNum parkingTotalNum;

	public Page<GrParkingInfo> getRegister(final Page<GrParkingInfo> page,final Map<String,Object> obj){

		Map map = new HashMap();
		map.put("parkingInfoOrgName", obj.get("parkingInfoOrgName"));
		return sqlDao.findPage(page, "PropertySQL.selectAllParkinginfos", map);
	}
	
   
	
	
	public void addOneParkinginfo(Map map){
		List<Map> list = new ArrayList();		
		list.add(map);
		sqlDao.batchInsert("PropertySQL.insertParkinginfo", list);
	}
	
	public String selectCarNumber(){
	
		String carNumber="";
		List<Map> list =sqlDao.findList("PropertySQL.selectCarNumber", null);
		for(Map map: list){
			if(map.get("TOTALNUM")!=null){
				carNumber=map.get("TOTALNUM").toString();
			}else{
				carNumber="0";
			}
			
		}
		return carNumber;
	}
	
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	
	
	public String selectOrgName(Map map){
	
		String orgName="";
		List<Map> list =sqlDao.findList("PropertySQL.findParkingOrgById", map);
		for(Map map1: list){
			if(map1.get("ORGNAME")!=null){
				orgName=map1.get("ORGNAME").toString();
			}else{
				orgName="";
			}
			
		}
		return orgName;
	}
	public String selectOrgNum(Map map){
		
		String parkingNum="";
		List<Map> list =sqlDao.findList("PropertySQL.findParkingNumById", map);
		for(Map map11: list){
			if(map11.get("PARKINGNUM")!=null){
				parkingNum=map11.get("PARKINGNUM").toString();
			}else{
				parkingNum="";
			}
			
		}
		return parkingNum;
	}
	public String selectOrgId(Map map){
		
		String parkingNum="";
		List<Map> list =sqlDao.findList("PropertySQL.findParkingIdById", map);
		for(Map map11: list){
			if(map11.get("PARKINGNUM")!=null){
				parkingNum=map11.get("PARKINGNUM").toString();
			}else{
				parkingNum="";
			}
			
		}
		return parkingNum;
	}
	
	
	
	
	
	public List queryNotIn(){
		List<HashMap> resultList = sqlDao.findList("PropertySQL.selectOrgNotIn", null);
		List<GrEmOrgResCar> list = new ArrayList<GrEmOrgResCar>();
		if (resultList != null && resultList.size() > 0){
			for (int i = 0;i<resultList.size();i++){
				GrEmOrgResCar grEmOrgResCar = new GrEmOrgResCar();
				if (resultList.get(i).get("ORG_ID") != null){
					grEmOrgResCar.setOrgId(Long.parseLong(String.valueOf(resultList.get(i).get("ORG_ID"))));
				}
				
				if (resultList.get(i).get("NAME") != null){
					grEmOrgResCar.setName(String.valueOf(resultList.get(i).get("NAME")));
				}
				
				list.add(grEmOrgResCar);
			}
		}
		return list;
	}
	
	public void insertNotIn(Map map){
		List<Map> list = new ArrayList();		
		list.add(map);
		sqlDao.batchInsert("PropertySQL.insertNotIn", list);
	}
	
	
	
	
	
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public String selectTotalNumber(){
		
		String carTotalNumber="";
		List<Map> list =sqlDao.findList("PropertySQL.selectTotalNumber", null);
		for(Map map: list){
			carTotalNumber=map.get("PARKING").toString();
		}
		return carTotalNumber;
	}

	public void updateTotalNumber(Map map){
		List<Map> list = new ArrayList();		
		list.add(map);
		sqlDao.batchUpdate("PropertySQL.updateTotalNumber", list);
	}

	
	public void addParkinginfos(List<GrParkingInfo> parkinginfos){
		List<Map> list = new ArrayList();
		Map map = null;
		for(GrParkingInfo parkinginfo : parkinginfos){
			map = new HashMap();
			map.put("parkingInfoOrgId", parkinginfo.getParkingInfoOrgId());
			map.put("parkingInfoOrgName", parkinginfo.getParkingInfoOrgName());
			map.put("parkingInfoNumber", parkinginfo.getParkingInfoNumber());
			map.put("resBak1", parkinginfo.getResBak1());
			map.put("resBak2", parkinginfo.getResBak2());
			map.put("resBak3", parkinginfo.getResBak3());
			map.put("resBak4", parkinginfo.getResBak4());
			
			list.add(map);
		}
		sqlDao.batchInsert("PropertySQL.insertParkinginfo", list);
	}
	
	public void deleteOneParkinginfo(Long parkingInfoId){
		List<Map> list = new ArrayList();
		Map map = new HashMap();
		map.put("parkingInfoId", parkingInfoId);
		list.add(map);
		
		sqlDao.batchDelete("PropertySQL.deleteParkinginfo", list);
	}
	
	public void deleteSelectParkinginfo(String[] parkingInfoId){
		List list = new ArrayList();
		HashMap parMap = new HashMap();
		parMap.put("parkingInfoId", parkingInfoId[0]);
		list.add(parMap);
		sqlDao.batchDelete("PropertySQL.deleteParkinginfo", list);
		
		dao.deleteByIds(GrParkingInfo.class, parkingInfoId);
	}
	
	public void deleteParkinginfos(Long[] parkingInfoIds){
		List<Map> list = new ArrayList();
		Map map =null;
		for(Long parkingInfoId:parkingInfoIds){
			map = new HashMap();
			map.put("parkingInfoId", parkingInfoId);
			list.add(map);
		}
		sqlDao.batchDelete("PropertySQL.deleteParkinginfo", list);
	}
	
	public void updateOneParkinginfo(Map map){
		List<Map> list = new ArrayList();		
		list.add(map);
		sqlDao.batchUpdate("PropertySQL.updateParkinginfo", list);
	}
	
	public void saveOrUpdate(Object entity) {
		super.dao.saveOrUpdate(entity);
	}
	
	public void updateParkinginfos(List<GrParkingInfo> parkinginfos){
		
		List<Map> list = new ArrayList();
		Map map = null;
		for(GrParkingInfo parkinginfo : parkinginfos){
			map = new HashMap();
			map.put("parkingInfoId", parkinginfo.getParkingInfoId());
			map.put("parkingInfoOrgId", parkinginfo.getParkingInfoOrgId());
			map.put("parkingInfoOrgName", parkinginfo.getParkingInfoOrgName());
			map.put("parkingInfoNumber", parkinginfo.getParkingInfoNumber());
			map.put("resBak1", parkinginfo.getResBak1());
			map.put("resBak2", parkinginfo.getResBak2());
			map.put("resBak3", parkinginfo.getResBak3());
			map.put("resBak4", parkinginfo.getResBak4());
			
			list.add(map);
		}
		sqlDao.batchUpdate("PropertySQL.updateParkinginfo", list);
	}
	
	public List<GrParkingInfo> queryParkinginfos(Long parkingInfoOrgId,String parkingInfoOrgName,Long parkingInfoNumber){
		Map map = new HashMap();
		map.put("parkingInfoOrgId", parkingInfoOrgId);
		map.put("parkingInfoOrgName", parkingInfoOrgName);
		map.put("parkingInfoNumber", parkingInfoNumber);
		List<Map> list = sqlDao.findList("PropertySQL.selectAllParkinginfos", map);
		if(list.isEmpty()){
			return null;
		}
		else {
			return mapToParkinginfo(list);
		}
	}
	
	public List<GrParkingInfo> mapToParkinginfo(List<Map> list){
		List<GrParkingInfo> result = new ArrayList<GrParkingInfo>();
		GrParkingInfo parkinginfo;
		for(Map map: list){
			parkinginfo = new GrParkingInfo();
			parkinginfo.setParkingInfoId(Long.parseLong(map.get("PARKINGINFO_ID").toString()));
			parkinginfo.setParkingInfoOrgId(Long.parseLong(map.get("PARKINGINFO_ORG_ID").toString()));
			parkinginfo.setParkingInfoOrgName(map.get("PARKINGINFO_ORGNAME").toString());
			parkinginfo.setParkingInfoNumber(map.get("PARKINGINFO_NUMBER").toString());
			parkinginfo.setResBak1((String)map.get("RES_BAK1"));
			parkinginfo.setResBak2((String)map.get("RES_BAK2"));
			parkinginfo.setResBak3((String)map.get("RES_BAK3"));
			parkinginfo.setResBak4((String)map.get("RES_BAK4"));
			result.add(parkinginfo);
		}
		return result;
	}
	
	public GrParkingInfo findParkinginfoById(Long parkingInfoId){
		Map map = new HashMap();
		map.put("parkingInfoId", parkingInfoId);
		List<Map> list = sqlDao.findList("PropertySQL.findParkinginfoById", map);
		List<GrParkingInfo> result;
		GrParkingInfo parkinginfo=null;
		if(list.isEmpty())
			return null;
		else {
			result = mapToParkinginfo(list);
			parkinginfo=(GrParkingInfo)result.get(0);
		}
		return parkinginfo;
	}
	
	public Page<HashMap> queryAllParkinginfos(final Page<HashMap> page, HashMap<String, String> parMap){
		return sqlDao.findPage(page, "PropertySQL.selectAllParkinginfos", parMap);
	}
	
	/**
	 * 通过读取配置文件得到车位总数
	 */
	public String getTotalNum(){

		Resource ctx = new FileSystemResource("E:/xcds/workspace5/app_njhw/src/com/cosmosource/app/property/spring/spring-app-callcenter.xml");
		BeanFactory factory = new XmlBeanFactory(ctx); 
		parkingTotalNum = (ParkingTotalNum)factory.getBean("parkingTotalNum");
		return parkingTotalNum.getTotalNum();
	}
	/**
	 * 将车位总数写入到配置文件中
	 * @throws DocumentException 
	 * @throws IOException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 */
	public void setTotalNum(String totalnum) throws DocumentException, IOException{
        String xmlAddress ="E:/xcds/workspace5/app_njhw/src/com/cosmosource/app/property/spring/spring-app-callcenter.xml";
		File file = new File(xmlAddress);
		SAXReader sax = new SAXReader();
		org.dom4j.Document document = sax.read(file);
        
		org.dom4j.Element root = document.getRootElement();
		root = root.element("bean");
		if(root.attribute("id").getText().equals("parkingTotalNum")){

				org.dom4j.Element e=root.element("property");
				String name = e.attribute("name").getText();
				if(name.equals("totalNum")){
					org.dom4j.Element value = e.element("value");
						value.setText(totalnum);
				}
			 }

		
		 //重新写入到文件
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    FileWriter fileWriter = new FileWriter(file);
	    XMLWriter output = new XMLWriter(fileWriter,format); 
	    output.write(document);
	    output.close();
	}
	
	public ParkingTotalNum getParkingTotalNum() {
		return parkingTotalNum;
	}

	public void setParkingTotalNum(ParkingTotalNum parkingTotalNum) {
		this.parkingTotalNum = parkingTotalNum;
	}

}
