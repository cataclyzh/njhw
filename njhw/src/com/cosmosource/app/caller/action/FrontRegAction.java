package com.cosmosource.app.caller.action;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.struts2.ServletActionContext;
import org.springframework.util.FileCopyUtils;

import com.cosmosource.app.caller.model.CallerModel;
import com.cosmosource.app.caller.model.VMCountModel;
import com.cosmosource.app.caller.service.FrontRegManager;
import com.cosmosource.app.caller.service.NoOrderManager;
import com.cosmosource.app.caller.service.VisitorqueryManager;
import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.entity.VmVisit;
import com.cosmosource.app.entity.VmVisitAuxi;
import com.cosmosource.app.entity.VmVisitorinfo;
import com.cosmosource.app.personnel.service.PersonnelExpInforManager;
import com.cosmosource.app.port.serviceimpl.DevicePermissionToAppService;
import com.cosmosource.app.port.serviceimpl.IPPhoneSiteToAppService;
import com.cosmosource.app.port.serviceimpl.PersonCardQueryToAppService;
import com.cosmosource.app.utils.PictureUtils;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.PropertiesUtil;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

/**
 * @description: 新前台登记
 * @author gxh
 * @date 2013-4-2 下午11:37:38
 */
@SuppressWarnings("all")
public class FrontRegAction extends BaseAction {

	private static String serviceUrl = "http://localhost:8080/quartz_njhw/services/accessAndGatesSiteService";
	// TODO
	private static String domainName = "http://tempuri.org/";

	/**
	 * 
	 */
	private static final long serialVersionUID = 2258271560170740513L;
	private FrontRegManager frontRegManager;
	private NoOrderManager noOrderManager;
	private Page<HashMap> page = new Page<HashMap>(10);// 默认每页20条记录
	private DevicePermissionToAppService devicePermissionToAppService;
	private PersonCardQueryToAppService personCardQueryToAppService;
	private PersonnelExpInforManager personnelExpInforManager;
	private VisitorqueryManager visitorqueryManager;
	private IPPhoneSiteToAppService iPPhoneSiteToAppService;
	private String fatherCardId; // 传来的临时卡号

	private NjhwTscard njhwTscard = new NjhwTscard();
	private VmVisit vmVisit = new VmVisit();
	private VmVisitorinfo vmVisiorinfo = new VmVisitorinfo();

	// 访客事务基本信息对象
	private CallerModel callerModel = new CallerModel();
	private String number;// 号码

	private String cardId;// 临时卡号
	// private String cardType;// 类型
	private String vsId;// 访客基本信息
	private String type;
	private String excardId; // 退卡id
	private Short vsNums;// 访问人数

	private String cardNum;// 退卡传过来的卡号

	private String vsType;
	private String blackResidentNo;// 加入黑名单的身份证
	private String blackReson; // 原因
	private String userid;

	private String cardTypenum = "";// 多个卡类型
	private String locatorIdNum = ""; // 多个定位卡号
	private String cardIdnum = ""; // 多个卡号
	private String residentNonum = ""; // 多个身份证号
	private String userNamenum = "";
	private String auxiIdNum = "";// 多个副卡Id;
	private String pawnNum = "";// 多个抵押物;

	private String typeId; // 判断保存还是约前登记1保存2发卡
	private String judge;// 判断内部人员
	private int num;
	private String curvsid;

	private String viName;
	
	private String vsFlag;
	
	private String vsSt;
	
	private String vsEt;
	
	
	
	
	public String getViName() {
		return viName;
	}

	public void setViName(String viName) {
		this.viName = viName;
	}

	public String getVsFlag() {
		return vsFlag;
	}

	public void setVsFlag(String vsFlag) {
		this.vsFlag = vsFlag;
	}

	public String getVsSt() {
		return vsSt;
	}

	public void setVsSt(String vsSt) {
		this.vsSt = vsSt;
	}



	public String getVsEt() {
		return vsEt;
	}

