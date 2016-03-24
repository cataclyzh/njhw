package com.cosmosource.app.personnel.action;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.Users;

import com.cosmosource.app.ldap.service.LDAPService;
import com.cosmosource.app.ldap.service.LDAPServiceSrc;
import com.cosmosource.app.personnel.model.PersonnerModel;
import com.cosmosource.app.personnel.service.PersonnelExpInforManager;
import com.cosmosource.app.port.serviceimpl.DevicePermissionToAppService;
import com.cosmosource.app.port.serviceimpl.PersonCardQueryToAppService;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.EncodeUtil;
import com.cosmosource.base.util.PropertiesUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

/**
 * 人员扩展信息类
 * 
 * @description: TODO
 * @author gxh
 * @date 2013-4-2 下午10:55:26
 */
@SuppressWarnings("serial")
public class PersonnelManagAction extends BaseAction<PersonnerModel> {
	// private static final String FILEPATH = "D:/upload";

	private PersonnerModel personnerModel = new PersonnerModel();
	private NjhwUsersExp njhwUsersExp;// 人员扩展表
	private Users user;
	private PersonnelExpInforManager personnelExpInforManager;
	private PersonCardQueryToAppService personCardQueryToAppService;
	private EmOrgRes emOrgRes;
	private DevicePermissionToAppService devicePermissionToAppService;
	private File file;
	private String fileFileName;
	private String fileFileContentType;
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页20条记录
	private Org org;

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public PersonnerModel getModel() {
		// TODO Auto-generated method stub
		return personnerModel;
	}

	/**
	 * 进入人员详细信息管理页面
	 * 
	 * @title: infoPersManage
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-4-2 下午11:01:56
	 * @throws
	 */
	public String infoPersManage() {
		String departmentid = "";
		// 用户id
		String userid = this.getRequest().getParameter("userId");

		if (userid != null && !userid.equals("")) {
			Users u = personnelExpInforManager.getUserByid(Long
					.parseLong(userid));
			departmentid = u.getOrgId().toString();
		} else {
			userid = "";
			departmentid = this.getRequest().getParameter("orgId");
		}

		// 用户是否管理员：1、管理员;0、非管理员
		// String type = this.getRequest().getParameter("type").trim();

		return toExtInfoPage(userid, Long.parseLong(departmentid), INPUT);

	}

	/**
	 * 
	 * @title: toExtInfoPage
	 * @description: 初始化 用户扩展信息页面
	 * @author gxh
	 * @param userId
	 * @param type
	 * @return
	 * @date 2013-4-16 下午03:55:57
	 * @throws
	 */
	public String toExtInfoPage(String userId, Long departmentid, String type) {

		if (userId != null && !userId.equals("")) {
			this.getRequest().setAttribute("curUserId", userId);
			Map map = personnelExpInforManager.getperManagerByid(userId);

			if (map != null) {
				System.out.print(map);
				this.getRequest().setAttribute("map", map);
				if (map.get("UEP_PHOTO") != null
						&& !map.get("UEP_PHOTO").equals("")) {

					String imgSrc = showPic(map.get("UEP_PHOTO").toString());
					this.getRequest().setAttribute("imgSrc", imgSrc);
				}
			}
		}

		this.getRequest().setAttribute("departmentid", departmentid);
		List<EmOrgRes> EmOrgResList1 = personnelExpInforManager
				.getEmOrgResByid(departmentid, EmOrgRes.EOR_TYPE_ROOM);
		List<EmOrgRes> EmOrgResList3 = personnelExpInforManager
				.getEmOrgResByid(departmentid, EmOrgRes.EOR_TYPE_PHONE);//
		List<EmOrgRes> EmOrgResList4 = personnelExpInforManager
				.getEmOrgResByid(departmentid, EmOrgRes.EOR_TYPE_CARD);
		List<EmOrgRes> EmOrgResList5 = personnelExpInforManager
				.getEmOrgResByid(departmentid, EmOrgRes.EOR_TYPE_TEL_NO);

		this.getRequest().setAttribute("EmOrgResList1", EmOrgResList1);// 房间
		this.getRequest().setAttribute("EmOrgResList3", EmOrgResList3);// 电话
		this.getRequest().setAttribute("EmOrgResList4", EmOrgResList4);// 车牌
		this.getRequest().setAttribute("EmOrgResList5", EmOrgResList5);// 车牌

		return type;
	}

