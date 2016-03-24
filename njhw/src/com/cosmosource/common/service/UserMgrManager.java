/**
 * <p>文件名: UserMgrManager.java</p>
 * <p>描述：用户管理Manager</p>
 * <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
 * <p>公司: Cosmosource Beijing Office</p>
 * <p>All right reserved.</p>
 * @创建时间： 2010-8-30 下午03:21:23 
 * @作者： WXJ
 * @版本： V1.0
 * <p>类修改者		 修改日期			修改说明   </p>   
 * 
 */
package com.cosmosource.common.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.cosmosource.app.common.service.CommonManager;
import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.NjhwUsersPlatenum;
import com.cosmosource.app.entity.Users;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.PropertiesUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TAcOrg;
import com.cosmosource.common.entity.TAcUser;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * @类描述: 用户的管理类 调用通用DAO对用户的CRUD，及机构树数据的查询
 * 
 * @作者： WXJ
 */

public class UserMgrManager extends BaseManager {
	private CommonManager commonManager;
	
	public CommonManager getCommonManager() {
		return commonManager;
	}

	public void setCommonManager(CommonManager commonManager) {
		this.commonManager = commonManager;
	}
	
	/**
	 * 新增或是更新保存对象
	 * 
	 * @param entity
	 */
	public void saveUser(TAcUser entity) {
		if (StringUtil.isBlank(entity.getPassword())) {
			entity.setPassword(DigestUtils.md5Hex(PropertiesUtil
					.getResourcesProperty("default.user.loginname")));
			entity.setPlainpassword(PropertiesUtil
					.getResourcesProperty("default.user.loginname"));
			entity.setPwdModifyTime(new Date());
			entity.setPwdWrongCount(0L);
		}
		dao.saveOrUpdate(entity);

	}

