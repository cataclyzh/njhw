/**
* <p>文件名: LevelAction.java</p>
* <p>:描述：知识查询</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2012-8-2 10:21:30 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.JsonUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TAcLevel;
import com.cosmosource.common.entity.TAcOrg;
import com.cosmosource.common.model.LevelModel;
import com.cosmosource.common.service.LevelManager;

/**
 * 
 * @类描述: 级别查询Action
 * @作者： WXJ
 *
 */
public class LevelAction extends BaseAction<TAcLevel> {
	
	private static final long serialVersionUID = 4219715360994887915L;

	private LevelManager levelManager;
	
	private LevelModel levelModel = new LevelModel();
	
	private TAcLevel levelEntity = new TAcLevel();
	
	private Page<Map<String, Object>> page = new Page<Map<String, Object>>(Constants.PAGESIZE);

	private TAcOrg entityOrg = new TAcOrg();
	
	private Page<Map<String, Object>> pageVendor = new Page<Map<String, Object>>(Constants.PAGESIZE);

	private String _chk[];
	
	/**
	 * 公司编码
	 */
	private String company;
	
	public TAcLevel getModel() {
		return levelEntity;
	}

	@Override
	protected void prepareModel() throws Exception {
        Long levelId = levelEntity.getLevelId();
        if(levelId != null){
        	levelEntity = (TAcLevel)levelManager.findById(TAcLevel.class, levelId);
        }
        else {
        	levelEntity = new TAcLevel();
        	levelEntity.setCompany(company);
        }
	}
	
	/**
	 * @描述: 级别列表
	 * @作者： WXJ
	 * @日期：2012-8-2
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String levelList() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter levelList...");
		}
		String sql = "CommonSQL.findLevelList";
		if(StringUtils.isEmpty(levelModel.getCompany())){
			logger.info("company:" + company);
			levelModel.setCompany(company);
		}
		else {
			logger.info("从levelModel中获取company：" + levelModel.getCompany());
		}

		page = levelManager.findPageListBySql(page, sql, null, levelModel, null);
		
		return LIST;
	}
	
	/**
	 * @描述: 指定级别供应商列表
	 * @作者： WXJ
	 * @日期：2012-8-2
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String levelVendorList() throws Exception{
		if(logger.isDebugEnabled()){
		    logger.debug("enter levelVendorList...");
		}
		String levelCode = getParameter("levelCode");
		logger.info("levelCode:" + levelCode);
		String sql = "CommonSQL.findLevelVendorList";
		levelModel.setCompany(company);
		levelModel.setLevelCode(levelCode);
		pageVendor = levelManager.findPageListBySql(pageVendor, sql, entityOrg, levelModel, null);
		
		return LIST;
	}

	/**
	 * @描述: 显示修改供应商级别页面
	 * @作者： WXJ
	 * @日期：2012-8-2
	 * 
	 * @return
	 * @throws Exception
	 */
	public String showUpdateVendorLevel() throws Exception{
		if(logger.isDebugEnabled()){
		    logger.debug("enter showUpdateVendorLevel...");
		}
		String orgIds = getParameter("orgIds");
		String levelCode = getParameter("levelCode");
		logger.info("orgIds:" + orgIds);
		levelModel.setOrgIds(orgIds);
		levelModel.setCompany(company);
		levelModel.setLevelCode(levelCode);

		return EDIT;
	}
	
