package com.cosmosource.app.suggest.service;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.app.entity.Suggest;
import com.cosmosource.app.entity.SuggestReply;
import com.cosmosource.app.suggest.vo.SuggestReplyVO;
import com.cosmosource.app.suggest.vo.SuggestVO;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;



public class SuggestManager extends BaseManager {
	private static final Log log = LogFactory.getLog(SuggestManager.class);

	/**
	 * @Description 根据ID 修改意见
	 * @Author：pingxianghua
	 * @Date 2013-7-23 下午2:57:59
	 * @param
	 * @return
	 * @throws Exception
	 * @version V1.0
	 **/
	public void saveOrUpdateSuggest(Suggest entity) {
		dao.saveOrUpdate(entity);
	}

	/**
	* @Description 根据ID 修改意见箱回复
	* @Author：pingxianghua
	* @Date 2013-7-23 下午2:58:29 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public void saveOrUpdateSuggestReply(SuggestReply entity) {
		dao.saveOrUpdate(entity);
	}
	

	 /**
	* @Description 根据意见回复id  查询意见回复
	* @Author：zhujiabiao
	* @Date 2013-7-23 下午03:18:24 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public SuggestReply findSuggestReplyById(Long repid) {
		return (SuggestReply)dao.findUniqueBy(SuggestReply.class, "repid", repid);
	}

	 /**
	* @Description 根据意见id 查询意见
	* @Author：zhujiabiao
	* @Date 2013-7-23 下午03:19:28 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public Suggest findSuggestById(Long sugid) {
		return (Suggest)dao.findUniqueBy(Suggest.class, "sugid", sugid);
	}
	
	 /**
	* @Description 查询意见分页列表
	* @Author：zhujiabiao
	* @Date 2013-7-23 下午04:26:44 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	 * @throws ParseException 
	**/
	public Page<SuggestVO> selectSuggestPageList(Page<HashMap> page,Map condition){
		page =  sqlDao.findPage(page, "SuggestSql.selectSuggestPageList", condition);
		List<HashMap> l = page.getResult();
		List<SuggestVO> suggestList = new ArrayList<SuggestVO>();
		Set<SuggestVO> suggestSet = new HashSet<SuggestVO>();
		Map map = null;
		Iterator<HashMap> i = l.iterator();
		while(i.hasNext()){
			map = i.next();
			
			//解析回复
			Long suggestionid = map.get("SUGGESTIONID")!=null?Long.valueOf(map.get("SUGGESTIONID").toString()):null;
			String repcontent = map.get("REPCONTENT")!=null?map.get("REPCONTENT").toString():null;
			String  repcreatetime = map.get("REPCREATETIME")!=null?map.get("REPCREATETIME").toString():null;
			String repmodifytime = map.get("REPMODIFYTIME")!=null?map.get("REPMODIFYTIME").toString():null;
			Long repid = map.get("REPID")!=null?Long.valueOf(map.get("REPID").toString()):null;
			Long repuserid = map.get("REPUSERID")!=null?Long.valueOf(map.get("REPUSERID").toString()):null;
			String displayName = map.get("DISPLAYNAME")!=null?map.get("DISPLAYNAME").toString():null;
			String shortName = map.get("SHORTNAME")!=null?map.get("SHORTNAME").toString():null;
			SuggestReplyVO tempSuggestReplyVO = new SuggestReplyVO();
			
			tempSuggestReplyVO.setContent(repcontent);
			tempSuggestReplyVO.setCreatetimeString(repcreatetime);
			tempSuggestReplyVO.setModifytimeString(repmodifytime);
			tempSuggestReplyVO.setRepid(repid);
			tempSuggestReplyVO.setSuggestionid(suggestionid);
			tempSuggestReplyVO.setUserid(repuserid);
			tempSuggestReplyVO.setDisplayName(displayName);
			tempSuggestReplyVO.setShortName(shortName);
			//解析意见
			String status = map.get("STATUS")!=null?map.get("STATUS").toString():null;
			String content = map.get("CONTENT")!=null?map.get("CONTENT").toString():null;
			Long sugid = map.get("SUGID")!=null?Long.valueOf(map.get("SUGID").toString()):null;
			Long userid = map.get("USERID")!=null?Long.valueOf(map.get("USERID").toString()):null;
			String createtime = map.get("SUGCREATETIME")!=null?map.get("SUGCREATETIME").toString():null;
			
			SuggestVO tempSuggestVO = new SuggestVO();
			tempSuggestVO.setContent(content);
			tempSuggestVO.setCreatetimeString(createtime);
			tempSuggestVO.setStatus(status);
			tempSuggestVO.setSugid(sugid);
			tempSuggestVO.setUserid(userid);
			if(!suggestSet.contains(tempSuggestVO)){
				suggestSet.add(tempSuggestVO);
				if(tempSuggestReplyVO.getRepid()!=null){
					List<SuggestReplyVO> tempSuggestReplyVOList = tempSuggestVO.getSuggestReplyVOList();
					if(tempSuggestReplyVOList==null){
						tempSuggestVO.setSuggestReplyVOList(new ArrayList<SuggestReplyVO>());
					}
					tempSuggestVO.getSuggestReplyVOList().add(tempSuggestReplyVO);
				}
				suggestList.add(tempSuggestVO);
			}else{
				for(SuggestVO sv:suggestSet){
					if(sv.equals(tempSuggestVO)){
						tempSuggestVO = sv;
						break;
					}
				}
				if(tempSuggestReplyVO.getRepid()!=null){
					List<SuggestReplyVO> tempSuggestReplyVOList = tempSuggestVO.getSuggestReplyVOList();
					if(tempSuggestReplyVOList==null){
						tempSuggestVO.setSuggestReplyVOList(new ArrayList<SuggestReplyVO>());
					}
					tempSuggestVO.getSuggestReplyVOList().add(tempSuggestReplyVO);
				}
			}
			
		}
		//page.setResult(suggestList);
		
		Page<SuggestVO> suggestPage = new Page<SuggestVO>();
		suggestPage.setPageNo(page.getPageNo());
		suggestPage.setPageSize(page.getPageSize());
		suggestPage.setResult(suggestList);
		suggestPage.setTogglestatus(page.getTogglestatus());
		suggestPage.setTotalCount(page.getTotalCount());
		suggestPage.setTotalPages(page.getTotalPages());
		suggestPage.setJumpNumber(page.getJumpNumber());
		
		return suggestPage;
	}
	
	
	 /**
	* @Description 查询意见及意见回复
	* @Author：zhujiabiao
	* @Date 2013-7-28 下午04:26:44 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	 * @throws ParseException 
	**/
	public SuggestVO selectSuggestSuggestReplyListBySugid(Long sugId){
		Map<String,Long> mapcon = new HashMap<String,Long>();
		mapcon.put("sugid", sugId);
		List<HashMap> list = sqlDao.findList("SuggestSql.selectSuggestSuggestReplyListBySugid", mapcon);
		List<SuggestVO> suggestList = new ArrayList<SuggestVO>();
		Set<SuggestVO> suggestSet = new HashSet<SuggestVO>();
		Iterator<HashMap> i = list.iterator();
		Map map = null;
		while(i.hasNext()){
			map = i.next();
			
			//解析回复
			Long suggestionid = map.get("SUGGESTIONID")!=null?Long.valueOf(map.get("SUGGESTIONID").toString()):null;
			String repcontent = map.get("REPCONTENT")!=null?map.get("REPCONTENT").toString():null;
			String  repcreatetime = map.get("REPCREATETIME")!=null?map.get("REPCREATETIME").toString():null;
			String repmodifytime = map.get("REPMODIFYTIME")!=null?map.get("REPMODIFYTIME").toString():null;
			Long repid = map.get("REPID")!=null?Long.valueOf(map.get("REPID").toString()):null;
			Long repuserid = map.get("REPUSERID")!=null?Long.valueOf(map.get("REPUSERID").toString()):null;
			String displayName = map.get("DISPLAYNAME")!=null?map.get("DISPLAYNAME").toString():null;
			String shortName = map.get("SHORTNAME")!=null?map.get("SHORTNAME").toString():null;
			SuggestReplyVO tempSuggestReplyVO = new SuggestReplyVO();
			
			tempSuggestReplyVO.setContent(repcontent);
			tempSuggestReplyVO.setCreatetimeString(repcreatetime);
			tempSuggestReplyVO.setModifytimeString(repmodifytime);
			tempSuggestReplyVO.setRepid(repid);
			tempSuggestReplyVO.setSuggestionid(suggestionid);
			tempSuggestReplyVO.setUserid(repuserid);
			tempSuggestReplyVO.setDisplayName(displayName);
			tempSuggestReplyVO.setShortName(shortName);
			
			//解析意见
			String status = map.get("STATUS")!=null?map.get("STATUS").toString():null;
			String content = map.get("CONTENT")!=null?map.get("CONTENT").toString():null;
			Long sugid = map.get("SUGID")!=null?Long.valueOf(map.get("SUGID").toString()):null;
			Long userid = map.get("USERID")!=null?Long.valueOf(map.get("USERID").toString()):null;
			String createtime = map.get("SUGCREATETIME")!=null?map.get("SUGCREATETIME").toString():null;
			
			SuggestVO tempSuggestVO = new SuggestVO();
			tempSuggestVO.setContent(content);
			tempSuggestVO.setCreatetimeString(createtime);
			tempSuggestVO.setStatus(status);
			tempSuggestVO.setSugid(sugid);
			tempSuggestVO.setUserid(userid);
			if(!suggestSet.contains(tempSuggestVO)){
				suggestSet.add(tempSuggestVO);
				if(tempSuggestReplyVO.getRepid()!=null){
					List<SuggestReplyVO> tempSuggestReplyVOList = tempSuggestVO.getSuggestReplyVOList();
					if(tempSuggestReplyVOList==null){
						tempSuggestVO.setSuggestReplyVOList(new ArrayList<SuggestReplyVO>());
					}
					tempSuggestVO.getSuggestReplyVOList().add(tempSuggestReplyVO);
				}
				suggestList.add(tempSuggestVO);
			}else{
				for(SuggestVO sv:suggestSet){
					if(sv.equals(tempSuggestVO)){
						tempSuggestVO = sv;
						break;
					}
				}
				if(tempSuggestReplyVO.getRepid()!=null){
					List<SuggestReplyVO> tempSuggestReplyVOList = tempSuggestVO.getSuggestReplyVOList();
					if(tempSuggestReplyVOList==null){
						tempSuggestVO.setSuggestReplyVOList(new ArrayList<SuggestReplyVO>());
					}
					tempSuggestVO.getSuggestReplyVOList().add(tempSuggestReplyVO);
				}
			}
			
		}
		
		if(suggestList!=null&&suggestList.size()>0){
			return suggestList.get(0);
		}else{
			return null;
		}
	}
	
	 /**
	* @Description 根据ID 删除意见箱意见和回复
	* @Author：wangzhenglu
	* @Date 2013-7-23 下午03:13:15 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public void deleteSuggest(String[] sugids) {
		List<String> slist = Arrays.asList(sugids);
	    sqlDao.batchDelete("SuggestSql.delByIds", slist);
		sqlDao.batchDelete("SuggestSql.delByIdrs", slist);
	}
	
	public void deleteSuggestReply(Long repid){
		if(repid!=null){
			SuggestReply suggestReply = findSuggestReplyById(repid);
			Suggest suggest = findSuggestById(suggestReply.getSuggestionid());
			List<SuggestReply> suggestReplyList = findSuggestReplyListBySugid(suggestReply.getSuggestionid());
			if(suggestReplyList!=null&&suggestReplyList.size()==1){
				suggest.setStatus("0");
				dao.update(suggest);
			}
			dao.deleteById(SuggestReply.class, repid);
		}
	}
	
	public List<SuggestReply> findSuggestReplyListBySugid(Long sugid){
		return (List<SuggestReply>)dao.findByHQL("from SuggestReply sr where sr.suggestionid = ?", sugid);
	}
}
