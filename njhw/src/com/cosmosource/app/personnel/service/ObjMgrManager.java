/**
* <p>文件名: OrgMgrManager.java</p>
* <p>描述：对象资源树管理Manager</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-8-30 下午01:25:51 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.app.personnel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.personnel.model.DoorControllerModel;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;


/**
 * @类描述:  对象资源树的管理类
 * 调用通用DAO对对象资源树的CRUD，及对象资源树树数据的查询
 * 
 * @作者： WXJ
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class ObjMgrManager extends BaseManager{
	
	/**
	 * 
	* @Title: findById 
	* @Description: TODO
	* @author WXJ
	* @date 2013-3-24 下午06:32:18 
	* @param @param orgid
	* @param @return    
	* @return Objtank 
	* @throws
	 */
	public Objtank findByNodeId(long orgid) {
		List<Objtank> list = this.findByHQL("select t from Objtank t where t.nodeId = ?", orgid);
		if (list!=null && list.size()>0) {
			Objtank org = (Objtank) list.get(0);
			return org;
		}else{
			return null;
		}		
	}
	
	 /**
	* @Description 判断门牌号是否存在
	* @Author：pingxianghua
	* @Date 2013-8-27 下午02:50:33 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public boolean findByName(String Name,long nodeid) {
		List<Objtank> list=null;
		if(nodeid !=0l)
		{
			 list = this.findByHQL("select t from Objtank t where t.name = ? and t.nodeId !=?", Name,nodeid);
		}else{
			list = this.findByHQL("select t from Objtank t where t.name = ?", Name);
		}
		if (list!=null && list.size()>0) {
				return true;
		}else{
			return false;
		}		
	}
		
	/**
	 * 新增或是更新保存对象
	 * @param entity
	 */
	public void saveOrg(Objtank entity){
		//设置本节点的父节点ExtIsBottom字段
		if (entity.getPId() != null && entity.getPId() > 0) {
			Objtank parent =(Objtank)dao.get(Objtank.class, entity.getPId());
			parent.setExtIsBottom("1");
			dao.update(parent);
			
			// 根据父节点的LevelNum计算节点的LevelNum
			long plvl = parent.getLevelNum() != null ? parent.getLevelNum().longValue() : 0;
			entity.setLevelNum((long)(plvl+1));
		} else {
			entity.setLevelNum((long)1);	// 当没有上级节点时，默认为顶级
		}
		
		// 本节点是否有下级（新建一个节点默认为最末级）
		if(entity.getNodeId() == null) {
			entity.setExtIsBottom("0");
			entity.setCreator(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
			entity.setCreateDate(DateUtil.getSysDate());
		} else {
			entity.setLastUpdateBy(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
			entity.setLastUpdateDate(DateUtil.getSysDate());
		}
		entity.setIsSystem((long)0);	// 外部添加非系统自动生成
		entity.setObjType("F");
		dao.saveOrUpdate(entity);
	}	
	
	/**
	 * 设备维护添加设备功能
	 * @param entity
	 */
	public void saveJzsbLight(Objtank entity){
		if (entity.getPId() != null && entity.getPId() > 0) {
			Objtank parent =(Objtank)dao.get(Objtank.class, entity.getPId());
			long plvl = parent.getLevelNum() != null ? parent.getLevelNum().longValue() : 0;
			entity.setLevelNum((long)(plvl+1));
		}else{
			entity.setLevelNum((long)1);	// 当没有上级节点时，默认为顶级
		}
		entity.setLastUpdateBy(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		entity.setLastUpdateDate(DateUtil.getSysDate());
		entity.setIsSystem((long)0);	// 外部添加非系统自动生成
		entity.setCreateDate(DateUtil.getSysDate());
		entity.setCreator(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		entity.setObjType("F");
		entity.setExtResType("6");
		entity.setExtIsBottom("1");
		dao.save(entity);
	}
	/**
	 * 设备维护添加设备功能 空调
	 * @param entity
	 */
	public void saveJzsbAirCond(Objtank entity){
		if (entity.getPId() != null && entity.getPId() > 0) {
			Objtank parent =(Objtank)dao.get(Objtank.class, entity.getPId());
			long plvl = parent.getLevelNum() != null ? parent.getLevelNum().longValue() : 0;
			entity.setLevelNum((long)(plvl+1));
		}else{
			entity.setLevelNum((long)1);	// 当没有上级节点时，默认为顶级
		}
		entity.setLastUpdateBy(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		entity.setLastUpdateDate(DateUtil.getSysDate());
		entity.setIsSystem((long)0);	// 外部添加非系统自动生成
		entity.setCreateDate(DateUtil.getSysDate());
		entity.setCreator(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		entity.setObjType("F");
		entity.setExtResType("7");
		entity.setExtIsBottom("1");
		dao.save(entity);
	}
	
	/**
	 * 设备维护添加设备功能 门锁
	 * @param entity
	 */
	public void saveJzsbLock(Objtank entity,Map par){
		if (entity.getPId() != null && entity.getPId() > 0) {
			Objtank parent =(Objtank)dao.get(Objtank.class, entity.getPId());
			long plvl = parent.getLevelNum() != null ? parent.getLevelNum().longValue() : 0;
			entity.setLevelNum((long)(plvl+1));
		}else{
			entity.setLevelNum((long)1);	// 当没有上级节点时，默认为顶级
		}
		entity.setLastUpdateBy(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		entity.setLastUpdateDate(DateUtil.getSysDate());
		entity.setIsSystem((long)0);	// 外部添加非系统自动生成
		entity.setCreateDate(DateUtil.getSysDate());
		entity.setCreator(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		entity.setObjType("F");
		entity.setExtResType("3");
		entity.setExtIsBottom("1");
		dao.save(entity);
		
		if(entity.getNodeId() != null){
			par.put("nodeId", entity.getNodeId());
			List list = new ArrayList();
			list.add(par);
			if(par != null){
				this.insertBatchBySql("PersonnelSQL.insertNjhwDoor", list);
			}
		}
	}
	
	public List<Map> queryJzsbLightList(String nodeId){
		List<Map> result = new ArrayList<Map>();
		
		Map param = new HashMap();
		param.put("nodeId", nodeId);
		result = findListBySql("PersonnelSQL.queryJzsbLightList", param);
		
		return result;
	}
	
	/**
	 * 查询对象资源树列表数据
	 * @param page
	 * @param model
	 * @return
	 */
	public Page<Objtank> queryOrgs(final Page<Objtank> page, final Objtank model) {
		List lPara = new ArrayList();
	
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select t from Objtank t ")
		.append("where t.PId=? ");
		lPara.add(model.getPId());
		if(StringUtil.isNotBlank(model.getName())){
			sbHql.append(" and t.name like ? ");
			lPara.add("%"+model.getName()+"%");
		}
		if(StringUtil.isNotBlank(model.getTitle())){
			sbHql.append("and t.title like ? ");
			lPara.add("%"+model.getTitle()+"%");
		}
		if(StringUtil.isNotBlank(model.getKeyword())){
			sbHql.append("and t.keyword like ? ");
			lPara.add("%"+model.getKeyword()+"%");
		}
		if(StringUtil.isNotBlank(model.getExtResType())){
			sbHql.append("and t.extResType = ");
			lPara.add(model.getExtResType());
		}
		sbHql.append(" order by t.extIsBottom desc, t.sort ");
		return dao.findPage(page,sbHql.toString(),lPara.toArray());
	}

	/**
	 * 查询对象资源树列表数据与右边列表相对应
	 * @param page
	 * @param model
	 * @return
	 */
	public Page<Objtank> queryOrgsMenu(final Page<Objtank> page, final Objtank model) {
		List lPara = new ArrayList();
		
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select t from Objtank t ")
		.append("where t.PId=? ");
		lPara.add(model.getPId());
		if(StringUtil.isNotBlank(model.getName())){
			sbHql.append(" and t.name like ? ");
			lPara.add("%"+model.getName()+"%");
		}
		if(StringUtil.isNotBlank(model.getTitle())){
			sbHql.append("and t.title like ? ");
			lPara.add("%"+model.getTitle()+"%");
		}
		if(StringUtil.isNotBlank(model.getKeyword())){
			sbHql.append("and t.keyword like ? ");
			lPara.add("%"+model.getKeyword()+"%");
		}
		sbHql.append(" order by t.extIsBottom desc, t.sort , t.name ");
		return dao.findPage(page,sbHql.toString(),lPara.toArray());
	}
	
	public Page<Objtank> queryJzjg(final Page<Objtank> page, final Objtank model) {
		List lPara = new ArrayList();
	
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select t from Objtank t ")
		.append("where t.PId=? and (t.extResType='S' or t.extResType='B' or t.extResType='F' or t.extResType='R') ");
		lPara.add(model.getPId());
		if(StringUtil.isNotBlank(model.getName())){
			sbHql.append(" and t.name like ? ");
			lPara.add("%"+model.getName()+"%");
		}
		if(StringUtil.isNotBlank(model.getTitle())){
			sbHql.append("and t.title like ? ");
			lPara.add("%"+model.getTitle()+"%");
		}
		if(StringUtil.isNotBlank(model.getKeyword())){
			sbHql.append("and t.keyword like ? ");
			lPara.add("%"+model.getKeyword()+"%");
		}
		if(StringUtil.isNotBlank(model.getExtResType())){
			sbHql.append("and t.extResType = ");
			lPara.add(model.getExtResType());
		}
		sbHql.append(" order by length(name), name ");

		return dao.findPage(page,sbHql.toString(),lPara.toArray());
		
	}
	
	/**
	 * 导航菜单权限分配 用户
	 * @param page
	 * @param model
	 * @return
	 */
	public List queryAdminUser(long nodeId) {
		Map param = new HashMap();
		param.put("nodeId", nodeId);
		return this.findListBySql("PersonnelSQL.queryUserAdminRoot", param);
	}
	/**
	 * 导航菜单权限分配组织
	 * @param page
	 * @param model
	 * @return
	 */
	public List queryAdminOrg(long nodeId) {
		Map param = new HashMap();
		param.put("nodeId", nodeId);
		return this.findListBySql("PersonnelSQL.queryOrgAdminRoot", param);
	} 
	
	/**
	 * 删除用户的权限。
	 * @开发者：ywl
	 * @param userids
	 * @return
	 */
	public void delAdminUser(List list){
		this.deleteBatchBySql("PersonnelSQL.delObjAdminRoot", list);
	}

	 /**
	* @Description 删除通讯机
	* @Author：zhujiabiao
	* @Date 2013-8-17 上午10:43:00 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public void deleteDoorControllerComm(Map map){
		this.deleteBySql("PersonnelSQL.deleteDoorControllerComm",map);
	}
	
	
	 /**
	* @Description 查找具有改菜单权限的人
	* @Author：zhujiabiao
	* @Date 2013-8-7 下午07:34:57 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public List<Users> queryUsers(Long nodeid) {
		String sql = "select u.* from users u where u.userid in (select opm.person_id from obj_perm_map opm where opm.node_id = ?)";
		return (List<Users>)dao.getSession().createSQLQuery(sql).addEntity(Users.class).setLong(0, nodeid).list();
	} 
	/**
	 * @描述: 删除选中的对象资源树
	 * 如果对象资源树下有用户，角色，权限，或是子对象资源树则此对象资源树不能被删除
	 * 整个过程是一个事务
	 * @param ids　选中的对象资源树ＩＤ数组
	 */
	public Long deleteOrgs(String[] ids){
		Long lVari = checkOrgChildren(ids);
		if(lVari==null){			
			dao.deleteByIds(Objtank.class, ids);
			return (long)0;
		}else{
			return lVari;
		}
		
		
	}
	
	public Long deleteLock(String[] ids,List list){
		Long lVari = checkOrgChildren(ids);
		if(lVari==null){			
			dao.deleteByIds(Objtank.class, ids);
			this.deleteBatchBySql("PersonnelSQL.delJzsbLockCheck", list);
			return (long)0;
		}else{
			return lVari;
		}
		
		
	}
	
	/**
	 * 开发者：ywl
	 * 合并房间及设备
	 * @param param
	 * @return
	 */
	public void updateHomeById(Map param)throws Exception{
		this.updateBySql("PersonnelSQL.updateHome", param);
		this.updateBySql("PersonnelSQL.updateObjPid", param);
	}
	/**
	 * 合并设备 暂不使用该方法
	 * @param param
	 * @return
	 */
	public int updateObjPid(Map param){
		return this.updateBySql("PersonnelSQL.updateObjPid", param);
	}
	/**
	 * 删除被合并的房间
	 * @param param
	 * @return
	 */
	public int delHomeByNid(Map param){
		return this.deleteBySql("PersonnelSQL.delHome", param);
	}
	/**
	 * 查询被合并的房间下是否有人
	 * @param param
	 * @return
	 */
	public List findHomePeople(Map param){
		return this.findListBySql("PersonnelSQL.searchHomePerple", param);
	}
	/**
	 * 更新对象，传入sqlMap Map
	 * @param sqlMap
	 * @param param
	 * @return
	 */
	public int updateObjTankAll(String sqlMap , Map param){
		return this.updateBySql(sqlMap, param);
	}
	
	public int updateObjTankSecond(Map param,Map par){
		 this.updateBySql("PersonnelSQL.updateJzsbInfoLight", param);
		 this.updateBySql("PersonnelSQL.updateJzsbInfoLock", par);
		 return 0 ;
	}
	
	/**
	 * 根据条件查询是否存在数据
	 * @param sqlMap
	 * @param param
	 * @return
	 */
	public List queryListLockCount(String sqlMap, Map param){
		return this.findListBySql(sqlMap, param);
	}
	
	/**
	 * 查询对象数据
	 * @param sqlMap
	 * @param param
	 * @return
	 */
	public List findObjTankAll(String sqlMap , Map param){
		return this.findListBySql(sqlMap, param);
	}
	
	/**
	 * @描述: 判断此对象资源树是否有关联信息不能删除
	 * @作者： WXJ
	 * @param ids
	 * @return
	 */
	public Long checkOrgChildren(String[] ids){
		List list = null;
		Objtank ent = null;
		for(int i=0;i<ids.length;i++){
			
			ent = (Objtank)dao.get(Objtank.class, Long.parseLong(ids[i]));
			if(ent!=null){

				//判断子对象资源树
				list = dao.findByProperty(Objtank.class, "PId", Long.parseLong(ids[i]));
				if(list!=null&&list.size()>0){
					return Long.parseLong(ids[i]);
				}
//				//判断用户
//				list = dao.findByProperty(TAcUser.class, "orgid", new Long(ids[i]));
//				if(list!=null&&list.size()>0){
//					return new Long(ids[i]);
//				}
//				//判断角色
//				list = dao.findByProperty(TAcRole.class, "orgid", new Long(ids[i]));
//				if(list!=null&&list.size()>0){
//					return new Long(ids[i]);
//				}
//				
//				//如果是总部
//				if("1".equals(ent.getOrgtype())){
//					//判断权限
//					list = dao.findByProperty(TAcMenu.class, "orgid", new Long(ids[i]));
//					if(list!=null&&list.size()>1){
//						return new Long(ids[i]);
//					}
//				}else{
//					//判断权限
//					list = dao.findByProperty(TAcMenu.class, "orgid", new Long(ids[i]));
//					if(list!=null&&list.size()>0){
//						return new Long(ids[i]);
//					}
//				}
			}
		}
		
		return null;
	}
	
	/**
	 * 
	* @Title: getObjTreeSelectData 
	* @Description: 
	* @author WXJ
	* @date 2013-5-6 下午01:01:57 
	* @param @param orgid
	* @param @param ctx
	* @param @param type
	* @param @return    
	* @return String 
	* @throws
	 */
	public String getObjTreeSelectData(String objId, String ctx, String type) {
		if (objId==null) {	
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");
			
			Map pMap = new HashMap();
			pMap.put("userid", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
			List<Objtank> list = this.findListBySql("PersonnelSQL.getObjByManager", pMap);
			
			for (Objtank obj : list) {
	            Element el=root.addElement("item");   
	            el.addAttribute("text", obj.getName());
	            el.addAttribute("id", obj.getNodeId()+"");
	            el.addAttribute("child", "1");	                     
	        }

			return doc.asXML();
		} else {		
			String nodeId = objId;
			Objtank objt = (Objtank)this.findById(Objtank.class, new Long(nodeId));
			Element root= DocumentHelper.createElement("tree"); 
			
			root.addAttribute("id",nodeId+"");
			
			List<Objtank> list = this.findByHQL(" from Objtank where extResType in ('"+
					Objtank.EXT_RES_TYPE_S+"','"+
					Objtank.EXT_RES_TYPE_B+"','"+
					Objtank.EXT_RES_TYPE_F+"','"+
					Objtank.EXT_RES_TYPE_R+"'"+
					") and PId=?", new Long(nodeId));
			
			for (Objtank obj : list) {
	            Element el=root.addElement("item");   
	            el.addAttribute("text", obj.getName());
	            el.addAttribute("id", obj.getNodeId()+"");           
	            if("1".equals(obj.getExtIsBottom())){
	            	el.addAttribute("child", "1");
	            }else{
	            	el.addAttribute("child", "0");
	            }	            
	        }
			return root.asXML();
		}		

	}	
	
	/**
	 * 
	 * 此方法描述的是： 设备管理page页面
	 * @author:zhangquanwei
	 * @version: 2013-8-17 下午02:55:14
	 * @return page
	 */
	public Page deviceAuthMangerPage(final Page<HashMap<String,String>> page ,HashMap<String, String> conMap)
	{
		return sqlDao.findPage(page, "PersonnelSQL.deviceAuthList", conMap);
	}
	
	public String getJzjgTreeData(String objId, String ctx, String type,String resType) {		
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");
			Map pMap = new HashMap();
			List<Objtank> list = this.findListBySql("PersonnelSQL.getTopStorey", pMap);
			
			for (Objtank org : list) {
	            Element el=root.addElement("item");   
	            el.addAttribute("text", org.getTitle());
	            el.addAttribute("id", org.getNodeId()+"");
	            el.addAttribute("open", "1");
	            Element elx = el.addElement("userdata");
	            elx.addAttribute("name", "url");
	            if("1".equals(org.getExtIsBottom())){
	            	el.addAttribute("child", "1");
	            }else{
	            	el.addAttribute("child", "0");
	            }
	            elx.addText(ctx+"/app/per/jzjgList.act?nodeId="+ org.getNodeId() + "&resType=" + resType);
	        }
			
			return doc.asXML();
		} else {
			Element root= DocumentHelper.createElement("tree"); 
			root.addAttribute("id",objId);
			List<Objtank> list =   dao.findByHQL("select t from Objtank t where t.PId=? order by length(name), name ", Long.parseLong(objId));
			
			List<String> usefullTypeList = new ArrayList<String>();
			usefullTypeList.add("S");
			usefullTypeList.add("B");
			usefullTypeList.add("F");
			usefullTypeList.add("R");
			
			for (Objtank obj : list) {
				String extResType = obj.getExtResType();

				if(extResType == null || !usefullTypeList.contains(extResType)){
					continue;
				}
				
	            Element el=root.addElement("item");   
	            el.addAttribute("text", obj.getName());
	            el.addAttribute("id", obj.getNodeId()+"");
	            Element elx = el.addElement("userdata");
	            elx.addAttribute("name", "url");
	            
	            if(extResType.equals("R")){
	            	el.addAttribute("child", "0");
		           }else{
	            	el.addAttribute("child", "1");
	            	elx.addText(ctx+"/app/per/jzjgList.act?nodeId="+ obj.getNodeId() + "&resType=" + resType);
	            }
	        }

			return root.asXML();
		}
		
	}
	
	public String getJzsbTreeData(String objId, String ctx, String type,String resType) {		
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");
			Map pMap = new HashMap();
//			List<Objtank> list = this.findListBySql("PersonnelSQL.getTopStorey", pMap);
			
            Element el=root.addElement("item");   
            el.addAttribute("text", "设备");
            el.addAttribute("id", "1");
            el.addAttribute("open", "1");
            Element elx = el.addElement("userdata");
            //elx.addAttribute("name", "url");
            el.addAttribute("child", "1");
            //elx.addText(ctx+"/app/per/jzsbList.act?nodeId=1");
			return doc.asXML();
		} else {
			Element root= DocumentHelper.createElement("tree"); 
			root.addAttribute("id",objId);
			
			if(objId.equals("1")){
	            Element el1=root.addElement("item");   
	            el1.addAttribute("text", "门锁通讯机");
	            el1.addAttribute("id", "2");
	            el1.addAttribute("child", "0");
	            el1.addAttribute("im0", "tongxunji.PNG");
	            el1.addAttribute("im1", "tongxunji.PNG");
	            el1.addAttribute("im2", "tongxunji.PNG");
	            Element elx1 = el1.addElement("userdata");
	            elx1.addAttribute("name", "url");	            
	            elx1.addText(ctx+"/app/per/jzsbDoorControllerList.act?nodeId=12");
	            
	            Element el2=root.addElement("item");   
	            el2.addAttribute("text", "电子门锁");
	            el2.addAttribute("id", "3");
	            el2.addAttribute("child", "1");
	            el2.addAttribute("im0", "mensuo.PNG");
	            el2.addAttribute("im1", "mensuo.PNG");
	            el2.addAttribute("im2", "mensuo.PNG");
	            Element elx2 = el2.addElement("userdata");
	            elx2.addAttribute("name", "url");	            
	            elx2.addText(ctx+"/app/per/jzsbLockListFath.act?nodeId=13");
	            
	            Element el3=root.addElement("item");   
	            el3.addAttribute("text", "房间照明");
	            el3.addAttribute("id", "4");
	            el3.addAttribute("child", "1");
	            el3.addAttribute("im0", "zhaoming.PNG");
	            el3.addAttribute("im1", "zhaoming.PNG");
	            el3.addAttribute("im2", "zhaoming.PNG");
	            Element elx3 = el3.addElement("userdata");
	            elx3.addAttribute("name", "url");	            
	            elx3.addText(ctx+"/app/per/jzsbLightListFath.act?nodeId=14");
	            
	            Element el4=root.addElement("item");   
	            el4.addAttribute("text", "房间空调");
	            el4.addAttribute("id", "5");
	            el4.addAttribute("child", "1");
	            el4.addAttribute("im0", "kongtiao.PNG");
	            el4.addAttribute("im1", "kongtiao.PNG");
	            el4.addAttribute("im2", "kongtiao.PNG");
	            Element elx4 = el4.addElement("userdata");
	            elx4.addAttribute("name", "url");	            
	            elx4.addText(ctx+"/app/per/jzsbAirCondListFath.act?nodeId=15");
			}else if(objId.equals("3")){
				addElement(root, ctx, 3);
			}else if(objId.equals("4")){
				addElement(root, ctx, 4);
			}else if(objId.equals("5")){
				addElement(root, ctx, 5);
			}

			return root.asXML();
		}
	}
	
	private void addElement(Element root, String ctx, int type){
		Map pMap = new HashMap();
		List<Map> list = this.findListBySql("PersonnelSQL.getFloors", pMap);
		for(Map m : list){
			Element el=root.addElement("item");
			if(m.get("NAME").toString() == ""){
				el.addAttribute("text", "");
			}else{
				el.addAttribute("text", m.get("NAME").toString());
			}
			if(m.get("NODE_ID").toString() == ""){
				el.addAttribute("id", "");
			}else{
				el.addAttribute("id", type + "_" + m.get("NODE_ID").toString());
			}
			el.addAttribute("child", "0");
            Element elx = el.addElement("userdata");
            elx.addAttribute("name", "url");
            if(type == 3){
            	elx.addText(ctx+"/app/per/jzsbElectronicLockList.act?nodeId=" + m.get("NODE_ID").toString());
            }else if(type == 4){
            	elx.addText(ctx+"/app/per/jzsbLightList.act?nodeId=" + m.get("NODE_ID").toString());
            }else if(type == 5){
            	elx.addText(ctx+"/app/per/jzsbAirConditionList.act?nodeId=" + m.get("NODE_ID").toString());
            }
		}
	}
	
	/*
	public String getJzsbTreeData(String objId, String ctx, String type,String resType) {		
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");
			Map pMap = new HashMap();
			List<Objtank> list = this.findListBySql("PersonnelSQL.getTopStorey", pMap);
			
			for (Objtank org : list) {
	            Element el=root.addElement("item");   
	            el.addAttribute("text", org.getTitle());
	            el.addAttribute("id", org.getNodeId()+"");
	            el.addAttribute("open", "1");
	            Element elx = el.addElement("userdata");
	            elx.addAttribute("name", "url");
	            if("1".equals(org.getExtIsBottom())){
	            	el.addAttribute("child", "1");
	            }else{
	            	el.addAttribute("child", "0");
	            }
	            elx.addText(ctx+"/app/per/jzsbList.act?nodeId="+ org.getNodeId() + "&resType=" + resType);
	        }
			
			return doc.asXML();
		} else {
			Element root= DocumentHelper.createElement("tree"); 
			root.addAttribute("id",objId);
			List<Objtank> list =   dao.findByHQL("select t from Objtank t where t.PId=? order by length(name), name ", Long.parseLong(objId));
			
			List<String> usefullTypeList = new ArrayList<String>();
			usefullTypeList.add("S");
			usefullTypeList.add("B");
			usefullTypeList.add("F");
			usefullTypeList.add("R");
			usefullTypeList.add("3");
			usefullTypeList.add("5");
			usefullTypeList.add("6");
			
			for (Objtank obj : list) {
				String extResType = obj.getExtResType();

				if(extResType == null || !usefullTypeList.contains(extResType)){
					continue;
				}
				
	            Element el=root.addElement("item");   
	            el.addAttribute("text", obj.getName());
	            el.addAttribute("id", obj.getNodeId()+"");
	            Element elx = el.addElement("userdata");
	            elx.addAttribute("name", "url");
	            
	            if("1".equals(obj.getExtIsBottom())){
					if(obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_1)||
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_2)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_3)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_4)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_5)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_6)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_7)	
					){
						el.addAttribute("child", "0");
					}else{
						el.addAttribute("child", "1");
					}
				}else{
					el.addAttribute("child", "0");
				}
	            elx.addText(ctx+"/app/per/jzsbList.act?nodeId="+ obj.getNodeId() + "&resType=" + resType);
	        }

			return root.asXML();
		}
		
	}*/
	
	/**
	 * 
	* @Title: getObjTreeData 
	* @Description: 一级页面建筑结构树
	* @author WXJ
	* @date 2013-5-18 上午09:46:32 
	* @param @param objId
	* @param @param ctx
	* @param @param type
	* @param @return    
	* @return String 
	* @throws
	 */
	public String getObjTreeData(String objId, String ctx, String type,String resType) {		
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");
//			List<Objtank> list = this.findByHQL("select t from Objtank t where t.nodeId = ?", Long.parseLong(orgid));
			
			Map pMap = new HashMap();
			//pMap.put("userid", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
			pMap.put("userid", "1");
			List<Objtank> list = this.findListBySql("PersonnelSQL.getObjByManager", pMap);
			
//			Objtank org = (Objtank) list.get(0);
//			Element el = root.addElement("item");
//			el.addAttribute("text", org.getTitle());
//			el.addAttribute("id", org.getNodeId() + "");
//			el.addAttribute("child", "1");
//			Element elx = el.addElement("userdata");
//			elx.addAttribute("name", "url");
//			elx.addText(ctx + "/app/per/objList.act?nodeId=" + org.getNodeId());
			
			for (Objtank org : list) {
	            Element el=root.addElement("item");   
	            el.addAttribute("text", org.getTitle());
	            el.addAttribute("id", org.getNodeId()+"");
	            Element elx = el.addElement("userdata");
	            elx.addAttribute("name", "url");
	            if("1".equals(org.getExtIsBottom())){
	            	el.addAttribute("child", "1");
	            }else{
	            	el.addAttribute("child", "0");
	            }
	            elx.addText(ctx+"/app/per/objList.act?nodeId="+ org.getNodeId() + "&resType=" + resType);
	        }
			
			return doc.asXML();
		} else {
			Element root= DocumentHelper.createElement("tree"); 
			root.addAttribute("id",objId);
			List<Objtank> list =   dao.findByHQL("select t from Objtank t where t.PId=? order by keyword ", Long.parseLong(objId));
			if(list.size()<=200){		
				for (Objtank org : list) {
		            Element el=root.addElement("item");   
		            el.addAttribute("text", org.getTitle());
		            el.addAttribute("id", org.getNodeId()+"");
		            Element elx = el.addElement("userdata");
		            elx.addAttribute("name", "url");
		            if("1".equals(org.getExtIsBottom())){
		            	el.addAttribute("child", "1");
		            }else{
		            	el.addAttribute("child", "0");
		            }
		            elx.addText(ctx+"/app/per/objList.act?nodeId="+ org.getNodeId() + "&resType=" + resType);
		        }
			}
			return root.asXML();
		}
		
	}
	 /**
	* @Description
	* @Author：zhujiabiao
	* @Date 2013-8-12 下午08:32:15 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String getMenuTreeData(String objId, String ctx, String type,String resType) {		
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");
//			List<Objtank> list = this.findByHQL("select t from Objtank t where t.nodeId = ?", Long.parseLong(orgid));
			
			Map pMap = new HashMap();
			//pMap.put("userid", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
			pMap.put("userid", "1");
			List<Objtank> list = this.findListBySql("PersonnelSQL.getObjByManager", pMap);
			
//			Objtank org = (Objtank) list.get(0);
//			Element el = root.addElement("item");
//			el.addAttribute("text", org.getTitle());
//			el.addAttribute("id", org.getNodeId() + "");
//			el.addAttribute("child", "1");
//			Element elx = el.addElement("userdata");
//			elx.addAttribute("name", "url");
//			elx.addText(ctx + "/app/per/objList.act?nodeId=" + org.getNodeId());
			
			for (Objtank org : list) {
				Element el=root.addElement("item");   
				el.addAttribute("text", org.getTitle());
				el.addAttribute("id", org.getNodeId()+"");
				Element elx = el.addElement("userdata");
				elx.addAttribute("name", "url");
				if("1".equals(org.getExtIsBottom())){
					el.addAttribute("child", "1");
				}else{
					el.addAttribute("child", "0");
				}
				elx.addText(ctx+"/app/per/menuList.act?nodeId="+ org.getNodeId() + "&resType=" + resType);
			}
			
			return doc.asXML();
		} else {
			Element root= DocumentHelper.createElement("tree"); 
			root.addAttribute("id",objId);
			List<Objtank> list =   dao.findByHQL("select t from Objtank t where t.PId=? order by keyword ", Long.parseLong(objId));
			if(list.size()<=200){		
				for (Objtank org : list) {
					Element el=root.addElement("item");   
					el.addAttribute("text", org.getTitle());
					el.addAttribute("id", org.getNodeId()+"");
					Element elx = el.addElement("userdata");
					elx.addAttribute("name", "url");
					if("1".equals(org.getExtIsBottom())){
						el.addAttribute("child", "1");
					}else{
						el.addAttribute("child", "0");
					}
					elx.addText(ctx+"/app/per/menuList.act?nodeId="+ org.getNodeId() + "&resType=" + resType);
				}
			}
			return root.asXML();
		}
		
	}
	 /**
	* @Description
	* @Author：zhujiabiao
	* @Date 2013-8-7 下午05:58:10 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String getObjAdminTreeData(String objId, String ctx, String type,String resType) {		
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");
//			List<Objtank> list = this.findByHQL("select t from Objtank t where t.nodeId = ?", Long.parseLong(orgid));
			
			Map pMap = new HashMap();
			//pMap.put("userid", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
			pMap.put("userid", "1");
			List<Objtank> list = this.findListBySql("PersonnelSQL.getObjByManager", pMap);
			
//			Objtank org = (Objtank) list.get(0);
//			Element el = root.addElement("item");
//			el.addAttribute("text", org.getTitle());
//			el.addAttribute("id", org.getNodeId() + "");
//			el.addAttribute("child", "1");
//			Element elx = el.addElement("userdata");
//			elx.addAttribute("name", "url");
//			elx.addText(ctx + "/app/per/objList.act?nodeId=" + org.getNodeId());
			
			for (Objtank org : list) {
				Element el=root.addElement("item");   
				el.addAttribute("text", org.getTitle());
				el.addAttribute("id", org.getNodeId()+"");
				Element elx = el.addElement("userdata");
				elx.addAttribute("name", "url");
				if("1".equals(org.getExtIsBottom())){
					el.addAttribute("child", "1");
				}else{
					el.addAttribute("child", "0");
				}
				elx.addText(ctx+"/app/per/objAdminList.act?nodeId="+ org.getNodeId() + "&resType=" + resType);
			}
			
			return doc.asXML();
		} else {
			Element root= DocumentHelper.createElement("tree"); 
			root.addAttribute("id",objId);
			List<Objtank> list =   dao.findByHQL("select t from Objtank t where t.PId=? order by keyword ", Long.parseLong(objId));
			if(list.size()<=200){		
				for (Objtank org : list) {
					Element el=root.addElement("item");   
					el.addAttribute("text", org.getTitle());
					el.addAttribute("id", org.getNodeId()+"");
					Element elx = el.addElement("userdata");
					elx.addAttribute("name", "url");
					if("1".equals(org.getExtIsBottom())){
						el.addAttribute("child", "1");
					}else{
						el.addAttribute("child", "0");
					}
					elx.addText(ctx+"/app/per/objAdminList.act?nodeId="+ org.getNodeId() + "&resType=" + resType);
				}
			}
			return root.asXML();
		}
		
	}
	
		
	/**
	 * 
	* @Title: getObjMenuTreeSelectData 
	* @Description: 一级页面功能菜单树
	* @author WXJ
	* @date 2013-5-16 下午10:54:57 
	* @param @param objId
	* @param @param ctx
	* @param @param type
	* @param @return    
	* @return String 
	* @throws
	 */
	public String getObjMenuTreeData(String objId, String ctx, String type,String resType) {
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");
			
			List<Objtank> list = this.findByHQL(" from Objtank where nodeId=?", 30L);
			
			for (Objtank obj : list) {
	            Element el=root.addElement("item");   
	            el.addAttribute("text", obj.getName());
	            el.addAttribute("id", obj.getNodeId()+"");
	            el.addAttribute("child", "1");	  
	            Element elx = el.addElement("userdata");
	            elx.addAttribute("name", "url");
	            if("1".equals(obj.getExtIsBottom())){
	            	el.addAttribute("child", "1");
	            }else{
	            	el.addAttribute("child", "0");
	            }
	            elx.addText(ctx+"/app/per/objList.act?nodeId="+ obj.getNodeId() + "&resType=" + resType);
	        }

			return doc.asXML();
		} else {		
			String nodeId = objId;
			Objtank objt = (Objtank)this.findById(Objtank.class, new Long(nodeId));
			Element root= DocumentHelper.createElement("tree"); 
			
			root.addAttribute("id",nodeId+"");
			
			List<Objtank> list = this.findByHQL(" from Objtank where PId=? and extResType = ? ", new Long(nodeId), Objtank.EXT_RES_TYPE_M);
			
			for (Objtank obj : list) {
	            Element el=root.addElement("item");   
	            el.addAttribute("text", obj.getName());
	            el.addAttribute("id", obj.getNodeId()+"");      
	            if("1".equals(obj.getExtIsBottom())){
	            	el.addAttribute("child", "1");
	            }else{
	            	el.addAttribute("child", "0");
	            }	      
	            Element elx = el.addElement("userdata");
	            elx.addAttribute("name", "url");
	            if("1".equals(obj.getExtIsBottom())){
	            	el.addAttribute("child", "1");
	            }else{
	            	el.addAttribute("child", "0");
	            }
	            elx.addText(ctx+"/app/per/objList.act?nodeId="+ obj.getNodeId() + "&resType=" + resType);
	        }
			return root.asXML();
		}		

	}	

	/**
	 * 
	 * @Title: getMenuMenuTreeData 
	 * @Description: 一级页面功能菜单树
	 * @author WXJ
	 * @date 2013-5-16 下午10:54:57 
	 * @param @param objId
	 * @param @param ctx
	 * @param @param type
	 * @param @return    
	 * @return String 
	 * @throws
	 */
	public String getMenuMenuTreeData(String objId, String ctx, String type,String resType) {
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");
			
			List<Objtank> list = this.findByHQL(" from Objtank where nodeId=?", 30L);
			
			for (Objtank obj : list) {
				Element el=root.addElement("item");   
				el.addAttribute("text", obj.getName());
				el.addAttribute("id", obj.getNodeId()+"");
				el.addAttribute("child", "1");	  
				Element elx = el.addElement("userdata");
				elx.addAttribute("name", "url");
				if("1".equals(obj.getExtIsBottom())){
					el.addAttribute("child", "1");
				}else{
					el.addAttribute("child", "0");
				}
				elx.addText(ctx+"/app/per/menuList.act?nodeId="+ obj.getNodeId() + "&resType=" + resType);
			}
			
			return doc.asXML();
		} else {		
			String nodeId = objId;
			Objtank objt = (Objtank)this.findById(Objtank.class, new Long(nodeId));
			Element root= DocumentHelper.createElement("tree"); 
			
			root.addAttribute("id",nodeId+"");
			
			List<Objtank> list = this.findByHQL(" from Objtank where PId=? and extResType = ? order by extIsBottom desc, sort asc, name ", new Long(nodeId), Objtank.EXT_RES_TYPE_M);
			
			for (Objtank obj : list) {
				Element el=root.addElement("item");   
				el.addAttribute("text", obj.getName());
				el.addAttribute("id", obj.getNodeId()+"");      
				if("1".equals(obj.getExtIsBottom())){
					el.addAttribute("child", "1");
				}else{
					el.addAttribute("child", "0");
				}	      
				Element elx = el.addElement("userdata");
				elx.addAttribute("name", "url");
				if("1".equals(obj.getExtIsBottom())){
					el.addAttribute("child", "1");
				}else{
					el.addAttribute("child", "0");
				}
				elx.addText(ctx+"/app/per/menuList.act?nodeId="+ obj.getNodeId() + "&resType=" + resType);
			}
			return root.asXML();
		}		
		
	}	
	 /**
	* @Description
	* @Author：zhujiabiao
	* @Date 2013-8-7 下午05:56:30 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String getObjAdminMenuTreeData(String objId, String ctx, String type,String resType) {
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");
			
			List<Objtank> list = this.findByHQL(" from Objtank where nodeId=?", 30L);
			
			for (Objtank obj : list) {
				Element el=root.addElement("item");
				el.addAttribute("open", "1");
				el.addAttribute("text", obj.getName());
				el.addAttribute("id", obj.getNodeId()+"");
				el.addAttribute("child", "1");	  
				Element elx = el.addElement("userdata");
				elx.addAttribute("name", "url");
				if("1".equals(obj.getExtIsBottom())){
					el.addAttribute("child", "1");
				}else{
					el.addAttribute("child", "0");
				}
				elx.addText(ctx+"/app/per/objAdminList.act?nodeId="+ obj.getNodeId() + "&resType=" + resType);
			}
			System.out.println(doc.asXML());
			return doc.asXML();
		} else {		
			String nodeId = objId;
			Objtank objt = (Objtank)this.findById(Objtank.class, new Long(nodeId));
			Element root= DocumentHelper.createElement("tree"); 
			
			root.addAttribute("id",nodeId+"");
			
			List<Objtank> list = this.findByHQL(" from Objtank where PId=? and extResType = ?  order by extIsBottom desc,sort , name ", new Long(nodeId), Objtank.EXT_RES_TYPE_M);
			
			for (Objtank obj : list) {
				Element el=root.addElement("item");   
				el.addAttribute("text", obj.getName());
				el.addAttribute("id", obj.getNodeId()+"");      
				if("1".equals(obj.getExtIsBottom())){
					el.addAttribute("child", "1");
				}else{
					el.addAttribute("child", "0");
				}	      
				Element elx = el.addElement("userdata");
				elx.addAttribute("name", "url");
				if("1".equals(obj.getExtIsBottom())){
					el.addAttribute("child", "1");
				}else{
					el.addAttribute("child", "0");
				}
				elx.addText(ctx+"/app/per/objAdminList.act?nodeId="+ obj.getNodeId() + "&resType=" + resType);
			}
			System.out.println(root.asXML());
			return root.asXML();
		}		
		
	}	
	
	
	
	public String getObjDevTreeData(String objId, String ctx, String type,String resType) {
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");
			
			Map pMap = new HashMap();
			pMap.put("userid", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
			List<Objtank> list = this.findListBySql("PersonnelSQL.getTopStorey", pMap);
			
			for (Objtank obj : list) {
	            Element el=root.addElement("item");
	            el.addAttribute("open", "1");
	            el.addAttribute("text", obj.getName());
	            el.addAttribute("id", obj.getNodeId()+"");
	            el.addAttribute("child", "1");	      
	            Element elx = el.addElement("userdata");
	            elx.addAttribute("name", "url");
	            if("1".equals(obj.getExtIsBottom())){
	            	el.addAttribute("child", "1");
	            }else{
	            	el.addAttribute("child", "0");
	            }
	            elx.addText(ctx+"/app/per/objList.act?nodeId="+ obj.getNodeId() + "&resType=" + resType);
	        }

			return doc.asXML();
		} else {		
			String nodeId = objId;
			Objtank objt = (Objtank)this.findById(Objtank.class, new Long(nodeId));
			Element root= DocumentHelper.createElement("tree"); 
			
			root.addAttribute("id",nodeId+"");
			
			List<Objtank> list = this.findByHQL(" from Objtank o where o.extResType <> 'M' and PId=? order by o.extResType desc ,length(o.name), o.name asc", new Long(nodeId));
			
			for (Objtank obj : list) {
	            Element el=root.addElement("item");   
	            el.addAttribute("text", obj.getName());
	            el.addAttribute("id", obj.getNodeId()+"");          
	            if("1".equals(obj.getExtIsBottom())){
	            	if(obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_1)||
		            	obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_2)||	
	        			obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_3)||	
						obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_4)||	
						obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_5)||	
						obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_6)||	
						obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_7)	
	            	){
						el.addAttribute("child", "0");
					}else{
						el.addAttribute("child", "1");
					}
	            }else{
	            	el.addAttribute("child", "0");
	            }	            
	            Element elx = el.addElement("userdata");
	            elx.addAttribute("name", "url");
	            if("1".equals(obj.getExtIsBottom())){
	            	if(obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_1)||
			            	obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_2)||	
		        			obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_3)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_4)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_5)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_6)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_7)	
		            	){
							el.addAttribute("child", "0");
						}else{
							el.addAttribute("child", "1");
						}
	            }else{
	            	el.addAttribute("child", "0");
	            }
	            elx.addText(ctx+"/app/per/objList.act?nodeId="+ obj.getNodeId() + "&resType=" + resType);
	            Element elx1 = el.addElement("userdata");
	            elx1.addAttribute("name", "res");
	            elx1.addText(resType);
	        }
			return root.asXML();
		}		

	}	

	 /**
	* @Description
	* @Author：zhujiabiao
	* @Date 2013-8-12 下午08:34:27 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String getMenuDevTreeData(String objId, String ctx, String type,String resType) {
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");
			
			Map pMap = new HashMap();
			pMap.put("userid", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
			List<Objtank> list = this.findListBySql("PersonnelSQL.getTopStorey", pMap);
			
			for (Objtank obj : list) {
				Element el=root.addElement("item");
				el.addAttribute("open", "1");
				el.addAttribute("text", obj.getName());
				el.addAttribute("id", obj.getNodeId()+"");
				el.addAttribute("child", "1");	      
				Element elx = el.addElement("userdata");
				elx.addAttribute("name", "url");
				if("1".equals(obj.getExtIsBottom())){
					el.addAttribute("child", "1");
				}else{
					el.addAttribute("child", "0");
				}
				elx.addText(ctx+"/app/per/MenuList.act?nodeId="+ obj.getNodeId() + "&resType=" + resType);
			}
			
			return doc.asXML();
		} else {		
			String nodeId = objId;
			Objtank objt = (Objtank)this.findById(Objtank.class, new Long(nodeId));
			Element root= DocumentHelper.createElement("tree"); 
			
			root.addAttribute("id",nodeId+"");
			
			List<Objtank> list = this.findByHQL(" from Objtank o where o.extResType <> 'M' and PId=? order by o.extResType desc ,length(o.name), o.name asc", new Long(nodeId));
			
			for (Objtank obj : list) {
				Element el=root.addElement("item");   
				el.addAttribute("text", obj.getName());
				el.addAttribute("id", obj.getNodeId()+"");          
				if("1".equals(obj.getExtIsBottom())){
					if(obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_1)||
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_2)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_3)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_4)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_5)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_6)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_7)	
					){
						el.addAttribute("child", "0");
					}else{
						el.addAttribute("child", "1");
					}
				}else{
					el.addAttribute("child", "0");
				}	            
				Element elx = el.addElement("userdata");
				elx.addAttribute("name", "url");
				if("1".equals(obj.getExtIsBottom())){
					if(obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_1)||
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_2)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_3)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_4)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_5)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_6)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_7)	
					){
						el.addAttribute("child", "0");
					}else{
						el.addAttribute("child", "1");
					}
				}else{
					el.addAttribute("child", "0");
				}
				elx.addText(ctx+"/app/per/menuList.act?nodeId="+ obj.getNodeId() + "&resType=" + resType);
				Element elx1 = el.addElement("userdata");
				elx1.addAttribute("name", "res");
				elx1.addText(resType);
			}
			return root.asXML();
		}		
		
	}	
	 /**
	* @Description
	* @Author：zhujiabiao
	* @Date 2013-8-7 下午05:54:52 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String getObjAdminDevTreeData(String objId, String ctx, String type,String resType) {
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");
			
			Map pMap = new HashMap();
			pMap.put("userid", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
			List<Objtank> list = this.findListBySql("PersonnelSQL.getTopStorey", pMap);
			
			for (Objtank obj : list) {
				Element el=root.addElement("item");   
				el.addAttribute("text", obj.getName());
				el.addAttribute("id", obj.getNodeId()+"");
				el.addAttribute("child", "1");	      
				Element elx = el.addElement("userdata");
				elx.addAttribute("name", "url");
				if("1".equals(obj.getExtIsBottom())){
					el.addAttribute("child", "1");
				}else{
					el.addAttribute("child", "0");
				}
				elx.addText(ctx+"/app/per/objAdminList.act?nodeId="+ obj.getNodeId() + "&resType=" + resType);
			}
			
			return doc.asXML();
		} else {		
			String nodeId = objId;
			Objtank objt = (Objtank)this.findById(Objtank.class, new Long(nodeId));
			Element root= DocumentHelper.createElement("tree"); 
			
			root.addAttribute("id",nodeId+"");
			
			List<Objtank> list = this.findByHQL(" from Objtank o where o.extResType <> 'M' and PId=? order by o.extResType desc ,length(o.name), o.name asc", new Long(nodeId));
			
			for (Objtank obj : list) {
				Element el=root.addElement("item");   
				el.addAttribute("text", obj.getName());
				el.addAttribute("id", obj.getNodeId()+"");          
				if("1".equals(obj.getExtIsBottom())){
					if(obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_1)||
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_2)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_3)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_4)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_5)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_6)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_7)	
					){
						el.addAttribute("child", "0");
					}else{
						el.addAttribute("child", "1");
					}
				}else{
					el.addAttribute("child", "0");
				}	            
				Element elx = el.addElement("userdata");
				elx.addAttribute("name", "url");
				if("1".equals(obj.getExtIsBottom())){
					if(obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_1)||
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_2)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_3)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_4)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_5)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_6)||	
							obj.getExtResType().equals(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_7)	
					){
						el.addAttribute("child", "0");
					}else{
						el.addAttribute("child", "1");
					}
				}else{
					el.addAttribute("child", "0");
				}
				elx.addText(ctx+"/app/per/objAdminList.act?nodeId="+ obj.getNodeId() + "&resType=" + resType);
				Element elx1 = el.addElement("userdata");
				elx1.addAttribute("name", "res");
				elx1.addText(resType);
			}
			return root.asXML();
		}		
		
	}	
	/**
	 * 检查纳税人识别号码是否唯一.
	 *
	 * @return taxno在数据库中唯一或等于oldtaxno时返回true.
	 */
	public boolean isTaxnoUnique(String newtaxno, String oldtaxno) {
		return dao.isPropertyUnique(Objtank.class,"taxno", newtaxno, oldtaxno);
	}
	/**
	 * 
	 * @描述: TODO 修改对象资源树信息--邮箱，企业名，联系人，等几项。
	 * @param org
	 */
	public void updateOrg(Objtank org){
		if(org != null){
			dao.saveOrUpdate(org);
		}
	}

	public void insertMenuPermMap(List listOrg,List listUser){
		insertBatchBySql("PersonnelSQL.insertAdminUserRoot", listOrg);
		insertBatchBySql("PersonnelSQL.insertAdminUserRoot", listUser);
	}

	public List<Map> getDoorStoreList(){
		Map param = new HashMap();
		return this.findListBySql("PersonnelSQL.getDoorStoreList", param);
	}

	public Page<DoorControllerModel> getDoorControllerPage(Page pageDoorController,Map param){
		 Page page =  sqlDao.findPage(pageDoorController,"PersonnelSQL.getDoorControllerList", param);
		 List<Map<String,Object>> listMap = page.getResult();
		 List<DoorControllerModel> doorControllerModelList = new ArrayList<DoorControllerModel>();
		 for(Map<String,Object> m :listMap){
			 DoorControllerModel tempDoorControllerModel = new DoorControllerModel();
			 Long commId  = m.get("COMM_ID")==null?null:Long.valueOf(m.get("COMM_ID").toString());
			 String commIp = m.get("COMM_IP")==null?null:m.get("COMM_IP").toString();
			 String memo = m.get("MEMO")==null?null:m.get("MEMO").toString();
			 Long id = m.get("ID")==null?null:Long.valueOf(m.get("ID").toString());
			 tempDoorControllerModel.setCommId(commId);
			 tempDoorControllerModel.setCommIp(commIp);
			 tempDoorControllerModel.setMemo(memo);
			 tempDoorControllerModel.setId(id);
			 doorControllerModelList.add(tempDoorControllerModel);
		 }
		 page.setResult(doorControllerModelList);
		 return page;
		 
	}
	
	public DoorControllerModel getDoorControllerModelById(Long id){
		Map map = new HashMap();
		map.put("id", id);
		List list = sqlDao.findList("PersonnelSQL.getDoorControllerModelById", map);
		if(list!=null&&list.size()>0){
			Map<String,Object> m = new HashMap<String, Object>();
			m = (Map<String,Object>)list.get(0);
			DoorControllerModel tempDoorControllerModel = new DoorControllerModel();
			 Long commId  = m.get("COMM_ID")==null?null:Long.valueOf(m.get("COMM_ID").toString());
			 String commIp = m.get("COMM_IP")==null?null:m.get("COMM_IP").toString();
			 String memo = m.get("MEMO")==null?null:m.get("MEMO").toString();
			 Long cid = m.get("ID")==null?null:Long.valueOf(m.get("ID").toString());
			 String bak = m.get("BAK")==null?null:m.get("BAK").toString();
			 String comm_bak1 = m.get("COMM_BAK1")==null?null:m.get("COMM_BAK1").toString();
			 String comm_bak2 = m.get("COMM_BAK2")==null?null:m.get("COMM_BAK2").toString();
			 String comm_bak3 = m.get("COMM_BAK3")==null?null:m.get("COMM_BAK3").toString();
			 
			 tempDoorControllerModel.setCommId(commId);
			 tempDoorControllerModel.setCommIp(commIp);
			 tempDoorControllerModel.setMemo(memo);
			 tempDoorControllerModel.setId(cid);
			 tempDoorControllerModel.setBak(bak);
			 tempDoorControllerModel.setCommBak1(comm_bak1);
			 tempDoorControllerModel.setCommBak2(comm_bak2);
			 tempDoorControllerModel.setBak(comm_bak3);
			 
			return tempDoorControllerModel;
			
		}
		return null;
	}
	
	public void saveDoorControllerComm(Map<String,Object> map){
		//insertBySql("PersonnelSQL.insertDoorControllerComm", map);
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		listMap.add(map);
		insertBatchBySql("PersonnelSQL.insertDoorControllerComm",listMap);
	}

	public void updateDoorControllerComm(Map<String,Object> map){
		//insertBySql("PersonnelSQL.insertDoorControllerComm", map);
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		listMap.add(map);
		updateBatchBySql("PersonnelSQL.updateDoorControllerComm", listMap);
	}
	
	public Boolean checkCommId(Long commId){
		Map map = new HashMap();
		map.put("commid", commId);
		List list = sqlDao.findList("PersonnelSQL.getCommId", map);
		if(list==null||list.size()==0){
			return true;
		}else{
			return false;
		}
	}

	public Boolean checkCommIp(String commIp){
		Map map = new HashMap();
		map.put("commip", commIp);
		List list = sqlDao.findList("PersonnelSQL.getCommIp", map);
		if(list==null||list.size()==0){
			return true;
		}else{
			return false;
		}
	}
	
	
	public boolean isStoreCommExist(String store, String comm){
		Map param = new HashMap();
		param.put("store", store);
		param.put("comm", comm);
		List<Map> list = findListBySql("PersonnelSQL.countStoreComm", param);
		
		if(list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public long getInsertObjtankId(){
		Map param = new HashMap();
		List<Map> list = findListBySql("PersonnelSQL.getMaxIdOfObjtank", param);
		if(list != null && list.size() > 0){
			return Long.parseLong(list.get(0).get("ID").toString()) + 1;
		}else{
			return 0;
		}
	}
	
	public void insertDoorControl(long nodeId, String name, String store, String comm, String ip){
		Map param = new HashMap();
		param.put("nodeId", nodeId);
		param.put("name", name);
		param.put("store", store);
		param.put("comm", comm);
		param.put("ip", ip);
		List<Map> list = new ArrayList<Map>();
		list.add(param);
		insertBatchBySql("PersonnelSQL.insertDoorControl", list);		
	}
	
	/**
	 * 查询设备设施根据传入的类型
	 * @param nodeId
	 * @return
	 */
	public List<Objtank> getQueryLightListByType(Map param){
		//return dao.findPageBySQL(page, sql, "");
		return this.findListBySql("PersonnelSQL.queryLightListByType", param);
	}
	/**
	 * 查询设备设施根据传入的类型总数
	 * @param nodeId
	 * @return
	 */
	public List<Map> getQueryLightListCount(Map param){
		return this.findListBySql("PersonnelSQL.queryLightListCount", param);
	}
	
	/**
	 * 设备维护空调模块
	 * @param param
	 * @return
	 */
	public List<Objtank> getQueryAirCondListByType(Map param){
		//return dao.findPageBySQL(page, sql, "");
		return this.findListBySql("PersonnelSQL.queryAirCondListByType", param);
	}
	/**
	 * 设备维护空调模块
	 * @param param
	 * @return
	 */
	public List<Map> getQueryAirCondListCount(Map param){
		return this.findListBySql("PersonnelSQL.queryAirCondListCount", param);
	}
	
	/**
	 * 查询设备设施根据传入的类型门禁
	 * @param nodeId
	 * @return
	 */
	public List<Objtank> getQueryLockListByType(Map param){
		//return dao.findPageBySQL(page, sql, "");
		return this.findListBySql("PersonnelSQL.queryLockListByType", param);
	}
	/**
	 * 查询设备设施根据传入的类型总数门禁
	 * @param nodeId
	 * @return
	 */
	public List<Map> getQueryLockListCount(Map param){
		return this.findListBySql("PersonnelSQL.queryLockListCount", param);
	}
	
}
