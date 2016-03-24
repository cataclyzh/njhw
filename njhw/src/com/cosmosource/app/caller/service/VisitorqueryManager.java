package com.cosmosource.app.caller.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.List;


import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.VmVisit;
import com.cosmosource.app.entity.VmVisitAuxi;
import com.cosmosource.app.entity.VmVisitorinfo;
import com.cosmosource.base.dao.BaseDaoiBatis;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

/**
 * 发放临时卡
* @description: TODO
* @author gxh
* @date 2013-4-2 下午11:42:06
 */
public class VisitorqueryManager extends BaseManager{

    /**
     * 前台查找已确认访客
    * @title: queryVisitor 
    * @description: TODO
    * @author gxh
    * @param page
    * @param viName
    * @param residentNo
    * @param vsSt
    * @param vsFlag
    * @return
    * @date 2013-4-2 下午11:42:28     
    * @throws
     */
	public Page<HashMap> queryVisitor(final Page<HashMap> page, final String viName,String residentNo,String vsSt,String vsEt,String vsFlag) {
		 
		Map<String,Object> cMap = new HashMap<String,Object>();
		cMap.put("viName", viName);
		cMap.put("residentNo", residentNo);
		cMap.put("vsSt", vsSt);
		cMap.put("vsEt", vsEt);
		cMap.put("vsFlag", vsFlag);
		
	//	cMap.put("cMap",cMap);
		//List<String> vsFlagList = new ArrayList<String>();
		//if (vsFlag == null || vsFlag.trim().length() < 1){
		//	vsFlagList.add(VmVisit.VS_FLAG_SURE);
		//	vsFlagList.add(VmVisit.VS_FLAG_REFUSED);
		//	vsFlagList.add(VmVisit.VS_FLAG_CARDED);
		//	vsFlagList.add(VmVisit.VS_FLAG_COME);
		//} else {
		//	vsFlagList.add(vsFlag);
	//	}
	//	cMap.put("vsFlagList",vsFlagList);
	//	cMap.put("vsType",vsType);
		return sqlDao.findPage(page, "CallerSQL.selectRegCallerPageList", cMap);
	}
	
	
	/**
	 * 查看基本信息和访问事物
	* @title: findVisitorsinfo 
	* @description: TODO
	* @author gxh
	* @param vsId
	* @return
	* @date 2013-4-2 下午11:42:48     
	* @throws
	 */
	public Map findVisitorsinfo(String vsId){
		
		Map<String,String> vMap = new HashMap<String,String>();
		vMap.put("vsId", vsId);
		List<Map> list = sqlDao.findList("CallerSQL.findVisitorsinformation", vMap);
		if(list!=null&&list.size()>0){
			return list.get(0);
			
		}else{
			return null;
		}
		
	}
	/**
	 * 查找绑定副卡的条数
	* @title: selectNum 
	* @description: TODO
	* @author gxh
	* @param vsId
	* @return
	* @date 2013-4-2 下午11:42:59     
	* @throws
	 */
    public List<VmVisitAuxi> selectNum(Long vsId){
    	List<VmVisitAuxi> vvaList =  dao.findByProperty(VmVisitAuxi.class, "vsId" ,vsId);
		return vvaList;
	
    }
    
	/**
	 *根据id 得到访客事物对象
	* @title: getVisitByid 
	* @description: TODO
	* @author gxh
	* @param vsId
	* @return
	* @date 2013-4-2 下午11:43:08     
	* @throws
	 */
	public VmVisit getVisitByid(Long vsId){
		
		VmVisit visit = new VmVisit(); 
		List visitList = dao.findByProperty(VmVisit.class, "vsId", vsId);
		if(visitList != null && visitList.size()>=1){
			visit = (VmVisit)visitList.get(0);
			return visit;
		}
		return null;
	}
     /**
      * 退主卡
     * @title: exitCardByid 
     * @description: TODO
     * @author gxh
     * @param cardId
     * @return
     * @date 2013-4-2 下午11:43:20     
     * @throws
      */
   public VmVisit exitCardByid(String cardId){
		
		VmVisit visit = new VmVisit(); 
		List visitList = dao.findByProperty(VmVisit.class, "cardId", cardId);
		if(visitList != null && visitList.size()>=1){
			visit = (VmVisit)visitList.get(0);
			return visit;
		}
		return null;
	}
   public VmVisit exitCardByid1(String cardId){
		
		VmVisit visit = new VmVisit(); 
		List visitList = dao.findByHQL("select vva from VmVisit vva where vva.cardId = ? and vva.vsReturn =? and vva.cardType=? ", cardId,"2","2");
		if(visitList != null && visitList.size()>=1){
			visit = (VmVisit)visitList.get(0);
			return visit;
		}
		return null;
	}
   /**
    * 退副卡
   * @title: exitCardFByid 
   * @description: TODO
   * @author gxh
   * @param cardId
   * @return
   * @date 2013-4-2 下午11:43:31     
   * @throws
    */
 public VmVisitAuxi exitCardFByid(String cardId){
		
	 VmVisitAuxi visitf = new VmVisitAuxi(); 
		List visitList = dao.findByProperty(VmVisitAuxi.class, "cardId", cardId);
		if(visitList != null && visitList.size()>=1){
			visitf = (VmVisitAuxi)visitList.get(0);
			return visitf;
		}
		return null;
	}
 
