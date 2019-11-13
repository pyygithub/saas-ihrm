package com.pyy.ihrm.system.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ---------------------------
 * 按钮权限 (PermissionButton)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@Table(name = "permission_button")
public class PermissionButton {

	/** ID */
	@Id
	private String id;
	/** 权限ID */
	private String permissionId;
	/** 图标 */
	private String icon;

}