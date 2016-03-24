/**
* <p>文件名: KnowledgeManager.java</p>
* <p>:描述：知识管理</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2012-2-9 9:20:10 
* @作者： csq
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.ConvertUtils;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.common.entity.TCommonKnowledge;
import com.cosmosource.common.entity.TCommonKnowledgeFile;
import com.cosmosource.common.model.KnowledgeModel;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * @类描述: 知识管理
 * @作者： csq
 *
 */
public class KnowledgeManager extends BaseManager {

	/**
	 * @描述: 保存知识
	 * @作者：csq
	 * @日期：2012-2-9 9:34:22
	 * @param entity
	 */
	public void saveKnowledge(TCommonKnowledge entity){
		dao.saveOrUpdate(entity);
	}

	/**
	 * @描述: 保存知识
	 * @作者：csq
	 * @日期：2012-2-9 9:34:22
	 * @param entity
	 */
	public void saveKnowledgeFile(TCommonKnowledgeFile entity){
		dao.saveOrUpdate(entity);
	}
	
	/**
	 * @描述: 删除知识
	 * @作者：csq
	 * @日期：2012-2-9 9:35:22 
	 * @param ids 知识主键值集合
	 */
	public void deleteKnowledges(String[] ids){
		for(String knowledgeId : ids){
			//删除知识
            this.deleteKnowledgeFileByKnowledgeId(knowledgeId);
            //删除知识文件
			sqlDao.getSqlMapClientTemplate().delete("CommonSQL.deleteKnowledge", new Long(knowledgeId));
		}
	}
	
	/**
	 * @描述: 删除知识文件
	 * @作者：csq
	 * @日期：2012-2-9 9:35:22  
	 * @param ids 知识文件主键值集合
	 */
	public void deleteKnowledgeFiles(String[] ids){
		dao.deleteByIds(TCommonKnowledgeFile.class, ids);
	}
	
	/**
	 * @描述: 删除知识文件
	 * @作者：csq
	 * @日期：2012-2-10 10:55:21  
	 * @param id 知识文件主键值
	 */
	public int deleteKnowledgeFile(String id){
		int result = 0;
		if(!StringUtil.isEmpty(id)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fileId", new Long(id));
			
			result = sqlDao.getSqlMapClientTemplate().delete("CommonSQL.deleteKnowledgeFile", map);
		}
		
		return result;
	}
	
	/**
	 * @描述: 根据知识主键删除知识文件
	 * @作者：csq
	 * @日期：2012-2-14 16:50:21  
	 * @param id 知识文件主键值
	 */
	public int deleteKnowledgeFileByKnowledgeId(String id){
		int result = 0;
		if(!StringUtil.isEmpty(id)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("knowledgeId", new Long(id));
			
			result = sqlDao.getSqlMapClientTemplate().delete("CommonSQL.deleteKnowledgeFile", map);
		}
		
		return result;
	}
	
	/**
	 * @描述: 发布知识
	 * @作者：csq
	 * @日期：2012-2-13 15:55:40  
	 * @param ids 知识主键值集合
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int publishKnowledges(final String[] ids, final String loginName){
		Integer result = new Integer(0);
		if(ids != null && ids.length > 0){
			result = (Integer)sqlDao.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				int count = 0;			
			    public Object doInSqlMapClient(SqlMapExecutor executor) throws
			        SQLException {			    	
			        executor.startBatch();

		        	Map<String, Object> map = new HashMap<String, Object>();
		        	map.put("publishTime", new Date());
		        	map.put("publishUser", loginName);
			        for (String id : ids) {
			        	map.put("knowledgeId", new Long(id));
			    	    count += executor.update("CommonSQL.updateKnowledge", map);
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
	 * @描述: 查询知识列表(分页)
	 * @作者：csq
	 * @日期：2012-2-9 9:36:36
	 * @param page
	 * @param model
	 * @param subModel
	 * @param pMap
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<TCommonKnowledge> findKnowledgeList(final Page<TCommonKnowledge> page,
			final TCommonKnowledge model,final KnowledgeModel subModel,final Map pMap) {
		String sql = "CommonSQL.findKnowledgeList";
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(model != null){
			map.putAll(ConvertUtils.pojoToMap(model));
		}
		
		if(subModel != null){
			map.putAll(ConvertUtils.pojoToMap(subModel));
		}
		
		if(pMap != null){
			map.putAll(pMap);
		}
		
		logger.info(map.toString());
		
		return sqlDao.findPage(page, sql, map);
	}
	
	/**
	 * @描述: 查询知识文件列表
	 * @作者：csq
	 * @日期：2012-2-9 9:37:46
	 * @param paraMap 查询参数
	 * @return
	 */
	public List<?> findKnowledgeFileList(Map<String, String> paraMap){
		final String sql = "CommonSQL.findKnowledgeFileList";
		return sqlDao.findList(sql, paraMap);
	}
	
	/**
	 * @描述: 查询首页知识列表
	 * @作者：csq
	 * @日期：2012-4-23 14:09:20
	 * @param paraMap 查询参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TCommonKnowledge> homeKnowledgeList(Map<String, Long> paraMap){
		final String sql = "CommonSQL.homeKnowledgeList";
		
		return sqlDao.findList(sql, paraMap);
	}
}
