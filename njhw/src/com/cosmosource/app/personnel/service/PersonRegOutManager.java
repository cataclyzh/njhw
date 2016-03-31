package com.cosmosource.app.personnel.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.hssf.record.formula.functions.Or;
import org.springframework.jdbc.support.xml.SqlXmlValue;

import com.cosmosource.app.common.service.CommonManager;
import com.cosmosource.app.entity.AttendanceApprovers;
import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.LeaderLevel;
import com.cosmosource.app.entity.NjhwDoorcontrolExp;
import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.NjhwUsersPlatenum;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.OrgAttendanceAdmin;
import com.cosmosource.app.entity.TcIpTel;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.fax.model.NjhwTscardPK;
import com.cosmosource.app.ldap.model.UserInfo;
import com.cosmosource.app.ldap.service.LDAPService;
import com.cosmosource.app.message.vo.User;
import com.cosmosource.app.personnel.model.PersonModel;
import com.cosmosource.app.port.serviceimpl.DevicePermissionToAppService;
import com.cosmosource.app.port.serviceimpl.DoorControlToAppService;
import com.cosmosource.app.transfer.serviceimpl.AccessAndGateService;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.NumberUtil;
import com.cosmosource.base.util.PropertiesUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;

@SuppressWarnings("unchecked")
public class PersonRegOutManager extends BaseManager {

	private DevicePermissionToAppService devicePermissionToApp;
	private CommonManager commonManager;
	private DoorControlToAppService doorControlToAppService;
	private AccessMgrManager accessMgrManager;
	private UnitAdminManager unitAdminManager;

	/**
	 * unitAdminManager
	 * 
	 * @return the unitAdminManager
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public UnitAdminManager getUnitAdminManager() {
		return unitAdminManager;
	}

	/**
	 * @param unitAdminManager
	 *            the unitAdminManager to set
	 */

	public void setUnitAdminManager(UnitAdminManager unitAdminManager) {
		this.unitAdminManager = unitAdminManager;
	}

	public CommonManager getCommonManager() {
		return commonManager;
	}

	public void setCommonManager(CommonManager commonManager) {
		this.commonManager = commonManager;
	}

	/**
	 * 取得当前登陆用户所属的顶级单位
	 */
	public List<HashMap> getTopOrgId() {
		Map pMap = new HashMap();
		pMap.put("userid",
				Struts2Util.getSession().getAttribute(Constants.USER_ID)
						.toString());
		List<HashMap> list = this.findListBySql(
				"PersonnelSQL.getTopOrgIdByUserId", pMap);
		return list;
	}

	public List<HashMap> getGongYongAccountOrgName() {
		Map pMap = new HashMap();
		pMap.put("userid",
				Struts2Util.getSession().getAttribute(Constants.USER_ID)
						.toString());
		List<HashMap> list = this.findListBySql(
				"PersonnelSQL.getGongYongAccountOrgName", pMap);
		return list;
	}

	/**
	 * 用户排序最大
	 * 
	 * @title: getMax
	 * @description: TODO
	 * @author zhangqw
	 * @param orgId
	 *            组织id
	 * @return
	 * @date 2013年7月3日17:22:20
	 * @throws
	 */
	private Users getMaxUser(Long orgId) {
		Map pMap = new HashMap();
		pMap.put("orgId", orgId);

		List<Users> list = this.findListBySql("PersonnelSQL.getMaxUser", pMap);
		if (list != null && list.size() > 0
				&& null != list.get(0).getOrderNum()) {
			return list.get(0);
		} else {
			Users users = new Users();
			users.setOrderNum(0);
			return users;
		}
	}

	public String telMac(Long telId) {
		String telMac = null;

		if (null == telId || 0l == telId)
			return telMac;

		TcIpTel tcIpTel = (TcIpTel) dao.findById(TcIpTel.class, telId);
		if (null != telMac && null != tcIpTel.getTelMac()) {
			telMac = tcIpTel.getTelMac();
		}

		return telMac;
	}
	
	/**
	 * 删除用户在obj_perm_map中的门锁权限
	 * @param userId
	 */
	public void delUserOldDoorControl(long userId){
		List<Map> lockList = sqlDao.getSqlMapClientTemplate().queryForList("PersonnelSQL.getLockIdByUserId", userId);
		
		for(Map m : lockList){
			String nodeId = m.get("NODE_ID").toString();
			logger.info("node_id["+nodeId+"]");
			Map param = new HashMap();
			param.put("userId", userId);
			param.put("nodeId", nodeId);
			sqlDao.getSqlMapClientTemplate().delete("PersonnelSQL.deleteUserLockPerm", param);
		}
	}

