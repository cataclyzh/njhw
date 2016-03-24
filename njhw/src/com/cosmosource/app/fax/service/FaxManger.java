package com.cosmosource.app.fax.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.CmsArticle;
import com.cosmosource.app.entity.ExtInFaxList;
import com.cosmosource.app.port.serviceimpl.FaxToAppService;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

public class FaxManger extends BaseManager {
	
	// 定义注入对象
	private FaxToAppService faxToAppService;
	/**
	 * @description: 传真登陆模拟
	 * @author qiyanqiang
	 * @param  userName password
	 * @return Page<HashMap>
	 */
	 public String faxToAppService(String userName , String password)
	 {
	    return 	 faxToAppService.requestDataForSessionId(userName, password);	 
	 }

	 /**
		 * @description: 查询已经发送传真的列表
		 * @author qiyanqiang
		 */
	  @SuppressWarnings({ "unchecked", "rawtypes" })
		public Page<HashMap> querySendFaxList(Page<HashMap> page, Map map) {
			return sqlDao.findPage(page, "FaxSQL.sendFaxList", map);
		}
	 
	  
	  
	  /**
		 * @description: 查询已接收传真
		 * @author qiyanqiang
		 */
	  @SuppressWarnings("unchecked")
		public Page<HashMap> queryReceiveFaxList(Page<HashMap> page, Map map) {
		  	Page<HashMap> repage = null;
		  	try {
		  		repage = sqlDao.findPage(page, "FaxSQL.receiveFaxList", map);
		  	} catch(Exception e){
		  		e.printStackTrace();
		  	}
			return repage;
		}
	  
	  /**
		 * @description:删除已经发送的fax
		 * @author  qiyanqiang
		 * @param ids
		 */
		public void deleteSendFax(String[] ids){
			dao.deleteByIds( ExtInFaxList.class, ids);
			//dao.flush();
		}
		
		  /**
		 * @description:当点击附件的时候更新已读标记
		 * @author  qiyanqiang
		 */
		public void updateReadMark(Long id){
			try {
				ExtInFaxList ext= (ExtInFaxList) dao.findById(ExtInFaxList.class,id);
				ext.setReadFlag("1");
				System.out.println(ext.getReadFlag());
				dao.saveOrUpdate(ext);
				//dao.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
				

		}
		
		
		  /**
		 * @description:删除已经接收的fax
		 * @author  qiyanqiang
		 * @param ids
		 */
		public void deleteReceiveFax(String[] ids){
			dao.deleteByIds( ExtInFaxList.class, ids);
			//dao.flush();
		}
		
		
		
	
	public FaxToAppService getFaxToAppService() {
		return faxToAppService;
	}

	public void setFaxToAppService(FaxToAppService faxToAppService) {
		this.faxToAppService = faxToAppService;
	}
	
	
	
	
	
	
	

}
