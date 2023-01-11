package com.cat.task.model;

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
@Table(name = "Task_TaskRouter")
@BatchSize(size = 50)
@DynamicInsert
public class TaskRouter extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6962640826437122117L;

	/** 编码 作为ID使用 */
	@Id
	@GeneratedValue(generator = "assignedGenerator")
	@GenericGenerator(name = "assignedGenerator", strategy = "assigned")
	@Column(length = 40)
	private String id;

	/** 本级代码 current Level Code */
	@Column(length = 20, nullable = false)
	private String clc;

	/** 名称 */
	@Column(length = 500, nullable = false)
	private String name;

	/** 上级 */
	@Column(length = 40)
	private String scId;

	/** 末级 */
	@Column(nullable = false)
	private boolean isLeaf = true;
	
	/** 组件 */
	@Column(length = 100)
	private String component;
	
	/** 步骤 */
	@Column(length = 10)
	private Integer step;
	
	@Transient
	private String scName;

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

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Override
	public String toString() {
		return this.id;
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
	
	public String getScName() {
		if(!StringUtil.isEmpty(this.scId)) {
			TaskRouter o = (TaskRouter)getService().findById(TaskRouter.class, this.scId);
			if(o!=null) {
				return o.getName();
			}
		}
		return scName;
	}

	public String getScId() {
		return scId;
	}

	public void setScId(String scId) {
		this.scId = scId;
	}

	public void setScName(String scName) {
		this.scName = scName;
	}

}
