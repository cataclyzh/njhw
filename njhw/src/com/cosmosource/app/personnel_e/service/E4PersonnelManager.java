/**
* <p>文件名: OrgMgrManager.java</p>
* <p>描述：机构管理Manager</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2013-4-26 下午01:25:51 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.app.personnel_e.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Hibernate;

import com.cosmosource.app.entity.AttendanceApprovers;
import com.cosmosource.app.entity.BmMonitor;
import com.cosmosource.app.entity.BuildingAttendancers;
import com.cosmosource.app.entity.LeaderLevel;
import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.NjhwUsersPlatenum;
import com.cosmosource.app.entity.ObjPermMap;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.OrgAttendanceAdmin;
import com.cosmosource.app.entity.TcIpTel;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.ldap.service.LDAPService;
import com.cosmosource.app.ldap.service.LDAPServiceSrc;
import com.cosmosource.app.port.serviceimpl.DoorControlToAppService;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.NumberUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;

public class E4PersonnelManager extends BaseManager{
		
	public List<Map> getE4List(long uid){
		Map param = new HashMap();
		param.put("USERID", uid);
		return sqlDao.findList("E4PersonnelSQL.selectE4PersonnelByUserId", param);
	}
	
	public boolean isE4Personnel(long uid){
		List result = getE4List(uid);
		if(result == null || result.size() == 0){
			return false;
		}else{
			return true;
		}
	}
}