 public VmVisitAuxi exitCardFByid1( String cardId){
		
	 VmVisitAuxi visitf = new VmVisitAuxi(); 
		List visitList = dao.findByHQL("select vva from VmVisitAuxi vva,VmVisit vv where vva.cardId = ?  and vva.vaReturn = ? and vva.cardType = ? and vv.status = ? and vv.vsId = vva.vsId", cardId,"2","2","1");
		if(visitList != null && visitList.size()>=1){
			visitf = (VmVisitAuxi)visitList.get(0);
			return visitf;
		}
		return null;
	}
	
	/**
	 * 新增或是更新保存对象访问事物
	* @title: saveEntity 
	* @description: TODO
	* @author gxh
	* @param entity
	* @date 2013-4-2 下午11:43:41     
	* @throws
	 */
	public void saveEntity(VmVisit entity){
		try{
		dao.saveOrUpdate(entity);
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	/**
	 * 新增或是更新保存对象副卡
	* @title: saveSoncard 
	* @description: TODO
	* @author gxh
	* @param entity
	* @date 2013-4-2 下午11:43:54     
	* @throws
	 */
	public void saveSoncard(VmVisitAuxi entity){
		try{
			dao.save(entity);
			}catch(Exception e){
				e.printStackTrace();
				
			}
		
	}

	/**
	 * 查询事务是否有没有归还的副卡
	* @title: getUnBackVVACard 
	* @description: TODO
	* @author gxh
	* @param vsId
	* @return
	* @date 2013-4-2 下午11:44:03     
	* @throws
	 */
	public List<VmVisitAuxi> getUnBackVVACard(Long vsId) {
		
		try{
			return dao.findByHQL("select vva from VmVisitAuxi vva where vsId = ? and vaReturn = ? ", vsId ,"2");
		}catch(Exception e){
			
			e.printStackTrace();
			return null;
		}
		 
	}

	/**
	 * 修改卡副卡归还状态
	* @title: updVmVisitStatus 
	* @description: TODO
	* @author gxh
	* @param vsId
	* @date 2013-4-2 下午11:44:26     
	* @throws
	 */
	public void updVmVisitStatus(Long vsId) {
		
		VmVisit vmVisit = (VmVisit)dao.findById(VmVisit.class, vsId);
		vmVisit.setVsretSub(VmVisit.VS_RET_SUB_YES);//副卡还完
		if(!vmVisit.getVsReturn().equals(VmVisit.VS_RETURN_RNO))//临时卡也还
		{
			vmVisit.setVsFlag(VmVisit.VS_FLAG_FINISH);	
			
		}
		dao.update(vmVisit);
		
	}
	
   /**
    * 根据id删除副卡
   * @title: delete 
   * @description: TODO
   * @author gxh
   * @param fid
   * @date 2013-4-6 下午03:34:54     
   * @throws
    */
	public int deleteVmVisitAuxi(long fid) {
		try{
			dao.deleteById(VmVisitAuxi.class, fid);
			return 1;
		}catch(Exception e){
			
			e.printStackTrace();
			return 0;
		}
		
		
	}
	
	/**
	 * 根据被访者id找到扩展信息
	* @title: findUsersExpByUserid 
	* @description: TODO
	* @author gxh
	* @param userid
	* @return
	* @date 2013-4-12 上午11:33:15     
	* @throws
	 */
	public NjhwUsersExp findUsersExpByUserid(Long userid){
		NjhwUsersExp exp = new NjhwUsersExp(); 
		List expList = dao.findByProperty(NjhwUsersExp.class, "userid", userid);
		if(expList != null && expList.size()>=1){
			exp = (NjhwUsersExp)expList.get(0);
			return exp;
		}
		return null;
	}
}
