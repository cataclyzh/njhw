package com.cosmosource.common.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.common.entity.TCommonVendorAns;
import com.cosmosource.common.entity.TCommonVendorAnsfile;
import com.cosmosource.common.entity.TCommonVendorQuest;

public class QuestionManager  extends BaseManager{
	
	
	public String  findLoginNameWithMinQuestion(Map<String,Object> pMap){
		return "";
	}
	
	public void saveOrUpdate(Object entity){
		dao.saveOrUpdate(entity);
	}

	public void saveVenQuestion(TCommonVendorQuest entity,
			TCommonVendorAns entityAns, TCommonVendorAnsfile entityFile,
			File file) {
		
	}

	public void saveVenQuestionCode(TCommonVendorQuest entity) {
		
	}

	public Page<TCommonVendorQuest> queryVendorQuest(
			Page<TCommonVendorQuest> page, TCommonVendorQuest entity,
			String dateStart, String dateEnd) {
		return null;
	}

	public Page<TCommonVendorQuest> queryCloseVendorQuest(
			Page<TCommonVendorQuest> page, TCommonVendorQuest entity,
			String dateStart, String dateEnd) {
		return null;
	}

	public void deleteVendorQuest(String[] _chk) {
		
	}

	public String findQuestContent(Map param) {
		return null;
	}

	public List findAnsList(Map param) {
		return null;
	}

	public String findRoleByRoleName(Map<String, Object> tescoAPRoleNameMap) {
		return null;
	}

	public List findAnsFileList(Map param) {
		return null;
	}

}
