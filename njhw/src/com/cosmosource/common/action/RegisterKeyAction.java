/**
* <p>文件名: RegisterKeyAction.java</p>
* <p>描述：注册码生成Action</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2011-1-26 下午01:25:51 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.action;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.common.entity.TAcRegistkey;
import com.cosmosource.common.service.RegisterkeyManager;

/**
* @类描述: 注册码生成Action
* @作者： WXJ
*/
public class RegisterKeyAction extends BaseAction<TAcRegistkey> {

	private static final long serialVersionUID = 8497952646128136433L;
	private RegisterkeyManager registerkeyManager;
	//-- 页面属性 --//
	private TAcRegistkey entity = new TAcRegistkey();
	private Page<TAcRegistkey> page = new Page<TAcRegistkey>(Constants.PAGESIZE);//默认每页20条记录
	private String _chk[];//选中记录的ID数组

	
	//-- ModelDriven 与 Preparable函数 --//
	public TAcRegistkey getModel() {
		return entity;
	}
	/**
	 * 在查看和保存之前执行此方法，初始化实体对象
	 * @return 
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {
		if (entity.getRegid() != null) {
			entity = (TAcRegistkey)registerkeyManager.findById(TAcRegistkey.class, entity.getRegid());
		} else {
			entity = new TAcRegistkey();
		}
	}
	/**
	 * 查询注册码列表
	 * @return 
	 * @throws Exception
	 */
	public String list() throws Exception {
		page = registerkeyManager.queryRegKeys(page, entity);
		return SUCCESS;
	}
	/**
	 * 显示录入页面
	 * @return 
	 * @throws Exception
	 */
	public String input() throws Exception {
		String year = String.valueOf(DateUtil.getYear()+1);
		String month = String.valueOf(DateUtil.getMonth());
		if(month.length()==1){
			month = "0"+month;
		}
		entity.setUptomon(year+month);
		return INPUT;
	}
	/**
	 * 保存信息
	 * @return 
	 * @throws Exception
	 */
	public String save() throws Exception {
		try {
			
			if (registerkeyManager.isExist(entity)||registerkeyManager.isExistValid(entity)){
				setIsSuc("exist");
			}else{
				entity.setOperuser((String)getSessionAttribute(Constants.LOGIN_NAME));
				registerkeyManager.save(entity);
				setIsSuc("true");
			}
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return 
	 * @throws Exception
	 */
	public String delete() throws Exception {
		try {
			registerkeyManager.delete(_chk);
			addActionMessage("删除注册码成功");
		} catch (Exception e) {
			addActionMessage("删除注册码失败");
		}
		return RELOAD;
	}
	
	//-- 页面属性访问函数 --//
	/**
	 * list页面显示分页列表.
	 */
	public Page<TAcRegistkey> getPage() {
		return page;
	}
	public void setRegisterkeyManager(RegisterkeyManager registerkeyManager) {
		this.registerkeyManager = registerkeyManager;
	}
	
	public String[] get_chk() {
		return _chk;
	}

	public void set_chk(String[] chk) {
		_chk = chk;
	}
}
