package com.cosmosource.app.ldap.wsClient;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import com.cosmosource.app.ldap.model.UserInfo;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.1.3-hudson-390-
 * Generated source version: 2.0
 * 
 */
@WebService(name = "SynUserDelegate", targetNamespace = "http://action.ldap.fujitsu.com/")
public interface SynUserDelegate {

	/**
	 * 
	 * @param arg0
	 * @return returns boolean
	 */
	@WebMethod
	@WebResult(targetNamespace = "")
	@RequestWrapper(localName = "updateUser", targetNamespace = "http://action.ldap.fujitsu.com/", className = "com.cosmosource.app.ldap.wsClient.UpdateUser")
	@ResponseWrapper(localName = "updateUserResponse", targetNamespace = "http://action.ldap.fujitsu.com/", className = "com.cosmosource.app.ldap.wsClient.UpdateUserResponse")
	public boolean updateUser(
			@WebParam(name = "arg0", targetNamespace = "") UserInfo arg0);

	/**
	 * 
	 * @param arg0
	 * @return returns boolean
	 */
	@WebMethod
	@WebResult(targetNamespace = "")
	@RequestWrapper(localName = "deleteUser", targetNamespace = "http://action.ldap.fujitsu.com/", className = "com.cosmosource.app.ldap.wsClient.DeleteUser")
	@ResponseWrapper(localName = "deleteUserResponse", targetNamespace = "http://action.ldap.fujitsu.com/", className = "com.cosmosource.app.ldap.wsClient.DeleteUserResponse")
	public boolean deleteUser(
			@WebParam(name = "arg0", targetNamespace = "") String arg0);

	/**
	 * 
	 * @param arg0
	 * @return returns boolean
	 */
	@WebMethod
	@WebResult(targetNamespace = "")
	@RequestWrapper(localName = "insertUser", targetNamespace = "http://action.ldap.fujitsu.com/", className = "com.cosmosource.app.ldap.wsClient.InsertUser")
	@ResponseWrapper(localName = "insertUserResponse", targetNamespace = "http://action.ldap.fujitsu.com/", className = "com.cosmosource.app.ldap.wsClient.InsertUserResponse")
	public boolean insertUser(
			@WebParam(name = "arg0", targetNamespace = "") UserInfo arg0);

	/**
	 * 
	 * @param arg2
	 * @param arg1
	 * @param arg0
	 * @return returns boolean
	 */
	@WebMethod
	@WebResult(targetNamespace = "")
	@RequestWrapper(localName = "changePwd", targetNamespace = "http://action.ldap.fujitsu.com/", className = "com.cosmosource.app.ldap.wsClient.ChangePwd")
	@ResponseWrapper(localName = "changePwdResponse", targetNamespace = "http://action.ldap.fujitsu.com/", className = "com.cosmosource.app.ldap.wsClient.ChangePwdResponse")
	public boolean changePwd(
			@WebParam(name = "arg0", targetNamespace = "") String arg0,
			@WebParam(name = "arg1", targetNamespace = "") String arg1,
			@WebParam(name = "arg2", targetNamespace = "") String arg2);

	/**
	 * 
	 * @param arg1
	 * @param arg0
	 * @return returns boolean
	 */
	@WebMethod
	@WebResult(targetNamespace = "")
	@RequestWrapper(localName = "resetPwd", targetNamespace = "http://action.ldap.fujitsu.com/", className = "com.cosmosource.app.ldap.wsClient.ResetPwd")
	@ResponseWrapper(localName = "resetPwdResponse", targetNamespace = "http://action.ldap.fujitsu.com/", className = "com.cosmosource.app.ldap.wsClient.ResetPwdResponse")
	public boolean resetPwd(
			@WebParam(name = "arg0", targetNamespace = "") String arg0,
			@WebParam(name = "arg1", targetNamespace = "") String arg1);

	/**
	 * 
	 * @param arg0
	 * @return returns
	 *         java.util.List<com.cosmosource.app.ldap.wsClient.UserInfo>
	 */
	@WebMethod
	@WebResult(targetNamespace = "")
	@RequestWrapper(localName = "findUser", targetNamespace = "http://action.ldap.fujitsu.com/", className = "com.cosmosource.app.ldap.wsClient.FindUser")
	@ResponseWrapper(localName = "findUserResponse", targetNamespace = "http://action.ldap.fujitsu.com/", className = "com.cosmosource.app.ldap.wsClient.FindUserResponse")
	public List<UserInfo> findUser(
			@WebParam(name = "arg0", targetNamespace = "") String arg0);

	/**
	 * 
	 * @param arg0
	 * @return returns com.cosmosource.app.ldap.wsClient.UserInfo
	 */
	@WebMethod
	@WebResult(targetNamespace = "")
	@RequestWrapper(localName = "findUserByLoginUid", targetNamespace = "http://action.ldap.fujitsu.com/", className = "com.cosmosource.app.ldap.wsClient.FindUserByLoginUid")
	@ResponseWrapper(localName = "findUserByLoginUidResponse", targetNamespace = "http://action.ldap.fujitsu.com/", className = "com.cosmosource.app.ldap.wsClient.FindUserByLoginUidResponse")
	public UserInfo findUserByLoginUid(
			@WebParam(name = "arg0", targetNamespace = "") String arg0);

}
