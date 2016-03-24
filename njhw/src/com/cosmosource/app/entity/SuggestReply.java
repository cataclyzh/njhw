package com.cosmosource.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/** 
* @ClassName: SuggestReply 
* @Description: 意见回复实体类
* @author zhujiabiao
* @date 2013-7-23 下午02:25:19 
*  
*/
@SuppressWarnings("serial")
@Entity
@Table(name = "T_COMMON_SUG_REPLY")
public class SuggestReply implements java.io.Serializable {
	private Long repid;//回复id
	
	private Date createtime;//回复时间

	private Date modifytime;//修改时间
		
	private String content;//回复内容
	
	private String status;//
	
	private Long userid;//回复人id
	
	private Long suggestionid;//意见id	

	@Id
	@Column(name = "REPID", unique = true, nullable = false, precision = 22, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_SUGGEST_REPLY")
	@SequenceGenerator(name="SEQ_SUGGEST_REPLY",allocationSize=1,initialValue=1, sequenceName="SEQ_SUGGEST_REPLY")
	public Long getRepid() {
		return repid;
	}

	public void setRepid(Long repid) {
		this.repid = repid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFYTIME")
	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	@Column(name = "CONTENT", nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "USERID", precision = 12, scale = 0)
	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Column(name = "SUGGESTIONID", precision = 12, scale = 0)
	public Long getSuggestionid() {
		return suggestionid;
	}

	public void setSuggestionid(Long suggestionid) {
		this.suggestionid = suggestionid;
	}

}
