package com.pyy.ihrm.domain.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ---------------------------
 * 角色 (Role)         
 * ---------------------------
 * 作者：  
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@ApiModel(value = "RoleVO",description = "角色类")
public class RoleVO {

	@ApiModelProperty("ID")
    private String id;
	@ApiModelProperty("企业id")
    private String companyId;
	@ApiModelProperty("说明")
    private String description;
	@ApiModelProperty("角色名")
    private String name;
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