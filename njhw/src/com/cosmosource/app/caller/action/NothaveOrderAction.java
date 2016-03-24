package com.cosmosource.app.caller.action;

import java.util.List;

import com.cosmosource.app.caller.model.CallerModel;
import com.cosmosource.app.caller.service.NothaveOrderManager;
import com.cosmosource.app.caller.service.VisitorqueryManager;
import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.entity.VmVisit;
import com.cosmosource.app.entity.VmVisitAuxi;
import com.cosmosource.app.entity.VmVisitorinfo;
import com.cosmosource.app.port.serviceimpl.PersonCardQueryToAppService;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
/**
 * 没有预约的访客前台登记类
* @description: TODO
* @author gxh
* @date 2013-4-2 下午11:15:47
 */
@SuppressWarnings({ "unchecked", "serial" })
public class NothaveOrderAction  extends BaseAction{
 
	private Long viId;//访客基本信息id
	private Long vsId; //访问事务id
	private String fatherCardId; //传来的临时卡号

	private NjhwTscard njhwTscard = new NjhwTscard();
	private VmVisit vmVisit = new VmVisit();
	private VmVisitorinfo vmVisiorinfo = new VmVisitorinfo();
	private CallerModel callerModel= new CallerModel();
	private Page<VmVisit> page = new Page<VmVisit>(Constants.PAGESIZE);//默认每页20条记录
	private NothaveOrderManager nothaveOrderManager;
	private PersonCardQueryToAppService personCardQueryToAppService;//接口
	private VisitorqueryManager visitorqueryManager;
	  private String residentNonum; //多个身份证
	    private String cardIdnum; //多个卡号
	    private String userNamenum;
	    private String message=null;
	
