package com.cosmosource.app.suggest.vo;

import java.util.Date;

/** 
* @ClassName: SuggestReply 
* @Description: 意见回复实体类
* @author zhujiabiao
* @date 2013-7-23 下午02:25:19 
*  
*/
public class SuggestReplyVO{
	private Long repid;//回复id
	
	private Date createtime;//回复时间

	private Date modifytime;//修改时间
		
	private String content;//回复内容
	
	private String status;//
	
	private Long userid;//回复人id
	
	private Long suggestionid;//意见id	
	
	private String createtimeString;//创建时间字符串
	
	private String modifytimeString;//修改时间字符串
	
	private String displayName;//回复人姓名

	private String shortName;//部门处室名称

	public Long getRepid() {
		return repid;
	}

	public void setRepid(Long repid) {
		this.repid = repid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
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

	public Long getSuggestionid() {
		return suggestionid;
	}

	public void setSuggestionid(Long suggestionid) {
		this.suggestionid = suggestionid;
	}

	public String getCreatetimeString() {
		return createtimeString;
	}

	public void setCreatetimeString(String createtimeString) {
		this.createtimeString = createtimeString;
	}

	public String getModifytimeString() {
		return modifytimeString;
	}

	public void setModifytimeString(String modifytimeString) {
		this.modifytimeString = modifytimeString;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((repid == null) ? 0 : repid.hashCode());
		return result;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SuggestReplyVO other = (SuggestReplyVO) obj;
		if (repid == null) {
			if (other.repid != null)
				return false;
		} else if (!repid.equals(other.repid))
			return false;
		return true;
	}

}
