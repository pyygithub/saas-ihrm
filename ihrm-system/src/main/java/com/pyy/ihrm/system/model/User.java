package com.pyy.ihrm.system.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ---------------------------
 * 用户 (User)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@Table(name = "user")
public class User {

	/** ID */
	@Id
	private String id;
	/** 工号 */
	private String workNumber;
	/** 企业ID */
	private String companyId;
	/** 部门ID */
	private String departmentId;
	/** 用户名称 */
	private String username;
	/** 密码 */
	private String password;
	/** 手机号码 */
	private String mobile;
	/** 入职时间 */
	private java.util.Date timeOfEntry;
	/** 聘用形式 */
	private String formOfEmployment;
	/** 管理形式 */
	private String formOfManagement;
	/** 工作城市 */
	private String workingCity;
	/** 转正时间 */
	private java.util.Date correctionTime;
	/** 在职状态 1：在职  2：离职 */
	private String inServiceStatus;
	/** 启用状态 0：禁用  1：启用 */
	private String enableState;
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