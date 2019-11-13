package com.pyy.ihrm.domain.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ---------------------------
 * 权限 (Permission)         
 * ---------------------------
 * 作者：  
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@ApiModel(value = "PermissionVO",description = "权限类")
public class PermissionVO {

	@ApiModelProperty("ID")
    private String id;
	@ApiModelProperty("权限名称")
    private String name;
	@ApiModelProperty("权限标识")
    private String code;
	@ApiModelProperty("权限类型: 1为菜单 2为功能 3为API")
    private String type;
	@ApiModelProperty("权限描述")
    private String description;
	@ApiModelProperty("可见状态: 0 不可见 1 可见")
    private String enVisible;
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