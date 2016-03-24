package com.cosmosource.base.dao;


import com.cosmosource.base.util.NumberUtil;


/**
 * 直接调用DAO类.
 * 
 * @author WXJ
 */
public class ComDao extends BaseDao {
	
	/**
	 * 按ids删除对象，
	 * 用于页面选择checkbox后传递到后台字符串数组进行删除.
	 */
	@SuppressWarnings("unchecked")
	public void deleteByIds(final Class entityClass, final String[] ids) {
		Object tmp = null;
		for(int i = 0 ;i< ids.length ; i++){
			tmp = get(entityClass, NumberUtil.strToLong(ids[i]));
			if(tmp!=null){
				deleteById(entityClass, NumberUtil.strToLong(ids[i]));
			}
			
		}
	}
	
	/**
	 * 按ids删除对象，
	 * 用于页面选择checkbox后传递到后台字符串数组进行删除.
	 */
	@SuppressWarnings("unchecked")
	public void deleteByIdsForString(final Class entityClass, final String[] ids) {
		Object tmp = null;
		for(int i = 0 ;i< ids.length ; i++){
			tmp = get(entityClass, ids[i]);
			if(tmp!=null){
				deleteById(entityClass,ids[i]);
			}
			
		}
	}
	
}
