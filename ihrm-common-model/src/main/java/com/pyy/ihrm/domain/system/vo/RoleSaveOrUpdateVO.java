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
@ApiModel(value = "RoleSaveOrUpdateVO",description = "角色保存和修改VO类")
public class RoleSaveOrUpdateVO {

	@ApiModelProperty("ID")
	private String id;
	@ApiModelProperty("企业id")
	private String companyId;
	@ApiModelProperty("说明")
	private String description;
	@ApiModelProperty("角色名")
	private String name;
	@ApiModelProperty("操作人ID")
	private String operaterId;
	@ApiModelProperty("操作人名称")
	private String operaterName;
}