package com.cosmosource.app;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cosmosource.base.util.PropertiesUtil;

@SuppressWarnings("serial")
  
public class PhotoServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String  captureStringResult = null;
		
	  
		try {
			
			 String filePath = PropertiesUtil.getAnyConfigProperty("dir.reg.photo.temp", PropertiesUtil.NJHW_CONFIG);
			 String  stringName=  request.getParameter("photoName");
			 int  length =  stringName.length();
		     int  position = stringName.indexOf(".");
			 String  captureString= stringName.substring(0, position);
			 System.out.println( length );
			 System.out.println(captureString);
		     captureStringResult= captureString+ "_"+System.currentTimeMillis()+".jpg";
		 
			 File myFilePath = new File(filePath); 
		     if (!myFilePath.exists()) { 
		    	 myFilePath.mkdirs(); 
		     } 
		     
			//File file=new File(request.getParameter("photoName"));
		    File file=new File(captureStringResult); 
			FileOutputStream dos=new FileOutputStream(file);  
			int x=request.getInputStream().read();
			while (x>-1) {
				dos.write(x);
				x=request.getInputStream().read();
			}
			dos.flush();
			dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String s= "{\"data\":{\"urls\":[\""+captureStringResult+"\"]},\"status\":1,\"statusText\":\"保存存成功\"}";
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(s);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
