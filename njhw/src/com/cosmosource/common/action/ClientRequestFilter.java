//package com.cosmosource.common.action;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.Date;
//import java.util.Hashtable;
//import java.util.List;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.apache.commons.codec.digest.DigestUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.security.web.context.HttpRequestResponseHolder;
//import org.springframework.security.web.context.SecurityContextRepository;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//
//import com.cosmosource.base.service.DBManager;
//import com.cosmosource.base.service.SpringContextHolder;
//import com.cosmosource.base.util.Constants;
//import com.cosmosource.base.util.CryptoUtil;
//import com.cosmosource.base.util.DateUtil;
//import com.cosmosource.base.util.Split;
//import com.cosmosource.common.entity.TAcSubsys;
//import com.cosmosource.common.entity.TAcUser;
//import com.cosmosource.common.entity.TCommonLogOperation;
//import com.cosmosource.common.security.AuthSuccessHandler;
//import com.cosmosource.common.security.Logininfo;
//import com.cosmosource.common.security.SpringSecurityUtils;
//import com.cosmosource.common.service.AuthorityManager;
//
//import sun.misc.BASE64Decoder;
//
//
//public class ClientRequestFilter extends HttpServlet implements Filter {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -4102004244684762732L;
//	private static Logger logger = LoggerFactory.getLogger(AuthSuccessHandler.class);
//	
//	public void destroy() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
//			FilterChain filterChain) throws IOException, ServletException {
//		// TODO Auto-generated method stub
//		
//		HttpServletRequest request = (HttpServletRequest) servletRequest;
//		
//		ServletContext servletContext = request.getSession().getServletContext();           
//		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext); 
//		AuthorityManager authorityManager = (AuthorityManager)ctx.getBean( "authorityManager");
//		
//		String requestURI = request.getRequestURI(); // 取得根目录所对应的绝对路径:
//		
//			String queryString = request.getQueryString(); // 取得根目录所对应的绝对路径
//			Hashtable<String,String> params=ConvertClientRequestParams(queryString);
//			
//			if(params==null||params.size()==0){
//				try {
//					throw new ClientRequestException("客户端请求访问页面时，参数不能为空!");
//				} catch (ClientRequestException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				request.getSession().setAttribute(Constants.ORG_ID, null);	
//				return;
//			}
//	
//			String isClientRequest=params.get("enter");
//			
//			if("njhw".equals(isClientRequest)){			
//										
//	//			String sumbittime=params.get("sumbittime");
//	//			Long outTime=30000L;
//	//			
//	//			Date dd1=DateUtil.str2Date(sumbittime,"yyyy-MM-dd hh:mm:ss");
//	//			Date dd2=DateUtil.getSysDate();
//	//			Long dif=Long.valueOf("0");
//	//			if(dd1.before(dd2)){
//	//				dif=(dd2.getTime()-dd1.getTime())/(1000*60);
//	//			}else{
//	//				dif=(dd1.getTime()-dd2.getTime())/(1000*60);
//	//			}
//	//			System.out.println("dd1="+dd1+"$$$dd2="+dd2+"$$$dif="+dif+"minute"+outTime+":"+(dif>outTime));
//	//			if(dif>outTime){
//	//				try {
//	//					throw new ClientRequestException("对不起，您的时间和服务器不一致，请重新登录！");
//	//				} catch (ClientRequestException e) {
//	//					// TODO Auto-generated catch block
//	//					e.printStackTrace();
//	//				}
//	//			}
//				
//				String username = params.get("loginname");
//				String uspwd = params.get("pwd");		
//				
//				TAcUser user = authorityManager.findUserByLoginName(username);
//				if (user == null || !uspwd.equals(user.getPassword())){
//					try {
//						throw new ClientRequestException("对不起，用户名密码错误!");
//					} catch (ClientRequestException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();						
//					}
//
//					request.getSession().setAttribute(Constants.ORG_ID, null);	
//					System.err.println("****************非法访问21****************");
//					return;
//				}
//				
////				request.getSession().setAttribute(Constants.ORG_ID, user.getOrgid());	
////				request.getSession().setAttribute(Constants.USER_NAME, username);	
//				
//				//如果被激活且当前用户未登录则进行登录			
////				UserDetailsService userDetailsService = (UserDetailsService)ctx.getBean( "userDetailsService");				
////				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
////				SpringSecurityUtils.saveUserDetailsToContext(userDetails, (HttpServletRequest) request);
//			
//				
//				//AuthSuccessHandler
//				logger.info("登录成功： 登录名：[{}] 登录时间：[{}] 登录IP：[{}] 登录SessionID：[{}] ", new Object[] { user.getLoginname(), 
//						DateUtil.getDateTime(),SpringSecurityUtils.getCurrentUserIp(),request.getSession().getId()});
//				
//				//登录成功后,密码错误次数清零				
//				authorityManager.updatePasswordWrongCountZero(user.getLoginname());
//		    	
//				Logininfo  logininfo = authorityManager.findLogininfoByLoginName(user.getLoginname());
//				
////				TAcUser user = logininfo.getUser();
////				request.getSession().setAttribute(Constants.LOGININFO, logininfo);
////				request.getSession().setAttribute("_userinfo", logininfo.getUser());
//				request.getSession().setAttribute(Constants.LOGIN_NAME, user.getLoginname());
////		    	request.getSession().setAttribute(Constants.AUTH, authLocal);
//		    	
//		    	request.getSession().setAttribute(Constants.ROLES, logininfo.getUserrole());
//		    	request.getSession().setAttribute(Constants.ORG_ID, logininfo.getOrg().getOrgid());
//		    	request.getSession().setAttribute(Constants.ORG_CODE, logininfo.getOrg().getOrgcode());
//		    	request.getSession().setAttribute(Constants.ORG_TYPE, logininfo.getOrg().getOrgtype());
//		    	request.getSession().setAttribute(Constants.COMPANY, logininfo.getOrg().getCompany());
//		    	request.getSession().setAttribute(Constants.COMPANY_Min, logininfo.getOrg().getCompany().replace("admin", ""));
//		    	request.getSession().setAttribute(Constants.ORG_LAYER, logininfo.getOrg().getTreelayer());
//		    	request.getSession().setAttribute(Constants.ORG_TAXNO, logininfo.getOrg().getTaxno());
//		    	request.getSession().setAttribute(Constants.ORG_TAXNAME, logininfo.getOrg().getTaxname());
//		    	request.getSession().setAttribute(Constants.ORG_NAME, logininfo.getOrg().getOrgname());
//		    	request.getSession().setAttribute(Constants.USER_NAME, logininfo.getUser().getUsername());
//		    	request.getSession().setAttribute(Constants.USER_CODE, logininfo.getUser().getUsercode());
//		    	request.getSession().setAttribute(Constants.USER_ID, logininfo.getUser().getUserid());
//		    	request.getSession().setAttribute(Constants.USER_STATUS, logininfo.getUser().getStatus());
//		    	request.getSession().setAttribute(Constants.ORG_PARENTID, logininfo.getOrg().getParentid());
//		    	request.getSession().setAttribute(Constants.USER_TYPE, logininfo.getUser().getUsertype());
//		    	
//		    	String treelayer = logininfo.getOrg().getTreelayer();
//		    	if(treelayer!=null){
//		    		String[] arrLayer = treelayer.split("\\.");
//		    		
//		    		if(arrLayer.length<=2){
//		    			request.getSession().setAttribute(Constants.COMPANY_ID,logininfo.getOrg().getOrgid());
//		    			request.getSession().setAttribute(Constants.ORG_ROOT,logininfo.getOrg().getOrgid());
//		    		}else{
//		    			String companyid = arrLayer[2];
//		    			request.getSession().setAttribute(Constants.COMPANY_ID,Long.parseLong(companyid));
//		    			request.getSession().setAttribute(Constants.ORG_ROOT,Long.parseLong(companyid));
//		    		}
//		    	}
//				List<String> menus = authorityManager.getMenuList(logininfo.getUserrole(), (Long)request.getSession().getAttribute(Constants.COMPANY_ID));
//				List<TAcSubsys> sys = authorityManager.getSysList((Long)request.getSession().getAttribute(Constants.COMPANY_ID));
//		    	
//		    	request.getSession().setAttribute(Constants.ROLES_MENUS, menus);
//		    	request.getSession().setAttribute(Constants.ORG_SUBSYS, sys);
//		    	
//		    	request.getSession().setAttribute("_treelayer", treelayer);
//		        //是否记录操作日志
//		        if("Y".equals(Constants.DBMAP.get("COMMON_WRITEOPLOG_YN"))){
//		    		DBManager mgr = SpringContextHolder.getBean("dbMgr");
//		    		
//		    		TCommonLogOperation logOp = new TCommonLogOperation();
//		    		
//		    		logOp.setOpTime(DateUtil.getSysDate());
//		    		logOp.setActionId("login.act");
//		    		logOp.setActionName("登录系统");
//		    		logOp.setLoginname(user.getLoginname());
//		    		logOp.setSessionId(request.getSession().getId());
//		    		logOp.setOpType("00");
//		    		logOp.setOpIp(SpringSecurityUtils.getCurrentUserIp());
//		    		mgr.save(logOp);
//		        	
//		        }	
//		    		       
//		   	        
////		        SecurityContext context = readSecurityContextFromSession((HttpSession)request.getSession());
//		        
//		        //将request、response对象交给HttpRequestResponseHolder维持
////		        SecurityContextRepository repo = (SecurityContextRepository)ctx.getBean("securityContextRepository");		        
////		        HttpRequestResponseHolder holder = new HttpRequestResponseHolder((HttpServletRequest)servletRequest, (HttpServletResponse)servletResponse);
////		        SecurityContext contextBeforeChainExecution = repo.loadContext(holder);
////		        SecurityContextHolder.setContext(contextBeforeChainExecution);
//	
//		    		
//				// 加入filter链继续向下执行
//				filterChain.doFilter(servletRequest, servletResponse);
//	
//			}else{
//				if (request.getSession().getAttribute(Constants.ORG_ID) == null) {
//					System.err.println("****************非法访问22****************");
//					return;				
//				}else{										
//					// 加入filter链继续向下执行
//					filterChain.doFilter(servletRequest, servletResponse);
//				}
//			}
//
////		}
//	}
//
//	public void init(FilterConfig filterConfig) throws ServletException {
//		// TODO Auto-generated method stub
//		
//	}
//	
//	 private SecurityContext readSecurityContextFromSession(HttpSession httpSession) {
//	        final boolean debug = logger.isDebugEnabled();
//
//	        if (httpSession == null) {
//	            if (debug) {
//	                logger.debug("No HttpSession currently exists");
//	            }
//
//	            return null;
//	        }
//
//	        // Session exists, so try to obtain a context from it.
//	        String springSecurityContextKey = "SPRING_SECURITY_CONTEXT";
//	        
//	        Object contextFromSession = httpSession.getAttribute(springSecurityContextKey);
//
//	        if (contextFromSession == null) {
//	            if (debug) {
//	                logger.debug("HttpSession returned null object for SPRING_SECURITY_CONTEXT");
//	            }
//
//	            return null;
//	        }
//
//	        // We now have the security context object from the session.
//	        if (!(contextFromSession instanceof SecurityContext)) {
//	            if (logger.isWarnEnabled()) {
//	                logger.warn(springSecurityContextKey + " did not contain a SecurityContext but contained: '"
//	                        + contextFromSession + "'; are you improperly modifying the HttpSession directly "
//	                        + "(you should always use SecurityContextHolder) or using the HttpSession attribute "
//	                        + "reserved for this class?");
//	            }
//
//	            return null;
//	        }
//
//	        if (debug) {
//	            logger.debug("Obtained a valid SecurityContext from " + springSecurityContextKey + ": '" + contextFromSession + "'");
//	        }
//
//	        // Everything OK. The only non-null return from this method.
//
//	        return (SecurityContext) contextFromSession;
//	    }
//
//
//	/**
//	 * 将客户端请求参数转化为Hashtable
//	 * @param clientRequestParams
//	 * @return
//	 */
//	public static Hashtable<String,String> ConvertClientRequestParams(String clientRequestParams){
//		
//		if(clientRequestParams==null||"".equals(clientRequestParams)) return null;
//		
//		
//		Hashtable<String,String> params=new Hashtable<String,String>();
//		
//		try
//		{
//		
////			String key=clientRequestParams.substring(clientRequestParams.lastIndexOf("&")+1);
////			System.out.println("key:"+key);
//			byte[] key=new byte[]{-24, -94, -12, -7, -124, 41, -12, 15, -53, -38, 108, -57, 13, 7, -90, 110};
////			String paramStrEncrypt=clientRequestParams.substring(0,clientRequestParams.lastIndexOf("&"));
//			String paramStrEncrypt=clientRequestParams;
//			System.out.println("paramStrEncrypt:"+paramStrEncrypt);
//			String paramStrDecrypt=new String(CryptoUtil.aesDecryptFromHex(paramStrEncrypt,key)); 
////			String paramStrDecrypt=paramStrEncrypt;
//			System.out.println("paramStrDecrypt:"+paramStrDecrypt);
//			
//			List list=Split.split(paramStrDecrypt, "&");
//			if(list!=null){
//				String str="";
//				List keylist;
//				for(int i=0;i<list.size();i++){
//					str=(String)list.get(i);
//					
//					if("".equals(str)) break;
//					
//					keylist=Split.split(str,"=");
//					if(keylist!=null&&keylist.size()==2){
//						
//						params.put(keylist.get(0).toString(), keylist.get(1).toString());
//						
//					}
//					
//				}
//			}
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return params;
//	}
//	
//	
//	public static void main(String[] args){
//		byte[] key=new byte[]{-24, -94, -12, -7, -124, 41, -12, 15, -53, -38, 108, -57, 13, 7, -90, 110};
//		String aes=CryptoUtil.aesEncryptToHex("loginname=admin&pwd=123456", key);
//		String targetURL="http://localhost:8080/eipp-interface/scf/sysset/tscfLogin.action?"+aes;
//		//跳转到targetURL
//
//		
//	}
//}
