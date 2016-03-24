/**
* <p>文件名: RegisterkeyManager.java</p>
* <p>描述：注册码生成Manager</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2011-01-26 下午01:25:51 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.DesUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.common.entity.TAcRegistkey;

/**
 * @类描述:  注册码生成
 * 
 * @作者： WXJ
 */
@SuppressWarnings({"rawtypes" })
public class RegisterkeyManager  extends BaseManager{
	
	public void deletekey(TAcRegistkey registerkey) {
		List lregister = dao.findByExample(TAcRegistkey.class,registerkey);
		for (int i = 0; i < lregister.size(); i++) {
			TAcRegistkey tmp = (TAcRegistkey) lregister.get(i);
			dao.delete(tmp);
		}
	}
	
	public boolean isExist(TAcRegistkey registerkey){
		TAcRegistkey regkey = new TAcRegistkey();
		regkey.setTaxno(registerkey.getTaxno());
		regkey.setSys(registerkey.getSys());
		regkey.setUptomon(registerkey.getUptomon());//截止月份
		regkey.setValidflag("1");
		List list = dao.findByExample(TAcRegistkey.class,regkey);
		if (list.size()>0)
			return true;
		else
			return false;
	}
	
	public boolean isExistValid(TAcRegistkey registerkey){
		boolean result = false;
		TAcRegistkey regkey = new TAcRegistkey();
		regkey.setTaxno(registerkey.getTaxno());
		regkey.setSys(registerkey.getSys());
		regkey.setValidflag("1");
		List list = dao.findByExample(TAcRegistkey.class,regkey);
		for (int i=0;i<list.size();i++){
			regkey = (TAcRegistkey)list.get(i);
			if (Integer.parseInt(regkey.getUptomon())>Integer.parseInt(registerkey.getUptomon())){
				result = true;
				break;
			}
		}
		return result;	
	}
	/**
	 * @描述: 新增
	 * @作者：WXJ
	 * @日期：2011-1-26
	 * @param entity
	 */
	public void save(TAcRegistkey registerkey){
		String privateKey = null;
		if (registerkey.getSys().equals("2"))
			privateKey = "Cosmosourcecq000";
		if (registerkey.getSys().equals("1"))
			privateKey = "Cosmosourcesm000";
		if (registerkey.getSys().equals("3"))
			privateKey = "Cosmosourcekp000";
		String keycode = DesUtil.CRC32DES32(registerkey.getTaxno()+registerkey.getUptomon(),privateKey);
		registerkey.setRegistkey(keycode);
		registerkey.setOperdate(new Date());
		registerkey.setStartdate(DateUtil.date2Str(registerkey.getOperdate(), "yyyyMMdd"));
		registerkey.setValidflag("1");
		dao.save(registerkey);
	}
	/**
	 * @描述: 查询
	 * @作者：WXJ
	 * @日期：2011-1-26
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<TAcRegistkey> queryRegKeys(final Page<TAcRegistkey> page, final TAcRegistkey model) {
		List lPara = new ArrayList();
	
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select t from TAcRegistkey t ")
		.append("where 1=1 ");
		if(StringUtil.isNotBlank(model.getTaxno())){
			sbHql.append(" and t.taxno like ? ");
			lPara.add("%"+model.getTaxno()+"%");
		}
		if(StringUtil.isNotBlank(model.getTaxname())){
			sbHql.append("and t.taxname like ? ");
			lPara.add("%"+model.getTaxname()+"%");
		}
		if(StringUtil.isNotBlank(model.getSys())){
			sbHql.append("and t.sys = ? ");
			lPara.add(model.getSys());
		}
		if(StringUtil.isNotBlank(model.getUptomon())){
			sbHql.append("and t.uptomon = ? ");
			lPara.add(model.getUptomon());
		}
		sbHql.append(" order by t.regid ");

		return dao.findPage(page,sbHql.toString(),lPara.toArray());
	}

	
	/**
	 * @描述: 删除
	 * @作者：WXJ
	 * @日期：2011-1-26
	 * @param ids　选中的ＩＤ数组
	 */
	public void delete(String[] ids){
		dao.deleteByIds(TAcRegistkey.class, ids);
	}
}