package com.cosmosource.app.ldap.wsClient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.cosmosource.app.ldap.model.UserInfo;

/**
 * <p>
 * Java class for findUserByLoginUidResponse complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="findUserByLoginUidResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://action.ldap.fujitsu.com/}userInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findUserByLoginUidResponse", propOrder = { "_return" })
public class FindUserByLoginUidResponse {

	@XmlElement(name = "return")
	protected UserInfo _return;

	/**
	 * Gets the value of the return property.
	 * 
	 * @return possible object is {@link UserInfo }
	 * 
	 */
	public UserInfo getReturn() {
		return _return;
	}

	/**
	 * Sets the value of the return property.
	 * 
	 * @param value
	 *            allowed object is {@link UserInfo }
	 * 
	 */
	public void setReturn(UserInfo value) {
		this._return = value;
	}

}
