package com.pyy.ihrm.system.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ---------------------------
 * 菜单权限 (PermissionMenu)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@Table(name = "permission_menu")
public class PermissionMenu {

	/** ID */
	@Id
	private String id;
	/** 权限ID */
	private String permissionId;
	/** 图标 */
	private String icon;
	/** 地址 */
	private String uri;
	/** 排序号 */
	private Integer orderNo;

}