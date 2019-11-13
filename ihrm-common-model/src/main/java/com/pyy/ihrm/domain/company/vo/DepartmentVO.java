package com.pyy.ihrm.domain.company.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ---------------------------
 * 部门 (Department)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:03:44
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@ApiModel(value = "DepartmentVO",description = "部门类")
public class DepartmentVO {

	@ApiModelProperty("ID")
    private String id;
	@ApiModelProperty("父级ID")
    private String parentId;
	@ApiModelProperty("企业ID")
    private String companyId;
	@ApiModelProperty("部门编码")
    private String code;
	@ApiModelProperty("部门名称")
    private String name;
	@ApiModelProperty("负责人ID")
    private String managerId;
	@ApiModelProperty("负责人名称")
    private String managerName;
	@ApiModelProperty("介绍")
    private String introduce;
	@ApiModelProperty("创建人ID")
    private String createId;
	@ApiModelProperty("创建人名称")
    private String createName;
	@ApiModelProperty("创建时间")
    private java.util.Date createTime;
	@ApiModelProperty("更新人ID")
    private String updateId;
	@ApiModelProperty("更新人名称")
    private String updateName;
	@ApiModelProperty("更新时间")
    private java.util.Date updateTime;
	@ApiModelProperty("删除标记 0：正常 1：删除")
    private Integer isDeleted;

}