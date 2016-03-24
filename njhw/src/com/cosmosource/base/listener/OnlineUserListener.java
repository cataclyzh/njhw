package com.cosmosource.base.listener;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OnlineUserListener implements HttpSessionListener {
	
	private static final  Log log = LogFactory.getLog(OnlineUserListener.class);
	
	public static Map sessionMap = new ConcurrentHashMap();
	
    public void sessionCreated(HttpSessionEvent event) {
    	
    }
    
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        // 判断当前session，取得session进行移除
        String sessionId = session.getId();
        log.info("session进入remove状态:"+sessionId);
        try{
        	if(sessionMap.size() > 0 ){
            	Iterator<Map.Entry> it = sessionMap.entrySet().iterator();
                while (it.hasNext()) {
        		    Map.Entry<String, Map> entry = it.next();
        		    Map sessionIdMap = entry.getValue();
        		    String userId = entry.getKey();
        		    Iterator<Map.Entry> idIter = sessionIdMap.entrySet().iterator();
        			while (idIter.hasNext()) {
        			    Map.Entry<String, HttpSession> idEntry = idIter.next();
        			    HttpSession sessionobj = idEntry.getValue();
        			    if(sessionId.equals(sessionobj.getId())){
        			    	sessionIdMap.remove(sessionId);
        			    	sessionMap.put(userId,sessionIdMap);
        			    	break;
        			    }
        			}
        		}
            }
        }catch(Exception e){
        	 log.info("session:"+sessionId+" 发现remove 异常！");
        }
    }
}
