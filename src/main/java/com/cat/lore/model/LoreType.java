package com.cat.lore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import com.cat.boot.model.BaseEntity;
import com.cat.boot.util.StringUtil;
import com.cat.dictionary.model.Dictionary;
import com.cat.system.model.Organ;

@Entity
@Table(name = "LORE_TYPE")
@BatchSize(size = 50)
@DynamicInsert
public class LoreType extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7902936867123415817L;

	@Id
	@GeneratedValue(generator = "assignedGenerator")
	@GenericGenerator(name = "assignedGenerator", strategy = "assigned")
	@Column(length = 40)
	private String id;

	/** 本级代码 current Level Code */
	@Column(length = 8, nullable = false)
	private String clc;

	/** 名称 */
	@Column(length = 40)
	private String name;

	/** 上级 */
	@Column(length = 40)
	private String sc_id;

	/** 末级 */
	@Column
	private boolean isLeaf = true;

	@Column(length = 40)
	private String user_id;

	/** 手机端显示图标 */
	@Column(length = 40)
	private String icon;

	@Column
	private Integer xh = 1;

	/** 1:公共类型 0:部门类型 */
	@Column(length = 40)
	private String all_organ;

	@Column(length = 40)
	private String organ_id;

	@Transient
	private String scName;

	@Transient
	private String organ_name;

	public String getOrgan_name() {
		if (!StringUtil.isEmpty(organ_id)) {
			Organ sup = (Organ) getService().findById(Organ.class, this.organ_id);
			if (null != sup) {
				return sup.getName();
			}
		}
		return organ_name;
	}

	public void setOrgan_name(String organ_name) {
		this.organ_name = organ_name;
	}

	public String getScName() {
		if (!StringUtil.isEmpty(sc_id)) {
			LoreType sup = (LoreType) getService().findById(LoreType.class, this.sc_id);
			if (null != sup) {
				return sup.getName();
			}
		}
		return scName;
	}

	public void setScName(String scName) {
		this.scName = scName;
	}

	public String getOrgan_id() {
		return organ_id;
	}

	public void setOrgan_id(String organ_id) {
		this.organ_id = organ_id;
	}

	public String getClc() {
		return clc;
	}

	public void setClc(String clc) {
		this.clc = clc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSc_id() {
		return sc_id;
	}

	public void setSc_id(String sc_id) {
		this.sc_id = sc_id;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getXh() {
		return xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}

	public String getAll_organ() {
		return all_organ;
	}

	public void setAll_organ(String all_organ) {
		this.all_organ = all_organ;
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
}