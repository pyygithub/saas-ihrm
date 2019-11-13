package com.pyy.ihrm.system.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ---------------------------
 * 角色权限 (RolePermission)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@Table(name = "role_permission")
public class RolePermission {

	/** 角色ID */
	@Id
	private String roleId;
	/** 权限ID */
	@Id
	private String permissionId;

}