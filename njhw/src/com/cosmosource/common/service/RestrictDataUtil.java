package com.cosmosource.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 数据权限工具类
 * @author WXJ
 *
 */
public class RestrictDataUtil {
	
	private static Logger logger = LoggerFactory.getLogger(RestrictDataUtil.class);
	
	/**
	 * 动态生成允许访问数据SQL
	 * @param sid 身份id
	 * @param sidType 身份类型
	 * @param dataType 数据类型
	 * @param restrictColumn 限制字段
	 * @return
	 */
	public static String makeRestrictDataSql(String sid, String sidType, String dataType, String restrictColumn, String company){
		RestrictDataContext context = new RestrictDataContext();
		RestrictDataCfg dataCfg = new RestrictDataCfg();
		dataCfg.addRestrictDataPrinciple(sid, sidType);
		dataCfg.setDataType(dataType);
		dataCfg.setRestrictColumn(restrictColumn);
		dataCfg.setCompany(company);
		context.addRestrictDataCfg(dataCfg);

        String restrictDataSql = context.makeRestrictDataSql();
        if(logger.isDebugEnabled()){
    		logger.debug("数据权限动态SQL:" + restrictDataSql);
        }
		
		return restrictDataSql;
	}
	
	/**
	 * 动态生成用户允许访问数据SQL
	 * @param dataType 数据类型
	 * @param restrictColumn 限制字段
	 * @return
	 */
	public static String makeUserRestrictDataSql(String userid, String dataType, String restrictColumn, String company){
		Assert.notNull(userid, "用户id不能为null");
		String principleType = RestrictDataContext.PRINCIPLE_USER;
		
		return makeRestrictDataSql(userid, principleType, dataType, restrictColumn, company);
	}

	/**
	 * 动态生成机构允许访问数据SQL
	 * @param dataType 数据类型
	 * @param restrictColumn 限制字段
	 * @param company 公司编号
	 * @return
	 */
	public static String makeOrgRestrictDataSql(String orgid, String dataType, String restrictColumn, String company){
		Assert.notNull(orgid, "机构id不能为null");
		String principleType = RestrictDataContext.PRINCIPLE_ORG;
		
		return makeRestrictDataSql(orgid, principleType, dataType, restrictColumn, company);
	}

	/**
	 * 动态生成角色允许访问数据SQL
	 * @param dataType 数据类型
	 * @param restrictColumn 限制字段
	 * @param company 公司编号
	 * @return
	 */
	public static String makeRoleRestrictDataSql(String roleid, String dataType, String restrictColumn, String company){
		Assert.notNull(roleid, "角色id不能为null");
		String principleType = RestrictDataContext.PRINCIPLE_ROLE;
		
		return makeRestrictDataSql(roleid, principleType, dataType, restrictColumn, company);
	}
	
	/**
	 * 动态生成允许访问购方税号SQL
	 * @param sid 身份id
	 * @param sidType 身份类型
	 * @param company 公司编号
	 * @return
	 */
	public static String makeBuyTaxnoRestrictDataSql(String sid, String sidType, String company){
		String dataType = RestrictDataContext.DATA_TYPE_BUY_TAXNO;
		String restrictColumn = "BUYTAXNO";

		return makeRestrictDataSql(sid, sidType, dataType, restrictColumn, company);
	}
	
	/**
	 * 动态生成允许访问销方税号SQL
	 * @param sid 身份id
	 * @param sidType 身份类型
	 * @param company 公司编号
	 * @return
	 */
	public static String makeSellTaxnoRestrictDataSql(String sid, String sidType, String company){
		String dataType = RestrictDataContext.DATA_TYPE_SELL_TAXNO;
		String restrictColumn = "SELLTAXNO";

		return makeRestrictDataSql(sid, sidType, dataType, restrictColumn, company);
	}
	
	/**
	 * 动态生成用户允许访问购方税号SQL
	 * @param company 公司编号
	 * @return
	 */
	public static String makeBuyTaxnoUserRestrictDataSql(String userid, String company){
		Assert.notNull(userid, "用户id不能为null");
		String principleType = RestrictDataContext.PRINCIPLE_USER;

		return makeBuyTaxnoRestrictDataSql(userid, principleType, company);
	}
	
	/**
	 * 动态生成机构允许访问购方税号SQL
	 * @param company 公司编号
	 * @return
	 */
	public static String makeBuyTaxnoOrgRestrictDataSql(String orgid, String company){
		Assert.notNull(orgid, "机构id不能为null");
		String principleType = RestrictDataContext.PRINCIPLE_ORG;

		return makeBuyTaxnoRestrictDataSql(orgid, principleType, company);
	}
	
	/**
	 * 动态生成角色允许访问购方税号SQL
	 * @param company 公司编号
	 * @return
	 */
	public static String makeBuyTaxnoRoleRestrictDataSql(String roleid, String company){
		Assert.notNull(roleid, "角色id不能为null");
		String principleType = RestrictDataContext.PRINCIPLE_ROLE;

		return makeBuyTaxnoRestrictDataSql(roleid, principleType, company);
	}

	/**
	 * 动态生成用户允许访问销方税号SQL
	 * @param company 公司编号
	 * @return
	 */
	public static String makeSellTaxnoUserRestrictDataSql(String userid, String company){
		String principleType = RestrictDataContext.PRINCIPLE_USER;

		return makeSellTaxnoRestrictDataSql(userid, principleType, company);
	}
	
	/**
	 * 动态生成机构允许访问销方税号SQL
	 * @param company 公司编号
	 * @return
	 */
	public static String makeSellTaxnoOrgRestrictDataSql(String orgid, String company){
		Assert.notNull(orgid, "机构id不能为null");
		String principleType = RestrictDataContext.PRINCIPLE_ORG;

		return makeSellTaxnoRestrictDataSql(orgid, principleType, company);
	}
	
	/**
	 * 动态生成角色允许访问销方税号SQL
	 * @param company 公司编号
	 * @return
	 */
	public static String makeSellTaxnoRoleRestrictDataSql(String roleid, String company){
		Assert.notNull(roleid, "角色id不能为null");
		String principleType = RestrictDataContext.PRINCIPLE_ROLE;

		return makeSellTaxnoRestrictDataSql(roleid, principleType, company);
	}
}
