package com.cosmosource.app.personnel.action;

import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.entity.Org;
import com.cosmosource.app.personnel.service.PerInfoQueManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;

public class PerInfoQueAction extends BaseAction {
	private PerInfoQueManager perInfoQueManager;
	private Page<HashMap> page = new Page<HashMap>(25);// 默认每页20条记录
	
	private String displayName;
	private String residentNo;
	private String phone;
	private String cityCard;
	private String carNum;
	private String orgId;
	private String recu;
	
	/*
	 * 1 否 0 是
	 */
	private String cardIsnormal;
	
	
	/*
	 * 1 是  0 否
	 */
	private String isAdmin;
	
	/*
	 * 卡类型 1 市民卡  2：临时卡
	 */
    private String cityCardType;
    
    /*
     * 是否有内部车位  2：有， 1：无
     */
	private String plateType;
	
	/*
	 *临时人员类型  2：临时人员 1：非临时人员
	 */
	private String personType;
	
	/*
	 * 房间号
	 */
    private String roomInfo;
	
    /*
	 * 电话号码
	 */
    private String tel;
    
    
    
	/** 
	 * cardIsnormal 
	 * 
	 * @return the cardIsnormal 
	 * @since   CodingExample Ver(编码范例查看) 1.0 
	 */
	
	public String getCardIsnormal()
	{
		return cardIsnormal;
	}

	/** 
	 * @param cardIsnormal the cardIsnormal to set 
	 */
	
	public void setCardIsnormal(String cardIsnormal)
	{
		this.cardIsnormal = cardIsnormal;
	}

	/** 
	 * isAdmin 
	 * 
	 * @return the isAdmin 
	 * @since   CodingExample Ver(编码范例查看) 1.0 
	 */
	
	public String getIsAdmin()
	{
		return isAdmin;
	}

	/** 
	 * @param isAdmin the isAdmin to set 
	 */
	
	public void setIsAdmin(String isAdmin)
	{
		this.isAdmin = isAdmin;
	}

	/**
	 * 进入人员查询页面
	* @title: intoqueryPerInfo 
	* @description: TODO
	* @author gxh
	* @return
	* @date 2013-5-3 上午09:49:26     
	* @throws
	 */
	public String intoqueryPerInfo() {
		return SUCCESS;
	}
   
