package com.cosmosource.app.parking_lot.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosmosource.app.entity.Suggest;
import com.cosmosource.app.entity.SuggestReply;
import com.cosmosource.app.suggest.service.SuggestManager;
import com.cosmosource.app.suggest.vo.SuggestReplyVO;
import com.cosmosource.app.suggest.vo.SuggestVO;
import com.cosmosource.app.wirelessLocation.service.WirelessManager;
import com.cosmosource.app.wirelessLocation.service.WirelessSqlManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;

/** 
* @ClassName: SuggestAction 
* @Description: 意见箱 
* @author pingxianghua
* @date 2013-7-23 下午2:04:11 
*  
*/
public class ParkingAction extends BaseAction<Object>{
	private Logger log = LoggerFactory.getLogger(getClass());
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);//默认每页20条记录
	private Date startDate;// 开始时间
	private Date endDate;// 结束时间
	private String status;//状态
	private SuggestManager suggestManager;
	private String content;//内容
	private Integer pageNo;//页数
	private Suggest suggest;//意见
	private Boolean isAll = true;//是否是全部意见
	private String[] sugids;//意见id
	private SuggestReply suggestReply;//意见回复
	private Long repid;//回复id
	private Long sugid;//单个意见id
	private Page<SuggestVO> suggestPage= new Page<SuggestVO>(Constants.PAGESIZE);//
	private Date sugCreatetime;//意见提交时间
	private Date repCreatetime;//回复时间
	private WirelessSqlManager wirelessSqlManager = null;
	
	private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


	public WirelessSqlManager getWirelessSqlManager() {
		return wirelessSqlManager;
	}


	public void setWirelessSqlManager(WirelessSqlManager wirelessSqlManager) {
		this.wirelessSqlManager = wirelessSqlManager;
	}



	private WirelessManager wirelessManager = null;

	public WirelessManager getWirelessManager() {
		return wirelessManager;
	}


	public void setWirelessManager(WirelessManager wirelessManager) {
		this.wirelessManager = wirelessManager;
	}


	/**
	* @Description 初始化页面
	* @Author：huangyongfa
	* @Date 2013-7-24 上午10:17:24 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String init() {
		String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
	    return INIT;
	}
	
	
	/**
	* @Description
	* @Author：pingxianghua
	* @Date 2013-7-26 下午2:57:34 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String initProperty() {
		String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		return "property";
	}
	
	
	/**
	* @Description 意见新建页面
	* @Author：huangyongfa
	* @Date 2013-7-24 上午10:17:24 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String newSuggest() {
		logger.info("newSuggest method invoked!");
		return "newSuggest";
	}
	
	
	/**
	* @Description 新增指定Suggest数据
	* @Author：pingxianghua
	* @Date 2013-7-23 下午1:56:38 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String saveSuggest(){
		if(suggest!=null){
			Long userId = Long.valueOf(getRequest().getSession().getAttribute(
					Constants.USER_ID).toString());
			suggest.setCreatetime(new Date());
			suggest.setStatus("0");
			suggest.setUserid(userId);
			suggestManager.saveOrUpdateSuggest(suggest);
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	/**
	* @Description 新增或者修改指定Suggest_reply数据
	* @Author：pingxianghua
	* @Date 2013-7-23 下午1:56:38 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	 * @throws UnsupportedEncodingException 
	**/
	public String saveOrUpdateSuggestReply() throws UnsupportedEncodingException{
		String repcontent=getParameter("content");

		if(repid==null){
			suggestReply = new SuggestReply();
			suggestReply.setCreatetime(new Date());
		}else{
			suggestReply = suggestManager.findSuggestReplyById(repid);
			suggestReply.setStatus("1");
		}
		suggestReply.setModifytime(new Date());
		suggestReply.setContent(repcontent);
		suggestReply.setStatus("0");
		suggestReply.setSuggestionid(sugid);
		suggest = suggestManager.findSuggestById(sugid);
		suggest.setStatus("1");
		suggestManager.saveOrUpdateSuggest(suggest);
		
		Object o = getRequest().getSession().getAttribute(Constants.USER_ID);
		Long userId = null;
		if(o==null){
			return ERROR;
		}else{
			userId  = Long.valueOf(o.toString());
		}
		suggestReply.setUserid(userId);
		
		try {
			//suggestReply.setCreatetime(DateUtil.str2Date(repCreatetime, "yyyy-MM-dd HH:mm:ss"));
			suggestManager.saveOrUpdateSuggestReply(suggestReply);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		SuggestVO tempSuggestVO = null;
		tempSuggestVO = suggestManager.selectSuggestSuggestReplyListBySugid(suggest.getSugid());
		getRequest().setAttribute("sug", tempSuggestVO);
		
		return SUCCESS;
	}
	
	/**
	* @Description
	* @Author：pingxianghua 编辑答复，答复意见
	* @Date 2013-7-25 上午9:37:32 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String findSuggestReplyById(){
		SuggestReplyVO svo = new SuggestReplyVO();
		if(suggestReply!=null){
			svo = suggestReplyPO2VO(suggestReply);
			getRequest().setAttribute("suggestReply", svo);
		}
		suggest=suggestManager.findSuggestById(sugid);
		if(repid!=null){
			suggestReply = suggestManager.findSuggestReplyById(repid);
			SuggestReplyVO suggestreplyvo = suggestReplyPO2VO(suggestReply);
			getRequest().setAttribute("suggestReply", suggestreplyvo);
		}
		SuggestVO suggestvo = suggestPO2VO(suggest);
		getRequest().setAttribute("suggest", suggestvo);
		return EDIT;
	}
	
	/**
	* @Description 删除指定ID的Suggest数据
	* @Author：pingxianghua
	* @Date 2013-7-23 下午1:57:41 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String delSuggest(){
		suggestManager.deleteSuggest(sugids);
		return SUCCESS;
	}
	
	/**
	* @Description 删除指定ID的SuggestReply数据
	* @Author：zhujiabiao
	* @Date 2013-7-23 下午1:57:41 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String delSuggestReply(){
		SuggestReply tempSuggestReply = suggestManager.findSuggestReplyById(repid);
		Long suggestionid = tempSuggestReply.getSuggestionid();
		suggestManager.deleteSuggestReply(repid);
		
		SuggestVO tempSuggestVO = null;
		
		try {
			tempSuggestVO = suggestManager.selectSuggestSuggestReplyListBySugid(suggestionid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getRequest().setAttribute("sug", tempSuggestVO);
		return SUCCESS;
	}
	
	public String suggestList(){
		logger.info("suggestList method invoked!");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("status", status);
		map.put("content", content);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		getRequest().setAttribute("startDate", startDate==null?"":sdf.format(startDate));
		getRequest().setAttribute("endDate", endDate==null?"":sdf.format(endDate));
		getRequest().setAttribute("status", status);
		getRequest().setAttribute("content", content);
		if(isAll!=null&&!isAll){
			Long userId = Long.valueOf(getRequest().getSession().getAttribute(Constants.USER_ID).toString());
			map.put("userid", userId);
		}
		page.setPageNo(pageNo);
		suggestPage= suggestManager.selectSuggestPageList(page, map);
		return LIST;
	}
	
	public String replyList(){
		logger.info("suggestList method invoked!");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("status", status);
		map.put("content", content);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		getRequest().setAttribute("startDate", startDate==null?"":sdf.format(startDate));
		getRequest().setAttribute("endDate", endDate==null?"":sdf.format(endDate));
		getRequest().setAttribute("status", status);
		getRequest().setAttribute("content", content);
		
		page.setPageNo(pageNo);
		suggestPage = suggestManager.selectSuggestPageList(page, map);
		if(suggestPage.getResult().size() == 0 && pageNo > 1){
			page.setPageNo(pageNo -1);
			suggestPage.setPageNo(page.getPageNo());
			suggestPage = suggestManager.selectSuggestPageList(page, map);
		}
		return LIST;
	}

	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	public SuggestReplyVO suggestReplyPO2VO(SuggestReply suggestPO){
		SuggestReplyVO suggestVO = new SuggestReplyVO();
		suggestVO.setRepid(suggestPO.getRepid());
		suggestVO.setSuggestionid(suggestPO.getSuggestionid());
		suggestVO.setStatus(suggestPO.getStatus());
		suggestVO.setUserid(suggestPO.getUserid());
		suggestVO.setContent(suggestPO.getContent());
		suggestVO.setCreatetimeString(DateUtil.date2Str(suggestPO.getCreatetime(), "yyyy-MM-dd HH:mm:ss"));
		suggestVO.setModifytimeString(DateUtil.date2Str(suggestPO.getModifytime(), "yyyy-MM-dd HH:mm:ss"));
		return suggestVO;
	}
	
	public SuggestVO suggestPO2VO(Suggest suggestPO){
		SuggestVO suggestVO = new SuggestVO();
		suggestVO.setSugid(suggestPO.getSugid());
		suggestVO.setStatus(suggestPO.getStatus());
		suggestVO.setUserid(suggestPO.getUserid());
		suggestVO.setContent(suggestPO.getContent());
		suggestVO.setCreatetimeString(DateUtil.date2Str(suggestPO.getCreatetime(), "yyyy-MM-dd HH:mm:ss"));
		return suggestVO;
	}
	
	//测试无线按钮的无线定位功能
	public void wireless() throws ParseException, SQLException
	{
		//String cardnum=getParameter("cardnum");
		String tagmac=wirelessManager.getqueryTagMacByIDCradNum("121321321");//根据证件号取tagmac
		//List sss = wirelessManager.getTest();
		//System.out.println(sss.size());
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		JSONArray ja = wirelessSqlManager.getqueryHistoryList(tagmac,sf.parse("2013-08-20 11:37:45"),new Date());
		JSONObject jo= wirelessManager.wirelessLocation("B0:8E:1A:01:00:3A");
		System.out.print(ja.toString());
		
	}
	
	public Object getModel() {
		return null;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Boolean getIsAll() {
		return isAll;
	}

	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}

	public Suggest getSuggest() {
		return suggest;
	}

	public void setSuggest(Suggest suggest) {
		this.suggest = suggest;
	}

	public String[] getSugids() {
		return sugids;
	}

	public void setSugids(String[] sugids) {
		this.sugids = sugids;
	}
	
	public SuggestReply getSuggestReply() {
		return suggestReply;
	}

	public void setSuggestReply(SuggestReply suggestReply) {
		this.suggestReply = suggestReply;
	}

	public SuggestManager getSuggestManager() {
		return suggestManager;
	}

	public void setSuggestManager(SuggestManager suggestManager) {
		this.suggestManager = suggestManager;
	}



	public Page<SuggestVO> getSuggestPage() {
		return suggestPage;
	}

	public void setSuggestPage(Page<SuggestVO> suggestPage) {
		this.suggestPage = suggestPage;
	}

	public Long getRepid() {
		return repid;
	}

	public void setRepid(Long repid) {
		this.repid = repid;
	}

	public Long getSugid() {
		return sugid;
	}

	public void setSugid(Long sugid) {
		this.sugid = sugid;
	}
	
	public Date getRepCreatetime() {
		return repCreatetime;
	}



	public void setRepCreatetime(Date repCreatetime) {
		this.repCreatetime = repCreatetime;
	}



	public Date getSugCreatetime() {
		return sugCreatetime;
	}



	public void setSugCreatetime(Date sugCreatetime) {
		this.sugCreatetime = sugCreatetime;
	}
}

