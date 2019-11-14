package com.pyy.ihrm.domain.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * ---------------------------
 * 角色 + 权限类
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@ApiModel(value = "RolePermissionsVO",description = "角色 + 权限类")
public class RolePermissionsVO extends RoleVO{

	@ApiModelProperty("角色关联权限ID集合")
    private List<String> permissionIds;

}