	/**
	 * @描述: 级别列表json文件
	 * @作者： WXJ
	 * @日期：2012-8-2
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked" })
	public String getLevelList() throws Exception{
		if(logger.isDebugEnabled()){
		    logger.debug("enter getLevelList...");
		}
		Map<String, String> paraMap = new HashMap<String, String>();
		String company = getParameter("company");
		logger.info("company:" + company);
		paraMap.put("company", company);
		try{
			String sql = "CommonSQL.findLevelList";
			page.setAutoCount(false);
			page = levelManager.findPageListBySql(page, sql, null, null, paraMap);
			logger.info("levelList size is " + page.getResult().size());
			Struts2Util.renderJson(JsonUtil.beanToJson(page.getResult()), "encoding:UTF-8", "no-cache:true");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * @描述: 修改供应商级别页面
	 * @作者： WXJ
	 * @日期：2012-8-2
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateVendorLevel() throws Exception{
		if(logger.isDebugEnabled()){
		    logger.debug("enter updateVendorLevel...");
		}
		try{
			String changeLevelCode = getParameter("changeLevelCode");
			String orgIds = levelModel.getOrgIds();

			logger.info("changeLevelCode:" + changeLevelCode);
			logger.info("orgIds:" + orgIds);
			String[] orgIdArr = StringUtils.splitPreserveAllTokens(orgIds, ",");
			levelManager.updateVendorLevel(orgIdArr, changeLevelCode);
		    setIsSuc("true");
		    return SUCCESS;
		} catch (Exception e) {
			logger.error("更新供应商级别异常！", e);
			setIsSuc("false");
			return ERROR;
		}
	}
	
	/**
	 * @描述: 显示修改级别页面
	 * @作者： WXJ
	 * @日期：2012-8-3
	 * 
	 * @return
	 * @throws Exception
	 */
	public String levelInput() throws Exception{
		if(logger.isDebugEnabled()){
		    logger.debug("enter levelInput...");
		}
        
		return INPUT;
	}
	
	/**
	 * @描述: 修改级别页面
	 * @作者： WXJ
	 * @日期：2012-8-3
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateLevel() throws Exception{
		if(logger.isDebugEnabled()){
		    logger.debug("enter updateLevel...");
		}
		try{
			levelManager.saveOrUpdate(levelEntity);
		    setIsSuc("true");
	        return SUCCESS;
	    } catch (Exception e) {			
	    	logger.error("保存级别异常！", e);
		    setIsSuc("false");
		    return ERROR;
	    }
	}
	
	/**
	 * @描述: 删除级别
	 * @作者： WXJ
	 * @日期：2012-8-3
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteLevels() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter deleteLevels...");
		}
		
		try {
			levelManager.deleteLevels(_chk);
			addActionMessage("删除级别数据成功！");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			addActionMessage("删除级别数据失败！");
			return ERROR;
		}
	}
	
	/**
	 * @描述: 查询已经被使用的级别列表
	 * @作者： WXJ
	 * @日期：2012-8-6
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryUsedLevelCodeList() throws Exception{
		if(logger.isDebugEnabled()){
		    logger.debug("enter queryUsedLevelCodeList...");
		}
		String levelCodes = getParameter("levelCodes");
		logger.info("levelCodes:" + levelCodes);
		try{
			String[] levelCodeArr = StringUtils.splitPreserveAllTokens(levelCodes, ",");
			List<Map<String, Object>> usedLevelCodeList = levelManager.queryUsedLevelCodeList(levelCodeArr);
			logger.info("usedLevel codeList size is " + usedLevelCodeList.size());
			Struts2Util.renderJson(JsonUtil.beanToJson(usedLevelCodeList), "encoding:UTF-8", "no-cache:true");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	//--services
	public LevelManager getLevelManager() {
		return levelManager;
	}

	public void setLevelManager(LevelManager levelManager) {
		this.levelManager = levelManager;
	}

	//--properties
	public LevelModel getLevelModel() {
		return levelModel;
	}

	public void setLevelModel(LevelModel levelModel) {
		this.levelModel = levelModel;
	}

	public Page<Map<String, Object>> getPage() {
		return page;
	}

	public void setPage(Page<Map<String, Object>> page) {
		this.page = page;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Page<Map<String, Object>> getPageVendor() {
		return pageVendor;
	}

	public void setPageVendor(Page<Map<String, Object>> pageVendor) {
		this.pageVendor = pageVendor;
	}

	public TAcOrg getEntityOrg() {
		return entityOrg;
	}

	public void setEntityOrg(TAcOrg entityOrg) {
		this.entityOrg = entityOrg;
	}

	public TAcLevel getLevelEntity() {
		return levelEntity;
	}

	public void setLevelEntity(TAcLevel levelEntity) {
		this.levelEntity = levelEntity;
	}

	public String[] get_chk() {
		return _chk;
	}

	public void set_chk(String[] _chk) {
		this._chk = _chk;
	}

}
