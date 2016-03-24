package com.cosmosource.common.entity;

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

@Entity
@Table(name = "T_COMMON_FILE_DOWNLOAD")
public class TCommonFileDownload implements java.io.Serializable {


	private static final long serialVersionUID = -4173069370892369289L;
	private Long downid;
	private String inputuser;
	private String softname;
	private String softtype;
	private String filesize;
	private String description;
	private Date releasedate;
	private Date uploaddate;
	private String softver;
	private Long downcount;
	private String outurl;
	private String environment;
	private String contact;
	private String status;
	private String filetype;
	private String company;
	private String isnetver;
	private String filename;
	private String orgtype;

	// Constructors

	/** default constructor */
	public TCommonFileDownload() {
	}

	/** minimal constructor */
	public TCommonFileDownload(Long downid) {
		this.downid = downid;
	}

	/** full constructor */
	public TCommonFileDownload(Long downid, String inputuser, String softname,
			String softtype, String filesize, String description,
			Date releasedate, Date uploaddate, String softver,
			Long downcount, String outurl, String environment,
			String contact, String status, String filetype, String company,
			String isnetver, String filename) {
		this.downid = downid;
		this.inputuser = inputuser;
		this.softname = softname;
		this.softtype = softtype;
		this.filesize = filesize;
		this.description = description;
		this.releasedate = releasedate;
		this.uploaddate = uploaddate;
		this.softver = softver;
		this.downcount = downcount;
		this.outurl = outurl;
		this.environment = environment;
		this.contact = contact;
		this.status = status;
		this.filetype = filetype;
		this.company = company;
		this.isnetver = isnetver;
		this.filename = filename;
	}

	// Property accessors
	@Id
	@Column(name = "DOWNID", unique = true, nullable = false, precision = 20, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_COMMON_FILE_DOWNLOAD")
	@SequenceGenerator(name="SEQ_COMMON_FILE_DOWNLOAD",allocationSize=1,initialValue=1, sequenceName="SEQ_COMMON_FILE_DOWNLOAD")
	public Long getDownid() {
		return this.downid;
	}

	public void setDownid(Long downid) {
		this.downid = downid;
	}

	@Column(name = "INPUTUSER", length = 50)
	public String getInputuser() {
		return this.inputuser;
	}

	public void setInputuser(String inputuser) {
		this.inputuser = inputuser;
	}

	@Column(name = "SOFTNAME")
	public String getSoftname() {
		return this.softname;
	}

	public void setSoftname(String softname) {
		this.softname = softname;
	}

	@Column(name = "SOFTTYPE", length = 2)
	public String getSofttype() {
		return this.softtype;
	}

	public void setSofttype(String softtype) {
		this.softtype = softtype;
	}

	@Column(name = "FILESIZE", length = 100)
	public String getFilesize() {
		return this.filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	@Column(name = "DESCRIPTION", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RELEASEDATE", length = 7)
	public Date getReleasedate() {
		return this.releasedate;
	}

	public void setReleasedate(Date releasedate) {
		this.releasedate = releasedate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPLOADDATE", length = 7)
	public Date getUploaddate() {
		return this.uploaddate;
	}

	public void setUploaddate(Date uploaddate) {
		this.uploaddate = uploaddate;
	}

	@Column(name = "SOFTVER", length = 20)
	public String getSoftver() {
		return this.softver;
	}

	public void setSoftver(String softver) {
		this.softver = softver;
	}

	@Column(name = "DOWNCOUNT", precision = 20, scale = 0)
	public Long getDowncount() {
		return this.downcount;
	}

	public void setDowncount(Long downcount) {
		this.downcount = downcount;
	}

	@Column(name = "OUTURL")
	public String getOuturl() {
		return this.outurl;
	}

	public void setOuturl(String outurl) {
		this.outurl = outurl;
	}

	@Column(name = "ENVIRONMENT")
	public String getEnvironment() {
		return this.environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	@Column(name = "CONTACT", length = 20)
	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "FILETYPE", length = 2)
	public String getFiletype() {
		return this.filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	@Column(name = "COMPANY", length = 40)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "ISNETVER", length = 1)
	public String getIsnetver() {
		return this.isnetver;
	}

	public void setIsnetver(String isnetver) {
		this.isnetver = isnetver;
	}

	@Column(name = "FILENAME")
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	@Column(name = "ORGTYPE", length = 1)
	public String getOrgtype() {
		return this.orgtype;
	}

	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}
}