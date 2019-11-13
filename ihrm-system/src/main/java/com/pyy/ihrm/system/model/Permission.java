package com.pyy.ihrm.system.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ---------------------------
 * 权限 (Permission)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@Table(name = "permission")
public class Permission {

	/** ID */
	@Id
	private String id;
	/** 父节点id */
	private String parentId;
	/** 企业id */
	private String companyId;
	/** 权限名称 */
	private String name;
	/** 权限标识 */
	private String code;
	/** 权限类型: 1 菜单 2 功能 3 API */
	private String type;
	/** 权限描述 */
	private String description;
	/** 企业可见状态: 0 不可见 1 可见 */
	private String enVisible;
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