package com.pyy.ihrm.domain.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ---------------------------
 * API接口权限 (PermissionApi)         
 * ---------------------------
 * 作者：  
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@ApiModel(value = "PermissionApiVO",description = "API接口权限类")
public class PermissionApiVO {

	@ApiModelProperty("ID")
    private String id;
	@ApiModelProperty("权限ID")
    private String permissionId;
	@ApiModelProperty("链接")
    private String apiUrl;
	@ApiModelProperty("请求类型")
    private String apiMethod;
	@ApiModelProperty("权限等级，1为通用接口权限，2为需校验接口权限")
    private String apiLevel;

}