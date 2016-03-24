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
 * ExtOutFaxAttach entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EXT_OUT_FAX_ATTACH")
public class ExtOutFaxAttach implements java.io.Serializable {

	// Fields

	private Long eofaId;
	private Long eofcId;
	private String eofaFname;
	private String eofaFtype;
	private String eofaDesc;
	private String exp1;
	private String exp2;
	private String exp3;
	private String exp4;
	private String exp5;
	private Long insertId;
	private Date insertDate;

	// Constructors

	/** default constructor */
	public ExtOutFaxAttach() {
	}

	/** minimal constructor */
	public ExtOutFaxAttach(Long eofaId) {
		this.eofaId = eofaId;
	}

	/** full constructor */
	public ExtOutFaxAttach(Long eofaId, Long eofcId,
			String eofaFname, String eofaFtype, String eofaDesc, String exp1,
			String exp2, String exp3, String exp4, String exp5, Long insertId,
			Date insertDate) {
		this.eofaId = eofaId;
		this.eofcId = eofcId;
		this.eofaFname = eofaFname;
		this.eofaFtype = eofaFtype;
		this.eofaDesc = eofaDesc;
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.exp3 = exp3;
		this.exp4 = exp4;
		this.exp5 = exp5;
		this.insertId = insertId;
		this.insertDate = insertDate;
	}

	// Property accessors
	@Id
	@Column(name = "EOFA_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_EXT_OUT_FAX_ATTACH")
	@SequenceGenerator(name="SEQ_EXT_OUT_FAX_ATTACH",allocationSize=1,initialValue=1, sequenceName="SEQ_EXT_OUT_FAX_ATTACH")
	public Long getEofaId() {
		return this.eofaId;
	}

	public void setEofaId(Long eofaId) {
		this.eofaId = eofaId;
	}

	@Column(name = "EOFC_ID",precision = 12, scale = 0)
	public Long getEofcId() {
		return eofcId;
	}

	public void setEofcId(Long eofcId) {
		this.eofcId = eofcId;
	}

	@Column(name = "EOFA_FNAME", length = 50)
	public String getEofaFname() {
		return this.eofaFname;
	}

	public void setEofaFname(String eofaFname) {
		this.eofaFname = eofaFname;
	}

	@Column(name = "EOFA_FTYPE", length = 1)
	public String getEofaFtype() {
		return this.eofaFtype;
	}

	public void setEofaFtype(String eofaFtype) {
		this.eofaFtype = eofaFtype;
	}

	@Column(name = "EOFA_DESC")
	public String getEofaDesc() {
		return this.eofaDesc;
	}

	public void setEofaDesc(String eofaDesc) {
		this.eofaDesc = eofaDesc;
	}

	@Column(name = "EXP1", length = 20)
	public String getExp1() {
		return this.exp1;
	}

	public void setExp1(String exp1) {
		this.exp1 = exp1;
	}

	@Column(name = "EXP2", length = 20)
	public String getExp2() {
		return this.exp2;
	}

	public void setExp2(String exp2) {
		this.exp2 = exp2;
	}

	@Column(name = "EXP3", length = 20)
	public String getExp3() {
		return this.exp3;
	}

	public void setExp3(String exp3) {
		this.exp3 = exp3;
	}

	@Column(name = "EXP4", length = 20)
	public String getExp4() {
		return this.exp4;
	}

	public void setExp4(String exp4) {
		this.exp4 = exp4;
	}

	@Column(name = "EXP5", length = 20)
	public String getExp5() {
		return this.exp5;
	}

	public void setExp5(String exp5) {
		this.exp5 = exp5;
	}

	@Column(name = "INSERT_ID", precision = 12, scale = 0)
	public Long getInsertId() {
		return this.insertId;
	}

	public void setInsertId(Long insertId) {
		this.insertId = insertId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INSERT_DATE", length = 7)
	public Date getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

}