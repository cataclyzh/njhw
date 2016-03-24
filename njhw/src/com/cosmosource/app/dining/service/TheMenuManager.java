package com.cosmosource.app.dining.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import com.cosmosource.app.entity.FsDishes;
import com.cosmosource.app.entity.FsDishesIssue;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;

public class TheMenuManager extends BaseManager {
	
	/**
	 * 
	* @title: saveUpdateTheMenu 
	* @description: 保存或修改菜肴信息
	* @author sqs
	* @param obj
	* @date 2013-3-19 下午09:05:19    
	* @throws
	 */
	public void saveUpdateTheMenu(Object obj){
		super.dao.saveOrUpdate(obj);
	}
	
	/**
	 * 
	* @title: queryTheMenu 
	* @description: 查询按钮使用
	* @author sqs
	* @param page
	* @param obj
	* @return
	* @date 2013-3-19 下午09:23:19     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<FsDishesIssue> queryTheMenu(final Page<FsDishesIssue> page,final Map<String,Object> obj){
		Map map = new HashMap();
		map.put("fdiType", obj.get("fdiType"));
		map.put("fdiFlag", obj.get("fdiFlag"));
	  //map.put("fdClass", obj.get("fdClass"));
		return sqlDao.findPage(page, "FoodManagementSQL.queryTheMenu", map);
	}
	/**
	 * 
	* @title: deleteTheMenu 
	* @description: 通过主键批量删除
	* @author sqs
	* @param ids
	* @date 2013-3-19 下午06:15:25     
	* @throws
	 */
	public void deleteTheMenu(String[] ids){
		super.dao.deleteByIds(FsDishesIssue.class, ids);
	}
	
	/**
	 * 
	 * @title: theMenuSave 
	 * @description: 保存发布的菜单
	 * @author sqs
	 * @param ids
	 * @date 2013-3-19 下午06:15:25     
	 * @throws
	 */
		@SuppressWarnings("unchecked")
		public void saveTheMenu(FsDishesIssue fsDishesIssue, String ids,String fdiFlag ,String fdiType,String fabufdiIdact) {
			// 分解字符串为数组
			String[] strs = ids.split(",");
			
			delete(fdiFlag,fdiType);
			if (null != strs && strs.length > 0 ) {
				for (int i = 0 ;i < strs.length ; i++) {
						FsDishesIssue fDishesIssue = new FsDishesIssue();
						if(StringUtil.isNotBlank(strs[i])){
							fDishesIssue.setFdId(Long.parseLong(strs[i]));
						}
						fDishesIssue.setFdiFlag(fdiFlag);
						fDishesIssue.setFdiType(fdiType);
						fDishesIssue.setInsertDate(DateUtil.getSysDate());
						if(StringUtil.isNotBlank(strs[i])){
						List<FsDishesIssue> list = dao.findByHQL("select t from FsDishesIssue t where t.fdId = ? and t.fdiFlag = ? and t.fdiType = ? ", Long.valueOf(strs[i]),fdiFlag,fdiType);
						if (null == list || list.size() < 1){
							try {
								dao.save(fDishesIssue);
								//dao.flush();
							} catch (Exception e) {
								e.printStackTrace();
							}	
						}}
					
			}}
		}
		
		/**
		 * @description:加载未分配的菜肴
		 * @author SQS
		 * @return List
		 * @date 2013-03-19
		 */

		@SuppressWarnings("unchecked")
		public Page<FsDishes> loadTheMenu(final Page<FsDishes> page,
				HashMap<String, Long> map) {
			return sqlDao.findPage(page, "FoodManagementSQL.selectTheMenu", map);
		}
		
		/**
		 * @title:
		 * @description: 取消已经发布的菜肴
		 * @author SQS 
		 * @date 2013-3-19
		 * @throws
		 */
		public void delete(String fdiFlag,String fdiType){
			String hql = " delete  FsDishesIssue f where f.fdiFlag = ? and f.fdiType = ?";
			Query query = dao.createQuery(hql,fdiFlag,fdiType);
			query.executeUpdate();
		}
	
}
