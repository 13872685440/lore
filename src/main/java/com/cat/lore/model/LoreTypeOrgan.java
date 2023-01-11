package com.cat.lore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import com.cat.boot.model.BaseEntity;

@Entity
@Table(name = "LORE_TYPE_ORGAN")
@BatchSize(size = 50)
@DynamicInsert
public class LoreTypeOrgan extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7902936867123415817L;

	@Id
	@GeneratedValue(generator = "assignedGenerator")
	@GenericGenerator(name = "assignedGenerator", strategy = "assigned")
	@Column(length = 40)
	private String id;

	@Column(length = 40)
	private String lore_type;

	/** 名称 */
	@Column(length = 40)
	private String organ_id;

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

	public String getLore_type() {
		return lore_type;
	}

	public void setLore_type(String lore_type) {
		this.lore_type = lore_type;
	}

	public String getOrgan_id() {
		return organ_id;
	}

	public void setOrgan_id(String organ_id) {
		this.organ_id = organ_id;
	}

}