	public void setVsEt(String vsEt) {
		this.vsEt = vsEt;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getAuxiIdNum() {
		return auxiIdNum;
	}

	public void setAuxiIdNum(String auxiIdNum) {
		this.auxiIdNum = auxiIdNum;
	}

	public String getPawnNum() {
		return pawnNum;
	}

	public void setPawnNum(String pawnNum) {
		this.pawnNum = pawnNum;
	}

	public String getCurvsid() {
		return curvsid;
	}

	public void setCurvsid(String curvsid) {
		this.curvsid = curvsid;
	}

	/**
	 * 
	 * @Title: respondentsTreeSelect
	 * @Description: 机构树
	 * @author SQS
	 * @date 2013-5-6 上午11:11:11
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @throws
	 */
	public String respondentsTreeSelect() throws Exception {
		String state = getParameter("state");
		this.getRequest().setAttribute("state", state);
		return SUCCESS;

	}

	public String respondentsTreeSelectForIframe() throws Exception {
		String state = getParameter("state");
		this.getRequest().setAttribute("state", state);
		return SUCCESS;

	}

	/**
	 * 
	 * @Title: selectRespondents
	 * @Description: 机构 受访人
	 * @author SQS
	 * @date 2013-5-6 上午11:11:11
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @throws
	 */
	public String selectRespondentsForAdmin() throws Exception {
		try {
			String id = getParameter("id");
			String type = getParameter("type");
			Struts2Util.renderXml(frontRegManager.getSelectRespondents(id,
					getContextPath(), type), "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String selectRespondents() throws Exception {
		if ("all".equals(getParameter("state"))) {
			return selectRespondentsForAll();

		} else {
			return selectRespondentsForAdmin();
		}
	}

	public String selectRespondentsForAll() throws Exception {
		try {
			String id = getParameter("id");
			String type = getParameter("type");
			Struts2Util.renderXml(frontRegManager.getSelectRespondentsForAll(
					id, getContextPath(), type), "encoding:UTF-8",
					"no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @title: saveVMVisit
	 * @description: 保存预约登记
	 * @author herb
	 * @return
	 * @date Apr 24, 2013 7:13:49 AM
	 * @throws
	 */
	public String saveVMVisit() {
		JSONObject json = new JSONObject();
		try {
			json.put("result", "0");
			String vsId = this.getRequest().getParameter("vsId");
			String names = this.getRequest().getParameter("names");
			String pnos = this.getRequest().getParameter("pnos");
			String cards = this.getRequest().getParameter("cards");
			String cardType = this.getRequest().getParameter("cardType");
			String cardNo = this.getRequest().getParameter("cardNo");
			String photoName = this.getRequest().getParameter("photoName");
			String[] nameArr = names.split(",");
			String[] pnoArr = pnos.split(",");
			String[] cardArr = cards.split(",");
			// 删除全部附卡
			frontRegManager.deleteVisitAuxiByVsId(vsId);
			// 添加新的附卡
			for (int i = 0; i < pnoArr.length; i++) {// 根据身份证号找
				VmVisitAuxi vmVisitAuxi = new VmVisitAuxi();
				vmVisitAuxi.setVsId(Long.valueOf(vsId));
				vmVisitAuxi.setVaBak1(nameArr[i]);
				vmVisitAuxi.setResidentNo(pnoArr[i]);
				vmVisitAuxi.setCardId(cardArr[i]);
				vmVisitAuxi.setCardType("2");
				vmVisitAuxi.setVaReturn(VmVisitAuxi.VA_RETURN_NO);
				frontRegManager.saveSoncard(vmVisitAuxi);
			}
			// 修改主卡表:卡信息
			if (null != cardNo && cardNo.trim().length() > 0) {
				VmVisit vm = new VmVisit();
				vm.setVsId(Long.valueOf(vsId));
				vm.setCardId(cardNo);
				vm.setCardType(cardType);
				vm.setVsReturn(VmVisit.VS_RETURN_RNO);
				Integer vsNum = pnoArr.length + 1;
				vm.setVsNum(Short.valueOf(vsNum.toString()));
				frontRegManager.updateVmVisit(vm);
				// TODO 给卡授权限
			}
			// 修改访客基本信息
			if (photoName != null && photoName.trim().length() > 0) {
				VmVisit vm = (VmVisit) frontRegManager.findById(VmVisit.class,
						Long.valueOf(vsId));
				VmVisitorinfo info = new VmVisitorinfo();
				info.setViId(vm.getViId());
				// 得到编辑后的大图
				String realName = dealPhoto(photoName);
				info.setResBak2(realName);
				frontRegManager.updateVmVisitorInfo(info);
			}

			json.put("result", "1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}

	/**
	 * 
	 * @title: dealPhoto
	 * @description: 摄像头照片处理
	 * @author herb
	 * @param photoName
	 * @return
	 * @date Apr 26, 2013 11:32:07 PM
	 * @throws
	 */
	private String dealPhoto(String photoName) {
		String realPath = handlerPicture(photoName);
		String realDir = PropertiesUtil.getAnyConfigProperty("dir.reg.photo",
				PropertiesUtil.NJHW_CONFIG);
		File myFilePath = new File(realDir);
		if (!myFilePath.exists()) {
			myFilePath.mkdirs();
		}
		String tmpDir = PropertiesUtil.getAnyConfigProperty(
				"dir.reg.photo.temp", PropertiesUtil.NJHW_CONFIG);
		String realName = realDir
				+ realPath.substring(realPath.indexOf("_") + 1, realPath
						.length());
		File deleteFile = new File(realPath);// 旧文件
		try {
			FileCopyUtils.copy(deleteFile, new java.io.File(realName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		/* 删除临时文件 */
		String pre = realPath.substring(0, realPath.indexOf("_"));
		pre = pre.substring(pre.lastIndexOf("/") + 1);
		File file = new File(tmpDir);
		File[] files = file.listFiles();
		for (File ff : files) {
			String ffName = ff.getName();
			ffName = ffName.substring(0, ffName.indexOf("_"));
			if (ffName.equals(pre)) {
				ff.delete();
			}
		}
		return realName;
	}

	/**
	 * 
	 * @title: handlerPicture
	 * @description: TODO
	 * @author herb
	 * @param photoName
	 * @date Apr 26, 2013 3:15:06 PM
	 * @throws
	 */
	private String handlerPicture(String photoName) {

		String stringName = Struts2Util.getParameter("photoName");
		String name = stringName.substring(stringName.lastIndexOf("/") + 1);
		name = name.substring(0, name.lastIndexOf("."));
		String filePath = PropertiesUtil.getAnyConfigProperty(
				"dir.reg.photo.temp", PropertiesUtil.NJHW_CONFIG);
		File file = new File(filePath);
		String[] files = file.list();
		if (null == files) {
			return null;
		}
		List<Map> list = new ArrayList<Map>();
		for (String fileStr : files) {
			String tmpStr = fileStr.substring(0, fileStr.indexOf("_"));
			String timeStr = fileStr.substring(fileStr.indexOf("_") + 1,
					fileStr.indexOf(".") - 1);
			if (tmpStr.equals(name)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", timeStr);
				map.put("fileStr", fileStr);
				list.add(map);
			}
		}
		return filePath + "/" + getRulePicturePath(list);
	}

	/**
	 * 
	 * @title: getRulePicturePath
	 * @description: 得到符合规则的照片路径
	 * @author qyq
	 * @param list
	 * @return
	 * @date Apr 26, 2013 11:38:12 PM
	 * @throws
	 */
	private String getRulePicturePath(List<Map> list) {
		String selFilePath = "";
		if (null != list && list.size() > 0) {
			long[] ids = new long[list.size()];
			for (int i = 0; i < list.size(); i++) {
				ids[i] = Long.valueOf(list.get(i).get("id").toString());
			}
			long mid = 0;
			if (ids[0] >= ids[1]) {
				if (ids[2] <= ids[0])
					mid = ids[2];
				else
					mid = ids[0];
			} else {
				if (ids[2] <= ids[1])
					mid = ids[2];
				else
					mid = ids[1];
			}
			for (int i = 0; i < list.size(); i++) {
				if (Long.valueOf(list.get(i).get("id").toString()).equals(mid)) {
					selFilePath = list.get(i).get("fileStr").toString();
					break;
				}
			}
		}
		return selFilePath;
	}

	/**
	 * new 前台登记 左侧查询 条件查询申请成功到访的访客
	 * 
	 * @title: visitorList
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @throws Exception
	 * @date 2013-4-2 下午11:38:37
	 * @throws
	 */

	public String visitorList() {
		String viName = Struts2Util.getParameter("viName");
		// 卡号
		String cardId = Struts2Util.getParameter("cardId");
		String residentNo = Struts2Util.getParameter("residentNo");
		String vsSt = Struts2Util.getParameter("vsSt");
		String vsEt = Struts2Util.getParameter("vsEt");
		String vsFlag = Struts2Util.getParameter("vsFlag");
		int p1 = 1;
		p1 = (null == this.getRequest().getParameter("pageNo")) ? 1 : Integer
				.valueOf(this.getRequest().getParameter("pageNo").trim());
		page.setPageNo(p1);
		try {
			this.page = frontRegManager.queryVisitor(page, viName, vsFlag,
					vsSt, vsEt, residentNo, cardId,Struts2Util.getParameter("type"));

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
		return SUCCESS;
	}
	
	/**
	 * new 取消预约申请
	 * 
	 * @title: cancelVistor
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @throws Exception
	 * @date 2013-4-2 下午11:38:37
	 * @throws
	 */
	public String cancelVistor(){
		String vsId = this.getRequest().getParameter("vsId");
		frontRegManager.cancelVistor(Long.parseLong(vsId),"99");
		return SUCCESS;
	}

	/**
	 * 
	 * @title: refreshCardVmList
	 * @description: new 前台登记 刷卡 刷新左侧列表 通过市民卡（或者临时卡）查询全部
	 *               查询全部市民卡对应(当天、已经确认|拒绝|完成)的事务
	 * @author gxh
	 * @return
	 * @throws Exception
	 * @date 2013-4-2 下午11:38:37
	 * @throws
	 */

	public String refreshCardVmList() {

		String cardId = Struts2Util.getParameter("cardId");// 市民卡或者临时卡
		page.setPageSize(10);
		int p1 = 1;
		p1 = (null == this.getRequest().getParameter("pageNo")) ? 1 : Integer
				.valueOf(this.getRequest().getParameter("pageNo").trim());
		page.setPageNo(p1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 完整的时间
		Date date = new Date();
		String startTime = sdf.format(date) + " 00:00:00";
		String endTime = sdf.format(date) + " 23:59:59";
		try {
			if (cardId != null && !cardId.trim().equals("")) {
				this.page = frontRegManager.findVmVisitByCardId(page, cardId,
						startTime, endTime);
				if (null == page || page.getResult().size() == 0) {// 如果没有查询到市民卡号
					// 调用市民卡接口查询身份证号码
					NjhwTscard nt = personCardQueryToAppService
							.queryPersonCard(cardId);
					if (null != nt) {
						this.page = frontRegManager.findVmVisitByCardId(page,
								nt.getCardId(), startTime, endTime);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return SUCCESS;
	}

	/**
	 * 读卡取访客基本信息
	 * 
	 * @title: getvisifoByid
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-4-2 下午11:38:47
	 * @throws
	 */

	public String getvisifoByid() {
		String resBak1 = Struts2Util.getParameter("resBak1");
		String residentNo = null;
		String vsId = null;
		try {

			Map map = frontRegManager.findVisitorsinfo(vsId, residentNo, vsId);
			this.getRequest().setAttribute("map", map);

			int num = frontRegManager.selectNum(Long.parseLong(vsId)).size();
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
	* @title: fatherCardIdDis
	* @description: 未被分配的访客卡号
	* @author pxh
	* @date 2013年9月26日14:28:44
	*/ 
	public String fatherCardIdDis()
	{
		// page.setPageSize(16);
		List telList = frontRegManager.getFatherCardIdList();
		getRequest().setAttribute("telList", telList);
		return SUCCESS;
	}

	/**
	 * @title: getAllInfor
	 * @description: 新前台登记 点击左侧列表项目 刷新右侧操作框
	 * @author herb
	 * @return
	 * @date 2013-4-2 下午11:38:59
	 * @throws
	 */
	public String getAllInfor() {
		String vsId = Struts2Util.getParameter("vsId");
		HashMap vm = frontRegManager.getVisitInfoDetail(vsId);
		List<VmVisitAuxi> vvaList = null;
		String telnum = null;	

		if (null != vm) {
			// 查询副卡列表
			vvaList = frontRegManager.selectNum(Long.parseLong(vsId));
			this.getRequest().setAttribute("vNum", vvaList.size());
			if (null != vm.get("USER_ID")) {
				long userId = Long.valueOf(vm.get("USER_ID").toString());
				telnum = frontRegManager.getPhoneByid(userId);
			}

			// 读取字照片路径
			String cardPath = null;
			if (null != vm.get("PIC")) {
				cardPath = vm.get("PIC").toString().trim();
			}
			if (cardPath == null || cardPath.length() < 1) {// 通过市民卡号,读市民卡表找图片
				if (vm.get("CITY_CARD") != null) {
					NjhwTscard tsCard = personnelExpInforManager
							.njhwTscardByCardId(vm.get("CITY_CARD").toString());
					if (null != tsCard) {
						vm.put("PIC", tsCard.getUserPhoto());
					}
				}
			}
			// 读取照片字节流
			if (vm.get("PIC") != null
					&& vm.get("PIC").toString().trim().length() > 0) {
				cardPath = vm.get("PIC").toString().trim();
				String contents = PictureUtils.getBase64Pic(cardPath);
				vm.put("PIC", contents);
			}
		}
		String time = null;
		String time1 = null;
		if (vm.get("VS_ST") != null
				&& vm.get("VS_ST").toString().length() >= 10) {
			time = vm.get("VS_ST").toString().substring(0, 10);
			time1 = DateUtil.getDateTime("yyyy-MM-dd");
		}
		this.getRequest().setAttribute("time1", time1);
		this.getRequest().setAttribute("vm", vm);
		this.getRequest().setAttribute("time", time);
		this.getRequest().setAttribute("vvaList", vvaList);
		this.getRequest().setAttribute("telnum", telnum);
		getRequest().setAttribute("nowDate",
				DateUtil.getDateTime("yyyy-MM-dd HH:mm"));
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
			vmVisit = frontRegManager.getVisitByid(Long.parseLong(vsId));
			vmVisit.setCardId(cardId);
			vmVisit.setCardType(VmVisit.VS_CARTYPE_TEMCARD);// 卡类型临时卡
			// vmVisit.setVsFlag(VmVisit.VS_FLAG_COME);
			vmVisit.setVsReturn(VmVisit.VS_RETURN_RNO);// 状态未还2
			frontRegManager.saveEntity(vmVisit);

			this.getRequest().setAttribute("vsids", vmVisit.getVsId());
			setIsSuc("true");
		} catch (Exception e) {
			e.printStackTrace();
			setIsSuc("false");

		}
		return SUCCESS;
	}

	/**
	 * 保存副卡
	 */
	/*
	 * public String saveSoncard() {
	 * 
	 * JSONObject json = new JSONObject(); String auxivsId =
	 * Struts2Util.getParameter("vsid"); try { String cardTypenum =
	 * Struts2Util.getParameter("cardTypenum"); String residentNonum =
	 * Struts2Util.getParameter("residentNonum"); String cardIdnum =
	 * Struts2Util.getParameter("cardIdnum"); String userNamenum =
	 * Struts2Util.getParameter("userNamenum");
	 * 
	 * String[] arrayd; String[] array; String[] arrayb; String[] arrayc; arrayd
	 * = cardTypenum.split(","); array = residentNonum.split(","); arrayb =
	 * cardIdnum.split(","); arrayc = userNamenum.split(",");
	 * 
	 * vmVisit = frontRegManager.getVisitByid(Long.parseLong(auxivsId)); for
	 * (int i = 0; i < array.length; i++) { VmVisitAuxi vmVisitAuxi1 = new
	 * VmVisitAuxi(); vmVisitAuxi1.setVsId(Long.parseLong(auxivsId));
	 * vmVisitAuxi1.setVaBak1(arrayc[i]); vmVisitAuxi1.setResidentNo(array[i]);
	 * vmVisitAuxi1.setCardId(arrayb[i]); vmVisitAuxi1.setCardType(array[i]);
	 * vmVisitAuxi1.setVaReturn(VmVisitAuxi.VA_RETURN_NO);
	 * frontRegManager.saveSoncard(vmVisitAuxi1); }
	 * vmVisit.setVsretSub(VmVisit.VS_RET_SUB_NO);// //
	 * vmVisit.setVsNum(vsNums); frontRegManager.saveEntity(vmVisit);
	 * 
	 * // 刷新副卡列表 List<VmVisitAuxi> vvaList = frontRegManager.selectNum(Long
	 * .parseLong(auxivsId)); if (vvaList.size() > 0) {
	 * Struts2Util.renderJson(vvaList, "encoding:UTF-8", "no-cache:true"); }
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * 
	 * }
	 * 
	 * return null;
	 * 
	 * }
	 */
	/**
	 * 
	 * @title: findDailyVmList
	 * @description: 方法实现查询当天的所有访客信息，为访客统计用
	 * @author herb
	 * @return
	 * @date Apr 22, 2013 2:20:14 PM
	 * @throws
	 */
	public String findDailyVmList() {
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

				if (attMap.get("VS_ST1_LEA") != null) {
					visitDelay = attMap.get("VS_ST1_LEA").toString();
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

					/*
					 * if (flag.equals(VmVisit.VS_FLAG_COME)) { vzx += 1;
					 * vcount.setVmVisiting(vzx);
					 * 
					 * }
					 */
					/*
					 * if (flag.equals(VmVisit.VS_FLAG_FINISH) ||
					 * flag.equals(VmVisit.VS_FLAG_ERROR)) { vlk += 1;
					 * vcount.setVmLeave(vlk); }
					 */
					// 外部访问人员情况
					if (StringToDate(visitStart).getTime() < new Date()
							.getTime()
							&& new Date().getTime() < StringToDate(visitEnd)
									.getTime()) {
						vzx += 1;
						vcount.setUserVisiting(vzx);
					}

					if (new Date().getTime() > StringToDate(visitEnd).getTime()) {

						vlk += 1;
						// vcount.setVmLeave(vlk);
						vcount.setUserLeave(vlk);

					}
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

			vcount.setVmCount(unum); // 内部人员访客总数
			vcount.setUserCount(vmList.size() - unum); // 外部人员访客数目

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
		return SUCCESS;

	}

	/**
	 * 保存访客信息
	 * 
	 * @title: save
	 * @description: 未注册预约登记保存
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public String saveUnBeforeVM() throws Exception{
		fatherCardId=getParameter("fatherCardId");
		
		if(fatherCardId!=null && fatherCardId.startsWith("0000")){
			fatherCardId = fatherCardId.substring(4);
		}
		
		cardId=getParameter("cardId");
		VmVisit vmvi = null;
		Long VsIdpub = (long) 0;
		// 证件号
		String rnNum = "";
		JSONObject json = new JSONObject();
		String beginTime = "";
		String endTime = "";
		NjhwTscard nt = null;

		//beginTime = callerModel.getBeginTime();
		Calendar c = Calendar.getInstance(); 
		c.setTime(new Date());
		c.add(Calendar.MINUTE, 1); //加快一分钟，以便数据轮询的时候能够查到
		beginTime=DateUtil.date2Str(c.getTime(), "yyyy-MM-dd HH:mm:ss");
		endTime = callerModel.getEndTime();

		VmVisitorinfo vmInfo = null;

		// 1市民卡2身份证3其他证件
		if (getType().equals("1")) {
			rnNum = callerModel.getCityCard();
			
			try {
				nt = personCardQueryToAppService.queryPersonCard(rnNum);
			} catch (Exception e) {
				json.put("status", "false");
				json.put("message", "市民卡接口未通");
				Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
				return null;
			}
			/*
			if (nt == null) {
				json.put("status", "false");
				json.put("message", "读取市民卡信息不成功");
				Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
				return null;
			}else{
				String puicStatus = nt.getPuicstatus();
				String lostedStatus = nt.getCardLosted();
				
//				if(puicStatus != null && puicStatus.equals("0")){
//					json.put("status", "false");
//					json.put("message", "市民卡未开通,请办理市民卡激活业务.");
//					Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
//					return null;
//				}
				
				if(lostedStatus != null && lostedStatus.equals("1")){
					json.put("status", "false");
					json.put("message", "该市民卡已挂失.");
					Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
					return null;
				}
			}*/
			vmInfo = noOrderManager.findVmVisitorinfoByCityCard(rnNum);
			if (vmInfo == null && nt != null) {
				vmInfo = noOrderManager.findVmVisitorinfoByCardId(nt.getResidentNo());
			}
		} else if (this.getType().equals("2")) {// 身份证
			rnNum = callerModel.getResidentNo();
			vmInfo = noOrderManager.findVmVisitorinfoByCardId(rnNum);
		} else {// 其他证件
			rnNum = callerModel.getCertificateNo();
			vmInfo = noOrderManager.findVmVisitorinfoByCertificateNo(rnNum);
		}

		try {
			if (curvsid != null && !curvsid.equals("")) {
				vmvi = frontRegManager.getVisitByid(Long.parseLong(curvsid));
			}
			// 设置访客基本信息表
			if (vmInfo == null) {
				if (getType().equals("1")) {// 市民卡
					vmVisiorinfo.setResBak1(rnNum);
					if(nt != null){
						vmVisiorinfo.setResidentNo(nt.getResidentNo());
					}
				} else if (this.getType().equals("2")) {// 身份证
					vmVisiorinfo.setResidentNo(rnNum);
				} else {// 其他证件
					//vmVisiorinfo.setResBak3(rnNum);
					vmVisiorinfo.setResBak3(fatherCardId);
					vmVisiorinfo.setResBak4(callerModel.getCertificateName().trim());
				}
				vmVisiorinfo.setViName(callerModel.getUserName().trim());// 访客名字
				vmVisiorinfo.setViMobile(callerModel.getPhone().trim());// 电话
				vmVisiorinfo.setPlateNum(callerModel.getCarId().trim());// 车牌
				
				// 访客类型(01 政府单位;02 公众个人;03企业客户;04 服务业人员;)
				vmVisiorinfo.setViType("02"); 
				vmVisiorinfo.setViOrigin(callerModel.getVmDepart().trim());
				if (callerModel.getPhotoName().trim() != null 
						&& !callerModel.getPhotoName().trim().equals("")) {
					String photopath = dealPhoto(callerModel.getPhotoName().trim());
					vmVisiorinfo.setResBak2(photopath);
				}
				noOrderManager.save(vmVisiorinfo);
				if (vmvi != null) {
					vmvi.setViId(vmVisiorinfo.getViId());
				} else {
					vmVisit.setViId(vmVisiorinfo.getViId());
				}
			} else {
				vmInfo.setViMobile(callerModel.getPhone().trim());// 电话
				vmInfo.setPlateNum(callerModel.getCarId().trim());// 车牌
				// 访客类型(01 政府单位;02 公众个人;03 企业客户;04 服务业人员;)
				vmInfo.setViType("02"); 
				vmInfo.setViOrigin(callerModel.getVmDepart().trim());
				vmInfo.setViName(callerModel.getUserName().trim());
				
				String photoName = callerModel.getPhotoName();
				if (photoName!=null && !photoName.trim().equals("")) {
					String photopath = dealPhoto(photoName.trim());
					vmInfo.setResBak2(photopath);
				} else {
					String dbPhotoName = callerModel.getDbPhotoName();
					if (dbPhotoName != null && !dbPhotoName.trim().equals("")) {
						vmInfo.setResBak2(dbPhotoName.trim());
					}
				}
				noOrderManager.save(vmInfo);
				if (vmvi != null) {
					vmvi.setViId(vmInfo.getViId());
				} else {
					vmVisit.setViId(vmInfo.getViId());
				}
			}

			if (vmvi == null) {
				// 设置访问事务表
				vmVisit.setVsYn("1"); // 是否预约1:已预约 0未预约
				vmVisit.setVsType("3");// 预约类型(1 主动预约；2 被动预约；3；前台预约)
				
				//前台预约默认为申请确认
				if (getTypeId() != null && getTypeId().equals("2")) {
					vmVisit.setVsFlag(VmVisit.VS_FLAG_APP);
				} else {
					vmVisit.setVsFlag(VmVisit.VS_FLAG_SURE);
				}

				vmVisit.setOrgId(Long.parseLong(callerModel.getOrgId().trim()));//
				vmVisit.setOrgName(callerModel.getOrgName().trim());// 设置被访者单位名称;
				vmVisit.setViName(callerModel.getUserName().trim());// 访客名字
				vmVisit.setUserName(callerModel.getOutName().trim());// 被访者名字
				vmVisit.setUserId(Long.parseLong(callerModel.getUserid()));// 被访者名字id
				vmVisit.setVsNum((short) (getNum() + 1));// 来访人数
				vmVisit.setVsSt(DateUtil.StringToDate(beginTime));// 开始时间
				vmVisit.setVsEt(DateUtil.StringToDate(endTime));// 结束时间
				vmVisit.setResBak1(getType());
				vmVisit.setResBak2(callerModel.getPawn());// 抵押物
				//vmVisit.setResBak3(callerModel.getLocatorCard());// 定位卡号
				vmVisit.setResBak3(fatherCardId);// 定位卡号
				//类型为市民卡
				if (getType().equals("1")) {
					
					String tmpCityCard = getParameter("cityCard");
					vmVisit.setCardId(tmpCityCard);
					//vmVisit.setCardId(callerModel.getCardId());
					vmVisit.setCardType(VmVisit.VS_CARTYPE_CITYCARD); 
				}
				if(callerModel.getCardId()==null || "".equals(callerModel.getCardId()))
				{
					vmVisit.setCardId(callerModel.getFatherCardId());
				}
				noOrderManager.save(vmVisit);
				VsIdpub = vmVisit.getVsId();
			} else {
				vmvi.setVsYn("1"); // 是否预约1:已预约 0未预约
				if (getTypeId() != null && getTypeId().equals("2")) {
					vmvi.setVsFlag(VmVisit.VS_FLAG_APP);
				} else {
					vmvi.setVsFlag(VmVisit.VS_FLAG_SURE);
				}

				vmVisit.setOrgId(Long.parseLong(callerModel.getOrgId().trim()));//
				vmvi.setOrgName(callerModel.getOrgName().trim());// 设置被访者单位名称;
				vmvi.setViName(callerModel.getUserName().trim());// 访客名字
				vmvi.setUserName(callerModel.getOutName().trim());// 被访者名字
				vmvi.setUserId(Long.parseLong(callerModel.getUserid()));// 被访者名字id
				vmvi.setVsNum((short) (getNum() + 1));// 来访人数
				vmvi.setVsSt(DateUtil.StringToDate(beginTime));// 开始时间
				vmvi.setVsEt(DateUtil.StringToDate(endTime));// 结束时间
				vmvi.setResBak1(getType());
				vmvi.setResBak2(callerModel.getPawn());
				vmvi.setResBak3(callerModel.getLocatorCard());
				if(callerModel.getCardId()==null || "".equals(callerModel.getCardId()))
				{
					vmvi.setCardId(callerModel.getFatherCardId());
				}else{
					vmvi.setCardId(callerModel.getCardId());
				}

				if (getType().equals("1")) {
					if(callerModel.getCardId()==null || "".equals(callerModel.getCardId()))
					{
						vmvi.setCardId(callerModel.getFatherCardId());
					}else{
						vmvi.setCardId(callerModel.getCardId());
					}
					vmvi.setCardType(VmVisit.VS_CARTYPE_CITYCARD);
				}
//				if(callerModel.getCardId()==null || "".equals(callerModel.getCardId()))
//				{
//					vmVisit.setCardId(callerModel.getFatherCardId());
//				}
				noOrderManager.save(vmvi);
				VsIdpub = vmvi.getVsId();
			}

			VmVisit v = frontRegManager.getVisitByid(VsIdpub);
			if (fatherCardId != null && (getType().equals("2") || getType().equals("3"))) {
				v.setCardId(fatherCardId);
				v.setCardType(VmVisit.VS_CARTYPE_TEMCARD);// 类型为临时卡
				//前台预约,默认用户已到访
				v.setVsFlag(VmVisit.VS_FLAG_COME);
				v.setResBak3(fatherCardId);
				v.setVsReturn(VmVisit.VS_RETURN_RNO);// 未归还
				
				if(!v.getCardId().startsWith("0000")){
					v.setCardId("0000" + v.getCardId());
				}
				
				frontRegManager.saveEntity(v);
			}
			
			json.put("status", "true");
			v.setStatus("1");
			frontRegManager.saveEntity(v);
		} catch (Exception e) {
			json.put("status", "false");
			json.put("message", "保存失败");
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}

	/**
	 * 
	 * @param xmlStr
	 * @return
	 */
	private String setAuthVisitRight(String xmlStr) {
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL(serviceUrl));
			call.addParameter(new QName(domainName, "visitId"),
					org.apache.axis.Constants.XSD_STRING, ParameterMode.IN);
			call.setOperationName(new QName(domainName, "AuthVisitRight"));
			call.setReturnType(XMLType.XSD_STRING);
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(domainName + "AuthVisitRight");
			String ret = (String) call.invoke(new Object[] { xmlStr });
			return ret;
		} catch (ServiceException e) {
			return "failure";
		} catch (MalformedURLException e) {
			return "failure";
		} catch (RemoteException e) {
			return "failure";
		}
	}

	/**
	 * 
	 * @param curvsid
	 * @param cardId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private boolean isRegister(String curvsid, String cardId, String startTime,
			String endTime) {

		return this.frontRegManager.findVmVisitorinfoByTime(curvsid, cardId,
				startTime, endTime);
	}

	/**
	 * 
	 * @return
	 */
	private List getSecondCard() {
		String[] array;
		String[] arrayb;
		String[] arrayc;
		String[] arrayd;
		String[] arraye;
		String[] arrayf;
		String[] arrayg;

		array = locatorIdNum.split(",");
		arrayb = cardIdnum.split(",");
		arrayc = userNamenum.split(",");
		arrayd = cardTypenum.split(",");
		arrayf = pawnNum.split(",");
		arrayg = residentNonum.split(",");

		if (!auxiIdNum.equals("")) {
			arraye = auxiIdNum.split(",");
		} else {
			arraye = null;
		}
		List auxiList = new ArrayList<VmVisitAuxi>();
		for (int i = 0; i < array.length; i++) {
			VmVisitAuxi vmVisitAuxi1 = new VmVisitAuxi();
			if (!arraye[i].equals("")) {
				vmVisitAuxi1.setVaBak1(arrayc[i].trim());
				vmVisitAuxi1.setVaBak2(array[i].trim());
				vmVisitAuxi1.setVaBak3(arrayf[i].trim());
				vmVisitAuxi1.setCardId(arrayb[i].trim());
				vmVisitAuxi1.setCardType(arrayd[i].trim());
				vmVisitAuxi1.setResidentNo(arrayg[i].trim());
				vmVisitAuxi1.setVaReturn(VmVisitAuxi.VA_RETURN_NO);
				auxiList.add(vmVisitAuxi1);
			}

		}
		return auxiList;
	}

	/**
	 * bu 保存副卡
	 * 
	 * @title: saveSoncard
	 * @description: TODO
	 * @author gxh
	 * @param auxivsId
	 * @param v
	 * @return
	 * @date 2013-4-6 下午03:47:23
	 * @throws
	 */
	private int saveSoncard(Long auxivsId, VmVisit v,String beginTime,String endTime) {
		int snum = 1;
		VmVisitAuxi vv = null;
		try {
			String[] array;
			String[] arrayb;
			String[] arrayc;
			String[] arrayd;
			String[] arraye;
			String[] arrayf;
			String[] arrayg;

			array = locatorIdNum.split(",");
			arrayb = cardIdnum.split(",");
			arrayc = userNamenum.split(",");
			arrayd = cardTypenum.split(",");
			arrayf = pawnNum.split(",");
			arrayg = residentNonum.split(",");

			if (!auxiIdNum.equals("")) {
				arraye = auxiIdNum.split(",");
			} else {
				arraye = null;
			}
			if (null != auxivsId && !"".equals(auxivsId)) {
				frontRegManager.deleteVmVisitAuxiById(auxivsId);
			}
			for (int i = 0; i < array.length; i++) {
				VmVisitAuxi vmVisitAuxi1 = new VmVisitAuxi();
				if (arraye != null && null != arraye[i]
						&& !"".equals(arraye[i])) {
					vv = frontRegManager.findVmVisitAuxiById(Long
							.parseLong(arraye[i]));
					if (vv != null) {
						vv.setVsId(auxivsId);
						vv.setVaBak1(arrayc[i].trim());
						vv.setVaBak2(array[i].trim());
						vv.setVaBak3(arrayf[i].trim());
						vv.setCardId(arrayb[i].trim());
						vv.setCardType(arrayd[i].trim());
						vv.setResidentNo(arrayg[i].trim());
						vv.setVaReturn(VmVisitAuxi.VA_RETURN_NO);
						vv.setStartTime(DateUtil.StringToDate(beginTime));
						vv.setEndTime(DateUtil.StringToDate(endTime));
						frontRegManager.saveSoncard1(vv);
					} else {
						vmVisitAuxi1.setVsId(auxivsId);
						vmVisitAuxi1.setVaBak1(arrayc[i].trim());
						vmVisitAuxi1.setVaBak2(array[i].trim());
						vmVisitAuxi1.setVaBak3(arrayf[i].trim());
						vmVisitAuxi1.setCardId(arrayb[i].trim());
						vmVisitAuxi1.setCardType(arrayd[i].trim());
						vmVisitAuxi1.setResidentNo(arrayg[i].trim());
						vmVisitAuxi1.setVaReturn(VmVisitAuxi.VA_RETURN_NO);
						vmVisitAuxi1.setStartTime(DateUtil.StringToDate(beginTime));
						vmVisitAuxi1.setEndTime(DateUtil.StringToDate(endTime));
						frontRegManager.saveSoncard(vmVisitAuxi1);
					}

				}
			}
			v.setVsretSub(VmVisit.VS_RET_SUB_NO);// 是否还副卡 未还
			v.setVsNum(v.getVsNum());
			frontRegManager.saveEntity(v);
			return snum;

		} catch (Exception e) {
			return snum = 0;
		}

	}

	/**
	 * 
	 * @title: refreshVmOpeContent
	 * @description: 刷卡查询操作区域内容
	 * @author herb
	 * @return
	 * @date Apr 23, 2013 1:29:12 AM
	 * @throws
	 */
	public String refreshVmOpeContent() {
		// this.getRequest()
		// / .setAttribute("orgs", this.noOrderManager.getAllOrgs());

		List<VmVisitAuxi> vvaList = null;
		HashMap vm = null;
		String cardId = Struts2Util.getParameter("cardId");// 市民卡或者临时卡
		if (cardId == null || cardId.trim().length() < 1) {// 返回空页面
			String time = DateUtil.getDateTime("yyyy-MM-dd");
			this.getRequest().setAttribute("time", time);
			this.getRequest().setAttribute("time1", time);
			getRequest().setAttribute("nowDate",
					DateUtil.getDateTime("yyyy-MM-dd HH:mm"));
			return INPUT;
		}
		page.setPageSize(20);
		int p1 = 1;
		p1 = (null == this.getRequest().getParameter("pageNo")) ? 1 : Integer
				.valueOf(this.getRequest().getParameter("pageNo").trim());
		page.setPageNo(p1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 完整的时间
		Date date = new Date();
		String startTime = sdf.format(date) + " 00:00:00";
		String endTime = sdf.format(date) + " 23:59:59";
		try {
			if (cardId != null && !cardId.trim().equals("")) {
				this.page = frontRegManager.findVmVisitByCardId(page, cardId,
						startTime, endTime);
				if (null == page || page.getResult().size() == 0) {// 如果没有查询到市民卡号
					// 调用市民卡接口查询身份证号码
					NjhwTscard nt = personCardQueryToAppService
							.queryPersonCard(cardId);
					if (null != nt) {
						this.page = frontRegManager.findVmVisitByCardId(page,
								nt.getCardId(), startTime, endTime);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		NjhwUsersExp userExp = null;
		if (null != page && page.getResult().size() > 0) {
			vm = page.getResult().get(0);
			String vsId = vm.get("VS_ID").toString().trim();
			vvaList = frontRegManager.selectNum(Long.parseLong(vsId));
			this.getRequest().setAttribute("vvaList", vvaList);

			if (null != vm) {
				// 刷新副卡列表
				vvaList = frontRegManager.selectNum(Long.parseLong(vsId));
				if (null != vm.get("USER_ID")) {
					long userId = Long.valueOf(vm.get("USER_ID").toString());
					userExp = personnelExpInforManager.getpsByid(userId);
				}
				// 读取字照片路径
				String cardPath = null;
				if (null != vm.get("PIC")) {
					cardPath = vm.get("PIC").toString().trim();
				}
				if (cardPath == null || cardPath.length() < 1) {// 通过市民卡号,读市民卡表找图片
					if (vm.get("CITY_CARD") != null) {
						NjhwTscard tsCard = personnelExpInforManager
								.njhwTscardByCardId(vm.get("CITY_CARD")
										.toString());
						if (null != tsCard) {
							vm.put("PIC", tsCard.getUserPhoto());
						}
					}
				}
				// 读取照片字节流
				if (vm.get("PIC") != null
						&& vm.get("PIC").toString().trim().length() > 0) {
					cardPath = vm.get("PIC").toString().trim();
					String contents = PictureUtils.getBase64Pic(cardPath);
					vm.put("PIC", contents);
				}
			}
		}

		String time = vm.get("VS_ST").toString().substring(0, 10);
		this.getRequest().setAttribute("time", time);
		this.getRequest().setAttribute("vm", vm);
		this.getRequest().setAttribute("vvaList", vvaList);
		this.getRequest().setAttribute("userExp", userExp);
		if (null == vm) {
			return INPUT;
		}
		return SUCCESS;
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
	 * 通过机构名称得到受访者名称
	 * 
	 * @title: ajaxGetUserByOrgId
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-4-23 下午11:13:26
	 * @throws
	 */
	public String ajaxGetUserByOrgId() {
		JSONObject json = new JSONObject();
		try {
			String orgId = getRequest().getParameter("orgId");
			String outName = getRequest().getParameter("outName");
			Map map = new HashMap();
			map.put("orgId", Long.parseLong(orgId));
			map.put("userName", outName);
			List<Map> list = frontRegManager.findOrgUserId(map);
			System.out.print(list.size());
			JSONArray alist = new JSONArray(list);
			Struts2Util.renderJson(alist.toString(), "encoding:UTF-8",
					"no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据身份证号加载访客信息
	 * 
	 * @title: loadVisiInfo
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @throws Exception
	 * @date 2013-4-24 上午12:11:00
	 * @throws
	 */
	public String loadVisiInfo() throws Exception {
		JSONObject json = new JSONObject();
		String number = Struts2Util.getParameter("residentId");

		String type = Struts2Util.getParameter("type");
		String residentId = null;

		if (type != null && type.equals("1")) {
			// 先调用市民卡接口
			NjhwTscard nt = new NjhwTscard();
			nt = personCardQueryToAppService.queryPersonCard(number);// 调用市民卡接口
			if (nt != null) {
				residentId = nt.getResidentNo();
				json.put("getUserName", nt.getUserName().trim());
				json.put("residentId", nt.getResidentNo().trim());

				if (!"0".equals(nt.getCardstatus())
						|| !"0".equals(nt.getCardLosted())) {
					residentId = null;
					json.put("status", 8);// 市民卡没查到信息
					json.put("message", "卡未激活或已挂失");
					Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
							"no-cache:true");
					return null;
				} else {
					// 再从NjhwTscard查找卡片信息，是否内部挂失
					nt = frontRegManager.findNjhwTscardBycardId(number);
					if (nt != null && "1".equals(nt.getSystemLosted())) {
						residentId = null;
						json.put("status", 8);// 市民卡没查到信息
						json.put("message", "卡内部挂失");
						Struts2Util.renderJson(json.toString(),
								"encoding:UTF-8", "no-cache:true");
						return null;
					} else if (nt != null) {
						residentId = nt.getResidentNo();
						json.put("getUserName", nt.getUserName().trim());
						json.put("residentId", nt.getResidentNo().trim());
					}
				}
			} else {
				residentId = null;
				json.put("status", 8);// 市民卡没查到信息
				json.put("message", "未查到市民卡信息");
				json.put("number", number);
				Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
						"no-cache:true");
				return null;
			}
		} else if (type != null && type.equals("2")) {
			residentId = number;
		}
		// 通过身份证号查询
		Map map = null;
		if (type != null && residentId != null && !residentId.equals("")) {
			map = frontRegManager.findUsersExpByReNo(residentId);
		}
		if (null != map) {// 通过身份证查到信息
			json.put("status", 9);// 内部人员
			if (map.get("UEP_PHOTO") != null
					&& !map.get("UEP_PHOTO").equals("")) {
				String imgSrc1 = PictureUtils.getBase64Pic(map.get("UEP_PHOTO")
						.toString());
				json.put("imgSrc1", imgSrc1);

			}
			json.put("map", map);
		} else {// 身份证查不到信息

			vmVisiorinfo = this.noOrderManager
					.findVmVisitorinfoByCardId(residentId);
			try {
				if (vmVisiorinfo != null) {

					json.put("VIName", vmVisiorinfo.getViName());// 姓名
					json.put("PlateNum", vmVisiorinfo.getPlateNum());// 车牌
					json.put("ViMobile", vmVisiorinfo.getViMobile());// 手机
					json.put("ViOrigin", vmVisiorinfo.getViOrigin());// 单位
					json.put("residentId", residentId);

					if (vmVisiorinfo.getResBak2() == null
							|| vmVisiorinfo.getResBak2().equals("")) {
						NjhwTscard tsCard = personnelExpInforManager
								.njhwTscardByCardId(vmVisiorinfo.getResBak1());
						if (null != tsCard) {
							vmVisiorinfo.setResBak2(tsCard.getUserPhoto());
						}
					}
					if (vmVisiorinfo.getResBak2() != null
							&& !vmVisiorinfo.getResBak2().equals("")) {
						String imgSrc = PictureUtils.getBase64Pic(vmVisiorinfo
								.getResBak2());
						json.put("imgSrc", imgSrc);
						json.put("dbPhotoName", vmVisiorinfo.getResBak2());

					}
					json.put("status", 0);// 存在
				} else {
					if (type != null && type.equals("1")) {
						json.put("status", 8);// 市民卡没查到信息
					} else {
						json.put("status", 1);// 身份证没查到信息
					}
				}
			} catch (JSONException e) {

			}
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}

	public NoOrderManager getNoOrderManager() {
		return noOrderManager;
	}

	public void setNoOrderManager(NoOrderManager noOrderManager) {
		this.noOrderManager = noOrderManager;
	}

	@Override
	protected void prepareModel() throws Exception {

	}

	public FrontRegManager getFrontRegManager() {
		return frontRegManager;
	}

	public void setFrontRegManager(FrontRegManager frontRegManager) {
		this.frontRegManager = frontRegManager;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	@Override
	public CallerModel getModel() {
		return callerModel;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	// public String getCardType() {
	// return cardType;
	// }

	// public void setCardType(String cardType) {
	// /this.cardType = cardType;
	// }

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

	public PersonCardQueryToAppService getPersonCardQueryToAppService() {
		return personCardQueryToAppService;
	}

	public void setPersonCardQueryToAppService(
			PersonCardQueryToAppService personCardQueryToAppService) {
		this.personCardQueryToAppService = personCardQueryToAppService;
	}
	public String getResidentNonum() {
		return residentNonum;
	}

	public void setResidentNonum(String residentNonum) {
		this.residentNonum = residentNonum;
	}

	/**
	 * 
	 * @title: photograph
	 * @description:通过摄像头拍摄照片
	 * @author qyq
	 * @return
	 * @throws Exception
	 * @date 2013-4-23
	 * @throws
	 */
	public String photograph() {
		String dirPath = PropertiesUtil.getAnyConfigProperty(
				"dir.reg.photo.temp", PropertiesUtil.NJHW_CONFIG);
		this.getRequest().setAttribute("photoName",
				dirPath + "/" + System.currentTimeMillis() + ".jpg");
		return SUCCESS;
	}

	public String giveBack() {
		JSONObject json = new JSONObject();

		try {
			json.put("result", "3");
			String backCards = this.getRequest().getParameter("cardId");

			VmVisitAuxi f = visitorqueryManager.exitCardFByid1(backCards);// 副卡
			if (f != null) {
				json.put("result", "2");
			} else {
				VmVisit vmvisit = visitorqueryManager.exitCardByid1(backCards);
				if (vmvisit != null) {
					json.put("result", "2");
				} else {
					json.put("result", "1");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
					"no-cache:true");
			return null;
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");

		return null;

	}

	/**
	 * 
	 * @title: giveBackCards
	 * @description: 退卡
	 * @author herb
	 * @return
	 * @date Apr 25, 2013 8:43:16 PM
	 * @throws
	 */
	public String giveBackCards() {
		JSONObject json = new JSONObject();
		try {
			json.put("result", "0");
			String backCards = this.getRequest().getParameter("backCards");
			String vsId = this.getRequest().getParameter("vsId");
			String[] array;
			array = backCards.split(",");

			for (int i = 0; i < array.length; i++) {
				VmVisitAuxi f = visitorqueryManager.exitCardFByid(array[i]);// 副卡
				if (f != null) {
					if (!f.getVaReturn().equals(VmVisitAuxi.VA_RETURN_YES)) {// 如果副卡未归还
						f.setVaReturn(VmVisitAuxi.VA_RETURN_YES);// 归还
						List<VmVisitAuxi> list = visitorqueryManager
								.getUnBackVVACard(f.getVsId());// 是否还有副卡未还
						if (list.size() < 2) {
							visitorqueryManager.updVmVisitStatus(f.getVsId());// 修改主卡状态副卡还完
						}
						// revokePermission(array[i]);// 取消权限
						visitorqueryManager.saveSoncard(f);
					} else {
						this.getRequest().setAttribute("message", "此卡已退");
					}

				} else {
					VmVisit vmvisit = visitorqueryManager
							.exitCardByid(array[i]);
					if (vmvisit != null) {
						if (!vmvisit.getVsReturn().equals(
								VmVisit.VS_RETURN_RYES)) {// 如果临时卡未归还
							vmvisit.setVsReturn(VmVisit.VS_RETURN_RYES);// 归还
							if (vmvisit.getVsretSub().equals(
									VmVisit.VS_RET_SUB_YES)) {// 副卡是否都归还

								vmvisit.setVsFlag(VmVisit.VS_FLAG_FINISH); // 事务结束
							}
							// revokePermission(array[i]);// 取消权限
							visitorqueryManager.saveEntity(vmvisit);// 保存
						}
					}
				}
			}

			json.put("result", "1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}

	/**
	 * 
	 * @title: giveBackCards1
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-6-2 下午02:48:09
	 * @throws
	 */
	public String giveBackCards1() {
		JSONObject json = new JSONObject();
		try {

			String backCards = this.getRequest().getParameter("cardId");
			String vsId = this.getRequest().getParameter("vsid");

			// VmVisitAuxi f = visitorqueryManager.exitCardFByid1(
			// Long.parseLong(vsId), backCards);// 副卡
			VmVisitAuxi f = visitorqueryManager.exitCardFByid1(backCards);// 副卡
			if (f != null) {
				if (!f.getVaReturn().equals(VmVisitAuxi.VA_RETURN_YES)) {// 如果副卡未归还
					f.setVaReturn(VmVisitAuxi.VA_RETURN_YES);// 归还
					List<VmVisitAuxi> list = visitorqueryManager
							.getUnBackVVACard(f.getVsId());// 是否还有副卡未还
					if (list.size() < 2) {
						visitorqueryManager.updVmVisitStatus(f.getVsId());// 修改主卡状态副卡还完
					}
					// revokePermission(backCards);// 取消权限
					visitorqueryManager.saveSoncard(f);
					List<VmVisitAuxi> vvaList = frontRegManager.selectNum(Long
							.parseLong(vsId));
					Struts2Util.renderJson(vvaList, "encoding:UTF-8",
							"no-cache:true");
				} else {
					json.put("guihuan", "guihuan");
					Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
							"no-cache:true");

				}
			} else {
				VmVisit vmvisit = visitorqueryManager.exitCardByid1(backCards);
				if (vmvisit != null) {
					if (!vmvisit.getVsReturn().equals(VmVisit.VS_RETURN_RYES)) {// 如果临时卡未归还
						vmvisit.setVsReturn(VmVisit.VS_RETURN_RYES);// 归还
						if (vmvisit.getVsretSub() != null
								&& !vmvisit.getVsretSub().equals("")) {
							if (vmvisit.getVsretSub().equals(
									VmVisit.VS_RET_SUB_YES)) {// 副卡是否都归还

								vmvisit.setVsFlag(VmVisit.VS_FLAG_FINISH); // 事务结束
							}
						} else {
							vmvisit.setVsFlag(VmVisit.VS_FLAG_FINISH); // 事务结束
							vmvisit.setInOut(VmVisit.VISITOR_OUT);
						}

						// revokePermission(array[i]);//取消权限
						visitorqueryManager.saveEntity(vmvisit);// 保存
						json.put("vsReturn", vmvisit.getVsReturn());
						json.put("vsFlag", vmvisit.getVsFlag());
					} else {
						json.put("vsReturn", vmvisit.getVsReturn());
						json.put("vsFlag", vmvisit.getVsFlag());
						json.put("zguihuan", "zguihuan");
					}

				}
				Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
						"no-cache:true");
			}

		} catch (Exception e) {
		}

		return null;
	}

	/**
	 * 取消权限
	 * 
	 * @title: revokePermission
	 * @description: TODO
	 * @author gxh
	 * @param recarid
	 * @return
	 * @date 2013-4-12 下午12:04:43
	 * @throws
	 */
	private void revokePermission(String recarid) {
		try {
			NjhwUsersExp exp = new NjhwUsersExp();
			System.out.print("***************************" + userid);
			exp = visitorqueryManager.findUsersExpByUserid(Long
					.parseLong(userid));
			if (exp != null) {
				if (exp.getRoomId() != null) {
					devicePermissionToAppService.cancelAuthDeviceOther(recarid,
							exp.getRoomId().toString());// 取消权限
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * 
	 * @title: photograph
	 * @description:读取照片进行显示
	 * @author qyq
	 * @return
	 * @throws Exception
	 * @date 2013-4-23
	 * @throws
	 */
	public String getBytesPic() {
		String contents = "";
		String userPhoto = Struts2Util.getParameter("photoName");
		try {
			String realPath = this.handlerPicture(userPhoto);
			if (null == realPath || realPath.trim().length() < 1)
				return "";
			//this.getRequest().setAttribute("photoName",realPath);
			contents = PictureUtils.getBase64Pic(realPath);
			//contents2=compressData(contents);
		} catch (Exception e) {
			contents = "";
			e.printStackTrace();
		}
		Struts2Util.renderJson(contents, "encoding:UTF-8", "no-cache:true");
		return null;
	}
	

	
	/**
	 * 根据id查找组织名称
	 * 
	 * @title: orgNameByid
	 * @description: TODO
	 * @author gxh
	 * @param id
	 * @return
	 * @date 2013-4-15 上午11:10:51
	 * @throws
	 */
	private String orgNameByid(String orgid) {

		return noOrderManager.orgName(Long.parseLong(orgid));

	}

	/**
	 * 
	 * @title: getUsersByOrgId
	 * @description: 根据单位得到用户
	 * @author cjw
	 * @return
	 * @date 2013-3-28 下午05:40:08
	 * @throws
	 */
	public String getUsersByComeUserName() {
		JSONObject json = new JSONObject();
		Users user = frontRegManager.getUsersByOrgIdAndUserName(
				getParameter("orgId"), getParameter("outName"));
		try {
			if (user != null) {
				json.put("userId", user.getUserid());
				json.put("state", 1);
			} else {
				json.put("state", 0);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}

	public String getFatherCardId() {
		return fatherCardId;
	}

	public void setFatherCardId(String fatherCardId) {
		this.fatherCardId = fatherCardId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getLocatorNo() {
		return locatorIdNum;
	}

	public void setLocatorIdNum(String locatorIdNum) {
		this.locatorIdNum = locatorIdNum;
	}

	public String getCardIdnum() {
		return cardIdnum;
	}

	public String getCardTypenum() {
		return cardTypenum;
	}

	public void setCardTypenum(String cardTypenum) {
		this.cardTypenum = cardTypenum;
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

	public CallerModel getCallerModel() {
		return callerModel;
	}

	public void setCallerModel(CallerModel callerModel) {
		this.callerModel = callerModel;
	}

	public PersonnelExpInforManager getPersonnelExpInforManager() {
		return personnelExpInforManager;
	}

	public void setPersonnelExpInforManager(
			PersonnelExpInforManager personnelExpInforManager) {
		this.personnelExpInforManager = personnelExpInforManager;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public VisitorqueryManager getVisitorqueryManager() {
		return visitorqueryManager;
	}

	public void setVisitorqueryManager(VisitorqueryManager visitorqueryManager) {
		this.visitorqueryManager = visitorqueryManager;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}

	public IPPhoneSiteToAppService getiPPhoneSiteToAppService() {
		return iPPhoneSiteToAppService;
	}

	public void setiPPhoneSiteToAppService(
			IPPhoneSiteToAppService iPPhoneSiteToAppService) {
		this.iPPhoneSiteToAppService = iPPhoneSiteToAppService;
	}

	public String loadOrg() {

		List<Org> orgs = this.noOrderManager.getAllOrgs();

		Struts2Util.renderJson(orgs, "encoding:UTF-8", "no-cache:true");

		return null;
	}

	public String phoneByUserid() {
		JSONObject json = new JSONObject();
		String auxivsId = Struts2Util.getParameter("userid");
		try {
			if(null!=frontRegManager.findUsersExpByUserid(
					Long.parseLong(auxivsId))){
			String tell = (String) frontRegManager.findUsersExpByUserid(
					Long.parseLong(auxivsId)).get("TEL_NUM");
			if (tell != null && !tell.equals("")) {
				json.put("tell", tell);

			} 
		 }
		else {
				json.put("tell", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}

	/**
	 * 找到顶级id
	 * 
	 * @title: byUseridOrgIdesd
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-5-24 下午05:54:10
	 * @throws
	 */

	public String byUseridOrgIdesd() {
		JSONObject json = new JSONObject();
		String auxivsId = Struts2Util.getParameter("userid");
		try {
			long orgId = 0;
			String name = "";
			// 找到当前用户的顶级部门
			List<HashMap> list = this.frontRegManager.getTopOrgId(Long
					.parseLong(auxivsId));
			if (list.size() > 0) {
				orgId = list.get(0).get("TOP_ORG_ID") != null ? Long
						.parseLong(list.get(0).get("TOP_ORG_ID").toString())
						: 0;
				name = list.get(0).get("ORG_NAME") != null ? list.get(0).get(
						"ORG_NAME").toString() : "";
				json.put("orgId", orgId);
				json.put("name", name);
			} else {
				json.put("topOrgId", 0);
				json.put("name", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;

	}

	/**
	 * 打电话
	 * 
	 * @title: ipCall
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-5-24 下午06:48:21
	 * @throws
	 */
	public String ipCall() {
		try {
			Map np = frontRegManager.findUsersExpByUserid(Long.parseLong(this
					.getRequest().getSession().getAttribute(Constants.USER_ID)
					.toString()));

			String selfMac = "";
			if (null != np.get("TEL_MAC")) {
				selfMac = np.get("TEL_MAC").toString();
			}
			String selfTel = "";
			if (null != np.get("TEL_NUM")) {
				selfTel = np.get("TEL_NUM").toString();
			}
			String called = Struts2Util.getParameter("tel");

			boolean isSuccess = iPPhoneSiteToAppService.dail(selfTel, selfMac,
					called);

			if (isSuccess)
				Struts2Util.renderText("success", "encoding:UTF-8",
						"no-cache:true");
			else
				Struts2Util.renderText("fail", "encoding:UTF-8",
						"no-cache:true");
		} catch (Exception e) {
			Struts2Util.renderText("error", "encoding:UTF-8", "no-cache:true");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 模糊查找用户
	 * 
	 * @title: findUsers
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-5-14 下午10:36:37
	 * @throws
	 */

	public String findUsers() {
		String id = null;
		if (!"all".equals(getParameter("type"))) {
			id = getParameter("id");
		}

		String displayName = Struts2Util.getParameter("displayName");
		if (displayName != null) {
			List<Map> listMap = frontRegManager.findUsers(displayName, id);

			if (listMap != null && listMap.size() > 0) {
				Struts2Util.renderJson(listMap, "encoding:UTF-8",
						"no-cache:true");
			}
		}
		return null;
	}

	private String baifenbi(int y, int z) {

		String baifenbi = "";// 接受百分比的值
		double baiy = y * 1.0;
		double baiz = z * 1.0;
		double fen = baiy / baiz;

		DecimalFormat df1 = new DecimalFormat("##.00%");
		// DecimalFormat df1 = new DecimalFormat("####.00");
		baifenbi = df1.format(fen);
		return baifenbi;
	}
}
