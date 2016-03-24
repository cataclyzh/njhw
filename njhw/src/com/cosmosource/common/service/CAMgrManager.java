/**
* <p>文件名: CAMgrManager.java</p>
* <p>描述：用户管理Manager</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2011-11-23 下午03:21:23 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.ConvertUtils;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.common.entity.TAcCaaction;
import com.cosmosource.common.entity.TAcCaapply;
import com.cosmosource.common.entity.TAcCauser;
import com.cosmosource.common.entity.TAcCauseraction;
import com.cosmosource.common.entity.TAcCauserapply;
import com.cosmosource.common.model.CALicenseModel;
import com.cosmosource.common.model.CAUserActionModel;
import com.cosmosource.common.model.CAUserLicenseModel;
import com.cosmosource.common.model.CaModel;
import com.cosmosource.common.model.CaapplyAuditQueryModel;
import com.cosmosource.common.model.CafunctionQueryModel;

/**
 * @类描述:  CA的管理类
 * 
 * @作者： WXJ
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class CAMgrManager extends BaseManager{
	/**
	 * @描述: 查询CA实体
	 * @作者： yc
	 * @日期：2011-11-22
	 * @param page 分页对象
	 * @param model 页面主查询模型
	 * @param subModel　页面子查询模型
	 * @param pMap　页面查询中的特殊参数
	 * @return
	 */
	public Page<TAcCaapply> findCaList(final Page<TAcCaapply> page,
			final TAcCaapply model,final CaModel subModel,final Map pMap) {
		String sql = "CommonSQL.caQryList";
		Map<String, Object> map = new HashMap<String, Object>();
		if(subModel!=null){
			map.putAll(ConvertUtils.pojoToMap(subModel));
		}
		if(model!=null){
			map.putAll(ConvertUtils.pojoToMap(model));
		}
		if(pMap!=null){
			map.putAll(pMap);
		}
		logger.info(map.toString());
		return sqlDao.findPage(page, sql, map);
		
	}
	
	
	/**
	 * 查询caapply列表（分页）
	 * @param page
	 * @param entity
	 * @param caapplyQueryModel
	 * @param nmap
	 * @return
	 * ffj 2011-11-23
	 */
	public Page<TAcCaapply> findCaapplyList(Page<TAcCaapply> page, TAcCaapply entity, CaapplyAuditQueryModel caapplyQueryModel, Map nmap) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.putAll(ConvertUtils.pojoToMap(entity));
		if (caapplyQueryModel != null)
			map.putAll(ConvertUtils.pojoToMap(caapplyQueryModel));
		if (nmap != null)
			map.putAll(nmap);
		return sqlDao.findPage(page, "CommonSQL.findCaapplyAuditList", map);
	}
	
	/**
	 * 查询一个
	 * @param id
	 * @return
	 * ffj 2011-11-24
	 */
	public TAcCaapply findById(Long id){
		return (TAcCaapply) dao.findById(TAcCaapply.class, id);
	}
	
	/**
	 * 修改CA资料
	 * @param entity
	 * ffj 2011-11-24
	 */
	public void update(TAcCaapply entity){
		dao.update(entity);
	}
	/*
	 * 得到某一用户信息
	 */
	public TAcCaapply findCaById(Long id){
		return (TAcCaapply) dao.get(TAcCaapply.class, id);
	}
	
	/**
	 * @描述: 根据Ca信息ID数组  修改CA实体对象删除状态为已删除 进行逻辑删除操作
	 * @作者： yc
	 * @日期：2011-11-24
	 * @param deleteUser 删除用户名
	 * @param caIds Ca信息ID数组
	 * @return
	 * @throws Exception 
	 */
	public String deleteCaByIds(String deleteUser,long... caIds) {
		// 在删除之前首先确认CA状态确定是否可删除，下面字符串存放不可删除CA号
		String changeCaNos = "";
		for(long caId : caIds){
				
			TAcCaapply caApply = (TAcCaapply)this.findById(TAcCaapply.class, caId);
			if(caApply != null){
				String auditstatus = caApply.getAuditstatus();
				String delstatus = caApply.getDelstatus();
				//当审核状态(0－未审核 1－审核通过 2－审核不通过)不等于0 时 , 抛出不能删除的异常
				if((!auditstatus.trim().equals("0"))||(delstatus.trim().equals("1"))){
					changeCaNos = changeCaNos + caApply.getCaid() + ";";
					continue;
				}
					
				//修改CA信息状态
				caApply.setDelstatus("1");//已删除
					
				//保存CA实体
				this.dao.update(caApply);
			}
		}
		return changeCaNos;
		
	}
	

