package com.cosmosource.app.port.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cosmosource.app.entity.ExtInOaBacklog;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;

public class UploadManager extends BaseManager{
	
	
	
	/**
	* @Description：删除库中存在的已经上传的文件
	* @Author：hp
	* @Date：2013-5-24
	* @param extInOaBacklog
	**/
	public void deleteUploadXml(ExtInOaBacklog extInOaBacklog){
		dao.delete(extInOaBacklog);
	}
	
	
	/**
	* @Description：解析上传的xml解析入库
	* @Author：hp
	* @Date：2013-5-24
	* @throws DocumentException
	 * @throws ParseException 
	 * @throws IOException 
	**/
	@SuppressWarnings("unchecked")
	public void saveUploadXml(File file) throws DocumentException, ParseException, IOException{
		File oa = new File(Constants.DBMAP.get("OA_UPLOAD_PATH"));
		oa.mkdirs();
		
//		System.out.println(file.getName()+"*******"+file.getPath());
		
		InputStream inputStream = new FileInputStream(file);
		File destFile = new File(oa.getPath(),file.getName().substring(0,file.getName().length()-4)+"--"+DateUtil.date2Str(new Date(), "yyyyMMddHHmm")+".xml");
		
		
		OutputStream outputStream = new FileOutputStream(destFile);
		
		byte[] buffer = new byte[400];
		int length = 0;
		
		while(-1 != (length = inputStream.read(buffer)))	{
			outputStream.write(buffer, 0, length);
		}
		
		inputStream.close();
		outputStream.close();	
		
		
		
		
		
//		FileUtils.copyFile(file, new File(oa.getPath()+"/"+file.getName().substring(0,file.getName().length()-4)+"--"+DateUtil.date2Str(new Date(), "yyyyMMddHHmm")+".xml"));
//		List<ExtInOaBacklog> ext = dao.findByHQL("from ExtInOaBacklog t");		
//		if(CollectionUtils.isNotEmpty(ext)){
//			for (ExtInOaBacklog extInOaBacklog : ext) {
//				dao.delete(extInOaBacklog);
//			}
//		}
		dao.batchExecute("delete from ExtInOaBacklog");
		SAXReader reader = new SAXReader();
		reader.setEncoding("UTF-8");
//		Document doc = reader.read(new FileInputStream(file));
		Document doc = reader.read(new FileInputStream(destFile));
		Element root = doc.getRootElement();
		List nodes = root.elements("user");
		Iterator<Element> it = nodes.iterator();
		while(it.hasNext()){
			Element user = it.next();
			Element userName = user.element("userName");
			Element openId = user.element("openId");
			Element state = user.element("state");
			Element total = user.element("total");
			List infos =user.elements("info");			
			Iterator<Element> info = infos.iterator();
			while(info.hasNext()){
				ExtInOaBacklog extInOaBacklog = new ExtInOaBacklog();
				Element inf = info.next();
				Element urgentLevel = inf.element("Urgent_level");
				Element desc = inf.element("Desc");
				Element received = inf.element("Received");
				Element deadTime = inf.element("Dead_time");
				Element staffes = inf.element("Staffes");
				Element subUrl = inf.element("Sub_url");
				extInOaBacklog.setUsername(userName.getText());
				extInOaBacklog.setOpenid(openId.getText());
				extInOaBacklog.setState(state.getText());
				extInOaBacklog.setTotal(Integer.parseInt(total.getText()));
				if(urgentLevel != null) extInOaBacklog.setUrgentLevel(urgentLevel.getText());
				if(desc != null) extInOaBacklog.setDesc1(desc.getText());
				if(received != null) extInOaBacklog.setReceived(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(received.getText()));
				if(deadTime != null) extInOaBacklog.setDeadTim(deadTime.getText());
				if(staffes != null) extInOaBacklog.setStaffes(staffes.getText());
				if(subUrl != null) extInOaBacklog.setSubUrl(subUrl.getText());
				extInOaBacklog.setInsertDate(new Date());
				dao.save(extInOaBacklog);
			}
		}
	}

}
