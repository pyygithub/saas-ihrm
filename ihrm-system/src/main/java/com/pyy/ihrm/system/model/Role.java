package com.pyy.ihrm.system.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ---------------------------
 * 角色 (Role)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@Table(name = "role")
public class Role {

	/** ID */
	@Id
	private String id;
	/** 企业id */
	private String companyId;
	/** 说明 */
	private String description;
	/** 角色名 */
	private String name;
	/** 创建人ID */
	private String createId;
	/** 创建人名称 */
	private String createName;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 更新人ID */
	private String updateId;
	/** 更新人名称 */
	private String updateName;
	/** 更新时间 */
	private java.util.Date updateTime;
	/** 删除标记 0：正常 1：删除 */
	private Integer isDeleted;

}