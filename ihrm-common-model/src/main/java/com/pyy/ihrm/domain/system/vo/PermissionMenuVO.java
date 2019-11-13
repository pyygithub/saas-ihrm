package com.pyy.ihrm.domain.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ---------------------------
 * 菜单权限 (PermissionMenu)         
 * ---------------------------
 * 作者：  
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@ApiModel(value = "PermissionMenuVO",description = "菜单权限类")
public class PermissionMenuVO {

	@ApiModelProperty("ID")
    private String id;
	@ApiModelProperty("权限ID")
    private String permissionId;
	@ApiModelProperty("图标")
    private String icon;
	@ApiModelProperty("地址")
    private String uri;
	@ApiModelProperty("排序号")
    private Integer orderNo;

}