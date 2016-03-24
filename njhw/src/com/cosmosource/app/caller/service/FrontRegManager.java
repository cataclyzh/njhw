package com.cosmosource.app.caller.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.entity.VmVisit;
import com.cosmosource.app.entity.VmVisitAuxi;
import com.cosmosource.app.entity.VmVisitorinfo;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

/**
 * 发放临时卡
 * 
 * @description: TODO
 * @author gxh
 * @date 2013-4-2 下午11:42:06
 */
public class FrontRegManager extends BaseManager {

	/**
	 * 前台查找已确认访客
	 * 
	 * @title: queryVisitor
	 * @description: TODO
	 * @author gxh
	 * @param page
	 * @param viName
	 * @param residentNo
	 * @param vsSt
	 * @param vsFlag
	 * @return
	 * @date 2013-4-2 下午11:42:28
	 * @throws
	 */
	public Page<HashMap> queryVisitor(final Page<HashMap> page,
			final String viName, String vsFlag, String beginTime,
			String endTime, String residentNo, String cardId,String type) {

		Map<String, Object> cMap = new HashMap<String, Object>();
		cMap.put("viName", viName);
		cMap.put("vsFlag", vsFlag);
		cMap.put("vsSt", beginTime);
		cMap.put("vsEt", endTime);
		cMap.put("residentNo", residentNo);
		cMap.put("cardId", cardId);
		List<String> vsFlagList = new ArrayList<String>();
		vsFlagList.add(VmVisit.VS_FLAG_SURE);// 确定
		vsFlagList.add(VmVisit.VS_FLAG_COME);// 到访的
		vsFlagList.add(VmVisit.VS_FLAG_FINISH);// 正常结束
		vsFlagList.add(VmVisit.VS_FLAG_ERROR);// 异常结束
		vsFlagList.add(VmVisit.VS_FLAG_CANCELED);// 取消
		// cMap.put("vsFlagList",vsFlagList);
		if ("export".equals(type)) {
			 page.setResult(sqlDao.findList("CallerSQL.selectRegCallerPageList1",cMap));
			 return page;
		}else {
			return sqlDao.findPage(page, "CallerSQL.selectRegCallerPageList1", cMap);
		}
		
	}
	
