package com.cosmosource.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Manager基础类
 * 注入Hibernate 公用dao及iBatis公用dao
 * 实现基本的数据操作，所有的Manager需要继承此类，并在Spring注入时继承此类
 * @author WXJ
 *
 */
@SuppressWarnings({"rawtypes"})
public class DBManager extends BaseManager{
  
	/**
	 * 保存新增或修改的对象.
	 */
	public void saveOrUpdate(final Object entity) {
		dao.saveOrUpdate(entity);

	}
	/**
	 * 保存新增对象.
	 */
	public Serializable save(final Object entity) {
		return dao.save(entity);
	}	
	/**
	 * 保存修改的对象.
	 */
	public void update(final Object entity) {
		dao.update(entity);
	}	
	/**
	 * 保存修改的对象.
	 */
	public void merge(final Object entity) {
		dao.merge(entity);
	}
	/**
	 * 删除对象.
	 * 
	 * @param entity 对象必须是session中的对象或含id属性的transient对象.
	 */
	public void delete(final Object entity) {
		dao.delete(entity);
	}
	

	/**
	 * 按id删除对象.
	 */
	public void deleteById(final Class entityClass, final Serializable id) {
		dao.deleteById(entityClass, id);
	}
	
	/**
	 * 按id获取对象.
	 */
	public Object get(final Class entityClass, final Serializable id) {
		return  dao.get(entityClass, id);
	}
	/**
	 * 按id获取对象.
	 */
	public Object load(final Class entityClass, final Serializable id) {
		return  dao.load(entityClass, id);
	}
	/**
	 * 按id获取对象.
	 */
	public Object findById(final Class entityClass,final Serializable id) {
		return dao.findById(entityClass, id);
	}
	/**
	 *	获取全部对象.
	 */
	public List getAll(final Class entityClass) {
		return dao.getAll(entityClass);
	}

	/**
	 *	获取全部对象,支持排序.
	 */
	public List getAll(final Class entityClass,String orderBy, boolean isAsc) {
		
		return dao.getAll(entityClass, orderBy, isAsc);
	}

	/**
	 * 按属性查找对象列表,匹配方式为相等.
	 */
	public List findByProperty(final Class entityClass,final String propertyName, final Object value) {
		return dao.findByProperty(entityClass, propertyName, value);
	}
	
	
	public List findByExample( final Class entityClass,final Object exampleEntity) {		
		return dao.findByExample(entityClass, exampleEntity);
	}
	

	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 */
	public Object findUniqueBy(final Class entityClass,final String propertyName, final Object value) {
		
		return  dao.findUniqueBy(entityClass, propertyName, value);
	}

	/**
	 * 按id列表获取对象.
	 */
	public List findByIds(final Class entityClass,List ids) {
		return dao.findByIds(entityClass, ids);
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public  List findByHQL(final String hql, final Object... values) {
		return dao.findByHQL(hql, values);
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	public List findByHQL(final String hql, final Map<String, ?> values) {
		return dao.findByHQL(hql, values);
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public Object findUnique(final String hql, final Object... values) {
		return dao.findUnique(hql, values);
	}
	/**
	 * 按SQL查询唯一对象.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public Object findUniqueBySQL(final String sql, final Object... values) {
		return dao.findUniqueBySQL(sql, values);
	}
	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	public Object findUnique(final String hql, final Map<String, ?> values) {
		return dao.findUnique(hql, values);
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 */
	public int batchExecute(final String hql, final Object... values) {
		return dao.batchExecute(hql, values);
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final Map<String, ?> values) {
		return dao.batchExecute(hql, values);
	}
	/**
	 * 从数据库得到序列值
	 * @return Map
	 */
	public Long getSeqNextVal(String seqName) {
		return dao.getSeqNextVal(seqName);
	}
	
	/**
	* @描述: 执行jdbc进行批量插入/更新/删除
	* @作者：WXJ
	* @日期：2012-7-24 下午05:56:16
	* @param sql
	* @param batchArgs
	* @return void
	*/
	public int[] updateBatchJdbc(String sql, List<Object[]> batchArgs){
		int[] result = dao.jdbcBatchUpdate(sql, batchArgs);
		return result;
	}
	
}
