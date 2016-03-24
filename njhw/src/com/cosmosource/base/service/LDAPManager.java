/**
 * 
 */
package com.cosmosource.base.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.smi.OctetString;

import com.cosmosource.app.ldap.model.UserInfo;
import com.cosmosource.base.dao.BaseDaoLDAP;






/**
 * @author Administrator
 * 
 */
public class LDAPManager {
	private String LDAP_KEYSTORE_PATH = getProperties("LDAP_KEYSTORE_PATH");
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	BaseDaoLDAP ldapDao;
	
	/**
	 * 使用用户名密码登陆数据库
	 * 
	 * @param providerUrl
	 *            ldap服务器地址
	 * @param protocol
	 *            传输协议，ssl或plain
	 * @param userName
	 *            登录名
	 * @param userPassword
	 *            密码
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean connect(String providerUrl, String protocol,
			String userName, String userPassword) {
		// String userName = "uid=admin,ou=system";
		// String userPassword = "secret";

		Hashtable env = new Hashtable();
		// Access the keystore, this is where the Root CA public key cert was
		// installed
		// Could also do this via command line java
		// -Djavax.net.ssl.trustStore....
		String keystore = LDAP_KEYSTORE_PATH;
		System.setProperty("javax.net.ssl.trustStore", keystore);

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		// Enable connection pooling
		env.put("com.sun.jndi.ldap.connect.pool", "true");
		// the following pool parameters doesn't work
		// must setup as java init parameters
		env.put("com.sun.jndi.ldap.connect.pool.protocol", "plain ssl");
		env.put("com.sun.jndi.ldap.connect.pool.authentication", "none simple DIGEST-MD5");
		// the following code will send the trace output to System.out.
		// env.put("com.sun.jndi.ldap.trace.ber", System.out);
		// set security credentials, note using simple cleartext authentication
		if (userName != null)
			env.put(Context.SECURITY_PRINCIPAL, userName);
		if (userPassword != null)
			env.put(Context.SECURITY_CREDENTIALS, userPassword);
		env.put(Context.SECURITY_AUTHENTICATION, "simple"); // "none", "simple",
															// "strong"

		// specify use of ssl
		env.put(Context.SECURITY_PROTOCOL, protocol);// "ssl","plain"

		// connect to my domain controller
		String ldapURL = providerUrl;
		env.put(Context.PROVIDER_URL, ldapURL);
		// Create the initial directory context
		
		logger.info("Connecting server " + ldapURL + " for: " + userName);
		try {
			ldapDao = new BaseDaoLDAP(new InitialLdapContext(env, null)) ;			
			logger.info("Success");			
		} catch (NamingException e) {
			logger.error("Problem connecting server: " + e);			
			return false;
		}
		return true;
	}
	
	/**
	 * 断开连接数据库
	 * 
	 * @return
	 */
	public boolean disconnect() {
		logger.info("Closing server");
		try {
			ldapDao.close();
			logger.info("Success");			
		} catch (NamingException e) {
			logger.info("Problem closing server: " + e);
			return false;
		} catch (NullPointerException e) {
			logger.error("Server did not connected: " + e);
			return false;
		}
		return true;
	}
	
	/**
	 * 获取schema结构
	 * 
	 * @param schemaName
	 * 			schema名
	 */
	@SuppressWarnings("rawtypes")
	public DirContext getSchema(String schemaName) {
		DirContext schema = null;
		try {
			logger.info("Starting getting Schema: " + schemaName);
			schema = ldapDao.getSchema("");
			//返回objectclass 
		    DirContext cs = (DirContext)schema.lookup("ClassDefinition/" + schemaName);
		    Attributes attrs = cs.getAttributes("");
		   	NamingEnumeration enu = attrs.getAll();
		    	while(enu.hasMoreElements()){
		    		logger.info(enu.next().toString());		    		
		    }
			
		} catch (NamingException e) {
			logger.error("Problem getting schema : " + e);			
		} catch (Exception e) {
			logger.error("Problem getting schema: " + e);			
		}
		return schema;
	}
	
	
	
	
	
	@SuppressWarnings("rawtypes")
	public boolean addEntry(String distinguishedName, Map map, Attribute objclass) {
		Attributes attrs = new BasicAttributes();
		
		
		Iterator iterator = map.keySet().iterator();
		while(iterator.hasNext()) {
			String nextKey = iterator.next().toString();
			if(map.get(nextKey)!=null)
				attrs.put(nextKey, map.get(nextKey));			
		}
		
		attrs.put(objclass);
		logger.info("Creating Entry for: " + distinguishedName);
		try {
			ldapDao.createSubcontext(distinguishedName, attrs);
		} catch (NamingException e) {
			logger.error("Problem creating entry: " + e);
			return false;
		} catch (Exception e) {
			logger.error("Problem creating entry: " + e);
			return false;
		}
		return true;
	}
	
