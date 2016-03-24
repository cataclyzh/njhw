package com.cosmosource.common.service;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据权限上下文
 * @author WXJ
 *
 */
public class RestrictDataContext {
	
	/**
	 * 动态数据限制SQL
	 */
	public static final String RESTRICT_DATA_SQL = "restrictDataSql";
	
	/**
	 * 用户身份
	 */
	public static final String PRINCIPLE_USER = "1";
	
	/**
	 * 机构身份
	 */
	public static final String PRINCIPLE_ORG = "2";
	
	/**
	 * 角色身份
	 */
	public static final String PRINCIPLE_ROLE = "3";
	
	/**
	 * 数据限制类型为购方税号
	 */
	public static final String DATA_TYPE_BUY_TAXNO = "1";
	
	/**
	 * 数据限制类型为销方税号
	 */
	public static final String DATA_TYPE_SELL_TAXNO = "2";
	
	/**
	 * 公司编码-海尔
	 */
	public static final String COMPANY_HAIER = "haier";
	
	/**
	 * 公司编码-宏图三胞
	 */
	public static final String COMPANY_HISAP = "hisap";
	
	/**
	 * 公司编码-卜蜂莲花
	 */
	public static final String COMPANY_LOTUS = "lotus";
	
	/**
	 * 公司编码-欧尚
	 */
	public static final String COMPANY_AUCHAN = "auchan";
	
	/**
	 * 公司编码-乐购
	 */
	public static final String COMPANY_TESCO = "tesco";

	private List<RestrictDataCfg> restrictDataCfgList = new ArrayList<RestrictDataCfg>();
	
	public String makeRestrictDataSql(){
		StringBuilder sql = new StringBuilder();
		for(int i = 0; i < restrictDataCfgList.size(); i++){
			RestrictDataCfg dataCfg = restrictDataCfgList.get(i);
			if(i > 0){
				sql.append(" AND ");
			}
			sql.append(" EXISTS(SELECT * FROM T_COMMON_RESTRICT_DATA RD_ ");
			sql.append(" WHERE RD_.COMPANY = '" + dataCfg.getCompany()).append("'")
			        .append(" AND RD_.DATA_VALUE = " + dataCfg.getRestrictColumn())
			        .append(" AND RD_.DATA_TYPE = '").append(dataCfg.getDataType()).append("'");
			int principleSize = dataCfg.getPrincipleList().size();
			if(principleSize > 0){
				sql.append(" AND (");
				for(int j = 0; j < principleSize; j++){
					RestrictDataPrinciple principle = dataCfg.getPrincipleList().get(j);
					if(j > 0){
						sql.append(" OR ");
					}
					sql.append(" (RD_.SID = '" + principle.getSid() +"'")
					        .append(" AND RD_.SID_TYPE = '" + principle.getSidType() + "')");
				}
				sql.append(")");
			}
			sql.append(")");
		}
		
		return sql.toString();
	}
	
	public void addRestrictDataCfg(RestrictDataCfg dataCfg){
		restrictDataCfgList.add(dataCfg);
	}
	
	public void removeRestrictDataCfg(RestrictDataCfg dataCfg){
		restrictDataCfgList.remove(dataCfg);
	}

	public List<RestrictDataCfg> getRestrictDataCfgList() {
		return restrictDataCfgList;
	}

	public void setRestrictDataCfgList(List<RestrictDataCfg> restrictDataCfgList) {
		this.restrictDataCfgList = restrictDataCfgList;
	}
	
}
