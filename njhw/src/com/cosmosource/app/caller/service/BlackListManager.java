package com.cosmosource.app.caller.service;

import java.util.Map;

import com.cosmosource.app.entity.VmVisitorinfo;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

/**
 * 
* @description: 访客管理(黑名单)
* @author SQS
* @date 2013-3-17 下午06:32:02
 */
public class BlackListManager extends BaseManager {
	/** 
	* @title: 
	* @description: 跟据条件查询黑名单列表数据
	* @author SQS
	* ${tags}
	* @date 2013-3-19  
	* @throws 
	*/ 
	@SuppressWarnings("unchecked")
	public Page<VmVisitorinfo> selectBlackList(final Page<VmVisitorinfo> page,final Map<String,Object> map){
		return sqlDao.findPage(page, "CallerSQL.selectBlackList", map);
	}
	
	/** 
	 * @title: 
	 * @description: 保存按钮使用
	 * @author SQS
	 * ${tags}
	 * @date 2013-3-19 
	 * @throws 
	 */ 
	public void saveBlackList(VmVisitorinfo v,Long id,String blackReson){
		VmVisitorinfo vv = (VmVisitorinfo) dao.get(VmVisitorinfo.class, id);
		vv.setIsBlack(VmVisitorinfo.IS_BLACK_YES);
		vv.setBlackReson(blackReson);
		dao.update(vv);
		//dao.flush();
	}
	
	/** 
	 * @title: 
	 * @description: 取消按钮使用
	 * @author SQS
	 * ${tags}
	 * @date 2013-3-19 
	 * @throws 
	 */ 
	public void updateBlackListCancel(VmVisitorinfo v,Long id){
		VmVisitorinfo vv = (VmVisitorinfo) dao.get(VmVisitorinfo.class, id);
		vv.setIsBlack(VmVisitorinfo.IS_BLACK_NO);
		vv.setBlackReson(null);
		dao.update(vv);
		//dao.flush();
	}
}