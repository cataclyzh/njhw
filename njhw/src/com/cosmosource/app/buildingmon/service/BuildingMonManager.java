package com.cosmosource.app.buildingmon.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.port.serviceimpl.DevicePermissionToAppService;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
/**
* @description: 楼宇监控公用manager(摄像头监控)
* @author herb
* @date Mar 26, 2013 6:53:06 PM
 */
public class BuildingMonManager extends BaseManager {
	
	private DevicePermissionToAppService devicePermissionToApp;
	
	/**
	 * 查询对象资源列表数据（电梯）
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap<String, Object>> queryObjtank(final Page<HashMap<String, Object>> page, final Objtank obj) {
		Map map = new HashMap();
		map.put("pid", obj.getNodeId());
		map.put("extResType", Objtank.EXT_RES_TYPE_7);
		Objtank objtank = (Objtank)dao.findById(Objtank.class, obj.getNodeId());
		map.put("floor", objtank.getExtResType());
		return sqlDao.findPage(page, "BuildingmonSQL.dtQuery", map);
	}
	
	/**
	 * @描述: 生成xml数据
	 * @param nodeId
	 * @param orgid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getOrgTreeData(String orgid, String ctx, String type,String floor,String actionName) {
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");
			List<Objtank> list = dao.findByHQL("select t from Objtank t where t.nodeId = ?", Long.parseLong(orgid));
			Objtank org = (Objtank) list.get(0);
			Element el = root.addElement("item");
			el.addAttribute("text", org.getTitle());
			el.addAttribute("res", org.getExtResType());
			el.addAttribute("id", org.getNodeId() + "");
			el.addAttribute("child", "1");
			Element elx = el.addElement("userdata");
			elx.addAttribute("name", "url");
			elx.addText(ctx + "/app/buildingmon/"+actionName+".act?nodeId=" + org.getNodeId());
			return doc.asXML();
		} else {
			Element root= DocumentHelper.createElement("tree"); 
			root.addAttribute("id",orgid);
			List<Objtank> list = null;
			if (floor.equals("F")){
				list = dao.findByHQL("select t from Objtank t where t.PId=? and (t.extResType like ? or t.extResType like ?) order by keyword ", Long.parseLong(orgid),"%B%","%F%");
			} else if (floor.equals("R")){
				list =   dao.findByHQL("select t from Objtank t where t.PId=? order by keyword ", Long.parseLong(orgid));
			}else {
				list =   dao.findByHQL("select t from Objtank t where t.PId=? and t.extResType like ? order by keyword ", Long.parseLong(orgid),"%B%");
			}
			if(list.size()<=200){		
				for (Objtank org : list) {
		            Element el=root.addElement("item");   
		            el.addAttribute("text", org.getTitle());
		            el.addAttribute("id", org.getNodeId()+"");
		            Element elx = el.addElement("userdata");
		            el.addAttribute("res", org.getExtResType());
		            elx.addAttribute("name", "url");
		            
		            if("1".equals(org.getExtIsBottom())){
		            	el.addAttribute("child", "1");
		            }else{
		            	el.addAttribute("child", "0");
		            }

		            if(floor.equals(Objtank.EXT_RES_TYPE_F)){
		            	if (org.getExtResType().equals(Objtank.EXT_RES_TYPE_F)){
		            		el.addAttribute("child", "0");
		            	}
		            } else if (floor.equals(Objtank.EXT_RES_TYPE_R)){
		            	if (org.getExtResType().equals(Objtank.EXT_RES_TYPE_R)){
		            		el.addAttribute("child", "0");
		            	}
		            }else if (floor.equals(Objtank.EXT_RES_TYPE_B)){
		            	if (org.getExtResType().equals(Objtank.EXT_RES_TYPE_B)){
		            		el.addAttribute("child", "0");
		            	}
		            }
		            
		            elx.addText(ctx+"/app/buildingmon/"+actionName+".act?nodeId="+ org.getNodeId());
		        }
			}
			return root.asXML();
		}
		
	}
	
	/**
	 * 
	* @title: getLevelSXTPageList 
	* @description: 分级查询摄像头列表，带参数
	* @author herb
	* @param page
	* @param entity
	* @return
	* @date Mar 29, 2013 2:25:23 PM     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<Map> getLevelSXTPageList(Page<Map> page, String nodeId) {
		HashMap map = new HashMap();
		map.put("pid", nodeId);
		map.put("SearchExtResType", Objtank.EXT_RES_TYPE_4);
		// 判断当前节点是哪级
		Objtank objtank = (Objtank)dao.findById(Objtank.class, Long.valueOf(nodeId));
		map.put("extResType", objtank.getExtResType());
		return sqlDao.findPage(page, "BuildingmonSQL.getLevelSXTPageList", map);
	}
	
	
	/**
	 * 
	* @title: getPermissionObjTankPageList 
	* @description: 通过接口 查询所有有权限的用户智能设备列表
	* @author herb
	* @param page
	* @param nodeId
	* @return
	* @date Apr 1, 2013 6:28:44 PM     
	* @throws
	 */
	public  Page<Objtank> getPermissionObjTankPageList(Page<Objtank> page,String extType,long pid, String userId){
		Page<Objtank> siteList = devicePermissionToApp.getAuthSiteCollection(page, extType, pid, userId);
		return siteList;
	}

	public DevicePermissionToAppService getDevicePermissionToApp() {
		return devicePermissionToApp;
	}

	public void setDevicePermissionToApp(
			DevicePermissionToAppService devicePermissionToApp) {
		this.devicePermissionToApp = devicePermissionToApp;
	}
}
