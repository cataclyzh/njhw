package com.cosmosource.app.cms.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.CmsArticle;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;

/** 
* @description: 内容管理的操作
* @author qiyanqiang
* @date 2013-05-04
*/
@SuppressWarnings("unchecked")
public class CmsManager extends BaseManager {
	
	/**
	 * @description: 根据标题和栏目的名称查询
	 * @author qiyanqiang
	 * @param map
	 * @return Page<HashMap>
	 */

	public Page<HashMap> queryCmsContent(Page<HashMap> page, Map map) {
		return sqlDao.findPage(page, "CsmSQL.selectCmsList", map);
	}
	
	/**
	 * ip电话帮助
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<HashMap> queryIpHelp(Page<HashMap> page, Map map) {
		return sqlDao.findPage(page, "CsmSQL.selectIpHelpList", map);
	}
	
	public List queryArticle(Map param){
		return sqlDao.findList("CsmSQL.queryCmsList", param);
	}
	
	/**
	 * @description: 保存
	 * @author qiyanqiang
	 * @param cmsArticle
	 */
	public void saveArticle(CmsArticle cmsArticle,String inputtype,String content1) {
		CmsArticle article = null;
		if (cmsArticle.getId() != null && cmsArticle.getId() > 0) {
			article = (CmsArticle) dao.findById(CmsArticle.class, cmsArticle.getId());
		} else {
			article = new CmsArticle();
		}
		if(cmsArticle.getMid()==Long.parseLong("3")){
			article.setContent(content1);
			
		}else{
			
			article.setContent(cmsArticle.getContent());
		}
		
		article.setTitle(cmsArticle.getTitle());
		article.setEdittime(DateUtil.getSysDate());
		if(inputtype!="" && inputtype != null) article.setMid(1l);
		else article.setMid(cmsArticle.getMid());
		article.setWeight(cmsArticle.getWeight());
		
		if (cmsArticle.getId() != null && cmsArticle.getId() > 0) {
			dao.update(article);
		} else {
			dao.save(article);
		}
		//dao.flush();
	}
	
	/**
	 * 
	* @title: saveChannelArticle 
	* @description: 保存分类文章
	* @author herb
	* @param cmsArticle
	* @param content1
	* @date May 23, 2013 6:53:25 PM     
	* @throws
	 */
	public void saveChannelArticle(CmsArticle cmsArticle,String content1) {
		if (cmsArticle.getId() != null && cmsArticle.getId() > 0) {
			CmsArticle dbCmsArticle = (CmsArticle) dao.findById(CmsArticle.class, cmsArticle.getId());
			dbCmsArticle.setContent(cmsArticle.getContent());
			dbCmsArticle.setMid(cmsArticle.getMid());
			dbCmsArticle.setTitle(cmsArticle.getTitle());
			dbCmsArticle.setWeight(cmsArticle.getWeight());
			cmsArticle = dbCmsArticle;
		} else {
			cmsArticle.setCreatetime(DateUtil.getSysDate());
		}
		
		if(cmsArticle.getMid()==Long.parseLong("3")){
			cmsArticle.setContent(content1);
		} else if (cmsArticle.getMid()==Long.parseLong("1")){
			cmsArticle.setCartFlag(CmsArticle.NO_CLAIM);
			cmsArticle.setCreateName(Struts2Util.getSession().getAttribute(Constants.USER_NAME).toString());
		}
		
		cmsArticle.setEdittime(DateUtil.getSysDate());
		if (cmsArticle.getId() != null && cmsArticle.getId() > 0) {
			dao.update(cmsArticle);
		} else {
			dao.save(cmsArticle);
		}
		//dao.flush();
	}
	
	/**
	 * 查询IP电话帮助总数
	 * @return
	 */
	public List queryCmsAtricleCount(){
		Map param = new HashMap();
		return this.findListBySql("CsmSQL.selectIpHelpCount", param);
	}
	
	public List<Map> queryCmsAtricleMap(){
		Map param = new HashMap();
		return this.findListBySql("CsmSQL.selectIpHelpCount", param);
	}
	
	public void updateCmsAtricleWeight(List pList){
		this.updateBatchBySql("CsmSQL.updateIpHelpWeightById", pList);
	}
	
	public void updateCmsAtricleWeights(List pList){
		this.updateBatchBySql("CsmSQL.updateIpHelpWeightByIds", pList);
	}
	
	public CmsArticle queryCmsAtricleMap(long id){
		return (CmsArticle)this.findById(CmsArticle.class, id);
	}
	
	/**
	 * 
	* @title: saveClaim 
	* @description: 保存认领信息
	* @author herb
	* @param cmsArticle
	* @param content1
	* @date May 23, 2013 6:53:25 PM     
	* @throws
	 */
	public void saveClaim(CmsArticle cmsArticle) {
		
		CmsArticle dbCmsArticle = (CmsArticle) dao.findById(CmsArticle.class, cmsArticle.getId());
		dbCmsArticle.setCartDate(DateUtil.getSysDate());
		dbCmsArticle.setCartUname(Struts2Util.getSession().getAttribute(Constants.USER_NAME).toString());
		dbCmsArticle.setCartUserid(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		dbCmsArticle.setCartNum(cmsArticle.getCartNum());
		dbCmsArticle.setCartFlag(CmsArticle.CLAIM);
		dbCmsArticle.setCartPhone(cmsArticle.getCartPhone());
		dbCmsArticle.setCartName(cmsArticle.getCartName());
		dbCmsArticle.setCartMemo(cmsArticle.getCartMemo());
		dao.update(dbCmsArticle);
		//dao.flush();
	}
	
	/**
	 * @description:批量删除
	 * @author  qiyanqiang
	 * @param ids
	 * @date 2013-05-04
	 */
	public void deleteArticle(String[] ids){
		dao.deleteByIds(CmsArticle.class, ids);
	}
	
}


