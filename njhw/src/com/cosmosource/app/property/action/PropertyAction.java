package com.cosmosource.app.property.action;

import java.util.ArrayList;
import java.util.List;

import com.cosmosource.app.entity.GrConference;
import com.cosmosource.app.entity.GrLostFound;
import com.cosmosource.app.entity.GrRepair;
import com.cosmosource.app.property.service.ConferenceManager;
import com.cosmosource.app.property.service.LostFoundManager;
import com.cosmosource.app.property.service.RepairManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.common.entity.TCommonMsgBoard;
import com.cosmosource.common.service.MsgBoardManager;

@SuppressWarnings("unchecked")
public class PropertyAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<GrRepair> repairList = new ArrayList<GrRepair>();
	private RepairManager repairManager;
	private List<GrConference> conferenceList = new ArrayList<GrConference>();
	private ConferenceManager conferenceManager;
	private List<GrLostFound> lostFoundsList = new ArrayList<GrLostFound>();
	private LostFoundManager lostFoundManager;
	private List<TCommonMsgBoard> commonMsgBoard = new ArrayList<TCommonMsgBoard>();	
	private MsgBoardManager msgBoardManager; 
	private String notClaimCountNumber = "0";

	public String showNotClaimCountNumber() {
		notClaimCountNumber = lostFoundManager.showNotClaimCountNumber();
		return null;
	}

	public Object getModel() {
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
	}

	/**
	 * 
	 * @title: toPropertyIndex
	 * @description: 跳转至物业管理页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-16 19:59:07
	 * @throws
	 */
	public String toPropertyIndex() {
		   String nowDate=DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss");
	       
	        
	        conferenceManager.updatePassConferenceState(nowDate);
		//repair
		List<GrRepair> repairTempList = repairManager.initGrRepairList();
		for (int i = 0; i < repairTempList.size(); i++) {
			if (i >= 7) {
				break;
			}
			repairList.add(repairTempList.get(i));
		}
		
		//conference
		List<GrConference> conferenceTempList=conferenceManager.queryConferencesForIndex(null,null,null,null,null,null,null);
		for (int i = 0; i < conferenceTempList.size(); i++) {
			if (i >= 7) {
				break;
			}
			conferenceList.add(conferenceTempList.get(i));
		}
		
		//lostFound
		//List<GrLostFound> lostFoundsTempList=lostFoundManager.queryLostFoundsForIndex(null, null, null, null, "0");
		List<GrLostFound> lostFoundsTempList=lostFoundManager.queryLostFoundsForIndex(null, null, null, null, null);
		for (int i = 0; i < lostFoundsTempList.size(); i++) {
			if (i >= 7) {
				break;
			}
			lostFoundsList.add(lostFoundsTempList.get(i));
		}
		notClaimCountNumber = lostFoundManager.showNotClaimCountNumber();
		
		//bulletin
		List<TCommonMsgBoard> commonMsgBoardsTempList = msgBoardManager.queryAllMsg();
		for (int i = 0; i < commonMsgBoardsTempList.size(); i++) {
			if (i >= 7) {
				break;
			}
			commonMsgBoard.add(commonMsgBoardsTempList.get(i));
		}
		
		return SUCCESS;
	}
	


	public List<TCommonMsgBoard> getCommonMsgBoard() {
		return commonMsgBoard;
	}

	public void setCommonMsgBoard(List<TCommonMsgBoard> commonMsgBoard) {
		this.commonMsgBoard = commonMsgBoard;
	}

	public MsgBoardManager getMsgBoardManager() {
		return msgBoardManager;
	}

	public void setMsgBoardManager(MsgBoardManager msgBoardManager) {
		this.msgBoardManager = msgBoardManager;
	}

	public String onlyShowConferenceUnstart(){
		List<GrConference> temp_conferenceList=conferenceManager.queryConferences(null,null,null,null,null,"0",null);
		if(temp_conferenceList.size()>6){
			for(int i=0;i<6;i++){
				conferenceList.add(temp_conferenceList.get(i));
			}
		}else {
			conferenceList=temp_conferenceList;
		}
		return SUCCESS;
	}

	public List<GrRepair> getRepairList() {
		return repairList;
	}

	public void setRepairList(List<GrRepair> repairList) {
		this.repairList = repairList;
	}

	public RepairManager getRepairManager() {
		return repairManager;
	}

	public void setRepairManager(RepairManager repairManager) {
		this.repairManager = repairManager;
	}

	public List<GrLostFound> getLostFoundsList() {
		return lostFoundsList;
	}

	public void setLostFoundsList(List<GrLostFound> lostFoundsList) {
		this.lostFoundsList = lostFoundsList;
	}

	public LostFoundManager getLostFoundManager() {
		return lostFoundManager;
	}

	public void setLostFoundManager(LostFoundManager lostFoundManager) {
		this.lostFoundManager = lostFoundManager;
	}

	public List<GrConference> getConferenceList() {
		return conferenceList;
	}

	public void setConferenceList(List<GrConference> conferenceList) {
		this.conferenceList = conferenceList;
	}

	public ConferenceManager getConferenceManager() {
		return conferenceManager;
	}

	public void setConferenceManager(ConferenceManager conferenceManager) {
		this.conferenceManager = conferenceManager;
	}
	
	public String getNotClaimCountNumber() {
		return notClaimCountNumber;
	}

	public void setNotClaimCountNumber(String notClaimCountNumber) {
		this.notClaimCountNumber = notClaimCountNumber;
	}
}
