/**
* <p>文件名: LevelManager.java</p>
* <p>:描述：级别管理Manager</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2012-8-6 11:09:30 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.common.entity.TAcLevel;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * 
 * @类描述: 级别管理Manager
 * @作者： WXJ
 *
 */
public class LevelManager extends BaseManager{
	
	/**
	 * @描述: 批量更新级别
	 * @作者：WXJ
	 * @日期：2012-8-2
	 * @param orgIdArr 机构id数组
	 * @param levelCode 级别编码
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int updateVendorLevel(final String[] orgIdArr, final String levelCode){
		Integer result = new Integer(0);
		if(orgIdArr != null && orgIdArr.length > 0){
			result = (Integer)sqlDao.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				int count = 0;			
			    public Object doInSqlMapClient(SqlMapExecutor executor) throws
			        SQLException {			    	
			        executor.startBatch();

		        	Map<String, Object> map = new HashMap<String, Object>();
		        	map.put("levelCode", levelCode);
			        for (String orgId : orgIdArr) {
			        	map.put("orgId", new Long(orgId));
			    	    count += executor.update("CommonSQL.updateVendorLevel", map);
			        }
			        executor.executeBatch();
			        return count;
			    }
			});
		}
		
		logger.info("publish knowledges count:" + result);
		
		return result;
	}
	
	/**
	 * @描述: 查询级别列表
	 * @作者：WXJ
	 * @日期：2012-8-2
	 * @param paraMap 查询参数
	 * @return
	 */
	public List<?> findLevelList(Map<String, String> paraMap){
		final String sql = "CommonSQL.findLevelList";
		return sqlDao.findList(sql, paraMap);
	}
	
	/**
	 * @描述: 保存级别
	 * @作者：WXJ
	 * @日期：2012-8-2
	 * @param levelEntity 级别
	 * @return
	 */
	public void saveOrUpdate(TAcLevel levelEntity){
		dao.saveOrUpdate(levelEntity);
	}
	
	/**
	 * @描述: 删除级别列表
	 * @作者：WXJ
	 * @日期：2012-8-3
	 * @param levelIdArr 级别id数组
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int deleteLevels(final String[] levelIdArr){
		Integer result = new Integer(0);
		if(levelIdArr != null && levelIdArr.length > 0){
			result = (Integer)sqlDao.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				int count = 0;			
			    public Object doInSqlMapClient(SqlMapExecutor executor) throws
			        SQLException {			    	
			        executor.startBatch();

		        	Map<String, Object> map = new HashMap<String, Object>();
			        for (String levelId : levelIdArr) {
			        	map.put("levelId", levelId);
			    	    count += executor.update("CommonSQL.deleteLevel", map);
			        }
			        executor.executeBatch();
			        return count;
			    }
			});
		}
		
		logger.info("delete levels count:" + result);
		
		return result;
	}
	
	/**
	 * @描述: 查询已经被使用的级别编码列表
	 * @作者：WXJ
	 * @日期：2012-8-6
	 * @param levelCodeArr 级别编码数组
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryUsedLevelCodeList(final String[] levelCodeArr){
		if(levelCodeArr != null && levelCodeArr.length > 0){
			final String sql = "CommonSQL.queryUsedLevelCodeList";
			List<String> levelCodeList = new ArrayList<String>();
			for(String levelCode : levelCodeArr){
				levelCodeList.add(levelCode);
			}
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("levelCodeList", levelCodeList);
			return sqlDao.findList(sql, paraMap);
		}
		else {
			return new ArrayList<Map<String, Object>>();
		}
	}
}