 /**
  * 人员查询
  * @title: queryPerInfo 
  * @description: TODO
  * @author gxh
  * @return
  * @date 2013-5-3 上午09:49:53     
  * @throws
  */
   public String queryPerInfo() {
		try {
			HashMap<String,String> cMap = new HashMap<String,String>();
			cMap.put("displayName", displayName);		
			cMap.put("residentNo", residentNo);
			cMap.put("phone", phone);		
			cMap.put("cityCard", cityCard);
			cMap.put("carNum", carNum);
			cMap.put("recu", "on");
			cMap.put("orgId", orgId);
			this.page = perInfoQueManager.queryPerInfo(page, cMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
   
   /**
    * @title: perInfoCheckin 
    * @description: 人员登记查询列表
    * @author 	zh
    * @return	SUCCESS
    * @date 2013-5-8
    */
    public String perInfoCheckin() {
    	String paQuerNo = this.getRequest().getParameter("paQuerNo");
    	String opt = this.getRequest().getParameter("opt");
    	if (!StringUtil.isEmpty(paQuerNo))
		{
    		page.setPageNo(1);
		}
    	try {
    		HashMap cMap = new HashMap();
			cMap.put("displayName", displayName);		
			cMap.put("residentNo", residentNo);
			cMap.put("cityCardType", cityCardType);		
			cMap.put("plateType", plateType);		
			cMap.put("personType", personType);		
			cMap.put("phone", phone);
			cMap.put("cardIsnormal", cardIsnormal);
			if ("init".equals(opt))
			{
				setOrgId(perInfoQueManager.getRootOrgId(orgId).get(0).get("ROOTORGID").toString());
				cMap.put("orgId", perInfoQueManager.getRootOrgId(orgId).get(0).get("ROOTORGID"));
			}
			else
			{
				cMap.put("orgId", orgId);
			}
			
			cMap.put("recu", "on");
			cMap.put("roomInfo", roomInfo);
			cMap.put("tel", tel);
			if ("1".equals(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()))
			{   
				if (StringUtil.isEmpty(isAdmin))
				{   
					setIsAdmin("1");
					cMap.put("isAdmin", 1);
				}
				else 
				{
					cMap.put("isAdmin", isAdmin);
				}
			}
			
			this.page = perInfoQueManager.queryPerInfo(page, cMap);					
		} catch (Exception e) {
			e.printStackTrace();
		}
		getRequest().setAttribute("org",(Org)perInfoQueManager.findById(Org.class, Long.parseLong(orgId)));
		getRequest().setAttribute("loginId",Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		return SUCCESS;
  	}
    
    /**
     * @title: perInfoCheckinOther 
     * @description: 人员登记查询列表
     * @author 	hj
     * @return	SUCCESS
     * @date 2013-9-13
     */
     public String perInfoCheckinOther() {
     	String paQuerNo = this.getRequest().getParameter("paQuerNo");
     	String opt = this.getRequest().getParameter("opt");
     	if (!StringUtil.isEmpty(paQuerNo))
 		{
     		page.setPageNo(1);
 		}
     	try {
     		HashMap cMap = new HashMap();
 			cMap.put("displayName", displayName);		
 			cMap.put("cityCardType", cityCardType);	
 			cMap.put("phone", phone);
 			cMap.put("plateType", plateType);
 			if (!"2".equals(cityCardType)) {
 				cMap.put("cardIsnormal", cardIsnormal);
 			}
 			
 			if ("init".equals(opt))
 			{
 				List<Org> list = perInfoQueManager.findByHQL("from Org o where o.orgId = 1400 and o.orgId <> '2' order by o.orgId asc");
 				if (list != null && list.size() > 0) {
 					orgId = list.get(0).getOrgId().toString();
 				}

 				cMap.put("orgId", orgId);
 			}
 			else
 			{
 				cMap.put("orgId", orgId);
 			}
 			
 			cMap.put("recu", "on");
 			cMap.put("telNo", tel);
 			
 			this.page = perInfoQueManager.queryPerInfo(page, cMap);					
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		getRequest().setAttribute("org",(Org)perInfoQueManager.findById(Org.class, Long.parseLong(orgId)));
 		getRequest().setAttribute("loginId",Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
 		return SUCCESS;
   	}
     
     public String perInfoCheckinOther1() {
      	String paQuerNo = this.getRequest().getParameter("paQuerNo");
      	String opt = this.getRequest().getParameter("opt");
      	if (!StringUtil.isEmpty(paQuerNo))
  		{
      		page.setPageNo(1);
  		}
      	try {
      		HashMap cMap = new HashMap();
  			cMap.put("displayName", displayName);		
  			cMap.put("cityCardType", cityCardType);	
  			cMap.put("phone", phone);
 			cMap.put("plateType", plateType);
  			if (!"2".equals(cityCardType)) {
  				cMap.put("cardIsnormal", cardIsnormal);
  			}
  			
  			if ("init".equals(opt))
  			{
  				List<Org> list = perInfoQueManager.findByHQL("from Org o where o.orgId = 1381 and o.orgId <> '2' order by o.orgId asc");
  				if (list != null && list.size() > 0) {
  					orgId = list.get(0).getOrgId().toString();
  				}

  				cMap.put("orgId", orgId);
  			}
  			else
  			{
  				cMap.put("orgId", orgId);
  			}
  			
  			cMap.put("recu", "on");
  			cMap.put("telNo", tel);
  			
  			this.page = perInfoQueManager.queryPerInfo(page, cMap);					
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  		getRequest().setAttribute("org",(Org)perInfoQueManager.findById(Org.class, Long.parseLong(orgId)));
  		getRequest().setAttribute("loginId",Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
  		return SUCCESS;
	}
     
     /**
      * 新城一期
      * @return
      */
     public String perInfoCheckinMetro() {
       	String paQuerNo = this.getRequest().getParameter("paQuerNo");
       	String opt = this.getRequest().getParameter("opt");
       	if (!StringUtil.isEmpty(paQuerNo))
   		{
       		page.setPageNo(1);
   		}
       	try {
       		HashMap cMap = new HashMap();
   			cMap.put("displayName", displayName);		
   			cMap.put("cityCardType", cityCardType);	
   			cMap.put("phone", phone);
  			cMap.put("plateType", plateType);
   			if (!"2".equals(cityCardType)) {
   				cMap.put("cardIsnormal", cardIsnormal);
   			}
   			
   			if ("init".equals(opt))
   			{
   				List<Org> list = perInfoQueManager.findByHQL("from Org o where o.orgId = 1070 and o.orgId <> '2' order by o.orgId asc");
   				if (list != null && list.size() > 0) {
   					orgId = list.get(0).getOrgId().toString();
   				}

   				cMap.put("orgId", orgId);
   			}
   			else
   			{
   				cMap.put("orgId", orgId);
   			}
   			
   			cMap.put("recu", "on");
   			cMap.put("telNo", tel);
   			
   			this.page = perInfoQueManager.queryPerInfo(page, cMap);					
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
   		getRequest().setAttribute("org",(Org)perInfoQueManager.findById(Org.class, Long.parseLong(orgId)));
   		getRequest().setAttribute("loginId",Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
   		return SUCCESS;
 	}
     
     /**
      * 政务服务中心
      * @return
      */
     public String perInfoCheckinAdminService() {
        	String paQuerNo = this.getRequest().getParameter("paQuerNo");
        	String opt = this.getRequest().getParameter("opt");
        	if (!StringUtil.isEmpty(paQuerNo))
    		{
        		page.setPageNo(1);
    		}
        	try {
        		HashMap cMap = new HashMap();
    			cMap.put("displayName", displayName);		
    			cMap.put("cityCardType", cityCardType);	
    			cMap.put("phone", phone);
   			cMap.put("plateType", plateType);
    			if (!"2".equals(cityCardType)) {
    				cMap.put("cardIsnormal", cardIsnormal);
    			}
    			
    			if ("init".equals(opt))
    			{
    				List<Org> list = perInfoQueManager.findByHQL("from Org o where o.orgId = 1069 and o.orgId <> '2' order by o.orgId asc");
    				if (list != null && list.size() > 0) {
    					orgId = list.get(0).getOrgId().toString();
    				}

    				cMap.put("orgId", orgId);
    			}
    			else
    			{
    				cMap.put("orgId", orgId);
    			}
    			
    			cMap.put("recu", "on");
    			cMap.put("telNo", tel);
    			
    			this.page = perInfoQueManager.queryPerInfo(page, cMap);					
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		getRequest().setAttribute("org",(Org)perInfoQueManager.findById(Org.class, Long.parseLong(orgId)));
    		getRequest().setAttribute("loginId",Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
    		return SUCCESS;
  	}

	public PerInfoQueManager getPerInfoQueManager() {
		return perInfoQueManager;
	}

	public void setPerInfoQueManager(PerInfoQueManager perInfoQueManager) {
		this.perInfoQueManager = perInfoQueManager;
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

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getResidentNo() {
		return residentNo;
	}

	public void setResidentNo(String residentNo) {
		this.residentNo = residentNo;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getRecu() {
		return recu;
	}

	public void setRecu(String recu) {
		this.recu = recu;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCityCard() {
		return cityCard;
	}

	public void setCityCard(String cityCard) {
		this.cityCard = cityCard;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	/** 
	 * roomInfo 
	 * 
	 * @return the roomInfo 
	 * @since   CodingExample Ver(编码范例查看) 1.0 
	 */
	
	public String getRoomInfo()
	{
		return roomInfo;
	}

	/** 
	 * @param 房间号
	 */
	
	public void setRoomInfo(String roomInfo)
	{
		this.roomInfo = roomInfo;
	}

	/** 
	 * tel 
	 * 
	 * @return the tel 
	 * @since   CodingExample Ver(编码范例查看) 1.0 
	 */
	
	public String getTel()
	{
		return tel;
	}

	/** 
	 * @param tel the tel to set 
	 */
	
	public void setTel(String tel)
	{
		this.tel = tel;
	}

	/** 
	 * cityCardType 
	 * 
	 * @return the cityCardType 
	 * @since   CodingExample Ver(编码范例查看) 1.0 
	 */
	
	public String getCityCardType()
	{
		return cityCardType;
	}

	/** 
	 * @param cityCardType the cityCardType to set 
	 */
	
	public void setCityCardType(String cityCardType)
	{
		this.cityCardType = cityCardType;
	}

	/** 
	 * plateType 
	 * 
	 * @return the plateType 
	 * @since   CodingExample Ver(编码范例查看) 1.0 
	 */
	
	public String getPlateType()
	{
		return plateType;
	}

	/** 
	 * @param plateType the plateType to set 
	 */
	
	public void setPlateType(String plateType)
	{
		this.plateType = plateType;
	}

	/** 
	 * personType 
	 * 
	 * @return the personType 
	 * @since   CodingExample Ver(编码范例查看) 1.0 
	 */
	
	public String getPersonType()
	{
		return personType;
	}

	/** 
	 * @param personType the personType to set 
	 */
	
	public void setPersonType(String personType)
	{
		this.personType = personType;
	}
	
	
	
	
}
