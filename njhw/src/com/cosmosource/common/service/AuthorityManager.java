package com.cosmosource.common.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.Users;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.ServiceException;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.common.entity.TAcMenu;
import com.cosmosource.common.entity.TAcOrg;
import com.cosmosource.common.entity.TAcRole;
import com.cosmosource.common.entity.TAcSubsys;
import com.cosmosource.common.entity.TAcUser;
import com.cosmosource.common.security.Logininfo;
import com.cosmosource.common.security.SecurityConstant;
import com.cosmosource.common.security.SpringSecurityUtils;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 * 
 * @author WXJ
 */
public class AuthorityManager extends BaseManager{

	private static Logger logger = LoggerFactory.getLogger(AuthorityManager.class);
	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		dao.delete(id);
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}
	
	/**
	 * 
	* @Title: findUserByLoginName 
	* @Description: TODO
	* @author WXJ
	* @date 2013-4-6 上午11:19:14 
	* @param @param loginname
	* @param @return    
	* @return Users 
	* @throws
	 */
	public Users findUserByLoginName(String loginname) {
		List<Users> usersList = this.dao.findByProperty(Users.class,"loginUid", loginname);
		Users user = null;
		
		if (usersList!=null && usersList.size()>0) {
			user = (Users)usersList.get(0);
		}
				
		return user;
	}
	
	/**
	 * 
	* @Title: findDefaultPageByUserid 
	* @Description: TODO
	* @author WXJ
	* @date 2013-4-19 下午09:35:48 
	* @param @param userid
	* @param @return    
	* @return String 
	* @throws
	 */
	public String findDefaultPageByUserid(String userid) {
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("userid", userid);
		para.put("type", "user");
		List<String> dpList = this.sqlDao.findList("CommonSQL.getDefaultPage", para);
		
		String rtn = "";
		if (dpList!=null && dpList.size()>0) {
			rtn = dpList.get(0);
		}
				
		return rtn;
	}
		
	/**
	 * 
	* @Title: updateUsers 
	* @Description: TODO
	* @author WXJ
	* @date 2013-4-6 上午11:19:03 
	* @param @param user    
	* @return void 
	* @throws
	 */
	public void updateUsers(Users user) {
		this.dao.update(user);
			
	}
	
	/**
	 * @描述:查询主菜单
	 * @作者：WXJ
	 * @日期：2012-4-19
	 * @param subSysId
	 * @param roleIds
	 * @param orgId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TAcMenu> getRootMenu(String subSysId, String roleIds, String orgId){
		StringBuilder sql = new StringBuilder();
		//一级菜单
		sql.append("select distinct b from TAcRoleright a,TAcMenu b, TAcOrg c ")
			.append("where a.menuid=b.menuid and b.orgid=c.orgid and a.roleid in " + roleIds + " and c.orgid=? ")
			.append("and b.treelevel=? ");
		if(StringUtil.isNotBlank(subSysId)){
			sql.append("and b.subsysid = "+subSysId);
		}
		sql.append(" order by b.parentid,b.sortno");
		
		List<TAcMenu> list = dao.findByHQL(sql.toString(), Long.valueOf(orgId), new Long(1));
		
		return list;
	}
	
	/**
	 * @描述:查询子菜单
	 * @作者：WXJ
	 * @日期：2012-4-19
	 * @param rootMenuId
	 * @param roleIds
	 * @param orgId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TAcMenu> getSubMenu(String rootMenuId, String roleIds, String orgId){
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct b from TAcRoleright a,TAcMenu b, TAcOrg c ")
			.append("where a.menuid=b.menuid and b.orgid=c.orgid and a.roleid in  "+ roleIds +"  and c.orgid=? ")
			.append("and b.treelevel=? and b.parentid=? ")
			.append("order by b.parentid,b.sortno");
		List<TAcMenu> listSub = dao.findByHQL(sql.toString(), Long.valueOf(orgId), new Long(2), Long.valueOf(rootMenuId));
	    
		return listSub;
	}
	
	@SuppressWarnings("unchecked")
	public List<TAcSubsys> getSysList(Long orgid){
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct b ")
		.append(" from TAcSubsys b ");
		
		return dao.findByHQL(sb.toString());
	}
	
	
	
	
	/**
	 * @描述:更新密码错误次数
	 * @作者：WXJ
	 * @日期：2012-6-4
	 * @param loginname 用户登录名
	 * @param wrongCount 密码错误次数
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int updatePasswordWrongCount(final String loginname, final Integer wrongCount){
		Integer result = new Integer(0);
		if(StringUtils.isNotEmpty(loginname)){
			result = (Integer)sqlDao.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {		
			    public Object doInSqlMapClient(SqlMapExecutor executor) throws
			        SQLException {
		        	Map<String, Object> map = new HashMap<String, Object>();
		        	map.put("loginname", loginname);
		        	map.put("wrongCount", wrongCount);
		    	    return executor.update("CommonSQL.updatePasswordWrongCount", map);
			    }
			});
		}
		
		return result;
	}
	
	/**
	 * @描述:密码错误次数清零
	 * @作者：WXJ
	 * @日期：2012-6-4
	 * @param loginname 用户登录名
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int updatePasswordWrongCountZero(final String loginname){
		Integer result = new Integer(0);
		if(StringUtils.isNotEmpty(loginname)){
			result = (Integer)sqlDao.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {		
			    public Object doInSqlMapClient(SqlMapExecutor executor) throws
			        SQLException {
		        	Map<String, Object> map = new HashMap<String, Object>();
		        	map.put("loginname", loginname);
		    	    return executor.update("CommonSQL.clearPasswordWrongCount", map);
			    }
			});
		}
		
		return result;
	}
	
	/**
	 * @描述:查询密码错误次数
	 * @作者：WXJ
	 * @日期：2012-6-4
	 * @param loginname 用户登录名
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Long findPasswordWrongCount(final String loginname){
		Long result = new Long(0);
		if(StringUtils.isNotEmpty(loginname)){
			result = (Long)sqlDao.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {			
			    public Object doInSqlMapClient(SqlMapExecutor executor) throws
			        SQLException {
		        	Map<String, Object> map = new HashMap<String, Object>();
		        	map.put("loginname", loginname);
		    	    return (Long)executor.queryForObject("CommonSQL.findPasswordWrongCount", map);
			    }
			});
		}
		
		return result;
	}
	
	/**
	 * @描述:更新用户状态
	 * @作者：WXJ
	 * @日期：2012-6-5
	 * @param loginname 用户登录名
	 * @param status 用户状态
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int updateUserState(final String loginname, final String status){
		Integer result = new Integer(0);
		if(StringUtils.isNotEmpty(loginname)){
			result = (Integer)sqlDao.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {		
			    public Object doInSqlMapClient(SqlMapExecutor executor) throws
			        SQLException {
		        	Map<String, Object> map = new HashMap<String, Object>();
		        	map.put("loginname", loginname);
		        	map.put("status", status);
		    	    return executor.update("CommonSQL.updateUserStatus", map);
			    }
			});
		}
		
		return result;
	}
	
	/**
	 * @描述:更新用户状态为锁定
	 * @作者：WXJ
	 * @日期：2012-6-7
	 * @param loginname 用户登录名
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int updateUserLocked(final String loginname){
		Integer result = new Integer(0);
		if(StringUtils.isNotEmpty(loginname)){
			result = (Integer)sqlDao.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {		
			    public Object doInSqlMapClient(SqlMapExecutor executor) throws
			        SQLException {
		        	Map<String, Object> map = new HashMap<String, Object>();
		        	map.put("loginname", loginname);
		    	    return executor.update("CommonSQL.updateUserLocked", map);
			    }
			});
		}
		
		return result;
	}
	
	/**
	 * @描述:查询密码已过期用户名
	 * @作者：WXJ
	 * @日期：2012-6-5
	 * @param loginname
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> findPasswordExpired(int passwordExpiredDays, String company){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("passwordExpiredDays", passwordExpiredDays);
    	map.put("company", company);
		return sqlDao.findList("CommonSQL.findPasswordExpired", map);
	}
	
	/**
	 * @描述:查询达到解锁时间的用户名
	 * @作者：WXJ
	 * @日期：2012-6-7
	 * @param loginname
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> findPasswordExpiredLockedUser(int unlockDays, String company){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("unlockDays", unlockDays);
    	map.put("company", company);
		return sqlDao.findList("CommonSQL.findPasswordExpiredLockedUser", map);
	}
	
	/**
	 * @描述:更新密码已过期用户状态
	 * @作者：WXJ
	 * @日期：2012-6-5
	 * @param loginnameList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int updatePasswordExpired(final List<String> loginnameList){
		Integer result = new Integer(0);
		if(loginnameList != null && !loginnameList.isEmpty()){
			result = (Integer)sqlDao.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				int count = 0;			
			    public Object doInSqlMapClient(SqlMapExecutor executor) throws
			        SQLException {			    	
			        executor.startBatch();
			        Map<String, Object> param = new HashMap<String, Object>();
			        param.put("status", SecurityConstant.ACCOUNT_INIT);
			        for (String loginname : loginnameList) {
			        	param.put("loginname", loginname);
			    	    executor.update("CommonSQL.updateUserStatus", param);
			    	    count++;
			        }
			        executor.executeBatch();
			        return count;
			    }
			});
		}
		
		return result;	
	}
	
	/**
	 * @描述:更新用户状态为正常
	 * @作者：WXJ
	 * @日期：2012-6-7
	 * @param loginnameList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int updateUserUnlock(final List<String> loginnameList){
		Integer result = new Integer(0);
		if(loginnameList != null && !loginnameList.isEmpty()){
			result = (Integer)sqlDao.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				int count = 0;			
			    public Object doInSqlMapClient(SqlMapExecutor executor) throws
			        SQLException {			    	
			        executor.startBatch();
			        Map<String, Object> param = new HashMap<String, Object>();
			        for (String loginname : loginnameList) {
			        	param.put("loginname", loginname);
			    	    executor.update("CommonSQL.updateUserUnLock", param);
			    	    count++;
			        }
			        executor.executeBatch();
			        return count;
			    }
			});
		}
		
		return result;	
	}
	
	/**
	 * 
	* @Title: getUserExp 
	* @Description: 用户扩展信息
	* @author WXJ
	* @date 2013-5-14 下午05:04:08 
	* @param @param userid
	* @param @return    
	* @return NjhwUsersExp 
	* @throws
	 */
	public NjhwUsersExp getUserExp(Long userid) {
		List<NjhwUsersExp> uepList = this.dao.findByProperty(NjhwUsersExp.class, "userid", userid);
		if (uepList!=null && uepList.size()>0){			
			return uepList.get(0);
		}
		return null;
	}
	
	/**
	 * 
	* @Title: getUserOrg 
	* @Description: 获取用户机构信息
	* @author WXJ
	* @date 2013-5-19 下午03:21:44 
	* @param @param orgId
	* @param @return    
	* @return Org 
	* @throws
	 */
	public Org getUserOrg(Long orgId) {
		Org org = (Org)this.dao.findById(Org.class, orgId);		
		return org;
	}
	
	/**
	 * 
	* @Title: getScard 
	* @Description: 市民卡信息
	* @author WXJ
	* @date 2013-5-15 上午10:06:36 
	* @param @param userid
	* @param @return    
	* @return NjhwTscard 
	* @throws
	 */
	public NjhwTscard getScard(Long userid) {
		List<NjhwTscard> cardList = this.dao.findByProperty(NjhwTscard.class, "userId", userid);
		if (cardList!=null && cardList.size()>0){
			return cardList.get(0);
		}
		return null;
	}
	
	/**
	 * 
	* @Title: getAuthAllMenuCollection 
	* @Description: 获取当前登录人全部授权的功能菜单
	* @author WXJ
	* @date 2013-6-2 下午03:15:57 
	* @param @param userid
	* @param @return    
	* @return List<Objtank> 
	* @throws
	 */
	public List<Objtank> getAuthAllMenuCollection(Long userid) {
		List<Objtank> objtanks = null;	
		Map map = new HashMap();
		map.put("userId", userid);
		objtanks = this.findListBySql("PortSQL.allMenuAuth", map);		
		
		return objtanks;
	}

	/**
	 * 
	* @Title: getOrgId 
	* @Description: 获取当前登录人的所属单位
	* @author HJ
	* @date 2013-8-28
	* @param @param orgId
	* @param @return    
	* @return List<Objtank> 
	* @throws
	 */
	public String getOrgId(Long orgId) {
		String rtn = "";
		Map map = new HashMap();
		map.put("orgId", orgId);
		List<Map> l = this.findListBySql("PortSQL.getOrgId", map);
		if (l != null && l.size() > 0) {
			rtn = l.get(0).get("ORG_ID") == null ? "" : l.get(0).get("ORG_ID").toString();
		}
		return rtn;
	}

	@SuppressWarnings("rawtypes")
	public List queryWebCountByCurrentDate(Map map) {
		return this.findListBySql("PortSQL.queryWebCount", map);
	}

	public int updateWebCount(Map<String, Object> map) {
		return this.updateBySql("PortSQL.updateWebCount", map);
	}

	public int insertWebCount(Map<String, Object> map) {
		return this.insertBySql("PortSQL.insertWebCount", map);
	}
	
	
	
}
