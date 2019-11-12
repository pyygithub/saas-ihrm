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
 * 时间：  2019-11-12 10:10:10
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@ApiModel(value = "CompanyQueryConditionVO",description = "企业信息类")
public class CompanyQueryConditionVO {

    @ApiModelProperty("名称")
    private String name;

}