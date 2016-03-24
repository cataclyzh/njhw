package com.cosmosource.common.entity;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * TCommonVendorAnsfile entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_COMMON_VENDOR_ANSFILE")
public class TCommonVendorAnsfile implements java.io.Serializable {

	private static final long serialVersionUID = 5160989169125759292L;
	private Long fileId;
	private Blob fileContent;
	private String fileName;
	private Long fileSize;
	private String fileExtension;
	private Long ansId;

	// Constructors

	/** default constructor */
	public TCommonVendorAnsfile() {
	}

	/** minimal constructor */
	public TCommonVendorAnsfile(Long fileId) {
		this.fileId = fileId;
	}

	/** full constructor */
	public TCommonVendorAnsfile(Long fileId, Blob fileContent,
			String fileName, Long fileSize, String fileExtension, Long ansId) {
		this.fileId = fileId;
		this.fileContent = fileContent;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileExtension = fileExtension;
		this.ansId = ansId;
	}

	// Property accessors
	@Id
	@Column(name = "FILE_ID", unique = true, nullable = false, precision = 16, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_COMMON_VENDOR_ANSFILE")
	@SequenceGenerator(name="SEQ_COMMON_VENDOR_ANSFILE",allocationSize=1,initialValue=1, sequenceName="SEQ_COMMON_VENDOR_ANSFILE")
	public Long getFileId() {
		return this.fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	@Column(name = "FILE_CONTENT")
	public Blob getFileContent() {
		return this.fileContent;
	}

	public void setFileContent(Blob fileContent) {
		this.fileContent = fileContent;
	}

	@Column(name = "FILE_NAME", length = 60)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "FILE_SIZE", precision = 10, scale = 0)
	public Long getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name = "FILE_EXTENSION", length = 20)
	public String getFileExtension() {
		return this.fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	@Column(name = "ANS_ID", precision = 16, scale = 0)
	public Long getAnsId() {
		return this.ansId;
	}

	public void setAnsId(Long ansId) {
		this.ansId = ansId;
	}

}