package com.cosmosource.app.caller.action;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cosmosource.app.caller.service.NothaveOrderManager;
import com.cosmosource.app.caller.service.VisitorqueryManager;

import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.VmVisit;
import com.cosmosource.app.entity.VmVisitAuxi;
import com.cosmosource.app.entity.VmVisitorinfo;
import com.cosmosource.app.port.serviceimpl.DevicePermissionToAppService;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

/**
 * 发放临时卡
 * 
 * @description: TODO
 * @author gxh
 * @date 2013-4-2 下午11:37:38
 */
@SuppressWarnings("all")
public class VisitorqueryAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2258271560170740513L;
	private VisitorqueryManager visitorqueryManager;
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页20条记录
	private DevicePermissionToAppService devicePermissionToAppService;
	private NothaveOrderManager nothaveOrderManager;
	private String residentNo;// 身份证；
	private String vsSt;// 访问日期
	private String vsEt;// 结束时间
	private String viName;// 访客姓名

	private String vsFlag;
	private VmVisitorinfo vmVisitorinfo;// 访客基本信息对象
	private VmVisit vmvisit;// 访客事物表
	private String cardId;// 临时卡好
	private String cardType;// 类型
	private String vsId;// 访客基本信息

	private String excardId; // 退卡id
	private Short vsNums;// 访问人数
   
	private String cardNum;//退卡传过来的卡号
	private String vsType;
   private String blackResidentNo;//加入黑名单的身份证
   private String blackReson; //原因
   private String  userid;
	/**
	 * 进入页面
	 * 
	 * @title: visitorListIn
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-4-3 下午06:01:48
	 * @throws
	 */
	public String visitorListIn() {
		return SUCCESS;
	}

	/**
	 * 条件查询申请成功访客
	 * 
	 * @title: visitorList
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @throws Exception
	 * @date 2013-4-2 下午11:38:37
	 * @throws
	 */

	public String visitorList() throws Exception {
		try {
			this.page = visitorqueryManager.queryVisitor(page, viName,
					residentNo, vsSt, vsEt,"02");
		} catch (Exception e) {
			e.printStackTrace();

		}
		return SUCCESS;
	}

	/**
	 * 条件查询到访访客
	 * 
	 * @title: visitorList
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @throws Exception
	 * @date 2013-4-2 下午11:38:37
	 * @throws
	 */

	public String visitorList1() throws Exception {
		try {
			this.page = visitorqueryManager.queryVisitor(page, viName,
					residentNo, vsSt, vsEt,"04");
		} catch (Exception e) {
			e.printStackTrace();

		}
		return SUCCESS;
	}
	
	/**
	 * 读取访客基本信息
	 * 
	 * @title: getvisifoByid
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-4-2 下午11:38:47
	 * @throws
	 */

	public String getvisifoByid() {

		try {

			Map map = visitorqueryManager.findVisitorsinfo(vsId);
			this.getRequest().setAttribute("map", map);

			int num = visitorqueryManager.selectNum(Long.parseLong(vsId))
					.size();
			if (num != 0) {
				this.getRequest().setAttribute("num", num);
			} else {
				this.getRequest().setAttribute("num", 0);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 查看发卡信息
	 * 
	 * @title: getAllInfor
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-4-2 下午11:38:59
	 * @throws
	 */
	public String getAllInfor() {

		int num = 0;
		try {

			Map map = visitorqueryManager.findVisitorsinfo(vsId);
			this.getRequest().setAttribute("map", map);

			num = visitorqueryManager.selectNum(Long.parseLong(vsId)).size();
			if (num != 0) {
				this.getRequest().setAttribute("num", num);
			} else {
				this.getRequest().setAttribute("num", 0);
			}

			List<VmVisitAuxi> vvaList = visitorqueryManager.selectNum(Long
					.parseLong(vsId));

			this.getRequest().setAttribute("vvaList", vvaList);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return SUCCESS;

	}
	
	/**
	 * 
	 * 查找未退的副卡
	* @title: getAllInfor1 
	* @description: TODO
	* @author gxh
	* @return
	* @date 2013-4-9 下午10:49:03     
	* @throws
	 */
	public String getAllInfor1() {

	
		try {

			Map map = visitorqueryManager.findVisitorsinfo(vsId);
			this.getRequest().setAttribute("map", map);

			

			List<VmVisitAuxi> vvaList = visitorqueryManager.getUnBackVVACard(Long
					.parseLong(vsId));

			this.getRequest().setAttribute("vvaList", vvaList);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return SUCCESS;
	}

	/**
	 * 实现发放临时卡
	 * 
	 * @title: getVisitByid
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-4-2 下午11:39:29
	 * @throws
	 */

	public String getVisitByid() {
		try {
			vmvisit = visitorqueryManager.getVisitByid(Long.parseLong(vsId));
			vmvisit.setCardId(cardId);
			vmvisit.setCardType(VmVisit.VS_CARTYPE_TEMCARD);//卡类型临时卡
		//	vmvisit.setVsFlag(VmVisit.VS_FLAG_COME);
			vmvisit.setVsReturn(VmVisit.VS_RETURN_RNO);//状态未还2
			visitorqueryManager.saveEntity(vmvisit);
			
           this.getRequest().setAttribute("vsids", vmvisit.getVsId());
           setIsSuc("true");
		} catch (Exception e) {
			e.printStackTrace();
			setIsSuc("false");

		}
		return SUCCESS;
	}

	/**
	 * 绑定副卡页面
	 * 
	 * @return
	 */
	/*
	 * public String boundSonCard(){
	 * 
	 * 
	 * return SUCCESS; }
	 */
	/**
	 * 保存副卡
	 */
	public String saveSoncard() {
		
		JSONObject json = new JSONObject();
		String auxivsId = Struts2Util.getParameter("vsid");
		try {
			String residentNonum = Struts2Util.getParameter("residentNonum");
			String cardIdnum = Struts2Util.getParameter("cardIdnum");
			String userNamenum = Struts2Util.getParameter("userNamenum");

			String[] array;
			String[] arrayb;
			String[] arrayc;

			array = residentNonum.split(",");
			arrayb = cardIdnum.split(",");
			arrayc = userNamenum.split(",");

			vmvisit = visitorqueryManager
					.getVisitByid(Long.parseLong(auxivsId));
			for (int i = 0; i < array.length; i++) {
				VmVisitAuxi vmVisitAuxi1 = new VmVisitAuxi();
				vmVisitAuxi1.setVsId(Long.parseLong(auxivsId));
				vmVisitAuxi1.setVaBak1(arrayc[i]);
				vmVisitAuxi1.setResidentNo(array[i]);
				vmVisitAuxi1.setCardId(arrayb[i]);
				vmVisitAuxi1.setCardType("2");
				vmVisitAuxi1.setVaReturn(VmVisitAuxi.VA_RETURN_NO);
				visitorqueryManager.saveSoncard(vmVisitAuxi1);
			}
			vmvisit.setVsretSub(VmVisit.VS_RET_SUB_NO);//
			//vmvisit.setVsNum(vsNums);
			visitorqueryManager.saveEntity(vmvisit);

			// 刷新副卡列表
			List<VmVisitAuxi> vvaList = visitorqueryManager.selectNum(Long
					.parseLong(auxivsId));
			if (vvaList.size() > 0) {
				Struts2Util.renderJson(vvaList, "encoding:UTF-8",
						"no-cache:true");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;

	}

	/**
	 * 进入退卡页面
	 * 
	 * @title: inputExitCard
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-4-2 下午11:40:22
	 * @throws
	 */
	public String inputExitCard() {
		return SUCCESS;
	}

	/**
	 * 归还临时卡
	 * 退卡
	 * @title: exitCard
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-4-2 下午11:40:53
	 * @throws
	 */
	public String exitCard() {
		String[] array;
		array = cardNum.split(",");

		try {
			if(blackReson!=null&&!blackReson.equals("")){
				addblack();		
			}
			
			for (int i = 0; i < array.length; i++) {
				VmVisitAuxi f = visitorqueryManager.exitCardFByid(array[i]);//副卡

				if (f != null) {
					if (!f.getVaReturn().equals(VmVisitAuxi.VA_RETURN_YES)) {//如果副卡未归还	
						f.setVaReturn(VmVisitAuxi.VA_RETURN_YES);//归还
						List<VmVisitAuxi> list = visitorqueryManager
								.getUnBackVVACard(f.getVsId());//是否还有副卡未还
						if (list.size() < 2) {
							visitorqueryManager.updVmVisitStatus(f.getVsId());//修改主卡状态副卡还完
							
						}
					//	revokePermission(array[i]);//取消权限
						visitorqueryManager.saveSoncard(f);
					
						setIsSuc("true");
					} else {
						this.getRequest().setAttribute("message", "此卡已退");
					}

				} else {
					vmvisit = visitorqueryManager.exitCardByid(array[i]);
					if (vmvisit != null) {
						if (!vmvisit.getVsReturn().equals(VmVisit.VS_RETURN_RYES)) {// 如果临时卡未归还				
							vmvisit.setVsReturn(VmVisit.VS_RETURN_RYES);//归还				
							if(vmvisit.getVsretSub().equals(VmVisit.VS_RET_SUB_YES)){//副卡是否都归还
						
								vmvisit.setVsFlag(VmVisit.VS_FLAG_FINISH);				//事物结束	
							}
						//	revokePermission(array[i]);//取消权限
							visitorqueryManager.saveEntity(vmvisit);//保存
							setIsSuc("true");
                     
						} else if (vmvisit.getVsReturn().equals(VmVisit.VS_RETURN_RYES)) {

							this.getRequest().setAttribute("message", "此卡已退");
						} else {
							this.getRequest().setAttribute("message", "卡号错误！");
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			setIsSuc("false");

		}
		return SUCCESS;

	}
	/**
	 * 取消权限
	* @title: revokePermission 
	* @description: TODO
	* @author gxh
	* @param recarid
	* @return
	* @date 2013-4-12 下午12:04:43     
	* @throws
	 */
	private void revokePermission(String recarid){
		try{
		NjhwUsersExp exp = new NjhwUsersExp(); 
		System.out.print("***************************"+userid);
     exp= visitorqueryManager.findUsersExpByUserid(Long.parseLong(userid));
     if(exp!=null){
    	 if(exp.getRoomId()!=null){
    		 devicePermissionToAppService.cancelAuthDeviceOther(recarid,exp.getRoomId().toString());//取消权限 
    		
    	 }
    	  
     }
		}catch(Exception e){
			e.printStackTrace();
			
		}
			
	}
	
	

	/**
	 * 删除副卡
	 * 
	 * @title: deleteVmVisitAuxi
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-4-6 下午03:42:20
	 * @throws
	 */
	public String deleteVmVisitAuxi() {
		// JSONObject json = new JSONObject();
		String fid = Struts2Util.getParameter("fid");
		String vsids = Struts2Util.getParameter("vsids");

		try {
			if (fid != null) {
				int num = visitorqueryManager.deleteVmVisitAuxi(Long
						.parseLong(fid));
			}
			List<VmVisitAuxi> vvaList = visitorqueryManager.selectNum(Long
					.parseLong(vsids));
			if (vvaList.size() > 0) {
				Struts2Util.renderJson(vvaList, "encoding:UTF-8",
						"no-cache:true");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;
	}

	/**
	 * 刷新副卡信息
	 * 
	 * @title: flshVmVisitAuxi
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-4-7 下午02:27:58
	 * @throws
	 */
	public String flshVmVisitAuxi() {

		String vsids = Struts2Util.getParameter("vsid");

		try {

			List<VmVisitAuxi> vvaList = visitorqueryManager.selectNum(Long
					.parseLong(vsids));
			if (vvaList.size() > 0) {
				Struts2Util.renderJson(vvaList, "encoding:UTF-8",
						"no-cache:true");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;
	}
    /**
     * 加入黑名单
    * @title: addblack 
    * @description: TODO
    * @author gxh
    * @return
    * @date 2013-4-9 下午03:00:12     
    * @throws
     */
	private void addblack()
	{
		VmVisitorinfo vmInfo = nothaveOrderManager.findVmVisitorinfoByCardId(blackResidentNo);
		if(vmInfo!=null){
			vmInfo.setIsBlack(VmVisitorinfo.IS_BLACK_YES);
			vmInfo.setBlackReson(blackReson);
			nothaveOrderManager.save(vmInfo);
			
		}
		
	}
	
	
	
	
	public NothaveOrderManager getNothaveOrderManager() {
		return nothaveOrderManager;
	}

	public void setNothaveOrderManager(NothaveOrderManager nothaveOrderManager) {
		this.nothaveOrderManager = nothaveOrderManager;
	}

	@Override
	protected void prepareModel() throws Exception {

	}

	public VisitorqueryManager getVisitorqueryManager() {
		return visitorqueryManager;
	}

	public void setVisitorqueryManager(VisitorqueryManager visitorqueryManager) {
		this.visitorqueryManager = visitorqueryManager;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public String getResidentNo() {
		return residentNo;
	}

	public void setResidentNo(String residentNo) {
		this.residentNo = residentNo;
	}

	public String getViName() {
		return viName;
	}

	public void setViName(String viName) {
		this.viName = viName;
	}

	public String getVsSt() {
		return vsSt;
	}

	public void setVsSt(String vsSt) {
		this.vsSt = vsSt;
	}

	@Override
	public VmVisit getModel() {
		return null;
	}

	public VmVisitorinfo getVmVisitorinfo() {
		return vmVisitorinfo;
	}

	public void setVmVisitorinfo(VmVisitorinfo vmVisitorinfo) {
		this.vmVisitorinfo = vmVisitorinfo;
	}

	public VmVisit getVmvisit() {
		return vmvisit;
	}

	public void setVmvisit(VmVisit vmvisit) {
		this.vmvisit = vmvisit;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getVsId() {
		return vsId;
	}

	public void setVsId(String vsId) {
		this.vsId = vsId;
	}

	public String getExcardId() {
		return excardId;
	}

	public void setExcardId(String excardId) {
		this.excardId = excardId;
	}

	/*
	 * public String getResidentNonum() { return residentNonum; } public void
	 * setResidentNonum(String residentNonum) { this.residentNonum =
	 * residentNonum; }
	 */

	public String getVsFlag() {
		return vsFlag;
	}

	public void setVsFlag(String vsFlag) {
		this.vsFlag = vsFlag;
	}

	public String getVsEt() {
		return vsEt;
	}

	public void setVsEt(String vsEt) {
		this.vsEt = vsEt;
	}

	public String getVsType() {
		return vsType;
	}

	public void setVsType(String vsType) {
		this.vsType = vsType;
	}

	public Short getVsNums() {
		return vsNums;
	}

	public void setVsNums(Short vsNums) {
		this.vsNums = vsNums;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public DevicePermissionToAppService getDevicePermissionToAppService() {
		return devicePermissionToAppService;
	}

	public void setDevicePermissionToAppService(
			DevicePermissionToAppService devicePermissionToAppService) {
		this.devicePermissionToAppService = devicePermissionToAppService;
	}

	public String getBlackResidentNo() {
		return blackResidentNo;
	}

	public void setBlackResidentNo(String blackResidentNo) {
		this.blackResidentNo = blackResidentNo;
	}

	public String getBlackReson() {
		return blackReson;
	}

	public void setBlackReson(String blackReson) {
		this.blackReson = blackReson;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}