	/**
	 * 删除节点
	 * 
	 * @param dn
	 *            节点路径
	 * @return
	 */
	public boolean deleteEntry(String distinguishedName) {
		logger.info("Deleting Entry for: " + distinguishedName);
		try {
			ldapDao.destroySubcontext(distinguishedName);
			logger.info("Success");
		} catch (NamingException e) {
			logger.error("Problem deleting entry: " + e);
			return false;
		} catch (Exception e) {
			logger.error("Problem deleting entry: " + e);
			return false;
		}
		return true;
	}
	
	/**
	 * 查找节点（包括子目录）
	 * 
	 * @param searchBase
	 *            搜索根路径
	 * @param filter
	 *            过滤条件
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<UserInfo> search(String searchBase, String filter) {
		NamingEnumeration <SearchResult> searchResult = null; 
		List<UserInfo> list = new ArrayList<UserInfo>();
		HashMap mapUser = new HashMap();
		try {
			logger.info("Starting Searching in Base: " + searchBase +" with filter: " + filter);
			SearchControls controls=new SearchControls();			
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			searchResult=ldapDao.search(searchBase, filter, controls);
			int count = 0;
			while(searchResult.hasMore()){
				NamingEnumeration attrs = searchResult.next().getAttributes().getAll();
				while (attrs.hasMore()) {
				    Attribute attr = (Attribute)attrs.next();
				    if(attr.getID().equals("objectClass"))
				    	continue;
				    if (attr.getID().equals("userPassword")) 
				    	mapUser.put(attr.getID(), new OctetString((byte[]) attr.get()).toString());
				    else
				    	mapUser.put(attr.getID(), attr.get().toString());
				    
				}
				list.add((UserInfo)parametersToJavaBean(mapUser, new UserInfo().getClass()));
				count++;
			}
			logger.info(list.toString());
			logger.info("Search complete: "+count+" result(s)");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			logger.error("Problem searching entry: " + e);
		} catch (Exception e) {
			logger.error("Problem searching entry: " + e);			
		}
		return list;
	}
	
	/**
	 * 查询对象
	 * 
	 * @param searchBase
	 *            搜索根路径
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UserInfo lookup(String searchBase) {
		HashMap mapUser = new HashMap();
		UserInfo UserInfo = null;
		try {
			logger.info("Starting Looking Up in Base: " + searchBase);
			NamingEnumeration attrs = ((DirContext)ldapDao.lookup(searchBase)).getAttributes("").getAll();
			while (attrs.hasMore()) {
			    Attribute attr = (Attribute)attrs.next();
			    if(attr.getID().equals("objectClass"))
			    	continue;
			    if (attr.getID().equals("userPassword")) 
			    	mapUser.put(attr.getID(), new OctetString((byte[]) attr.get()).toString());
			    else
			    	mapUser.put(attr.getID(), attr.get().toString());
			    
			}
			UserInfo =(UserInfo)parametersToJavaBean(mapUser, new UserInfo().getClass());
			logger.info(UserInfo.toString());
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			logger.error("Problem lookuping entry: " + e);
		} catch (Exception e) {
			logger.error("Problem lookuping entry: " + e);			
		}
		return UserInfo;
	}
	
	/**
	 * 列举上下文
	 * 
	 * @param searchBase
	 *            搜索根路径
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public void list(String searchBase) {
		try {
			logger.info("Starting Listing Base: " + searchBase);
			NamingEnumeration list = ldapDao.list(searchBase);
			while (list.hasMore()) {
			    NameClassPair nc = (NameClassPair)list.next();
			    logger.info(nc.toString());
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			logger.error("Problem listing entry: " + e);
		} catch (Exception e) {
			logger.error("Problem listing entry: " + e);			
		}
	}
	
	/**
	 * 对节点对象进行重命名
	 * 
	 * @param oldName
	 * 				旧名字
	 * @param newName
	 * 				新名字
	 */
	public boolean rename(String oldName,String newName) {
		try {
			logger.info("Starting rename oldName:" + oldName +" to: " + newName);
			ldapDao.rename(oldName, newName);
			logger.info("Success");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			logger.error("Problem renameing entry: " + e);
			return false;
		} catch (Exception e) {
			logger.error("Problem renameing entry: " + e);	
			return false;
		}
		return true;
	}
	
