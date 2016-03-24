package com.cosmosource.app.cms.action;
import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.cms.service.CmsManager;
import com.cosmosource.app.entity.CmsArticle;
import com.cosmosource.app.entity.CmsChannel;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;

/** 
* @description: 内容管理的操作
* @author qiyanqiang
* @date 2013-05-04
*/ 
@SuppressWarnings("unchecked")
public class CmsArticleAction extends BaseAction<Object> {
	
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
		getRequest().setAttribute("channels", this.cmsTelManager.findByHQL("select t from CmsChannel t", null));
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
		map.put("mid", Long.parseLong(getParameter("mid")));
		page = cmsTelManager.queryCmsContent(page, map);
		getRequest().setAttribute("map", map);
		getRequest().setAttribute("channels", this.cmsTelManager.findByHQL("select t from CmsChannel t", null));
		
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
		getRequest().setAttribute("channel_id",getParameter("inputtype"));
		CmsArticle  article = new CmsArticle();
		//System.out.println(getParameter("cmsId"));
		if (!"".equals(getParameter("cmsId")) && getParameter("cmsId") != null) {
			article = (CmsArticle)cmsTelManager.findById(CmsArticle.class, Long.parseLong(getParameter("cmsId")));
			getRequest().setAttribute("article", article);
		}
		getRequest().setAttribute("channels", this.cmsTelManager.findByHQL("select t from CmsChannel t", null));
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
			String pa = getParameter("inputtype");
			//this.getRequest().setAttribute("inputtype", pa);

			

			this.cmsTelManager.saveArticle(cmsArticle, pa,content1);

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
		CmsArticle  articleDetail = new CmsArticle();
	      String  id= getRequest().getParameter("id");
		 articleDetail = (CmsArticle)cmsTelManager.findById(CmsArticle.class, Long.parseLong(getParameter("id")));
		 getRequest().setAttribute("articleDetail", articleDetail);
		
		return  SUCCESS;
		
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
