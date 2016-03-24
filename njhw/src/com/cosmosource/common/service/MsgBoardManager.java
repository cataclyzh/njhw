/**
* <p>文件名: MsgBoardManager.java</p>
* <p>描述：公告板Manager</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间：2010-11-19 20:42:13 
* @作者： ls
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TCommonMsgBoard;
import com.cosmosource.common.entity.TCommonMsgBoardRead;
/**
 * @类描述: 公告板Manager
 * @作者： ls
 */
public class MsgBoardManager extends BaseManager {

	/**
	 * @描述: 新增或是更新保存对象
	 * @作者：ls
	 * @日期：2010-12-01
	 * @param entity 公告板模型
	 */
	public void saveBulletin(TCommonMsgBoard entity){
		this.dao.save(entity);
	}
	public void updateBulletin(TCommonMsgBoard entity){
		this.dao.update(entity);
	}
	/**
	 * @描述: 查询用户列表数据
	 * @作者：zh
	 * @日期：2010-12-01
	 * @param pPage 公告板Page对象
	 * @param pOrgId 机构ID
	 * @return Page<TCommonMsgBoard>
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> findBulletin(Page<HashMap> pPage, String pCompany) {
		List list  = dao.findByHQL("select t from TCommonMsgBoardRead t where t.userid = ?", Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		pPage = this.sqlDao.findPage(pPage, "CommonSQL.loadBoard", null);
		for (HashMap map : pPage.getResult()) {
			String isRead = "false";
			for (int i = 0; i < list.size(); i++) {
				TCommonMsgBoardRead boardRead = (TCommonMsgBoardRead)list.get(i);
				if (Long.parseLong(map.get("MSGID").toString()) == boardRead.getMsgid()) {
					isRead = "true";
					break;
				}
			}
			map.put("ISREAD", isRead);
		}
		return pPage;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<TCommonMsgBoard> queryAllMsg() {
		List<HashMap> resultList = new ArrayList<HashMap>();
		List<TCommonMsgBoard> msgList = new ArrayList<TCommonMsgBoard>();
		try {
			resultList = sqlDao.findList("CommonSQL.selectAllMsgBoard",null);
			if (resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					TCommonMsgBoard msgBoardList = new TCommonMsgBoard();
					if (resultList.get(i).get("MSGID") != null) {
						Long msgid = Long.parseLong(String
								.valueOf(resultList.get(i).get("MSGID")));
						msgBoardList.setMsgid(msgid);
					}

				
					if (resultList.get(i).get("AUTHOR") != null) {
						String author = String.valueOf(resultList
								.get(i).get("AUTHOR"));
						msgBoardList.setAuthor(author);
					}

					if (resultList.get(i).get("TITLE") != null) {
						String title = String.valueOf(resultList
								.get(i).get("TITLE"));
						
						if(title.length()>4){
							msgBoardList.setTitle(title.substring(0,4)+"..");
						}else{
							msgBoardList.setTitle(title);
						}
						
					}
					if (resultList.get(i).get("CONTENT") != null) {
						String content = String.valueOf(resultList
								.get(i).get("CONTENT"));
						
						if(content.length()>4){
							msgBoardList.setContent(content.substring(0,4)+"..");
						}else{
							msgBoardList.setContent(content);
						}
						
					}
					if (resultList.get(i).get("MSGTIME") != null) {
						Date msgtime = (Date) resultList.get(i)
								.get("MSGTIME");
						msgBoardList.setMsgtime(msgtime);
					}
					msgList.add(msgBoardList);
				}
			}
		} catch (Exception e) {
		}
		return msgList;
	}
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public void updateMsgInfo(Map map){
		List<Map> list = new ArrayList();		
		list.add(map);
		sqlDao.batchUpdate("CommonSQL.updateMsgInfo", list);
	}
	
	/**
	 * @描述: 物业通知管理
	 * @作者：qyq
	 * @param pPage 公告板Page对象
	 * @return Page<TCommonMsgBoard>
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> findBulletinManager(Page<HashMap> pPage) {
		return this.sqlDao.findPage(pPage, "CommonSQL.loadBoard", null);
	}
	
	public List findUserNameByLoginId(Map param){
		return sqlDao.findList("CommonSQL.searchUserNameByLogin", param);
	}
	
	/**
	 * @描述: 根据主键查询模型
	 * @作者：ls
	 * @日期：2010-12-01
	 * @param pMsgId 机构ID
	 * @return TCommonMsgBoard
	 */
	public TCommonMsgBoard findBulletinById(Long pMsgId) {
		return (TCommonMsgBoard) this.dao.findById(TCommonMsgBoard.class, pMsgId);
	}
	
	/**
	 * @描述: 保存此次阅读记录
	 * @作者：zh
	 * @日期：2013-5-14
	 * @param TCommonMsgBoardRead tCommonMsgBoardRead
	 */
	public void saveRead(TCommonMsgBoardRead tCommonMsgBoardRead) {
		int num = dao.findByHQL("select t from TCommonMsgBoardRead t where t.msgid = ? and t.userid = ?", tCommonMsgBoardRead.getMsgid(), tCommonMsgBoardRead.getUserid()).size();
		if (num == 0) this.dao.save(tCommonMsgBoardRead);
	}

	 /**
	 * @description:删除 选中的物业通知的
	 * @author  qiyanqiang
	 * @param ids
	 */
	@SuppressWarnings("unchecked")
	public void deleteData(String[] ids){
			//dao.deleteByIds(TCommonMsgBoard.class, ids);
			//dao.deleteByIdsForString(TCommonMsgBoard.class, ids);
//		List list = new ArrayList();
//		HashMap parMap = new HashMap();
//		parMap.put("msgid", msgid[0]);
//		list.add(parMap);
//		sqlDao.batchDelete("CommonSQL.delbr", list);
//		
//		dao.deleteByIds(TCommonMsgBoard.class, msgid);\
		
		for(int i=0;i<ids.length;i++){
			List list = new ArrayList();
			Map map = new HashMap();
			map.put("msgid", ids[i]);
			list.add(map);
			sqlDao.batchDelete("CommonSQL.delbr", list);
			sqlDao.batchDelete("CommonSQL.delBoard", list);
		}
	}
	

	/**
	 * @描述: 登录信息提示：获取近一个月所在机构所发布公告的数目
	 * @作者：ls
	 * @日期：2010-12-01
	 * @param pLoginname 登录名
	 * @return Integer
	 */
	@SuppressWarnings("unchecked")
	public Integer findBltnCntByRecentMonth(String pCompany) {
		List<TCommonMsgBoard> list = new ArrayList<TCommonMsgBoard>();
		
		StringBuilder hql = new StringBuilder();
		hql.append("select t from TCommonMsgBoard t where t.msgtime >= add_months(sysdate, -1) ");
		
		list = this.dao.findByHQL(hql.toString());
		if (list != null && !list.isEmpty()) {
			return new Integer(list.size());
		}
		return new Integer(0);
	}
}
