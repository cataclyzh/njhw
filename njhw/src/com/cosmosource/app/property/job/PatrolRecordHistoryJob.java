package com.cosmosource.app.property.job;

import java.util.Date;
import java.util.List;

import com.cosmosource.app.entity.GrPatrolRecord;
import com.cosmosource.app.property.service.PatrolRecordManager;

public class PatrolRecordHistoryJob {
	private PatrolRecordManager patrolRecordManager;
	
    public void execute(){
    	List<GrPatrolRecord> grPatrolRecordList = patrolRecordManager.getGrPatrolRecord();
    	for (GrPatrolRecord grPatrolRecord:grPatrolRecordList){
    		Long scheduleId = grPatrolRecord.getScheduleId();
    		Long orgId = grPatrolRecord.getOrgId();
    		String orgName = grPatrolRecord.getOrgName();
    		Long userId = grPatrolRecord.getUserId();
    		String userName = grPatrolRecord.getUserName();
    		Long patrolLineId = grPatrolRecord.getPatrolLineId();
    		String patrolLineName = grPatrolRecord.getPatrolLineName();
    		String patrolLineDesc = grPatrolRecord.getPatrolLineDesc();
    		String patrolNodes = grPatrolRecord.getPatrolNodes();
    		Date scheduleStartTime = grPatrolRecord.getScheduleStartTime();
    		Date scheduleEndTime = grPatrolRecord.getScheduleEndTime();
    		String isDeal = "1";
    		patrolRecordManager.addGrPatrolRecordHistory(scheduleId, orgId, orgName, userId, userName, patrolLineId, patrolLineName, patrolLineDesc, patrolNodes, scheduleStartTime, scheduleEndTime, isDeal);
    	}
    }

	public PatrolRecordManager getPatrolRecordManager() {
		return patrolRecordManager;
	}

	public void setPatrolRecordManager(PatrolRecordManager patrolRecordManager) {
		this.patrolRecordManager = patrolRecordManager;
	}
}
