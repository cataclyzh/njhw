package com.cosmosource.app.caller.action;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.cosmosource.app.caller.model.CallerModel;
import com.cosmosource.app.caller.model.VMCountModel;
import com.cosmosource.app.caller.service.FrontRegManager;
import com.cosmosource.app.caller.service.NoOrderManager;
import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.VmVisit;
import com.cosmosource.app.entity.VmVisitAuxi;
import com.cosmosource.app.entity.VmVisitorinfo;
import com.cosmosource.app.port.serviceimpl.PersonCardQueryToAppService;
import com.cosmosource.app.utils.DateUtils;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.service.AuthorityManager;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
/**
 * 没有预约的访客前台登记类
* @description: TODO
* @author gxh
* @date 2013-4-2 下午11:15:47
 */
@SuppressWarnings({ "unchecked", "serial" })
public class NoOrderAction  extends BaseAction{
	private FrontRegManager frontRegManager;
	private NoOrderManager noOrderManager;
	private Long viId;//访客基本信息id
	private Long vsId; //访问事务id
	private String fatherCardId; //传来的临时卡号

	private NjhwTscard njhwTscard = new NjhwTscard();
	private VmVisit vmVisit = new VmVisit();
	private VmVisitorinfo vmVisiorinfo = new VmVisitorinfo();
	private CallerModel callerModel= new CallerModel();
	private Page<VmVisit> page = new Page<VmVisit>(Constants.PAGESIZE);//默认每页20条记录
	
	private PersonCardQueryToAppService personCardQueryToAppService;//接口
	
	private String residentNonum; //多个身份证
	private String cardIdnum; //多个卡号
	private String userNamenum;
	private String message=null;
	    
	//区分不同种类的物业首页
	private String type;
	    
