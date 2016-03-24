package com.cosmosource.app.ldap.wsClient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.cosmosource.app.ldap.model.UserInfo;

/**
 * <p>
 * Java class for insertUser complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="insertUser">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://action.ldap.fujitsu.com/}userInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "insertUser", propOrder = { "arg0" })
public class InsertUser {

	protected UserInfo arg0;

	/**
	 * Gets the value of the arg0 property.
	 * 
	 * @return possible object is {@link UserInfo }
	 * 
	 */
	public UserInfo getArg0() {
		return arg0;
	}

	/**
	 * Sets the value of the arg0 property.
	 * 
	 * @param value
	 *            allowed object is {@link UserInfo }
	 * 
	 */
	public void setArg0(UserInfo value) {
		this.arg0 = value;
	}

}