	/**
	 * 获取节点属性
	 * 
	 * @param dn
	 *            节点路径
	 * @param properties
	 *            属性名
	 * @return
	 */
	public String getAttribute(String distinguishedName, String properties){
		String prop = null;
		try {
			Attributes attrs = ldapDao.getAttributes(distinguishedName);
			
			if(properties.equals("userPassword")){
				if (attrs.get(properties)!= null) 
					prop = new OctetString((byte[]) attrs.get(properties).get()).toString();				
			}
			else {
				if (attrs.get(properties)!= null) 
					prop = attrs.get(properties).get().toString();
			}
			//logger.info(properties + ": " + prop);

		} catch (NamingException e) {
			logger.error("Problem getting entry: " + e);

		}	catch (Exception e) {
			logger.error("Problem getting properties: " + e);
		}
		
		return prop;
	}
	
	/**
	 * 添加节点属性
	 * 
	 * @param dn
	 *            节点路径
	 * @param properties
	 *            属性名
	 * @param value
	 *            值
	 * @return
	 */
	public boolean addAttribute(String distinguishedName, String properties, String value) {
		logger.info("Adding Attribute for: " + distinguishedName);
		try {
			// set password is a ldap modfy operation
			ModificationItem[] mods = new ModificationItem[1];
			byte[] unicodeValue = value.getBytes("UTF8");
			mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE,
					new BasicAttribute(properties, unicodeValue));

			// Perform the update
			ldapDao.modifyAttributes(distinguishedName, mods);
			logger.info("Success");
		} catch (NamingException e) {
			logger.error("Problem adding attribute: " + e);
			return false;
		} catch (UnsupportedEncodingException e) {
			logger.error("Problem encoding attribute: " + e);
			return false;
		} catch (Exception e) {
			logger.error("Problem adding attribute: " + e);
			return false;
		}
		return true;
	}
	/**
	 * 删除节点属性
	 * 
	 * @param dn
	 *            节点路径
	 * @param properties
	 *            属性名
	 * @param value
	 *            值
	 * @return
	 */
	public boolean removeAttribute(String distinguishedName, String properties, String value) {
		logger.info("Removing Attribute for: " + distinguishedName);
		try {
			ModificationItem[] mods = new ModificationItem[1];
			byte[] unicodeValue = value.getBytes("UTF8");
			mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
					new BasicAttribute(properties, unicodeValue));

			// Perform the update
			ldapDao.modifyAttributes(distinguishedName, mods);
			logger.info("Success");
		} catch (NamingException e) {
			logger.error("Problem removing attribute: " + e);
			return false;
		} catch (UnsupportedEncodingException e) {
			logger.error("Problem encoding attribute: " + e);
			return false;
		} catch (Exception e) {
			logger.error("Problem removing attribute: " + e);
			return false;
		}
		return true;
	}
	
	/**
	 * 修改节点属性
	 * 
	 * @param dn
	 *            节点路径
	 * @param properties
	 *            属性名
	 * @param value
	 *            值
	 * @return
	 */
	public boolean setAttribute(String distinguishedName, String properties, String value) {
		logger.info("Setting attribute for: " + distinguishedName);
		try {
			// set password is a ldap modfy operation
			String oldValue= getAttribute(distinguishedName, properties);
			ModificationItem[] mods ;
			if (oldValue==null) {
				mods = new ModificationItem[1];
				byte[] unicodeValue = value.getBytes("UTF8");
				mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
						new BasicAttribute(properties, unicodeValue));
			}else {
				mods = new ModificationItem[2];				
				byte[] oldUnicodeValue = oldValue.getBytes("UTF8");				
				byte[] newUnicodeValue = value.getBytes("UTF8");
				mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
						new BasicAttribute(properties, oldUnicodeValue));
				mods[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE,
						new BasicAttribute(properties, newUnicodeValue));
			}
			

			// Perform the update
			ldapDao.modifyAttributes(distinguishedName, mods);
			logger.info("Success");
		} catch (NamingException e) {
			logger.error("Problem Setting attribute: " + e);
			return false;
		} catch (UnsupportedEncodingException e) {
			logger.error("Problem encoding attribute: " + e);
			return false;
		} catch (Exception e) {
			logger.error("Problem setting attribute: " + e);
			return false;
		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	public boolean setAttributes(String distinguishedName, HashMap map) {
		logger.info("Setting attribute for: " + distinguishedName);
		try {
			
			
			// ignore userPassword and loginUid
			int size = map.size();
			if (map.containsKey("userPassword"))
				size--;
			if (map.containsKey("loginUid"))
				size--;
			ModificationItem[] mods  = new ModificationItem[size];
			
			int count = 0;
			Iterator iterator = map.keySet().iterator();
			while(iterator.hasNext()) {
				String nextKey = iterator.next().toString();
				if (nextKey.equals("userPassword")||nextKey.equals("loginUid")) 
					continue;
				String oldValue = this.getAttribute(distinguishedName, nextKey);
				if(oldValue!=null && !oldValue.equals("")){
					
					if(map.get(nextKey)!=null){
						byte[] unicodeValue = map.get(nextKey).toString().getBytes("UTF8");
						mods[count] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(nextKey, unicodeValue));
					}else {
						mods[count] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, new BasicAttribute(nextKey, oldValue));
					}
					
				}else {
					if(map.get(nextKey)!=null){
						byte[] unicodeValue = map.get(nextKey).toString().getBytes("UTF8");
						mods[count] = new ModificationItem(DirContext.ADD_ATTRIBUTE, new BasicAttribute(nextKey, unicodeValue));
					}else {
						byte[] unicodeValue = map.get("loginUid").toString().getBytes("UTF8");
						mods[count] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("loginUid", unicodeValue));
					}
					
				}
				
				count++;
			}
			// Perform the update
			ldapDao.modifyAttributes(distinguishedName, mods);
			logger.info("Success");
		} catch (NamingException e) {
			logger.error("Problem Setting attributes: " + e);
			return false;
		} catch (UnsupportedEncodingException e) {
			logger.error("Problem encoding attributes: " + e);
			return false;
		} catch (Exception e) {
			logger.error("Problem setting attributes: " + e);
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	private Object parametersToJavaBean(HashMap map, Class cls) {
		Object obj = null;
		try {
			obj = cls.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 取出bean里的所有方法
		Method[] methods = cls.getMethods();
		for (int i = 0; i < methods.length; i++) {
			// 取方法名
			String method = methods[i].getName();
			// 取出方法的类型
			Class[] cc = methods[i].getParameterTypes();
			if (cc.length != 1)
				continue;

			// 如果方法名没有以set开头的则退出本次for
			if (method.indexOf("set") < 0)
				continue;
			// 类型
			String type = cc[0].getSimpleName();

			try {
				// 转成小写
				//Object value = method.substring(3).toLowerCase();
				Object value = method.substring(3,4).toLowerCase() + method.substring(4);
				// 如果map里有该key
				if (map.containsKey(value)) {
					// 调用其底层方法
					setValue(type, map.get(value), i, methods, obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/***
	 * 调用底层方法设置值
	 */
	private void setValue(String type, Object value, int i,
			Method[] method, Object bean) {
		if (value != null && !value.equals("")) {
			try {
				if (type.equals("String")) {
					// 第一个参数:从中调用基础方法的对象第二个参数:用于方法调用的参数
					method[i].invoke(bean, new Object[] { value });
				} else if (type.equals("int") || type.equals("Integer")) {
					method[i].invoke(bean, new Object[] { new Integer(""
							+ value) });
				} else if (type.equals("long") || type.equals("Long")) {
					method[i].invoke(bean,
							new Object[] { new Long("" + value) });
				} else if (type.equals("boolean") || type.equals("Boolean")) {
					method[i].invoke(bean,
							new Object[] { Boolean.valueOf("" + value) });
				} else if (type.equals("Date")) {
					Date date = null;
					if (value.getClass().getName().equals("java.util.Date")) {
						date = (Date) value;
					} else {
						String format = ((String) value).indexOf(":") > 0 ? "yyyy-MM-dd hh:mm"
								: "yyyy-MM-dd";
						date = new SimpleDateFormat(format).parse("" + value);
					}
					if (date != null) {
						method[i].invoke(bean, new Object[] { date });
					}
				} else if (type.equals("byte[]")) {
					method[i].invoke(bean,
							new Object[] { new String(value + "").getBytes() });
				}
			} catch (Exception e) {
				//System.out.println("将linkHashMap 或 HashTable 里的值填充到javabean时出错,请检查!");
				e.printStackTrace();
			}
		}
	}
	protected String getProperties(String name) {
		InputStream in = getClass().getResourceAsStream(
				"../config/db.properties");
		Properties pro = new Properties();
		String value = null;
		try {
			pro.load(in);
			value = pro.getProperty(name).trim();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
}
