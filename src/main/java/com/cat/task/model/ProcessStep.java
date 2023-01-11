package com.cat.task.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

@Entity
@Table(name = "Task_Process_Step")
@BatchSize(size = 50)
@DynamicInsert
public class ProcessStep extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3526381516313640211L;

	@Id
	@GeneratedValue(generator = "assignedGenerator")
	@GenericGenerator(name = "assignedGenerator", strategy = "assigned")
	@Column(length = 40)
	private String id;
	
	@Column(length = 40)
	private String appId;

	/** 本级代码 current Level Code */
	@Column(length = 8, nullable = false)
	private String clc;

	/** 名称 */
	@Column(length = 500, nullable = false)
	private String name;
	
	/** 关联的表单的table名 用来更新实体 */
	@Column(length = 500)
	private String table_name;

	/** 上级  */
	@Column(length = 40)
	private String scId;

	/** 末级 */
	@Column(nullable = false,columnDefinition="int")
	private boolean isLeaf = true;
	
	/** 组件 */
	@Column(length = 100)
	private String component;
	
	/** 主服务路径 */
	@Column(length = 100)
	private String service_path;
	
	/** 服务子路径 */
	@Column(length = 20)
	private String service_path_type;
	
	/** 步骤 */
	@Column(length = 10)
	private Integer step;
	
	/** 当前审核人类型 1:用户 2:岗位角色 3:领导角色 4:其他 5:会签*/
	@Column(length = 10)
	private String shr_type;
	
	/** 当前审核人 用户IDs*/
	@Column(length = 1000)
	private String userIds;
	
	/** 当前审核角色 */
	@Column(length = 1000)
	private String roles;
	
	/** 当前审核领导角色 */
	@Column(length = 1000)
	private String ld_roles;
	
	/** 当前审核角色类型 1:上级部门 2:本级部门 3:指定部门 4:不指定部门*/
	@Column(length = 10)
	private String role_type;
	
	/** 当前审核机构 与角色配套使用*/
	@Column(length = 1000)
	private String organIds;
	
	/** 抄送人类型 1:用户 2:岗位角色 3:领导角色*/
	@Column(length = 10)
	private String csr_type;
	
	/** 抄送人 用户IDs*/
	@Column(length = 1000)
	private String cs_userIds;
	
	/** 抄送角色 */
	@Column(length = 1000)
	private String cs_roles;
	
	/** 抄送领导角色 */
	@Column(length = 1000)
	private String cs_ld_roles;
	
	/** 抄送角色类型 1:上级部门 2:本级部门 3:指定部门 4:不指定部门*/
	@Column(length = 10)
	private String cs_role_type;
	
	/** 抄送机构 与角色配套使用*/
	@Column(length = 1000)
	private String cs_organIds;
	
	/** 超时时间 */
	@Column(length = 10)
	private Integer time_out;
	
	/** 是否需要督办 0 不督办 1 督办*/
	@Column(length = 10)
	private String has_supervise;
	
	/** 督办人类型 1=用户 2=角色*/
	@Column(length = 10)
	private String supervise_type;
	
	/** 督办人 用户IDs*/
	@Column(length = 1000)
	private String supervise_userIds;
	
	/** 督办角色 */
	@Column(length = 1000)
	private String supervise_roles;
	
	@Transient
	private String scName;
	
	@Transient
	private String sc_TableName;
	
	@Transient
	private List<String> user_s = new ArrayList<String>();
	
	@Transient
	private List<String> role_s = new ArrayList<String>();
	
	@Transient
	private List<String> ld_role_s = new ArrayList<String>();
	
	@Transient
	private List<String> organ_s = new ArrayList<String>();
	
	@Transient
	private List<String> cs_user_s = new ArrayList<String>();
	
	@Transient
	private List<String> cs_role_s = new ArrayList<String>();
	
	@Transient
	private List<String> cs_ld_role_s = new ArrayList<String>();
	
	@Transient
	private List<String> cs_organ_s = new ArrayList<String>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getScId() {
		return scId;
	}

	public void setScId(String scId) {
		this.scId = scId;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public String getScName() {
		if(!StringUtil.isEmpty(this.scId)) {
			ProcessStep o = (ProcessStep)getService().findById(ProcessStep.class, this.scId);
			if(o!=null) {
				return o.getName();
			}
		}
		return scName;
	}

	public void setScName(String scName) {
		this.scName = scName;
	}

	public String getShr_type() {
		return shr_type;
	}

	public void setShr_type(String shr_type) {
		this.shr_type = shr_type;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getRole_type() {
		return role_type;
	}

	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getOrganIds() {
		return organIds;
	}

	public void setOrganIds(String organIds) {
		this.organIds = organIds;
	}

	public String getService_path() {
		return service_path;
	}

	public void setService_path(String service_path) {
		this.service_path = service_path;
	}

	public String getService_path_type() {
		return service_path_type;
	}

	public void setService_path_type(String service_path_type) {
		this.service_path_type = service_path_type;
	}

	public List<String> getUser_s() {
		if(!StringUtil.isListEmpty(user_s)) {
			return user_s;
		}
		if(!StringUtil.isEmpty(this.userIds)) {
			user_s.addAll(Arrays.asList(this.userIds.split(",")));
			return user_s;
		}
		return user_s;
	}

	public void setUser_s(List<String> user_s) {
		this.user_s = user_s;
	}
	
	public List<String> getRole_s() {
		if(!StringUtil.isListEmpty(role_s)) {
			return role_s;
		}
		if(!StringUtil.isEmpty(this.roles)) {
			role_s.addAll(Arrays.asList(this.roles.split(",")));
			return role_s;
		}
		return role_s;
	}

	public void setRole_s(List<String> role_s) {
		this.role_s = role_s;
	}

	public String getLd_roles() {
		return ld_roles;
	}

	public void setLd_roles(String ld_roles) {
		this.ld_roles = ld_roles;
	}

	public List<String> getLd_role_s() {
		if(!StringUtil.isListEmpty(ld_role_s)) {
			return ld_role_s;
		}
		if(!StringUtil.isEmpty(this.ld_roles)) {
			ld_role_s.addAll(Arrays.asList(this.ld_roles.split(",")));
			return ld_role_s;
		}
		return ld_role_s;
	}

	public void setLd_role_s(List<String> ld_role_s) {
		this.ld_role_s = ld_role_s;
	}

	public List<String> getOrgan_s() {
		if(!StringUtil.isListEmpty(organ_s)) {
			return organ_s;
		}
		if(!StringUtil.isEmpty(this.organIds)) {
			organ_s.addAll(Arrays.asList(this.organIds.split(",")));
			return organ_s;
		}
		return organ_s;
	}

	public void setOrgan_s(List<String> organ_s) {
		this.organ_s = organ_s;
	}

	public String getSc_TableName() {
		if(!StringUtil.isEmpty(this.scId)) {
			ProcessStep p = (ProcessStep)getService().findById(ProcessStep.class, this.scId);
			return p.getTable_name();
		}
		return sc_TableName;
	}

	public void setSc_TableName(String sc_TableName) {
		this.sc_TableName = sc_TableName;
	}

	public Integer getTime_out() {
		return time_out;
	}

	public void setTime_out(Integer time_out) {
		this.time_out = time_out;
	}

	public String getHas_supervise() {
		return has_supervise;
	}

	public void setHas_supervise(String has_supervise) {
		this.has_supervise = has_supervise;
	}

	public String getSupervise_type() {
		return supervise_type;
	}

	public void setSupervise_type(String supervise_type) {
		this.supervise_type = supervise_type;
	}

	public String getSupervise_userIds() {
		return supervise_userIds;
	}

	public void setSupervise_userIds(String supervise_userIds) {
		this.supervise_userIds = supervise_userIds;
	}

	public String getSupervise_roles() {
		return supervise_roles;
	}

	public void setSupervise_roles(String supervise_roles) {
		this.supervise_roles = supervise_roles;
	}

	public String getCsr_type() {
		return csr_type;
	}

	public void setCsr_type(String csr_type) {
		this.csr_type = csr_type;
	}

	public String getCs_userIds() {
		return cs_userIds;
	}

	public void setCs_userIds(String cs_userIds) {
		this.cs_userIds = cs_userIds;
	}

	public String getCs_roles() {
		return cs_roles;
	}

	public void setCs_roles(String cs_roles) {
		this.cs_roles = cs_roles;
	}

	public String getCs_ld_roles() {
		return cs_ld_roles;
	}

	public void setCs_ld_roles(String cs_ld_roles) {
		this.cs_ld_roles = cs_ld_roles;
	}

	public String getCs_role_type() {
		return cs_role_type;
	}

	public void setCs_role_type(String cs_role_type) {
		this.cs_role_type = cs_role_type;
	}

	public String getCs_organIds() {
		return cs_organIds;
	}

	public void setCs_organIds(String cs_organIds) {
		this.cs_organIds = cs_organIds;
	}

	public List<String> getCs_user_s() {
		if(!StringUtil.isListEmpty(cs_user_s)) {
			return cs_user_s;
		}
		if(!StringUtil.isEmpty(this.cs_userIds)) {
			cs_user_s.addAll(Arrays.asList(this.cs_userIds.split(",")));
			return cs_user_s;
		}
		return cs_user_s;
	}

	public void setCs_user_s(List<String> cs_user_s) {
		this.cs_user_s = cs_user_s;
	}

	public List<String> getCs_role_s() {
		if(!StringUtil.isListEmpty(cs_role_s)) {
			return cs_role_s;
		}
		if(!StringUtil.isEmpty(this.cs_roles)) {
			cs_role_s.addAll(Arrays.asList(this.cs_roles.split(",")));
			return cs_role_s;
		}
		return cs_role_s;
	}

	public void setCs_role_s(List<String> cs_role_s) {
		this.cs_role_s = cs_role_s;
	}

	public List<String> getCs_ld_role_s() {
		if(!StringUtil.isListEmpty(cs_ld_role_s)) {
			return cs_ld_role_s;
		}
		if(!StringUtil.isEmpty(this.cs_ld_roles)) {
			cs_ld_role_s.addAll(Arrays.asList(this.cs_ld_roles.split(",")));
			return cs_ld_role_s;
		}
		return cs_ld_role_s;
	}

	public void setCs_ld_role_s(List<String> cs_ld_role_s) {
		this.cs_ld_role_s = cs_ld_role_s;
	}

	public List<String> getCs_organ_s() {
		if(!StringUtil.isListEmpty(cs_organ_s)) {
			return cs_organ_s;
		}
		if(!StringUtil.isEmpty(this.cs_organIds)) {
			cs_organ_s.addAll(Arrays.asList(this.cs_organIds.split(",")));
			return cs_organ_s;
		}
		return cs_organ_s;
	}

	public void setCs_organ_s(List<String> cs_organ_s) {
		this.cs_organ_s = cs_organ_s;
	}

	@Override
	public String toString() {
		return this.id;
	}

}
