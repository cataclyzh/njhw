package com.cosmosource.app.property.job;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.cosmosource.app.entity.GrPatrolException;
import com.cosmosource.app.entity.GrPatrolRecord;
import com.cosmosource.app.property.service.PatrolRecordManager;

public class PatrolExceptionHistoryJob {
	
	private PatrolRecordManager patrolRecordManager;
	
	public void execute() throws ParseException {
		List<GrPatrolException> grPatrolExceptionList = patrolRecordManager.getGrPatrolException();
		for (GrPatrolException grPatrolException:grPatrolExceptionList){
    		Long scheduleId = grPatrolException.getScheduleId();
    		Long orgId = grPatrolException.getOrgId();
    		String orgName = grPatrolException.getOrgName();
    		Long userId = grPatrolException.getUserId();
    		String userName = grPatrolException.getUserName();
    		Long patrolLineId = grPatrolException.getPatrolLineId();
    		String patrolLineName = grPatrolException.getPatrolLineName();
    		String patrolLineDesc = grPatrolException.getPatrolLineDesc();
    		Date scheduleStartDate = grPatrolException.getScheduleStartDate();
    		Date scheduleEndDate =  grPatrolException.getScheduleEndDate();
    		String exceptionDesc=grPatrolException.getExceptionDesc();
    		String isNormal=grPatrolException.getIsNormal();
    		patrolRecordManager.addGrPatrolExceptionHistory(scheduleId, orgId, orgName, userId, userName, patrolLineId, patrolLineName, patrolLineDesc, scheduleStartDate, scheduleEndDate,exceptionDesc, isNormal);
    	}
	}

	public PatrolRecordManager getPatrolRecordManager() {
		return patrolRecordManager;
	}

	public void setPatrolRecordManager(PatrolRecordManager patrolRecordManager) {
		this.patrolRecordManager = patrolRecordManager;
	}
}
