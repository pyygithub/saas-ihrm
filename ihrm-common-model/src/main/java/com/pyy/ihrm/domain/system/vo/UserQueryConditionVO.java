package com.pyy.ihrm.domain.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@ApiModel(value = "UserQueryConditionVO",description = "用户类")
public class UserQueryConditionVO {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("在职状态 1：在职  2：离职")
    private String inServiceStatus;

    @ApiModelProperty("启用状态 0：禁用  1：启用")
    private String enableState;

    @ApiModelProperty("部门ID")
    private String departmentId;

    @ApiModelProperty("企业ID")
    private String companyId;
}