    public String getType() {
			return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	private AuthorityManager authorityManager;
    	    	
	public AuthorityManager getAuthorityManager() {
		return authorityManager;
	}
	public void setAuthorityManager(AuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
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
	
	private Date StringToDate(String s) {
		Date time = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			time = sd.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	/**
	 * 
	 * @title: findDailyVmList
	 * @description: 方法实现查询当天的所有访客信息，为访客统计用
	 * @author herb
	 * @return
	 * @date Apr 22, 2013 2:20:14 PM
	 * @throws
	 */
	public VMCountModel findDailyVmList() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 完整的时间
		String vsSt = sdf.format(date) + " 00:00:00";
		String vsEt = sdf.format(date) + " 23:59:59";
		Map<String, Object> attMap = new HashMap<String, Object>();
		int vnum = 0, unum = 0, vzx = 0, vlk = 0, uzx = 0, ulk = 0, vc = 0;

		String visitStart = null; // 访问开始时间
		String visitEnd = null; // 结束时间
		String visitDelay = null; // 真实离开时间
		VMCountModel vcount = new VMCountModel();
		BigDecimal outsider = null;
		BigDecimal insider = null;

		List<Map> vmList = frontRegManager.dailyVMCountInfo(vsSt, vsEt);
		if (vmList != null && vmList.size() > 0) {
			// System.out.print("+++++*******************************"+vmList.get(0));
			for (int i = 0; i < vmList.size(); i++) {
				attMap = vmList.get(i);
				String rn = (String) attMap.get("RESIDENT_NO");
				String flag = (String) attMap.get("VS_FLAG");

				if (attMap.get("VS_ST") != null) {
					visitStart = attMap.get("VS_ST").toString();
				}

				// 访问结束时间
				/*
				 * if (attMap.get("VS_ET") != null) {
				 * System.out.println("the result of query from database ------ "
				 * + attMap.get("VS_ET")); vset = new
				 * SimpleDateFormat().format(attMap.get("VS_ET"));
				 * System.out.println("Hello Man !"); } else { vset = (String)
				 * attMap.get("VS_ET"); }
				 */

				if (attMap.get("VS_ET") != null) {
					visitEnd = attMap.get("VS_ET").toString();
				}

				if (attMap.get("VS_ET1") != null) {
					visitDelay = attMap.get("VS_ET1").toString();
				}

				// 真实离开时间
				/*
				 * if (attMap.get("VS_ST1_LEA") != null) {
				 * System.out.println("the result of query from database ------ "
				 * + attMap.get("VS_ST1_LEA")); lev = new
				 * SimpleDateFormat().format(attMap .get("VS_ST1_LEA"));
				 * System.out.println("Hello World !"); } else { lev = (String)
				 * attMap.get("VS_ST1_LEA"); }
				 */

				// 13-6-24 上午12:00
				// 根据身份证号码查询人员类型
				boolean res = frontRegManager.exsitNjhwExpByRnId1(rn);

				if (res) {

					// 判断内部在访人数和离开人数

					if (StringToDate(visitStart).getTime() < new Date()
							.getTime()
							&& new Date().getTime() < StringToDate(visitEnd)
									.getTime()) {
						uzx += 1;
						// vcount.setUserVisiting(uzx);
						vcount.setVmVisiting(uzx);

					}

					if (new Date().getTime() > StringToDate(visitEnd).getTime()) {

						ulk += 1;
						// vcount.setUserLeave(ulk);
						vcount.setVmLeave(ulk);

					}

					/*
					 * if (flag.equals(VmVisit.VS_FLAG_COME)) { uzx += 1;
					 * vcount.setUserVisiting(uzx); } if
					 * (flag.equals(VmVisit.VS_FLAG_FINISH) ||
					 * flag.equals(VmVisit.VS_FLAG_ERROR)) { ulk += 1;
					 * vcount.setUserLeave(ulk); }
					 */

					unum += 1;

				} else {

					
					 if (flag.equals(VmVisit.VS_FLAG_COME)) { vzx += 1;
					  vcount.setUserVisiting(vzx);
					  
					  }
					 
					
					  if (flag.equals(VmVisit.VS_FLAG_FINISH) ||
					  flag.equals(VmVisit.VS_FLAG_ERROR)) { vlk += 1;
					  vcount.setUserLeave(vlk); }
					 
					// 外部访问人员情况
//					if (StringToDate(visitStart).getTime() < new Date()
//							.getTime()
//							&& new Date().getTime() < StringToDate(visitEnd)
//									.getTime()) {
//						vzx += 1;
//						vcount.setUserVisiting(vzx);
//					}

//					if (new Date().getTime() > StringToDate(visitEnd).getTime()) {
//
//						vlk += 1;
//						// vcount.setVmLeave(vlk);
//						vcount.setUserLeave(vlk);
//
//					}
					vnum += 1;

				}
				// 超时访问人数
				if (visitDelay != null && !("").equals(visitDelay)
						&& visitEnd != null && !("").equals(visitEnd)) {
					// 比较预约结束时间跟真实离开时间
					Date delay = StringToDate(visitDelay);
					Date end = StringToDate(visitEnd);
					if (delay.getTime() > end.getTime()) {
						vc += 1;
						vcount.setExpiredCount(vc);

					} else {

						vcount.setExpiredCount(0);
					}

				}

			}

//			vcount.setVmCount(unum); // 内部人员访客总数
//			vcount.setUserCount(vmList.size() - unum); // 外部人员访客数目
			vcount.setUserCount(vnum);
			// 内外部访问人员统计数据
			// outsider = new DecimalFormat("#.00").format(vmList.size());
			// insider = new DecimalFormat("#.00").format(1/3);

			double total = vmList.size() * 1.00;
			double d1 = vcount.getVmCount() * 1.00; // 内部
			double d2 = vcount.getUserCount() * 1.00; // 外部

			outsider = new BigDecimal(d2 / total).setScale(2,
					BigDecimal.ROUND_HALF_UP);
			insider = new BigDecimal(d1 / total).setScale(2,
					BigDecimal.ROUND_HALF_UP);

			ServletActionContext.getRequest().setAttribute("insider", insider);
			ServletActionContext.getRequest()
					.setAttribute("outsider", outsider);
			ServletActionContext.getRequest().setAttribute("kong", 0);

		} else {
			ServletActionContext.getRequest().setAttribute("insider", 0);
			ServletActionContext.getRequest().setAttribute("outsider", 0);
			ServletActionContext.getRequest().setAttribute("kong", 100);
		}

		this.getRequest().setAttribute("vmList", vcount);
		return vcount;

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
			
			VmVisitorinfo vmInfo = this.noOrderManager.findVmVisitorinfoByCardId(this.callerModel.getCardId());
			//设置访客基本信息表
			if(vmInfo ==null){
				//this.vmVisiorinfo.setResBak1(this.cardId);//设置市民卡号
				this.vmVisiorinfo.setResidentNo(this.callerModel.getCardId()); //身份证号
   
				this.vmVisiorinfo.setViName(this.callerModel.getUserName());//访客名字
				this.vmVisiorinfo.setViMobile(this.callerModel.getPhone());//电话
				this.vmVisiorinfo.setPlateNum(this.callerModel.getCarId());//车牌
				this.vmVisiorinfo.setViType("02"); //访客类型(01 政府单位;02 公众个人;03 企业客户;04 服务业人员;)
				this.noOrderManager.save(this.vmVisiorinfo);
				
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

			this.noOrderManager.save(this.vmVisit); // 保存访问事务
		
		if(fatherCardId!=null&&fatherCardId.trim().length()>0)	{
			//实现发主临时卡根据访客事物id
			VmVisit v = new VmVisit();
			v = frontRegManager.getVisitByid(this.vmVisit.getVsId());
			v.setCardId(fatherCardId);
			v.setCardType(VmVisit.VS_CARTYPE_TEMCARD);//类型为临时卡
		//	v.setVsFlag(VmVisit.VS_FLAG_COME);
			v.setVsReturn(VmVisit.VS_RETURN_RNO);//未归还
			frontRegManager.saveEntity(v);
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
				frontRegManager.saveSoncard(vmVisitAuxi1);
			}
			v.setVsretSub(VmVisit.VS_RET_SUB_NO);//是否还副卡 未还
			v.setVsNum(v.getVsNum());
			frontRegManager.saveEntity(v);
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
			 
			 int num =frontRegManager.deleteVmVisitAuxi(Long.parseLong(fid));
			if(num>0){
				  List<VmVisitAuxi> vvaList = frontRegManager.selectNum();
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
		List list = noOrderManager.getUsersByOrgId(getParameter("orgId"));
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
		VMCountModel vcount= findDailyVmList();
		//System.out.print(message);
		//if(message!=null){
		//	
		//	this.getRequest().setAttribute("meg", "保存成功！");
			
		//}
		//首页 用户名与登录名
		String loginname = (String)this.getSession().getAttribute(Constants.LOGIN_NAME);
		String userName = (String)this.getSession().getAttribute(Constants.USER_NAME);
		String username = userName + " (" + loginname + ") "; 
		getRequest().setAttribute("username", username);
		getRequest().setAttribute("vmList", vcount);
		getRequest().setAttribute("startTime",DateUtils.getFirstDayTimeOfMonth());
		getRequest().setAttribute("endTime",DateUtils.getCurrentTime());
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
		
		return noOrderManager.orgName(Long.parseLong(orgid));
	
		
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
		
		return noOrderManager.userName(Long.parseLong(userid));
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
	public FrontRegManager getFrontRegManager() {
		return frontRegManager;
	}
	public void setFrontRegManager(FrontRegManager frontRegManager) {
		this.frontRegManager = frontRegManager;
	}
	public NoOrderManager getNoOrderManager() {
		return noOrderManager;
	}
	public void setNoOrderManager(NoOrderManager noOrderManager) {
		this.noOrderManager = noOrderManager;
	}

	
}
