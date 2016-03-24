package com.cosmosource.app.personnel.action;

import java.util.Map;

import com.cosmosource.app.entity.TcTempcardRfid;
import com.cosmosource.app.personnel.service.TemporaryCardManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.ConvertUtils;

@SuppressWarnings("serial")
public class TemporaryCardAction extends BaseAction<TcTempcardRfid> {

	private TcTempcardRfid tcTempcardRfid = new TcTempcardRfid();
	private Page<TcTempcardRfid> page = new Page<TcTempcardRfid>(Constants.PAGESIZE); //默认是20页
	private TemporaryCardManager temporaryCardManager;
	private String cardId;
	private String _chk[];//选中记录的ID数组
	@Override
	protected void prepareModel() throws Exception {
	}
	@Override
	public TcTempcardRfid getModel() {
		return tcTempcardRfid;
	}
	
	/**
	 * 
	* @title: temporaryCardAdd 
	* @description: 修改临时卡信息
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:12:58     
	* @throws
	 */
	public String temporaryCardInput(){
		cardId = tcTempcardRfid.getCardId();
		if(cardId !=null){
			tcTempcardRfid = (TcTempcardRfid)temporaryCardManager.findById(TcTempcardRfid.class, cardId);
		}else{
			tcTempcardRfid = new TcTempcardRfid();
		}
		getRequest().setAttribute("rfid", tcTempcardRfid.getRfid());
		return INPUT;
	}
	
	/**
	 * 
	* @title: temporaryCardAdd 
	* @description: 增加临时卡信息
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:12:58     
	* @throws
	 */
	public String temporaryCardAdd(){
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: initTemporaryCard 
	* @description: 初始化录入页面
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:12:39     
	* @throws
	 */
	public String initTemporaryCard(){
		return INIT;
	}
	
	/**
	 * 
	* @title: saveTemporaryCard 
	* @description: 保存或修改临时卡信息
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:12:58     
	* @throws
	 */
	public String saveTemporaryCard(){
		try {
			temporaryCardManager.saveUpdateTeCard(tcTempcardRfid);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @title: saveTemporaryCard 
	 * @description: 保存或修改临时卡信息
	 * @author sqs
	 * @return
	 * @date 2013-3-19 下午09:12:58     
	 * @throws
	 */
	public String inputTemporaryCard(){
		try {
			temporaryCardManager.saveUpdateTeCard(tcTempcardRfid);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: queryTcTempcard 
	* @description: 查询按钮使用
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:19:19     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String queryTcTempcard() throws Exception {
		try {
			Map localMap = ConvertUtils.pojoToMap(tcTempcardRfid);
			page = temporaryCardManager.queryTcTempcard(page, localMap);
			return SUCCESS;
		} catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 
	* @title: deleteTemporaryCard 
	* @description: 批量删除选中的临时卡
	* @author sqs
	* @return
	* @throws Exception
	* @date 2013-3-19 下午06:19:21     
	* @throws
	 */
	public String deleteTemporaryCard() throws Exception {
		try {
			temporaryCardManager.deleteTemporaryCard(_chk);
			addActionMessage("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除失败");
		}
		return SUCCESS;
	}
	
	public String[] get_chk() {
		return _chk;
	}
	public void set_chk(String[] chk) {
		_chk = chk;
	}
	public TcTempcardRfid getTcTempcardRfid() {
		return tcTempcardRfid;
	}
	public void setTcTempcardRfid(TcTempcardRfid tcTempcardRfid) {
		this.tcTempcardRfid = tcTempcardRfid;
	}
	public Page<TcTempcardRfid> getPage() {
		return page;
	}
	public void setPage(Page<TcTempcardRfid> page) {
		this.page = page;
	}
	public TemporaryCardManager getTemporaryCardManager() {
		return temporaryCardManager;
	}
	public void setTemporaryCardManager(TemporaryCardManager temporaryCardManager) {
		this.temporaryCardManager = temporaryCardManager;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
}
