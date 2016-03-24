package com.cosmosource.app.attendance.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.set.SynchronizedSet;

import com.cosmosource.app.attendance.util.AttendanceUtil;
import com.cosmosource.app.entity.AttendanceApprovers;
import com.cosmosource.app.entity.LeaderLevel;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.OrgAttendanceAdmin;
//import com.cosmosource.app.entity.OrgAttendanceAdmin;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.utils.DataUtils;
import com.cosmosource.app.utils.DateUtils;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.DateUtil;

public class NewCityAttendanceManager extends BaseManager {
	
	private ExecutorService pool = Executors.newCachedThreadPool();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List checkLeader(long uid) {

		// 根据登陆用户ID来查找，是否领导，部门还是单位领导
		List<Map<String, Object>> list = null;
		try {

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("user_id", uid);
			list = sqlDao.findList("AttendanceSQL.checkLeader", paramMap);

		} catch (Exception e) {

			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}

	/**
	 * 
	 * @param userid
	 * @return 考勤审批人员的信息
	 */
	public List<Users> queryApproversByUId(long userid) {
		Object o = dao.findById(AttendanceApprovers.class, userid);
		if (null != o) {
			return (List<Users>) dao
					.findByHQL("select u from Users u where u.userid in ("
							+ ((AttendanceApprovers) o).getApprovers() + ")");
		} else {
			return new ArrayList<Users>();
		}

	}
	
	/**
	 * 判断用户是否为大厦考勤员
	 * @param userid
	 * @return
	 */
	public boolean isBuildingAtt(long userid){
		List list = dao
				.findByHQL(
						"select aa from BuildingAttendancers aa where ',' || aa.userid || ',' like ?",
						"%,"+String.valueOf(userid)+",%");
		if (null != list && list.size() > 0) {	
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * 
	 * @param userid
	 * @return 是否为考勤管理员
	 */
	@SuppressWarnings("unchecked")
	public boolean isOrgAttendanceAdmin(long userid) {
		List<OrgAttendanceAdmin> o = dao.findByProperty(OrgAttendanceAdmin.class, "userid", userid);
		if (!o.isEmpty()) {
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * 是否为单位领导
	 * @param userid
	 * @return
	 */
	public boolean isOrgLeader(long userid){
		Object o = dao.findById(LeaderLevel.class, userid);
		if(null ==  o || 1 != ((LeaderLevel)o).getStage()){
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * 是否为临时人员
	 * @param userid
	 * @return
	 */
	public boolean isTempUser(long userid){
		List<NjhwUsersExp> usersexp=  dao.findByProperty(NjhwUsersExp.class, "userid", userid);
		if(usersexp.isEmpty() || !"04".equals(usersexp.get(0).getUepType())){
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * 单位领导或临时人员
	 * @param userid
	 * @return
	 */
	public boolean isLeaderOrTempUser(long userid){
		if(isTempUser(userid) || isOrgLeader(userid)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 
	 * @param userid
	 * @return 是否有考勤审批人员. true：有 false：没有
	 */
	public boolean isContainsApprovers(long userid) {
		if (queryApproversByUId(userid).size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param userid
	 *            考勤审批人员id
	 * @return 是否有被审批人员. true：有 false：没有
	 */
	public boolean isContainsUsers(long userid) {
		List list = dao
				.findByHQL(
						"select aa from AttendanceApprovers aa where ',' || aa.approvers || ',' like ?",
						"%,"+String.valueOf(userid)+",%");
		if (null != list && list.size() > 0) {	
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param user_id
	 * @return 领导级别 1 ： 单位领导 2：部门领导 3：普通用户
	 */
	@SuppressWarnings("rawtypes")
	public int getUserType(Long user_id) {

		List leaderList = this.checkLeader(user_id);
		Map leaderMap = null;
		String leaderLevel = null;
		int i = 3;
		if (!leaderList.isEmpty() && leaderList.size() > 0) {
			try {
				leaderMap = (Map) leaderList.get(0);
				leaderLevel = ((BigDecimal) leaderMap.get("STAGE"))
						.toPlainString();
				i = Integer.valueOf(leaderLevel);
			} catch (Exception e) {
				logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd")
						+ " : " + e.getMessage());
			}

		}
		return i;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<HashMap> queryPersonalAttendances(final Page<HashMap> page,
			long uid) {

		Page<HashMap> tmpPage = null;

		try {

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("user_id", uid);
			tmpPage = sqlDao.findPage(page,
					"AttendanceSQL.selectPersonalAttendances", paramMap);

		} catch (Exception e) {

			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}

		return tmpPage;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<HashMap> queryUnitAttendances(final Page<HashMap> page, long uid) {

		Page<HashMap> tmpPage = null;

		try {

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("user_id", uid);
			tmpPage = sqlDao.findPage(page,
					"AttendanceSQL.selectUnitAttendances", paramMap);

		} catch (Exception e) {

			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}

		return tmpPage;
	}

	/**
	 * 部门领导查看部门员工考勤信息
	 * 
	 * @param page
	 * @param parMap
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<HashMap> queryDeptAttendances(final Page<HashMap> page,
			Map paramMap,String type) {

		List empList = null;

		// 根据 分页信息获取当前要查询的人员信息 数目=page.size()

		try {
			
			String org_id = null;
			
			List orgList = sqlDao.findList("AttendanceSQL.getOrgIdByUserId", paramMap);
			
			if (!orgList.isEmpty() && orgList.size()>0) {
				org_id = ((Map) orgList.get(0)).get("ORG_ID").toString();
				paramMap.put("org_id", org_id);
			}
			// 获取部门下所有员工信息
			empList = sqlDao.findList("AttendanceSQL.selectEmpListByOrdId",
					paramMap);
			
			// 添加临时人员考勤信息
			try {
				long id = Integer.parseInt(paramMap.get("user_id").toString());
				if(id == 2131){
					String oldOrgId = paramMap.get("org_id").toString();
					
					//运维团队
					paramMap.put("org_id", "2580");
					List tmp1 = sqlDao.findList("AttendanceSQL.selectEmpListByOrdId_chjl",
							paramMap);
					empList.addAll(tmp1);
					
					//临时部门
//					paramMap.put("org_id", "1179");
//					List tmp2 = sqlDao.findList("AttendanceSQL.selectEmpListByOrdId",
//							paramMap);
//					empList.addAll(tmp2);
					
					paramMap.put("org_id", oldOrgId);
				}
			} catch (Exception e) {
				logger.error("添加临时人员考勤信息Error", e);
			}
			

			logger.info(empList.get(0).toString());

			// 所有员工数目就等于给页面返回的记录总数
			page.setTotalCount(empList.size());

			// 根据分页参数解析empList 获取返回页面显示的员工信息
			// 第一页 则取 0 <= 区间 <= page.size() 记录

			List result = new LinkedList<HashMap>();
			int start = (page.getPageNo() - 1) * page.getPageSize();
			int end = page.getPageNo() * page.getPageSize();
			
//			try{
//				long id = Integer.parseInt(paramMap.get("user_id").toString());
//				if(id == 2131){
//					paramMap.remove("org_id");
//					paramMap.remove("user_name");
//				}
//			}catch(Exception e){
//				logger.error("添加临时人员考勤信息Error", e);
//			}

			for (int j = 0; j < empList.size(); j++) {
				if("export".equals(type)){


					// 查询出当前人的考勤信息
					// empList 内容 ： U.USERID, U.DISPLAY_NAME, U.ORG_ID, O.NAME
					Map userMap = (Map) empList.get(j);
//					Map selectMap = new HashMap<String, Object>();
//					selectMap.put("user_id", userMap.get("USERID"));
					paramMap.put("user_id", userMap.get("USERID"));

					List empAttendanceList = sqlDao.findList(
							"AttendanceSQL.selectEmpAttendanceList_chjl", paramMap);
					
					//如果没有查询到当前人记录
					if (empAttendanceList.isEmpty() || empAttendanceList.size() == 0) {
						Map empMap = new HashMap();
						empMap.put("userId", userMap.get("USERID")); // 用户ID
						empMap.put("userName", userMap.get("DISPLAY_NAME")); // 姓名
						empMap.put("orgName", userMap.get("NAME")); // 部门
						empMap.put("normalAttendance", "--"); // 出勤总数
						empMap.put("execpAttendance", "--"); // 缺勤总数
						empMap.put("lateCount", "--"); // 迟到总数
						empMap.put("leaveEarlyCount", "--"); // 早退总数
						empMap.put("sickLeave", "--"); // 病假
						empMap.put("affairLeave", "--"); // 事假
						empMap.put("businessLeave", "--"); // 公出
						empMap.put("otherStatus", "--"); // 其它总数
						result.add(empMap);
					} else {
						// 统计当前人考勤信息
						Map analyseAttendanceMap = AttendanceUtil
								.analyseAttendanceList(empAttendanceList);
						// 统计之后的结果，保存到page对象结果集
						result.add(analyseAttendanceMap);
					}
				
				}else{
					// 取此期间记录
					if (j >= start && j < end) {

						// 查询出当前人的考勤信息
						// empList 内容 ： U.USERID, U.DISPLAY_NAME, U.ORG_ID, O.NAME
						Map userMap = (Map) empList.get(j);
//						Map selectMap = new HashMap<String, Object>();
//						selectMap.put("user_id", userMap.get("USERID"));
						paramMap.put("user_id", userMap.get("USERID"));

						List empAttendanceList = sqlDao.findList(
								"AttendanceSQL.selectEmpAttendanceList_chjl", paramMap);
						
						//如果没有查询到当前人记录
						if (empAttendanceList.isEmpty() || empAttendanceList.size() == 0) {
							Map empMap = new HashMap();
							empMap.put("userId", userMap.get("USERID")); // 用户ID
							empMap.put("userName", userMap.get("DISPLAY_NAME")); // 姓名
							empMap.put("orgName", userMap.get("NAME")); // 部门
							empMap.put("normalAttendance", "--"); // 出勤总数
							empMap.put("execpAttendance", "--"); // 缺勤总数
							empMap.put("lateCount", "--"); // 迟到总数
							empMap.put("leaveEarlyCount", "--"); // 早退总数
							empMap.put("sickLeave", "--"); // 病假
							empMap.put("affairLeave", "--"); // 事假
							empMap.put("businessLeave", "--"); // 公出
							empMap.put("otherStatus", "--"); // 其它总数
							result.add(empMap);
						} else {
							// 统计当前人考勤信息
							Map analyseAttendanceMap = AttendanceUtil
									.analyseAttendanceList(empAttendanceList);
							// 统计之后的结果，保存到page对象结果集
							result.add(analyseAttendanceMap);
						}
					}
				}

			}
			page.setResult(result);
		
		
		
		
		
		/*
		
		List list, pageList = null;

		try {
			// 获取考勤信息list
			list = sqlDao.findList("AttendanceSQL.selectDeptAttendances",
					paramMap);
			// 解析 list
			// 最终返回 size() 等于 pageSize 的 list
			pageList = AttendanceUtil.resolveAttendanceList(list);

			// int totalCount = 0;
			// totalCount = this.getDeptAttendancesTotalCountByUserId(paramMap
			// .get("user_id"));
			page.setTotalCount(pageList.size());

			if (pageList.size() < page.getPageSize()) {
				page.setPageNo(1);
				page.setResult(pageList);
			} else {
				List result = new LinkedList<HashMap>();
				int start = (page.getPageNo() - 1) * page.getPageSize();
				int end = page.getPageNo() * page.getPageSize();

				// 获取当前页面返回的 list
				for (int j = 0; j < pageList.size(); j++) {
					// 取此期间记录
					if (j >= start && j < end) {
						result.add(pageList.get(j));
					}
				}
				page.setResult(result);
			}

			logger.info("page no : " + page.getPageNo());
			logger.info("page size : " + page.getPageSize());
			logger.info("page total count : " + page.getTotalCount());

			// 解析 list 返回page

			// tmpPage = sqlDao.findPage(page,
			// "AttendanceSQL.selectDeptAttendances", parMap);
			 * */

		} catch (Exception e) {

			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}

		return page;
	}

	// 查询单位所有考勤信息
	@SuppressWarnings("rawtypes")
	public List queryUnitAttendancesList(Map paramMap) {
		List list = null;
		try {
			list = sqlDao.findList("AttendanceSQL.selectUnitAttendancesList",
					paramMap);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}

	/**
	 * 
	 * @param top_org_id
	 *            单位org_id 信息中心：70
	 * @return
	 */
	public List queryUnitExcepAttendances(String top_org_id) {

		List list = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("top_org_id", top_org_id);

		try {
			list = sqlDao.findList("AttendanceSQL.queryUnitExcepAttendances",
					paramMap);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List getDeptList(String top_org_id) {
		List list = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("top_org_id", top_org_id);
		try {
			list = sqlDao.findList("AttendanceSQL.getDeptList", paramMap);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<HashMap> queryPersonalHistoryAttendances(Page<HashMap> page,
			long uid, String date) {
		Page<HashMap> tmpPage = null;
		try {

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("user_id", uid);
			// date 格式为 yyyy-MM-dd
			paramMap.put("atten_date", date);
			tmpPage = sqlDao.findPage(page,
					"AttendanceSQL.getPersonalHistoryAttendances", paramMap);

		} catch (Exception e) {

			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}

		return tmpPage;
	}

	/**
	 * 
	 * 针对部门领导 根据当前登录用户ID查找部门人员信息
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryDeptEmpByDeptUserId(Map paramMap) {
		List list = null;
		try {
			list = sqlDao.findList("AttendanceSQL.queryDeptEmpByDeptUserId",
					paramMap);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}

	/**
	 * 
	 * @param list
	 *            t_011_attendance_per_day 表查询list结果集
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getAttendanceSortList(List list, Page<HashMap> page) {
		List<HashMap> empList = new LinkedList<HashMap>();
		return null;
	}

	/**
	 * 获取用户最后一次打卡记录
	 * 
	 * @param uid
	 * @return
	 * @param uid
	 *            用户ID
	 * @param year
	 *            查询年份
	 * @param month
	 *            查询月份
	 * @param date
	 *            查询日期
	 * @return 考勤结果集 当前部门人员考勤信息统计总数
	 */
	public int getDeptAttendancesTotalCountByUserId(Object uid) {

		int count = 0;
		List list = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", uid);
		try {
			list = sqlDao.findList(
					"AttendanceSQL.getDeptAttendancesTotalCountByUserId",
					paramMap);
			count = list.size();
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return count;

	}

	/**
	 * 查找我的申请 操作的表 ： t_011_attendance_per_day
	 * 
	 * @param page
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<HashMap> queryApplyInfoByUserId(Page<HashMap> page, Map paramMap) {
		Page<HashMap> tmpPage = null;

		try {
			tmpPage = sqlDao.findPage(page,
					"AttendanceSQL.queryApplyInfoByUserId", paramMap);

		} catch (Exception e) {

			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}

		return tmpPage;
	}

	/**
	 * 查看我的审批
	 * 
	 * @param uid
	 *            用户 ID
	 * @return
	 * @param uid
	 *            用户ID
	 * @param year
	 *            查询年份
	 * @param month
	 *            查询月份
	 * @param date
	 *            查询日期
	 * @return 考勤结果集
	 * @param uid
	 *            用户ID
	 * @param year
	 *            查询年份
	 * @param month
	 *            查询月份
	 * @param date
	 *            查询日期
	 * @return 考勤结果集
	 * @param uid
	 *            用户ID
	 * @param year
	 *            查询年份
	 * @param month
	 *            查询月份
	 * @param date
	 *            查询日期
	 * @return 考勤结果集
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<HashMap> getAcceptInfoByUserId(Page<HashMap> page,
			Map<String, Object> paramMap) {

		Page<HashMap> tmpPage = null;
		try {
			tmpPage = sqlDao.findPage(page,
					"AttendanceSQL.selectAcceptInfoByUserId", paramMap);

		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}

		return tmpPage;
	}

	private int getAcceptListTotalCountByUserId(Object uid) {

		int count = 0;
		List list = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", uid);
		try {
			list = sqlDao.findList(
					"AttendanceSQL.getAcceptListTotalCountByUserId", paramMap);
			count = list.size();
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return count;

	}

	/**
	 * 将拒绝考勤申请信息写入数据库
	 * 
	 * @param cMap 写入数据库的内容
	 */
	public void updateRejectApprove(Map cMap) {
		List parList = new ArrayList();
		parList.add(cMap);
		sqlDao.batchUpdate("AttendanceSQL.rejectApprove", parList);
		
		final String id = (String) cMap.get("id");
		pool.execute(new Runnable(){
			@Override
			public void run() {
				syncAttendancePerDayById(id, 1, 3, false);
			}
		});
	}

	/**
	 * 将同意考勤申请信息写入数据库
	 * 
	 * @param cMap 写入数据库的内容
	 */
	public void updatePassApprove(Map cMap) {
		List parList = new ArrayList();
		parList.add(cMap);
		sqlDao.batchUpdate("AttendanceSQL.passApprove", parList);
		
		final String id = (String) cMap.get("id");
		pool.execute(new Runnable(){
			@Override
			public void run() {
				syncAttendancePerDayById(id, 2, 2, true);
			}
		});		
		
	}
	
	/**
	 * 在申请审批被批示同意后,更新考勤记录表中的记录
	 * 将申请为假期的考勤记录更新到相应的状态
	 * 
	 * @param id 
	 * 		申请审批表[T_018_APPROVERS_INFO]的ID
	 * 		对应一条用户的申请
	 * 
	 * @param deal_stat 异常情况处理标志
	 * 
	 * 状态字更新
	 * 
	 * 申请通过:
	 * deal_stat 2 accept_flag 2
	 * 
	 * 申请拒绝
	 * deal_stat 1 accept_flag 3
	 * 
	 * 提交申请
	 * deal_stat 3 accept_flag 1
	 * 
	 */
	public void syncAttendancePerDayById(String id, int deal_stat, int accept_flag, boolean isSyncType){
		Map approve = getApproversInfoById(id);
		syncAttendancePerDay(approve, deal_stat, accept_flag, isSyncType);
	}
	
	/**
	 * @description 在申请审批被批示同意后,更新考勤记录表中的记录
	 * 				将申请为假期的考勤记录更新到相应的状态
	 * 
	 * @param approve 
	 * 		申请审批表[T_018_APPROVERS_INFO]的信息
	 * 
	 * 状态字更新
	 * 
	 * 申请通过:
	 * deal_stat 2 accept_flag 2
	 * 
	 * 申请拒绝
	 * deal_stat 1 accept_flag 3
	 * 
	 * 提交申请
	 * deal_stat 3 accept_flag 1
	 * 
	 */
	public void syncAttendancePerDay(Map approve, int deal_stat, int accept_flag, boolean syncType){
		String startDay=DataUtils.getMapValue(approve, "STARTTIMESTR");
		String endDay=DataUtils.getMapValue(approve, "ENDTIMESTR");
		String startType=DataUtils.getMapValue(approve, "START_TYPE");
		String endType=DataUtils.getMapValue(approve, "END_TYPE");
		String approverId = DataUtils.getMapValue(approve, "APPROVER_ID");
		String type = DataUtils.getMapValue(approve, "TYPE");
		String userDesc = DataUtils.getMapValue(approve, "USER_DESC");
		String userId = DataUtils.getMapValue(approve, "USER_ID");
		String approveId = DataUtils.getMapValue(approve, "ID");
		
		logger.info("startDay: " + startDay);
		logger.info("endDay: " + endDay);
		logger.info("startType: " + startType);
		logger.info("endType: " + endType);
		logger.info("approverId: " + approverId);
		logger.info("type: " + type);
		logger.info("userDesc: " + userDesc);
		logger.info("userId: " + userId);
		
		if(startDay == null){
			return;
		}
		
		int st = Integer.parseInt(startType);
		int et = Integer.parseInt(endType);
		
		//需要更新的考情记录集合,通过对日期的循环处理添加记录,最后做batch写入到数据库
		List<Map> approveList = new ArrayList<Map>();
		
		List<Date> list = DateUtil.getDiffDaysList(startDay,endDay,"yyyy-MM-dd",false);
		for(int i=0; i<list.size(); i++){
			Date d = list.get(i);
			String dateStr = DateUtils.getDateString(d);
			logger.info("date: " + dateStr);
			String yearStr = DateUtils.getYearStr(dateStr);
			String monthStr = DateUtils.getMonthStr(dateStr);
			String dayStr = DateUtils.getDayStr(dateStr);
			logger.info("year: " + yearStr);
			logger.info("monthStr: " + monthStr);
			logger.info("dayStr: " + dayStr);
			
			/**
			 * 对考情表做上午,下午,全天处理的标志量
			 * 0: 不作处理
			 * 1: 代表处理上午
			 * 2: 代表处理下午
			 * 3: 代表处理全天
			 */
			int flag = 0;
			
			/**
			 * 对申请开始日期和结束日期为同一天的记录做处理
			 * 
			 * startType, endType
			 * 0 0 上午 到 上午
			 * 0 1 上午 到 下午
			 * 1 1 下午 到 下午
			 */
			if(list.size() == 1){
				if(st == 0 && et == 0){
					flag = 1;
				}else if(st == 1 && et == 1){
					flag = 2;
				}else if((st == 0 && et == 1) || (st == 1 && et == 0)){
					flag = 3;
				}else{
					logger.error("申请考勤记录startType,endType设置有误id["+startType+"|"+endType+"]");
				}
			}else if(i==0){//对申请日期中第一天做处理
				if(st == 0){
					flag = 3;
				}else if(st == 1){
					flag = 2;
				}else{
					logger.error("申请考勤记录startType,endType设置有误id["+startType+"|"+endType+"]");
				}
			}else if( i == (list.size()-1)){//对申请日期中最后一天做处理
				if(et == 0){
					flag = 1;
				}else if(et == 1){
					flag = 3;
				}else{
					logger.error("申请考勤记录startType,endType设置有误id["+startType+"|"+endType+"]");
				}
			}else{//对其他的申请日期做处理
				flag = 3;
			}
			
			Map param = new HashMap();
			switch(flag){
			case 1://上午
				//更新的字段
				if(syncType){
					param.put("ATTEN_MORNING_TYPE", type);
				}
				param.put("MORNING_DEAL_STAT", deal_stat);
				param.put("ACCEPT_MORNING_FLAG", accept_flag);
				param.put("ACCEPT_MORNING_CONTENT", userDesc);
				param.put("ACCEPT_MORNING_USER_ID", approverId);
				//更新数据的查询条件
				param.put("USER_ID", userId);
				param.put("ATTEND_YEAR", yearStr);
				param.put("ATTEND_MONTH", monthStr);
				param.put("ATTEND_DATE", dayStr);
				approveList.add(param);
				break;
			case 2://下午
				//更新的字段
				if(syncType){
					param.put("ATTEN_AFTERNOON_TYPE", type);
				}
				param.put("AFTERNOON_DEAL_STAT", deal_stat);
				param.put("ACCEPT_AFTERNOON_FLAG", accept_flag);
				param.put("ACCEPT_AFTERNOON_CONTENT", userDesc);
				param.put("ACCEPT_AFTERNOON_USER_ID", approverId);
				//更新数据的查询条件
				param.put("USER_ID", userId);
				param.put("ATTEND_YEAR", yearStr);
				param.put("ATTEND_MONTH", monthStr);
				param.put("ATTEND_DATE", dayStr);
				approveList.add(param);
				break;
			case 3://全天
				//更新的字段
				if(syncType){
					param.put("ATTEN_MORNING_TYPE", type);
					param.put("ATTEN_AFTERNOON_TYPE", type);
				}
				param.put("MORNING_DEAL_STAT", deal_stat);
				param.put("ACCEPT_MORNING_FLAG", accept_flag);
				param.put("AFTERNOON_DEAL_STAT", deal_stat);
				param.put("ACCEPT_AFTERNOON_FLAG", accept_flag);
				param.put("ACCEPT_MORNING_CONTENT", userDesc);
				param.put("ACCEPT_AFTERNOON_CONTENT", userDesc);
				param.put("ACCEPT_MORNING_USER_ID", approverId);
				param.put("ACCEPT_AFTERNOON_USER_ID", approverId);
				//更新数据的查询条件
				param.put("USER_ID", userId);
				param.put("ATTEND_YEAR", yearStr);
				param.put("ATTEND_MONTH", monthStr);
				param.put("ATTEND_DATE", dayStr);
				approveList.add(param);
				break;
			default:
				logger.error("申请记录信息有误["+approveId+"]");
				break;	
			}
			sqlDao.batchUpdate("AttendanceSQL.updateApprovePerDay", approveList);
		}
	}
	
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addApproversInfo(Map param ,int deal_stat, int accept_flag) {
		List parList = new ArrayList();
		parList.add(param);
		sqlDao.batchUpdate("AttendanceSQL.addApproversInfo", parList);
		
		Map approve = new HashMap(); 
		approve.put("STARTTIMESTR", DataUtils.getMapValue(param, "START_DATE"));
		approve.put("ENDTIMESTR", DataUtils.getMapValue(param, "END_DATE"));
		approve.put("START_TYPE", DataUtils.getMapValue(param, "START_TYPE"));
		approve.put("END_TYPE", DataUtils.getMapValue(param, "END_TYPE"));
		approve.put("APPROVER_ID", DataUtils.getMapValue(param, "APPROVER_ID"));
		approve.put("TYPE", DataUtils.getMapValue(param, "TYPE"));
		approve.put("USER_DESC", DataUtils.getMapValue(param, "USER_DESC"));
		approve.put("USER_ID", DataUtils.getMapValue(param, "USER_ID"));
		approve.put("ID", "");
		syncAttendancePerDay(approve, deal_stat, accept_flag, true);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<HashMap> getApproversInfo(Page<HashMap> page, Map paramMap) {
		Page<HashMap> tmpPage = null;

		try {
			
			tmpPage = sqlDao.findPage(page, "AttendanceSQL.getApproversInfo",
					paramMap);
			

		} catch (Exception e) {

			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}

		return tmpPage;
	}

	public Map getApproversInfoById(String id) {
		Map result = null;
		try {
			Map param = new HashMap();
			param.put("id", id);
			List<Map> list = sqlDao.findList(
					"AttendanceSQL.getApproversInfoById", param);
			if (list.size() > 0) {
				result = list.get(0);
			}

		} catch (Exception e) {

			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}

		return result;
	}
	
	public Map getGongChuJia(String approveId){
		Map result = null;
		try {
			List<Map> list =sqlDao.getSqlMapClientTemplate().queryForList("AttendanceSQL.selectGongChuJia", Long.valueOf(approveId));
			if (list.size() > 0) {
				result = list.get(0);
			}
		} catch (Exception e) {
			logger.error("查询公出流程信息", e);
		}
		return result;
	}

	/**
	 * 获取用户第一次打卡记录
	 * 
	 * @param uid
	 *            用户ID
	 * @param year
	 *            查询年份
	 * @param month
	 *            查询月份
	 * @param date
	 *            查询日期
	 * @return 考勤结果集
	 */
	@SuppressWarnings("rawtypes")
	public List queryTodayFirstAttendanceListByUserId(long uid, String year,
			String month, String date) {
		List list = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", uid);
		paramMap.put("year", year);
		paramMap.put("month", month);
		paramMap.put("date", date);
		try {
			list = sqlDao.findList("AttendanceSQL.getUserFirstAttendanceList",
					paramMap);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}

	/**
	 * 根据人员ID 查找指定天数考勤信息
	 * 
	 * @param page
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<HashMap> queryAttendanceDetailByUserId(Page<HashMap> page,
			Map paramMap) {

		Page<HashMap> tmpPage = null;
		try {
			tmpPage = sqlDao.findPage(page,
					"AttendanceSQL.queryAttendanceDetailByUserId", paramMap);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}

		return tmpPage;

	}

	/**
	 * 事假处理
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryAffairLeaveListByUserId(Map paramMap) {
		List list = null;
		try {
			list = sqlDao.findList("AttendanceSQL.getUserAffairLeaveList",
					paramMap);

			if (!list.isEmpty() && list.size() > 0) {
				// 最后组装成 日期 ： 审批人 ： 事由 格式
				// 异常类型 1:病假 2:事假 3:公休 4:其它
				list = AttendanceUtil.resoleLeaveInfo(list, String.valueOf(2));
			}
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}

	/**
	 * 病假处理
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List querySickLeaveListByUserId(Map paramMap) {

		List list = null;
		try {
			list = sqlDao.findList("AttendanceSQL.getUserSickLeaveList",
					paramMap);
			if (!list.isEmpty() && list.size() > 0) {
				// 最后组装成 日期 ： 审批人 ： 事由 格式
				// 异常类型 1:病假 2:事假 3:公出 4:其它
				list = AttendanceUtil.resoleLeaveInfo(list, String.valueOf(1));
			}

		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}

	/**
	 * 公出假期处理
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryBusinessLeaveListByUserId(Map paramMap) {
		List list = null;
		try {
			list = sqlDao.findList("AttendanceSQL.getUserBusinessLeaveList",
					paramMap);
			if (!list.isEmpty() && list.size() > 0) {
				// 最后组装成 日期 ： 审批人 ： 事由 格式
				// 异常类型 1:病假 2:事假 3:公休 4:其它
				list = AttendanceUtil.resoleLeaveInfo(list, String.valueOf(3));
			}
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}

	/**
	 * 其它请假处理
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryOtherLeaveListByUserId(Map paramMap) {
		List list = null;
		try {
			list = sqlDao.findList("AttendanceSQL.getUserOtherLeaveList", paramMap);
			if (!list.isEmpty() && list.size() > 0) {
				 list = AttendanceUtil.resoleLeaveInfo(list,
				 String.valueOf(4));
			}
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}

	/**
	 * 根据领导级别来获取员工考勤
	 * 
	 * @param page
	 * @param paramsMap
	 * @param leaderShip
	 *            领导级别 1:单位领导 2：部门领导
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<HashMap> getUnitAttendanceList(Page<HashMap> page,
			Map paramsMap, int leaderShip, String type) {

		List empList = null;

		// 根据 分页信息获取当前要查询的人员信息 数目=page.size()

		try {
			// 获取单位下所有员工信息
			empList = sqlDao.findList("AttendanceSQL.selectEmpListByOrdId",
					paramsMap);

//			logger.info(empList.get(0).toString());

			// 所有员工数目就等于给页面返回的记录总数
			page.setTotalCount(empList.size());

			// 根据分页参数解析empList 获取返回页面显示的员工信息
			// 第一页 则取 0 <= 区间 <= page.size() 记录

			List result = new LinkedList<HashMap>();
			int start = (page.getPageNo() - 1) * page.getPageSize();
			int end = page.getPageNo() * page.getPageSize();

			for (int j = 0; j < empList.size(); j++) {
				if ("export".equals(type)) {
					// 查询出当前人的考勤信息
					// empList 内容 ： U.USERID, U.DISPLAY_NAME, U.ORG_ID, O.NAME
					Map userMap = (Map) empList.get(j);
					// Map selectMap = new HashMap<String, Object>();
					// selectMap.put("user_id", userMap.get("USERID"));
					paramsMap.put("user_id", userMap.get("USERID"));
					// logger.info("USERID : " + userMap.get("USERID"));

					List empAttendanceList = sqlDao.findList(
							"AttendanceSQL.selectEmpAttendanceList", paramsMap);

					// 如果没有查询到当前人记录
					if (empAttendanceList.isEmpty()
							|| empAttendanceList.size() == 0) {
						Map empMap = new HashMap();
						empMap.put("userId", userMap.get("USERID")); // 用户ID
						empMap.put("userName", userMap.get("DISPLAY_NAME")); // 姓名
						empMap.put("orgName", userMap.get("NAME")); // 部门
						empMap.put("normalAttendance", "--"); // 出勤总数
						empMap.put("execpAttendance", "--"); // 缺勤总数
						empMap.put("lateCount", "--"); // 迟到总数
						empMap.put("leaveEarlyCount", "--"); // 早退总数
						empMap.put("sickLeave", "--"); // 病假
						empMap.put("affairLeave", "--"); // 事假
						empMap.put("businessLeave", "--"); // 公出
						empMap.put("otherStatus", "--"); // 其它总数
						result.add(empMap);

					} else {

						// 统计当前人考勤信息
						Map analyseAttendanceMap = AttendanceUtil
								.analyseAttendanceList(empAttendanceList);

						// 统计之后的结果，保存到page对象结果集
						result.add(analyseAttendanceMap);
					}

				} else {
					// 取此期间记录
					if (j >= start && j < end) {

						// 查询出当前人的考勤信息
						// empList 内容 ： U.USERID, U.DISPLAY_NAME, U.ORG_ID,
						// O.NAME
						Map userMap = (Map) empList.get(j);
						// Map selectMap = new HashMap<String, Object>();
						// selectMap.put("user_id", userMap.get("USERID"));
						paramsMap.put("user_id", userMap.get("USERID"));
						// logger.info("USERID : " + userMap.get("USERID"));

						List empAttendanceList = sqlDao.findList(
								"AttendanceSQL.selectEmpAttendanceList",
								paramsMap);

						// 如果没有查询到当前人记录
						if (empAttendanceList.isEmpty()
								|| empAttendanceList.size() == 0) {
							Map empMap = new HashMap();
							empMap.put("userId", userMap.get("USERID")); // 用户ID
							empMap.put("userName", userMap.get("DISPLAY_NAME")); // 姓名
							empMap.put("orgName", userMap.get("NAME")); // 部门
							empMap.put("normalAttendance", "--"); // 出勤总数
							empMap.put("execpAttendance", "--"); // 缺勤总数
							empMap.put("lateCount", "--"); // 迟到总数
							empMap.put("leaveEarlyCount", "--"); // 早退总数
							empMap.put("sickLeave", "--"); // 病假
							empMap.put("affairLeave", "--"); // 事假
							empMap.put("businessLeave", "--"); // 公出
							empMap.put("otherStatus", "--"); // 其它总数
							result.add(empMap);

						} else {

							// 统计当前人考勤信息
							Map analyseAttendanceMap = AttendanceUtil
									.analyseAttendanceList(empAttendanceList);

							// 统计之后的结果，保存到page对象结果集
							result.add(analyseAttendanceMap);
						}

					}
				}
			}
			page.setResult(result);
		}
		catch (Exception e) {
			
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
			logger.error(e.getMessage(),e);
		}
		logger.info("page no : " + page.getPageNo());
		logger.info("page size : " + page.getPageSize());
		logger.info("page total count : " + page.getTotalCount());
		return page;

	}
	
	/**
	 * 统计用户缺勤信息
	 * @param paramMap
	 * @return
	 * 上午状态:0:正常 1:迟到 2:缺勤 3:早退
	 */
	@SuppressWarnings("rawtypes")
	public List queryAbsentListByUserId(Map paramMap) {
		List list = null;
		try {
			//缺勤统计
			list = sqlDao.findList("AttendanceSQL.getUserAbsentList", paramMap);
			if (!list.isEmpty() && list.size() > 0) {
				 list = AttendanceUtil.resoleAttendanceUnusualInfo(list,
				 String.valueOf(2));
			}
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}
	
	/**
	 * 统计用户迟到信息
	 * @param paramMap
	 * @return
	 * 上午状态:0:正常 1:迟到 2:缺勤 3:早退
	 */
	
	@SuppressWarnings("rawtypes")
	public List queryLateListByUserId(Map paramMap) {
		List list = null;
		try {
			//迟到统计
			list = sqlDao.findList("AttendanceSQL.getUserLateList", paramMap);
			if (!list.isEmpty() && list.size() > 0) {
				 list = AttendanceUtil.resoleAttendanceUnusualInfo(list,
				 String.valueOf(1));
			}
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}

	/**
	 * 统计用户早退信息
	 * @param paramMap
	 * @return
	 * 上午状态:0:正常 1:迟到 2:缺勤 3:早退
	 */
	@SuppressWarnings("rawtypes")
	public List queryLeaveEarlylListByUserId(Map paramMap) {
		List list = null;
		try {
			//早退统计
			list = sqlDao.findList("AttendanceSQL.getUserLeaveEarlyList", paramMap);
			if (!list.isEmpty() && list.size() > 0) {
				 list = AttendanceUtil.resoleAttendanceUnusualInfo(list,
				 String.valueOf(3));
			}
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}
	
	
	/**
	 * 针对大厦考勤管理员
	 * 获取大厦所有单位信息
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getUnitListForNewCityAdmin() {
		List list = null;
		Map tMap = new LinkedHashMap<String, Object>();
		try {
			//新城大厦底下所有单位信息
			list = sqlDao.findList("AttendanceSQL.getUnitListForNewCityAdmin", null);
			tMap.put("0","全部");
			if (!list.isEmpty() && list.size() > 0) {
				 for (Object object : list) {
					BigDecimal org_id = (BigDecimal) ((Map)object).get("ORG_ID");
					tMap.put(org_id.toPlainString(),((Map)object).get("ORG_NAME"));
				}
			}
			
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage(),e);
		}
		return tMap;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<HashMap> queryBuildingAttendances(Page<HashMap> page,
			Map paramsMap,String type) {

		List empList = null;
		
		try {
			
			// 获取大厦所有员工信息
			empList = sqlDao.findList("AttendanceSQL.selectBuildingAttendancesList",
					paramsMap);

			logger.info("查询到员工信息，共 ：" + empList.size());

			// 所有员工数目就等于给页面返回的记录总数
			page.setTotalCount(empList.size());

			// 根据分页参数解析empList 获取返回页面显示的员工信息
			// 第一页 则取 0 <= 区间 <= page.size() 记录

			List result = new LinkedList<HashMap>();
			int start = (page.getPageNo() - 1) * page.getPageSize();
			int end = page.getPageNo() * page.getPageSize();

			for (int j = 0; j < empList.size(); j++) {
				if("export".equals(type)){
					// 查询出当前人的考勤信息
					// empList 内容 ： U.USERID, U.DISPLAY_NAME, U.ORG_ID, O.NAME
					Map userMap = (Map) empList.get(j);
					paramsMap.put("user_id", userMap.get("USERID"));
					//不需要的字段删除
					paramsMap.remove("org_id");

					List empAttendanceList = sqlDao.findList(
							"AttendanceSQL.selectEmpAttendanceList", paramsMap);
					
					//如果没有查询到当前人记录
					if (empAttendanceList.isEmpty() || empAttendanceList.size() == 0) {
						Map empMap = new HashMap();
						empMap.put("userId", userMap.get("USERID")); // 用户ID
						empMap.put("unitName", userMap.get("ORG_NAME")); // 用户所在单位
						empMap.put("userName", userMap.get("DISPLAY_NAME")); // 姓名
						empMap.put("orgName", userMap.get("DEPARTMENT_NAME")); // 部门
						empMap.put("normalAttendance", "--"); // 出勤总数
						empMap.put("execpAttendance", "--"); // 缺勤总数
						empMap.put("lateCount", "--"); // 迟到总数
						empMap.put("leaveEarlyCount", "--"); // 早退总数
						empMap.put("sickLeave", "--"); // 病假
						empMap.put("affairLeave", "--"); // 事假
						empMap.put("businessLeave", "--"); // 公出
						empMap.put("otherStatus", "--"); // 其它总数
						result.add(empMap);
						
					} else {
						
						// 统计当前人考勤信息
						Map analyseAttendanceMap = AttendanceUtil
								.analyseAttendanceList(empAttendanceList);
						
						// 统计之后的结果，保存到page对象结果集
						result.add(analyseAttendanceMap);
					}
					
				
				}else{
				// 取此期间记录
				if (j >= start && j < end) {

					// 查询出当前人的考勤信息
					// empList 内容 ： U.USERID, U.DISPLAY_NAME, U.ORG_ID, O.NAME
					Map userMap = (Map) empList.get(j);
					paramsMap.put("user_id", userMap.get("USERID"));
					//不需要的字段删除
					paramsMap.remove("org_id");

					List empAttendanceList = sqlDao.findList(
							"AttendanceSQL.selectEmpAttendanceList", paramsMap);
					
					//如果没有查询到当前人记录
					if (empAttendanceList.isEmpty() || empAttendanceList.size() == 0) {
						Map empMap = new HashMap();
						empMap.put("userId", userMap.get("USERID")); // 用户ID
						empMap.put("unitName", userMap.get("ORG_NAME")); // 用户所在单位
						empMap.put("userName", userMap.get("DISPLAY_NAME")); // 姓名
						empMap.put("orgName", userMap.get("DEPARTMENT_NAME")); // 部门
						empMap.put("normalAttendance", "--"); // 出勤总数
						empMap.put("execpAttendance", "--"); // 缺勤总数
						empMap.put("lateCount", "--"); // 迟到总数
						empMap.put("leaveEarlyCount", "--"); // 早退总数
						empMap.put("sickLeave", "--"); // 病假
						empMap.put("affairLeave", "--"); // 事假
						empMap.put("businessLeave", "--"); // 公出
						empMap.put("otherStatus", "--"); // 其它总数
						result.add(empMap);
						
					} else {
						
						// 统计当前人考勤信息
						Map analyseAttendanceMap = AttendanceUtil
								.analyseAttendanceList(empAttendanceList);
						
						// 统计之后的结果，保存到page对象结果集
						result.add(analyseAttendanceMap);
					}
					
				}
				}
			}
			page.setResult(result);


		} catch (Exception e) {
			
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
			logger.error(e.getMessage(),e);
		}

		logger.info("page no : " + page.getPageNo());
		logger.info("page size : " + page.getPageSize());
		logger.info("page total count : " + page.getTotalCount());
		return page;
	}
	
	/*
	 * 查询请假审批 
	 * @params 
	 * 		userId 申请人ID
	 * 		time   请假日期
	 * @return 
	 * 		包含满足条件的数据集合
	 */
	public List<Map> getApprovesForTimeCheck(Map params){
		List<Map> list = null;
		try{
			list = sqlDao.findList("AttendanceSQL.getApprovesForTimeCheck", params);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return list;
	}
	
	/**
	 * 检查请假时间是否有冲突
	 * 
	 * @param userId 申请人ID 
	 * @param st 请假开始日期
	 * @param et 请假结束日期
	 * @param sf 开始日期上下午标志 
	 * @param ef 结束日期上下午标志
	 * 
	 * @return  string 错误信息
	 * 			null 表示没有冲突
	 * 
	 */
	public String checkTimeConflictForApprove(long userId, String sd, String ed, int sf, int ef){
		String isConflict = null;
		
		Map<String,String> approveDays = new HashMap<String,String>();
		getSplitDays(sd, ed, sf, ef, approveDays);
		
		Map<String,String> existDays = getApprovedDays(userId, sd, ed);
		
		Iterator<Entry<String, String>> aIt = approveDays.entrySet().iterator();
		while(aIt.hasNext()){
			Entry<String,String> aEntry = aIt.next();
			Iterator<Entry<String, String>> eIt = existDays.entrySet().iterator();
			String aDay = aEntry.getKey();
			while(eIt.hasNext()){
				Entry<String,String> eEntry = eIt.next();
				String eDay = eEntry.getKey();
				if(aDay.equals(eDay)){
					return eEntry.getValue();
				}
			}
		}
		return isConflict;
	}
	
	/**
	 * 检查请假日期是否和考勤记录有冲突
	 * 如果考勤中某一天的考勤记录是正常的,那么这一天将不能被申请请假
	 * 
	 * @param userId 申请人ID 
	 * @param st 请假开始日期
	 * @param et 请假结束日期
	 * @param sf 开始日期上下午标志 
	 * @param ef 结束日期上下午标志
	 * 
	 * @return  string 错误信息
	 * 			null 表示没有冲突
	 */
	public String checkAttendanceTimeConflict(long userId ,String sd, String ed, int sf, int ef){
		List<String> morningDays = new ArrayList<String>();
		List<String> afternoonDays = new ArrayList<String>();
		
		Map<String,String> approveDays = new HashMap<String,String>();
		getSplitDays(sd, ed, sf, ef, approveDays);
		Iterator<String> it = approveDays.keySet().iterator();
		while(it.hasNext()){
			String date = it.next();
			if(date != null){
				int flag = Integer.parseInt(date.substring(11));
				String day = date.substring(0, 10); 
				if(flag == 0){
					morningDays.add(day);
				}else if(flag == 1){
					afternoonDays.add(day);
				}else{
					logger.error("错误的考勤记录状态");
				}
			}
		}
		
		if(morningDays.size() > 0){
			Map params = new HashMap();
			params.put("morningDate", morningDays);
			params.put("userId", userId);
			String reStr = checkAttendanceTimeConflictResult(params, "上午无需请假"); 
			if(reStr != null){
				return reStr; 
			}
		}
		
		if(afternoonDays.size() > 0){
			Map params = new HashMap();
			params.put("afternoonDate", afternoonDays);
			params.put("userId", userId);
			String reStr = checkAttendanceTimeConflictResult(params, "下午无需请假");
			if(reStr != null){
				return reStr; 
			}
		}
		
		return null;
	}
	
	private String checkAttendanceTimeConflictResult(Map params, String errorInfo){
		List<Map> list = sqlDao.findList("AttendanceSQL.checkAttendanceTimeConflict", params);
		
		if(list.size() > 0){
			Map m = list.get(0);
			String dayStr = DataUtils.getMapValue(m, "ATTEND_YEAR")
					+"-"+DataUtils.getMapValue(m, "ATTEND_MONTH")
					+"-"+DataUtils.getMapValue(m, "ATTEND_DATE");
			return dayStr+errorInfo;
		}
		return null;
	}
	
	

	/**
	 * 
	 * 得到已经请假了的日期
	 * 
	 * @param userId 	用户ID
	 * @param startDay	开始日期
	 * @param endDay	结束日期
	 * @return			返回请假日期的集合
	 */
	public Map<String,String> getApprovedDays(long userId, String startDay, String endDay){
		Map param = new HashMap();
		param.put("USERID", userId);
		param.put("START_D", startDay);
		param.put("END_D", endDay);
		
		List<Map> approves = getApprovesForTimeCheck(param);
		logger.info("result: " + approves);
		
		Map<String,String> result = new HashMap<String,String>();
		for(Map m : approves){
			String id = DataUtils.getMapValue(m, "ID");
			String sd = DataUtils.getMapValue(m, "START_DATE");
			String ed = DataUtils.getMapValue(m, "END_DATE");
			String sf = DataUtils.getMapValue(m, "START_TYPE");
			String ef = DataUtils.getMapValue(m, "END_TYPE");
			logger.info("getSplitDays from t_018_approvers_info id["+id+"]");
			getSplitDays(sd, ed, Integer.parseInt(sf), Integer.parseInt(ef), result);
		}
		return result;
	}
	
	/**
	 * 
	 * 得到分开日期的集合
	 * 
	 * @param sd	开始日期
	 * @param ed	结束日期
	 * @param sf	开始日期标志
	 * @param ef	结束日期标志
	 * @param result	输出参数,包含日期集合
	 * 					结果集包含格式类似 2014-04-01-0 后两位-0表示上午请假,-1表示下午请假
	 * 
	 * 	sf ef
	 *  0  0  表示上午到上午
	 *  0  1  表示上午到下午
	 *  1  1  表示下午到下午
	 *  1  0  表示下午到上午
	 */
	private void getSplitDays(String sd, String ed, int sf, int ef, Map<String,String> result){
		List<Date> list = DateUtil.getDiffDaysList(sd, ed, "yyyy-MM-dd", false);
		for(int i=0; i< list.size(); i++){
			Date d = list.get(i);
			String dateStr = DateUtils.getDateString(d);
			logger.info("date: " + dateStr);
			if(list.size() == 1){
				if(sf == 0 && ef == 0){
					result.put(dateStr+"-0", getConflictTimeError(sd,ed,"上午"));
				}else if((sf == 0 && ef == 1) || (sf == 1 && ef == 0)){
					result.put(dateStr+"-0", getConflictTimeError(sd,ed,"全天"));
					result.put(dateStr+"-1", getConflictTimeError(sd,ed,"全天"));
				}else if(sf == 1 && ef == 1){
					result.put(dateStr+"-1", getConflictTimeError(sd,ed,"下午"));
				}else{
					logger.error("日期标志设置错误["+sf+"|"+ef+"]");
				}
			}else if(i==0){
				if(sf == 0 && ef == 0){
					result.put(dateStr+"-0", getConflictTimeError(sd,ed,"上午到上午"));
					result.put(dateStr+"-1", getConflictTimeError(sd,ed,"上午到上午"));
				}else if(sf == 0 && ef == 1){
					result.put(dateStr+"-0", getConflictTimeError(sd,ed,"上午到下午"));
					result.put(dateStr+"-1", getConflictTimeError(sd,ed,"上午到下午"));
				}else if(sf == 1 && ef == 1){
					result.put(dateStr+"-1", getConflictTimeError(sd,ed,"下午到下午"));
				}else if(sf == 1 && ef == 0){
					result.put(dateStr+"-1", getConflictTimeError(sd,ed,"下午到上午"));
				}else{
					logger.error("日期标志设置错误["+sf+"|"+ef+"]");
				}
			}else if(i==(list.size()-1) ){
				if(sf == 0 && ef == 0){
					result.put(dateStr+"-0", getConflictTimeError(sd,ed,"上午到上午"));
				}else if(sf == 0 && ef == 1){
					result.put(dateStr+"-0", getConflictTimeError(sd,ed,"上午到下午"));
					result.put(dateStr+"-1", getConflictTimeError(sd,ed,"上午到下午"));
				}else if(sf == 1 && ef == 1){
					result.put(dateStr+"-0", getConflictTimeError(sd,ed,"下午到下午"));
					result.put(dateStr+"-1", getConflictTimeError(sd,ed,"下午到下午"));
				}else if(sf == 1 && ef == 0){
					result.put(dateStr+"-0", getConflictTimeError(sd,ed,"下午到上午"));
				}else{
					logger.error("日期标志设置错误["+sf+"|"+ef+"]");
				}
			}else{
				result.put(dateStr+"-0", getConflictTimeError(sd,ed,"下午到下午"));
				result.put(dateStr+"-1", getConflictTimeError(sd,ed,"下午到下午"));
			}
		}
	}
	
	private String getConflictTimeError(String sd, String ed, String txt){
		return "申请时间冲突[开始时间"+sd+"到结束时间"+ed+"|"+txt+"]";
	}

	
	/**
	 * 检查请假申请是否有时间冲突
	 * @param userId
	 * 			用户ID
	 * @param time
	 * 			请假时间
	 * @return
	 * 			true:	有冲突
	 * 			false:	没冲突
	 */
	public boolean isApproveTimeConflict(long userId, String time){
		boolean result = false;
		
		Map param = new HashMap();
		param.put("USERID", userId);
		param.put("TIME", time);
		List list = getApprovesForTimeCheck(param);
		if(list != null){
			if(list.size() > 0){
				result = true; 
			}else{
				result = false;
			}
		}else{
			result = false;
		}
		return result;
	}

	
	/**
	 * 获取考勤实时统计数据
	 * 按大厦、单位、部门统计
	 * @param leaderShip 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Integer> queryRealTimeAttendanceList(Map paramsMap, int leaderShip) {

		List<Integer> finalList = new LinkedList<Integer>();
		List<Map<String, Object>> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		switch (leaderShip) {
		case 1:
			//单位
//			paramsMap.put("top_org_id", org_id);
//			paramsMap.put("org_id", selectDepartment);
			//有按部门过滤
			if (null != paramsMap.get("org_id")) {
				paramsMap.remove("top_org_id");
				map.put("org_id", paramsMap.get("org_id"));
				map.put("scope", 2);
			}else {
				map.put("top_org_id", paramsMap.get("top_org_id"));
				map.put("scope", 1);
			}
			
			break;
		case 2:
			//部门
			map.put("org_id", paramsMap.get("org_id"));
			map.put("scope", 2);
			break;
		case 3:
			//大厦人员统计
//			paramsMap.put("org_id", organizationSelect);
//			paramsMap.put("department_id", selectDepartment);
			
			//有单位信息
			if (null != paramsMap.get("org_id")) {
				map.put("top_org_id", paramsMap.get("org_id"));
				map.put("scope", 1);
			}else if (null != paramsMap.get("department_id")) {
				map.put("org_id", paramsMap.get("department_id"));
				map.put("scope", 2);
			}else {
				map.put("scope", 3);
			}
			break;

		default:
			break;
		}
		
		try {
			//获取单位、部门、大厦所有人员数目，分临时人员跟内部人员
			//temporary worker, internal employee
			map.put("isInternalEmp", "1");
			list = sqlDao.findList("AttendancePieChartSQL.getEmpCount", map);
			int internalEmpCount = Integer.valueOf(list.get(0).get("COUNT").toString());
			finalList.add(internalEmpCount);
			
			//在大厦里的内部人员，最后刷卡是闸机，状态为进
			map.put("status", "in_building"); 
			list = sqlDao.findList("AttendancePieChartSQL.realTimeInBuildingEmpList", map);
			int internalEmpInBuilding = Integer.valueOf(((Map)list.get(0)).get("COUNT").toString());
			finalList.add(internalEmpInBuilding);
			
			//查找不在大厦之外内部人员数目，最后刷卡时闸机，状态为出
			map.put("status", "out_of_building");
			list = sqlDao.findList("AttendancePieChartSQL.realTimeInBuildingEmpList", map);
			int internalEmpOutOfBuilding = Integer.valueOf(((Map)list.get(0)).get("COUNT").toString());
			finalList.add(internalEmpOutOfBuilding);
			
			//未知内部人员数目
			finalList.add(internalEmpCount - internalEmpInBuilding - internalEmpOutOfBuilding);
			
			
			//大厦总临时人员数目
			map.remove("isInternalEmp");
			//查找临时人员数目
			list = sqlDao.findList("AttendancePieChartSQL.getEmpCount", map);
			int tempWorkerCount = Integer.valueOf(list.get(0).get("COUNT").toString());
			finalList.add(tempWorkerCount);
			
			//在大厦里的临时人员，最后刷卡为闸机，状态为进
			map.put("status", "in_building");  
			list = sqlDao.findList("AttendancePieChartSQL.realTimeInBuildingEmpList", map);
			int tempWorkerInBuilding = Integer.valueOf(((Map)list.get(0)).get("COUNT").toString());
			finalList.add(tempWorkerInBuilding);
			
			//查找不在大厦之外临时人员数目，最后刷卡时闸机，状态为出
			map.put("status", "out_of_building");
			list = sqlDao.findList("AttendancePieChartSQL.realTimeInBuildingEmpList", map);
			int tempWorkerOutOfBuilding = Integer.valueOf(((Map)list.get(0)).get("COUNT").toString());
			finalList.add(tempWorkerOutOfBuilding);
			
			//未知临时人员数目
			finalList.add(tempWorkerCount - tempWorkerInBuilding - tempWorkerOutOfBuilding);
			
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage(),e);
			e.printStackTrace();
		}
		return finalList;
	
	
	}

	
	/**
	 * 实时查询员工是否在大厦状态
	 * @param page
	 * @param paramMap
	 * @param scope 
	 * @param status 
	 * @return page
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<HashMap> getRealTimeEmpDetail(
			Page<HashMap> page, Map<String, Object> paramMap, String scope) {
		

	String dept_id = null;
	// 根据 分页信息获取当前要查询的人员信息 数目=page.size()
	try {
		
		if ("3".equals(scope)){
			//部门领导或部门考勤人员
			List orgList = sqlDao.findList("AttendanceSQL.getOrgIdByUserId",
					paramMap);
			
			if (!orgList.isEmpty() && orgList.size() > 0){
				
				Map map = (Map) orgList.get(0);
				dept_id = map.get("ORG_ID").toString();
				paramMap.put("org_id",dept_id);
				
			}
		}
		
		
		//@Deprecated
		// 获取应查询所有员工信息,ibatis 封装后查询效率慢
/*		page = sqlDao.findPage(page,"AttendancePieChartSQL.selectRealTimeEmpStatus",
				paramMap);*/
		
		
		List empList = sqlDao.findList("AttendancePieChartSQL.getRealTimegEmpList",
				paramMap);
		
		
		page.setTotalCount(empList.size());
		
		
		List result = new LinkedList<HashMap>();
		int start = (page.getPageNo() - 1) * page.getPageSize();
		int end = page.getPageNo() * page.getPageSize();
		
		for (int j = 0; j < empList.size(); j++) {
			// 取此期间记录
			if (j >= start && j < end) {
/*				Map userMap = (Map) empList.get(j);
				paramMap.put("user_id", userMap.get("USERID"));
				List recordTimeList = sqlDao.findList(
						"AttendancePieChartSQL.selectLastRecordTimeByUserId", paramMap);*/
				
				//如果没有查询到当前人记录
				Map empMap = new HashMap();
				empMap.put("userName", ((Map)empList.get(j)).get("USER_NAME")); 
				empMap.put("recordTime", ((Map)empList.get(j)).get("LAST_RECORD_TIME")); // 最后一次刷卡时间
				
				
				result.add(empMap);
			}
		}

	page.setResult(result);

		

//		logger.info(page.getResult().toString());

		/*		// 所有员工数目就等于给页面返回的记录总数
		page.setTotalCount(empList.size());

		// 根据分页参数解析empList 获取返回页面显示的员工信息
		// 第一页 则取 0 <= 区间 <= page.size() 记录

		List result = new LinkedList<HashMap>();
		int start = (page.getPageNo() - 1) * page.getPageSize();
		int end = page.getPageNo() * page.getPageSize();

		for (int j = 0; j < empList.size(); j++) {
				// 取此期间记录
				if (j >= start && j < end) {
					Map userMap = (Map) empList.get(j);
					paramMap.put("user_id", userMap.get("USERID"));
					List empStatus = sqlDao.findList(
							"AttendancePieChartSQL.selectRealTimeEmpStatus", paramMap);
					
					//如果没有查询到当前人记录
					Map empMap = new HashMap();
					if (empStatus.isEmpty() || empStatus.size() == 0) {
						empMap.put("userId", userMap.get("USERID")); 
						empMap.put("userName", userMap.get("USER_NAME").toString().trim()); 
						empMap.put("recordTime", "--"); // 最后一次刷卡时间
						empMap.put("status", "--"); // 进出状态
					} else {
						empMap.put("userId", ((Map)empStatus.get(0)).get("USER_ID")); // 用户ID
						empMap.put("userName", ((Map)empStatus.get(0)).get("USER_NAME").toString().trim()); // 姓名
						empMap.put("recordTime", ((Map)empStatus.get(0)).get("LAST_RECORD_TIME").toString().replaceAll("/", "-")); // 最后一次刷卡时间
						empMap.put("status", ((Map)empStatus.get(0)).get("STATUS")); // 进出状态
					}
					result.add(empMap);
				}
			}

		page.setResult(result);*/
	

	} catch (Exception e) {

		logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
				+ e.getMessage(),e);
	}

	return page;

	}

	
	/**
	 * 获取未读审批信息
	 * @param uid
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getUnReadApproveCountByUserId(long uid) {
		
		int count = 0;
		try {
			Map map = new HashMap();
			map.put("userId", uid);
			List<Map<String,Object>> countList = sqlDao.findList("AttendanceSQL.selectApproveCountByUserId", map);
			
			if (!countList.isEmpty() && countList.size() > 0){
				count = Integer.valueOf(countList.get(0).get("COUNT").toString());
			}
			
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage(),e);
		}
		return count;
	}
}
