package com.cosmosource.base.interceptor;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.cosmosource.base.service.IProxyManager;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.base.service.DBManager;
import com.cosmosource.base.service.SpringContextHolder;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TCommonLogOperation;
import com.cosmosource.common.security.SpringSecurityUtils;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
* @类描述: 日志拦截器
* @作者： WXJ
*/
//@SuppressWarnings({"unchecked","rawtypes"})
public class LoggerInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1358600090729208361L;
	private static Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);
	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		ai.invoke();
//		IProxyManager mgr = (IProxyManager) Struts2Util.getBean("proxyManager");
		String url = "";
		String namespace = ai.getProxy().getNamespace();
		if (StringUtils.isNotBlank(namespace) && !namespace.equals("/")) {
			url = url + namespace;
		}
		String actionName = ai.getProxy().getActionName();
		if (StringUtils.isNotBlank(actionName)) {
			url = url + "/" + actionName + ".act";
		}
		url = url.substring(1);
		String loginName = SpringSecurityUtils.getCurrentUserName();
		String ip = SpringSecurityUtils.getCurrentUserIp();
		
		String menuName = "";
		List<Objtank> objtanks = (List<Objtank>)Struts2Util.getSessionAttribute(Constants.ROLES_MENUS);
		for (Objtank obj : objtanks) {
			  if (obj.getLink()!=null && url.equals(obj.getLink().trim())) {
				  menuName = obj.getName();
				  break;
			  }
		}
		if ("".equals(menuName)) menuName = "非法访问";
		
		logger.debug("功能>>>菜单名称：[{}]  操作人：[{}] 操作时间：[{}] 操作IP：[{}] Session ID：[{}]", new Object[] { (String)Struts2Util.getSessionAttribute(url), 
				loginName, DateUtil.getDateTime(),ip,Struts2Util.getSession().getId()});
        if("Y".equals(Constants.DBMAP.get("COMMON_WRITEOPLOG_YN"))){
			DBManager mgr = SpringContextHolder.getBean("dbMgr");
			
			TCommonLogOperation logOp = new TCommonLogOperation();
			
			logOp.setOpTime(DateUtil.getSysDate());
			logOp.setActionId(url);
			logOp.setActionName(menuName);
			logOp.setLoginname(loginName);
			logOp.setSessionId(Struts2Util.getSession().getId());
			logOp.setOpType("01");
			logOp.setOpIp(ip);
			mgr.save(logOp);
        }
		
		
		return Action.SUCCESS;
	}
}