package com.cosmosource.app.personnel.service;

import java.util.List;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.Org;
import com.cosmosource.base.service.BaseManager;
/** 
* @description: 委办局房间分配查询
* @author sqs
* @date 2013-03-31
*/
@SuppressWarnings("unchecked")
public class SeeAssignRoomManager extends BaseManager {
	/**
	 * @description:加载一级机构（委办局）
	 * @author sqs
	 * @return List
	 * @date 2013-03-31
	 */
	public List<Org> loadOrg() {
		return dao.findByHQL("select t from Org t where t.levelNum = 2");
	}
	
	/**
	 * @description:批量删除
	 * @author sqs
	 * @param ids
	 * @date 2013-03-31
	 */
	public void deleteAlreadyRoom(String[] ids){
		dao.deleteByIds(EmOrgRes.class, ids);
	}
	
}
