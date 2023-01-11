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
@Table(name = "LORE_MARK")
@BatchSize(size = 50)
@DynamicInsert
public class LoreMark extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 331820207299001019L;

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 40)
	private String id;

	@Column(length = 40)
	private String lore_id;

	@Column(length = 40)
	private String user_id;

	@Column
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getLore_id() {
		return lore_id;
	}

	public void setLore_id(String lore_id) {
		this.lore_id = lore_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

}
