package com.cosmosource.app.cms.action;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.PageBreakRecord.Break;

import com.cfca.util.pki.ocsp.Req;
import com.cosmosource.app.cms.service.CmsManager;
import com.cosmosource.app.entity.CmsArticle;
import com.cosmosource.app.entity.CmsChannel;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;

/** 
* @description: 分类内容管理的操作
* @author qiyanqiang
* @date 2013-05-04
*/ 
@SuppressWarnings("unchecked")
public class ChannelCmsArticleAction extends BaseAction<Object> {
	
	// 定义全局变量
	private static final long serialVersionUID = 1L;
	// 定义注入对象
	private CmsManager  cmsTelManager;
	// 定义实体变量（栏目和文章）
	private  CmsChannel  cmsChannel = new CmsChannel();
	private  CmsArticle  cmsArticle = new CmsArticle();
	// 定义分页变量
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);
	
	//选中记录的ID数组
	private String _chk[];
	private String content1 ;
	
	/**
	 * 
	* @title: init
	* @description: 初始化页面
	* @author qiyanqiang
	* @return
	* @date 2013-5-04    
	* @throws
	 */
	public String init(){
		//得到频道id
		String mid = this.getRequest().getParameter("mid");
		if (null != mid && mid.trim().length() > 0) {
			CmsChannel channel = (CmsChannel)cmsTelManager.findById(CmsChannel.class, Long.valueOf(mid));
			this.getRequest().setAttribute("channel",channel);
		}
		
		HashMap map =  new HashMap();
		map.put("title", getParameter("title"));
		map.put("mid", Long.parseLong(getParameter("mid")));
		page = cmsTelManager.queryCmsContent(page, map);
		getRequest().setAttribute("map", map);
		return INIT;
	}
	
	/**
	 * 
	* @title: listArticle
	* @description: 通过标题和栏目名称查询
	* @author qiyanqiang
	* @return
	* @date 2013-5-04    
	* @throws
	 */
	
	public String listArticle(){
		HashMap map =  new HashMap();
		map.put("title", getParameter("title"));
		map.put("cartFlag", getParameter("cartFlag"));
		map.put("mid", Long.parseLong(getParameter("mid")));
		
		page = cmsTelManager.queryCmsContent(page, map);
		
		getRequest().setAttribute("map", map);	
		
		String mid = this.getRequest().getParameter("mid");
		if (null != mid && mid.trim().length() > 0) {
			CmsChannel channel = (CmsChannel)cmsTelManager.findById(CmsChannel.class, Long.valueOf(mid));
			this.getRequest().setAttribute("channel",channel);
		}
		return SUCCESS;
	}
	/**
	 * IP 电话帮助
	 * @return
	 */
	public String ipPhoneHelp(){
		HashMap map =  new HashMap();
		map.put("title", getParameter("title"));
		map.put("cartFlag", getParameter("cartFlag"));
		//map.put("mid", Long.parseLong(getParameter("mid")));
		
		page.setPageSize(14);
		page = cmsTelManager.queryIpHelp(page, map);
		
		getRequest().setAttribute("map", map);	
		
//		String mid = this.getRequest().getParameter("mid");
//		if (null != mid && mid.trim().length() > 0) {
//			CmsChannel channel = (CmsChannel)cmsTelManager.findById(CmsChannel.class, Long.valueOf(mid));
//			this.getRequest().setAttribute("channel",channel);
//		}
		return INPUT;
	}
	/**
	 * ip电话帮助新增
	 * @return
	 */
	public String ipHelpSave()
	{
		String weight = getRequest().getParameter("weight");
		int afterCoun = 1;
		int agoCoun = 0;
		boolean countFlag = false;
		boolean exits = false;
		try {
			List<Map> list = new ArrayList();
			list = cmsTelManager.queryCmsAtricleMap();
			if(list != null){
				if(list.size() == 1&&cmsArticle.getId()!= null){
					weight = "1";
				}else{
					for(int i=0;i<list.size();i++){
						int danum = Integer.parseInt(list.get(i).get("WEIGHT").toString());
						//对比输入的排序数字是否在数据库存在？
						if(danum != Integer.parseInt(weight)){
							exits = false;
						}else{
							exits = true;
							break;
						}
					}
					
					//exits: 是否存在， true：存在； false：不存在
					if(exits){
						//存在：长度是否大于当期表的总数据长度
						if(Integer.parseInt(weight)>list.size()){
							//取得长度  +1 赋值给新增的值
							countFlag = false;
						}else{
							//当存在且小于表的长度时，
							countFlag = true;
						}
					}else{
						//不存在：长度是否大于当期表的总数据长度
						if(Integer.parseInt(weight)>list.size()){
							//取得长度  +1 赋值给新增的值
							countFlag = false;
						}else{
							//当不存在且小于表的长度时，
							countFlag = true;
						}
					}
					// countFlag ：当ture时：遍历该数字之前及之后的数据
					if(countFlag){
						//当输入为1时，之前的数据无法遍历
						//执行该数字之前的数据全部遍历
						List afterList = new ArrayList();
						for(int i=0;i<Integer.parseInt(weight);i++){
							Map param = new HashMap();
							param.put("ID",list.get(i).get("ID"));
							param.put("WEIGHT", afterCoun);
							afterList.add(param);
							afterCoun++;
						}
						cmsTelManager.updateCmsAtricleWeight(afterList);
						//执行该数字之后的数据全部遍历
						List agoList = new ArrayList();
						agoCoun = Integer.parseInt(weight)+1;
						for(int i=Integer.parseInt(weight)-1;i<list.size();i++){
							Map param = new HashMap();
							param.put("ID",list.get(i).get("ID"));
							param.put("WEIGHT", agoCoun);
							agoList.add(param);
							agoCoun++;
						}
						cmsTelManager.updateCmsAtricleWeight(agoList);
					}else{
						//取得长度  +1 赋值给新增的值
						weight = list.size() + 1 + "";
					}
				}
			}else{
				weight = "1";
			}
				
//				
//				for(int i=0;i<list.size();i++){
//					//对比输入的排序数字是否在数据库存在？
//					if(!weight.equals(list.get(i).get("WEIGHT"))){
//						//数据库中不存在该排序数字 1:该数字未被使用 ,数据比较乱，2：大于表中所有数据
//						// 当用户输入的排序数字大于数据库里的总数据时，默认+1
//						if(Integer.parseInt(weight)>list.size()){
//							//取的该表的长度 +1
//							weight = list.size() + 1 + "";
//							countFlag = false;
//							break;
//						}else{
//							//当该数字不存在，且该数字小于该表的总数据长度时，退出
//							countFlag = true;
//							break;
//						}
//					}else{
//						//存在该数字则退出循环
//						countFlag = true;
//					}
//				}
//			}
//			
//			//countFlag: true or false
//			// true: 1：该数字在该表中未使用到  2：该数字小于当前表数据的总长度
//			if(countFlag){
//				//执行该数字之后的数据全部遍历
//				List afterList = new ArrayList();
//				for(int i=Integer.parseInt(weight)-1;i<list.size();i++){
//					Map param = new HashMap();
//					param.put("ID", list.get(i).get("ID"));
//					param.put("WEIGHT", Integer.parseInt(weight)+1);
//					afterList.add(param);
//				}
//				cmsTelManager.updateCmsAtricleWeight(afterList);
//				//执行该数字之前的数据全部遍历
//				List agoList = new ArrayList();
//				for(int j=0;j<Integer.parseInt(weight)-1;j++){
//					//判断下该数字之前的最后一个数字于长度是否一致
//					if(list.get(Integer.parseInt(weight)-2).get("WEIGHT").equals(Integer.parseInt(weight)-1)){
//						break;
//					}else{
//						Map param = new HashMap();
//						param.put("ID", list.get(j).get("ID"));
//						param.put("WEIGHT", coun);
//						agoList.add(param);
//						coun++;
//					}
//				}
//				cmsTelManager.updateCmsAtricleWeight(agoList);
//			}else{
//				List pLists = new ArrayList();
//				for(int i=0;i<list.size();i++){
//					//判断下该数字之前的最后一个数字于长度是否一致
//					if(!list.get(list.size()-1).get("WEIGHT").equals(list.size()+"")){
//						for(int cun=0;cun<list.size();cun++){
//							Map param = new HashMap();
//							param.put("ID", list.get(i).get("ID"));
//							param.put("WEIGHT", coun);
//							pLists.add(param);
//							coun++;
//						}
//						break;
//					}else{
//						break;
//					}
//				}
//				cmsTelManager.updateCmsAtricleWeight(pLists);
			
			
			getRequest().setAttribute("mid",3);
			long uid = Long.valueOf(this.getRequest().getSession().getAttribute(Constants.USER_ID).toString());
			cmsArticle.setEuid(uid);
			cmsArticle.setUserid(uid);
			cmsArticle.setMid(Long.parseLong("3"));
			cmsArticle.setWeight(Integer.parseInt(weight));
			this.cmsTelManager.saveChannelArticle(cmsArticle,content1);
			this.getRequest().setAttribute("article", cmsArticle);
			
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return INPUT;
	}
	
	public String ipHelpUpdate(){
		String title = getRequest().getParameter("title");
		String content = getRequest().getParameter("content");
		String id = getRequest().getParameter("id");
		String weight = getRequest().getParameter("weight"); //150
		String hiddenWeight = getRequest().getParameter("hiddenWeight");//7
		boolean flag = false;
		try {
			List<Map> list = new ArrayList();
			list = cmsTelManager.queryCmsAtricleMap();
			
			for(int i=0;i<list.size();i++){
				int danum = Integer.parseInt(list.get(i).get("WEIGHT").toString());
				//如果要修改的值 在数据库中存在，则为true
				if(danum == Integer.parseInt(weight)){
					flag = true;
					break;
				}else{
					//不存在，则为false
					flag = false;
				}
			}
			
			if(flag){
				for(int i=0;i<list.size();i++){
					int danum = Integer.parseInt(list.get(i).get("WEIGHT").toString());
					if(danum == Integer.parseInt(hiddenWeight)){
						List agoList = new ArrayList();
						Map param = new HashMap();
						param.put("ID",list.get(i).get("ID"));
						param.put("WEIGHT", weight);
						agoList.add(param);
						cmsTelManager.updateCmsAtricleWeight(agoList);
					}
					if(danum == Integer.parseInt(weight)){
						List agoList = new ArrayList();
						Map param = new HashMap();
						param.put("ID",list.get(i).get("ID"));
						param.put("WEIGHT", hiddenWeight);
						agoList.add(param);
						cmsTelManager.updateCmsAtricleWeight(agoList);
					}
				}
			}else{
				for(int s=Integer.parseInt(hiddenWeight);s<list.size();s++){
					List agoList = new ArrayList();
					Map param = new HashMap();
					param.put("ID",list.get(s).get("ID"));
					param.put("WEIGHT", Integer.parseInt(list.get(s).get("WEIGHT").toString()) - 1);
					agoList.add(param);
					cmsTelManager.updateCmsAtricleWeight(agoList);
				}
				for(int i=0;i<list.size();i++){
					if(Integer.parseInt(list.get(i).get("WEIGHT").toString()) == Integer.parseInt(hiddenWeight)){
						List agoList = new ArrayList();
						Map param = new HashMap();
						param.put("ID",list.get(i).get("ID"));
						param.put("WEIGHT", list.size());
						agoList.add(param);
						cmsTelManager.updateCmsAtricleWeight(agoList);
					}
				}
			}
			List lpm = new ArrayList();
			Map pm = new HashMap();
			pm.put("ID", id);
			pm.put("TITLE", title);
			pm.put("WEIGHT", weight);
			pm.put("CONTENT", content);
			lpm.add(pm);
			cmsTelManager.updateCmsAtricleWeights(lpm);
			setIsSuc("true");
		}catch (Exception e){
			setIsSuc("false");
			e.printStackTrace();
		}
		return INPUT;
	}
	
	public String ipHelpQuery(){
		String id = getRequest().getParameter("cmsId");
		try {
			this.cmsArticle = cmsTelManager.queryCmsAtricleMap(Long.parseLong(id));
			String cont = cmsArticle.getContent();
			if(cont != null){
				cont=cont.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
				cont=cont.replaceAll("[(>)<]", "");
				this.cmsArticle.setContent(cont);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INPUT;
	}
	
	/**
	 * 删除ip电话帮助列表
	 * @return
	 */
	public String deleteIpHelp(){
		int countNum = 1;
		try {
			cmsTelManager.deleteArticle(_chk);
			addActionMessage("删除成功");
			List<Map> list = new ArrayList();
			list = cmsTelManager.queryCmsAtricleMap();
			List agoList = new ArrayList();
			for(int i=0;i<list.size();i++){
				Map param = new HashMap();
				param.put("ID",list.get(i).get("ID"));
				param.put("WEIGHT", countNum);
				agoList.add(param);
				countNum ++ ;
			}
			cmsTelManager.updateCmsAtricleWeight(agoList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除失败");
		}
		return RELOAD;
	}
	
	public String ipHelpInput(){
		getRequest().setAttribute("mid",getParameter("mid"));
		CmsArticle  article = null;
		if (!"".equals(getParameter("cmsId")) && getParameter("cmsId") != null) {
			article = (CmsArticle)cmsTelManager.findById(CmsArticle.class, Long.parseLong(getParameter("cmsId")));
			getRequest().setAttribute("article", article);
		}
		return SUCCESS;
	}
	
	public String inputHelp(){
		return SUCCESS;
	}
	
	/**
	 * 失物招领更多列表页
	 * @开发者：ywl
	 * @时间：2013-07-15
	 * @return
	 */
	public String newListArticle(){
		Map param = new HashMap();
		page = cmsTelManager.queryCmsContent(page, param);
		setPage(page);
		String mid = this.getRequest().getParameter("mid");
		if (null != mid && mid.trim().length() > 0) {
			CmsChannel channel = (CmsChannel)cmsTelManager.findById(CmsChannel.class, Long.valueOf(mid));
			this.getRequest().setAttribute("channel",channel);
		}
		List list = page.getResult();
		getRequest().setAttribute("list", list);
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: inputArticle
	* @description: 修改新增操作
	* @author qiyanqiang
	* @return
	* @date 2013-5-04    
	* @throws
	 */
	public String inputArticle()
	{
		getRequest().setAttribute("mid",getParameter("mid"));
		CmsArticle  article = null;
		if (!"".equals(getParameter("cmsId")) && getParameter("cmsId") != null) {
			article = (CmsArticle)cmsTelManager.findById(CmsArticle.class, Long.parseLong(getParameter("cmsId")));
			getRequest().setAttribute("article", article);
		}
		return INPUT;
	}
	
	/**
	 * 
	* @title: saveArticle
	* @description: 保存操作
	* @author qiyanqiang
	* @return
	* @date 2013-5-04    
	* @throws
	 */
	public String saveArticle()
	{
		try {
			getRequest().setAttribute("mid",getParameter("mid"));
			long uid = Long.valueOf(this.getRequest().getSession().getAttribute(Constants.USER_ID).toString());
			cmsArticle.setEuid(uid);
			cmsArticle.setUserid(uid);
			this.cmsTelManager.saveChannelArticle(cmsArticle,content1);
			this.getRequest().setAttribute("article", cmsArticle);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: deleteArticle
	* @description: 删除操作
	* @author qiyanqiang
	* @return
	* @date 2013-5-04    
	* @throws
	 */
	public String deleteArticle() throws Exception {
		try {
			cmsTelManager.deleteArticle(_chk);
			addActionMessage("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除失败");
		}
		return RELOAD;
	}
	
	/**
	 * 
	* @title: searchCmsDetail
	* @description: 当点击详情的时候的操作
	* @author qiyanqiang
	* @return
	* @date 2013-5-20  
	* @throws
	 */
	public String searchCmsDetail()
	{  
		CmsArticle articleDetail = (CmsArticle)cmsTelManager.findById(CmsArticle.class, Long.parseLong(getParameter("id")));
		getRequest().setAttribute("articleDetail", articleDetail);
		if (articleDetail.getCartDate() != null && articleDetail.getCartDate().toString() != "") {
			getRequest().setAttribute("cartDate", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(articleDetail.getCartDate()));
		}
		return  SUCCESS;
	}
	
	/**
	* @title: saveClaim
	* @description: 保存认领
	* @author zh
	* @return
	* @date 2013-5-20  
	* @throws
	 */
	public String saveClaim() {
		try {
			this.cmsTelManager.saveClaim(cmsArticle);
			Struts2Util.renderText("success");
		} catch (Exception e) {
			Struts2Util.renderText("fail");
			e.printStackTrace();
		}
		return  null;
	}

	public CmsManager getCmsTelManager() {
		return cmsTelManager;
	}

	public void setCmsTelManager(CmsManager cmsTelManager) {
		this.cmsTelManager = cmsTelManager;
	}

	@Override
	protected void prepareModel() throws Exception {
	
		
	}
	
	@Override
	public CmsArticle getModel() {
		return cmsArticle;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public CmsChannel getCmsChannel() {
		return cmsChannel;
	}

	public void setCmsChannel(CmsChannel cmsChannel) {
		this.cmsChannel = cmsChannel;
	}

	public CmsArticle getCmsArticle() {
		return cmsArticle;
	}

	public void setCmsArticle(CmsArticle cmsArticle) {
		this.cmsArticle = cmsArticle;
	}

	public String[] get_chk() {
		return _chk;
	}

	public void set_chk(String[] chk) {
		_chk = chk;
	}

	public String getContent1() {
		return content1;
	}

	public void setContent1(String content1) {
		this.content1 = content1;
	}
	
}
