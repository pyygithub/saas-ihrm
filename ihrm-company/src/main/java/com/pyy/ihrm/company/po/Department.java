package com.pyy.ihrm.company.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ---------------------------
 * 部门 (Department)         
 * ---------------------------
 * 作者：  
 * 时间：  2019-11-13 10:03:44
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@Table(name = "department")
public class Department {

	/** ID */
	@Id
	private String id;
	/** 父级ID */
	private String parentid;
	/** 企业ID */
	private String companyId;
	/** 部门编码 */
	private String code;
	/** 部门名称 */
	private String name;
	/** 负责人ID */
	private String managerId;
	/** 负责人名称 */
	private String managerName;
	/** 介绍 */
	private String introduce;
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