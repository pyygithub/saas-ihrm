package com.pyy.ihrm.domain.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@ApiModel(value = "PermissionButtonSaveOrUpdateVO",description = "按钮权限保存和修改VO类")
public class PermissionButtonSaveOrUpdateVO {

	@ApiModelProperty("图标")
	private String icon;

}