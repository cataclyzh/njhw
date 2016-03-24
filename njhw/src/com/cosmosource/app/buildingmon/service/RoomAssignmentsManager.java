/**
* <p>文件名: HomeManager.java</p>
* <p>:描述：首页manager</p>
* <p>版权: Copyright (c) 2010 Beijing Holytax Co. Ltd.</p>
* <p>公司: Holytax Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2012-2-7 
* @作者： jtm
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
*/
package com.cosmosource.app.buildingmon.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.Objtank;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.StringUtil;

/**
 * @类描述:  
 * @作者： SQS
 */
public class RoomAssignmentsManager extends BaseManager {
	/** 
	* @title: 
	* @description: 查询楼宇列表数据
	* @author SQS
	* ${tags}
	* @date 2013-3-17   
	* @throws 
	*/ 
	@SuppressWarnings("unchecked")
	public Page<Objtank> getRoom(final Page<Objtank> page, final Map<String,Object> map){
		List<Object> list = new ArrayList<Object>();
		String hql=" select o from Objtank o where 1=1";
		if(map.get("name") !=""){
			hql+=" and o.name like ?";
			list.add("%"+map.get("name")+"%");
		}
		if(map.get("title") !=""){
			hql+=" and o.title like ?";
			list.add("%"+map.get("title")+"%");
		}
		if(map.get("keyword") !=""){
			hql+=" and o.keyword = ?";
			list.add(map.get("keyword"));
		}
		return super.dao.findPage(page, hql, list.toArray());
	}
	
	@SuppressWarnings("rawtypes")
	public Object findById(final Class entityClass,final Serializable id) {
		return dao.findById(entityClass, id);
	}
}