package com.cosmosource.common.entity;

import java.sql.Blob;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_AC_CAAPPLY")
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class TAcCaapply implements java.io.Serializable {
	private static final long serialVersionUID = 8353664563475400449L;
	private Long caid;
	private String applyuser;
	private Date applydate;
	private String applyno;
	private Long applynum;
	private String caterm;
	private String catype;
	private String applytype;
	private String cadn;
	private String orgname;
	private String orgnameen;
	private String orgidtype;
	private String orgidname;
	private String orgidnum;
	private String handler;
	private String handleridtype;
	private String handleridnum;
	private String handlertel;
	private String handlerfax;
	private String handlermail;
	private String handleraddr;
	private String handlerpostcode;
	private Blob handlerstamp;
	private Date stampdate;
	private String delstatus;
	private String audituser;
	private Date auditdate;
	private String auditstatus;
	private String auditdesc;
	private String gencauser;
	private Date gencadate;
	private String isgenca;
	private String gencadesc;
	private String issubmit;
	private String stepcode;
	// Constructors

	/** default constructor */
	public TAcCaapply() {
	}

	/** minimal constructor */
	public TAcCaapply(Long caid) {
		this.caid = caid;
	}

	/** full constructor */
	public TAcCaapply(Long caid, String applyuser, Date applydate,
			String applyno, Long applynum, String caterm, String catype,
			String applytype, String cadn, String orgname, String orgnameen,
			String orgidtype, String orgidname, String orgidnum,
			String handler, String handleridtype, String handleridnum,
			String handlertel, String handlerfax, String handlermail,
			String handleraddr, String handlerpostcode, Blob handlerstamp,
			Date stampdate, String delstatus, String audituser, Date auditdate,
			String auditstatus, String auditdesc, String gencauser,
			Date gencadate, String isgenca, String gencadesc,String issubmit,
			String stepcode) {
		this.caid = caid;
		this.applyuser = applyuser;
		this.applydate = applydate;
		this.applyno = applyno;
		this.applynum = applynum;
		this.caterm = caterm;
		this.catype = catype;
		this.applytype = applytype;
		this.cadn = cadn;
		this.orgname = orgname;
		this.orgnameen = orgnameen;
		this.orgidtype = orgidtype;
		this.orgidname = orgidname;
		this.orgidnum = orgidnum;
		this.handler = handler;
		this.handleridtype = handleridtype;
		this.handleridnum = handleridnum;
		this.handlertel = handlertel;
		this.handlerfax = handlerfax;
		this.handlermail = handlermail;
		this.handleraddr = handleraddr;
		this.handlerpostcode = handlerpostcode;
		this.handlerstamp = handlerstamp;
		this.stampdate = stampdate;
		this.delstatus = delstatus;
		this.audituser = audituser;
		this.auditdate = auditdate;
		this.auditstatus = auditstatus;
		this.auditdesc = auditdesc;
		this.gencauser = gencauser;
		this.gencadate = gencadate;
		this.isgenca = isgenca;
		this.gencadesc = gencadesc;
		this.issubmit = issubmit;
		this.stepcode = stepcode;
	}

	// Property accessors
	@Id
	@Column(name = "CAID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getCaid() {
		return this.caid;
	}

	public void setCaid(Long caid) {
		this.caid = caid;
	}

	@Column(name = "APPLYUSER", length = 40)
	public String getApplyuser() {
		return this.applyuser;
	}

	public void setApplyuser(String applyuser) {
		this.applyuser = applyuser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPLYDATE", length = 7)
	public Date getApplydate() {
		return this.applydate;
	}

	public void setApplydate(Date applydate) {
		this.applydate = applydate;
	}

	@Column(name = "APPLYNO", length = 40)
	public String getApplyno() {
		return this.applyno;
	}

	public void setApplyno(String applyno) {
		this.applyno = applyno;
	}

	@Column(name = "APPLYNUM", precision = 22, scale = 0)
	public Long getApplynum() {
		return this.applynum;
	}

	public void setApplynum(Long applynum) {
		this.applynum = applynum;
	}

	@Column(name = "CATERM", length = 1)
	public String getCaterm() {
		return this.caterm;
	}

	public void setCaterm(String caterm) {
		this.caterm = caterm;
	}

	@Column(name = "CATYPE", length = 1)
	public String getCatype() {
		return this.catype;
	}

	public void setCatype(String catype) {
		this.catype = catype;
	}

	@Column(name = "APPLYTYPE", length = 1)
	public String getApplytype() {
		return this.applytype;
	}

	public void setApplytype(String applytype) {
		this.applytype = applytype;
	}

	@Column(name = "CADN", length = 100)
	public String getCadn() {
		return this.cadn;
	}

	public void setCadn(String cadn) {
		this.cadn = cadn;
	}

	@Column(name = "ORGNAME", length = 150)
	public String getOrgname() {
		return this.orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	@Column(name = "ORGNAMEEN", length = 150)
	public String getOrgnameen() {
		return this.orgnameen;
	}

	public void setOrgnameen(String orgnameen) {
		this.orgnameen = orgnameen;
	}

	@Column(name = "ORGIDTYPE", length = 1)
	public String getOrgidtype() {
		return this.orgidtype;
	}

	public void setOrgidtype(String orgidtype) {
		this.orgidtype = orgidtype;
	}

	@Column(name = "ORGIDNAME", length = 150)
	public String getOrgidname() {
		return this.orgidname;
	}

	public void setOrgidname(String orgidname) {
		this.orgidname = orgidname;
	}

	@Column(name = "ORGIDNUM", length = 40)
	public String getOrgidnum() {
		return this.orgidnum;
	}

	public void setOrgidnum(String orgidnum) {
		this.orgidnum = orgidnum;
	}

	@Column(name = "HANDLER", length = 40)
	public String getHandler() {
		return this.handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	@Column(name = "HANDLERIDTYPE", length = 1)
	public String getHandleridtype() {
		return this.handleridtype;
	}

	public void setHandleridtype(String handleridtype) {
		this.handleridtype = handleridtype;
	}

	@Column(name = "HANDLERIDNUM", length = 40)
	public String getHandleridnum() {
		return this.handleridnum;
	}

	public void setHandleridnum(String handleridnum) {
		this.handleridnum = handleridnum;
	}

	@Column(name = "HANDLERTEL", length = 40)
	public String getHandlertel() {
		return this.handlertel;
	}

	public void setHandlertel(String handlertel) {
		this.handlertel = handlertel;
	}

	@Column(name = "HANDLERFAX", length = 40)
	public String getHandlerfax() {
		return this.handlerfax;
	}

	public void setHandlerfax(String handlerfax) {
		this.handlerfax = handlerfax;
	}

	@Column(name = "HANDLERMAIL", length = 40)
	public String getHandlermail() {
		return this.handlermail;
	}

	public void setHandlermail(String handlermail) {
		this.handlermail = handlermail;
	}

	@Column(name = "HANDLERADDR", length = 200)
	public String getHandleraddr() {
		return this.handleraddr;
	}

	public void setHandleraddr(String handleraddr) {
		this.handleraddr = handleraddr;
	}

	@Column(name = "HANDLERPOSTCODE", length = 10)
	public String getHandlerpostcode() {
		return this.handlerpostcode;
	}

	public void setHandlerpostcode(String handlerpostcode) {
		this.handlerpostcode = handlerpostcode;
	}

	@Column(name = "HANDLERSTAMP", columnDefinition = "BLOB", nullable=true)
	public Blob getHandlerstamp() {
		return this.handlerstamp;
	}

	public void setHandlerstamp(Blob handlerstamp) {
		this.handlerstamp = handlerstamp;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STAMPDATE", length = 7)
	public Date getStampdate() {
		return this.stampdate;
	}

	public void setStampdate(Date stampdate) {
		this.stampdate = stampdate;
	}

	@Column(name = "DELSTATUS", length = 1)
	public String getDelstatus() {
		return this.delstatus;
	}

	public void setDelstatus(String delstatus) {
		this.delstatus = delstatus;
	}

	@Column(name = "AUDITUSER", length = 40)
	public String getAudituser() {
		return this.audituser;
	}

	public void setAudituser(String audituser) {
		this.audituser = audituser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUDITDATE", length = 7)
	public Date getAuditdate() {
		return this.auditdate;
	}

	public void setAuditdate(Date auditdate) {
		this.auditdate = auditdate;
	}

	@Column(name = "AUDITSTATUS", length = 1)
	public String getAuditstatus() {
		return this.auditstatus;
	}

	public void setAuditstatus(String auditstatus) {
		this.auditstatus = auditstatus;
	}

	@Column(name = "AUDITDESC", length = 200)
	public String getAuditdesc() {
		return this.auditdesc;
	}

	public void setAuditdesc(String auditdesc) {
		this.auditdesc = auditdesc;
	}

	@Column(name = "GENCAUSER", length = 40)
	public String getGencauser() {
		return this.gencauser;
	}

	public void setGencauser(String gencauser) {
		this.gencauser = gencauser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "GENCADATE", length = 7)
	public Date getGencadate() {
		return this.gencadate;
	}

	public void setGencadate(Date gencadate) {
		this.gencadate = gencadate;
	}

	@Column(name = "ISGENCA", length = 1)
	public String getIsgenca() {
		return this.isgenca;
	}

	public void setIsgenca(String isgenca) {
		this.isgenca = isgenca;
	}

	@Column(name = "GENCADESC", length = 100)
	public String getGencadesc() {
		return this.gencadesc;
	}

	public void setGencadesc(String gencadesc) {
		this.gencadesc = gencadesc;
	}
	@Column(name = "ISSUBMIT", length = 1)
	public String getIssubmit() {
		return this.issubmit;
	}

	public void setIssubmit(String issubmit) {
		this.issubmit = issubmit;
	}

	@Column(name = "STEPCODE", length = 10)
	public String getStepcode() {
		return stepcode;
	}

	public void setStepcode(String stepcode) {
		this.stepcode = stepcode;
	}
	
}