//	/**
//	 * 查询caapply列表（分页）
//	 * @param page
//	 * @param entity
//	 * @param caapplyQueryModel
//	 * @param nmap
//	 * @return
//	 * ffj 2011-11-23
//	 */
//	@SuppressWarnings("unchecked")
//	public Page<TAcCaapply> findCaapplyList(Page<TAcCaapply> page,
//			TAcCaapply entity, CaapplyAuditQueryModel caapplyQueryModel,
//			Map nmap) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.putAll(ConvertUtils.pojoToMap(entity));
//		if (caapplyQueryModel != null)
//			map.putAll(ConvertUtils.pojoToMap(caapplyQueryModel));
//		if (nmap != null)
//			map.putAll(nmap);
//		return sqlDao.findPage(page, "CommonSQL.findCaapplyAuditList", map);
//	}
	
	
//	/**
//	 * 修改CA资料
//	 * @param entity
//	 * ffj 2011-11-24
//	 */
//	public void update(TAcCaapply entity){
//		dao.update(entity);
//	}
	
	/**
	 * 查询caapply列表（分页）
	 * 
	 * @param page
	 * @param paraMap
	 */
	public Page<TAcCaapply> findCaApply4LicenseList(Page<TAcCaapply> page, TAcCaapply entity,
			CALicenseModel caLicenseModel, Map paraMap) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.putAll(ConvertUtils.pojoToMap(entity));
		if (caLicenseModel != null){
			map.putAll(ConvertUtils.pojoToMap(caLicenseModel));
		}
		if (paraMap != null){
			map.putAll(paraMap);
		}
		return sqlDao.findPage(page, "CommonSQL.findCaApply4LicenseList", map);
	}

