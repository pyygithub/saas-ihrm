package com.pyy.ihrm.domain.company.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * ---------------------------
 * 模糊查询条件VO
 * ---------------------------
 * 作者：  
 * 时间：  2019-11-13 10:03:44
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@ApiModel(value = "DepartmentQueryConditionVO",description = "部门类")
public class DepartmentQueryConditionVO {

    @ApiModelProperty("名称")
    private String name;

}