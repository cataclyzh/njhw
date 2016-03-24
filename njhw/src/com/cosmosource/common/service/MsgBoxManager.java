/**
 * <p>文件名: MsgBoxManager.java</p>
 * <p>描述：消息箱Manager</p>
 * <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
 * <p>公司: Cosmosource Beijing Office</p>
 * <p>All right reserved.</p>
 * @创建时间：2010-11-20 15:58:35 
 * @作者： ls
 * @版本： V1.0
 * <p>类修改者		 修改日期			修改说明   </p>   
 * 
 */
package com.cosmosource.common.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.Users;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.ConvertUtils;
import com.cosmosource.common.entity.TAcUser;
import com.cosmosource.common.entity.TCommonMsgBox;

/**
 * @类描述: 消息箱Manager
 * @作者： ls
 */
public class MsgBoxManager extends BaseManager {
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		new MsgBoxManager().getMessageId();

	}

	/**
	 * @描述: 新增对象
	 * @作者：ls
	 * @日期：2010-12-01
	 * @param entity
	 *            消息箱模型
	 */
	public void sendGroupMessage(TCommonMsgBox entity, String loginNames) {
		dao.save(entity);
	}

	/**
	 * @描述: 解析上传的供应商的信息
	 * @作者：WXJ
	 * @日期：2011-8-17 下午01:18:35
	 * @param vendorsFile
	 * @return
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public String parserFile(File vendorsFile) throws Exception {
		StringBuffer sbvendor = new StringBuffer();
		InputStreamReader fr = new InputStreamReader(new FileInputStream(
				vendorsFile));
		BufferedReader br = null;
		br = new BufferedReader(fr);
		String rec = null;
		while ((rec = br.readLine()) != null) {
			DecimalFormat df = new DecimalFormat("00000000");
			try {
				rec = df.format(Integer.parseInt(rec));
			} catch (Exception e) {
				int j = 8 - rec.length();
				for (int ii = 0; ii < j; ii++) {
					rec = "0" + rec;
				}
			}
			String vendorname = "lt" + rec;
			sbvendor = sbvendor.append(vendorname + ", ");
		}
		return sbvendor.toString();
	}

	/**
	 * @描述: 新增对象
	 * @作者：ls
	 * @日期：2010-12-01
	 * @param entity
	 *            消息箱模型
	 */
	@SuppressWarnings("unchecked")
	public void saveMessage(TCommonMsgBox entity) {
		String strReceiver = entity.getReceiver().replace("，", ",");
		String[] arrReceiver = strReceiver.split(",");
		String sReceiver = null;
		List<TAcUser> listUser = null;
		TCommonMsgBox ent = null;
		for (int i = 0; i < arrReceiver.length; i++) {
			sReceiver = arrReceiver[i].trim();
			listUser = dao
					.findByProperty(TAcUser.class, "loginname", sReceiver);
			if (listUser.size() > 0) {
				ent = new TCommonMsgBox();
				ConvertUtils.copyValue(entity, ent);
				ent.setReceiver(sReceiver);
				dao.save(ent);
			}
		}
	}

	/**
	 * @描述: 保存
	 * @作者：sqs
	 * @日期：2010-12-01
	 * @param
	 */
	public void saveTCommonMsgBox(TCommonMsgBox entity) {
		dao.save(entity);
	}

	/**
	 * 根据用户名查询其ID
	 * 
	 * @author CJ
	 */
	public long findIDbyUsername(String username) {

		long id = 0;

		String sql = "select t.userid from users t where t.login_uid = ? or t.display_name = ?";

		id = ((java.math.BigDecimal) dao.findUniqueBySQL(sql, username,
				username)).longValue();

		logger.info(id + "");

		return id;
	}

	/**
	 * @描述: 更新对象
	 * @作者：ls
	 * @日期：2010-12-01
	 * @param entity
	 *            消息箱模型
	 */
	public void updateMessage(TCommonMsgBox entity) {
		dao.update(entity);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Long getMessageId() {
		Long id = (long) 0;
		// String currentTime = String.valueOf(System.currentTimeMillis());
		// String randomNum = String.valueOf((int) (Math.random() * 100000));
		// String buf = currentTime + randomNum;
		//
		// logger.info(currentTime);
		// logger.info(randomNum);
		// logger.info(buf);

		HashMap map = new HashMap(); // 参数Map
		List<Map> cList = null; // 列表
		cList = this.findListBySql("CommonSQL.getMessageId", map);
		for (Map m : cList) {
			id = Long.parseLong(m.get("NEXTVAL").toString());
			
		}

		return id;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getOrgById(Long userid) {
		
		// String currentTime = String.valueOf(System.currentTimeMillis());
		// String randomNum = String.valueOf((int) (Math.random() * 100000));
		// String buf = currentTime + randomNum;
		//
		// logger.info(currentTime);
		// logger.info(randomNum);
		// logger.info(buf);

		HashMap map = new HashMap(); // 参数Map
		map.put("userid", userid);
		List<Map> cList = null; // 列表
		//cList = this.findListBySql("PersonnelSQL.getTopOrgIdByUserId", map);
		cList = this.findListBySql("PersonnelSQL.getOrgIdByUserId", map);
		// logger.info(userid.toString());
		// logger.info(cList.toString());
		// for (Map m : cList) {
		// logger.info("bbbbbbbbbbbbb = " + m.get("DISPLAY_NAME"));
		// logger.info("bbbbbbbbbbbbb = " + m.get("ORG_NAME"));
		// //logger.info(cList.toString());
		// }

		return cList;
	}

	@SuppressWarnings({ "unchecked" })
	public Page<TCommonMsgBox> getOutboxStatus(final Page<TCommonMsgBox> pPage,
			Long notificationId, String status) throws Exception {
		List<Object> lParam = new ArrayList<Object>(1);
		// logger.info(",,,,,,,,,,,," + status);
		StringBuilder hql = new StringBuilder();
		//String hqlString = "select t from TCommonMsgBox t where t.notificationId = ? ";
		
		String hqlString = "select t from Users uu, TCommonMsgBox t where t.receiverid=uu.userid and t.notificationId = ?";
		
		if (!status.equals("-1")) {
			hqlString += "and t.status = " + status;
		}
		hqlString += "order by t.status asc";
		logger.info(hqlString);
		hql.append(hqlString);
		// hql.append("select t from TCommonMsgBox t where  t.notificationId = ? and t.status = ? order by  t.msgtime desc ");
		// hql.append("select t from TCommonMsgBox t where t.receiverid = ? order by t.status asc, t.msgtime desc ");
		lParam.add(notificationId);
		
		Page pp = null;
		
		try {
			pp = dao.findPage(pPage, hql.toString(), lParam.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pp;
//		return dao.findPage(pPage, hql.toString(), lParam.toArray());
		// JSONObject jObject = null;
		// HashMap map = new HashMap(); // 参数Map
		// List<Map> list = null; // 列表
		// map.put("notificationId", notificationId);
		// list = this.findListBySql("CommonSQL.getOutboxStatus", map);
		// logger.info("list Str:" + list.toString());
		//
		// // jArray.fromObject("['json','is','easy']");
		//
		// for (Map m : list) {
		// // jObject.fromObject(m);
		// logger.info("bbbbbbbbbbbbb = " + m.get("NEXTVAL"));
		// }
		//
		// return pPage;
	}

	/**
	 * @描述: 判断收件人在发件人的所属机构是否存在
	 * @作者：ls
	 * @日期：2010-12-01
	 * @param pReciver
	 *            收件人
	 * @param pCompany
	 *            公司
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public boolean checkReciver(String pReciver, String pCompany) {
		boolean isExist = false;
		List<String> list = new ArrayList<String>();

		List<Object> lParam = new ArrayList<Object>(1);
		StringBuilder hql = new StringBuilder("");
		hql.append("select u.loginname from TAcUser u where u.orgid in (select o.orgid from TAcOrg o where o.company = ?)");
		lParam.add(pCompany);

		list = dao.findByHQL(hql.toString(), lParam.toArray());
		for (String loginname : list) {
			if (pReciver.equals(loginname)) {
				isExist = true;
				break;
			}
		}

		return isExist;
	}

	/**
	 * @描述: 查询收件箱数据
	 * @作者：ls
	 * @日期：2010-12-01
	 * @param pPage
	 *            消息箱Page模型
	 * @param pLoginname
	 *            登录名
	 * @return Page<TCommonMsgBox>
	 */
	@SuppressWarnings("unchecked")
	public Page<TCommonMsgBox> findReceiverbox(final Page<TCommonMsgBox> pPage,
			String userId) {
		List<Object> lParam = new ArrayList<Object>(1);

		StringBuilder hql = new StringBuilder();
		hql.append("select t from TCommonMsgBox t where t.receiverid = ? order by t.status asc, t.msgtime desc ");
		lParam.add(new Long(userId));

		return dao.findPage(pPage, hql.toString(), lParam.toArray());
	}

	/**
	 * @描述: 查询发件箱数据
	 * @作者：ls
	 * @日期：2010-12-01
	 * @param pPage
	 *            消息箱Page模型
	 * @param pLoginname
	 *            登录名
	 * @return Page<TCommonMsgBox>
	 */
	@SuppressWarnings("unchecked")
	public Page<TCommonMsgBox> findSenderbox(final Page<TCommonMsgBox> pPage,
			Long senderId) {
		List<Object> lParam = new ArrayList<Object>(1);

		StringBuilder hql = new StringBuilder();
		hql.append("select t from TCommonMsgBox t where t.msgid in (select max(t1.msgid) from TCommonMsgBox t1 group by t1.notificationId) and t.senderId = ? order by t.msgtime desc ");
		// hql.append("select t from TCommonMsgBox t where t.sender = ? order by t.msgtime desc ");
		lParam.add(senderId);

		return dao.findPage(pPage, hql.toString(), lParam.toArray());
	}

	/**
	 * @描述: 根据主键查询模型
	 * @作者：ls
	 * @日期：2010-12-01
	 * @param pMsgId
	 *            消息ID
	 * @return TCommonMsgBox
	 */
	public TCommonMsgBox findMessageById(Long pMsgId) {
		return (TCommonMsgBox) dao.findById(TCommonMsgBox.class, pMsgId);
	}

	/**
	 * @描述: 登录信息提示：获取登陆者未读消息的数目
	 * @作者：ls
	 * @日期：2010-12-01
	 * @param pLoginname
	 *            登录名
	 * @return Integer
	 */
	@SuppressWarnings("unchecked")
	public Integer findMsgCntByNotRead(String pLoginname) {
		List<TAcUser> list = new ArrayList<TAcUser>();

		TCommonMsgBox pEntity = new TCommonMsgBox();
		pEntity.setReceiver(pLoginname);
		pEntity.setStatus("0");

		list = dao.findByExample(TCommonMsgBox.class, pEntity);
		if (list != null && !list.isEmpty()) {
			return new Integer(list.size());
		}
		return new Integer(0);
	}
	
	public Users queryUserName(long id){
		return (Users)dao.findById(Users.class, id);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void deleteSentMsg(String notificationId) {
		HashMap map = new HashMap(); // 参数Map
		List<Map> list = null; // 列表
		map.put("notificationId", notificationId);
		list = this.findListBySql("CommonSQL.getOutboxStatus", map);
		
		for (Map m : list) {
			String[] buffer={m.get("MSGID").toString()};
			this.deleteMoreMessage(buffer);
		}

		
	}
	/**
	 * 
	 * @title: deleteDishes
	 * @description: 通过主键批量删除消息
	 * @author sqs
	 * @param ids
	 * @date 2013-3-19 下午06:15:25
	 * @throws
	 */
	public void deleteMoreMessage(String[] ids) {
		super.dao.deleteByIds(TCommonMsgBox.class, ids);
	}
}