//	/**
//	 * @param id
//	 */
//	public TAcCaapply findById(Long id) {
//		return (TAcCaapply) dao.findById(TAcCaapply.class, id);
//	}
	/**
	 * 查询申请单下所有用户信息
	 * @param applyno
	 * @return
	 */
	public List<TAcCauserapply> findCaUserApplayByNo(String applyno) {
		
		List<TAcCauserapply> tAcCauserapplies = dao.findByProperty(TAcCauserapply.class, "applyno", applyno);
		return tAcCauserapplies;
	}
	/**
	 * 保存用户与key的对应关系，修改caapply发证状态
	 * @param caUserLicenseModels
	 * @param entity
	 * @throws Exception 
	 */
	public void saveTAcCaapplyAndUser(List<CAUserLicenseModel> caUserLicenseModels, TAcCaapply entity) throws Exception {
		
		for (CAUserLicenseModel caUserLicenseModel : caUserLicenseModels) {
			String loginname = caUserLicenseModel.getLoginname();
			String[] loginnames = loginname.split(",");
			for (String sloginname : loginnames) {
				TAcCauser tAcCauser = new TAcCauser();
				tAcCauser.setLoginname(sloginname);
				tAcCauser.setCano(caUserLicenseModel.getCano());
				//2012.04.05 设置 applyno
				tAcCauser.setApplyno(entity.getApplyno());
				Long causerid = dao.getSeqNextVal("SEQ_AC_CAUSER");
				tAcCauser.setCauserid(causerid);
				try {
					dao.saveOrUpdate(tAcCauser);
				} catch (HibernateException e) {
					logger.error("保存CAUSER失败",e);
					throw new Exception();
				}
			}
		}
		//是否已制证(0－否 1－是)
		entity.setIsgenca("1");
		try {
			dao.saveOrUpdate(entity);
		} catch (HibernateException e) {
			logger.error("更新CAAPPLY失败",e);
			throw new Exception();
		}
	}
	
	public TAcCaapply findApplyById(String caid){
		return (TAcCaapply)dao.load(TAcCaapply.class, new Long(caid));
	}
	
	public Page<?> findCaUserByNo(Page<?> page, Map<String, ?> values) {
		String hql = "from TAcCauserapply where applyno=:applyno";
		return dao.findPage(page, hql, values);	
	}

	
    //--csq add start
	 
	/**
	 * 保存申请表
	 * @param entity
	 * @throws Exception
	 */
	public void saveApply(TAcCaapply entity) throws Exception{
		if(entity.getCaid() == null){
			Long caid = dao.getSeqNextVal("SEQ_AC_CAAPPLY");
			logger.info("caid : " + caid);
			entity.setCaid(caid);
		}
		entity.setApplyno(this.makeApplyno(entity.getCaid().toString()));
		
		dao.saveOrUpdate(entity);
	}
	
	/**
	 * 格式化CA申请号
	 * @param code
	 * @return
	 */
	private String formatCode(String code){
		StringBuffer formatCode = new StringBuffer();
		int fullCount = 4;
		if(StringUtils.isNotEmpty(code)){
			int appendCount = fullCount - code.length();
			for(int i = 0; i < appendCount; i++){
				formatCode.append("0");
			}
			formatCode.append(code);
		}
		
		return formatCode.toString();
	}
	
	/**
	 * 生成CA申请号
	 * @param caid
	 * @return
	 */
	private String makeApplyno(String caid){
		StringBuffer applyno = new StringBuffer();
		applyno.append(DateUtil.getDateTime("yyyyMMdd")).append(this.formatCode(caid));
		logger.info("申请号：" + applyno);
		
		return applyno.toString();
	}
	
	/**
	 * 保存印章
	 * @param entity
	 * @throws Exception
	 */
	public void saveApplyStamp(TAcCaapply entity)throws Exception{
		TAcCaapply loadEntity = (TAcCaapply)dao.load(TAcCaapply.class, entity.getCaid());
		loadEntity.setHandlerstamp(entity.getHandlerstamp());
		loadEntity.setStepcode(entity.getStepcode());
		dao.merge(loadEntity);
	}
	
	/**
	 * 查询相同纳税人识别号的用户信息
	 * @param map
	 * @return
	 */
	public List<?> findUsersWithTaxno(final Map map) {	
		return sqlDao.findList("CommonSQL.findUsersWithTaxno", map);
	}
	
	/**
	 * 保存CA帐号用户信息
	 * @param userApplyList
	 * @throws Exception
	 */
	public void saveUserApplyList(List<TAcCauserapply> userApplyList) throws Exception{
		for(TAcCauserapply userApply : userApplyList){
			Long causerapplyid = dao.getSeqNextVal("SEQ_AC_CAUSERAPPLY");
			userApply.setCauserapplyid(causerapplyid);
			dao.saveOrUpdate(userApply);
		}
	}
	
	/**
	 * 保存用户申请表
	 * @param userApply
	 */
	public void saveUserApply(TAcCauserapply userApply){
		Long causerapplyid = dao.getSeqNextVal("SEQ_AC_CAUSERAPPLY");
		userApply.setCauserapplyid(causerapplyid);
		dao.saveOrUpdate(userApply);
	}

	/**
	 * 查询用户已经申请次数
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public Long countUserApply(final Map map) throws SQLException{
		return (Long)sqlDao.getSqlMapClientTemplate().queryForObject("CommonSQL.countUserApply", map);
	}

	/**
	 * 查询当前申请步骤信息
	 * @param map
	 * @return
	 */
	public List<?> findCurrentApplyStep(final Map map) {	
		return sqlDao.findList("CommonSQL.currentApplyStep", map);
	}
	
	/**
	 * 生成更新申请表为提交状态的hql语句
	 * @return
	 */
	private String makeUpdateApplyHql(){
		StringBuffer updateApplyHql = new StringBuffer();
		updateApplyHql.append("update TAcCaapply set ")
		.append("stepcode='relation', ")
		.append("issubmit='1'")
		.append("where caid=:caid");
		
		return updateApplyHql.toString();
	}
	
	/**
	 * 更新申请表信息为提交状态
	 * @param values
	 */
	public void updateApplySubmit(Map<String, ?> values){
		dao.batchExecute(this.makeUpdateApplyHql(), values);
	}
	
	public Page<?> findCaUserActionList(Page<?> page, CAUserActionModel userActionModel, Map paraMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(userActionModel != null){
			map.putAll(ConvertUtils.pojoToMap(userActionModel));
		}

		if(paraMap!=null){
			map.putAll(paraMap);
		}
		return sqlDao.findPage(page, "CommonSQL.findUserActionList", map);
	}
	
	public void deleteCaUserAction(String[] ids){
		dao.deleteByIds(TAcCauseraction.class, ids);
	}
	
	public List<TAcCaaction> getAllCaAction(){
		return (List<TAcCaaction>)dao.find(TAcCaaction.class);
	}
	
	public void saveCaUserActions(List<TAcCauseraction> userActionList){
		for(TAcCauseraction userAction : userActionList){
			Long causeractionid = dao.getSeqNextVal("SEQ_AC_CAUSERACTION");
			logger.info("causeractionid : " + causeractionid);
			userAction.setCauseractionid(causeractionid);
			dao.save(userAction);
		}
	}
	
	public List<TAcCauseraction> findCaUserActionByLoginname(String loginname) throws Exception{
		return dao.findByProperty(TAcCauseraction.class, "loginname", loginname);
	}
	
	/**
	 * 修改CA用户操作
	 * @param entity
	 */
	public void update(TAcCauseraction entity){
		dao.update(entity);
	}
	
	/**
	 * 查询CA用户没有关联的操作
	 * @param loginname
	 * @return
	 * @throws Exception
	 */
	public List<?> findUserNotHaveActionList(final String loginname) throws Exception{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("loginname", loginname);
		String sql = "CommonSQL.findUserNotHaveActionList";
	    return sqlDao.findList(sql, paramMap);	
	}
	
	/**
	 * 查询一个用户是否存在
	 * @param loginname
	 * @return
	 * @throws Exception
	 */
	public boolean isUserExist(final String loginname) throws Exception{		
		Map<String, String> paramMap = new HashMap<String, String>();
	    paramMap.put("loginname", loginname);

		return (Long)sqlDao.getSqlMapClientTemplate().queryForObject("CommonSQL.countUser", paramMap) > 0 ? true : false;
	}
	
	public List<?> findCaaction4Mem() throws Exception{
		return sqlDao.findList("CommonSQL.findCaaction4Mem", null);
	}
	
	public List<?> findCauser4Mem() throws Exception{
		return sqlDao.findList("CommonSQL.findCauser4Mem", null);
	}

        //--csq add end
	
	public Page<TAcCauser> findCauserList(Page<TAcCauser> page,
			TAcCauser entity, CaModel caModel, Map<String,Object> pMap) {
		String sql = "CommonSQL.causerQryList";
		Map<String, Object> map = new HashMap<String, Object>();
		if(caModel!=null){
			map.putAll(ConvertUtils.pojoToMap(caModel));
		}
		if(entity!=null){
			map.putAll(ConvertUtils.pojoToMap(entity));
		}
		if(pMap!=null){
			map.putAll(pMap);
		}
		return sqlDao.findPage(page, sql, map);
	}


	public TAcCauser findCauserById(Long causerid) {
		return (TAcCauser)dao.load(TAcCauser.class, new Long(causerid));
	}

	/**
	 * 修改CA用户关联
	 * @param entity
	 * yc 2011-12-14
	 */
	public void update(TAcCauser entity){
		dao.update(entity);
	}
	
	/**
	 * 删除CA用户关联
	 * @param entity
	 * yc 2011-12-14
	 */
	public void delete(TAcCauser entity){
		dao.deleteById(TAcCauser.class, entity.getCauserid());
	}

	/* ca功能维护模块 ffj add start 2011年12月14日 */
	/**
	 * 查询ca功能维护列表（分页）
	 * 
	 * @param page
	 * @param entity
	 * @param cafunctionQueryModel
	 * @param nmap
	 * @return ffj 2011-12-14
	 */
	public Page<TAcCaaction> findCaFunctionList(Page<TAcCaaction> page,
			TAcCaaction entity, CafunctionQueryModel cafunctionQueryModel,
			Map nmap) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.putAll(ConvertUtils.pojoToMap(entity));
		if (cafunctionQueryModel != null)
			map.putAll(ConvertUtils.pojoToMap(cafunctionQueryModel));
		if (nmap != null)
			map.putAll(nmap);
		return sqlDao.findPage(page, "CommonSQL.findCaFunctionList", map);
	}

	/**
	 * ca功能维护查询明细
	 * 
	 * @param actionid
	 * @return ffj 2011-12-14
	 */
	public TAcCaaction findByActionId(long actionid) throws Exception {
		return (TAcCaaction) dao.findById(TAcCaaction.class, actionid);
	}

	/**
	 * 增加ca功能维护
	 * 
	 * @param entity
	 *            ffj 2011-12-14
	 * @throws Exception
	 */
	public void saveCaFunction(TAcCaaction entity) throws Exception {
		dao.saveOrUpdate(entity);
	}

	/**
	 * ca功能维护删除方法
	 * 
	 * @param ids
	 *            ffj 2011-12-14
	 */
	public void deleteCaFunction(String[] ids) throws Exception {
		dao.deleteByIds(TAcCaaction.class, ids);
	}
	
	/**
	 * ca功能维护删除方法
	 * 
	 * @param ids
	 *            ffj 2011-12-14
	 */
	public void deleteCaFunction(Long id) throws Exception {
		dao.deleteById(TAcCaaction.class, id);
	}
	
	public void updateCaFunction(TAcCaaction entity){
		dao.update(entity);
	}
	
	/**
	 * 验证actioncode是否唯一
	 * @param actioncode
	 * @return
	 * ffj 2011-12-19
	 */
	public boolean isActionCodeUnique(String actioncode){
		return dao.isPropertyUnique(TAcCaaction.class,"actioncode", actioncode, null);
	}
	
	/**
	 * 查询actioncode在表T_AC_CAUSERACTION中是否存在
	 * @param actioncode
	 * @return
	 * ffj 2011-12-19
	 */
	public TAcCauseraction findCauseractionByactioncode(String actioncode){
		List<TAcCauseraction> entity = dao.findByProperty(TAcCauseraction.class, "actioncode", actioncode);
		if (entity != null && entity.size() > 0) {
			TAcCauseraction n = entity.get(0);
			return n;
		}
		return null;
	}

	/* ca功能维护模块 end */
	
	/**
	 * @描述:根据供应商税号获取供应商编码
	 * @作者：fjy
	 * @日期：2012-03-31
	 * 通过供应商登录名获取ca发证后的applyno
	 * @param loginname
	 * @return
	 */
	public String getCauserApplyNoByLoginname(String loginname){
		List applyList = dao.createSQLQuery("select t.applyno from t_ac_causer t where t.loginname='"+ loginname +"'").list();
		if(applyList.size() == 0){
			return "";
		}else
			return applyList.get(0) == null ? "":applyList.get(0).toString();
	}
	
	/**
	 * @描述:通过applyno获取ca申请信息
	 * @作者：fjy
	 * @日期：2012-03-31
	 * @param applyno
	 * @return
	 */
	public TAcCaapply getCaapplyByApplyno(String applyno){
		List<TAcCaapply> caapplyList = dao.findByProperty(TAcCaapply.class, "applyno", applyno);
		if(caapplyList.isEmpty() || caapplyList == null){
			return null;
		}else
			return (TAcCaapply)caapplyList.get(0);
	}
	
}
