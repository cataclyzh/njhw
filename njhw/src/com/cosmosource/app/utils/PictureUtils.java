package com.cosmosource.app.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;

import com.cosmosource.base.util.EncodeUtil;


public class PictureUtils {
	
	/**
	 * 
	* @title: getBase64Pic 
	* @description: 得到Base64编码图片串
	* @author herb
	* @param filePath
	* @return
	* @date Apr 25, 2013 3:16:50 PM     
	* @throws
	 */
	public static final String getBase64Pic(String filePath){
		String contents = "";
		if (filePath == null || filePath.trim().length() < 1) return "";
		BufferedInputStream in;
		try {
			in = new BufferedInputStream(
					new FileInputStream(filePath));
			if (null == in)return""; 
			ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
			byte[] temp = new byte[1024 * 1024];
			int size = 0;
			while ((size = in.read(temp)) != -1) {
				out.write(temp, 0, size);
			}
			in.close();
			byte[] content = out.toByteArray();
			contents = EncodeUtil.base64Encode(content);
			contents = "data:image/x-icon;base64," + contents;
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			return "";
		} catch (IOException e) {
			//e.printStackTrace();
			return "";
		}
		return contents;
	}
	
	public static void main(String [] arg){
		String newdbpass = DigestUtils.md5Hex("123456"); //存入数据库的md5值密码
		System.out.println(newdbpass.substring(0,20).toUpperCase());
	}
	
}
