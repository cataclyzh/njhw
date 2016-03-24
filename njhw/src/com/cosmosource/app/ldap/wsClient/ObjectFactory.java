package com.cosmosource.app.ldap.wsClient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import com.cosmosource.app.ldap.model.UserInfo;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the com.cosmosource.app.ldap.wsClient package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _ChangePwdResponse_QNAME = new QName(
			"http://action.ldap.fujitsu.com/", "changePwdResponse");
	private final static QName _FindUserByLoginUidResponse_QNAME = new QName(
			"http://action.ldap.fujitsu.com/", "findUserByLoginUidResponse");
	private final static QName _InsertUserResponse_QNAME = new QName(
			"http://action.ldap.fujitsu.com/", "insertUserResponse");
	private final static QName _ChangePwd_QNAME = new QName(
			"http://action.ldap.fujitsu.com/", "changePwd");
	private final static QName _InsertUser_QNAME = new QName(
			"http://action.ldap.fujitsu.com/", "insertUser");
	private final static QName _ResetPwd_QNAME = new QName(
			"http://action.ldap.fujitsu.com/", "resetPwd");
	private final static QName _UpdateUserResponse_QNAME = new QName(
			"http://action.ldap.fujitsu.com/", "updateUserResponse");
	private final static QName _DeleteUserResponse_QNAME = new QName(
			"http://action.ldap.fujitsu.com/", "deleteUserResponse");
	private final static QName _FindUserByLoginUid_QNAME = new QName(
			"http://action.ldap.fujitsu.com/", "findUserByLoginUid");
	private final static QName _ResetPwdResponse_QNAME = new QName(
			"http://action.ldap.fujitsu.com/", "resetPwdResponse");
	private final static QName _UpdateUser_QNAME = new QName(
			"http://action.ldap.fujitsu.com/", "updateUser");
	private final static QName _FindUserResponse_QNAME = new QName(
			"http://action.ldap.fujitsu.com/", "findUserResponse");
	private final static QName _FindUser_QNAME = new QName(
			"http://action.ldap.fujitsu.com/", "findUser");
	private final static QName _DeleteUser_QNAME = new QName(
			"http://action.ldap.fujitsu.com/", "deleteUser");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: com.cosmosource.app.ldap.wsClient
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link ChangePwdResponse }
	 * 
	 */
	public ChangePwdResponse createChangePwdResponse() {
		return new ChangePwdResponse();
	}

	/**
	 * Create an instance of {@link UpdateUser }
	 * 
	 */
	public UpdateUser createUpdateUser() {
		return new UpdateUser();
	}

	/**
	 * Create an instance of {@link UpdateUserResponse }
	 * 
	 */
	public UpdateUserResponse createUpdateUserResponse() {
		return new UpdateUserResponse();
	}

	/**
	 * Create an instance of {@link FindUser }
	 * 
	 */
	public FindUser createFindUser() {
		return new FindUser();
	}

	/**
	 * Create an instance of {@link DeleteUserResponse }
	 * 
	 */
	public DeleteUserResponse createDeleteUserResponse() {
		return new DeleteUserResponse();
	}

	/**
	 * Create an instance of {@link ResetPwdResponse }
	 * 
	 */
	public ResetPwdResponse createResetPwdResponse() {
		return new ResetPwdResponse();
	}

	/**
	 * Create an instance of {@link InsertUser }
	 * 
	 */
	public InsertUser createInsertUser() {
		return new InsertUser();
	}

	/**
	 * Create an instance of {@link ResetPwd }
	 * 
	 */
	public ResetPwd createResetPwd() {
		return new ResetPwd();
	}

	/**
	 * Create an instance of {@link DeleteUser }
	 * 
	 */
	public DeleteUser createDeleteUser() {
		return new DeleteUser();
	}

	/**
	 * Create an instance of {@link UserInfo }
	 * 
	 */
	public UserInfo createUserInfo() {
		return new UserInfo();
	}

	/**
	 * Create an instance of {@link FindUserByLoginUidResponse }
	 * 
	 */
	public FindUserByLoginUidResponse createFindUserByLoginUidResponse() {
		return new FindUserByLoginUidResponse();
	}

	/**
	 * Create an instance of {@link FindUserByLoginUid }
	 * 
	 */
	public FindUserByLoginUid createFindUserByLoginUid() {
		return new FindUserByLoginUid();
	}

	/**
	 * Create an instance of {@link FindUserResponse }
	 * 
	 */
	public FindUserResponse createFindUserResponse() {
		return new FindUserResponse();
	}

	/**
	 * Create an instance of {@link ChangePwd }
	 * 
	 */
	public ChangePwd createChangePwd() {
		return new ChangePwd();
	}

	/**
	 * Create an instance of {@link InsertUserResponse }
	 * 
	 */
	public InsertUserResponse createInsertUserResponse() {
		return new InsertUserResponse();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link ChangePwdResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://action.ldap.fujitsu.com/", name = "changePwdResponse")
	public JAXBElement<ChangePwdResponse> createChangePwdResponse(
			ChangePwdResponse value) {
		return new JAXBElement<ChangePwdResponse>(_ChangePwdResponse_QNAME,
				ChangePwdResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link FindUserByLoginUidResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://action.ldap.fujitsu.com/", name = "findUserByLoginUidResponse")
	public JAXBElement<FindUserByLoginUidResponse> createFindUserByLoginUidResponse(
			FindUserByLoginUidResponse value) {
		return new JAXBElement<FindUserByLoginUidResponse>(
				_FindUserByLoginUidResponse_QNAME,
				FindUserByLoginUidResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link InsertUserResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://action.ldap.fujitsu.com/", name = "insertUserResponse")
	public JAXBElement<InsertUserResponse> createInsertUserResponse(
			InsertUserResponse value) {
		return new JAXBElement<InsertUserResponse>(_InsertUserResponse_QNAME,
				InsertUserResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ChangePwd }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://action.ldap.fujitsu.com/", name = "changePwd")
	public JAXBElement<ChangePwd> createChangePwd(ChangePwd value) {
		return new JAXBElement<ChangePwd>(_ChangePwd_QNAME, ChangePwd.class,
				null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link InsertUser }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://action.ldap.fujitsu.com/", name = "insertUser")
	public JAXBElement<InsertUser> createInsertUser(InsertUser value) {
		return new JAXBElement<InsertUser>(_InsertUser_QNAME, InsertUser.class,
				null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ResetPwd }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://action.ldap.fujitsu.com/", name = "resetPwd")
	public JAXBElement<ResetPwd> createResetPwd(ResetPwd value) {
		return new JAXBElement<ResetPwd>(_ResetPwd_QNAME, ResetPwd.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link UpdateUserResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://action.ldap.fujitsu.com/", name = "updateUserResponse")
	public JAXBElement<UpdateUserResponse> createUpdateUserResponse(
			UpdateUserResponse value) {
		return new JAXBElement<UpdateUserResponse>(_UpdateUserResponse_QNAME,
				UpdateUserResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link DeleteUserResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://action.ldap.fujitsu.com/", name = "deleteUserResponse")
	public JAXBElement<DeleteUserResponse> createDeleteUserResponse(
			DeleteUserResponse value) {
		return new JAXBElement<DeleteUserResponse>(_DeleteUserResponse_QNAME,
				DeleteUserResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link FindUserByLoginUid }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://action.ldap.fujitsu.com/", name = "findUserByLoginUid")
	public JAXBElement<FindUserByLoginUid> createFindUserByLoginUid(
			FindUserByLoginUid value) {
		return new JAXBElement<FindUserByLoginUid>(_FindUserByLoginUid_QNAME,
				FindUserByLoginUid.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link ResetPwdResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://action.ldap.fujitsu.com/", name = "resetPwdResponse")
	public JAXBElement<ResetPwdResponse> createResetPwdResponse(
			ResetPwdResponse value) {
		return new JAXBElement<ResetPwdResponse>(_ResetPwdResponse_QNAME,
				ResetPwdResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUser }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://action.ldap.fujitsu.com/", name = "updateUser")
	public JAXBElement<UpdateUser> createUpdateUser(UpdateUser value) {
		return new JAXBElement<UpdateUser>(_UpdateUser_QNAME, UpdateUser.class,
				null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link FindUserResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://action.ldap.fujitsu.com/", name = "findUserResponse")
	public JAXBElement<FindUserResponse> createFindUserResponse(
			FindUserResponse value) {
		return new JAXBElement<FindUserResponse>(_FindUserResponse_QNAME,
				FindUserResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link FindUser }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://action.ldap.fujitsu.com/", name = "findUser")
	public JAXBElement<FindUser> createFindUser(FindUser value) {
		return new JAXBElement<FindUser>(_FindUser_QNAME, FindUser.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUser }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://action.ldap.fujitsu.com/", name = "deleteUser")
	public JAXBElement<DeleteUser> createDeleteUser(DeleteUser value) {
		return new JAXBElement<DeleteUser>(_DeleteUser_QNAME, DeleteUser.class,
				null, value);
	}

}
