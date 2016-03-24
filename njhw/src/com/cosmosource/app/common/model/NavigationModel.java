/**
* <p>文件名: NavigationModel.java</p>
* <p>:描述：首页导航信息</p>
* <p>版权: Copyright (c) 2010 Beijing Holytax Co. Ltd.</p>
* <p>公司: Holytax Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2012-2-7 
* @作者： jtm
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
*/
package com.cosmosource.app.common.model;
/**
 * 首页导航信息model
 * @作者 jtm
 *
 */
public class NavigationModel {
	//导航名称
	private String navigationName;
	//导航链接地址，可作为权限判断
	private String navigationLink;
	//导航图片源
	private String navigationImgSrc;
	//属于子系统编号 与数据库对应 T_AC_SUBSYS
	private String subsysid;
	//属于子系统名称 与数据库对应 T_AC_SUBSYS
	private String subsysname;
	//上级菜单名称 与数据库 T_AC_MENU 父记录
	private String accordionname;
	
	public String getNavigationName() {
		return navigationName;
	}

	public void setNavigationName(String navigationName) {
		this.navigationName = navigationName;
	}

	public String getNavigationLink() {
		return navigationLink;
	}

	public void setNavigationLink(String navigationLink) {
		this.navigationLink = navigationLink;
	}

	public String getNavigationImgSrc() {
		return navigationImgSrc;
	}

	public void setNavigationImgSrc(String navigationImgSrc) {
		this.navigationImgSrc = navigationImgSrc;
	}

	public String getSubsysid() {
		return subsysid;
	}

	public void setSubsysid(String subsysid) {
		this.subsysid = subsysid;
	}

	public String getSubsysname() {
		return subsysname;
	}

	public void setSubsysname(String subsysname) {
		this.subsysname = subsysname;
	}

	public String getAccordionname() {
		return accordionname;
	}

	public void setAccordionname(String accordionname) {
		this.accordionname = accordionname;
	}

	
}
