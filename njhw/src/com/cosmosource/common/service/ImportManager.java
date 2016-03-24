/**
* <p>文件名: ImportManager.java</p>
* <p>:描述：(用一句话描述该文件做什么)</p>
* <p>版权: Copyright (c) 2010 Beijing Holytax Co. Ltd.</p>
* <p>公司: Holytax Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2012-4-25 下午01:32:52 
* @作者： hp
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/

package com.cosmosource.common.service;

import java.util.List;

import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.Users;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.common.model.UserModel;

/**
 * @类描述: 从xml中得到类的集合然后用hibernate的save方法持久化对象
 * @作者： hp 
 */
public class ImportManager extends BaseManager {

	/**
	 * 
	* @title: save 
	* @description: 针对多表的保存
	* @author cjw
	* @param list
	* @throws Exception
	* @date 2013-4-9 下午11:25:54     
	* @throws
	 */
	public void save(List<Object> list) throws Exception{
			if(list != null){
				for (int i = 0; i < list.size(); i++) {
					UserModel um = (UserModel) list.get(i);
					
					Users us = new Users();
					NjhwTscard njhw = new NjhwTscard();
					NjhwUsersExp ne = new NjhwUsersExp();
					
					us.setIsSystem(0l);
					us.setChangeLoginPwdFlag(0l);
					us.setDisplayName(um.getRealName());
					us.setLoginUid(um.getLoginName());
					us.setOrgId(um.getOrgId());
					us.setValidity(-1l);
					us.setActiveFlag(1l);
					us.setCreator(1l);
					us.setLastUpdateBy(1l);
					dao.save(us);
					
					njhw.setCardId(um.getCityCard());
					njhw.setUserId(us.getUserid());
					njhw.setCardType("1");
					dao.save(njhw);
					
					if(um.getSex().equals("男")){
						ne.setUepSex("1");
					}else{
						ne.setUepSex("0");
					}
					ne.setUepMobile(um.getPhone());
					ne.setUepMail(um.getEmail());
					ne.setUserid(us.getUserid());
					
					dao.save(ne);
				}
			}
		
	}
	
}
