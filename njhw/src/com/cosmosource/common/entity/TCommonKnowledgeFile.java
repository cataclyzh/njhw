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
 * TCommonKnowledgeFile entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_COMMON_KNOWLEDGE_FILE")
public class TCommonKnowledgeFile implements java.io.Serializable {

	private static final long serialVersionUID = -6328173356896826131L;
	
	// Fields
	private Long fileId;
	private Blob fileContent;
	private String fileName;
	private Long fileSize;
	private String fileExtension;
	private String knowledgeId;

	// Constructors

	/** default constructor */
	public TCommonKnowledgeFile() {
	}

	/** minimal constructor */
	public TCommonKnowledgeFile(Long fileId) {
		this.fileId = fileId;
	}

	/** full constructor */
	public TCommonKnowledgeFile(Long fileId, Blob fileContent,
			String fileName, Long fileSize, String fileExtension,
			String knowledgeId) {
		this.fileId = fileId;
		this.fileContent = fileContent;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileExtension = fileExtension;
		this.knowledgeId = knowledgeId;
	}

	// Property accessors
	@Id
	@Column(name = "FILE_ID", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_COMMON_KNOWLEDGE_FILE")
	@SequenceGenerator(name="SEQ_COMMON_KNOWLEDGE_FILE",allocationSize=1,initialValue=1, sequenceName="SEQ_COMMON_KNOWLEDGE_FILE")
	public Long getFileId() {
		return this.fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	@Column(name = "FILE_CONTENT", columnDefinition = "BLOB", nullable=true)
	public Blob getFileContent() {
		return this.fileContent;
	}

	public void setFileContent(Blob fileContent) {
		this.fileContent = fileContent;
	}

	@Column(name = "FILE_NAME", length = 50)
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

	@Column(name = "KNOWLEDGE_ID", length = 20)
	public String getKnowledgeId() {
		return this.knowledgeId;
	}

	public void setKnowledgeId(String knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

}