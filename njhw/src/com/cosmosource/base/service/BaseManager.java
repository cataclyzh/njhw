package com.cosmosource.base.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.Users;
import com.cosmosource.base.dao.ComDao;
import com.cosmosource.base.dao.ComDaoiBatis;
import com.cosmosource.base.util.ConvertUtils;
import com.cosmosource.common.entity.TAcDictdeta;
import com.cosmosource.common.entity.TAcDicttype;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * Manager基础类
 * 注入Hibernate 公用dao及iBatis公用dao
 * 实现基本的数据操作，所有的Manager需要继承此类，并在Spring注入时继承此类
 * @author WXJ
 *
 */
@SuppressWarnings({"rawtypes"})
public class BaseManager {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	//hibernate公用ddao
	protected ComDao dao;
	//ibatis公用ddao
	protected ComDaoiBatis sqlDao;
	/**
	* @描述: 根据ID取实体
	* @作者：WXJ
	* @日期：2011-2-14 上午09:25:11
	* @param entityClass
	* @param id
	* @return
	* @return Object
	 */
	public Object findById(final Class entityClass,final Serializable id) {
		return dao.findById(entityClass, id);
	}
	
	/**
	 * @描述: sqlmap分页查询通用方法
	 * @作者： WXJ
	 * @日期：2010-11-15
	 * @param page 分页对象
	 * @param sql　ibatis 中的Sql ID
	 * @param pMap　页面查询中的参数
	 * @return Page
	 */
	public Page findListBySql(final Page page,String sql,final Map pMap) {
		return sqlDao.findPage(page, sql, pMap);
	}

	/**
	 * @描述: 列表查询通用方法
	 * @作者： WXJ
	 * @日期：2010-11-27
	 * @param sql　ibatis 中的Sql ID
	 * @param pMap　页面查询中的参数
	 * @return List
	 */
   public List  findListBySql(String sql,final Map pMap) { 
       return sqlDao.findList(sql, pMap);
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
	 * 托拽机构树中的部门.
	 * 
	 * @param values 
	 */
	public void updateTreeDrag(Org o){
		dao.update(o);
		dao.flush();
	}
	
	/**
	 * 托拽机构树中的部门.
	 * 
	 * @param values 
	 */
	public void updatePerDragOrg(Users u){
		dao.update(u);
		dao.flush();
	}

	/**
	 * @描述: 通过字典ＩＤ查询显示内容
	 * @param dicttypecode
	 * @param dicttypename
	 * @return
	 */
	public  String findDictNameByid(final String dicttypecode, final String dictcode) {
		List types = dao.findByHQL("select t from TAcDicttype t where t.dicttypecode=?", dicttypecode);
		if(types!=null&&types.size()>0){
			TAcDicttype typeEnt = (TAcDicttype)types.get(0);
			
			List detas = dao.findByHQL("select t from TAcDictdeta t where t.dicttype=? and t.dictcode=?", typeEnt.getDicttypeid(),dictcode);
			if(detas!=null&&detas.size()>0){
				return ((TAcDictdeta)detas.get(0)).getDictname();
			}
		}
		
		return "";
	}
	/**
	 * @描述: 表单参数查询通用方法（分页）
	 * @作者： WXJ
	 * @日期：2012-1-15
	 * @param page 分页对象
	 * @param sql　ibatis 中的Sql ID
	 * @param model 页面主查询模型
	 * @param subModel　页面子查询模型
	 * @param pMap　页面查询中的特殊参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page findPageListBySql(final Page page,String sql,
			final Object model,final Object subModel,final Map pMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		//model,subModel,pMap组合成map传到配置文件中
		if(model!=null){
			map.putAll(ConvertUtils.pojoToMap(model));		
		}
		if(subModel!=null){
			map.putAll(ConvertUtils.pojoToMap(subModel));
		}
		if(pMap!=null){
			map.putAll(pMap);
		}
		return sqlDao.findPage(page, sql, map);
		
	}
	
	/**
	 * @描述: 表单参数查询通用方法（不分页）
	 * @作者： WXJ
	 * @日期：2010-11-27
	 * @param sql　ibatis 中的Sql ID
	 * @param model 页面主查询模型
	 * @param subModel　页面子查询模型
	 * @param pMap　页面查询中的特殊参数
	 * @return
	 */
   @SuppressWarnings("unchecked")
   public List  findListBySql(String sql,
			final Object model,final Object subModel,final Map pMap) { 
		Map<String, Object> map = new HashMap<String, Object>();
		//model,subModel,pMap组合成map传到配置文件中
		if(model!=null){
			map.putAll(ConvertUtils.pojoToMap(model));	
		}
		if(subModel!=null){
			map.putAll(ConvertUtils.pojoToMap(subModel));
		}
		if(pMap!=null){
			map.putAll(pMap);
		}
       return sqlDao.findList(sql, map);
   } 
   
    /**
	 * @描述: 执行ibatis更新
	 * @作者： WXJ
	 * @日期：2012-2-28
	 * 
     * @param sql ibatis SQL
     * @param condition 查询条件
     */
	@SuppressWarnings("unchecked")
	public int updateBySql(final String sql, final Map condition) {
		return (Integer)sqlDao.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {	
		    public Object doInSqlMapClient(SqlMapExecutor executor) throws
		        SQLException {			    	
		    	return executor.update(sql, condition);
		    }
		});
	}
	
    /**
	 * @描述: 执行ibatis删除
	 * @作者： WXJ
	 * @日期：2012-2-29
	 * 
     * @param sql ibatis SQL
     * @param condition 查询条件
     */
	@SuppressWarnings("unchecked")
	public int deleteBySql(final String sql, final Map condition){
		return (Integer)sqlDao.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {	
		    public Object doInSqlMapClient(SqlMapExecutor executor) throws
		        SQLException {			    	
		    	return executor.delete(sql, condition);
		    }
		});
	}
    /**
	 * @描述: 执行ibatis新增
	 * @作者： WXJ
	 * @日期：2012-2-29
	 * 
     * @param sql ibatis SQL
     * @param paras 参数
     */
	@SuppressWarnings("unchecked")
	public int insertBySql(final String sql, final Map paras){
		return (Integer)sqlDao.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {	
		    public Object doInSqlMapClient(SqlMapExecutor executor) throws
		        SQLException {			    	
		    	return executor.insert(sql, paras);
		    }
		});	
	}
    /**
	 * @描述: 执行ibatis批量新增
	 * @作者： WXJ
	 * @日期：2012-5-29
     * @param sql ibatis SQL
     * @param paras 数据
     */
	public void insertBatchBySql(String sql, List paras){
		sqlDao.batchInsert(sql, paras);	
	}
    /**
	 * @描述: 执行ibatis批量更新
	 * @作者： WXJ
	 * @日期：2012-5-29
     * @param sql ibatis SQL
     * @param paras 数据
     */
	public void updateBatchBySql(String sql, List paras){
		sqlDao.batchUpdate(sql, paras);	
	}
    /**
	 * @描述: 执行ibatis批量删除
	 * @作者： WXJ
	 * @日期：2012-5-29
     * @param sql ibatis SQL
     * @param paras 数据
     */
	public void deleteBatchBySql(String sql, List paras){
		sqlDao.batchDelete(sql, paras);	
	}
	public void setDao(ComDao dao) {
		this.dao = dao;
	}
	public void setSqlDao(ComDaoiBatis sqlDao) {
		this.sqlDao = sqlDao;
	}
}
