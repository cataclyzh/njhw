package com.cosmosource.app.property.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.GrEmOrgResCar;
import com.cosmosource.app.entity.GrParkingInfo;
import com.cosmosource.app.entity.NjhwUsersPlatenum;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.property.service.ParkingInfoManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.ConvertUtils;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;

/**
 * @description: 车辆进出管理
 * @author chengyun
 * @date 2013-07-09
 */
@SuppressWarnings("unchecked")
public class ParkingInfoAction extends BaseAction<GrParkingInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 定义实体变量
	private GrParkingInfo parkingInfo = new GrParkingInfo();
	private GrEmOrgResCar emOrgResCar = new GrEmOrgResCar();

	// 定义分页变量
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页10条记录
	private Page<GrParkingInfo> pageTwo = new Page<GrParkingInfo>(
			Constants.PAGESIZE);// 默认每页10条记录
	private Long parkingInfoOrgId;

	// 定义注入对象
	private ParkingInfoManager parkingInfoManager;
	List<GrParkingInfo> result = new ArrayList<GrParkingInfo>();
	
	private String orgname;
	private String department;
	private String username;
	private String platenumber;
	
	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPlatenumber() {
		return platenumber;
	}

	public void setPlatenumber(String platenumber) {
		this.platenumber = platenumber;
	}

	public List<GrParkingInfo> getResult() {
		return result;
	}

	public void setResult(List<GrParkingInfo> result) {
		this.result = result;
	}

	/**
	 * 默认执行方法
	 */
	public String execute() {
		return SUCCESS;
	}

	/**
	 * 分页显示所有的车位分配信息list
	 * 
	 * @return
	 */
	public String parkingInfoList() {

		result = parkingInfoManager.queryParkinginfos(null, null, null);

		return SUCCESS;
	}

	public String parkingInfoListPage() throws Exception {

		getRequest().setCharacterEncoding("utf-8");
		HashMap map = new HashMap();
		map.put("parkingInfoOrgId", getRequest().getParameter(
				"parkingInfoOrgId"));
		page = parkingInfoManager.queryAllParkinginfos(page, map);
		List orgList = this.parkingInfoManager.findByHQL(
			"select t from Org t where (t.levelNum = '2' and t.PId = '2') or (t.levelNum = '1' and t.orgId <> '2')");
		orgList.add(0, new Org(null, "全部", null, null, null, null, null, null,
				null, null, null,null,null));
		getRequest().setAttribute("orgList", orgList);
		return SUCCESS;
	}

	/**
	 * title 车位列表
	 * @return
	 * @throws Exception
	 */
	
	public String parkingList() throws Exception{
		List<GrEmOrgResCar> notInList=parkingInfoManager.queryNotIn();
			for(int i=0;i<notInList.size();i++){
				
		

				Map notInMap=new HashMap();
				notInMap.put("notInORG_ID", notInList.get(i).getOrgId());
				notInMap.put("notInORG_NAME", notInList.get(i).getName());
				notInMap.put("notInRES_NAME","0");
				notInMap.put("notInINSERT_ID","1");
				notInMap.put("notInEOR_TYPE","6");	
//				notInMap.put("notInINSERT_DATE",DateUtil.getDateTime("yyyy-MM-dd"));
				parkingInfoManager.insertNotIn(notInMap);
				
				}
		
		HashMap map = new HashMap();
		map.put("parkingInfoOrgId", getRequest().getParameter(
				"parkingInfoOrgId"));
		page = parkingInfoManager.queryAllParkinginfos(page, map);
		List orgList = this.parkingInfoManager.findByHQL(
				"select t from Org t where (t.levelNum = '2' and t.PId = '2') or (t.levelNum = '1' and t.orgId <> '2') and orgId <> '1400'");
		orgList.add(0, new Org(null, "全部", null, null, null, null, null, null,
				null, null, null,null,null));
		getRequest().setAttribute("orgList", orgList);
		return SUCCESS;
	}
	
	
	
	
	
	public GrEmOrgResCar getEmOrgResCar() {
		return emOrgResCar;
	}

	public void setEmOrgResCar(GrEmOrgResCar emOrgResCar) {
		this.emOrgResCar = emOrgResCar;
	}

	public String configParkingRead() throws Exception {
		String parkingTotal = parkingInfoManager.selectTotalNumber();
		getRequest().setAttribute("totalNum", parkingTotal);

		String carsNum = parkingInfoManager.selectCarNumber();
		int carNumber = Integer.parseInt(carsNum);
		/*Map param = new HashMap();
		List<Map> CountNum = this.parkingInfoManager.findListBySql("PropertySQL.selectAllParkingCount", param);
		getRequest().setAttribute("CountNum", CountNum.get(0).get("CUN"));*/
		getRequest().setAttribute("carNumber", carNumber);
		return SUCCESS;
	}

	public String configParkingReads(){
		/*
		try {
			Map param = new HashMap();
			if(orgname != null)
				param.put("orgname", orgname);
			if(department != null)
				param.put("department", department);
			if(username != null)
				param.put("username", username);
			if(platenumber != null)
				param.put("platenumber", platenumber);
				
			page.setPageSize(15);
			page.setAutoCount(true);
			page = parkingInfoManager.findListBySql(page, "PropertySQL.queryCard", param);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return SUCCESS;
	}
	
	public String configParkingReads1(){
		return SUCCESS;
	}
	
	public String configParkingLoad() throws Exception {
		String totalNum = getRequest().getParameter("totalNum");

		Map map = new HashMap();
		map.put("parkingTotal", totalNum);

		parkingInfoManager.updateTotalNumber(map);
		return SUCCESS;

	}

	public String parkingListPage() throws Exception {
		try {
			Map localMap = ConvertUtils.pojoToMap(parkingInfo);
			pageTwo = parkingInfoManager.getRegister(pageTwo, localMap);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}

	}

	public String parkingIndex() {
		return SUCCESS;
	}

	public String lookParking() throws Exception {
		try {
			String id = getParameter("parkingInfoId");
			parkingInfo = (GrParkingInfo) parkingInfoManager.findById(
					GrParkingInfo.class, Long.valueOf(id));
			getRequest().setAttribute("parkingInfo", parkingInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 根据前台传过来的conferenceId查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findParkinginfoById() throws Exception {
		Long parkingInfoId = Long.parseLong(getRequest().getParameter(
				"parkingInfoId"));
		GrParkingInfo parkingInfo = parkingInfoManager
				.findParkinginfoById(parkingInfoId);

		if (parkingInfo != null) {
			getRequest().setAttribute("parkingInfo", parkingInfo);
			return SUCCESS;
		} else
			return ERROR;
	}

	public String deleteOneParkinginfo() {
		Long parkingInfoId = Long.parseLong(getRequest().getParameter(
				"parkingInfoId"));
		parkingInfoManager.deleteOneParkinginfo(parkingInfoId);

		return SUCCESS;
	}

	public String insertParkinginfo() {

		String carsNum = parkingInfoManager.selectCarNumber();
		int carNumber = Integer.parseInt(carsNum);
		String parkingTotal = parkingInfoManager.selectTotalNumber();
		int carTotalNumber = Integer.parseInt(parkingTotal);
		int carNumberLeft = carTotalNumber - carNumber;

		getRequest().setAttribute("carNumberLeft", carNumberLeft);
		List orgList = this.parkingInfoManager.findByHQL(
				"select t from Org t where t.levelNum = ?", Org.LEVELNUM_2);
		getRequest().setAttribute("orgList", orgList);

		return SUCCESS;
	}

	public String modifyParking() {
		int countAllot = 0;
		try {
			String carsNum = parkingInfoManager.selectCarNumber();
			int carNumber = carsNum==""?0:Integer.parseInt(carsNum);
			String parkingTotal = parkingInfoManager.selectTotalNumber();
			int carTotalNumber = parkingTotal==""?0:Integer.parseInt(parkingTotal);
			int carNumberLeft = carTotalNumber - carNumber;

			getRequest().setAttribute("carNumberLeft", carNumberLeft);

			Long id = 0l;
				
			if (StringUtil.isNotBlank(getRequest().getParameter("parkingInfoId")) && StringUtil
					.isNumeric(getRequest().getParameter("parkingInfoId"))) {
				id = Long.parseLong(getRequest().getParameter("parkingInfoId"));
			}

			String parkingOrgId = getParameter("orgId");
			Org o = (Org) parkingInfoManager.findById(Org.class, Long.parseLong(parkingOrgId));
			getRequest().setAttribute("parkingOrgName", o.getName());
			
			Long parkingOrgNum = 0l;
			
			List<EmOrgRes> le = parkingInfoManager.findByHQL("select t from EmOrgRes t where t.eorType = '6' and t.orgId = ?", Long.parseLong(parkingOrgId));
			
			if (le != null && le.size() > 0) {
				parkingOrgNum = Long.parseLong(le.get(0).getResName());
			}
			
			getRequest().setAttribute("parkingOrgNum", parkingOrgNum);
			getRequest().setAttribute("parkingInfoId", id);
			
			getRequest().setAttribute("parkingOrgId", parkingOrgId);
			
//			parkingInfo = (GrParkingInfo) parkingInfoManager.findById(
//					GrParkingInfo.class, Long.valueOf(id));
//			getRequest().setAttribute("parkingInfo", parkingInfo);
			int parkingInfoNumberInte=Integer.parseInt(parkingOrgId);
			int parkingLeftTotal=carNumberLeft+parkingInfoNumberInte;
			getRequest().setAttribute("parkingLeftTotal", parkingLeftTotal);
			
			// 统计已分配的固定车辆
			HashMap pMap = new HashMap();
			pMap.put("orgId", parkingOrgId);
			List<Map> result = parkingInfoManager.findListBySql("PersonnelSQL.queryAllocateCarNumber", pMap);
			countAllot = Integer.parseInt(result.get(0).get("NUM").toString());
			
			getRequest().setAttribute("countAllot", countAllot);
			getRequest().setAttribute("orgId", getRequest().getParameter("orgId"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 获取指定单位的停车位分配总数, 已经使用的停车位
	 * 
	 * @return Ajax调用
	 */
	public void getParkingNum(){
		try{
			//ORG_ID
			String orgId = Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString();
			
			int parkingNum;
			List<EmOrgRes> le = parkingInfoManager.findByHQL("select t from EmOrgRes t where t.eorType = '6' and t.orgId = ?", 
					Long.parseLong(orgId));
			if (le != null && le.size() > 0) {
				parkingNum = Integer.valueOf(le.get(0).getResName());
			}else{
				parkingNum = 0;
			}
			
			// 统计已分配的固定车辆
			HashMap pMap = new HashMap();
			pMap.put("orgId", orgId);
			List<Map> result = parkingInfoManager.findListBySql("PersonnelSQL.queryAllocateCarNumber", pMap);
			int countAllot = Integer.parseInt(result.get(0).get("NUM").toString());
			
			Map respMap = new HashMap();
			respMap.put("parkingNum", parkingNum);
			respMap.put("countAllot", countAllot);
			Struts2Util.renderJson(respMap, "encoding:UTF-8", "no-cache:true");
		}catch(Exception e){
			logger.error("getParkingNum error.", e);
		}
	}

	public String modifyCarParking() {
		Long parkingInfoId = Long.parseLong(getRequest().getParameter(
				"parkingInfoId"));
		String parkingInfoNumber = getRequest().getParameter(
				"parkingInfoNumber");
		String orgId = getRequest().getParameter("orgId");
		if (StringUtil.isNumeric(parkingInfoNumber)
				&& Long.parseLong(parkingInfoNumber) > 0) {
			EmOrgRes eor = (EmOrgRes) parkingInfoManager.findById(
					EmOrgRes.class, parkingInfoId);
			if (eor != null) {
				eor.setResName(parkingInfoNumber);
				eor.setModifyId(Long.parseLong(this.getRequest().getSession().getAttribute(Constants.USER_ID).toString()));
				eor.setModifyDate(new Date());
				parkingInfoManager.saveOrUpdate(eor);
			} else {
				eor = new EmOrgRes();
				eor.setOrgId(Long.parseLong(orgId));
				eor.setEorType("6");
				eor.setPorFlag("1");
				eor.setInsertId(Long.parseLong(this.getRequest().getSession().getAttribute(Constants.USER_ID).toString()));
				eor.setInsertDate(new Date());
				eor.setResName(parkingInfoNumber);
				parkingInfoManager.saveOrUpdate(eor);
			}
		}

		return SUCCESS;

	}

	public String deleteParking() throws Exception {

		try {
			String parkingInfoId = getParameter("parkingInfoId");
			String[] ids = new String[] { parkingInfoId };
			parkingInfoManager.deleteSelectParkinginfo(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String addOneParkinginfo() {
		String parkingInfoNumber = getRequest().getParameter(
				"parkingInfoNumber");
		Long parkingInfoOrgId = Long.parseLong(getRequest().getParameter(
				"parkingInfoOrgName"));
		Org org = (Org) parkingInfoManager
				.findById(Org.class, parkingInfoOrgId);
		String parkingInfoOrgName = org.getName();



		Map map = new HashMap();
		map.put("parkingInfoOrgId", parkingInfoOrgId);
		map.put("parkingInfoOrgName", parkingInfoOrgName);
		map.put("parkingInfoNumber", parkingInfoNumber);


		parkingInfoManager.addOneParkinginfo(map);
		return SUCCESS;
	}

	// public String

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public GrParkingInfo getModel() {
		// TODO Auto-generated method stub
		return parkingInfo;
	}

	public GrParkingInfo getParkingInfo() {
		return parkingInfo;
	}

	public void setParkingInfo(GrParkingInfo parkingInfo) {
		this.parkingInfo = parkingInfo;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public Page<GrParkingInfo> getPageTwo() {
		return pageTwo;
	}

	public void setPageTwo(Page<GrParkingInfo> pageTwo) {
		this.pageTwo = pageTwo;
	}

	public ParkingInfoManager getParkingInfoManager() {
		return parkingInfoManager;
	}

	public void setParkingInfoManager(ParkingInfoManager parkingInfoManager) {
		this.parkingInfoManager = parkingInfoManager;
	}

	public Long getParkingInfoOrgId() {
		return parkingInfoOrgId;
	}

	public void setParkingInfoOrgId(Long parkingInfoOrgId) {
		this.parkingInfoOrgId = parkingInfoOrgId;
	}
}