	/**
	 * @description:保存登记信息
	 * @author zh
	 * @param PersonModel
	 *            personModel
	 * @param NjhwTscard
	 *            jkCard
	 * @return
	 */
	@SuppressWarnings("static-access")
	public int saveRegister(PersonModel personModel, NjhwTscard jkCard,
			String carNums, String isFastenCarNums) {
		int num = 0;
		String loginId = Struts2Util.getSession()
				.getAttribute(Constants.USER_ID).toString();

		NjhwUsersExp userExp = null; // 用户扩展信息
		Users user = null; // 用户基本信息
		NjhwTscard card = null; // 市民卡信息

		// Users
		if (personModel.getUserid() != null
				&& personModel.getUserid().longValue() > 0) {
			user = (Users) dao.findById(Users.class, personModel.getUserid());
			user.setLastUpdateBy(Long.parseLong(Struts2Util.getSession()
					.getAttribute(Constants.USER_ID).toString()));
			user.setLastUpdateDate(DateUtil.date2Str(DateUtil.getSysDate(),
					"yyyy-MM-dd HH:mm:ss"));
			personModel.setLoginUidOld(user.getLoginUid());
		} else {
			user = new Users();
			// zhangqw 解决排序号不能为空 Date：2013年7月3日17:21:21
			user.setOrderNum(this.getMaxUser(personModel.getOrgId())
					.getOrderNum() + 1);
			user.setCreator(Long.parseLong(Struts2Util.getSession()
					.getAttribute(Constants.USER_ID).toString()));
			user.setCreatDate(DateUtil.date2Str(DateUtil.getSysDate(),
					"yyyy-MM-dd HH:mm:ss"));
			// 如果是系统管理员则只能新增管理员 张权威
			if ("1".equals(Struts2Util.getSession()
					.getAttribute(Constants.USER_ID).toString())) {
				user.setUType(1l);
				user.setIsSystem(1l);
				user.setPId(Long.parseLong(Struts2Util.getSession()
						.getAttribute(Constants.USER_ID).toString()));

			}
			// 否则只能新增普通用户
			else {
				user.setUType(null);
				user.setPId(null);
				user.setIsSystem(0l);
			}
			user.setActiveFlag((long) 1);
			user.setChangeLoginPwdFlag((long) 1);
			user.setLoginPwd(DigestUtils
					.md5Hex(PropertiesUtil.getAnyConfigProperty(
							"user.default.pwd", PropertiesUtil.NJHW_CONFIG))
					.substring(0, 20).toUpperCase());
			user.setLoginPwdMd5(DigestUtils.md5Hex(
					PropertiesUtil.getAnyConfigProperty("user.default.pwd",
							PropertiesUtil.NJHW_CONFIG)).toUpperCase());

		}
		// if (personModel.getIsSystem()!= null && personModel.getIsSystem() ==
		// 1) {
		// user.setUType(1l);
		// user.setPId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		// user.setIsSystem(1l);
		// } else {
		// user.setUType(null);
		// user.setPId(null);
		// user.setIsSystem(0l);
		// }
		user.setOrgId(personModel.getOrgId());
		user.setLoginUid(personModel.getLoginUid());
		user.setUCode(personModel.getUcode());
		user.setDisplayName(personModel.getName());

		if (personModel.getUserid() != null && personModel.getUserid() > 0) {
			dao.update(user);
		} else {
			dao.save(user);
			if ("1".equals(Struts2Util.getSession()
					.getAttribute(Constants.USER_ID).toString())) {
				Map parMap = new HashMap();
				List list = new ArrayList();
				parMap.put("orgId",
						getRootOrgId(personModel.getOrgId().toString()).get(0)
								.get("ROOTORGID"));
				parMap.put("creator",
						Struts2Util.getSession()
								.getAttribute(Constants.USER_ID).toString());
				parMap.put("createDate",
						DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss"));
				parMap.put("userid", user.getUserid());
				list.add(parMap);
				sqlDao.batchInsert("PersonnelSQL.insertAOM", list);

				List listUser = new ArrayList();
				Map param = new HashMap();
				param.put("userid", user.getUserid());
				param.put("nodeid", getNodeid());
				param.put("type", "user");
				listUser.add(param);
				sqlDao.batchDelete("PersonnelSQL.insertAdminUserRoot", listUser);
			}
		}
		List<Map> listRoom = null;
		// NjhwUsersExp
		long userid = personModel.getUserid() != null ? personModel.getUserid()
				.longValue() : (long) 0;
		List list = dao
				.findByHQL(
						"select t from NjhwUsersExp t, Users u where u.userid = t.userid and u.userid = ?",
						userid);
		boolean isChange = false;
		if (list.size() > 0 && list != null) {
			userExp = (NjhwUsersExp) list.get(0);
			userExp.setModifyDate(DateUtil.getSysDate());
			userExp.setModifyId(Long.parseLong(Struts2Util.getSession()
					.getAttribute(Constants.USER_ID).toString()));
			HashMap<String, String> pMap = new HashMap<String, String>();
			pMap.put("cardId", personModel.getCityCardold());
			if (null == personModel.getRoomId() && null != userExp.getRoomId()) {
				isChange = (null == personModel.getRoomId())
						&& (null != userExp.getRoomId());
			} else if (null != userExp.getRoomId()) {
				isChange = !personModel.getRoomId().equals(userExp.getRoomId());
			} else if (null != personModel.getRoomId()
					&& null == userExp.getRoomId()) {
				isChange = true;
			}

			// 房间号变动
			if (isChange) {

				if (null != userExp.getRoomId()) {
					doorControlToAppService.delDoorAuth(user.getUserid()
							.toString(), loginId, userExp.getRoomId()
							.toString(), null, true);
				}

			}

		} else {
			userExp = new NjhwUsersExp();
			userExp.setInsertDate(DateUtil.getSysDate());
			userExp.setInsertId(Long.parseLong(loginId));
		}
		if (jkCard != null && "1".equals(personModel.getCityCardType())) {
			userExp.setCardUid(personModel.getCardUID());
		}
		userExp.setUserid(user.getUserid());
		userExp.setUepSex(personModel.getSex());
		userExp.setUepMail(personModel.getEmail());

		userExp.setUepFax(personModel.getFax());
		userExp.setWebFax(personModel.getFax_web());

		userExp.setUepMobile(personModel.getPhone());
		userExp.setResidentNo(personModel.getResidentNo());
		userExp.setUepType(personModel.getIsTempUser());
		userExp.setUepPhoto(personModel.getUserPhoto());
		userExp.setRoomId(personModel.getRoomId());
		userExp.setRoomInfo(personModel.getRoomNum());
		// userExp.setTelId(personModel.getIpTelId());
		// userExp.setTelMac(telMac(personModel.getIpTelId()));
		userExp.setTelNum(personModel.getIpTelId() == null ? null : personModel
				.getIpTelId().toString());
		if (StringUtil.isBlank(personModel.getTmpCardStartDate())) {
			userExp.setUepBak2(DateUtil.date2Str(DateUtil.getSysDate(),
					"yyyy-MM-dd"));
		}
		userExp.setUepBak3(personModel.getDateFrom());
		userExp.setUepBak4(personModel.getDateTo());

		// userExp.setPlateNum(personModel.getCarNum());
		userExp.setCardType(personModel.getCityCardType());
		if ("2".equals(personModel.getCityCardType())) // 当选择的是临时卡时，将临时卡号保存到扩展字段TmpCard中
			userExp.setTmpCard(personModel.getCityCard());
		else
			userExp.setTmpCard(null);

		if (list.size() > 0 && list != null) {
			dao.update(userExp);
		} else {
			dao.save(userExp);
		}
		Map<String, Long> pMap = new HashMap<String, Long>();
		pMap.put("orgId", personModel.getOrgId());

		List<Map> listOrg = this.findListBySql("PersonnelSQL.getRootOrgId",
				pMap);
		if ((null == personModel.getIsTempUser() || !personModel
				.getIsTempUser().equals("04"))) {
			// 超级管理员新增管理员 不指定考勤审批员和人员类型
			if (!"1".equals(Struts2Util.getSession()
					.getAttribute(Constants.USER_ID).toString())) { 
				LeaderLevel ll = new LeaderLevel();
				ll.setUserid(user.getUserid());
				ll.setStage(personModel.getStage());
				dao.saveOrUpdate(ll);
				// 新增user时 设置默认的考勤审批人员
				if (null == personModel.getUserid()) {
					// 普通员工 0
					if (0l == personModel.getStage()) {
						StringBuffer stids = new StringBuffer();
						List<Long> appIds = dao
								.findByHQL(
										"select us.userid from Users us, LeaderLevel t  "
												+ "where  us.orgId = (select orgId from Users uu where uu.userid = ?) "
												+ "and    us.userid = t.userid and t.stage  = 2 and us.userid != ?",
										user.getUserid(), user.getUserid());

						if (null != appIds && appIds.size() > 0) {
							for (long appId : appIds) {
								if (appId != user.getUserid().longValue()) {
									stids.append(appId).append(",");
								}

							}
							AttendanceApprovers aa = new AttendanceApprovers(
									user.getUserid(), stids.substring(0,
											stids.length() - 1));
							dao.saveOrUpdate(aa);
						}
					}
					// 部门领导 2
					else if (2l == personModel.getStage()) {
						StringBuffer stids = new StringBuffer();
						List<Long> appIds = dao
								.findByHQL(
										"select us.userid from Users us, LeaderLevel t "
												+ " where  us.orgId in (select orgId from Org where  PId= ? )"
												+ " and    us.userid = t.userid and t.stage  = 1 and us.userid != ?",
										Long.valueOf(listOrg.get(0)
												.get("ROOTORGID").toString()),
										user.getUserid());

						if (null != appIds && appIds.size() > 0) {
							for (long appId : appIds) {
								if (appId != user.getUserid().longValue()) {
									stids.append(appId).append(",");
								}
							}
							AttendanceApprovers aa = new AttendanceApprovers(
									user.getUserid(), stids.substring(0,
											stids.length() - 1));
							dao.saveOrUpdate(aa);
						}

					}
				}
				// 考勤管理员
				if (1 == personModel.getIsAttendanceAdmin()) {
					OrgAttendanceAdmin orga = new OrgAttendanceAdmin(
							Long.valueOf(listOrg.get(0).get("ROOTORGID")
									.toString()), user.getUserid());
					dao.saveOrUpdate(orga);
				} else {
					if (null != dao.findById(
							OrgAttendanceAdmin.class,
							Long.valueOf(listOrg.get(0).get("ROOTORGID")
									.toString()))) {
						dao.deleteById(
								OrgAttendanceAdmin.class,
								Long.valueOf(listOrg.get(0).get("ROOTORGID")
										.toString()));
					}
				}

			}

		} else {
			if (null != dao.findById(LeaderLevel.class, user.getUserid())) {
				dao.deleteById(LeaderLevel.class, user.getUserid());
			}
			if (null != dao.findById(AttendanceApprovers.class,
					user.getUserid())) {
				dao.deleteById(AttendanceApprovers.class, user.getUserid());
			}
			if (null != dao.findById(OrgAttendanceAdmin.class,
					Long.valueOf(listOrg.get(0).get("ROOTORGID").toString()))) {
				dao.deleteById(OrgAttendanceAdmin.class, Long.valueOf(listOrg
						.get(0).get("ROOTORGID").toString()));
			}
		}

		// NjhwUsersPlatenum
		// 之前的全部删掉, 有新的车牌号，就以新的为准，
		dao.batchExecute("delete from NjhwUsersPlatenum t where t.userid = ?",
				user.getUserid());
		if (StringUtil.isNotEmpty(carNums)) {
			String[] carNumList = carNums.split(",");
			String[] isFastens = isFastenCarNums.split(",");
			for (int i = 0; i <= carNumList.length - 1; i++) {
				NjhwUsersPlatenum plateNum = new NjhwUsersPlatenum();

				plateNum.setNupPn(carNumList[i].toString());
				plateNum.setUserid(user.getUserid());
				plateNum.setNupFlag(isFastens[i]);
				plateNum.setInsertDate(DateUtil.getSysDate());
				plateNum.setInsertId(Long.parseLong(loginId));
				dao.save(plateNum);
			}
		}

		// 市民卡
		if (jkCard != null && "1".equals(personModel.getCityCardType())) { // 1.市民卡2.临时卡
			// 删除用户的其他市民卡信息
			List<NjhwTscard> delCardList = dao
					.findByHQL(
							"select t from NjhwTscard t where t.userId = ? and t.cardId != ?",
							user.getUserid(), personModel.getCityCard());
			for (NjhwTscard njhwTscard : delCardList) {
				dao.delete(njhwTscard);
			}

			// cardExp1 一个卡对应多个部门 存放orgid
			List cardList = dao
					.findByHQL(
							"select t from NjhwTscard t where t.cardId = ? and t.cardExp1 = ?",
							personModel.getCityCard(),
							listOrg.get(0).get("ROOTORGID").toString());
			if (cardList.size() > 0 && cardList != null) {
				card = (NjhwTscard) cardList.get(0);
				card.setModifyDate(DateUtil.getSysDate());
				card.setModifyId(Long.parseLong(Struts2Util.getSession()
						.getAttribute(Constants.USER_ID).toString()));
			} else {
				card = new NjhwTscard();
				// 查询跟单位的id

				card.setInsertDate(DateUtil.getSysDate());
				card.setInsertId(Long.parseLong(Struts2Util.getSession()
						.getAttribute(Constants.USER_ID).toString()));
				// 一个card可以对应多个部门用户
				if (null != listOrg && listOrg.size() > 0) {
					card.setCardExp1(String.valueOf(listOrg.get(0).get(
							"ROOTORGID")));
				} else {
					commonManager.log.error("*****用户所属部门为空!*******");
				}
			}

			card.setCardId(jkCard.getCardId()); // 市民卡面号
			card.setSocitype(jkCard.getSocitype()); // 证件类型
			card.setResidentNo(jkCard.getResidentNo()); // 证件号码
			card.setCardType(jkCard.getCardType()); // 市民卡类型
			card.setCustcode(jkCard.getCustcode()); // 市民卡内号
			card.setUserName(jkCard.getUserName()); // 用户姓名
			card.setCustsex(jkCard.getCustsex()); // 性别
			card.setDomaddr(jkCard.getDomaddr()); // 户籍地址
			card.setComaddr(jkCard.getComaddr()); // 居住地址
			card.setMoblie(jkCard.getMoblie()); // 手机号码
			card.setPuicstatus(jkCard.getPuicstatus()); // 通卡状态
			card.setCardstatus(jkCard.getCardstatus()); // 卡状态
			card.setCardLosted(jkCard.getCardLosted()); // 挂失状态
			card.setUserDiff("1"); // 用户类型为：内部人员
			card.setUserId(user.getUserid());
			card.setCardUid(personModel.getCardUID());
			card.setCustcode(personModel.getCardUID());
			if (cardList.size() > 0 && cardList != null)
				dao.update(card);
			else
				dao.save(card);
		} else {
			// 删除用户的其他市民卡信息
			List<NjhwTscard> delCardList = dao
					.findByHQL(
							"select t from NjhwTscard t where t.userId = ? and t.cardId = ?",
							user.getUserid(), personModel.getCityCard());
			for (NjhwTscard njhwTscard : delCardList) {
				dao.delete(njhwTscard);
			}
		}

		dao.flush();

		// 如果是修改
		if (list.size() > 0 && list != null) {
			// 房间号变动
			if (isChange && null != personModel.getRoomId()) {
				if (null != personModel.getRoomId()) {
					doorControlToAppService.addDoorAuth(
							String.valueOf(user.getUserid()), loginId,
							personModel.getRoomId().toString(), null);
				}

			}
		}
		// 如果是新增
		else {
			if (null != personModel.getRoomId()) {
				// 授予权限
				doorControlToAppService.addDoorAuth(String.valueOf(user
						.getUserid()), loginId, personModel.getRoomId()
						.toString(), null);
			}

		}
		return num;
	}

	/**
	 * @description:保存登记信息
	 * @author zh
	 * @param PersonModel
	 *            personModel
	 * @param NjhwTscard
	 *            jkCard
	 * @return
	 */
	@SuppressWarnings("static-access")
	public int saveRegisterE(PersonModel personModel, NjhwTscard jkCard,
			String carNums, String isFastenCarNums) {
		int num = 0;
		String loginId = Struts2Util.getSession()
				.getAttribute(Constants.USER_ID).toString();

		NjhwUsersExp userExp = null; // 用户扩展信息
		Users user = null; // 用户基本信息
		NjhwTscard card = null; // 市民卡信息

		// Users
		if (personModel.getUserid() != null
				&& personModel.getUserid().longValue() > 0) {
			user = (Users) dao.findById(Users.class, personModel.getUserid());
			user.setLastUpdateBy(Long.parseLong(Struts2Util.getSession()
					.getAttribute(Constants.USER_ID).toString()));
			user.setLastUpdateDate(DateUtil.date2Str(DateUtil.getSysDate(),
					"yyyy-MM-dd HH:mm:ss"));
			personModel.setLoginUidOld(user.getLoginUid());
		} else {
			user = new Users();
			// zhangqw 解决排序号不能为空 Date：2013年7月3日17:21:21
			user.setOrderNum(this.getMaxUser(personModel.getOrgId())
					.getOrderNum() + 1);
			user.setCreator(Long.parseLong(Struts2Util.getSession()
					.getAttribute(Constants.USER_ID).toString()));
			user.setCreatDate(DateUtil.date2Str(DateUtil.getSysDate(),
					"yyyy-MM-dd HH:mm:ss"));
			// 如果是系统管理员则只能新增管理员 张权威
			if ("1".equals(Struts2Util.getSession()
					.getAttribute(Constants.USER_ID).toString())) {
				user.setUType(1l);
				user.setIsSystem(1l);
				user.setPId(Long.parseLong(Struts2Util.getSession()
						.getAttribute(Constants.ORG_ID).toString()));

			}
			// 否则只能新增普通用户
			else {
				user.setUType(null);
				user.setPId(Long.parseLong(Struts2Util.getSession()
						.getAttribute(Constants.ORG_ID).toString()));
				user.setIsSystem(0l);
			}
			user.setActiveFlag((long) 1);
			user.setChangeLoginPwdFlag((long) 1);
			user.setLoginPwd(DigestUtils
					.md5Hex(PropertiesUtil.getAnyConfigProperty(
							"user.default.pwd", PropertiesUtil.NJHW_CONFIG))
					.substring(0, 20).toUpperCase());
			user.setLoginPwdMd5(DigestUtils.md5Hex(
					PropertiesUtil.getAnyConfigProperty("user.default.pwd",
							PropertiesUtil.NJHW_CONFIG)).toUpperCase());

		}
		// if (personModel.getIsSystem()!= null && personModel.getIsSystem() ==
		// 1) {
		// user.setUType(1l);
		// user.setPId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		// user.setIsSystem(1l);
		// } else {
		// user.setUType(null);
		// user.setPId(null);
		// user.setIsSystem(0l);
		// }
		user.setOrgId(personModel.getOrgId());
		user.setLoginUid(personModel.getLoginUid());
		user.setUCode(personModel.getUcode());
		user.setDisplayName(personModel.getName());

		if (personModel.getUserid() != null && personModel.getUserid() > 0) {
			dao.update(user);
		} else {
			dao.save(user);
			if ("1".equals(Struts2Util.getSession()
					.getAttribute(Constants.USER_ID).toString())) {
				Map parMap = new HashMap();
				List list = new ArrayList();
				parMap.put("orgId",
						getRootOrgId(personModel.getOrgId().toString()).get(0)
								.get("ROOTORGID"));
				parMap.put("creator",
						Struts2Util.getSession()
								.getAttribute(Constants.USER_ID).toString());
				parMap.put("createDate",
						DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss"));
				parMap.put("userid", user.getUserid());
				list.add(parMap);
				sqlDao.batchInsert("PersonnelSQL.insertAOM", list);

				List listUser = new ArrayList();
				Map param = new HashMap();
				param.put("userid", user.getUserid());
				param.put("nodeid", getNodeid());
				param.put("type", "user");
				listUser.add(param);
				sqlDao.batchDelete("PersonnelSQL.insertAdminUserRoot", listUser);
			}
		}
		// NjhwUsersExp
		long userid = personModel.getUserid() != null ? personModel.getUserid()
				.longValue() : (long) 0;
		List list = dao
				.findByHQL(
						"select t from NjhwUsersExp t, Users u where u.userid = t.userid and u.userid = ?",
						userid);
		if (list.size() > 0 && list != null) {
			userExp = (NjhwUsersExp) list.get(0);
			userExp.setModifyDate(DateUtil.getSysDate());
			userExp.setModifyId(Long.parseLong(Struts2Util.getSession()
					.getAttribute(Constants.USER_ID).toString()));
			HashMap<String, String> pMap = new HashMap<String, String>();
			pMap.put("cardId", personModel.getCityCardold());
		} else {
			userExp = new NjhwUsersExp();
			userExp.setInsertDate(DateUtil.getSysDate());
			userExp.setInsertId(Long.parseLong(loginId));
		}
		if (jkCard != null && "1".equals(personModel.getCityCardType())) {
			userExp.setCardUid(personModel.getCardUID());
		}
		userExp.setUserid(user.getUserid());
		userExp.setUepSex(personModel.getSex());
		userExp.setUepMail(personModel.getEmail());

		userExp.setUepFax(personModel.getFax());
		userExp.setWebFax(personModel.getFax_web());

		userExp.setUepMobile(personModel.getPhone());
		userExp.setResidentNo(personModel.getResidentNo());
		userExp.setUepType(personModel.getIsTempUser());
		userExp.setUepPhoto(personModel.getUserPhoto());

		/* userExp.setRoomId(personModel.getRoomNum()); */
		userExp.setRoomInfo(personModel.getRoomNum());
		// userExp.setTelId(personModel.getIpTelId());
		// userExp.setTelMac(telMac(personModel.getIpTelId()));
		userExp.setTelNum(personModel.getIpTelId() == null ? null : personModel
				.getIpTelId().toString());
		if (StringUtil.isBlank(personModel.getTmpCardStartDate())) {
			userExp.setUepBak2(DateUtil.date2Str(DateUtil.getSysDate(),
					"yyyy-MM-dd"));
		}
		userExp.setUepBak3(personModel.getDateFrom());
		userExp.setUepBak4(personModel.getDateTo());

		// userExp.setPlateNum(personModel.getCarNum());
		userExp.setCardType(personModel.getCityCardType());
		if ("2".equals(personModel.getCityCardType())) // 当选择的是临时卡时，将临时卡号保存到扩展字段TmpCard中
			userExp.setTmpCard(personModel.getCityCard());
		else
			userExp.setTmpCard(null);

		if (list.size() > 0 && list != null) {
			dao.update(userExp);
		} else {
			dao.save(userExp);
		}
		Map<String, Long> pMap = new HashMap<String, Long>();
		pMap.put("orgId", personModel.getOrgId());

		List<Map> listOrg = this.findListBySql("PersonnelSQL.getRootOrgId",
				pMap);
		if (null == personModel.getIsTempUser()
				|| !personModel.getIsTempUser().equals("04")) {
			// 人员类型
			LeaderLevel ll = new LeaderLevel();
			ll.setUserid(user.getUserid());
			ll.setStage(personModel.getStage());
			dao.saveOrUpdate(ll);
			// 新增user时 设置默认的考勤审批人员
			if (null == personModel.getUserid()) {
				// 普通员工 0
				if (0l == personModel.getStage()) {
					StringBuffer stids = new StringBuffer();
					List<Long> appIds = dao
							.findByHQL(
									"select us.userid from Users us, LeaderLevel t  "
											+ "where  us.orgId = (select orgId from Users uu where uu.userid = ?) "
											+ "and    us.userid = t.userid and t.stage  = 2 and us.userid != ?",
									user.getUserid(), user.getUserid());

					if (null != appIds && appIds.size() > 0) {
						for (long appId : appIds) {
							if (appId != user.getUserid().longValue()) {
								stids.append(appId).append(",");
							}

						}
						AttendanceApprovers aa = new AttendanceApprovers(
								user.getUserid(), stids.substring(0,
										stids.length() - 1));
						dao.saveOrUpdate(aa);
					}
				}
				// 部门领导 2
				else if (2l == personModel.getStage()) {
					StringBuffer stids = new StringBuffer();
					List<Long> appIds = dao
							.findByHQL(
									"select us.userid from Users us, LeaderLevel t "
											+ " where  us.orgId in (select orgId from Org where  PId= ? )"
											+ " and    us.userid = t.userid and t.stage  = 1 and us.userid != ?",
									Long.valueOf(listOrg.get(0)
											.get("ROOTORGID").toString()),
									user.getUserid());

					if (null != appIds && appIds.size() > 0) {
						for (long appId : appIds) {
							if (appId != user.getUserid().longValue()) {
								stids.append(appId).append(",");
							}
						}
						AttendanceApprovers aa = new AttendanceApprovers(
								user.getUserid(), stids.substring(0,
										stids.length() - 1));
						dao.saveOrUpdate(aa);
					}

				}
			}
			// 考勤管理员
			if (1 == personModel.getIsAttendanceAdmin()) {
				OrgAttendanceAdmin orga = new OrgAttendanceAdmin(
						Long.valueOf(listOrg.get(0).get("ROOTORGID").toString()),
						user.getUserid());
				dao.saveOrUpdate(orga);
			} else {
				if (null != dao.findById(OrgAttendanceAdmin.class, Long
						.valueOf(listOrg.get(0).get("ROOTORGID").toString()))) {
					dao.deleteById(
							OrgAttendanceAdmin.class,
							Long.valueOf(listOrg.get(0).get("ROOTORGID")
									.toString()));
				}
			}
		} else {
			if (null != dao.findById(LeaderLevel.class, user.getUserid())) {
				dao.deleteById(LeaderLevel.class, user.getUserid());
			}
			if (null != dao.findById(AttendanceApprovers.class,
					user.getUserid())) {
				dao.deleteById(AttendanceApprovers.class, user.getUserid());
			}
			if (null != dao.findById(OrgAttendanceAdmin.class,
					Long.valueOf(listOrg.get(0).get("ROOTORGID").toString()))) {
				dao.deleteById(OrgAttendanceAdmin.class, Long.valueOf(listOrg
						.get(0).get("ROOTORGID").toString()));
			}
		}

		// NjhwUsersPlatenum
		// 之前的全部删掉, 有新的车牌号，就以新的为准，
		dao.batchExecute("delete from NjhwUsersPlatenum t where t.userid = ?",
				user.getUserid());
		if (StringUtil.isNotEmpty(carNums)) {
			String[] carNumList = carNums.split(",");
			String[] isFastens = isFastenCarNums.split(",");
			for (int i = 0; i <= carNumList.length - 1; i++) {
				NjhwUsersPlatenum plateNum = new NjhwUsersPlatenum();

				plateNum.setNupPn(carNumList[i].toString());
				plateNum.setUserid(user.getUserid());
				plateNum.setNupFlag(isFastens[i]);
				plateNum.setInsertDate(DateUtil.getSysDate());
				plateNum.setInsertId(Long.parseLong(loginId));
				dao.save(plateNum);
			}
		}

		// 市民卡
		if (jkCard != null && "1".equals(personModel.getCityCardType())) { // 1.市民卡2.临时卡
			// 删除用户的其他市民卡信息
			List<NjhwTscard> delCardList = dao
					.findByHQL(
							"select t from NjhwTscard t where t.userId = ? and t.cardId != ?",
							user.getUserid(), personModel.getCityCard());
			for (NjhwTscard njhwTscard : delCardList) {
				dao.delete(njhwTscard);
			}

			// cardExp1 一个卡对应多个部门 存放orgid
			List cardList = dao
					.findByHQL(
							"select t from NjhwTscard t where t.cardId = ? and t.cardExp1 = ?",
							personModel.getCityCard(),
							listOrg.get(0).get("ROOTORGID").toString());
			if (cardList.size() > 0 && cardList != null) {
				card = (NjhwTscard) cardList.get(0);
				card.setModifyDate(DateUtil.getSysDate());
				card.setModifyId(Long.parseLong(Struts2Util.getSession()
						.getAttribute(Constants.USER_ID).toString()));
			} else {
				card = new NjhwTscard();
				// 查询跟单位的id

				card.setInsertDate(DateUtil.getSysDate());
				card.setInsertId(Long.parseLong(Struts2Util.getSession()
						.getAttribute(Constants.USER_ID).toString()));
				// 一个card可以对应多个部门用户
				if (null != listOrg && listOrg.size() > 0) {
					card.setCardExp1(String.valueOf(listOrg.get(0).get(
							"ROOTORGID")));
				} else {
					commonManager.log.error("*****用户所属部门为空!*******");
				}
			}

			card.setCardId(jkCard.getCardId()); // 市民卡面号
			card.setSocitype(jkCard.getSocitype()); // 证件类型
			card.setResidentNo(jkCard.getResidentNo()); // 证件号码
			card.setCardType(jkCard.getCardType()); // 市民卡类型
			card.setCustcode(jkCard.getCustcode()); // 市民卡内号
			card.setUserName(jkCard.getUserName()); // 用户姓名
			card.setCustsex(jkCard.getCustsex()); // 性别
			card.setDomaddr(jkCard.getDomaddr()); // 户籍地址
			card.setComaddr(jkCard.getComaddr()); // 居住地址
			card.setMoblie(jkCard.getMoblie()); // 手机号码
			card.setPuicstatus(jkCard.getPuicstatus()); // 通卡状态
			card.setCardstatus(jkCard.getCardstatus()); // 卡状态
			card.setCardLosted(jkCard.getCardLosted()); // 挂失状态
			card.setUserDiff("1"); // 用户类型为：内部人员
			card.setUserId(user.getUserid());
			card.setCardUid(personModel.getCardUID());
			card.setCustcode(personModel.getCardUID());
			if (cardList.size() > 0 && cardList != null)
				dao.update(card);
			else
				dao.save(card);
		} else {
			// 删除用户的其他市民卡信息
			List<NjhwTscard> delCardList = dao
					.findByHQL(
							"select t from NjhwTscard t where t.userId = ? and t.cardId = ?",
							user.getUserid(), personModel.getCityCard());
			for (NjhwTscard njhwTscard : delCardList) {
				dao.delete(njhwTscard);
			}
		}

		dao.flush();
		return num;
	}

	/**
	 * @description:保存其他人员登记信息
	 * @author hj
	 * @param PersonModel
	 *            personModel
	 * @param NjhwTscard
	 *            jkCard
	 * @return
	 */
	@SuppressWarnings("static-access")
	public int saveRegisterOther(PersonModel personModel, NjhwTscard jkCard,
			String carNums, String isFastenCarNums) {
		int num = 0;
		String loginId = Struts2Util.getSession()
				.getAttribute(Constants.USER_ID).toString();

		NjhwUsersExp userExp = null; // 用户扩展信息
		Users user = null; // 用户基本信息
		NjhwTscard card = null; // 市民卡信息

		// Users
		if (personModel.getUserid() != null
				&& personModel.getUserid().longValue() > 0) {
			user = (Users) dao.findById(Users.class, personModel.getUserid());
			user.setLastUpdateBy(Long.parseLong(Struts2Util.getSession()
					.getAttribute(Constants.USER_ID).toString()));
			user.setLastUpdateDate(DateUtil.date2Str(DateUtil.getSysDate(),
					"yyyy-MM-dd HH:mm:ss"));
			personModel.setLoginUidOld(user.getLoginUid());
		} else {
			user = new Users();
			// zhangqw 解决排序号不能为空 Date：2013年7月3日17:21:21
			user.setOrderNum(this.getMaxUser(personModel.getOrgId())
					.getOrderNum() + 1);
			user.setCreator(Long.parseLong(Struts2Util.getSession()
					.getAttribute(Constants.USER_ID).toString()));
			user.setCreatDate(DateUtil.date2Str(DateUtil.getSysDate(),
					"yyyy-MM-dd HH:mm:ss"));

			user.setUType(null);
			user.setPId(null);
			user.setIsSystem(0l);
			user.setChangeLoginPwdFlag(1l);
			user.setActiveFlag((long) 1);

		}

		user.setOrgId(personModel.getOrgId());
		user.setDisplayName(personModel.getName());

		if (personModel.getUserid() != null && personModel.getUserid() > 0) {
			dao.update(user);
		} else {
			dao.save(user);
		}
		List<Map> listRoom = null;
		// NjhwUsersExp
		long userid = personModel.getUserid() != null ? personModel.getUserid()
				.longValue() : (long) 0;
		List list = dao
				.findByHQL(
						"select t from NjhwUsersExp t, Users u where u.userid = t.userid and u.userid = ?",
						userid);
		if (list.size() > 0 && list != null) {
			userExp = (NjhwUsersExp) list.get(0);
			userExp.setModifyDate(DateUtil.getSysDate());
			userExp.setModifyId(Long.parseLong(Struts2Util.getSession()
					.getAttribute(Constants.USER_ID).toString()));

		} else {
			userExp = new NjhwUsersExp();
			userExp.setInsertDate(DateUtil.getSysDate());
			userExp.setInsertId(Long.parseLong(loginId));
		}

		userExp.setUserid(user.getUserid());
		userExp.setUepSex(personModel.getSex());

		userExp.setUepMobile(personModel.getPhone());
		userExp.setResidentNo(personModel.getResidentNo());
		userExp.setUepType("01");

		userExp.setTelNum(personModel.getIpTelNum());

		// userExp.setPlateNum(personModel.getCarNum());
		userExp.setCardType(personModel.getCityCardType());
		if ("2".equals(personModel.getCityCardType())) // 当选择的是临时卡时，将临时卡号保存到扩展字段TmpCard中
			userExp.setTmpCard(personModel.getCityCard());

		if (list.size() > 0 && list != null) {
			dao.update(userExp);
		} else {
			dao.save(userExp);
		}

		// NjhwUsersPlatenum
		// 之前的全部删掉, 有新的车牌号，就以新的为准，
		dao.batchExecute("delete from NjhwUsersPlatenum t where t.userid = ?",
				user.getUserid());
		if (StringUtil.isNotEmpty(carNums)) {
			String[] carNumList = carNums.split(",");
			String[] isFastens = isFastenCarNums.split(",");
			for (int i = 0; i <= carNumList.length - 1; i++) {
				NjhwUsersPlatenum plateNum = new NjhwUsersPlatenum();

				plateNum.setNupPn(carNumList[i].toString());
				plateNum.setUserid(user.getUserid());
				plateNum.setNupFlag(isFastens[i]);
				plateNum.setInsertDate(DateUtil.getSysDate());
				plateNum.setInsertId(Long.parseLong(loginId));
				dao.save(plateNum);
			}
		}

		// 市民卡
		if (jkCard != null && "1".equals(personModel.getCityCardType())) { // 1.市民卡2.临时卡
			// 删除用户的其他市民卡信息
			List<NjhwTscard> delCardList = dao
					.findByHQL(
							"select t from NjhwTscard t where t.userId = ? and t.cardId != ?",
							user.getUserid(), personModel.getCityCard());
			for (NjhwTscard njhwTscard : delCardList) {
				dao.delete(njhwTscard);
			}

			Map<String, Long> pMap = new HashMap<String, Long>();
			pMap.put("orgId", personModel.getOrgId());

			List<Map> listOrg = this.findListBySql("PersonnelSQL.getRootOrgId",
					pMap);

			// cardExp1 一个卡对应多个部门 存放orgid
			List cardList = dao
					.findByHQL(
							"select t from NjhwTscard t where t.cardId = ? and t.cardExp1 = ?",
							personModel.getCityCard(),
							listOrg.get(0).get("ROOTORGID").toString());
			if (cardList.size() > 0 && cardList != null) {
				card = (NjhwTscard) cardList.get(0);
				card.setModifyDate(DateUtil.getSysDate());
				card.setModifyId(Long.parseLong(Struts2Util.getSession()
						.getAttribute(Constants.USER_ID).toString()));
			} else {
				card = new NjhwTscard();
				// 查询跟单位的id

				card.setInsertDate(DateUtil.getSysDate());
				card.setInsertId(Long.parseLong(Struts2Util.getSession()
						.getAttribute(Constants.USER_ID).toString()));
				// 一个card可以对应多个部门用户
				if (null != listOrg && listOrg.size() > 0) {
					card.setCardExp1(String.valueOf(listOrg.get(0).get(
							"ROOTORGID")));
				} else {
					commonManager.log.error("*****用户所属部门为空!*******");
				}
			}

			card.setCardId(jkCard.getCardId()); // 市民卡面号
			card.setSocitype(jkCard.getSocitype()); // 证件类型
			card.setResidentNo(jkCard.getResidentNo()); // 证件号码
			card.setCardType(jkCard.getCardType()); // 市民卡类型
			card.setCustcode(jkCard.getCustcode()); // 市民卡内号
			card.setUserName(jkCard.getUserName()); // 用户姓名
			card.setCustsex(jkCard.getCustsex()); // 性别
			card.setDomaddr(jkCard.getDomaddr()); // 户籍地址
			card.setComaddr(jkCard.getComaddr()); // 居住地址
			card.setMoblie(jkCard.getMoblie()); // 手机号码
			card.setPuicstatus(jkCard.getPuicstatus()); // 通卡状态
			card.setCardstatus(jkCard.getCardstatus()); // 卡状态
			card.setCardLosted(jkCard.getCardLosted()); // 挂失状态
			card.setUserDiff("1"); // 用户类型为：内部人员
			card.setUserId(user.getUserid());
			card.setCardUid(personModel.getCardUID());
			card.setCustcode(personModel.getCardUID());
			if (cardList.size() > 0 && cardList != null)
				dao.update(card);
			else
				dao.save(card);
		}

		dao.flush();
		return num;
	}

	/*
	 * 根据nodeid 获得roomid
	 */
	// public Long getRIDByNID(Long nodeId)
	// {
	// Map<String, Long> pMap = new HashMap<String, Long>();
	// pMap.put("nodeId", nodeId);
	// List<HashMap> roomid =
	// this.findListBySql("PersonnelSQL.getRoomIdByNodeId", pMap);
	// if (null != roomid && roomid.size() > 0)
	// {
	// return Long.parseLong(roomid.get(0).get("ROOM_ID").toString());
	// }
	// else
	// {
	// return null;
	// }
	//
	// }
	//
	// /*
	// *根据roomid 获得nodeid
	// */
	// public Long getNIDByRID(Long roomId)
	// {
	// Map<String, Long> pMap = new HashMap<String, Long>();
	// pMap.put("roomId", roomId);
	// List<HashMap> roomid =
	// this.findListBySql("PersonnelSQL.getNodeIdByRoomId", pMap);
	// if (null != roomid && roomid.size() > 0)
	// {
	// return Long.parseLong(roomid.get(0).get("NODE_ID").toString());
	// }
	// else
	// {
	// return null;
	// }
	//
	// }

	/**
	 * @description:挂失
	 * @author zh
	 * @param HashMap
	 *            map
	 * @return
	 */
	public int saveModifyCardIsLosted(String optType, String cityCard) {
		int num = 0;
		List<NjhwTscard> tsCardList = (List<NjhwTscard>) dao.findByProperty(
				NjhwTscard.class, "cardId", cityCard);

		// 验证市民卡号是否存在
		if (tsCardList == null || tsCardList.size() == 0)
			num = 1;
		else { // 根据操作进行修改
			for (NjhwTscard tsCard : tsCardList) {
				if ("confirmLosted".equals(optType)) {
					tsCard.setSystemLosted(NjhwTscard.SYSTEM_LOSTED_LOSTED);
					// 取消权限
					commonManager.delAuthority(cityCard);

					accessMgrManager.accessAndGateOpt(tsCard.getUserId(), "0");
				} else if ("cancelLosted".equals(optType)) {
					tsCard.setSystemLosted(NjhwTscard.SYSTEM_LOSTED_UNLOSTED);
					// 授予权限
					commonManager.addAuthority(cityCard);

					accessMgrManager.accessAndGateOpt(tsCard.getUserId(), "1");
				}

				dao.update(tsCard);
				// dao.flush();
			}
		}
		return num;
	}

	/**
	 * 
	 * @param org
	 *            单位id
	 * @return
	 */
	public Users getOrgAttAdminName(String org) {
		Object o = dao.findById(OrgAttendanceAdmin.class, Long.valueOf(org));
		if (o != null) {
			Users u = (Users) dao.findById(Users.class,
					((OrgAttendanceAdmin) o).getUserid());
			return u;
		} else {
			return null;
		}

	}

	private String getNodeid() {
		// 按照张军哥讲的 根据 单位管理员 查找nodeid 找不到去找他
		String nodeId = null;
		List<HashMap<String, String>> obj = this.findListBySql(
				"PersonnelSQL.getOBJNodeId", new HashMap());
		if (null != obj && obj.size() > 0) {
			nodeId = String.valueOf(obj.get(0).get("NODE_ID"));
		}

		return nodeId;
	}

	/**
	 * @description:设置用户为管理员
	 * @author zh
	 * @param HashMap
	 *            map
	 * @return
	 */
	public int saveModifySMA(String optType, String userid) {
		int num = 0;
		String nodeId = getNodeid();

		try {
			Users user = (Users) dao.findById(Users.class,
					Long.parseLong(userid));
			if ("confirm".equals(optType)) {
				user.setIsSystem(1l);
				user.setUType(1l);
				user.setPId(Long.parseLong(Struts2Util.getSession()
						.getAttribute(Constants.USER_ID).toString()));

				List listUser = new ArrayList();
				Map param = new HashMap();
				// System.out.println(ids[i].substring(5,ids[i].length()));
				param.put("userid", userid);
				param.put("nodeId", nodeId);
				listUser.add(param);
				sqlDao.batchDelete("PersonnelSQL.delObjAdminRoot", listUser);
				sqlDao.batchDelete("PersonnelSQL.delAOM", listUser);
			} else if ("cancel".equals(optType)) {
				user.setIsSystem(0l);
				user.setUType(null);
				user.setPId(null);
			}
			dao.update(user);

			// 找到指定人员的顶级单位
			long topOrgId = 0;
			Map pMap = new HashMap();
			pMap.put("userid", userid);
			List<HashMap> orglist = this.findListBySql(
					"PersonnelSQL.getTopOrgIdByUserId", pMap);
			if (orglist.size() > 0)
				topOrgId = orglist.get(0).get("TOP_ORG_ID") != null ? Long
						.parseLong(orglist.get(0).get("TOP_ORG_ID").toString())
						: 0;

			HashMap parMap = new HashMap();
			parMap.put("userid", userid);
			List list = new ArrayList();
			if ("confirm".equals(optType)) { // 管理员映射表插入记录

				parMap.put("orgId", topOrgId);
				parMap.put("creator",
						Struts2Util.getSession()
								.getAttribute(Constants.USER_ID).toString());
				parMap.put("createDate",
						DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss"));
				list.add(parMap);
				sqlDao.batchInsert("PersonnelSQL.insertAOM", list);

				// //继承功能菜单权限 Add By WXJ 2013-5-30
				// String sessionUserid =
				// Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
				// List<ObjPermMap> listOpm =
				// this.findByHQL("select p from ObjPermMap p,Objtank o " +
				// "where o.extResType='"+Objtank.EXT_RES_TYPE_M+"' " +
				// "and p.nodeId=o.nodeId and p.personId = "+sessionUserid);
				// if (listOpm!=null && listOpm.size()>0){
				// for (ObjPermMap opm : listOpm){
				// List<ObjPermMap> listOpm2 =
				// this.findByHQL("from ObjPermMap where personId = "+userid
				// +" and nodeId=?", opm.getNodeId());
				// if (listOpm2==null || listOpm2.size()==0){
				// dao.batchExecute("insert into ObjPermMap(mapid,personId,nodeId,permCode,denyFlag,type) "
				// +
				// "select cast((max(m.mapid)+1) as long),cast(" +
				// userid+" as long),cast("+
				// opm.getNodeId()+" as long),'obj_vis',cast(1 as long),'user' "+
				// "from ObjPermMap m " );
				// }
				// }
				// }

				List listUser = new ArrayList();
				Map param = new HashMap();

				param.put("userid", userid);
				param.put("nodeid", nodeId);
				param.put("type", "user");
				listUser.add(param);
				sqlDao.batchDelete("PersonnelSQL.insertAdminUserRoot", listUser);
			} else if ("cancel".equals(optType)) { // 管理员映射表删除记录
				list.add(parMap);
				sqlDao.batchDelete("PersonnelSQL.delAOM", list);
				List listUser = new ArrayList();
				Map param = new HashMap();
				param.put("userid", userid);
				param.put("nodeId", nodeId);
				listUser.add(param);
				sqlDao.batchDelete("PersonnelSQL.delObjAdminRoot", listUser);
				// //删除功能菜单权限 Add By WXJ 2013-5-30
				// List<ObjPermMap> listOpm =
				// this.findByHQL("select p from ObjPermMap p,Objtank o " +
				// "where o.extResType='"+Objtank.EXT_RES_TYPE_M+"' " +
				// "and p.nodeId=o.nodeId and p.personId = "+userid);
				// if (listOpm!=null && listOpm.size()>0){
				// for (ObjPermMap opm : listOpm){
				// dao.delete(opm);
				// }
				// }
			}

			// dao.flush();
		} catch (Exception e) {
			num = 1;
			e.printStackTrace();
		}
		return num;
	}

	// /**
	// * @description:加载未分配的电话号码
	// * @author zh
	// * @param HashMap map
	// * @return
	// */
	// public List<HashMap> loadNoAllotPhoneNum(String searchType,String telNum,
	// long orgId) {
	// // 获取已分配的电话号码
	// String hql =
	// "select eor.resName from Users u ,NjhwUsersExp e, EmOrgRes eor where u.userid = e.userid and eor.resName = e.telNum and u.orgId = ? and eor.orgId = ?";
	// List<String> list = dao.findByHQL(hql, orgId, orgId);
	// List ids = new ArrayList();
	// for (String resName : list) {
	// if (resName != null && StringUtil.isNotEmpty(resName)) ids.add(resName);
	// }
	//
	// // 如果当前操作是修改的话，就从排除列表中移除当前用户的电话号码
	// if ("update".equals(searchType)) ids.remove(telNum);
	//
	// // 取得未分配的房间
	// HashMap map = new HashMap();
	// map.put("ids", ids);
	// map.put("len", ids.size());
	// map.put("orgId", orgId);
	// map.put("eorType", EmOrgRes.EOR_TYPE_TEL_NO);
	// return sqlDao.findList("PersonnelSQL.NoAllotTelNumList", map);
	// }

	/**
	 * @description:加载未分配的话机
	 * @author zh
	 * @param HashMap
	 *            map
	 * @return
	 */
	public List<HashMap> loadNoAllotIpTel(String searchType, Long telId,
			long orgId) {
		// 获取已分配的话机
		List<Long> list = dao
				.findByHQL(
						"select e.telId from Users u, NjhwUsersExp e where u.userid = e.userid and u.orgId = ? and e.telId is not null",
						orgId);
		List ids = new ArrayList();
		for (Long telid : list) {
			if (telid != null && telid > 0)
				ids.add(Long.parseLong(telid.toString()));
		}

		// 如果当前操作是修改的话，就从排除列表中移除当前用户的电话号码
		if ("update".equals(searchType))
			ids.remove(telId);

		// 取得未分配的话机
		HashMap map = new HashMap();
		map.put("ids", ids);
		map.put("len", ids.size());
		map.put("orgId", orgId);
		map.put("eorType", EmOrgRes.EOR_TYPE_PHONE);
		return sqlDao.findList("PersonnelSQL.NoAllotIpTelList", map);
	}

	/**
	 * @description:停用
	 * @author zh
	 * @param PersonModel
	 *            personModel
	 * @return
	 */
	public void updateOut(long userId, String optType) {
		try {
			Users user = (Users) dao.findById(Users.class, userId);
			if ("logout".equals(optType)) {
				user.setActiveFlag((long) 0); // 激活状态1：正常，0：迁出
				// 取消权限
				commonManager.delAuthority(userId);
				/*
				 * 春神已经能判断账户状态 此代码注释掉 2013年9月13日16:02:43 new
				 * LDAPService().deleteUserByLoginUid(user.getLoginUid());
				 */
			} else if ("login".equals(optType)) {
				user.setActiveFlag((long) 1); // 激活状态1：正常，0：迁出
				// 授予权限
				commonManager.addAuthority(userId);
				/*
				 * 春神已经能判断账户状态 此代码注释掉 2013年9月13日16:02:43 UserInfo userInfo =
				 * getUserInfoByUserId(user); new
				 * LDAPService().addUser(userInfo);
				 */
			}
			user.setLastUpdateBy(Long.parseLong(Struts2Util.getSession()
					.getAttribute(Constants.USER_ID).toString()));
			user.setLastUpdateDate(DateUtil.date2Str(DateUtil.getSysDate(),
					"yyyy-MM-dd HH:mm:ss"));
			dao.update(user);

			List<Org> orgList = dao.findByHQL(
					"select t from Org t where t.orgId = ?",
					Long.parseLong(Struts2Util.getSession()
							.getAttribute(Constants.ORG_ID).toString()));
			String orgName = "";
			if (orgList != null && orgList.size() > 0) {
				orgName = orgList.get(0).getShortName();
			}

			if ("logout".equals(optType)) {
				// 用户扩展信息
				List expList = dao
						.findByHQL(
								"select t from NjhwUsersExp t, Users u where u.userid = t.userid and u.userid = ?",
								userId);
				if (expList.size() > 0 && expList != null) {
					NjhwUsersExp exp = (NjhwUsersExp) expList.get(0);
					exp.setTelId(null);
					exp.setTelMac("");
					exp.setTelNum("");
					dao.update(exp);

					accessMgrManager.accessAndGateOpt(userId, "0");
				}

			} else if ("login".equals(optType)) {
				// 用户扩展信息
				List expList = dao
						.findByHQL(
								"select t from NjhwUsersExp t, Users u where u.userid = t.userid and u.userid = ?",
								userId);
				if (expList.size() > 0 && expList != null) {
					NjhwUsersExp exp = (NjhwUsersExp) expList.get(0);
					if (!StringUtil.isBlank(exp.getUepBak2())) {
						Date now = new Date();
						SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
						Date start = f.parse(exp.getUepBak2());
						long days = (now.getTime() - start.getTime()) / 1000
								/ 60 / 60 / 24;
						if (days >= 30) {
							exp.setUepBak2(DateUtil.date2Str(
									DateUtil.getSysDate(), "yyyy-MM-dd"));
							dao.update(exp);
						}
					}

					accessMgrManager.accessAndGateOpt(userId, "1");
				}
			}
			// dao.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: getPlayCardRoomsLocks
	 * @Description: 查询本单位的房间
	 * @author zhangqw
	 * @date 2013年7月3日14:34:38
	 * @param orgid
	 *            部门id
	 * @param userid
	 *            用户id
	 */
	public List getPlayCardRoomsLocks(String orgId, String userId, String roomId) {
		List list = null;
		try {
			Map<String, String> pMap = new HashMap<String, String>();
			List<Map> listOrg = getRootOrgId(orgId);
			if (null != listOrg && listOrg.size() > 0) {
				pMap.put("orgId",
						String.valueOf(listOrg.get(0).get("ROOTORGID")));
			} else {
				pMap.put("orgId", orgId);
			}
			if (StringUtil.isEmpty(userId)) {
				list = sqlDao.findList(
						"PersonnelUnitSQL.getUnitUNResRoomsLocks", pMap);
			} else {
				pMap.put("userId", userId);
				if ("R".equals(roomId)) {
					list = sqlDao.findList(
							"PersonnelUnitSQL.getUnitUNResRoomsLocksM", pMap);
				} else {
					list = sqlDao.findList(
							"PersonnelUnitSQL.getUnitResRoomsLocks", pMap);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @title: showDistributeLock
	 * @description: 显示房间门锁分配状态
	 * @author ZHANGQW
	 * @return json
	 * @date 2013年7月13日11:01:29
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public JSONObject showDistributeLock(String cardNo, String isAuth) {
		JSONArray json = null;
		JSONObject mObj = new JSONObject();
		// String cardNo =
		try {
			if (null != cardNo) {
				// 得到房间的全部门锁
				List<NjhwDoorcontrolExp> nodeidList = dao.findByHQL(
						"select t from NjhwDoorcontrolExp t where t.cardId =?",
						cardNo);
				if (null != nodeidList && nodeidList.size() > 0) {
					json = new JSONArray();
					for (int i = 0; i < nodeidList.size(); i++) {
						if (null != nodeidList.get(i).getDlockId()) {
							String nodeId = nodeidList.get(i).getDlockId()
									.toString().trim();
							// 清除所有添加授权失败和删除授权失败的信息
							if (!"YES".equals(isAuth)) {
								removeErrorDoorInfo(nodeId, cardNo);
							}

						}
					}

					// 得到授权成功的
					List<Map> roomSuccList = this.getResPermission(cardNo,
							"SUCCESS");

					// 得到授权失败的
					List<Map> roomFaiList = this.getResPermission(cardNo,
							"FAIL");

					StringBuffer roomsu = new StringBuffer();
					if (null != roomSuccList && roomSuccList.size() > 0) {

						for (Map room : roomSuccList) {
							roomsu.append(room.get("ROOM_NAME") + "，");
						}

					}
					mObj.put("roomSu", roomsu.toString());
					StringBuffer roomfa = new StringBuffer();
					if (null != roomFaiList && roomFaiList.size() > 0) {

						for (Map room : roomSuccList) {
							roomfa.append(room.get("ROOM_NAME") + "，");
						}

					}
					mObj.put("roomFa", roomfa.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mObj;
	}

	public List<Map> getResPermission(String cardNo, String status) {
		Map condtion = new HashMap();
		if ("FAIL".equals(status)) {
			condtion.put("exp1", "2");
		}
		condtion.put("cardNo", cardNo);

		return sqlDao.findList("PersonnelUnitSQL.getResPerDoor", condtion);
	}

	/**
	 * 删除多余的授权失败信息
	 * 
	 * @param nodeId
	 */
	public void removeErrorDoorInfo(String nodeId, String cardNo) {
		// 删除添加授权未成功的数据
		dao.batchExecute("delete from NjhwDoorcontrolExp exp where exp.dlockId = '"
				+ NumberUtil.strToLong(nodeId)
				+ "' and exp.exp1 = '2' and cardId=" + cardNo);
		// 更新删除授权未成功的数据
		dao.batchExecute("update NjhwDoorcontrolExp set exp1 = null  where dlock_id = '"
				+ NumberUtil.strToLong(nodeId)
				+ "' and exp1 = '3' and cardId="
				+ cardNo);
	}

	/**
	 * 
	 * @Title: getRootOrgId
	 * @Description: 查询根部门
	 * @author zhangqw
	 * @date 2013年7月5日13:46:11
	 * @param orgId
	 *            子部门
	 */
	public List<Map> getRootOrgId(String orgId) {
		// 查询跟单位的id
		Map<String, String> pMap = new HashMap<String, String>();
		pMap.put("orgId", orgId);
		List<Map> listOrg = this.findListBySql("PersonnelSQL.getRootOrgId",
				pMap);
		return listOrg;

	}

	/**
	 * 
	 * @Title: getPlayCardRoomsLocks
	 * @Description: 查询本单位的房间
	 * @author zhangqw
	 * @date 2013年7月3日14:34:38
	 * @param @param page
	 * @param @param orgId
	 * @param @return
	 * @return Page<Map>
	 * @throws
	 */
	public List getPlayCardTel(String orgId, String telType) {
		List list = null;
		try {
			Map<String, String> pMap = new HashMap<String, String>();
			List<Map> listOrg = getRootOrgId(orgId);
			if (null != listOrg && listOrg.size() > 0) {
				pMap.put("orgId",
						String.valueOf(listOrg.get(0).get("ROOTORGID")));
			} else {
				pMap.put("orgId", orgId);
			}
			pMap.put("telType", telType);
			// 传真
			if ("2".equals(telType)) {
				list = sqlDao.findList("PersonnelUnitSQL.getUnitFaxDis", pMap);
			}
			// 网络传真
			else if ("3".equals(telType)) {
				list = sqlDao.findList("PersonnelUnitSQL.getUnitWebFaxDis",
						pMap);
			}
			// ip 电话
			else {
				list = sqlDao.findList("PersonnelUnitSQL.getUnitTelDis", pMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 
	 * @Title: 卡号切换 删除老卡权限 授权门锁权限给新卡
	 * @Description:分配门锁
	 * @author zhangqw
	 * @date 2013年7月15日13:43:44
	 * @param loginId
	 *            登陆账号
	 * @param cardCityOld
	 *            老卡号
	 * @oartm cardCity 新卡卡号
	 * @param userid
	 *            userid
	 */
	public void cardDoorAuth(String cardCityOld, String cardCity,
			String loginId, String userId, String isNormal) {

		HashMap<String, String> pMap = new HashMap<String, String>();
		pMap.put("cardId", cardCityOld);
		List<Map> listRoom = this.findListBySql("PersonnelSQL.getRoomIdByCardId", pMap);
		if (null != listRoom && listRoom.size() > 0) {
			// 删除老卡权限
			for (int i = 0; i < listRoom.size(); i++) {
				doorControlToAppService.delDoorAuth(userId, loginId, listRoom
						.get(i).get("ROOM_ID").toString(), cardCityOld, true);
			}
			dao.flush();
			if ("false".equals(isNormal)) {
				// 给新卡赋予门锁权限
				for (int i = 0; i < listRoom.size(); i++) {
					doorControlToAppService
							.addDoorAuth(userId, loginId,
									listRoom.get(i).get("ROOM_ID").toString(),
									cardCity);
				}
			}
		}
	}

	/**
	 * 
	 * @Title: doorDis
	 * @Description:分配门锁
	 * @author zhangqw
	 * @date 2013年7月12日20:38:49
	 * @param doorIds
	 *            nodeids
	 * @param cityCard
	 *            cardid
	 * @param userid
	 */
	public void doorDis(String userId, String[] doorIds, String cityCard,
			String loginId) {
		// 调用通卡接口
		unitAdminManager.savePlayCard(cityCard, doorIds, loginId);
	}

	public DevicePermissionToAppService getDevicePermissionToApp() {
		return devicePermissionToApp;
	}

	public void setDevicePermissionToApp(
			DevicePermissionToAppService devicePermissionToApp) {
		this.devicePermissionToApp = devicePermissionToApp;
	}

	/**
	 * doorControlToAppService
	 * 
	 * @return the doorControlToAppService
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public DoorControlToAppService getDoorControlToAppService() {
		return doorControlToAppService;
	}

	/**
	 * @param doorControlToAppService
	 *            the doorControlToAppService to set
	 */

	public void setDoorControlToAppService(
			DoorControlToAppService doorControlToAppService) {
		this.doorControlToAppService = doorControlToAppService;
	}

	public void setAccessMgrManager(AccessMgrManager accessMgrManager) {
		this.accessMgrManager = accessMgrManager;
	}

	public AccessMgrManager getAccessMgrManager() {
		return accessMgrManager;
	}

	/**
	 * userinfo
	 * 
	 * @author zhangquanwei
	 * @date 2013年9月11日15:51:543
	 */
	private UserInfo getUserInfoByUserId(Users users) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String importTime = df.format(new Date());
		List<Map<String, String>> userMap = new ArrayList<Map<String, String>>();
		Map map = new HashMap();
		map.put("userId", users.getUserid());
		userMap = sqlDao.findList("LDAPSQL.getUserByLdapId", map);

		UserInfo userInfo = new UserInfo();
		// required
		userInfo.setSn(importTime);
		userInfo.setCn(importTime);
		userInfo.setUserPassword("123456");
		userInfo.setUid(StringUtil.empty2Null(String.valueOf(userMap.get(0)
				.get("USERID"))));
		userInfo.setLoginUid(StringUtil.empty2Null(userMap.get(0).get(
				"LOGIN_UID")));

		// optional
		userInfo.setActiveFlag("1");
		userInfo.setCardType(StringUtil.empty2Null(userMap.get(0).get(
				"CARD_TYPE")));
		userInfo.setCardId(StringUtil.empty2Null(userMap.get(0).get("CARD_ID")));
		userInfo.setCardUid(StringUtil.empty2Null(userMap.get(0)
				.get("CARD_UID")));
		userInfo.setChangeLoginPwdFlag("1");
		userInfo.setDisplayName(StringUtil.empty2Null(userMap.get(0).get(
				"DISPLAY_NAME")));
		userInfo.setFeeType(null);
		userInfo.setIsSystem(StringUtil.empty2Null("1"));
		userInfo.setJobId(null);
		userInfo.setLoginPwd(StringUtil.empty2Null(userMap.get(0).get(
				"LOGIN_PWD")));
		userInfo.setLoginPwdMD5(StringUtil.empty2Null(userMap.get(0).get(
				"LOGIN_PWD_MD5")));
		userInfo.setMemo(null);
		userInfo.setOrderNum(null);

		List<HashMap> orgNameRs = new ArrayList<HashMap>();
		Map orgMap = new HashMap();
		orgMap.put("orgid", userMap.get(0).get("ORG_ID"));
		orgNameRs = sqlDao.findList("LDAPSQL.getOrgInfo", orgMap);
		Org org = (Org) dao.findById(Org.class, users.getOrgId());
		userInfo.setOrgId(users.getOrgId().toString());
		userInfo.setOrgName(StringUtil.empty2Null(org.getName()));

		List<Map> orglist = getRootOrgId(users.getOrgId().toString());
		userInfo.setPId(orglist.get(0).get("ROOTORGID").toString());
		userInfo.setPName(orglist.get(0).get("ROOTORGNAME").toString());
		List<HashMap<String, String>> plateNumRs = new ArrayList<HashMap<String, String>>();
		Map plateNumMap = new HashMap();
		plateNumMap.put("userid", users.getUserid());
		plateNumRs = sqlDao.findList("LDAPSQL.getPlateNum", plateNumMap);
		String plateNum = null;
		for (int j = 0; j < plateNumRs.size(); j++) {
			plateNum = plateNumRs.get(0).get("PLATE_NUM");
		}
		userInfo.setPlateNum(StringUtil.empty2Null(plateNum));
		userInfo.setReqTelId(StringUtil.empty2Null(userMap.get(0).get(
				"REQ_TEL_ID")));
		userInfo.setResidentNo(StringUtil.empty2Null(userMap.get(0).get(
				"RESIDENT_NO")));
		userInfo.setRoomId(StringUtil.empty2Null(userMap.get(0).get("ROOM_ID")));
		userInfo.setRoomInfo(StringUtil.empty2Null(userMap.get(0).get(
				"ROOM_INFO")));
		userInfo.setStationId(StringUtil.empty2Null(userMap.get(0).get(
				"STATION_ID")));
		userInfo.setStationName(StringUtil.empty2Null(userMap.get(0).get(
				"STATION_NAME")));
		userInfo.setTelId(StringUtil.empty2Null(userMap.get(0).get("TEL_ID")));
		userInfo.setTelMAC(StringUtil.empty2Null(userMap.get(0).get("TEL_MAC")));

		List<HashMap<String, String>> telNumRs = new ArrayList<HashMap<String, String>>();
		Map telNumMap = new HashMap();
		telNumMap.put("telId", userMap.get(0).get("TEL_NUM"));
		telNumRs = sqlDao.findList("LDAPSQL.getTelInfo", telNumMap);
		String telNum = null;
		for (int j = 0; j < telNumRs.size(); j++) {
			telNum = telNumRs.get(j).get("TEL_NUM");
		}
		userInfo.setTelNum(StringUtil.empty2Null(telNum));
		userInfo.setTmpCard(StringUtil.empty2Null(userMap.get(0)
				.get("TMP_CARD")));
		userInfo.setUCode(StringUtil.empty2Null(userMap.get(0).get("U_CODE")));
		userInfo.setUepBak1(StringUtil.empty2Null(userMap.get(0)
				.get("UEP_BAK1")));
		userInfo.setUepBak2(StringUtil.empty2Null(userMap.get(0)
				.get("UEP_BAK2")));
		userInfo.setUepBak3(StringUtil.empty2Null(userMap.get(0)
				.get("UEP_BAK3")));
		userInfo.setUepBak4(StringUtil.empty2Null(userMap.get(0)
				.get("UEP_BAK4")));

		List<HashMap<String, String>> uepFaxRs = new ArrayList<HashMap<String, String>>();
		Map uepFaxMap = new HashMap();
		uepFaxMap.put("telId", userMap.get(0).get("UEP_FAX"));
		uepFaxRs = sqlDao.findList("LDAPSQL.getTelInfo", uepFaxMap);
		String uepFax = null;
		for (int j = 0; j < uepFaxRs.size(); j++) {
			uepFax = uepFaxRs.get(j).get("TEL_NUM");
		}
		userInfo.setUepFax(StringUtil.empty2Null(uepFax));
		userInfo.setUepFlag(StringUtil.empty2Null(userMap.get(0)
				.get("UEP_FLAG")));
		userInfo.setUepHobby(StringUtil.empty2Null(userMap.get(0).get(
				"UEP_HOBBY")));
		userInfo.setUepMail(StringUtil.empty2Null(userMap.get(0)
				.get("UEP_MAIL")));
		userInfo.setUepMobile(StringUtil.empty2Null(userMap.get(0).get(
				"UEP_MOBILE")));
		userInfo.setUepPhoto(StringUtil.empty2Null(userMap.get(0).get(
				"UEP_PHOTO")));
		userInfo.setUepSex(StringUtil.empty2Null(userMap.get(0).get("UEP_SEX")));
		userInfo.setUepSkill(StringUtil.empty2Null(userMap.get(0).get(
				"UEP_SKILL")));
		userInfo.setUepType(StringUtil.empty2Null(userMap.get(0)
				.get("UEP_TYPE")));
		userInfo.setUType(StringUtil.empty2Null(String.valueOf(userMap.get(0)
				.get("U_TYPE"))));

		List<HashMap<String, String>> webFaxRs = new ArrayList<HashMap<String, String>>();
		Map webFaxMap = new HashMap();
		webFaxMap.put("telId", userMap.get(0).get("WEB_FAX"));
		webFaxRs = sqlDao.findList("LDAPSQL.getTelInfo", webFaxMap);
		String webFax = null;
		for (int j = 0; j < webFaxRs.size(); j++) {
			webFax = webFaxRs.get(j).get("TEL_NUM");
		}
		userInfo.setWebFax(StringUtil.empty2Null(webFax));
		return userInfo;
	}

	/**
	 * PersonModel to UserInfo for Ldap
	 * 
	 * @author zhangquanwei
	 * @date 2013年8月27日11:48:55
	 */
	public UserInfo personModel2UserInfo(PersonModel personModel) {
		UserInfo ldapUser = new UserInfo();
		String orgName = null;
		if (null != personModel.getOrgId()) {
			Org org = (Org) dao.findById(Org.class, personModel.getOrgId());
			orgName = org.getName();
			List<Map> orglist = getRootOrgId(personModel.getOrgId().toString());
			ldapUser.setPId(orglist.get(0).get("ROOTORGID").toString());
			ldapUser.setPName(orglist.get(0).get("ROOTORGNAME").toString());
		}

		if (1070l != personModel.getPId()) {
			String ipTelNum = null;
			if (null != personModel.getIpTelId()) {
				TcIpTel tel = (TcIpTel) dao.findById(TcIpTel.class,
						personModel.getIpTelId());
				ipTelNum = tel.getTelNum();
			}

			String faxNum = null;
			if (StringUtil.isNotEmpty(personModel.getFax())) {
				TcIpTel tel = (TcIpTel) dao.findById(TcIpTel.class,
						Long.parseLong(personModel.getFax()));
				faxNum = tel.getTelNum();
			}
			ldapUser.setTelNum(StringUtil.empty2Null(ipTelNum));
			ldapUser.setUepFax(StringUtil.empty2Null(faxNum));
		} else {
			if (null != personModel.getIpTelId()) {
				ldapUser.setTelNum(personModel.getIpTelId().toString());
			}
			ldapUser.setUepFax(StringUtil.empty2Null(personModel.getFax()));
		}

		String webFax = null;

		if (StringUtil.isNotEmpty(personModel.getFax_web())) {
			TcIpTel tel = (TcIpTel) dao.findById(TcIpTel.class,
					Long.parseLong(personModel.getFax_web()));
			webFax = tel.getTelNum();
		}
		String userId = null;
		if (null == personModel.getUserid()
				&& StringUtil.isNotEmpty(personModel.getLoginUid())) {
			List<Users> userslist = dao.findByProperty(Users.class, "loginUid",
					personModel.getLoginUid());
			if (null != userslist && userslist.size() > 0) {
				userId = userslist.get(0).getUserid().toString();
			}
		} else {
			userId = personModel.getUserid().toString();
		}
		ldapUser.setUid(StringUtil.empty2Null(userId));
		ldapUser.setActiveFlag("1");
		ldapUser.setCardId(StringUtil.empty2Null(personModel.getCityCard()));
		ldapUser.setCardType(StringUtil.empty2Null(personModel
				.getCityCardType()));
		ldapUser.setCardUid(StringUtil.empty2Null(personModel.getCardUID()));
		ldapUser.setChangeLoginPwdFlag("1");
		ldapUser.setCn("CN");
		ldapUser.setDisplayName(StringUtil.empty2Null(personModel.getName()));
		ldapUser.setFeeType(null);
		ldapUser.setIsSystem(null);
		ldapUser.setJobId(null);
		ldapUser.setUserPassword(PropertiesUtil.getAnyConfigProperty(
				"user.default.pwd", PropertiesUtil.NJHW_CONFIG));
		ldapUser.setLoginPwd(PropertiesUtil.getAnyConfigProperty(
				"user.default.pwd", PropertiesUtil.NJHW_CONFIG));
		ldapUser.setLoginPwdMD5(PropertiesUtil.getAnyConfigProperty(
				"user.default.pwd", PropertiesUtil.NJHW_CONFIG));
		ldapUser.setLoginUid(StringUtil.empty2Null(personModel.getLoginUid()));
		ldapUser.setMemo(null);
		ldapUser.setOrderNum(null);
		ldapUser.setPlateNum(StringUtil.empty2Null(personModel.getCarNum()));
		ldapUser.setOrgId(StringUtil.empty2Null(personModel.getOrgId()
				.toString()));
		ldapUser.setOrgName(StringUtil.empty2Null(orgName));
		ldapUser.setResidentNo(StringUtil.empty2Null(personModel
				.getResidentNo()));
		ldapUser.setRoomInfo(StringUtil.empty2Null(personModel.getRoomNum()));
		ldapUser.setSn("SN");

		ldapUser.setWebFax(StringUtil.empty2Null(webFax));
		ldapUser.setTmpCard(StringUtil.empty2Null(personModel.getCityCard()));
		ldapUser.setUepMail(StringUtil.empty2Null(personModel.getEmail()));
		ldapUser.setUepMobile(StringUtil.empty2Null(personModel.getPhone()));
		ldapUser.setUepSex(StringUtil.empty2Null(personModel.getSex()));
		ldapUser.setUCode(StringUtil.empty2Null(personModel.getUcode()));
		return ldapUser;
	}

}