	public VisitorqueryManager getVisitorqueryManager() {
		return visitorqueryManager;
	}
	public void setVisitorqueryManager(VisitorqueryManager visitorqueryManager) {
		this.visitorqueryManager = visitorqueryManager;
	}
	public PersonCardQueryToAppService getPersonCardQueryToAppService() {
		return personCardQueryToAppService;
	}
	public void setPersonCardQueryToAppService(
			PersonCardQueryToAppService personCardQueryToAppService) {
		this.personCardQueryToAppService = personCardQueryToAppService;
	}
	@Override
	public CallerModel getModel() {
		return callerModel;
	}
	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		if(vsId!=null){
			//this.vmVisit = (VmVisit)this.callerManager.findById(VmVisit.class, vsId);
		}else{
			this.callerModel = new CallerModel();
		}
	}
	
	/**
	 * 保存访客信息
	* @title: save 
	* @description: TODO
	* @author gxh
	* @return
	* @throws Exception
	* @date 2013-4-2 下午11:17:59     
	* @throws
	 */
	public String save() throws Exception{
		
		try {
		
			//通过身份证号判断访客基本信息是否存在
			
			VmVisitorinfo vmInfo = this.nothaveOrderManager.findVmVisitorinfoByCardId(this.callerModel.getCardId());
			//设置访客基本信息表
			if(vmInfo ==null){
				//this.vmVisiorinfo.setResBak1(this.cardId);//设置市民卡号
				this.vmVisiorinfo.setResidentNo(this.callerModel.getCardId()); //身份证号
   
				this.vmVisiorinfo.setViName(this.callerModel.getUserName());//访客名字
				this.vmVisiorinfo.setViMobile(this.callerModel.getPhone());//电话
				this.vmVisiorinfo.setPlateNum(this.callerModel.getCarId());//车牌
				this.vmVisiorinfo.setViType("02"); //访客类型(01 政府单位;02 公众个人;03 企业客户;04 服务业人员;)
				this.nothaveOrderManager.save(this.vmVisiorinfo);
				
				this.vmVisit.setViId(this.vmVisiorinfo.getViId());//设置关联外键 事物表
			//	this.njhwTscard.setUserId(this.vmVisiorinfo.getViId());//设置关联外键 实名卡表
			}else{
				this.vmVisit.setViId(vmInfo.getViId());//设置关联外键
				//this.njhwTscard.setUserId(vmInfo.getViId());//设置关联外键
			}
			//设置访问事务表     
			this.vmVisit.setVsYn("1");  //是否预约1:已预约 0未预约
			this.vmVisit.setVsType("3");//预约类型(1 主动预约；2 被动预约；3；前台预约)
			this.vmVisit.setVsFlag(VmVisit.VS_FLAG_SURE);//事务状态
			//this.vmVisit.setOrgName(this.callerModel.getOrgName());//设置被访者单位名称;
			this.vmVisit.setOrgId(Long.parseLong(this.callerModel.getOrgId()));//单位id
			this.vmVisit.setOrgName(orgNameByid(this.callerModel.getOrgId()));
			this.vmVisit.setViName(this.callerModel.getUserName());//访客名字
			//this.vmVisit.setUserName(this.callerModel.getOutName());//被访者名字
			this.vmVisit.setUserId(Long.parseLong(this.callerModel.getOutId()));//被访者名字id
			this.vmVisit.setUserName(userNameByid(this.callerModel.getOutId()));
			this.vmVisit.setVsNum(this.callerModel.getComeNum());//来访人数
			this.vmVisit.setVsInfo(this.callerModel.getComeWhy()); //访问事由
			this.vmVisit.setVsSt(DateUtil.StringToDate(this.callerModel.getBeginTime()));//开始时间
			this.vmVisit.setVsEt(DateUtil.StringToDate(this.callerModel.getEndTime()));//结束时间
			this.vmVisit.setCardId(this.callerModel.getCardId());//设置卡号
		//	this.vmVisit.setResBak1(this.callerModel.getOrderType());//0市民卡、2身份证

			this.nothaveOrderManager.save(this.vmVisit); // 保存访问事务
		
		if(fatherCardId!=null&&fatherCardId.trim().length()>0)	{
			//实现发主临时卡根据访客事物id
			VmVisit v = new VmVisit();
			v = visitorqueryManager.getVisitByid(this.vmVisit.getVsId());
			v.setCardId(fatherCardId);
			v.setCardType(VmVisit.VS_CARTYPE_TEMCARD);//类型为临时卡
		//	v.setVsFlag(VmVisit.VS_FLAG_COME);
			v.setVsReturn(VmVisit.VS_RETURN_RNO);//未归还
			visitorqueryManager.saveEntity(v);
			if(v.getCardId()!=null&&!residentNonum.equals("")&&!cardIdnum.equals("")&&!userNamenum.equals("")){
				int sunum =  saveSoncard(this.vmVisit.getVsId(),v);
			  if(sunum>0){
				  setIsSuc("true"); 
			  }	else{
					  setIsSuc("false");	  
			  }
			}	
			  setIsSuc("true"); 
		}else{
			setIsSuc("false");
		}
		
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	public String getFatherCardId() {
		return fatherCardId;
	}
	public void setFatherCardId(String fatherCardId) {
		this.fatherCardId = fatherCardId;
	}
	/**
	*保存副卡
	* @title: saveSoncard 
	* @description: TODO
	* @author gxh
	* @param auxivsId
	* @param v
	* @return
	* @date 2013-4-6 下午03:47:23     
	* @throws
	 */
	private int saveSoncard(Long auxivsId,VmVisit v){
		int snum = 1;
		try{
			String[] array;
			String[] arrayb;
			String[] arrayc;
	
			array=residentNonum.split(",");
			arrayb=cardIdnum.split(",");
			arrayc=userNamenum.split(",");
			
		
		
			for (int i= 0;i<array.length ;i++){
				VmVisitAuxi vmVisitAuxi1 =new VmVisitAuxi();
				vmVisitAuxi1.setVsId(auxivsId);
				vmVisitAuxi1.setVaBak1(arrayc[i]);
				vmVisitAuxi1.setResidentNo(array[i]);
				vmVisitAuxi1.setCardId(arrayb[i]);
				vmVisitAuxi1.setCardType("2");	
				vmVisitAuxi1.setVaReturn(VmVisitAuxi.VA_RETURN_NO);
				visitorqueryManager.saveSoncard(vmVisitAuxi1);
			}
			v.setVsretSub(VmVisit.VS_RET_SUB_NO);//是否还副卡 未还
			v.setVsNum(v.getVsNum());
			visitorqueryManager.saveEntity(v);
			return snum;
		
		}catch(Exception e){
			e.printStackTrace();
			return snum = 0;
		}
		
	
	}
   /* *//**
     * 刷新副卡列表
    * @title: deleteVmVisitAuxi 
    * @description: TODO
    * @author gxh
    * @return
    * @date 2013-4-7 下午02:08:19     
    * @throws
     *//*
	public String flushVmVisitAuxi(){
		//JSONObject json = new JSONObject();
		String fid=Struts2Util.getParameter("fid");
		String vsids= Struts2Util.getParameter("vsids");   
	  
		try{
			 
			 int num =visitorqueryManager.deleteVmVisitAuxi(Long.parseLong(fid));
			if(num>0){
				  List<VmVisitAuxi> vvaList = visitorqueryManager.selectNum();
				  if(vvaList.size()>0){
				  Struts2Util.renderJson(vvaList, "encoding:UTF-8", "no-cache:true");	  
			  }
			}
		}catch(Exception e){
			e.printStackTrace();
			
		} 
		
		return null;
	}
	
	*/
	
	
	/** 
	* @title: loadVisitorInfo
	* @description: 根据身份证号加载访客信息
	* @author gxh
	* ${tags}
	* @date 2013-03-19 
	*/ 
	public String loadVisiInfo() {
		JSONObject json = new JSONObject(); 
		String residentId = Struts2Util.getParameter("residentId");
		vmVisiorinfo = this.nothaveOrderManager.findVmVisitorinfoByCardId(residentId);
		try {
			if (vmVisiorinfo != null) {
				json.put("VIName", vmVisiorinfo.getViName());//姓名		
				json.put("PlateNum", vmVisiorinfo.getPlateNum());//车牌
				json.put("ViMobile", vmVisiorinfo.getViMobile());//手机			
				json.put("status", 0);
			} else {
				json.put("status", 1);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	
	
	
	/**
	 * 
	* @title: getUsersByOrgId 
	* @description: 根据单位得到用户
	* @author gxh
	* @return
	* @date 2013-3-28 下午05:40:08     
	* @throws
	 */
	public String getUsersByOrgId(){
		JSONObject json = new JSONObject();
		List list = nothaveOrderManager.getUsersByOrgId(getParameter("orgId"));
		try {
			if (list != null &&  list.size() > 0) {
				json.put("list", list);
				json.put("status", 0);
			} else {
				json.put("status", 1);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	/**
	 * 验证市民卡
	* @title: exsitVmVisit 
	* @description: TODO
	* @author gxh
	* @return
	* @date 2013-4-3 下午11:49:17     
	* @throws
	 */
	public String exsitVmVisit() {

			NjhwTscard card = null; 
			String cId = Struts2Util.getParameter("cardId");
			try {
				card = personCardQueryToAppService.queryPersonCard(cId);
				if(card!=null) 
				{
					Struts2Util.renderText("true");
				}else{
					Struts2Util.renderText("false");
				}	
			} catch (Exception e) {
				e.printStackTrace();
				Struts2Util.renderText("false");
			}		
		return null;
	}
	
  /**
   * 访客前台预约页面
  * @title: initOrd 
  * @description: TODO
  * @author gxh
  * @return
  * @throws Exception
  * @date 2013-4-2 下午11:30:18     
  * @throws
   */
	public String initOrd() throws Exception {
		System.out.print(message);
		if(message!=null){
			
			this.getRequest().setAttribute("meg", "保存成功！");
			
		}
		this.getRequest().setAttribute("orgs", this.nothaveOrderManager.getAllOrgs());		
		return INIT;
	}
	
/**
 * 根据id查找组织名称
* @title: orgNameByid 
* @description: TODO
* @author gxh
* @param id
* @return
* @date 2013-4-15 上午11:10:51     
* @throws
 */
	private String orgNameByid(String orgid){
		
		return nothaveOrderManager.orgName(Long.parseLong(orgid));
	
		
	}
	/**
	 * 根据id找到姓名
	* @title: userNameByid 
	* @description: TODO
	* @author gxh
	* @param id
	* @return
	* @date 2013-4-15 上午11:15:28     
	* @throws
	 */
	private String userNameByid(String userid){
		
		return nothaveOrderManager.userName(Long.parseLong(userid));
	}
	
	
	public Long getViId() {
		return viId;
	}
	public void setViId(Long viId) {
		this.viId = viId;
	}
	public Long getVsId() {
		return vsId;
	}
	public void setVsId(Long vsId) {
		this.vsId = vsId;
	}
/*	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}*/
	public NjhwTscard getNjhwTscard() {
		return njhwTscard;
	}
	public void setNjhwTscard(NjhwTscard njhwTscard) {
		this.njhwTscard = njhwTscard;
	}
	public VmVisit getVmVisit() {
		return vmVisit;
	}
	public void setVmVisit(VmVisit vmVisit) {
		this.vmVisit = vmVisit;
	}
	public VmVisitorinfo getVmVisiorinfo() {
		return vmVisiorinfo;
	}
	public void setVmVisiorinfo(VmVisitorinfo vmVisiorinfo) {
		this.vmVisiorinfo = vmVisiorinfo;
	}
	public CallerModel getCallerModel() {
		return callerModel;
	}
	public void setCallerModel(CallerModel callerModel) {
		this.callerModel = callerModel;
	}
	public Page<VmVisit> getPage() {
		return page;
	}
	public void setPage(Page<VmVisit> page) {
		this.page = page;
	}
	public NothaveOrderManager getNothaveOrderManager() {
		return nothaveOrderManager;
	}
	public void setNothaveOrderManager(NothaveOrderManager nothaveOrderManager) {
		this.nothaveOrderManager = nothaveOrderManager;
	}
	public String getResidentNonum() {
		return residentNonum;
	}
	public void setResidentNonum(String residentNonum) {
		this.residentNonum = residentNonum;
	}
	public String getCardIdnum() {
		return cardIdnum;
	}
	public void setCardIdnum(String cardIdnum) {
		this.cardIdnum = cardIdnum;
	}
	public String getUserNamenum() {
		return userNamenum;
	}
	public void setUserNamenum(String userNamenum) {
		this.userNamenum = userNamenum;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	
}
