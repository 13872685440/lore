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
import com.cat.system.model.Organ;

@Entity
@Table(name = "USER_LOREITG")
@BatchSize(size = 50)
@DynamicInsert
public class UserLoreItg extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5004225040622848867L;

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 40)
	private String id;

	/** 登陆名 */
	@Column(length = 40)
	private String user_id;

	/** 机构 */
	@Column(length = 40)
	private String organ_id;

	/** 用户名 */
	@Column
	private Integer integral;

	@Column(length = 40)
	private String user_name;

	@Transient
	private String organ_name;

	public String getOrgan_id() {
		return organ_id;
	}

	public void setOrgan_id(String organ_id) {
		this.organ_id = organ_id;
	}

	public String getOrgan_name() {
		if (!StringUtil.isEmpty(organ_id)) {
			Organ organ = (Organ) getService().findById(Organ.class, organ_id);
			if (null != organ) {
				return organ.getName();
			}
		}
		return organ_name;
	}

	public void setOrgan_name(String organ_name) {
		this.organ_name = organ_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
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

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

}