	/**
	 * 查询用户列表数据
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	public Page<TAcUser> queryUsers(final Page<TAcUser> page,
			final TAcUser model) {
		List lPara = new ArrayList();

		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select t from TAcUser t ").append("where t.orgid=? ");
		lPara.add(model.getOrgid());
		if (StringUtil.isNotBlank(model.getUsercode())) {
			sbHql.append(" and t.usercode like ? ");
			lPara.add("%" + model.getUsercode() + "%");
		}

		if (StringUtil.isNotBlank(model.getUsername())) {
			sbHql.append("and t.username like ? ");
			lPara.add("%" + model.getUsername() + "%");
		}

		sbHql.append(" order by t.userid ");

		return dao.findPage(page, sbHql.toString(), lPara.toArray());
	}

	public void deleteUsers(String[] ids) {
		dao.deleteByIds(TAcUser.class, ids);
	}

	/**
	 * @描述: 生成xml数据
	 * @param nodeId
	 * @param orgid
	 * @return
	 */
	public String getOrgTreeData(String orgid, String ctx, String type) {
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");

			TAcOrg org = (TAcOrg) dao.findById(TAcOrg.class, new Long(orgid));
			Element el = root.addElement("item");
			el.addAttribute("text", org.getOrgname());
			el.addAttribute("id", org.getOrgid() + "");
			// el.addAttribute("open", "1");
			el.addAttribute("child", "1");
			Element elx = el.addElement("userdata");
			elx.addAttribute("name", "url");
			elx.addText(ctx + "/common/userMgr/list.act?nodeId="
					+ org.getOrgid() + "&orgtype=" + org.getOrgtype());

			// getOrgTreeDoc(new Long(orgid), el, ctx, org.getOrgtype());
			return doc.asXML();
		} else {
			Element root = DocumentHelper.createElement("tree");
			root.addAttribute("id", orgid);
			List<TAcOrg> list = dao
					.findByHQL("select t from TAcOrg t where t.parentid="
							+ orgid + " order by orgcode ");
			if (list.size() <= 500) {
				for (TAcOrg org : list) {
					Element el = root.addElement("item");
					el.addAttribute("text", org.getOrgname());
					el.addAttribute("id", org.getOrgid() + "");
					if ("1".equals(org.getIsbottom())) {
						el.addAttribute("child", "1");
					} else {
						el.addAttribute("child", "0");
					}
					// if(org.getParentid()==0){
					// el.addAttribute("open", "1");
					// }
					Element elx = el.addElement("userdata");
					elx.addAttribute("name", "url");
					if (!org.getOrgtype().equals("3")) {
						elx.addText(ctx + "/common/userMgr/list.act?nodeId="
								+ org.getOrgid() + "&orgtype="
								+ org.getOrgtype());
					} else {
						elx.addText(ctx
								+ "/common/userMgr/orgFrame.act?nodeId="
								+ org.getOrgid());
					}
				}
			}
			return root.asXML();
		}

	}

	/**
	 * @描述: 查询DOC
	 * @param orgId
	 * @param root
	 */
	public void getOrgTreeDoc(Long orgId, Element root, String ctx,
			String orgtype) {

		List<TAcOrg> list = dao
				.findByHQL("select t from TAcOrg t where t.parentid=" + orgId);
		if (!orgtype.equals("3")) {
			for (TAcOrg org : list) {
				Element el = root.addElement("item");
				el.addAttribute("text", org.getOrgname());
				el.addAttribute("id", org.getOrgid() + "");
				if (org.getParentid() == 0) {
					el.addAttribute("open", "1");
				}
				Element elx = el.addElement("userdata");
				elx.addAttribute("name", "url");
				if (!org.getOrgtype().equals("3")) {
					elx.addText(ctx + "/common/userMgr/list.act?nodeId="
							+ org.getOrgid() + "&orgtype=" + org.getOrgtype());
				} else {
					elx.addText(ctx + "/common/userMgr/orgFrame.act?nodeId="
							+ org.getOrgid());
				}

				if ("1".equals(org.getIsbottom())) {
					getOrgTreeDoc(org.getOrgid(), el, ctx, org.getOrgtype());
				}
			}
		}
	}

	/**
	 * 检查用户名是否唯一.
	 * 
	 * @return loginName在数据库中唯一或等于oldLoginName时返回true.
	 */
	public boolean isLoginNameUnique(String newLoginName, String oldLoginName) {
		return dao.isPropertyUnique(TAcUser.class, "loginname", newLoginName,
				oldLoginName);
	}

	/**
	 * *
	 * 
	 * @描述: TODO(更新用户密码)
	 * @param user
	 *            add by sjy 2010-12-23
	 */
	public void updateUser(TAcUser user) {
		if (user != null) {
			dao.saveOrUpdate(user);
		}
	}

	/**
	 * 
	 * @描述: 通过供应商编码查询用户的机构id
	 * @param loginname
	 * @return add by sjy 2010-12-24 update by sjy 2011-1-7
	 *         改为通过登录名找机构id,因为供应商编码不是唯一的，登录名是
	 */
	public TAcUser getOrgidByLoginname(String loginname) {
		TAcUser user = new TAcUser();
		List userList = dao.findByProperty(TAcUser.class, "loginname",
				loginname);
		if (userList != null && userList.size() >= 1) {
			user = (TAcUser) userList.get(0);
		}
		return user;
	}

	/**
	 * 生成随机密码，以当前时间的MD5值的前5位再加3位随机数字
	 * 
	 * @return
	 */
	public String CreatePass() {
		String strmd = DigestUtils.md5Hex(DateUtil.date2Str(new Date(),
				"yyyyMMdd"));
		strmd = strmd.substring(0, 5);
		int rand = new Double(Math.random() * 3220000).intValue();
		String num = String.valueOf(rand).substring(0, 3);
		return strmd + num;
	}

	/**
	 * 查询机构列表数据
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	public Page<TAcOrg> queryOrgs(final Page<TAcOrg> page, final TAcOrg model) {
		List lPara = new ArrayList();

		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select t from TAcOrg t ").append("where t.parentid=? ");
		lPara.add(model.getParentid());
		if (StringUtil.isNotBlank(model.getOrgcode())) {
			sbHql.append(" and t.orgcode like ? ");
			lPara.add("%" + model.getOrgcode() + "%");
		}
		if (StringUtil.isNotBlank(model.getOrgname())) {
			sbHql.append("and t.orgname like ? ");
			lPara.add("%" + model.getOrgname() + "%");
		}
		if (StringUtil.isNotBlank(model.getTaxno())) {
			sbHql.append("and t.taxno like ? ");
			lPara.add("%" + model.getTaxno() + "%");
		}
		if (StringUtil.isNotBlank(model.getTaxname())) {
			sbHql.append("and t.taxname like ? ");
			lPara.add("%" + model.getTaxname() + "%");
		}
		sbHql.append(" order by t.orgid ");

		return dao.findPage(page, sbHql.toString(), lPara.toArray());
	}

	// /**
	// * 初始化User的延迟加载关联roleList.
	// */
	// public void initUser(TAcUser user) {
	// dao.initProxyObject(user.getRoleList());
	// }

	public void findOut() {

		List list = dao.findByHQL("from TAcUser t ");
		System.out.println("当前用户数：　　　" + list.size());

	}

	// /** *
	// * @描述:保存调查表信息
	// * @param user
	// * add by sjy 2010-12-23
	// */
	// public void saveLotusQa(TLotusVendorQa entity){
	// if(entity!=null){
	// List list = dao.findByProperty(TLotusVendorQa.class, "loginname",
	// entity.getLoginname());
	// if(list!=null&&list.size()>0){
	// //
	// }else{
	// dao.saveOrUpdate(entity);
	// }
	//			
	//			
	// }
	// }

	/**
	 * @描述:更新用户状态
	 * @作者：WXJ
	 * @日期：2012-6-5
	 * @param loginname
	 *            用户登录名
	 * @param state
	 *            用户状态
	 * @return
	 */
	public int updateUserState(final String loginname, final String status) {
		Integer result = new Integer(0);
		if (StringUtils.isNotEmpty(loginname)) {
			result = (Integer) sqlDao.getSqlMapClientTemplate().execute(
					new SqlMapClientCallback() {
						public Object doInSqlMapClient(SqlMapExecutor executor)
								throws SQLException {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("loginname", loginname);
							map.put("status", status);
							return executor.update(
									"CommonSQL.updateUserStatus", map);
						}
					});
		}

		return result;
	}

	/**
	 * @描述:更新输入密码连续错误次数为零
	 * @作者：WXJ
	 * @日期：2012-6-5
	 * @param loginname
	 *            用户登录名
	 * @return
	 */
	public int updatePasswordWrongCountZero(final String loginname) {
		Integer result = new Integer(0);
		if (StringUtils.isNotEmpty(loginname)) {
			result = (Integer) sqlDao.getSqlMapClientTemplate().execute(
					new SqlMapClientCallback() {
						public Object doInSqlMapClient(SqlMapExecutor executor)
								throws SQLException {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("loginname", loginname);
							return executor.update(
									"CommonSQL.clearPasswordWrongCount", map);
						}
					});
		}

		return result;
	}

	/**
	 * @描述:更新用户状态为正常，并清空锁定时间，秘密错误次数为零
	 * @作者：WXJ
	 * @日期：2012-6-8
	 * @param loginname
	 *            用户登录名
	 * @return
	 */
	public int updateUserUnLock(final String loginname) {
		Integer result = new Integer(0);
		if (StringUtils.isNotEmpty(loginname)) {
			result = (Integer) sqlDao.getSqlMapClientTemplate().execute(
					new SqlMapClientCallback() {
						public Object doInSqlMapClient(SqlMapExecutor executor)
								throws SQLException {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("loginname", loginname);
							return executor.update(
									"CommonSQL.updateUserUnLock", map);
						}
					});
		}

		return result;
	}

	/**
	 * @描述:个人信息的查询
	 * @作者：qiyanqiang
	 * @日期：2013-05-10
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap searchPersonInfo(Map map) {
		List<HashMap> list = sqlDao.findList("CommonSQL.searchPersonIfo", map);
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}
	
	
	
	/**
	 * @描述:卡信息的查询  
	 * @作者：qiyanqiang
	 * @日期：2013-05-10
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap searchCardInfo(Map map) {
		List<HashMap> list = sqlDao.findList("CommonSQL.searchCardsIfo", map);
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	/**
	 * @描述:个人信息的保存
	 * @作者：qiyanqiang
	 * @日期：2013-05-10
	 * @return
	 */

	public void savePersonInfo(NjhwUsersExp object , String carNums, String isFastenCarNums) {
		dao.saveOrUpdate(object);
		
		// NjhwUsersPlatenum
		// 之前的全部删掉, 有新的车牌号，就以新的为准，
		dao.batchExecute("delete from NjhwUsersPlatenum t where t.userid = ?", object.getUserid());
		if(StringUtil.isNotEmpty(carNums)) {
			String[] carNumList = carNums.split(",");
			String[] isFastens = isFastenCarNums.split(",");
			for(int i = 0; i <= carNumList.length - 1; i++) {
				NjhwUsersPlatenum plateNum = new NjhwUsersPlatenum();
				
				plateNum.setNupPn(carNumList[i].toString());
				plateNum.setUserid(object.getUserid());
				plateNum.setNupFlag(isFastens[i]);
				plateNum.setInsertDate(DateUtil.getSysDate());
				plateNum.setInsertId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
				
				dao.save(plateNum);
			}
		}
		dao.flush();

	}
	
	/**
	 * @描述:个人信息的保存
	 * @作者：hj
	 * @日期：2013-07-19
	 * @return
	 */

	public void updateUserExp(NjhwUsersExp object) {
		dao.saveOrUpdate(object);
	}

	/**
	 * 
	 * @title: findNjhwTscardByCardId
	 * @description: 通过市民卡号得到市民卡信息对象
	 * @author qiyanqiang
	 * @param userId
	 * @return
	 * @date 2013-5-11 
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public NjhwTscard findNjhwTscardByCardId(String cardId) {
		List<NjhwTscard> list = dao.findByProperty(NjhwTscard.class,
				"cardId", cardId);
		if (list != null && list.size() >= 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @title: findUsersPlatenum
	 * @description: 通过用户的ID 得到用户车牌号的对象
	 * @author qiyanqiang
	 * @param
	 * @return
	 * @date 2013-5-11
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public NjhwUsersPlatenum findUsersPlatenum(Long userid) {
		List<NjhwUsersPlatenum> list = dao.findByProperty(NjhwUsersPlatenum.class,
				"userid", userid);
		if (list != null && list.size() >= 1) {
			return list.get(0);
		} else {
			return null;
		}

	}
	
	
	
	/**
	 * 
	 * @title: findUsersExpById
	 * @description: 通过用户的ID 得到用户扩展的对象
	 * @author qiyanqiang
	 * @param
	 * @return
	 * @date 2013-5-11
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public NjhwUsersExp findUsersExpById(Long userid) {
		List<NjhwUsersExp> list = dao.findByProperty(NjhwUsersExp.class,
				"userid", userid);
		if (list != null && list.size() >= 1) {
			return list.get(0);
		} else {
			return null;
		}

	}

	/**
	 * @description: 根据验证码更新用户的手机号码
	 * @author qiyanqiang
	 * @param
	 * @return
	 * @date 2013-5-11 
	 * @throws
	 */
	public void saveUserTel(Object object) {
		System.err.println();
		dao.saveOrUpdate(object);
	}

	/**
	 * @description:查找登录的用户是否存在如果小于1表示不存在可以进行更新
	 * @author qiyanqiang
	 * @param
	 * @return
	 * @date 2013-5-11 
	 * @throws
	 */
	@SuppressWarnings("null")
	public boolean findUserName(Long userId, String loginId) {
		boolean res = false;

		List<Users> users = dao.findByHQL(
				" select u  from Users u where u.userid!=? and u.loginUid=?",
				userId, loginId);
		if (users.size() < 1) {
			res = true;
		}
		return res;
	}

	/**
	 * @description:保存登录用户名字
	 * @author qiyanqiang
	 * @param
	 * @return
	 * @date 2013-5-11
	 * @throws
	 */
	public void saveLoginUser(Object object) {
		System.err.println();
		dao.saveOrUpdate(object);
	}

	/**
	 * @description:查找登录用户的 id
	 * @author qiyanqiang
	 * @param
	 * @return
	 * @date 2013-5-11
	 * @throws
	 */
	public Users getUsersById(Long userId) {
		try {
			return (Users) dao.findById(Users.class, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	
	
	
	/**
	 * 
	 * @描述:查询用户的车牌
	 * @param map
	 * @author qiyanqiang
	 * @时间  2013- 05 -17
	 */
   	
	@SuppressWarnings("unchecked")
	public  List searchUserLicensePlate(Map map) {
		List<HashMap> list = sqlDao.findList("CommonSQL.searchUserLicensePlate", map);
		if (list != null && list.size() > 0)
			return list;
		else
			return null;
	}
	
	
	/**
	 * 
	 * @描述:  查询用户闸机、门禁、门锁：
	 * @param map
	 * @author qiyanqiang
	 * @时间  2013- 05 -17
	 */
   	
	@SuppressWarnings("unchecked")
	public  List  searchAllFacility(Map map) {
		List<HashMap> list = sqlDao.findList("CommonSQL.searchAllFacility", map);
		if (list != null && list.size() > 0)
			return list;
		else
			return null;
	}
	
	
	
	
	/**
	 * @description:挂失
	 * @author qyq
	 * @param HashMap map
	 * @return 
	 */
	public int modifyCardIsLosted(String optType,String cityCard) {
		int num = 0;
		List<NjhwTscard> list =super.dao.findByProperty(NjhwTscard.class,"cardId", cityCard);
        if (null != list && list.size()>0)
		{   
        	NjhwTscard tsCard = null;
        	for (int i = 0; i < list.size(); i++)
			{   
        		tsCard = list.get(i);
        		// 验证市民卡号是否存在
        		if (tsCard == null)	num = 1;
        		else {	// 根据操作进行修改			
        			if ("confirmLosted".equals(optType)) {
        				tsCard.setSystemLosted(NjhwTscard.SYSTEM_LOSTED_LOSTED);
//        				//取消权限
//        				commonManager.delAuthority(cityCard);
        			}else if ("cancelLosted".equals(optType)) {
        				tsCard.setSystemLosted(NjhwTscard.SYSTEM_LOSTED_UNLOSTED);
//        				//授予权限
//        				commonManager.addAuthority(cityCard);
        			}
        			
        			
        			dao.update(tsCard);
        			dao.flush();
			}
        	
		}
		
		
		}
		return num;
	}

	
	/**
	 * @description:校验此卡号是否是当前管理员
	 * @author hj
	 * @param String cardId
	 * @return boolean
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean checkAdmin(String cardId) {
		
		Long userid = (Long) Struts2Util.getSession().getAttribute(Constants.USER_ID);
		String orgId = Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString();

		Map<String,Object> condtion = new HashMap<String,Object>();						
		condtion.put("orgId", orgId);
		condtion.put("cardId", cardId);
		
		List<Map> listMap = sqlDao.findList("PersonnelUnitSQL.isUnitAdmin", condtion);
		Long useridCompare = 0l;
		if (null == listMap || listMap.size() == 0) {
			useridCompare = (Long) listMap.get(0).get("USERID");
		}
		return useridCompare.equals(userid);
	}

	
}