	/**
	 * 前台查找已确认访客
	 * 
	 * @title: queryVisitor
	 * @description: TODO
	 * @author gxh
	 * @param page
	 * @param viName
	 * @param residentNo
	 * @param vsSt
	 * @param vsFlag
	 * @return
	 * @date 2013-4-2 下午11:42:28
	 * @throws
	 */
	public boolean cancelVistor(Long vsId, String vsFlag) {

		Map<String, Object> cMap = new HashMap<String, Object>();
		boolean result = true;
		List parList;
		try {
			cMap.put("vsId", vsId);
			cMap.put("vsFlag", vsFlag);
			parList = new ArrayList();
			parList.add(cMap);
			sqlDao.batchUpdate("CallerSQL.updateFlagMyCall", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 查看基本信息和访问事物
	 * 
	 * @title: findVisitorsinfo
	 * @description: TODO
	 * @author gxh
	 * @param vsId
	 * @return
	 * @date 2013-4-2 下午11:42:48
	 * @throws
	 */
	public Map findVisitorsinfo(String vsId, String residentNo, String resBak1) {

		Map<String, String> vMap = new HashMap<String, String>();
		vMap.put("vsId", vsId);
		vMap.put("residentNo", residentNo);
		vMap.put("resBak1", resBak1);
		// List<String> vsFlagList = new ArrayList<String>();

		List<Map> list = sqlDao.findList("CallerSQL.findVisitorsinformation1",
				vMap);
		if (list != null && list.size() > 0) {
			return list.get(0);

		} else {
			return null;
		}

	}

	/**
	 * 查找绑定副卡的条数
	 * 
	 * @title: selectNum
	 * @description: TODO
	 * @author gxh
	 * @param vsId
	 * @return
	 * @date 2013-4-2 下午11:42:59
	 * @throws
	 */
	public List<VmVisitAuxi> selectNum(Long vsId) {
		List<VmVisitAuxi> vvaList = dao.findByProperty(VmVisitAuxi.class,
				"vsId", vsId);
		return vvaList;

	}

	/**
	 *根据id 得到访客事物对象
	 * 
	 * @title: getVisitByid
	 * @description: TODO
	 * @author gxh
	 * @param vsId
	 * @return
	 * @date 2013-4-2 下午11:43:08
	 * @throws
	 */
	public VmVisit getVisitByid(Long vsId) {

		VmVisit visit = new VmVisit();
		List visitList = dao.findByProperty(VmVisit.class, "vsId", vsId);
		if (visitList != null && visitList.size() >= 1) {
			visit = (VmVisit) visitList.get(0);
			return visit;
		}
		return null;
	}
	
	public List getFatherCardIdList() {
		List list = null;
		try{
			Map<String,String> pMap =new HashMap<String, String>();
			list = sqlDao.findList("CallerSQL.selectFatherCardIdList", pMap);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	

	/**
	 * 退主卡
	 * 
	 * @title: exitCardByid
	 * @description: TODO
	 * @author gxh
	 * @param cardId
	 * @return
	 * @date 2013-4-2 下午11:43:20
	 * @throws
	 */
	public VmVisit exitCardByid(String cardId) {

		VmVisit visit = new VmVisit();
		List visitList = dao.findByProperty(VmVisit.class, "cardId", cardId);
		if (visitList != null && visitList.size() >= 1) {
			visit = (VmVisit) visitList.get(0);
			return visit;
		}
		return null;
	}

	/**
	 * 退副卡
	 * 
	 * @title: exitCardFByid
	 * @description: TODO
	 * @author gxh
	 * @param cardId
	 * @return
	 * @date 2013-4-2 下午11:43:31
	 * @throws
	 */
	public VmVisitAuxi exitCardFByid(String cardId) {

		VmVisitAuxi visitf = new VmVisitAuxi();
		List visitList = dao
				.findByProperty(VmVisitAuxi.class, "cardId", cardId);
		if (visitList != null && visitList.size() >= 1) {
			visitf = (VmVisitAuxi) visitList.get(0);
			return visitf;
		}
		return null;
	}

	/**
	 * 新增或是更新保存对象访问事物
	 * 
	 * @title: saveEntity
	 * @description: TODO
	 * @author gxh
	 * @param entity
	 * @date 2013-4-2 下午11:43:41
	 * @throws
	 */
	public void saveEntity(VmVisit entity) {
		try {
			dao.saveOrUpdate(entity);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * 新增或是更新保存对象副卡
	 * 
	 * @title: saveSoncard
	 * @description: TODO
	 * @author gxh
	 * @param entity
	 * @date 2013-4-2 下午11:43:54
	 * @throws
	 */
	public void saveSoncard(VmVisitAuxi entity) {
		try {
			dao.save(entity);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void saveSoncard1(VmVisitAuxi entity) {
		try {
			dao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void deleteSoncard(VmVisitAuxi entity) {
		try {
			dao.delete(entity);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * 查询事务是否有没有归还的副卡
	 * 
	 * @title: getUnBackVVACard
	 * @description: TODO
	 * @author gxh
	 * @param vsId
	 * @return
	 * @date 2013-4-2 下午11:44:03
	 * @throws
	 */
	public List<VmVisitAuxi> getUnBackVVACard(Long vsId) {

		try {
			return dao
					.findByHQL(
							"select vva from VmVisitAuxi vva where vsId = ? and vaReturn = ? ",
							vsId, "2");
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 修改卡副卡归还状态
	 * 
	 * @title: updVmVisitStatus
	 * @description: TODO
	 * @author gxh
	 * @param vsId
	 * @date 2013-4-2 下午11:44:26
	 * @throws
	 */
	public void updVmVisitStatus(Long vsId) {

		VmVisit vmVisit = (VmVisit) dao.findById(VmVisit.class, vsId);
		vmVisit.setVsretSub(VmVisit.VS_RET_SUB_YES);// 副卡还完
		if (!vmVisit.getVsReturn().equals(VmVisit.VS_RETURN_RNO))// 临时卡也还
		{
			vmVisit.setVsFlag(VmVisit.VS_FLAG_FINISH);

		}
		dao.update(vmVisit);

	}

	/**
	 * 
	 * @title: upVmVisit
	 * @description: 修改访问事务表
	 * @author herb
	 * @return
	 * @date Apr 24, 2013 8:12:41 AM
	 * @throws
	 */
	public void updateVmVisit(VmVisit vmVisit) {
		VmVisit visit = (VmVisit) dao
				.findById(VmVisit.class, vmVisit.getVsId());
		visit.setCardType(vmVisit.getCardType());
		visit.setCardId(vmVisit.getCardId());
		visit.setVsNum(vmVisit.getVsNum());
		dao.update(visit);
	}

	/**
	 * 根据id删除副卡
	 * 
	 * @title: delete
	 * @description: TODO
	 * @author gxh
	 * @param fid
	 * @date 2013-4-6 下午03:34:54
	 * @throws
	 */
	public int deleteVmVisitAuxi(long fid) {
		try {
			dao.deleteById(VmVisitAuxi.class, fid);
			return 1;
		} catch (Exception e) {

			e.printStackTrace();
			return 0;
		}

	}

	/**
	 * 根据被访者id找到扩展信息
	 * 
	 * @title: findUsersExpByUserid
	 * @description: TODO
	 * @author gxh
	 * @param userid
	 * @return
	 * @date 2013-4-12 上午11:33:15
	 * @throws
	 */
	public Map findUsersExpByUserid(Long userid) {

		HashMap parMap = new HashMap();
		parMap.put("userid", userid);

		List<HashMap> telList = sqlDao.findList(
				"CallerSQL.loadMacAndNumByUserId", parMap);
		if (telList != null && telList.size() > 0) {
			return telList.get(0);

		} else {
			return null;
		}
	}

	/**
	 * 根据身份证号找到扩展信息
	 * 
	 * @title: findUsersExpByReNo
	 * @description: TODO
	 * @author gxh
	 * @param residentNo
	 * @return
	 * @date 2013-5-18 下午02:39:47
	 * @throws
	 */
	public Map findUsersExpByReNo(String residentNo) {
		Map<String, String> vMap = new HashMap<String, String>();
		vMap.put("residentNo", residentNo);

		List<Map> list = sqlDao.findList("CallerSQL.findIndorUser", vMap);

		if (list != null && list.size() > 0) {
			return list.get(0);

		} else {
			return null;
		}
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
	public List<Map> dailyVMCountInfo(String vsSt, String vsEt) {

		Map<String, Object> vMap = new HashMap<String, Object>();
		vMap.put("vsSt", vsSt);
		vMap.put("vsEt", vsEt);
		List<String> vsFlagList = new ArrayList<String>();
		vsFlagList.add(VmVisit.VS_FLAG_COME);
		vsFlagList.add(VmVisit.VS_FLAG_FINISH);
		vsFlagList.add(VmVisit.VS_FLAG_ERROR);
		vMap.put("vsFlagList",vsFlagList);
		List<Map> list = sqlDao.findList("CallerSQL.dailyVMCountInfo", vMap);
		if (list != null && list.size() > 0) {
			return list;

		} else {
			return null;
		}
	}

	/**
	 * 根据身份证号查找判断用户是否存在
	 * 
	 * @title: exsitNjhwTscardByCardId
	 * @description: TODO
	 * @author gxh
	 * @param cardId
	 * @return
	 * @date 2013-4-22 下午10:01:48
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean exsitNjhwTscardByRnId(String residentNo) {
		List<NjhwTscard> list = super.dao.findByProperty(NjhwTscard.class,
				"residentNo", residentNo);
		if (list != null && list.size() >= 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据身份证号判断扩展信息是否存在
	 * 
	 * @title: exsitNjhwTscardByRnId1
	 * @description: TODO
	 * @author gxh
	 * @param residentNo
	 * @return
	 * @date 2013-5-20 下午05:15:46
	 * @throws
	 */
	@SuppressWarnings("all")
	public boolean exsitNjhwExpByRnId1(String residentNo) {
		List<NjhwUsersExp> list = super.dao.findByProperty(NjhwUsersExp.class,
				"residentNo", residentNo);
		if (list != null && list.size() >= 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @title: findVmVisitByCardId
	 * @description: 根据卡号 查询全部的事务
	 * @author herb
	 * @param page
	 * @param cardId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @date Apr 22, 2013 9:22:13 PM
	 * @throws
	 */
	public Page<HashMap> findVmVisitByCardId(Page<HashMap> page, String cardId,
			String startTime, String endTime) {
		Map<String, Object> vMap = new HashMap<String, Object>();
		vMap.put("startTime", startTime);
		vMap.put("endTime", endTime);
		vMap.put("cardId", cardId);
		List<String> vsFlagList = new ArrayList<String>();
		vsFlagList.add(VmVisit.VS_FLAG_COME);
		vsFlagList.add(VmVisit.VS_FLAG_FINISH);
		vMap.put("vsFlagList", vsFlagList);
		Page<HashMap> list = sqlDao.findPage(page,
				"CallerSQL.findVmVisitByCardId", vMap);
		return list;
	}

	/**
	 * 通过机构名称得到受访者名称
	 * 
	 * @title: findOrgUserId
	 * @description: TODO
	 * @author gxh
	 * @param map
	 * @return
	 * @date 2013-4-23 下午11:17:38
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findOrgUserId(final Map<String, Integer> map) {
		List<Map> list = null;
		try {
			list = sqlDao.findList("CallerSQL.getUserByOrgId", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 
	 * @title: getVisitInfoDetail
	 * @description: 根据事务id查询访客详细信息
	 * @author herb
	 * @param vsId
	 * @return
	 * @date Apr 23, 2013 9:27:14 PM
	 * @throws
	 */
	public HashMap getVisitInfoDetail(String vsId) {
		Map condition = new HashMap();
		condition.put("vsId", vsId);
		List<HashMap> list =null;
		List<HashMap> listVis = sqlDao.findList("CallerSQL.findVis",
				condition);
		try {
			if(listVis!=null && listVis.size()>0)
			{
			 list = sqlDao.findList("CallerSQL.findVmVisitInfoByVsId2",
					condition);
			}else{
				list = sqlDao.findList("CallerSQL.findVmVisitInfoByVsId",
						condition);
			}
			if (null != list && list.size()>0) {
				System.out.println(list.get(0).get("VS_ST"));
				return list.get(0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public String getPhoneByid(long userId) {
		
		Map vMap = new HashMap<String, String>();
		vMap.put("userid", userId);

		List<Map> list = sqlDao.findList("CallerSQL.loadMacAndNumByUserId", vMap);

		if (list != null && list.size() > 0) {
			return list.get(0).get("TEL_NUM")==null?"":list.get(0).get("TEL_NUM").toString();

		} else {
			return "";
		}
	}

	/**
	 * 
	 * @title: deleteVisitAuxiByVsId
	 * @description: 删除全部附卡
	 * @author herb
	 * @param vsId
	 * @date Apr 24, 2013 7:31:13 AM
	 * @throws
	 */
	public void deleteVisitAuxiByVsId(String vsId) {
		dao.createQuery("delete from VmVisitAuxi  where vsId = ?",
				Long.valueOf(vsId)).executeUpdate();
		// dao.findByHQL("delete from VmVisitAuxi  where vsId = ?", vsId);
	}

	/**
	 * 
	 * @title: updateVmVisitorInfo
	 * @description: 修改用户扩展信息
	 * @author herb
	 * @param info
	 * @date Apr 25, 2013 2:50:37 PM
	 * @throws
	 */
	public void updateVmVisitorInfo(VmVisitorinfo info) {
		VmVisitorinfo visit = (VmVisitorinfo) dao.findById(VmVisitorinfo.class,
				info.getViId());
		visit.setResBak2(info.getResBak2());
		dao.update(visit);
	}

	/**
	 * 保存市民卡
	 * 
	 * @title: saveNtCard
	 * @description: TODO
	 * @author gxh
	 * @param nt
	 * @date 2013-4-26 上午12:05:21
	 * @throws
	 */
	public void saveNtCard(NjhwTscard nt) {
		try {
			dao.saveOrUpdate(nt);
		} catch (Exception e) {
			e.printStackTrace();

		}
		// dao.save(nt);
	}

	/**
	 * @description:根据组织的id得到用户的信息
	 * @author cjw
	 * @return List
	 * @date 2013-03-21
	 */
	@SuppressWarnings("unchecked")
	public Users getUsersByOrgIdAndUserName(String orgId, String userName) {
		List<Users> list = dao
				.findByHQL(
						"select u from Users u where u.orgId = ? and trim(u.displayName)=?",
						Long.parseLong(orgId), userName);
		if (list != null && list.size() >= 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据卡号找到市民卡号
	 * 
	 * @title: findNjhwTscard
	 * @description: TODO
	 * @author gxh
	 * @param residentNo
	 * @return
	 * @date 2013-5-21 下午02:11:07
	 * @throws
	 */
	public NjhwTscard findNjhwTscardBycardId(String cardId) {
		NjhwTscard exp = new NjhwTscard();
		List expList = dao.findByProperty(NjhwTscard.class, "cardId", cardId);
		if (expList != null && expList.size() >= 1) {
			exp = (NjhwTscard) expList.get(0);
			return exp;
		}
		return null;
	}

	/**
	 * 
	 * @Title: getSelectRespondents
	 * @Description: 弹出页面机构及用户树 受访人
	 * @author SQS
	 * @date 2013-5-19 上午11:19:47
	 * @param @param orgid
	 * @param @param roomid
	 * @param @param rootElement
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String getSelectRespondents(String orgid, String ctx, String type) {
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");

			Org org = (Org) this.findById(Org.class, Long.parseLong(orgid));
			Element el = root.addElement("item");
			el.addAttribute("text", org.getName());
			el.addAttribute("id", orgid + "-o");
			el.addAttribute("child", "1");
			el.addAttribute("open", "1");

			return doc.asXML();
		} else {
			Element rootElement = DocumentHelper.createElement("tree");
			rootElement.addAttribute("id", orgid);

			List<Org> list = dao.findByHQL(
					"select t from Org t where t.PId=? order by orderNum",
					new Long(orgid.substring(0, orgid.indexOf("-o"))));
			for (Org org : list) {
				Element el = rootElement.addElement("item");
				el.addAttribute("text", org.getName().trim());
				el.addAttribute("id", org.getOrgId() + "-o");
				el.addAttribute("child", "1");
			}
			List<Users> userList = dao.findByHQL(
					"select t from Users t where t.orgId=? ", new Long(orgid
							.substring(0, orgid.indexOf("-o"))));
			for (Users user : userList) {
				Element el = rootElement.addElement("item");
				el.addAttribute("text", user.getDisplayName().trim());
				el.addAttribute("id", user.getUserid() + "-u");

				el.addAttribute("im0", "user.gif");
				el.addAttribute("im1", "user.gif");
				el.addAttribute("im2", "user.gif");
			}

			return rootElement.asXML();
		}
	}

	/**
	 * 
	 * @Title: getSelectRespondents
	 * @Description: 弹出页面机构及用户树 受访人
	 * @author SQS
	 * @date 2013-5-19 上午11:19:47
	 * @param @param orgid
	 * @param @param roomid
	 * @param @param rootElement
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String getSelectRespondentsForAll(String orgid, String ctx,
			String type) {
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");

			Org org = (Org) this.findById(Org.class, 2L);
			Element el = root.addElement("item");
			el.addAttribute("text", org.getName());
			el.addAttribute("id", org.getOrgId() + "-o");
			el.addAttribute("child", "1");
			el.addAttribute("open", "1");

			return doc.asXML();
		} else {
			Element rootElement = DocumentHelper.createElement("tree");
			rootElement.addAttribute("id", orgid);

			List<Org> list = dao.findByHQL(
					"select t from Org t where t.PId=? order by orderNum",
					new Long(orgid.substring(0, orgid.indexOf("-o"))));
			for (Org org : list) {
				Element el = rootElement.addElement("item");
				el.addAttribute("text", org.getName().trim());
				el.addAttribute("id", org.getOrgId() + "-o");
				el.addAttribute("child", "1");
			}
			List<Users> userList = dao.findByHQL(
					"select t from Users t where t.orgId=? ", new Long(orgid
							.substring(0, orgid.indexOf("-o"))));
			for (Users user : userList) {
				Element el = rootElement.addElement("item");
				el.addAttribute("text", user.getDisplayName().trim());
				el.addAttribute("id", user.getUserid() + "-u");

				el.addAttribute("im0", "user.gif");
				el.addAttribute("im1", "user.gif");
				el.addAttribute("im2", "user.gif");
			}

			return rootElement.asXML();
		}
	}

	/**
	 * @description:取得当前登陆用户所属的顶级单位
	 * @author zh
	 * @param HashMap
	 *            map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> getTopOrgId(Long userid) {
		Map pMap = new HashMap();
		pMap.put("userid", userid);
		List<HashMap> list = this.findListBySql(
				"CallerSQL.getTopOrgIdByUserId", pMap);
		return list;
	}

	/**
	 * 根据id找到组织
	 * 
	 * @title: getOrgByid
	 * @description: TODO
	 * @author gxh
	 * @param orgId
	 * @return
	 * @date 2013-5-24 下午05:51:36
	 * @throws
	 */
	public Org getOrgByid(Long orgId) {
		Org exp = new Org();
		List expList = dao.findByProperty(Org.class, "orgId", orgId);
		if (expList != null && expList.size() >= 1) {
			exp = (Org) expList.get(0);
			return exp;
		}
		return null;

	}

	/**
	 * 根据用户姓名模糊查找
	 * 
	 * @title: findOrgUserId
	 * @description: TODO
	 * @author gxh
	 * @param map
	 * @return
	 * @date 2013-5-14 下午10:20:28
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findUsers(String displayName, String id) {
		Map vMap = new HashMap();
		vMap.put("displayName", displayName);
		List<Map> list = new ArrayList();
		if (id != null) {
			Long orgId = Long.parseLong(id);
			vMap.put("orgId", orgId);
			list = sqlDao.findList("CallerSQL.getUsersAndOrg1", vMap);
		} else {
			list = sqlDao.findList("CallerSQL.getUsersAndOrg", vMap);
		}

		if (list != null && list.size() > 0) {
			return list;

		} else {
			return null;
		}
	}

	public VmVisitAuxi findVmVisitAuxiById(Long vaId) {
		VmVisitAuxi exp = new VmVisitAuxi();
		List expList = dao.findByProperty(VmVisitAuxi.class, "vaId", vaId);
		if (expList != null && expList.size() >= 1) {
			exp = (VmVisitAuxi) expList.get(0);
			return exp;
		}
		return null;
	}

	public VmVisitAuxi findVmVisitAuxiByVsId(Long vsId) {
		VmVisitAuxi exp = new VmVisitAuxi();
		List expList = dao.findByProperty(VmVisitAuxi.class, "vsId", vsId);
		if (expList != null && expList.size() >= 1) {
			exp = (VmVisitAuxi) expList.get(0);
			return exp;
		}
		return null;
	}

	public void deleteVmVisitAuxiById(Long vsId) {
		VmVisitAuxi exp = new VmVisitAuxi();
		List expList = dao.findByProperty(VmVisitAuxi.class, "vsId", vsId);
		if (expList != null && expList.size() >= 1) {
			for (int i = 0; i < expList.size(); i++) {
				exp = (VmVisitAuxi) expList.get(i);
				dao.delete(exp);
			}
		}
	}

	public boolean findVmVisitorinfoByTime(String curvsid, String carId,
			String startTime, String endTime) {

		Map vMap = new HashMap();
		List<Map> list = new ArrayList();

		vMap.put("cardId", carId);
		vMap.put("beginTime", startTime);
		vMap.put("endTime", endTime);

		list = sqlDao.findList("CallerSQL.findVisitorsByTimeFormVM", vMap);

		if (list != null && list.size() > 0) {
			if (curvsid != null && !"".equals(curvsid)) {
				if (list.size() == 1
						&& curvsid.equals(list.get(0).get("VS_ID").toString())) {
					// DO Nothing
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		list = sqlDao.findList("CallerSQL.findVisitorsByTimeFormAuxi", vMap);
		if (list != null && list.size() > 0) {
			if (curvsid != null && !"".equals(curvsid)) {
				if (list.size() == 1
						&& curvsid.equals(list.get(0).get("VS_ID").toString())) {
					// DO Nothing
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		return true;
	}

}
