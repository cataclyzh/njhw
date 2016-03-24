package com.cosmosource.base.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.ReflectionUtil;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapException;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;


/**
 * iBatis dao 基类
 * 继承DaoSupport，实现了后台分页查询功能
 */
@SuppressWarnings({"deprecation","rawtypes","unchecked"})
public abstract class BaseDaoiBatis extends SqlMapClientDaoSupport {

	private static Logger logger = LoggerFactory.getLogger(BaseDaoiBatis.class);
	
	private SqlExecutor sqlExecutor;
	public SqlExecutor getSqlExecutor() {
		return sqlExecutor;
	}

	public void setSqlExecutor(SqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}

	public void setEnableLimit(boolean enableLimit) {
		if (sqlExecutor instanceof LimitSqlExecutoriBatis) {
			((LimitSqlExecutoriBatis) sqlExecutor).setEnableLimit(enableLimit);
		}
	}
	
	public void initialize() throws Exception {
		if (sqlExecutor != null) {
			SqlMapClient sqlMapClient = getSqlMapClientTemplate()
					.getSqlMapClient();
			if (sqlMapClient instanceof ExtendedSqlMapClient) {
				ReflectionUtil.setFieldValue(((ExtendedSqlMapClient) sqlMapClient)
						.getDelegate(), "sqlExecutor", SqlExecutor.class,
						sqlExecutor);
			}
		}
	}
	/**
	 * 取得查询的总记录数
	 * @param String selectQuery 查询SQL
	 * @param Object parameterObject 输入参数
	 * @return long 总记录数
	 */
	public long getObjectTotal(String selectQuery, Object parameterObject) {
		prepareCountQuery(selectQuery);
		return (Long) getSqlMapClientTemplate().queryForObject(
				CountStatementUtiliBatis.getCountStatementId(selectQuery),
				parameterObject);
	}
	/**
	 * 取得查询的总记录数
	 * @param String selectQuery 查询SQL
	 * @return long 总记录数
	 */
	public long getObjectTotal(String selectQuery) {
		prepareCountQuery(selectQuery);
		return (Long) getSqlMapClientTemplate().queryForObject(
				CountStatementUtiliBatis.getCountStatementId(selectQuery));
	}

	/**
	 * 准备总记录数据的查询SQL
	 * @param selectQuery
	 */
	protected void prepareCountQuery(String selectQuery) {
	
		String countQuery = CountStatementUtiliBatis.getCountStatementId(selectQuery);
		SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
		if (sqlMapClient instanceof ExtendedSqlMapClient) {
			SqlMapExecutorDelegate delegate = ((ExtendedSqlMapClient) sqlMapClient)
					.getDelegate();
			try {
				delegate.getMappedStatement(countQuery);
			} catch (SqlMapException e) {
				delegate.addMappedStatement(CountStatementUtiliBatis
						.createCountStatement(delegate
								.getMappedStatement(selectQuery)));
			}

		}
	}

   /**
    * iBatis 分页查询功能
	 * @param Page page 分页对象
	 * @param String sql 配置文件中的SQL id
	 * @param Map map  输入参数
	 * @return Page
	 */
   public Page  findPage(final Page page, String sql,Map map) { 
       List list = null; 
       try { 
           if(page.getPageSize()==0){ 
               list = getSqlMapClientTemplate().queryForList(sql, map); 
           }else{ 
               list = getSqlMapClientTemplate().queryForList(sql, map, page.getFirst()-1, 
            		   page.getPageSize()); 
          		if (page.isAutoCount()) {
        			long totalCount = getObjectTotal(sql,map);
        			page.setTotalCount(totalCount);
        		}
           } 
           page.setResult(list);
       }catch(Exception e ) { 
    	   logger.error("不可能抛出的异常{}", e.getMessage());
       } 
       return page;
   } 

	/**
	 * iBatis 查询功能
	 * @param String sql 配置文件中的SQL id
	 * @param Map  map 输入参数
	 * @return List
	 */
	public List findList(String sql, Map map) {
		return getSqlMapClientTemplate().queryForList(sql, map);
	}
	/**
	* @描述: 批量更新数据
	* @作者：WXJ
	* @日期：2012-6-11 下午02:23:13
	* @param statementName
	* @param list
	* @return void
	*/
	public void batchUpdate(final String statementName, final List list) {
		if (list != null) {
			this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				public Object doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					executor.startBatch();
					for (int i = 0, n = list.size(); i < n; i++) {
						executor.update(statementName, list.get(i));
					}
					executor.executeBatch();
					return null;
				}
			});
		}
	}
	/**
	* @描述: 批量插入数据
	* @作者：WXJ
	* @日期：2012-6-11 下午02:23:13
	* @param statementName
	* @param list
	* @return void
	*/
	public void batchInsert(final String statementName, final List list) {
		if (list != null) {
			this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				public Object doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					executor.startBatch();
					for (int i = 0, n = list.size(); i < n; i++) {
						executor.insert(statementName, list.get(i));
					}
					executor.executeBatch();
					return null;
				}
			});
		}

	}
	/**
	* @描述: 批量删除数据
	* @作者：WXJ
	* @日期：2012-6-11 下午02:23:13
	* @param statementName
	* @param list
	* @return void
	*/
	public void batchDelete(final String statementName, final List list) {
		if (list != null) {
			this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				public Object doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					executor.startBatch();
					for (int i = 0, n = list.size(); i < n; i++) {
						executor.delete(statementName, list.get(i));
					}
					executor.executeBatch();
					return null;
				}
			});
		}
	}
}