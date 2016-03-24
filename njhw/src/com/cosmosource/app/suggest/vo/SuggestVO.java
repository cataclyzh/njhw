package com.cosmosource.app.suggest.vo;

import java.util.Date;
import java.util.List;


/** 
* @ClassName: Suggest 
* @Description: 意见实体类
* @author zhujiabiao
* @date 2013-7-23 下午02:25:00 
*  
*/
public class SuggestVO{
	
	private Long sugid;//意见id
	
	private Date createtime;//创建时间
	
	private String content;//意见内容
	
	private String status;//‘0’未回复，‘1’ 已回复
	
	private Long userid;//意见发布人id
	
	private String createtimeString;//创建时间字符串
	
	private List<SuggestReplyVO> suggestReplyVOList;//回复列表

	public Long getSugid() {
		return sugid;
	}

	public void setSugid(Long sugid) {
		this.sugid = sugid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public List<SuggestReplyVO> getSuggestReplyVOList() {
		return suggestReplyVOList;
	}

	public void setSuggestReplyVOList(List<SuggestReplyVO> suggestReplyVOList) {
		this.suggestReplyVOList = suggestReplyVOList;
	}

	public String getCreatetimeString() {
		return createtimeString;
	}

	public void setCreatetimeString(String createtimeString) {
		this.createtimeString = createtimeString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sugid == null) ? 0 : sugid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SuggestVO other = (SuggestVO) obj;
		if (sugid == null) {
			if (other.sugid != null)
				return false;
		} else if (!sugid.equals(other.sugid))
			return false;
		return true;
	}
	
	
	
}
