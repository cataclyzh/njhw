package com.cosmosource.remote;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

public class GetPicture extends HttpServlet{

	private static final Logger log = Logger.getLogger(GetPicture.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String filePath = req.getParameter("filePath"); 
		File file = new File(filePath);
		if(!file.exists()){
			log.debug("file not found[" + filePath + "]");
			return;
		}
		
		InputStream in = null;
		ByteArrayOutputStream bos = null;
		OutputStream out = null;
		try{
			in = new FileInputStream(file);
			bos = new ByteArrayOutputStream();
			out = resp.getOutputStream();
			
			byte[] tmp = new byte[128];
			int len = 0;
			while((len = in.read(tmp)) != -1){
				bos.write(tmp, 0 , len);
			}		
			byte[] date = bos.toByteArray();
			
			BASE64Encoder encoder = new BASE64Encoder();
			out.write(encoder.encode(date).getBytes());
		}catch(Exception e){
			log.error(e.getMessage());
		}finally{
			if(out!=null) out.close();
			if(in!=null) in.close();
			if(bos!=null) bos.close();
		}
	}
	
}