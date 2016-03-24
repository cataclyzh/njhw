package com.cosmosource.app.quartz.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.app.personnel.service.PersonRegOutManager;
import com.cosmosource.app.port.serviceimpl.PersonCardQueryToAppService;
import com.cosmosource.base.service.BaseManager;

public class AppQuartService extends BaseManager{
	private static final Log log = LogFactory.getLog(AppQuartService.class);
	
	private PersonCardQueryToAppService personCardQueryToAppService;
	
	private PersonRegOutManager personROManager;

	public void setPersonCardQueryToAppService(
			PersonCardQueryToAppService personCardQueryToAppService) {
		this.personCardQueryToAppService = personCardQueryToAppService;
	}
	
	public void setPersonROManager(PersonRegOutManager personROManager) {
		this.personROManager = personROManager;
	}

	/*
	 * 判断市民卡状态是否正常
	 */
	private boolean isRight(String lose, String card){
		//LoseStatus 0:正常 1:黑名单
		//PuicStatus 1:正常 0:未开卡
		//CardStatus 0:正常
		if(lose.equals("0") && card.equals("0")){
			return true;
		}else{
			return false;
		}
	}
	
	/*
	 * quartz调用,轮询修改市民卡状态
	 */
	public void changeTsCardSystemLostStatus(){
		List<Map> list = null;
		try {
			long t = System.currentTimeMillis();
			list = sqlDao.findList("AppQuartz.getNjhwTscard", new HashMap());
			log.debug("NjhwTscard: " + list.size() + " cost time: " 
					+ (System.currentTimeMillis() - t)/1000.0 + "s");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		if(list == null){
			return;
		}

		for(Map map : list){
			try {
				String extLoseStatus = map.get("LOST").toString().trim();
				String extCardStatus = map.get("CARDSTATUS").toString().trim();
				
				boolean extIsRightStatus = isRight(extLoseStatus,extCardStatus);
				
				Object cardIdObj = map.get("CARDID");
				if(cardIdObj != null){
					String cardId = cardIdObj.toString();
					Map card = personCardQueryToAppService.queryPersonCardForLost(cardId);
					if(card == null){
						continue;
					}
					
					String loseStatus = card.get("LoseStatus").toString().trim();
					String cardStatus = card.get("CardStatus").toString().trim();
					
					boolean isRightStatus = isRight(loseStatus, cardStatus);
					
					if(extIsRightStatus==true && isRightStatus==false){
						log.debug(cardId + " right->error");
						personROManager.saveModifyCardIsLosted(cardId, "confirmLosted");
					}else if(extIsRightStatus==false && isRightStatus==true){
						log.debug(cardId + " error->right");
						personROManager.saveModifyCardIsLosted(cardId, "cancelLosted");
					}else{
					}
				}
			} catch (Exception e) {
				log.info(e.getMessage());
			}
		}
	}
}
