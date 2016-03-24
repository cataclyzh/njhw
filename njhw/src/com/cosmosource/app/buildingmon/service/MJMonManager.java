package com.cosmosource.app.buildingmon.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.Objtank;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

public class MJMonManager extends BaseManager {
	
	/**
	 * 查询对象资源树门禁列表数据
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> queryObjtank(final Page<HashMap> page, final Objtank obj) {
		HashMap map = new HashMap();
		map.put("keyword", obj.getKeyword());
		map.put("name", obj.getName());
		map.put("title", obj.getTitle());
		map.put("pid", obj.getNodeId());
		map.put("SearchExtResType", Objtank.EXT_RES_TYPE_2);
		// 判断当前节点是哪级
		Objtank objtank = (Objtank)dao.findById(Objtank.class, obj.getNodeId());
		map.put("extResType", objtank.getExtResType());
		return sqlDao.findPage(page, "BuildingmonSQL.mjQuery", map);
	}
}
