package com.cosmosource.common.security;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.cosmosource.app.entity.Users;
import com.cosmosource.common.service.AuthorityManager;
import com.google.common.collect.Sets;


/**
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函数.
 * 
 * @author WXJ
 */
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	private AuthorityManager authorityManager;

	public void setAuthorityManager(AuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
	}

	/**
	 * 获取用户Details信息的回调函数.
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException,DisabledException,DataAccessException {
		
		Users user = authorityManager.findUserByLoginName(username);
		if (user == null) {
			logger.info("用户" + username + " 不存在");
			throw new UsernameNotFoundException("用户" + username + " 不存在");
		}
		//用户账户是否停用
		Long activeFlag = user.getActiveFlag();
		if(activeFlag == 0){
			logger.info("用户" + username + " 已经停用");
			throw new DisabledException("用户" + username + " 已经停用");
		}
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		Set<GrantedAuthority> grantedAuths = obtainGrantedAuthorities(user);

		UserDetails userdetails = new org.springframework.security.core.userdetails.User(user.getLoginUid(), user.getLoginPwd(),
				 				enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);

		return userdetails;
	}

	/**
	 * 获得用户所有角色的权限集合.
	 */
	private Set<GrantedAuthority> obtainGrantedAuthorities(Users user) {
		//authorityManager.initUser(user);
		Set<GrantedAuthority> authSet = Sets.newHashSet();
//		for (TAcRole role : authorityManager.getRoleList(user)) {
			//authSet.add(new GrantedAuthorityImpl("ROLE_" + role.getRolename()));
//			authSet.add(new GrantedAuthorityImpl(role.getRoleid().toString()));
//		}
		authSet.add(new GrantedAuthorityImpl("ROLE_PASS"));
		return authSet;
	}


}
