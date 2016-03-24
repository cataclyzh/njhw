package com.cosmosource.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.cosmosource.base.dao.AuditableEntity;


/**
 * TAcMenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_AC_MENU")
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class TAcMenu extends AuditableEntity implements HibernateTree,java.io.Serializable {

	private static final long serialVersionUID = 4792359764354327668L;
	private Long menuid;
	private String menucode;
	private Long parentid;
	private Long orgid;
	private String menuname;
	private String menulabel;
	private String menuaction;
	private Long treelevel;
	private String menudesc;
	private String isfunc;
	private String isbottom;
	private Long subsysid;
	private String sortno;
	private String image;
	private String treelayer;

	// Constructors

	/** default constructor */
	public TAcMenu() {
	}

	/** minimal constructor */
	public TAcMenu(Long menuid, Long parentid, Long orgid, String menuname) {
		this.menuid = menuid;
		this.parentid = parentid;
		this.orgid = orgid;
		this.menuname = menuname;
	}

	/** full constructor */
	public TAcMenu(Long menuid, String menucode, Long parentid, Long orgid,
			String menuname, String menulabel, String menuaction,
			Long treelevel, String menudesc, String isfunc, String isbottom,
			Long subsysid, String sortno,
			String image,String treelayer) {
		this.menuid = menuid;
		this.menucode = menucode;
		this.parentid = parentid;
		this.orgid = orgid;
		this.menuname = menuname;
		this.menulabel = menulabel;
		this.menuaction = menuaction;
		this.treelevel = treelevel;
		this.menudesc = menudesc;
		this.isfunc = isfunc;
		this.isbottom = isbottom;
		this.subsysid = subsysid;
		this.sortno = sortno;
		this.image = image;
		this.treelayer = treelayer;
	}

	// Property accessors
	@Id
	@Column(name = "MENUID", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_AC_MENU")
	@SequenceGenerator(name="SEQ_AC_MENU",allocationSize=1,initialValue=1, sequenceName="SEQ_AC_MENU")
	public Long getMenuid() {
		return this.menuid;
	}

	public void setMenuid(Long menuid) {
		this.menuid = menuid;
	}

	@Column(name = "MENUCODE", length = 20)
	public String getMenucode() {
		return this.menucode;
	}

	public void setMenucode(String menucode) {
		this.menucode = menucode;
	}

	@Column(name = "PARENTID", nullable = false, precision = 10, scale = 0)
	public Long getParentid() {
		return this.parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	@Column(name = "ORGID", nullable = false, precision = 10, scale = 0)
	public Long getOrgid() {
		return this.orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	@Column(name = "MENUNAME", nullable = false, length = 40)
	public String getMenuname() {
		return this.menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	@Column(name = "MENULABEL", length = 40)
	public String getMenulabel() {
		return this.menulabel;
	}

	public void setMenulabel(String menulabel) {
		this.menulabel = menulabel;
	}

	@Column(name = "MENUACTION", length = 200)
	public String getMenuaction() {
		return this.menuaction;
	}

	public void setMenuaction(String menuaction) {
		this.menuaction = menuaction;
	}

	@Column(name = "MENULEVEL", precision = 10, scale = 0)
	public Long getTreelevel() {
		return this.treelevel;
	}

	public void setTreelevel(Long treelevel) {
		this.treelevel = treelevel;
	}

	@Column(name = "MENUDESC", length = 200)
	public String getMenudesc() {
		return this.menudesc;
	}

	public void setMenudesc(String menudesc) {
		this.menudesc = menudesc;
	}

	@Column(name = "ISFUNC", length = 1)
	public String getIsfunc() {
		return this.isfunc;
	}

	public void setIsfunc(String isfunc) {
		this.isfunc = isfunc;
	}

	@Column(name = "ISBOTTOM", length = 1)
	public String getIsbottom() {
		return this.isbottom;
	}

	public void setIsbottom(String isbottom) {
		this.isbottom = isbottom;
	}

	@Column(name = "SUBSYSID", precision = 10, scale = 0)
	public Long getSubsysid() {
		return this.subsysid;
	}

	public void setSubsysid(Long subsysid) {
		this.subsysid = subsysid;
	}

	@Column(name = "SORTNO", length = 10)
	public String getSortno() {
		return this.sortno;
	}

	public void setSortno(String sortno) {
		this.sortno = sortno;
	}

	@Column(name = "IMAGE", length = 200)
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	@Column(name = "MENULAYER", length = 400)
	public String getTreelayer() {
		return this.treelayer;
	}

	public void setTreelayer(String treelayer) {
		this.treelayer = treelayer;
	}
}