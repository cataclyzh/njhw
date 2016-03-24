/**
* <p>文件名: ComManager.java</p>
* <p>描述：公共的管理类Manager</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-11-08 下午01:25:51 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.common.entity.TAcOrg;
import com.cosmosource.common.entity.TAcUser;

/**
 * @类描述:  公共的管理类
 * 主要提供通用的查询方法
 * 
 * @作者： WXJ
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ComManager extends BaseManager{
	
	/**
	 * @描述: 查询某机构下所有购方的纳税人识别号
	 * @param orgid
	 * @param company
	 * @return
	 */
	public List findBuytaxnos(Long orgid,String company){
		List lTaxno  = new ArrayList();
		TAcOrg org = (TAcOrg)dao.findById(TAcOrg.class, orgid);
		if(org.getTaxno()!=null){
			lTaxno.add(org.getTaxno());
		}
		String  treelayer = org.getTreelayer();
		StringBuilder sb = new StringBuilder();
		sb.append("select t from TAcOrg t ")
			.append("where t.treelayer like '"+treelayer+"%' ")
			.append("t.company = ?");
		List list = dao.findByHQL(sb.toString(),company);
		
		for(int i=0;i<list.size();i++){
			org = (TAcOrg)list.get(i);
			if(org.getTaxno()!=null){
				lTaxno.add(org.getTaxno());
			}
		}
		return lTaxno;
	}
	/**
	 * @描述: 查询某机构下所有购方的纳税人识别号，包括自己的纳税人识别号
	 * @作者：WXJ
	 * @日期：2010-12-13
	 * @param orgid　机构ＩＤ
	 * @return
	 */
	public List findBuytaxnos(Long orgid){
		List lTaxno  = new ArrayList();
		TAcOrg org = (TAcOrg)dao.findById(TAcOrg.class, orgid);
		if(org.getTaxno()!=null){
			lTaxno.add(org.getTaxno());
		}
		String  treelayer = org.getTreelayer();
		StringBuilder sb = new StringBuilder();
		sb.append("select t from TAcOrg t ")
			.append("where t.treelayer like '"+treelayer+"%' and t.orgtype='5' ");
		List list = dao.findByHQL(sb.toString());
		
		for(int i=0;i<list.size();i++){
			org = (TAcOrg)list.get(i);
			if(org.getTaxno()!=null){
				lTaxno.add(org.getTaxno());
			}
		}
		return lTaxno;
	}
	
	
	/**
	 * @描述: 用户是否有效
	 * @作者：WXJ
	 * @日期：2010-12-13
	 * @param loginname 登录名
	 * @param pwd 	密码
	 * @param encrypt 	加密码类型
	 * @return int 0-有效；1-无此用户；  2-密码错误 (用户过期)
	 */
	public int validateUser(String loginname,String pwd,String encrypt){
		TAcUser user = null;
		try {
			List list = dao.findByProperty(TAcUser.class, "loginname", loginname);
			if(list!=null&&list.size()>0){
				user = (TAcUser)list.get(0);
			}else{
				return 1;//无此用户 
			}
			//不加密
			if(encrypt==null||"text".equalsIgnoreCase(encrypt)){
				if(!pwd.equals(user.getPassword())){
					return 2;
				}
			}else if("md5".equalsIgnoreCase(encrypt)){
				if(!DigestUtils.md5Hex(pwd).equals(user.getPassword())){// 
					return 2;
				}
			}

		} catch (Exception e) {
			return 99; 
		}
		return 0; 
	}
	
	
	/**
	 * @描述: 通过登录名查询用户信息
	 * @作者：WXJ
	 * @日期：2010-12-13
	 * @param loginname 登录名
	 * @return 用户信息
	 */
	public TAcUser findUserByLoginname(String loginname){
		try {
			List list = dao.findByProperty(TAcUser.class, "loginname", loginname);
			if(list!=null&&list.size()>0){
				return (TAcUser)list.get(0);
			}else{
				return null;//无此用户 
			}
		} catch (Exception e) {
			return null; 
		}
	}
	
	/**
	 * @描述:根据登录名查询机构信息
	 * @作者：WXJ
	 * @日期：2010-12-13
	 * @param loginname 登录名
	 * @param pwd 	密码
	 * @param encrypt 	加密码类型
	 * @return TAcOrg 机构实体
	 */
	public TAcOrg findOrgByLoginname(String loginname){
		TAcUser user = null;
		try {
			List list = dao.findByProperty(TAcUser.class, "loginname", loginname);
			if(list!=null&&list.size()>0){
				user = (TAcUser)list.get(0);
			}else{
				return null;
			}
			return (TAcOrg)dao.findById(TAcOrg.class, user.getOrgid());
		} catch (Exception e) {
			return null; 
		}
	}
	
	
	/**
	 * 
	 * @描述: 根据纳税人识别号与company查 纳税人名称
	 * @param taxno
	 * @param company
	 * @return
	 * add by sjy 
	 * 2010-12-3
	 */
	public String findNsrnameBytaxnocompany(String taxno,String company){
		String nsrName = "";
		String sql = "select t.taxname from TAcOrg t where t.taxno=? and t.company=?";
		List list = dao.findByHQL(sql, taxno, company);
		if(list!=null){
			if(list.size()>0){
				nsrName = (String)list.get(0);
			}			
		}
		return nsrName; 
	}
	
	
	/**
	 * 
	 * @描述: 根据纳税人识别号查 纳税人机构对象
	 * @param taxno
	 * @return
	 * add by songquan 
	 * 2011-11-24
	 */
	public TAcOrg findOrgBytaxno(String taxno,String company){
		String sql = "select t from TAcOrg t where t.taxno=? and t.company=?";
		List list = dao.findByHQL(sql, taxno, company);
		if(list!=null && list.size()>0){
			return (TAcOrg) list.get(0);		
		}
		return null; 
	}
	/**
	 * @描述: 查询某机构下所有购方
	 * @作者：WXJ
	 * @日期：2010-12-13
	 * @param company　
	 * @return
	 */
	public List findBuytaxnos(String company){

		StringBuilder sb = new StringBuilder();
		sb.append("select t from TAcOrg t ")
			.append("where t.company = '"+company+"' ")
			.append("and (t.orgtype='5' or t.orgtype='6') ");

		return dao.findByHQL(sb.toString());
	}
	/**
	 * @描述: 取数据库时间
	 * @作者：WXJ
	 * @日期：2012-7-29
	 * @param company　
	 * @return
	 */
	public Timestamp getDBTime() {
		return dao.getDBTime();
	}
}
