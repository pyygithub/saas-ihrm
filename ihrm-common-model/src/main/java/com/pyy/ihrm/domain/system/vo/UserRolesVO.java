package com.pyy.ihrm.domain.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * ---------------------------
 * 用户 + 关联角色 (roleIds)
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@ApiModel(value = "UserRolesVO",description = "用户 + 关联角色 (roleIds)")
public class UserRolesVO extends UserVO{

    @ApiModelProperty("用户拥有角色ID集合")
    private List<String> roleIds;
}