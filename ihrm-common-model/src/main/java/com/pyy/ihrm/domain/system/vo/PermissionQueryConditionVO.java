package com.pyy.ihrm.domain.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * ---------------------------
 * 模糊查询条件VO
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@ApiModel(value = "PermissionQueryConditionVO",description = "权限类")
public class PermissionQueryConditionVO {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("企业ID")
    private String companyId;

    @ApiModelProperty("parentId")
    private String parentId;

    @ApiModelProperty("权限类型: 1 菜单 2 功能 3 API")
    private String type;

    @ApiModelProperty("企业可见状态: 0 不可见 1 可见")
    private String enVisible;
}