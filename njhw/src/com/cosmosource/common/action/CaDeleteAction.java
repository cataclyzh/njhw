package com.cosmosource.common.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.common.entity.TAcCaapply;
import com.cosmosource.common.model.CaModel;
import com.cosmosource.common.service.CAMgrManager;


/**
* @类描述: CA信息删除记录
* @作者： yc
*/
public class CaDeleteAction extends BaseAction<TAcCaapply> {
	private static final long serialVersionUID = 7693759893350855099L;
	private CAMgrManager caMgrManager;//CA管理类注入
	//-- 页面属性 --//
	private TAcCaapply entity = new TAcCaapply();//CA信息实体类
	private Page<TAcCaapply> page = new Page<TAcCaapply>(Constants.PAGESIZE);//默认每页20条记录
	private CaModel caModel = new CaModel(); //Ca查询条件信息类
	private long[] _chk; //复选框信息
	//购方/销方标志
	private String flog;
	
	/**
	 * @prepareModel()
	 * @初始化实体
	 * @作者：yc
	 * @日期：2011-11-23
	 * @return 
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {

	}
	/**
	 * @描述:初始化查询页面
	 * @作者：yc
	 * @日期：2011-11-23
	 * @return 
	 * @throws Exception
	 */
	public String init() throws Exception {
		//设置申请日期
		caModel.setApplyDateStart(DateUtil.getStrMonthFirstDay());
		caModel.setApplyDateEnd(DateUtil.getDateTime("yyyy-MM-dd"));
		return INIT;
	}
	
	
	/**
	 * @描述:查询待删除CA信息列表
	 * @作者：yc
	 * @日期：2011-11-23
	 * @return List
	 * @throws Exception
	 */
	public String query() throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		//当为销方时
		if(flog!=null&&flog.trim().equals("1")){
			//输入申请人
			String loginname = (String)getSession().getAttribute(Constants.LOGIN_NAME);
			entity.setApplyuser(loginname);
			//当销方 不输入任何申请人后 返回一个空的list集合
			if(entity.getApplyuser()==null||entity.getApplyuser().trim().equals("")){
				return LIST;
			}
			entity.setDelstatus("0");//正常状态 未删除
			entity.setAuditstatus("0");//未审核
			entity.setIssubmit("0");//未提交
		//购方	
		}else{
			entity.setDelstatus("0");//正常状态 未删除
			entity.setAuditstatus("0");//未审核
		}
		map.put("dateType", "1");//设置日期查询方式为申请日期
		page = caMgrManager.findCaList(page, entity,caModel,map);		
		return LIST;
	}
	
	/**
	 * @描述:完成CA信息删除
	 * @作者：yc
	 * @日期：2011-11-23
	 * @return String
	 */
	public String delete() {
		
		String user = (String)getSession().getAttribute(Constants.LOGIN_NAME);
		String message = null;
		//判断页面的IDs是否已经传入
		if(_chk != null && _chk.length>0){
			try{
				String changeCaNos = caMgrManager.deleteCaByIds(user,_chk);
				if(!StringUtils.isEmpty(changeCaNos)){
					message = "CA申请代码为 ["+changeCaNos+"]的CA状态变更，无法删除，请确认！";
				}else{
					message = "CA信息已删除成功";
				}
				
			}catch(Exception e){
				message = "CA信息删除失败";
			}
			this.addActionMessage(message);
		}
		return SUCCESS;
	}
	//-- 页面属性访问函数 --//
	/**
	 * list页面显示分页列表.
	 */
	public Page<TAcCaapply> getPage() {
		return page;
	}
	public CAMgrManager getCaMgrManager() {
		return caMgrManager;
	}
	public void setCaMgrManager(CAMgrManager caMgrManager) {
		this.caMgrManager = caMgrManager;
	}
	public void setCaModel(CaModel caModel) {
		this.caModel = caModel;
	}
	public CaModel getCaModel() {
		return caModel;
	}
	public long[] get_chk() {
		return _chk;
	}
	public void set_chk(long[] _chk) {
		this._chk = _chk;
	}
	public void setPage(Page<TAcCaapply> page) {
		this.page = page;
	}
	//-- ModelDriven 与 Preparable函数 --//
	public TAcCaapply getModel() {
		return entity;
	}
	public TAcCaapply getEntity() {
		return entity;
	}
	public void setEntity(TAcCaapply entity) {
		this.entity = entity;
	}
	public String getFlog() {
		return flog;
	}
	public void setFlog(String flog) {
		this.flog = flog;
	}
}
