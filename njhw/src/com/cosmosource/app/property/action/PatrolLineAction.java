package com.cosmosource.app.property.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.entity.GrPatrolLine;
import com.cosmosource.app.property.service.PatrolLineManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;

@SuppressWarnings("unchecked")
public class PatrolLineAction extends BaseAction {

	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页10条记录
	private GrPatrolLine patrolLine; 
	private List<GrPatrolLine> patrolLineList = new ArrayList<GrPatrolLine>();
	private PatrolLineManager patrolLineManager;
	
	private Long patrolLineId;
	private String patrolLineName;
	private String patrolLineDesc;
	private String patrolNodes;
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object getModel() {
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
	}
	
	/**
	 * 
	 * @title: patrolIndex
	 * @description: 巡查首页
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String patrolIndex() {
		return SUCCESS;
	}

	/**
	 * 
	 * @title: initPatrolLineList
	 * @description: 初始化获取巡查路线信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String initPatrolLineList() {
		patrolLineList = patrolLineManager.initGrPatrolLineInfo();
		return SUCCESS;
	}

	/**
	 * 
	 * @title: getPatrolLineInfoList
	 * @description: 分页得到巡查路线信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String getPatrolLineInfoList() {
		page = patrolLineManager.getGrPatrolLineList(page, null);
		return SUCCESS;
	}
	
	/**
	 * 
	 * @title: queryPatrolLineInfoList
	 * @description: 根据搜索条件查询巡查路线信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String queryPatrolLineInfoList() {
		HashMap parMap = new HashMap();
        String patrolLineName = getRequest().getParameter("patrolLineName");
		if (patrolLineName != null && !"".equals(patrolLineName)) {
			parMap.put("patrolLineName", patrolLineName);
		}
		page = patrolLineManager.getGrPatrolLineList(page, parMap);
		return SUCCESS;
	}
	

	/**
	 * 
	 * @title: addPatrolLine
	 * @description: 路线制定
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:12
	 * @throws
	 */
	public String addPatrolLine() {
		try {
			String patrolLineName = getRequest().getParameter("patrolLineName");
			String patrolLineDesc = getRequest().getParameter("patrolLineDesc");
			String patrolNodes = getRequest().getParameter("patrolNodes");
			patrolLineManager.addGrPatrolLineInfo(patrolLineName, patrolLineDesc, patrolNodes);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @title: editPatrolLine
	 * @description: 路线编辑
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:12
	 * @throws
	 */
	public String editPatrolLine() {
		try {
			Long patrolLineId = Long.parseLong(getRequest().getParameter("patrolLineId"));
			String patrolLineName = getRequest().getParameter("patrolLineName");
			String patrolLineDesc = getRequest().getParameter("patrolLineDesc");
			String patrolNodes = getRequest().getParameter("patrolNodes");
			patrolLineManager.editGrPatrolLineInfo(patrolLineId, patrolLineName, patrolLineDesc, patrolNodes);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}


	/**
	 * 
	 * @title: deletePatrolLine
	 * @description: 删除路线
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:48
	 * @throws
	 */
	public String deletePatrolLine() {
		try {
			Long patrolLineId = Long.parseLong(getRequest().getParameter("patrolLineId"));
			patrolLineManager.deleteGrPatrolLineInfo(patrolLineId);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toAddPatrolLine
	 * @description: 跳转至制定路线页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 16:56:47
	 * @throws
	 */
	public String toAddPatrolLine() {
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toEditPatrolLine
	 * @description: 跳转至编辑路线页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 16:57:21
	 * @throws
	 */
	public String toEditPatrolLine() {
		try {
			this.patrolLineId = Long.parseLong(getRequest().getParameter(
					"patrolLineId"));
			patrolLine = patrolLineManager.loadGrPatrolLineInfoByPatrolLineId(this.patrolLineId);
			this.patrolLineName = patrolLine.getPatrolLineName();
			this.patrolLineDesc = patrolLine.getPatrolLineDesc();
			this.patrolNodes = patrolLine.getPatrolNodes();
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toViewPatrolLine
	 * @description: 跳转至查看路线页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 17:06:35
	 * @throws
	 */
	public String toViewPatrolLine() {
		try {
			this.patrolLineId = Long.parseLong(getRequest().getParameter(
					"patrolLineId"));
			patrolLine = patrolLineManager.loadGrPatrolLineInfoByPatrolLineId(this.patrolLineId);
			this.patrolLineName = patrolLine.getPatrolLineName();
			this.patrolLineDesc = patrolLine.getPatrolLineDesc();
			this.patrolNodes = patrolLine.getPatrolNodes();
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public GrPatrolLine getPatrolLine() {
		return patrolLine;
	}

	public void setPatrolLine(GrPatrolLine patrolLine) {
		this.patrolLine = patrolLine;
	}

	public List<GrPatrolLine> getPatrolLineList() {
		return patrolLineList;
	}

	public void setPatrolLineList(List<GrPatrolLine> patrolLineList) {
		this.patrolLineList = patrolLineList;
	}

	public PatrolLineManager getPatrolLineManager() {
		return patrolLineManager;
	}

	public void setPatrolLineManager(PatrolLineManager patrolLineManager) {
		this.patrolLineManager = patrolLineManager;
	}

	public String getPatrolLineName() {
		return patrolLineName;
	}

	public void setPatrolLineName(String patrolLineName) {
		this.patrolLineName = patrolLineName;
	}

	public Long getPatrolLineId() {
		return patrolLineId;
	}

	public void setPatrolLineId(Long patrolLineId) {
		this.patrolLineId = patrolLineId;
	}

	public String getPatrolLineDesc() {
		return patrolLineDesc;
	}

	public void setPatrolLineDesc(String patrolLineDesc) {
		this.patrolLineDesc = patrolLineDesc;
	}

	public String getPatrolNodes() {
		return patrolNodes;
	}

	public void setPatrolNodes(String patrolNodes) {
		this.patrolNodes = patrolNodes;
	}
}
