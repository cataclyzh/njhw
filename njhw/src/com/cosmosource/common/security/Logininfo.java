package com.cosmosource.common.security;

import java.util.List;

import com.cosmosource.common.entity.TAcMenu;
import com.cosmosource.common.entity.TAcOrg;
import com.cosmosource.common.entity.TAcRole;
import com.cosmosource.common.entity.TAcUser;


public class Logininfo implements java.io.Serializable {

	    
	private static final long serialVersionUID = 1L;

	private Long userid;

	private String nsrsbh;

	private String username;
	
	private String loginname;
	
	private String nsrname;

	private String userpwd;
	
	private List<TAcRole> userrole;
	
	private List<TAcMenu> usermenu;
	
	private TAcUser user;
	
	private TAcOrg org;
	
	public Logininfo() {
	}


	public Logininfo(String nsrsbh, String username, String nsrname, String userpwd, List<TAcRole> userrole,List<TAcMenu> usermenu) {
		this.nsrsbh = nsrsbh;
		this.username = username;
		this.nsrname = nsrname;
		this.userpwd = userpwd;
		this.userrole = userrole;
		this.usermenu = usermenu;
	}


	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getNsrsbh() {
		return this.nsrsbh;
	}

	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpwd() {
		return this.userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public String getNsrname() {
		return nsrname;
	}

	public void setNsrname(String nsrname) {
		this.nsrname = nsrname;
	}
	public String getLoginname() {
		return loginname;
	}


	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}


	public List<TAcRole> getUserrole() {
		return userrole;
	}


	public void setUserrole(List<TAcRole> userrole) {
		this.userrole = userrole;
	}


	public List<TAcMenu> getUsermenu() {
		return usermenu;
	}


	public void setUsermenu(List<TAcMenu> usermenu) {
		this.usermenu = usermenu;
	}


	public TAcUser getUser() {
		return user;
	}


	public void setUser(TAcUser user) {
		this.user = user;
	}


	public TAcOrg getOrg() {
		return org;
	}


	public void setOrg(TAcOrg org) {
		this.org = org;
	}



}