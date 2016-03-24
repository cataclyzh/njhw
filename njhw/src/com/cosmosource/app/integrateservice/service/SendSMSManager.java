package com.cosmosource.app.integrateservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cosmosource.app.entity.NjhwAdlistGroup;
import com.cosmosource.app.entity.NjhwUsersAlist;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.TCommonSmsBox;
import com.cosmosource.app.port.model.SmsMessage;
import com.cosmosource.app.port.serviceimpl.SmsSendMessageService;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;

public class SendSMSManager extends BaseManager{
	private SmsSendMessageService smsSendMessage;	// 接口manager
	/**
	 * @description:查询列表
	 * @author zh
	 * @param page
	 * @param parMap
	 * @return Page<HashMap>
	 * @date 2013-05-24
	 */
	public Page<TCommonSmsBox> querySendSMS(final Page<TCommonSmsBox> page, HashMap parMap) {
		try {
			 sqlDao.findPage(page, "IntegrateSQL.querySendList", parMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sqlDao.findPage(page, "IntegrateSQL.querySendList", parMap);
	}
	
	/**
	* @title: save
	* @description: 保存管理
	* @author zh
	* @date 2013-5-24 上午11:11:33
	 */
	public String save(String[] ReceiverArray, String content, String isSave){
		String sendRst = "";
		try {
			for (int i = 0; i < ReceiverArray.length; i++) {
				if ("".equals(ReceiverArray[i])) continue;
				
				String [] receiverArray = ReceiverArray[i].split(",");
				for (String nm : receiverArray) {
					String receiver = "", receivermobile = "";
					if (nm.indexOf("_") > 0) {
						receiver = nm.split("_")[1];
						receivermobile = nm.split("_")[2];
					} else {
						receiver = "";
						receivermobile = nm;
					}
					if ("y".equals(isSave)) {
						TCommonSmsBox smsBox = new TCommonSmsBox();
						smsBox.setContent(content);
						smsBox.setReceiver(receiver);
						smsBox.setReceivermobile(receivermobile);
						smsBox.setSenderid(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
						smsBox.setSender(Struts2Util.getSession().getAttribute(Constants.USER_NAME).toString());
						
						NjhwUsersExp exp = (NjhwUsersExp)dao.findById(NjhwUsersExp.class, Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
						smsBox.setSendermobile(exp.getUepMobile());
						smsBox.setSmstime(DateUtil.getSysDate());
						
						dao.save(smsBox);
					}
					
					// 调用接口发送短信
					SmsMessage sms = new SmsMessage();
					sms.setPhoneNumber(receivermobile);
					sms.setSendContent(content);
					String rst = smsSendMessage.send(sms);
					
					if (StringUtil.isNotEmpty(rst))  {
						if (StringUtil.isEmpty(receiver)) {
							sendRst += receivermobile+",";
						} else {
							sendRst += receiver+",";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sendRst;
	}
	
	/**
	 * @description:批量删除
	 * @author zh
	 * @param ids
	 * @date 2013-03-23
	 */
	public void delete(String[] ids){
		dao.deleteByIds(TCommonSmsBox.class, ids);
	}
	
	/**
	 * 
	* @Title: getMesOrgUserTreeData 
	* @Description: 加载单位树信息
	* @date 2013-5-20 上午11:20:40 
	* @param @param orgid
	* @param @param ctx
	* @param @param roomid
	* @return String 
	* @throws
	 */
	public String getUnitTreeData(String orgid, String ids, String ctx, String type) {		
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("tree");
		root.addAttribute("id", "0");

		getUnitTreeData(orgid, ids,root,type);
		return doc.asXML();
	}
	
	/**
	 * 
	* @Title: getUnitTreeData 
	* @Description: 加载单位树信息
	* @param @param orgid
	* @param @param roomid
	* @param @param rootElement    
	* @throws
	 */
	public void getUnitTreeData(String orgid, String ids,Element rootElement, String type){	
	   try {
		   	if ("init".equals(type)) {		
				Map pMap = new HashMap();
				pMap.put("userid", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
				List<HashMap> list = this.findListBySql("PersonnelSQL.getTopOrgIdByUserId", pMap);
				if(list.size()>0){
					HashMap map = list.get(0);
					Org org = (Org)this.findById(Org.class,Long.valueOf(map.get("TOP_ORG_ID").toString()));
				
		            Element el=rootElement.addElement("item");   
		            el.addAttribute("text", org.getName());
		            el.addAttribute("id", org.getOrgId()+"-o");
		            el.addAttribute("open", "1");
		            
		            getUnitTreeData(org.getOrgId().toString(), ids, el,"open");
				}
			} else {			
				List<Org> list = dao.findByHQL("select t from Org t where t.PId=? order by orderNum", new Long(orgid));
						
				for (Org org : list) {
		            Element el=rootElement.addElement("item");   
		            el.addAttribute("text", org.getName());
		            el.addAttribute("id", org.getOrgId()+"-o");
		            getUnitTreeData(org.getOrgId().toString(), ids, el,type);
		        }
			    
				HashMap map = new HashMap();
				map.put("orgid", Long.parseLong(orgid));
		    	List<HashMap> userList = sqlDao.findList("IntegrateSQL.loadUserByOrgid", map);
		    	
		    	//dao.findByHQL("select t from Users t where t.orgId=? ", new Long(orgid));
				
		    	String[] idsArray = (ids != "" && ids != null) ? ids.split(",") : null;
				for (HashMap rstMap : userList) {
					String name = rstMap.get("DISPLAY_NAME") != null ? rstMap.get("DISPLAY_NAME").toString().trim() : "";
					String mobile = rstMap.get("UEP_MOBILE") != null ? rstMap.get("UEP_MOBILE").toString().trim() : "";
					
					if ("".equals(mobile)) 	continue;	// 只添加有手机号的人员
					Element el=rootElement.addElement("item");   
		            el.addAttribute("text", name);
		            el.addAttribute("id", rstMap.get("USERID").toString()+"_"+name+"_"+mobile+"_u");
		            el.addAttribute("im0", "user.gif");		
		            el.addAttribute("im1", "user.gif");		
		            el.addAttribute("im2", "user.gif");
		            if (idsArray != null && idsArray.length > 0) {
		            	for (String strID : idsArray) {
			            	if (rstMap.get("USERID").toString().equals(strID)) {
			            		el.addAttribute("checked", "1");
			            		break;
			            	}
						}
		            }
				}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* @Title: getAddressSMSTree
	* @Description: 加载个人通讯录数信息
	* @author zh
	* @date 2013-5-20 上午11:20:40 
	* @param @param userid
	* @param @param ids
	* @return String 
	* @throws
	 */
	public String getAddressSMSTree(long userid, String ids) {		
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("tree");
		root.addAttribute("id", "0");

		getAddressTreeDoc(null, userid, ids,root);
		return doc.asXML();		
	}
	
	/**
	 * 
	* @Title: getOrgUserTreeDoc 
	* @Description: 加载个人通讯录数信息
	* @author WXJ
	* @date 2013-5-20 上午11:19:47 
	* @param @param gid
	* @param @param userid
	* @param @param rootElement    
	* @return void 
	* @throws
	 */
	public void getAddressTreeDoc(Long gid, Long userid, String ids,Element rootElement){	
	   try {		
		   // 加载分组联系人
		   List<NjhwAdlistGroup> list = dao.findByHQL("select t from NjhwAdlistGroup t where t.nagUser=? order by t.nagId", userid);
		   for (NjhwAdlistGroup gruop : list) {
	            Element el=rootElement.addElement("item");
	            el.addAttribute("text", gruop.getNagName());
	            el.addAttribute("id", gruop.getNagId()+"-g");
	            
	            List<NjhwUsersAlist> alist = dao.findByHQL("select t from NjhwUsersAlist t where t.nuaGroup = ? and t.userid = ?", gruop.getNagId(), userid);
	            if (alist.size() > 0 && alist != null) {
	            	getGroupAddressDoc(ids, el, alist);
	            }
	       }
		   
		   // 添加无分组的联系人
		   List<NjhwUsersAlist> alist = dao.findByHQL("select t from NjhwUsersAlist t where t.nuaGroup = ? and t.userid = ?", 0l, userid);
		   String[] idsArray = (ids != "" && ids != null) ? ids.split(",") : null;
			for (NjhwUsersAlist userAlist : alist) {
				String phoneInfo = userAlist.getNuaPhone() != null ? userAlist.getNuaPhone().toString() : "";
				if ("".equals(phoneInfo)) continue;
				
				if (phoneInfo.indexOf(",") > 0) {
					String [] phoneArray =  phoneInfo.split(",");
					for (String phone : phoneArray) {
						Element el=rootElement.addElement("item");
						el.addAttribute("text", userAlist.getNuaName()+" "+phone);
			            el.addAttribute("id", userAlist.getNuaId()+"-"+phone+"_"+userAlist.getNuaName()+"_"+phone+"_u");
			            el.addAttribute("im0", "user.gif");		
			            el.addAttribute("im1", "user.gif");		
			            el.addAttribute("im2", "user.gif");
			            if(idsArray != null && idsArray.length > 0) {
			            	for (String strID : idsArray) {
			            		String uid = userAlist.getNuaId().toString();
			            		if (strID.indexOf("-") > 0) uid = uid+"-"+phone;
				            	if (uid.equals(strID)) {
				            		el.addAttribute("checked", "1");
				            		break;
				            	}
							}
			            }
					}
				} else {
					Element el=rootElement.addElement("item");
		            el.addAttribute("text", userAlist.getNuaName());
		            el.addAttribute("id", userAlist.getNuaId()+"_"+userAlist.getNuaName()+"_"+phoneInfo+"_u");
		            el.addAttribute("im0", "user.gif");		
		            el.addAttribute("im1", "user.gif");		
		            el.addAttribute("im2", "user.gif");
		            if(idsArray != null && idsArray.length > 0) {
		            	for (String strID : idsArray) {
			            	if (userAlist.getNuaId().toString().equals(strID)) {
			            		el.addAttribute("checked", "1");
			            		break;
			            	}
						}
		            }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* @Title: getGroupAddressDoc 
	* @Description: 添加分组内的人
	* @author zh
	* @date 2013-5-20 上午11:19:47 
	* @param @param ids
	* @param @param alist
	* @param @param rootElement    
	* @return void 
	* @throws
	 */
	public void getGroupAddressDoc(String ids, Element rootElement, List<NjhwUsersAlist> alist){	
	   try {			
		   String[] idsArray = (ids != "" && ids != null) ? ids.split(",") : null;
			for (NjhwUsersAlist userAlist : alist) {
				String phoneInfo = userAlist.getNuaPhone() != null ? userAlist.getNuaPhone().toString() : "";
				if ("".equals(phoneInfo)) continue;
				
				if (phoneInfo.indexOf(",") > 0) {
					String [] phoneArray =  phoneInfo.split(",");
					for (String phone : phoneArray) {
						Element el=rootElement.addElement("item");
			            el.addAttribute("text", userAlist.getNuaName()+" "+phone);
			            el.addAttribute("id", userAlist.getNuaId()+"-"+phone+"_"+userAlist.getNuaName()+"_"+phone+"_u");
			            el.addAttribute("im0", "user.gif");		
			            el.addAttribute("im1", "user.gif");		
			            el.addAttribute("im2", "user.gif");
			            if(idsArray != null && idsArray.length > 0) {
			            	for (String strID : idsArray) {
			            		String uid = userAlist.getNuaId().toString();
			            		if (strID.indexOf("-") > 0) uid = uid+"-"+phone;
				            	if (uid.equals(strID)) {
				            		el.addAttribute("checked", "1");
				            		break;
				            	}
							}
			            }
					}
				} else {
					Element el=rootElement.addElement("item");
		            el.addAttribute("text", userAlist.getNuaName());
		            el.addAttribute("id", userAlist.getNuaId()+"_"+userAlist.getNuaName()+"_"+phoneInfo+"_u");
		            el.addAttribute("im0", "user.gif");		
		            el.addAttribute("im1", "user.gif");		
		            el.addAttribute("im2", "user.gif");
		            if(idsArray != null && idsArray.length > 0) {
		            	for (String strID : idsArray) {
			            	if (userAlist.getNuaId().toString().equals(strID)) {
			            		el.addAttribute("checked", "1");
			            		break;
			            	}
						}
		            }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SmsSendMessageService getSmsSendMessage() {
		return smsSendMessage;
	}
	public void setSmsSendMessage(SmsSendMessageService smsSendMessage) {
		this.smsSendMessage = smsSendMessage;
	}
}
