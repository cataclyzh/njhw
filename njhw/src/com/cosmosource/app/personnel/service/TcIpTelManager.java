package com.cosmosource.app.personnel.service;

import java.util.ArrayList;
import java.util.List;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.TcIpTel;
import com.cosmosource.app.entity.Users;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.StringUtil;

public class TcIpTelManager extends BaseManager {
	
	/**
	 * 
	* @title: saveUpdateIpTel 
	* @description: 保存或修改ip电话信息
	* @author cjw
	* @param obj
	* @date 2013-3-23 下午09:05:42     
	* @throws
	 */
	public void saveUpdateIpTel(Object obj){
		super.dao.saveOrUpdate(obj);
	}
	
	/**
	 * 
	* @title: getTcIpTels 
	* @description: 通过电话号码和mac地址查询ip电话
	* @author cjw
	* @param page
	* @param obj
	* @return
	* @date 2013-3-23 下午09:23:45     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<TcIpTel> getTcIpTels(Page<TcIpTel> page, TcIpTel obj){
		System.err.println(obj.getTelMac());
		List<String> list = new ArrayList<String>();
		String hql=" select tit from TcIpTel tit where 1=1";
		if(StringUtil.isNotBlank(obj.getTelNum())){
			hql+=" and tit.telNum like ?";
			list.add("%"+obj.getTelNum()+"%");
		}
		if(StringUtil.isNotBlank(obj.getTelMac())){
			hql+=" and tit.telMac like ?";
			list.add("%"+obj.getTelMac()+"%");
		}
		if(StringUtil.isNotBlank(obj.getTelType())){
			hql+=" and tit.telType = ?";
			list.add(obj.getTelType());
		}
		hql+=" order by tit.telNum desc";
		return super.dao.findPage(page, hql, list.toArray());
	}

	/**
	 * 
	* @title: deleteUsers 
	* @description: 通过主键批量删除
	* @author cjw
	* @param ids
	* @date 2013-3-20 下午06:15:25     
	* @throws
	 */
	public String deleteTcIpTels(String[] ids){
		String telNums = "";
		
		for (String id : ids) {
			List<EmOrgRes> lm = dao.findByProperty(EmOrgRes.class, "resId", Long.parseLong(id));
			List<Users> lu = dao.findByHQL("select u from Users u, NjhwUsersExp uep, TcIpTel t where u.userid = uep.userid and" + 
					" (uep.telNum = t.telId or uep.uepFax = t.telId or uep.webFax = t.telId) and t.telId = ?", Long.parseLong(id));
			
			if ((lm != null && lm.size() > 0) || (lu != null && lu.size() > 0)) {
				TcIpTel tel = (TcIpTel) dao.findById(TcIpTel.class, Long.parseLong(id));
				telNums += tel.getTelNum() + ",";
			} else {
				super.dao.deleteById(TcIpTel.class, Long.parseLong(id));
			}
		}
		
		return StringUtil.isNotBlank(telNums) ? StringUtil.chop(telNums) : "";
	}

	/**
	 * @description:检查电话号码是否已经存在
	 * @author hj
	 * @param
	 * @return
	 * @date 2013-8-16 
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean checkTelNum(String telId, String telNum) {
		boolean res = false;

		List<Users> users = new ArrayList<Users>();
		if (StringUtil.isNotBlank(telId)) {
			users = dao.findByHQL(
					" select t  from TcIpTel t where t.telId !=? and t.telNum=?",
					Long.valueOf(telId), telNum);
		} else {
			users = dao.findByHQL(
					" select t  from TcIpTel t where t.telNum=?", telNum);
		}
		
		if (users.size() < 1) {
			res = true;
		}
		return res;
	}
	
	/**
	 * @description:检查电话MAC是否已经存在
	 * @author hj
	 * @param
	 * @return
	 * @date 2013-8-16
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean checkTelMac(String telId, String telMac) {
		boolean res = false;

		List<Users> users = new ArrayList<Users>();
		if (StringUtil.isNotBlank(telId)) {
			users = dao.findByHQL(
					" select t  from TcIpTel t where t.telId !=? and t.telMac=?",
					Long.valueOf(telId), telMac);
		} else {
			users = dao.findByHQL(
					" select t  from TcIpTel t where t.telMac=?", telMac);
		}
		
		if (users.size() < 1) {
			res = true;
		}
		return res;
	}
	
	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public Object findUnique(final String hql, final Object... values) {
		return super.dao.findUnique(hql, values);
	}
}
