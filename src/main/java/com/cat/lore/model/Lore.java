package com.cat.lore.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import com.cat.boot.util.StringUtil;
import com.cat.system.model.Organ;
import com.cat.system.model.User;
import com.cat.task.model.ProcessEntity;

@Entity
@Table(name = "LORE_RECORD")
@BatchSize(size = 50)
@DynamicInsert
public class Lore extends ProcessEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2884007722007710246L;

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 40)
	private String id;

	/** 编号,相同编号为同一条知识 */
	@Column(length = 40)
	private String code;

	/** 标题 */
	@Column(length = 100)
	private String title;

	/** 内容 */
	@Lob
	@Column
	@Basic(fetch = FetchType.LAZY)
	private String content;

	/** 关键字 */
	@Column(length = 40)
	private String keyword;

	/** 主题 */
	@Column(length = 40)
	private String theme;

	/** 类型 */
	@Column(length = 40)
	private String lore_type;

	/** 版本 */
	@Column(length = 4)
	private String lore_version;

	/** 有效期 */
	@Column(length = 40)
	private String expiry_date;

	/** 评分 */
	@Column
	private Integer score;

	/** 最新版本 仅显示最新版本 */
	@Column
	private boolean latest = true;

	@Column
	private boolean recommend = false;

	@Column
	private String fileExt;

	/** 申请部门 */
	@Column(length = 40)
	private String org_id;

	/** 申请人 */
	@Column(length = 40)
	private String sqr_id;

	/** 申请时间 */
	@Column(length = 40)
	private String sqsj;

	@Transient
	private String sqr_name;

	@Transient
	private String org_name;

	@Transient
	private Boolean readOnly;

	@Transient
	private String lore_type_name;

	@Transient
	private boolean nofile;

	public boolean isNofile() {
		return StringUtil.isEmpty(fileExt);
	}

	public void setNofile(boolean nofile) {
		this.nofile = nofile;
	}

	public String getLore_type_name() {
		if (!StringUtil.isEmpty(lore_type)) {
			LoreType entity = (LoreType) getService().findById(LoreType.class, lore_type);
			if (null != entity) {
				return entity.getName();
			}
		}
		return lore_type_name;
	}

	public void setLore_type_name(String lore_type_name) {
		this.lore_type_name = lore_type_name;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getSqr_id() {
		return sqr_id;
	}

	public void setSqr_id(String sqr_id) {
		this.sqr_id = sqr_id;
	}

	public String getSqsj() {
		return sqsj;
	}

	public void setSqsj(String sqsj) {
		this.sqsj = sqsj;
	}

	public String getSqr_name() {
		if (!StringUtil.isEmpty(this.sqr_id)) {
			User o = (User) getService().findById(User.class, this.sqr_id);
			return o.getName();
		}
		return sqr_name;
	}

	public void setSqr_name(String sqr_name) {
		this.sqr_name = sqr_name;
	}

	public String getOrg_name() {
		if (!StringUtil.isEmpty(this.org_id)) {
			Organ o = (Organ) getService().findById(Organ.class, this.org_id);
			return o.getName();
		}
		return org_name;

	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public Boolean getReadOnly() {
		if (!StringUtil.isEmpty(id) && getStep() != 0) {
			return true;
		}
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public String getId() {
		return this.id;
	}

	public boolean isLatest() {
		return latest;
	}

	public void setLatest(boolean latest) {
		this.latest = latest;
	}

	public String getLore_type() {
		return lore_type;
	}

	public void setLore_type(String lore_type) {
		this.lore_type = lore_type;
	}

	public String getLore_version() {
		return lore_version;
	}

	public void setLore_version(String lore_version) {
		this.lore_version = lore_version;
	}

}
