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
* @ClassName: Suggest 
* @Description: 意见实体类
* @author zhujiabiao
* @date 2013-7-23 下午02:25:00 
*  
*/
@SuppressWarnings("serial")
@Entity
@Table(name = "T_COMMON_SUGGESTION")
public class Suggest implements java.io.Serializable{
	
	private Long sugid;//意见id
	
	private Date createtime;//创建时间
	
	private String content;//意见内容
	
	private String status;//‘0’未回复，‘1’ 已回复
	
	private Long userid;//意见发布人id

	@Id
	@Column(name = "SUGID", unique = true, nullable = false, precision = 22, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_SUGGEST")
	@SequenceGenerator(name="SEQ_SUGGEST",allocationSize=1,initialValue=1, sequenceName="SEQ_SUGGEST")
	public Long getSugid() {
		return sugid;
	}

	public void setSugid(Long sugid) {
		this.sugid = sugid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME", nullable = false)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "CONTENT", nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "STATUS", nullable = false)
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
	
}
