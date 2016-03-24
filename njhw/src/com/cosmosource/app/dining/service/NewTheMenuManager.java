package com.cosmosource.app.dining.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import com.cosmosource.app.entity.FsDishesIssue;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.Users;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;

public class NewTheMenuManager extends BaseManager {
	
	/**
	 * 
	* @title: queryNewTheMenu 
	* @description: 查询使用
	* @author sqs
	* @param page
	* @param obj
	* @return
	* @date 2013-3-19 下午09:23:19     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> queryNewTheMenu(final List<HashMap> list,final Map<String,Object> obj){
		Map map = new HashMap();
		map.put("fdiType", obj.get("fdiType")); //周几
		map.put("fdiFlag", obj.get("fdiFlag"));//上中下		
		return sqlDao.findList("FoodManagementSQL.queryNewTheMenu", map);
	}
	
	/**
	 * 
	* @title: queryTheMenu 
	* @description: 编辑按钮使用
	* @author sqs
	* @param page
	* @param obj
	* @return
	* @date 2013-3-19 下午09:23:19     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<FsDishesIssue> selectNewTheMenu(final List<FsDishesIssue> page,final Map<String,Object> obj){
		Map map = new HashMap();
		map.put("fdiType", obj.get("fdiType"));
		map.put("fdiFlag", obj.get("fdiFlag"));
		return sqlDao.findList("FoodManagementSQL.queryTheMenu", map);
	}
	
	
	
	
	/**
	 * 
	 * @title: theNewMenuSave 
	 * @description: 保存发布的菜单
	 * @author sqs
	 * @param ids
	 * @date 2013-3-19 下午06:15:25     
	 * @throws
	 */
		@SuppressWarnings("unchecked")
		public void saveTheNewMenu(FsDishesIssue fsDishesIssue, String ids,String fdiFlag ,String fdiType,String fabufdiIdact) {
			// 分解字符串为数组
			String[] strs = ids.split(",");
			
			deleteMenu(fdiFlag,fdiType);
			if (null != strs && strs.length > 0 ) {
				for (int i = 0 ;i < strs.length ; i++) {
						FsDishesIssue fDishesIssue = new FsDishesIssue();
						if(StringUtil.isNotBlank(strs[i])){
							fDishesIssue.setFdId(Long.parseLong(strs[i]));
						}
						fDishesIssue.setFdiFlag(fdiFlag);
						fDishesIssue.setFdiType(fdiType);
						fDishesIssue.setInsertDate(DateUtil.getSysDate());
						fDishesIssue.setOrderNum(maxNum(fdiType,fdiFlag)+1);
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
					
			}
			}
		}
		private Long maxNum(String fdiType,String fdiFlag){
			Long exp =(long) 0;
			List<FsDishesIssue> list = dao.findByHQL("select t from FsDishesIssue t where  t.fdiType = ? and t.fdiFlag = ? order by t.orderNum desc ",fdiType, fdiFlag);	
			if(list!=null&&list.size()>0){
				if (list != null && list.size() >= 1) {
					exp = Long.parseLong(list.get(0).getOrderNum().toString());
				  if(exp==null){
					  
					  exp = (long) 0;
				  }
				}
				
			}
			return exp;
			
		}
		
		/**
		 * @title:
		 * @description: 取消已经发布的菜肴
		 * @author SQS 
		 * @date 2013-3-19
		 * @throws
		 */
		public void deleteMenu(String fdiFlag,String fdiType){
			String hql = " delete  FsDishesIssue f where trim(f.fdiFlag) = ? and trim(f.fdiType) = ?";
			Query query = dao.createQuery(hql,fdiFlag,fdiType);
			query.executeUpdate();
		}
		
		
     /**
      * 菜单上移		
     * @title: upMenu 
     * @description: TODO
     * @author gxh
     * @param fdiId
     * @return
     * @date 2013-5-28 下午06:43:02     
     * @throws
      */
		public int updateUpMenu(Long fdiId) {
			int res = 0;
			FsDishesIssue exp =  new FsDishesIssue();
		
			FsDishesIssue exp2 = new FsDishesIssue();

			List expList = dao.findByProperty(FsDishesIssue.class, "fdiId", fdiId);
			if (expList != null && expList.size() >= 1) {
				exp = (FsDishesIssue) expList.get(0);
			}
			if (exp != null) {
				Long numId1 = exp.getOrderNum();
						
					List<FsDishesIssue> list = dao.findByHQL("select t from FsDishesIssue t where t.orderNum < ? and t.fdiType=? and t.fdiFlag=? order by to_number(t.orderNum) desc",
							 exp.getOrderNum(),exp.getFdiType(),exp.getFdiFlag());
					if (list != null && list.size() >= 1) {
						exp2 = (FsDishesIssue) list.get(0);
					}
					if(exp2!=null){
						Long numId2= exp2.getOrderNum();
						if(numId2!=null){
						exp2.setOrderNum(numId1);					
						exp.setOrderNum(numId2);
						dao.update(exp);
					
						//dao.flush();    
						dao.update(exp2);
						//dao.flush();
						res = 0; 
					}else{
						res = 2; 
						
					}		
					}	
			
			}else{
				res = 1;//失败
				
			}
	     return res;
		}
		
		/**
		 * 菜单下移
		* @title: downMenu 
		* @description: TODO
		* @author gxh
		* @param fdiId
		* @return
		* @date 2013-5-28 下午06:57:13     
		* @throws
		 */
		public int updateDownMenu(Long fdiId) {
			int res = 0;
			FsDishesIssue exp =  new FsDishesIssue();
		
			FsDishesIssue exp2 = new FsDishesIssue();

			List expList = dao.findByProperty(FsDishesIssue.class, "fdiId", fdiId);
			if (expList != null && expList.size() >= 1) {
				exp = (FsDishesIssue) expList.get(0);
			}
			if (exp != null) {
				Long numId1 = exp.getOrderNum();
						
					List<FsDishesIssue> list = dao.findByHQL("select t from FsDishesIssue t where t.orderNum > ? and t.fdiType=? and t.fdiFlag=? order by to_number(t.orderNum)",
							 exp.getOrderNum(),exp.getFdiType(),exp.getFdiFlag());
					if (list != null && list.size() >= 1) {
						exp2 = (FsDishesIssue) list.get(0);
					}
					if(exp2!=null){
						Long numId2= exp2.getOrderNum();
					if(numId2!=null){
						exp2.setOrderNum(numId1);					
						exp.setOrderNum(numId2);
						dao.update(exp);
					
						//dao.flush();    
						dao.update(exp2);
						//dao.flush();
						res = 0; 
					}else {
						res = 2; 
					}
					}
			
			}else{
				res = 1;//失败
				
			}
	     return res;
		}
		
		
		@SuppressWarnings("unchecked")
		public List<Map> ajaxqueryMenu(String fdiType,String fdiFlag){
			Map map = new HashMap();
			map.put("fdiType",fdiType); //周几
			map.put("fdiFlag", fdiFlag);//上中下
			List<Map> list = sqlDao.findList("FoodManagementSQL.ajaxqueryMenu", map);
			 if(list!=null&&list.size()>0){
					return list;
					
				}else{
					return null;
				}
		}
		
		public FsDishesIssue fsDishesIssueByid(Long fdiId){
			FsDishesIssue exp = new FsDishesIssue();
			List expList = dao.findByProperty(FsDishesIssue.class, "fdiId", fdiId);
			if (expList != null && expList.size() >= 1) {
				exp = (FsDishesIssue) expList.get(0);
				return exp;
			}else {
				return null;
			}
			
			
		}
		
		
}
