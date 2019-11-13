package com.pyy.ihrm.system.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ---------------------------
 * 用户角色 (UserRole)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@Table(name = "user_role")
public class UserRole {

	/** 用户ID */
	@Id
	private String userId;
	/** 角色ID */
	@Id
	private String roleId;

}