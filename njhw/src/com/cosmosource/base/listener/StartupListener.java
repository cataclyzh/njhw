package com.cosmosource.base.listener;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.context.ContextLoaderListener;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.SpringContextHolder;
import com.cosmosource.base.util.CheckTheTime;
import com.cosmosource.base.util.Client;
import com.cosmosource.base.util.CommObject;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.NewCheckLink;
import com.cosmosource.base.util.SocketPool;
import com.cosmosource.common.entity.TAcDicttype;
import com.cosmosource.common.entity.TCommonConstants;

/**
 * 
* @类描述: 应用启动监听器
* @作者： WXJ
 */
@SuppressWarnings({"rawtypes"})
public class StartupListener extends ContextLoaderListener
implements ServletContextListener {
	
	private static final Log log = LogFactory.getLog(StartupListener.class);
    
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        Map config = (HashMap) context.getAttribute(Constants.CONFIG);
        if (config == null) {
            config = new HashMap();
        }
        //添加字典表信息
        BaseManager mgr = SpringContextHolder.getBean("manager");
	    List types = mgr.findByHQL("select t from TAcDicttype t order by dicttypeid");
	    List detas = null;
        if(types!=null&&types.size()>0){
        	TAcDicttype ent = null;
        	for(int i=0;i<types.size();i++){
        		ent = (TAcDicttype)types.get(i);
        		detas = mgr.findByHQL("select t from TAcDictdeta t where t.dicttype=? order by t.sortno",ent.getDicttypeid());
        		context.setAttribute("DICT_"+ent.getDicttypecode(), detas);
        	}
        }
        //常量表数据
	    List cttList = mgr.findByHQL("select t from TCommonConstants t ");
        if(cttList!=null&&cttList.size()>0){
        	TCommonConstants ent = null;
        	for(int i=0;i<cttList.size();i++){
        		ent = (TCommonConstants)cttList.get(i);
        		Constants.DBMAP.put(ent.getCttKey(), ent.getCttValue());
        	}
        }
        //加载CA
        if("0".equals(Constants.DBMAP.get("COMMON_CA_ISLOAD"))){
            Map tmpMap = null;
            List listCaAction = mgr.findListBySql("CommonSQL.findCaaction4Mem", null);
            if(listCaAction!=null&&listCaAction.size()>0){
            	for(int i=0;i<listCaAction.size();i++){
            		tmpMap = (Map)listCaAction.get(i);
            		Constants.CAACTIONMAP.put((String)tmpMap.get("LOGINNAME")+(String)tmpMap.get("ACTIONCODE"), (String)tmpMap.get("ISUSECA"));
            	}
            	
            }
            List listCaUser = mgr.findListBySql("CommonSQL.findCauser4Mem", null);
            if(listCaUser!=null&&listCaUser.size()>0){
            	for(int i=0;i<listCaUser.size();i++){
            		tmpMap = (Map)listCaUser.get(i);
            		Constants.CAUSERMAP.put((String)tmpMap.get("LOGINNAME")+(String)tmpMap.get("CANO"), (String)tmpMap.get("ISVALIDCA"));
            	}
            }
        }

        
        
//	    List companyList = mgr.findByHQL("select t.company from TAcOrg t where t.company <> 'Cosmosource' group by t.company ");
//	    List taxnoList = null;
//	    TAcOrg org = null;
//	    Map<String,String> map = null;
//	    if(companyList!=null&&companyList.size()>0){
//        	String sCompany = null;
//        	for(int i=0;i<companyList.size();i++){
//        		sCompany =  (String)companyList.get(i);
//        		taxnoList = mgr.findByHQL("select t from TAcOrg t where (t.orgtype = '5' or t.orgtype = '6') and t.taxno is not null and t.company = ? ",sCompany);
//        		
//                if(taxnoList!=null&&taxnoList.size()>0){
//                	map = new HashMap<String,String>();
//                	for(int j=0;j<taxnoList.size();j++){
//                		org = (TAcOrg)taxnoList.get(j);
//                		map.put(org.getTaxno(), org.getTaxname());
//                	}
//                	Constants.BUYTAXNOMAP.put(sCompany, map);
//                }
//                taxnoList = mgr.findByHQL("select t from TAcOrg t where t.orgtype = '8' and t.taxno is not null and t.company = ? ",sCompany);
//                if(taxnoList!=null&&taxnoList.size()>0){
//                	map = new HashMap<String,String>();
//                	for(int j=0;j<taxnoList.size();j++){
//                		org = (TAcOrg)taxnoList.get(j);
//                		map.put(org.getTaxno(), org.getTaxname());
//                	}
//                	Constants.SELLTAXNOMAP.put(sCompany, map);
//                }
//        	}
//        }
        setupContext(context);

		// 加载通讯机信息
		String CommConfigPath = getConfigPath(event)
				+ "ComInfo.xml";
		getCommInfo(CommConfigPath);
    }

    public static void setupContext(ServletContext context) {
    
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
    
	/**
	 * 获取门锁数据
	 * 
	 * @param commConfigPath
	 *            配置文件路径
	 */
	@SuppressWarnings("unchecked")
	private static void getCommInfo(String commConfigPath) {
		try {
			SAXReader reader = new SAXReader(false);
			Document doc = reader.read(commConfigPath);
			Element rootElement = doc.getRootElement();
			List<Element> commList = rootElement.elements();
			String commIndex = null;
			for (Element commElement : commList) {
				CommObject commObject = new CommObject();
				commIndex = commElement.elementTextTrim("INDEX");
				commObject.setIndex(commIndex);
				commObject.setIP(commElement.elementTextTrim("IP"));
				commObject.setPort(Integer.valueOf(commElement
						.elementTextTrim("PORT")));
				// 启动客户端
				log.error(commIndex + "号通讯机启动...");
				Client client = new Client(commObject);
				Thread clientThread = new Thread(client, commIndex
						+ "_COMM_RECEIVE_THREAD");
				clientThread.start();
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				SocketPool.commSocketThreadMap.put(commIndex, client);
			}
			// 启动链路检测线程
			NewCheckLink checkLink = new NewCheckLink(Constants.CHECK_LINK_TIME * 2);
			Thread checkLinkThread = new Thread(checkLink, "CHECK_LINK_THREAD");
			checkLinkThread.start();
			
			// 同步锁时间
			CheckTheTime checkTime = new CheckTheTime(
					Constants.CHECK_TIME_TIME);
			Thread checkTimeThread = new Thread(checkTime, "CHECK_TIME_THREAD");
			checkTimeThread.start();
		} catch (DocumentException e) {
			log.error("加载COMM通讯机配置文件出错...");
			e.printStackTrace();
		}
	}
    
	/**
	 * 获取通信机配置文件路径
	 * 
	 * @param servletcontextevent
	 *            servletcontextevent
	 * @return 配置文件路径
	 */
	private String getConfigPath(ServletContextEvent servletcontextevent) {
		String filePath = servletcontextevent.getServletContext().getRealPath(
				"")
				+ File.separator
				+ "WEB-INF"
				+ File.separator
				+ "conf"
				+ File.separator;
		return filePath;
	}
}
