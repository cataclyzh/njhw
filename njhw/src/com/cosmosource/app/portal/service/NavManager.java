package com.cosmosource.app.portal.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.cosmosource.app.entity.VmVisit;
import com.cosmosource.app.entity.mxw.SLeftmenu;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.ServiceException;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.common.entity.TAcMenu;
import com.cosmosource.common.entity.TAcOrg;
import com.cosmosource.common.entity.TAcRole;
import com.cosmosource.common.entity.TAcSubsys;
import com.cosmosource.common.entity.TAcUser;
import com.cosmosource.common.entity.TCommonMsgBox;
import com.cosmosource.common.security.Logininfo;
import com.cosmosource.common.security.SecurityConstant;
import com.cosmosource.common.security.SpringSecurityUtils;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
* @description: 读取权限系统:安全相关实体的管理类, 包括用户,角色,资源与授权类.
* @author herb
* @date Mar 22, 2013 11:07:06 AM
 */
public class NavManager extends BaseManager{

	private static Logger logger = LoggerFactory.getLogger(NavManager.class);
	
	@SuppressWarnings("unchecked")
	public List<TAcRole> getRoleList(TAcUser user){
		return dao.findByHQL("select b from TAcUserrole a,TAcRole b where a.roleid=b.roleid and a.userid = ? ", user.getUserid());
	}
	

	/**
	 * 
	* @title: getRootMenu 
	* @description: 查询mxw和mup得到子菜单
	* @author herb
	* @param subSysId
	* @param roleIds
	* @param orgId
	* @return
	* @date Mar 22, 2013 11:10:22 AM     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<SLeftmenu> getMenuByPid(String pid){
		StringBuffer sb = new StringBuffer("select menu from SLeftmenu menu");
		if (null == pid || pid.trim().length() < 1) {
			sb.append(" where parentId is null order by sort");
		} else {
			sb.append(" where parentId = ? order by sort");
		}
		return dao.findByHQL(sb.toString(), Long.valueOf(pid));
	}
	
	

	
}