	public EmOrgRes getEmOrgRes() {
		return emOrgRes;
	}

	public void setEmOrgRes(EmOrgRes emOrgRes) {
		this.emOrgRes = emOrgRes;
	}

	/**
	 * 保存人员信息
	 * 
	 * @title: savePersonnelExinfo
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-4-2 下午11:02:32
	 * @throws
	 */
	@SuppressWarnings("all")
	public String savePersonnelExinfo() {
		Users user1 = new Users();
		NjhwUsersExp njhwUsersExp1;
		String rawPass = "123456";

		try {

			if (null != this.getPersonnerModel().getUserid()) {

				user1 = personnelExpInforManager.getUserByid(this
						.getPersonnerModel().getUserid());

			} else {
				user1.setOrgId(this.getPersonnerModel().getOrgId());
				user1.setChangeLoginPwdFlag(Long.parseLong("0"));
				user1.setIsSystem(Long.parseLong("0"));
				user1.setLoginPwd(DigestUtils.md5Hex(rawPass).substring(0, 20)
						.toUpperCase());

			}
			// if(null!=this.getPersonnerModel().getPwd()&&!this.getPersonnerModel().getPwd().trim().equals("")){
			// user1.setLoginPwd(DigestUtils.md5Hex(rawPass).substring(0,
			// 20).toUpperCase());
			// }

			user1.setDisplayName(this.getPersonnerModel().getDisplayName()
					.trim());
			user1.setLoginUid(this.getPersonnerModel().getLoginUid().trim());
			personnelExpInforManager.saveEntit1(user1);

			njhwUsersExp1 = personnelExpInforManager.getpsByid(user1
					.getUserid());
			if (njhwUsersExp1 == null) {
				njhwUsersExp1 = new NjhwUsersExp();
				njhwUsersExp1.setUserid(user1.getUserid());
			}

			// 修改扩展信息
			njhwUsersExp1.setUepSex(this.getPersonnerModel().getUepSex());
			njhwUsersExp1.setResidentNo(this.getPersonnerModel()
					.getResidentNo().trim());
			njhwUsersExp1.setUepMobile(this.getPersonnerModel().getUepMobile()
					.trim());
			njhwUsersExp1.setUepMail(this.getPersonnerModel().getUepMail()
					.trim());
			njhwUsersExp1.setPlateNum(this.getPersonnerModel().getPlateNum()
					.trim());
			njhwUsersExp1.setRoomId(this.getPersonnerModel().getRoomId());
			njhwUsersExp1.setRoomInfo(this.getPersonnerModel().getRoomInfo()
					.trim());
			njhwUsersExp1.setTelId(this.getPersonnerModel().getTelId());
			njhwUsersExp1
					.setTelMac(this.getPersonnerModel().getTelMac().trim());
			njhwUsersExp1
					.setTelNum(this.getPersonnerModel().getTelNum().trim());
			njhwUsersExp1
					.setUepFax(this.getPersonnerModel().getUepFax().trim());
			njhwUsersExp1.setUepHobby(this.getPersonnerModel().getUepHobby()
					.trim());
			njhwUsersExp1.setUepSkill(this.getPersonnerModel().getUepSkill()
					.trim());
			njhwUsersExp1.setUepFlag(NjhwUsersExp.UEP_FLAG_OFFLINE);
			njhwUsersExp1.setUepBak1(this.getPersonnerModel().getUepBak1()
					.trim());// 市民卡号

			njhwUsersExp1.setTmpCard(this.getPersonnerModel().getTmpCard()
					.trim());// 临时卡号
			njhwUsersExp1.setFeeType(this.getPersonnerModel().getFeeType());// 停车场收费

			if (this.getPersonnerModel().getUepBak1() != null
					&& !this.getPersonnerModel().getUepBak1().equals("")) {
				boolean res = this.personnelExpInforManager
						.exsitNjhwTscardByCardId(this.getPersonnerModel()
								.getUepBak1());
				System.out.print("************************" + res);
				if (!res) {// 不存在市民卡信息

					NjhwTscard nv = personnelExpInforManager
							.NjhwTscardByUserid(this.getPersonnerModel()
									.getUserid());
					if (nv != null) {
						Map condition = new HashMap();
						condition.put("userId", this.getPersonnerModel()
								.getUserid());
						condition
								.put("cardFlag", NjhwTscard.CARD_STATUS_UNUSED);
						personnelExpInforManager.updateBySql(
								"PersonnelSQL.updCardSetCardFlag", condition);// 改为无效
					}

					NjhwTscard card = null;
					try {
						// card =
						// personCardQueryToAppService.queryPersonCard(njhwUsersExp.getUepBak1());
						// personnelExpInforManager.saveNjhwTscard(card);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				personnelExpInforManager.saveEntit1(njhwUsersExp1);
				// 调用信息接口
				setIsSuc("true");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return toExtInfoPage(String.valueOf(user1.getUserid()), this
				.getPersonnerModel().getOrgId(), SUCCESS);
	}

	/*
	 * public String savePersonnelExinfo() {
	 * 
	 * try { NjhwUsersExp njhwUsersExp1 = new NjhwUsersExp();
	 * if(njhwUsersExp.getUepId()!=null){ njhwUsersExp1 =
	 * personnelExpInforManager.getpsByid(njhwUsersExp.getUserid());
	 * 
	 * }else { njhwUsersExp1.setUserid(njhwUsersExp.getUserid()); }
	 * njhwUsersExp1.setUepSex(njhwUsersExp.getUepSex());
	 * njhwUsersExp1.setResidentNo(njhwUsersExp.getResidentNo().trim());
	 * 
	 * njhwUsersExp1.setUepMobile(njhwUsersExp.getUepMobile().trim());
	 * njhwUsersExp1.setUepMail(njhwUsersExp.getUepMail().trim());
	 * njhwUsersExp1.setPlateNum(njhwUsersExp.getPlateNum());
	 * njhwUsersExp1.setRoomId(njhwUsersExp.getRoomId());
	 * njhwUsersExp1.setRoomInfo(njhwUsersExp.getRoomInfo());
	 * njhwUsersExp1.setTelId(njhwUsersExp.getTelId());
	 * njhwUsersExp1.setTelMac(njhwUsersExp.getTelMac());
	 * njhwUsersExp1.setTelNum(njhwUsersExp.getTelNum());
	 * 
	 * 
	 * //njhwUsersExp1.setStationName(njhwUsersExp.getStationName().trim());
	 * 
	 * njhwUsersExp1.setUepFax(njhwUsersExp.getUepFax().trim());
	 * //njhwUsersExp1.setJobId(njhwUsersExp.getJobId());
	 * njhwUsersExp1.setUepHobby(njhwUsersExp.getUepHobby().trim()); //
	 * njhwUsersExp1.setUepType(njhwUsersExp.getUepType().trim());
	 * njhwUsersExp1.setUepSkill(njhwUsersExp.getUepSkill().trim());
	 * njhwUsersExp1.setUepFlag(NjhwUsersExp.UEP_FLAG_OFFLINE);
	 * njhwUsersExp1.setUepBak1(njhwUsersExp.getUepBak1().trim());//市民卡号
	 * 
	 * njhwUsersExp1.setTmpCard(njhwUsersExp.getTmpCard().trim());//临时卡号
	 * njhwUsersExp1.setFeeType(njhwUsersExp.getFeeType().trim());//停车场收费
	 * 
	 * herb 添加市民卡到市民卡表
	 * 
	 * if (njhwUsersExp1.getUepBak1()!=
	 * null&&!njhwUsersExp1.getUepBak1().equals("")) { boolean res =
	 * this.personnelExpInforManager.exsitNjhwTscardByCardId(njhwUsersExp1.getUepBak1());
	 * System.out.print("************************"+res); if (!res) {//不存在市民卡信息
	 * 
	 * NjhwTscard nv=
	 * personnelExpInforManager.NjhwTscardByUserid(njhwUsersExp1.getUserid());
	 * if(nv!=null){ Map condition = new HashMap(); condition.put("userId",
	 * njhwUsersExp1.getUserid()); condition.put("cardFlag",
	 * NjhwTscard.CARD_FLAG_UNUSED);
	 * personnelExpInforManager.updateBySql("PersonnelSQL.updCardSetCardFlag",
	 * condition);//改为无效 }
	 * 
	 * NjhwTscard card = null; try { // card =
	 * personCardQueryToAppService.queryPersonCard(njhwUsersExp.getUepBak1()); //
	 * personnelExpInforManager.saveNjhwTscard(card); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 * 
	 * personnelExpInforManager.saveEntity(njhwUsersExp1); //调用信息接口
	 * setIsSuc("true"); } } catch (Exception e) { setIsSuc("false");
	 * e.printStackTrace(); } //return
	 * toExtInfoPage(String.valueOf(njhwUsersExp.getUserid()),SUCCESS); return
	 * null; }
	 */
	/**
	 * 挂失实名卡
	 * 
	 * @title: lossCityCardId
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-4-8 下午02:47:50
	 * @throws
	 */
	public String lossCityCardId() {
		JSONObject json = new JSONObject();
		try {
			String cityCardId = Struts2Util.getParameter("cityCardId");
			String roomid = Struts2Util.getParameter("roomid");
			NjhwTscard ncard = new NjhwTscard();
			ncard = personnelExpInforManager.njhwTscardByCardId(cityCardId);
			if (ncard != null) {
				ncard.setSystemLosted(NjhwTscard.SYSTEM_LOSTED_LOSTED);
				// devicePermissionToAppService.cancelAuthDevice(cityCardId,roomid);
				personnelExpInforManager.saveNjhwTscard(ncard);

				json.put("status", 0);
			} else {
				json.put("status", 1);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}

	/**
	 * 验证市民卡
	 * 
	 * @title: checkCityCar
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-4-11 下午11:32:40
	 * @throws
	 */
	public String checkCityCar() {
		String cardId = Struts2Util.getParameter("nvrCard");
		try {
			JSONObject json = new JSONObject();
			// 调用市民卡接口

			NjhwTscard nt = null;// personCardQueryToAppService.queryPersonCard(cardId);
			// NjhwTscard nt=
			// personnelExpInforManager.njhwTscardByCardId(cardId);
			if (nt == null) {
				json.put("state", "error");
			} else {
				json.put("state", "success");
			}
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
					"no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws JSONException
	 *             保存图片
	 * @throws IOException
	 * @title: saveImg
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-4-16 上午12:01:17
	 * @throws
	 */
	public String saveImg() throws IOException, JSONException {
		JSONObject json = new JSONObject();
		FileOutputStream fos = null;
		FileInputStream fis = null;

		// String orgId = Struts2Util.getParameter("orgId");
		// String uepId = Struts2Util.getParameter("uepId");
		// String userid = Struts2Util.getParameter("userid");
		// String displayName = Struts2Util.getParameter("displayName");
		// String residentNo = Struts2Util.getParameter("residentNo");

		String time = DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss");
		String times = time.replaceAll(":", "-").trim();

		try {
			String name = times + this.getFileFileName();
			File uepPhoto = this.getFile();
			fis = new FileInputStream(uepPhoto);
			String rootpath = PropertiesUtil.getAnyConfigProperty(
					"dir.user.photo", PropertiesUtil.NJHW_CONFIG);
			File rootFile = new File(rootpath);
			if (!rootFile.exists())
				rootFile.mkdirs();
			fos = new FileOutputStream(rootpath + "/" + name);

			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}

			/* 上传成功的时候，保存或修改照片url */
			// if(StringUtil.isEmpty(userid)){
			// if(StringUtil.isEmpty(displayName) ||
			// StringUtil.isEmpty(residentNo)){
			// Users u = new Users();
			// u.setDisplayName(displayName);
			// u.setOrgId(Long.parseLong(orgId));
			// u.setChangeLoginPwdFlag(Long.parseLong("0"));
			// u.setIsSystem(Long.parseLong("0"));
			//					
			// personnelExpInforManager.saveEntity1(u);
			// userid = u.getUserid().toString();
			// json.put("userid", u.getUserid());
			// } else {
			// json.put("error", "error");
			// }
			// }
			// if(StringUtil.isNotEmpty(userid)) {
			// if(StringUtil.isEmpty(uepId)) {
			// NjhwUsersExp njhwUsersExp1 = new NjhwUsersExp();
			// njhwUsersExp1.setUserid(Long.parseLong(userid));
			// njhwUsersExp1.setUepPhoto(rootpath + "/" + name);
			// personnelExpInforManager.saveEntity1(njhwUsersExp1);
			// json.put("uepId", njhwUsersExp1.getUepId());
			// json.put("userid", userid);
			// } else {
			// njhwUsersExp =
			// personnelExpInforManager.getpsByid(Long.parseLong(userid));
			// njhwUsersExp.setUepPhoto(rootpath + "/" + name);
			// personnelExpInforManager.saveEntity(njhwUsersExp);
			// json.put("uepId", uepId);
			// json.put("userid", userid);
			// }
			String imgSrc = showPic(rootpath + "/" + name);
			json.put("imgSrc", imgSrc);
			json.put("picSrc", rootpath + "/" + name);
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(fos, fis);
		}
		Struts2Util.render("text/html", json.toString(), "no-cache:true");
		return null;
	}

	/**
	 * 读取照片
	 * 
	 * @title: showPic
	 * @description: TODO
	 * @author gxh
	 * @param userPhoto
	 * @return
	 * @date 2013-4-17 上午11:07:05
	 * @throws
	 */
	private String showPic(String userPhoto) {
		String contents = "";
		try {
			File f = new File(userPhoto);
			if (f.exists()) {
				BufferedInputStream in = new BufferedInputStream(
						new FileInputStream(userPhoto));
				ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
				byte[] temp = new byte[1024 * 1024];
				int size = 0;
				while ((size = in.read(temp)) != -1) {
					out.write(temp, 0, size);
				}
				in.close();
				byte[] content = out.toByteArray();
				contents = EncodeUtil.base64Encode(content);
				contents = "data:image/x-icon;base64," + contents;
			} else {
				System.out.println("文件不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contents;
	}

	public String intoperOrgQuery() {
		String OrgId = this.getRequest().getParameter("orgId");
		this.getRequest().setAttribute("orgId", OrgId);
		return SUCCESS;
	}

	/**
	 * 查询某部门下所有的子部门
	 * 
	 * @title: getAllOrgs
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-5-5 下午01:47:23
	 * @throws
	 */
	@SuppressWarnings("all")
	public String getSonOrgs() {
		String OrgId = this.getRequest().getParameter("orgId");
		try {
			this.page = personnelExpInforManager.getSonOrgs(page, OrgId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;

	}

	public String getAllPerByOrg() {

		String orgId = this.getRequest().getParameter("orgId");
		try {
			this.page = personnelExpInforManager.getAllPerByOrg(page, orgId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 修改组织机构
	 * 
	 * @title: modOrgByid
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-5-6 下午08:57:00
	 * @throws
	 */
	public String modOrgByid() {
		String orgId = this.getRequest().getParameter("orgId");
		try {
			org = personnelExpInforManager.modOrgByid(Long.parseLong(orgId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;

	}

	/**
	 * 保存修改组织名称
	 * 
	 * @title: saveModOrg
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-5-7 下午02:32:59
	 * @throws
	 */
	public String saveModOrg() {
		try {
			Org o = new Org();

			o = personnelExpInforManager.updateOrg(org.getOrgId());
			o.setName(org.getName().trim());
			personnelExpInforManager.saveEntit1(o);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/**
	 * 重置密码
	 * 
	 * @title: resetPwd
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-5-9 下午02:48:11
	 * @throws
	 */
	public String resetPwd() {
		JSONObject json = new JSONObject();
		String rawPass = "123456";
		try {
			String configPwd = PropertiesUtil.getAnyConfigProperty("user.default.pwd",PropertiesUtil.NJHW_CONFIG);
			rawPass = (null==configPwd || configPwd.trim().equals(""))?"":configPwd;
			String userid = Struts2Util.getParameter("userid");
			Users u = new Users();
			u = personnelExpInforManager.getUserByid(Long.parseLong(userid));
			if (u != null) {
				u.setLoginPwd(DigestUtils.md5Hex(rawPass).substring(0, 20)
						.toUpperCase());
				personnelExpInforManager.saveEntit1(u);
				json.put("status", 1);
			} else {
				json.put("status", 0);
			}
           new LDAPService().resetPasswordByLoginUid(u.getLoginUid(),configPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;

	}

	/**
	 * 设置管理员
	 * 
	 * @title: isSystem
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-5-9 下午03:10:36
	 * @throws
	 */
	public String isSystem() {
		JSONObject json = new JSONObject();

		try {
			String userid = Struts2Util.getParameter("userid");
			String pwd = Struts2Util.getParameter("pwd");
			Users u = new Users();
			u = personnelExpInforManager.getUserByid(Long.parseLong(userid));
			if (u != null) {
				u.setIsSystem(Long.parseLong("1"));
				personnelExpInforManager.saveEntit1(u);
				json.put("status", 0);
			} else {
				json.put("status", 1);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;

	}

	public PersonCardQueryToAppService getPersonCardQueryToAppService() {
		return personCardQueryToAppService;
	}

	public void setPersonCardQueryToAppService(
			PersonCardQueryToAppService personCardQueryToAppService) {
		this.personCardQueryToAppService = personCardQueryToAppService;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileFileContentType() {
		return fileFileContentType;
	}

	public void setFileFileContentType(String fileFileContentType) {
		this.fileFileContentType = fileFileContentType;
	}

	public DevicePermissionToAppService getDevicePermissionToAppService() {
		return devicePermissionToAppService;
	}

	public void setDevicePermissionToAppService(
			DevicePermissionToAppService devicePermissionToAppService) {
		this.devicePermissionToAppService = devicePermissionToAppService;
	}

	public NjhwUsersExp getNjhwUsersExp() {
		return njhwUsersExp;
	}

	public void setNjhwUsersExp(NjhwUsersExp njhwUsersExp) {
		this.njhwUsersExp = njhwUsersExp;
	}

	public PersonnelExpInforManager getPersonnelExpInforManager() {
		return personnelExpInforManager;
	}

	public void setPersonnelExpInforManager(
			PersonnelExpInforManager personnelExpInforManager) {
		this.personnelExpInforManager = personnelExpInforManager;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public PersonnerModel getPersonnerModel() {
		return personnerModel;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public void setPersonnerModel(PersonnerModel personnerModel) {
		this.personnerModel = personnerModel;
	}

	private void close(FileOutputStream fos, FileInputStream fis) {
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				System.out.println("FileInputStream关闭失败");
				e.printStackTrace();
			}
		}
		if (fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
				System.out.println("FileOutputStream关闭失败");
				e.printStackTrace();
			}
		}
	}

}
