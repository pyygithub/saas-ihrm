package com.pyy.ihrm.domain.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@ApiModel(value = "PermissionMenuSaveOrUpdateVO",description = "菜单权限保存和修改VO类")
public class PermissionMenuSaveOrUpdateVO {

	@ApiModelProperty("图标")
	private String icon;
	@ApiModelProperty("地址")
	private String uri;
	@ApiModelProperty("排序号")
	private Integer orderNo;

}