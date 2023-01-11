package com.cat.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import com.cat.boot.model.BaseEntity;

@Entity
@Table(name = "SYS_APPVERSION")
@Inheritance(strategy = InheritanceType.JOINED)
@BatchSize(size = 50)
@DynamicInsert
public class AppVersion extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6096004078942966003L;

	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 40)
	private String id;

	/** 版本号 */
	@Column
	private Double version_code;

	/** 版本名称 */
	@Column(length = 40)
	private String version_name;

	/** 版本描述 */
	@Column(length = 400)
	private String version_des;

	/** 下载的URL */
	@Column(length = 40)
	private String version_app;

	/** 版本平台 */
	@Column(length = 40)
	private String version_platform;

	/***/
	@Column(length = 40)
	private String isnew;

	public Double getVersion_code() {
		return version_code;
	}

	public void setVersion_code(Double version_code) {
		this.version_code = version_code;
	}

	public String getVersion_name() {
		return version_name;
	}

	public void setVersion_name(String version_name) {
		this.version_name = version_name;
	}

	public String getVersion_des() {
		return version_des;
	}

	public void setVersion_des(String version_des) {
		this.version_des = version_des;
	}

	public String getVersion_app() {
		return version_app;
	}

	public void setVersion_app(String version_app) {
		this.version_app = version_app;
	}

	public String getVersion_platform() {
		return version_platform;
	}

	public void setVersion_platform(String version_platform) {
		this.version_platform = version_platform;
	}

	public String getIsnew() {
		return isnew;
	}

	public void setIsnew(String isnew) {
		this.isnew = isnew;
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
