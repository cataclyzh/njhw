package com.cosmosource.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 定位卡
 * @author zhujiabiao
 *
 */
@Entity
@Table(name="VIS_RFIDANDID")
public class VisRfidandid {
	public Long visId;//id
	public String tagmac;//卡号mac
	public String idcradnum;//定位卡号
	public String status;//是否启用 1 启用  0 停用
	public String tagnickname;//定位卡别名
	
	@Id
	@Column(name = "VISID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_vis_rfidandid")
	@SequenceGenerator(name="seq_vis_rfidandid",allocationSize=1,initialValue=1, sequenceName="seq_vis_rfidandid")
	public Long getVisId() {
		return visId;
	}
	public void setVisId(Long visId) {
		this.visId = visId;
	}
	
	@Column(name = "TAGMAC")
	public String getTagmac() {
		return tagmac;
	}
	public void setTagmac(String tagmac) {
		this.tagmac = tagmac;
	}
	
	
	public String getIdcradnum() {
		return idcradnum;
	}
	public void setIdcradnum(String idcradnum) {
		this.idcradnum = idcradnum;
	}
	
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "TAGNICKNAME")
	public String getTagnickname() {
		return tagnickname;
	}
	public void setTagnickname(String tagnickname) {
		this.tagnickname = tagnickname;
	}
	
}
