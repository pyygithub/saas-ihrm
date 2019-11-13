package com.pyy.ihrm.system.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ---------------------------
 * API接口权限 (PermissionApi)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@Table(name = "permission_api")
public class PermissionApi {

	/** ID */
	@Id
	private String id;
	/** 权限ID */
	private String permissionId;
	/** 链接 */
	private String apiUrl;
	/** 请求类型 */
	private String apiMethod;
	/** 权限等级，1为通用接口权限，2为需校验接口权限 */
	private String apiLevel;

}