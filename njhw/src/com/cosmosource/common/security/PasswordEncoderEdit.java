package com.cosmosource.common.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * @ClassName:PasswordEncoderEdit
 * @Description：密码加密
 * @Author：hp 
 * @Date:2013-4-16
 */
public class PasswordEncoderEdit extends Md5PasswordEncoder{

	
	 /**
	* @Description：比对密码是否正确
	* @Author：hp
	* @Date：2013-4-16
	* @param encPass
	* @param rawPass
	* @param salt
	* @return
	**/
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
	        String pass1 = "" + encPass;
	        String pass2 =DigestUtils.md5Hex(rawPass).substring(0, 20).toUpperCase();
	        return new PasswordEncoderEdit().equals(pass1,pass2);
	    }

	 
	 
	 
	  /**
	* @Description：比对两个密码的 UTF-8的字符集是否正确，还有字节的异或是否正确
	* @Author：hp
	* @Date：2013-4-16
	* @param expected
	* @param actual
	* @return
	**/
	public boolean equals(String expected, String actual) {
	        byte[] expectedBytes = expected.getBytes();
	        byte[] actualBytes = actual.getBytes();
	        int expectedLength = expectedBytes == null ? -1 : expectedBytes.length;
	        int actualLength = actualBytes == null ? -1 : actualBytes.length;
	        if (expectedLength != actualLength) {
	            return false;
	        }

	        int result = 0;
	        for (int i = 0; i < expectedLength; i++) {
	            result |= expectedBytes[i] ^ actualBytes[i];
	        }
	        return result == 0;
	    }
	  
	  
	  
}
