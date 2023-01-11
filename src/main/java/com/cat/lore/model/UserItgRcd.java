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

@Entity
@Table(name = "USER_ITGRCD")
@BatchSize(size = 50)
@DynamicInsert
public class UserItgRcd extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1483805746482848561L;

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 40)
	private String id;

	@Column(length = 40)
	private String integralId;

	/** 用户名 */
	@Column(length = 50)
	private Integer integral;

	/** 关联实体 */
	@Column(length = 50, nullable = false)
	private String lore_id;

	/** 用户 */
	@Column(length = 50, nullable = false)
	private String user_id;

	@Transient
	private String lore_name;

	public String getLore_name() {
		return lore_name;
	}

	public void setLore_name(String lore_name) {
		this.lore_name = lore_name;
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

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getIntegralId() {
		return integralId;
	}

	public void setIntegralId(String integralId) {
		this.integralId = integralId;
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
